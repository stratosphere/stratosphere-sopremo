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

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import java.lang.reflect.Array;

/**
 * @author Arvid Heise
 */
public class ArrayCache<T> implements ISopremoCache {
	private final Class<T> type;

	private final transient Int2ObjectMap<T[]> cache = new Int2ObjectArrayMap<T[]>();

	public ArrayCache(Class<T> type) {
		this.type = type;
	}

	public T[] getArray(int size) {
		final T[] cachedArray = this.cache.get(size);
		if (cachedArray != null)
			return cachedArray;
		@SuppressWarnings("unchecked")
		T[] newArray = (T[]) Array.newInstance(this.type, size);
		this.cache.put(size, newArray);
		return newArray;
	}

	@Override
	public ArrayCache<T> clone() {
		return new ArrayCache<T>(this.type);
	}
}
