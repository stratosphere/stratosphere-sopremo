package eu.stratosphere.sopremo;

import org.junit.Test;

import eu.stratosphere.sopremo.base.Grouping;
import eu.stratosphere.sopremo.expressions.BatchAggregationExpression;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.expressions.ObjectCreation;
import eu.stratosphere.sopremo.testing.SopremoTestPlan;

/**
 * Tests {@link BuiltinFunctions}
 */
public class AggregationIT {
	@Test
	public void shouldGroupAll() {
		BatchAggregationExpression bae = new BatchAggregationExpression();
		final ObjectCreation resultProjection = new ObjectCreation();
		resultProjection.addMapping("id", bae.add(CoreFunctions.FIRST, new ObjectAccess("id")));
		resultProjection.addMapping("values1", bae.add(CoreFunctions.ALL, new ObjectAccess("value")));
		resultProjection.addMapping("values2", bae.add(CoreFunctions.ALL, new ObjectAccess("value")));
		Grouping grouping = new Grouping().
			withGroupingKey(new ObjectAccess("id")).
			withResultProjection(resultProjection);

		SopremoTestPlan plan = new SopremoTestPlan(grouping);
		plan.getInput(0).
			addObject("id", 1, "value", 11).
			addObject("id", 1, "value", 12).
			addObject("id", 2, "value", 21).
			addObject("id", 2, "value", 22);
		plan.getExpectedOutput(0).
			addObject("id", 1, "values1", new int[] { 11, 12 }, "values2", new int[] { 11, 12 }).
			addObject("id", 2, "values1", new int[] { 21, 22 }, "values2", new int[] { 21, 22 });

		plan.run();
	}
}
