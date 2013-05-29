package eu.stratosphere.sopremo.expressions;

import static eu.stratosphere.sopremo.type.JsonUtil.createArrayNode;
import static eu.stratosphere.sopremo.type.JsonUtil.createObjectNode;
import junit.framework.Assert;

import org.junit.Test;

import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

public class ArrayAccessTest extends EvaluableExpressionTest<ArrayAccess> {
	@Override
	protected ArrayAccess createDefaultInstance(final int index) {
		return new ArrayAccess(index);
	}

	@Test
	public void shouldAccessAllElements() {
		final IJsonNode result = new ArrayAccess().evaluate(
			createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
				createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
				createObjectNode("fieldName", 5)));
		Assert.assertEquals(createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
			createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
			createObjectNode("fieldName", 5)), result);
	}

	@Test
	public void shouldAccessOneElement() {
		final IJsonNode result = new ArrayAccess(1).evaluate(
			createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
				createObjectNode("fieldName", 3)));
		Assert.assertEquals(createObjectNode("fieldName", 2), result);
	}

	@Test
	public void shouldAccessOneElementWithNegativeIndex() {
		final IJsonNode result = new ArrayAccess(-1).evaluate(
			createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
				createObjectNode("fieldName", 3)));
		Assert.assertEquals(createObjectNode("fieldName", 3), result);
	}

	@Test
	public void shouldAccessRangeOfElement() {
		final IJsonNode result = new ArrayAccess(1, 3).evaluate(
			createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
				createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
				createObjectNode("fieldName", 5)));
		Assert.assertEquals(createArrayNode(createObjectNode("fieldName", 2),
			createObjectNode("fieldName", 3), createObjectNode("fieldName", 4)), result);
	}

	@Test
	public void shouldAccessRangeOfElementWithNegativeEndIndex() {
		final IJsonNode result = new ArrayAccess(1, -1).evaluate(
			createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
				createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
				createObjectNode("fieldName", 5)));
		Assert.assertEquals(createArrayNode(createObjectNode("fieldName", 2),
			createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
			createObjectNode("fieldName", 5)), result);
	}

	@Test
	public void shouldAccessRangeOfElementWithNegativeStartAndEndIndex() {
		final IJsonNode result = new ArrayAccess(-3, -1).evaluate(
			createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
				createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
				createObjectNode("fieldName", 5)));
		Assert.assertEquals(createArrayNode(createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
			createObjectNode("fieldName", 5)), result);
	}

	@Test
	public void shouldAccessRangeOfElementWithNegativeStartIndex() {
		final IJsonNode result = new ArrayAccess(-3, 4).evaluate(
			createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
				createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
				createObjectNode("fieldName", 5)));
		Assert.assertEquals(createArrayNode(createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
			createObjectNode("fieldName", 5)), result);
	}

	@Test
	public void shouldAccessReversedRangeOfElements() {
		final IJsonNode result = new ArrayAccess(3, 1).evaluate(
			createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
				createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
				createObjectNode("fieldName", 5)));
		Assert.assertEquals(createArrayNode(createObjectNode("fieldName", 4), createObjectNode("fieldName", 3),
			createObjectNode("fieldName", 2)), result);
	}

	@Test
	public void shouldReuseTargetIfWholeNodeIsAccessed() {
		final IArrayNode<?> input = createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
			createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
			createObjectNode("fieldName", 5));
		final ArrayAccess arrayAccess = new ArrayAccess();
		final IJsonNode result1 = arrayAccess.evaluate(input);
		final IJsonNode result2 = arrayAccess.evaluate(input);

		Assert.assertEquals(input, result1);
		Assert.assertNotSame(input, result1);
		Assert.assertSame(result1, result2);
	}

	@Test
	public void shouldReuseTargetIfRangeIsAccessed() {
		final IArrayNode<?> input = createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
			createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
			createObjectNode("fieldName", 5));
		final ArrayAccess arrayAccess = new ArrayAccess(1, 2);
		final IJsonNode result1 = arrayAccess.evaluate(input);
		final IJsonNode result2 = arrayAccess.evaluate(input);

		Assert.assertNotSame(input, result1);
		Assert.assertSame(result1, result2);
	}

	@Test
	public void shouldReuseTargetIfIndexIsAccessed() {
		final IArrayNode<?> input = createArrayNode(createObjectNode("fieldName", 1), createObjectNode("fieldName", 2),
			createObjectNode("fieldName", 3), createObjectNode("fieldName", 4),
			createObjectNode("fieldName", 5));
		final ArrayAccess arrayAccess = new ArrayAccess(1);
		final IJsonNode result1 = arrayAccess.evaluate(input);
		final IJsonNode result2 = arrayAccess.evaluate(input);

		Assert.assertNotSame(input, result1);
		Assert.assertSame(result1, result2);
	}
}
