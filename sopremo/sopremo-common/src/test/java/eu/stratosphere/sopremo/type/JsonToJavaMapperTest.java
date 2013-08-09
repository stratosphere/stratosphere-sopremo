package eu.stratosphere.sopremo.type;

import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.reflect.TypeToken;

@SuppressWarnings("serial")
public class JsonToJavaMapperTest {
	private JsonToJavaMapper mapper;

	@Before
	public void setup() {
		this.mapper = new JsonToJavaMapper();
	}

	@Test
	public void shouldMapArray() {
		ArrayNode<IJsonNode> input = new ArrayNode<IJsonNode>().add(new TextNode("field1")).add(new IntNode(1));
		Assert.assertArrayEquals(new Object[] { "field1", 1 }, this.mapper.<Object[]> map(input, null, Object[].class));
	}

	@Test
	public void shouldMapNestedArray() {
		ArrayNode<IJsonNode> input =
			new ArrayNode<IJsonNode>().add(new ArrayNode<IJsonNode>().add(new TextNode("field1")).add(new IntNode(1)))
				.add(new ArrayNode<IJsonNode>().add(new TextNode("field2")).add(new IntNode(2)));
		final Object[] expected = { new Object[] { "field1", 1 }, new Object[] { "field2", 2 } };
		Assert.assertArrayEquals(expected, this.mapper.map(input, null, Object[][].class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldMapNestedList() {
		ArrayNode<IJsonNode> input =
			new ArrayNode<IJsonNode>().add(new ArrayNode<IJsonNode>().add(new TextNode("field1")).add(new IntNode(1)))
				.add(new ArrayNode<IJsonNode>().add(new TextNode("field2")).add(new IntNode(2)));
		final List<Object[]> expected = Arrays.asList(new Object[] { "field1", 1 }, new Object[] { "field2", 2 });
		final Object actual = this.mapper.map(input, null, new TypeToken<List<Object[]>>() {
		}.getType());
		Assert.assertTrue(actual instanceof List);
		Assert.assertEquals(2, ((List<Object>)actual).size());
		Assert.assertArrayEquals(expected.get(0), ((List<Object[]>) actual).get(0));
		Assert.assertArrayEquals(expected.get(1), ((List<Object[]>) actual).get(1));

		final List<List<Object>> expected2 =
			Arrays.asList(Arrays.<Object> asList("field1", 1), Arrays.<Object> asList("field2", 2));
		Assert.assertEquals(expected2,
			this.mapper.map(input, null, new TypeToken<List<List<Object>>>() {
			}.getType()));
		Assert.assertEquals(expected2, this.mapper.map(input));
	}

	@Test
	public void shouldMapIntArray() {
		ArrayNode<IJsonNode> input =
			new ArrayNode<IJsonNode>().add(new IntNode(1)).add(new IntNode(2)).add(new IntNode(3));

		Assert.assertArrayEquals(new int[] { 1, 2, 3 }, this.mapper.map(input, null, int[].class));
		Assert.assertArrayEquals(new Integer[] { 1, 2, 3 }, this.mapper.map(input, null, Integer[].class));
		Assert.assertArrayEquals(new byte[] { 1, 2, 3 }, this.mapper.map(input, null, byte[].class));
		Assert.assertArrayEquals(new Byte[] { 1, 2, 3 }, this.mapper.map(input, null, Byte[].class));
		Assert.assertArrayEquals(new short[] { 1, 2, 3 }, this.mapper.map(input, null, short[].class));
		Assert.assertArrayEquals(new Short[] { 1, 2, 3 }, this.mapper.map(input, null, Short[].class));
	}

	@Test
	public void shouldMapEnum() {
		Assert.assertEquals(RetentionPolicy.RUNTIME,
			this.mapper.map(TextNode.valueOf(RetentionPolicy.RUNTIME.name()), null, RetentionPolicy.class));
	}

	@Test
	public void shouldMapString() {
		Assert.assertEquals("test", this.mapper.map(TextNode.valueOf("test"), null, String.class));
		Assert.assertEquals("test", this.mapper.map(TextNode.valueOf("test"), null, Object.class));
		Assert.assertEquals("test", this.mapper.map(TextNode.valueOf("test"), null, CharSequence.class));
		Assert.assertEquals("test", this.mapper.map(TextNode.valueOf("test")));
	}

	@Test
	public void shouldMapStringBuilder() {
		assertEquals(new StringBuilder("test"), this.mapper.map(TextNode.valueOf("test"), null, StringBuilder.class));
	}

	private void assertEquals(StringBuilder expected, Object actual) {
		if (!(actual instanceof StringBuilder))
			Assert.assertEquals(expected, actual);
		else
			Assert.assertEquals(expected.toString(), actual.toString());
	}

	@Test
	public void shouldMapDouble() {
		Assert.assertEquals(Double.valueOf(1.23), this.mapper.map(DoubleNode.valueOf(1.23)));
		Assert.assertEquals(Double.valueOf(1.23), this.mapper.map(DoubleNode.valueOf(1.23), null, Number.class));
		Assert.assertEquals(Double.valueOf(1.23), this.mapper.map(DoubleNode.valueOf(1.23), null, Object.class));
		Assert.assertEquals(Double.valueOf(1.23), this.mapper.map(DoubleNode.valueOf(1.23), null, Double.class));
		Assert.assertEquals(Double.valueOf(1.23), this.mapper.map(DoubleNode.valueOf(1.23), null, Double.TYPE));
		Assert.assertEquals(Float.valueOf(1.23f), this.mapper.map(DoubleNode.valueOf(1.23), null, Float.class));
		Assert.assertEquals(Float.valueOf(1.23f), this.mapper.map(DoubleNode.valueOf(1.23), null, Float.TYPE));
	}

	@Test
	public void shouldMapInt() {
		Assert.assertEquals(Integer.valueOf(42), this.mapper.map(IntNode.valueOf(42)));
		Assert.assertEquals(Integer.valueOf(42), this.mapper.map(IntNode.valueOf(42), null, Number.class));
		Assert.assertEquals(Integer.valueOf(42), this.mapper.map(IntNode.valueOf(42), null, Object.class));
		Assert.assertEquals(Integer.valueOf(42), this.mapper.map(IntNode.valueOf(42), null, Integer.TYPE));
		Assert.assertEquals(Integer.valueOf(42), this.mapper.map(IntNode.valueOf(42), null, Integer.class));
		Assert.assertEquals(Byte.valueOf((byte) 42), this.mapper.map(IntNode.valueOf(42), null, Byte.TYPE));
		Assert.assertEquals(Byte.valueOf((byte) 42), this.mapper.map(IntNode.valueOf(42), null, Byte.class));
		Assert.assertEquals(Short.valueOf((short) 42), this.mapper.map(IntNode.valueOf(42), null, Short.TYPE));
		Assert.assertEquals(Short.valueOf((short) 42), this.mapper.map(IntNode.valueOf(42), null, Short.class));
	}

	@Test
	public void shouldMapLong() {
		Assert.assertEquals(Long.valueOf(42), this.mapper.map(LongNode.valueOf(42), null, Number.class));
		Assert.assertEquals(Long.valueOf(42), this.mapper.map(LongNode.valueOf(42), null, Object.class));
		Assert.assertEquals(Long.valueOf(42), this.mapper.map(LongNode.valueOf(42), null, Long.TYPE));
		Assert.assertEquals(Long.valueOf(42), this.mapper.map(LongNode.valueOf(42), null, Long.class));
		Assert.assertEquals(Long.valueOf(42), this.mapper.map(LongNode.valueOf(42)));
	}

	@Test
	public void shouldMapBoolean() {
		Assert.assertEquals(false, this.mapper.map(BooleanNode.FALSE, null, Object.class));
		Assert.assertEquals(true, this.mapper.map(BooleanNode.TRUE, null, Boolean.TYPE));
		Assert.assertEquals(false, this.mapper.map(BooleanNode.FALSE, null, Boolean.class));
		Assert.assertEquals(true, this.mapper.map(BooleanNode.TRUE));
	}

	@Test
	public void shouldMapObject() {
		ObjectNode object = new ObjectNode();
		object.put("a", IntNode.valueOf(1));
		object.put("b", TextNode.valueOf("test"));

		Map<String, Object> expected = new HashMap<String, Object>();
		expected.put("a", 1);
		expected.put("b", "test");

		Assert.assertEquals(expected, this.mapper.map(object, null, Map.class));
		Assert.assertEquals(expected, this.mapper.map(object, null, HashMap.class));
		Assert.assertSame(HashMap.class, this.mapper.map(object, null, HashMap.class).getClass());
		Assert.assertEquals(expected, this.mapper.map(object));
	}

	@Test
	public void shouldNestedMapObject() {
		ObjectNode input = new ObjectNode();
		input.put("a", IntNode.valueOf(1));
		input.put("b", TextNode.valueOf("test"));
		ObjectNode nestedObjectNode = new ObjectNode();
		nestedObjectNode.put("1", TextNode.valueOf("x"));
		nestedObjectNode.put("2", BooleanNode.valueOf(true));
		input.put("nestedObject", nestedObjectNode);

		Map<String, Object> expected = new HashMap<String, Object>();
		expected.put("a", 1);
		expected.put("b", "test");
		Map<String, Object> nested = new HashMap<String, Object>();
		nested.put("1", "x");
		nested.put("2", true);
		expected.put("nestedObject", nested);

		Assert.assertEquals(expected, this.mapper.map(input, null, Map.class));
		Assert.assertEquals(expected, this.mapper.map(input));
	}

	@Test
	public void shouldMapList() {
		ArrayNode<IJsonNode> input = new ArrayNode<IJsonNode>();
		input.add(IntNode.valueOf(1));
		input.add(TextNode.valueOf("test"));

		List<Object> expected = new ArrayList<Object>();
		expected.add(1);
		expected.add("test");

		Assert.assertEquals(expected, this.mapper.map(input, null, List.class));
		Assert.assertEquals(expected, this.mapper.map(input));
	}

	@Test
	public void shouldMapToStringWhenSpecified() {
		Assert.assertEquals("42", this.mapper.map(IntNode.valueOf(42), null, String.class));
		Assert.assertEquals("41", this.mapper.map(IntNode.valueOf(41), null, String.class));
		Assert.assertEquals("1.23", this.mapper.map(new DecimalNode("1.23"), null, String.class));
	}

	@Test
	public void shouldMapTypedList() {
		ArrayNode<IJsonNode> input = new ArrayNode<IJsonNode>();
		input.add(IntNode.valueOf(1));
		input.add(TextNode.valueOf("test"));

		List<String> expected = new ArrayList<String>();
		expected.add("1");
		expected.add("test");

		final Object actual = this.mapper.map(input, null, new TypeToken<List<String>>() {
		}.getType());
		Assert.assertEquals(expected, actual);
	}

}
