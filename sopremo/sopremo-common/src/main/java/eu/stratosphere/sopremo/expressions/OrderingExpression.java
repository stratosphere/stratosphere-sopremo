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
package eu.stratosphere.sopremo.expressions;

import java.io.IOException;
import java.util.Comparator;

import eu.stratosphere.api.common.operators.Order;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IntNode;

public class OrderingExpression extends EvaluationExpression {
	private Order order;

	private EvaluationExpression path;

	private final transient IntNode result = new IntNode();

	public OrderingExpression(final Order order, final PathSegmentExpression path) {
		this.order = order;
		this.path = path;
	}

	/**
	 * Initializes OrderingExpression.
	 */
	public OrderingExpression() {
		this(Order.ASCENDING, EvaluationExpression.VALUE);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.EvaluationExpression#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append(this.order.toString()).append(' ');
		this.path.appendAsString(appendable);
	}

	/**
	 * Returns the order.
	 * 
	 * @return the order
	 */
	public Order getOrder() {
		return this.order;
	}

	/**
	 * Returns the path.
	 * 
	 * @return the path
	 */
	public EvaluationExpression getPath() {
		return this.path;
	}

	/**
	 * Sets the path to the specified value.
	 * 
	 * @param path
	 *        the path to set
	 */
	public void setPath(final EvaluationExpression path) {
		if (path == null)
			throw new NullPointerException("path must not be null");

		this.path = path;
	}

	/**
	 * Sets the order to the specified value.
	 * 
	 * @param order
	 *        the order to set
	 */
	public void setOrder(final Order order) {
		if (order == null)
			throw new NullPointerException("order must not be null");

		this.order = order;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.EvaluationExpression#evaluate(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public IntNode evaluate(final IJsonNode node) {
		@SuppressWarnings("unchecked")
		final IArrayNode<IJsonNode> pair = (IArrayNode<IJsonNode>) node;
		this.result.setValue(this.compare(pair.get(0), pair.get(1)));
		return this.result;
	}

	public int compare(final IJsonNode node1, final IJsonNode node2) {
		final int result = this.path.evaluate(node1).compareTo(this.path.evaluate(node2));
		return this.order == Order.DESCENDING ? -result : result;
	}

	public Comparator<IJsonNode> asComparator() {
		return new Comparator<IJsonNode>() {
			/*
			 * (non-Javadoc)
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(final IJsonNode node1, final IJsonNode node2) {
				final int result =
					OrderingExpression.this.path.evaluate(node1).compareTo(OrderingExpression.this.path.evaluate(node2));
				return OrderingExpression.this.order == Order.DESCENDING ? -result : result;
			}
		};
	}
}
