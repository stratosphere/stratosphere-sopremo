/***********************************************************************************************************************
 *
 * Copyright (C) 2010 by the Stratosphere project (http://stratosphere.eu)
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

import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoCopyable;
import com.esotericsoftware.kryo.Serializer;

import eu.stratosphere.pact.common.type.PactRecord;
import eu.stratosphere.pact.common.type.Value;
import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.pact.JsonNodeWrapper;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.MissingNode;

/**
 * @author Arvid Heise
 */
public final class SopremoRecord extends AbstractSopremoType implements ISopremoType, KryoCopyable<SopremoRecord> {
	private final JsonNodeWrapper[] slots;

	private final Class<? extends Value>[] pactSchema;

	private final BitSet changedFields = new BitSet();

	private PactRecord record;

	/**
	 * Initializes SopremoRecord.
	 */
	@SuppressWarnings("unchecked")
	public SopremoRecord(int numSlots) {
		this.pactSchema = new Class[numSlots];
		Arrays.fill(this.pactSchema, JsonNodeWrapper.class);
		this.slots = new JsonNodeWrapper[numSlots];
		for (int index = 0; index < numSlots; index++)
			this.slots[index] = new JsonNodeWrapper();
	}

	/**
	 * Initializes SopremoRecord.
	 */
	SopremoRecord() {
		this.pactSchema = null;
		this.slots = null;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		appendable.append("[");
		for (int index = 0; index < this.record.getNumFields(); index++) {
			if (index != 0)
				appendable.append(", ");
			this.getField(index).appendAsString(appendable);
		}
		appendable.append("]");
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#copyPropertiesFrom(eu.stratosphere.sopremo.ISopremoType)
	 */
	@Override
	public void copyPropertiesFrom(ISopremoType original) {
		if (this == original)
			return;

		super.copyPropertiesFrom(original);
		final SopremoRecord otherRecord = (SopremoRecord) original;
		if (this.record == null) {
			if (otherRecord.record == null)
				return;
			this.record = new PactRecord();
		}
		otherRecord.record.copyTo(this.record);
	}

	/**
	 * @param index
	 * @return
	 */
	public IJsonNode getField(int index) {
		return this.record.isNull(index) ? MissingNode.getInstance()
			: this.record.getField(index, JsonNodeWrapper.class).getValue();
	}

	@SuppressWarnings("unchecked")
	public final <T extends IJsonNode> T getField(int index, @SuppressWarnings("unused") Class<T> expectedClass) {
		return (T) this.getField(index);
	}

	/**
	 * @param index
	 * @return
	 */
	public IJsonNode getFieldForUpdate(int index) {
		final IJsonNode field = this.getField(index);
		this.setField(index, field);
		return field;
	}

	@SuppressWarnings("unchecked")
	public final <T extends IJsonNode> T getFieldForUpdate(int index, @SuppressWarnings("unused") Class<T> expectedClass) {
		// this.changedFields.set(index);
		// return (T) getField(index);
		final IJsonNode field = this.getField(index);
		this.setField(index, field);
		return (T) field;
	}

	public void setFieldChanged(int index) {
		this.changedFields.set(index);
	}

	/**
	 * Returns the pactSchema.
	 * 
	 * @return the pactSchema
	 */
	public Class<? extends Value>[] getPactSchema() {
		return this.pactSchema;
	}

	/**
	 * Returns the record.
	 * 
	 * @return the record
	 */
	public PactRecord getRecord() {
		return this.record;
	}

	public boolean isEmpty(int index) {
		return this.getField(index).isMissing();
	}

	public void setField(int index, IJsonNode node) {
		this.record.setField(index, this.wrap(index, node));
	}
	
	public void setEmpty(int index) {
		this.record.setNull(index);
	}

	/**
	 * Sets the record to the specified value.
	 * 
	 * @param record
	 *        the record to set
	 */
	public void setRecord(PactRecord record) {
		if (record == null)
			throw new NullPointerException("record must not be null");

		this.record = record;
		this.changedFields.clear();
	}

	/**
	 * @param startIndex
	 * @param endIndex
	 *        exclusive
	 * @param count
	 */
	public void shiftRight(int startIndex, int endIndex, int count) {
		for (int oldIndex = endIndex - count - 1; oldIndex >= startIndex; oldIndex--) {
			final int newIndex = oldIndex + count;
			this.record.setField(newIndex,
				this.wrap(newIndex, this.unwrap(this.record.getField(oldIndex, JsonNodeWrapper.class))));
		}
	}

	/**
	 * @param startIndex
	 * @param endIndex
	 *        exclusive
	 * @param count
	 */
	public void shiftLeft(int startIndex, int endIndex, int count) {
		for (int oldIndex = startIndex + count; oldIndex < endIndex; oldIndex++) {
			final int newIndex = oldIndex - count;
			this.record.setField(newIndex,
				this.wrap(newIndex, this.unwrap(this.record.getField(oldIndex, JsonNodeWrapper.class))));
		}
	}

	public final void addField(IJsonNode value) {
		final int index = this.record.getNumFields();
		this.record.addField(this.wrap(index, value));
	}

	protected JsonNodeWrapper wrap(final int index, IJsonNode value) {
		// if (value.isMissing())
		// return null;
		this.slots[index].setValue(value);
		return this.slots[index];
	}

	protected IJsonNode unwrap(JsonNodeWrapper wrapper) {
		// if (wrapper == null)
		// return MissingNode.getInstance();
		return wrapper.getValue();
	}

	/**
	 * 
	 */
	public void updateChangedFields() {
		// let the PactRecord know, which internal fields have been changed
		for (int i = this.changedFields.nextSetBit(0); i >= 0; i = this.changedFields.nextSetBit(i + 1))
			this.record.setField(i, this.record.getField(i, (Class<Value>) null));
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoCopyable#copy(com.esotericsoftware.kryo.Kryo)
	 */
	@Override
	public SopremoRecord copy(Kryo kryo) {
		@SuppressWarnings("unchecked")
		final Serializer<SopremoRecord> serializer = kryo.getDefaultSerializer(SopremoRecord.class);
		final SopremoRecord copy = serializer.copy(kryo, this);
		// workaround for the shallow copy in ObjectArraySerializer
		for (int index = 0; index < this.slots.length; index++)
			this.slots[index] = this.slots[index].copy();
		return copy;
	}
}
