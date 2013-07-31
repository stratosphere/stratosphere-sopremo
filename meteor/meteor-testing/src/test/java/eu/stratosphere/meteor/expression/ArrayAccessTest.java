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
package eu.stratosphere.meteor.expression;

import org.junit.Assert;
import org.junit.Test;

import eu.stratosphere.meteor.MeteorParseTest;
import eu.stratosphere.sopremo.base.Projection;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.ArrayProjection;
import eu.stratosphere.sopremo.expressions.ConstantExpression;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.FunctionCall;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.expressions.ObjectCreation;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.SopremoPlan;

/**
 * @author arv
 */
public class ArrayAccessTest extends MeteorParseTest {
	@Test
	public void testArrayProjection() {
		final SopremoPlan actualPlan =
			parseScript(
				"$input = read from 'file://input.json';\n" +
					"$result = transform $input into { result: $input.addresses[*].street };\n" +
					"write $result to 'file://output.json'; ");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final Projection projection = new Projection().
			withInputs(input).
			withResultProjection(new ObjectCreation(
				new ObjectCreation.FieldAssignment("result",
					new ArrayProjection(new ObjectAccess("street")).
						withInputExpression(
						new ObjectAccess("addresses").withInputExpression(new InputSelection(0))))));
		final Sink sink = new Sink("file://output.json").withInputs(projection);
		expectedPlan.setSinks(sink);

		Assert.assertEquals("unexpectedPlan", expectedPlan, actualPlan);
	}

	@Test
	public void testArrayProjectionWithMethodCall() {
		final SopremoPlan actualPlan =
			parseScript(
				"$input = read from 'file://input.json';\n" +
					"$result = transform $input into { result: $input.addresses[*].count() };\n" +
					"write $result to 'file://output.json'; ");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final Projection projection = new Projection().
			withInputs(input).
			withResultProjection(new ObjectCreation(
				new ObjectCreation.FieldAssignment("result",
					new ArrayProjection(new FunctionCall("count", expectedPlan, EvaluationExpression.VALUE)).
						withInputExpression(
						new ObjectAccess("addresses").withInputExpression(new InputSelection(0))))));
		final Sink sink = new Sink("file://output.json").withInputs(projection);
		expectedPlan.setSinks(sink);

		Assert.assertEquals("unexpectedPlan", expectedPlan, actualPlan);
	}

	@Test
	public void testArrayProjectionOnMethodCall() {
		final SopremoPlan actualPlan =
			parseScript(
				"$input = read from 'file://input.json';\n" +
					"$result = transform $input into { result: $input.all()[*].street };\n" +
					"write $result to 'file://output.json'; ");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final Projection projection = new Projection().
			withInputs(input).
			withResultProjection(new ObjectCreation(
				new ObjectCreation.FieldAssignment("result",
					new ArrayProjection(new ObjectAccess("street")).
						withInputExpression(new FunctionCall("all", expectedPlan, new InputSelection(0))))));
		final Sink sink = new Sink("file://output.json").withInputs(projection);
		expectedPlan.setSinks(sink);

		Assert.assertEquals("unexpectedPlan", expectedPlan, actualPlan);
	}

	@Test
	public void testMethodCallWithComplexExpression2() {
		final SopremoPlan actualPlan =
			parseScript(
				"$input = read from 'file://input.json';\n"
					+
					"$result = transform $input into { result: $input.replace('a', 'b')[*].street[1] };\n"
					+
					"write $result to 'file://output.json'; ");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final EvaluationExpression replaceExpression = new FunctionCall("replace", expectedPlan,
			new InputSelection(0),
			new ConstantExpression("a"),
			new ConstantExpression("b"));
		final Projection projection = new Projection().
			withInputs(input).
			withResultProjection(new ObjectCreation(
				new ObjectCreation.FieldAssignment("result",
					new ArrayAccess(1).withInputExpression(
						new ArrayProjection(new ObjectAccess("street")).
							withInputExpression(replaceExpression)))));
		final Sink sink = new Sink("file://output.json").withInputs(projection);
		expectedPlan.setSinks(sink);

		Assert.assertEquals("unexpectedPlan", expectedPlan, actualPlan);
	}

}
