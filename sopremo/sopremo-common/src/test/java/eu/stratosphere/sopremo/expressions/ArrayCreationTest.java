package eu.stratosphere.sopremo.expressions;

import static eu.stratosphere.sopremo.type.JsonUtil.createArrayNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IntNode;

public class ArrayCreationTest extends EvaluableExpressionTest<ArrayCreation> {

	@Override
	protected ArrayCreation createDefaultInstance(final int index) {
		return new ArrayCreation(new ConstantExpression(IntNode.valueOf(index)));
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.OrExpressionTest#initVerifier(nl.jqno.equalsverifier.EqualsVerifier)
	 */
	@Override
	protected void initVerifier(final EqualsVerifier<ArrayCreation> equalVerifier) {
		super.initVerifier(equalVerifier);
		equalVerifier.withPrefabValues(List.class, new ArrayList<Object>(), new ArrayList<EvaluationExpression>(
			Collections.singleton(EvaluationExpression.VALUE)));
	}

	@Test
	public void shouldCreateArrayWithListAsParam() {
		final List<EvaluationExpression> list = new ArrayList<EvaluationExpression>();
		list.add(new ConstantExpression(IntNode.valueOf(0)));
		list.add(EvaluationExpression.VALUE);

		final IJsonNode result = new ArrayCreation(list).evaluate(IntNode.valueOf(42));

		Assert.assertEquals(createArrayNode(IntNode.valueOf(0), IntNode.valueOf(42)), result);
	}

	@Test
	public void shouldReuseTarget() {
		final ArrayCreation arrayCreation = new ArrayCreation(new ConstantExpression(IntNode.valueOf(42)));
		final IJsonNode result1 = arrayCreation.evaluate(IntNode.valueOf(42));
		final IJsonNode result2 = arrayCreation.evaluate(IntNode.valueOf(42));

		Assert.assertEquals(new ArrayNode<IJsonNode>(IntNode.valueOf(42)), result1);
		Assert.assertSame(result1, result2);
		Assert.assertEquals(new ArrayNode<IJsonNode>(IntNode.valueOf(42)), result2);
	}
}
