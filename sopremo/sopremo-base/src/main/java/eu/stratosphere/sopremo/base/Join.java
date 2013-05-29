package eu.stratosphere.sopremo.base;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.expressions.AggregationExpression;
import eu.stratosphere.sopremo.expressions.AndExpression;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.ArrayCreation;
import eu.stratosphere.sopremo.expressions.BinaryBooleanExpression;
import eu.stratosphere.sopremo.expressions.BooleanExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression.BinaryOperator;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.ExpressionUtil;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.expressions.ObjectCreation;
import eu.stratosphere.sopremo.expressions.TransformFunction;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.CompositeOperator;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.JsonStream;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.OutputCardinality;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.operator.SopremoModule;
import eu.stratosphere.sopremo.rewrite.ReplaceInputSelectionWithArray;
import eu.stratosphere.util.IsInstancePredicate;

/**
 * Executes a complex N-way join over multiple data sources.<br>
 * Implementation note (may change in the future): <br>
 * The data schema for a join is a position-encoding array [record1, record2, ..., recordN].
 * The position of each record corresponds with the index of the input.<br>
 * Originally, for each input, the input is translated to an array, e.g. [null, record, null, ... null] for the second
 * source.<br>
 * Then TwoSourceJoins are successively executed to merge the arrays and fill the null values. Finally, a projection is
 * executed on the result array.
 */
@InputCardinality(min = 2)
@OutputCardinality(1)
@Name(verb = "join")
public class Join extends CompositeOperator<Join> {
	private BooleanExpression joinCondition = new AndExpression();

	private List<BinaryBooleanExpression> binaryConditions = new ArrayList<BinaryBooleanExpression>();

	private EvaluationExpression resultProjection = ObjectCreation.CONCATENATION;

	private final IntSet outerJoinSources = new IntOpenHashSet();

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.CompositeOperator#asModule(eu.stratosphere.sopremo.EvaluationContext)
	 */
	@Override
	public void addImplementation(SopremoModule module, EvaluationContext context) {
		switch (this.binaryConditions.size()) {
		case 0:
			throw new IllegalStateException("No join condition specified");
		case 1:
			// only two way join
			final TwoSourceJoin join = new TwoSourceJoin().
				withOuterJoinIndices(this.outerJoinSources.toIntArray()).
				withInputs(module.getInputs()).
				withCondition(this.binaryConditions.get(0)).
				withResultProjection(this.getResultProjection());
			module.getOutput(0).setInput(0, join);
			break;

		default:
			List<TwoSourceJoin> joins;
			List<BinaryBooleanExpression> minimalSpanningTree = findMinimalSpanningTree(this.binaryConditions); 
			joins = this.getInitialJoinOrder(module, minimalSpanningTree);
			
			//TODO please comment
			final List<JsonStream> inputs = new ArrayList<JsonStream>();
			int numInputs = this.getNumInputs();
			for (int index = 0; index < numInputs; index++)
				inputs.add(OperatorUtil.positionEncode(module.getInput(index), index, numInputs));
			
			//TODO please comment
			for (final TwoSourceJoin twoSourceJoin : joins) {
				List<JsonStream> operatorInputs = twoSourceJoin.getInputs();

				final List<JsonStream> actualInputs = new ArrayList<JsonStream>(operatorInputs.size());
				List<Source> moduleInput = module.getInputs();
				for (int index = 0; index < operatorInputs.size(); index++) {
					final int inputIndex = moduleInput.indexOf(operatorInputs.get(index).getSource().getOperator());
					actualInputs.add(inputs.get(inputIndex).getSource());
					inputs.set(inputIndex, twoSourceJoin);
				}
				twoSourceJoin.setInputs(actualInputs);
				twoSourceJoin.setResultProjection(new AggregationExpression(new ArrayUnion()));
			}
			
			final TwoSourceJoin lastJoin = joins.get(joins.size() - 1);
			ElementaryOperator<?> lastOperator = lastJoin; 
			
			//check if any cycles exist in the predicates, if so create post-selection with left predicates not in the MST
			if (minimalSpanningTree.size() != this.binaryConditions.size()) {
				List<BinaryBooleanExpression> leftExpressions = new ArrayList<BinaryBooleanExpression>();
				for(BinaryBooleanExpression leftExpressionCandidate : this.binaryConditions){
					if(!minimalSpanningTree.contains(leftExpressionCandidate)){
						BinaryBooleanExpression adjustedExpression = (BinaryBooleanExpression) leftExpressionCandidate.clone();
						adjustedExpression.replace(new IsInstancePredicate(InputSelection.class),
							new TransformFunction() {
								@Override
								public EvaluationExpression apply(EvaluationExpression argument) {
									InputSelection inputSelection = (InputSelection) argument;
									final int originalIndex = inputSelection.getIndex();
									return ExpressionUtil.makePath(new InputSelection(0), new ArrayAccess(originalIndex));
								}
							});
						leftExpressions.add(adjustedExpression);
					}
				}
				final AndExpression selectionCondition = new AndExpression(leftExpressions);
				lastOperator = new Selection()
							.withCondition(selectionCondition).withInputs(lastJoin);
			}
			
			EvaluationExpression resultProjection = this.getResultProjection();
			resultProjection.replace(new IsInstancePredicate(InputSelection.class),
				new ReplaceInputSelectionWithArray());
			module.getOutput(0).setInput(0,
				new Projection().withInputs(lastOperator).withResultProjection(resultProjection));
		}
	}
	
