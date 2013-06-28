package eu.stratosphere.sopremo.base;

import static eu.stratosphere.sopremo.type.JsonUtil.createPath;

import org.junit.Test;

import eu.stratosphere.sopremo.expressions.AndExpression;
import eu.stratosphere.sopremo.expressions.ArrayCreation;
import eu.stratosphere.sopremo.expressions.BooleanExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression.BinaryOperator;
import eu.stratosphere.sopremo.expressions.ElementInSetExpression;
import eu.stratosphere.sopremo.expressions.ElementInSetExpression.Quantor;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.expressions.ObjectCreation;
import eu.stratosphere.sopremo.operator.SopremoOperatorTestBase;
import eu.stratosphere.sopremo.testing.SopremoTestPlan;
import eu.stratosphere.sopremo.type.JsonUtil;
import eu.stratosphere.sopremo.type.MissingNode;

public class JoinTest extends SopremoOperatorTestBase<Join> {
	@Override
	protected Join createDefaultInstance(final int index) {
		final ObjectCreation transformation = new ObjectCreation();
		transformation.addMapping("field", createPath("0", "[" + index + "]"));
		final BooleanExpression condition =
			new ComparativeExpression(createPath("0", "id"), BinaryOperator.EQUAL, createPath("1", "userid"));
		return new Join().withJoinCondition(condition).withResultProjection(transformation);
	}

	@Test
	public void shouldPerformAntiJoin() {
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(2, 1);

		final AndExpression condition =
			new AndExpression(new ElementInSetExpression(createPath("0", "DeptName"), Quantor.EXISTS_NOT_IN,
				createPath("1", "Name")));
		final Join join = new Join().withJoinCondition(condition);
		join.setInputs(sopremoPlan.getInputOperators(0, 2));
		sopremoPlan.getOutputOperator(0).setInputs(join);
		sopremoPlan.getInput(0).addObject("Name", "Harry", "EmpId", 3415, "DeptName", "Finance").addObject("Name",
			"Sally", "EmpId", 2241, "DeptName", "Sales")
			.addObject("Name", "George", "EmpId", 3401, "DeptName", "Finance").addObject("Name", "Harriet", "EmpId",
				2202, "DeptName", "Production");
		sopremoPlan.getInput(1).addObject("Name", "Sales", "Manager", "Harriet").addObject("Name", "Production",
			"Manager", "Charles");
		sopremoPlan.getExpectedOutput(0).addArray(
			JsonUtil.createObjectNode("Name", "Harry", "EmpId", 3415, "DeptName", "Finance"))
			.addArray(JsonUtil.createObjectNode("Name", "George", "EmpId", 3401, "DeptName", "Finance"));

		sopremoPlan.run();
	}

	@Test
	public void shouldPerformEquiJoin() {
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(2, 1);

		final AndExpression condition =
			new AndExpression(new ComparativeExpression(createPath("0", "id"), BinaryOperator.EQUAL, createPath("1",
				"userid")));
		final Join join = new Join().withJoinCondition(condition);
		join.setInputs(sopremoPlan.getInputOperators(0, 2));
		sopremoPlan.getOutputOperator(0).setInputs(join);
		sopremoPlan.getInput(0).
			addObject("name", "Jon Doe", "password", "asdf1234", "id", 1).
			addObject("name", "Jane Doe", "password", "qwertyui", "id", 2).
			addObject("name", "Max Mustermann", "password", "q1w2e3r4", "id", 3);
		sopremoPlan.getInput(1).addObject("userid", 1, "url", "code.google.com/p/jaql/").addObject("userid", 2, "url",
			"www.cnn.com")
			.addObject("userid", 1, "url", "java.sun.com/javase/6/docs/api/");
		sopremoPlan
			.getExpectedOutput(0)
			.addArray(JsonUtil.createObjectNode("name", "Jon Doe", "password", "asdf1234", "id", 1),
				JsonUtil.createObjectNode("userid", 1, "url", "code.google.com/p/jaql/"))
			.addArray(JsonUtil.createObjectNode("name", "Jon Doe", "password", "asdf1234", "id", 1),
				JsonUtil.createObjectNode("userid", 1, "url", "java.sun.com/javase/6/docs/api/"))
			.addArray(JsonUtil.createObjectNode("name", "Jane Doe", "password", "qwertyui", "id", 2),
				JsonUtil.createObjectNode("userid", 2, "url", "www.cnn.com"));

		sopremoPlan.run();
	}

