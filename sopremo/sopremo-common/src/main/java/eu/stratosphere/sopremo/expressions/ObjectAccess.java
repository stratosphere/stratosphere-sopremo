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

import eu.stratosphere.sopremo.EvaluationException;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.sopremo.type.NullNode;

/**
 * Returns the value of an attribute of one or more Json nodes.
 * 
 * @author Arvid Heise
 */
@OptimizerHints(scope = Scope.OBJECT)
public class ObjectAccess extends PathSegmentExpression {

	private final String field;

	/**
	 * Initializes ObjectAccess with the given field name.
	 * 
	 * @param field
	 *        the name of the field
	 */
	public ObjectAccess(final String field) {
		this.field = field;
	}

	/**
	 * Initializes ObjectAccess.
	 */
	ObjectAccess() {
		this.field = null;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#segmentHashCode()
	 */
	@Override
	protected int segmentHashCode() {
		return this.field.hashCode();
	}

	@Override
	public boolean equalsSameClass(PathSegmentExpression other) {
		return this.field.equals(((ObjectAccess) other).field);
	}

	/**
	 * Returns the field.
	 * 
	 * @return the field
	 */
	public String getField() {
		return this.field;
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
		if (!(node instanceof IObjectNode)) 
			return MissingNode.getInstance();
		final IJsonNode value = ((IObjectNode) node).get(this.field);
		return value == null ? NullNode.getInstance() : value;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#setSegment(eu.stratosphere.sopremo.type.IJsonNode,
	 * eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	protected IJsonNode setSegment(IJsonNode node, IJsonNode value) {
		if (!(node instanceof IObjectNode))
			throw new EvaluationException("Cannot set field of non-object " + node.getClass().getSimpleName());
		SopremoUtil.replaceWithCopy((IObjectNode) node, this.field, value);
		return node;
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.appendInputAsString(appendable);
		appendable.append('.').append(this.field);
	}
}