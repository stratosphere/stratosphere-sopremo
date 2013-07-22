package eu.stratosphere.sopremo.type;

import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;

public class ArrayNodeTest extends ArrayNodeBaseTest<ArrayNode<IJsonNode>> {

	@Override
	public void initArrayNode() {
		this.node = new ArrayNode<IJsonNode>();
		final int numberOfNodes = 10;

		for (int i = 0; i < numberOfNodes; i++)
			this.node.add(i, IntNode.valueOf(i));
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected ArrayNode<IJsonNode> createDefaultInstance(int index) {
		final ArrayNode<IJsonNode> node = new ArrayNode<IJsonNode>();
		while (index-- > 0)
			node.add(new IntNode(index));
		return node;
	}

	@Test
	public void shouldReturnCorrectSubarray() {
		final int numberOfNodesInSubarray = 5;
		final int startIndex = 3;
		final IArrayNode<IJsonNode> result = new ArrayNode<IJsonNode>();

		for (int i = 0; i < numberOfNodesInSubarray; i++)
			result.add(i, IntNode.valueOf(startIndex + i));

		Assert.assertEquals(result, this.node.subArray(startIndex, startIndex + numberOfNodesInSubarray));
	}

	@Test
	public void shouldCreateNewArrayNodeFromIterator() {
		final Iterator<IJsonNode> it = this.node.iterator();
		final ArrayNode<IJsonNode> newArray = ArrayNode.valueOf(it);

		Assert.assertEquals(this.node, newArray);
	}
}