	@Test
	public void shouldPerformEquiJoinOnThreeInputs() {
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(3, 1);

		final AndExpression condition =
			new AndExpression(new ComparativeExpression(createPath("0", "id"), BinaryOperator.EQUAL, createPath("1", "userid")),
				new ComparativeExpression(createPath("1", "url"), BinaryOperator.EQUAL, createPath("2", "page")));
		final ObjectCreation transformation = new ObjectCreation();
		transformation.addMapping("name", createPath("0", "name"));
		transformation.addMapping("url", createPath("1", "url"));
		transformation.addMapping("company", createPath("2", "company"));
		final Join join = new Join().withJoinCondition(condition).withResultProjection(transformation);
		join.setInputs(sopremoPlan.getInputOperators(0, 3));
		sopremoPlan.getOutputOperator(0).setInputs(join);
		sopremoPlan.getInput(0).
			addObject("name", "Jon Doe", "password", "asdf1234", "id", 1).
			addObject("name", "Jane Doe", "password", "qwertyui", "id", 2).
			addObject("name", "Max Mustermann", "password", "q1w2e3r4", "id", 3);
		sopremoPlan.getInput(1).addObject("userid", 1, "url", "code.google.com/p/jaql/").addObject("userid", 2, "url",
			"www.oracle.com")
			.addObject("userid", 1, "url", "java.sun.com/javase/6/docs/api/").addObject("userid", 3, "url",
				"www.oracle.com");
		sopremoPlan.getInput(2).addObject("page", "code.google.com/p/jaql/", "company", "ibm").addObject("page",
			"www.oracle.com", "company", "oracle")
			.addObject("page", "java.sun.com/javase/6/docs/api/", "company", "oracle");
		sopremoPlan.getExpectedOutput(0).addObject("name", "Jon Doe", "url", "code.google.com/p/jaql/", "company",
			"ibm")
			.addObject("name", "Jon Doe", "url", "java.sun.com/javase/6/docs/api/", "company", "oracle")
			.addObject("name", "Jane Doe", "url", "www.oracle.com", "company", "oracle")
			.addObject("name", "Max Mustermann", "url", "www.oracle.com", "company", "oracle");

		sopremoPlan.trace();
		sopremoPlan.run();
	}

	@Test
	public void shouldPerformCircularEquiJoinOnThreeInputs1() {
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(3, 1);

		final AndExpression condition =
			new AndExpression(
				// new ComparativeExpression(createPath("0", "companyid"),
				// BinaryOperator.EQUAL, createPath("1", "id")),
				new ComparativeExpression(createPath("1", "id"), BinaryOperator.EQUAL, createPath("0", "companyid")),
				new ComparativeExpression(createPath("1",
					"url"), BinaryOperator.EQUAL, createPath("2", "page")), new ComparativeExpression(createPath("0",
					"id"), BinaryOperator.EQUAL,
					createPath("2", "adminid")));
		final ObjectCreation transformation = new ObjectCreation();
		transformation.addMapping("name", createPath("0", "name"));
		transformation.addMapping("company", createPath("1", "companyname"));
		transformation.addMapping("page", createPath("2", "page"));
		final Join join = new Join().withJoinCondition(condition).withResultProjection(transformation);
		join.setInputs(sopremoPlan.getInputOperators(0, 3));
		sopremoPlan.getOutputOperator(0).setInputs(join);
		sopremoPlan.getInput(0).addObject("name", "Jon Doe", "id", 1, "companyid", 1).addObject("name", "Jane Doe",
			"id", 2, "companyid", 3)
			.addObject("name", "Max Mustermann", "id", 3, "companyid", 2).addObject("name", "Irene Mustermann", "id",
				4, "companyid", 2);
		sopremoPlan.getInput(1).addObject("id", 1, "companyname", "Google", "url", "www.google.com")
			.addObject("id", 2, "companyname", "Oracle", "url", "www.oracle.com").addObject("id", 3, "companyname",
				"Sun", "url", "www.sun.com")
			.addObject("id", 4, "companyname", "SAP", "url", "www.sap.com");
		sopremoPlan.getInput(2).addObject("page", "www.google.com", "adminid", 1).addObject("page", "www.oracle.com",
			"adminid", 3)
			.addObject("page", "www.sap.com", "adminid", 5).addObject("page", "www.sun.com", "adminid", 2);
		sopremoPlan.getExpectedOutput(0).addObject("name", "Jon Doe", "company", "Google", "page", "www.google.com")
			.addObject("name", "Jane Doe", "company", "Sun", "page", "www.sun.com")
			.addObject("name", "Max Mustermann", "company", "Oracle", "page", "www.oracle.com");

		sopremoPlan.run();
	}

