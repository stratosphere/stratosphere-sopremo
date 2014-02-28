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

import java.util.ArrayList;
import java.util.List;

import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.type.BooleanNode;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Represents all expressions with a boolean semantic.
 */
public abstract class BooleanExpression extends EvaluationExpression implements ISopremoType {
	public static final BooleanExpression TRUE = BooleanExpression.ensureBooleanExpression(new ConstantExpression(true)),
			FALSE = BooleanExpression.ensureBooleanExpression(new ConstantExpression(false));

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.EvaluationExpression#evaluate(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public abstract BooleanNode evaluate(IJsonNode node);

	/**
	 * Wraps the given {@link EvaluationExpression} as a {@link BooleanExpression}.
	 * 
	 * @param expression
	 *        the expression that should be wrapped
	 * @return the wrapped expression
	 */
	public static BooleanExpression ensureBooleanExpression(final EvaluationExpression expression) {
		if (expression instanceof BooleanExpression)
			return (BooleanExpression) expression;
		return new UnaryExpression(expression);
	}

	/**
	 * Wraps the given list of {@link EvaluationExpression} as a list of {@link BooleanExpression}.
	 * 
	 * @param expressions
	 *        the expressions that should be wrapped
	 * @return the wrapped expression
	 */
	public static List<BooleanExpression> ensureBooleanExpressions(
			final List<? extends EvaluationExpression> expressions) {
		final ArrayList<BooleanExpression> booleans = new ArrayList<BooleanExpression>(expressions.size());
		for (final EvaluationExpression evaluationExpression : expressions)
			booleans.add(ensureBooleanExpression(evaluationExpression));
		return booleans;
	}
}