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

import eu.stratosphere.sopremo.expressions.tree.ChildIterator;
import eu.stratosphere.sopremo.expressions.tree.NamedChildIterator;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * This traceExpression logs the evaluation of an {@link EvaluationExpression} with the help of {@link SopremoUtil.LOG}.
 */
public class TraceExpression extends EvaluationExpression {
	private EvaluationExpression traceExpression;

	/**
	 * Initializes a TraceExpression with the given {@link EvaluationExpression}.
	 * 
	 * @param traceExpression
	 *        the traceExpression where the evauation should be logged
	 */
	public TraceExpression(final EvaluationExpression expression) {
		this.traceExpression = expression;
	}

	/**
	 * Initializes TraceExpression.
	 */
	public TraceExpression() {
		this(VALUE);
	}

	@Override
	public IJsonNode evaluate(final IJsonNode node) {
		SopremoUtil.LOG.trace(this.traceExpression.evaluate(node));
		return node;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.ExpressionParent#iterator()
	 */
	@Override
	public ChildIterator iterator() {
		return new NamedChildIterator("traceExpression") {

			@Override
			protected void set(int index, EvaluationExpression e) {
				TraceExpression.this.traceExpression = e;
			}

			@Override
			protected EvaluationExpression get(int index) {
				return TraceExpression.this.traceExpression;
			}
		};
	}
}
