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

import java.io.IOException;

import eu.stratosphere.api.common.typeutils.TypeSerializer;
import eu.stratosphere.core.memory.DataInputView;
import eu.stratosphere.core.memory.DataOutputView;
import eu.stratosphere.sopremo.packages.ITypeRegistry;

/**
 * Implementation of the (de)serialization and copying logic for the {@link SopremoRecord}.
 */
public class SopremoRecordSerializer extends TypeSerializer<SopremoRecord> {
	private final SopremoRecordLayout layout;

	private final ITypeRegistry typeRegistry;

	private SopremoRecord serializationRecord;

	/**
	 * Creates a new instance of the SopremoRecordSerializers. Private to prevent instantiation.
	 */
	SopremoRecordSerializer(final SopremoRecordLayout layout, final ITypeRegistry typeRegistry) {
		if (layout == null)
			throw new NullPointerException();
		this.layout = layout;
		this.typeRegistry = typeRegistry;
	}

	// --------------------------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeAccessorsV2#copy(eu.stratosphere.core.memory.
	 * DataInputViewV2, eu.stratosphere.core.memory.DataOutputViewV2)
	 */
	@Override
	public void copy(final DataInputView source, final DataOutputView target) throws IOException {
		final int numKeys = this.layout.getNumKeys();
		for (int index = 0; index < numKeys; index++)
			target.writeInt(source.readInt());
		final int size = source.readInt();
		target.writeInt(size);
		target.write(source, size);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeAccessors#copyTo(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void copyTo(final SopremoRecord from, final SopremoRecord to) {
		from.copyTo(to);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeAccessors#createCopy(java.lang.Object)
	 */
	@Override
	public SopremoRecord createCopy(final SopremoRecord from) {
		return from.copy();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeAccessors#createInstance()
	 */
	@Override
	public SopremoRecord createInstance() {
		return new SopremoRecord(this.layout, this.typeRegistry);
	}

	// --------------------------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeAccessorsV2#deserialize(java.lang.Object,
	 * eu.stratosphere.core.memory.DataInputViewV2)
	 */
	@Override
	public void deserialize(final SopremoRecord target, final DataInputView source) throws IOException {
		target.read(source);
		target.parseNode();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeAccessorsV2#getLength()
	 */
	@Override
	public int getLength() {
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeAccessorsV2#serialize(java.lang.Object,
	 * eu.stratosphere.core.memory.DataOutputViewV2)
	 */
	@Override
	public void serialize(final SopremoRecord record, final DataOutputView target) throws IOException {
		if (this.serializationRecord != record) {
			this.serializationRecord = record;
			this.serializationRecord.init(this.layout, this.typeRegistry);
		}
		record.write(target);
	}
}
