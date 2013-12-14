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

/**
 * @author arv
 */
public class DefaultNameChooserProvider implements NameChooserProvider {
	private NameChooser constantNameChooser, functionNameChooser, operatorNameChooser, propertyNameChooser,
			typeNameChooser, formatNameChooser;

	/**
	 * Initializes DefaultNameChooserProvider.
	 */
	public DefaultNameChooserProvider() {
		this(new DefaultNameChooser());
	}

	/**
	 * Initializes DefaultNameChooserProvider.
	 */
	public DefaultNameChooserProvider(final NameChooser defaultNameChooser) {
		this.constantNameChooser =
			this.functionNameChooser =
				this.operatorNameChooser =
					this.propertyNameChooser =
						this.typeNameChooser =
							this.formatNameChooser = defaultNameChooser;
	}

	@Override
	public NameChooser getConstantNameChooser() {
		return this.constantNameChooser;
	}

	@Override
	public NameChooser getFormatNameChooser() {
		return this.formatNameChooser;
	}

	@Override
	public NameChooser getFunctionNameChooser() {
		return this.functionNameChooser;
	}

	@Override
	public NameChooser getOperatorNameChooser() {
		return this.operatorNameChooser;
	}

	@Override
	public NameChooser getPropertyNameChooser() {
		return this.propertyNameChooser;
	}

	@Override
	public NameChooser getTypeNameChooser() {
		return this.typeNameChooser;
	}

	public void setConstantNameChooser(final NameChooser constantNameChooser) {
		if (constantNameChooser == null)
			throw new NullPointerException("constantNameChooser must not be null");

		this.constantNameChooser = constantNameChooser;
	}

	public void setFormatNameChooser(final NameChooser formatNameChooser) {
		if (formatNameChooser == null)
			throw new NullPointerException("formatNameChooser must not be null");

		this.formatNameChooser = formatNameChooser;
	}

	public void setFunctionNameChooser(final NameChooser functionNameChooser) {
		if (functionNameChooser == null)
			throw new NullPointerException("functionNameChooser must not be null");

		this.functionNameChooser = functionNameChooser;
	}

	public void setOperatorNameChooser(final NameChooser operatorNameChooser) {
		if (operatorNameChooser == null)
			throw new NullPointerException("operatorNameChooser must not be null");

		this.operatorNameChooser = operatorNameChooser;
	}

	public void setPropertyNameChooser(final NameChooser propertyNameChooser) {
		if (propertyNameChooser == null)
			throw new NullPointerException("propertyNameChooser must not be null");

		this.propertyNameChooser = propertyNameChooser;
	}

	public void setTypeNameChooser(final NameChooser typeNameChooser) {
		if (typeNameChooser == null)
			throw new NullPointerException("typeNameChooser must not be null");

		this.typeNameChooser = typeNameChooser;
	}

}
