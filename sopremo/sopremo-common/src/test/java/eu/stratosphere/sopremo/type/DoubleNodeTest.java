package eu.stratosphere.sopremo.type;


public class DoubleNodeTest extends JsonNodeTest<DoubleNode> {
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected DoubleNode createDefaultInstance(int index) {
		return new DoubleNode(index);
	}
}
