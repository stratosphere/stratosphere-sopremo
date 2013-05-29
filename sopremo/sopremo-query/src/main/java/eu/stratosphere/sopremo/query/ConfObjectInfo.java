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
import eu.stratosphere.sopremo.packages.DefaultRegistry;
import eu.stratosphere.sopremo.packages.IRegistry;
import eu.stratosphere.sopremo.packages.NameChooser;
import eu.stratosphere.sopremo.type.IJsonNode;
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
		protected ConfObjectIndexedPropertyInfo(final String name, final IndexedPropertyDescriptor descriptor) {
			super(name, descriptor);
		}

		@Override
		public IndexedPropertyDescriptor getDescriptor() {
			return (IndexedPropertyDescriptor) super.getDescriptor();
		}

		public Object getValue(final ConfigurableSopremoType operator, final int index) {
			try {
				return this.getDescriptor().getIndexedReadMethod().invoke(operator, new Object[] { index });
			} catch (final Exception e) {
				throw new RuntimeException(
					String.format("Could not get property %s of %s", this.getName(), operator), e);
			}
		}

		public void setValue(final ConfigurableSopremoType operator, final int index, final Object value) {
			try {
				this.getDescriptor().getIndexedWriteMethod().invoke(operator, index, value);
			} catch (final Exception e) {
				throw new RuntimeException(
					String.format("Could not set property %s of %s to %s", this.getName(), operator, value), e);
			}
		}

		public void setValue(final ConfigurableSopremoType operator, final int index,
				final EvaluationExpression expression) {
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
		public ConfObjectPropertyInfo(final String name, final PropertyDescriptor descriptor) {
			super(name, descriptor);
		}

		public Object getValue(final ConfigurableSopremoType operator) {
			try {
				return this.getDescriptor().getReadMethod().invoke(operator, new Object[0]);
			} catch (final Exception e) {
				throw new RuntimeException(
					String.format("Could not get property %s of %s", this.getName(), operator), e);
			}
		}

		public void setValue(final ConfigurableSopremoType operator, final Object value) {
			try {
				this.getDescriptor().getWriteMethod().invoke(operator, value);
			} catch (final Exception e) {
				throw new RuntimeException(
					String.format("Could not set property %s of %s to %s", this.getName(), operator, value), e);
			}
		}

		public void setValue(final ConfigurableSopremoType operator, final EvaluationExpression expression) {
			try {
				this.getDescriptor().getWriteMethod()
					.invoke(operator, coerce(expression, this.getDescriptor().getPropertyType()));
			} catch (final Exception e) {
				throw new RuntimeException(
					String.format("Could not set property %s of %s to %s", this.getName(), operator, expression), e);
			}
		}

	}

	public abstract static class PropertyInfo extends AbstractSopremoType implements ISopremoType {
		private final String name;

		private final PropertyDescriptor descriptor;

		public PropertyInfo(final String name, final PropertyDescriptor descriptor) {
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
		public void appendAsString(Appendable appendable) throws IOException {
			appendable.append(this.name);
		}
	}

	private Class<Obj> operatorClass;

	private final String name;

	private final NameChooser propertyNameChooser;

	private final IRegistry<ConfObjectPropertyInfo> operatorPropertyRegistry =
		new DefaultRegistry<ConfObjectPropertyInfo>();

	private final IRegistry<ConfObjectIndexedPropertyInfo> inputPropertyRegistry = new DefaultRegistry<ConfObjectIndexedPropertyInfo>();

	public ConfObjectInfo(final Class<Obj> operatorClass, final String opName,
			final NameChooser propertyNameChooser) {
		this.operatorClass = operatorClass;
		this.name = opName;
		this.propertyNameChooser = propertyNameChooser;
	}

	public static Object coerce(EvaluationExpression expression, Class<?> propertyType) {
		if (EvaluationExpression.class.isAssignableFrom(propertyType))
			return expression;
		final IJsonNode constantExpressionResult = expression.evaluate(MissingNode.getInstance());
		return JsonToJavaMapper.INSTANCE.treeToValue(constantExpressionResult, propertyType);
	}

	private void initProperties() {
		try {
			final PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(this.operatorClass)
				.getPropertyDescriptors();
			for (final PropertyDescriptor propertyDescriptor : propertyDescriptors)
				if (propertyDescriptor.getWriteMethod() != null
					|| propertyDescriptor instanceof IndexedPropertyDescriptor &&
					((IndexedPropertyDescriptor) propertyDescriptor).getIndexedWriteMethod() != null) {
					String name = this.propertyNameChooser.choose(
						(String[]) propertyDescriptor.getValue(ConfigurableSopremoType.Info.NAME_NOUNS),
						(String[]) propertyDescriptor.getValue(ConfigurableSopremoType.Info.NAME_VERB),
						(String[]) propertyDescriptor.getValue(ConfigurableSopremoType.Info.NAME_ADJECTIVE),
						(String[]) propertyDescriptor.getValue(ConfigurableSopremoType.Info.NAME_PREPOSITION));
					if (name == null)
						name = propertyDescriptor.getName();

					if (propertyDescriptor.getValue(ConfigurableSopremoType.Info.INPUT) == Boolean.TRUE)
						this.inputPropertyRegistry.put(name, new ConfObjectIndexedPropertyInfo(name,
							(IndexedPropertyDescriptor) propertyDescriptor));
					else
						this.operatorPropertyRegistry.put(name, new ConfObjectPropertyInfo(name, propertyDescriptor));
				}
		} catch (final IntrospectionException e) {
			e.printStackTrace();
		}
	}

	private AtomicBoolean needsInitialization = new AtomicBoolean(true);

	protected boolean needsInitialization() {
		return this.needsInitialization.getAndSet(false);
	}

	public IRegistry<ConfObjectIndexedPropertyInfo> getInputPropertyRegistry() {
		if (this.needsInitialization())
			this.initProperties();
		return this.inputPropertyRegistry;
	}

	public String getName() {
		return this.name;
	}

	public IRegistry<ConfObjectPropertyInfo> getOperatorPropertyRegistry() {
		if (this.needsInitialization())
			this.initProperties();
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
	public void appendAsString(Appendable appendable) throws IOException {
		appendable.append(this.name);
	}
}