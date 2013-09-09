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
package eu.stratosphere.sopremo.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import com.google.common.reflect.TypeToken;

public abstract class TypeMapper<From, To> {
	private final Class<? extends To> defaultType;

	private final Class<?> toType;

	public TypeMapper(final Class<? extends To> defaultType) {
		this.defaultType = defaultType;
		final Type toBinding =
			((ParameterizedType) TypeToken.of(getClass()).getSupertype(TypeMapper.class).getType()).getActualTypeArguments()[1];
		this.toType = TypeToken.of(toBinding).getRawType();
	}

	public abstract To mapTo(From from, To target);

	/**
	 * Returns the resultType.
	 * 
	 * @return the resultType
	 */
	public Class<? extends To> getDefaultType() {
		return this.defaultType;
	}

	public boolean isValidTarget(Object possibleTarget) {
		return this.toType.isInstance(possibleTarget);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		ParameterizedType type = (ParameterizedType) TypeToken.of(getClass()).getSupertype(TypeMapper.class).getType();
		return "TypeMapper " + Arrays.asList(type.getActualTypeArguments());
	}
}