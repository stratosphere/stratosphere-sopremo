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

import eu.stratosphere.sopremo.expressions.EvaluationExpression;

/**
 * @author Arvid Heise
 */
public class ExpressionCacheCache<E extends EvaluationExpression> implements ISopremoCache {
	// Thread-local may be overkill, but this ensures that the complete cache is garbage-collected after a thread ended
	private final transient ThreadLocal<Map<E, ExpressionCache<E>>> caches =
		new ThreadLocal<Map<E, ExpressionCache<E>>>() {
			@Override
			protected Map<E, ExpressionCache<E>> initialValue() {
				return new IdentityHashMap<E, ExpressionCache<E>>();
			};
		};

	public ExpressionCache<E> get(E expression) {
		final ExpressionCache<E> cache = this.caches.get().get(expression);
		if (cache != null)
			return cache;
		final ExpressionCache<E> newCache = new ExpressionCache<E>(expression);
		this.caches.get().put(expression, newCache);
		return newCache;
	}

	@Override
	public ExpressionCacheCache<E> clone() {
		return new ExpressionCacheCache<E>();
	}
}
