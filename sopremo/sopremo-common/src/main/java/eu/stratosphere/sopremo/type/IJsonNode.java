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

import eu.stratosphere.sopremo.ICloneable;
import eu.stratosphere.sopremo.ISopremoType;

/**
 * Interface for all JsonNodes.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
public interface IJsonNode extends ISopremoType, ICloneable, Comparable<IJsonNode> {
	/**
	 * This enumeration contains all possible types of JsonNode.
	 * 
	 * @author Michael Hopstock
	 * @author Tommy Neubert
	 */
	public enum Type {
		IntNode(IntNode.class, true),
		LongNode(LongNode.class, true),
		BigIntegerNode(BigIntegerNode.class, true),
		DecimalNode(DecimalNode.class, true),
		DoubleNode(DoubleNode.class, true),

		ArrayNode(ArrayNode.class, false),
		ObjectNode(ObjectNode.class, false),
		TextNode(TextNode.class, false),
		BooleanNode(BooleanNode.class, false),
		NullNode(NullNode.class, false),
		MissingNode(MissingNode.class, false),
		CustomNode(AbstractJsonNode.class, false);

		private final Class<? extends IJsonNode> clazz;

		private final boolean numeric;

		private Type(final Class<? extends IJsonNode> clazz, final boolean isNumeric) {
			this.clazz = clazz;
			this.numeric = isNumeric;
		}

		/**
		 * Returns either the node represented by a specific enumeration element is numeric or not.
		 */
		public boolean isNumeric() {
			return this.numeric;
		}

		/**
		 * Returns an instantiable class of the node which is represented by a specific enumeration element.
		 * 
		 * @return the class of the represented node
		 */
		public Class<? extends IJsonNode> getClazz() {
			return this.clazz;
		}

	};

	public abstract void clear();

	/**
	 * Returns the {@link eu.stratosphere.sopremo.type.JsonNode.Type} of this node.
	 * 
	 * @return nodetype
	 */
	public abstract AbstractJsonNode.Type getType();

	/**
	 * Transforms this node into his standard representation.
	 * 
	 * @return standard representation
	 */
	public abstract IJsonNode canonicalize();

	/**
	 * Deeply copies the state of the given node to this node.
	 * 
	 * @param otherNode
	 *        the node of which the state should be copied
	 */
	public abstract void copyValueFrom(IJsonNode otherNode);

	/**
	 * Checks whether the state of the given node can be deeply copied with {@link #copyValueFrom(IJsonNode)} to this
	 * node.
	 * 
	 * @param otherNode
	 *        the node of which the state should be copied
	 * @return true if it can be copied
	 */
	public boolean isCopyable(IJsonNode otherNode);

	/**
	 * Creates a new instance of this class and invokes {@link #copyValueFrom(IJsonNode)}.<br />
	 * A call to this function should be completely avoided during runtime and only used during query construction and
	 * optimization.
	 * 
	 * @return a copy of this object
	 */
	@Override
	public IJsonNode clone();

	/**
	 * Returns either this node is a representation for a null-value or not.
	 */
	public abstract boolean isNull();

	/**
	 * Returns either this node is a representation for a missing value or not.
	 */
	public abstract boolean isMissing();

	/**
	 * Returns either this node is a representation of a Json-Object or not.
	 */
	public abstract boolean isObject();

	/**
	 * Returns either this node is a representation of a Json-Array or not.
	 */
	public abstract boolean isArray();

	/**
	 * Returns either this node is a representation of a Text-value or not.
	 */
	public abstract boolean isTextual();

	/**
	 * Compares this node with another.
	 * 
	 * @param other
	 *        the node this node should be compared with
	 * @return result of the comparison
	 */
	@Override
	public abstract int compareTo(final IJsonNode other);

	/**
	 * Compares this node with another {@link eu.stratosphere.sopremo.type.IJsonNode}.
	 * 
	 * @param other
	 *        the node this node should be compared with
	 * @return result of the comparison
	 */
	public abstract int compareToSameType(IJsonNode other);

	public int getMaxNormalizedKeyLen();

	public void copyNormalizedKey(final byte[] target, final int offset, final int len);

	/**
	 * @param in
	 */
	public abstract IJsonNode readResolve(DataInput in) throws IOException;

	/**
	 * @param out
	 */
	public abstract void write(DataOutput out) throws IOException;
}