	/**
	 * Finds the minimal spanning tree over a graph of Join predicates based on the Kruskal's algorithm.
	 * @param someBinaryConditions
	 * @return the minimal spanning tree over the input graph of expressions
	 */
	private List<BinaryBooleanExpression> findMinimalSpanningTree(List<BinaryBooleanExpression> someBinaryConditions) {
		
		List<BinaryBooleanExpression> minimalSpanningTree = new ArrayList<BinaryBooleanExpression>();
		
		Set<InputSelection> vertices = buildVerticesSet(someBinaryConditions);
		Map<BinaryBooleanExpression, Integer> edgesWithWeight = buildWeightedEdgesMap(someBinaryConditions);
		List<BinaryBooleanExpression> sortedEdges = sortEdgesByWeight(edgesWithWeight); 
		
		while(!sortedEdges.isEmpty()){
			BinaryBooleanExpression currentEdge = sortedEdges.remove(0);
			if(!currentEdgeProducesCycleInMST(currentEdge, vertices)){
				minimalSpanningTree.add(currentEdge);
			}
		}
		return minimalSpanningTree;
	}
	
	private List<BinaryBooleanExpression> sortEdgesByWeight(Map<BinaryBooleanExpression, Integer> edges) {
		SortedMap<BinaryBooleanExpression, Integer> sortedMap = new TreeMap<BinaryBooleanExpression, Integer>(new ValueComparer<BinaryBooleanExpression, Integer>(edges) );
        sortedMap.putAll(edges);
        return new ArrayList<BinaryBooleanExpression>(sortedMap.keySet());
	}
	
	//adapted from http://paaloliver.wordpress.com/2011/02/11/take-2-sorting-maps-in-java-based-on-it%E2%80%99s-values/
	private static class ValueComparer<K, V extends Comparable<V>> implements Comparator<K> {

        private final Map<K, V> map;

        public ValueComparer(Map<K, V> map) {
            super();
            this.map = map;
        }

        public int compare(K key1, K key2) {
            V value1 = this.map.get(key1);
            V value2 = this.map.get(key2);
            int c = value1.compareTo(value2);
            if (c != 0) {
                return c;
            }
            Integer hashCode1 = key1.hashCode();
            Integer hashCode2 = key2.hashCode();
            return hashCode1.compareTo(hashCode2);
        }
    }

	private boolean currentEdgeProducesCycleInMST(BinaryBooleanExpression currentEdge, Set<InputSelection> vertices) {
		currentEdge = (ComparativeExpression) currentEdge;
		ObjectAccess expr1 = (ObjectAccess) ((ComparativeExpression) currentEdge).getExpr1();
		ObjectAccess expr2 = (ObjectAccess) ((ComparativeExpression) currentEdge).getExpr2();
		InputSelection vertex1 = (InputSelection) expr1.getInputExpression();
		InputSelection vertex2 = (InputSelection) expr2.getInputExpression();
		
		//an edge does not produce a cycle if it points to at least one remaining vertex
		if (vertices.contains(vertex1)) {
			vertices.remove(vertex1);
			if (vertices.contains(vertex2)) {
				vertices.remove(vertex2);
			}
			return false;
		} else if (vertices.contains(vertex2)) {
			vertices.remove(vertex2);
			return false;
		}
		return true;
	}

