package eu.stratosphere.sopremo.function;

import java.io.IOException;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;

public abstract class SimpleMacro<In extends EvaluationExpression> extends MacroBase {

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		appendable.append("Simple macro");
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.function.Callable#call(java.lang.Object, eu.stratosphere.sopremo.EvaluationContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EvaluationExpression call(final EvaluationExpression[] params) {
		return this.call((In) params[0]);
	}

	public abstract EvaluationExpression call(In inputExpr);
}
