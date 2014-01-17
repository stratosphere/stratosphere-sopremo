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

import static com.esotericsoftware.kryo.util.Util.className;

import org.objenesis.instantiator.ObjectInstantiator;

import com.esotericsoftware.kryo.ClassResolver;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ReferenceResolver;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.util.DefaultClassResolver;
import com.esotericsoftware.kryo.util.MapReferenceResolver;

import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IObjectNode;

/**
 * 
 */
class DataKryo extends Kryo {
	private ClassResolver classResolver;

	/** Creates a new Kryo with a {@link DefaultClassResolver} and a {@link MapReferenceResolver}. */
	public DataKryo() {
		this(new DefaultClassResolver(), new MapReferenceResolver());
	}

	/**
	 * Creates a new Kryo with a {@link DefaultClassResolver}.
	 * 
	 * @param referenceResolver
	 *        May be null to disable references.
	 */
	public DataKryo(ReferenceResolver referenceResolver) {
		this(new DefaultClassResolver(), referenceResolver);
	}

	/**
	 * @param referenceResolver
	 *        May be null to disable references.
	 */
	public DataKryo(ClassResolver classResolver, ReferenceResolver referenceResolver) {
		super(classResolver, referenceResolver);
		this.classResolver = classResolver;
	}

	public Registration registerAlias(Class<?> originalType, Class<?> alias) {
		Registration aliasRegistration = this.classResolver.getRegistration(alias);
		Registration newRegistration =
			new Registration(originalType, aliasRegistration.getSerializer(), aliasRegistration.getId());
		final ObjectInstantiator instantiator = aliasRegistration.getInstantiator();
		if (instantiator != null)
			newRegistration.setInstantiator(instantiator);
		this.classResolver.register(newRegistration);
		// overwrites id->type assiocation for deserialization
		this.classResolver.register(aliasRegistration);
		return newRegistration;
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.Kryo#getRegistration(java.lang.Class)
	 */
	@Override
	public Registration getRegistration(@SuppressWarnings("rawtypes") Class type) {
		Registration registration = this.classResolver.getRegistration(type);
		if (registration == null) {
			if (IArrayNode.class.isAssignableFrom(type))
				return registerAlias(type, IArrayNode.class);
			if (IObjectNode.class.isAssignableFrom(type))
				return registerAlias(type, IObjectNode.class);
			throw new IllegalArgumentException("Class is not registered: " + className(type)
				+ "\nNote: A class is automatically detected by Sopremo's package loader if it isn't tagged @Internal");
		}
		return registration;
	}
}
