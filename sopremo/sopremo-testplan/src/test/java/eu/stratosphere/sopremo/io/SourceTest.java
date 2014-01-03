package eu.stratosphere.sopremo.io;

import org.junit.Test;

import eu.stratosphere.sopremo.EqualCloneTest;
import eu.stratosphere.sopremo.expressions.ArrayCreation;
import eu.stratosphere.sopremo.expressions.ConstantExpression;
import eu.stratosphere.sopremo.testing.SopremoTestPlan;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.TextNode;

//import eu.stratosphere.sopremo.testing.SopremoTestPlan;

public class SourceTest extends EqualCloneTest<Source> {
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected Source createDefaultInstance(final int index) {
		return new Source("file:///" + String.valueOf(index));
	}

	@Override
	@Test
	public void shouldComplyEqualsOperator() {
		super.shouldComplyEqualsOperator(new Source(new ConstantExpression(0)), new Source(new ConstantExpression(1)),
			new Source("file:///2"), new Source("file:///3"));
	}

	@Test
	public void shouldGenerateAdhocInput() {
		final SopremoTestPlan plan = new SopremoTestPlan(new Source(new ConstantExpression(42)));
		plan.getExpectedOutput(0).add(IntNode.valueOf(42));
		plan.run();
	}

	@Test
	public void shouldGenerateMultipleAdhocInput() {
		final SopremoTestPlan plan =
			new SopremoTestPlan(new Source(
				new ArrayCreation(new ConstantExpression(42), new ConstantExpression("test"))));
		plan.getExpectedOutput(0).
			add(IntNode.valueOf(42)).
			add(TextNode.valueOf("test"));
		plan.run();
	}
}
