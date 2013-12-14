package eu.stratosphere.sopremo.io;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.JsonUtil;
import eu.stratosphere.sopremo.type.ObjectNode;

public class CsvInputFormatTest extends InputFormatTest {

	/**
	 * Tests if a {@link TestPlan} can be executed.
	 * 
	 * @throws IOException
	 */
	@Test
	public void shouldParseCsv() throws IOException {
		final File source = new File(this.getResource("CsvInputFormat/restaurant_short.csv"));

		final CsvFormat format = new CsvFormat();
		format.setFieldDelimiter(",");
		final Collection<IJsonNode> actual = readFromFile(source, format);

		final List<ObjectNode> expected = Arrays.asList(
			JsonUtil.createObjectNode("id", "1", "name", "arnie morton's of chicago",
				"addr", "435 s. la cienega blv.", "city", "los angeles",
				"phone", "310/246-1501", "type", "american", "class", "'0'"),
			JsonUtil.createObjectNode("id", "2", "name", "\"arnie morton's of chicago\"",
				"addr", "435 s. la cienega blv.", "city", "los,angeles",
				"phone", "310/246-1501", "type", "american", "class", "'0'"),
			JsonUtil.createObjectNode("id", "3", "name", "arnie morton's of chicago",
				"addr", "435 s. la cienega blv.", "city", "los\nangeles", "phone", "310/246-1501",
				"type", "american", "class", "'0'"));
		Assert.assertEquals(expected, actual);
	}

	/**
	 * Tests if line breaks "\n" are handled correctly.
	 * 
	 * @throws IOException
	 */
	@Test
	public void shouldParseCsvWithoutTrailingNewline() throws IOException {
		final File source = new File(this.getResource("CsvInputFormat/trailNewLines.csv"));

		final CsvFormat format = new CsvFormat();
		format.setFieldDelimiter(",");
		final Collection<IJsonNode> actual = readFromFile(source, format);

		final List<ObjectNode> expected = Arrays.asList(
			JsonUtil.createObjectNode("a", "1", "b", "2"),
			JsonUtil.createObjectNode("a", "3", "b", "4"));

		Assert.assertEquals(expected, actual);
	}

	/**
	 * Tests if input containing multibytes is handled properly.
	 * 
	 * @throws IOException
	 */
	@Test
	public void shouldParseCsvWithMultibyte() throws IOException {
		final File source = new File(this.getResource("CsvInputFormat/multibyte.csv"));

		final CsvFormat format = new CsvFormat();
		format.setFieldDelimiter(",");
		final Collection<IJsonNode> actual = readFromFile(source, format);

		final List<ObjectNode> expected =
			Arrays.asList(
				JsonUtil.createObjectNode(
					"a",
					"1",
					"b",
					"€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€"),
				JsonUtil.createObjectNode(
					"a",
					"2",
					"b",
					"€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€"));

		Assert.assertEquals(expected, actual);
	}

	private String getResource(final String name) throws IOException {
		return JsonInputFormatTest.class.getClassLoader().getResources(name)
			.nextElement().getFile();
	}
}
