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
package eu.stratosphere.sopremo;

/**
 * @author Arvid Heise
 */
public class SopremoRuntime {
	/**
	 * 
	 */
	private static final ThreadLocal<SopremoRuntime> INSTANCE = new ThreadLocal<SopremoRuntime>() {
		protected SopremoRuntime initialValue() {
			return new SopremoRuntime();
		};
	};

	private EvaluationContext currentEvaluationContext = new EvaluationContext();

	private ClassLoader classLoader = getClass().getClassLoader();
	
	public static SopremoRuntime getInstance() {
		return INSTANCE.get();
	}

	/**
	 * Returns the currentEvaluationContext.
	 * 
	 * @return the currentEvaluationContext
	 */
	public EvaluationContext getCurrentEvaluationContext() {
		return this.currentEvaluationContext;
	}

	/**
	 * Sets the currentEvaluationContext to the specified value.
	 * 
	 * @param currentEvaluationContext
	 *        the currentEvaluationContext to set
	 */
	public void setCurrentEvaluationContext(EvaluationContext currentEvaluationContext) {
		if (currentEvaluationContext == null)
			throw new NullPointerException("currentEvaluationContext must not be null");

		this.currentEvaluationContext = currentEvaluationContext;
	}

	/**
	 * Returns the classLoader.
	 * 
	 * @return the classLoader
	 */
	public ClassLoader getClassLoader() {
		return this.classLoader;
	}
	
	/**
	 * Sets the classLoader to the specified value.
	 *
	 * @param classLoader the classLoader to set
	 */
	public void setClassLoader(ClassLoader classLoader) {
		if (classLoader == null)
			throw new NullPointerException("classLoader must not be null");

		this.classLoader = classLoader;
	}
}
