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

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import eu.stratosphere.pact.common.type.PactRecord;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.ArrayNodeBaseTest;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IntNode;

/**
 * @author Michael Hopstock
 */
public class LazyTailArrayNodeTest extends ArrayNodeBaseTest<LazyTailArrayNode> {

	@Override
	public void testValue() {
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected LazyTailArrayNode createDefaultInstance(int index) {
		final ArrayNode<IJsonNode> node = new ArrayNode<IJsonNode>();
		while (index-- > 0)
			node.add(new IntNode(index));
		final TailArraySchema schema = new TailArraySchema(5);
		PactRecord record = new PactRecord();
		schema.jsonToRecord(node, record);
		return (LazyTailArrayNode) schema.recordToJson(record);
	}

	@Override
	protected IJsonNode lowerNode() {
		final TailArraySchema schema = new TailArraySchema(5);
		final PactRecord record = new PactRecord();
		schema.jsonToRecord(new ArrayNode<IntNode>(IntNode.valueOf(0), IntNode.valueOf(1), IntNode.valueOf(2)), record);
		return schema.recordToJson(record);
	}

	@Override
	protected IJsonNode higherNode() {
		final TailArraySchema schema = new TailArraySchema(5);
		final PactRecord record = new PactRecord();
		schema.jsonToRecord(new ArrayNode<IntNode>(IntNode.valueOf(0), IntNode.valueOf(1), IntNode.valueOf(3)), record);
		return schema.recordToJson(record);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualCloneTest#testClone()
	 */
	@Override
	@Test
	public void testClone() throws IllegalAccessException {
		for (IArrayNode<?> original : Iterables.concat(Arrays.asList(this.first, this.second), this.more)) {
			final IArrayNode<?> clone = original.clone();
			this.testPropertyClone(ArrayList.class, Lists.newArrayList(original.iterator()),
				Lists.newArrayList(clone.iterator()));
		}
	}

	@Override
	@Test(expected = UnsupportedOperationException.class)
	public void shouldNormalizeKeys() {
		super.shouldNormalizeKeys();
	}

	@Override
	public void initArrayNode() {
		final TailArraySchema schema = new TailArraySchema(5);
		final PactRecord record = new PactRecord();
		schema.jsonToRecord(new ArrayNode<IntNode>(IntNode.valueOf(0), IntNode.valueOf(1), IntNode.valueOf(2)), record);
		this.node = (LazyTailArrayNode) schema.recordToJson(record);

	}

	@Test
	public void shouldSetNewNode() {
		this.node.set(2, IntNode.valueOf(3));
		Assert.assertEquals(IntNode.valueOf(3), this.node.get(2));
		this.node.addAll(Arrays.asList(IntNode.valueOf(4), IntNode.valueOf(5), IntNode.valueOf(6)));
		this.node.set(0, IntNode.valueOf(7));
		Assert.assertEquals(IntNode.valueOf(7), this.node.get(0));

	}
}
