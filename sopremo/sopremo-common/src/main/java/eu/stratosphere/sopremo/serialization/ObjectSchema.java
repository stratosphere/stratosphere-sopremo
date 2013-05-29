package eu.stratosphere.sopremo.serialization;

import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoCopyable;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.ObjectNode;
import eu.stratosphere.util.CollectionUtil;
import eu.stratosphere.util.reflect.ReflectUtil;

/**
 * This {@link Schema} handles ObjectNodes and provides the functionality to save fields explicitly in the resulting
 * PactRecords.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 * @author Arvid Heise
 */
public class ObjectSchema extends AbstractSchema implements KryoSerializable, KryoCopyable<ObjectSchema> {

	private final LazyObjectNode node;

	public ObjectSchema(final Collection<ObjectAccess> accesses) {
		super(accesses.size() + 1, CollectionUtil.setRangeFrom(0, accesses.size()));

		List<String> mappings = new ArrayList<String>();
		for (final ObjectAccess access : accesses)
			mappings.add(access.getField());
		Collections.sort(mappings);
		this.node = new LazyObjectNode(mappings);
	}

	public ObjectSchema(final String... fieldNames) {
		super(fieldNames.length + 1, CollectionUtil.setRangeFrom(0, fieldNames.length));

		List<String> mappings = new ArrayList<String>();
		for (final String fieldName : fieldNames)
			mappings.add(fieldName);
		Collections.sort(mappings);
		this.node = new LazyObjectNode(mappings);
	}

	ObjectSchema(List<String> mappings) {
		super(mappings.size() + 1, CollectionUtil.setRangeFrom(0, mappings.size()));

		this.node = new LazyObjectNode(mappings);
	}

	/**
	 * Initializes ObjectSchema.
	 */
	public ObjectSchema() {
		this(new String[0]);
	}

	/**
	 * Returns the node.
	 * 
	 * @return the node
	 */
	LazyObjectNode getNode() {
		return this.node;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final ObjectSchema other = (ObjectSchema) obj;
		return this.node.getMappings().equals(other.node.getMappings());
	}

	public List<String> getMappings() {
		return this.node.getMappings();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.node.getMappings().hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.serialization.Schema#indicesOf(eu.stratosphere.sopremo.expressions.EvaluationExpression)
	 */
	@Override
	public IntSet indicesOf(final EvaluationExpression expression) {
		final ObjectAccess objectAccess = (ObjectAccess) expression;
		final int index = this.node.getAttributeIndex(objectAccess.getField());
		if (index == -1)
			throw new IllegalArgumentException("Field not found " + objectAccess.getField());
		return IntSets.singleton(index);
	}

	private final transient IObjectNode others = new ObjectNode();

	@Override
	public void jsonToRecord(final IJsonNode value, SopremoRecord target) {
		if (value instanceof LazyObjectNode) {
			target.copyPropertiesFrom(((LazyObjectNode) value).getRecord());
			return;
		}

		// traverse the mapping and fill them into the record
		final IObjectNode object = (IObjectNode) value;
		List<String> mappings = this.node.getMappings();
		for (int i = 0; i < mappings.size(); i++)
			target.addField(object.get(mappings.get(i)));

		this.others.clear();
		// each other entry comes into the last record field
		for (final Entry<String, IJsonNode> entry : object)
			if (this.node.getAttributeIndex(entry.getKey()) == -1)
				this.others.put(entry.getKey(), entry.getValue());
		target.addField(this.others);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.serialization.AbstractSchema#recordToJson(eu.stratosphere.sopremo.serialization.SopremoRecord
	 * )
	 */
	@Override
	public LazyObjectNode recordToJson(SopremoRecord record) {
		this.node.setRecord(record);
		return this.node;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder("ObjectSchema [");
		List<String> mappings = this.node.getMappings();
		for (int index = 0; index < mappings.size(); index++)
			builder.append(mappings.get(index)).append(", ");
		builder.append("<other>]");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoCopyable#copy(com.esotericsoftware.kryo.Kryo)
	 */
	@Override
	public ObjectSchema copy(Kryo kryo) {
		return new ObjectSchema(this.node.getMappings());
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoSerializable#write(com.esotericsoftware.kryo.Kryo,
	 * com.esotericsoftware.kryo.io.Output)
	 */
	@Override
	public void write(Kryo kryo, Output output) {
		kryo.writeObject(output, this.node.getMappings().toArray(new String[0]));
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoSerializable#read(com.esotericsoftware.kryo.Kryo,
	 * com.esotericsoftware.kryo.io.Input)
	 */
	@Override
	public void read(Kryo kryo, Input input) {
		final ArrayList<String> mappings = new ArrayList<String>(Arrays.asList(kryo.readObject(input, String[].class)));
		ReflectUtil.setField(this, ObjectSchema.class, "node", new LazyObjectNode(mappings));
		this.kryoInit(mappings.size() + 1, CollectionUtil.setRangeFrom(0, mappings.size()));
	}
}
