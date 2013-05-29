package eu.stratosphere.sopremo.base.join;

import eu.stratosphere.sopremo.expressions.ComparativeExpression;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoCross;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.BooleanNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

@InputCardinality(min = 2, max = 2)
public class ThetaJoin extends TwoSourceJoinBase<ThetaJoin> {
	private ComparativeExpression comparison = new ComparativeExpression(new InputSelection(0),
		ComparativeExpression.BinaryOperator.EQUAL, new InputSelection(1));

	public ComparativeExpression getComparison() {
		return this.comparison;
	}

	public void setComparison(ComparativeExpression comparison) {
		if (comparison == null)
			throw new NullPointerException("comparison must not be null");

		this.comparison = comparison;
	}

	public ThetaJoin withComparison(ComparativeExpression comparison) {
		this.setComparison(comparison);
		return this;
	}

	public static class Implementation extends SopremoCross {
		private transient final IArrayNode<IJsonNode> inputs = new ArrayNode<IJsonNode>();

		private ComparativeExpression comparison;

		@Override
		protected void cross(IJsonNode value1, IJsonNode value2, JsonCollector out) {
			this.inputs.set(0, value1);
			this.inputs.set(1, value2);
			if (this.comparison.evaluate(this.inputs) == BooleanNode.TRUE)
				out.collect(this.inputs);
		}
	}
}