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

import eu.stratosphere.sopremo.io.JsonFormat;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.SopremoPlan;

/**
 */
public class SourceTest extends MeteorParseTest {

	@Test
	public void shouldConfigureEncoding() {
		final SopremoPlan actualPlan = this.parseScript(
			"$input = read json from 'file://input.json'" +
				"  encoding 'iso-8859-1';\n" +
				"write $input to 'file://output.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source(new JsonFormat().withEncoding("ISO-8859-1"), "file://input.json");
		final Sink output = new Sink("file://output.json").withInputs(input);
		expectedPlan.setSinks(output);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void shouldDefaultToJson() {
		final SopremoPlan actualPlan = this.parseScript(
			"$input = read from 'file://input.json';\n" +
				"write $input to 'file://output.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source(new JsonFormat(), "file://input.json");
		final Sink output = new Sink("file://output.json").withInputs(input);
		expectedPlan.setSinks(output);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void shouldSupportHdfs() {
		final SopremoPlan actualPlan = this.parseScript(
			"$input = read from 'hdfs://localhost:8020/input.json';\n" +
				"write $input to 'file://output.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source(new JsonFormat(), "hdfs://localhost:8020/input.json");
		final Sink output = new Sink("file://output.json").withInputs(input);
		expectedPlan.setSinks(output);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void shouldUseWorkingDirectory() {
		final SopremoPlan actualPlan = this.parseScript(
			"setWorkingDirectory('hdfs://cluster:1234/mydir');" +
				"$input = read from 'input.json';\n" +
				"write $input to 'output.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source(new JsonFormat(), "hdfs://cluster:1234/mydir/input.json");
		final Sink output = new Sink("hdfs://cluster:1234/mydir/output.json").withInputs(input);
		expectedPlan.setSinks(output);

		assertPlanEquals(expectedPlan, actualPlan);
	}

}
