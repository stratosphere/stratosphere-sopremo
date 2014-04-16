package eu.stratosphere.sopremo.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

import eu.stratosphere.sopremo.CoreFunctions;
import eu.stratosphere.sopremo.aggregation.AssociativeAggregation;
import eu.stratosphere.sopremo.expressions.*;
import eu.stratosphere.sopremo.operator.CompositeOperator;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.JsonStream;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.OutputCardinality;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.operator.SopremoModule;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoCoGroup;
import eu.stratosphere.sopremo.pact.SopremoReduce;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;
import eu.stratosphere.sopremo.type.NullNode;
import eu.stratosphere.util.CollectionUtil;

@InputCardinality(min = 1, max = 2)
@OutputCardinality(1)
@Name(verb = "group")
public class Grouping extends CompositeOperator<Grouping> {
	private final static EvaluationExpression GROUP_ALL = new ConstantExpression(NullNode.getInstance());

	private EvaluationExpression defaultGroupingKey = GROUP_ALL;

	private final List<List<OrderingExpression>> innerGroupOrders =
		new ArrayList<List<OrderingExpression>>();

	private final List<EvaluationExpression> keyExpressions = new ArrayList<EvaluationExpression>(1);

	private EvaluationExpression resultProjection = EvaluationExpression.VALUE;

	{
		for (int index = 0; index < this.getMinInputs(); index++)
			this.innerGroupOrders.add(new ArrayList<OrderingExpression>());
	}

	@Override
	public void addImplementation(final SopremoModule module) {
		JsonStream output;
		switch (this.getNumInputs()) {
		case 0:
			throw new IllegalStateException("No input given for grouping");
		case 1:
			output = this.createGrouping(module);
			break;
		case 2:
			output =
				new CoGroupProjection().withResultProjection(this.resultProjection).
					withKeyExpression(0, this.getGroupingKey(0).clone().remove(new InputSelection(0))).
					withKeyExpression(1, this.getGroupingKey(1).clone().remove(new InputSelection(1))).
					withInnerGroupOrdering(0, this.innerGroupOrders.get(0)).
					withInnerGroupOrdering(1, this.innerGroupOrders.size() > 1 ? this.innerGroupOrders.get(1)
						: Collections.<OrderingExpression> emptyList()).
					withInputs(module.getInputs());
			break;
		default:
			throw new IllegalStateException("More than two sources are not supported");
			// List<JsonStream> inputs = new ArrayList<JsonStream>();
			// List<EvaluationExpression> keyExpressions = new ArrayList<EvaluationExpression>();
			// for (int index = 0; index < numInputs; index++) {
			// inputs.add(ContractUtil.positionEncode(module.getInput(index), index, numInputs));
			// keyExpressions.add(new PathExpression(new InputSelection(index), getGroupingKey(index)));
			// }
			// final UnionAll union = new UnionAll().
			// withInputs(inputs);
			// final PathExpression projection =
			// new PathExpression(new AggregationExpression(new ArrayUnion()), this.resultProjection);
			// output = new GroupProjection(projection).
			// withInputs(union);
			// break;
		}

		module.getOutput(0).setInput(0, output);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.Operator#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		super.appendAsString(appendable);
		appendable.append(" on ");
		for (int input = 0; input < this.getNumInputs(); input++) {
			if (input > 1)
				appendable.append(", ");
			this.getGroupingKey(input).appendAsString(appendable);
		}
		appendable.append(" to ");
		this.resultProjection.appendAsString(appendable);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		final Grouping other = (Grouping) obj;
		return this.resultProjection.equals(other.resultProjection);
	}

	public EvaluationExpression getDefaultGroupingKey() {
		return this.defaultGroupingKey;
	}

	public EvaluationExpression getGroupingKey(final int index) {
		final EvaluationExpression keyExpression =
			index < this.keyExpressions.size() ? this.keyExpressions.get(index) : null;
		if (keyExpression == null)
			return this.getDefaultGroupingKey();
		return keyExpression;
	}

	public EvaluationExpression getGroupingKey(final JsonStream input) {
		return this.getGroupingKey(this.getSafeInputIndex(input));
	}

