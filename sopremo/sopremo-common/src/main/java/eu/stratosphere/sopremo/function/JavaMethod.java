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

import java.lang.reflect.Method;
import java.util.Collection;

import eu.stratosphere.sopremo.EvaluationException;
import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.cache.ArrayCache;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.util.reflect.DynamicMethod;
import eu.stratosphere.util.reflect.Signature;

/**
 * @author Arvid Heise
 */
public class JavaMethod extends SopremoFunction {

	protected final DynamicMethod<IJsonNode> method;

	private final transient ArrayCache<IJsonNode> arrayCache = new ArrayCache<IJsonNode>(IJsonNode.class);

	/**
	 * Initializes JavaMethod.
	 */
	public JavaMethod(final String name) {
		super(name, 0, Integer.MAX_VALUE);
		this.method = new DynamicMethod<IJsonNode>(name);
	}

	/**
	 * Initializes JavaMethod.
	 */
	JavaMethod() {
		this.method = new DynamicMethod<IJsonNode>("");
	}

	public void addSignature(final Method method) {
		this.method.addSignature(method);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.function.Callable#call(java.lang.Object)
	 */
	@Override
	public IJsonNode call(IArrayNode<IJsonNode> params) throws EvaluationException {
		try {
			return this.method.invoke(null, (Object[]) params.toArray(this.arrayCache));
		} catch (Throwable e) {
			throw new EvaluationException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#copyPropertiesFrom(eu.stratosphere.sopremo.AbstractSopremoType)
	 */
	@Override
	public void copyPropertiesFrom(ISopremoType original) {
		super.copyPropertiesFrom(original);
		DynamicMethod<?> method = ((JavaMethod) original).method;
		for (Signature signature : method.getSignatures())
			this.addSignature(method.getMethod(signature));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		JavaMethod other = (JavaMethod) obj;
		return this.method.equals(other.method);
	}

	public Collection<Signature> getSignatures() {
		return this.method.getSignatures();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.method.hashCode();
		return result;
	}
}