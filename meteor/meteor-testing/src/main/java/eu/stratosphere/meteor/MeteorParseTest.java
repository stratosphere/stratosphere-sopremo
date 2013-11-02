/***********************************************************************************************************************
 *
 * Copyright (C) 2010-2013 by the Stratosphere project (http://stratosphere.eu)
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
package eu.stratosphere.meteor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Ignore;

import com.google.common.base.Function;
import com.google.common.base.Predicates;

import eu.stratosphere.sopremo.aggregation.Aggregation;
import eu.stratosphere.sopremo.aggregation.AggregationFunction;
import eu.stratosphere.sopremo.expressions.AggregationExpression;
import eu.stratosphere.sopremo.expressions.BatchAggregationExpression;
import eu.stratosphere.sopremo.expressions.ChainedSegmentExpression;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.ExpressionUtil;
import eu.stratosphere.sopremo.expressions.FunctionCall;
import eu.stratosphere.sopremo.function.Callable;
import eu.stratosphere.sopremo.function.ExpressionFunction;
import eu.stratosphere.sopremo.function.MacroBase;
import eu.stratosphere.sopremo.function.SopremoFunction;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.query.QueryParserException;
import eu.stratosphere.sopremo.testing.SopremoTestUtil;

/**
 * @author Arvid Heise
 */
@Ignore
public class MeteorParseTest {
	public SopremoPlan parseScript(final String script) {
		// printBeamerSlide(script);
		SopremoPlan plan = null;
		try {
			final QueryParser queryParser = new QueryParser().withInputDirectory(new File("."));
			initParser(queryParser);
			plan = queryParser.tryParse(script);
		} catch (final QueryParserException e) {
			final AssertionError error = new AssertionError(String.format("could not parse script: %s", e.getMessage()));
			error.initCause(e);
			throw error;
		}

		Assert.assertNotNull("could not parse script", plan);

		// System.out.println(plan);
		return plan;
	}

	public SopremoPlan parseScript(final File script) {
		// printBeamerSlide(script);
		SopremoPlan plan = null;
		try {
			final QueryParser queryParser = new QueryParser().withInputDirectory(script.getParentFile());
			initParser(queryParser);
			plan = queryParser.tryParse(loadScriptFromFile(script));
		} catch (final QueryParserException e) {
			final AssertionError error = new AssertionError(String.format("could not parse script: %s", e.getMessage()));
			error.initCause(e);
			throw error;
		}

		Assert.assertNotNull("could not parse script", plan);

		// System.out.println(plan);
		return plan;
	}

	protected EvaluationExpression createFunctionCall(Callable<?, ?> callable, EvaluationExpression... params) {
		return this.createMethodCall(callable, null, params);
	}

	protected EvaluationExpression createFunctionCall(Aggregation aggregation, EvaluationExpression... params) {
		return this.createMethodCall(new AggregationFunction(aggregation), null, params);
	}

	protected EvaluationExpression addToBatch(BatchAggregationExpression bae, Aggregation aggregation, EvaluationExpression preprocessing) {
		return bae.add(aggregation, preprocessing);
	}

	protected EvaluationExpression addToBatch(BatchAggregationExpression bae, Aggregation aggregation) {
		return bae.add(aggregation);
	}

	protected EvaluationExpression addToBatch(final BatchAggregationExpression bae, ExpressionFunction aggregation) {
		return addToBatch(bae, aggregation, EvaluationExpression.VALUE);
	}

	protected EvaluationExpression addToBatch(final BatchAggregationExpression bae, ExpressionFunction aggregation,
			final EvaluationExpression preprocessing) {
		return aggregation.inline(preprocessing).replace(Predicates.instanceOf(AggregationExpression.class),
			new Function<EvaluationExpression, EvaluationExpression>() {
				@Override
				public EvaluationExpression apply(EvaluationExpression expression) {
					AggregationExpression ae = (AggregationExpression) expression;
					return bae.add(ae.getAggregation(),
						ExpressionUtil.replaceArrayProjections(new ChainedSegmentExpression(ae.getInputExpression(), preprocessing).simplify()));
				}
			});
	}

	protected EvaluationExpression createMethodCall(Callable<?, ?> callable, EvaluationExpression object,
			EvaluationExpression... params) {
		if (callable instanceof MacroBase)
			return ((MacroBase) callable).call(params);
		if (!(callable instanceof SopremoFunction))
			throw new QueryParserException(String.format("Unknown callable %s", callable));

		if (object != null) {
			EvaluationExpression[] shiftedParams = new EvaluationExpression[params.length + 1];
			System.arraycopy(params, 0, shiftedParams, 1, params.length);
			params = shiftedParams;
			params[0] = object;
		}

		if (callable instanceof ExpressionFunction)
			return ((ExpressionFunction) callable).inline(params);
		return new FunctionCall(((SopremoFunction) callable).getName(), (SopremoFunction) callable, params);
	}

	private String loadScriptFromFile(File scriptFile) {
		try {
			final BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
			StringBuilder builder = new StringBuilder();
			int ch;
			while ((ch = reader.read()) != -1)
				builder.append((char) ch);
			reader.close();
			return builder.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@SuppressWarnings("unused")
	protected void initParser(QueryParser queryParser) {

	}

	public static void assertPlanEquals(SopremoPlan expectedPlan, SopremoPlan actualPlan) {
		SopremoTestUtil.assertPlanEquals(expectedPlan, actualPlan);
	}
}
