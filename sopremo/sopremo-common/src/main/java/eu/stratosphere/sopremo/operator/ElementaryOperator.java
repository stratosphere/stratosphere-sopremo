package eu.stratosphere.sopremo.operator;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eu.stratosphere.api.common.functions.AbstractFunction;
import eu.stratosphere.api.common.functions.Function;
import eu.stratosphere.api.common.functions.GenericMapper;
import eu.stratosphere.api.common.operators.Ordering;
import eu.stratosphere.api.common.operators.base.CoGroupOperatorBase;
import eu.stratosphere.api.common.operators.base.CoGroupOperatorBase.CombinableFirst;
import eu.stratosphere.api.common.operators.base.CoGroupOperatorBase.CombinableSecond;
import eu.stratosphere.api.common.operators.base.CrossOperatorBase;
import eu.stratosphere.api.common.operators.base.JoinOperatorBase;
import eu.stratosphere.api.common.operators.base.MapOperatorBase;
import eu.stratosphere.api.common.operators.base.ReduceOperatorBase;
import eu.stratosphere.api.common.operators.base.ReduceOperatorBase.Combinable;
import eu.stratosphere.api.common.operators.util.OperatorUtil;
import eu.stratosphere.configuration.Configuration;
import eu.stratosphere.pact.common.IdentityMap;
import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.expressions.OrderingExpression;
import eu.stratosphere.sopremo.pact.SopremoCoGroupOperator;
import eu.stratosphere.sopremo.pact.SopremoReduceOperator;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;
import eu.stratosphere.util.CollectionUtil;
import eu.stratosphere.util.IdentityList;

/**
 * An ElementaryOperator is an {@link Operator} that directly translates to a
 * PACT. Such an operator has at most one output.<br>
 * By convention, the first inner class of implementing operators that inherits
 * from {@link Function} is assumed to be the implementation of this operator. The
 * following example demonstrates a minimalistic operator implementation.
 * 
 * <pre>
 * public static class TwoInputIntersection extends ElementaryOperator {
 * 	public TwoInputIntersection(JsonStream input1, JsonStream input2) {
 * 		super(input1, input2);
 * 	}
 * 
 * 	public static class Implementation extends
 * 			SopremoCoGroup&lt;PactJsonObject.Key, PactJsonObject, PactJsonObject, PactJsonObject.Key, PactJsonObject&gt; {
 * 		&#064;Override
 * 		public void coGroup(PactJsonObject.Key key,
 * 				Iterator&lt;PactJsonObject&gt; values1,
 * 				Iterator&lt;PactJsonObject&gt; values2,
 * 				Collector&lt;PactJsonObject.Key, PactJsonObject&gt; out) {
 * 			if (values1.hasNext() &amp;&amp; values2.hasNext())
 * 				out.collect(key, values1.next());
 * 		}
 * 	}
 * }
 * </pre>
 * 
 * To exert more control, several hooks are available that are called in fixed
 * order.
 * <ul>
 * <li>{@link #getFunctionClass()} allows to choose a different Function than the first inner class inheriting from
 * {@link Function}.
 * <li>{@link #getOperator(SopremoRecordLayout)} instantiates a contract matching the stub class resulting from the previous callback. This
 * callback is especially useful if a PACT stub is chosen that is not supported in Sopremo yet.
 * <li>{@link #configureOperator(eu.stratosphere.api.common.operators.Operator, Configuration)} is a callback used to set parameters of
 * the {@link Configuration} of the stub.
 * <li>{@link #asPactModule()} gives complete control over the creation of the {@link PactModule}.
 * </ul>
 */
