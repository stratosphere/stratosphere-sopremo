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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * @author Arvid Heise
 */
@DefaultSerializer(StreamNode.StreamNodeSerializer.class)
public class StreamNode<T extends IJsonNode> extends AbstractJsonNode implements IStreamNode<T> {
	private transient Iterator<T> nodeIterator;

	@SuppressWarnings("rawtypes")
	private final static Iterator EMPTY_ITERATOR = Collections.EMPTY_SET.iterator();

	@SuppressWarnings("unchecked")
	public StreamNode(Iterator<? extends T> nodeIterator) {
		this.nodeIterator = (Iterator<T>) nodeIterator;
	}

	/**
	 * Initializes OneTimeArrayNode.
	 */
	@SuppressWarnings("unchecked")
	public StreamNode() {
		this(EMPTY_ITERATOR);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#getType()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class<IArrayNode<T>> getType() {
		return (Class) IArrayNode.class;
	}

	public Iterator<T> getNodeIterator() {
		return this.nodeIterator;
	}

	public void setNodeIterator(Iterator<T> nodeIterator) {
		if (nodeIterator == null)
			throw new NullPointerException("nodeIterator must not be null");

		this.nodeIterator = nodeIterator;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IArrayNode#clear()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		this.nodeIterator = EMPTY_ITERATOR;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IArrayNode#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return !this.nodeIterator.hasNext();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return this.nodeIterator;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#toString(java.lang.StringBuilder)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		// printing should only be done during debugging
		appendable.append('[');
		final Iterator<T> iterator = this.nodeIterator;

		if (iterator.hasNext()) {
			List<T> elements = new ArrayList<T>();
			while (iterator.hasNext())
				elements.add((T) iterator.next().clone());

			elements.get(0).appendAsString(appendable);
			for (int index = 1; index < elements.size() && index < 100; index++) {
				appendable.append(", ");
				elements.get(index).appendAsString(appendable);
			}

			if (elements.size() > 100)
				appendable.append(", ...");
			this.nodeIterator = elements.iterator();
		}

		appendable.append(']');
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IJsonNode#copyValueFrom(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public void copyValueFrom(IJsonNode otherNode) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#compareToSameType(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public int compareToSameType(IJsonNode other) {
		return System.identityHashCode(this) - System.identityHashCode(other);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#hashCode()
	 */
	@Override
	public int hashCode() {
		return 42;
	}

	public static class StreamNodeSerializer extends Serializer<StreamNode<?>> {
		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#write(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Output, java.lang.Object)
		 */
		@Override
		public void write(Kryo kryo, Output output, StreamNode<?> object) {
			throw new UnsupportedOperationException(
				"Use CoreFunctions#ALL to transform this stream array into a materialized array");
		}
		
		/* (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#copy(com.esotericsoftware.kryo.Kryo, java.lang.Object)
		 */
		@Override
		public StreamNode<?> copy(Kryo kryo, StreamNode<?> original) {
			return original;
		}

		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#read(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Input, java.lang.Class)
		 */
		@Override
		public StreamNode<?> read(Kryo kryo, Input input, Class<StreamNode<?>> type) {
			throw new UnsupportedOperationException();
		}
	}

}
