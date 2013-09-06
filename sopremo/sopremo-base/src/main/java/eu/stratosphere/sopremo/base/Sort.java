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

import eu.stratosphere.pact.common.contract.Order;
import eu.stratosphere.sopremo.expressions.ConstantExpression;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.OrderingExpression;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoReduce;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;

/**
 * Sorts the tuples globally.
 */
@InputCardinality(1)
public class Sort extends ElementaryOperator<Sort> {
	/**
	 * Initializes Sort.
	 */
	public Sort() {
		this.setInnerGroupOrder(0,
			Collections.singletonList(new OrderingExpression(Order.ASCENDING, EvaluationExpression.VALUE)));
		this.setKeyExpressions(0, ConstantExpression.NULL);
	}

	/**
	 * Sets the sortingExpression to the specified value.
	 * 
	 * @param sortingExpression
	 *        the sortingExpression to set
	 */
	public void setSortingExpression(OrderingExpression sortingExpression) {
		if (sortingExpression == null)
			throw new NullPointerException("sortingExpression must not be null");

		this.setInnerGroupOrder(0, Collections.singletonList(sortingExpression));
	}

	/**
	 * Returns the sortingExpression.
	 * 
	 * @return the sortingExpression
	 */
	public EvaluationExpression getSortingExpression() {
		return this.getInnerGroupOrder(0).get(0);
	}

	public Sort withSortingExpression(OrderingExpression sortingExpression) {
		setSortingExpression(sortingExpression);
		return this;
	}

	public static class Implementation extends SopremoReduce {

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.pact.GenericSopremoReduce#reduce(eu.stratosphere.sopremo.type.IStreamNode,
		 * eu.stratosphere.sopremo.pact.JsonCollector)
		 */
		@Override
		protected void reduce(IStreamNode<IJsonNode> values, JsonCollector<IJsonNode> out) {
			// reemit all values, they have been sorted by the second order function
			for (IJsonNode value : values)
				out.collect(value);
		}
	}
}
