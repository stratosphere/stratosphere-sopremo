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
package eu.stratosphere.sopremo.function;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.ISopremoType;

/**
 * @author Arvid Heise
 */
public abstract class Callable<Result, InputType> extends AbstractSopremoType implements ISopremoType {

	private final int minimumNumberOfParameters, maximumNumberOfParameters;

	public Callable(int minimumNumberOfParameters, int maximumNumberOfParameters) {
		this.minimumNumberOfParameters = minimumNumberOfParameters;
		this.maximumNumberOfParameters = maximumNumberOfParameters;
	}

	/**
	 * Returns true if the function can be called with the given number of parameters.
	 */
	public boolean accepts(int numberOfArguments) {
		return this.minimumNumberOfParameters <= numberOfArguments &&
			numberOfArguments <= this.maximumNumberOfParameters;
	}

	public abstract Result call(InputType params);

	/**
	 * Returns the maximumNumberOfParameters.
	 * 
	 * @return the maximumNumberOfParameters
	 */
	public int getMaximumNumberOfParameters() {
		return this.maximumNumberOfParameters;
	}

	/**
	 * Returns the minimumNumberOfParameters.
	 * 
	 * @return the minimumNumberOfParameters
	 */
	public int getMinimumNumberOfParameters() {
		return this.minimumNumberOfParameters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.maximumNumberOfParameters;
		result = prime * result + this.minimumNumberOfParameters;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Callable<?, ?> other = (Callable<?, ?>) obj;
		return this.maximumNumberOfParameters == other.maximumNumberOfParameters &&
			this.minimumNumberOfParameters == other.minimumNumberOfParameters;
	}
}
