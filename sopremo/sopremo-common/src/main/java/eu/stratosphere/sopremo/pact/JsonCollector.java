package eu.stratosphere.sopremo.pact;

import eu.stratosphere.pact.common.stubs.Collector;
import eu.stratosphere.pact.common.type.PactRecord;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.serialization.Schema;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * The JsonCollector converts {@link IJsonNode}s to {@link PactRecord}s and collects this records with a given
 * Collector.
 */
public class JsonCollector {

	private Collector<PactRecord> collector;

	private EvaluationContext context;

	private final Schema schema;

	private PactRecord record = new PactRecord();

	private EvaluationExpression resultProjection = EvaluationExpression.VALUE;

	/**
	 * Initializes a JsonCollector with the given {@link Schema}.
	 * 
	 * @param schema
	 *        the schema that should be used for the IJsonNode - PactRecord conversion.
	 */
	public JsonCollector(final Schema schema) {
		this.schema = schema;
	}

	/**
	 * Sets the collector to the specified value.
	 * 
	 * @param collector
	 *        the collector to set
	 */
	public void configure(final Collector<PactRecord> collector, final EvaluationContext context) {
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
		this.schema.jsonToRecord(resultValue, this.record);
		this.collector.collect(this.record);
	}
}
