package eu.stratosphere.sopremo.io;

import eu.stratosphere.pact.common.contract.FileDataSink;
import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.io.SopremoFileFormat.SopremoOutputFormat;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.ElementarySopremoModule;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.OutputCardinality;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;

/**
 * Represents a data sink in a PactPlan.
 */
@InputCardinality(1)
@OutputCardinality(0)
public class Sink extends ElementaryOperator<Sink> {
	private String outputPath;

	private SopremoFileFormat format;

	/**
	 * Initializes a Sink with the given {@link FileOutputFormat} and the given name.
	 * 
	 * @param outputFormat
	 *        the FileOutputFormat that should be used
	 * @param outputPath
	 *        the path of this Sink
	 */
	public Sink(final SopremoFileFormat format, final String outputPath) {
		this.format = format;
		this.outputPath = outputPath;

		if (format.getOutputFormat() == null)
			throw new IllegalArgumentException("given format does not support writing");
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
		this("");
	}

	/**
	 * Returns the format.
	 * 
	 * @return the format
	 */
	public SopremoFileFormat getFormat() {
		return this.format;
	}

	/**
	 * Sets the format to the specified value.
	 * 
	 * @param format
	 *        the format to set
	 */
	@Property(preferred = true)
	public void setFormat(SopremoFileFormat format) {
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
		final FileDataSink contract = new FileDataSink(this.format.getOutputFormat(), this.outputPath, this.outputPath);
		SopremoUtil.transferFieldsToConfiguration(this.format, SopremoFileFormat.class, contract.getParameters(),
			this.format.getOutputFormat(), SopremoOutputFormat.class);
		contract.setInput(pactModule.getInput(0));
		SopremoUtil.setEvaluationContext(contract.getParameters(), context);
		SopremoUtil.setLayout(contract.getParameters(), layout);
		// if(this.outputFormat == JsonOutputFormat.class)
		contract.setDegreeOfParallelism(1);
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
	public String toString() {
		return "Sink [" + this.outputPath + "]";
	}

}
