package eu.stratosphere.sopremo.expressions;

import static eu.stratosphere.sopremo.type.JsonUtil.createArrayNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Assert;
import org.junit.Test;

import eu.stratosphere.sopremo.function.ExpressionFunction;
import eu.stratosphere.sopremo.function.FunctionUtil;
import eu.stratosphere.sopremo.function.SopremoFunction;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.type.DoubleNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.INumericNode;

public class FunctionCallTest extends EvaluableExpressionTest<FunctionCall> {

	@Override
	protected FunctionCall createDefaultInstance(final int index) {
		return new FunctionCall(new ExpressionFunction(0, new ConstantExpression(index)));
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.OrExpressionTest#initVerifier(nl.jqno.equalsverifier.EqualsVerifier)
	 */
	@Override
	protected void initVerifier(EqualsVerifier<FunctionCall> equalVerifier) {
		super.initVerifier(equalVerifier);
		equalVerifier.withPrefabValues(List.class, new ArrayList<Object>(), new ArrayList<EvaluationExpression>(
			Collections.singleton(EvaluationExpression.VALUE)));
		equalVerifier.withPrefabValues(SopremoFunction.class, new ExpressionFunction(0, new ConstantExpression("red")),
			new ExpressionFunction(1, new ConstantExpression("black")));
	}

	@Test
	public void shouldCallFunction() {
		final IJsonNode result = FunctionUtil.createFunctionCall(
			FunctionCallTest.class, "sum", new ArrayAccess(0), new ArrayAccess(1)).
			evaluate(createArrayNode(1, 2));
		Assert.assertEquals(new DoubleNode(3), result);
	}

	@Name(verb = "sum")
	public static DoubleNode sum(final INumericNode... nodes) {
		double sum = 0.0;
		for (final INumericNode node : nodes)
			sum += node.getDoubleValue();
		return new DoubleNode(sum);
	}
}
