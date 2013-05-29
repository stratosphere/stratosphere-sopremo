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
package eu.stratosphere.sopremo;

import eu.stratosphere.sopremo.cache.NodeCache;
import eu.stratosphere.sopremo.expressions.ArithmeticExpression;
import eu.stratosphere.sopremo.expressions.ConstantExpression;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.function.SopremoFunction1;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.packages.BuiltinProvider;
import eu.stratosphere.sopremo.type.DoubleNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.INumericNode;

/**
 * @author Arvid Heise
 */
public class MathFunctions implements BuiltinProvider {
	public static final EvaluationExpression PI = new ConstantExpression(Math.PI), E = new ConstantExpression(Math.E);

	@Name(verb = "sqrt")
	public static final class SQRT extends SopremoFunction1<INumericNode> {
		SQRT() {
			super("sqrt");
		}

		private final DoubleNode result = new DoubleNode();

		@Override
		protected IJsonNode call(INumericNode input) {
			this.result.setValue(Math.sqrt(input.getDoubleValue()));
			return this.result;
		}
	};

	@Name(verb = "sqr")
	public static final class SQR extends SopremoFunction1<INumericNode> {
		SQR() {
			super("sqr");
		}

		private final transient NodeCache cache = new NodeCache();

		@Override
		protected IJsonNode call(INumericNode input) {
			return ArithmeticExpression.ArithmeticOperator.MULTIPLICATION.evaluate(input, input, this.cache);
		}
	};
}
