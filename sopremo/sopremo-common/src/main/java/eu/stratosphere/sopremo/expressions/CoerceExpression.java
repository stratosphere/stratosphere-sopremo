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

import javolution.text.TextFormat;
import eu.stratosphere.sopremo.cache.NodeCache;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.TypeCoercer;

/**
 * Converts the result of an evaluation to a various number of node types.
 */
@OptimizerHints(scope = Scope.NUMBER)
public class CoerceExpression extends PathSegmentExpression {
	private final Class<IJsonNode> targetType;

	/**
	 * Initializes a CoerceExpression with the given value and the given type.
	 * 
	 * @param targetType
	 *        the class of the node the result should be converted to
	 */
	@SuppressWarnings("unchecked")
	public CoerceExpression(final Class<? extends IJsonNode> targetType) {
		if (targetType == null)
			throw new NullPointerException();
		this.targetType = (Class<IJsonNode>) targetType;
	}

	/**
	 * Initializes CoerceExpression.
	 */
	CoerceExpression() {
		this.targetType = null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#withInputExpression(eu.stratosphere.sopremo.expressions
	 * .EvaluationExpression)
	 */
	@Override
	public CoerceExpression withInputExpression(EvaluationExpression inputExpression) {
		return (CoerceExpression) super.withInputExpression(inputExpression);
	}

	private final transient NodeCache nodeCache = new NodeCache();

	@Override
	protected IJsonNode evaluateSegment(final IJsonNode node) {
		return TypeCoercer.INSTANCE.coerce(node, this.nodeCache, this.targetType);
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append('(');
		TextFormat.getInstance(Class.class).format(this.targetType, appendable);
		appendable.append(')');
		if (this.getInputExpression() != EvaluationExpression.VALUE) {
			appendable.append(' ');
			this.getInputExpression().appendAsString(appendable);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.targetType.hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!super.equals(obj))
			return false;
		final CoerceExpression other = (CoerceExpression) obj;
		return this.targetType == other.targetType;
	}
}
