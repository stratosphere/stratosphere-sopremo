package eu.stratosphere.sopremo.query;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import java.io.File;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import org.antlr.runtime.*;
import org.antlr.runtime.BitSet;

import com.google.common.base.CharMatcher;

import eu.stratosphere.core.fs.Path;
import eu.stratosphere.sopremo.CoreFunctions;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.MathFunctions;
import eu.stratosphere.sopremo.SecondOrderFunctions;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.expressions.CoerceExpression;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.FunctionCall;
import eu.stratosphere.sopremo.function.Callable;
import eu.stratosphere.sopremo.function.ExpressionFunction;
import eu.stratosphere.sopremo.function.MacroBase;
import eu.stratosphere.sopremo.function.SopremoFunction;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.SopremoFormat;
import eu.stratosphere.sopremo.operator.ConfigurableSopremoType;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.Operator;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.packages.IConstantRegistry;
import eu.stratosphere.sopremo.packages.IFunctionRegistry;
import eu.stratosphere.sopremo.packages.IRegistry;
import eu.stratosphere.sopremo.packages.ITypeRegistry;
import eu.stratosphere.sopremo.packages.NameChooserProvider;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.query.ConfObjectInfo.ConfObjectIndexedPropertyInfo;
import eu.stratosphere.sopremo.query.ConfObjectInfo.ConfObjectPropertyInfo;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.MissingNode;

public abstract class AbstractQueryParser extends Parser implements ParsingScope {
	/**
	 * 
	 */
	public static final String DEFAULT_ERROR_MESSAGE = "Cannot parse script";

	private final PackageManager packageManager = new PackageManager(this.getNameChooserProvider());

	protected InputSuggestion inputSuggestion = new InputSuggestion().withMaxSuggestions(3).withMinSimilarity(0.5);

	protected List<Sink> sinks = new ArrayList<Sink>();

	private final SopremoPlan currentPlan = new SopremoPlan();

	private final Map<String, Class<? extends IJsonNode>> typeNameToType =
		new HashMap<String, Class<? extends IJsonNode>>();

	public AbstractQueryParser(final TokenStream input) {
		super(input);
		this.init();
	}

	public AbstractQueryParser(final TokenStream input, final RecognizerSharedState state) {
		super(input, state);
		this.init();
	}

	// /**
	// *
	// */
	// protected void setupParser() {
	// if (this.hasParserFlag(ParserFlag.FUNCTION_OBJECTS))
	// this.bindingContraints = new BindingConstraint[] {
	// BindingConstraint.AUTO_FUNCTION_POINTER,
	// BindingConstraint.NON_NULL };
	// else
	// this.bindingContraints = new BindingConstraint[] {
	// BindingConstraint.NON_NULL };
	// }
	//
	public void addFunction(final String name, final ExpressionFunction function) {
		this.getFunctionRegistry().put(name, function);
	}

	public void addFunction(final String name, final String udfPath) {
		final int delim = udfPath.lastIndexOf('.');
		if (delim == -1)
			throw new IllegalArgumentException("Invalid path");
		final String className = udfPath.substring(0, delim), methodName = udfPath.substring(delim + 1);

		try {
			final Class<?> clazz = Class.forName(className);
			this.getFunctionRegistry().put(name, clazz, methodName);
		} catch (final ClassNotFoundException e) {
			throw new IllegalArgumentException("Unknown class " + className);
		}
	}
	
	public void addTypeAlias(final String alias, final Class<? extends IJsonNode> type) {
		this.typeNameToType.put(alias, type);
	}

	public EvaluationExpression coerce(final String type, final EvaluationExpression valueExpression) {
		final Class<? extends IJsonNode> targetType = this.typeNameToType.get(type);
		if (targetType == null)
			throw new IllegalArgumentException("unknown type " + type);
		return new CoerceExpression(targetType).withInputExpression(valueExpression);
	}

	public EvaluationExpression createCheckedMethodCall(final String packageName, final Token name,
			final EvaluationExpression object,
			EvaluationExpression[] params)
			throws RecognitionException {
		final IFunctionRegistry functionRegistry = this.getScope(packageName).getFunctionRegistry();
		final Callable<?, ?> callable = functionRegistry.get(name.getText());
		if (callable == null)
			throw new RecognitionExceptionWithUsageHint(name, String.format(
				"Unknown function %s; possible alternatives %s", name,
				this.inputSuggestion.suggest(name.getText(), functionRegistry.keySet())));
		if (callable instanceof MacroBase)
			return ((MacroBase) callable).call(params);
		if (!(callable instanceof SopremoFunction))
			throw new QueryParserException(String.format("Unknown callable %s", callable));

		if (object != null) {
			final EvaluationExpression[] shiftedParams = new EvaluationExpression[params.length + 1];
			System.arraycopy(params, 0, shiftedParams, 1, params.length);
			params = shiftedParams;
			params[0] = object;
		}

		if (callable instanceof ExpressionFunction)
			return ((ExpressionFunction) callable).inline(params);
		return new FunctionCall((SopremoFunction) callable, params);
	}

