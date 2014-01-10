package eu.stratosphere.sopremo.base;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import eu.stratosphere.sopremo.expressions.ArrayCreation;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.OutputCardinality;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoMap;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Splits a tuple explicitly into multiple outgoing tuples.<br>
 * This operator provides a means to emit more than one tuple in contrast to most other base operators.
 */
@Name(verb = "split value")
@InputCardinality(1)
@OutputCardinality(1)
public class ValueSplit extends ElementaryOperator<ValueSplit> {
	private List<EvaluationExpression> projections = new ArrayList<EvaluationExpression>();

	public ValueSplit addProjection(final EvaluationExpression... projections) {
		for (final EvaluationExpression evaluationExpression : projections)
			this.projections.add(evaluationExpression);
		return this;
	}

	@Name(preposition = "into")
	@Property
	public void setProjections(final ArrayCreation projections) {
		if (projections == null)
			throw new NullPointerException("projections must not be null");

		this.projections = projections.getElements();
	}

	/**
	 * Returns the projections.
	 * 
	 * @return the projections
	 */
	public ArrayCreation getProjections() {
		return new ArrayCreation(this.projections);
	}

	public void setProjections(final List<EvaluationExpression> projections) {
		if (projections == null)
			throw new NullPointerException("projections must not be null");

		this.projections = projections;
	}

	public void setProjections(final EvaluationExpression... projections) {
		if (projections == null)
			throw new NullPointerException("projections must not be null");

		this.projections = Lists.newArrayList(projections);
	}

	public ValueSplit withProjections(final List<EvaluationExpression> projections) {
		this.setProjections(projections);
		return this;
	}

	public ValueSplit withProjections(final EvaluationExpression... projections) {
		this.setProjections(projections);
		return this;
	}

	public ValueSplit withProjections(final ArrayCreation projections) {
		this.setProjections(projections);
		return this;
	}

	public static class Implementation extends SopremoMap {
		private final List<EvaluationExpression> projections = new ArrayList<EvaluationExpression>();

		@Override
		protected void map(final IJsonNode value, final JsonCollector<IJsonNode> out) {
			for (final EvaluationExpression projection : this.projections)
				out.collect(projection.evaluate(value));
		}
	}
}
