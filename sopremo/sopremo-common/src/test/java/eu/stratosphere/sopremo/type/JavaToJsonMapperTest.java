package eu.stratosphere.sopremo.type;

import java.lang.annotation.RetentionPolicy;

import junit.framework.Assert;

import org.junit.Test;

public class JavaToJsonMapperTest {

	@Test
	public void shouldMapArray() {
		final Object[] array = { "field1", 1 };
		final IJsonNode node = new JavaToJsonMapper().valueToTree(array);
		Assert.assertEquals(new ArrayNode<IJsonNode>().add(new TextNode("field1")).add(new IntNode(1)), node);
	}

	@Test
	public void shouldMapNestedArray() {
		final Object[] root = new Object[2];
		final Object[] array1 = { "field1", 1 };
		final Object[] array2 = { "field2", 2 };
		root[0] = array1;
		root[1] = array2;
		final IJsonNode node = new JavaToJsonMapper().valueToTree(root);

		Assert.assertEquals(
			new ArrayNode<IJsonNode>().add(new ArrayNode<IJsonNode>().add(new TextNode("field1")).add(new IntNode(1)))
				.add(new ArrayNode<IJsonNode>().add(new TextNode("field2")).add(new IntNode(2))),
			node);
	}

	@Test
	public void shouldMapIntArray() {
		final int[] root = { 1, 2, 3 };
		final IJsonNode node = new JavaToJsonMapper().valueToTree(root);
		Assert.assertEquals(new ArrayNode<IJsonNode>().add(new IntNode(1)).add(new IntNode(2)).add(new IntNode(3)),
			node);
	}
	
	@Test
	public void shouldMapEnum() {
		final IJsonNode node = new JavaToJsonMapper().valueToTree(RetentionPolicy.RUNTIME);
		Assert.assertEquals(TextNode.valueOf(RetentionPolicy.RUNTIME.toString()), node);
	}
}
