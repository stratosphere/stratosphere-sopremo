package eu.stratosphere.sopremo.type;


public class TextNodeTest extends JsonNodeTest<TextNode> {
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected TextNode createDefaultInstance(int index) {
		return new TextNode(String.valueOf(index));
	}
}
