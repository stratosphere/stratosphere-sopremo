package eu.stratosphere.meteor.base;

import org.junit.Test;

import eu.stratosphere.meteor.MeteorTest;
import eu.stratosphere.sopremo.base.Difference;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.SopremoPlan;

public class DifferenceTest extends MeteorTest {

	@Test
	public void testDifference() {
		final SopremoPlan actualPlan = parseScript("$oldUsers = read from 'file://oldUsers.json';\n" +
			"$currentUsers = read from 'file://currentUsers.json';\n" +
			"$newUsers = subtract $currentUsers, $oldUsers;\n" +
			"write $newUsers to 'file://newUsers.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source old = new Source("file://oldUsers.json");
		final Source current = new Source("file://currentUsers.json");
		final Difference difference = new Difference().
			withInputs(current, old);
		final Sink output = new Sink("file://newUsers.json").
			withInputs(difference);
		expectedPlan.setSinks(output);

		assertPlanEquals(expectedPlan, actualPlan);
	}

}
