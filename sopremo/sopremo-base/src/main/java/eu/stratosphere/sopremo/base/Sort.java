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

import eu.stratosphere.api.common.operators.Order;
import eu.stratosphere.sopremo.expressions.ConstantExpression;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.expressions.OrderingExpression;
import eu.stratosphere.sopremo.operator.DegreeOfParallelism;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoReduce;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;

/**
 * Sorts the tuples globally.
 */
@InputCardinality(1)
@DegreeOfParallelism(1)
@Name(verb = "sort", noun = "sort")
public class Sort extends ElementaryOperator<Sort> {
	/**
	 * Initializes Sort.
	 */
	public Sort() {
		this.setKeyExpressions(0, ConstantExpression.NULL);
		this.setInnerGroupOrder(0, new OrderingExpression());
	}

	public Order getDirection() {
		return this.getOrderingExpression().getOrder();
	}

	/**
	 * Returns the orderingExpression.
	 * 
	 * @return the orderingExpression
	 */
	public OrderingExpression getOrderingExpression() {
		return this.getInnerGroupOrder(0).get(0);
	}

	public EvaluationExpression getSortingExpression() {
		return this.getOrderingExpression().getPath();
	}

	@Property
	@Name(noun = { "direction", "order" })
	public void setDirection(final Order order) {
		this.getOrderingExpression().setOrder(order);
	}

	/**
	 * Sets the orderingExpression to the specified value.
	 * 
	 * @param orderingExpression
	 *        the orderingExpression to set
	 */
	public void setOrderingExpression(final OrderingExpression orderingExpression) {
		if (orderingExpression == null)
			throw new NullPointerException("orderingExpression must not be null");

		this.setInnerGroupOrder(0, orderingExpression);
	}

	@Property
	@Name(preposition = "on")
	public void setSortingExpression(final EvaluationExpression sortingExpression) {
		this.getOrderingExpression().setPath(sortingExpression.remove(InputSelection.class));
	}

	public Sort withDirection(final Order order) {
		this.setDirection(order);
		return this;
	}

	public Sort withOrderingExpression(final OrderingExpression orderingExpression) {
		this.setOrderingExpression(orderingExpression);
		return this;
	}

	public Sort withSortingExpression(final EvaluationExpression sortingExpression) {
		this.setSortingExpression(sortingExpression);
		return this;
	}

	public static class Implementation extends SopremoReduce {
		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.pact.GenericSopremoReduce#reduce(eu.stratosphere.sopremo.type.IStreamNode,
		 * eu.stratosphere.sopremo.pact.JsonCollector)
		 */
		@Override
		protected void reduce(final IStreamNode<IJsonNode> values, final JsonCollector<IJsonNode> out) {
			for (final IJsonNode value : values)
				out.collect(value);
		}
	}
}
// public class Sort extends ElementaryOperator<Sort> {
// /**
// * Initializes Sort.
// */
// public Sort() {
// this.setInnerGroupOrder(0,
// Collections.singletonList(new OrderingExpression(Order.ASCENDING, EvaluationExpression.VALUE)));
// this.setKeyExpressions(0, ConstantExpression.NULL);
// }
//
// /**
// * Sets the orderingExpression to the specified value.
// *
// * @param orderingExpression
// * the orderingExpression to set
// */
// public void setOrderingExpression(OrderingExpression orderingExpression) {
// if (orderingExpression == null)
// throw new NullPointerException("orderingExpression must not be null");
//
// this.setInnerGroupOrder(0, Collections.singletonList(orderingExpression));
// }
//
// /**
// * Returns the orderingExpression.
// *
// * @return the orderingExpression
// */
// public EvaluationExpression getOrderingExpression() {
// return this.getInnerGroupOrder(0).get(0);
// }
//
// public Sort withOrderingExpression(OrderingExpression orderingExpression) {
// setOrderingExpression(orderingExpression);
// return this;
// }
//
// public static class Implementation extends SopremoReduce {
//
// /*
// * (non-Javadoc)
// * @see eu.stratosphere.sopremo.pact.GenericSopremoReduce#reduce(eu.stratosphere.sopremo.type.IStreamNode,
// * eu.stratosphere.sopremo.pact.JsonCollector)
// */
// @Override
// protected void reduce(IStreamNode<IJsonNode> values, JsonCollector<IJsonNode> out) {
// // reemit all values, they have been sorted by the second order function
// for (IJsonNode value : values)
// out.collect(value);
// }
// }
// }
