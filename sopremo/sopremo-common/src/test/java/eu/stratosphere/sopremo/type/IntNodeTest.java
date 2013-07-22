package eu.stratosphere.sopremo.type;


public class IntNodeTest extends JsonNodeTest<IntNode> {
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected IntNode createDefaultInstance(int index) {
		return new IntNode(index);
	}
}
