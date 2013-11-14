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
package eu.stratosphere.sopremo.function;

import com.google.common.base.Function;
import com.google.common.base.Predicates;

import eu.stratosphere.sopremo.aggregation.Aggregation;
import eu.stratosphere.sopremo.aggregation.AggregationFunction;
import eu.stratosphere.sopremo.expressions.AggregationExpression;
import eu.stratosphere.sopremo.expressions.BatchAggregationExpression;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.ExpressionUtil;
import eu.stratosphere.sopremo.expressions.FunctionCall;
import eu.stratosphere.sopremo.packages.DefaultFunctionRegistry;

/**
 * @author arvid
 */
public class FunctionUtil {

	public static EvaluationExpression createFunctionCall(Class<?> methodProvider, String methodName, EvaluationExpression... params) {
		DefaultFunctionRegistry registry = new DefaultFunctionRegistry();
		registry.put(methodProvider);
		return createMethodCall(registry.get(methodName), null, params);
	}

	public static EvaluationExpression createFunctionCall(Callable<?, ?> callable, EvaluationExpression... params) {
		return createMethodCall(callable, null, params);
	}

	public static EvaluationExpression createFunctionCall(Aggregation aggregation, EvaluationExpression... params) {
		return createMethodCall(new AggregationFunction(aggregation), null, params);
	}

	public static EvaluationExpression addToBatch(BatchAggregationExpression bae, Aggregation aggregation,
			EvaluationExpression preprocessing) {
		return bae.add(aggregation, preprocessing);
	}

	public static EvaluationExpression addToBatch(BatchAggregationExpression bae, Aggregation aggregation) {
		return bae.add(aggregation);
	}

	public static EvaluationExpression addToBatch(final BatchAggregationExpression bae, ExpressionFunction aggregation) {
		return addToBatch(bae, aggregation, EvaluationExpression.VALUE);
	}

	public static EvaluationExpression addToBatch(final BatchAggregationExpression bae, ExpressionFunction aggregation,
			final EvaluationExpression preprocessing) {
		return aggregation.inline(preprocessing).replace(Predicates.instanceOf(AggregationExpression.class),
			new Function<EvaluationExpression, EvaluationExpression>() {
				@Override
				public EvaluationExpression apply(EvaluationExpression expression) {
					AggregationExpression ae = (AggregationExpression) expression;
					return bae.add(ae.getAggregation(),
						ExpressionUtil.replaceArrayProjections(ae.getInputExpression()));
				}
			});
	}

	public static EvaluationExpression createMethodCall(Callable<?, ?> callable, EvaluationExpression object,
			EvaluationExpression... params) {
		if (callable instanceof MacroBase)
			return ((MacroBase) callable).call(params);
		if (!(callable instanceof SopremoFunction))
			throw new IllegalArgumentException(String.format("Unknown callable %s", callable));

		if (object != null) {
			EvaluationExpression[] shiftedParams = new EvaluationExpression[params.length + 1];
			System.arraycopy(params, 0, shiftedParams, 1, params.length);
			params = shiftedParams;
			params[0] = object;
		}

		if (callable instanceof ExpressionFunction)
			return ((ExpressionFunction) callable).inline(params);
		return new FunctionCall((SopremoFunction) callable, params);
	}

}
