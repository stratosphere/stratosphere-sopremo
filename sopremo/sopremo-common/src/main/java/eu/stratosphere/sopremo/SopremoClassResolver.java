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

import com.esotericsoftware.kryo.ClassResolver;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * @author arv
 *
 */
public class SopremoClassResolver implements ClassResolver {
	

	/* (non-Javadoc)
	 * @see com.esotericsoftware.kryo.ClassResolver#setKryo(com.esotericsoftware.kryo.Kryo)
	 */
	@Override
	public void setKryo(Kryo kryo) {
	}

	/* (non-Javadoc)
	 * @see com.esotericsoftware.kryo.ClassResolver#register(com.esotericsoftware.kryo.Registration)
	 */
	@Override
	public Registration register(Registration registration) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.esotericsoftware.kryo.ClassResolver#registerImplicit(java.lang.Class)
	 */
	@Override
	public Registration registerImplicit(Class type) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.esotericsoftware.kryo.ClassResolver#getRegistration(java.lang.Class)
	 */
	@Override
	public Registration getRegistration(Class type) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.esotericsoftware.kryo.ClassResolver#getRegistration(int)
	 */
	@Override
	public Registration getRegistration(int classID) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.esotericsoftware.kryo.ClassResolver#writeClass(com.esotericsoftware.kryo.io.Output, java.lang.Class)
	 */
	@Override
	public Registration writeClass(Output output, Class type) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.esotericsoftware.kryo.ClassResolver#readClass(com.esotericsoftware.kryo.io.Input)
	 */
	@Override
	public Registration readClass(Input input) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.esotericsoftware.kryo.ClassResolver#reset()
	 */
	@Override
	public void reset() {
	}

}
