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

import eu.stratosphere.sopremo.expressions.tree.ChildIterator;
import eu.stratosphere.sopremo.expressions.tree.NamedChildIterator;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 */
public abstract class PathSegmentExpression extends EvaluationExpression {
	private EvaluationExpression inputExpression = EvaluationExpression.VALUE;

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.EvaluationExpression#clone()
	 */
	@Override
	public PathSegmentExpression clone() {
		return (PathSegmentExpression) super.clone();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.EvaluationExpression#clone()
	 */
	public PathSegmentExpression cloneSegment() {
		final EvaluationExpression originalInput = this.inputExpression;
		this.inputExpression = EvaluationExpression.VALUE;
		final PathSegmentExpression partialClone = this.clone();
		this.inputExpression = originalInput;
		return partialClone;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final PathSegmentExpression other = (PathSegmentExpression) obj;
		return this.equalsSameClass(other) && this.inputExpression.equals(other.inputExpression);
	}

	public boolean equalsThisSeqment(final PathSegmentExpression obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		return this.equalsSameClass(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.EvaluationExpression#evaluate(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public IJsonNode evaluate(final IJsonNode node) {
		return this.evaluateSegment(this.getInputExpression().evaluate(node));
	}

	/**
	 * Returns the inputExpression.
	 * 
	 * @return the inputExpression
	 */
	public EvaluationExpression getInputExpression() {
		return this.inputExpression;
	}

	public PathSegmentExpression getLast() {
		PathSegmentExpression segment = this;
		while (segment.inputExpression != EvaluationExpression.VALUE &&
			this.inputExpression instanceof PathSegmentExpression)
			segment = (PathSegmentExpression) this.inputExpression;
		return segment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.inputExpression.hashCode();
		result = prime * result + this.segmentHashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.ExpressionParent#iterator()
	 */
	@Override
	public ChildIterator iterator() {
		return this.namedChildIterator();
	}

	/**
	 * Sets the value of the node specified by this expression.<br/>
	 * Use this method with caution. Changing values in-place has side-effects inside of reducer, matcher, and
	 * cogrouper. To be safe, always clone the input node.
	 * 
	 * @param node
	 *        the node to change
	 * @param value
	 *        the value to set
	 * @return the node or a new node if the expression directly accesses the node
	 */
	public IJsonNode set(final IJsonNode node, final IJsonNode value) {
		if (this.getInputExpression() == EvaluationExpression.VALUE)
			return this.setSegment(node, value);
		this.setSegment(this.getInputExpression().evaluate(node), value);
		return node;
	}

	/**
	 * Sets the inputExpression to the specified value.
	 * 
	 * @param inputExpression
	 *        the inputExpression to set
	 */
	public void setInputExpression(final EvaluationExpression inputExpression) {
		if (inputExpression == null)
			throw new NullPointerException("inputExpression must not be null");

		this.inputExpression = inputExpression;
	}

	/**
	 * Sets the inputExpression to the specified value.
	 * 
	 * @param inputExpression
	 *        the inputExpression to set
	 */
	public PathSegmentExpression withInputExpression(final EvaluationExpression inputExpression) {
		this.setInputExpression(inputExpression);
		return this;
	}

	public PathSegmentExpression withTail(final EvaluationExpression tail) {
		this.getLast().setInputExpression(tail);
		return this;
	}

	protected void appendInputAsString(final Appendable appendable) throws IOException {
		if (this.inputExpression != EvaluationExpression.VALUE)
			this.inputExpression.appendAsString(appendable);
	}

	protected abstract boolean equalsSameClass(PathSegmentExpression other);

	protected abstract IJsonNode evaluateSegment(IJsonNode node);

	protected NamedChildIterator namedChildIterator() {
		return new NamedChildIterator("inputExpression") {
			@Override
			protected EvaluationExpression get(final int index) {
				return PathSegmentExpression.this.inputExpression;
			}

			@Override
			protected void set(final int index, final EvaluationExpression childExpression) {
				PathSegmentExpression.this.inputExpression = childExpression;
			}
		};
	}

	protected abstract int segmentHashCode();

	protected IJsonNode setSegment(final IJsonNode node, final IJsonNode value) {
		throw new UnsupportedOperationException(String.format(
			"Cannot change the value with expression %s of node %s to %s", this, node, value));
	}
}
