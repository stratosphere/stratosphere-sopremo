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

import it.unimi.dsi.fastutil.objects.Object2IntMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import eu.stratosphere.api.common.operators.BulkIteration;
import eu.stratosphere.api.common.operators.DeltaIteration;
import eu.stratosphere.api.common.operators.GenericDataSink;
import eu.stratosphere.api.common.operators.util.ContractUtil;
import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.io.JsonFormat.JsonInputFormat;
import eu.stratosphere.sopremo.io.JsonFormat.JsonOutputFormat;
import eu.stratosphere.sopremo.pact.SopremoNop;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;
import eu.stratosphere.util.IdentityList;
import eu.stratosphere.util.IdentitySet;
import eu.stratosphere.util.dag.GraphTraverseListener;
import eu.stratosphere.util.dag.OneTimeTraverser;

/**
 * 
 */
public class IterativeSopremoModule extends SopremoModule {

	/**
	 * Initializes IterativeSopremoModule.
	 * 
	 * @param numberOfInputs
	 * @param numberOfOutputs
	 */
	public IterativeSopremoModule(int numberOfInputs, int numberOfOutputs) {
		super(numberOfInputs, numberOfOutputs);
	}

	/**
	 * Initializes IterativeSopremoModule.
	 */
	IterativeSopremoModule() {
	}

	private JsonStream terminationCriterion, nextWorkset, solutionSetDelta;

	/**
	 * The maximum number of iterations. Possibly used only as a safeguard.
	 */
	private int maxNumberOfIterations = -1;

	private NopOperator workingSet = new NopOperator().withName("workingSet"),
			solutionSet = new NopOperator().withName("solutionSet");

