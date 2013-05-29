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
 * A {@link Schema} that handles {@link PactRecord}s with the structure: { &#60head nodes&#62, [other nodes] }.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 * @author Arvid Heise
 */
public class HeadArraySchema extends AbstractSchema {

	// [ head, ArrayNode(others) ]

	private final int headSize;

	private final LazyHeadArrayNode node;

	public HeadArraySchema(final int headSize) {
		super(headSize + 1, CollectionUtil.setRangeFrom(0, headSize));
		this.headSize = headSize;
		this.node = new LazyHeadArrayNode(headSize);
	}

	/**
	 * Initializes HeadArraySchema.
	 */
	HeadArraySchema() {
		super(0, IntSets.EMPTY_SET);
		this.headSize = 0;
		this.node = null;
	}

	public int getHeadSize() {
		return this.headSize;
	}

	/**
	 * Returns the node.
	 * 
	 * @return the node
	 */
	LazyHeadArrayNode getNode() {
		return this.node;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.serialization.Schema#indicesOf(eu.stratosphere.sopremo.expressions.EvaluationExpression)
	 */
	@Override
	public IntSet indicesOf(final EvaluationExpression expression) {
		final ArrayAccess arrayExpression = (ArrayAccess) expression;

		if (arrayExpression.isSelectingAll())
			return CollectionUtil.setRangeFrom(0, this.headSize + 1);
		else if (arrayExpression.isSelectingRange()) {
			final int startIndex = arrayExpression.getStartIndex();
			final int endIndex = arrayExpression.getEndIndex();
			if (startIndex < 0 || endIndex < 0)
				throw new UnsupportedOperationException("Tail indices are not supported yet");
			if (endIndex >= this.headSize)
				throw new IllegalArgumentException("Target index is not in head");

			return CollectionUtil.setRangeFrom(startIndex, endIndex);
		}
		final int index = arrayExpression.getStartIndex();
		if (index >= this.headSize)
			throw new IllegalArgumentException("Target index is not in head");
		else if (index < 0)
			throw new UnsupportedOperationException("Tail indices are not supported yet");
		return IntSets.singleton(index);
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
		if (value instanceof LazyHeadArrayNode) {
			target.copyPropertiesFrom(((LazyHeadArrayNode) value).getRecord());
			return;
		}

		final IArrayNode<IJsonNode> array = (IArrayNode<IJsonNode>) value;
		for (int index = 0; index < this.headSize; index++)
			target.addField(array.get(index));

		this.subArrayNode.init(array, Math.min(this.headSize, array.size()));
		target.addField(this.subArrayNode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.serialization.AbstractSchema#recordToJson(eu.stratosphere.sopremo.serialization.SopremoRecord
	 * )
	 */
	@Override
	public LazyHeadArrayNode recordToJson(SopremoRecord record) {
		this.node.setRecord(record);
		return this.node;
	}
}