@OutputCardinality(min = 1, max = 1)
public abstract class ElementaryOperator<Self extends ElementaryOperator<Self>>
		extends Operator<Self> {
	private final List<List<? extends EvaluationExpression>> keyExpressions =
		new ArrayList<List<? extends EvaluationExpression>>();

	private final List<List<OrderingExpression>> innerGroupOrders =
		new ArrayList<List<OrderingExpression>>();

	private EvaluationExpression resultProjection = EvaluationExpression.VALUE;

	{
		for (int index = 0; index < this.getMinInputs(); index++)
			this.keyExpressions.add(new ArrayList<EvaluationExpression>());
		for (int index = 0; index < this.getMinInputs(); index++)
			this.innerGroupOrders.add(new ArrayList<OrderingExpression>());
	}

	private boolean combinable = false, combinableFirst = false, combinableSecond = false;

	/**
	 * Initializes the ElementaryOperator with the number of outputs set to 1.
	 * The {@link InputCardinality} annotation must be set with this
	 * constructor.
	 */
	public ElementaryOperator() {
		super();
	}

	/**
	 * Initializes the ElementaryOperator with the given number of inputs.
	 * 
	 * @param inputs
	 *        the number of inputs
	 */
	public ElementaryOperator(final int inputs) {
		this(inputs, inputs);
	}

	/**
	 * Initializes the ElementaryOperator with the given number of inputs.
	 * 
	 * @param minInputs
	 *        the minimum number of inputs
	 * @param maxInputs
	 *        the maximum number of inputs
	 */
	public ElementaryOperator(final int minInputs, final int maxInputs) {
		super(minInputs, maxInputs, 1, 1);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.Operator#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		super.appendAsString(appendable);
		if (this.getResultProjection() != EvaluationExpression.VALUE) {
			appendable.append(" to ");
			this.getResultProjection().appendAsString(appendable);
		}
	}

	@Override
	public ElementarySopremoModule asElementaryOperators() {
		final ElementarySopremoModule module =
			new ElementarySopremoModule(this.getInputs().size(), this.getOutputs().size());
		module.setName(this.toString());
		final Operator<Self> clone = this.clone();
		for (int index = 0; index < this.getInputs().size(); index++)
			clone.setInput(index, module.getInput(index));
		final List<JsonStream> outputs = clone.getOutputs();
		for (int index = 0; index < outputs.size(); index++)
			module.getOutput(index).setInput(index, outputs.get(index));
		return module;
	}

	public PactModule asPactModule() {
		SopremoRecordLayout layout = SopremoEnvironment.getInstance().getLayout();
		final eu.stratosphere.api.common.operators.Operator contract = this.getOperator(layout);
		SopremoEnvironment.getInstance().getEvaluationContext().setResultProjection(this.resultProjection);
		this.configureOperator(contract, contract.getParameters());

		final List<List<eu.stratosphere.api.common.operators.Operator>> inputLists = OperatorUtil
			.getInputs(contract);
		final List<eu.stratosphere.api.common.operators.Operator> distinctInputs =
			new IdentityList<eu.stratosphere.api.common.operators.Operator>();
		for (final List<eu.stratosphere.api.common.operators.Operator> inputs : inputLists) {
			// assume at least one input for each contract input slot
			if (inputs.isEmpty())
				inputs.add(new MapOperatorBase<GenericMapper<SopremoRecord, SopremoRecord>>(IdentityMap.class, "nop"));
			for (final eu.stratosphere.api.common.operators.Operator input : inputs)
				if (!distinctInputs.contains(input))
					distinctInputs.add(input);
		}
		final PactModule module = new PactModule(distinctInputs.size(), 1);
		for (final List<eu.stratosphere.api.common.operators.Operator> inputs : inputLists)
			for (int index = 0; index < inputs.size(); index++)
				inputs.set(index, module.getInput(distinctInputs.indexOf(inputs.get(index))));
		OperatorUtil.setInputs(contract, inputLists);

		module.getOutput(0).addInput(contract);
		return module;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final ElementaryOperator<?> other = (ElementaryOperator<?>) obj;
		return this.keyExpressions.equals(other.keyExpressions) &&
			this.innerGroupOrders.equals(other.innerGroupOrders) &&
			this.resultProjection.equals(other.resultProjection);
	}

	public Set<EvaluationExpression> getAllKeyExpressions() {
		final Set<EvaluationExpression> allKeys = new HashSet<EvaluationExpression>();
		final List<JsonStream> inputs = this.getInputs();
		for (int index = 0; index < inputs.size(); index++) {
			allKeys.addAll(this.getKeyExpressions(index));
			for (final OrderingExpression orderingExpression : this.getInnerGroupOrder(index))
				allKeys.add(orderingExpression.getPath());
		}
		return allKeys;
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

	/**
	 * Returns the key expressions of the given input.
	 * 
	 * @param inputIndex
	 *        the index of the input
	 * @return the key expressions of the given input
	 */
	@SuppressWarnings("unchecked")
	public List<? extends EvaluationExpression> getKeyExpressions(final int inputIndex) {
		if (inputIndex >= this.keyExpressions.size())
			return Collections.EMPTY_LIST;
		final List<? extends EvaluationExpression> expressions = this.keyExpressions.get(inputIndex);
		if (expressions == null)
			return Collections.EMPTY_LIST;
		return expressions;
	}

	public EvaluationExpression getResultProjection() {
		return this.resultProjection;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.keyExpressions.hashCode();
		result = prime * result + this.innerGroupOrders.hashCode();
		result = prime * result + this.resultProjection.hashCode();
		return result;
	}

	/**
	 * Returns true, if the implementing stub is combinable. This method will only be invoked for Reduce stubs.
	 * 
	 * @see Combinable
	 */
	public boolean isCombinable() {
		return this.combinable;
	}

	/**
	 * Returns true, if the implementing stub is combinable for left input. This method will only be invoked for CoGroup
	 * stubs.
	 * 
	 * @see CombinableFirst
	 */
	public boolean isCombinableFirst() {
		return this.combinableFirst;
	}

	/**
	 * Returns true, if the implementing stub is combinable for right input. This method will only be invoked for
	 * CoGroup stubs.
	 * 
	 * @see CombinableSecond
	 */
	public boolean isCombinableSecond() {
		return this.combinableSecond;
	}

	/**
	 * Sets the combinable to the specified value. This method has only effects for Reduce stubs.
	 * 
	 * @param combinable
	 *        the combinable to set
	 * @see Combinable
	 */
	public void setCombinable(final boolean combinable) {
		this.combinable = combinable;
	}

	/**
	 * Sets the combinableFirst to the specified value. This method has only effects for CoGroup stubs.
	 * 
	 * @param combinableFirst
	 *        the combinableFirst to set
	 * @see CombinableFirst
	 */
	public void setCombinableFirst(final boolean combinableFirst) {
		this.combinableFirst = combinableFirst;
	}

	/**
	 * Sets the combinableSecond to the specified value. This method has only effects for CoGroup stubs.
	 * 
	 * @param combinableSecond
	 *        the combinableSecond to set
	 * @see CombinableSecond
	 */
	public void setCombinableSecond(final boolean combinableSecond) {
		this.combinableSecond = combinableSecond;
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

	/**
	 * Sets the keyExpressions of the given input to the specified value.
	 * 
	 * @param keyExpressions
	 *        the keyExpressions to set
	 */
	public void setKeyExpressions(final int index, final EvaluationExpression... keyExpressions) {
		if (keyExpressions.length == 0)
			throw new IllegalArgumentException(
				"keyExpressions must not be null");

		this.setKeyExpressions(index, Arrays.asList(keyExpressions));
	}

	/**
	 * Sets the keyExpressions to the specified value.
	 * 
	 * @param keyExpressions
	 *        the keyExpressions to set
	 * @param inputIndex
	 *        the index of the input
	 */
	// @Property(hidden = true)
	public void setKeyExpressions(final int inputIndex,
			final List<? extends EvaluationExpression> keyExpressions) {
		if (keyExpressions == null)
			throw new NullPointerException("keyExpressions must not be null");
		CollectionUtil.ensureSize(this.keyExpressions, inputIndex + 1);
		this.keyExpressions.set(inputIndex, new ArrayList<EvaluationExpression>(keyExpressions));
	}

	@Property
	@Name(preposition = "into")
	public void setResultProjection(final EvaluationExpression resultProjection) {
		if (resultProjection == null)
			throw new NullPointerException("resultProjection must not be null");

		if (this.getMaxInputs() == 1)
			this.resultProjection = resultProjection.clone().remove(new InputSelection(0));
		else
			this.resultProjection = resultProjection;
	}

	/**
	 * Sets the combinable to the specified value. This method has only effects for Reduce stubs.
	 * 
	 * @param combinable
	 *        the combinable to set
	 * @see Combinable
	 */
	public Self withCombinable(final boolean combinable) {
		this.setCombinable(combinable);
		return this.self();
	}

	/**
	 * Sets the combinableFirst to the specified value. This method has only effects for CoGroup stubs.
	 * 
	 * @param combinableFirst
	 *        the combinableFirst to set
	 * @see CombinableFirst
	 */
	public Self withCombinableFirst(final boolean combinableFirst) {
		this.combinableFirst = combinableFirst;
		return this.self();
	}

	/**
	 * Sets the combinableSecond to the specified value. This method has only effects for CoGroup stubs.
	 * 
	 * @param combinableSecond
	 *        the combinableSecond to set
	 * @see CombinableSecond
	 */
	public void withCombinableSecond(final boolean combinableSecond) {
		this.combinableSecond = combinableSecond;
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
	public Self withInnerGroupOrdering(final int index, final List<OrderingExpression> innerGroupOrder) {
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
	public Self withInnerGroupOrdering(final int index, final OrderingExpression... innerGroupOrder) {
		this.setInnerGroupOrder(index, innerGroupOrder);
		return this.self();
	}

	/**
	 * Sets the keyExpressions of the given input to the specified value.
	 * 
	 * @param keyExpressions
	 *        the keyExpressions to set
	 * @param index
	 *        the index of the input
	 * @return this
	 */
	public Self withKeyExpression(final int index, final EvaluationExpression... keyExpressions) {
		this.setKeyExpressions(index, keyExpressions);
		return this.self();
	}

	/**
	 * Sets the keyExpressions of the given input to the specified value.
	 * 
	 * @param keyExpressions
	 *        the keyExpressions to set
	 * @param index
	 *        the index of the input
	 * @return this
	 */
	public Self withKeyExpressions(final int index, final List<? extends EvaluationExpression> keyExpressions) {
		this.setKeyExpressions(index, keyExpressions);
		return this.self();
	}

	public Self withResultProjection(final EvaluationExpression resultProjection) {
		this.setResultProjection(resultProjection);
		return this.self();
	}

	/**
	 * Callback to add parameters to the stub configuration.<br>
	 * The default implementation adds the context and all non-transient,
	 * non-final, non-static fields.
	 * 
	 * @param contract
	 *        the contract to configure
	 * @param stubConfiguration
	 *        the configuration of the stub
	 */
	@SuppressWarnings("unchecked")
	protected void configureOperator(final eu.stratosphere.api.common.operators.Operator contract,
			final Configuration stubConfiguration) {

		SopremoUtil.transferFieldsToConfiguration(this, ElementaryOperator.class, stubConfiguration,
			(Class<? extends AbstractFunction>) contract.getUserCodeWrapper().getUserCodeClass(),
			AbstractFunction.class);

		contract.setDegreeOfParallelism(this.getDegreeOfParallelism());
		SopremoEnvironment.getInstance().save(contract.getParameters());
	}

	protected Ordering createOrdering(final SopremoRecordLayout layout, final List<OrderingExpression> innerGroupOrder) {
		final List<EvaluationExpression> paths = new ArrayList<EvaluationExpression>();
		for (final OrderingExpression orderingExpression : innerGroupOrder)
			paths.add(orderingExpression.getPath());
		final int[] keyIndices = this.getKeyIndices(layout, paths);
		final Ordering ordering = new Ordering();
		for (int index = 0; index < keyIndices.length; index++)
			ordering.appendOrdering(keyIndices[index], null, innerGroupOrder.get(index).getOrder());
		return ordering;
	}

	/**
	 * Creates a module that delegates all input directly to the output.
	 * 
	 * @return a short circuit module
	 */
	protected PactModule createShortCircuitModule() {
		final PactModule module = new PactModule(1, 1);
		module.getOutput(0).setInput(module.getInput(0));
		return module;
	}

	/**
	 * Returns the stub class that represents the functionality of this
	 * operator.<br>
	 * This method returns the first static inner class found with {@link Class#getDeclaredClasses()} that is extended
	 * from {@link Function} by
	 * default.
	 * 
	 * @return the stub class
	 */
	@SuppressWarnings("unchecked")
	protected Class<? extends Function> getFunctionClass() {
		for (final Class<?> stubClass : this.getClass().getDeclaredClasses())
			if ((stubClass.getModifiers() & Modifier.STATIC) != 0
				&& Function.class.isAssignableFrom(stubClass))
				return (Class<? extends Function>) stubClass;
		return null;
	}

	protected int[] getKeyIndices(final SopremoRecordLayout sopremoRecordLayout,
			final Iterable<? extends EvaluationExpression> keyExpressions) {
		final IntSet keyIndices = new IntOpenHashSet();
		for (final EvaluationExpression expression : keyExpressions)
			keyIndices.addAll(sopremoRecordLayout.indicesOf(expression));
		if (keyIndices.isEmpty()) {
			if (keyExpressions.iterator().hasNext())
				throw new IllegalStateException(
					String.format("Operator %s did not specify key expression that it now requires",
						this.getClass()));

			throw new IllegalStateException(String.format("Needs to specify key expressions: %s", this.getClass()));
		}
		return keyIndices.toIntArray();
	}

	// protected abstract Schema getKeyFields();

	/**
	 * Creates the {@link Operator} that represents this operator.
	 * 
	 * @return the contract representing this operator
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected eu.stratosphere.api.common.operators.Operator getOperator(final SopremoRecordLayout layout) {
		final Class<? extends Function> stubClass = this.getFunctionClass();
		if (stubClass == null)
			throw new IllegalStateException("no implementing stub found");
		final Class<? extends eu.stratosphere.api.common.operators.Operator> contractClass =
			OperatorUtil.getContractClass(stubClass);
		if (contractClass == null)
			throw new IllegalStateException("no associated contract found");

		final String name = this.toString();
		try {
			if (contractClass == ReduceOperatorBase.class) {
				final int[] keyIndices = this.getKeyIndices(layout, this.getKeyExpressions(0));
				final SopremoReduceOperator contract = new SopremoReduceOperator(this, stubClass, keyIndices, name);
				if (!this.getInnerGroupOrder(0).isEmpty())
					contract.setInnerGroupOrder(this.createOrdering(layout, this.getInnerGroupOrder(0)));
				return contract;
			} else if (contractClass == CoGroupOperatorBase.class) {
				final int[] keyIndices1 = this.getKeyIndices(layout, this.getKeyExpressions(0));
				final int[] keyIndices2 = this.getKeyIndices(layout, this.getKeyExpressions(1));
				final SopremoCoGroupOperator contract =
					new SopremoCoGroupOperator(this, stubClass, keyIndices1, keyIndices2, name);
				if (!this.getInnerGroupOrder(0).isEmpty())
					contract.setFirstInnerGroupOrdering(this.createOrdering(layout, this.getInnerGroupOrder(0)));
				if (!this.getInnerGroupOrder(1).isEmpty())
					contract.setSecondInnerGroupOrdering(this.createOrdering(layout, this.getInnerGroupOrder(1)));
				return contract;
			} else if (contractClass == JoinOperatorBase.class) {
				final int[] keyIndices1 = this.getKeyIndices(layout, this.getKeyExpressions(0));
				final int[] keyIndices2 = this.getKeyIndices(layout, this.getKeyExpressions(1));

				return new JoinOperatorBase(stubClass, keyIndices1, keyIndices2, name);
			} else if (contractClass == MapOperatorBase.class)
				return new MapOperatorBase(stubClass, name);
			else if (contractClass == CrossOperatorBase.class)
				return new CrossOperatorBase(stubClass, name);
			else
				throw new UnsupportedOperationException("Unknown contract type");

		} catch (final Exception e) {
			throw new IllegalStateException("Cannot create contract from stub "
				+ stubClass, e);
		}
	}
}
