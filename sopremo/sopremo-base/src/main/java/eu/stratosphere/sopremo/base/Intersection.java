package eu.stratosphere.sopremo.base;

import java.util.Iterator;

import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.operator.CompositeOperator;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.OutputCardinality;
import eu.stratosphere.sopremo.operator.SopremoModule;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoReduce;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;

/**
 * Calculates the set-based intersection of two or more input streams.<br>
 * It is assumed that each value occurs only once in each input stream.<br>
 * A value <i>v</i> is emitted only iff <i>v</i> is contained once in every input stream.
 * 
 * @author Arvid Heise
 */
@Name(verb = "intersect")
@InputCardinality(min = 1)
@OutputCardinality(1)
public class Intersection extends CompositeOperator<Intersection> {
	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.operator.CompositeOperator#addImplementation(eu.stratosphere.sopremo.operator.SopremoModule
	 * , eu.stratosphere.sopremo.EvaluationContext)
	 */
	@Override
	public void addImplementation(final SopremoModule module, final EvaluationContext context) {
		final UnionAll merged = new UnionAll().withInputs(module.getInputs());
		module.embed(new FilterLess().withThreshold(module.getNumInputs()).withInputs(merged));
	}

	@InputCardinality(min = 1, max = 1)
	public static class FilterLess extends ElementaryOperator<FilterLess> {
		private int threshold;

		/**
		 * Initializes Unique.
		 */
		public FilterLess() {
			this.setKeyExpressions(0, EvaluationExpression.VALUE);
		}

		public FilterLess withThreshold(final int threshold) {
			this.threshold = threshold;
			return this;
		}

		/**
		 * Returns the threshold.
		 * 
		 * @return the threshold
		 */
		public int getThreshold() {
			return this.threshold;
		}

		/**
		 * Sets the threshold to the specified value.
		 * 
		 * @param threshold
		 *        the threshold to set
		 */
		public void setThreshold(final int threshold) {
			this.threshold = threshold;
		}

		public static class Implementation extends SopremoReduce {
			private int threshold;

			/*
			 * (non-Javadoc)
			 * @see eu.stratosphere.sopremo.pact.GenericSopremoReduce#reduce(eu.stratosphere.sopremo.type.IStreamNode,
			 * eu.stratosphere.sopremo.pact.JsonCollector)
			 */
			@Override
			protected void reduce(final IStreamNode<IJsonNode> values, final JsonCollector<IJsonNode> out) {
				final Iterator<IJsonNode> iterator = values.iterator();
				final IJsonNode value = iterator.next();
				int count = 1;
				for (; iterator.hasNext(); count++)
					iterator.next();

				if (count == this.threshold)
					out.collect(value);
			}
		}
	}
}
