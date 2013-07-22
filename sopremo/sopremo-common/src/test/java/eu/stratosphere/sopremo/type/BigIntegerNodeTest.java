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
}