	@Test
	public void shouldPerformCircularEquiJoinOnThreeInputs2() {
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(3, 1);

		final AndExpression condition =
			new AndExpression(
				// new ComparativeExpression(createPath("0", "companyid"),
				// BinaryOperator.EQUAL, createPath("1", "id")),
				new ComparativeExpression(createPath("1", "id"), BinaryOperator.EQUAL, createPath("0", "companyid")),
				new ComparativeExpression(createPath("1",
					"url"), BinaryOperator.EQUAL, createPath("2", "page")), new ComparativeExpression(createPath("0",
					"id"), BinaryOperator.EQUAL,
					createPath("2", "adminid")));
		final ObjectCreation transformation = new ObjectCreation();
		transformation.addMapping("name", createPath("0", "name"));
		transformation.addMapping("company", createPath("1", "companyname"));
		transformation.addMapping("page", createPath("2", "page"));
		final Join join = new Join().withJoinCondition(condition).withResultProjection(transformation);
		join.setInputs(sopremoPlan.getInputOperators(0, 3));
		sopremoPlan.getOutputOperator(0).setInputs(join);
		sopremoPlan.getInput(0).addObject("name", "Jon Doe", "id", 1, "companyid", 1).addObject("name", "Jane Doe",
			"id", 2, "companyid", 3)
			.addObject("name", "Max Mustermann", "id", 3, "companyid", 2).addObject("name", "Irene Mustermann", "id",
				4, "companyid", 2);
		sopremoPlan.getInput(1).addObject("id", 1, "companyname", "Google", "url", "www.google.com")
			.addObject("id", 2, "companyname", "Oracle", "url", "www.oracle.com").addObject("id", 3, "companyname",
				"Sun", "url", "www.sun.com")
			.addObject("id", 4, "companyname", "SAP", "url", "www.sap.com");
		sopremoPlan.getInput(2).addObject("page", "www.google.com", "adminid", 1).addObject("page", "www.oracle.com",
			"adminid", 3)
			.addObject("page", "www.sap.com", "adminid", 5).addObject("page", "www.sun.com", "adminid", 2);
		sopremoPlan.getExpectedOutput(0).addObject("name", "Jon Doe", "company", "Google", "page", "www.google.com")
			.addObject("name", "Jane Doe", "company", "Sun", "page", "www.sun.com")
			.addObject("name", "Max Mustermann", "company", "Oracle", "page", "www.oracle.com");

		sopremoPlan.run();
	}

