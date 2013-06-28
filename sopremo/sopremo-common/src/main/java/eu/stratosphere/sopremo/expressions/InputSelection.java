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
package eu.stratosphere.sopremo.expressions;

import java.io.IOException;

import javolution.text.TypeFormat;
import eu.stratosphere.sopremo.EvaluationException;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Returns the element of an array which is saved at the specified index.
 */
@OptimizerHints(scope = Scope.ANY, minNodes = 1, maxNodes = OptimizerHints.UNBOUND)
public class InputSelection extends PathSegmentExpression {
	private final int index;

	/**
	 * Initializes an InputSelection with the given index.
	 * 
	 * @param index
	 *        the index of the element that should be returned
	 */
	public InputSelection(final int index) {
		this.index = index;
	}

	/**
	 * Initializes InputSelection.
	 */
	InputSelection() {
		this.index = 0;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#segmentHashCode()
	 */
	@Override
	protected int segmentHashCode() {
		return this.index;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#equalsSameClass(eu.stratosphere.sopremo.expressions
	 * .PathSegmentExpression)
	 */
	@Override
	public boolean equalsSameClass(PathSegmentExpression other) {
		return this.index == ((InputSelection) other).index;
	}

	@Override
	protected IJsonNode evaluateSegment(final IJsonNode node) {
		if (!(node instanceof IArrayNode<?>))
			throw new EvaluationException("Cannot select input " + node.getClass().getSimpleName());
		return ((IArrayNode<?>) node).get(this.index);
	}

	/**
	 * Returns the index
	 * 
	 * @return the index
	 */
	public int getIndex() {
		return this.index;
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.appendInputAsString(appendable);
		appendable.append("in");
		TypeFormat.format(this.index, appendable);
	}

	/**
	 * @return
	 */
	public ArrayAccess asArrayAccess() {
		return new ArrayAccess(index);
	}
}