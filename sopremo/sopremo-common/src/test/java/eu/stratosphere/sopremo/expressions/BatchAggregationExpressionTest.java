package eu.stratosphere.sopremo.expressions;

import static eu.stratosphere.sopremo.expressions.ExpressionUtil.makePath;
import static eu.stratosphere.sopremo.type.JsonUtil.createArrayNode;

import java.util.Arrays;

import junit.framework.Assert;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

import eu.stratosphere.sopremo.CoreFunctions;
import eu.stratosphere.sopremo.expressions.ArithmeticExpression.ArithmeticOperator;
import eu.stratosphere.sopremo.expressions.BatchAggregationExpression.Partial;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.INumericNode;
import eu.stratosphere.sopremo.type.IntNode;

public class BatchAggregationExpressionTest extends EvaluableExpressionTest<BatchAggregationExpression> {
	@Override
	protected BatchAggregationExpression createDefaultInstance(final int index) {
		switch (index) {
		case 0:
			return new BatchAggregationExpression(CoreFunctions.MAX);
		case 1:
			return new BatchAggregationExpression(CoreFunctions.COUNT);
		case 2:
			return new BatchAggregationExpression(CoreFunctions.FIRST);
		default:
			return new BatchAggregationExpression(CoreFunctions.ALL);
		}
	}

	@Before
	public void setup() {
	}

	@Override
	protected void initVerifier(final EqualsVerifier<BatchAggregationExpression> equalVerifier) {
		super.initVerifier(equalVerifier);

		equalVerifier.withPrefabValues(IJsonNode.class, IntNode.valueOf(23), IntNode.valueOf(42));
	}

	@Test
	public void shouldPerformBatch() {
		final BatchAggregationExpression batch = this.createBatchExpression();
		final IJsonNode result = batch.evaluate(createArrayNode(2, 3, 4, 5, 1));

		Assert.assertTrue(result instanceof IArrayNode);
		final IArrayNode<?> resultArray = (IArrayNode<?>) result;
		final double[] doubleResult = new double[resultArray.size()];
		for (int index = 0; index < doubleResult.length; index++) {
			Assert.assertTrue(resultArray.get(index) instanceof INumericNode);
			doubleResult[index] = ((INumericNode) resultArray.get(index)).getDoubleValue();
		}

		final double[] expected = { 1 + 2 + 3 + 4 + 5, 5, 5 * 5 };
		Assert.assertTrue(Arrays.equals(expected, doubleResult));
	}

	private BatchAggregationExpression createBatchExpression() {
		final BatchAggregationExpression batch = new BatchAggregationExpression(CoreFunctions.SUM);
		batch.add(CoreFunctions.MAX);
		batch.add(CoreFunctions.MAX, new ArithmeticExpression(EvaluationExpression.VALUE,
			ArithmeticOperator.MULTIPLICATION, EvaluationExpression.VALUE));
		return batch;
	}

	@Test
	public void shouldReuseTarget() {
		final BatchAggregationExpression batch = this.createBatchExpression();
		final IJsonNode result1 = batch.evaluate(createArrayNode(2, 3, 4, 5, 1));
		final IJsonNode result2 = batch.evaluate(createArrayNode(2, 3));

		Assert.assertSame(result1, result2);
	}

	@Test
	public void testPartialClone() throws IllegalAccessException {
		final BatchAggregationExpression original = this.createBatchExpression();
		final Partial partial1Clone = (Partial) original.getPartial(0).clone();
		final Partial partial2Clone = (Partial) original.getPartial(1).clone();

		this.testPropertyClone(BatchAggregationExpression.class, original, partial1Clone.getBatch());
		Assert.assertSame(partial1Clone.getBatch(), partial2Clone.getBatch());
	}

	@Test
	public void testMultipleClones() throws IllegalAccessException {
		final BatchAggregationExpression original = this.createBatchExpression();
		final Partial partial1Clone = (Partial) original.getPartial(0).clone();
		final Partial partial2Clone = (Partial) original.getPartial(1).clone();
		final Partial partial1Clone2 = (Partial) original.getPartial(0).clone();
		final Partial partial2Clone2 = (Partial) original.getPartial(1).clone();

		this.testPropertyClone(BatchAggregationExpression.class, original, partial1Clone.getBatch());
		this.testPropertyClone(BatchAggregationExpression.class, original, partial1Clone2.getBatch());
		this.testPropertyClone(BatchAggregationExpression.class, partial1Clone.getBatch(), partial1Clone2.getBatch());
		Assert.assertSame(partial1Clone.getBatch(), partial2Clone.getBatch());
		Assert.assertSame(partial1Clone2.getBatch(), partial2Clone2.getBatch());
	}

	@Test
	public void testSuccessiveClones() throws IllegalAccessException {
		final BatchAggregationExpression original = this.createBatchExpression();
		final Partial partial1Clone = (Partial) original.getPartial(0).clone();
		final Partial partial2Clone = (Partial) original.getPartial(1).clone();
		final Partial partial1Clone2 = (Partial) partial1Clone.clone();
		final Partial partial2Clone2 = (Partial) partial2Clone.clone();

		this.testPropertyClone(BatchAggregationExpression.class, original, partial1Clone.getBatch());
		this.testPropertyClone(BatchAggregationExpression.class, original, partial1Clone2.getBatch());
		this.testPropertyClone(BatchAggregationExpression.class, partial1Clone.getBatch(), partial1Clone2.getBatch());
		Assert.assertSame(partial1Clone.getBatch(), partial2Clone.getBatch());
		Assert.assertSame(partial1Clone2.getBatch(), partial2Clone2.getBatch());
	}
	
	@Override
	@Before
	public void initContext() {
		super.initContext();
		this.context.getFunctionRegistry().put(CoreFunctions.class);
	}
	
	@Test
	public void shouldSerializeComplexAggregation() {
		final ObjectCreation transformation = new ObjectCreation();
		transformation.addMapping("dept",
			makePath(new InputSelection(0), new ArrayAccess(0), new ObjectAccess("dept")));
		transformation.addMapping("deptName",
			makePath(new InputSelection(1), new ArrayAccess(0), new ObjectAccess("name")));
		transformation.addMapping("emps", new FunctionCall("sort", this.context,
			makePath(new InputSelection(0), new ArrayProjection(new ObjectAccess("id")))));
		transformation.addMapping("numEmps", new FunctionCall("count", this.context, new InputSelection(0)));

		final EvaluationExpression aggregation = ExpressionUtil.replaceAggregationWithBatchAggregation(
			ExpressionUtil.replaceIndexAccessWithAggregation(transformation));
		
		testKryoSerialization(aggregation);
	}
}
