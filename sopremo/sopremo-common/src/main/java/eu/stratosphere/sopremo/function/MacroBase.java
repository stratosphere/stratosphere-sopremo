package eu.stratosphere.sopremo.function;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;

public abstract class MacroBase extends Callable<EvaluationExpression, EvaluationExpression[]> {

	/**
	 * Initializes MacroBase.
	 * 
	 * @param minimumNumberOfParameters
	 * @param maximumNumberOfParameters
	 */
	public MacroBase(final int minimumNumberOfParameters, final int maximumNumberOfParameters) {
		super(minimumNumberOfParameters, maximumNumberOfParameters);
	}

	public MacroBase(final int numberOfParameters) {
		super(numberOfParameters, numberOfParameters);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.function.Callable#call(java.lang.Object)
	 */
	@Override
	public final EvaluationExpression call(final EvaluationExpression[] params) {
		if (!this.accepts(params.length))
			throw new IllegalArgumentException("Incorrect number of parameters");
		return this.process(params);
	}

	protected abstract EvaluationExpression process(EvaluationExpression[] params);
}
