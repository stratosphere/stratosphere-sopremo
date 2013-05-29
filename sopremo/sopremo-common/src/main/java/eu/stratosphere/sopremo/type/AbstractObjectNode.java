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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoCopyable;

import eu.stratosphere.sopremo.pact.SopremoUtil;

/**
 * Abstract class to provide basic implementations for object type nodes.
 * 
 * @author Arvid Heise
 */
public abstract class AbstractObjectNode extends AbstractJsonNode implements IObjectNode,
		KryoCopyable<AbstractObjectNode> {

	/**
	 * Initializes AbstractObjectNode.
	 */
	public AbstractObjectNode() {
		super();
	}

	@Override
	public final Type getType() {
		return Type.ObjectNode;
	}

	@Override
	public IJsonNode readResolve(final DataInput in) throws IOException {
		this.clear();
		final int len = in.readInt();

		for (int i = 0; i < len; i++) {
			final String key = in.readUTF();
			this.put(key, SopremoUtil.deserializeNode(in, null));
		}
		return this;
	}

	@Override
	public void write(final DataOutput out) throws IOException {
		out.writeInt(this.size());

		for (final Entry<String, IJsonNode> entry : this) {
			out.writeUTF(entry.getKey());
			SopremoUtil.serializeNode(out, entry.getValue());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 47;
		int result = 1;
		for (Entry<String, IJsonNode> entry : this)
			result = prime * result + entry.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonObject#putAll(eu.stratosphere.sopremo.type.JsonObject)
	 */
	@Override
	public IObjectNode putAll(final IObjectNode jsonNode) {
		for (final Entry<String, IJsonNode> entry : jsonNode)
			this.put(entry.getKey(), entry.getValue());
		return this;
	}

	// @Override
	// public boolean equals(final Object obj) {
	// if (this == obj)
	// return true;
	// if (obj == null)
	// return false;
	//
	// final AbstractObjectNode other = (AbstractObjectNode) obj;
	// return this.compareToSameType(other) == 0;
	// }

	@Override
	public final boolean isObject() {
		return true;
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append("{");
		boolean first = true;
		final Iterator<Entry<String, IJsonNode>> iterator = this.iterator();
		while (iterator.hasNext()) {
			if (first)
				first = false;
			else
				appendable.append(", ");
			final Entry<String, IJsonNode> child = iterator.next();
			appendable.append(child.getKey()).append(": ");
			child.getValue().appendAsString(appendable);
		}
		appendable.append("}");
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#clone()
	 */
	@Override
	public AbstractObjectNode clone() {
		return (AbstractObjectNode) super.clone();
	}

	@Override
	public int compareToSameType(final IJsonNode other) {
		final IObjectNode node = (IObjectNode) other;
		final Iterator<Entry<String, IJsonNode>> entries1 = this.iterator(), entries2 = node.iterator();

		while (entries1.hasNext() && entries2.hasNext()) {
			final Entry<String, IJsonNode> entry1 = entries1.next(), entry2 = entries2.next();
			final int keyComparison = entry1.getKey().compareTo(entry2.getKey());
			if (keyComparison != 0)
				return keyComparison;

			final int valueComparison = entry1.getValue().compareTo(entry2.getValue());
			if (valueComparison != 0)
				return valueComparison;
		}

		if (!entries1.hasNext())
			return entries2.hasNext() ? -1 : 0;
		if (!entries2.hasNext())
			return 1;
		return 0;
	}

	@Override
	public void copyValueFrom(final IJsonNode otherNode) {
		this.checkForSameType(otherNode);
		final IObjectNode objectNode = (IObjectNode) otherNode;
		this.clear();

		for (final Entry<String, IJsonNode> child : objectNode)
			this.put(child.getKey(), child.getValue().clone());
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoCopyable#copy(com.esotericsoftware.kryo.Kryo)
	 */
	@Override
	public AbstractObjectNode copy(Kryo kryo) {
		final ObjectNode node = new ObjectNode();
		node.copyValueFrom(this);
		return node;
	}
}