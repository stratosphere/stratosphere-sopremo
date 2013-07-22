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
package eu.stratosphere.sopremo.cache;

import java.util.IdentityHashMap;
import java.util.Map;

import eu.stratosphere.sopremo.type.DefaultNodeFactory;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.NodeFactory;

/**
 * @author Arvid Heise
 */
public final class NodeCache implements ISopremoCache {

	private final transient Map<Class<? extends IJsonNode>, IJsonNode> classCache =
		new IdentityHashMap<Class<? extends IJsonNode>, IJsonNode>();

	private final NodeFactory nodeFactory;

	/**
	 * Initializes NodeCache.
	 */
	public NodeCache() {
		this(DefaultNodeFactory.getInstance());
	}

	public NodeCache(NodeFactory nodeFactory) {
		this.nodeFactory = nodeFactory;
	}

	@SuppressWarnings("unchecked")
	public <T extends IJsonNode> T getNode(Class<T> type) {
		final IJsonNode cachedValue = this.classCache.get(type);
		if (cachedValue != null)
			return (T) cachedValue;
		final IJsonNode newValue = this.nodeFactory.instantiate(type);
		this.classCache.put(type, newValue);
		return (T) newValue;
	}

	/**
	 * Creates a clone and reuses an existing node in the cache with the same type.
	 */
	public IJsonNode clone(IJsonNode node) {
		final IJsonNode clone = this.getNode(node.getClass());
		clone.copyValueFrom(node);
		return clone;
	}

	@Override
	public NodeCache clone() {
		return new NodeCache();
	}
}
