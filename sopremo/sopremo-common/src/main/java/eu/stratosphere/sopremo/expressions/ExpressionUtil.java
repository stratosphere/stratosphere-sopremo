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

import java.util.*;

import com.google.common.base.Predicates;

import eu.stratosphere.sopremo.CoreFunctions;
import eu.stratosphere.sopremo.aggregation.Aggregation;
import eu.stratosphere.sopremo.aggregation.AggregationFunction;
import eu.stratosphere.sopremo.aggregation.ArrayAccessAsAggregation;
import eu.stratosphere.sopremo.cache.NodeCache;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.sopremo.type.TypeCoercer;

/**
 */
public class ExpressionUtil {
	private final static ThreadLocal<NodeCache> NodeCache = new ThreadLocal<NodeCache>() {
		@Override
		protected NodeCache initialValue() {
			return new NodeCache();
		};
	};

	public static <T extends IJsonNode> T getConstant(final EvaluationExpression expression, final Class<T> type) {
		final IJsonNode constant = expression.evaluate(MissingNode.getInstance());
		return TypeCoercer.INSTANCE.coerce(constant, NodeCache.get(), type);
	}

	/**
	 * Wraps the given {@link EvaluationExpression}s in a single {@link PathSegmentExpression}
	 * 
	 * @param expressions
	 *        a List of the expressions that should be wrapped
	 * @return the {@link PathSegmentExpression}
	 */
	public static PathSegmentExpression makePath(final List<PathSegmentExpression> expressions) {
		if (expressions.size() == 0)
			return EvaluationExpression.VALUE;

		final PathSegmentExpression result = expressions.get(expressions.size() - 1);
		PathSegmentExpression last = result;
		for (int index = expressions.size() - 2; index >= 0; index--) {
			final PathSegmentExpression expression = expressions.get(index);
			last.getLast().setInputExpression(expression);
			last = expression;
		}
		return result;
	}
	
