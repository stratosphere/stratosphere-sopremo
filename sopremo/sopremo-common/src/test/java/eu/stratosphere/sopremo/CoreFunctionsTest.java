package eu.stratosphere.sopremo;

import static eu.stratosphere.sopremo.FunctionTest.assertAggregate;
import static eu.stratosphere.sopremo.FunctionTest.assertReturn;
import static eu.stratosphere.sopremo.type.JsonUtil.createArrayNode;
import static eu.stratosphere.sopremo.type.JsonUtil.createCompactArray;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import eu.stratosphere.sopremo.io.JsonParseException;
import eu.stratosphere.sopremo.io.JsonParser;
import eu.stratosphere.sopremo.type.DoubleNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.JavaToJsonMapper;
import eu.stratosphere.sopremo.type.JsonUtil;
import eu.stratosphere.sopremo.type.NullNode;

/**
 * Tests {@link BuiltinFunctions}
 * 
 */
public class CoreFunctionsTest {

	protected EvaluationContext context = new EvaluationContext();

	/**
	 * 
	 */
	@Test
	public void shouldCoerceDataWhenSumming() {
		assertAggregate(6.4, CoreFunctions.SUM, 1.1, 2, new BigDecimal("3.3"));
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

	// /**
	// *
	// */
	// @Test
	// public void shouldUnionAllStreamArrays() {
	// Assert.assertEquals(
	// JsonUtil.createArrayNode(1, 2, 3, 4, 5, 6),
	// CoreFunctions.unionAll(JsonUtil.createArrayNode(1, 2, 3), JsonUtil.createArrayNode(4, 5),
	// JsonUtil.createArrayNode(6)));
	// }

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

	// Assertion failed: Expected <NaN> but was: <NaN>
	// @Test
	// public void shouldReturnNanIfMeanNotAggregated() {
	// BuiltinFunctions.AVERAGE.initialize();
	//
	// Assert.assertEquals(DoubleNode.valueOf(Double.NaN),
	// BuiltinFunctions.AVERAGE.getFinalAggregate());
	// }

	@Test
	public void shouldReturnCorrectSubstring() {
		assertReturn("345", CoreFunctions.SUBSTRING, "0123456789", 3, 6);
	}

	@Test
	public void shouldCreateRightCamelCaseRepresentation() {
		assertReturn("This Is Just A Test !!!", CoreFunctions.CAMEL_CASE, "this iS JusT a TEST !!!");
	}
	@Test
	public void shouldreturnRightDMdata()  {

		String  aString="jsonDataMarketApi([\n{\n\"ds\":\"178f!ikl=5v\",\n\"status\":\"success\",\n\"id\":\"178f\",\n\"title\":\"GDP (current US$)\",\n\"columns\":[\n{\n\"dimension_id\":\"1\",\n\"title\":\"Year\",\n\"cid\":\"year\",\n\"time_granularity\":\"year\"\n},\n{\n\"cid\":\"-5445889268476685030\",\n\"dimension_values\":[],\n\"title\":\"GDP (current US$)\"\n}\n],\n\"applied_filters\":[\n{\n\"dimension_id\":\"ikl\",\n\"title\":\"Country\",\n\"values\":[\n{\n\"id\":\"5v\",\n\"value\":\"Spain\"\n}\n]\n}\n],\n\"data\":[\n[\n\"2011\",\n1490809722222.22\n]\n]\n}\n])";
		
		assertReturn( aString,CoreFunctions.GETDMDATA,"178f!ikl=5v&&mindate=2011-01-01");
	}
	@Test
	public void shouldreturnConvertedDMdata() throws IOException {
		
		String input= this.getResourceContent("DataMarketAPITest/resultDM.json");
		String expectedOutput = this.getResourceContent("DataMarketAPITest/resultDM_converted.json");
		JsonParser parser = new JsonParser(expectedOutput);
		IJsonNode expected;
		try {
			expected = parser.readValueAsTree();
		} catch (JsonParseException e) {
			expected = NullNode.getInstance();
		}
		@SuppressWarnings("unchecked")
		final IArrayNode<IJsonNode> params = (IArrayNode<IJsonNode>) JavaToJsonMapper.INSTANCE.valueToTree(new Object[] {input});
		final IJsonNode result = CoreFunctions.JSONCONVERTER.call(params);
		Assert.assertEquals(expected, result);
	}
	
	private String getResourceContent(final String name) throws IOException {
		ClassLoader loader = CoreFunctionsTest.class.getClassLoader();
		File file = new File (loader.getResources(name).nextElement().getPath());
		String content = FileUtils.readFileToString(file);
		return content;
	}
}
