package eu.stratosphere.sopremo.function;

import java.io.IOException;

import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * A base for built-in and user-defined functions.
 * 
 * @author Arvid Heise
 */
public abstract class SopremoFunction extends Callable<IJsonNode, IArrayNode<IJsonNode>> {
	private final String name;

	private final int minimumNumberOfParameters, maximumNumberOfParameters;

	public SopremoFunction(String name, int numberOfParameters) {
		this(name, numberOfParameters, numberOfParameters);
	}

	public SopremoFunction(String name, int minimumNumberOfParameters, int maximumNumberOfParameters) {
		this.name = name;
		this.minimumNumberOfParameters = minimumNumberOfParameters;
		this.maximumNumberOfParameters = maximumNumberOfParameters;
	}

	/**
	 * Initializes SopremoFunction.
	 */
	SopremoFunction() {
		this("", 0, 0);
	}

	/**
	 * Returns true if the function can be called with the given number of parameters.
	 */
	public boolean accepts(int numberOfArguments) {
		return this.minimumNumberOfParameters <= numberOfArguments &&
			numberOfArguments <= this.maximumNumberOfParameters;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		appendable.append(this.name);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#clone()
	 */
	@Override
	public SopremoFunction clone() {
		return (SopremoFunction) super.clone();
	}

	/**
	 * Returns the maximumNumberOfParameters.
	 * 
	 * @return the maximumNumberOfParameters
	 */
	public int getMaximumNumberOfParameters() {
		return this.maximumNumberOfParameters;
	}

	/**
	 * Returns the minimumNumberOfParameters.
	 * 
	 * @return the minimumNumberOfParameters
	 */
	public int getMinimumNumberOfParameters() {
		return this.minimumNumberOfParameters;
	}

	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.maximumNumberOfParameters;
		result = prime * result + this.minimumNumberOfParameters;
		result = prime * result + this.name.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		SopremoFunction other = (SopremoFunction) obj;
		return this.maximumNumberOfParameters == other.maximumNumberOfParameters &&
			this.minimumNumberOfParameters == other.minimumNumberOfParameters &&
			this.name.equals(other.name);
	}

	public SopremoFunction withDefaultParameters(IJsonNode... defaultValues) {
		final int fixedParameters = this.getMinimumNumberOfParameters() - defaultValues.length;
		final SopremoFunctionWithDefaultParameters sfdp =
			new SopremoFunctionWithDefaultParameters(this, fixedParameters);
		for (int index = 0; index < defaultValues.length; index++)
			sfdp.setDefaultParameter(fixedParameters + index, defaultValues[index]);
		return sfdp;
	}
}
