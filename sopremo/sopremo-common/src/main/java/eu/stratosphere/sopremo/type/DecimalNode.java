package eu.stratosphere.sopremo.type;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import eu.stratosphere.sopremo.pact.SopremoUtil;

/**
 * This node represents a {@link BigDecimal}.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
public class DecimalNode extends AbstractNumericNode implements INumericNode {

	private BigDecimal value;

	/**
	 * Initializes a DecimalNode which represents 0.
	 */
	public DecimalNode() {
		this.value = new BigDecimal(0);
	}

	/**
	 * Initializes a DecimalNode which represents the given {@link BigDecimal}. To create new DecimalNodes please
	 * use DecimalNode.valueOf(BigDecimal) instead.
	 * 
	 * @param v
	 *        the value that should be represented by this node
	 */
	public DecimalNode(final BigDecimal v) {
		this.value = v;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.INumericNode#getGeneralilty()
	 */
	@Override
	public byte getGeneralilty() {
		return 80;
	}

	/**
	 * Creates a new DecimalNode which represents the given {@link BigDecimal}.
	 * 
	 * @param v
	 *        the value that should be represented by this node
	 * @return the new DecimalNode
	 */
	public static DecimalNode valueOf(final BigDecimal v) {
		if (v == null)
			throw new NullPointerException();
		return new DecimalNode(v);
	}

	public void setValue(final BigDecimal value) {
		this.value = value;
	}

	@Override
	public BigDecimal getJavaValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.value.hashCode();
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

		final DecimalNode other = (DecimalNode) obj;
		if (!this.value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public int getIntValue() {
		return this.value.intValue();
	}

	@Override
	public long getLongValue() {
		return this.value.longValue();
	}

	@Override
	public BigInteger getBigIntegerValue() {
		return this.value.toBigInteger();
	}

	@Override
	public BigDecimal getDecimalValue() {
		return this.value;
	}

	@Override
	public double getDoubleValue() {
		return this.value.doubleValue();
	}

	@Override
	public boolean isFloatingPointNumber() {
		return true;
	}

	@Override
	public Class<DecimalNode> getType() {
		return DecimalNode.class;
	}

	@Override
	public String getValueAsText() {
		return this.value.toPlainString();
	}

	@Override
	public int compareToSameType(final IJsonNode other) {
		return this.value.compareTo(((DecimalNode) other).value);
	}

	@Override
	public void copyValueFrom(final IJsonNode otherNode) {
		checkNumber(otherNode);
		this.value = ((INumericNode) otherNode).getDecimalValue();
	}

	@Override
	public void clear() {
		if (SopremoUtil.DEBUG)
			this.value = BigDecimal.ZERO;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		//TextFormat.getInstance(BigDecimal.class).format(this.value, appendable);
		appendable.append(this.value.toPlainString());
	}
}
