package eu.stratosphere.sopremo.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import junit.framework.Assert;

import org.junit.Test;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.generic.io.FormatUtil;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.io.JsonFormat.JsonInputFormat;
import eu.stratosphere.sopremo.io.SopremoFormat.SopremoFileInputFormat;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;
import eu.stratosphere.sopremo.testing.SopremoTestPlan;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.IntNode;

//import eu.stratosphere.sopremo.testing.SopremoTestPlan;

/**
 * Tests {@link JsonInputFormat}.
 * 
 * @author Arvid Heise
 */
public class JsonInputFormatTest {

	/**
	 * Tests if a {@link TestPlan} can be executed.
	 * 
	 * @throws IOException
	 */
	@Test
	public void completeTestPasses() throws IOException {
		final Source read = new Source(new JsonFormat(), this.getResource("SopremoTestPlan/test.json"));

		final SopremoTestPlan testPlan = new SopremoTestPlan(read);
		testPlan.run();
		Assert.assertEquals("input and output should be equal in identity map", testPlan.getInput(0), testPlan
			.getActualOutput(0));
	}

	/**
	 * Tests if a {@link TestPlan} can be executed.
	 * 
	 * @throws IOException
	 */
	@Test
	public void completeTestPassesWithExpectedValues() throws IOException {
		final Source read = new Source(new JsonFormat(), this.getResource("SopremoTestPlan/test.json"));

		final SopremoTestPlan testPlan = new SopremoTestPlan(read);
		testPlan.getExpectedOutput(0).load(this.getResource("SopremoTestPlan/test.json"));
		testPlan.run();
	}

	private String getResource(final String name) throws IOException {
		return JsonInputFormatTest.class.getClassLoader().getResources(name)
			.nextElement().toString();
	}

	/**
	 * @throws IOException
	 */
	@Test
	public void shouldProperlyReadArray() throws IOException {
		final File file = File.createTempFile("jsonInputFormatTest", null);
		file.delete();
		final OutputStreamWriter jsonWriter = new OutputStreamWriter(new FileOutputStream(file));
		jsonWriter.write("{\"id\": 1}, {\"id\": 2}, {\"id\": 3}, {\"id\": 4}, {\"id\": 5}");
		jsonWriter.close();

		Configuration config = new Configuration();
		final EvaluationContext context = new EvaluationContext();
		SopremoUtil.setEvaluationContext(config, context);
		SopremoUtil.setLayout(config, SopremoRecordLayout.EMPTY);
		final JsonFormat format = new JsonFormat();
		SopremoUtil.transferFieldsToConfiguration(format, SopremoFormat.class, config,
			JsonInputFormat.class, SopremoFileInputFormat.class);
		final SopremoFileInputFormat inputFormat =
			FormatUtil.openInput(JsonInputFormat.class, file.toURI().toString(), config);
		final SopremoRecord record = new SopremoRecord(SopremoRecordLayout.EMPTY);
		for (int index = 1; index <= 5; index++) {
			Assert.assertFalse("more pairs expected @ " + index, inputFormat.reachedEnd());
			Assert.assertTrue("valid pair expected @ " + index, inputFormat.nextRecord(record));
			Assert.assertEquals("other order expected", index,
				((IntNode) ((IObjectNode) record.getNode()).get("id")).getIntValue());
		}

		if (!inputFormat.reachedEnd()) {
			Assert.assertTrue("no more pairs but reachedEnd did not return false", inputFormat.nextRecord(record));
			Assert.fail("pair unexpected: " + record.getNode());
		}
	}

	/**
	 * @throws IOException
	 */
	@Test
	public void shouldProperlyReadSingleValue() throws IOException {
		final File file = File.createTempFile("jsonInputFormatTest", null);
		file.delete();
		final OutputStreamWriter jsonWriter = new OutputStreamWriter(new FileOutputStream(file));
		jsonWriter.write("{\"array\": [{\"id\": 1}, {\"id\": 2}, {\"id\": 3}, {\"id\": 4}, {\"id\": 5}]}");
		jsonWriter.close();

		Configuration config = new Configuration();
		final EvaluationContext context = new EvaluationContext();
		SopremoUtil.setEvaluationContext(config, context);
		SopremoUtil.setLayout(config, SopremoRecordLayout.EMPTY);
		SopremoUtil.transferFieldsToConfiguration(new JsonFormat(), SopremoFormat.class, config,
			JsonInputFormat.class, SopremoFileInputFormat.class);
		final SopremoFileInputFormat inputFormat =
			FormatUtil.openInput(JsonInputFormat.class, file.toURI().toString(), config);
		final SopremoRecord record = new SopremoRecord(SopremoRecordLayout.EMPTY);

		if (!inputFormat.reachedEnd())
			if (!inputFormat.nextRecord(record))
				Assert.fail("one value expected expected: " + record.getNode());

		if (!inputFormat.reachedEnd()) {
			Assert.assertTrue("no more values but reachedEnd did not return false", inputFormat.nextRecord(record));
			Assert.fail("value unexpected: " + record.getNode());
		}

		final IArrayNode<?> arrayNode = (IArrayNode<?>) ((IObjectNode) record.getNode()).get("array");
		Assert.assertNotNull("could not find top level node", arrayNode);
		for (int index = 1; index <= 5; index++) {
			Assert.assertNotNull("could not find array element " + index, arrayNode.get(index - 1));
			Assert.assertEquals("other order expected", index,
				((IntNode) ((IObjectNode) arrayNode.get(index - 1)).get("id")).getIntValue());
		}
	}
}
