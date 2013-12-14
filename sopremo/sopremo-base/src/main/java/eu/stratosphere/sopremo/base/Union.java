package eu.stratosphere.sopremo.base;

import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.operator.CompositeOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.OutputCardinality;
import eu.stratosphere.sopremo.operator.SopremoModule;

/**
 * Calculates the set-based union of two or more input streams.<br>
 * If a value is contained in more than one input streams and/or more than once within one input stream, it is
 * emitted once only.
 * 
 * @author Arvid Heise
 */
@Name(verb = "union")
@InputCardinality(min = 1)
@OutputCardinality(1)
public class Union extends CompositeOperator<Union> {
	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.operator.CompositeOperator#addImplementation(eu.stratosphere.sopremo.operator.SopremoModule
	 * , eu.stratosphere.sopremo.EvaluationContext)
	 */
	@Override
	public void addImplementation(final SopremoModule module, final EvaluationContext context) {
		module.embed(new Unique().withInputs(new UnionAll().withInputs(module.getInputs())));
	}

}
