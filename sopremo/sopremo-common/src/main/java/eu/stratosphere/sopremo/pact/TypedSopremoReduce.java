package eu.stratosphere.sopremo.pact;

import java.util.Iterator;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.common.stubs.Collector;
import eu.stratosphere.pact.generic.stub.AbstractStub;
import eu.stratosphere.pact.generic.stub.GenericReducer;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;
import eu.stratosphere.sopremo.type.StreamNode;

/**
 * An abstract implementation of the {@link ReduceStub}. SopremoReduce provides the functionality to convert the
 * standard input of the ReduceStub to a more manageable representation (the input is converted to an {@link IArrayNode}
 * ).
 */
public abstract class TypedSopremoReduce<T extends IJsonNode> extends AbstractStub
		implements GenericReducer<SopremoRecord, SopremoRecord>, SopremoStub {
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
		SopremoEnvironment.getInstance().setClassLoader(getClass().getClassLoader());
		this.context = SopremoUtil.getEvaluationContext(parameters);
		this.cachedIterator = new RecordToJsonIterator();
		this.collector = new JsonCollector(SopremoUtil.getLayout(parameters));
		SopremoUtil.configureWithTransferredState(this, TypedSopremoReduce.class, parameters);
		SopremoEnvironment.getInstance().setEvaluationContext(this.getContext());
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
	 * @see eu.stratosphere.pact.generic.stub.GenericReducer#combine(java.util.Iterator,
	 * eu.stratosphere.pact.common.stubs.Collector)
	 */
	@Override
	public void combine(Iterator<SopremoRecord> records, Collector<SopremoRecord> out) throws Exception {
		this.collector.configure(out, this.context);
		this.cachedIterator.setIterator(records);

		try {
			if (SopremoUtil.DEBUG && SopremoUtil.LOG.isTraceEnabled()) {
				ArrayNode<T> array = new ArrayNode<T>(this.array);
				SopremoUtil.LOG.trace(String.format("%s %s", this.getContext().getOperatorDescription(), array));
				this.combine(array, this.collector);
			} else
				this.combine(this.array, this.collector);
		} catch (final RuntimeException e) {
			SopremoUtil.LOG.error(String.format("Error occurred @ %s with %s: %s",
				this.getContext().getOperatorDescription(),
				this.array, e));
			throw e;
		}
	}

	/**
	 * This method can be overridden by reduce stubs that want to make use of the combining feature.
	 * In addition, the ReduceStub extending class must be annotated as Combinable.
	 * <p>
	 * The use of the combiner is typically a pre-reduction of the data. It works similar as the reducer, only that is
	 * is not guaranteed to see all values with the same key in one call to the combine function. Since it is called
	 * prior to the <code>reduce()</code> method, input and output types of the combine method are the input types of
	 * the <code>reduce()</code> method.
	 * 
	 * @see eu.stratosphere.pact.common.contract.ReduceContract.Combinable
	 * @param records
	 *        The records to be combined. Unlike in the reduce method, these are not necessarily all records
	 *        belonging to the given key.
	 * @param out
	 *        The collector to write the result to.
	 * @throws Exception
	 *         Implementations may forward exceptions, which are caught by the runtime. When the
	 *         runtime catches an exception, it aborts the combine task and lets the fail-over logic
	 *         decide whether to retry the combiner execution.
	 */
	protected void combine(IStreamNode<T> values, JsonCollector out) {
		reduce(values, out);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.common.stubs.ReduceStub#reduce(java.util.Iterator,
	 * eu.stratosphere.pact.common.stubs.Collector)
	 */
	@Override
	public void reduce(final Iterator<SopremoRecord> records, final Collector<SopremoRecord> out) {
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
