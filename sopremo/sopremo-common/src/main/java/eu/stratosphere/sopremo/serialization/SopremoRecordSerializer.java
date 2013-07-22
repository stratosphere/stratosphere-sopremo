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

import eu.stratosphere.nephele.services.memorymanager.DataInputView;
import eu.stratosphere.nephele.services.memorymanager.DataOutputView;
import eu.stratosphere.pact.generic.types.TypeSerializer;

/**
 * Implementation of the (de)serialization and copying logic for the {@link SopremoRecord}.
 */
public final class SopremoRecordSerializer extends TypeSerializer<SopremoRecord> {
	private final SopremoRecordLayout layout;

	/**
	 * Creates a new instance of the SopremoRecordSerializers. Private to prevent instantiation.
	 */
	SopremoRecordSerializer(SopremoRecordLayout layout) {
		this.layout = layout;
	}

	// --------------------------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeAccessors#createInstance()
	 */
	@Override
	public SopremoRecord createInstance() {
		return new SopremoRecord(this.layout);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeAccessors#createCopy(java.lang.Object)
	 */
	@Override
	public SopremoRecord createCopy(SopremoRecord from) {
		return from.copy();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeAccessors#copyTo(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void copyTo(SopremoRecord from, SopremoRecord to) {
		from.copyTo(to);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeAccessorsV2#getLength()
	 */
	@Override
	public int getLength() {
		return -1;
	}

	// --------------------------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeAccessorsV2#serialize(java.lang.Object,
	 * eu.stratosphere.nephele.services.memorymanager.DataOutputViewV2)
	 */
	@Override
	public void serialize(SopremoRecord record, DataOutputView target) throws IOException {
		record.write(target);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeAccessorsV2#deserialize(java.lang.Object,
	 * eu.stratosphere.nephele.services.memorymanager.DataInputViewV2)
	 */
	@Override
	public void deserialize(SopremoRecord target, DataInputView source) throws IOException {
		target.read(source);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeAccessorsV2#copy(eu.stratosphere.nephele.services.memorymanager.
	 * DataInputViewV2, eu.stratosphere.nephele.services.memorymanager.DataOutputViewV2)
	 */
	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		int numKeys = this.layout.getNumKeys();
		for (int index = 0; index < numKeys; index++)
			target.writeInt(source.readInt());
		int size = source.readInt();
		target.writeInt(size);
		target.write(source, size);
	}
}
