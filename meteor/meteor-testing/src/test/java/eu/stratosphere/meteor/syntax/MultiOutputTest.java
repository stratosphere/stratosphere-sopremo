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

import java.io.IOException;

import org.junit.Test;

import eu.stratosphere.meteor.MeteorParseTest;
import eu.stratosphere.meteor.QueryParser;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.base.Grouping;
import eu.stratosphere.sopremo.expressions.ArrayCreation;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.JsonStreamExpression;
import eu.stratosphere.sopremo.expressions.NestedOperatorExpression;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.expressions.ObjectCreation;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.CompositeOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.Operator;
import eu.stratosphere.sopremo.operator.OutputCardinality;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.operator.SopremoModule;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.query.IConfObjectRegistry;
import eu.stratosphere.sopremo.type.JsonUtil;

/**
 * @author arv
 */
public class MultiOutputTest extends MeteorParseTest {
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.meteor.MeteorParseTest#initParser(eu.stratosphere.meteor.QueryParser)
	 */
	@Override
	protected void initParser(QueryParser queryParser) {
		final IConfObjectRegistry<Operator<?>> operatorRegistry = queryParser.getPackageManager().getOperatorRegistry();
		operatorRegistry.put(MultiOutputOp.class);
		super.initParser(queryParser);
	}

