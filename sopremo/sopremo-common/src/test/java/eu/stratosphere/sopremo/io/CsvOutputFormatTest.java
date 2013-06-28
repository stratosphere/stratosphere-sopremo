package eu.stratosphere.sopremo.io;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import eu.stratosphere.sopremo.io.CsvFormat.CsvOutputFormat;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.JsonUtil;

public class CsvOutputFormatTest extends OutputFormatTest {

	/**
	 * Tests if a {@link TestPlan} can be executed.
	 * 
	 * @throws IOException
	 */
	@Test
	public void shouldWriteParsableCsv() throws IOException {
		// final Sink sink = new Sink(new CsvFormat(), output);
		//
		// final EqualCloneTestPlan testPlan = new EqualCloneTestPlan(sink); // write
		//
		// testPlan.getInput(0).
		// addObject("id", "1", "name", "arnie morton's of chicago",
		// "addr", "435 s. la cienega blv.", "city", "los angeles",
		// "phone", "310/246-1501", "type", "american", "class", "'0'").
		// addObject("id", "2", "name", "\"arnie morton's of chicago\"",
		// "addr", "435 s. la cienega blv.", "city", "los,angeles",
		// "phone", "310/246-1501", "type", "american", "class", "'0'").
		// addObject("id", "3", "name", "arnie morton's of chicago",
		// "addr", "435 s. la cienega blv.", "city", "los\nangeles", "phone", "310/246-1501",
		// "type", "american", "class", "'0'");

		final CsvFormat format = new CsvFormat();
		format.setKeyNames("id", "name", "addr", "city", "phone", "type", "class");

		IJsonNode[] values = {
			JsonUtil.createObjectNode("id", "1", "name", "arnie morton's of chicago",
				"addr", "435 s. la cienega blv.", "city", "los angeles",
				"phone", "310/246-1501", "type", "american", "class", "'0'"),
			JsonUtil.createObjectNode("id", "2", "name", "\"arnie morton's of chicago\"",
				"addr", "435 s. la cienega blv.", "city", "los,angeles",
				"phone", "310/246-1501", "type", "american", "class", "'0'"),
			JsonUtil.createObjectNode("id", "3", "name", "arnie morton's of chicago",
				"addr", "435 s. la cienega blv.", "city", "los\nangeles", "phone", "310/246-1501",
				"type", "american", "class", "'0'"), };

		writeAndRead(format, NULL_LAYOUT, values);
	}

	@Test
	public void shouldEscapeProperly() {
		final String escapedString = new CsvOutputFormat().escapeString("\"Unesc\\unesc\"end\"");
		Assert.assertEquals("\\\"Unesc\\\\unesc\\\"end\\\"", escapedString);
	}

}
