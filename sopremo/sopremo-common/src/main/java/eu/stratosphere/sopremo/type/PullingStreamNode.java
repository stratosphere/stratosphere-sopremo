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
package eu.stratosphere.sopremo.type;

import java.util.Collections;
import java.util.Iterator;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.util.ConversionIterator;

/**
 * @author rico
 * @author Arvid Heise
 */
public class PullingStreamNode<T extends IJsonNode> extends StreamNode<T> {
	private Iterator<IJsonNode> source;

	private EvaluationExpression expression = null;

	@SuppressWarnings("unchecked")
	private Iterator<T> iterator = Collections.EMPTY_SET.iterator();

	/**
	 * Initializes PullingStreamNode.
	 */
	public PullingStreamNode() {
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IArrayNode#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return !this.iterator.hasNext();
	}

	@SuppressWarnings("unchecked")
	public void setSource(IStreamNode<?> node) {
		this.source = (Iterator<IJsonNode>) node.iterator();
		this.iterator = new ConversionIterator<IJsonNode, T>(this.source) {
			@Override
			protected T convert(IJsonNode inputObject) {
				return (T) PullingStreamNode.this.expression.evaluate(inputObject);
			}
		};
	}

	@Override
	public Iterator<T> iterator() {
		return this.iterator;
	}

	public void setExpression(EvaluationExpression expression) {
		this.expression = expression;
	}
}