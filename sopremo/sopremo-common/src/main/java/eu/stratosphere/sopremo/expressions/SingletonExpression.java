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

import eu.stratosphere.sopremo.Immutable;

/**
 * This expression represents a Singleton.
 */
@Immutable
public abstract class SingletonExpression extends EvaluationExpression {

	private final String textualRepresentation;

	/**
	 * Initializes SingletonExpression.
	 */
	public SingletonExpression(final String textualRepresentation) {
		this.textualRepresentation = textualRepresentation;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj == this;
	}

	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.EvaluationExpression#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		super.appendAsString(appendable);
		appendable.append(this.textualRepresentation);
	}

}
