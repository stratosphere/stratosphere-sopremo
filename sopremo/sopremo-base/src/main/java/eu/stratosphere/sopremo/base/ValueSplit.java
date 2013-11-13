package eu.stratosphere.sopremo.base;

import java.util.ArrayList;
import java.util.List;

import eu.stratosphere.sopremo.expressions.ArrayCreation;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoMap;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Splits a tuple explicitly into multiple outgoing tuples.<br>
 * This operator provides a means to emit more than one tuple in contrast to most other base operators.
 * 
 * @author Arvid Heise
 */
@Name(verb = "split value")
public class ValueSplit extends ElementaryOperator<ValueSplit> {
	private List<EvaluationExpression> projections = new ArrayList<EvaluationExpression>();

	public ValueSplit addProjection(EvaluationExpression... projections) {
		for (EvaluationExpression evaluationExpression : projections)
			this.projections.add(evaluationExpression);
		return this;
	}

	@Name(preposition = "into")
	@Property
	public void setProjections(ArrayCreation projections) {
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

	public void setProjections(List<EvaluationExpression> projections) {
		if (projections == null)
			throw new NullPointerException("projections must not be null");

		this.projections = projections;
	}
	
	public ValueSplit withProjections(List<EvaluationExpression> projections) {
		setProjections(projections);
		return this;
	}
	
	public ValueSplit withProjections(ArrayCreation projections) {
		setProjections(projections);
		return this;
	}

	public static class Implementation extends SopremoMap {
		private List<EvaluationExpression> projections = new ArrayList<EvaluationExpression>();

		@Override
		protected void map(IJsonNode value, JsonCollector<IJsonNode> out) {
			for (EvaluationExpression projection : this.projections)
				out.collect(projection.evaluate(value));
		}
	}
}
