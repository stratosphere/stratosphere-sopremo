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

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.generic.types.TypeSerializer;
import eu.stratosphere.pact.generic.types.TypeSerializerFactory;
import eu.stratosphere.sopremo.pact.SopremoUtil;

/**
 * A factory that create a serializer for the {@link SopremoRecord} data type.
 */
public class SopremoRecordSerializerFactory implements TypeSerializerFactory<SopremoRecord> {

	private SopremoRecordLayout layout;

	// --------------------------------------------------------------------------------------------

	/**
	 * Initializes SopremoRecordSerializerFactory.
	 */
	public SopremoRecordSerializerFactory() {
	}

	public SopremoRecordSerializerFactory(SopremoRecordLayout layout) {
		this.layout = layout;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.common.generic.types.TypeSerializerFactory#getSerializer()
	 */
	@Override
	public TypeSerializer<SopremoRecord> getSerializer() {
		return new SopremoRecordSerializer(this.layout);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.layout.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SopremoRecordSerializerFactory other = (SopremoRecordSerializerFactory) obj;
		return this.layout.equals(other.layout);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.common.generic.types.TypeSerializerFactory#getDataType()
	 */
	@Override
	public Class<SopremoRecord> getDataType() {
		return SopremoRecord.class;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeSerializerFactory#writeParametersToConfig(eu.stratosphere.nephele.
	 * configuration.Configuration)
	 */
	@Override
	public void writeParametersToConfig(Configuration config) {
		SopremoUtil.setObject(config, SopremoRecordLayout.LAYOUT_KEY, this.layout);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeSerializerFactory#readParametersFromConfig(eu.stratosphere.nephele.
	 * configuration.Configuration, java.lang.ClassLoader)
	 */
	@Override
	public void readParametersFromConfig(Configuration config, ClassLoader cl) throws ClassNotFoundException {
		this.layout = SopremoUtil.getObject(config, SopremoRecordLayout.LAYOUT_KEY, null);
	}
}
