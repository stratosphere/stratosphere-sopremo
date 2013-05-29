package eu.stratosphere.sopremo.expressions;

import static eu.stratosphere.sopremo.type.JsonUtil.createArrayNode;
import static eu.stratosphere.sopremo.type.JsonUtil.createObjectNode;
import junit.framework.Assert;

import org.junit.Test;

import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.NullNode;

public class ArrayMergerTest extends EvaluableExpressionTest<ArrayMerger> {

	@Override
	protected ArrayMerger createDefaultInstance(final int index) {
		return new ArrayMerger();
	}

	@Test
	public void shouldMergeOneArray() {
		final IJsonNode result = new ArrayMerger().evaluate(
			createArrayNode(createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
				createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
				createObjectNode("fieldName", 5))));
		Assert.assertEquals(createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
			createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
			createObjectNode("fieldName", 5)), result);
	}

	@Test
	public void shouldMergeEmptyArray() {
		final IJsonNode result = new ArrayMerger().evaluate(
			createArrayNode(createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
				createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
				createObjectNode("fieldName", 5)), createArrayNode()));
		Assert.assertEquals(createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
			createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
			createObjectNode("fieldName", 5)), result);
	}

	@Test
	public void shouldFillNullValuesWithValuesFromOtherArrays() {
		final IJsonNode result = new ArrayMerger().evaluate(
			createArrayNode(createArrayNode(null, createObjectNode("fieldName", 2),
				createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
				createObjectNode("fieldName", 5)), createArrayNode(createObjectNode("fieldName", 1))));
		Assert.assertEquals(createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
			createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
			createObjectNode("fieldName", 5)), result);
	}

	@Test
	public void shouldFillNullNodesWithValuesFromOtherArrays() {
		final IJsonNode result = new ArrayMerger().evaluate(
			createArrayNode(
				createArrayNode(NullNode.getInstance(), createObjectNode("fieldName", 2),
					NullNode.getInstance()), createArrayNode(createObjectNode("fieldName", 1)),
				createArrayNode(null, null, createObjectNode("fieldName", 3))));
		Assert.assertEquals(createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
			createObjectNode("fieldName", 3)), result);
	}

	@Test
	public void shouldReuseTarget() {
		final IArrayNode<?> firstArray = createArrayNode(null, IntNode.valueOf(2), IntNode.valueOf(3));
		final IArrayNode<?> secondArray = createArrayNode(IntNode.valueOf(1));

		final ArrayMerger arrayMerger = new ArrayMerger();
		final IJsonNode result1 = arrayMerger.evaluate(createArrayNode(firstArray, secondArray));
		final IJsonNode result2 = arrayMerger.evaluate(createArrayNode(firstArray, secondArray));

		Assert.assertEquals(createArrayNode(IntNode.valueOf(1), IntNode.valueOf(2), IntNode.valueOf(3)), result2);
		Assert.assertSame(result1, result2);
	}
}
