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

import eu.stratosphere.sopremo.operator.Operator;

/**
 * This expression represents all {@link Operator}s.
 */
public class NestedOperatorExpression extends UnevaluableExpression {

	private final Operator<?> operator;

	/**
	 * Initializes a NestedOperatorExpression with the given {@link Operator}.
	 * 
	 * @param operator
	 *        the operator that should be represented by this expression
	 */
	public NestedOperatorExpression(final Operator<?> operator) {
		super("Nested operator: " + operator);
		this.operator = operator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.operator.hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final NestedOperatorExpression other = (NestedOperatorExpression) obj;
		return this.operator.equals(other.operator);
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append("<");
		this.appendAsString(appendable);
		appendable.append(">");
	}

	/**
	 * Returns the operator
	 * 
	 * @return the operator
	 */
	public Operator<?> getOperator() {
		return this.operator;
	}
}
