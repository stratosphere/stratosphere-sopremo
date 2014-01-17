package eu.stratosphere.sopremo.io;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import eu.stratosphere.api.common.io.OutputFormat;
import eu.stratosphere.api.common.operators.GenericDataSink;
import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.OrderingExpression;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.ElementarySopremoModule;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.OutputCardinality;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;
import eu.stratosphere.util.Equaler;

/**
 * Represents a data sink in a PactPlan.
 */
@InputCardinality(1)
@OutputCardinality(0)
@Name(noun = "sink")
public class Sink extends ElementaryOperator<Sink> {
	private String outputPath;

	private SopremoFormat format;

	private List<OrderingExpression> globalSortingKey = new ArrayList<OrderingExpression>(),
			localSortingKey = new ArrayList<OrderingExpression>();

	/**
	 * Initializes a Sink. This constructor uses {@link Sink#Sink(String)} with an empty string.
	 */
	public Sink() {
		this("file:///");
	}

	/**
	 * Initializes a Sink with the given {@link SopremoFormat}.
	 * 
	 * @param format
	 *        the SopremoFormat that should be used
	 */
	public Sink(final SopremoFormat format) {
		this(format, null);
	}

	/**
	 * Initializes a Sink with the given {@link SopremoFormat} and the given path.
	 * 
	 * @param format
	 *        the SopremoFormat that should be used
	 * @param outputPath
	 *        the path of this Sink
	 */
	public Sink(final SopremoFormat format, final String outputPath) {
		this.format = format;
		this.outputPath = outputPath;

		if (format.getOutputFormat() == null)
			throw new IllegalArgumentException("given format does not support writing");
		this.checkPath();
		this.addPropertiesFrom(format);
	}

	/**
	 * Initializes a Sink with the given name. This Sink uses a {@link JsonFormat} to write the data.
	 * 
	 * @param outputPath
	 *        the path of this Sink
	 */
	public Sink(final String outputPath) {
		this(new JsonFormat(), outputPath);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.ElementaryOperator#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append(this.getName());
		appendable.append(" [");
		if (this.outputPath != null)
			appendable.append(this.outputPath).append(", ");
		this.format.appendAsString(appendable);
		appendable.append("]");
	}

	@Override
	public ElementarySopremoModule asElementaryOperators() {
		final ElementarySopremoModule module = new ElementarySopremoModule(1, 0);
		final Sink clone = (Sink) this.clone();
		module.addInternalOutput(clone);
		clone.setInput(0, module.getInput(0));
		return module;
	}

	@Override
	public PactModule asPactModule() {
		final PactModule pactModule = new PactModule(1, 0);

		final Class<? extends OutputFormat<SopremoRecord>> outputFormat = this.format.getOutputFormat();
		final GenericDataSink contract = new GenericDataSink(outputFormat, this.getName());
		this.format.configureForOutput(contract.getParameters(), this.outputPath);
		SopremoEnvironment.getInstance().save(contract.getParameters());
		contract.setDegreeOfParallelism(this.getDegreeOfParallelism());

		SopremoRecordLayout layout = SopremoEnvironment.getInstance().getLayout();
		contract.setInput(pactModule.getInput(0));
		if (!this.globalSortingKey.isEmpty())
			contract.setGlobalOrder(this.createOrdering(layout, this.globalSortingKey));
		if (!this.localSortingKey.isEmpty())
			contract.setLocalOrder(this.createOrdering(layout, this.localSortingKey));
		pactModule.addInternalOutput(contract);
		return pactModule;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final Sink other = (Sink) obj;
		return Equaler.SafeEquals.equal(this.outputPath, other.outputPath)
			&& Equaler.SafeEquals.equal(this.format, other.format);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.ElementaryOperator#getAllKeyExpressions()
	 */
	@Override
	public Set<EvaluationExpression> getAllKeyExpressions() {
		final Set<EvaluationExpression> allKeyExpressions = super.getAllKeyExpressions();
		for (final OrderingExpression ordering : this.globalSortingKey)
			allKeyExpressions.add(ordering.getPath());
		for (final OrderingExpression ordering : this.localSortingKey)
			allKeyExpressions.add(ordering.getPath());
		return allKeyExpressions;
	}

	/**
	 * Returns the format.
	 * 
	 * @return the format
	 */
	public SopremoFormat getFormat() {
		return this.format;
	}

	/**
	 * Returns the globalSortingKey.
	 * 
	 * @return the globalSortingKey
	 */
	public List<OrderingExpression> getGlobalSortingKey() {
		return this.globalSortingKey;
	}

	/**
	 * Returns the localSortingKey.
	 * 
	 * @return the localSortingKey
	 */
	public List<OrderingExpression> getLocalSortingKey() {
		return this.localSortingKey;
	}

	/**
	 * Returns the name of this Sink.
	 * 
	 * @return the name
	 */
	public String getOutputPath() {
		return this.outputPath;
	}

	@Override
	public Output getSource() {
		throw new UnsupportedOperationException("Sink has not output");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.format == null ? 0 : this.format.hashCode());
		result = prime * result + (this.outputPath == null ? 0 : this.outputPath.hashCode());
		return result;
	}

