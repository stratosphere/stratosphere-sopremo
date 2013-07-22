package eu.stratosphere.sopremo.type;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import javolution.text.TypeFormat;
import eu.stratosphere.sopremo.pact.SopremoUtil;

/**
 * This node represents an integer value.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
public class IntNode extends AbstractNumericNode implements INumericNode {

	private int value;

	public static final IntNode ZERO = new IntNode(0), ONE = new IntNode(1);

	/**
	 * Initializes an IntNode which represents 0
	 */
	public IntNode() {
		this.value = 0;
	}

	/**
	 * Initializes an IntNode which represents the given <code>int</code>. To create new IntNodes please use
	 * IntNode.valueOf(<code>int</code>) instead.
	 * 
	 * @param v
	 *        the value that should be represented by this node
	 */
	public IntNode(final int v) {
		this.value = v;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.INumericNode#getGeneralilty()
	 */
	@Override
	public byte getGeneralilty() {
		return 16;
	}

	/**
	 * Creates a new instance of IntNode. This new instance represents the given value.
	 * 
	 * @param v
	 *        the value that should be represented by the new instance
	 * @return the newly created instance of IntNode
	 */
	public static IntNode valueOf(final int v) {
		return new IntNode(v);
	}

	/**
	 * Sets the value to the specified value.
	 * 
	 * @param value
	 *        the value to set
	 */
	public void setValue(final int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return this.value;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;

		final IntNode other = (IntNode) obj;
		return this.value == other.value;
	}

	@Override
	public int getIntValue() {
		return this.value;
	}

	@Override
	public long getLongValue() {
		return Long.valueOf(this.value);
	}

	@Override
	public BigInteger getBigIntegerValue() {
		return BigInteger.valueOf(this.value);
	}

	@Override
	public BigDecimal getDecimalValue() {
		return BigDecimal.valueOf(this.value);
	}

	@Override
	public double getDoubleValue() {
		return Double.valueOf(this.value);
	}

	@Override
	public boolean isIntegralNumber() {
		return true;
	}

	@Override
	public String getValueAsText() {
		return String.valueOf(this.value);
	}

	@Override
	public Integer getJavaValue() {
		return this.value;
	}

	@Override
	public Class<IntNode> getType() {
		return IntNode.class;
	}

	@Override
	public void copyValueFrom(final IJsonNode otherNode) {
		checkNumber(otherNode);
		this.value = ((INumericNode) otherNode).getIntValue();
	}

	@Override
	public int compareToSameType(final IJsonNode other) {
		return this.value - ((IntNode) other).value;
	}

	@Override
	public void clear() {
		if (SopremoUtil.DEBUG)
			this.value = 0;
	}

	public void increment() {
		this.value++;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		TypeFormat.format(this.value, appendable);
	}
}