	/**
	 * Returns the innerGroupOrder expressions of the given input.
	 * 
	 * @param inputIndex
	 *        the index of the input
	 * @return the secondarySortKey expressions of the given input
	 */
	@SuppressWarnings("unchecked")
	public List<OrderingExpression> getInnerGroupOrder(final int inputIndex) {
		if (inputIndex >= this.innerGroupOrders.size())
			return Collections.EMPTY_LIST;
		final List<OrderingExpression> innerGroupOrder = this.innerGroupOrders.get(inputIndex);
		if (innerGroupOrder == null)
			return Collections.EMPTY_LIST;
		return innerGroupOrder;
	}

	public EvaluationExpression getResultProjection() {
		return this.resultProjection;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.resultProjection.hashCode();
		return result;
	}

	@Property(hidden = true)
	public void setDefaultGroupingKey(final EvaluationExpression defaultGroupingKey) {
		if (defaultGroupingKey == null)
			throw new NullPointerException("defaultGroupingKey must not be null");

		this.defaultGroupingKey = defaultGroupingKey;
	}

	@Property(preferred = true, input = true)
	@Name(preposition = "by")
	public void setGroupingKey(final int inputIndex, final EvaluationExpression keyExpression) {
		CollectionUtil.ensureSize(this.keyExpressions, inputIndex + 1);
		this.keyExpressions.set(inputIndex, keyExpression);
	}

	public void setGroupingKey(final JsonStream input, final EvaluationExpression keyExpression) {
		if (keyExpression == null)
			throw new NullPointerException("keyExpression must not be null");

		this.setGroupingKey(this.getSafeInputIndex(input), keyExpression);
	}

	/**
	 * Sets the innerGroupOrder to the specified value.
	 * 
	 * @param innerGroupOrder
	 *        the innerGroupOrder to set
	 * @param inputIndex
	 *        the index of the input
	 */
	// @Property(hidden = true)
	public void setInnerGroupOrder(final int inputIndex, final List<OrderingExpression> innerGroupOrder) {
		if (innerGroupOrder == null)
			throw new NullPointerException("innerGroupOrders must not be null");
		CollectionUtil.ensureSize(this.innerGroupOrders, inputIndex + 1);
		this.innerGroupOrders.set(inputIndex, new ArrayList<OrderingExpression>(innerGroupOrder));
	}

	/**
	 * Sets the innerGroupOrder to the specified value.
	 * 
	 * @param innerGroupOrder
	 *        the innerGroupOrder to set
	 * @param inputIndex
	 *        the index of the input
	 */
	// @Property(hidden = true)
	public void setInnerGroupOrder(final int inputIndex, final OrderingExpression... innerGroupOrder) {
		if (innerGroupOrder == null)
			throw new NullPointerException("innerGroupOrders must not be null");
		this.setInnerGroupOrder(inputIndex, Arrays.asList(innerGroupOrder));
	}

	@Property(preferred = true)
	@Name(preposition = "into")
	public void setResultProjection(final EvaluationExpression resultProjection) {
		if (resultProjection == null)
			throw new NullPointerException("resultProjection must not be null");

		this.resultProjection =
			ExpressionUtil.replaceAggregationWithBatchAggregation(
				ExpressionUtil.replaceIndexAccessWithAggregation(resultProjection));
	}

	public Grouping withGroupingKey(final EvaluationExpression groupingKey) {
		this.setDefaultGroupingKey(groupingKey);
		return this;
	}

	public Grouping withGroupingKey(final int inputIndex, final EvaluationExpression groupingKey) {
		this.setGroupingKey(inputIndex, groupingKey);
		return this;
	}

	/**
	 * Sets the innerGroupOrder of the given input to the specified value.
	 * 
	 * @param innerGroupOrder
	 *        the innerGroupOrder to set
	 * @param index
	 *        the index of the input
	 * @return this
	 */
	public Grouping withInnerGroupOrdering(final int index, final List<OrderingExpression> innerGroupOrder) {
		this.setInnerGroupOrder(index, innerGroupOrder);
		return this.self();
	}

