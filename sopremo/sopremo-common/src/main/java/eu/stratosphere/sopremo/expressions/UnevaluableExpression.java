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
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Represents all expressions which are not evaluable.
 */
@OptimizerHints(scope = Scope.ANY)
public class UnevaluableExpression extends EvaluationExpression {
	private final String message;

	/**
	 * Initializes an UnevaluableExpression with the given message.
	 * 
	 * @param message
	 *        the message of this expression
	 */
	public UnevaluableExpression(final String message) {
		this.message = message;
	}

	/**
	 * Initializes UnevaluableExpression.
	 */
	UnevaluableExpression() {
		this.message = null;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!super.equals(obj))
			return false;
		final UnevaluableExpression other = (UnevaluableExpression) obj;
		return this.message.equals(other.message);
	}

	@Override
	public IJsonNode evaluate(final IJsonNode node) {
		throw new EvaluationException(this.message);
	}

	@Override
	public int hashCode() {
		return 31 * super.hashCode() + this.message.hashCode();
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append(this.message);
	}

}