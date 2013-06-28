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
package eu.stratosphere.sopremo.serialization;

import eu.stratosphere.sopremo.type.CachingArrayNode;
import eu.stratosphere.sopremo.type.DefaultNodeFactory;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.ObjectNode;

/**
 * @author arv
 */
public class CachingNodeFactory extends DefaultNodeFactory {
	private final static CachingNodeFactory Instance = (CachingNodeFactory) new CachingNodeFactory().
		register(IArrayNode.class, CachingArrayNode.class).
		register(IObjectNode.class, ObjectNode.class);

	/**
	 * Returns the instance.
	 * 
	 * @return the instance
	 */
	public static CachingNodeFactory getInstance() {
		return Instance;
	}

	/**
	 * Initializes CachingNodeFactory.
	 */
	private CachingNodeFactory() {
	}
}
