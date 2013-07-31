package eu.stratosphere.sopremo.io;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import eu.stratosphere.pact.common.contract.GenericDataSink;
import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.pact.generic.io.OutputFormat;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.ElementarySopremoModule;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.OutputCardinality;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;
import eu.stratosphere.util.Equaler;

/**
 * Represents a data sink in a PactPlan.
 */
@InputCardinality(1)
@OutputCardinality(0)
public class Sink extends ElementaryOperator<Sink> {
	private String outputPath;

	private SopremoFormat format;

	/**
	 * Initializes a Sink with the given {@link FileOutputFormat} and the given path.
	 * 
	 * @param outputFormat
	 *        the FileOutputFormat that should be used
	 * @param outputPath
	 *        the path of this Sink
	 */
	public Sink(final SopremoFormat format, final String outputPath) {
		this.format = format;
		this.outputPath = outputPath;

		if (format.getOutputFormat() == null)
			throw new IllegalArgumentException("given format does not support writing");
		checkPath();
	}

	/**
	 * Initializes a Sink with the given {@link FileOutputFormat}.
	 * 
	 * @param outputFormat
	 *        the FileOutputFormat that should be used
	 */
	public Sink(final SopremoFormat format) {
		this(format, null);
	}

	/**
	 * Initializes a Sink with the given name. This Sink uses {@link Sink#Sink(Class, String)} with the given name and
	 * a {@link JsonOutputFormat} to write the data.
	 * 
	 * @param outputPath
	 *        the name of this Sink
	 */
	public Sink(final String outputName) {
		this(new JsonFormat(), outputName);
	}

	/**
	 * Initializes a Sink. This constructor uses {@link Sink#Sink(String)} with an empty string.
	 */
	Sink() {
		this("file:///");
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
	 * Sets the format to the specified value.
	 * 
	 * @param format
	 *        the format to set
	 */
	@Property(preferred = true)
	public void setFormat(SopremoFormat format) {
		if (format == null)
			throw new NullPointerException("format must not be null");
		if (format.getOutputFormat() == null)
			throw new IllegalArgumentException("writing for the given format is not supported");

		this.format = format;
	}

	@Override
	public Output getSource() {
		throw new UnsupportedOperationException("Sink has not output");
	}

	@Override
	public PactModule asPactModule(final EvaluationContext context, SopremoRecordLayout layout) {
		final PactModule pactModule = new PactModule(1, 0);

		final Class<? extends OutputFormat<SopremoRecord>> outputFormat = this.format.getOutputFormat();
		final GenericDataSink contract = new GenericDataSink(outputFormat, this.getName());
		this.format.configureForOutput(contract.getParameters(), this.outputPath);
		SopremoUtil.setEvaluationContext(contract.getParameters(), context);
		SopremoUtil.setLayout(contract.getParameters(), layout);
		contract.setDegreeOfParallelism(getDegreeOfParallelism());

		contract.setInput(pactModule.getInput(0));
		pactModule.addInternalOutput(contract);
		return pactModule;
	}

	@Override
	public ElementarySopremoModule asElementaryOperators(final EvaluationContext context) {
		final ElementarySopremoModule module = new ElementarySopremoModule(1, 0);
		final Sink clone = (Sink) this.clone();
		module.addInternalOutput(clone);
		clone.setInput(0, module.getInput(0));
		return module;
	}

	/**
	 * Returns the name of this Sink.
	 * 
	 * @return the name
	 */
	public String getOutputPath() {
		return this.outputPath;
	}

	/**
	 * Sets the outputPath to the specified value.
	 * 
	 * @param outputPath
	 *        the outputPath to set
	 */
	public void setOutputPath(String outputPath) {
		if (outputPath == null)
			throw new NullPointerException("outputPath must not be null");

		this.outputPath = outputPath;
		checkPath();
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
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Invalid path", e);
		}
	}

	/**
	 * Sets the outputPath to the specified value.
	 * 
	 * @param outputPath
	 *        the outputPath to set
	 * @return
	 */
	public Sink withOutputPath(String outputPath) {
		this.setOutputPath(outputPath);
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.format == null ? 0 : this.format.hashCode());
		result = prime * result + (this.outputPath == null ? 0 : this.outputPath.hashCode());
		return result;
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
	 * @see eu.stratosphere.sopremo.operator.ElementaryOperator#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		appendable.append("Sink [");
		if (this.outputPath != null)
			appendable.append(this.outputPath).append(", ");
		this.format.appendAsString(appendable);
		appendable.append("]");
	}
}
