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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;

import eu.stratosphere.core.fs.Path;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.packages.DefaultNameChooserProvider;
import eu.stratosphere.sopremo.packages.NameChooserProvider;
import eu.stratosphere.util.SopremoKryo;

/**
 */
public class EvaluationContext extends AbstractSopremoType {

	private String workingPath;

	private String operatorDescription;

	private EvaluationExpression resultProjection = EvaluationExpression.VALUE;

	private final NameChooserProvider nameChooserProvider;

	// public LinkedList<Operator<?>> getOperatorStack() {
	// return this.operatorStack;
	// }

	private int taskId;

	private final transient Kryo kryo;

	private final Map<String, Object> contextParameters = new HashMap<String, Object>();

	/**
	 * Initializes EvaluationContext.
	 */
	public EvaluationContext() {
		this(new DefaultNameChooserProvider());
	}

	/**
	 * Initializes EvaluationContext.
	 */
	public EvaluationContext(final NameChooserProvider nameChooserProvider) {
		this.nameChooserProvider = nameChooserProvider;

		this.workingPath = new Path(new File(".").toURI().toString()).toString();

		this.kryo = new SopremoKryo();
	}

	/**
	 * Initializes EvaluationContext.
	 */
	protected EvaluationContext(final EvaluationContext context) {
		this(context.nameChooserProvider);
		this.contextParameters.putAll(context.contextParameters);
		this.copyPropertiesFrom(context);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.SopremoType#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append("Context @ ").append(this.operatorDescription).append("\n");
		appendable.append("\nParameters: ");
		appendable.append(this.contextParameters.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#clone()
	 */
	@Override
	public EvaluationContext clone() {
		return (EvaluationContext) super.clone();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#copyPropertiesFrom(eu.
	 * stratosphere.sopremo.AbstractSopremoType)
	 */
	public void copyPropertiesFrom(final ISopremoType original) {
		final EvaluationContext context = (EvaluationContext) original;
		this.resultProjection = context.resultProjection.clone();
		this.operatorDescription = context.operatorDescription;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final EvaluationContext other = (EvaluationContext) obj;
		if (this.contextParameters == null) {
			if (other.contextParameters != null)
				return false;
		} else if (!this.contextParameters.equals(other.contextParameters))
			return false;
		if (this.operatorDescription == null) {
			if (other.operatorDescription != null)
				return false;
		} else if (!this.operatorDescription.equals(other.operatorDescription))
			return false;
		if (this.resultProjection == null) {
			if (other.resultProjection != null)
				return false;
		} else if (!this.resultProjection.equals(other.resultProjection))
			return false;
		if (this.taskId != other.taskId)
			return false;
		if (this.workingPath == null) {
			if (other.workingPath != null)
				return false;
		} else if (!this.workingPath.equals(other.workingPath))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#getKryo()
	 */
	public Kryo getKryo() {
		return this.kryo;
	}

	/**
	 * Returns the nameChooserProvider.
	 * 
	 * @return the nameChooserProvider
	 */
	public NameChooserProvider getNameChooserProvider() {
		return this.nameChooserProvider;
	}

	/**
	 * Returns the operatorDescription.
	 * 
	 * @return the operatorDescription
	 */
	public String getOperatorDescription() {
		return this.operatorDescription;
	}

	@SuppressWarnings("unchecked")
	public <Type> Type getParameter(final String key, final Class<Type> type) {
		final Object value = this.contextParameters.get(key);
		return (Type) value;
	}

	public EvaluationExpression getResultProjection() {
		return this.resultProjection;
	}

	public int getTaskId() {
		return this.taskId;
	}

	/**
	 * Returns the hdfsPath.
	 * 
	 * @return the hdfsPath
	 */
	public Path getWorkingPath() {
		return new Path(this.workingPath);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.contextParameters == null ? 0 : this.contextParameters.hashCode());
		result = prime * result + (this.operatorDescription == null ? 0 : this.operatorDescription.hashCode());
		result = prime * result + (this.resultProjection == null ? 0 : this.resultProjection.hashCode());
		result = prime * result + this.taskId;
		result = prime * result + (this.workingPath == null ? 0 : this.workingPath.hashCode());
		return result;
	}

	public void putParameter(final String key, final Object value) {
		this.contextParameters.put(key, value);
	}

	/**
	 * Sets the operatorDescription to the specified value.
	 * 
	 * @param operatorDescription
	 *        the operatorDescription to set
	 */
	public void setOperatorDescription(final String operatorDescription) {
		if (operatorDescription == null)
			throw new NullPointerException("operatorDescription must not be null");

		this.operatorDescription = operatorDescription;
	}

	public void setResultProjection(final EvaluationExpression resultProjection) {
		if (resultProjection == null)
			throw new NullPointerException("resultProjection must not be null");

		this.resultProjection = resultProjection;
	}

	public void setTaskId(final int taskId) {
		this.taskId = taskId;
	}

	/**
	 * Sets the hdfsPath to the specified value.
	 * 
	 * @param hdfsPath
	 *        the hdfsPath to set
	 */
	public void setWorkingPath(final Path hdfsPath) {
		if (hdfsPath == null)
			throw new NullPointerException("hdfsPath must not be null");

		this.workingPath = hdfsPath.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#shallowClone()
	 */
	@Override
	public EvaluationContext shallowClone() {
		return (EvaluationContext) super.shallowClone();
	}

}
