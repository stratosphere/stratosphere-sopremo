package eu.stratosphere.sopremo.type.typed;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.JavaToJsonMapper;
import eu.stratosphere.sopremo.type.JsonToJavaMapper;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.sopremo.type.NullNode;
import eu.stratosphere.sopremo.type.ObjectNode;

/**
 * This class is the abstract superclass for all concrete TypedObjectNodes. It
 * stores a backingObject inside, storing all the object information. It also
 * delegates most of the {@link IObjectNode}s calls to this backingObject.
 * Exceptions are: - public TypedObjectNode {@link TypedObjectNode#clone()} -
 * public IObjectNode {@link TypedObjectNode#put(String fieldName, IJsonNode value)} - public
 * IJsonNode {@link TypedObjectNode#get(String fieldName)}
 */

public abstract class TypedObjectNode implements ITypedObjectNode {
	protected IObjectNode backingObject;

	protected static final JavaToJsonMapper JavaToJsonMapperInstance = JavaToJsonMapper.INSTANCE;

	protected static final JsonToJavaMapper JsonToJavaMapperInstance = JsonToJavaMapper.INSTANCE;

	protected TypedObjectNode() {
		this.backingObject = new ObjectNode();
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.backingObject.appendAsString(appendable);

	}

	@Override
	public void clear() {
		this.backingObject.clear();
	}

	@Override
	public TypedObjectNode clone() {
		try {
			final TypedObjectNode clone = this.getClass().newInstance();
			clone.backingObject = this.backingObject.clone();
			return clone;
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int compareTo(final IJsonNode other) {
		return this.backingObject.compareTo(other);
	}

	@Override
	public int compareToSameType(final IJsonNode other) {
		return this.backingObject.compareTo(other);
	}

	@Override
	public void copyValueFrom(final IJsonNode otherNode) {
		this.backingObject.copyValueFrom(otherNode);
	}

	@Override
	public final <T extends IJsonNode> T get(final String fieldName) {
		return this.backingObject.get(fieldName);
	}

	public IObjectNode getBackingNode() {
		return this.backingObject;
	}

	@SuppressWarnings("cast")
	public final <T extends IJsonNode> T getOrNull(final String fieldName) {
		final T result = this.backingObject.get(fieldName);
		if (result == MissingNode.getInstance() || result == NullNode.getInstance())
			return null;
		return (T) result;
	}

	@Override
	public Class<IObjectNode> getType() {
		return IObjectNode.class;
	}

	public final <T extends ITypedObjectNode> T getTyped(final String fieldName, final T object) {
		final IJsonNode result = this.get(fieldName);
		if (result == MissingNode.getInstance() || result == NullNode.getInstance())
			return null;
		((TypedObjectNode) object).setBackingNode((IObjectNode) result);
		return object;
	}

	@Override
	public Iterator<Entry<String, IJsonNode>> iterator() {
		return this.backingObject.iterator();
	}

	@Override
	public IObjectNode put(final String fieldName, final IJsonNode value) {
		return this.backingObject.put(fieldName, value);
	}

	@Override
	public IObjectNode putAll(final IObjectNode jsonNode) {
		return this.backingObject.putAll(jsonNode);
	}

	public IObjectNode putOrNull(final String fieldName, final IJsonNode value) {
		return this.backingObject.put(fieldName, value == null ? NullNode.getInstance() : value);
	}

	public final void putTyped(final String fieldName, final ITypedObjectNode value) {
		this.backingObject.put(fieldName,
			value == null ? NullNode.getInstance() : ((TypedObjectNode) value).getBackingNode());
	}

	@Override
	public void remove(final String fieldName) {
		this.backingObject.remove(fieldName);
	}

	public void setBackingNode(final IObjectNode backingNode) {
		this.backingObject = backingNode;
	}

	@Override
	public int size() {
		return this.backingObject.size();
	}

	public TypedObjectNode withBackingNode(final IObjectNode backingNode) {
		this.backingObject = backingNode;
		return this;
	}

	protected final <T extends ITypedObjectNode> T createWrappingObject(final Class<T> aDesiredClass) {
		return TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(aDesiredClass);
	}
}