	@Test
	public void shouldFindCircleThroughJoin() {
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(3, 1);

		final AndExpression condition =
			new AndExpression(new ComparativeExpression(createPath("0", "fk"), BinaryOperator.EQUAL, createPath("1",
				"kryo")),
				new ComparativeExpression(createPath("2", "fk"), BinaryOperator.EQUAL, createPath("0", "kryo")),
				new ComparativeExpression(createPath("1", "fk"),
					BinaryOperator.EQUAL, createPath("2", "kryo")));
		final ObjectCreation transformation = new ObjectCreation();
		transformation.addMapping("k1", createPath("0", "kryo"));
		transformation.addMapping("k2", createPath("1", "kryo"));
		transformation.addMapping("k3", createPath("2", "kryo"));
		final Join join = new Join().withJoinCondition(condition).withResultProjection(transformation);
		join.setInputs(sopremoPlan.getInputOperators(0, 3));
		sopremoPlan.getOutputOperator(0).setInputs(join);
		for (int i = 0; i <= 2; i++)
			sopremoPlan.getInput(i).addObject("kryo", 1, "fk", 2).addObject("kryo", 2, "fk", 3).addObject("kryo", 3,
				"fk", 1);
		sopremoPlan.getExpectedOutput(0).addObject("k1", 1, "k2", 2, "k3", 3).addObject("k1", 2, "k2", 3, "k3", 1).addObject(
			"k1", 3, "k2", 1, "k3", 2);

		sopremoPlan.run();
	}

	@Test
	public void shouldFindTriangleThroughJoin() {
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(3, 1);

		final AndExpression condition =
			new AndExpression(new ComparativeExpression(createPath("1", "fk"), BinaryOperator.EQUAL, createPath("2",
				"fk")),
				new ComparativeExpression(createPath("2", "kryo"), BinaryOperator.EQUAL, createPath("0", "kryo")),
				new ComparativeExpression(createPath("0", "fk"),
					BinaryOperator.EQUAL, createPath("1", "kryo")));
		final ObjectCreation transformation = new ObjectCreation();
		transformation.addMapping("k1", createPath("0", "kryo"));
		transformation.addMapping("k2", createPath("1", "kryo"));
		transformation.addMapping("k3", createPath("2", "fk"));
		final Join join = new Join().withJoinCondition(condition).withResultProjection(transformation);
		join.setInputs(sopremoPlan.getInputOperators(0, 3));
		sopremoPlan.getOutputOperator(0).setInputs(join);
		for (int i = 0; i <= 2; i++)
			sopremoPlan.getInput(i).addObject("kryo", 1, "fk", 2).addObject("kryo", 2, "fk", 3).addObject("kryo", 1,
				"fk", 3);
		sopremoPlan.getExpectedOutput(0).addObject("k1", 1, "k2", 2, "k3", 3);

		sopremoPlan.run();
	}

	@Test
	public void shouldFindAlignedTrianglesThroughJoin() {
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(5, 1);

		final AndExpression condition =
			new AndExpression(new ComparativeExpression(createPath("0", "t"), BinaryOperator.EQUAL,
				createPath("1", "s")),
				new ComparativeExpression(createPath("1", "t"), BinaryOperator.EQUAL, createPath("2", "s")),
				new ComparativeExpression(createPath("2", "t"),
					BinaryOperator.EQUAL, createPath("0", "s")),
				new ComparativeExpression(createPath("0", "t"), BinaryOperator.EQUAL, createPath("3", "s")),
				new ComparativeExpression(createPath("3", "t"),
					BinaryOperator.EQUAL, createPath("4", "s")),
				new ComparativeExpression(createPath("4", "t"), BinaryOperator.EQUAL, createPath("0", "s")),
				new ComparativeExpression(createPath("2", "s"),
					BinaryOperator.NOT_EQUAL, createPath("4", "s")));
		final ObjectCreation transformation = new ObjectCreation();
		transformation.addMapping("s1", createPath("0", "s"));
		transformation.addMapping("s2", createPath("1", "s"));
		transformation.addMapping("s3", createPath("2", "s"));
		transformation.addMapping("s4", createPath("4", "s"));
		final Join join = new Join().withJoinCondition(condition).withResultProjection(transformation);
		join.setInputs(sopremoPlan.getInputOperators(0, 5));
		sopremoPlan.getOutputOperator(0).setInputs(join);
		for (int i = 0; i <= 4; i++)
			sopremoPlan.getInput(i).addObject("s", 1, "t", 3).addObject("s", 3, "t", 2).addObject("s", 2, "t", 1).addObject(
				"s", 3, "t", 4)
				.addObject("s", 4, "t", 1);
		sopremoPlan.getExpectedOutput(0).addObject("s1", 1, "s2", 3, "s3", 2, "s4", 4).addObject("s1", 1, "s2", 3,
			"s3", 4, "s4", 2);

		sopremoPlan.run();
	}

