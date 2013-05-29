package eu.stratosphere.sopremo.pact;

import java.util.Iterator;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.common.stubs.Collector;
import eu.stratosphere.pact.common.stubs.ReduceStub;
import eu.stratosphere.pact.common.type.PactRecord;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.SopremoRuntime;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;
import eu.stratosphere.sopremo.type.StreamNode;

/**
 * An abstract implementation of the {@link ReduceStub}. SopremoReduce provides the functionality to convert the
 * standard input of the ReduceStub to a more manageable representation (the input is converted to an {@link IArrayNode}
 * ).
 */
public abstract class TypedSopremoReduce<T extends IJsonNode> extends ReduceStub implements SopremoStub {
	private EvaluationContext context;

	private JsonCollector collector;

	private RecordToJsonIterator cachedIterator;

	private final StreamNode<T> array = new StreamNode<T>();

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.common.stubs.Stub#open(eu.stratosphere.nephele.configuration.Configuration)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void open(final Configuration parameters) throws Exception {
		// We need to pass our class loader since the default class loader is
		// not able to resolve classes coming from the Sopremo user jar file.
		SopremoRuntime.getInstance().setClassLoader(getClass().getClassLoader());
		this.context = (EvaluationContext) SopremoUtil.getObject(parameters, SopremoUtil.CONTEXT, null);
		this.cachedIterator = new RecordToJsonIterator(this.context.getInputSchema(0));
		this.collector = new JsonCollector(this.context.getOutputSchema(0));
		SopremoUtil.configureWithTransferredState(this, TypedSopremoReduce.class, parameters);
		SopremoRuntime.getInstance().setCurrentEvaluationContext(this.getContext());
		this.array.setNodeIterator((Iterator<T>) this.cachedIterator);
	}

	@Override
	public final EvaluationContext getContext() {
		return this.context;
	}

	/**
	 * This method must be implemented to provide a user implementation of a reduce.
	 * 
	 * @param values
	 *        an {@link IArrayNode} that holds all elements that belong to the same key
	 * @param out
	 *        a collector that collects all output nodes
	 */
	protected abstract void reduce(IStreamNode<T> values, JsonCollector out);

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.common.stubs.ReduceStub#reduce(java.util.Iterator,
	 * eu.stratosphere.pact.common.stubs.Collector)
	 */
	@Override
	public void reduce(final Iterator<PactRecord> records, final Collector<PactRecord> out) {
		this.context.incrementInputCount();
		this.collector.configure(out, this.context);
		this.cachedIterator.setIterator(records);

		try {
			if (SopremoUtil.DEBUG && SopremoUtil.LOG.isTraceEnabled()) {
				ArrayNode<T> array = new ArrayNode<T>(this.array);
				SopremoUtil.LOG.trace(String.format("%s %s", this.getContext().getOperatorDescription(), array));
				this.reduce(array, this.collector);
			} else
				this.reduce(this.array, this.collector);
		} catch (final RuntimeException e) {
			SopremoUtil.LOG.error(String.format("Error occurred @ %s with %s: %s",
				this.getContext().getOperatorDescription(),
				this.array, e));
			throw e;
		}
	}
}
