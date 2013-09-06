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

import eu.stratosphere.pact.common.contract.Ordering;
import eu.stratosphere.pact.generic.contract.GenericReduceContract;
import eu.stratosphere.sopremo.operator.ElementaryOperator;

@SuppressWarnings({ "unchecked", "rawtypes" })
public final class SopremoReduceContract extends GenericReduceContract {
	private final ElementaryOperator<?> operator;

	private Ordering innerGroupOrder;

	public SopremoReduceContract(ElementaryOperator<?> operator, Class udf, int[] keyPositions, String name) {
		super(udf, keyPositions, name);
		this.operator = operator;
	}

	/**
	 * Sets the innerGroupOrder to the specified value.
	 * 
	 * @param innerGroupOrder
	 *        the innerGroupOrder to set
	 */
	public void setInnerGroupOrder(Ordering secondarySort) {
		if (secondarySort == null)
			throw new NullPointerException("innerGroupOrder must not be null");

		this.innerGroupOrder = secondarySort;
	}

	/**
	 * Returns the innerGroupOrder.
	 * 
	 * @return the innerGroupOrder
	 */
	public Ordering getInnerGroupOrder() {
		return this.innerGroupOrder;
	}

	@Override
	public boolean isCombinable() {
		return this.operator.isCombinable();
	}
}