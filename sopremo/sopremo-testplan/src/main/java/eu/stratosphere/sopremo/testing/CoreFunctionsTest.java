package eu.stratosphere.sopremo.testing;

import static eu.stratosphere.sopremo.testing.FunctionTest.assertAggregate;
import static eu.stratosphere.sopremo.testing.FunctionTest.assertReturn;
import static eu.stratosphere.sopremo.type.JsonUtil.createArrayNode;
import static eu.stratosphere.sopremo.type.JsonUtil.createCompactArray;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.stratosphere.sopremo.CoreFunctions;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.type.DoubleNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.JsonUtil;
import eu.stratosphere.sopremo.type.MissingNode;

/**
 * Tests {@link BuiltinFunctions}
 */
public class CoreFunctionsTest {

	protected EvaluationContext context = new EvaluationContext();

	/**
	 * 
	 */
	@Test
	public void shouldCoerceDataWhenSumming() {
		assertAggregate(new BigDecimal("6.5"), CoreFunctions.SUM, 1.2, 2, new BigDecimal("3.3"));
	}

	/**
	 * 
	 */
	@Test(expected = ClassCastException.class)
	public void shouldNotConcatenateObjects() {
		assertAggregate("bla1blu2", CoreFunctions.CONCAT, "bla", 1, "blu", 2);
	}

	/**
	 * 
	 */
	@Test
	public void shouldConcatenateStrings() {
		assertAggregate("blabliblu", CoreFunctions.CONCAT, "bla", "bli", "blu");
	}

	/**
	 * 
	 */
	@Test
	public void shouldCountNormalArray() {
		assertAggregate(3, CoreFunctions.COUNT, 1, 2, 3);
	}

	/**
	 * 
	 */
	@Test
	public void shouldCountZeroForEmptyArray() {
		assertAggregate(0, CoreFunctions.COUNT);
	}

	/**
	 * 
	 */
	@Test(expected = ClassCastException.class)
	public void shouldFailToSumIfNonNumbers() {
		assertAggregate(null, CoreFunctions.SUM, "test");
	}

	/**
	 * 
	 */
	@Test
	public void shouldReturnEmptyStringWhenConcatenatingEmptyArray() {
		assertAggregate("", CoreFunctions.CONCAT);
	}

	/**
	 * 
	 */
	@Test
	public void shouldSortArrays() {
		final IArrayNode<?> expected =
			createArrayNode(new Number[] { 1, 2.4 }, new Number[] { 1, 3.4 }, new Number[] { 2, 2.4 },
				new Number[] { 2, 2.4, 3 });
		assertAggregate(expected, CoreFunctions.SORT, new Number[] { 1, 3.4 }, new Number[] { 2, 2.4 },
			new Number[] { 1, 2.4 }, new Number[] { 2, 2.4, 3 });
	}

	/**
	 * 
	 */
	@Test
	public void shouldSortDoubles() {
		assertAggregate(createArrayNode(1.2, 2.0, 3.14, 4.5), CoreFunctions.SORT, 3.14, 4.5, 1.2, 2.0);
	}

	/**
	 * 
	 */
	@Test
	public void shouldSortEmptyArray() {
		assertAggregate(createArrayNode(), CoreFunctions.SORT);
	}

	/**
	 * 
	 */
	@Test
	public void shouldSortIntegers() {
		assertAggregate(createArrayNode(1, 2, 3, 4), CoreFunctions.SORT, 3, 4, 1, 2);
	}

	/**
	 * 
	 */
	@Test
	public void shouldSumDoubles() {
		assertAggregate(6.6, CoreFunctions.SUM, 1.1, 2.2, 3.3);
	}

	/**
	 * 
	 */
	@Test
	public void shouldSplitCorrectly() {
		assertReturn(createArrayNode("OpenNew", "x", "Open", "New"), CoreFunctions.SPLIT, "OpenNew x Open New", " ");
		assertReturn(createArrayNode("ZoomIn", "x", "Zoom", "In"), CoreFunctions.SPLIT, "ZoomIn x Zoom In", " ");
	}

	/**
	 * 
	 */
	@Test
	public void shouldSumEmptyArrayToZero() {
		assertAggregate(0, CoreFunctions.SUM);
	}

	/**
	 * 
	 */
	@Test
	public void shouldSumIntegers() {
		assertAggregate(6, CoreFunctions.SUM, 1, 2, 3);
	}

	/**
	 * 
	 */
	@Test
	public void shouldUnionAllCompactArrays() {
		final IArrayNode<?> expectedResult = createArrayNode(1, 2, 3, 4, 5, 6);
		assertReturn(expectedResult, CoreFunctions.UNION_ALL,
			createCompactArray(1, 2, 3), createCompactArray(4, 5), createCompactArray(6));
	}

	/**
	 * Very rare case...
	 */
	@Test
	public void shouldUnionAllMixedArrayTypes() {
		final IArrayNode<?> expectedResult = createArrayNode(1, 2, 3, 4, 5, 6);
		assertReturn(expectedResult, CoreFunctions.UNION_ALL,
			createArrayNode(1, 2, 3), createCompactArray(4, 5), JsonUtil.createArrayNode(6));
	}

	/**
	 * 
	 */
	@Test
	public void shouldUnionAllNormalArrays() {
		final IArrayNode<?> expectedResult = createArrayNode(1, 2, 3, 4, 5, 6);
		assertReturn(expectedResult, CoreFunctions.UNION_ALL,
			createArrayNode(1, 2, 3), createArrayNode(4, 5, 6));
	}

	/**
	 *
	 */
	@Test
	public void shouldUnionAllStreamArrays() {
		assertReturn(JsonUtil.createArrayNode(1, 2, 3, 4, 5, 6), CoreFunctions.UNION_ALL,
			JsonUtil.createStreamArrayNode(1, 2, 3),
			JsonUtil.createStreamArrayNode(4, 5),
			JsonUtil.createStreamArrayNode(6));
	}

	@Test
	public void shouldCalculateMean() {
		assertReturn(new BigDecimal("50"), CoreFunctions.MEAN, 50, 25, 75);
	}

	@Test
	public void shouldCalculateMeanWithDifferentNodes() {
		List<Object> numbers = new ArrayList<Object>();
		for (int i = 1; i < 500; i++)
			numbers.add(i % 2 == 0 ? IntNode.valueOf(i) : DoubleNode.valueOf(i));

		assertReturn(250.0, CoreFunctions.MEAN, numbers.toArray());
	}

	@Test
	public void shouldReturnMissingIfMeanNotAggregated() {
		assertReturn(MissingNode.getInstance(), CoreFunctions.MEAN);
	}

	@Test
	public void shouldReturnCorrectSubstring() {
		assertReturn("345", CoreFunctions.SUBSTRING, "0123456789", 3, 6);
	}

	@Test
	public void shouldCreateRightCamelCaseRepresentation() {
		assertReturn("This Is Just A Test !!!", CoreFunctions.CAMEL_CASE, "this iS JusT a TEST !!!");
	}
}
