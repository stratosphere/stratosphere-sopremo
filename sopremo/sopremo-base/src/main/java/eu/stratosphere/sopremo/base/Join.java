package eu.stratosphere.sopremo.base;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.AbstractObject2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Predicates;

import eu.stratosphere.sopremo.base.join.ThetaJoin;
import eu.stratosphere.sopremo.expressions.AggregationExpression;
import eu.stratosphere.sopremo.expressions.AndExpression;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.ArrayCreation;
import eu.stratosphere.sopremo.expressions.BinaryBooleanExpression;
import eu.stratosphere.sopremo.expressions.BooleanExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression.BinaryOperator;
import eu.stratosphere.sopremo.expressions.ConstantExpression;
import eu.stratosphere.sopremo.expressions.ElementInSetExpression;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.ExpressionUtil;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.expressions.TransformFunction;
import eu.stratosphere.sopremo.expressions.UnaryExpression;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.CompositeOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.JsonStream;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.OutputCardinality;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.operator.SopremoModule;
import eu.stratosphere.sopremo.rewrite.ReplaceInputSelectionWithArray;

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

	private EvaluationExpression resultProjection = EvaluationExpression.VALUE;

	private final IntSet outerJoinSources = new IntOpenHashSet();

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.CompositeOperator#asModule(eu.stratosphere.sopremo.EvaluationContext)
	 */
	@Override
	public void addImplementation(final SopremoModule module) {
		switch (this.binaryConditions.size()) {
		case 0:
			final ThetaJoin cross = new ThetaJoin().withCondition(new UnaryExpression(new ConstantExpression(true))).
				withInputs(module.getInputs()).
				withResultProjection(this.getResultProjection());
			module.getOutput(0).setInput(0, cross);
			break;
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
			final List<BinaryBooleanExpression> minimalSpanningTree =
				this.findMinimalSpanningTree(this.binaryConditions);

			final List<TwoSourceJoin> joins = this.getInitialJoinOrder(module, minimalSpanningTree);

			// wrap each input in an array that contains the element at the position of the input
			// input2 -> [null, null, input2, ...]
			final int numInputs = this.getNumInputs();
			final JsonStream[] inputs = new JsonStream[numInputs];
			for (int index = 0; index < numInputs; index++)
				inputs[index] = OperatorUtil.positionEncode(module.getInput(index), index, numInputs);

			// rewire individual joins
			// the input of each join is either the module input or the result of a previous join
			for (final TwoSourceJoin twoSourceJoin : joins) {
				final List<JsonStream> operatorInputs = twoSourceJoin.getInputs();

				final JsonStream[] actualInputs = new JsonStream[2];
				final List<Source> moduleInput = module.getInputs();
				for (int index = 0; index < 2; index++) {
					final int inputIndex = moduleInput.indexOf(operatorInputs.get(index).getSource().getOperator());
					actualInputs[index] = inputs[inputIndex];

					// we keep inputs up-to-date, so that it points either to the original input or the latest join that
					// includes the source
					for (int updateIndex = 0; updateIndex < numInputs; updateIndex++)
						if (inputs[updateIndex] == actualInputs[index])
							inputs[updateIndex] = twoSourceJoin;
				}
				twoSourceJoin.setInputs(actualInputs);
				twoSourceJoin.setResultProjection(new AggregationExpression(new ArrayUnion()));
			}

			JsonStream lastOperator = inputs[0];

			// check if any cycles exist in the predicates, if so create post-selection with left predicates not in the
			// MST
			if (minimalSpanningTree.size() != this.binaryConditions.size()) {
				final List<BinaryBooleanExpression> leftExpressions = new ArrayList<BinaryBooleanExpression>();
				for (final BinaryBooleanExpression leftExpressionCandidate : this.binaryConditions)
					if (!minimalSpanningTree.contains(leftExpressionCandidate)) {
						final BinaryBooleanExpression adjustedExpression =
							(BinaryBooleanExpression) leftExpressionCandidate
								.clone();
						adjustedExpression.replace(Predicates.instanceOf(InputSelection.class),
							new TransformFunction() {
								@Override
								public EvaluationExpression apply(final EvaluationExpression argument) {
									final InputSelection inputSelection = (InputSelection) argument;
									final int originalIndex = inputSelection.getIndex();
									return ExpressionUtil.makePath(new InputSelection(0),
										new ArrayAccess(originalIndex));
								}
							});
						leftExpressions.add(adjustedExpression);
					}
				final AndExpression selectionCondition = new AndExpression(leftExpressions);
				lastOperator = new Selection()
					.withCondition(selectionCondition).withInputs(lastOperator);
			}

			final EvaluationExpression resultProjection = this.getResultProjection();
			resultProjection.replace(Predicates.instanceOf(InputSelection.class),
				new ReplaceInputSelectionWithArray());
			module.getOutput(0).setInput(0,
				new Projection().withInputs(lastOperator).withResultProjection(resultProjection));
		}
	}

	/**
	 * Finds the minimal spanning tree over a graph of Join predicates based on the Kruskal's algorithm.
	 * 
	 * @param someBinaryConditions
	 * @return the minimal spanning tree over the input graph of expressions
	 */
	private List<BinaryBooleanExpression> findMinimalSpanningTree(
			final List<BinaryBooleanExpression> someBinaryConditions) {

		final List<BinaryBooleanExpression> minimalSpanningTree = new ArrayList<BinaryBooleanExpression>();

		final BitSet remainingVertices = new BitSet();
		remainingVertices.set(0, this.getNumInputs());
		final LinkedList<Object2IntMap.Entry<BinaryBooleanExpression>> edgesWithWeight =
			this.weightEdges(someBinaryConditions);
		this.sortEdgesByWeight(edgesWithWeight);

		while (!edgesWithWeight.isEmpty()) {
			final BinaryBooleanExpression currentEdge = edgesWithWeight.removeFirst().getKey();
			if (!this.currentEdgeProducesCycleInMST(currentEdge, remainingVertices))
				minimalSpanningTree.add(currentEdge);
		}
		return minimalSpanningTree;
	}

	private void sortEdgesByWeight(final List<Object2IntMap.Entry<BinaryBooleanExpression>> edgesWithWeight) {
		Collections.sort(edgesWithWeight, new Comparator<Object2IntMap.Entry<BinaryBooleanExpression>>() {
			/*
			 * (non-Javadoc)
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(final Entry<BinaryBooleanExpression> o1, final Entry<BinaryBooleanExpression> o2) {
				return o1.getIntValue() - o2.getIntValue();
			}
		});
	}

	private boolean currentEdgeProducesCycleInMST(final BinaryBooleanExpression currentEdge,
			final BitSet remainingVertices) {
		final int vertex1 = currentEdge.getExpr1().findFirst(InputSelection.class).getIndex();
		final int vertex2 = currentEdge.getExpr2().findFirst(InputSelection.class).getIndex();

		// an edge does not produce a cycle if it points to at least one remaining vertex
		if (remainingVertices.get(vertex1)) {
			remainingVertices.clear(vertex1);
			if (remainingVertices.get(vertex2))
				remainingVertices.clear(vertex2);
			return false;
		} else if (remainingVertices.get(vertex2)) {
			remainingVertices.clear(vertex2);
			return false;
		}

		// both nodes were already used -> cycle
		return true;
	}

	private LinkedList<Object2IntMap.Entry<BinaryBooleanExpression>> weightEdges(
			final List<BinaryBooleanExpression> someBinaryConditions) {
		final LinkedList<Object2IntMap.Entry<BinaryBooleanExpression>> edgesWithWeight =
			new LinkedList<Object2IntMap.Entry<BinaryBooleanExpression>>();
		for (final BinaryBooleanExpression expression : someBinaryConditions) {
			// TODO better weighting schema required
			int weight;
			if (expression instanceof ElementInSetExpression)
				weight = 5;
			else if (((ComparativeExpression) expression).getBinaryOperator().equals(BinaryOperator.EQUAL))
				weight = 1;
			else
				weight = 10;

			edgesWithWeight.add(new AbstractObject2IntMap.BasicEntry<BinaryBooleanExpression>(expression, weight));
		}
		return edgesWithWeight;
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
		final EvaluationExpression[] expressions = new EvaluationExpression[this.outerJoinSources.size()];
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
	public void setJoinCondition(final BooleanExpression joinCondition) {
		if (joinCondition == null)
			throw new NullPointerException("joinCondition must not be null");

		final ArrayList<BinaryBooleanExpression> expressions = new ArrayList<BinaryBooleanExpression>();
		this.addBinaryExpressions(joinCondition, expressions);
		if (expressions.size() == 0)
			throw new IllegalArgumentException("No join condition given");

		this.joinCondition = joinCondition;
		this.binaryConditions = expressions;
	}

	public void setOuterJoinIndices(final int... outerJoinIndices) {
		if (outerJoinIndices == null)
			throw new NullPointerException("outerJoinIndices must not be null");

		this.outerJoinSources.clear();
		for (final int index : outerJoinIndices)
			this.outerJoinSources.add(index);
	}

	@Property
	@Name(verb = "preserve")
	public void setOuterJoinSources(final EvaluationExpression outerJoinSources) {
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
		for (final EvaluationExpression expression : expressions)
			this.outerJoinSources.add(((InputSelection) expression).getIndex());
	}

	@Property
	@Name(preposition = "into")
	public void setResultProjection(final EvaluationExpression resultProjection) {
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

	public Join withJoinCondition(final BooleanExpression joinCondition) {
		this.setJoinCondition(joinCondition);
		return this;
	}

	public Join withOuterJoinIndices(final int... outerJoinIndices) {
		this.setOuterJoinIndices(outerJoinIndices);
		return this;
	}

	public Join withOuterJoinSources(final EvaluationExpression outerJoinSources) {
		this.setOuterJoinSources(outerJoinSources);
		return this;
	}

	public Join withResultProjection(final EvaluationExpression resultProjection) {
		this.setResultProjection(resultProjection);
		return this;
	}

	private void addBinaryExpressions(final BooleanExpression joinCondition,
			final List<BinaryBooleanExpression> expressions) {
		if (joinCondition instanceof BinaryBooleanExpression)
			expressions.add((BinaryBooleanExpression) joinCondition);
		else if (joinCondition instanceof AndExpression)
			for (final BooleanExpression expression : ((AndExpression) joinCondition).getExpressions())
				this.addBinaryExpressions(expression, expressions);
		else
			throw new IllegalArgumentException("Cannot handle expression " + joinCondition);
	}

	private List<TwoSourceJoin> getInitialJoinOrder(final SopremoModule module,
			final List<BinaryBooleanExpression> minimalSpanningTree) {
		final List<TwoSourceJoin> joins = new ArrayList<TwoSourceJoin>();
		for (final BinaryBooleanExpression expression : minimalSpanningTree)
			joins.add(this.getTwoSourceJoinForExpression(expression, module));
		// TODO: add some kind of optimization?
		return joins;
	}

	/**
	 * Create a TwoSourceJoin that performs the join on the given condition.<br>
	 * Adjusts the expression to the position-encoding data schema.
	 */
	private TwoSourceJoin getTwoSourceJoinForExpression(final BinaryBooleanExpression binaryCondition,
			final SopremoModule module) {
		final IntList originalIndices = new IntArrayList();

		final BinaryBooleanExpression adjustedExpression = (BinaryBooleanExpression) binaryCondition.clone();
		// change indices and emulate old behavior with array access
		// in1.fk == in3.key -> in0[1].fk == in1[3].key
		adjustedExpression.replace(Predicates.instanceOf(InputSelection.class),
			new TransformFunction() {
				@Override
				public EvaluationExpression apply(final EvaluationExpression argument) {
					final InputSelection inputSelection = (InputSelection) argument;
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
		final IntList outerJoinIndices = new IntArrayList();
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