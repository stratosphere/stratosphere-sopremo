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
import eu.stratosphere.sopremo.operator.JsonStream;
import eu.stratosphere.sopremo.operator.Operator;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Special expression to select the input to which to apply subsequent expressions. This expression is a simple form of
 * {@link ArrayAccess} with a semantical annotation.<br/>
 * Inside an operator it is safe to freely exchange both expressions, however many operators perform optimizations by
 * inlining or transforming {@link InputSelection}. Thus, when manually assembling expressions for {@link Operator}s
 * make sure to use the semantically correct form: 
 * <ol>
 * <li> InputSelection: needed only when the {@link Operator} can have more than one input to disambiguate the access.</li>
 * <li> ArrayAccess: accessing an array inside a {@link JsonStream}.</li>
 * </ol>
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

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.appendInputAsString(appendable);
		appendable.append("in");
		TypeFormat.format(this.index, appendable);
	}

	/**
	 * Returns an {@link ArrayAccess} that performs the same information.
	 */
	public ArrayAccess asArrayAccess() {
		return new ArrayAccess(this.index);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#equalsSameClass(eu.stratosphere.sopremo.expressions
	 * .PathSegmentExpression)
	 */
	@Override
	public boolean equalsSameClass(final PathSegmentExpression other) {
		return this.index == ((InputSelection) other).index;
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
	protected IJsonNode evaluateSegment(final IJsonNode node) {
		if (!(node instanceof IArrayNode<?>))
			throw new EvaluationException("Cannot select input " + node.getClass().getSimpleName());
		return ((IArrayNode<?>) node).get(this.index);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#segmentHashCode()
	 */
	@Override
	protected int segmentHashCode() {
		return this.index;
	}
}