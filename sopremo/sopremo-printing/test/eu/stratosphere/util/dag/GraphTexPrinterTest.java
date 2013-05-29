package eu.stratosphere.util.dag;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.stratosphere.sopremo.base.Grouping;
import eu.stratosphere.sopremo.base.Selection;
import eu.stratosphere.sopremo.base.Union;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.ComparativeExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression.BinaryOperator;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.Operator;
import eu.stratosphere.sopremo.operator.OperatorNavigator;
import eu.stratosphere.sopremo.operator.SopremoModule;

public class GraphTexPrinterTest {

	private SopremoModule plan;

	@Before
	public void setUp() {
		this.plan = new SopremoModule("module", 0, 0);
	}

	@Test
	public void graphWithRealOperators() {
		Source source1 = new Source("1");
		Source source2 = new Source("2");

		Operator<Selection> selection = new Selection().withCondition(new ComparativeExpression(new ArrayAccess(0),
			BinaryOperator.LESS, new ArrayAccess(1)));
		selection.withInputs(source1);

		Operator<Union> union = new Union();
		union.withInputs(selection, source2);

		Operator<Grouping> grouping = new Grouping().withGroupingKey(new ArrayAccess(1));
		grouping.setInputs(union);

		Sink sink1 = new Sink("1");
		sink1.withInputs(grouping);

		this.plan.addInternalOutput(sink1);

		final GraphTexPrinter<Operator<?>> graphPrinter = new GraphTexPrinter<Operator<?>>(this.plan);
		graphPrinter.configure(this.getConfig());
		graphPrinter.configure(this.getNodeConfig());
		graphPrinter.compileResult(true);
		graphPrinter.changeFileDirectory("/home/killer/Dokumente/dag/");
		String result = graphPrinter.convert(OperatorNavigator.INSTANCE);
		// System.out.println(result);
		Assert.assertTrue(true);
	}

	private GraphTexPrinter.NodeConfiguration getNodeConfig() {
		GraphTexPrinter.NodeConfiguration config = new GraphTexPrinter.NodeConfiguration();
		config.configure(TexTagProvider.CONSTANT.MINIMUM_OPERATOR_WIDTH, "200pt");
		config.configure(TexTagProvider.CONSTANT.OPERATOR_COLOR, "black");
		config.configure(TexTagProvider.CONSTANT.SOURCE_COLOR, "black");
		config.configure(TexTagProvider.CONSTANT.SINK_COLOR, "black");
		return config;
	}

	private GraphTexPrinter.TexPictureConfiguration getConfig() {
		GraphTexPrinter.TexPictureConfiguration config = new GraphTexPrinter.TexPictureConfiguration();
		config.configure(GraphTexPrinter.TexPictureConfiguration.OPTION.SCALE, 1);
		config.configure(GraphTexPrinter.TexPictureConfiguration.OPTION.NODE_DISTANCE, 1);
		return config;
	}
}
