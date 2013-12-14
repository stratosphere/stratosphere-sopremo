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
package eu.stratosphere.sopremo.query;

import eu.stratosphere.sopremo.io.SopremoFormat;
import eu.stratosphere.sopremo.operator.ConfigurableSopremoType;

/**
 * @author arv
 */
@SuppressWarnings("unchecked")
public interface AdditionalInfoResolver {
	public <T extends ConfigurableSopremoType> ConfObjectInfo<T> get(T object);

	public static class None implements AdditionalInfoResolver {
		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.query.AdditionalInfoResolver#get(eu.stratosphere.sopremo.operator.ConfigurableSopremoType
		 * )
		 */
		@Override
		public <T extends ConfigurableSopremoType> ConfObjectInfo<T> get(final T object) {
			return null;
		}
	}

	public static class Format implements AdditionalInfoResolver {
		private final IConfObjectRegistry<?> formatRegistry;

		public Format(final IConfObjectRegistry<?> formatRegistry) {
			this.formatRegistry = formatRegistry;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.query.AdditionalInfoResolver#get(eu.stratosphere.sopremo.operator.ConfigurableSopremoType
		 * )
		 */
		@Override
		public <T extends ConfigurableSopremoType> ConfObjectInfo<T> get(final T object) {
			return (ConfObjectInfo<T>) this.formatRegistry.get(object.getClass());
		}
	}

	public static class OperatorOrFormat implements AdditionalInfoResolver {
		private final IConfObjectRegistry<?> operatorRegistry, formatRegistry;

		public OperatorOrFormat(final IConfObjectRegistry<?> operatorRegistry,
				final IConfObjectRegistry<?> formatRegistry) {
			this.operatorRegistry = operatorRegistry;
			this.formatRegistry = formatRegistry;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.query.AdditionalInfoResolver#get(eu.stratosphere.sopremo.operator.ConfigurableSopremoType
		 * )
		 */
		@Override
		public <T extends ConfigurableSopremoType> ConfObjectInfo<T> get(final T object) {
			final Class<? extends ConfigurableSopremoType> clazz = object.getClass();
			if (SopremoFormat.class.isAssignableFrom(clazz))
				return (ConfObjectInfo<T>) this.formatRegistry.get(clazz);
			return (ConfObjectInfo<T>) this.operatorRegistry.get(clazz);
		}
	}
}
