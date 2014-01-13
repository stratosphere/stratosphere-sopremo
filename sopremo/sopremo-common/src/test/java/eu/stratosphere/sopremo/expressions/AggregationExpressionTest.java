package eu.stratosphere.sopremo.expressions;

import static eu.stratosphere.sopremo.type.JsonUtil.createArrayNode;

import org.junit.Assert;
import org.junit.Test;

import eu.stratosphere.sopremo.CoreFunctions;
import eu.stratosphere.sopremo.aggregation.Aggregation;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.INumericNode;
import eu.stratosphere.sopremo.type.IntNode;

public class AggregationExpressionTest extends EvaluableExpressionTest<AggregationExpression> {
	@Test
	public void shouldAggregate() {
		final IJsonNode result = new AggregationExpression(CoreFunctions.MAX).evaluate(createArrayNode(2, 4));
		Assert.assertTrue(result instanceof INumericNode);
		Assert.assertEquals(IntNode.valueOf(4), result);
	}

	@Test
	public void testFunctionAndExpression() {
		final Aggregation func = CoreFunctions.SUM;
		final ConstantExpression expr = new ConstantExpression(1);
		final AggregationExpression aggregation = new AggregationExpression(func).withInputExpression(expr);
		Assert.assertEquals(func, aggregation.getAggregation());
		Assert.assertEquals(expr, aggregation.getInputExpression());
	}

	@Override
	protected AggregationExpression createDefaultInstance(final int index) {
		switch (index) {
		case 0:
			return new AggregationExpression(CoreFunctions.MAX);
		case 1:
			return new AggregationExpression(CoreFunctions.CONCAT);
		case 2:
			return new AggregationExpression(CoreFunctions.FIRST);
		default:
			return new AggregationExpression(CoreFunctions.MIN);
		}
	}
}
