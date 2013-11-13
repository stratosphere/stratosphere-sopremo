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

import java.lang.reflect.AccessibleObject;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.operator.Name;

/**
 * @author arv
 * @param <T>
 */
public abstract class AbstractRegistry<T> extends AbstractSopremoType implements IRegistry<T> {
	protected final static NameChooser StandardNameChooser = new DefaultNameChooser(0, 1, 2, 3);
	
	private final NameChooser nameChooser;

	/**
	 * Initializes AbstractRegistry.
	 */
	public AbstractRegistry(NameChooser nameChooser) {
		if(nameChooser == null)
			throw new NullPointerException();
		this.nameChooser = nameChooser;
	}

	/**
	 * Initializes AbstractRegistry.
	 */
	protected AbstractRegistry() {
		this(StandardNameChooser);
	}

	@Override
	public void put(Name name, T element) {
		put(getName(name), element);
	}
	
	/* (non-Javadoc)
	 * @see eu.stratosphere.sopremo.packages.IRegistry#get(eu.stratosphere.sopremo.operator.Name)
	 */
	@Override
	public T get(Name name) {
		return get(getName(name));
	}

	@Override
	public String getName(Name nameAnnotation) {
		if(this.nameChooser == null)
			throw new IllegalStateException("This registry was initialized without NameChooser");
		return this.nameChooser.choose(nameAnnotation.noun(), nameAnnotation.verb(), nameAnnotation.adjective(),
			nameAnnotation.preposition());
	}

	protected String getName(Class<?> object) {
		final Name nameAnnotation = object.getAnnotation(Name.class);
		if (nameAnnotation == null)
			throw new IllegalArgumentException(object + " has no name annotation");
		return getName(nameAnnotation);
	}

	protected String getName(AccessibleObject object) {
		final Name nameAnnotation = object.getAnnotation(Name.class);
		if (nameAnnotation == null)
			throw new IllegalArgumentException(object + " has no name annotation");
		return getName(nameAnnotation);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.nameChooser.hashCode();
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
		AbstractRegistry<?> other = (AbstractRegistry<?>) obj;
		return this.nameChooser.equals(other.nameChooser);
	}

}