	@Test
	public void testOneOutput() {
		String query = "$input = read from 'file://input.json';\n" +
			"$result1 = multi $input;\n" +
			"write $result1 to 'file://output1.json';";
		final SopremoPlan actualPlan = parseScript(query);

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final MultiOutputOp multi = new MultiOutputOp().withInputs(input);
		final Sink output1 = new Sink("file://output1.json").withInputs(multi.getOutput(0));
		expectedPlan.setSinks(output1);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void testTwoOutput() {
		String query = "$input = read from 'file://input.json';\n" +
			"$result1, $result2 = multi $input;\n" +
			"write $result1 to 'file://output1.json';\n" +
			"write $result2 to 'file://output2.json';";
		final SopremoPlan actualPlan = parseScript(query);

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final MultiOutputOp multi = new MultiOutputOp().withInputs(input);
		final Sink output1 = new Sink("file://output1.json").withInputs(multi.getOutput(0));
		final Sink output2 = new Sink("file://output2.json").withInputs(multi.getOutput(1));
		expectedPlan.setSinks(output1, output2);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void testThreeOutput() {
		String query = "$input = read from 'file://input.json';\n" +
			"$result1, $result2, $result3 = multi $input;\n" +
			"write $result1 to 'file://output1.json';\n" +
			"write $result2 to 'file://output2.json';\n" +
			"write $result3 to 'file://output3.json';";
		final SopremoPlan actualPlan = parseScript(query);

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final MultiOutputOp multi = new MultiOutputOp().withInputs(input);
		final Sink output1 = new Sink("file://output1.json").withInputs(multi.getOutput(0));
		final Sink output2 = new Sink("file://output2.json").withInputs(multi.getOutput(1));
		final Sink output3 = new Sink("file://output3.json").withInputs(multi.getOutput(2));
		expectedPlan.setSinks(output1, output2, output3);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void testTwoOutputProjection() {
		String query = "$input = read from 'file://input.json';\n" +
			"$result1, $result2 = multi $input with [\n" +
			"  group $input by $input.key into {" +
			"    name: $input.name" +
			"  }," +
			"  group $input by $input.key2 into {" +
			"    name2: $input.name2" +
			"  }" +
			"];\n" +
			"write $result1 to 'file://output1.json';\n" +
			"write $result2 to 'file://output2.json';";
		final SopremoPlan actualPlan = parseScript(query);

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input = new Source("file://input.json");
		final MultiOutputOp multi = new MultiOutputOp().withInputs(input);
		Grouping grouping1 = new Grouping().withGroupingKey(JsonUtil.createPath("0", "key")).withResultProjection(
			new ObjectCreation().addMapping("name", JsonUtil.createPath("0", "name")));
		Grouping grouping2 = new Grouping().withGroupingKey(JsonUtil.createPath("0", "key2")).withResultProjection(
			new ObjectCreation().addMapping("name2", JsonUtil.createPath("0", "name2")));
		multi.setProjection(new ArrayCreation(new NestedOperatorExpression(grouping1),
			new NestedOperatorExpression(grouping2)));

		final Sink output1 = new Sink("file://output1.json").withInputs(multi.getOutput(0));
		final Sink output2 = new Sink("file://output2.json").withInputs(multi.getOutput(1));
		expectedPlan.setSinks(output1, output2);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void testTwoInputAndOutputProjection() {
		String query = "$input1 = read from 'file://input1.json';\n" +
			"$input2 = read from 'file://input2.json';\n" +
			"$result1, $result2 = multi $input1, $input2 with [\n" +
			"  group $input1 by $input1.key into {" +
			"    name: $input1.name" +
			"  }," +
			"  group $input2 by $input2.key2 into {" +
			"    name2: $input2.name2" +
			"  }" +
			"];\n" +
			"write $result1 to 'file://output1.json';\n" +
			"write $result2 to 'file://output2.json';";
		final SopremoPlan actualPlan = parseScript(query);

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input1 = new Source("file://input1.json");
		final Source input2 = new Source("file://input2.json");
		final MultiOutputOp multi = new MultiOutputOp().withInputs(input1, input2);
		Grouping grouping1 = new Grouping().withGroupingKey(JsonUtil.createPath("0", "key")).withResultProjection(
			new ObjectCreation().addMapping("name", JsonUtil.createPath("0", "name")));
		Grouping grouping2 = new Grouping().withGroupingKey(JsonUtil.createPath("1", "key2")).withResultProjection(
			new ObjectCreation().addMapping("name2", JsonUtil.createPath("1", "name2")));
		multi.setProjection(new ArrayCreation(new NestedOperatorExpression(grouping1),
			new NestedOperatorExpression(grouping2)));

		final Sink output1 = new Sink("file://output1.json").withInputs(multi.getOutput(0));
		final Sink output2 = new Sink("file://output2.json").withInputs(multi.getOutput(1));
		expectedPlan.setSinks(output1, output2);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@Test
	public void testCrossReferenceProjection() {
		String query = "$input1 = read from 'file://input1.json';\n" +
			"$input2 = read from 'file://input2.json';\n" +
			"$result1, $result2 = multi $input1, $input2 with [\n" +
			"  group $input1 by $input1.key into {" +
			"    name: $result2.name" +
			"  }," +
			"  group $input2 by $input2.key2 into {" +
			"    name2: $result1.name2" +
			"  }" +
			"];\n" +
			"write $result1 to 'file://output1.json';\n" +
			"write $result2 to 'file://output2.json';";
		final SopremoPlan actualPlan = parseScript(query);

		final SopremoPlan expectedPlan = new SopremoPlan();
		final Source input1 = new Source("file://input1.json");
		final Source input2 = new Source("file://input2.json");
		final MultiOutputOp multi = new MultiOutputOp().withInputs(input1, input2);
		Grouping grouping1 = new Grouping().
			withGroupingKey(JsonUtil.createPath("0", "key")).
			withResultProjection(new ObjectCreation().addMapping("name",
				new ObjectAccess("name").withInputExpression(new JsonStreamExpression(multi.getOutput(1)))));
		Grouping grouping2 = new Grouping().
			withGroupingKey(JsonUtil.createPath("1", "key2")).
			withResultProjection(new ObjectCreation().addMapping("name2",
				new ObjectAccess("name2").withInputExpression(new JsonStreamExpression(multi.getOutput(0)))));
		multi.setProjection(new ArrayCreation(new NestedOperatorExpression(grouping1),
			new NestedOperatorExpression(grouping2)));

		final Sink output1 = new Sink("file://output1.json").withInputs(multi.getOutput(0));
		final Sink output2 = new Sink("file://output2.json").withInputs(multi.getOutput(1));
		expectedPlan.setSinks(output1, output2);

		assertPlanEquals(expectedPlan, actualPlan);
	}

	@InputCardinality(min = 1, max = 3)
	@OutputCardinality(min = 1, max = 3)
	@Name(noun = "multi")
	public static class MultiOutputOp extends CompositeOperator<MultiOutputOp> {
		private EvaluationExpression projection = EvaluationExpression.VALUE;

		/**
		 * Sets the projection to the specified value.
		 * 
		 * @param projection
		 *        the projection to set
		 */
		@Property
		@Name(adjective = "with")
		public void setProjection(EvaluationExpression assignment) {
			if (assignment == null)
				throw new NullPointerException("projection must not be null");

			this.projection = assignment;
		}

		/**
		 * Returns the projection.
		 * 
		 * @return the projection
		 */
		public EvaluationExpression getProjection() {
			return this.projection;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + this.projection.hashCode();
			return result;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			MultiOutputOp other = (MultiOutputOp) obj;
			return this.projection.equals(other.projection);
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.operator.Operator#appendAsString(java.lang.Appendable)
		 */
		@Override
		public void appendAsString(Appendable appendable) throws IOException {
			super.appendAsString(appendable);
			appendable.append(" with ");
			this.projection.appendAsString(appendable);
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.operator.CompositeOperator#addImplementation(eu.stratosphere.sopremo.operator.
		 * SopremoModule, eu.stratosphere.sopremo.EvaluationContext)
		 */
		@Override
		public void addImplementation(SopremoModule module, EvaluationContext context) {
		}

	}
}
