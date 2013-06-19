package eu.stratosphere.sopremo.type;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

import javolution.util.FastSet;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import eu.stratosphere.sopremo.pact.SopremoUtil;

/**
 * This node represents a json object.
 */
public class ObjectNode extends AbstractObjectNode implements IObjectNode {

	/**
	 * Do not store null nodes
	 */
	private final SortedMap<String, IJsonNode> children = new TreeMap<String, IJsonNode>();

	@Override
	public int size() {
		return this.children.size();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonObject#put(java.lang.String, eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public ObjectNode put(final String fieldName, final IJsonNode value) {
		if (value == null)
			throw new NullPointerException();

		if (value == MissingNode.getInstance())
			this.children.remove(fieldName);
		else
			this.children.put(fieldName, value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonObject#get(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IJsonNode get(final String fieldName) {
		final IJsonNode node = this.children.get(fieldName);
		if (node != null)
			return node;
		return MissingNode.getInstance();
	}

	//
	// @Override
	// public IJsonNode readResolve(final DataInput in) throws IOException {
	// final int len = in.readInt();
	//
	// // performance optimization: reuse existing nodes
	// Set<String> currentKeys = new HashSet<String>(this.children.keySet());
	// for (int i = 0; i < len; i++) {
	// final String key = in.readUTF();
	// currentKeys.remove(key);
	// this.children.put(key, SopremoUtil.deserializeNode(in, this.children.get(key)));
	// }
	// for (String currentKey : currentKeys)
	// this.children.remove(currentKey);
	//
	// return this;
	// }

	public static final class ObjectSerializer extends ReusingSerializer<AbstractObjectNode> {
		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#write(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Output, java.lang.Object)
		 */
		@Override
		public void write(Kryo kryo, Output output, AbstractObjectNode object) {
			output.writeInt(object.size());

			for (final Entry<String, IJsonNode> entry : object) {
				output.writeString(entry.getKey());
				kryo.writeClassAndObject(output, entry.getValue());
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#read(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Input, java.lang.Class)
		 */
		@Override
		public AbstractObjectNode read(Kryo kryo, Input input, Class<AbstractObjectNode> type) {
			final int len = input.readInt();

			AbstractObjectNode object = new ObjectNode();
			for (int i = 0; i < len; i++) {
				final String key = input.readString();
				object.put(key, (IJsonNode) kryo.readClassAndObject(input));
			}
			return object;
		}

		private Set<String> currentKeys = new FastSet<String>();

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.type.ReusingSerializer#read(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Input, java.lang.Object, java.lang.Class)
		 */
		@Override
		public AbstractObjectNode read(Kryo kryo, Input input, AbstractObjectNode oldInstance,
				Class<AbstractObjectNode> type) {
			if (oldInstance == null)
				return this.read(kryo, input, type);

			final int len = input.readInt();
			ObjectNode object = (ObjectNode) oldInstance;

			// performance optimization: reuse existing nodes
			final SortedMap<String, IJsonNode> children = object.children;
			this.currentKeys.addAll(children.keySet());
			for (int i = 0; i < len; i++) {
				final String key = input.readString();
				this.currentKeys.remove(key);
				children.put(key, SopremoUtil.deserializeInto(kryo, input, children.get(key)));
			}
			for (String currentKey : this.currentKeys)
				children.remove(currentKey);
			this.currentKeys.clear();

			for (int i = 0; i < len; i++) {
				final String key = input.readString();
				object.put(key, (IJsonNode) kryo.readClassAndObject(input));
			}
			return object;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonObject#remove(java.lang.String)
	 */
	@Override
	public void remove(final String fieldName) {
		this.children.remove(fieldName);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.children.hashCode();
		return result;
	}

	@Override
	public Iterator<Entry<String, IJsonNode>> iterator() {
		return this.children.entrySet().iterator();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (this.getClass() != obj.getClass())
			return super.equals(obj);

		final ObjectNode other = (ObjectNode) obj;
		return this.children.equals(other.children);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonObject#getFieldNames()
	 */
	public SortedSet<String> getFieldNames() {
		// safe cast, since children is a SortedMap
		return (SortedSet<String>) this.children.keySet();
	}

	@Override
	public void clear() {
		this.children.clear();
	}
}
