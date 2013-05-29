/***********************************************************************************************************************
 *
 * Copyright (C) 2010-2013 by the Stratosphere project (http://stratosphere.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 **********************************************************************************************************************/
package eu.stratosphere.sopremo.serialization;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.type.AbstractObjectNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.util.AbstractIteratorOfMutables;
import eu.stratosphere.util.MutableEntry;
import eu.stratosphere.util.reflect.ReflectUtil;

/**
 * This {@link IObjectNode} supports {@link PactRecord}s more efficient by working directly with the record instead of
 * transforming it to a JsonNode. The record is handled by a {@link ObjectSchema}.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 * @author Arvid Heise
 */
public class LazyObjectNode extends AbstractObjectNode implements KryoSerializable {

	private final List<String> mappings;

	private transient final Object2IntMap<String> attributeIndex;

	private transient SopremoRecord record;

	private final int mappingSize;

	/**
	 * Initializes a LazyObjectNode with the given {@link PactRecord} and the given {@link ObjectSchema}.
	 * 
	 * @param record
	 *        the PactRecord that should be used
	 * @param schema
	 *        the ObjectSchema that should be used
	 */
	LazyObjectNode(final List<String> mappings, Object2IntMap<String> attributeIndex) {
		this.mappings = mappings;
		this.mappingSize = this.mappings.size();
		this.attributeIndex = attributeIndex;
	}

	/**
	 * Initializes a LazyObjectNode with the given {@link PactRecord} and the given {@link ObjectSchema}.
	 * 
	 * @param record
	 *        the PactRecord that should be used
	 * @param schema
	 *        the ObjectSchema that should be used
	 */
	LazyObjectNode(final List<String> mappings) {
		this(mappings, new Object2IntOpenHashMap<String>());
		for (int index = 0, size = mappings.size(); index < size; index++)
			this.attributeIndex.put(mappings.get(index), index);
		this.attributeIndex.defaultReturnValue(-1);
	}

	/**
	 * Constructor for serialization.
	 */
	LazyObjectNode() {
		this.mappings = null;
		this.mappingSize = 0;
		this.attributeIndex = new Object2IntOpenHashMap<String>();
		this.attributeIndex.defaultReturnValue(-1);
	}

	/**
	 * Returns the record.
	 * 
	 * @return the record
	 */
	SopremoRecord getRecord() {
		return this.record;
	}

	/**
	 * Sets the record to the specified value.
	 * 
	 * @param record
	 *        the record to set
	 */
	void setRecord(SopremoRecord record) {
		if (record == null)
			throw new NullPointerException("record must not be null");

		this.record = record;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.mappings.size(); i++)
			this.record.setField(i, MissingNode.getInstance());

