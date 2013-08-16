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
package eu.stratosphere.sopremo.packages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * @author arv
 */
public class DefaultTypeRegistry extends AbstractSopremoType implements ITypeRegistry {
	private Map<String, Class<? extends IJsonNode>> elements = new HashMap<String, Class<? extends IJsonNode>>();

	private List<Class<? extends IJsonNode>> typeList = new ArrayList<Class<? extends IJsonNode>>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.stratosphere.sopremo.packages.IRegistry#get(java.lang.String)
	 */
	@Override
	public Class<? extends IJsonNode> get(String name) {
		return this.elements.get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.stratosphere.sopremo.packages.IRegistry#put(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void put(String name, Class<? extends IJsonNode> element) {
		this.elements.put(name, element);
		this.typeList.add(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.stratosphere.sopremo.packages.ITypeRegistry#put(java.lang.Class)
	 */
	@Override
	public void put(Class<? extends IJsonNode> type) {
		this.put(type.getName(), type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.stratosphere.sopremo.packages.IRegistry#keySet()
	 */
	@Override
	public Set<String> keySet() {
		return this.elements.keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.stratosphere.sopremo.ISopremoType#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		appendable.append("Registry: {");
		boolean first = true;
		for (final Class<? extends IJsonNode> type : this.typeList) {
			appendable.append(type.getName());
			if (first)
				first = false;
			else
				appendable.append(", ");
		}
		appendable.append("}");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.stratosphere.sopremo.packages.ITypeRegistry#getTypes()
	 */
	@Override
	public List<Class<? extends IJsonNode>> getTypes() {
		return this.typeList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
		result = prime * result + ((typeList == null) ? 0 : typeList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultTypeRegistry other = (DefaultTypeRegistry) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		if (typeList == null) {
			if (other.typeList != null)
				return false;
		} else if (!typeList.equals(other.typeList))
			return false;
		return true;
	}

}
