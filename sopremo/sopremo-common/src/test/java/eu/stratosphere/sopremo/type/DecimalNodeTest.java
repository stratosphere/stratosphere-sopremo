package eu.stratosphere.sopremo.type;

import java.math.BigDecimal;

public class DecimalNodeTest extends JsonNodeTest<DecimalNode> {

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected DecimalNode createDefaultInstance(int index) {
		return new DecimalNode(BigDecimal.valueOf(index));
	}
}
