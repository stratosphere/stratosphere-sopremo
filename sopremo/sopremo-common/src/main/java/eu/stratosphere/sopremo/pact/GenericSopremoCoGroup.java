package eu.stratosphere.sopremo.pact;

import java.util.Iterator;

import com.google.common.reflect.TypeToken;

import eu.stratosphere.api.common.functions.AbstractFunction;
import eu.stratosphere.api.common.functions.GenericCoGrouper;
import eu.stratosphere.configuration.Configuration;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;
import eu.stratosphere.sopremo.type.StreamNode;
import eu.stratosphere.sopremo.type.typed.TypedObjectNode;
import eu.stratosphere.util.Collector;

/**
 * An abstract implementation of the {@link GenericCoGrouper}. SopremoCoGroup provides the functionality to convert the
 * standard input of the GenericCoGrouper to a more manageable representation (both inputs are converted to an
 * {@link IStreamNode}).
 */
public abstract class GenericSopremoCoGroup<LeftElem extends IJsonNode, RightElem extends IJsonNode, Out extends IJsonNode>
		extends AbstractFunction
		implements GenericCoGrouper<SopremoRecord, SopremoRecord, SopremoRecord>, SopremoFunction {
	private EvaluationContext context;

	private JsonCollector<Out> collector;

	private RecordToJsonIterator<LeftElem> cachedIterator1;

	private RecordToJsonIterator<RightElem> cachedIterator2;

	private final StreamNode<LeftElem> leftArray = new StreamNode<LeftElem>();

	private final StreamNode<RightElem> rightArray = new StreamNode<RightElem>();

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.api.record.functions.CoGroupFunction#coGroup(java.util.Iterator, java.util.Iterator,
	 * eu.stratosphere.api.record.functions.Collector)
	 */
	@Override
	public void coGroup(final Iterator<SopremoRecord> records1, final Iterator<SopremoRecord> records2,
			final Collector<SopremoRecord> out) {
		this.collector.configure(out);
		this.cachedIterator1.setIterator(records1);
		this.cachedIterator2.setIterator(records2);
		try {
			if (SopremoUtil.DEBUG && SopremoUtil.LOG.isTraceEnabled()) {
				final ArrayNode<LeftElem> leftArray = new ArrayNode<LeftElem>(this.leftArray);
				final ArrayNode<RightElem> rightArray = new ArrayNode<RightElem>(this.rightArray);

				SopremoUtil.LOG.trace(String.format("%s %s/%s", this.getContext().getOperatorDescription(), leftArray,
					rightArray));
				this.coGroup(new StreamNode<LeftElem>(leftArray.iterator()),
					new StreamNode<RightElem>(rightArray.iterator()), this.collector);
			} else
				this.coGroup(this.leftArray, this.rightArray, this.collector);
		} catch (final RuntimeException e) {
			SopremoUtil.LOG.error(String.format("Error occurred @ %s with %s/%s: %s", this.getContext()
				.getOperatorDescription(), this.leftArray, this.rightArray, e));
			throw e;
		}
	}

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
	 */
	@Override
	public void combineFirst(final Iterator<SopremoRecord> records, final Collector<SopremoRecord> out) {
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
	 */
	@Override
	public void combineSecond(final Iterator<SopremoRecord> records, final Collector<SopremoRecord> out) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final EvaluationContext getContext() {
		return this.context;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.api.record.functions.Function#open(eu.stratosphere.configuration.Configuration)
	 */
	@Override
	public void open(final Configuration parameters) throws Exception {
		SopremoEnvironment.getInstance().load(parameters);
		this.context = SopremoEnvironment.getInstance().getEvaluationContext();
		this.collector = new JsonCollector<Out>(this.context);
		SopremoUtil.configureWithTransferredState(this, GenericSopremoCoGroup.class, parameters);
		final TypedObjectNode[] typedInputNodes =
			SopremoUtil.getTypedNodes(TypeToken.of(this.getClass()).getSupertype(GenericSopremoCoGroup.class));
		this.cachedIterator1 = typedInputNodes[0] == null ?
			new UntypedRecordToJsonIterator<LeftElem>() : new TypedRecordToJsonIterator<LeftElem>(typedInputNodes[0]);
		this.cachedIterator2 = typedInputNodes[1] == null ?
			new UntypedRecordToJsonIterator<RightElem>() : new TypedRecordToJsonIterator<RightElem>(typedInputNodes[1]);
		this.leftArray.setNodeIterator(this.cachedIterator1);
		this.rightArray.setNodeIterator(this.cachedIterator2);
	}

	/**
	 * This method must be implemented to provide a user implementation of a CoGroup.
	 * 
	 * @param values1
	 *        an {@link IStreamNode} that holds all elements of the first input which were paired with the key
	 * @param values2
	 *        an {@link IStreamNode} that holds all elements of the second input which were paired with the key
	 * @param out
	 *        a collector that collects all output pairs
	 */
	protected abstract void coGroup(IStreamNode<LeftElem> values1, IStreamNode<RightElem> values2,
			JsonCollector<Out> out);
}
