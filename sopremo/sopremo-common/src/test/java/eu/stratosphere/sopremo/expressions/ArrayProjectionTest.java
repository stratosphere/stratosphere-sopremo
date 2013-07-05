package eu.stratosphere.sopremo.expressions;

import static eu.stratosphere.sopremo.type.JsonUtil.createArrayNode;
import static eu.stratosphere.sopremo.type.JsonUtil.createObjectNode;
import static eu.stratosphere.sopremo.type.JsonUtil.createStreamArrayNode;
import junit.framework.Assert;

import org.junit.Test;

import eu.stratosphere.pact.testing.AssertUtil;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;

public class ArrayProjectionTest extends EvaluableExpressionTest<ArrayProjection> {
	@Override
	protected ArrayProjection createDefaultInstance(final int index) {
		return new ArrayProjection().withInputExpression(new ObjectAccess(String.valueOf(index)));
	}

	@Test
	public void shouldAccessFieldOfArray() {
		final ArrayProjection arrayProjection = new ArrayProjection(new ObjectAccess("fieldName"));
		final IJsonNode result = arrayProjection.evaluate(
			createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
				createObjectNode("fieldName", 3)));
		Assert.assertEquals(createArrayNode(1, 2, 3), result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldAccessFieldOfStreamArray() {
		final ArrayProjection arrayProjection = new ArrayProjection(new ObjectAccess("fieldName"));
		final IJsonNode result = arrayProjection.evaluate(
			createStreamArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
				createObjectNode("fieldName", 3)));
		Assert.assertFalse(result instanceof IArrayNode);
		Assert.assertTrue(result instanceof IStreamNode);
		AssertUtil.assertIteratorEquals(createArrayNode(1, 2, 3).iterator(), ((Iterable<IJsonNode>) result).iterator());
	}

}
