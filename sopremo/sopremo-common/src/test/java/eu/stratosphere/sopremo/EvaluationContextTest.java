package eu.stratosphere.sopremo;

import eu.stratosphere.sopremo.type.IntNode;

public class EvaluationContextTest extends EqualCloneTest<EvaluationContext> {

	@Override
	protected EvaluationContext createDefaultInstance(int index) {
		EvaluationContext context = new EvaluationContext();
		context.putParameter("index", IntNode.valueOf(index));
		return context;
	}

}
