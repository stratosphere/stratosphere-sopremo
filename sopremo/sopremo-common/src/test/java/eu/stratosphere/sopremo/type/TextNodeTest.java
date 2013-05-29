package eu.stratosphere.sopremo.type;

import junit.framework.Assert;

public class TextNodeTest extends JsonNodeTest<TextNode> {
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected TextNode createDefaultInstance(int index) {
		return new TextNode(String.valueOf(index));
	}

	@Override
	public void testValue() {
		final TextNode textnode = new TextNode("sample TextNode");
		Assert.assertEquals("sample TextNode", textnode.getTextValue().toString());
	}

	@Override
	protected IJsonNode lowerNode() {
		return TextNode.valueOf("1 lower Node");
	}

	@Override
	protected IJsonNode higherNode() {
		return TextNode.valueOf("2 higher Node");
	}
}
