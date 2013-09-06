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

import eu.stratosphere.pact.common.contract.Order;

public class OrderingExpression extends UnevaluableExpression {
	private final Order order;

	private final PathSegmentExpression path;

	public OrderingExpression(Order order, PathSegmentExpression path) {
		super("Ordering must be interpreted by operator");
		this.order = order;
		this.path = path;
	}

	/**
	 * Initializes OrderingExpression.
	 */
	public OrderingExpression() {
		this(null, null);
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
	public PathSegmentExpression getPath() {
		return this.path;
	}
}
