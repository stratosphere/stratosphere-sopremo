/***********************************************************************************************************************
 *
 * Copyright (C) 2010 by the Stratosphere project (http://stratosphere.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 **********************************************************************************************************************/
package eu.stratosphere.sopremo.expressions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.stratosphere.sopremo.expressions.tree.ChildIterator;
import eu.stratosphere.sopremo.expressions.tree.GenericListChildIterator;
import eu.stratosphere.sopremo.type.BooleanNode;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Represents a logical AND.
 */
@OptimizerHints(scope = Scope.ANY)
public class AndExpression extends BooleanExpression {
	private final List<BooleanExpression> expressions;

	/**
	 * Initializes an AndExpression with the given {@link EvaluationExpression}s.
	 * 
	 * @param expressions
	 *        the expressions which evaluate to the input for this AndExpression
	 */
	public AndExpression(final BooleanExpression... expressions) {
		this(Arrays.asList(expressions));
	}

	/**
	 * Initializes an AndExpression with the given {@link EvaluationExpression}s.
	 * 
	 * @param expressions
	 *        the expressions which evaluate to the input for this AndExpression
	 */
	public AndExpression(final List<? extends BooleanExpression> expressions) {
		this.expressions = new ArrayList<BooleanExpression>(expressions);
	}

	/**
	 * Initializes AndExpression.
	 */
	public AndExpression() {
		this(new ArrayList<BooleanExpression>());
	}

	public AndExpression addExpression(final BooleanExpression expression) {
		this.expressions.add(expression);
		return this;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!super.equals(obj))
			return false;
		final AndExpression other = (AndExpression) obj;
		return this.expressions.equals(other.expressions);
	}

	@Override
	public BooleanNode evaluate(final IJsonNode node) {
		// we can ignore 'target' because no new Object is created
		for (final EvaluationExpression booleanExpression : this.expressions)
			if (booleanExpression.evaluate(node) == BooleanNode.FALSE)
				return BooleanNode.FALSE;

		return BooleanNode.TRUE;
	}

	/**
	 * Returns the expressions.
	 * 
	 * @return the expressions
	 */
	public List<BooleanExpression> getExpressions() {
		return this.expressions;
	}

	@Override
	public int hashCode() {
		final int prime = 41;
		int result = super.hashCode();
		result = prime * result + this.expressions.hashCode();
		return result;
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append("(");
		this.append(appendable, this.expressions, " AND ");
		appendable.append(")");
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.ExpressionParent#iterator()
	 */
	@Override
	public ChildIterator iterator() {
		return new GenericListChildIterator<BooleanExpression>(this.expressions.listIterator()) {
			/*
			 * (non-Javadoc)
			 * @see
			 * eu.stratosphere.sopremo.expressions.tree.GenericListChildIterator#convert(eu.stratosphere.sopremo.expressions
			 * .EvaluationExpression)
			 */
			@Override
			protected BooleanExpression convert(EvaluationExpression childExpression) {
				return BooleanExpression.ensureBooleanExpression(childExpression);
			}
		};
	}

	/**
	 * Creates an AndExpression with the given {@link BooleanExpression}.
	 * 
	 * @param expression
	 *        the expression that should be used as the condition
	 * @return the created AndExpression
	 */
	public static AndExpression valueOf(final BooleanExpression expression) {
		if (expression instanceof AndExpression)
			return (AndExpression) expression;
		return new AndExpression(expression);
	}

	/**
	 * Creates an AndExpression with the given {@link BooleanExpression}s.
	 * 
	 * @param childConditions
	 *        the expressions that should be used as conditions for the created AndExpression
	 * @return the created AndExpression
	 */
	public static AndExpression valueOf(final List<? extends EvaluationExpression> childConditions) {
		final List<BooleanExpression> booleanExpressions = BooleanExpression.ensureBooleanExpressions(childConditions);
		if (booleanExpressions.size() == 1)
			return valueOf(booleanExpressions.get(0));
		return new AndExpression(booleanExpressions.toArray(new BooleanExpression[booleanExpressions.size()]));
	}
}