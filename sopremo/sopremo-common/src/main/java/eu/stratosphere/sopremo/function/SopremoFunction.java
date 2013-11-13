package eu.stratosphere.sopremo.function;

import java.io.IOException;

import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.packages.BuiltinUtil;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * A base for built-in and user-defined functions.
 * 
 * @author Arvid Heise
 */
public abstract class SopremoFunction extends Callable<IJsonNode, IArrayNode<IJsonNode>> {

	public SopremoFunction(int numberOfParameters) {
		super(numberOfParameters, numberOfParameters);
	}

	public SopremoFunction(int minimumNumberOfParameters, int maximumNumberOfParameters) {
		super(minimumNumberOfParameters, maximumNumberOfParameters);
	}

	/**
	 * Initializes SopremoFunction.
	 */
	SopremoFunction() {
		this(0, 0);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		appendable.append(BuiltinUtil.getName(this, 
			SopremoEnvironment.getInstance().getEvaluationContext().getFunctionRegistry()));
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#clone()
	 */
	@Override
	public SopremoFunction clone() {
		return (SopremoFunction) super.clone();
	}

	public SopremoFunction withDefaultParameters(IJsonNode... defaultValues) {
		final int fixedParameters = this.getMinimumNumberOfParameters() - defaultValues.length;
		final SopremoFunctionWithDefaultParameters sfdp =
			new SopremoFunctionWithDefaultParameters(this, fixedParameters);
		for (int index = 0; index < defaultValues.length; index++)
			sfdp.setDefaultParameter(fixedParameters + index, defaultValues[index]);
		return sfdp;
	}

	/**
	 * Binds a function by setting the last X parameters to predefined values.
	 */
	public SopremoFunction bind(IJsonNode... boundParameters) {
		final int fixedParameters = this.getMinimumNumberOfParameters() - boundParameters.length;
		final SopremoFunctionWithDefaultParameters sfdp =
			new SopremoFunctionWithDefaultParameters(this, fixedParameters, fixedParameters);
		for (int index = 0; index < boundParameters.length; index++)
			sfdp.setDefaultParameter(fixedParameters + index, boundParameters[index]);
		return sfdp;
	}
}
