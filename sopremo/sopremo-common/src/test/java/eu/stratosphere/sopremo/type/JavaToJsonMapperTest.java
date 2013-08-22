package eu.stratosphere.sopremo.type;

import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.reflect.TypeToken;

@SuppressWarnings("serial")
public class JavaToJsonMapperTest {
	private JavaToJsonMapper mapper;

	@Before
	public void setup() {
		this.mapper = new JavaToJsonMapper();
	}

	@Test
	public void shouldMapArray() {
		final ArrayNode<IJsonNode> expected =
			new ArrayNode<IJsonNode>().add(new TextNode("field1")).add(new IntNode(1));
		final Object[] input = new Object[] { "field1", 1 };
		Assert.assertEquals(expected, this.mapper.map(input));
		Assert.assertEquals(expected, this.mapper.map(input, null, CachingArrayNode.class));
		Assert.assertEquals(expected, this.mapper.map(input, null, IArrayNode.class));
		Assert.assertEquals(expected, this.mapper.map(input, null, ArrayNode.class));
		Assert.assertEquals(expected, this.mapper.map(input, null, new TypeToken<ArrayNode<IJsonNode>>() {
		}.getType()));
		Assert.assertEquals(expected, this.mapper.map(input, null, new TypeToken<IArrayNode<IJsonNode>>() {
		}.getType()));
		Assert.assertEquals(expected, this.mapper.map(input, null, new TypeToken<IArrayNode<AbstractJsonNode>>() {
		}.getType()));
		Assert.assertSame(CachingArrayNode.class, this.mapper.getDefaultMappingType(Object[].class));
	}

	@Test
	public void shouldMapNestedArray() {
		final Object[] root = { new Object[] { "field1", 1 }, new Object[] { "field2", 2 } };
		final IJsonNode node = this.mapper.map(root);

		Assert.assertEquals(
			new ArrayNode<IJsonNode>().add(new ArrayNode<IJsonNode>().add(new TextNode("field1")).add(new IntNode(1)))
				.add(new ArrayNode<IJsonNode>().add(new TextNode("field2")).add(new IntNode(2))),
			node);
	}

	@Test
	public void shouldMapIntArray() {
		final int[] root = { 1, 2, 3 };
		final IJsonNode node = this.mapper.map(root);
		Assert.assertEquals(new ArrayNode<IJsonNode>().add(new IntNode(1)).add(new IntNode(2)).add(new IntNode(3)),
			node);

	}

	@Test
	public void shouldMapEnum() {
		final RetentionPolicy value = RetentionPolicy.RUNTIME;
		Assert.assertEquals(TextNode.valueOf(value.name()), this.mapper.map(value));
		Assert.assertEquals(TextNode.valueOf(value.name()), this.mapper.map(value, null, TextNode.class));
		Assert.assertEquals(TextNode.valueOf(value.name()), this.mapper.map(value, null, IPrimitiveNode.class));
		Assert.assertEquals(TextNode.valueOf(value.name()), this.mapper.map(value, null, IJsonNode.class));
		Assert.assertEquals(TextNode.valueOf(value.name()), this.mapper.map(value, null, AbstractJsonNode.class));
		Assert.assertEquals(TextNode.valueOf(value.name()), this.mapper.map(value, null, Object.class));

		Assert.assertSame(TextNode.class, this.mapper.getDefaultMappingType(Enum.class));
	}

	@Test
	public void shouldMapString() {
		Assert.assertEquals(TextNode.valueOf("test"), this.mapper.map("test"));
		Assert.assertEquals(TextNode.valueOf("test"), this.mapper.map("test", null, TextNode.class));
		Assert.assertEquals(TextNode.valueOf("test"), this.mapper.map("test", null, IPrimitiveNode.class));
		Assert.assertEquals(TextNode.valueOf("test"), this.mapper.map("test", null, IJsonNode.class));
		Assert.assertEquals(TextNode.valueOf("test"), this.mapper.map("test", null, AbstractJsonNode.class));
		Assert.assertEquals(TextNode.valueOf("test"), this.mapper.map("test", null, Object.class));

		Assert.assertSame(TextNode.class, this.mapper.getDefaultMappingType(String.class));
	}

	@Test
	public void shouldMapStringBuilder() {
		Assert.assertEquals(TextNode.valueOf("test"), this.mapper.map(new StringBuilder("test")));
		Assert.assertEquals(TextNode.valueOf("test"), this.mapper.map(new StringBuilder("test"), null, TextNode.class));
		Assert.assertEquals(TextNode.valueOf("test"),
			this.mapper.map(new StringBuilder("test"), null, IPrimitiveNode.class));
		Assert.assertEquals(TextNode.valueOf("test"), this.mapper.map(new StringBuilder("test"), null, IJsonNode.class));
		Assert.assertEquals(TextNode.valueOf("test"),
			this.mapper.map(new StringBuilder("test"), null, AbstractJsonNode.class));
		Assert.assertEquals(TextNode.valueOf("test"), this.mapper.map(new StringBuilder("test"), null, Object.class));

		Assert.assertSame(TextNode.class, this.mapper.getDefaultMappingType(StringBuilder.class));
	}

