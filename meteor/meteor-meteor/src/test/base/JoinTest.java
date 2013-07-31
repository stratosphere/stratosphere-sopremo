package eu.stratosphere.meteor.base;

import org.junit.Test;

import eu.stratosphere.meteor.MeteorTest;
import eu.stratosphere.sopremo.base.Join;
import eu.stratosphere.sopremo.expressions.ComparativeExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression.BinaryOperator;
import eu.stratosphere.sopremo.expressions.ObjectCreation;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.type.JsonUtil;

public class JoinTest extends MeteorTest {

	@Test
	public void testJoin1() {
		final SopremoPlan actualPlan = parseScript("$users = read from 'file://users.json';\n" +
			"$pages = read from 'file://pages.json';\n" +
			"$result = join $users, $pages\n" +
			"  where $users.id == $pages.userid\n" +
			"  into { $users.name, $pages.* };\n" +
			"write $result to 'file://result.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source users = new Source("file://users.json");
		final Source pages = new Source("file://pages.json");
		final Join join = new Join().
			withInputs(users, pages).
			withJoinCondition(new ComparativeExpression(JsonUtil.createPath("0", "id"),
				BinaryOperator.EQUAL, JsonUtil.createPath("1", "userid"))).
			withResultProjection(new ObjectCreation(
				new ObjectCreation.FieldAssignment("name", JsonUtil.createPath("0", "name")),
				new ObjectCreation.CopyFields(JsonUtil.createPath("1"))
				));
		final Sink result = new Sink("file://result.json").withInputs(join);
		expectedPlan.setSinks(result);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void testJoin2() {
		final SopremoPlan actualPlan = parseScript("$users = read from 'file://users.json';\n" +
			"$pages = read from 'file://pages.json';\n" +
			"$result = join $u in $users, $p in $pages\n" +
			"  where $u.id == $p.userid\n" +
			"  into { $u.name, $p.* };\n" +
			"write $result to 'file://result.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source users = new Source("file://users.json");
		final Source pages = new Source("file://pages.json");
		final Join join = new Join().
			withInputs(users, pages).
			withJoinCondition(new ComparativeExpression(JsonUtil.createPath("0", "id"),
				BinaryOperator.EQUAL, JsonUtil.createPath("1", "userid"))).
			withResultProjection(new ObjectCreation(
				new ObjectCreation.FieldAssignment("name", JsonUtil.createPath("0", "name")),
				new ObjectCreation.CopyFields(JsonUtil.createPath("1"))
				));
		final Sink result = new Sink("file://result.json").withInputs(join);
		expectedPlan.setSinks(result);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void testOuterJoin() {
		final SopremoPlan actualPlan = parseScript("$users = read from 'file://users.json';\n" +
			"$pages = read from 'file://pages.json';\n" +
			"$result = join $u in $users, $p in $pages\n" +
			"  preserve $u\n" +
			"  where $u.id == $p.userid\n" +
			"  into { $u.name, $p.* };\n" +
			"write $result to 'file://result.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source users = new Source("file://users.json");
		final Source pages = new Source("file://pages.json");
		final Join join = new Join().
			withInputs(users, pages).
			withOuterJoinIndices(0).
			withJoinCondition(new ComparativeExpression(JsonUtil.createPath("0", "id"),
				BinaryOperator.EQUAL, JsonUtil.createPath("1", "userid"))).
			withResultProjection(new ObjectCreation(
				new ObjectCreation.FieldAssignment("name", JsonUtil.createPath("0", "name")),
				new ObjectCreation.CopyFields(JsonUtil.createPath("1"))
				));
		final Sink result = new Sink("file://result.json").withInputs(join);
		expectedPlan.setSinks(result);

		assertPlanEquals(expectedPlan, actualPlan);
	}
	
	@Test
	public void testLeftOuterJoin() {
		final SopremoPlan actualPlan = parseScript("$stocks = read from 'file://stocks.json';" +
				"$tweets = read from 'file://tweets.json';" +
				"$merged = join $s in $stocks, $t in $tweets where $s.date == $t.date" +
				"    preserve $s" +
				"    into {" +
				"        x1_volume: $s.volume," +
				"        date: $s.date," +
				"        x2_count: $t.count" +
				"    };" +
				"write $merged to 'file://merged.json';");

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source stocks = new Source("file://stocks.json");
		final Source tweets = new Source("file://tweets.json");
		final Join join = new Join().
			withInputs(stocks, tweets).
			withOuterJoinIndices(0).
			withJoinCondition(new ComparativeExpression(JsonUtil.createPath("0", "date"),
				BinaryOperator.EQUAL, JsonUtil.createPath("1", "date"))).
			withResultProjection(new ObjectCreation(
				new ObjectCreation.FieldAssignment("x1_volume", JsonUtil.createPath("0", "volume")),
				new ObjectCreation.FieldAssignment("date", JsonUtil.createPath("0", "date")),
				new ObjectCreation.FieldAssignment("x2_count", JsonUtil.createPath("1", "count"))
				));
		final Sink result = new Sink("file://merged.json").withInputs(join);
		expectedPlan.setSinks(result);

		assertPlanEquals(expectedPlan, actualPlan);
	}
}
