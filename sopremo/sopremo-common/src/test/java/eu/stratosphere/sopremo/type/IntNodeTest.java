package eu.stratosphere.sopremo.type;

import junit.framework.Assert;

public class IntNodeTest extends JsonNodeTest<IntNode> {
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected IntNode createDefaultInstance(int index) {
		return new IntNode(index);
	}

	@Override
	public void testValue() {
		final IntNode intnode = new IntNode(23);
		Assert.assertEquals(23, intnode.getIntValue());
	}

	@Override
	protected IJsonNode lowerNode() {
		return IntNode.valueOf(-42);
	}

	@Override
	protected IJsonNode higherNode() {
		return IntNode.valueOf(-41);
	}

}