	public EvaluationExpression createCheckedMethodCall(final String packageName, final Token name,
			final EvaluationExpression[] params)
			throws RecognitionException {
		return this.createCheckedMethodCall(packageName, name, null, params);
	}

	@Override
	public IConstantRegistry getConstantRegistry() {
		return this.packageManager.getConstantRegistry();
	}

	// private BindingConstraint[] bindingContraints;

	//
	// public <T> T getBinding(Token name, Class<T> expectedType) {
	// try {
	// return this.getContext().getBindings().get(name.getText(), expectedType,
	// this.bindingContraints);
	// } catch (Exception e) {
	// throw new QueryParserException(e.getMessage(), name);
	// }
	// }
	//
	// public boolean hasBinding(Token name, Class<?> expectedType) {
	// try {
	// Object result = this.getContext().getBindings().get(name.getText(),
	// expectedType, this.bindingContraints);
	// return result != null;
	// } catch (Exception e) {
	// return false;
	// }
	// }
	//
	// public <T> T getRawBinding(Token name, Class<T> expectedType) {
	// try {
	// return this.getContext().getBindings().get(name.getText(), expectedType);
	// } catch (Exception e) {
	// throw new QueryParserException(e.getMessage(), name);
	// }
	// }
	//

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.query.ParsingScope#getFileFormatRegistry()
	 */
	@Override
	public IConfObjectRegistry<SopremoFormat> getFileFormatRegistry() {
		return this.packageManager.getFileFormatRegistry();
	}

	//
	// public void setBinding(Token name, Object binding) {
	// this.getContext().getBindings().set(name.getText(), binding);
	// }
	//
	// public void setBinding(Token name, Object binding, int scopeLevel) {
	// this.getContext().getBindings().set(name.getText(), binding, scopeLevel);
	// }

	@Override
	public IFunctionRegistry getFunctionRegistry() {
		return this.packageManager.getFunctionRegistry();
	}

	@Override
	public IConfObjectRegistry<Operator<?>> getOperatorRegistry() {
		return this.packageManager.getOperatorRegistry();
	}

	/**
	 * Returns the packageManager.
	 * 
	 * @return the packageManager
	 */
	public PackageManager getPackageManager() {
		return this.packageManager;
	}

	@Override
	public ITypeRegistry getTypeRegistry() {
		return this.packageManager.getTypeRegistry();
	}

	public SopremoPlan parse() throws QueryParserException {
		// this.currentPlan = new SopremoPlan();
		try {
			// this.setupParser();
			this.parseSinks();
		} catch (final RecognitionExceptionWithUsageHint e) {
			throw new QueryParserException(e.getMessage(), e);
		} catch (final RecognitionException e) {
			throw new QueryParserException(DEFAULT_ERROR_MESSAGE, e);
		}
		this.currentPlan.setSinks(this.sinks);

		for (final PackageInfo info : this.packageManager.getImportedPackages())
			for (final File packages : info.getRequiredJarPaths())
				this.currentPlan.addRequiredPackage(packages.getAbsolutePath());
		this.currentPlan.setTypeRegistry(getTypeRegistry());

		return this.currentPlan;
	}

	@Override
	public Object recoverFromMismatchedSet(final IntStream input, final RecognitionException e, final BitSet follow)
			throws RecognitionException {
		throw e;
	}

	protected void explainUsage(final String usage, final RecognitionException e) throws RecognitionException {
		final RecognitionExceptionWithUsageHint sre = new RecognitionExceptionWithUsageHint(this.input, usage);
		sre.initCause(e);
		throw sre;
	}

