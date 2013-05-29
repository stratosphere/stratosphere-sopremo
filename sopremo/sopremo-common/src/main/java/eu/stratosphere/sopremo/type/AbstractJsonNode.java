package eu.stratosphere.sopremo.type;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.ISopremoType;

/**
 * Abstract class to provide basic implementations for all node types.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
public abstract class AbstractJsonNode extends AbstractSopremoType implements IJsonNode {

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IJsonNode#getType()
	 */
	@Override
	public abstract Type getType();

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IJsonNode#canonicalize()
	 */
	@Override
	public AbstractJsonNode canonicalize() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#copyPropertiesFrom(eu.stratosphere.sopremo.AbstractSopremoType)
	 */
	@Override
	public void copyPropertiesFrom(ISopremoType original) {
		this.copyValueFrom((IJsonNode) original);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#clone()
	 */
	@Override
	public AbstractJsonNode clone() {
		return (AbstractJsonNode) super.clone();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IJsonNode#read(java.io.DataInput)
	 */
	@Override
	public abstract IJsonNode readResolve(DataInput in) throws IOException;

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IJsonNode#write(java.io.DataOutput)
	 */

	@Override
	public abstract void write(DataOutput out) throws IOException;

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IJsonNode#isNull()
	 */
	@Override
	public boolean isNull() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IJsonNode#isMissing()
	 */
	@Override
	public boolean isMissing() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IJsonNode#isCopyable(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public boolean isCopyable(IJsonNode otherNode) {
		return otherNode.getType() == this.getType();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IJsonNode#isObject()
	 */
	@Override
	public boolean isObject() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IJsonNode#isArray()
	 */
	@Override
	public boolean isArray() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IJsonNode#isTextual()
	 */
	@Override
	public boolean isTextual() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IJsonNode#compareTo(eu.stratosphere.pact.common.type.Key)
	 */

	@Override
	public int compareTo(final IJsonNode otherNode) {
		final int result;
		if (this.getType() != otherNode.getType())
			result = this.compareToOtherType(otherNode);
		else
			result = this.compareToSameType(otherNode);
		// System.err.println(this + "<=>" + other + " -> " + result);
		return result;
	}

	protected int compareToOtherType(final IJsonNode other) {
		return this.getType().compareTo(other.getType());
	}

	protected void checkForSameType(final IJsonNode other) {
		if (other.getType() != this.getType())
			throw new IllegalArgumentException(String.format(
				"The type of this node %s does not match the type of the other node %s: %s", this.getType(),
				other.getType(), other));
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IJsonNode#compareToSameType(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public abstract int compareToSameType(IJsonNode other);

	@Override
	public int getMaxNormalizedKeyLen() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void copyNormalizedKey(final byte[] target, final int offset, final int len) {
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			this.write(new DataOutputStream(stream));
			final byte[] result = stream.toByteArray();
			final int resultLenght = result.length;
			for (int i = 0; i < resultLenght; i++)
				target[offset + i] = result[i];
			this.fillWithZero(target, offset + resultLenght, offset + len);
		} catch (final IOException e) {
			e.printStackTrace();

		}
	}

	protected void fillWithZero(final byte[] target, final int fromIndex, final int toIndex) {
		Arrays.fill(target, fromIndex, toIndex, (byte) 0);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof IJsonNode))
			return false;
		final IJsonNode other = (IJsonNode) obj;

		if (this.getType() != other.getType())
			return false;
		return this.compareToSameType(other) == 0;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public abstract int hashCode();
}