	/**
	 * Sets the terminationCriterion to the specified value.
	 * 
	 * @param terminationCriterion
	 *        the terminationCriterion to set
	 */
	public void setTerminationCriterion(JsonStream terminationCriterion) {
		if (terminationCriterion == null)
			throw new NullPointerException("terminationCriterion must not be null");

		this.terminationCriterion = terminationCriterion;
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

	/**
	 * @return
	 */
	public JsonStream getWorkingSet() {
		return this.workingSet;
	}

	/**
	 * @return
	 */
	public JsonStream getSolutionSet() {
		return this.solutionSet;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.util.dag.GraphModule#validate()
	 */
	@Override
	public void validate() {
		if (this.solutionSetDelta == null)
			throw new IllegalStateException("the module must provide a solution set delta");
		if (this.nextWorkset == null) {
			for (final Operator<?> operator : this.getReachableNodes())
				if (operator.getInputs().contains(getWorkingSet()))
					throw new IllegalStateException(
						"the module can only use working set, when it also provides a next working set");
		}
		if (this.terminationCriterion == null && this.maxNumberOfIterations == -1)
			throw new IllegalStateException("must set terminationCriterion and/or maxNumberOfIterations");
		if (this.solutionSetKeyExpressions.isEmpty())
			throw new IllegalStateException("solutionSetKeyExpressions must be set");
		super.validate();
	}

	public void setNextWorkset(JsonStream nextWorkset) {
		if (nextWorkset == null)
			throw new NullPointerException("nextWorkset must not be null");

		this.nextWorkset = nextWorkset;
	}

	public void setSolutionSetDelta(JsonStream solutionSetDelta) {
		if (solutionSetDelta == null)
			throw new NullPointerException("solutionSetDelta must not be null");

		this.solutionSetDelta = solutionSetDelta;
	}

	private List<? extends EvaluationExpression> solutionSetKeyExpressions;

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
	void setSolutionSetKeyExpressions(List<? extends EvaluationExpression> solutionSetKeyExpressions) {
		if (solutionSetKeyExpressions == null)
			throw new NullPointerException("solutionSetKeyExpressions must not be null");

		this.solutionSetKeyExpressions = solutionSetKeyExpressions;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.SopremoModule#asElementary(eu.stratosphere.sopremo.EvaluationContext)
	 */
	@Override
	public ElementarySopremoModule asElementary(EvaluationContext context) {
		return new ElementarySopremoModule(getNumInputs(), getNumOutputs());
	}

	@InputCardinality(min = 1)
	@OutputCardinality(1)
	static final class CoreIteration extends ElementaryOperator<CoreIteration> {
		IterativeSopremoModule module;

		Set<Operator<?>> step;

		List<JsonStream> stepIncomingEdges;

		/**
		 * Initializes CoreIteration.
		 * 
		 * @param iterativeSopremoModule
		 * @param step
		 */
		public CoreIteration(IterativeSopremoModule module, Set<Operator<?>> step,
				List<JsonStream> identitySet) {
			this.module = module;
			this.step = step;
			this.stepIncomingEdges = identitySet;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.operator.ElementaryOperator#getAllKeyExpressions()
		 */
		@Override
		public Set<EvaluationExpression> getAllKeyExpressions() {
			Set<EvaluationExpression> keyExpressions = new HashSet<>(this.module.solutionSetKeyExpressions);
			return keyExpressions;
		}

		/**
		 * Initializes IterativeSopremoModule.CoreIteration.
		 */
		public CoreIteration() {
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.operator.ElementaryOperator#asPactModule(eu.stratosphere.sopremo.EvaluationContext,
		 * eu.stratosphere.sopremo.serialization.SopremoRecordLayout)
		 */
		@Override
		public PactModule asPactModule(EvaluationContext context, SopremoRecordLayout layout) {
			List<JsonStream> stepInputs = new IdentityList<>(this.stepIncomingEdges);
			stepInputs.remove(this.module.solutionSet.getInput(0));
			stepInputs.remove(this.module.workingSet.getInput(0));

			PactModule iterationModule = new PactModule(getNumInputs(), getNumOutputs());

			if (this.module.nextWorkset == null) {
				SopremoModule stepModule = new SopremoModule(stepInputs.size(), 1);
				final BulkIteration bulkIteration = new BulkIteration();
				bulkIteration.setMaximumNumberOfIterations(this.module.maxNumberOfIterations);
			} else {
				boolean sameInitialSolutionAndWorkingSet =
					this.module.solutionSet.getInput(0) == this.module.workingSet.getInput(0);
				stepInputs.add(0, this.module.solutionSet.getOutput(0));
				stepInputs.add(1, this.module.workingSet.getOutput(0));
				SopremoModule stepSopremoModule = getDeltaStep(stepInputs);

				final PactModule stepModule = stepSopremoModule.asElementary(context).asPactModule(context, layout);
				final DeltaIteration deltaIteration =
					new DeltaIteration(getKeyIndices(layout, this.module.solutionSetKeyExpressions));
				deltaIteration.setDegreeOfParallelism(getDegreeOfParallelism());
				deltaIteration.setMaximumNumberOfIterations(this.module.maxNumberOfIterations);
				deltaIteration.setSolutionSetDelta(stepModule.getOutput(0).getInputs().get(0));
				deltaIteration.setNextWorkset(stepModule.getOutput(1).getInputs().get(0));

				iterationModule.getOutput(0).setInput(deltaIteration);
				replace(stepModule.getReachableNodes(), stepModule.getInput(0), deltaIteration.getSolutionSet());
				replace(stepModule.getReachableNodes(), stepModule.getInput(1), deltaIteration.getWorkset());

				for (int index = 2; index < stepInputs.size(); index++) {
					int moduleIndex = this.stepIncomingEdges.indexOf(stepInputs.get(index));
					replace(stepModule.getReachableNodes(), stepModule.getInput(index),
						iterationModule.getInput(moduleIndex));
//					final GenericDataSink contract = new GenericDataSink(JsonOutputFormat.class, "dummy for validation");
//					contract.addInput(iterationModule.getInput(moduleIndex));
//					iterationModule.addInternalOutput(contract);
				}

				deltaIteration.setInitialSolutionSet(iterationModule.getInput(this.stepIncomingEdges.indexOf(module.solutionSet.getInput(0))));
				deltaIteration.setInitialWorkset(iterationModule.getInput(this.stepIncomingEdges.indexOf(module.workingSet.getInput(0))));

				return iterationModule;
			}

			return super.asPactModule(context, layout);
		}

		private SopremoModule getDeltaStep(Collection<JsonStream> orderedInputs) {
			SopremoModule stepModule = new SopremoModule(orderedInputs.size(), 2);
			stepModule.getOutput(0).setInput(0, this.module.solutionSetDelta);
			stepModule.getOutput(1).setInput(0, this.module.nextWorkset);
			final Iterator<JsonStream> iterator = orderedInputs.iterator();
			for (int index = 0; iterator.hasNext(); index++)
				replace(stepModule.getReachableNodes(), iterator.next(), stepModule.getInput(index));
			return stepModule;
		}

	}

	public void setInitialSolutionSet(JsonStream initialSolutionSet) {
		if (initialSolutionSet == null)
			throw new NullPointerException("initialSolutionSet must not be null");

		this.solutionSet.setInput(0, initialSolutionSet);
	}

	public void setInitialWorkingset(JsonStream initialWorkingset) {
		if (initialWorkingset == null)
			throw new NullPointerException("initialWorkingset must not be null");

		this.workingSet.setInput(0, initialWorkingset);
	}

	/**
	 * @param module
	 */
	public void embedInto(SopremoModule module) {
		final Set<Operator<?>> stepOutputs = getStepOutputs(), stepInputs = getStepInputs();

		// partition module into the four phases
		final Set<Operator<?>> preprocessing = getPreprocessingOperators(stepInputs), postProcessing =
			getPostProcessingOperators(preprocessing), step = getStepOperators(stepOutputs), constantInputs =
			getConstantOperators(stepOutputs, preprocessing, step);
		//
		// Multimap<JsonStream, Operator<?>> preprocessingOutgoing =
		// getOutgoingEdges(preprocessing, constantInputs, postProcessing, step);
		// SopremoModule preprocessingModule = new SopremoModule(getNumInputs(), preprocessingOutgoing.keySet().size());
		// connectOutputs(preprocessingOutgoing, preprocessingModule);
		// final Operator<?> preprocessingOperator = preprocessingModule.asOperator();

		List<JsonStream> stepIncomingEdges = new IdentityList<>(getIncomingEdges(step).keySet());
		final CoreIteration core = new CoreIteration(this, step, stepIncomingEdges);
		// for (int index = 0; index < array.length; index++) {
		//
		// }

		// Multimap<JsonStream, Operator<?>> constantIncoming = getIncomingEdges(constantInputs);
		// Multimap<JsonStream, Operator<?>> constantOutgoing = getOutgoingEdges(constantInputs, postProcessing, step);
		// SopremoModule constantModule =
		// new SopremoModule(constantIncoming.keySet().size(), constantOutgoing.keySet().size());
		// connectOutputs(constantOutgoing, constantModule);
		// connectInputs(constantOutgoing, constantModule, constantInputs);
		//
		// Map<JsonStream, Integer> postProcessChannelIds = new IdentityHashMap<>();
		// SopremoModule postProcessingModule = extractModule(postProcessing, postProcessChannelIds);

		// module.embed(preprocessingOutgoing);

		core.setInputs(stepIncomingEdges);
		for (int index = 0; index < getNumOutputs(); index++)
			module.getOutput(index).setInput(0, getOutput(index).getInput(0));
		for (int index = 0; index < getNumInputs(); index++) {
			replace(getReachableNodes(), getInput(index).getOutput(0), module.getInput(index).getOutput(0));
			replace(Collections.singleton(core), getInput(index).getOutput(0), module.getInput(index).getOutput(0));
		}
		replace(getReachableNodes(), this.solutionSet.getOutput(0), core.getOutput(0));
		// replace(getReachableNodes(), this.initialSolutionSet, core.getInput(0));
		// replace(getReachableNodes(), solutionSet, core.getOutput(0));
		// replace(step, workingSet, module.getInput(index));
	}

	/**
	 * @param reachableNodes
	 * @param input
	 * @param input2
	 */
	private static void replace(Iterable<? extends Operator<?>> nodes, JsonStream toReplace, JsonStream replaceWith) {
		for (Operator<?> operator : nodes)
			for (int index = 0, size = operator.getNumInputs(); index < size; index++)
				if (operator.getInput(index) == toReplace)
					operator.setInput(index, replaceWith);
	}

	private static void replace(Iterable<? extends eu.stratosphere.api.common.operators.Operator> nodes,
			eu.stratosphere.api.common.operators.Operator toReplace,
			eu.stratosphere.api.common.operators.Operator replaceWith) {
		for (eu.stratosphere.api.common.operators.Operator operator : nodes) {
			final List<List<eu.stratosphere.api.common.operators.Operator>> inputs = ContractUtil.getInputs(operator);
			for (List<eu.stratosphere.api.common.operators.Operator> unionedInputs : inputs) {
				for (int index = 0; index < unionedInputs.size(); index++) {
					if (unionedInputs.get(index) == toReplace)
						unionedInputs.set(index, replaceWith);
				}
			}
			ContractUtil.setInputs(operator, inputs);
		}
	}

	private Set<Operator<?>> getConstantOperators(final Set<Operator<?>> stepOutputs,
			final Set<Operator<?>> preprocessing, final Set<Operator<?>> step) {
		final Set<Operator<?>> constantInputs = new IdentitySet<>();
		Iterables.addAll(constantInputs,
			OneTimeTraverser.INSTANCE.getReachableNodes(stepOutputs, OperatorNavigator.INSTANCE));
		constantInputs.removeAll(step);
		constantInputs.removeAll(preprocessing);
		return constantInputs;
	}

	private void connectOutputs(Multimap<JsonStream, Operator<?>> outgoings, SopremoModule module) {
		final Iterator<JsonStream> iterator = outgoings.keySet().iterator();
		for (int index = 0; iterator.hasNext(); index++)
			module.getOutput(index).setInput(0, iterator.next());
	}

	private void connectInputs(Multimap<JsonStream, Operator<?>> incomings, SopremoModule module,
			Set<Operator<?>> moduleOperators) {
		final List<JsonStream> incomingStreams = new IdentityList<>(incomings.keySet());
		for (Operator<?> operator : moduleOperators) {
			for (int index = 0; index < operator.getNumInputs(); index++) {
				final int moduleIndex = incomingStreams.indexOf(operator.getInput(index));
				if (moduleIndex != -1)
					operator.setInput(index, module.getInput(moduleIndex));
			}
		}
	}

	private Set<Operator<?>> getStepOperators(final Set<Operator<?>> stepOutputs) {
		final Set<Operator<?>> step = new IdentitySet<>();
		final Multimap<Operator<?>, Operator<?>> successors = getSuccessorRelations(stepOutputs);
		step.addAll(successors.get(getWorkingSet().getSource().getOperator()));
		step.addAll(successors.get(getSolutionSet().getSource().getOperator()));
		step.add(this.workingSet);
		step.add(this.solutionSet);
		return step;
	}

	private Set<Operator<?>> getPostProcessingOperators(final Set<Operator<?>> preprocessing) {
		final Set<Operator<?>> postProcessing = new IdentitySet<>();
		Iterables.addAll(postProcessing,
			OneTimeTraverser.INSTANCE.getReachableNodes(getAllOutputs(), OperatorNavigator.INSTANCE));
		postProcessing.removeAll(preprocessing);
		postProcessing.remove(getSolutionSet());
		return postProcessing;
	}

	private Set<Operator<?>> getPreprocessingOperators(Set<Operator<?>> stepInputs) {
		final Set<Operator<?>> preprocessing = new IdentitySet<>();

		Iterables.addAll(preprocessing,
			OneTimeTraverser.INSTANCE.getReachableNodes(stepInputs, OperatorNavigator.INSTANCE));
		preprocessing.removeAll(getInputs());
		return preprocessing;
	}

	private Set<Operator<?>> getStepOutputs() {
		final Set<Operator<?>> stepOutputs = new IdentitySet<>();
		stepOutputs.add(this.solutionSetDelta.getSource().getOperator());
		if (this.nextWorkset != null)
			stepOutputs.add(this.nextWorkset.getSource().getOperator());
		if (this.terminationCriterion != null)
			stepOutputs.add(this.terminationCriterion.getSource().getOperator());
		return stepOutputs;
	}

	private Set<Operator<?>> getStepInputs() {
		final Set<Operator<?>> stepInputs = new IdentitySet<>();
		stepInputs.add(this.solutionSet.getInput(0).getSource().getOperator());
		if (this.workingSet.getNumInputs() != 0)
			stepInputs.add(this.workingSet.getInput(0).getSource().getOperator());
		return stepInputs;
	}

	private Multimap<Operator<?>, Operator<?>> getSuccessorRelations(final Set<Operator<?>> stepOutputs) {
		final Multimap<Operator<?>, Operator<?>> successors =
			Multimaps.newMultimap(new IdentityHashMap<Operator<?>, Collection<Operator<?>>>(),
				IdentitySetSupplier.<Operator<?>> getInstance());
		OneTimeTraverser.INSTANCE.traverse(stepOutputs,
			OperatorNavigator.INSTANCE, new GraphTraverseListener<Operator<?>>() {
				@Override
				public void nodeTraversed(Operator<?> node) {
					for (JsonStream input : node.getInputs()) {
						successors.put(input.getSource().getOperator(), node);
						successors.putAll(input.getSource().getOperator(), successors.get(node));
					}
				}
			});
		return successors;
	}

	private SopremoModule extractModule(final Set<Operator<?>> postProcessing, Map<JsonStream, Integer> inputId) {
		Multimap<JsonStream, Operator<?>> incomingEdges = getIncomingEdges(postProcessing);
		SopremoModule postProcessingModule = new SopremoModule(incomingEdges.size(), getNumOutputs());
		for (JsonStream input : incomingEdges.keySet())
			inputId.put(input, inputId.size());
		final List<Sink> outputs = getOutputs();
		for (int index = 0; index < outputs.size(); index++)
			postProcessingModule.getOutput(index).setInput(0, getOutput(index).getInput(0));

		for (Operator<?> op : postProcessing) {
			final List<JsonStream> inputs = op.getInputs();
			for (int index = 0; index < inputs.size(); index++) {
				final Integer id = inputId.get(inputs.get(index));
				if (id != null)
					op.setInput(index, postProcessingModule.getInput(id));
			}
		}
		return postProcessingModule;
	}

	private Multimap<JsonStream, Operator<?>> getIncomingEdges(final Set<Operator<?>> partition) {
		Multimap<JsonStream, Operator<?>> incomingEdges =
			Multimaps.newMultimap(new IdentityHashMap<JsonStream, Collection<Operator<?>>>(),
				IdentitySetSupplier.<Operator<?>> getInstance());
		for (Operator<?> op : partition) {
			for (JsonStream input : op.getInputs())
				if (!partition.contains(input.getSource().getOperator()))
					incomingEdges.put(input, op);
		}
		return incomingEdges;
	}

	@SafeVarargs
	private final Multimap<JsonStream, Operator<?>> getOutgoingEdges(final Set<Operator<?>> partition,
			@SuppressWarnings("unchecked") final Set<Operator<?>>... otherPartitions) {
		Multimap<JsonStream, Operator<?>> outgoingEdges =
			Multimaps.newMultimap(new IdentityHashMap<JsonStream, Collection<Operator<?>>>(),
				IdentitySetSupplier.<Operator<?>> getInstance());
		for (Set<Operator<?>> otherPartition : otherPartitions)
			for (Operator<?> op : otherPartition) {
				for (JsonStream input : op.getInputs())
					if (partition.contains(input.getSource().getOperator()))
						outgoingEdges.put(input, op);
			}
		return outgoingEdges;
	}
}