		final IObjectNode otherField = this.getOtherFieldForUpdate();
		otherField.clear();
	}

	/**
	 * Returns the mappings.
	 * 
	 * @return the mappings
	 */
	List<String> getMappings() {
		return this.mappings;
	}

	/**
	 * Returns the attributeIndex.
	 * 
	 * @return the attributeIndex
	 */
	public int getAttributeIndex(String attribute) {
		return this.attributeIndex.getInt(attribute);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonObject#get(java.lang.String)
	 */
	@Override
	public IJsonNode get(final String fieldName) {
		final int index = this.attributeIndex.getInt(fieldName);
		if (index != -1)
			return this.record.getField(index);
		return this.getOtherField().get(fieldName);
	}

	private IObjectNode getOtherField() {
		return this.record.getField(this.mappingSize, IObjectNode.class);
	}

	private IObjectNode getOtherFieldForUpdate() {
		return this.record.getFieldForUpdate(this.mappingSize, IObjectNode.class);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonObject#iterator()
	 */
	@Override
	public Iterator<Entry<String, IJsonNode>> iterator() {
		return new AbstractIteratorOfMutables<Map.Entry<String, IJsonNode>>() {
			private final Iterator<Entry<String, IJsonNode>> otherIterator =
				LazyObjectNode.this.getOtherField().iterator();

			@SuppressWarnings("hiding")
			private final List<String> mappings = LazyObjectNode.this.mappings;

			private int mappingIndex = 0;

			private Entry<String, IJsonNode> nextOther = null;

			private MutableEntry<String, IJsonNode> nextMapping = new MutableEntry<String, IJsonNode>();

			private boolean loadedNextMappings = false;

			/*
			 * (non-Javadoc)
			 * @see eu.stratosphere.util.AbstractIterator#loadNext()
			 */
			@Override
			protected Entry<String, IJsonNode> loadNext() {
				if (this.nextOther == null && this.otherIterator.hasNext())
					this.nextOther = this.otherIterator.next();

				for (; !this.loadedNextMappings && this.mappingIndex < this.mappings.size(); this.mappingIndex++) {
					IJsonNode value = LazyObjectNode.this.record.getField(this.mappingIndex);
					if (!value.isMissing()) {
						this.nextMapping.setValue(value);
						this.nextMapping.setKey(this.mappings.get(this.mappingIndex));
						this.loadedNextMappings = true;
					}
				}

				if (this.nextOther == null) {
					// only mappings remain
					if (!this.loadedNextMappings)
						return this.noMoreElements();
					return this.nextMapping();
				} else if (!this.loadedNextMappings)
					// only other fields remain
					return this.nextOther();

				// two valid entries, return smaller key
				return this.nextOther.getKey().compareTo(this.nextMapping.getKey()) < 0 ? this.nextOther()
					: this.nextMapping();
			}

			// special return entry, because we load the next entry in our potentially mutable entries before returning
			// the current one
			private MutableEntry<String, IJsonNode> returnEntry = new MutableEntry<String, IJsonNode>();

			/*
			 * (non-Javadoc)
			 * @see eu.stratosphere.util.AbstractIteratorOfMutables#copy(java.lang.Object)
			 */
			@Override
			protected Entry<String, IJsonNode> copy(Entry<String, IJsonNode> currentValue) {
				this.returnEntry.copyFrom(currentValue);
				return this.returnEntry;
			}

			private Entry<String, IJsonNode> nextOther() {
				Entry<String, IJsonNode> value = this.nextOther;
				this.nextOther = null;
				return value;
			}

			private Entry<String, IJsonNode> nextMapping() {
				this.loadedNextMappings = false;
				return this.nextMapping;
			}
		};

	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonObject#put(java.lang.String, eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public IObjectNode put(final String fieldName, final IJsonNode value) {
		final int index = this.attributeIndex.getInt(fieldName);
		if (index != -1)
			this.record.setField(index, value);
		else
			this.getOtherFieldForUpdate().put(fieldName, value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonObject#remove(java.lang.String)
	 */
	@Override
	public void remove(final String fieldName) {
		final int index = this.attributeIndex.getInt(fieldName);
		if (index != -1)
			this.record.setField(index, MissingNode.getInstance());
		else
			this.getOtherFieldForUpdate().remove(fieldName);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#copyPropertiesFrom(eu.stratosphere.sopremo.ISopremoType)
	 */
	@Override
	public void copyPropertiesFrom(ISopremoType original) {
		this.record = (SopremoRecord) ((LazyObjectNode) original).record.clone();
	}

	@Override
	public int size() {
		// we have to manually iterate over our record to get his size
		// because there is a difference between NullNode and MissingNode
		int count = 0;
		for (int i = 0; i < this.mappingSize; i++)
			if (!this.record.isEmpty(i))
				count++;
		return count + this.getOtherField().size();
	}

	@Override
	public int getMaxNormalizedKeyLen() {
		return 0;
	}

	@Override
	public void copyNormalizedKey(final byte[] target, final int offset, final int len) {
		throw new UnsupportedOperationException("Use other ObjectNode Implementation instead");
	}

	// /*
	// * (non-Javadoc)
	// * @see com.esotericsoftware.kryo.KryoCopyable#copy(com.esotericsoftware.kryo.Kryo)
	// */
	// @Override
	// public LazyObjectNode copy(Kryo kryo) {
	// final LazyObjectNode node = new LazyObjectNode(this.mappings, this.attributeIndex);
	// node.record = this.record.copy();
	// node.record.setField(this.mappingSize, getOtherField().clone());
	// new Throwable().printStackTrace();
	// return node;
	// }

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoSerializable#write(com.esotericsoftware.kryo.Kryo,
	 * com.esotericsoftware.kryo.io.Output)
	 */
	@Override
	public void write(Kryo kryo, Output output) {
		kryo.writeObject(output, this.mappings);
		kryo.writeObjectOrNull(output, this.record, SopremoRecord.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoSerializable#read(com.esotericsoftware.kryo.Kryo,
	 * com.esotericsoftware.kryo.io.Input)
	 */
	@Override
	public void read(Kryo kryo, Input input) {
		ReflectUtil.setField(this, LazyObjectNode.class, "mappings", kryo.readObject(input, ArrayList.class));
		this.record = kryo.readObject(input, SopremoRecord.class);
		ReflectUtil.setField(this, LazyObjectNode.class, "mappingSize", this.mappings.size());
		for (int index = 0; index < this.mappings.size(); index++)
			this.attributeIndex.put(this.mappings.get(index), index);
	}
}
