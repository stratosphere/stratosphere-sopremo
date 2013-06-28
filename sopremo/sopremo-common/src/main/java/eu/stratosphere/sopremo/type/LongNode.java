package eu.stratosphere.sopremo.type;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import javolution.text.TypeFormat;
import eu.stratosphere.sopremo.pact.SopremoUtil;

/**
 * This node represents a long value.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
public class LongNode extends AbstractNumericNode implements INumericNode {

	private long value;

	/**
	 * Initializes a LongNode which represents the given <code>long</code>. To create new LongNodes please
	 * use LongNode.valueOf(<code>long</code>) instead.
	 * 
	 * @param v
	 *        the value that should be represented by this node
	 */
	public LongNode(final long value) {
		this.value = value;
	}

	/**
	 * Initializes LongNode.
	 */
	public LongNode() {
		this(0);
	}

	@Override
	public Long getJavaValue() {
		return this.value;
	}

	public void setValue(final long value) {
		this.value = value;
	}

	/**
	 * Creates a new instance of LongNode. This new instance represents the given value.
	 * 
	 * @param v
	 *        the value that should be represented by the new instance
	 * @return the newly created instance of LongNode
	 */
	public static LongNode valueOf(final long value) {
		return new LongNode(value);
	}

	@Override
	public int hashCode() {
		return (int) (this.value >>> 32) | (int) (this.value & 0xFFFFFFFF);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final LongNode other = (LongNode) obj;
		return this.value == other.value;
	}

	@Override
	public int getIntValue() {
		return (int) this.value;
	}

	@Override
	public long getLongValue() {
		return this.value;
	}

	/* (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.INumericNode#getGeneralilty()
	 */
	@Override
	public byte getGeneralilty() {
		return 32;
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
	public Class<LongNode> getType() {
		return LongNode.class;
	}

	@Override
	public String getValueAsText() {
		return String.valueOf(this.value);
	}

	@Override
	public void copyValueFrom(final IJsonNode otherNode) {
		checkNumber(otherNode);
		this.value = ((INumericNode) otherNode).getLongValue();
	}

	@Override
	public int compareToSameType(final IJsonNode other) {
		return Long.signum(this.value - ((LongNode) other).value);
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