	public static void sortExpressionsForInputs(List<? extends EvaluationExpression> expressions) {
		Collections.sort(expressions, new Comparator<EvaluationExpression>() {
			/* (non-Javadoc)
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(EvaluationExpression o1, EvaluationExpression o2) {
				return o1.findFirst(InputSelection.class).getIndex() - o2.findFirst(InputSelection.class).getIndex();
			}
		});
	}

	/**
	 * Wraps the given {@link EvaluationExpression}s in a single {@link PathSegmentExpression}
	 * 
	 * @param expressions
	 *        an Array of the expressions that should be wrapped
	 * @return the {@link PathSegmentExpression}
	 */
	public static PathSegmentExpression makePath(final PathSegmentExpression... expressions) {
		return makePath(Arrays.asList(expressions));
	}

	public static EvaluationExpression replaceAggregationWithBatchAggregation(final EvaluationExpression baseExpression) {
		final Map<FunctionCall, EvaluationExpression> aggregatingFunctionCalls =
			new IdentityHashMap<FunctionCall, EvaluationExpression>();
		final Map<AggregationExpression, EvaluationExpression> aggregatingExpressions =
			new IdentityHashMap<AggregationExpression, EvaluationExpression>();
		findAggregatingFunctionCalls(baseExpression, aggregatingFunctionCalls, aggregatingExpressions, null);

		if (aggregatingFunctionCalls.isEmpty() && aggregatingExpressions.isEmpty())
			return baseExpression;

		EvaluationExpression result = baseExpression;
		final Int2ObjectMap<BatchAggregationExpression> aggregationPerInput =
			new Int2ObjectOpenHashMap<BatchAggregationExpression>();
		for (final FunctionCall functionCall : aggregatingFunctionCalls.keySet()) {
			final AggregationFunction aggregationFunction = (AggregationFunction) functionCall.getFunction();
			final List<InputSelection> inputs = functionCall.findAll(InputSelection.class);
			if (inputs.isEmpty())
				// if no input selection, it is probably some constant calculation, ignore function call
				continue;

			final Aggregation aggregation = aggregationFunction.getAggregation();
			final int input = inputs.get(0).getIndex();
			for (int index = 1; index < inputs.size(); index++)
				if (inputs.get(index).getIndex() != input)
					throw new IllegalArgumentException("Cannot batch process aggregations with multiple inputs");

			final List<EvaluationExpression> parameters = functionCall.getParameters();
			// all expressions within this function call are from the same input
			BatchAggregationExpression batch = aggregationPerInput.get(input);
			if (batch == null) {
				aggregationPerInput.put(input, batch = new BatchAggregationExpression());
				batch.setInputExpression(new InputSelection(input));
			}

			final EvaluationExpression parent = aggregatingFunctionCalls.get(functionCall);
			final EvaluationExpression partial = batch.add(aggregation, replaceArrayProjections(parameters.get(0)));
			if (parent == null)
				result = partial;
			else
				parent.replace(functionCall, partial);
		}
		for (final AggregationExpression expression : aggregatingExpressions.keySet()) {
			final Aggregation aggregation = expression.getAggregation();

			final List<InputSelection> inputs = expression.getInputExpression().findAll(InputSelection.class);
			if (inputs.isEmpty())
				// if no input selection, it is probably some constant calculation, ignore function call
				continue;
			final int input = inputs.get(0).getIndex();
			for (int index = 1; index < inputs.size(); index++)
				if (inputs.get(index).getIndex() != input)
					throw new IllegalArgumentException("Cannot batch process aggregations with multiple inputs");

			// all expressions within this function call are from the same input
			BatchAggregationExpression batch = aggregationPerInput.get(input);
			if (batch == null) {
				aggregationPerInput.put(input, batch = new BatchAggregationExpression());
				batch.setInputExpression(new InputSelection(input));
			}

			final EvaluationExpression parent = aggregatingExpressions.get(expression);
			final EvaluationExpression partial =
				batch.add(aggregation, replaceArrayProjections(expression.getInputExpression()));
			if (parent == null)
				result = partial;
			else
				parent.replace(expression, partial);
		}
		return result;
	}

	public static EvaluationExpression replaceArrayProjections(final EvaluationExpression evaluationExpression) {
		return evaluationExpression.clone().remove(InputSelection.class).replace(
			Predicates.instanceOf(ArrayProjection.class), new TransformFunction() {
				@Override
				public EvaluationExpression apply(final EvaluationExpression argument) {
					final ArrayProjection arrayProjection = (ArrayProjection) argument;
					final EvaluationExpression projection = arrayProjection.getProjection();
					return new ChainedSegmentExpression(arrayProjection.getInputExpression(), projection);
				}
			}).simplify();
	}

	public static EvaluationExpression replaceInputSelectionsWithArrayAccess(
			final EvaluationExpression evaluationExpression) {
		return evaluationExpression.clone().replace(Predicates.instanceOf(InputSelection.class),
			new TransformFunction() {
				@Override
				public EvaluationExpression apply(final EvaluationExpression argument) {
					return ((InputSelection) argument).asArrayAccess();
				}
			}).simplify();
	}

	/**
	 * Replaces fragments in the form of path expression (InputSelection, ArrayAccess).
	 */
	public static EvaluationExpression replaceIndexAccessWithAggregation(final EvaluationExpression baseExpression) {
		return baseExpression.replace(Predicates.instanceOf(ArrayAccess.class), new TransformFunction() {
			@Override
			public EvaluationExpression apply(final EvaluationExpression argument) {
				final ArrayAccess arrayAccess = (ArrayAccess) argument;
				// only process array access directly on the input stream
				if (!(arrayAccess.getInputExpression() instanceof InputSelection))
					return arrayAccess;
				if (arrayAccess.getStartIndex() < 0 || arrayAccess.getEndIndex() < 0)
					throw new IllegalArgumentException("Negative indexes cannot replaced currently");
				if (arrayAccess.getStartIndex() > arrayAccess.getEndIndex())
					throw new IllegalArgumentException("Array inversion is not directly supported");

				if (arrayAccess.getStartIndex() == 0 && arrayAccess.getEndIndex() == 0)
					return new AggregationExpression(CoreFunctions.FIRST).withInputExpression(arrayAccess.getInputExpression());
				return new AggregationExpression(new ArrayAccessAsAggregation(arrayAccess.getStartIndex(),
					arrayAccess.getEndIndex(), arrayAccess.isSelectingRange())).withInputExpression(arrayAccess.getInputExpression());
				// final FunctionCall aggregation = new FunctionCall("array access",
				// new AggregationexFunction(new ArrayAccessAsAggregation(arrayAccess.getStartIndex(),
				// arrayAccess.getEndIndex(), arrayAccess.isSelectingRange())),
				// arrayAccess.getInputExpression().clone());
				// return aggregation;
			}
		});
	}

	private static void findAggregatingFunctionCalls(final EvaluationExpression expression,
			final Map<FunctionCall, EvaluationExpression> aggregatingFunctionCalls,
			final Map<AggregationExpression, EvaluationExpression> aggregatingExpressions,
			final EvaluationExpression parent) {
		if (expression instanceof FunctionCall &&
			((FunctionCall) expression).getFunction() instanceof AggregationFunction)
			aggregatingFunctionCalls.put((FunctionCall) expression, parent);
		else if (expression instanceof AggregationExpression)
			aggregatingExpressions.put((AggregationExpression) expression, parent);

		for (final EvaluationExpression child : expression)
			findAggregatingFunctionCalls(child, aggregatingFunctionCalls, aggregatingExpressions, expression);
	}
	
	public static void removeInputSelections(List<EvaluationExpression> expressions) {
		for (int index = 0; index < expressions.size(); index++) 
			expressions.set(index, expressions.get(index).remove(InputSelection.class));
	}
}
