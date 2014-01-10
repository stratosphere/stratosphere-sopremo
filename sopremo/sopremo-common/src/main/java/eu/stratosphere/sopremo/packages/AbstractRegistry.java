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
import java.lang.reflect.Member;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.operator.Internal;
import eu.stratosphere.sopremo.operator.Name;

/**
 * @param <T>
 */
public abstract class AbstractRegistry<T> extends AbstractSopremoType implements IRegistry<T> {
	protected final static NameChooser StandardNameChooser = new DefaultNameChooser(0, 1, 2, 3);

	private final NameChooser nameChooser;

	/**
	 * Initializes AbstractRegistry.
	 */
	public AbstractRegistry(final NameChooser nameChooser) {
		if (nameChooser == null)
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
	public void put(final Name nameAnnotation, final T element) {
		final String[] names = this.getNameChooser().getNames(nameAnnotation);
		for (final String name : names)
			this.put(name, element);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.packages.IRegistry#get(eu.stratosphere.sopremo.operator.Name)
	 */
	@Override
	public T get(final Name nameAnnotation) {
		return this.get(this.getNameChooser().getNames(nameAnnotation)[0]);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.packages.IRegistry#getNameChooser()
	 */
	@Override
	public NameChooser getNameChooser() {
		return this.nameChooser;
	}

	protected String[] getNames(final Class<?> object) {
		final Name nameAnnotation = object.getAnnotation(Name.class);
		if (nameAnnotation == null) {
			if (object.getAnnotation(Internal.class) != null)
				return new String[] { String.format("__%s", object.getSimpleName()) };
			throw new IllegalArgumentException(object + " has no name annotation");
		}
		return this.getNameChooser().getNames(nameAnnotation);
	}

	protected String[] getNames(final AccessibleObject object) {
		final Name nameAnnotation = object.getAnnotation(Name.class);
		if (nameAnnotation == null) {
			if (object.getAnnotation(Internal.class) != null)
				return new String[] { String.format("__%s", ((Member) object).getName()) };
			throw new IllegalArgumentException(object + " has no name annotation");
		}
		return this.getNameChooser().getNames(nameAnnotation);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.nameChooser.hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final AbstractRegistry<?> other = (AbstractRegistry<?>) obj;
		return this.nameChooser.equals(other.nameChooser);
	}

}