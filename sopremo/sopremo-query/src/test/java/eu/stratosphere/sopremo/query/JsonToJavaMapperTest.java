package eu.stratosphere.sopremo.query;

import java.lang.annotation.RetentionPolicy;

import org.junit.Assert;
import org.junit.Test;

import eu.stratosphere.sopremo.query.JsonToJavaMapper;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.JsonUtil;
import eu.stratosphere.sopremo.type.TextNode;

public class JsonToJavaMapperTest {

	@Test
	public void shouldMapArray() {
		final IJsonNode node = JsonUtil.createArrayNode(1, 2);
		final int[] expected = { 1, 2 };
		final int[] actual = new JsonToJavaMapper().treeToValue(node, int[].class);
		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void shouldMapArrayWithCoercion() {
		final IJsonNode node = JsonUtil.createArrayNode(1, 2);
		final String[] expected = { "1", "2" };
		final String[] actual = new JsonToJavaMapper().treeToValue(node, String[].class);
		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void shouldMapMixedArray() {
		final IJsonNode node = JsonUtil.createArrayNode(1, "2");
		final int[] expected = { 1, 2 };
		final int[] actual = new JsonToJavaMapper().treeToValue(node, int[].class);
		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void shouldMapInt() {
		final IJsonNode node = JsonUtil.createValueNode(42);
		final int expected = 42;
		final int actual = new JsonToJavaMapper().treeToValue(node, int.class);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void shouldMapInteger() {
		final IJsonNode node = JsonUtil.createValueNode(42);
		final Integer expected = 42;
		final Integer actual = new JsonToJavaMapper().treeToValue(node, Integer.class);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void shouldMapEnum() {
		final IJsonNode node = TextNode.valueOf(RetentionPolicy.RUNTIME.toString());
		final RetentionPolicy actual = new JsonToJavaMapper().treeToValue(node, RetentionPolicy.class);
		Assert.assertEquals(RetentionPolicy.RUNTIME, actual);
	}

	@Test
	public void shouldSuggestEnum() {
		final IJsonNode node = TextNode.valueOf(RetentionPolicy.RUNTIME.toString() + "x");
		try {
			new JsonToJavaMapper().treeToValue(node, RetentionPolicy.class);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains(RetentionPolicy.RUNTIME.toString()));
		}

	}
}
