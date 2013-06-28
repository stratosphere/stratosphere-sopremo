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

import eu.stratosphere.util.CachingList;

/**
 * @author Arvid Heise
 */
public class CachingArrayNode<T extends IJsonNode> extends ArrayNode<T> {
	/**
	 * Initializes CachingArrayNode.
	 */
	public CachingArrayNode() {
		super(new CachingList<T>());
	}

	public T reuseUnusedNode() {
		return ((CachingList<T>) this.getChildren()).reuseUnusedElement();
	}
	
	public T getUnusedNode() {
		return ((CachingList<T>) this.getChildren()).getUnusedElement();
	}

	@SuppressWarnings("unchecked")
	public CachingArrayNode<T> addClone(T node) {
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
		for (IJsonNode element : this)
			element.clear();
		super.clear();
	}

	@Override
	public void setSize(int size) {
		((CachingList<T>) this.getChildren()).setSize(size);
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
}
