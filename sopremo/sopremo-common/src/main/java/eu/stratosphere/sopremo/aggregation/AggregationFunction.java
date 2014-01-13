/***********************************************************************************************************************
 *
 * Copyright (C) 2010-2013 by the Stratosphere project (http://stratosphere.eu)
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
package eu.stratosphere.sopremo.aggregation;

import java.io.IOException;

import eu.stratosphere.sopremo.function.SopremoFunction1;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;

/**
 * AggregationFunction allows {@link Aggregation} to be called inside regular transformations.
 */
public class AggregationFunction extends SopremoFunction1<IStreamNode<?>> {
	private final Aggregation aggregation;

	/**
	 * Initializes AggregationFunction.
	 * 
	 * @param aggregation
	 */
	public AggregationFunction(final Aggregation aggregation) {
		this.aggregation = aggregation.clone();
	}

	/**
	 * Initializes AggregationFunction.
	 */
	AggregationFunction() {
		this.aggregation = null;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.function.SopremoFunction#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.aggregation.appendAsString(appendable);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final AggregationFunction other = (AggregationFunction) obj;
		return this.aggregation.equals(other.aggregation);
	}

	/**
	 * Returns the aggregation.
	 * 
	 * @return the aggregation
	 */
	public Aggregation getAggregation() {
		return this.aggregation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.aggregation.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.function.SopremoFunction1#call(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	protected IJsonNode call(final IStreamNode<?> items) {
		this.aggregation.initialize();

		for (final IJsonNode item : items)
			this.aggregation.aggregate(item);

		return this.aggregation.getFinalAggregate();
	}
}
