package eu.stratosphere.sopremo.operator;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.common.IdentityMap;
import eu.stratosphere.pact.common.contract.MapContract;
import eu.stratosphere.pact.common.plan.ContractUtil;
import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.pact.common.stubs.Stub;
import eu.stratosphere.pact.generic.contract.Contract;
import eu.stratosphere.pact.generic.contract.GenericCoGroupContract;
import eu.stratosphere.pact.generic.contract.GenericCrossContract;
import eu.stratosphere.pact.generic.contract.GenericMapContract;
import eu.stratosphere.pact.generic.contract.GenericMatchContract;
import eu.stratosphere.pact.generic.contract.GenericReduceContract;
import eu.stratosphere.sopremo.DegreeOfParallelism;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.expressions.UnevaluableExpression;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;
import eu.stratosphere.util.CollectionUtil;
import eu.stratosphere.util.IdentityList;
import eu.stratosphere.util.reflect.ReflectUtil;

/**
 * An ElementaryOperator is an {@link Operator} that directly translates to a
 * PACT. Such an operator has at most one output.<br>
 * By convention, the first inner class of implementing operators that inherits
 * from {@link Stub} is assumed to be the implementation of this operator. The
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
 * <li>{@link #getStubClass()} allows to choose a different Stub than the first inner class inheriting from {@link Stub}.
 * <li>{@link #getContract()} instantiates a contract matching the stub class resulting from the previous callback. This
 * callback is especially useful if a PACT stub is chosen that is not supported in Sopremo yet.
 * <li>{@link #configureContract(Contract, Configuration, EvaluationContext)} is a callback used to set parameters of
 * the {@link Configuration} of the stub.
 * <li>{@link #asPactModule(EvaluationContext)} gives complete control over the creation of the {@link PactModule}.
 * </ul>
 * 
 * @author Arvid Heise
 */
