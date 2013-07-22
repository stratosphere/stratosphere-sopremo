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
package eu.stratosphere.sopremo.operator;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.FeatureDescriptor;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import eu.stratosphere.sopremo.AbstractSopremoType;

/**
 * Provides the basic mechanism for exposing configuration parameters of objects through the Java Bean specification.<br />
 * Subclasses must provide a no-arg ctor and can add an arbitrary number of properties by adding the {@link Property}
 * annotation to the respective setter and/or getter.<br />
 * It is recommended to add {@link Name} annotation to the type and the properties.
 */
public abstract class ConfigurableSopremoType extends AbstractSopremoType implements BeanInfo {

	private static Map<Class<?>, Info> beanInfos = new IdentityHashMap<Class<?>, Info>();

	/**
	 * Initializes ConfigurableSopremoType.
	 */
	public ConfigurableSopremoType() {
		super();
	}

	@Override
	public BeanInfo[] getAdditionalBeanInfo() {
		return this.getBeanInfo().getAdditionalBeanInfo();
	}

	@Override
	public BeanDescriptor getBeanDescriptor() {
		return this.getBeanInfo().getBeanDescriptor();
	}

	@Override
	public int getDefaultEventIndex() {
		return this.getBeanInfo().getDefaultEventIndex();
	}

	@Override
	public int getDefaultPropertyIndex() {
		return this.getBeanInfo().getDefaultPropertyIndex();
	}

	@Override
	public EventSetDescriptor[] getEventSetDescriptors() {
		return this.getBeanInfo().getEventSetDescriptors();
	}

	@Override
	public Image getIcon(final int iconKind) {
		return this.getBeanInfo().getIcon(iconKind);
	}

	@Override
	public MethodDescriptor[] getMethodDescriptors() {
		return this.getBeanInfo().getMethodDescriptors();
	}

	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		return this.getBeanInfo().getPropertyDescriptors();
	}

	protected Info getBeanInfo() {
		Info beanInfo = beanInfos.get(this.getClass());
		if (beanInfo == null)
			beanInfos.put(this.getClass(), beanInfo = new Info(this.getClass()));
		return beanInfo;
	}

	public static class Info extends SimpleBeanInfo {
		public static final String NAME_PREPOSITION = "name.preposition";

		public static final String NAME_ADJECTIVE = "name.adjective";

		public static final String NAME_VERB = "name.verb";

		public static final String NAME_NOUNS = "name.nouns";

		public static final String INPUT = "flag.input";

		private final BeanDescriptor classDescriptor;

		private PropertyDescriptor[] properties;

		public Info(final Class<?> clazz) {
			this.classDescriptor = new BeanDescriptor(clazz);
			this.setNames(this.classDescriptor, clazz.getAnnotation(Name.class));

			this.findProperties(clazz);
		}

		@Override
		public BeanDescriptor getBeanDescriptor() {
			return this.classDescriptor;
		}

		@Override
		public PropertyDescriptor[] getPropertyDescriptors() {
			return this.properties;
		}

		private void findProperties(final Class<?> clazz) {
			final List<PropertyDescriptor> properties = new ArrayList<PropertyDescriptor>();
			try {
				for (final PropertyDescriptor descriptor : Introspector.getBeanInfo(clazz, 0).getPropertyDescriptors()) {
					final Method writeMethod = descriptor instanceof IndexedPropertyDescriptor ?
						((IndexedPropertyDescriptor) descriptor).getIndexedWriteMethod() :
						descriptor.getWriteMethod();
					Property propertyDescription;
					if (writeMethod != null
						&& (propertyDescription = writeMethod.getAnnotation(Property.class)) != null) {
						descriptor.setHidden(propertyDescription.hidden());
						properties.add(descriptor);

						descriptor.setValue(INPUT, propertyDescription.input());
						this.setNames(descriptor, writeMethod.getAnnotation(Name.class));
					}
				}
			} catch (final IntrospectionException e) {
				e.printStackTrace();
			}
			this.properties = properties.toArray(new PropertyDescriptor[properties.size()]);
		}

		private void setNames(final FeatureDescriptor description, final Name annotation) {
			if (annotation != null) {
				description.setValue(NAME_NOUNS, annotation.noun());
				description.setValue(NAME_VERB, annotation.verb());
				description.setValue(NAME_ADJECTIVE, annotation.adjective());
				description.setValue(NAME_PREPOSITION, annotation.preposition());
			} else {
				final String[] empty = new String[0];
				description.setValue(NAME_NOUNS, empty);
				description.setValue(NAME_VERB, empty);
				description.setValue(NAME_ADJECTIVE, empty);
				description.setValue(NAME_PREPOSITION, empty);
			}
		}
	}

}