package eu.stratosphere.sopremo.type;

import java.io.IOException;

import com.esotericsoftware.kryo.DefaultSerializer;

import eu.stratosphere.sopremo.Immutable;
import eu.stratosphere.sopremo.SingletonSerializer;

/**
 * This node represents a missing value.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
@Immutable
@DefaultSerializer(MissingNode.MissingSerializer.class)
public class MissingNode extends AbstractJsonNode implements IPrimitiveNode {

	private final static MissingNode Instance = new MissingNode();

	/**
	 * Initializes a MissingNode. This constructor is needed for serialization and
	 * deserialization of MissingNodes, please use MissingNode.getInstance() to get the instance of MissingNode.
	 */
	public MissingNode() {
	}

	/**
	 * Returns the instance of MissingNode.
	 * 
	 * @return the instance of MissingNode
	 */
	public static MissingNode getInstance() {
		return Instance;
	}

	@Override
	public void appendAsString(final Appendable sb) throws IOException {
		sb.append("<missing>");
	}

	@Override
	public boolean equals(final Object o) {
		return this == o;
	}

	@Override
	public Class<MissingNode> getType() {
		return MissingNode.class;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#copy()
	 */
	@Override
	public MissingNode clone() {
		return this;
	}

	@Override
	public int compareToSameType(final IJsonNode other) {
		return 0;
	}

	@Override
	public void copyValueFrom(final IJsonNode otherNode) {
		this.checkForSameType(otherNode);
	}

	@Override
	public int hashCode() {
		return 42;
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("MissingNode");
	}

	public static class MissingSerializer extends SingletonSerializer {
		/**
		 * Initializes MissingNode.MissingSerializer.
		 */
		public MissingSerializer() {
			super(MissingNode.getInstance());
		}
	}
}