	@Test
	public void shouldPerformFullOuterJoin() {
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(2, 1);

		// here we set outer join flag
		final EvaluationExpression leftJoinKey = createPath("0", "id");
		final EvaluationExpression rightJoinKey = createPath("1", "userid");
		final AndExpression condition =
			new AndExpression(new ComparativeExpression(leftJoinKey, BinaryOperator.EQUAL, rightJoinKey));
		final Join join =
			new Join().withJoinCondition(condition).withOuterJoinSources(
				new ArrayCreation(new InputSelection(0), new InputSelection(1)));
		join.setInputs(sopremoPlan.getInputOperators(0, 2));
		sopremoPlan.getOutputOperator(0).setInputs(join);
		sopremoPlan.getInput(0).addObject("name", "Jon Doe", "password", "asdf1234", "id", 1).addObject("name",
			"Jane Doe", "password", "qwertyui", "id", 2)
			.addObject("name", "Max Mustermann", "password", "q1w2e3r4", "id", 3);
		sopremoPlan.getInput(1).addObject("userid", 1, "url", "code.google.com/p/jaql/").addObject("userid", 2, "url",
			"www.cnn.com")
			.addObject("userid", 4, "url", "www.nbc.com").addObject("userid", 1, "url",
				"java.sun.com/javase/6/docs/api/");
		sopremoPlan
			.getExpectedOutput(0)
			.addArray(JsonUtil.createObjectNode("name", "Jon Doe", "password", "asdf1234", "id", 1),
				JsonUtil.createObjectNode("userid", 1, "url", "code.google.com/p/jaql/"))
			.addArray(JsonUtil.createObjectNode("name", "Jon Doe", "password", "asdf1234", "id", 1),
				JsonUtil.createObjectNode("userid", 1, "url", "java.sun.com/javase/6/docs/api/"))
			.addArray(JsonUtil.createObjectNode("name", "Jane Doe", "password", "qwertyui", "id", 2),
				JsonUtil.createObjectNode("userid", 2, "url", "www.cnn.com"))
			.addArray(JsonUtil.createObjectNode("name", "Max Mustermann", "password", "q1w2e3r4", "id", 3),
				new MissingNode())
			.addArray(new MissingNode(), JsonUtil.createObjectNode("userid", 4, "url", "www.nbc.com"));

		sopremoPlan.run();
	}

