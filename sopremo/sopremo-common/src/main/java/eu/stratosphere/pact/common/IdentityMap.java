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

package eu.stratosphere.pact.common;

import eu.stratosphere.api.common.functions.AbstractFunction;
import eu.stratosphere.api.common.functions.GenericMapper;
import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.util.Collector;

/**
 * Trivial PACT stub which emits the pairs without modifications.
 * 
 * @author Arvid Heise
 */
public class IdentityMap extends AbstractFunction implements GenericMapper<SopremoRecord, SopremoRecord> {
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.api.record.functions.MapFunction#map(eu.stratosphere.types.PactRecord,
	 * eu.stratosphere.api.record.functions.Collector)
	 */
	@Override
	public void map(final SopremoRecord record, final Collector<SopremoRecord> out) {
		out.collect(record);
	}
}
