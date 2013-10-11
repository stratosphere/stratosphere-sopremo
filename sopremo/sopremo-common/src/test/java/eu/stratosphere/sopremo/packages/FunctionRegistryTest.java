package eu.stratosphere.sopremo.packages;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.EvaluationException;
import eu.stratosphere.sopremo.function.JavaMethod;
import eu.stratosphere.sopremo.function.SopremoFunction;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.INumericNode;
import eu.stratosphere.sopremo.type.IPrimitiveNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.JsonUtil;
import eu.stratosphere.sopremo.type.ObjectNode;
import eu.stratosphere.sopremo.type.TextNode;

public class FunctionRegistryTest {
	private IFunctionRegistry registry;

	private EvaluationContext context;

	private static final IJsonNode GENERIC_VARARG_NODE = new TextNode("var");

	private static final IJsonNode GENERIC_NODE = new TextNode("generic");

	private static final IJsonNode ARRAY_NODE = new TextNode("array");

	private static final IJsonNode TWO_INT_NODE = new TextNode("2 int");

	private static final IJsonNode ONE_INT_VARARG_NODE = new TextNode("1 int + var");

	private static final IJsonNode SUM_NODE = new TextNode("sum");

	@Before
	public void setup() {
		this.context = new EvaluationContext();
		this.registry = this.context.getFunctionRegistry();
	}

	@Test
	public void shouldAddJavaFunctions() {
		this.registry.put(JavaFunctions.class);

		Assert.assertEquals("should have been 2 functions", 2, this.registry.keySet().size());
		for (final String name : this.registry.keySet())
			Assert.assertEquals("should have been a java function", JavaMethod.class,
				this.registry.get(name).getClass());

		Assert.assertEquals("should have been 5 count signatures", 5,
			((JavaMethod) this.registry.get("count")).getSignatures().size());
		Assert.assertEquals("should have been 1 sum signatures", 1,
			((JavaMethod) this.registry.get("sum")).getSignatures().size());
	}

	@Test(expected = EvaluationException.class)
	public void shouldFailIfNoApproporiateMatchingJavaFunction() {
		this.registry.put(JavaFunctions.class);

		this.evaluate("sum", new IntNode(1), new IntNode(2), new TextNode("3"));
	}

	@Test
	public void shouldInvokeArrayJavaFunctionForArrayNode() {
		this.registry.put(JavaFunctions.class);

		Assert.assertSame(ARRAY_NODE, this.evaluate("count", new ArrayNode<IJsonNode>()));
	}

	private IJsonNode evaluate(String name, IJsonNode... parameters) {
		final SopremoFunction method = (SopremoFunction) this.registry.get(name);
		Assert.assertNotNull(method);
		return method.call(JsonUtil.asArray(parameters));
	}

	@Test
	public void shouldInvokeDerivedVarargJavaFunction() {
		this.registry.put(JavaFunctions.class);

		Assert.assertSame(SUM_NODE, this.evaluate("sum", new IntNode(1), new IntNode(2), new IntNode(3)));
		Assert.assertSame(SUM_NODE, this.evaluate("sum", new IntNode(1)));
	}

	@Test
	public void shouldInvokeExactMatchingJavaFunction() {
		this.registry.put(JavaFunctions.class);

		Assert.assertSame(TWO_INT_NODE, this.evaluate("count", new IntNode(1), new IntNode(2)));
	}

	@Test
	public void shouldInvokeFallbackJavaFunction() {
		this.registry.put(JavaFunctions.class);

		Assert.assertSame(GENERIC_NODE, this.evaluate("count", new ObjectNode()));
	}

	@Test
	public void shouldInvokeGenericVarargJavaFunctionsIfNoExactMatch() {
		this.registry.put(JavaFunctions.class);

		Assert.assertSame(GENERIC_VARARG_NODE,
			this.evaluate("count", new ObjectNode(), new ObjectNode(), new ObjectNode()));
	}

	@Test
	public void shouldInvokeMostSpecificVarargJavaFunction() {
		this.registry.put(JavaFunctions.class);

		Assert.assertSame(ONE_INT_VARARG_NODE, this.evaluate("count", new IntNode(1), new IntNode(2), new IntNode(3)));
		Assert.assertSame(ONE_INT_VARARG_NODE, this.evaluate("count", new IntNode(1)));
	}

	public static class JavaFunctions {

		public static IJsonNode count(final IArrayNode<IJsonNode> node) {
			return ARRAY_NODE;
		}

		public static IJsonNode count(final IPrimitiveNode node, final IPrimitiveNode node2) {
			return TWO_INT_NODE;
		}

		public static IJsonNode count(final IPrimitiveNode node, final IPrimitiveNode... nodes) {
			return ONE_INT_VARARG_NODE;
		}

		public static IJsonNode count(final IJsonNode node) {
			return GENERIC_NODE;
		}

		public static IJsonNode count(final IJsonNode... node) {
			return GENERIC_VARARG_NODE;
		}

		public static IJsonNode sum(final INumericNode... nodes) {
			return SUM_NODE;
		}
	}
}
