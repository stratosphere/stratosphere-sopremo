/***********************************************************************************************************************
 *
 * Copyright (C) 2010-2013 by the Stratosphere project (http://stratosphere.eu)
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

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.operator.ConfigurableSopremoType;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.packages.DefaultRegistry;
import eu.stratosphere.sopremo.packages.IRegistry;
import eu.stratosphere.sopremo.packages.NameChooser;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.JsonToJavaMapper;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.util.reflect.ReflectUtil;

public class ConfObjectInfo<Obj extends ConfigurableSopremoType> extends AbstractSopremoType implements ISopremoType {

	public static class ConfObjectIndexedPropertyInfo extends PropertyInfo {

		/**
		 * Initializes OperatorPropertyInfo.
		 * 
		 * @param name
		 * @param descriptor
		 */
		protected ConfObjectIndexedPropertyInfo(final AdditionalInfoResolver additionalInfoResolver, final String name,
				final IndexedPropertyDescriptor descriptor) {
			super(additionalInfoResolver, name, descriptor);
		}

		@Override
		public IndexedPropertyDescriptor getDescriptor() {
			return (IndexedPropertyDescriptor) super.getDescriptor();
		}

		public Object getValue(final ConfigurableSopremoType operator, final int index) {
			final ConfigurableSopremoType delegate = this.getDelegate(operator);
			if (delegate != operator)
				return this.getValue(delegate, index);
			try {
				return this.getDescriptor().getIndexedReadMethod().invoke(operator, new Object[] { index });
			} catch (final Exception e) {
				throw new RuntimeException(
					String.format("Could not get property %s of %s", this.getName(), operator), e);
			}
		}

		public void setValue(final ConfigurableSopremoType operator, final int index, final Object value) {
			final ConfigurableSopremoType delegate = this.getDelegate(operator);
			if (delegate != operator) {
				this.setValue(delegate, index, value);
				return;
			}
			try {
				this.getDescriptor().getIndexedWriteMethod().invoke(operator, index, value);
			} catch (final Exception e) {
				throw new RuntimeException(
					String.format("Could not set property %s of %s to %s", this.getName(), operator, value), e);
			}
		}

		public void setValue(final ConfigurableSopremoType operator, final int index,
				final EvaluationExpression expression) {
			final ConfigurableSopremoType delegate = this.getDelegate(operator);
			if (delegate != operator) {
				this.setValue(delegate, index, expression);
				return;
			}
			try {
				this.getDescriptor().getIndexedWriteMethod()
					.invoke(operator, index, coerce(expression, this.getDescriptor().getIndexedPropertyType()));
			} catch (final Exception e) {
				throw new RuntimeException(
					String.format("Could not set property %s of %s to %s", this.getName(), operator, expression), e);
			}
		}

	}

	public static class ConfObjectPropertyInfo extends PropertyInfo {

		/**
		 * Initializes OperatorPropertyInfo.
		 * 
		 * @param name
		 * @param descriptor
		 */
		public ConfObjectPropertyInfo(final AdditionalInfoResolver additionalInfoResolver, final String name,
				final PropertyDescriptor descriptor) {
			super(additionalInfoResolver, name, descriptor);
		}

		public Object getValue(final ConfigurableSopremoType operator) {
			final ConfigurableSopremoType delegate = this.getDelegate(operator);
			if (delegate != operator)
				return this.getValue(delegate);
			try {
				return this.getDescriptor().getReadMethod().invoke(operator, new Object[0]);
			} catch (final Exception e) {
				throw new RuntimeException(
					String.format("Could not get property %s of %s", this.getName(), operator), e);
			}
		}

		public void setValue(final ConfigurableSopremoType operator, final Object value) {
			final ConfigurableSopremoType delegate = this.getDelegate(operator);
			if (delegate != operator) {
				this.setValue(delegate, value);
				return;
			}
			try {
				this.getDescriptor().getWriteMethod().invoke(operator, value);
			} catch (final Exception e) {
				throw new RuntimeException(
					String.format("Could not set property %s of %s to %s", this.getName(), operator, value), e);
			}
		}

		public void setValue(final ConfigurableSopremoType operator, final EvaluationExpression expression) {
			final ConfigurableSopremoType delegate = this.getDelegate(operator);
			if (delegate != operator) {
				this.setValue(delegate, expression);
				return;
			}
			try {
				this.getDescriptor().getWriteMethod()
					.invoke(operator, coerce(expression, this.getDescriptor().getPropertyType()));
			} catch (final Exception e) {
				throw new RuntimeException(
					String.format("Could not set property '%s' of '%s' to: %s", this.getName(), operator, expression),
					e);
			}
		}

	}

	public abstract static class PropertyInfo extends AbstractSopremoType implements ISopremoType {
		private final String name;

		private final PropertyDescriptor descriptor;

		private final AdditionalInfoResolver additionalInfoResolver;

		public PropertyInfo(final AdditionalInfoResolver additionalInfoResolver, final String name,
				final PropertyDescriptor descriptor) {
			this.additionalInfoResolver = additionalInfoResolver;
			this.name = name;
			this.descriptor = descriptor;
		}

		/**
		 * Returns the descriptor.
		 * 
		 * @return the descriptor
		 */
		public PropertyDescriptor getDescriptor() {
			return this.descriptor;
		}

		public boolean isFlag() {
			return this.getType() == Boolean.TYPE;
		}

		/**
		 * Returns the name.
		 * 
		 * @return the name
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * @return
		 */
		public Class<?> getType() {
			return this.descriptor.getPropertyType();
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.ISopremoType#appendAsString(java.lang.Appendable)
		 */
		@Override
		public void appendAsString(final Appendable appendable) throws IOException {
			appendable.append(this.name);
		}

		protected ConfigurableSopremoType getDelegate(final ConfigurableSopremoType operator) {
			final ConfigurableSopremoType[] additionalBeanInfo = operator.getAdditionalBeanInfo();
			if (additionalBeanInfo != null)
				for (final ConfigurableSopremoType delegate : additionalBeanInfo) {
					final ConfObjectInfo<?> info = this.additionalInfoResolver.get(delegate);
					if (info.getOperatorPropertyRegistry(delegate).get(this.name) == this)
						return this.getDelegate(delegate);
				}
			return operator;
		}
	}

	private final Class<Obj> operatorClass;

	private final String name;

	private final NameChooser propertyNameChooser;

	private final IRegistry<ConfObjectPropertyInfo> operatorPropertyRegistry =
		new DefaultRegistry<ConfObjectPropertyInfo>();

	private final IRegistry<ConfObjectIndexedPropertyInfo> inputPropertyRegistry =
		new DefaultRegistry<ConfObjectIndexedPropertyInfo>();

	private final BeanInfo beanInfo;

	private final AdditionalInfoResolver additionalInfoResolver;

	public ConfObjectInfo(final AdditionalInfoResolver additionalInfoResolver, final NameChooser propertyNameChooser,
			final Class<Obj> operatorClass, final String name) {
		this.operatorClass = operatorClass;
		try {
			this.beanInfo = Introspector.getBeanInfo(this.operatorClass);
		} catch (final IntrospectionException e) {
			throw new RuntimeException(e);
		}
		this.name = name;
		this.propertyNameChooser = propertyNameChooser;
		this.additionalInfoResolver = additionalInfoResolver;
	}

	public static Object coerce(final EvaluationExpression expression, final Class<?> propertyType) {
		if (EvaluationExpression.class.isAssignableFrom(propertyType))
			return expression;
		final IJsonNode constantExpressionResult = expression.evaluate(MissingNode.getInstance());
		return JsonToJavaMapper.INSTANCE.map(constantExpressionResult, null, propertyType);
	}

	private void initProperties() {
		final PropertyDescriptor[] propertyDescriptors = this.beanInfo.getPropertyDescriptors();
		for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			final Name nameAnnotation = (Name) propertyDescriptor.getValue(ConfigurableSopremoType.Info.NAME);
			String[] names;
			if (nameAnnotation == null || (names = this.propertyNameChooser.getNames(nameAnnotation)) == null)
				names = new String[] { propertyDescriptor.getName() };

			if (propertyDescriptor.getValue(ConfigurableSopremoType.Info.INPUT) == Boolean.TRUE)
				for (final String name : names)
					this.inputPropertyRegistry.put(name, new ConfObjectIndexedPropertyInfo(this.additionalInfoResolver,
						name,
						(IndexedPropertyDescriptor) propertyDescriptor));
			else
				for (final String name : names)
					this.operatorPropertyRegistry.put(name, new ConfObjectPropertyInfo(this.additionalInfoResolver,
						name,
						propertyDescriptor));
		}
	}

	private final AtomicBoolean needsInitialization = new AtomicBoolean(true);

	protected boolean needsInitialization() {
		return this.needsInitialization.getAndSet(false);
	}

	public IRegistry<ConfObjectIndexedPropertyInfo> getInputPropertyRegistry(final ConfigurableSopremoType object) {
		if (this.needsInitialization())
			this.initProperties();
		if (object != null) {
			final ConfigurableSopremoType[] additionalBeanInfos = object.getAdditionalBeanInfo();
			if (additionalBeanInfos != null && additionalBeanInfos.length > 0) {
				final StackedRegistry<ConfObjectIndexedPropertyInfo, IRegistry<ConfObjectIndexedPropertyInfo>> stackedRegistry =
					new StackedRegistry<ConfObjectIndexedPropertyInfo, IRegistry<ConfObjectIndexedPropertyInfo>>(
						this.inputPropertyRegistry);
				for (final ConfigurableSopremoType beanInfo : additionalBeanInfos) {
					final ConfObjectInfo<?> additionalConfInfo = this.additionalInfoResolver.get(beanInfo);
					stackedRegistry.push(additionalConfInfo.getInputPropertyRegistry(beanInfo));
				}
				return stackedRegistry;
			}
		}
		return this.inputPropertyRegistry;
	}

	/**
	 * Returns the operatorClass.
	 * 
	 * @return the operatorClass
	 */
	public Class<Obj> getOperatorClass() {
		return this.operatorClass;
	}

	public String getName() {
		return this.name;
	}

	public IRegistry<ConfObjectPropertyInfo> getOperatorPropertyRegistry(final ConfigurableSopremoType object) {
		if (this.needsInitialization())
			this.initProperties();
		if (object != null) {
			final ConfigurableSopremoType[] additionalBeanInfos = object.getAdditionalBeanInfo();
			if (additionalBeanInfos != null && additionalBeanInfos.length > 0) {
				final StackedRegistry<ConfObjectPropertyInfo, IRegistry<ConfObjectPropertyInfo>> stackedRegistry =
					new StackedRegistry<ConfObjectPropertyInfo, IRegistry<ConfObjectPropertyInfo>>(
						this.operatorPropertyRegistry);
				for (final ConfigurableSopremoType beanInfo : additionalBeanInfos) {
					final ConfObjectInfo<?> additionalConfInfo = this.additionalInfoResolver.get(beanInfo);
					stackedRegistry.push(additionalConfInfo.getOperatorPropertyRegistry(beanInfo));
				}
				return stackedRegistry;
			}
		}
		return this.operatorPropertyRegistry;
	}

	public Obj newInstance() {
		return ReflectUtil.newInstance(this.operatorClass);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append(this.name);
	}
}