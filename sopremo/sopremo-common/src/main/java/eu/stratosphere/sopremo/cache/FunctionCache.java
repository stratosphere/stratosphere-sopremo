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
package eu.stratosphere.sopremo.cache;

import java.util.ArrayList;
import java.util.List;

import eu.stratosphere.sopremo.function.SopremoFunction;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.SubArrayNode;

/**
 * @author Arvid Heise
 */
public class FunctionCache implements ISopremoCache {
	private final SopremoFunction template;

	private final transient List<SopremoFunction> functions = new ArrayList<SopremoFunction>();

	private final transient IArrayNode<IJsonNode> cachedParams;

	private final transient SubArrayNode<IJsonNode> subParams = new SubArrayNode<IJsonNode>();

	public FunctionCache(SopremoFunction template) {
		this.template = template;
		this.cachedParams = new ArrayNode<IJsonNode>(template.getMaximumNumberOfParameters());
		this.subParams.init(this.cachedParams, 0);
	}

	/**
	 * Returns the template.
	 * 
	 * @return the template
	 */
	public SopremoFunction getTemplate() {
		return this.template;
	}

	public SopremoFunction get(int index) {
		while (index >= this.functions.size())
			this.functions.add(this.template.clone());
		return this.functions.get(index);
	}

	public IJsonNode call(int functionIndex, IJsonNode... params) {
		this.subParams.setSize(params.length);
		for (int index = 0; index < params.length; index++)
			this.cachedParams.set(index, params[index]);
		return this.get(functionIndex).call(this.cachedParams);
	}

	@Override
	public FunctionCache clone() {
		return new FunctionCache(this.template.clone());
	}
}
