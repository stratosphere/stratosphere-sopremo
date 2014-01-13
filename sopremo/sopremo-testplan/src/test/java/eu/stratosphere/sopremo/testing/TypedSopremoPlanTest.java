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

import org.junit.Test;

import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.pact.GenericSopremoMap;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.JsonUtil;
import eu.stratosphere.sopremo.type.ObjectNode;
import eu.stratosphere.sopremo.type.typed.ITypedObjectNode;
import eu.stratosphere.sopremo.type.typed.TypedObjectNodeFactory;

/**
 */
public class TypedSopremoPlanTest {
	@Test
	public void testAnnotation() {
		final SopremoTestPlan plan = new SopremoTestPlan(new TestAnnotationOperator());

		plan.getInput(0).
			addObject("text", "lorum").
			addObject("text", "ipsum", "annotations", JsonUtil.asArray(this.getExpectedAnnotation(0)));
		plan.getExpectedOutput(0).
			addObject("text", "lorum", "annotations", JsonUtil.asArray(this.getExpectedAnnotation(0))).
			addObject("text", "ipsum", "annotations",
				JsonUtil.asArray(this.getExpectedAnnotation(0), this.getExpectedAnnotation(1)));

		plan.run();
	}

	protected ObjectNode getExpectedAnnotation(final int num) {
		return JsonUtil.createObjectNode("start", num * 10, "end", num * 10 + 5, "tag",
			TestAnnotationTag.values()[num % TestAnnotationTag.values().length].toString());
	}

	public static interface TestAnnotatedText extends ITypedObjectNode {
		public IArrayNode<TestAnnotation> getAnnotations();

		public String getText();

		public void setAnnotations(IArrayNode<TestAnnotation> annotations);

		public void setText(String text);
	}

	public static interface TestAnnotation extends ITypedObjectNode {
		public Integer getEnd();

		public Integer getStart();

		public TestAnnotationTag getTag();

		public void setEnd(Integer end);

		public void setStart(Integer start);

		public void setTag(TestAnnotationTag end);
	}

	@InputCardinality(1)
	public static class TestAnnotationOperator extends ElementaryOperator<TestAnnotationOperator> {
		public static class Implementation extends GenericSopremoMap<TestAnnotatedText, TestAnnotatedText> {
			private final TestAnnotation annotation = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
				TestAnnotation.class);

			/*
			 * (non-Javadoc)
			 * @see eu.stratosphere.sopremo.pact.GenericSopremoMap#map(eu.stratosphere.sopremo.type.IJsonNode,
			 * eu.stratosphere.sopremo.pact.JsonCollector)
			 */
			@Override
			protected void map(final TestAnnotatedText value, final JsonCollector<TestAnnotatedText> out) {
				IArrayNode<TestAnnotation> annotations = value.getAnnotations();
				if (annotations == null)
					value.setAnnotations(annotations = new ArrayNode<TestAnnotation>());
				final int num = annotations.size();

				this.annotation.setStart(num * 10);
				this.annotation.setEnd(num * 10 + 5);
				this.annotation.setTag(TestAnnotationTag.values()[num % TestAnnotationTag.values().length]);
				annotations.add(this.annotation);
				out.collect(value);
			}
		}
	}

	public enum TestAnnotationTag {
		PERSON, PROTEIN;
	}
}
