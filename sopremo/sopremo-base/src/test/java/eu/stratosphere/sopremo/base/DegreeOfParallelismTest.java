package eu.stratosphere.sopremo.base;

import static eu.stratosphere.sopremo.type.JsonUtil.createPath;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.stratosphere.sopremo.expressions.BinaryBooleanExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression.BinaryOperator;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.Operator;

public class DegreeOfParallelismTest {
	@Test
	public void shouldKeepDegreeOfParallelism() {
		final BinaryBooleanExpression condition = new ComparativeExpression(createPath("1", "id"),
			BinaryOperator.EQUAL, createPath("0", "userid"));
		final Join join = new Join().withJoinCondition(condition);
		join.setDegreeOfParallelism(42);

		for (ElementaryOperator<?> elementaryOperator : join.asElementaryOperators(null).getReachableNodes())
			if (elementaryOperator instanceof TwoSourceJoin)
				assertEquals(elementaryOperator.getDegreeOfParallelism(), join.getDegreeOfParallelism());
			else
				assertEquals(elementaryOperator.getDegreeOfParallelism(), Operator.STANDARD_DEGREE_OF_PARALLELISM);
	}
}
