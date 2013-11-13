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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

import eu.stratosphere.pact.common.contract.Order;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.OrderingExpression;
import eu.stratosphere.sopremo.operator.Operator;
import eu.stratosphere.sopremo.testing.SopremoOperatorTestBase;
import eu.stratosphere.sopremo.testing.SopremoTestPlan;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * @author arv
 */
public class SortTest extends SopremoOperatorTestBase<Sort> {
	@Override
	protected Sort createDefaultInstance(final int index) {
		return new Sort().withOrderingExpression(new OrderingExpression(Order.ASCENDING, new ArrayAccess(index)));
	}

	@Test
	public void shouldSortOnValue() {
		final Sort sort = new Sort();
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(sort);
		sort.setOrderingExpression(new OrderingExpression(Order.DESCENDING, EvaluationExpression.VALUE));

		sopremoPlan.getInput(0).
			addValue("b").
			addValue("d").
			addValue("c").
			addValue("a");
		sopremoPlan.getExpectedOutput(0).
			addValue("d").
			addValue("c").
			addValue("b").
			addValue("a");

		sopremoPlan.run();

		final List<IJsonNode> expected = Lists.newArrayList(sopremoPlan.getInput(0));
		Collections.sort(expected, Collections.reverseOrder());
		final List<IJsonNode> actual = Lists.newArrayList(sopremoPlan.getActualOutput(0));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void shouldSortOnExpression() {
		final Sort sort = new Sort();
		final SopremoTestPlan sopremoPlan = new SopremoTestPlan(ensureUnordered(sort));
		sort.setOrderingExpression(new OrderingExpression(Order.ASCENDING, new ArrayAccess(1)));

		sopremoPlan.getInput(0).
			addArray("b", "3").
			addArray("d", "2").
			addArray("c", "1").
			addArray("a", "4");
		sopremoPlan.getExpectedOutput(0).
			addArray("c", "1").
			addArray("d", "2").
			addArray("b", "3").
			addArray("a", "4");

		sopremoPlan.run();

		@SuppressWarnings({ "unchecked", "rawtypes" })
		final List<IArrayNode<?>> expected = (List) Lists.newArrayList(sopremoPlan.getInput(0));
		Collections.sort(expected, new Comparator<IArrayNode<?>>() {
			/*
			 * (non-Javadoc)
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(IArrayNode<?> o1, IArrayNode<?> o2) {
				return o1.get(1).compareTo(o2.get(1));
			}
		});
		final List<IJsonNode> actual = Lists.newArrayList(sopremoPlan.getActualOutput(0).unsortedIterator());
		Assert.assertEquals(expected, actual);
	}

	/**
	 * @param sort
	 * @return
	 */
	private Operator<?> ensureUnordered(Sort sort) {
//		final Projection arrayWrap =
//			new Projection().withInputs(sort).withResultProjection(
//				new ArrayCreation(ConstantExpression.MISSING, EvaluationExpression.VALUE));
//		final GlobalEnumeration ge =
//			new GlobalEnumeration().withEnumerationExpression(new ArrayAccess(0)).withInputs(arrayWrap);
//		return new Grouping().withGroupingKey(0, new ArrayAccess(0)).withInputs(ge).withResultProjection(
//			new ArrayAccess(1).withInputExpression(new ArrayAccess(0)));
		return sort;
	}
}