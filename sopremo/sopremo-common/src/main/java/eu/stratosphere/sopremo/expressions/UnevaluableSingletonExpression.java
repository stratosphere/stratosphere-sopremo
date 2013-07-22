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

import eu.stratosphere.sopremo.Immutable;

/**
 * This expression represents a Singleton.
 */
@Immutable
public class UnevaluableSingletonExpression extends UnevaluableExpression {

	/**
	 * Initializes SingletonExpression.
	 */
	public UnevaluableSingletonExpression(final String textualRepresentation) {
		super(textualRepresentation);
	}

	/**
	 * Initializes UnevaluableSingletonExpression.
	 */
	UnevaluableSingletonExpression() {
	}

	@Override
	public boolean equals(final Object obj) {
		return obj == this;
	}

	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}
}
