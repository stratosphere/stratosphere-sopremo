package eu.stratosphere.sopremo.expressions;

import static eu.stratosphere.sopremo.type.JsonUtil.createObjectNode;
import javolution.util.FastMap;
import junit.framework.Assert;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import eu.stratosphere.sopremo.CoreFunctions;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.JsonUtil;

public class GroupingExpressionTest extends EvaluableExpressionTest<GroupingExpression> {
	@Override
	protected GroupingExpression createDefaultInstance(final int index) {
		return new GroupingExpression(new ConstantExpression(index), ConstantExpression.NULL);
	}

	@Test
	public void testAggregation() {
		this.context.getFunctionRegistry().put(CoreFunctions.class);

		final ArrayNode<IJsonNode> input = new ArrayNode<IJsonNode>();
		input.add(createObjectNode("key", 1, "value", 11));
		input.add(createObjectNode("key", 2, "value", 24));
		input.add(createObjectNode("key", 3, "value", 33));
		input.add(createObjectNode("key", 2, "value", 25));
		input.add(createObjectNode("key", 1, "value", 12));

		final GroupingExpression aggExpression =
			new GroupingExpression(new ObjectAccess("key"), new FunctionCall("sum",
				this.context, new ArrayProjection(new ObjectAccess("value"))));

		final IJsonNode result = aggExpression.evaluate(input);

		final IArrayNode<?> expected = JsonUtil.createArrayNode(23, 49, 33);

		Assert.assertEquals(expected, result);
	}

	@Test
	public void shouldReuseTarget() {
		this.context.getFunctionRegistry().put(CoreFunctions.class);

		final ArrayNode<IJsonNode> input = new ArrayNode<IJsonNode>();
		input.add(createObjectNode("key", 1, "value", 11));
		input.add(createObjectNode("key", 2, "value", 24));
		input.add(createObjectNode("key", 3, "value", 33));
		input.add(createObjectNode("key", 2, "value", 25));
		input.add(createObjectNode("key", 1, "value", 12));

		final GroupingExpression aggExpression =
			new GroupingExpression(new ObjectAccess("key"), new FunctionCall("sum",
				this.context, new ArrayProjection(new ObjectAccess("value"))));

		final IJsonNode result1 = aggExpression.evaluate(input);

		final IArrayNode<?> expected = JsonUtil.createArrayNode(23, 49, 33);

		Assert.assertEquals(expected, result1);

		final IJsonNode result2 = aggExpression.evaluate(input);
		Assert.assertSame(result1, result2);
		Assert.assertEquals(expected, result2);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.EvaluableExpressionTest#initVerifier(nl.jqno.equalsverifier.EqualsVerifier)
	 */
	@Override
	protected void initVerifier(EqualsVerifier<GroupingExpression> equalVerifier) {
		super.initVerifier(equalVerifier);
		final FastMap<Object, Object> red = new FastMap<Object, Object>();
		final FastMap<Object, Object> black = new FastMap<Object, Object>();
		black.put("", "black");
		equalVerifier.withPrefabValues(FastMap.class, red, black);
	}
}
