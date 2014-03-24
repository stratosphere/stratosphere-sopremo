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
package eu.stratosphere.sopremo.base;

import static eu.stratosphere.sopremo.type.JsonUtil.createPath;

import org.junit.Test;

import eu.stratosphere.sopremo.CoreFunctions;
import eu.stratosphere.sopremo.expressions.AndExpression;
import eu.stratosphere.sopremo.expressions.BatchAggregationExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression.BinaryOperator;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.expressions.ObjectCreation;
import eu.stratosphere.sopremo.testing.SopremoTestPlan;

/**
 */
public class SPJGTest {
	@Test
	public void shouldPerformJoinAndGroup() {

		final AndExpression condition = new AndExpression(new ComparativeExpression(
			createPath("0", "id"), BinaryOperator.EQUAL, createPath("1", "userid")));
		final Join join = new Join().withJoinCondition(condition).withResultProjection(ObjectCreation.CONCATENATION);

		final BatchAggregationExpression bae = new BatchAggregationExpression();
		final Grouping group = new Grouping().
			withGroupingKey(new ObjectAccess("name")).
			withResultProjection(new ObjectCreation().
				addMapping("name", bae.add(CoreFunctions.FIRST, new ObjectAccess("name"))).
				addMapping("urls", bae.add(CoreFunctions.ALL, new ObjectAccess("url")))).
			withInputs(join);
		final Grouping group2 = new Grouping().
			withGroupingKey(new ObjectAccess("password")).
			withResultProjection(new ObjectCreation().
				addMapping("password", bae.add(CoreFunctions.FIRST, new ObjectAccess("password"))).
				addMapping("urls", bae.add(CoreFunctions.ALL, new ObjectAccess("url")))).
			withInputs(join);
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(new UnionAll().withInputs(group, group2));
		sopremoPlan.getInputForStream(join.getInput(0)).
			addObject("name", "Jon Doe", "password", "asdf1234", "id", 1).
			addObject("name", "Jane Doe", "password", "qwertyui", "id", 2).
			addObject("name", "Max Mustermann", "password", "q1w2e3r4", "id", 3);
		sopremoPlan.getInputForStream(join.getInput(1)).
			addObject("userid", 1, "url", "code.google.com/p/jaql/").
			addObject("userid", 2, "url",
				"www.cnn.com").
			addObject("userid", 1, "url", "java.sun.com/javase/6/docs/api/");
		sopremoPlan.
			getExpectedOutput(0).
			addObject("name", "Jon Doe", "urls",
				new String[] { "code.google.com/p/jaql/", "java.sun.com/javase/6/docs/api/" }).
			addObject("name", "Jane Doe", "urls", new String[] { "www.cnn.com" }).
			addObject("password", "asdf1234", "urls",
				new String[] { "code.google.com/p/jaql/", "java.sun.com/javase/6/docs/api/" }).
			addObject("password", "qwertyui", "urls", new String[] { "www.cnn.com" });

		sopremoPlan.run();
	}
}
