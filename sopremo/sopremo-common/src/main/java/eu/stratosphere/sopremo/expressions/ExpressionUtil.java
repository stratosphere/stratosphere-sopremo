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

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import eu.stratosphere.sopremo.aggregation.Aggregation;
import eu.stratosphere.sopremo.aggregation.AggregationFunction;
import eu.stratosphere.sopremo.aggregation.ArrayAccessAsAggregation;
import eu.stratosphere.util.IsInstancePredicate;

/**
 * @author Arvid Heise
 */
public class ExpressionUtil {
	/**
	 * Wraps the given {@link EvaluationExpression}s in a single {@link PathExpression}
	 * 
	 * @param expressions
	 *        a List of the expressions that should be wrapped
	 * @return the {@link PathExpression}
	 */
	public static EvaluationExpression makePath(final List<PathSegmentExpression> expressions) {
		if (expressions.size() == 0)
			return EvaluationExpression.VALUE;

		PathSegmentExpression result = expressions.get(expressions.size() - 1), last = result;
		for (int index = expressions.size() - 2; index >= 0; index--) {
			PathSegmentExpression expression = expressions.get(index);
			last.getLast().setInputExpression(expression);
			last = expression;
		}
		return result;
	}

	/**
	 * Wraps the given {@link EvaluationExpression}s in a single {@link PathExpression}
	 * 
	 * @param expressions
	 *        an Array of the expressions that should be wrapped
	 * @return the {@link PathExpression}
	 */
	public static EvaluationExpression makePath(final PathSegmentExpression... expressions) {
		return makePath(Arrays.asList(expressions));
	}

	/**
	 * Replaces fragments in the form of path expression (InputSelection, ArrayAccess).
	 */
	public static EvaluationExpression replaceIndexAccessWithAggregation(EvaluationExpression baseExpression) {
		return baseExpression.replace(new IsInstancePredicate(ArrayAccess.class), new TransformFunction() {
			@Override
			public EvaluationExpression apply(EvaluationExpression argument) {
				ArrayAccess arrayAccess = (ArrayAccess) argument;
				if (arrayAccess.getStartIndex() < 0 || arrayAccess.getEndIndex() < 0)
					throw new IllegalArgumentException("Negative indexes cannot replaced currently");
				if (arrayAccess.getStartIndex() > arrayAccess.getEndIndex())
					throw new IllegalArgumentException("Array inversion is not directly supported");

				final FunctionCall aggregation = new FunctionCall("array access",
					new AggregationFunction(new ArrayAccessAsAggregation(arrayAccess.getStartIndex(),
						arrayAccess.getEndIndex(), arrayAccess.isSelectingRange())),
					arrayAccess.getInputExpression().clone());
				return aggregation;
			}
		});
	}

	public static EvaluationExpression replaceAggregationWithBatchAggregation(EvaluationExpression baseExpression) {
		final Map<FunctionCall, EvaluationExpression> aggregatingFunctionCalls =
			new IdentityHashMap<FunctionCall, EvaluationExpression>();
		findAggregatingFunctionCalls(baseExpression, aggregatingFunctionCalls, null);

		if (aggregatingFunctionCalls.isEmpty())
			return baseExpression;

		EvaluationExpression result = baseExpression;
		final Int2ObjectMap<BatchAggregationExpression> aggregationPerInput =
			new Int2ObjectOpenHashMap<BatchAggregationExpression>();
		for (FunctionCall functionCall : aggregatingFunctionCalls.keySet()) {
			AggregationFunction aggregationFunction = (AggregationFunction) functionCall.getFunction();
			final List<InputSelection> inputs = functionCall.findAll(InputSelection.class);
			if (inputs.isEmpty())
				// if no input selection, it is probably some constant calculation, ignore function call
				continue;

			final Aggregation aggregation = aggregationFunction.getAggregation();
			int input = inputs.get(0).getIndex();
			for (int index = 1; index < inputs.size(); index++)
				if (inputs.get(index).getIndex() != input)
					throw new IllegalArgumentException("Cannot batch process aggregations with multiple inputs");

			final List<EvaluationExpression> parameters = functionCall.getParameters();
			if (parameters.size() > 1)
				throw new IllegalStateException("Cannot batch process aggregations with multiple parameters");

			// all expressions within this function call are from the same input
			BatchAggregationExpression batch = aggregationPerInput.get(input);
			if (batch == null) {
				aggregationPerInput.put(input, batch = new BatchAggregationExpression());
				batch.setInputExpression(new InputSelection(input));
			}

			final EvaluationExpression parent = aggregatingFunctionCalls.get(functionCall);
			final EvaluationExpression partial = batch.add(aggregation, adjustAggregationParameters(parameters.get(0)));
			if (parent == null)
				result = partial;
			else
				parent.replace(functionCall, partial);
		}
		return result;
	}

	private static void findAggregatingFunctionCalls(EvaluationExpression expression,
			Map<FunctionCall, EvaluationExpression> aggregatingFunctionCalls, EvaluationExpression parent) {
		if (expression instanceof FunctionCall &&
			((FunctionCall) expression).getFunction() instanceof AggregationFunction)
			aggregatingFunctionCalls.put((FunctionCall) expression, parent);

		for (EvaluationExpression child : expression)
			findAggregatingFunctionCalls(child, aggregatingFunctionCalls, expression);
	}

	private static EvaluationExpression adjustAggregationParameters(final EvaluationExpression evaluationExpression) {
		return evaluationExpression.clone().remove(InputSelection.class).replace(
			new IsInstancePredicate(ArrayProjection.class), new TransformFunction() {
				@Override
				public EvaluationExpression apply(EvaluationExpression argument) {
					final ArrayProjection arrayProjection = (ArrayProjection) argument;
					final EvaluationExpression projection = arrayProjection.getProjection();
					return projection;
				}
			}).simplify();
	}

}
