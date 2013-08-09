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
package eu.stratosphere.sopremo.testing;

import static eu.stratosphere.sopremo.testing.FunctionTest.assertReturn;

import org.junit.Test;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomains;
import com.google.common.collect.Ranges;

import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.MathFunctions;
import eu.stratosphere.sopremo.SecondOrderFunctions;
import eu.stratosphere.sopremo.expressions.ArithmeticExpression;
import eu.stratosphere.sopremo.expressions.ArithmeticExpression.ArithmeticOperator;
import eu.stratosphere.sopremo.expressions.ComparativeExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression.BinaryOperator;
import eu.stratosphere.sopremo.expressions.ConstantExpression;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.function.ExpressionFunction;
import eu.stratosphere.sopremo.function.FunctionNode;
import eu.stratosphere.sopremo.function.SopremoFunction;
import eu.stratosphere.sopremo.type.JsonUtil;

/**
 * @author Arvid Heise
 */
public class SecondOrderFunctionsTest {
	@Test
	public void shouldMapElements() {
		final FunctionNode pointer = new FunctionNode();
		EvaluationContext context = new EvaluationContext();
		context.getFunctionRegistry().put(MathFunctions.class);
		pointer.setFunction((SopremoFunction) context.getFunctionRegistry().get("sqr"));

		assertReturn(JsonUtil.createArrayNode(1, 4, 9), new SecondOrderFunctions.MAP(), new int[] { 1, 2, 3 }, pointer);
	}

	@Test
	public void shouldFilterElements() {
		final FunctionNode pointer = new FunctionNode();
		// EvaluationContext context = new EvaluationContext();
		// context.getFunctionRegistry().put(MathFunctions.class);
		pointer.setFunction(new ExpressionFunction(1,
			new ComparativeExpression(new InputSelection(0), BinaryOperator.GREATER, new ConstantExpression(5))));

		final ContiguousSet<Integer> input = Ranges.closed(0, 10).asSet(DiscreteDomains.integers());
		final ContiguousSet<Integer> expected = Ranges.closed(6, 10).asSet(DiscreteDomains.integers());
		assertReturn(expected, new SecondOrderFunctions.FILTER(), input, pointer);
	}

	@Test
	public void shouldFindElements() {
		final FunctionNode pointer = new FunctionNode();
		// EvaluationContext context = new EvaluationContext();
		// context.getFunctionRegistry().put(MathFunctions.class);
		pointer.setFunction(new ExpressionFunction(1,
			new ComparativeExpression(new InputSelection(0), BinaryOperator.GREATER, new ConstantExpression(5))));

		final ContiguousSet<Integer> input = Ranges.closed(0, 10).asSet(DiscreteDomains.integers());
		assertReturn(6, new SecondOrderFunctions.FIND(), input, pointer);
	}

	@Test
	public void shouldFoldElements() {
		final FunctionNode pointer = new FunctionNode();
		// EvaluationContext context = new EvaluationContext();
		// context.getFunctionRegistry().put(MathFunctions.class);
		pointer.setFunction(new ExpressionFunction(2,
			new ArithmeticExpression(new InputSelection(0), ArithmeticOperator.ADDITION, new InputSelection(1))));

		final ContiguousSet<Integer> input = Ranges.closed(0, 10).asSet(DiscreteDomains.integers());
		assertReturn(55, new SecondOrderFunctions.FOLD(), input, 0, pointer);
	}
}
