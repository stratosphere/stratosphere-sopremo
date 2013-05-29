package eu.stratosphere.sopremo.expressions;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import eu.stratosphere.sopremo.type.BooleanNode;

@Ignore
public abstract class BooleanExpressionTest<T extends BooleanExpression> extends EvaluableExpressionTest<T> {

	protected static UnaryExpression TRUE = new UnaryExpression(new ConstantExpression(BooleanNode.TRUE));

	protected static UnaryExpression FALSE = new UnaryExpression(new ConstantExpression(BooleanNode.FALSE));

	@Override
	@Test
	public void testToString() throws IOException {
		final StringBuilder builder = new StringBuilder();
		this.first.appendAsString(builder);
		Assert.assertNotSame(
			"builder did not write anything - override this test if it is indeed the desired behavior", "", builder
				.toString().intern());
	}
}
