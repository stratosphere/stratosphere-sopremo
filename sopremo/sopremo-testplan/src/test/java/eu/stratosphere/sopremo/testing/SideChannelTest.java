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

import java.io.IOException;

import org.junit.Test;

import eu.stratosphere.sopremo.CoreFunctions;
import eu.stratosphere.sopremo.expressions.*;
import eu.stratosphere.sopremo.function.FunctionUtil;
import eu.stratosphere.sopremo.io.CsvFormat;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoMap;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * 
 */
public class SideChannelTest {

	/**
	 * Tests if a {@link SopremoTestPlan} can be executed.
	 * 
	 * @throws IOException
	 */
	@Test
	public void sideChannelSupported() throws IOException {
		final Source source = new Source(new CsvFormat(), SopremoTestUtil.getResourcePath("SideChannelTest/dict.csv"));

		final TestProjection projection = new TestProjection();
		projection.setResultProjection(new ArrayCreation(
			EvaluationExpression.VALUE,
			FunctionUtil.createFunctionCall(CoreFunctions.CONTAINS,
				new ArrayProjection(new ObjectAccess("element")).withInputExpression(new JsonStreamExpression(source)),
				EvaluationExpression.VALUE)));

		final SopremoTestPlan testPlan = new SopremoTestPlan(projection);
		testPlan.getInput(0).
			addValue("a").
			addValue("c").
			addValue("d").
			addValue("e").
			addValue("b");
		testPlan.getExpectedOutput(0).
			addArray("a", true).
			addArray("c", true).
			addArray("d", false).
			addArray("e", false).
			addArray("b", true);
		testPlan.run();
	}
	
	/**
	 * Tests if a {@link SopremoTestPlan} can be executed.
	 * 
	 * @throws IOException
	 */
	@Test(expected = AssertionError.class)
	public void shouldProperlyFail() throws IOException {
		final Source source = new Source(SopremoTestUtil.getResourcePath("SideChannelTest/dict.csv"));

		final TestProjection projection = new TestProjection();
		projection.setResultProjection(new ArrayCreation(
			EvaluationExpression.VALUE,
			FunctionUtil.createFunctionCall(CoreFunctions.CONTAINS,
				new ArrayProjection(new ObjectAccess("element")).withInputExpression(new JsonStreamExpression(source)),
				EvaluationExpression.VALUE)));

		final SopremoTestPlan testPlan = new SopremoTestPlan(projection);
		testPlan.getInput(0).
			addValue("a").
			addValue("c").
			addValue("d").
			addValue("e").
			addValue("b");
		testPlan.getExpectedOutput(0).
			addArray("a", true).
			addArray("c", true).
			addArray("d", false).
			addArray("e", false).
			addArray("b", true);
		testPlan.run();
	}

	@InputCardinality(1)
	public static class TestProjection extends ElementaryOperator<TestProjection> {
		public static class ProjectionFunction extends SopremoMap {

			@Override
			protected void map(final IJsonNode value, final JsonCollector<IJsonNode> out) {
				out.collect(value);
			}
		}
	}

}
