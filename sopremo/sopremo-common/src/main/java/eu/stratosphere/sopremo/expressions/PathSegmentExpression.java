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

import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.expressions.tree.ChildIterator;
import eu.stratosphere.sopremo.expressions.tree.NamedChildIterator;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * @author Arvid Heise
 */
public abstract class PathSegmentExpression extends EvaluationExpression {
	private EvaluationExpression inputExpression = EvaluationExpression.VALUE;

	/**
	 * Returns the inputExpression.
	 * 
	 * @return the inputExpression
	 */
	public EvaluationExpression getInputExpression() {
		return this.inputExpression;
	}

	/**
	 * Sets the inputExpression to the specified value.
	 * 
	 * @param inputExpression
	 *        the inputExpression to set
	 */
	public void setInputExpression(EvaluationExpression inputExpression) {
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
	public PathSegmentExpression withInputExpression(EvaluationExpression inputExpression) {
		this.setInputExpression(inputExpression);
		return this;
	}

	public PathSegmentExpression getLast() {
		if (this.inputExpression instanceof PathSegmentExpression)
			return ((PathSegmentExpression) this.inputExpression).getLast();
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.EvaluationExpression#clone()
	 */
	@Override
	public PathSegmentExpression clone() {
		return (PathSegmentExpression) super.clone();
	}

	public PathSegmentExpression withTail(EvaluationExpression tail) {
		this.getLast().setInputExpression(tail);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.EvaluationExpression#evaluate(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public IJsonNode evaluate(IJsonNode node) {
		return this.evaluateSegment(this.getInputExpression().evaluate(node));
	}

	protected abstract IJsonNode evaluateSegment(IJsonNode node);

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#copyPropertiesFrom(eu.stratosphere.sopremo.ISopremoType)
	 */
	@Override
	public void copyPropertiesFrom(ISopremoType original) {
		super.copyPropertiesFrom(original);
		this.setInputExpression(((PathSegmentExpression) original).getInputExpression().clone());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.inputExpression.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		PathSegmentExpression other = (PathSegmentExpression) obj;
		return this.inputExpression.equals(other.inputExpression);
	}

	protected void appendInputAsString(Appendable appendable) throws IOException {
		if (this.inputExpression != EvaluationExpression.VALUE)
			this.inputExpression.appendAsString(appendable);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.ExpressionParent#iterator()
	 */
	@Override
	public ChildIterator iterator() {
		return this.namedChildIterator();
	}

	protected NamedChildIterator namedChildIterator() {
		return new NamedChildIterator("valueExpression") {
			@Override
			protected void set(int index, EvaluationExpression childExpression) {
				PathSegmentExpression.this.inputExpression = childExpression;
			}

			@Override
			protected EvaluationExpression get(int index) {
				return PathSegmentExpression.this.inputExpression;
			}
		};
	}
}
