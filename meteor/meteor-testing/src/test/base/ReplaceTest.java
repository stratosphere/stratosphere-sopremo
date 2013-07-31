package eu.stratosphere.meteor.base;

import org.junit.Test;

import eu.stratosphere.meteor.MeteorTest;
import eu.stratosphere.sopremo.base.Replace;
import eu.stratosphere.sopremo.expressions.ArithmeticExpression;
import eu.stratosphere.sopremo.expressions.ArithmeticExpression.ArithmeticOperator;
import eu.stratosphere.sopremo.expressions.ConstantExpression;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.type.JsonUtil;

public class ReplaceTest extends MeteorTest {

	@Test
	public void testReplaceAll() {
		final SopremoPlan actualPlan = this.parseScript("$persons = read from 'file://persons.json';\n" +
			"$languages = read from 'file://languages.json';\n" +
			"$normalizedPersons = replace all $person in $persons \n" +
			"	on $person.spokenLanguages\n" +
			"	with $languages;\n" +
			"write $normalizedPersons to 'file://normalizedPersons.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source persons = new Source("file://persons.json");
		final Source languages = new Source("file://languages.json");
		final Replace replace = new Replace().
			withInputs(persons, languages).
			withArrayElementsReplacement(true).
			withReplaceExpression(JsonUtil.createPath("0", "spokenLanguages"));
		final Sink normalizedPersons = new Sink("file://normalizedPersons.json").withInputs(replace);
		expectedPlan.setSinks(normalizedPersons);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void testReplaceWithDefaultValue() {
		final SopremoPlan actualPlan = this.parseScript("$persons = read from 'file://persons.json';\n" +
			"$languages = read from 'file://languages.json';\n" +
			"$normalizedPersons = replace all $person in $persons \n" +
			"	on $person.spokenLanguages\n" +
			"	with $languages\n" +
			"	default 'unknown language ' + $person.spokenLanguages;\n" +
			"write $normalizedPersons to 'file://normalizedPersons.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source persons = new Source("file://persons.json");
		final Source languages = new Source("file://languages.json");
		final Replace replace = new Replace().
			withInputs(persons, languages).
			withArrayElementsReplacement(true).
			withDefaultExpression(new ArithmeticExpression(new ConstantExpression("unknown language "),
				ArithmeticOperator.ADDITION, JsonUtil.createPath("0", "spokenLanguages"))).
			withReplaceExpression(JsonUtil.createPath("0", "spokenLanguages"));
		final Sink normalizedPersons = new Sink("file://normalizedPersons.json").withInputs(replace);
		expectedPlan.setSinks(normalizedPersons);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void testSimpleReplace() {
		final SopremoPlan actualPlan = this.parseScript("$persons = read from 'file://persons.json';\n" +
			"$nickNames = read from 'file://nickNames.json';\n" +
			"$normalizedPersons = replace $person in $persons\n" +
			"	on $person.firstName\n" +
			"	with $nickNames;\n" +
			"write $normalizedPersons to 'file://normalizedPersons.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source persons = new Source("file://persons.json");
		final Source nickNames = new Source("file://nickNames.json");
		final Replace replace = new Replace().
			withInputs(persons, nickNames).
			withReplaceExpression(JsonUtil.createPath("0", "firstName"));
		final Sink normalizedPersons = new Sink("file://normalizedPersons.json").withInputs(replace);
		expectedPlan.setSinks(normalizedPersons);

		assertPlanEquals(expectedPlan, actualPlan);
	}
}
