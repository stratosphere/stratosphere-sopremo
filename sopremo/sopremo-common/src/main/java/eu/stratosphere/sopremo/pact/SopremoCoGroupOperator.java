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
package eu.stratosphere.sopremo.pact;

import eu.stratosphere.api.common.operators.Ordering;
import eu.stratosphere.api.common.operators.base.CoGroupOperatorBase;
import eu.stratosphere.sopremo.operator.ElementaryOperator;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SopremoCoGroupOperator extends CoGroupOperatorBase {
	private final ElementaryOperator<?> operator;

	private Ordering firstInnerGroupOrdering, secondInnerGroupOrdering;

	public SopremoCoGroupOperator(final ElementaryOperator<?> operator, final Class udf, final int[] keyPositions1,
			final int[] keyPositions2, final String name) {
		super(udf, keyPositions1, keyPositions2, name);
		this.operator = operator;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.contract.GenericCoGroupOperator#isCombinableFirst()
	 */
	@Override
	public boolean isCombinableFirst() {
		return this.operator.isCombinableFirst();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.contract.GenericCoGroupOperator#isCombinableSecond()
	 */
	@Override
	public boolean isCombinableSecond() {
		return this.operator.isCombinableSecond();
	}

	public Ordering getFirstInnerGroupOrdering() {
		return this.firstInnerGroupOrdering;
	}

	public void setFirstInnerGroupOrdering(final Ordering firstInnerGroupOrdering) {
		if (firstInnerGroupOrdering == null)
			throw new NullPointerException("firstInnerGroupOrdering must not be null");

		this.firstInnerGroupOrdering = firstInnerGroupOrdering;
	}

	public Ordering getSecondInnerGroupOrdering() {
		return this.secondInnerGroupOrdering;
	}

	public void setSecondInnerGroupOrdering(final Ordering secondInnerGroupOrdering) {
		if (secondInnerGroupOrdering == null)
			throw new NullPointerException("secondInnerGroupOrdering must not be null");

		this.secondInnerGroupOrdering = secondInnerGroupOrdering;
	}

}