package eu.stratosphere.sopremo.io;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import eu.stratosphere.core.testing.TestPlan;
import eu.stratosphere.sopremo.io.JsonFormat.JsonInputFormat;
import eu.stratosphere.sopremo.testing.SopremoTestPlan;

/**
 * Tests {@link JsonInputFormat}.
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
}