	protected ConfObjectInfo<? extends SopremoFormat> findFormat(final String packageName, final Token name,
			final String pathName)
			throws RecognitionExceptionWithUsageHint {
		if (name == null) {
			URI path;
			try {
				path = new URI(pathName);
			} catch (final URISyntaxException e) {
				final RecognitionExceptionWithUsageHint hint =
					new RecognitionExceptionWithUsageHint(name, "invalid path URI");
				hint.initCause(e);
				throw hint;
			}
			final IConfObjectRegistry<SopremoFormat> fileFormatRegistry = this.getFileFormatRegistry();
			for (final String fileFormat : fileFormatRegistry.keySet()) {
				final ConfObjectInfo<SopremoFormat> info = fileFormatRegistry.get(fileFormat);
				if (info.newInstance().canHandleFormat(path))
					return info;
			}

			SopremoUtil.LOG.warn("Cannot find file format for " + pathName + " using default " +
				this.getDefaultFileFormat());
			return fileFormatRegistry.get(this.getDefaultFileFormat().getAnnotation(Name.class));
		}
		final ParsingScope scope = this.getScope(packageName);
		final ConfObjectInfo<SopremoFormat> format = scope.getFileFormatRegistry().get(name.getText());
		if (format == null)
			throw new RecognitionExceptionWithUsageHint(name, String.format(
				"Unknown file format %s; possible alternatives %s", name,
				this.inputSuggestion.suggest(name.getText(), scope.getFileFormatRegistry().keySet())));
		return format;
	}

	protected ConfObjectInfo.ConfObjectIndexedPropertyInfo findInputPropertyRelunctantly(
			final ConfigurableSopremoType object, final ConfObjectInfo<?> info, final Token firstWord,
			final boolean consume) {
		final IRegistry<ConfObjectIndexedPropertyInfo> inputPropertyRegistry = info.getInputPropertyRegistry(object);
		String name = firstWord.getText();
		ConfObjectInfo.ConfObjectIndexedPropertyInfo property;

		int lookAhead = 1;
		// Reluctantly concatenate tokens
		for (; (property = inputPropertyRegistry.get(name)) == null && this.input.LA(lookAhead) == firstWord.getType(); lookAhead++) {
			final Token matchedToken = this.input.LT(lookAhead);
			name = String.format("%s %s", name, matchedToken.getText());
		}

		if (property == null)
			return null;
		// throw new FailedPredicateException();
		// throw new
		// QueryParserException(String.format("Unknown property %s; possible alternatives %s",
		// name,
		// this.inputSuggestion.suggest(name, inputPropertyRegistry)),
		// firstWord);

		// consume additional tokens
		if (consume)
			for (; lookAhead > 0; lookAhead--)
				this.input.consume();

		return property;
	}

	protected ConfObjectInfo<? extends Operator<?>> findOperatorGreedily(final String packageName, final Token firstWord)
			throws RecognitionException {
		final IConfObjectRegistry<Operator<?>> registry = this.getScope(packageName).getOperatorRegistry();
		final StringBuilder name = new StringBuilder(firstWord.getText());
		final IntList wordBoundaries = this.greedilyAddToName(firstWord, name);
		final ConfObjectInfo<Operator<?>> info = this.findGreedilyInRegistry(registry, name, wordBoundaries);

		if (info == null)
			throw new RecognitionExceptionWithUsageHint(firstWord, String.format(
				"Unknown operator %s; possible alternatives %s", name,
				this.inputSuggestion.suggest(name, registry.keySet())));

		return info;
	}

	protected ConfObjectInfo.ConfObjectPropertyInfo findPropertyGreedily(final ConfigurableSopremoType object,
			final ConfObjectInfo<?> info, final Token firstWord) throws RecognitionException {

		final IRegistry<ConfObjectPropertyInfo> registry = info.getOperatorPropertyRegistry(object);
		final StringBuilder name = new StringBuilder(firstWord.getText());
		final IntList wordBoundaries = this.greedilyAddToName(firstWord, name);
		final ConfObjectPropertyInfo property = this.findGreedilyInRegistry(registry, name, wordBoundaries);

		if (property == null)
			throw new RecognitionExceptionWithUsageHint(firstWord, String.format(
				"Unknown property %s; possible alternatives %s", name,
				this.inputSuggestion.suggest(name, registry.keySet())));

		return property;
	}

	protected ConfObjectInfo.ConfObjectPropertyInfo findPropertyRelunctantly(final ConfigurableSopremoType object,
			final ConfObjectInfo<?> info, final Token firstWord)
			throws RecognitionException {
		String name = firstWord.getText();
		ConfObjectInfo.ConfObjectPropertyInfo property;

		int lookAhead = 1;
		// Reluctantly concatenate tokens
		final IRegistry<ConfObjectPropertyInfo> propertyRegistry = info.getOperatorPropertyRegistry(object);
		for (; (property = propertyRegistry.get(name)) == null && this.input.LA(lookAhead) == firstWord.getType(); lookAhead++) {
			final Token matchedToken = this.input.LT(lookAhead);
			name = String.format("%s %s", name, matchedToken.getText());
		}

		if (property == null)
			throw new RecognitionExceptionWithUsageHint(firstWord, String.format(
				"Unknown property %s; possible alternatives %s", name,
				this.inputSuggestion.suggest(name, propertyRegistry.keySet())));

		// consume additional tokens
		for (; lookAhead > 1; lookAhead--)
			this.input.consume();

		return property;
	}

