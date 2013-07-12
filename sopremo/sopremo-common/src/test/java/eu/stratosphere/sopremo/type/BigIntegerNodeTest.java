package eu.stratosphere.sopremo.type;

import java.math.BigInteger;

public class BigIntegerNodeTest extends JsonNodeTest<BigIntegerNode> {

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected BigIntegerNode createDefaultInstance(int index) {
		return new BigIntegerNode(BigInteger.valueOf(index));
	}

	@Override
	public void testValue() {
	}

	@Override
	protected IJsonNode lowerNode() {
		return BigIntegerNode.valueOf(BigInteger.valueOf(-42));
	}

	@Override
	protected IJsonNode higherNode() {
		return BigIntegerNode.valueOf(BigInteger.valueOf(Long.MAX_VALUE));
	}

}
