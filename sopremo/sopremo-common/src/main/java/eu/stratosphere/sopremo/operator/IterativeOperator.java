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

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;

/**
 */
@OutputCardinality(1)
public abstract class IterativeOperator<Self extends IterativeOperator<Self>> extends CompositeOperator<Self> {
	private List<? extends EvaluationExpression> solutionSetKeyExpressions =
		new ArrayList<EvaluationExpression>();

	private int maximumNumberOfIterations = 1;

	public abstract void addImplementation(IterativeSopremoModule iterativeSopremoModule);

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.operator.CompositeOperator#addImplementation(eu.stratosphere.sopremo.operator.SopremoModule
	 * , eu.stratosphere.sopremo.EvaluationContext)
	 */
	@Override
	public void addImplementation(final SopremoModule module) {
		final IterativeSopremoModule iterativeModule =
			new IterativeSopremoModule(module.getNumInputs(), module.getNumOutputs());
		this.addImplementation(iterativeModule);
		iterativeModule.setSolutionSetKeyExpressions(this.solutionSetKeyExpressions);
		iterativeModule.setMaxNumberOfIterations(this.maximumNumberOfIterations);
		iterativeModule.embedInto(module);
	}

	/**
	 * Returns the maximumNumberOfIterations.
	 * 
	 * @return the maximumNumberOfIterations
	 */
	public int getMaximumNumberOfIterations() {
		return this.maximumNumberOfIterations;
	}

	/**
	 * Sets the maximumNumberOfIterations to the specified value.
	 * 
	 * @param maximumNumberOfIterations
	 *        the maximumNumberOfIterations to set
	 */
	public void setMaximumNumberOfIterations(final int maximumNumberOfIterations) {
		if (maximumNumberOfIterations < 1)
			throw new NullPointerException("maximumNumberOfIterations must be > 0");

		this.maximumNumberOfIterations = maximumNumberOfIterations;
	}

	protected List<? extends EvaluationExpression> getSolutionSetKeyExpressions() {
		return this.solutionSetKeyExpressions;
	}

	/**
	 * Sets the solutionSetKeyExpressions to the specified value.
	 * 
	 * @param solutionSetKeyExpressions
	 *        the solutionSetKeyExpressions to set
	 */
	protected void setSolutionSetKeyExpressions(final EvaluationExpression... solutionSetKeyExpressions) {
		if (solutionSetKeyExpressions == null)
			throw new NullPointerException("solutionSetKeyExpressions must not be null");

		this.solutionSetKeyExpressions = Lists.newArrayList(solutionSetKeyExpressions);
	}

	/**
	 * Sets the solutionSetKeyExpressions to the specified value.
	 * 
	 * @param solutionSetKeyExpressions
	 *        the solutionSetKeyExpressions to set
	 */
	protected void setSolutionSetKeyExpressions(final List<? extends EvaluationExpression> solutionSetKeyExpressions) {
		if (solutionSetKeyExpressions == null)
			throw new NullPointerException("solutionSetKeyExpressions must not be null");

		this.solutionSetKeyExpressions = solutionSetKeyExpressions;
	}
}
