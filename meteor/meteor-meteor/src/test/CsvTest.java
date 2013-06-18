/***********************************************************************************************************************
 *
 * Copyright (C) 2010-2013 by the Stratosphere project (http://stratosphere.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 **********************************************************************************************************************/
package eu.stratosphere.meteor;

import org.junit.Test;

import eu.stratosphere.meteor.MeteorTest;
import eu.stratosphere.sopremo.io.CsvFormat;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.SopremoPlan;

/**
 * @author Arvid Heise
 */
public class CsvTest extends MeteorTest {

	@Test
	public void shouldConfigureColumnNames() {
		final SopremoPlan actualPlan = this.parseScript(
			"$input = read csv from 'file://input.any'" +
				"  columns ['A', 'B', 'C'];\n" +
				"write $input to 'file://output.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source(new CsvFormat().withKeyNames("A", "B", "C"), "file://input.any");
		final Sink output = new Sink("file://output.json").withInputs(input);
		expectedPlan.setSinks(output);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void shouldConfigureEncoding() {
		final SopremoPlan actualPlan = this.parseScript(
			"$input = read csv from 'file://input.any'" +
				"  encoding 'iso-8859-1';\n" +
				"write $input to 'file://output.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source(new CsvFormat().withEncoding("iso-8859-1"), "file://input.any");
		final Sink output = new Sink("file://output.json").withInputs(input);
		expectedPlan.setSinks(output);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void shouldConfigureFieldDelimiter() {
		final SopremoPlan actualPlan = this.parseScript(
			"$input = read csv from 'file://input.any'" +
				"  delimiter ',';\n" +
				"write $input to 'file://output.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source(new CsvFormat().withFieldDelimiter(","), "file://input.any");
		final Sink output = new Sink("file://output.json").withInputs(input);
		expectedPlan.setSinks(output);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void shouldConfigureQuotationOff() {
		final SopremoPlan actualPlan = this.parseScript(
			"$input = read csv from 'file://input.any'" +
				"  quote false;\n" +
				"write $input to 'file://output.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source(new CsvFormat().withQuotation(false), "file://input.any");
		final Sink output = new Sink("file://output.json").withInputs(input);
		expectedPlan.setSinks(output);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void shouldConfigureQuotationOn() {
		final SopremoPlan actualPlan = this.parseScript(
			"$input = read csv from 'file://input.any'" +
				"  quote true;\n" +
				"write $input to 'file://output.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source(new CsvFormat().withQuotation(true), "file://input.any");
		final Sink output = new Sink("file://output.json").withInputs(input);
		expectedPlan.setSinks(output);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void shouldDetectCsv() {
		final SopremoPlan actualPlan = this.parseScript(
			"$input = read from 'file://input.csv';\n" +
				"write $input to 'file://output.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source(new CsvFormat(), "file://input.csv");
		final Sink output = new Sink("file://output.json").withInputs(input);
		expectedPlan.setSinks(output);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void shouldSetToCsv() {
		final SopremoPlan actualPlan = this.parseScript(
			"$input = read csv from 'file://input.any';\n" +
				"write $input to 'file://output.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source(new CsvFormat(), "file://input.any");
		final Sink output = new Sink("file://output.json").withInputs(input);
		expectedPlan.setSinks(output);

		assertPlanEquals(expectedPlan, actualPlan);
	}

}