	private Map<BinaryBooleanExpression, Integer> buildWeightedEdgesMap(List<BinaryBooleanExpression> someBinaryConditions) {
		Map<BinaryBooleanExpression, Integer> edgesWithWeight = new HashMap<BinaryBooleanExpression, Integer>();
		for (BinaryBooleanExpression expression : someBinaryConditions) {
			//TODO better weighting schema required
			Integer weight;
			if(((ComparativeExpression) expression).getBinaryOperator().equals(BinaryOperator.EQUAL))
				weight=1;
			else
				weight=2;
			
			edgesWithWeight.put(expression, weight);
		}
		return edgesWithWeight;
	}

	private Set<InputSelection> buildVerticesSet(List<BinaryBooleanExpression> someBinaryConditions) {
		Set<InputSelection> vertices = new HashSet<InputSelection>();
		for (BinaryBooleanExpression expression : someBinaryConditions) {
			if (expression instanceof ComparativeExpression) {
				expression = (ComparativeExpression) expression;
				ObjectAccess expr1 = (ObjectAccess) ((ComparativeExpression) expression).getExpr1();
				ObjectAccess expr2 = (ObjectAccess) ((ComparativeExpression) expression).getExpr2();
				vertices.add((InputSelection) expr1.getInputExpression());
				vertices.add((InputSelection) expr2.getInputExpression());
			}
		}
		return vertices;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		return super.equals(obj) && this.joinCondition.equals(((Join) obj).joinCondition)
				&& this.outerJoinSources.equals(((Join) obj).outerJoinSources)
				&& this.binaryConditions.equals(((Join) obj).binaryConditions)
			&& this.resultProjection.equals(((Join) obj).resultProjection);
	}

	public BooleanExpression getJoinCondition() {
		return this.joinCondition;
	}

	public int[] getOuterJoinIndices() {
		return this.outerJoinSources.toIntArray();
	}

	public EvaluationExpression getOuterJoinSources() {
		EvaluationExpression[] expressions = new EvaluationExpression[this.outerJoinSources.size()];
		final IntIterator iterator = this.outerJoinSources.iterator();
		for (int index = 0; iterator.hasNext(); index++) {
			final int inputIndex = iterator.nextInt();
			expressions[index] = new InputSelection(inputIndex);
		}
		return new ArrayCreation(expressions);
	}

	public EvaluationExpression getResultProjection() {
		return this.resultProjection;
	}

	@Override
	public int hashCode() {
		final int prime = 37;
		int result = super.hashCode();
		result = prime * result + this.joinCondition.hashCode();
		result = prime * result + this.outerJoinSources.hashCode();
		result = prime * result + this.binaryConditions.hashCode();
		result = prime * result + this.resultProjection.hashCode();
		return result;
	}

	@Property
	@Name(preposition = "where")
	public void setJoinCondition(BooleanExpression joinCondition) {
		if (joinCondition == null)
			throw new NullPointerException("joinCondition must not be null");

		final ArrayList<BinaryBooleanExpression> expressions = new ArrayList<BinaryBooleanExpression>();
		this.addBinaryExpressions(joinCondition, expressions);
		if (expressions.size() == 0)
			throw new IllegalArgumentException("No join condition given");

		this.joinCondition = joinCondition;
		this.binaryConditions = expressions;
	}

	public void setOuterJoinIndices(int... outerJoinIndices) {
		if (outerJoinIndices == null)
			throw new NullPointerException("outerJoinIndices must not be null");

		this.outerJoinSources.clear();
		for (int index : outerJoinIndices)
			this.outerJoinSources.add(index);
	}

	@Property
	@Name(verb = "preserve")
	public void setOuterJoinSources(EvaluationExpression outerJoinSources) {
		if (outerJoinSources == null)
			throw new NullPointerException("outerJoinSources must not be null");
		final Iterable<? extends EvaluationExpression> expressions;
		if (outerJoinSources instanceof InputSelection)
			expressions = Collections.singleton(outerJoinSources);
		else if (outerJoinSources instanceof ArrayCreation)
			expressions = outerJoinSources;
		else
			throw new IllegalArgumentException(String.format("Cannot interpret %s", outerJoinSources));

		this.outerJoinSources.clear();
		for (EvaluationExpression expression : expressions)
			this.outerJoinSources.add(((InputSelection) expression).getIndex());
	}

