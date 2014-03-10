package eu.stratosphere.sopremo.pact;

import java.util.Iterator;

import com.google.common.reflect.TypeToken;

import eu.stratosphere.api.common.functions.AbstractFunction;
import eu.stratosphere.api.common.functions.GenericReducer;
import eu.stratosphere.api.common.operators.base.ReduceOperatorBase.Combinable;
import eu.stratosphere.configuration.Configuration;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;
import eu.stratosphere.sopremo.type.StreamNode;
import eu.stratosphere.sopremo.type.typed.TypedObjectNode;
import eu.stratosphere.util.Collector;

/**
 * An abstract implementation of the {@link GenericReducer}. SopremoReduce provides the functionality to convert the
 * standard input of the ReduceFunction to a more manageable representation (the input is converted to an
 * {@link IStreamNode} ).
 */
public abstract class GenericSopremoReduce<Elem extends IJsonNode, Out extends IJsonNode> extends AbstractFunction
		implements GenericReducer<SopremoRecord, SopremoRecord>, SopremoFunction {
	private EvaluationContext context;

	private JsonCollector<Out> collector;

	private RecordToJsonIterator<? extends Elem> iterator;

	private final StreamNode<Elem> array = new StreamNode<Elem>();

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.api.functions.GenericReducer#combine(java.util.Iterator,
	 * eu.stratosphere.api.record.functions.Collector)
	 */
	@Override
	public void combine(final Iterator<SopremoRecord> records, final Collector<SopremoRecord> collector)
			throws Exception {
		this.collector.configure(collector);
		this.iterator.setIterator(records);

		try {
			if (SopremoUtil.DEBUG && SopremoUtil.LOG.isTraceEnabled()) {
				final ArrayNode<Elem> array = new ArrayNode<Elem>(this.array);
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

	@Override
	public final EvaluationContext getContext() {
		return this.context;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.api.record.functions.Function#open(eu.stratosphere.configuration.Configuration)
	 */
	@Override
	public void open(final Configuration parameters) {
		SopremoEnvironment.getInstance().load(parameters);
		// SopremoEnvironment.getInstance().setConfigurationAndContext(parameters, getRuntimeContext());
		this.context = SopremoEnvironment.getInstance().getEvaluationContext();
		final TypedObjectNode typedInputNode =
			SopremoUtil.getTypedNodes(TypeToken.of(this.getClass()).getSupertype(GenericSopremoReduce.class))[0];
		this.iterator = typedInputNode == null ?
			new UntypedRecordToJsonIterator<Elem>() : new TypedRecordToJsonIterator<Elem>(typedInputNode);
		this.collector = new JsonCollector<Out>(this.context);
		SopremoUtil.configureWithTransferredState(this, GenericSopremoReduce.class, parameters);
		this.array.setNodeIterator(this.iterator);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.api.record.functions.ReduceFunction#reduce(java.util.Iterator,
	 * eu.stratosphere.api.record.functions.Collector)
	 */
	@Override
	public void reduce(final Iterator<SopremoRecord> records, final Collector<SopremoRecord> out) {
		this.collector.configure(out);
		this.iterator.setIterator(records);

		try {
			if (SopremoUtil.DEBUG && SopremoUtil.LOG.isTraceEnabled()) {
				final ArrayNode<Elem> array = new ArrayNode<Elem>(this.array);
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

	/**
	 * This method can be overridden by reduce stubs that want to make use of the combining feature.
	 * In addition, the ReduceFunction extending class must be annotated as Combinable.
	 * <p>
	 * The use of the combiner is typically a pre-reduction of the data. It works similar as the reducer, only that is
	 * is not guaranteed to see all values with the same key in one call to the combine function. Since it is called
	 * prior to the <code>reduce()</code> method, input and output types of the combine method are the input types of
	 * the <code>reduce()</code> method.
	 * 
	 * @see Combinable
	 * @param values
	 *        The records to be combined. Unlike in the reduce method, these are not necessarily all records
	 *        belonging to the given key.
	 * @param out
	 *        The collector to write the result to.
	 *        decide whether to retry the combiner execution.
	 */
	protected void combine(final IStreamNode<Elem> values, final JsonCollector<Out> out) {
		this.reduce(values, out);
	}

	/**
	 * This method must be implemented to provide a user implementation of a reduce.
	 * 
	 * @param values
	 *        an {@link IArrayNode} that holds all elements that belong to the same key
	 * @param collector
	 *        a collector that collects all output nodes
	 */
	protected abstract void reduce(IStreamNode<Elem> values, JsonCollector<Out> collector);
}
