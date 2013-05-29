package eu.stratosphere.sopremo.pact;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.stratosphere.sopremo.EqualCloneTest;
import eu.stratosphere.sopremo.SopremoTestUtil;
import eu.stratosphere.sopremo.io.JsonGenerator;
import eu.stratosphere.sopremo.io.JsonParser;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.BooleanNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.NullNode;
import eu.stratosphere.sopremo.type.ObjectNode;
import eu.stratosphere.sopremo.type.TextNode;

public class JsonNodeWrapperTest extends EqualCloneTest<JsonNodeWrapper> {

	private static IObjectNode obj;

	private ByteArrayOutputStream byteArray;

	private DataOutputStream outStream;

	private JsonNodeWrapper wrapper;

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected JsonNodeWrapper createDefaultInstance(int index) {
		return new JsonNodeWrapper(new IntNode(index));
	}


//	@Test
//	public void shouldNormalizeKeys() {
//		int lenght = 100;
//
//		final NormalizableKey lower = new JsonNodeWrapper(TextNode.valueOf("1 lower node"));
//		final NormalizableKey higher = new JsonNodeWrapper(TextNode.valueOf("2 higher node"));
//
//		lenght = higher.getMaxNormalizedKeyLen() < lenght ? higher.getMaxNormalizedKeyLen() : lenght;
//
//		final byte[] lowerTarget = new byte[lenght];
//		final byte[] higherTarget = new byte[lenght];
//
//		lower.copyNormalizedKey(lowerTarget, 0, lenght);
//		higher.copyNormalizedKey(higherTarget, 0, lenght);
//
//		for (int i = 0; i < lenght; i++) {
//			final byte lowerByte = lowerTarget[i];
//			final byte higherByte = higherTarget[i];
//
//			if (lowerByte < higherByte)
//				break;
//
//			Assert.assertTrue(lowerByte == higherByte);
//		}
//	}
	
	@BeforeClass
	public static void setUpClass() {
		obj = new ObjectNode();
		final ArrayNode<IJsonNode> friends = new ArrayNode<IJsonNode>();

		friends.add(new ObjectNode().put("name", TextNode.valueOf("testfriend 1")).put("age", IntNode.valueOf(20))
			.put("male", BooleanNode.TRUE));
		friends.add(new ObjectNode().put("name", TextNode.valueOf("testfriend 2")).put("age", IntNode.valueOf(30))
			.put("male", BooleanNode.FALSE));
		friends.add(new ObjectNode().put("name", TextNode.valueOf("testfriend 2")).put("age", IntNode.valueOf(40))
			.put("male", NullNode.getInstance()));

		obj.put("name", TextNode.valueOf("Person 1")).put("age", IntNode.valueOf(25)).put("male", BooleanNode.TRUE)
			.put("friends", friends);
	}

	@Before
	public void setUp() {
		this.byteArray = new ByteArrayOutputStream();
		this.outStream = new DataOutputStream(this.byteArray);
		this.wrapper = new JsonNodeWrapper(obj);
	}

	// TODO mleich: re-enable tests!
//	@Test
//	public void shouldSerializeAndDeserialize() {
//		try {
//
//			final ArrayNode<IJsonNode> array = new ArrayNode<IJsonNode>();
//			final JsonParser parser = new JsonParser(new URL(
//				SopremoTestUtil.getResourcePath("SopremoTestPlan/test.json")));
//			final File file = File.createTempFile("test", "json");
//
//			while (!parser.checkEnd())
//				array.add(parser.readValueAsTree());
//
//			new JsonNodeWrapper(array).write(this.outStream);
//
//			final ByteArrayInputStream byteArrayIn = new ByteArrayInputStream(this.byteArray.toByteArray());
//			final DataInputStream inStream = new DataInputStream(byteArrayIn);
//
//			final JsonNodeWrapper wrapper = new JsonNodeWrapper();
//			wrapper.read(inStream);
//			final IJsonNode target = wrapper.getValue();
//
//			// for watching the output
//			final JsonGenerator gen = new JsonGenerator(file);
//			gen.writeStartArray();
//			gen.writeTree(target);
//			gen.writeEndArray();
//			gen.close();
//
//			Assert.assertEquals(array, target);
//		} catch (final IOException e) {
//			e.printStackTrace();
//		}
//	}

	@Test
	public void shouldNotEqualWithNonWrapperNodes() {
		Assert.assertFalse(this.wrapper.equals(IntNode.valueOf(42)));
	}

	@Test
	public void shouldNotEqualWithNull() {
		Assert.assertFalse(this.wrapper.equals(null));
	}
}
