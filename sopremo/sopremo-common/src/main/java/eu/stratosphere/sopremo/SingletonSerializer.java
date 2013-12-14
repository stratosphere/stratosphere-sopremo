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
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import eu.stratosphere.sopremo.type.ReusingSerializer;

/**
 * @author Arvid Heise
 */
@SuppressWarnings("rawtypes")
public abstract class SingletonSerializer extends ReusingSerializer {
	private final Object singleton;

	/**
	 * Initializes SingletonSerializer.
	 */
	public SingletonSerializer(final Object singleton) {
		this.singleton = singleton;
		this.setImmutable(true);
	}

	@Override
	public void write(final Kryo kryo, final Output output, final Object object) {
	}

	@Override
	public Object read(final Kryo kryo, final Input input, final Class type) {
		return this.singleton;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.ReusingSerializer#read(com.esotericsoftware.kryo.Kryo,
	 * com.esotericsoftware.kryo.io.Input, java.lang.Object, java.lang.Class)
	 */
	@Override
	public Object read(final Kryo kryo, final Input input, final Object oldInstance, final Class type) {
		return this.singleton;
	}
}