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
import eu.stratosphere.sopremo.expressions.tree.ConcatenatingChildIterator;
import eu.stratosphere.sopremo.expressions.tree.NamedChildIterator;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;
import eu.stratosphere.sopremo.type.PullingStreamNode;

/**
 * Projects an array onto another one.
 */
@OptimizerHints(scope = Scope.ARRAY, iterating = true)
public class ArrayProjection extends PathSegmentExpression {
	private EvaluationExpression projection;

	/**
	 * Initializes ArrayProjection.
	 */
	public ArrayProjection(EvaluationExpression projection) {
		this.projection = projection;
	}

	/**
	 * Initializes ArrayProjection.
	 */
	protected ArrayProjection() {
		this(EvaluationExpression.VALUE);
	}

	/**
	 * Returns the projection.
	 * 
	 * @return the projection
	 */
	public EvaluationExpression getProjection() {
		return this.projection;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#withInputExpression(eu.stratosphere.sopremo.expressions
	 * .EvaluationExpression)
	 */
	@Override
	public ArrayProjection withInputExpression(EvaluationExpression inputExpression) {
		return (ArrayProjection) super.withInputExpression(inputExpression);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#copyPropertiesFrom(eu.stratosphere.sopremo.ISopremoType
	 * )
	 */
	@Override
	public void copyPropertiesFrom(ISopremoType original) {
		super.copyPropertiesFrom(original);
		this.projection = ((ArrayProjection) original).projection.clone();
	}

	private final transient IArrayNode<IJsonNode> materializedResult = new ArrayNode<IJsonNode>();

	private final transient PullingStreamNode<?> virtualResult = new PullingStreamNode<IJsonNode>();

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#iterator()
	 */
	@Override
	public ChildIterator iterator() {
		return new ConcatenatingChildIterator(super.iterator(), new NamedChildIterator("projection") {
			@Override
			protected void set(int index, EvaluationExpression childExpression) {
				ArrayProjection.this.projection = childExpression;
			}

			@Override
			protected EvaluationExpression get(int index) {
				return ArrayProjection.this.projection;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#evaluateSegment(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	protected IJsonNode evaluateSegment(IJsonNode node) {
		if (!(node instanceof IArrayNode)) {
			// virtual projection
			this.virtualResult.setSource((IStreamNode<?>) node);
			this.virtualResult.setExpression(this.projection);
			return this.virtualResult;
		}
		// materialized projection
		final IArrayNode<?> array = (IArrayNode<?>) node;
		this.materializedResult.clear();
		for (int index = 0, size = array.size(); index < size; index++)
			this.materializedResult.add(this.projection.evaluate(array.get(index)));
		return this.materializedResult;
	}

	@Override
	public IJsonNode set(final IJsonNode node, final IJsonNode value) {
		final EvaluationExpression inputExpression = this.getInputExpression();
		@SuppressWarnings("unchecked")
		final IArrayNode<IJsonNode> arrayNode = (ArrayNode<IJsonNode>) node;
		for (int index = 0, size = arrayNode.size(); index < size; index++)
			arrayNode.set(index, inputExpression.set(arrayNode.get(index), value));
		return arrayNode;
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.getInputExpression().appendAsString(appendable);
		appendable.append("[*]");
		if (this.projection != EvaluationExpression.VALUE)
			this.projection.appendAsString(appendable);
	}
}