@OutputCardinality(min = 1, max = 1)
public abstract class ElementaryOperator<Self extends ElementaryOperator<Self>>
		extends Operator<Self> {
	private static final org.apache.commons.logging.Log LOG = LogFactory.getLog(ElementaryOperator.class);

	private final List<List<? extends EvaluationExpression>> keyExpressions =
		new ArrayList<List<? extends EvaluationExpression>>();

	private EvaluationExpression resultProjection = EvaluationExpression.VALUE;

	public EvaluationExpression getResultProjection() {
		return this.resultProjection;
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

	public Self withResultProjection(final EvaluationExpression resultProjection) {
		this.setResultProjection(resultProjection);
		return this.self();
	}

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
	 * @param minInputs
	 *        the minimum number of inputs
	 * @param maxInputs
	 *        the maximum number of inputs
	 */
	public ElementaryOperator(final int minInputs, final int maxInputs) {
		super(minInputs, maxInputs, 1, 1);
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

	{
		for (int index = 0; index < this.getMinInputs(); index++)
			this.keyExpressions.add(new ArrayList<EvaluationExpression>());
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
	 * Sets the keyExpressions of the given input to the specified value.
	 * 
	 * @param keyExpressions
	 *        the keyExpressions to set
	 * @param inputIndex
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
	 * @param inputIndex
	 *        the index of the input
	 * @return this
	 */
	public Self withKeyExpressions(final int index, final List<? extends EvaluationExpression> keyExpressions) {
		this.setKeyExpressions(index, keyExpressions);
		return this.self();
	}

	@Override
	public PactModule asPactModule(final EvaluationContext context, SopremoRecordLayout layout) {
		final Contract contract = this.getContract(layout);
		context.setResultProjection(this.resultProjection);
		this.configureContract(contract, contract.getParameters(), context, layout);

		final List<List<Contract>> inputLists = ContractUtil
			.getInputs(contract);
		final List<Contract> distinctInputs = new IdentityList<Contract>();
		for (final List<Contract> inputs : inputLists) {
			// assume at least one input for each contract input slot
			if (inputs.isEmpty())
				inputs.add(MapContract.builder(IdentityMap.class).build());
			for (final Contract input : inputs)
				if (!distinctInputs.contains(input))
					distinctInputs.add(input);
		}
		final PactModule module = new PactModule(distinctInputs.size(), 1);
		for (final List<Contract> inputs : inputLists)
			for (int index = 0; index < inputs.size(); index++)
				inputs.set(index, module.getInput(distinctInputs.indexOf(inputs.get(index))));
		ContractUtil.setInputs(contract, inputLists);

		module.getOutput(0).addInput(contract);
		return module;
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
	 * Callback to add parameters to the stub configuration.<br>
	 * The default implementation adds the context and all non-transient,
	 * non-final, non-static fields.
	 * 
	 * @param contract
	 *        the contract to configure
	 * @param stubConfiguration
	 *        the configuration of the stub
	 * @param context
	 *        the context in which the {@link PactModule} is created and
	 *        evaluated
	 */
	protected void configureContract(final Contract contract, final Configuration stubConfiguration,
			final EvaluationContext context, SopremoRecordLayout layout) {
		for (final Field stubField : contract.getUserCodeClass()
			.getDeclaredFields())
			if ((stubField.getModifiers() & (Modifier.TRANSIENT | Modifier.FINAL | Modifier.STATIC)) == 0) {
				Class<?> clazz = this.getClass();
				do {
					Field thisField;
					try {
						thisField = clazz.getDeclaredField(stubField.getName());
						thisField.setAccessible(true);
						final Object value = thisField.get(this);
						if (SopremoUtil.DEBUG && value instanceof EvaluationExpression &&
							((EvaluationExpression) value).findFirst(UnevaluableExpression.class) != null)
							throw new IllegalStateException(String.format(
								"Cannot serialize field %s with unevaluable expressions %s",
								thisField.getName(), value));
						SopremoUtil.setObject(stubConfiguration, stubField.getName(), value);
					} catch (final NoSuchFieldException e) {
						// ignore field of stub if the field does not exist in
						// this operator
					} catch (final Exception e) {
						LOG.error(String.format(
							"Could not serialize field %s of class %s: %s",
							stubField.getName(), this.getClass().getSimpleName(), e));
						throw new RuntimeException(String.format(
							"Could not serialize field %s of class %s: %s",
							stubField.getName(), this.getClass().getSimpleName(), e));
					}
				} while ((clazz = clazz.getSuperclass()) != ElementaryOperator.class);
			}

		final DegreeOfParallelism degreeOfParallelism = ReflectUtil
			.getAnnotation(this.getClass(), DegreeOfParallelism.class);
		if (degreeOfParallelism != null)
			contract.setDegreeOfParallelism(degreeOfParallelism.value());
		else
			contract.setDegreeOfParallelism(this.getDegreeOfParallelism());
		SopremoUtil.setEvaluationContext(stubConfiguration, context);
		SopremoUtil.setLayout(contract.getParameters(), layout);
	}

	@Override
	public ElementarySopremoModule asElementaryOperators(final EvaluationContext context) {
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

	/**
	 * Creates the {@link Contract} that represents this operator.
	 * 
	 * @return the contract representing this operator
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Contract getContract(final SopremoRecordLayout layout) {
		final Class<? extends Stub> stubClass = this.getStubClass();
		if (stubClass == null)
			throw new IllegalStateException("no implementing stub found");
		final Class<? extends Contract> contractClass = ContractUtil.getContractClass(stubClass);
		if (contractClass == null)
			throw new IllegalStateException("no associated contract found");

		final String name = this.toString();
		try {
			if (contractClass == GenericReduceContract.class) {
				int[] keyIndices = this.getKeyIndices(layout, this.getKeyExpressions(0));
				return new GenericReduceContract(stubClass, keyIndices, name);
			}
			else if (contractClass == GenericCoGroupContract.class) {
				int[] keyIndices1 = this.getKeyIndices(layout, this.getKeyExpressions(0));
				int[] keyIndices2 = this.getKeyIndices(layout, this.getKeyExpressions(1));
				return new GenericCoGroupContract(stubClass, keyIndices1, keyIndices2, name);
			}
			else if (contractClass == GenericMatchContract.class) {
				int[] keyIndices1 = this.getKeyIndices(layout, this.getKeyExpressions(0));
				int[] keyIndices2 = this.getKeyIndices(layout, this.getKeyExpressions(1));

				return new GenericMatchContract(stubClass, keyIndices1, keyIndices2, name);
			} else if (contractClass == GenericMapContract.class)
				return new GenericMapContract(stubClass, name);
			else if (contractClass == GenericCrossContract.class)
				return new GenericCrossContract(stubClass, name);
			else
				throw new UnsupportedOperationException("Unknown contract type");

		} catch (final Exception e) {
			throw new IllegalStateException("Cannot create contract from stub "
				+ stubClass, e);
		}
	}

	public List<List<? extends EvaluationExpression>> getAllKeyExpressions() {
		final ArrayList<List<? extends EvaluationExpression>> allKeys =
			new ArrayList<List<? extends EvaluationExpression>>();
		final List<JsonStream> inputs = this.getInputs();
		for (int index = 0; index < inputs.size(); index++)
			allKeys.add(this.getKeyExpressions(index));
		return allKeys;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.keyExpressions.hashCode();
		result = prime * result + this.resultProjection.hashCode();
		return result;
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
		return this.keyExpressions.equals(other.keyExpressions) && this.resultProjection.equals(other.resultProjection);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.Operator#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		super.appendAsString(appendable);
		if (this.getResultProjection() != EvaluationExpression.VALUE) {
			appendable.append(" to ");
			this.getResultProjection().appendAsString(appendable);
		}
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
	 * Returns the stub class that represents the functionality of this
	 * operator.<br>
	 * This method returns the first static inner class found with {@link Class#getDeclaredClasses()} that is extended
	 * from {@link Stub} by
	 * default.
	 * 
	 * @return the stub class
	 */
	@SuppressWarnings("unchecked")
	protected Class<? extends Stub> getStubClass() {
		for (final Class<?> stubClass : this.getClass().getDeclaredClasses())
			if ((stubClass.getModifiers() & Modifier.STATIC) != 0
				&& Stub.class.isAssignableFrom(stubClass))
				return (Class<? extends Stub>) stubClass;
		return null;
	}
}
