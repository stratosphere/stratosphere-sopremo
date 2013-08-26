package eu.stratosphere.meteor;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.IdentityHashMap;
import java.util.Map;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;

import eu.stratosphere.nephele.fs.Path;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.operator.JsonStream;
import eu.stratosphere.sopremo.operator.Operator;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.packages.IRegistry;
import eu.stratosphere.sopremo.query.ConfObjectInfo;
import eu.stratosphere.sopremo.query.ConfObjectInfo.ConfObjectIndexedPropertyInfo;
import eu.stratosphere.sopremo.query.ConfObjectInfo.ConfObjectPropertyInfo;
import eu.stratosphere.sopremo.query.IConfObjectRegistry;
import eu.stratosphere.sopremo.query.PackageManager;
import eu.stratosphere.sopremo.query.PlanCreator;
import eu.stratosphere.sopremo.query.QueryParserException;
import eu.stratosphere.util.StringUtil;

public class QueryParser extends PlanCreator {
	private File inputDirectory = new File(".");
	
	private PackageManager packageManager = new PackageManager();

	private void appendExpression(final Object value, final JavaRenderInfo renderInfo) {
		renderInfo.adaptor.addJavaFragment(value, renderInfo.builder);
	}

	protected <O extends Operator<?>> void appendInputProperties(final O op, final JavaRenderInfo renderInfo,
			final ConfObjectInfo<O> info, final O defaultInstance) {
		final IRegistry<ConfObjectIndexedPropertyInfo> inputPropertyRegistry = info.getInputPropertyRegistry();
		for (final String propertyName : inputPropertyRegistry.keySet())
			for (int index = 0; index < op.getInputs().size(); index++) {
				final ConfObjectIndexedPropertyInfo propertyInfo = inputPropertyRegistry.get(propertyName);
				final Object actualValue = propertyInfo.getValue(op, index);
				final Object defaultValue = propertyInfo.getValue(defaultInstance, index);
				if (!actualValue.equals(defaultValue)) {
					renderInfo.builder.append(renderInfo.getVariableName(op)).
						append(".set").append(StringUtil.upperFirstChar(propertyInfo.getDescriptor().getName())).
						append("(").append(index).append(", ");
					this.appendExpression(actualValue, renderInfo);
					renderInfo.builder.append(");\n");
				}
			}
	}

	protected <O extends Operator<?>> void appendInputs(final O op, final JavaRenderInfo renderInfo,
			final O defaultInstance) {
		if (!defaultInstance.getInputs().equals(op.getInputs())) {
			renderInfo.builder.append(renderInfo.getVariableName(op)).append(".setInputs(");
			for (int index = 0; index < op.getInputs().size(); index++) {
				if (index > 0)
					renderInfo.builder.append(", ");
				renderInfo.builder.append(renderInfo.getVariableName(op.getInput(index)));
			}
			renderInfo.builder.append(");\n");
		}
	}

	@SuppressWarnings("unchecked")
	protected void appendJavaOperator(final Operator<?> op, final JavaRenderInfo renderInfo) {
		final String className = op.getClass().getSimpleName();
		renderInfo.builder.append(String.format("%s %s = new %1$s();\n", className, renderInfo.getVariableName(op)));

		final IConfObjectRegistry<Operator<?>> operatorRegistry = renderInfo.parser.getOperatorRegistry();
		final String name = operatorRegistry.getName((Class<? extends Operator<?>>) op.getClass());
		final ConfObjectInfo<Operator<?>> info = operatorRegistry.get(name);
		final Operator<?> defaultInstance = info.newInstance();
		this.appendInputs(op, renderInfo, defaultInstance);
		defaultInstance.setInputs(op.getInputs());
		this.appendOperatorProperties(op, renderInfo, info, defaultInstance);
		this.appendInputProperties(op, renderInfo, info, defaultInstance);
	}

	protected <O extends Operator<?>> void appendOperatorProperties(final O op,
			final JavaRenderInfo renderInfo, final ConfObjectInfo<O> info, final O defaultInstance) {

		final IRegistry<ConfObjectPropertyInfo> operatorPropertyRegistry = info.getOperatorPropertyRegistry();
		for (final String propertyName : operatorPropertyRegistry.keySet()) {
			final ConfObjectPropertyInfo propertyInfo = operatorPropertyRegistry.get(propertyName);
			final Object actualValue = propertyInfo.getValue(op);
			final Object defaultValue = propertyInfo.getValue(defaultInstance);
			if (!actualValue.equals(defaultValue)) {
				renderInfo.builder.append(renderInfo.getVariableName(op)).
					append(".set").append(StringUtil.upperFirstChar(propertyInfo.getDescriptor().getName())).
					append("(");
				this.appendExpression(actualValue, renderInfo);
				renderInfo.builder.append(");\n");
			}
		}
	}

