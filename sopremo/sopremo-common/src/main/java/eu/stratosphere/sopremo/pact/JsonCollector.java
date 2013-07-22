package eu.stratosphere.sopremo.pact;

import eu.stratosphere.pact.common.stubs.Collector;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * The JsonCollector converts {@link IJsonNode}s to {@link PactRecord}s and collects this records with a given
 * Collector.
 */
public class JsonCollector {

	private Collector<SopremoRecord> collector;

	private EvaluationContext context;

	private EvaluationExpression resultProjection = EvaluationExpression.VALUE;
	
	private final SopremoRecord sopremoRecord;

	/**
	 * Initializes a JsonCollector with the given {@link Schema}.
	 * 
	 * @param schema
	 *        the schema that should be used for the IJsonNode - PactRecord conversion.
	 */
	public JsonCollector(final SopremoRecordLayout sopremoRecordLayout) {
		this.sopremoRecord = new SopremoRecord(sopremoRecordLayout);
	}

	/**
	 * Sets the collector to the specified value.
	 * 
	 * @param collector
	 *        the collector to set
	 */
	public void configure(final Collector<SopremoRecord> collector, final EvaluationContext context) {
		this.collector = collector;
		this.context = context;
		this.resultProjection = context.getResultProjection();
	}

	/**
	 * Returns the context.
	 * 
	 * @return the context
	 */
	public EvaluationContext getContext() {
		return this.context;
	}

	/**
	 * Collects the given {@link IJsonNode}
	 * 
	 * @param value
	 *        the node that should be collected
	 */
	public void collect(final IJsonNode value) {
		final IJsonNode resultValue = this.resultProjection.evaluate(value);
		if (SopremoUtil.LOG.isTraceEnabled())
			SopremoUtil.LOG.trace(String.format(" to %s", resultValue));
		this.sopremoRecord.setNode(resultValue);
		this.collector.collect(this.sopremoRecord);
	}
}