	@Test
	public void shouldPerformLeftOuterJoin() {
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(2, 1);

		// here we set outer join flag
		final EvaluationExpression leftJoinKey = createPath("0", "id");
		final AndExpression condition =
			new AndExpression(new ComparativeExpression(leftJoinKey, BinaryOperator.EQUAL, createPath("1", "userid")));
		final Join join = new Join().withJoinCondition(condition).withOuterJoinSources(new InputSelection(0));
		join.setInputs(sopremoPlan.getInputOperators(0, 2));
		sopremoPlan.getOutputOperator(0).setInputs(join);
		sopremoPlan.getInput(0).addObject("name", "Jon Doe", "password", "asdf1234", "id", 1).addObject("name",
			"Jane Doe", "password", "qwertyui", "id", 2)
			.addObject("name", "Max Mustermann", "password", "q1w2e3r4", "id", 3);
		sopremoPlan.getInput(1).addObject("userid", 1, "url", "code.google.com/p/jaql/").addObject("userid", 2, "url",
			"www.cnn.com")
			.addObject("userid", 1, "url", "java.sun.com/javase/6/docs/api/").addObject("userid", 9, "url",
				"java.sun.com/javase/6/docs/api/");
		sopremoPlan
			.getExpectedOutput(0)
			.addArray(JsonUtil.createObjectNode("name", "Jon Doe", "password", "asdf1234", "id", 1),
				JsonUtil.createObjectNode("userid", 1, "url", "code.google.com/p/jaql/"))
			.addArray(JsonUtil.createObjectNode("name", "Jon Doe", "password", "asdf1234", "id", 1),
				JsonUtil.createObjectNode("userid", 1, "url", "java.sun.com/javase/6/docs/api/"))
			.addArray(JsonUtil.createObjectNode("name", "Jane Doe", "password", "qwertyui", "id", 2),
				JsonUtil.createObjectNode("userid", 2, "url", "www.cnn.com"))
			.addArray(JsonUtil.createObjectNode("name", "Max Mustermann", "password", "q1w2e3r4", "id", 3),
				new MissingNode());
		sopremoPlan.run();
	}

	@Test
	public void shouldPerformLeftOuterJoin2() {
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(2, 1);

		// here we set outer join flag
		final Join join =
			new Join()
				.withOuterJoinIndices(0)
				.withJoinCondition(
					new ComparativeExpression(JsonUtil.createPath("0", "date"), BinaryOperator.EQUAL,
						JsonUtil.createPath("1", "date")))
				.withResultProjection(
					new ObjectCreation(new ObjectCreation.FieldAssignment("x1_volume", JsonUtil.createPath("0",
						"volume")),
						new ObjectCreation.FieldAssignment("date", JsonUtil.createPath("0", "date")),
						new ObjectCreation.FieldAssignment("x2_count",
							JsonUtil.createPath("1", "count"))));
		join.setInputs(sopremoPlan.getInputOperators(0, 2));
		sopremoPlan.getOutputOperator(0).setInputs(join);
		sopremoPlan.getInput(0).addObject("volume", 1234, "date", "2011-01-01").addObject("volume", 1234, "date",
			"2011-01-02")
			.addObject("volume", 1234, "date", "2011-01-03");
		sopremoPlan.getInput(1).addObject("count", 3, "date", "2011-01-01");
		sopremoPlan.getExpectedOutput(0).addObject("x2_count", 3, "x1_volume", 1234, "date", "2011-01-01").addObject(
			"x1_volume", 1234, "date", "2011-01-02")
			.addObject("x1_volume", 1234, "date", "2011-01-03");

		sopremoPlan.run();
	}

	@Test
	public void shouldPerformRightOuterJoin() {
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(2, 1);

		// here we set outer join flag
		final EvaluationExpression rightJoinKey = createPath("1", "userid");
		final AndExpression condition =
			new AndExpression(new ComparativeExpression(createPath("0", "id"), BinaryOperator.EQUAL, rightJoinKey));
		final Join join = new Join().withJoinCondition(condition).withOuterJoinSources(new InputSelection(1));
		join.setInputs(sopremoPlan.getInputOperators(0, 2));
		sopremoPlan.getOutputOperator(0).setInputs(join);
		sopremoPlan.getInput(0).addObject("name", "Jon Doe", "password", "asdf1234", "id", 1).addObject("name",
			"Jane Doe", "password", "qwertyui", "id", 2)
			.addObject("name", "Max Mustermann", "password", "q1w2e3r4", "id", 3);
		sopremoPlan.getInput(1).addObject("userid", 1, "url", "code.google.com/p/jaql/").addObject("userid", 2, "url",
			"www.cnn.com")
			.addObject("userid", 4, "url", "www.nbc.com").addObject("userid", 1, "url",
				"java.sun.com/javase/6/docs/api/");
		sopremoPlan
			.getExpectedOutput(0)
			.addArray(JsonUtil.createObjectNode("name", "Jon Doe", "password", "asdf1234", "id", 1),
				JsonUtil.createObjectNode("userid", 1, "url", "code.google.com/p/jaql/"))
			.addArray(JsonUtil.createObjectNode("name", "Jon Doe", "password", "asdf1234", "id", 1),
				JsonUtil.createObjectNode("userid", 1, "url", "java.sun.com/javase/6/docs/api/"))
			.addArray(JsonUtil.createObjectNode("name", "Jane Doe", "password", "qwertyui", "id", 2),
				JsonUtil.createObjectNode("userid", 2, "url", "www.cnn.com"))
			.addArray(new MissingNode(), JsonUtil.createObjectNode("userid", 4, "url", "www.nbc.com"));
		sopremoPlan.run();
	}