	@Property
	@Name(preposition = "into")
	public void setResultProjection(EvaluationExpression resultProjection) {
		if (resultProjection == null)
			throw new NullPointerException("resultProjection must not be null");

		this.resultProjection = resultProjection;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getName());
		builder.append(" on ").append(this.getJoinCondition());
		if (this.getResultProjection() != EvaluationExpression.VALUE)
			builder.append(" to ").append(this.getResultProjection());
		return builder.toString();
	}

	public Join withJoinCondition(BooleanExpression joinCondition) {
		this.setJoinCondition(joinCondition);
		return this;
	}

	public Join withOuterJoinIndices(int... outerJoinIndices) {
		this.setOuterJoinIndices(outerJoinIndices);
		return this;
	}

	public Join withOuterJoinSources(EvaluationExpression outerJoinSources) {
		this.setOuterJoinSources(outerJoinSources);
		return this;
	}

	public Join withResultProjection(EvaluationExpression resultProjection) {
		this.setResultProjection(resultProjection);
		return this;
	}

	private void addBinaryExpressions(BooleanExpression joinCondition, List<BinaryBooleanExpression> expressions) {
		if (joinCondition instanceof BinaryBooleanExpression)
			expressions.add((BinaryBooleanExpression) joinCondition);
		else if (joinCondition instanceof AndExpression)
			for (BooleanExpression expression : ((AndExpression) joinCondition).getExpressions())
				this.addBinaryExpressions(expression, expressions);
		else
			throw new IllegalArgumentException("Cannot handle expression " + joinCondition);
	}

	private List<TwoSourceJoin> getInitialJoinOrder(SopremoModule module, List<BinaryBooleanExpression> minimalSpanningTree) {
		final List<TwoSourceJoin> joins = new ArrayList<TwoSourceJoin>();
		for (final BinaryBooleanExpression expression : minimalSpanningTree) {
			joins.add(this.getTwoSourceJoinForExpression(expression, module));
		}
		// TODO: add some kind of optimization?
		return joins;
	}

	/**
	 * Create a TwoSourceJoin that performs the join on the given condition.<br>
	 * Adjusts the expression to the position-encoding data schema.
	 */
	private TwoSourceJoin getTwoSourceJoinForExpression(final BinaryBooleanExpression binaryCondition,
			SopremoModule module) {
		final IntList originalIndices = new IntArrayList();

		BinaryBooleanExpression adjustedExpression = (BinaryBooleanExpression) binaryCondition.clone();
		// change indices and emulate old behavior with array access
		// in1.fk == in3.key -> in0[1].fk == in1[3].key
		adjustedExpression.replace(new IsInstancePredicate(InputSelection.class),
			new TransformFunction() {
				@Override
				public EvaluationExpression apply(EvaluationExpression argument) {
					InputSelection inputSelection = (InputSelection) argument;
					final int originalIndex = inputSelection.getIndex();
					int newIndex = originalIndices.indexOf(originalIndex);
					if (newIndex == -1) {
						newIndex = originalIndices.size();
						originalIndices.add(originalIndex);
					}
					return ExpressionUtil.makePath(new InputSelection(newIndex), new ArrayAccess(originalIndex));
				}
			});
		if (originalIndices.size() != 2)
			throw new IllegalArgumentException(String.format("Condition must refer to exactly two sources: %s",
				binaryCondition));

		// translate outer join flags
		IntList outerJoinIndices = new IntArrayList();
		final int firstIndex = originalIndices.getInt(0), secondIndex = originalIndices.getInt(1);
		if (this.outerJoinSources.contains(firstIndex))
			outerJoinIndices.add(0);
		if (this.outerJoinSources.contains(secondIndex))
			outerJoinIndices.add(1);

		return new TwoSourceJoin().withOuterJoinIndices(outerJoinIndices.toIntArray()).
			withInputs(module.getInput(firstIndex), module.getInput(secondIndex)).
			withCondition(adjustedExpression);
	}

}
