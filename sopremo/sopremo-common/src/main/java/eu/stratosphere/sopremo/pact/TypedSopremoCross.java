package eu.stratosphere.sopremo.pact;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.common.stubs.Collector;
import eu.stratosphere.pact.common.stubs.CrossStub;
import eu.stratosphere.pact.common.type.PactRecord;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.SopremoRuntime;
import eu.stratosphere.sopremo.serialization.Schema;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * An abstract implementation of the {@link CrossStub}. SopremoCross provides the functionality to convert the
 * standard input of the CrossStub to a more manageable representation (both inputs are converted to an
 * {@link IJsonNode}).
 */
public abstract class TypedSopremoCross<LeftType extends IJsonNode, RightType extends IJsonNode> extends CrossStub
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
		SopremoUtil.configureWithTransferredState(this, TypedSopremoCross.class, parameters);
		SopremoRuntime.getInstance().setCurrentEvaluationContext(this.getContext());
	}

	/**
	 * This method must be implemented to provide a user implementation of a cross.
	 * 
	 * @param values1
	 *        an {@link IJsonNode} from the first input
	 * @param values2
	 *        an {@link IJsonNode} from the second input
	 * @param out
	 *        a collector that collects all output pairs
	 */
	protected abstract void cross(IJsonNode value1, IJsonNode value2, JsonCollector out);

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.common.stubs.CrossStub#cross(eu.stratosphere.pact.common.type.PactRecord,
	 * eu.stratosphere.pact.common.type.PactRecord, eu.stratosphere.pact.common.stubs.Collector)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void cross(final PactRecord record1, final PactRecord record2, final Collector<PactRecord> out) {
		this.context.incrementInputCount();
		this.collector.configure(out, this.context);
		final LeftType input1 = (LeftType) this.inputSchema1.recordToJson(record1);
		final RightType input2 = (RightType) this.inputSchema2.recordToJson(record2);

		if (SopremoUtil.LOG.isTraceEnabled())
			SopremoUtil.LOG
				.trace(String.format("%s %s/%s", this.getContext().getOperatorDescription(), input1, input2));
		try {
			this.cross(input1, input2, this.collector);
		} catch (final RuntimeException e) {
			SopremoUtil.LOG.error(String.format("Error occurred @ %s with v1 %s/%s v2: %s", this.getContext()
				.getOperatorDescription(), input1, input2, e));
			throw e;
		}
	}

	@Override
	public final EvaluationContext getContext() {
		return this.context;
	}
}
