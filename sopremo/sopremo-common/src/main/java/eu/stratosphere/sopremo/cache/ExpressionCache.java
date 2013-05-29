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

import java.util.ArrayList;
import java.util.List;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;

/**
 * @author Arvid Heise
 */
public class ExpressionCache<E extends EvaluationExpression> implements ISopremoCache {
	private final E template;

	private final transient List<E> expressions = new ArrayList<E>();

	public ExpressionCache(E template) {
		this.template = template;
	}

	/**
	 * Initializes ExpressionCache.
	 */
	ExpressionCache() {
		this.template = null;
	}

	/**
	 * Returns the template.
	 * 
	 * @return the template
	 */
	public E getTemplate() {
		return this.template;
	}

	@SuppressWarnings("unchecked")
	public E get(int index) {
		while (index >= this.expressions.size())
			this.expressions.add((E) this.template.clone());
		return this.expressions.get(index);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExpressionCache<E> clone() {
		return new ExpressionCache<E>((E) this.template.clone());
	}
}
