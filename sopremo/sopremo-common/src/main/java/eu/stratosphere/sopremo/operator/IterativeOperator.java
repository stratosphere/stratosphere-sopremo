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

import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.io.Sink;

/**
 */
@OutputCardinality(1)
public abstract class IterativeOperator<Self extends IterativeOperator<Self>> extends CompositeOperator<Self> {
	public abstract void addImplementation(IterativeSopremoModule iterativeSopremoModule, EvaluationContext context);

	private List<? extends EvaluationExpression> solutionSetKeyExpressions =
		new ArrayList<EvaluationExpression>();

	/**
	 * Sets the solutionSetKeyExpressions to the specified value.
	 * 
	 * @param solutionSetKeyExpressions
	 *        the solutionSetKeyExpressions to set
	 */
	protected void setSolutionSetKeyExpressions(List<? extends EvaluationExpression> solutionSetKeyExpressions) {
		if (solutionSetKeyExpressions == null)
			throw new NullPointerException("solutionSetKeyExpressions must not be null");

		this.solutionSetKeyExpressions = solutionSetKeyExpressions;
	}

	/**
	 * Sets the solutionSetKeyExpressions to the specified value.
	 * 
	 * @param solutionSetKeyExpressions
	 *        the solutionSetKeyExpressions to set
	 */
	protected void setSolutionSetKeyExpressions(EvaluationExpression... solutionSetKeyExpressions) {
		if (solutionSetKeyExpressions == null)
			throw new NullPointerException("solutionSetKeyExpressions must not be null");

		this.solutionSetKeyExpressions = Lists.newArrayList(solutionSetKeyExpressions);
	}

	protected List<? extends EvaluationExpression> getSolutionSetKeyExpressions() {
		return this.solutionSetKeyExpressions;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.operator.CompositeOperator#addImplementation(eu.stratosphere.sopremo.operator.SopremoModule
	 * , eu.stratosphere.sopremo.EvaluationContext)
	 */
	@Override
	public void addImplementation(SopremoModule module, EvaluationContext context) {
		final IterativeSopremoModule iterativeModule =
			new IterativeSopremoModule(module.getNumInputs(), module.getNumOutputs());
		addImplementation(iterativeModule, context);
		iterativeModule.setSolutionSetKeyExpressions(solutionSetKeyExpressions);
		iterativeModule.embedInto(module);
	}
}