	@Test
	public void shouldMapDouble() {
		Assert.assertEquals(DoubleNode.valueOf(1.23), this.mapper.map(1.23));
		Assert.assertEquals(DoubleNode.valueOf(1.23), this.mapper.map(1.23, null, DoubleNode.class));
		Assert.assertEquals(DoubleNode.valueOf(1.23), this.mapper.map(1.23, null, AbstractNumericNode.class));
		Assert.assertEquals(DoubleNode.valueOf(1.23), this.mapper.map(1.23, null, IPrimitiveNode.class));
		Assert.assertEquals(DoubleNode.valueOf(1.23), this.mapper.map(1.23, null, INumericNode.class));
		Assert.assertEquals(DoubleNode.valueOf(1.23), this.mapper.map(1.23, null, IJsonNode.class));
		Assert.assertEquals(DoubleNode.valueOf(1.23), this.mapper.map(1.23, null, AbstractJsonNode.class));
		Assert.assertEquals(DoubleNode.valueOf(1.23), this.mapper.map(1.23, null, Object.class));

		Assert.assertSame(DoubleNode.class, this.mapper.getDefaultMappingType(Double.TYPE));
		Assert.assertSame(DoubleNode.class, this.mapper.getDefaultMappingType(Double.class));
		Assert.assertSame(DoubleNode.class, this.mapper.getDefaultMappingType(Float.TYPE));
		Assert.assertSame(DoubleNode.class, this.mapper.getDefaultMappingType(Float.class));
	}

	@Test
	public void shouldMapInt() {
		Assert.assertEquals(IntNode.valueOf(42), this.mapper.map(42));
		Assert.assertEquals(IntNode.valueOf(42), this.mapper.map((short) 42));
		Assert.assertEquals(IntNode.valueOf(42), this.mapper.map((byte) 42));

		Assert.assertEquals(IntNode.valueOf(42), this.mapper.map(42, null, IntNode.class));
		Assert.assertEquals(IntNode.valueOf(42), this.mapper.map(42d, null, IntNode.class));
		Assert.assertEquals(IntNode.valueOf(42), this.mapper.map(42L, null, IntNode.class));

		Assert.assertEquals(IntNode.valueOf(42), this.mapper.map(42, null, INumericNode.class));
		Assert.assertEquals(IntNode.valueOf(42), this.mapper.map(42, null, AbstractNumericNode.class));
		Assert.assertEquals(IntNode.valueOf(42), this.mapper.map(42, null, IPrimitiveNode.class));
		Assert.assertEquals(IntNode.valueOf(42), this.mapper.map(42, null, IJsonNode.class));
		Assert.assertEquals(IntNode.valueOf(42), this.mapper.map(42, null, AbstractJsonNode.class));
		Assert.assertEquals(IntNode.valueOf(42), this.mapper.map(42, null, Object.class));

		Assert.assertSame(IntNode.class, this.mapper.getDefaultMappingType(Integer.TYPE));
		Assert.assertSame(IntNode.class, this.mapper.getDefaultMappingType(Integer.class));
		Assert.assertSame(IntNode.class, this.mapper.getDefaultMappingType(Byte.TYPE));
		Assert.assertSame(IntNode.class, this.mapper.getDefaultMappingType(Byte.class));
		Assert.assertSame(IntNode.class, this.mapper.getDefaultMappingType(Short.TYPE));
		Assert.assertSame(IntNode.class, this.mapper.getDefaultMappingType(Short.class));
	}

	@Test
	public void shouldMapLong() {
		Assert.assertEquals(LongNode.valueOf(42), this.mapper.map(42L));
		Assert.assertEquals(LongNode.valueOf(42), this.mapper.map(42L, null, LongNode.class));
		Assert.assertEquals(LongNode.valueOf(42), this.mapper.map(42L, null, INumericNode.class));
		Assert.assertEquals(LongNode.valueOf(42), this.mapper.map(42L, null, AbstractNumericNode.class));
		Assert.assertEquals(LongNode.valueOf(42), this.mapper.map(42L, null, IPrimitiveNode.class));
		Assert.assertEquals(LongNode.valueOf(42), this.mapper.map(42L, null, IJsonNode.class));
		Assert.assertEquals(LongNode.valueOf(42), this.mapper.map(42L, null, AbstractJsonNode.class));
		Assert.assertEquals(LongNode.valueOf(42), this.mapper.map(42L, null, Object.class));

		Assert.assertSame(LongNode.class, this.mapper.getDefaultMappingType(Long.TYPE));
		Assert.assertSame(LongNode.class, this.mapper.getDefaultMappingType(Long.class));
	}

