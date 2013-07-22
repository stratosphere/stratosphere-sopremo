package eu.stratosphere.sopremo.type;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import javolution.text.TypeFormat;
import eu.stratosphere.sopremo.pact.SopremoUtil;

/**
 * This node represents a double value.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
public class DoubleNode extends AbstractNumericNode implements INumericNode {

	private double value;

	public final static DoubleNode NaN = DoubleNode.valueOf(Double.NaN);

	/**
	 * Initializes a DoubleNode which represents 0.0
	 */
	public DoubleNode() {
	}

	/**
	 * Initializes a DoubleNode which represents the given <code>double</code>. To create new DoubleNodes please
	 * use DoubleNode.valueOf(<code>double</code>) instead.
	 * 
	 * @param v
	 *        the value that should be represented by this node
	 */
	public DoubleNode(final double v) {
		this.value = v;
	}

	/**
	 * Initializes a DoubleNode which represents the given <code>float</code>. To create new DoubleNodes please
	 * use DoubleNode.valueOf(<code>double</code>) instead.
	 * 
	 * @param v
	 *        the value that should be represented by this node
	 */
	public DoubleNode(final float v) {
		this.value = v;
	}

	@Override
	public Double getJavaValue() {
		return this.value;
	}

	/**
	 * Creates a new instance of DoubleNode. This new instance represents the given value.
	 * 
	 * @param v
	 *        the value that should be represented by the new instance
	 * @return the newly created instance of DoubleNode
	 */
	public static DoubleNode valueOf(final double v) {
		return new DoubleNode(v);
	}

	public void setValue(final double value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.INumericNode#getGeneralilty()
	 */
	@Override
	public byte getGeneralilty() {
		return 64;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.value);
		result = prime * result + (int) (temp ^ temp >>> 32);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;

		final DoubleNode other = (DoubleNode) obj;
		if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}

	@Override
	public int getIntValue() {
		return (int) this.value;
	}

	@Override
	public long getLongValue() {
		return (long) this.value;
	}

	@Override
	public BigInteger getBigIntegerValue() {
		return BigInteger.valueOf(this.getLongValue());
	}

	@Override
	public BigDecimal getDecimalValue() {
		return BigDecimal.valueOf(this.value);
	}

	@Override
	public double getDoubleValue() {
		return this.value;
	}

	@Override
	public boolean isFloatingPointNumber() {
		return true;
	}

	@Override
	public Class<DoubleNode> getType() {
		return DoubleNode.class;
	}

	@Override
	public String getValueAsText() {
		return String.valueOf(this.value);
	}

	@Override
	public int compareToSameType(final IJsonNode other) {
		return Double.compare(this.value, ((DoubleNode) other).value);
	}

	@Override
	public void copyValueFrom(final IJsonNode otherNode) {
		checkNumber(otherNode);
		this.value = ((INumericNode) otherNode).getDoubleValue();
	}

	@Override
	public void clear() {
		if (SopremoUtil.DEBUG)
			this.value = 0;
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
