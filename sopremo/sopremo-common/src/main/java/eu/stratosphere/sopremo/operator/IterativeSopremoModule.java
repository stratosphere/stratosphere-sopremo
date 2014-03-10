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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import eu.stratosphere.api.common.operators.BulkIteration;
import eu.stratosphere.api.common.operators.DeltaIteration;
import eu.stratosphere.api.common.operators.util.OperatorUtil;
import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;
import eu.stratosphere.util.IdentityList;
import eu.stratosphere.util.IdentitySet;
import eu.stratosphere.util.dag.GraphTraverseListener;
import eu.stratosphere.util.dag.OneTimeTraverser;

/**
 * Provides a direct translation of the Core API to Sopremo. Iterative operators may include an arbitrary number of pre-
 * and postprocessing operators - they will automatically be detected and moved outside of the core iteration.
 */
public class IterativeSopremoModule extends SopremoModule {

	private JsonStream terminationCriterion, nextWorkset, solutionSetDelta;

	/**
	 * The maximum number of iterations. Possibly used only as a safeguard.
	 */
	private int maxNumberOfIterations = -1;

	private final NopOperator workingSet = new NopOperator().withName("workingSet"),
			solutionSet = new NopOperator().withName("solutionSet");

	private List<? extends EvaluationExpression> solutionSetKeyExpressions;

	/**
	 * Initializes IterativeSopremoModule.
	 * 
	 * @param numberOfInputs
	 * @param numberOfOutputs
	 */
	public IterativeSopremoModule(final int numberOfInputs, final int numberOfOutputs) {
		super(numberOfInputs, numberOfOutputs);
	}

	/**
	 * Initializes IterativeSopremoModule.
	 */
	IterativeSopremoModule() {
	}

	/**
	 * @param module
	 */
	public void embedInto(final SopremoModule module) {
		final Set<Operator<?>> stepOutputs = this.getStepOutputs();

		final Set<Operator<?>> step = this.getStepOperators(stepOutputs);
		final List<JsonStream> moduleInputs = new IdentityList<JsonStream>(this.getIncomingEdges(step));
		final CoreIteration core = CoreIteration.valueOf(this, moduleInputs);

		core.setInputs(moduleInputs);
		for (int index = 0; index < this.getNumOutputs(); index++)
			module.getOutput(index).setInput(0, this.getOutput(index).getInput(0));
		for (int index = 0; index < this.getNumInputs(); index++) {
			replace(this.getReachableNodes(), this.getInput(index).getOutput(0), module.getInput(index).getOutput(0));
			replace(Collections.singleton(core), this.getInput(index).getOutput(0), module.getInput(index).getOutput(0));
		}
		replace(this.getReachableNodes(), this.solutionSet.getOutput(0), core.getOutput(0));
	}

	/**
	 * Returns the maxNumberOfIterations.
	 * 
	 * @return the maxNumberOfIterations
	 */
	public int getMaxNumberOfIterations() {
		return this.maxNumberOfIterations;
	}

	/**
	 */
	public JsonStream getSolutionSet() {
		return this.solutionSet;
	}

	/**
	 * Returns the terminationCriterion.
	 * 
	 * @return the terminationCriterion
	 */
	public JsonStream getTerminationCriterion() {
		return this.terminationCriterion;
	}

	/**
	 */
	public JsonStream getWorkingSet() {
		return this.workingSet;
	}

	public void setInitialSolutionSet(final JsonStream initialSolutionSet) {
		if (initialSolutionSet == null)
			throw new NullPointerException("initialSolutionSet must not be null");

		this.solutionSet.setInput(0, initialSolutionSet);
	}

	public void setInitialWorkingset(final JsonStream initialWorkingset) {
		if (initialWorkingset == null)
			throw new NullPointerException("initialWorkingset must not be null");

		this.workingSet.setInput(0, initialWorkingset);
	}

	/**
	 * Sets the maxNumberOfIterations to the specified value.
	 * 
	 * @param maxNumberOfIterations
	 *        the maxNumberOfIterations to set
	 */
	public void setMaxNumberOfIterations(final int maxNumberOfIterations) {
		if (maxNumberOfIterations < 1)
			throw new NullPointerException("maxNumberOfIterations must >= 1");

		this.maxNumberOfIterations = maxNumberOfIterations;
	}

