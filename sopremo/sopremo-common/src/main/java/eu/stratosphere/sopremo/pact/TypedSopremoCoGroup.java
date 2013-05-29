package eu.stratosphere.sopremo.pact;

import java.util.Iterator;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.common.stubs.CoGroupStub;
import eu.stratosphere.pact.common.stubs.Collector;
import eu.stratosphere.pact.common.type.PactRecord;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.SopremoRuntime;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;
import eu.stratosphere.sopremo.type.StreamNode;

/**
 * An abstract implementation of the {@link CoGroupStub}. SopremoCoGroup provides the functionality to convert the
 * standard input of the CoGroupStub to a more manageable representation (both inputs are converted to an
 * {@link IArrayNode}).
 */
public abstract class TypedSopremoCoGroup<LeftType extends IJsonNode, RightType extends IJsonNode> extends CoGroupStub
		implements SopremoStub {
	private EvaluationContext context;

	private JsonCollector collector;

	private RecordToJsonIterator cachedIterator1, cachedIterator2;

	private final StreamNode<LeftType> leftArray = new StreamNode<LeftType>();

	private final StreamNode<RightType> rightArray = new StreamNode<RightType>();

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.common.stubs.CoGroupStub#coGroup(java.util.Iterator, java.util.Iterator,
	 * eu.stratosphere.pact.common.stubs.Collector)
	 */
	@Override
	public void coGroup(final Iterator<PactRecord> records1, final Iterator<PactRecord> records2,
			final Collector<PactRecord> out) {
		this.context.incrementInputCount();
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
		SopremoRuntime.getInstance().setClassLoader(getClass().getClassLoader());
		this.context = (EvaluationContext) SopremoUtil.getObject(parameters, SopremoUtil.CONTEXT, null);
		this.collector = new JsonCollector(this.context.getInputSchema(0));
		this.cachedIterator1 = new RecordToJsonIterator(this.context.getInputSchema(0));
		this.cachedIterator2 = new RecordToJsonIterator(this.context.getInputSchema(1));
		SopremoUtil.configureWithTransferredState(this, TypedSopremoCoGroup.class, parameters);
		SopremoRuntime.getInstance().setCurrentEvaluationContext(this.getContext());
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
