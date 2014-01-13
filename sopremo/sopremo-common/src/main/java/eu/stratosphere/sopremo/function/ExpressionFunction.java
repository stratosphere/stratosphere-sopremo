package eu.stratosphere.sopremo.function;

import java.io.IOException;

import com.google.common.base.Predicates;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.expressions.TransformFunction;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

public class ExpressionFunction extends SopremoFunction {
	private final EvaluationExpression definition;

	/**
	 * Initializes ExpressionFunction.
	 */
	public ExpressionFunction() {
		super(0, 0);
		this.definition = null;
	}

	public ExpressionFunction(final int numParams, final EvaluationExpression definition) {
		super(numParams, numParams);
		this.definition = definition;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
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
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final ExpressionFunction other = (ExpressionFunction) obj;
		return this.definition.equals(other.definition);
	}

	public EvaluationExpression getDefinition() {
		return this.definition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.definition.hashCode();
		return result;
	}

	public EvaluationExpression inline(final EvaluationExpression... paramList) {
		return this.getDefinition().clone().replace(Predicates.instanceOf(InputSelection.class),
			new TransformFunction() {
				@Override
				public EvaluationExpression apply(final EvaluationExpression in) {
					return paramList[((InputSelection) in).getIndex()].clone();
				}
			}).simplify();
	}
}