	public void setNextWorkset(final JsonStream nextWorkset) {
		if (nextWorkset == null)
			throw new NullPointerException("nextWorkset must not be null");

		this.nextWorkset = nextWorkset;
	}

	public void setSolutionSetDelta(final JsonStream solutionSetDelta) {
		if (solutionSetDelta == null)
			throw new NullPointerException("solutionSetDelta must not be null");

		this.solutionSetDelta = solutionSetDelta;
	}

	/**
	 * Sets the terminationCriterion to the specified value.
	 * 
	 * @param terminationCriterion
	 *        the terminationCriterion to set
	 */
	public void setTerminationCriterion(final JsonStream terminationCriterion) {
		if (terminationCriterion == null)
			throw new NullPointerException("terminationCriterion must not be null");

		this.terminationCriterion = terminationCriterion;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.util.dag.GraphModule#validate()
	 */
	@Override
	public void validate() {
		if (this.solutionSetDelta == null)
			throw new IllegalStateException("the module must provide a solution set delta");
		if (this.nextWorkset == null)
			for (final Operator<?> operator : this.getReachableNodes())
				if (operator.getInputs().contains(this.getWorkingSet()))
					throw new IllegalStateException(
						"the module can only use working set, when it also provides a next working set");
		if (this.terminationCriterion == null && this.maxNumberOfIterations == -1)
			throw new IllegalStateException("must set terminationCriterion and/or maxNumberOfIterations");
		if (this.terminationCriterion != null && this.nextWorkset != null)
			throw new IllegalStateException(
				"cannot use terminationCriterion and nextWorkset at the same time (iteration must be either bulk or delta)");
		if (this.solutionSetKeyExpressions.isEmpty())
			throw new IllegalStateException("solutionSetKeyExpressions must be set");
		super.validate();
	}

	/**
	 * Returns the solutionSetKeyExpressions.
	 * 
	 * @return the solutionSetKeyExpressions
	 */
	List<? extends EvaluationExpression> getSolutionSetKeyExpressions() {
		return this.solutionSetKeyExpressions;
	}

	/**
	 * Sets the solutionSetKeyExpressions to the specified value.
	 * 
	 * @param solutionSetKeyExpressions
	 *        the solutionSetKeyExpressions to set
	 */
	void setSolutionSetKeyExpressions(final List<? extends EvaluationExpression> solutionSetKeyExpressions) {
		if (solutionSetKeyExpressions == null)
			throw new NullPointerException("solutionSetKeyExpressions must not be null");

		this.solutionSetKeyExpressions = solutionSetKeyExpressions;
	}

	private Set<JsonStream> getIncomingEdges(final Set<Operator<?>> partition) {
		final Set<JsonStream> incomingEdges = new IdentitySet<JsonStream>();
		for (final Operator<?> op : partition)
			for (final JsonStream input : op.getInputs())
				if (!partition.contains(input.getSource().getOperator()))
					incomingEdges.add(input);
		return incomingEdges;
	}

	private Set<Operator<?>> getStepOperators(final Set<Operator<?>> stepOutputs) {
		final Set<Operator<?>> step = new IdentitySet<Operator<?>>();
		final Multimap<Operator<?>, Operator<?>> successors = this.getSuccessorRelations(stepOutputs);
		step.addAll(successors.get(this.getWorkingSet().getSource().getOperator()));
		step.addAll(successors.get(this.getSolutionSet().getSource().getOperator()));
		step.add(this.workingSet);
		step.add(this.solutionSet);
		return step;
	}

	private Set<Operator<?>> getStepOutputs() {
		final Set<Operator<?>> stepOutputs = new IdentitySet<Operator<?>>();
		stepOutputs.add(this.solutionSetDelta.getSource().getOperator());
		if (this.nextWorkset != null)
			stepOutputs.add(this.nextWorkset.getSource().getOperator());
		if (this.terminationCriterion != null)
			stepOutputs.add(this.terminationCriterion.getSource().getOperator());
		return stepOutputs;
	}

	private Multimap<Operator<?>, Operator<?>> getSuccessorRelations(final Set<Operator<?>> stepOutputs) {
		final Multimap<Operator<?>, Operator<?>> successors =
			Multimaps.newMultimap(new IdentityHashMap<Operator<?>, Collection<Operator<?>>>(),
				IdentitySetSupplier.<Operator<?>> getInstance());
		OneTimeTraverser.INSTANCE.traverse(stepOutputs,
			OperatorNavigator.INSTANCE, new GraphTraverseListener<Operator<?>>() {
				@Override
				public void nodeTraversed(final Operator<?> node) {
					for (final JsonStream input : node.getInputs()) {
						successors.put(input.getSource().getOperator(), node);
						successors.putAll(input.getSource().getOperator(), successors.get(node));
					}
				}
			});
		return successors;
	}

	private static void replace(final Iterable<? extends eu.stratosphere.api.common.operators.Operator> nodes,
			final eu.stratosphere.api.common.operators.Operator toReplace,
			final eu.stratosphere.api.common.operators.Operator replaceWith) {
		for (final eu.stratosphere.api.common.operators.Operator operator : nodes) {
			final List<List<eu.stratosphere.api.common.operators.Operator>> inputs = OperatorUtil.getInputs(operator);
			for (final List<eu.stratosphere.api.common.operators.Operator> unionedInputs : inputs)
				for (int index = 0; index < unionedInputs.size(); index++)
					if (unionedInputs.get(index) == toReplace)
						unionedInputs.set(index, replaceWith);
			OperatorUtil.setInputs(operator, inputs);
		}
	}

	private static void replace(final Iterable<? extends Operator<?>> nodes, final JsonStream toReplace,
			final JsonStream replaceWith) {
		for (final Operator<?> operator : nodes)
			for (int index = 0, size = operator.getNumInputs(); index < size; index++)
				if (operator.getInput(index) == toReplace)
					operator.setInput(index, replaceWith);
	}

	@InputCardinality(min = 1)
	@OutputCardinality(1)
	static final class CoreIteration extends ElementaryOperator<CoreIteration> {
		private final IterativeSopremoModule module;

		private final List<JsonStream> moduleInputs, stepInputs;

		private final ElementarySopremoModule stepSopremoModule;

		/**
		 * Initializes IterativeSopremoModule.CoreIteration.
		 */
		public CoreIteration() {
			this(null, null, null, null);
		}

		/**
		 * Initializes CoreIteration.
		 */
		public CoreIteration(final IterativeSopremoModule module, final List<JsonStream> moduleInputs,
				final ElementarySopremoModule stepSopremoModule,
				final List<JsonStream> stepInputs) {
			this.module = module;
			this.moduleInputs = moduleInputs;
			this.stepSopremoModule = stepSopremoModule;
			this.stepInputs = stepInputs;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.operator.ElementaryOperator#asPactModule(eu.stratosphere.sopremo.EvaluationContext,
		 * eu.stratosphere.sopremo.serialization.SopremoRecordLayout)
		 */
		@Override
		public PactModule asPactModule() {
			final PactModule iterationModule = new PactModule(this.getNumInputs(), this.getNumOutputs());
			final PactModule stepModule = this.stepSopremoModule.asPactModule();

			if (this.module.nextWorkset == null) {
				// not tested yet!
				final BulkIteration bulkIteration = new BulkIteration();
				bulkIteration.setDegreeOfParallelism(this.getDegreeOfParallelism());
				bulkIteration.setMaximumNumberOfIterations(this.module.maxNumberOfIterations);
				bulkIteration.setNextPartialSolution(stepModule.getOutput(0).getInputs().get(0));
				if (this.module.terminationCriterion != null)
					bulkIteration.setTerminationCriterion(stepModule.getOutput(1).getInputs().get(0));

				iterationModule.getOutput(0).setInput(bulkIteration);
				replace(stepModule.getReachableNodes(),
					stepModule.getInput(this.getInputIndex(stepModule, this.stepSopremoModule.getInput(0))),
					bulkIteration.getPartialSolution());

				for (int index = 2; index < this.stepInputs.size(); index++) {
					final int moduleIndex = this.moduleInputs.indexOf(this.stepInputs.get(index));
					replace(stepModule.getReachableNodes(),
						stepModule.getInput(this.getInputIndex(stepModule, this.stepSopremoModule.getInput(index))),
						iterationModule.getInput(moduleIndex));
				}
			} else {
				SopremoRecordLayout layout = SopremoEnvironment.getInstance().getLayout();
				final DeltaIteration deltaIteration =
					new DeltaIteration(this.getKeyIndices(layout, this.module.solutionSetKeyExpressions));
				deltaIteration.setDegreeOfParallelism(this.getDegreeOfParallelism());
				deltaIteration.setMaximumNumberOfIterations(this.module.maxNumberOfIterations);
				deltaIteration.setSolutionSetDelta(stepModule.getOutput(0).getInputs().get(0));
				deltaIteration.setNextWorkset(stepModule.getOutput(1).getInputs().get(0));

				iterationModule.getOutput(0).setInput(deltaIteration);
				replace(stepModule.getReachableNodes(),
					stepModule.getInput(this.getInputIndex(stepModule, this.stepSopremoModule.getInput(0))),
					deltaIteration.getSolutionSet());
				replace(stepModule.getReachableNodes(),
					stepModule.getInput(this.getInputIndex(stepModule, this.stepSopremoModule.getInput(1))),
					deltaIteration.getWorkset());

				for (int index = 2; index < this.stepInputs.size(); index++) {
					final int moduleIndex = this.moduleInputs.indexOf(this.stepInputs.get(index));
					replace(stepModule.getReachableNodes(),
						stepModule.getInput(this.getInputIndex(stepModule, this.stepSopremoModule.getInput(index))),
						iterationModule.getInput(moduleIndex));
				}

				deltaIteration.setInitialSolutionSet(iterationModule.getInput(this.moduleInputs.indexOf(this.module.solutionSet.getInput(0))));
				deltaIteration.setInitialWorkset(iterationModule.getInput(this.moduleInputs.indexOf(this.module.workingSet.getInput(0))));
			}

			return iterationModule;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.operator.ElementaryOperator#getAllKeyExpressions()
		 */
		@Override
		public Set<EvaluationExpression> getAllKeyExpressions() {
			final Set<EvaluationExpression> keyExpressions = new HashSet<EvaluationExpression>(this.module.solutionSetKeyExpressions);
			keyExpressions.addAll(this.stepSopremoModule.getSchema().getKeyExpressions());
			return keyExpressions;
		}

		private int getInputIndex(final PactModule pactModule, final Source sopremoSource) {
			for (int index = 0; index < pactModule.getNumInputs(); index++)
				if (pactModule.getInput(index).getName().equals(sopremoSource.getName()))
					return index;
			throw new IllegalStateException();
		}

		public static CoreIteration valueOf(final IterativeSopremoModule module,
				final List<JsonStream> moduleInputs) {
			final List<JsonStream> stepInputs = new IdentityList<JsonStream>(moduleInputs);
			stepInputs.remove(module.solutionSet.getInput(0));
			stepInputs.remove(module.workingSet.getInput(0));

			ElementarySopremoModule stepSopremoModule;
			if (module.nextWorkset == null) {
				stepInputs.add(0, module.solutionSet.getOutput(0));
				stepSopremoModule = getBulkStep(module, stepInputs).asElementary();
			} else {
				stepInputs.add(0, module.solutionSet.getOutput(0));
				stepInputs.add(1, module.workingSet.getOutput(0));
				stepSopremoModule = getDeltaStep(module, stepInputs).asElementary();
			}

			return new CoreIteration(module, moduleInputs, stepSopremoModule, stepInputs);
		}

		private static SopremoModule getBulkStep(final IterativeSopremoModule module,
				final Collection<JsonStream> stepInputs) {
			SopremoModule stepModule;
			if (module.terminationCriterion != null) {
				stepModule = new SopremoModule(stepInputs.size(), 2);
				stepModule.getOutput(1).setInput(0, module.terminationCriterion);
			}
			else
				stepModule = new SopremoModule(stepInputs.size(), 1);
			stepModule.getOutput(0).setInput(0, module.solutionSetDelta);
			final Iterator<JsonStream> iterator = stepInputs.iterator();
			for (int index = 0; iterator.hasNext(); index++)
				replace(stepModule.getReachableNodes(), iterator.next(), stepModule.getInput(index));
			return stepModule;
		}

		private static SopremoModule getDeltaStep(final IterativeSopremoModule module,
				final Collection<JsonStream> stepInputs) {
			final SopremoModule stepModule = new SopremoModule(stepInputs.size(), 2);
			stepModule.getOutput(0).setInput(0, module.solutionSetDelta);
			stepModule.getOutput(1).setInput(0, module.nextWorkset);
			final Iterator<JsonStream> iterator = stepInputs.iterator();
			for (int index = 0; iterator.hasNext(); index++)
				replace(stepModule.getReachableNodes(), iterator.next(), stepModule.getInput(index));
			return stepModule;
		}

	}

}
