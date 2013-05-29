package eu.stratosphere.sopremo.serialization;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import eu.stratosphere.pact.common.type.PactRecord;
import eu.stratosphere.sopremo.pact.JsonNodeWrapper;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.ArrayNodeBaseTest;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IntNode;

public class LazyHeadArrayNodeTest extends ArrayNodeBaseTest<LazyHeadArrayNode> {

	private PactRecord record;

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected LazyHeadArrayNode createDefaultInstance(int index) {
		final ArrayNode<IJsonNode> node = new ArrayNode<IJsonNode>();
		while (index-- > 0)
			node.add(new IntNode(index));
		final HeadArraySchema schema = new HeadArraySchema(5);
		PactRecord record = new PactRecord();
		schema.jsonToRecord(node, record);
		return (LazyHeadArrayNode) schema.recordToJson(record);
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
	public void initArrayNode() {
		final HeadArraySchema schema = new HeadArraySchema(5);
		this.record = new PactRecord();
		schema.jsonToRecord(new ArrayNode<IntNode>(IntNode.valueOf(0), IntNode.valueOf(1), IntNode.valueOf(2)),
			this.record);
		this.node = (LazyHeadArrayNode) schema.recordToJson(this.record);
	}

	@Test
	public void shouldIncrementOthersField() {
		final IArrayNode<?> others = (IArrayNode<?>) this.record.getField(5, JsonNodeWrapper.class).getValue();

		Assert.assertEquals(0, others.size());

		this.node.addAll(new ArrayNode<IntNode>(IntNode.valueOf(3), IntNode.valueOf(4), IntNode.valueOf(5)));

		Assert.assertEquals(1, others.size());
	}

	@Override
	public void testValue() {
	}

	@Override
	protected IJsonNode lowerNode() {
		final HeadArraySchema schema = new HeadArraySchema(5);
		final PactRecord record = new PactRecord();
		schema.jsonToRecord(new ArrayNode<IntNode>(IntNode.valueOf(0), IntNode.valueOf(1), IntNode.valueOf(2)), record);
		return schema.recordToJson(record);
	}

	@Override
	protected IJsonNode higherNode() {
		final HeadArraySchema schema = new HeadArraySchema(5);
		final PactRecord record = new PactRecord();
		schema.jsonToRecord(new ArrayNode<IntNode>(IntNode.valueOf(0), IntNode.valueOf(1), IntNode.valueOf(3)), record);
		return schema.recordToJson(record);
	}

	@Override
	@Test(expected = UnsupportedOperationException.class)
	public void shouldNormalizeKeys() {
		super.shouldNormalizeKeys();
	}

}
