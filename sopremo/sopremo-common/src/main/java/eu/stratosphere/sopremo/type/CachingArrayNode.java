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
package eu.stratosphere.sopremo.type;

import java.lang.reflect.Array;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.util.CachingList;

/**
 */
@DefaultSerializer(AbstractArrayNode.ArraySerializer.class)
public class CachingArrayNode<T extends IJsonNode> extends ArrayNode<T> {
	/**
	 * Initializes CachingArrayNode.
	 */
	@SuppressWarnings("unchecked")
	public CachingArrayNode() {
		this((Class<T>) IJsonNode.class);
	}

	/**
	 * Initializes CachingArrayNode.
	 */
	public CachingArrayNode(final CachingList<T> objectArrayList) {
		super(objectArrayList);
	}

	@SuppressWarnings("unchecked")
	public CachingArrayNode(final Class<T> elemType) {
		this(CachingList.wrap((T[]) Array.newInstance(elemType, 0)));
	}

	@SuppressWarnings("unchecked")
	public CachingArrayNode<T> addClone(final T node) {
		final T unusedNode = this.reuseUnusedNode();
		if (unusedNode == null)
			this.add((T) node.clone());
		else if (unusedNode.getType() == node.getType())
			unusedNode.copyValueFrom(node);
		else
			// cannot reuse existing node
			this.set(this.size() - 1, (T) node.clone());
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.ArrayNode#clear()
	 */
	@Override
	public void clear() {
		for (final IJsonNode element : this)
			element.clear();
		super.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoCopyable#copy(com.esotericsoftware.kryo.Kryo)
	 */
	@Override
	public AbstractArrayNode<T> copy(final Kryo kryo) {
		final CachingArrayNode<T> node = new CachingArrayNode<T>();
		node.copyValueFrom(this);
		return node;
	}

	public T getUnusedNode() {
		return ((CachingList<T>) this.getChildren()).getUnusedElement();
	}

	public T reuseUnusedNode() {
		return ((CachingList<T>) this.getChildren()).reuseUnusedElement();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IArrayNode#setAll(eu.stratosphere.sopremo.type.IJsonNode[])
	 */
	// @Override
	public void setAll(final T[] nodes) {
		for (int index = 0; index < nodes.length; index++)
			this.set(index, nodes[index]);
		this.setSize(nodes.length);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setSize(final int size) {
		((CachingList<T>) this.getChildren()).size(size, (T) MissingNode.getInstance());
	}

	public static class ArraySerializer extends AbstractArrayNode.ArraySerializer {
		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.type.AbstractArrayNode.ArraySerializer#read(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Input, eu.stratosphere.sopremo.type.ArrayNode, java.lang.Class)
		 */
		@Override
		public ArrayNode<IJsonNode> read(Kryo kryo, Input input, ArrayNode<IJsonNode> oldInstance,
				Class<ArrayNode<IJsonNode>> type) {
			if (oldInstance == null)
				return this.read(kryo, input, type);

			final int len = input.readInt();

			final int currentLen = oldInstance.size();
			for (int i = 0; i < currentLen; i++)
				oldInstance.set(i, SopremoUtil.deserializeInto(kryo, input, oldInstance.get(i)));
			CachingArrayNode<IJsonNode> cachingArrayNode = (CachingArrayNode<IJsonNode>) oldInstance;
			for (int i = currentLen; i < len; i++)
				oldInstance.set(i, SopremoUtil.deserializeInto(kryo, input, cachingArrayNode.getUnusedNode()));

			oldInstance.setSize(len);
			return oldInstance;
		}
	}

	@SuppressWarnings("unchecked")
	public void setSize(int size, T defaultNode) {
		CachingList<T> cachingList = (CachingList<T>) this.getChildren();
		int oldSize = cachingList.size();
		if (size < oldSize)
			cachingList.size(size);
		else
			for (int i = oldSize; i < size; i++)
				addClone(defaultNode);
	}
}