	@Test
	public void shouldMapBoolean() {
		Assert.assertEquals(BooleanNode.valueOf(true), this.mapper.map(true));
		Assert.assertEquals(BooleanNode.valueOf(true), this.mapper.map(true, null, BooleanNode.class));
		Assert.assertEquals(BooleanNode.valueOf(true), this.mapper.map(true, null, IPrimitiveNode.class));
		Assert.assertEquals(BooleanNode.valueOf(true), this.mapper.map(true, null, IJsonNode.class));
		Assert.assertEquals(BooleanNode.valueOf(true), this.mapper.map(true, null, AbstractJsonNode.class));
		Assert.assertEquals(BooleanNode.valueOf(true), this.mapper.map(true, null, Object.class));

		Assert.assertSame(BooleanNode.class, this.mapper.getDefaultMappingType(Boolean.TYPE));
		Assert.assertSame(BooleanNode.class, this.mapper.getDefaultMappingType(Boolean.class));
	}

	@Test
	public void shouldInferSpecifically() {
		Assert.assertEquals(IntNode.valueOf(42), this.mapper.map(42, null, IJsonNode.class));
		Assert.assertEquals(DoubleNode.valueOf(42.1), this.mapper.map(42.1, null, IJsonNode.class));
	}

	@Test
	public void shouldMapObject() {
		Map<String, Object> object = new HashMap<String, Object>();
		object.put("a", 1);
		object.put("b", "test");

		ObjectNode expected = new ObjectNode();
		expected.put("a", IntNode.valueOf(1));
		expected.put("b", TextNode.valueOf("test"));
		Assert.assertEquals(expected, this.mapper.map(object));
		Assert.assertEquals(expected, this.mapper.map(object, null, ObjectNode.class));
		Assert.assertEquals(expected, this.mapper.map(object, null, IObjectNode.class));
		Assert.assertEquals(expected, this.mapper.map(object, null, IJsonNode.class));
		Assert.assertEquals(expected, this.mapper.map(object, null, AbstractJsonNode.class));
		Assert.assertEquals(expected, this.mapper.map(object, null, Object.class));

		Assert.assertSame(ObjectNode.class, this.mapper.getDefaultMappingType(Map.class));
	}

	@Test
	public void shouldNestedMapObject() {
		Map<String, Object> object = new HashMap<String, Object>();
		object.put("a", 1);
		object.put("b", "test");
		Map<String, Object> nested = new HashMap<String, Object>();
		nested.put("1", "x");
		nested.put("2", true);
		object.put("nestedObject", nested);

		final IJsonNode node = this.mapper.map(object);

		ObjectNode expected = new ObjectNode();
		expected.put("a", IntNode.valueOf(1));
		expected.put("b", TextNode.valueOf("test"));
		ObjectNode nestedObjectNode = new ObjectNode();
		nestedObjectNode.put("1", TextNode.valueOf("x"));
		nestedObjectNode.put("2", BooleanNode.valueOf(true));
		expected.put("nestedObject", nestedObjectNode);
		Assert.assertEquals(expected, node);
	}

	@Test
	public void shouldMapList() {
		List<Object> object = new ArrayList<Object>();
		object.add(1);
		object.add("test");

		final IJsonNode node = this.mapper.map(object);

		ArrayNode<IJsonNode> expected = new ArrayNode<IJsonNode>();
		expected.add(IntNode.valueOf(1));
		expected.add(TextNode.valueOf("test"));
		Assert.assertEquals(expected, node);

		Assert.assertSame(CachingArrayNode.class, this.mapper.getDefaultMappingType(List.class));
	}

	@Test
	public void shouldMapToStringWhenSpecified() {
		Assert.assertEquals(TextNode.valueOf("42"), this.mapper.map(42, null, TextNode.class));
		Assert.assertEquals(TextNode.valueOf("41"), this.mapper.map(41, null, TextNode.class));
		Assert.assertEquals(TextNode.valueOf("1.23"), this.mapper.map(new BigDecimal("1.23"), null, TextNode.class));
	}

	@Test
	public void shouldMapArrayToStringArrayWhenSpecified() {
		Assert.assertEquals(new ArrayNode<TextNode>().add(TextNode.valueOf("41")).add(TextNode.valueOf("42")),
			this.mapper.map(new int[] { 41, 42 }, null, new TypeToken<IArrayNode<TextNode>>() {
			}.getType()));
	}

	@Test
	public void shouldMapTypedList() {
		List<Object> object = new ArrayList<Object>();
		object.add(1);
		object.add("test");

		final IJsonNode node = this.mapper.map(object, null, new TypeToken<ArrayNode<TextNode>>() {
		}.getType());

		ArrayNode<TextNode> expected = new ArrayNode<TextNode>();
		expected.add(TextNode.valueOf("1"));
		expected.add(TextNode.valueOf("test"));
		Assert.assertEquals(expected, node);

		Assert.assertSame(CachingArrayNode.class, this.mapper.getDefaultMappingType(List.class));
	}

}
