package eu.stratosphere.sopremo.pact;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.common.stubs.Collector;
import eu.stratosphere.pact.generic.stub.AbstractStub;
import eu.stratosphere.pact.generic.stub.GenericMapper;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * An abstract implementation of the {@link MapStub}. SopremoMap provides the functionality to convert the
 * standard input of the MapStub to a more manageable representation (the input is converted to an {@link IJsonNode}).
 */
public abstract class TypedSopremoMap<T extends IJsonNode> extends AbstractStub implements
		GenericMapper<SopremoRecord, SopremoRecord>, SopremoStub {
	private EvaluationContext context;

	private JsonCollector collector;

	@Override
	public void open(final Configuration parameters) {
		// We need to pass our class loader since the default class loader is
		// not able to resolve classes coming from the Sopremo user jar file.
		SopremoEnvironment.getInstance().setClassLoader(getClass().getClassLoader());
		this.context = SopremoUtil.getEvaluationContext(parameters);
		this.collector = new JsonCollector(SopremoUtil.getLayout(parameters));
		SopremoUtil.configureWithTransferredState(this, TypedSopremoMap.class, parameters);
		SopremoEnvironment.getInstance().setEvaluationContext(this.getContext());
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
	public void map(final SopremoRecord record, final Collector<SopremoRecord> out) {
		this.collector.configure(out, this.context);
		final T input = (T) record.getNode();
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
