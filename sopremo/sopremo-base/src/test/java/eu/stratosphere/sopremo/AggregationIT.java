package eu.stratosphere.sopremo;

import static eu.stratosphere.sopremo.function.FunctionUtil.createFunctionCall;

import org.junit.Test;

import eu.stratosphere.sopremo.base.Grouping;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.ArrayProjection;
import eu.stratosphere.sopremo.expressions.BatchAggregationExpression;
import eu.stratosphere.sopremo.expressions.ExpressionUtil;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.expressions.ObjectCreation;
import eu.stratosphere.sopremo.testing.SopremoTestPlan;

/**
 * Tests a complex {@link Grouping} and indeed tests {@link BatchAggregationExpression} as well. 
 */
public class AggregationIT {
	@Test
	public void shouldGroupAll() {
		final ObjectCreation resultProjection = new ObjectCreation();
		resultProjection.addMapping("id",
			ExpressionUtil.makePath(new InputSelection(0), new ArrayAccess(0), new ObjectAccess("id")));
		resultProjection.addMapping(
			"values1",
			createFunctionCall(CoreFunctions.ALL,
				new ArrayProjection(ExpressionUtil.makePath(new InputSelection(0), new ObjectAccess("value")))));
		resultProjection.addMapping(
			"values2",
			createFunctionCall(CoreFunctions.ALL,
				new ArrayProjection(ExpressionUtil.makePath(new InputSelection(0), new ObjectAccess("value")))));
		resultProjection.addMapping(
			"sorted",
			createFunctionCall(CoreFunctions.SORT,
				new ArrayProjection(ExpressionUtil.makePath(new InputSelection(0), new ObjectAccess("value")))));
		final Grouping grouping = new Grouping().
			withGroupingKey(new ObjectAccess("id")).
			withResultProjection(resultProjection);

		final SopremoTestPlan plan = new SopremoTestPlan(grouping);
		plan.getInput(0).
			addObject("id", 1, "value", 11).
			addObject("id", 1, "value", 12).
			addObject("id", 2, "value", 21).
			addObject("id", 2, "value", 22);
		plan.getExpectedOutput(0).
			addObject("id", 1, "values1", new int[] { 11, 12 }, "values2", new int[] { 11, 12 }, "sorted",
				new int[] { 11, 12 }).
			addObject("id", 2, "values1", new int[] { 21, 22 }, "values2", new int[] { 21, 22 }, "sorted",
				new int[] { 21, 22 });

		plan.run();
	}
}
