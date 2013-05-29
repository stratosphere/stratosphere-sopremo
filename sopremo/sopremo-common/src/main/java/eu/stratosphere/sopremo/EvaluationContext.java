package eu.stratosphere.sopremo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.stratosphere.nephele.fs.Path;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.packages.DefaultConstantRegistry;
import eu.stratosphere.sopremo.packages.DefaultFunctionRegistry;
import eu.stratosphere.sopremo.packages.EvaluationScope;
import eu.stratosphere.sopremo.packages.IConstantRegistry;
import eu.stratosphere.sopremo.packages.IFunctionRegistry;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.serialization.Schema;

/**
 * Provides additional context to the evaluation of {@link Evaluable}s, such as access to all registered functions.
 * 
 * @author Arvid Heise
 */
public class EvaluationContext extends AbstractSopremoType implements ISopremoType, EvaluationScope {
	private final IFunctionRegistry methodRegistry;

	private final IConstantRegistry constantRegistry;

	private Path workingPath = new Path(new File(".").toURI().toString());

	private String operatorDescription;

	private List<Schema> inputSchemas = new ArrayList<Schema>(), outputSchemas = new ArrayList<Schema>();

	private Schema schema;

	private transient int inputCount = 0;

	private EvaluationExpression resultProjection = EvaluationExpression.VALUE;

	// public LinkedList<Operator<?>> getOperatorStack() {
	// return this.operatorStack;
	// }

	private int taskId;

	/**
	 * Initializes EvaluationContext.
	 */
	public EvaluationContext() {
		this(0, 0, new DefaultFunctionRegistry(), new DefaultConstantRegistry());
	}

	/**
	 * Initializes EvaluationContext.
	 */
	public EvaluationContext(final int numInputs, final int numOutputs, IFunctionRegistry methodRegistry,
			IConstantRegistry constantRegistry) {
		this.methodRegistry = methodRegistry;
		this.constantRegistry = constantRegistry;
		this.setInputsAndOutputs(numInputs, numOutputs);
	}

	/**
	 * Initializes EvaluationContext.
	 */
	public EvaluationContext(EvaluationContext context) {
		this(context.inputSchemas.size(), context.outputSchemas.size(), context.methodRegistry,
			context.constantRegistry);
		this.copyPropertiesFrom(context);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.SopremoType#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append("Context @ ").append(this.operatorDescription).append("\n").
			append("Methods: ");
		this.methodRegistry.appendAsString(appendable);
		appendable.append("\nConstants: ");
		this.constantRegistry.appendAsString(appendable);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#clone()
	 */
	@Override
	public EvaluationContext clone() {
		return (EvaluationContext) super.clone();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#shallowClone()
	 */
	@Override
	public EvaluationContext shallowClone() {
		return (EvaluationContext) super.shallowClone();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#copyPropertiesFrom(eu.stratosphere.sopremo.AbstractSopremoType)
	 */
	@Override
	public void copyPropertiesFrom(ISopremoType original) {
		final EvaluationContext context = (EvaluationContext) original;
		this.inputSchemas.addAll(SopremoUtil.deepClone(context.inputSchemas));
		this.outputSchemas.addAll(SopremoUtil.deepClone(context.outputSchemas));
		this.schema = context.schema;
		this.resultProjection = context.resultProjection.clone();
		this.operatorDescription = context.operatorDescription;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.packages.RegistryScope#getConstantRegistry()
	 */
	@Override
	public IConstantRegistry getConstantRegistry() {
		return this.constantRegistry;
	}

	/**
	 * Returns the {@link FunctionRegistry} containing all registered function in the current evaluation context.
	 * 
	 * @return the FunctionRegistry
	 */
	@Override
	public IFunctionRegistry getFunctionRegistry() {
		return this.methodRegistry;
	}

	/**
	 * Returns the inputCount.
	 * 
	 * @return the inputCount
	 */
	public int getInputCount() {
		return this.inputCount;
	}

	/**
	 * Returns the inputSchemas.
	 * 
	 * @return the inputSchemas
	 */
	public Schema getInputSchema(final int index) {
		return this.inputSchemas.get(index);
	}

	/**
	 * Returns the outputSchemas.
	 * 
	 * @return the outputSchemas
	 */
	public Schema getOutputSchema(final int index) {
		return this.outputSchemas.get(index);
	}

	public EvaluationExpression getResultProjection() {
		return this.resultProjection;
	}

	/**
	 * Returns the schema.
	 * 
	 * @return the schema
	 */
	public Schema getSchema() {
		return this.schema;
	}

	public int getTaskId() {
		return this.taskId;
	}

	public void incrementInputCount() {
		this.inputCount++;
	}

	/**
	 * Sets the operatorDescription to the specified value.
	 * 
	 * @param operatorDescription
	 *        the operatorDescription to set
	 */
	public void setOperatorDescription(String operatorDescription) {
		if (operatorDescription == null)
			throw new NullPointerException("operatorDescription must not be null");

		this.operatorDescription = operatorDescription;
	}

	/**
	 * Returns the operatorDescription.
	 * 
	 * @return the operatorDescription
	 */
	public String getOperatorDescription() {
		return this.operatorDescription;
	}

	public void setInputsAndOutputs(final int numInputs, final int numOutputs) {
		this.inputSchemas.clear();
		this.outputSchemas.clear();
		for (int index = 0; index < numInputs; index++)
			this.inputSchemas.add((Schema) this.schema.clone());
		for (int index = 0; index < numOutputs; index++)
			this.outputSchemas.add((Schema) this.schema.clone());
	}

	public void setResultProjection(final EvaluationExpression resultProjection) {
		if (resultProjection == null)
			throw new NullPointerException("resultProjection must not be null");

		this.resultProjection = resultProjection;
	}

	/**
	 * @param schema
	 */
	public void setSchema(final Schema schema) {
		this.schema = schema;
	}

	public void setTaskId(final int taskId) {
		this.taskId = taskId;
	}

	/**
	 * Returns the hdfsPath.
	 * 
	 * @return the hdfsPath
	 */
	public Path getWorkingPath() {
		return this.workingPath;
	}

	/**
	 * Sets the hdfsPath to the specified value.
	 * 
	 * @param hdfsPath
	 *        the hdfsPath to set
	 */
	public void setWorkingPath(Path hdfsPath) {
		if (hdfsPath == null)
			throw new NullPointerException("hdfsPath must not be null");

		this.workingPath = hdfsPath;
	}

}
