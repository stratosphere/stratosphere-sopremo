package eu.stratosphere.sopremo.io;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class JsonParserFailureParamTest {

	private JsonParser parser;

	private String input;

	public JsonParserFailureParamTest(final String input) {
		this.input = input;
	}

	@Before
	public void setUp() {
		this.parser = new JsonParser(this.input);
	}

	@Test(expected = JsonParseException.class)
	public void shouldParseCorrectly() throws JsonParseException {
		this.parser.readValueAsTree();
	}

	@Parameters
	public static List<Object[]> combinations() {
		return Arrays.asList(new Object[][] {
			/* [0] */{ "a" },
			/* [1] */{ "nulL" },
			/* [2] */{ "[tru]" },
			/* [3] */{ "\"invalidEscape \\a sequence\"" },
			/* [4] */{ "[22 , 42, 1337] a" },
			/* [5] */{ "[42, 1337]  42" },
			/* [6] */{ "[ 42, 1337" },
			/* [7] */{ "{ \"key\" : 1337" },
			/* [8] */{ "[Null]" },
			/* [9] */{ "{ \"key\" : Null }" },
			/* [10] */{ "-" },
			/* [11] */{ "{ : 42}" },
			/* [12] */{ "{ \"42\" : }" },
			/* [13] */{ "{ 42 }" },
			/* [14] */{ "{ \"42\" }" },
			/* [15] */{ "{ [1337] : 42}" },
			/* [16] */{ "[ , 42 ]" },
			/* [17] */{ "[ 42, ]" },
			/* [18] */{ "[ 42, , 1337]" }

			/*
			 * Failures in json format that are currently not detected by JSONParser.
			 * { "{ \"key\" : 42 , , \"key2\" : 1337 }" },
			 * { "{ , \"key\" : 42 }" },
			 * { "{ \"key\" : 42 , }" },
			 * { "[ [] [] ]" }
			 */
		});
	}
}
