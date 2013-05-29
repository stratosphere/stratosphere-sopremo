package eu.stratosphere.sopremo.pact;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.common.stubs.Collector;
import eu.stratosphere.pact.common.stubs.MapStub;
import eu.stratosphere.pact.common.type.PactRecord;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.SopremoRuntime;
import eu.stratosphere.sopremo.serialization.Schema;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * An abstract implementation of the {@link MapStub}. SopremoMap provides the functionality to convert the
 * standard input of the MapStub to a more manageable representation (the input is converted to an {@link IJsonNode}).
 */
public abstract class TypedSopremoMap<T extends IJsonNode> extends MapStub implements SopremoStub {
	private EvaluationContext context;

	private Schema inputSchema;

	private JsonCollector collector;

	@Override
	public void open(final Configuration parameters) {
		// We need to pass our class loader since the default class loader is
		// not able to resolve classes coming from the Sopremo user jar file.
		SopremoRuntime.getInstance().setClassLoader(getClass().getClassLoader());
		this.context = (EvaluationContext) SopremoUtil.getObject(parameters, SopremoUtil.CONTEXT, null);
		this.inputSchema = this.context.getInputSchema(0);
		if (this.inputSchema == null)

			throw new IllegalStateException(
				"Could not deserialize input schema");
		final Schema outputSchema = this.context.getOutputSchema(0);
		if (outputSchema == null)
			throw new IllegalStateException(
				"Could not deserialize output schema");
		this.collector = new JsonCollector(outputSchema);
		SopremoUtil.configureWithTransferredState(this, TypedSopremoMap.class, parameters);
		SopremoRuntime.getInstance().setCurrentEvaluationContext(this.getContext());
	}

	@Override
	public final EvaluationContext getContext() {
		return this.context;
	}

	/**
	 * This method must be implemented to provide a user implementation of a map.
	 * 
	 * @param value
	 *        the {IJsonNode} to be mapped
	 * @param out
	 *        a collector that collects all output nodes
	 */
	protected abstract void map(IJsonNode value, JsonCollector out);

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.pact.common.stubs.MapStub#map(eu.stratosphere.pact.common
	 * .type.PactRecord, eu.stratosphere.pact.common.stubs.Collector)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void map(final PactRecord record, final Collector<PactRecord> out) {
		this.context.incrementInputCount();
		this.collector.configure(out, this.context);
		final T input = (T) this.inputSchema.recordToJson(record);
		if (SopremoUtil.LOG.isTraceEnabled())
			SopremoUtil.LOG.trace(String.format("%s %s", this.getContext().getOperatorDescription(), input));
		try {
			this.map(input, this.collector);
		} catch (final RuntimeException e) {
			SopremoUtil.LOG.error(String.format(
				"Error occurred @ %s with %s: %s", this.getContext().getOperatorDescription(), input, e));
			throw e;
		}
	};
}
