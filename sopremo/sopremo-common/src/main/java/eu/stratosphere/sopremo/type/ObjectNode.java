package eu.stratosphere.sopremo.type;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

import javolution.util.FastSet;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoCopyable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import eu.stratosphere.sopremo.pact.SopremoUtil;

/**
 * This node represents a json object.
 */
@DefaultSerializer(ObjectNode.ObjectSerializer.class)
public class ObjectNode extends AbstractJsonNode implements IObjectNode, KryoCopyable<ObjectNode> {

	/**
	 * Do not store null nodes
	 */
	private final SortedMap<String, IJsonNode> children = new TreeMap<String, IJsonNode>();

	@Override
	public int size() {
		return this.children.size();
	}

	@Override
	public final Class<IObjectNode> getType() {
		return IObjectNode.class;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonObject#putAll(eu.stratosphere.sopremo.type.JsonObject)
	 */
	@Override
	public IObjectNode putAll(final IObjectNode jsonNode) {
		for (final Entry<String, IJsonNode> entry : jsonNode)
			this.put(entry.getKey(), entry.getValue());
		return this;
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append("{");
		boolean first = true;
		final Iterator<Entry<String, IJsonNode>> iterator = this.iterator();
		while (iterator.hasNext()) {
			if (first)
				first = false;
			else
				appendable.append(", ");
			final Entry<String, IJsonNode> child = iterator.next();
			appendable.append(child.getKey()).append(": ");
			child.getValue().appendAsString(appendable);
		}
		appendable.append("}");
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#clone()
	 */
	@Override
	public ObjectNode clone() {
		return (ObjectNode) super.clone();
	}

	@Override
	public int compareToSameType(final IJsonNode other) {
		final IObjectNode node = (IObjectNode) other;
		final Iterator<Entry<String, IJsonNode>> entries1 = this.iterator(), entries2 = node.iterator();

		while (entries1.hasNext() && entries2.hasNext()) {
			final Entry<String, IJsonNode> entry1 = entries1.next(), entry2 = entries2.next();
			final int keyComparison = entry1.getKey().compareTo(entry2.getKey());
			if (keyComparison != 0)
				return keyComparison;

			final int valueComparison = entry1.getValue().compareTo(entry2.getValue());
			if (valueComparison != 0)
				return valueComparison;
		}

		if (!entries1.hasNext())
			return entries2.hasNext() ? -1 : 0;
		if (!entries2.hasNext())
			return 1;
		return 0;
	}

	@Override
	public void copyValueFrom(final IJsonNode otherNode) {
		this.checkForSameType(otherNode);
		final IObjectNode objectNode = (IObjectNode) otherNode;
		this.clear();

		for (final Entry<String, IJsonNode> child : objectNode)
			this.put(child.getKey(), child.getValue().clone());
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoCopyable#copy(com.esotericsoftware.kryo.Kryo)
	 */
	@Override
	public ObjectNode copy(Kryo kryo) {
		final ObjectNode node = new ObjectNode();
		node.copyValueFrom(this);
		return node;
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

	public static final class ObjectSerializer extends ReusingSerializer<ObjectNode> {
		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#write(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Output, java.lang.Object)
		 */
		@Override
		public void write(Kryo kryo, Output output, ObjectNode object) {
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
		public ObjectNode read(Kryo kryo, Input input, Class<ObjectNode> type) {
			final int len = input.readInt();

			ObjectNode object = new ObjectNode();
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
		public ObjectNode read(Kryo kryo, Input input, ObjectNode object, Class<ObjectNode> type) {
			if (object == null)
				return this.read(kryo, input, type);

			final int len = input.readInt();

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
	@Override
	public SortedSet<String> getFieldNames() {
		// safe cast, since children is a SortedMap
		return (SortedSet<String>) this.children.keySet();
	}

	@Override
	public void clear() {
		this.children.clear();
	}
}
