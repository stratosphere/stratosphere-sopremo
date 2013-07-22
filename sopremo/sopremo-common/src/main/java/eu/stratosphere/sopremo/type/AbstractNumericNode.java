package eu.stratosphere.sopremo.type;

/**
 * Abstract class to provide basic implementations for numeric type nodes.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
public abstract class AbstractNumericNode extends AbstractJsonNode implements INumericNode {

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.INumericNode#isFloatingPointNumber()
	 */
	@Override
	public boolean isFloatingPointNumber() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.INumericNode#isIntegralNumber()
	 */
	@Override
	public boolean isIntegralNumber() {
		return false;
	}

	public static void checkNumber(IJsonNode node) {
		if (!(node instanceof INumericNode))
			throw new IllegalArgumentException("Not a number " + node);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#clone()
	 */
	@Override
	public AbstractNumericNode clone() {
		return (AbstractNumericNode) super.clone();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#compareToOtherType(eu.stratosphere.pact.common.type.Key)
	 */
	@Override
	protected int compareToOtherType(IJsonNode other) {
		if (other instanceof INumericNode)
			return Double.compare(this.getDoubleValue(), ((INumericNode) other).getDoubleValue());
		return super.compareToOtherType(other);
	}
}
