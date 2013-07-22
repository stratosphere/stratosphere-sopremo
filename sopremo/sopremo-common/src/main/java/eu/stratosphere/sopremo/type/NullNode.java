package eu.stratosphere.sopremo.type;

import java.io.IOException;

import com.esotericsoftware.kryo.DefaultSerializer;

import eu.stratosphere.sopremo.Immutable;
import eu.stratosphere.sopremo.SingletonSerializer;

/**
 * This node represents the value 'null'.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
@Immutable
@DefaultSerializer(NullNode.NullSerializer.class)
public class NullNode extends AbstractJsonNode implements IPrimitiveNode {

	private final static NullNode Instance = new NullNode();

	/**
	 * Initializes a NullNode. This constructor is needed for serialization and
	 * deserialization of NullNodes, please use NullNode.getInstance() to get the instance of NullNode.
	 */
	public NullNode() {
	}

	/**
	 * Returns the instance of NullNode.
	 * 
	 * @return the instance of NullNode
	 */
	public static NullNode getInstance() {
		return Instance;
	}

	@Override
	public void appendAsString(final Appendable sb) throws IOException {
		sb.append("null");
	}

	@Override
	public boolean equals(final Object o) {
		return o == Instance;
		// return o instanceof NullNode ? true : false;
	}

	@Override
	public Class<NullNode> getType() {
		return NullNode.class;
	}

	@Override
	public NullNode clone() {
		return this;
	}

	@Override
	public void copyValueFrom(final IJsonNode otherNode) {
		this.checkForSameType(otherNode);
	}

	@Override
	public int compareToSameType(final IJsonNode other) {
		return 0;
	}

	@Override
	public int hashCode() {
		return 37;
	}

	@Override
	public void clear() {
	}

	public static class NullSerializer extends SingletonSerializer {
		/**
		 * Initializes NullNode.NullSerializer.
		 */
		public NullSerializer() {
			super(NullNode.getInstance());
		}
	}
}