	/**
	 * Sets the format to the specified value.
	 * 
	 * @param format
	 *        the format to set
	 */
	@Property(preferred = true)
	public void setFormat(final SopremoFormat format) {
		if (format == null)
			throw new NullPointerException("format must not be null");
		if (format.getOutputFormat() == null)
			throw new IllegalArgumentException("writing for the given format is not supported");

		this.removePropertiesFrom(this.format);
		this.format = format;
		this.addPropertiesFrom(format);
	}

	/**
	 * Sets the globalSortingKey to the specified value.
	 * 
	 * @param globalSortingKey
	 *        the globalSortingKey to set
	 */
	public void setGlobalSortingKey(final List<OrderingExpression> globalSortingKey) {
		if (globalSortingKey == null)
			throw new NullPointerException("globalSortingKey must not be null");

		this.globalSortingKey = globalSortingKey;
	}

	/**
	 * Sets the localSortingKey to the specified value.
	 * 
	 * @param localSortingKey
	 *        the localSortingKey to set
	 */
	public void setLocalSortingKey(final List<OrderingExpression> localSortingKey) {
		if (localSortingKey == null)
			throw new NullPointerException("localSortingKey must not be null");

		this.localSortingKey = localSortingKey;
	}

	/**
	 * Sets the outputPath to the specified value.
	 * 
	 * @param outputPath
	 *        the outputPath to set
	 */
	public void setOutputPath(final String outputPath) {
		if (outputPath == null)
			throw new NullPointerException("outputPath must not be null");

		this.outputPath = outputPath;
		this.checkPath();
	}

	public Sink withGlobalSortingKey(final List<OrderingExpression> sortingKey) {
		this.setGlobalSortingKey(sortingKey);
		return this;
	}

	public Sink withLocalSortingKey(final List<OrderingExpression> localSortingKey) {
		this.setLocalSortingKey(localSortingKey);
		return this;
	}

	/**
	 * Sets the outputPath to the specified value.
	 * 
	 * @param outputPath
	 *        the outputPath to set
	 * @return this
	 */
	public Sink withOutputPath(final String outputPath) {
		this.setOutputPath(outputPath);
		return this;
	}

	/**
	 * 
	 */
	private void checkPath() {
		try {
			final URI uri = new URI(this.outputPath);
			if (uri.getScheme() == null)
				throw new IllegalStateException(
					"File name of source does not have a valid schema (such as hdfs or file): " + this.outputPath);
		} catch (final URISyntaxException e) {
			throw new IllegalArgumentException("Invalid path", e);
		}
	}
}
