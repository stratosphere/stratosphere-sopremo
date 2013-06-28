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

import eu.stratosphere.sopremo.ICloneable;
import eu.stratosphere.sopremo.ISopremoType;

/**
 * Interface for all JsonNodes.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
public interface IJsonNode extends ISopremoType, ICloneable, Comparable<IJsonNode> {
	public void clear();
	
	public Class<? extends IJsonNode> getType();

	/**
	 * Deeply copies the state of the given node to this node.
	 * 
	 * @param otherNode
	 *        the node of which the state should be copied
	 */
	public abstract void copyValueFrom(IJsonNode otherNode);

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
}