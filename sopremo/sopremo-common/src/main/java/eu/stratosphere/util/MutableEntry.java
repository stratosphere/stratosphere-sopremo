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
package eu.stratosphere.util;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Arvid Heise
 */
public class MutableEntry<K, V> implements Map.Entry<K, V> {
	private K key;

	private V value;

	/*
	 * (non-Javadoc)
	 * @see java.util.Map.Entry#getKey()
	 */
	@Override
	public K getKey() {
		return this.key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map.Entry#getValue()
	 */
	@Override
	public V getValue() {
		return this.value;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map.Entry#setValue(java.lang.Object)
	 */
	@Override
	public V setValue(V value) {
		final V oldValue = this.value;
		this.value = value;
		return oldValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(this.key).append("=");
		builder.append(this.value);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @param nextMapping
	 */
	public void copyFrom(Entry<? extends K, ? extends V> entry) {
		this.setKey(entry.getKey());
		this.setValue(entry.getValue());
	}

}
