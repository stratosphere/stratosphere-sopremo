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
package eu.stratosphere.meteor.syntax;

import org.junit.Test;

import eu.stratosphere.meteor.MeteorParseTest;
import eu.stratosphere.sopremo.base.Projection;
import eu.stratosphere.sopremo.expressions.ArithmeticExpression;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.SopremoPlan;

/**
 * 
 */
public class ArithmeticTest extends MeteorParseTest {

	/**
	 * 
	 */
	@Test
	public void testTripleSum() {
		final SopremoPlan actualPlan =
			this.parseScript("$numbers = read from 'file://numbers.json';\n" +
				"$numbers = transform $n in $numbers into \n" +
				"  ($n + $n + $n) \n" +
				";\n" +
				"write $numbers to 'file://result.json';\n");

		final Source input = new Source("file://numbers.json");
		final Projection filter = new Projection().
			withInputs(input).
			withResultProjection(
				new ArithmeticExpression(EvaluationExpression.VALUE,
					ArithmeticExpression.ArithmeticOperator.ADDITION,
					new ArithmeticExpression(EvaluationExpression.VALUE,
						ArithmeticExpression.ArithmeticOperator.ADDITION,
						EvaluationExpression.VALUE)));

		final Sink sink = new Sink("file://result.json").withInputs(filter);
		final SopremoPlan expectedPlan = new SopremoPlan();
		expectedPlan.setSinks(sink);

		assertPlanEquals(expectedPlan, actualPlan);
	}
	
}