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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import eu.stratosphere.core.memory.DataInputViewStream;
import eu.stratosphere.core.memory.DataOutputViewStream;
import eu.stratosphere.sopremo.EqualCloneTest;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.cache.NodeCache;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.packages.DefaultTypeRegistry;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.JsonUtil;

/**
 */
public class SopremoRecordTest extends EqualCloneTest<SopremoRecord> {
	@Test
	public void testArrayKey() throws IOException {
		final SopremoRecordLayout layout = SopremoRecordLayout.create(new ArrayAccess(1));
		final IJsonNode node = JsonUtil.createArrayNode(0, 1, 2);

		final SopremoRecord sopremoRecord2 = this.serializeAndDeserialize(node, layout);
		Assert.assertEquals(new IntNode(1),
			sopremoRecord2.getKey(layout.getKeyIndex(new ArrayAccess(1)), new NodeCache()));
		Assert.assertSame(null, sopremoRecord2.getNode());
	}

	@Test
	public void testObjectKey() throws IOException {
		final SopremoRecordLayout layout = SopremoRecordLayout.create(new ObjectAccess("a"));
		final IJsonNode node = JsonUtil.createObjectNode("a", 1, "b", 2);

		final SopremoRecord sopremoRecord2 = this.serializeAndDeserialize(node, layout);
		Assert.assertEquals(new IntNode(1),
			sopremoRecord2.getKey(layout.getKeyIndex(new ObjectAccess("a")), new NodeCache()));
		Assert.assertSame(null, sopremoRecord2.getNode());
	}

	@Test
	public void testObjectSerialization() throws IOException {
		final IJsonNode node = JsonUtil.createObjectNode("a", 1, "b", 2);

		final SopremoRecord sopremoRecord2 = this.serializeAndDeserialize(node, SopremoRecordLayout.create());
		Assert.assertEquals(node, sopremoRecord2.getOrParseNode());
		Assert.assertNotSame(node, sopremoRecord2.getOrParseNode());
	}

	@Test
	public void testPrimitiveSerialization() throws IOException {
		final IJsonNode node = new IntNode(42);

		final SopremoRecord sopremoRecord2 = this.serializeAndDeserialize(node, SopremoRecordLayout.create());
		Assert.assertEquals(node, sopremoRecord2.getOrParseNode());
		Assert.assertNotSame(node, sopremoRecord2.getOrParseNode());
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected SopremoRecord createDefaultInstance(final int index) {
		final SopremoRecord record = new SopremoRecord();
		record.setNode(JsonUtil.createArrayNode(index));
		return record;
	}

	/**
	 * @param sopremoRecord
	 * @return
	 */
	private SopremoRecord serializeAndDeserialize(final IJsonNode node, final SopremoRecordLayout layout)
			throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final DataOutputViewStream daovs = new DataOutputViewStream(baos);
		SopremoRecord serializationRecord = new SopremoRecord(layout, new DefaultTypeRegistry());
		serializationRecord.setNode(node);
		serializationRecord.write(daovs);
		daovs.close();

		final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		final DataInputViewStream daivs = new DataInputViewStream(bais);
		final SopremoRecord deserialized = new SopremoRecord(layout, new DefaultTypeRegistry());
		deserialized.read(daivs);
		daivs.close();

		return deserialized;
	}
}
