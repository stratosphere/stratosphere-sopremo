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

import eu.stratosphere.api.common.operators.BulkIteration;
import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;

/**
 */
@InputCardinality(min = 1, max = 2)
@OutputCardinality(min = 1, max = 2)
public class IterativeOperator<Self extends IterativeOperator<Self>> extends ElementaryOperator<Self> {
	private Operator<?> terminationCriterion;

	private Operator<?> workingSetOperator, solutionSetOperator;

	public JsonStream getInitialWorkingSet() {
		return getInput(1);
	}

	public void setInitialWorkingSet(JsonStream workingSet) {
		if (workingSet == null)
			throw new NullPointerException("workingSet must not be null");

		this.setInput(1, workingSet);
	}

	public JsonStream getInitialSolutionSet() {
		return getInput(0);
	}

	public void setInitialSolutionSet(JsonStream solutionSet) {
		if (solutionSet == null)
			throw new NullPointerException("solutionSet must not be null");

		this.setInput(0, solutionSet);
	}

	public Operator<?> getWorkingSetOperator() {
		return this.workingSetOperator;
	}

	public void setWorkingSetOperator(Operator<?> workingSetOperator) {
		if (workingSetOperator == null)
			throw new NullPointerException("workingSetOperator must not be null");

		this.workingSetOperator = workingSetOperator;
	}

	public Operator<?> getSolutionSetOperator() {
		return this.solutionSetOperator;
	}

	public void setSolutionSetOperator(Operator<?> solutionSetOperator) {
		if (solutionSetOperator == null)
			throw new NullPointerException("solutionSetOperator must not be null");

		this.solutionSetOperator = solutionSetOperator;
	}

	/**
	 * Sets the terminationCriterion to the specified value.
	 * 
	 * @param terminationCriterion
	 *        the terminationCriterion to set
	 */
	public void setTerminationCriterion(Operator<?> terminationCriterion) {
		if (terminationCriterion == null)
			throw new NullPointerException("terminationCriterion must not be null");

		this.terminationCriterion = terminationCriterion;
	}

	/**
	 * Returns the terminationCriterion.
	 * 
	 * @return the terminationCriterion
	 */
	public Operator<?> getTerminationCriterion() {
		return this.terminationCriterion;
	}

	/**
	 * The maximum number of iterations. Possibly used only as a safeguard.
	 */
	private int maxNumberOfIterations = -1;

	/**
	 * Sets the maxNumberOfIterations to the specified value.
	 * 
	 * @param maxNumberOfIterations
	 *        the maxNumberOfIterations to set
	 */
	public void setMaxNumberOfIterations(int maxNumberOfIterations) {
		if (maxNumberOfIterations < 1)
			throw new NullPointerException("maxNumberOfIterations must >= 1");

		this.maxNumberOfIterations = maxNumberOfIterations;
	}

	/**
	 * Returns the maxNumberOfIterations.
	 * 
	 * @return the maxNumberOfIterations
	 */
	public int getMaxNumberOfIterations() {
		return this.maxNumberOfIterations;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.ElementaryOperator#asPactModule(eu.stratosphere.sopremo.EvaluationContext,
	 * eu.stratosphere.sopremo.serialization.SopremoRecordLayout)
	 */
	@Override
	public PactModule asPactModule(EvaluationContext context, SopremoRecordLayout layout) {
		PactModule module = new PactModule(getNumInputs(), getNumOutputs());

//		switch (getNumInputs()) {
//		case 1:
//			final BulkIteration bulkIteration = new BulkIteration();
//			bulkIteration.setDegreeOfParallelism(getDegreeOfParallelism());
//			if (maxNumberOfIterations != -1)
//				bulkIteration.setMaximumNumberOfIterations(maxNumberOfIterations);
//			if (terminationCriterion != null)
//				bulkIteration.setTerminationCriterion(terminationCriterion.asPactModule(context, layout));
//			module.getOutput(0).addInput(bulkIteration.getPartialSolution());
//		}
		return module;
	}
}
