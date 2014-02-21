/***********************************************************************************************************************
 *
 * Copyright (C) 2010 by the Stratosphere project (http://stratosphere.eu)
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
package eu.stratosphere.meteor.syntax;

import org.junit.Test;

import eu.stratosphere.meteor.MeteorParseTest;
import eu.stratosphere.sopremo.base.Join;
import eu.stratosphere.sopremo.io.JsonFormat;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.SopremoPlan;

/**
 * 
 */
public class IncludeTest extends MeteorParseTest {

	@Test
	public void testInclude() {
		final SopremoPlan plan = this.parseScript(getResource("include/master.meteor"));

		Source input = new Source(new JsonFormat(), "file://input.json");
		Source input2 = new Source(new JsonFormat(), "file://input2.json");
		Join join = new Join().withInputs(input, input2);
		final Sink output = new Sink(new JsonFormat(), "file://output.json").withInputs(join);
		final SopremoPlan expected = new SopremoPlan(output);
		
		assertPlanEquals(expected, plan);
	}
}
