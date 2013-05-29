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
package eu.stratosphere.sopremo.serialization;

import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.SubArrayNode;
import eu.stratosphere.util.CollectionUtil;

/**
 * A {@link Schema} that handles {@link PactRecord}s with the structure: { [other nodes], &#60tail nodes&#62 }.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
public class TailArraySchema extends AbstractSchema {

	// [ head, ArrayNode(others), tail ]

	private final LazyTailArrayNode node;

	private final int tailSize;

	public TailArraySchema(final int tailSize) {
		super(tailSize + 1, CollectionUtil.setRangeFrom(1, tailSize + 1));
		this.tailSize = tailSize;
		this.node = new LazyTailArrayNode(tailSize);
	}

	/**
	 * Initializes TailArraySchema.
	 */
	TailArraySchema() {
		super(0, IntSets.EMPTY_SET);
		this.tailSize = 0;
		this.node = null;
	}

	/**
	 * Returns the node.
	 * 
	 * @return the node
	 */
	LazyTailArrayNode getNode() {
		return this.node;
	}

	/**
	 * Returns the tailSize.
	 * 
	 * @return the tailSize
	 */
	public int getTailSize() {
		return this.tailSize;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.serialization.Schema#indicesOf(eu.stratosphere.sopremo.expressions.EvaluationExpression)
	 */
	@Override
	public IntSet indicesOf(final EvaluationExpression expression) {
		// TODO check correctness
		final ArrayAccess arrayExpression = (ArrayAccess) expression;

		if (arrayExpression.isSelectingAll())
			return CollectionUtil.setRangeFrom(0, this.tailSize + 1);
		else if (arrayExpression.isSelectingRange()) {
			int startIndex = arrayExpression.getStartIndex();
			int endIndex = arrayExpression.getEndIndex();
			if (startIndex >= 0 || endIndex >= 0)
				throw new UnsupportedOperationException("Head indices are not supported yet");
			endIndex += this.tailSize;
			startIndex += this.tailSize;
			if (endIndex >= this.tailSize)
				throw new IllegalArgumentException("Target index is not in tail");

			return CollectionUtil.setRangeFrom(startIndex + 1, endIndex + 1);
		}
		int index = arrayExpression.getStartIndex();
		if (index >= 0)
			throw new UnsupportedOperationException("Head indices are not supported yet");
		index += this.tailSize;
		if (index < 0)
			throw new IllegalArgumentException("Target index is not in tail");

		return IntSets.singleton(index + 1);
	}

	private final transient SubArrayNode<IJsonNode> subArrayNode = new SubArrayNode<IJsonNode>();

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.Schema#jsonToRecord(eu.stratosphere.sopremo.type.IJsonNode,
	 * eu.stratosphere.pact.common.type.PactRecord)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void jsonToRecord(final IJsonNode value, SopremoRecord target) {
		if (value instanceof LazyTailArrayNode) {
			target.copyPropertiesFrom(((LazyTailArrayNode) value).getRecord());
			return;
		}

		final IArrayNode<IJsonNode> array = (IArrayNode<IJsonNode>) value;

		final int size = array.size();
		this.subArrayNode.init(array, 0, Math.max(0, size - this.tailSize));
		target.addField(this.subArrayNode);

		for (int index = 0; index < this.tailSize; index++)
			target.addField(array.get(size - this.tailSize + index));
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.Schema#recordToJson(eu.stratosphere.pact.common.type.PactRecord,
	 * eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public IJsonNode recordToJson(final SopremoRecord record) {
		this.node.setRecord(record);
		return this.node;
	}

}
