package eu.stratosphere.sopremo.pact;

import java.util.Iterator;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.common.stubs.Collector;
import eu.stratosphere.pact.generic.stub.AbstractStub;
import eu.stratosphere.pact.generic.stub.GenericCoGrouper;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;
import eu.stratosphere.sopremo.type.StreamNode;

/**
 * An abstract implementation of the {@link CoGroupStub}. SopremoCoGroup provides the functionality to convert the
 * standard input of the CoGroupStub to a more manageable representation (both inputs are converted to an
 * {@link IArrayNode}).
 */
public abstract class TypedSopremoCoGroup<LeftType extends IJsonNode, RightType extends IJsonNode> extends AbstractStub
		implements GenericCoGrouper<SopremoRecord, SopremoRecord, SopremoRecord>, SopremoStub {
	private EvaluationContext context;

	private JsonCollector collector;

	private RecordToJsonIterator cachedIterator1, cachedIterator2;

	private final StreamNode<LeftType> leftArray = new StreamNode<LeftType>();

	private final StreamNode<RightType> rightArray = new StreamNode<RightType>();

	/**
	 * This method must be overridden by CoGoup UDFs that want to make use of the combining feature
	 * on their first input. In addition, the extending class must be annotated as CombinableFirst.
	 * <p>
	 * The use of the combiner is typically a pre-reduction of the data.
	 * 
	 * @param records
	 *        The records to be combined.
	 * @param out
	 *        The collector to write the result to.
	 * @throws Exception
	 *         Implementations may forward exceptions, which are caught by the runtime. When the
	 *         runtime catches an exception, it aborts the combine task and lets the fail-over logic
	 *         decide whether to retry the combiner execution.
	 */
	@Override
	public void combineFirst(Iterator<SopremoRecord> records, Collector<SopremoRecord> out) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method must be overridden by CoGoup UDFs that want to make use of the combining feature
	 * on their second input. In addition, the extending class must be annotated as CombinableSecond.
	 * <p>
	 * The use of the combiner is typically a pre-reduction of the data.
	 * 
	 * @param records
	 *        The records to be combined.
	 * @param out
	 *        The collector to write the result to.
	 * @throws Exception
	 *         Implementations may forward exceptions, which are caught by the runtime. When the
	 *         runtime catches an exception, it aborts the combine task and lets the fail-over logic
	 *         decide whether to retry the combiner execution.
	 */
	@Override
	public void combineSecond(Iterator<SopremoRecord> records, Collector<SopremoRecord> out) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.common.stubs.CoGroupStub#coGroup(java.util.Iterator, java.util.Iterator,
	 * eu.stratosphere.pact.common.stubs.Collector)
	 */
	@Override
	public void coGroup(final Iterator<SopremoRecord> records1, final Iterator<SopremoRecord> records2,
			final Collector<SopremoRecord> out) {
		this.collector.configure(out, this.context);
		this.cachedIterator1.setIterator(records1);
		this.cachedIterator2.setIterator(records2);

		try {
			if (SopremoUtil.DEBUG && SopremoUtil.LOG.isTraceEnabled()) {
				ArrayNode<LeftType> leftArray = new ArrayNode<LeftType>(this.leftArray);
				ArrayNode<RightType> rightArray = new ArrayNode<RightType>(this.rightArray);

				SopremoUtil.LOG.trace(String.format("%s %s/%s", this.getContext().getOperatorDescription(), leftArray,
					rightArray));
				this.coGroup(new StreamNode<LeftType>(leftArray.iterator()),
					new StreamNode<RightType>(rightArray.iterator()), this.collector);
			} else
				this.coGroup(this.leftArray, this.rightArray, this.collector);
		} catch (final RuntimeException e) {
			SopremoUtil.LOG.error(String.format("Error occurred @ %s with %s/%s: %s", this.getContext()
				.getOperatorDescription(), this.leftArray, this.rightArray, e));
			throw e;
		}
	}

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
		this.collector = new JsonCollector(SopremoUtil.getLayout(parameters));
		this.cachedIterator1 = new RecordToJsonIterator();
		this.cachedIterator2 = new RecordToJsonIterator();
		SopremoUtil.configureWithTransferredState(this, TypedSopremoCoGroup.class, parameters);
		SopremoEnvironment.getInstance().setEvaluationContext(this.getContext());
		this.leftArray.setNodeIterator((Iterator<LeftType>) this.cachedIterator1);
		this.rightArray.setNodeIterator((Iterator<RightType>) this.cachedIterator2);
	}

	/**
	 * This method must be implemented to provide a user implementation of a CoGroup.
	 * 
	 * @param values1
	 *        an {@link OneTimeArrayNode} that holds all elements of the first input which were paired with the key
	 * @param values2
	 *        an {@link OneTimeArrayNode} that holds all elements of the second input which were paired with the key
	 * @param out
	 *        a collector that collects all output pairs
	 */
	protected abstract void coGroup(IStreamNode<LeftType> values1, IStreamNode<RightType> values2, JsonCollector out);

	@Override
	public final EvaluationContext getContext() {
		return this.context;
	}
}
