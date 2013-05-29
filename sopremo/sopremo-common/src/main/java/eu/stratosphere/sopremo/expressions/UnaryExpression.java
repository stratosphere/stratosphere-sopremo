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
 * Represents a unary boolean expression.
 */
@OptimizerHints(scope = Scope.ANY)
public class UnaryExpression extends BooleanExpression {
	private EvaluationExpression expr;

	private final boolean negate;

	/**
	 * Initializes an UnaryExpression with the given {@link EvaluationExpression}.
	 * 
	 * @param booleanExpr
	 *        the expression which evaluates to the boolean value that should be represented by this
	 *        {@link UnaryExpression}
	 */
	public UnaryExpression(final EvaluationExpression booleanExpr) {
		this(booleanExpr, false);
	}

	/**
	 * Initializes an UnaryExpression with the given {@link EvaluationExpression} and the given negate-flag.
	 * 
	 * @param booleanExpr
	 *        the expression which evaluates to the boolean value that should be represented by this
	 *        {@link UnaryExpression}
	 * @param negate
	 *        indicates either the result of the evaluation should be negated or not
	 */
	public UnaryExpression(final EvaluationExpression expr, final boolean negate) {
		this.expr = expr;
		this.negate = negate;
	}

	/**
	 * Initializes UnaryExpression.
	 */
	UnaryExpression() {
		this.expr = null;
		this.negate = false;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!super.equals(obj))
			return false;
		final UnaryExpression other = (UnaryExpression) obj;
		return this.expr.equals(other.expr) && this.negate == other.negate;
	}

	private transient final NodeCache nodeCache = new NodeCache();

	@Override
	public BooleanNode evaluate(final IJsonNode node) {
		// no need to reuse target of coercion - no new boolean node is created anew
		final BooleanNode result =
			TypeCoercer.INSTANCE.coerce(this.expr.evaluate(node), this.nodeCache, BooleanNode.class);

		// we can ignore 'target' because no new Object is created
		if (this.negate)
			return result == BooleanNode.TRUE ? BooleanNode.FALSE : BooleanNode.TRUE;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.ExpressionParent#iterator()
	 */
	@Override
	public ChildIterator iterator() {
		return new NamedChildIterator("expr") {

			@Override
			protected void set(int index, EvaluationExpression e) {
				UnaryExpression.this.expr = e;
			}

			@Override
			protected EvaluationExpression get(int index) {
				return UnaryExpression.this.expr;
			}
		};
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.expr.hashCode();
		result = prime * result + (this.negate ? 1231 : 1237);
		return result;
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		if (this.negate)
			appendable.append("!");
		this.expr.appendAsString(appendable);
	}

	public static BooleanExpression not(EvaluationExpression expression) {
		return new UnaryExpression(expression, true);
	}
}