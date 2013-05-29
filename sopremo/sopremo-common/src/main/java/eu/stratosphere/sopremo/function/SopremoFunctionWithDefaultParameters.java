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
package eu.stratosphere.sopremo.function;

import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * @author Arvid Heise
 */
public class SopremoFunctionWithDefaultParameters extends SopremoFunction {
	private final IArrayNode<IJsonNode> defaultParameters = new ArrayNode<IJsonNode>();

	private final SopremoFunction originalFunction;

	/**
	 * Initializes SopremoFunctionWithDefaultParameters.
	 * 
	 * @param name
	 * @param minimumNumberOfParameters
	 * @param maximumNumberOfParameters
	 */
	public SopremoFunctionWithDefaultParameters(SopremoFunction originalFunction, int minimumNumberOfParameters) {
		super(originalFunction.getName(), minimumNumberOfParameters, originalFunction.getMinimumNumberOfParameters());
		this.originalFunction = originalFunction;
	}

	/**
	 * Initializes SopremoFunctionWithDefaultParameters.
	 */
	SopremoFunctionWithDefaultParameters() {
		super("", 0, 0);
		this.originalFunction = null;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.function.Callable#call(java.lang.Object)
	 */
	@Override
	public IJsonNode call(IArrayNode<IJsonNode> params) {
		for (int index = params.size(); index < this.getMaximumNumberOfParameters(); index++)
			params.set(index, this.defaultParameters.get(index));
		return this.originalFunction.call(params);
	}

	public IJsonNode getDefaultParameter(int index) {
		return this.defaultParameters.get(index);
	}

	public void setDefaultParameter(int index, IJsonNode node) {
		this.defaultParameters.set(index, node);
	}

}
