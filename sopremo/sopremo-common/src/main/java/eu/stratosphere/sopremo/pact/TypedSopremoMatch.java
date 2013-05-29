package eu.stratosphere.sopremo.pact;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.common.stubs.Collector;
import eu.stratosphere.pact.common.stubs.MatchStub;
import eu.stratosphere.pact.common.type.PactRecord;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.SopremoRuntime;
import eu.stratosphere.sopremo.serialization.Schema;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * An abstract implementation of the {@link MatchStub}. SopremoMatch provides the functionality to convert the
 * standard input of the MatchStub to a more manageable representation (both inputs are converted to an
 * {@link IJsonNode}).
 */
public abstract class TypedSopremoMatch<LeftType extends IJsonNode, RightType extends IJsonNode> extends MatchStub
		implements SopremoStub {
	private EvaluationContext context;

	private Schema inputSchema1, inputSchema2;

	private JsonCollector collector;

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.common.stubs.Stub#open(eu.stratosphere.nephele.configuration.Configuration)
	 */
	@Override
	public void open(final Configuration parameters) throws Exception {
		// We need to pass our class loader since the default class loader is
		// not able to resolve classes coming from the Sopremo user jar file.
		SopremoRuntime.getInstance().setClassLoader(getClass().getClassLoader());
		this.context = (EvaluationContext) SopremoUtil.getObject(parameters, SopremoUtil.CONTEXT, null);
		this.inputSchema1 = this.context.getInputSchema(0);
		this.inputSchema2 = this.context.getInputSchema(1);
		this.collector = new JsonCollector(this.context.getOutputSchema(0));
		SopremoUtil.configureWithTransferredState(this, TypedSopremoMatch.class, parameters);
		SopremoRuntime.getInstance().setCurrentEvaluationContext(this.getContext());
	}

	@Override
	public final EvaluationContext getContext() {
		return this.context;
	}

	/**
	 * This method must be implemented to provide a user implementation of a match.
	 * 
	 * @param value1
	 *        an {@link IJsonNode} that comes from the first input
	 * @param value2
	 *        an {@link IJsonNode} that comes from the second input
	 * @param out
	 *        a collector that collects all output pairs
	 */
	protected abstract void match(LeftType value1, RightType value2, JsonCollector out);

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.common.stubs.MatchStub#match(eu.stratosphere.pact.common.type.PactRecord,
	 * eu.stratosphere.pact.common.type.PactRecord, eu.stratosphere.pact.common.stubs.Collector)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void match(final PactRecord record1, final PactRecord record2, final Collector<PactRecord> out) {
		this.context.incrementInputCount();
		this.collector.configure(out, this.context);
		final LeftType input1 = (LeftType) this.inputSchema1.recordToJson(record1);
		final RightType input2 = (RightType) this.inputSchema2.recordToJson(record2);
		if (SopremoUtil.LOG.isTraceEnabled())
			SopremoUtil.LOG.trace(String.format("%s %s/%s", this.getContext().getOperatorDescription(), input1,
				input2));
		try {
			this.match(input1, input2, this.collector);
		} catch (final RuntimeException e) {
			SopremoUtil.LOG.error(String.format("Error occurred @ %s with %s/%s: %s", this.getContext()
				.getOperatorDescription(), input1, input2, e));
			throw e;
		}
	}
}