	/**
	 * Returns the inputDirectory.
	 * 
	 * @return the inputDirectory
	 */
	public File getInputDirectory() {
		return this.inputDirectory;
	}

	@Override
	public SopremoPlan getPlan(final InputStream stream) {
		try {
			return this.tryParse(stream);
		} catch (final Exception e) {
			return null;
		}
	}

	/**
	 * Sets the inputDirectory to the specified value.
	 * 
	 * @param inputDirectory
	 *        the inputDirectory to set
	 */
	public void setInputDirectory(File inputDirectory) {
		if (inputDirectory == null)
			throw new NullPointerException("inputDirectory must not be null");

		this.inputDirectory = inputDirectory;
	}

	protected String toSopremoCode(final CharStream input) {
		final MeteorLexer lexer = new MeteorLexer(input);
		final CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(lexer);
		final MeteorParser parser = new MeteorParser(tokens);
		final TraceableSopremoTreeAdaptor adaptor = new TraceableSopremoTreeAdaptor();
		parser.setTreeAdaptor(adaptor);
		final SopremoPlan result = parser.parse();
		final JavaRenderInfo info = new JavaRenderInfo(parser, adaptor);
		this.toSopremoCode(result, info);
		return info.builder.toString();
	}

	public String toSopremoCode(final InputStream stream) throws IOException, QueryParserException {
		return this.toSopremoCode(new ANTLRInputStream(stream));
	}

	protected String toSopremoCode(final SopremoPlan result, final JavaRenderInfo info) {
		for (final Operator<?> op : result.getContainedOperators())
			this.appendJavaOperator(op, info);
		return info.builder.toString();
	}

	public String toSopremoCode(final String script) throws QueryParserException {
		return this.toSopremoCode(new ANTLRStringStream(script));
	}

	protected SopremoPlan tryParse(final CharStream tryParse) {
		final MeteorLexer lexer = new MeteorLexer(tryParse);
		final CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(lexer);
		final MeteorParser parser = new MeteorParser(tokens);
		SopremoEnvironment.getInstance().getEvaluationContext()
			.setWorkingPath(new Path(this.inputDirectory.toURI().toString()));
		parser.getPackageManager().addAll(this.packageManager);
		parser.getPackageManager().addJarPathLocation(this.inputDirectory);
		parser.setTreeAdaptor(new SopremoTreeAdaptor());
		return parser.parse();
	}
	
	/**
	 * Returns the packageManager.
	 * 
	 * @return the packageManager
	 */
	public PackageManager getPackageManager() {
		return this.packageManager;
	}

	public SopremoPlan tryParse(final InputStream stream) throws IOException, QueryParserException {
		return this.tryParse(new ANTLRInputStream(stream));
	}

	public SopremoPlan tryParse(final String script) throws QueryParserException {
		return this.tryParse(new ANTLRStringStream(script));
	}

	/**
	 * Sets the inputDirectory to the specified value.
	 * 
	 * @param inputDirectory
	 *        the inputDirectory to set
	 */
	public QueryParser withInputDirectory(File inputDirectory) {
		setInputDirectory(inputDirectory);
		return this;
	}

	public static String getPrefixedName(String prefix, String name) {
		return String.format("%s:%s", prefix, name);
	}

	private static class JavaRenderInfo {
		private final MeteorParser parser;

		private final TraceableSopremoTreeAdaptor adaptor;

		private final StringBuilder builder = new StringBuilder();

		private final Map<JsonStream, String> variableNames = new IdentityHashMap<JsonStream, String>();

		private final Object2IntMap<Class<?>> instanceCounter = new Object2IntOpenHashMap<Class<?>>();

		public JavaRenderInfo(final MeteorParser parser, final TraceableSopremoTreeAdaptor adaptor) {
			this.parser = parser;
			this.adaptor = adaptor;
		}

		public String getVariableName(final JsonStream input) {
			final Operator<?> op = input instanceof Operator ? (Operator<?>) input : input.getSource().getOperator();
			String name = this.variableNames.get(op);
			if (name == null) {
				final int counter = this.instanceCounter.getInt(op.getClass()) + 1;
				this.instanceCounter.put(op.getClass(), counter);
				name = String.format("%s%d", StringUtil.lowerFirstChar(op.getClass().getSimpleName()), counter);
				this.variableNames.put(op, name);
			}
			return name;
		}
	}
}