	@Test
	public void shouldPerformSemiJoin() {
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(2, 1);

		final AndExpression condition =
			new AndExpression(new ElementInSetExpression(createPath("0", "DeptName"), Quantor.EXISTS_IN, createPath(
				"1", "Name")));
		final Join join = new Join().withJoinCondition(condition);
		join.setInputs(sopremoPlan.getInputOperators(0, 2));
		sopremoPlan.getOutputOperator(0).setInputs(join);
		sopremoPlan.getInput(0).addObject("Name", "Harry", "EmpId", 3415, "DeptName", "Finance").addObject("Name",
			"Sally", "EmpId", 2241, "DeptName", "Sales")
			.addObject("Name", "George", "EmpId", 3401, "DeptName", "Finance").addObject("Name", "Harriet", "EmpId",
				2202, "DeptName", "Production");
		sopremoPlan.getInput(1).addObject("Name", "Sales", "Manager", "Harriet").addObject("Name", "Production",
			"Manager", "Charles");
		sopremoPlan.getExpectedOutput(0).addArray(
			JsonUtil.createObjectNode("Name", "Sally", "EmpId", 2241, "DeptName", "Sales"))
			.addArray(JsonUtil.createObjectNode("Name", "Harriet", "EmpId", 2202, "DeptName", "Production"));

		sopremoPlan.run();
	}

	@Test
	public void shouldPerformThetaJoin() {
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(2, 1);

		final AndExpression condition =
			new AndExpression(new ComparativeExpression(createPath("0", "id"), BinaryOperator.LESS, createPath("1",
				"userid")));
		final Join join = new Join().withJoinCondition(condition);
		join.setInputs(sopremoPlan.getInputOperators(0, 2));
		sopremoPlan.getOutputOperator(0).setInputs(join);
		sopremoPlan.getInput(0).addObject("name", "Jon Doe", "password", "asdf1234", "id", 1).addObject("name",
			"Jane Doe", "password", "qwertyui", "id", 2)
			.addObject("name", "Max Mustermann", "password", "q1w2e3r4", "id", 3);
		sopremoPlan.getInput(1).addObject("userid", 1, "url", "code.google.com/p/jaql/").addObject("userid", 2, "url",
			"www.cnn.com")
			.addObject("userid", 4, "url", "www.nbc.com").addObject("userid", 1, "url",
				"java.sun.com/javase/6/docs/api/");
		sopremoPlan
			.getExpectedOutput(0)
			.addArray(JsonUtil.createObjectNode("name", "Jon Doe", "password", "asdf1234", "id", 1),
				JsonUtil.createObjectNode("userid", 2, "url", "www.cnn.com"))
			.addArray(JsonUtil.createObjectNode("name", "Jon Doe", "password", "asdf1234", "id", 1),
				JsonUtil.createObjectNode("userid", 4, "url", "www.nbc.com"))
			.addArray(JsonUtil.createObjectNode("name", "Jane Doe", "password", "qwertyui", "id", 2),
				JsonUtil.createObjectNode("userid", 4, "url", "www.nbc.com"))
			.addArray(JsonUtil.createObjectNode("name", "Max Mustermann", "password", "q1w2e3r4", "id", 3),
				JsonUtil.createObjectNode("userid", 4, "url", "www.nbc.com"));

		sopremoPlan.run();
	}
}
