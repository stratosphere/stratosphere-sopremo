package eu.stratosphere.sopremo.io;

import java.io.IOException;

import org.junit.Test;

import eu.stratosphere.core.testing.TestPlan;
import eu.stratosphere.sopremo.testing.SopremoTestPlan;

public class CsvInputFormatTest {

	/**
	 * Tests if a {@link TestPlan} can be executed.
	 * 
	 * @throws IOException
	 */
	@Test
	public void shouldHandleMultipleSplits() throws IOException {
		final Source read =
			new Source(new CsvFormat(), this.getResource("CsvInputFormat/restaurant_short.csv"));
		read.setDegreeOfParallelism(2);

		final SopremoTestPlan testPlan = new SopremoTestPlan(read);

		testPlan.getExpectedOutput(0).
			addObject("id", "1", "name", "arnie morton's of chicago",
				"addr", "435 s. la cienega blv.", "city", "los angeles",
				"phone", "310/246-1501", "type", "american", "class", "'0'").
			addObject("id", "2", "name", "\"arnie morton's of chicago\"",
				"addr", "435 s. la cienega blv.", "city", "los,angeles",
				"phone", "310/246-1501", "type", "american", "class", "'0'").
			addObject("id", "3", "name", "arnie morton's of chicago",
				"addr", "435 s. la cienega blv.", "city", "los\nangeles", "phone", "310/246-1501",
				"type", "american", "class", "'0'");

		testPlan.run();
	}

	/**
	 * Tests if a {@link TestPlan} can be executed.
	 * 
	 * @throws IOException
	 */
	@Test
	public void shouldHandleMultipleSplitsWithLongValues() throws IOException {
		final CsvFormat format = new CsvFormat();
		format.setKeyNames("dna");
		final Source read =
			new Source(format, this.getResource("CsvInputFormat/dna_10.csv"));
		read.setDegreeOfParallelism(5);

		final SopremoTestPlan testPlan = new SopremoTestPlan(read);

		testPlan.getExpectedOutput(0).
			addObject("dna", "T002232011022211032321221021111132111001102110331222013320211121121000030011").
			addObject("dna", "T000300331103312120321221111030021332320322223301201020201213221022323001220").
			addObject("dna", "T3313213211113131000333301313200310020101320032022100233232010033320112013.2").
			addObject("dna", "T311002132100221211031321321122132000033313133020031002010132003202201023210").
			addObject("dna", "T000133222312103202001112220100120002023132021122330021230020010111230222010").
			addObject("dna", "T131221201200111222000011102030013102110222300212303320012233302110303131133").
			addObject("dna", "T323221322022103221123032022222021232111122303221103303023212322330122232202").
			addObject("dna", "T031111222312212012001112220000111020300131021102223002132033200122333020103").
			addObject("dna", "T322232132003300222112132200200202220222111201202212201120112001122123302010").
			addObject("dna", "T321303333333001313300300030330000333030210122232122003211133303302022233200");

		testPlan.run();
	}

	/**
	 * Tests if a {@link TestPlan} can be executed.
	 * 
	 * @throws IOException
	 */
	@Test
	public void shouldHandleMultipleSplitsWithoutNewline() throws IOException {
		final Source read =
			new Source(new CsvFormat(), this.getResource("CsvInputFormat/restaurant_short_wo_newline.csv"));
		read.setDegreeOfParallelism(2);

		final SopremoTestPlan testPlan = new SopremoTestPlan(read);

		testPlan.getExpectedOutput(0).
			addObject("id", "1", "name", "arnie morton's of chicago",
				"addr", "435 s. la cienega blv.", "city", "los angeles",
				"phone", "310/246-1501", "type", "american", "class", "'0'").
			addObject("id", "2", "name", "\"arnie morton's of chicago\"",
				"addr", "435 s. la cienega blv.", "city", "los,angeles",
				"phone", "310/246-1501", "type", "american", "class", "'0'").
			addObject("id", "3", "name", "arnie morton's of chicago",
				"addr", "435 s. la cienega blv.", "city", "los\nangeles", "phone", "310/246-1501",
				"type", "american", "class", "'0'");

		testPlan.run();
	}

	/**
	 * Tests if a {@link TestPlan} can be executed.
	 * 
	 * @throws IOException
	 */
	@Test
	public void shouldParseCsv() throws IOException {
		final Source read =
			new Source(new CsvFormat(), this.getResource("CsvInputFormat/restaurant_short.csv"));

		final SopremoTestPlan testPlan = new SopremoTestPlan(read); // write

		testPlan.getExpectedOutput(0).
			addObject("id", "1", "name", "arnie morton's of chicago",
				"addr", "435 s. la cienega blv.", "city", "los angeles",
				"phone", "310/246-1501", "type", "american", "class", "'0'").
			addObject("id", "2", "name", "\"arnie morton's of chicago\"",
				"addr", "435 s. la cienega blv.", "city", "los,angeles",
				"phone", "310/246-1501", "type", "american", "class", "'0'").
			addObject("id", "3", "name", "arnie morton's of chicago",
				"addr", "435 s. la cienega blv.", "city", "los\nangeles", "phone", "310/246-1501",
				"type", "american", "class", "'0'");

		testPlan.run();
	}

	/**
	 * Tests if a {@link TestPlan} can be executed.
	 * 
	 * @throws IOException
	 */
	@Test
	public void shouldParseCsvWithoutNewline() throws IOException {
		final Source read =
			new Source(new CsvFormat(), this.getResource("CsvInputFormat/restaurant_short_wo_newline.csv"));

		final SopremoTestPlan testPlan = new SopremoTestPlan(read); // write

		testPlan.getExpectedOutput(0).
			addObject("id", "1", "name", "arnie morton's of chicago",
				"addr", "435 s. la cienega blv.", "city", "los angeles",
				"phone", "310/246-1501", "type", "american", "class", "'0'").
			addObject("id", "2", "name", "\"arnie morton's of chicago\"",
				"addr", "435 s. la cienega blv.", "city", "los,angeles",
				"phone", "310/246-1501", "type", "american", "class", "'0'").
			addObject("id", "3", "name", "arnie morton's of chicago",
				"addr", "435 s. la cienega blv.", "city", "los\nangeles", "phone", "310/246-1501",
				"type", "american", "class", "'0'");

		testPlan.run();
	}

	private String getResource(final String name) throws IOException {
		return JsonInputFormatTest.class.getClassLoader().getResources(name)
			.nextElement().toString();
	}
}
