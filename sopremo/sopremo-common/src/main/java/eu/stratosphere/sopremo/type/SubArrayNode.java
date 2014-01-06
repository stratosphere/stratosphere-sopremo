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
package eu.stratosphere.sopremo.type;

import java.util.Iterator;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.google.common.collect.Iterators;

/**
 * Represents a reusable view on arrays.
 * 
 */
@DefaultSerializer(AbstractArrayNode.ArraySerializer.class)
public class SubArrayNode<T extends IJsonNode> extends AbstractArrayNode<T> {
	@SuppressWarnings("unchecked")
	private IArrayNode<T> originalArray = (IArrayNode<T>) ArrayNode.EMPTY;

	private int startIndex, length;

	public void init(final IArrayNode<T> originalArray, final int startIndex, final int length) {
		if (startIndex < 0)
			throw new IllegalArgumentException();
		if (length < 0)
			throw new IllegalArgumentException();
		this.originalArray = originalArray;
		this.startIndex = startIndex;
		this.length = length;
	}

	public void init(final IArrayNode<T> originalArray, final int startIndex) {
		this.init(originalArray, startIndex, originalArray.size() - startIndex);
	}

	@Override
	public void setSize(final int length) {
		if (length < 0)
			throw new IllegalArgumentException();
		this.length = length;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IArrayNode#size()
	 */
	@Override
	public int size() {
		return this.length;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IArrayNode#add(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public IArrayNode<T> add(final T node) {
		this.originalArray.add(this.startIndex + this.length, node);
		this.length++;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IArrayNode#add(int, eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public IArrayNode<T> add(final int index, final T element) {
		this.originalArray.add(this.startIndex + index, element);
		this.length++;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IArrayNode#get(int)
	 */
	@Override
	public T get(final int index) {
		return this.originalArray.get(this.startIndex + index);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IArrayNode#set(int, eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public void set(final int index, final T node) {
		this.originalArray.set(this.startIndex + index, node);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IArrayNode#remove(int)
	 */
	@Override
	public void remove(final int index) {
		if (index < 0 || index >= this.size())
			return;

		this.length--;
		this.originalArray.remove(this.startIndex + index);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IArrayNode#clear()
	 */
	@Override
	public void clear() {
		for (; this.length > 0; this.length--)
			this.originalArray.remove(this.startIndex);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IStreamNode#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.length == 0;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		final Iterator<T> iterator = this.originalArray.iterator();
		Iterators.advance(iterator, this.startIndex);
		return Iterators.limit(iterator, this.length);
	}
}
