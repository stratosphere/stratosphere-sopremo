package eu.stratosphere.sopremo.base;

import java.util.List;

import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.CompositeOperator;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.JsonStream;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.OutputCardinality;
import eu.stratosphere.sopremo.operator.SopremoModule;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoCoGroup;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;

/**
 * Calculates the set-based difference of two or more input streams.<br>
 * Specifically, given a value <i>v</i> of the first input, the output contains <i>v</i> iff no other input contains an
 * equal value to <i>v</i>.<br>
 * If the first input contains multiple identical entries, only one representation is emitted.
 */
@Name(verb = "subtract")
@InputCardinality(min = 1)
@OutputCardinality(1)
public class Difference extends CompositeOperator<Difference> {
	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.operator.CompositeOperator#addImplementation(eu.stratosphere.sopremo.operator.SopremoModule
	 * , eu.stratosphere.sopremo.EvaluationContext)
	 */
	@Override
	public void addImplementation(final SopremoModule module) {
		final Source leftInput = module.getInput(0);
		final List<Source> otherInputs = module.getInputs().subList(1, module.getInputs().size());
		if (otherInputs.isEmpty())
			module.embed(new Unique().withInputs(leftInput));
		else {
			final JsonStream unionOfOtherInputs = new UnionAll().withInputs(otherInputs);
			module.embed(new TwoInputDifference().withInputs(leftInput, unionOfOtherInputs));
		}
	}

	@InputCardinality(min = 2, max = 2)
	public static class TwoInputDifference extends ElementaryOperator<TwoInputDifference> {
		public static class Implementation extends SopremoCoGroup {
			@Override
			protected void coGroup(final IStreamNode<IJsonNode> values1, final IStreamNode<IJsonNode> values2,
					final JsonCollector<IJsonNode> out) {
				if (values2.isEmpty())
					out.collect(values1.iterator().next());
			}
		}
	}
}
