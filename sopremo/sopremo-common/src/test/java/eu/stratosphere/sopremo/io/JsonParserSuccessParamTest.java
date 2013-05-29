package eu.stratosphere.sopremo.io;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.BooleanNode;
import eu.stratosphere.sopremo.type.DecimalNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.LongNode;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.sopremo.type.NullNode;
import eu.stratosphere.sopremo.type.ObjectNode;
import eu.stratosphere.sopremo.type.TextNode;

@RunWith(Parameterized.class)
public class JsonParserSuccessParamTest {

	private JsonParser parser;

	private String input;

	private IJsonNode[] expectedResults;

	int counter;

	public JsonParserSuccessParamTest(final String input, final IJsonNode[] expectedResults, int expectedCounter) {
		this.input = input;
		this.expectedResults = expectedResults;
		this.counter = expectedCounter;
	}

	@Before
	public void setUp() {
		this.parser = new JsonParser(this.input);
	}

	@Test
	public void shouldParseCorrectly() throws JsonParseException {
		for (IJsonNode expectedResult : this.expectedResults) {
			IJsonNode result = this.parser.readValueAsTree();
			Assert.assertEquals(expectedResult, result);
		}
	}

	@Test
	public void shouldParseCorrectNumberOfChars() throws JsonParseException {
		for (int i = 0; i < this.expectedResults.length; i++)
			this.parser.readValueAsTree();

		Assert.assertEquals(this.counter, this.parser.getNumberOfParsedChars());
	}

	@Parameters
	public static List<Object[]> combinations() {
		return Arrays.asList(new Object[][] {
			/* [0] */{ "[null]", create(new ArrayNode<NullNode>().add(NullNode.getInstance())), 6 },
			/* [1] */{ "null", create(NullNode.getInstance()), 4 },
			/* [2] */{ "[null,null]",
				create(new ArrayNode<NullNode>().add(NullNode.getInstance()).add(NullNode.getInstance())),
				11 },
			/* [3] */{ "[true]", create(new ArrayNode<BooleanNode>().add(BooleanNode.TRUE)), 6 },
			/* [4] */{ "[false]", create(new ArrayNode<BooleanNode>().add(BooleanNode.FALSE)), 7 },
			/* [5] */{ "\"42shadh34634\"", create(TextNode.valueOf("42shadh34634")), 14 },
			/* [6] */{ "[\"Test\"]", create(new ArrayNode<TextNode>().add(TextNode.valueOf("Test"))), 8 },
			/* [7] */{ "[\"Test\\\"Test\"]", create(new ArrayNode<TextNode>().add(TextNode.valueOf("Test\\\"Test"))),
				14 },
			/* [8] */{ "{\"key1\" : null}", create(new ObjectNode().put("key1", NullNode.getInstance())), 15 },
			/* [9] */{ "\"thisStringHas\tanEscape\u4567Sequence\"",
				create(TextNode.valueOf("thisStringHas\tanEscape\u4567Sequence")), 33 },
			/* [10] */{ "{\"key1\" : null,     \"key2\"     : {}}",
				create(new ObjectNode().put("key1", NullNode.getInstance()).put("key2", new ObjectNode())), 36 },
			/* [11] */{ "42", create(IntNode.valueOf(42)), 2 },
			/* [12] */{ "[42]", create(new ArrayNode<IntNode>().add(IntNode.valueOf(42))), 4 },
			/* [13] */{ "42.42", create(DecimalNode.valueOf(BigDecimal.valueOf(42.42))), 5 },
			/* [14] */{ String.valueOf(Long.MAX_VALUE), create(LongNode.valueOf(Long.MAX_VALUE)), 19 },
			/* [15] */{ "\"thisIs\\\"AEscape\\\"Sequence\"", create(TextNode.valueOf("thisIs\\\"AEscape\\\"Sequence")),
				27 },
			/* [16] */{ "   {   \"42\" :  42 , \"1337\" :   []  }",
				create(new ObjectNode().put("42", IntNode.valueOf(42)).put("1337", new ArrayNode<IJsonNode>())), 36 },
			/* [17] */{ "{ \"object\" : { \"key\" : [] } }",
				create(new ObjectNode().put("object", new ObjectNode().put("key", new ArrayNode<IJsonNode>()))), 29 },
			/* [18] */{ "", create(MissingNode.getInstance()), 0 },
			/* [19] */{ "-42", create(IntNode.valueOf(-42)), 3 },

			/*
			 * Tests with multiple records
			 */
			/* [20] */{ "null , null", create(NullNode.getInstance(), NullNode.getInstance()), 11 },
			/* [21] */{ "true, false, true, false",
				create(BooleanNode.TRUE, BooleanNode.FALSE, BooleanNode.TRUE, BooleanNode.FALSE), 24 },
			/* [22] */{
				"[null, null] , {\"key\" : null}",
				create(new ArrayNode<NullNode>().add(NullNode.getInstance()).add(NullNode.getInstance()),
					new ObjectNode().put("key", NullNode.getInstance())), 29 },
			/* [23] */{
				"[], {}, true, false, null, 42, \"TEST\"",
				create(new ArrayNode<IJsonNode>(), new ObjectNode(), BooleanNode.TRUE, BooleanNode.FALSE,
					NullNode.getInstance(),
					IntNode.valueOf(42), TextNode.valueOf("TEST")), 37 },
			/* [24] */{ "null, null",
				create(NullNode.getInstance(), NullNode.getInstance(), MissingNode.getInstance()), 10 }
		});
	}

	private static IJsonNode[] create(IJsonNode... elements) {
		return elements;
	}
}