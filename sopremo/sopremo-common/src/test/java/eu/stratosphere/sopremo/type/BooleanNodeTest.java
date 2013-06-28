package eu.stratosphere.sopremo.type;


public class BooleanNodeTest extends JsonNodeTest<BooleanNode> {
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected BooleanNode createDefaultInstance(int index) {
		return index == 0 ? BooleanNode.TRUE : BooleanNode.FALSE;
	}
}
