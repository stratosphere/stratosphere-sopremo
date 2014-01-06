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
package eu.stratosphere.meteor.expression;

import static eu.stratosphere.sopremo.function.FunctionUtil.createFunctionCall;

import org.junit.Assert;
import org.junit.Test;

import eu.stratosphere.meteor.MeteorParseTest;
import eu.stratosphere.sopremo.CoreFunctions;
import eu.stratosphere.sopremo.base.Projection;
import eu.stratosphere.sopremo.expressions.ArrayProjection;
import eu.stratosphere.sopremo.expressions.ConstantExpression;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.expressions.NotNullOrMissingBooleanExpression;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.expressions.ObjectCreation;
import eu.stratosphere.sopremo.expressions.TernaryExpression;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.JsonUtil;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.sopremo.type.NullNode;

/**
 */
public class MethodTest extends MeteorParseTest {

	@Test
	public void testMethodCall() {
		final SopremoPlan actualPlan = this.parseScript(
			"$input = read from 'file://input.json';\n" +
				"$result = transform $input into { result: $input.count() };\n" +
				"write $result to 'file://output.json'; ");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final Projection projection = new Projection().
			withInputs(input).
			withResultProjection(new ObjectCreation(
				new ObjectCreation.FieldAssignment("result",
					createFunctionCall(CoreFunctions.COUNT, new InputSelection(0)))));
		final Sink sink = new Sink("file://output.json").withInputs(projection);
		expectedPlan.setSinks(sink);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void testMethodCallWithAppendedPathSegment() {
		final SopremoPlan actualPlan = this.parseScript(
			"$input = read from 'file://input.json';\n" +
				"$result = transform $input into { result: $input.all().count() };\n" +
				"write $result to 'file://output.json'; ");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final Projection projection = new Projection().
			withInputs(input).
			withResultProjection(new ObjectCreation(
				new ObjectCreation.FieldAssignment("result",
					createFunctionCall(CoreFunctions.COUNT,
						createFunctionCall(CoreFunctions.ALL, new InputSelection(0))))));
		final Sink sink = new Sink("file://output.json").withInputs(projection);
		expectedPlan.setSinks(sink);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void testMethodCallInPath() {
		final SopremoPlan actualPlan = this.parseScript(
			"$input = read from 'file://input.json';\n" +
				"$result = transform $input into { result: $input.addresses.all().count() };\n" +
				"write $result to 'file://output.json'; ");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final Projection projection =
			new Projection().
				withInputs(input).
				withResultProjection(new ObjectCreation(
					new ObjectCreation.FieldAssignment("result",
						createFunctionCall(CoreFunctions.COUNT,
							createFunctionCall(CoreFunctions.ALL,
								new ObjectAccess("addresses").withInputExpression(new InputSelection(0)))))));
		final Sink sink = new Sink("file://output.json").withInputs(projection);
		expectedPlan.setSinks(sink);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void testSafeMethodCall() {
		final SopremoPlan actualPlan = this.parseScript(
			"$input = read from 'file://input.json';\n" +
				"$result = transform $input into $input.addresses?.count();\n" +
				"write $result to 'file://output.json'; ");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final Projection projection =
			new Projection().
				withInputs(input).
				withResultProjection(
					new TernaryExpression(
						new NotNullOrMissingBooleanExpression().withInputExpression(new ObjectAccess("addresses")),
						createFunctionCall(CoreFunctions.COUNT, new ObjectAccess("addresses")),
						new ObjectAccess("addresses")));
		final Sink sink = new Sink("file://output.json").withInputs(projection);
		expectedPlan.setSinks(sink);

		assertPlanEquals(expectedPlan, actualPlan);

		Assert.assertEquals(IntNode.valueOf(3),
			projection.getResultProjection().evaluate(
				JsonUtil.createObjectNode("addresses", JsonUtil.createArrayNode(1, 2, 3))));
		Assert.assertEquals(NullNode.getInstance(),
			projection.getResultProjection().evaluate(JsonUtil.createObjectNode("addresses", null)));
		Assert.assertEquals(MissingNode.getInstance(),
			projection.getResultProjection().evaluate(JsonUtil.createObjectNode()));
	}

	@Test
	public void testMethodCallWithComplexExpression() {
		final SopremoPlan actualPlan =
			this.parseScript(
				"$input = read from 'file://input.json';\n"
					+
					"$result = transform $input into { result: $input.addresses.replace($input.city, '').street.count() };\n"
					+
					"write $result to 'file://output.json'; ");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final EvaluationExpression replaceExpression = createFunctionCall(CoreFunctions.REPLACE,
			new ObjectAccess("addresses").withInputExpression(new InputSelection(0)),
			new ObjectAccess("city").withInputExpression(new InputSelection(0)),
			new ConstantExpression(""));
		final Projection projection = new Projection().
			withInputs(input).
			withResultProjection(new ObjectCreation(
				new ObjectCreation.FieldAssignment("result",
					createFunctionCall(CoreFunctions.COUNT,
						new ObjectAccess("street").withInputExpression(replaceExpression)))));
		final Sink sink = new Sink("file://output.json").withInputs(projection);
		expectedPlan.setSinks(sink);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void testMethodCallWithComplexExpression2() {
		final SopremoPlan actualPlan =
			this.parseScript(
				"$input = read from 'file://input.json';\n"
					+
					"$result = transform $input into { result: $input.addresses.replace($input.city, '')[*].street.count() };\n"
					+
					"write $result to 'file://output.json'; ");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final EvaluationExpression replaceExpression = createFunctionCall(CoreFunctions.REPLACE,
			new ObjectAccess("addresses").withInputExpression(new InputSelection(0)),
			new ObjectAccess("city").withInputExpression(new InputSelection(0)),
			new ConstantExpression(""));
		final Projection projection = new Projection().
			withInputs(input).
			withResultProjection(new ObjectCreation(
				new ObjectCreation.FieldAssignment("result",
					createFunctionCall(CoreFunctions.COUNT,
						new ArrayProjection(new ObjectAccess("street")).
							withInputExpression(replaceExpression)))));
		final Sink sink = new Sink("file://output.json").withInputs(projection);
		expectedPlan.setSinks(sink);

		assertPlanEquals(expectedPlan, actualPlan);
	}

}
