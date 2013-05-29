package eu.stratosphere.sopremo.pact;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import eu.stratosphere.pact.common.type.Key;
import eu.stratosphere.pact.common.type.Value;
import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IJsonNode.Type;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.util.reflect.ReflectUtil;

/**
 * A JsonNodeWrapper wraps a {@link IJsonNode} and adds some new functionality which
 * exceed the possibilities of {@link IJsonNode}s
 */
public class JsonNodeWrapper extends AbstractSopremoType implements Key, Value {

	private IJsonNode value;

	/**
	 * Initializes a JsonNodeWrapper that wraps a {@link MissingNode}.
	 */
	public JsonNodeWrapper() {
		this.value = MissingNode.getInstance();
	}

	/**
	 * Initializes a JsonNodeWrapper that wraps the given {@link IJsonNode}.
	 * 
	 * @param value
	 *        the {@link IJsonNode} that should be wrapped
	 */
	public JsonNodeWrapper(final IJsonNode value) {
		super();

		if (value == null)
			throw new NullPointerException();

		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.nephele.types.Record#read(java.io.DataInput)
	 */
	@Override
	public void read(DataInput in) throws IOException {
		try {
			final int typeIndex = in.readByte();
			if (this.value == null || this.value.getType().ordinal() != typeIndex)
				this.value = Type.values()[typeIndex].getClazz().newInstance();
			this.value = this.value.readResolve(in);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void write(final DataOutput out) throws IOException {
		out.writeByte(this.value.getType().ordinal());
		this.value.write(out);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Key o) {
		return this.value.compareTo(((JsonNodeWrapper) o).value);
	}

	/**
	 * Returns the wrapped node. Tries to cast the node to the given class
	 * 
	 * @param klass
	 *        the class that should be used to cast the wrapped node
	 * @return the wrapped node after casting
	 */
	@SuppressWarnings({ "unchecked" })
	public <T extends IJsonNode> T getValue() {
		return (T) this.value;
	}

	/**
	 * Sets the value to the specified {@link IJsonNode}.
	 * 
	 * @param value
	 *        the {@link IJsonNode} that should be wrapped
	 */
	public void setValue(final IJsonNode value) {
		if (value == null)
			throw new NullPointerException("value must not be null");

		this.value = value;
	}

	// @Override
	// public int hashCode() {
	// return this.value.hashCode();
	// }

	// @Override
	// public boolean equals(final Object o) {
	// return this.value.equals(((JsonNodeWrapper) o).getValue());
	// }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.value == null ? 0 : this.value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (this.getClass() != obj.getClass())
			return false;
		JsonNodeWrapper other = (JsonNodeWrapper) obj;
		return this.value.equals(other.getValue());
	}

	@Override
	public void appendAsString(final Appendable sb) throws IOException {
		this.value.appendAsString(sb);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#copyPropertiesFrom(eu.stratosphere.sopremo.ISopremoType)
	 */
	@Override
	public void copyPropertiesFrom(ISopremoType original) {
		final JsonNodeWrapper wrapper = (JsonNodeWrapper) original;
		if (this.value.getType() != wrapper.value.getType())
			this.value = ReflectUtil.newInstance(wrapper.value.getType().getClazz());
		this.value.copyValueFrom(wrapper.value);
	}
//
//	@Override
//	public int getMaxNormalizedKeyLen() {
//		final int maxNormalizedKeyLen = this.value.getMaxNormalizedKeyLen();
//		if (maxNormalizedKeyLen == Integer.MAX_VALUE)
//			return Integer.MAX_VALUE;
//		return maxNormalizedKeyLen + 1;
//	}
//
//	@Override
//	public void copyNormalizedKey(final byte[] target, final int offset, final int len) {
//		if (len > 0) {
//			target[offset] = (byte) this.value.getType().ordinal();
//
//			if (len > 1)
//				this.value.copyNormalizedKey(target, offset + 1, len - 1);
//		}
//	}
}
