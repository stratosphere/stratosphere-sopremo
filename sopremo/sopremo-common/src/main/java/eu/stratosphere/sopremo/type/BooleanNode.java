package eu.stratosphere.sopremo.type;

import java.io.IOException;

import javolution.text.TypeFormat;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * This node represents a boolean value.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
public class BooleanNode extends AbstractJsonNode implements IPrimitiveNode {

	/**
	 * @author Arvid Heise
	 */
	@DefaultSerializer(UnmodifiableBoolean.BooleanSerializer.class)
	private static final class UnmodifiableBoolean extends BooleanNode {
		/**
		 * Initializes UnmodifiableBoolean.
		 * 
		 * @param v
		 */
		private UnmodifiableBoolean(boolean v) {
			super(v);
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#copy()
		 */
		@Override
		public UnmodifiableBoolean clone() {
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.type.BooleanNode#copyValueFrom(eu.stratosphere.sopremo.type.IJsonNode)
		 */
		@Override
		public void copyValueFrom(IJsonNode otherNode) {
			throw new UnsupportedOperationException();
		}

		public static class BooleanSerializer extends Serializer<UnmodifiableBoolean> {
			{
				this.setImmutable(true);
			}

			@Override
			public void write(Kryo kryo, Output output, UnmodifiableBoolean object) {
				output.writeBoolean(object == TRUE);
			}

			@Override
			public UnmodifiableBoolean read(Kryo kryo, Input input, Class<UnmodifiableBoolean> type) {
				return (UnmodifiableBoolean) BooleanNode.valueOf(input.readBoolean());
			}
		}
	}

	public final static BooleanNode TRUE = new UnmodifiableBoolean(true);

	public final static BooleanNode FALSE = new UnmodifiableBoolean(false);

	private boolean value;

	/**
	 * Initializes a BooleanNode which represents <code>false</code>. This constructor is needed for serialization and
	 * deserialization of BooleanNodes, please use BooleanNode.valueOf(boolean) to get an instance of BooleanNode.
	 */
	public BooleanNode() {
		this(false);
	}

	private BooleanNode(final boolean v) {
		this.value = v;
	}

	/**
	 * Returns the instance of BooleanNode which represents the given <code>boolean</code>.
	 * 
	 * @param b
	 *        the value for which the BooleanNode should be returned for
	 * @return the BooleanNode which represents the given value
	 */
	public static BooleanNode valueOf(final boolean b) {
		return b ? TRUE : FALSE;
	}

	/**
	 * Returns either this BooleanNode represents the value <code>true</code> or not.
	 */
	public boolean getBooleanValue() {
		return this.value;
	}

	@Override
	public BooleanNode clone() {
		return (BooleanNode) super.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.value ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BooleanNode))
			return false;

		final BooleanNode other = (BooleanNode) obj;
		if (this.value != other.value)
			return false;
		return true;
	}

	@Override
	public void copyValueFrom(final IJsonNode otherNode) {
		this.checkForSameType(otherNode);
		this.value = ((BooleanNode) otherNode).value;
	}

	@Override
	public Class<BooleanNode> getType() {
		return BooleanNode.class;
	}

	@Override
	public int compareToSameType(final IJsonNode other) {
		return (this.value ? 1 : 0) - (((BooleanNode) other).value ? 1 : 0);
	}

	@Override
	public void clear() {
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		TypeFormat.format(this.value, appendable);
	}

	/**
	 * @return
	 */
	public BooleanNode negate() {
		return this.value ? BooleanNode.FALSE : BooleanNode.TRUE;
	}
}