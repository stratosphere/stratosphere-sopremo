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

import eu.stratosphere.sopremo.aggregation.Aggregation;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;

/**
 * Aggregates a {@link eu.stratosphere.sopremo.type.IStreamNode} with a given {@link Aggregation}.<br/>
 * <br/>
 * Given a (stream) array node [x<sub>1</sub>, x<sub>2</sub>, ..., x<sub>n</sub>], the following three steps are
 * performed.
 * <ol>
 * <li>The aggregator is intialized once by invoking {@link Aggregation#initialize()}.
 * <li>For each element {@link Aggregation#aggregate(IJsonNode)} is invoked, which combines the aggregator with the new
 * element.
 * <li>Finally, the aggregator is retrieved with {@link Aggregation#getFinalAggregate()}.
 * </ol>
 */
public class AggregationExpression extends PathSegmentExpression {
	private final Aggregation aggregation;

	/**
	 * Initializes an AggregationExpression with the given {@link AggregationFunction} and an additional preprocessing.
	 * 
	 * @param aggregation
	 *        the aggregation which will should be used for aggregation
	 */
	public AggregationExpression(final Aggregation aggregation) {
		this.aggregation = aggregation.clone();
	}

	/**
	 * Initializes AggregationExpression.
	 */
	AggregationExpression() {
		this.aggregation = null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#evaluateSegment(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	protected IJsonNode evaluateSegment(IJsonNode nodes) {
		this.aggregation.initialize();
		for (final IJsonNode node : (IStreamNode<?>) nodes)
			this.aggregation.aggregate(node);
		return this.aggregation.getFinalAggregate();
	}

	/**
	 * Returns the aggregation.
	 * 
	 * @return the aggregation
	 */
	public Aggregation getAggregation() {
		return this.aggregation;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#equalsSameClass(eu.stratosphere.sopremo.expressions
	 * .PathSegmentExpression)
	 */
	@Override
	public boolean equalsSameClass(PathSegmentExpression other) {
		return this.aggregation.equals(((AggregationExpression) other).aggregation);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#segmentHashCode()
	 */
	@Override
	protected int segmentHashCode() {
		return this.aggregation.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#withInputExpression(eu.stratosphere.sopremo.expressions
	 * .EvaluationExpression)
	 */
	@Override
	public AggregationExpression withInputExpression(EvaluationExpression inputExpression) {
		return (AggregationExpression) super.withInputExpression(inputExpression);
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.aggregation.appendAsString(appendable);
		appendable.append('(');
		if (this.getInputExpression() != EvaluationExpression.VALUE)
			this.getInputExpression().appendAsString(appendable);
		appendable.append(')');
	}
}
