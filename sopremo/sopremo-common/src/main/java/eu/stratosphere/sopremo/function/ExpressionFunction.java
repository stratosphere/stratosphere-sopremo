package eu.stratosphere.sopremo.function;

import java.io.IOException;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

public class ExpressionFunction extends SopremoFunction implements Inlineable {
	private final EvaluationExpression definition;

	public ExpressionFunction(final int numParams, final EvaluationExpression definition) {
		super("Sopremo function", numParams, numParams);
		this.definition = definition;
	}

	/**
	 * Initializes ExpressionFunction.
	 */
	public ExpressionFunction() {
		super("Sopremo function", 0, 0);
		this.definition = null;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		appendable.append("Sopremo function ");
		this.definition.appendAsString(appendable);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.function.Callable#call(InputType[], eu.stratosphere.sopremo.EvaluationContext)
	 */
	@Override
	public IJsonNode call(final IArrayNode<IJsonNode> params) {
		return this.definition.evaluate(params);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.definition.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		ExpressionFunction other = (ExpressionFunction) obj;
		return this.definition.equals(other.definition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.function.Inlineable#getDefinition(eu.stratosphere.sopremo.expressions.EvaluationExpression
	 * [])
	 */
	@Override
	public EvaluationExpression getDefinition() {
		return this.definition;
	}
}
