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

import static eu.stratosphere.sopremo.function.FunctionUtil.addToBatch;

import org.junit.Test;

import eu.stratosphere.meteor.MeteorParseTest;
import eu.stratosphere.sopremo.CoreFunctions;
import eu.stratosphere.sopremo.base.Grouping;
import eu.stratosphere.sopremo.base.Selection;
import eu.stratosphere.sopremo.expressions.ArrayCreation;
import eu.stratosphere.sopremo.expressions.BatchAggregationExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression.BinaryOperator;
import eu.stratosphere.sopremo.expressions.ConstantExpression;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.expressions.ObjectCreation;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.type.JsonUtil;

/**
 */
public class AggregationTest extends MeteorParseTest {
	@Test
	public void test() {
		final SopremoPlan actualPlan =
			this.parseScript("$li = read from 'file://lineitem.json';\n" +
				"$filterLi = filter $li where $li.l_linenumber >= 1;\n" +
				"$groups = group $filterLi by [$filterLi.l_linestatus, $filterLi.l_returnflag] into {\n" +
				"     first: $filterLi[0],\n" +
				"	  count_qty: count($filterLi),\n" +
				"	  sum_qty: sum($filterLi[*].l_quantity),\n" +
				"	  mean_qty: max($filterLi[*].l_quantity)\n" +
				"};\n" +
				"write $groups to 'file://q1.json';\n");

		final Source input = new Source("file://lineitem.json");
		final Selection filter = new Selection().
			withInputs(input).
			withCondition(new ComparativeExpression(new ObjectAccess("l_linenumber"),
				BinaryOperator.GREATER_EQUAL,
				new ConstantExpression(1)));
		final BatchAggregationExpression batch = new BatchAggregationExpression();
		batch.withInputExpression(new InputSelection(0));
		final Grouping grouping =
			new Grouping().
				withInputs(filter).
				withGroupingKey(
					0,
					new ArrayCreation(JsonUtil.createPath("0", "l_linestatus"),
						JsonUtil.createPath("0", "l_returnflag"))).
				withResultProjection(
					new ObjectCreation(
						new ObjectCreation.FieldAssignment("first", addToBatch(batch, CoreFunctions.FIRST)),
						new ObjectCreation.FieldAssignment("count_qty", addToBatch(batch, CoreFunctions.COUNT)),
						new ObjectCreation.FieldAssignment("sum_qty", addToBatch(batch, CoreFunctions.SUM,
							new ObjectAccess("l_quantity"))),
						new ObjectCreation.FieldAssignment("mean_qty", addToBatch(batch, CoreFunctions.MAX,
							new ObjectAccess("l_quantity")))
					));

		final Sink sink = new Sink("file://q1.json").withInputs(grouping);
		final SopremoPlan expectedPlan = new SopremoPlan();
		expectedPlan.setSinks(sink);

		assertPlanEquals(expectedPlan, actualPlan);
	}
}