	/**
	 * Sets the innerGroupOrder to the specified value.
	 * 
	 * @param innerGroupOrder
	 *        the innerGroupOrder to set
	 * @param index
	 *        the index of the input
	 */
	public Grouping withInnerGroupOrdering(final int index, final OrderingExpression... innerGroupOrder) {
		this.setInnerGroupOrder(index, innerGroupOrder);
		return this.self();
	}

	public Grouping withResultProjection(final EvaluationExpression resultProjection) {
		this.setResultProjection(resultProjection);
		return this;
	}

	private JsonStream createGrouping(final SopremoModule module) {
		final EvaluationExpression resultProjection = this.resultProjection.clone().remove(new InputSelection(0));
		final List<AggregationExpression> aggregations = resultProjection.findAll(AggregationExpression.class);
		for (final AggregationExpression aggregationExpression : aggregations)
			// not combinable, if there is a non-associative expression
			if (!(aggregationExpression.getAggregation() instanceof AssociativeAggregation))
				return new GroupProjection().withResultProjection(resultProjection).
					withKeyExpression(0, this.getGroupingKey(0).clone().remove(new InputSelection(0))).
					withInputs(module.getInputs());

		// first project all tuples to an array with a slot for each aggregation
		// fill the array with the input expressions of the respective aggregation
		final ArrayCreation aggregatedValues = new ArrayCreation();
		aggregatedValues.add(this.getGroupingKey(0).clone().remove(new InputSelection(0)));
		for (final AggregationExpression aggregationExpression : aggregations)
			aggregatedValues.add(aggregationExpression.getInputExpression());
		final Projection initialValues = new Projection().withResultProjection(aggregatedValues).
			withInputs(module.getInputs());

		// now we can create a combinable aggregation, that associatively aggregates the elements in the array
		final ArrayCreation combinableAggregation = new ArrayCreation();
		final BatchAggregationExpression bae = new BatchAggregationExpression();
		combinableAggregation.add(bae.add(CoreFunctions.FIRST, new ArrayAccess(0)));
		for (int index = 0, size = aggregations.size(); index < size; index++)
			combinableAggregation.add(bae.add(aggregations.get(index).getAggregation(), new ArrayAccess(index + 1)));
		final JsonStream combinableGrouping = new GroupProjection().
			withCombinable(true).
			withResultProjection(combinableAggregation).
			withKeyExpression(0, new ArrayAccess(0)).
			withInnerGroupOrdering(0, this.innerGroupOrders.get(0)).
			withInputs(initialValues);

		// and finally, we need to perform the actual project from the array to the desired output
		// replace the aggregation expression with the corresponding array access
		final EvaluationExpression finalProjection =
			resultProjection.replace(Predicates.instanceOf(AggregationExpression.class),
				new Function<EvaluationExpression, EvaluationExpression>() {
					int aggregationIndex = 1;

					@Override
					public EvaluationExpression apply(final EvaluationExpression expression) {
						return new ArrayAccess(this.aggregationIndex++);
					}
				});
		return new Projection().withResultProjection(finalProjection).
			withInputs(combinableGrouping);
	}

	@InputCardinality(min = 2, max = 2)
	public static class CoGroupProjection extends ElementaryOperator<CoGroupProjection> {
		public static class Implementation extends SopremoCoGroup {
			private final IArrayNode<IStreamNode<IJsonNode>> streams = new ArrayNode<IStreamNode<IJsonNode>>(2);

			@Override
			protected void coGroup(final IStreamNode<IJsonNode> values1, final IStreamNode<IJsonNode> values2,
					final JsonCollector<IJsonNode> out) {
				this.streams.set(0, values1);
				this.streams.set(1, values2);
				out.collect(this.streams);
			}
		}
	}

	@InputCardinality(1)
	public static class GroupProjection extends ElementaryOperator<GroupProjection> {
		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.operator.ElementaryOperator#getFunctionClass()
		 */
		public static class Implementation extends SopremoReduce {
			@Override
			protected void reduce(final IStreamNode<IJsonNode> values, final JsonCollector<IJsonNode> out) {
				out.collect(values);
			}
		}
	}
}