	protected EvaluationContext getContext() {
		return this.currentPlan.getCompilationContext();
	}

	/**
	 * @return the default {@link SopremoFormat} associated with the language that is parsed
	 */
	protected abstract Class<? extends SopremoFormat> getDefaultFileFormat();

	protected abstract NameChooserProvider getNameChooserProvider();

	protected ConfObjectInfo<? extends Operator<?>> getOperatorInfo(final Operator<?> operator) {
		final IConfObjectRegistry<Operator<?>> operatorRegistry = this.getOperatorRegistry();
		final Name nameAnnotation = operator.getClass().getAnnotation(Name.class);
		if (nameAnnotation != null)
			return operatorRegistry.get(nameAnnotation);
		final Set<String> operatorNames = operatorRegistry.keySet();
		for (final String opName : operatorNames)
			if (operatorRegistry.get(opName).getOperatorClass().isInstance(operator))
				return operatorRegistry.get(opName);
		return null;
	}

	protected ParsingScope getScope(final String packageName) {
		if (packageName == null)
			return this;
		final ParsingScope scope = this.packageManager.getPackageInfo(packageName);
		if (scope == null)
			throw new QueryParserException("Unknown package " + packageName);
		return scope;
	}

	protected SopremoFunction getSopremoFunction(final String packageName, final Token name) {
		final Callable<?, ?> callable = this.getScope(packageName).getFunctionRegistry().get(name.getText());
		if (!(callable instanceof SopremoFunction))
			throw new QueryParserException(String.format("Unknown function %s", name));
		return (SopremoFunction) callable;
	}

	protected String makeFilePath(final EvaluationExpression pathExpression) {
		String pathConstant = pathExpression.evaluate(MissingNode.getInstance()).toString();

		// try if file is self-contained
		try {
			final URI uri = new URI(pathConstant);
			if (uri.getScheme() == null)
				return new Path(SopremoEnvironment.getInstance().getEvaluationContext().getWorkingPath(), pathConstant).toString();
			return new Path(uri).toString();
		} catch (final URISyntaxException e) {
			throw new IllegalArgumentException("Invalid path " + pathConstant, e);
		}
	}

	protected Number parseInt(final String text) {
		final BigInteger result = new BigInteger(text);
		if (result.bitLength() <= 31)
			return result.intValue();
		if (result.bitLength() <= 63)
			return result.longValue();
		return result;
	}

	protected abstract void parseSinks() throws RecognitionException;

	@Override
	protected Object recoverFromMismatchedToken(final IntStream input, final int ttype, final BitSet follow)
			throws RecognitionException {
		// if next token is what we are looking for then "delete" this token
		if (this.mismatchIsUnwantedToken(input, ttype))
			throw new UnwantedTokenException(ttype, input);

		// can't recover with single token deletion, try insertion
		if (this.mismatchIsMissingToken(input, follow)) {
			final Object inserted = this.getMissingSymbol(input, null, ttype, follow);
			throw new MissingTokenException(ttype, input, inserted);
		}

		throw new MismatchedTokenException(ttype, input);
	}

	private <T> T findGreedilyInRegistry(final IRegistry<T> registry, final StringBuilder name,
			final IntList wordBoundaries) {
		int tokenCount = wordBoundaries.size();
		T info = null;
		for (; info == null && tokenCount > 0;)
			info = registry.get(name.substring(0, wordBoundaries.getInt(--tokenCount)));

		// consume additional tokens
		for (; tokenCount > 0; tokenCount--)
			this.input.consume();
		return info;
	}

	private IntList greedilyAddToName(final Token firstWord, final StringBuilder name) {
		final IntList wordBoundaries = new IntArrayList();
		wordBoundaries.add(name.length());

		// greedily concatenate as many tokens as possible
		for (int lookAhead = 1; this.input.LA(lookAhead) == firstWord.getType() ||
			CharMatcher.JAVA_LETTER.matchesAllOf(this.input.LT(lookAhead).getText()); lookAhead++) {
			final Token matchedToken = this.input.LT(lookAhead);
			name.append(' ').append(matchedToken.getText());
			wordBoundaries.add(name.length());
		}
		return wordBoundaries;
	}

	/**
	 * 
	 */
	private void init() {
		this.currentPlan.setContext(new EvaluationContext(this.getNameChooserProvider()));
		this.packageManager.getFunctionRegistry().put(CoreFunctions.class);
		this.packageManager.getFunctionRegistry().put(MathFunctions.class);
		this.packageManager.getFunctionRegistry().put(SecondOrderFunctions.class);
		SopremoEnvironment.getInstance().setEvaluationContext(this.getContext());
	}
}
