package eu.stratosphere.sopremo.base;

import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.JsonStream;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoCoGroup;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;

/**
 * Calculates the set-based difference of two or more input streams.<br>
 * Specifically, given a value <i>v</i> of the first input, the output contains <i>v</i> iff no other input contains an
 * equal value to <i>v</i>.<br>
 * If the first input contains multiple identical entries, only one representation is emitted.
 * 
 * @author Arvid Heise
 */
@Name(verb = "subtract")
public class Difference extends SetOperation<Difference> {

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.base.SetOperation#createBinaryOperations(eu.stratosphere.sopremo.JsonStream,
	 * eu.stratosphere.sopremo.JsonStream)
	 */
	@Override
	protected ElementaryOperator<?> createBinaryOperations(JsonStream leftInput, JsonStream rightInput) {
		return new TwoInputDifference().withInputs(leftInput, rightInput);
	}

	@InputCardinality(min = 2, max = 2)
	public static class TwoInputDifference extends ElementaryOperator<TwoInputDifference> {
		public static class Implementation extends SopremoCoGroup {
			@Override
			protected void coGroup(final IStreamNode<IJsonNode> values1, final IStreamNode<IJsonNode> values2,
					final JsonCollector out) {
				if (values2.isEmpty())
					out.collect(values1.iterator().next());
			}
		}
	}
}
