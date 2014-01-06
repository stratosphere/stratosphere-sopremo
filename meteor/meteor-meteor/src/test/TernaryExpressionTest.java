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

import eu.stratosphere.sopremo.base.Projection;
import eu.stratosphere.sopremo.expressions.ConstantExpression;
import eu.stratosphere.sopremo.expressions.ObjectCreation;
import eu.stratosphere.sopremo.expressions.TernaryExpression;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.type.JsonUtil;

/**
 */
public class TernaryExpressionTest extends MeteorTest {

	@Test
	public void shouldSupportTernary() {
		final SopremoPlan actualPlan = parseScript("$input = read from 'file://input.json';\n" +
			"$result = transform $input into {name: $input.name ? $input.name : 'unknown'};\n" +
			"write $result to 'file://output.json'; ");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final Projection projection = new Projection().
			withResultProjection(new ObjectCreation(
				new ObjectCreation.FieldAssignment("name",
					new TernaryExpression(JsonUtil.createPath("0", "name"),
						JsonUtil.createPath("0", "name"),
						new ConstantExpression("unknown"))))).
			withInputs(input);
		final Sink output = new Sink("file://output.json").withInputs(projection);
		expectedPlan.setSinks(output);

		assertPlanEquals(expectedPlan, actualPlan);
	}
	@Test
	public void shouldSupportElvis() {
		final SopremoPlan actualPlan = parseScript("$input = read from 'file://input.json';\n" +
			"$result = transform $input into {name: $input.name ? $input.name : 'unknown'};\n" +
			"write $result to 'file://output.json'; ");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final Projection projection = new Projection().
			withResultProjection(new ObjectCreation(
				new ObjectCreation.FieldAssignment("name",
					new TernaryExpression(JsonUtil.createPath("0", "name"),
						JsonUtil.createPath("0", "name"),
						new ConstantExpression("unknown"))))).
			withInputs(input);
		final Sink output = new Sink("file://output.json").withInputs(projection);
		expectedPlan.setSinks(output);

		assertPlanEquals(expectedPlan, actualPlan);
	}
}
