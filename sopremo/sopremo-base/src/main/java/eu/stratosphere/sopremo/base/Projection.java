package eu.stratosphere.sopremo.base;

import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.OutputCardinality;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoMap;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;
import eu.stratosphere.sopremo.type.IJsonNode;

@InputCardinality(1)
@OutputCardinality(1)
@Name(verb = "transform")
public class Projection extends ElementaryOperator<Projection> {
	/* (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.ElementaryOperator#asPactModule(eu.stratosphere.sopremo.EvaluationContext, eu.stratosphere.sopremo.serialization.SopremoRecordLayout)
	 */
	@Override
	public PactModule asPactModule(EvaluationContext context, SopremoRecordLayout layout) {
		if (this.getResultProjection() == EvaluationExpression.VALUE)
			return this.createShortCircuitModule();
		return super.asPactModule(context, layout);
	}

	public static class ProjectionStub extends SopremoMap {

		@Override
		protected void map(final IJsonNode value, final JsonCollector out) {
			out.collect(value);
		}
	}

}
