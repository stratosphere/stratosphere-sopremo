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

/**
 * This expression represents a pointer to a method.
 */
public class MethodPointerExpression extends UnevaluableExpression {
	private final String functionName;

	/**
	 * Initializes a MethodPointerExpression with the given function name.
	 * 
	 * @param functionName
	 *        a name of function
	 */
	public MethodPointerExpression(final String functionName) {
		super("&" + functionName);
		this.functionName = functionName;
	}

	/**
	 * Returns the functionName.
	 * 
	 * @return the functionName
	 */
	public String getFunctionName() {
		return this.functionName;
	}
}
