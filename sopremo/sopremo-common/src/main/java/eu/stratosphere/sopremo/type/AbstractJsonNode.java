package eu.stratosphere.sopremo.type;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import com.esotericsoftware.kryo.io.Output;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.SopremoEnvironment;

/**
 * Abstract class to provide basic implementations for all node types.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
public abstract class AbstractJsonNode extends AbstractSopremoType implements IJsonNode {

	public abstract void clear();

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
		return this.getType().getName().compareTo(other.getType().getName());
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
