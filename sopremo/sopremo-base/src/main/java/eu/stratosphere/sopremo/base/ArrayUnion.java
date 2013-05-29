/***********************************************************************************************************************
 *
 * Copyright (C) 2010-2013 by the Stratosphere project (http://stratosphere.eu)
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

import eu.stratosphere.sopremo.aggregation.Aggregation;
import eu.stratosphere.sopremo.aggregation.FixedTypeTransitiveAggregation;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

public final class ArrayUnion extends FixedTypeTransitiveAggregation<ArrayNode<IJsonNode>> {
	public ArrayUnion() {
		super("U<values>", new ArrayNode<IJsonNode>());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.aggregation.FixedTypeTransitiveAggregation#aggregateInto(eu.stratosphere.sopremo.type
	 * .IJsonNode, eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	protected void aggregateInto(final ArrayNode<IJsonNode> aggregator, IJsonNode element) {
		final IArrayNode<?> node = (IArrayNode<?>) element;
		for (int index = 0; index < node.size(); index++)
			if (aggregator.get(index).isMissing() && !node.get(index).isMissing())
				aggregator.set(index, node.get(index));
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.aggregation.TransitiveAggregation#clone()
	 */
	@Override
	public Aggregation clone() {
		return new ArrayUnion();
	}
}