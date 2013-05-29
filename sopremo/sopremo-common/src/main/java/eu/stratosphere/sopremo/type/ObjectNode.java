package eu.stratosphere.sopremo.type;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

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

		if (value.isMissing())
			this.children.remove(fieldName);
		else
			this.children.put(fieldName, value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonObject#get(java.lang.String)
	 */
	@Override
	public IJsonNode get(final String fieldName) {
		final IJsonNode node = this.children.get(fieldName);
		if (node != null)
			return node;
		return MissingNode.getInstance();
	}

	@Override
	public IJsonNode readResolve(final DataInput in) throws IOException {
		final int len = in.readInt();

		// performance optimization: reuse existing nodes
		Set<String> currentKeys = new HashSet<String>(this.children.keySet());
		for (int i = 0; i < len; i++) {
			final String key = in.readUTF();
			currentKeys.remove(key);
			this.children.put(key, SopremoUtil.deserializeNode(in, this.children.get(key)));
		}
		for (String currentKey : currentKeys)
			this.children.remove(currentKey);
		
		return this;
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
