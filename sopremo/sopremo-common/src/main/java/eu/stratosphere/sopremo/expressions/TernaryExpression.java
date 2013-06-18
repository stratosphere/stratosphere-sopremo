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

import eu.stratosphere.sopremo.cache.NodeCache;
import eu.stratosphere.sopremo.expressions.tree.ChildIterator;
import eu.stratosphere.sopremo.expressions.tree.NamedChildIterator;
import eu.stratosphere.sopremo.type.BooleanNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.TypeCoercer;

/**
 * Represents a if-then-else clause.
 */
public class TernaryExpression extends EvaluationExpression {

	private EvaluationExpression ifClause;

	private EvaluationExpression ifExpression, thenExpression;

	/**
	 * Initializes a TernaryExpression with the given {@link EvaluationExpression}s.
	 * 
	 * @param ifClause
	 *        the expression that represents the condition of this {@link TernaryExpression}
	 * @param ifExpression
	 *        the expression that should be evaluated if the iFClause evaluation results in {@link BooleanNode.TRUE}
	 * @param thenExpression
	 *        the expression that should be evaluated if the iFClause evaluation results in {@link BooleanNode.FALSE}
	 */
	public TernaryExpression(final EvaluationExpression ifClause, final EvaluationExpression ifExpression,
			final EvaluationExpression thenExpression) {
		this.ifClause = ifClause;
		this.ifExpression = ifExpression;
		this.thenExpression = thenExpression;
	}

	/**
	 * Initializes a TernaryExpression with the given {@link EvaluationExpression}s.
	 * 
	 * @param ifClause
	 *        the expression that represents the condition of this {@link TernaryExpression}
	 * @param ifExpression
	 *        the expression that should be evaluated if the iFClause evaluation results in {@link BooleanNode.TRUE}
	 */
	public TernaryExpression(final EvaluationExpression ifClause, final EvaluationExpression ifExpression) {
		this(ifClause, ifExpression, ConstantExpression.MISSING);
	}

	/**
	 * Initializes TernaryExpression.
	 */
	public TernaryExpression() {
		this.ifClause = null;
		this.ifExpression = null;
		this.thenExpression = null;
	}

	/**
	 * Returns the ifClause-expression
	 * 
	 * @return the ifClause-expression
	 */
	public EvaluationExpression getIfClause() {
		return this.ifClause;
	}

	/**
	 * Returns the ifExpression
	 * 
	 * @return the ifExpression
	 */
	public EvaluationExpression getIfExpression() {
		return this.ifExpression;
	}

	/**
	 * Returns the thenExpression
	 * 
	 * @return the thenExpression
	 */
	public EvaluationExpression getThenExpression() {
		return this.thenExpression;
	}

	private final transient NodeCache nodeCache = new NodeCache();

	@Override
	public IJsonNode evaluate(final IJsonNode node) {
		// no need to reuse the target of the coercion - a boolean node is never created anew
		if (TypeCoercer.INSTANCE.coerce(this.ifClause.evaluate(node), this.nodeCache, BooleanNode.class) == BooleanNode.TRUE)
			return this.ifExpression.evaluate(node);
		return this.thenExpression.evaluate(node);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.ExpressionParent#iterator()
	 */
	@Override
	public ChildIterator iterator() {
		return new NamedChildIterator("ifClause", "ifExpression", "thenExpression") {
			@Override
			protected void set(int index, EvaluationExpression childExpression) {
				switch (index) {
				case 0:
					TernaryExpression.this.ifClause = childExpression;
					break;
				case 1:
					TernaryExpression.this.ifExpression = childExpression;
					break;
				default:
					TernaryExpression.this.thenExpression = childExpression;
				}
			}

			@Override
			protected EvaluationExpression get(int index) {
				switch (index) {
				case 0:
					return TernaryExpression.this.ifClause;
				case 1:
					return TernaryExpression.this.ifExpression;
				default:
					return TernaryExpression.this.thenExpression;
				}
			}
		};
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.ifClause.appendAsString(appendable);
		appendable.append(" ? ");
		this.ifExpression.appendAsString(appendable);
		appendable.append(" : ");
		this.thenExpression.appendAsString(appendable);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.ifClause.hashCode();
		result = prime * result + this.ifExpression.hashCode();
		result = prime * result + this.thenExpression.hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!super.equals(obj))
			return false;
		final TernaryExpression other = (TernaryExpression) obj;
		return this.ifClause.equals(other.ifClause)
			&& this.ifExpression.equals(other.ifExpression)
			&& this.thenExpression.equals(other.thenExpression);
	}

}
