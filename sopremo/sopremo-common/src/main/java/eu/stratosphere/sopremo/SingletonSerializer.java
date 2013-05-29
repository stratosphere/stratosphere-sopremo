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

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * @author Arvid Heise
 */
@SuppressWarnings("rawtypes")
public abstract class SingletonSerializer extends Serializer {
	private final Object singleton;

	/**
	 * Initializes SingletonSerializer.
	 */
	public SingletonSerializer(Object singleton) {
		this.singleton = singleton;
		this.setImmutable(true);
	}

	@Override
	public void write(Kryo kryo, Output output, Object object) {
	}

	@Override
	public Object read(Kryo kryo, Input input, Class type) {
		return this.singleton;
	}
}