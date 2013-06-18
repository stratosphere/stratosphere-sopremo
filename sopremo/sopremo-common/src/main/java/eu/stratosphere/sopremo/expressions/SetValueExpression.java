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

import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Returns the value of an attribute of one or more Json nodes.
 * 
 * @author Arvid Heise
 */
@OptimizerHints(scope = Scope.OBJECT)
public class SetValueExpression extends PathSegmentExpression {
	private final PathSegmentExpression valueLocator;

	private final EvaluationExpression replaceExpression;

	public SetValueExpression(PathSegmentExpression valueLocator, EvaluationExpression replaceExpression) {
		this.valueLocator = valueLocator;
		this.replaceExpression = replaceExpression;
	}

	/**
	 * Initializes SetValueExpression.
	 */
	SetValueExpression() {
		this.valueLocator = EvaluationExpression.VALUE;
		this.replaceExpression = EvaluationExpression.VALUE;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#segmentHashCode()
	 */
	@Override
	protected int segmentHashCode() {
		return 41 * this.valueLocator.hashCode() + this.replaceExpression.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#equalsSameClass(eu.stratosphere.sopremo.expressions
	 * .PathSegmentExpression)
	 */
	@Override
	public boolean equalsSameClass(PathSegmentExpression other) {
		final SetValueExpression setValueExpression = (SetValueExpression) other;
		return this.valueLocator.equals(setValueExpression.valueLocator)
			&& this.replaceExpression.equals(setValueExpression.replaceExpression);
	}

	/**
	 * Returns the replaceExpression.
	 * 
	 * @return the replaceExpression
	 */
	public EvaluationExpression getReplaceExpression() {
		return this.replaceExpression;
	}

	/**
	 * Returns the valueLocator.
	 * 
	 * @return the valueLocator
	 */
	public EvaluationExpression getValueLocator() {
		return this.valueLocator;
	}

	/**
	 * If the input node is an array, the evaluation of this array performs a spread operation. In that case, the
	 * returned node is an array that contains the attribute value of each element node in the input array. In all other
	 * cases, the return value is the node associated with the field name of this FieldAccess instance or
	 * {@link NullNode} if no such value exists.
	 */
	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#evaluateSegment(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	protected IJsonNode evaluateSegment(final IJsonNode node) {
		return this.valueLocator.set(node, this.replaceExpression.evaluate(node));
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.appendInputAsString(appendable);
		this.valueLocator.appendAsString(appendable);
		appendable.append("<-");
		this.replaceExpression.appendAsString(appendable);
	}
}