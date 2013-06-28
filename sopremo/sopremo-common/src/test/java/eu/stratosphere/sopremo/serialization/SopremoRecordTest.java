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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import junit.framework.Assert;

import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import eu.stratosphere.sopremo.EqualCloneTest;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.JsonUtil;

/**
 * @author arv
 */
public class SopremoRecordTest extends EqualCloneTest<SopremoRecord> {
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected SopremoRecord createDefaultInstance(int index) {
		final SopremoRecord record = new SopremoRecord(SopremoRecordLayout.create(new ArrayAccess(index)));
		record.setNode(JsonUtil.createArrayNode(index));
		return record;
	}

	@Test
	public void testPrimitiveSerialization() throws IOException {
		final SopremoRecord sopremoRecord = new SopremoRecord();
		sopremoRecord.setNode(new IntNode(42));

		final SopremoRecord sopremoRecord2 = serializeAndDeserialize(sopremoRecord);
		Assert.assertEquals(sopremoRecord, sopremoRecord2);
		Assert.assertEquals(sopremoRecord.getNode(), sopremoRecord2.getNode());
		Assert.assertNotSame(sopremoRecord.getNode(), sopremoRecord2.getNode());
	}

	@Test
	public void testObjectSerialization() throws IOException {
		final SopremoRecord sopremoRecord = new SopremoRecord();
		sopremoRecord.setNode(JsonUtil.createObjectNode("a", 1, "b", 2));

		final SopremoRecord sopremoRecord2 = serializeAndDeserialize(sopremoRecord);
		Assert.assertEquals(sopremoRecord, sopremoRecord2);
		Assert.assertEquals(sopremoRecord.getNode(), sopremoRecord2.getNode());
		Assert.assertNotSame(sopremoRecord.getNode(), sopremoRecord2.getNode());
	}

	@Test
	public void testObjectKey() throws IOException {
		final SopremoRecordLayout layout = SopremoRecordLayout.create(new ObjectAccess("a"));
		final SopremoRecord sopremoRecord = new SopremoRecord(layout);
		sopremoRecord.setNode(JsonUtil.createObjectNode("a", 1, "b", 2));

		final SopremoRecord sopremoRecord2 = serializeAndDeserialize(sopremoRecord);
		Assert.assertEquals(new IntNode(1), sopremoRecord2.getKey(new ObjectAccess("a")));
		Assert.assertSame(null, sopremoRecord2.getNodeDirectly());
	}
	
	@Test
	public void testArrayKey() throws IOException {
		final SopremoRecordLayout layout = SopremoRecordLayout.create(new ArrayAccess(1));
		final SopremoRecord sopremoRecord = new SopremoRecord(layout);
		sopremoRecord.setNode(JsonUtil.createArrayNode(0, 1, 2));

		final SopremoRecord sopremoRecord2 = serializeAndDeserialize(sopremoRecord);
		Assert.assertEquals(new IntNode(1), sopremoRecord2.getKey(new ArrayAccess(1)));
		Assert.assertSame(null, sopremoRecord2.getNodeDirectly());
	}

	/**
	 * @param sopremoRecord
	 * @return
	 */
	private SopremoRecord serializeAndDeserialize(SopremoRecord sopremoRecord) throws IOException {
		Kryo kryo = new Kryo();
		kryo.setReferences(false);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output output = new Output(baos);
		kryo.writeObject(output, sopremoRecord);
		output.close();
		baos.close();

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		Input input = new Input(bais);
		SopremoRecord deserialized = kryo.readObject(input, SopremoRecord.class);
		deserialized.setLayout(sopremoRecord.getLayout());
		input.close();

		return deserialized;
	}
}
