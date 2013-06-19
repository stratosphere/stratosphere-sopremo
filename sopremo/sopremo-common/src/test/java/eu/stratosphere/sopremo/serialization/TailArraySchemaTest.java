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

import junit.framework.Assert;

import org.junit.Test;

import eu.stratosphere.pact.common.type.PactRecord;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.sopremo.util.PactRecordEqualer;

/**
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
public class TailArraySchemaTest {

	@Test
	public void shouldConvertFromJsonToRecord() {
		final TailArraySchema schema = new TailArraySchema(2);
		final IArrayNode<IJsonNode> array = new ArrayNode<IJsonNode>();
		array.add(IntNode.valueOf(1));
		final PactRecord result = new PactRecord();
		schema.jsonToRecord(array, result);

		final PactRecord expected = new PactRecord(3);
		expected.setField(2, new JsonNodeWrapper(IntNode.valueOf(1)));
		expected.setField(1, new JsonNodeWrapper(MissingNode.getInstance()));
		expected.setField(0, new JsonNodeWrapper(new ArrayNode<IJsonNode>()));

		Assert.assertTrue(PactRecordEqualer.recordsEqual(expected, result, schema.getPactSchema()));
	}

	@Test
	public void shouldConvertFromJsonToRecordWithOthers() {
		final TailArraySchema schema = new TailArraySchema(2);
		final IArrayNode<IJsonNode> array =
			new ArrayNode<IJsonNode>(IntNode.valueOf(1), IntNode.valueOf(2), IntNode.valueOf(3), IntNode.valueOf(4),
				IntNode.valueOf(5));
		final PactRecord result = new PactRecord();
		schema.jsonToRecord(array, result);

		final PactRecord expected = new PactRecord(3);
		expected.setField(1, new JsonNodeWrapper(IntNode.valueOf(4)));
		expected.setField(2, new JsonNodeWrapper(IntNode.valueOf(5)));
		// others field
		expected.setField(0,
			new JsonNodeWrapper(new ArrayNode<IntNode>(IntNode.valueOf(1), IntNode.valueOf(2), IntNode.valueOf(3))));

		Assert.assertTrue(PactRecordEqualer.recordsEqual(expected, result, schema.getPactSchema()));
	}

	@Test
	public void shouldConvertFromJsonToRecordWithoutOthers() {
		final TailArraySchema schema = new TailArraySchema(2);
		final IArrayNode<IJsonNode> array = new ArrayNode<IJsonNode>(IntNode.valueOf(1), IntNode.valueOf(2));
		final PactRecord result = new PactRecord();
		schema.jsonToRecord(array, result);

		final PactRecord expected = new PactRecord(3);
		expected.setField(1, new JsonNodeWrapper(IntNode.valueOf(1)));
		expected.setField(2, new JsonNodeWrapper(IntNode.valueOf(2)));
		// others field
		expected.setField(0, new JsonNodeWrapper(new ArrayNode<IJsonNode>()));

		Assert.assertTrue(PactRecordEqualer.recordsEqual(expected, result, schema.getPactSchema()));
	}

	@Test
	public void shouldConvertFromRecordToJson() {
		final PactRecord record = new PactRecord();
		final TailArraySchema schema = new TailArraySchema(4);

		record.setField(0, new JsonNodeWrapper(new ArrayNode<IntNode>(IntNode.valueOf(0))));
		record.setField(1, new JsonNodeWrapper(IntNode.valueOf(1)));
		record.setField(2, new JsonNodeWrapper(IntNode.valueOf(2)));
		record.setField(3, new JsonNodeWrapper(IntNode.valueOf(3)));
		record.setField(4, new JsonNodeWrapper(IntNode.valueOf(4)));

		final IArrayNode<IJsonNode> expected =
			new ArrayNode<IJsonNode>(IntNode.valueOf(0), IntNode.valueOf(1), IntNode.valueOf(2),
				IntNode.valueOf(3), IntNode.valueOf(4));
		final IJsonNode result = schema.recordToJson(record);

		Assert.assertEquals(expected, result);
	}

	@Test
	public void shouldKeepIdentityOnConversion() {
		final PactRecord record = new PactRecord(6);
		final TailArraySchema schema = new TailArraySchema(5);

		record.setField(5, new JsonNodeWrapper(IntNode.valueOf(0)));
		record.setField(4, new JsonNodeWrapper(IntNode.valueOf(1)));
		record.setField(0, new JsonNodeWrapper(new ArrayNode<IJsonNode>()));

		final IJsonNode node = schema.recordToJson(record);
		final PactRecord result = new PactRecord();
		schema.jsonToRecord(node, result);

		Assert.assertTrue(PactRecordEqualer.recordsEqual(record, result, schema.getPactSchema()));
	}
}
