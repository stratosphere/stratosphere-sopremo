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
package eu.stratosphere.sopremo.operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import eu.stratosphere.pact.common.contract.FileDataSource;
import eu.stratosphere.pact.common.contract.GenericDataSink;
import eu.stratosphere.pact.common.contract.GenericDataSource;
import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.pact.generic.contract.Contract;
import eu.stratosphere.pact.generic.contract.ContractUtil;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.Schema;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;
import eu.stratosphere.util.IdentityList;
import eu.stratosphere.util.dag.GraphTraverseListener;
import eu.stratosphere.util.dag.OneTimeTraverser;

/**
 * A {@link SopremoModule} that only contains {@link ElementaryOperator}s.
 * 
 * @author Arvid Heise
 */
public class ElementarySopremoModule extends SopremoModule {

	private Schema schema;

	/**
	 * Initializes ElementarySopremoModule.
	 * 
	 * @param name
	 * @param numberOfInputs
	 * @param numberOfOutputs
	 */
	public ElementarySopremoModule(final int numberOfInputs, final int numberOfOutputs) {
		super(numberOfInputs, numberOfOutputs);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.SopremoModule#asElementary()
	 */
	@Override
	public ElementarySopremoModule asElementary(final EvaluationContext context) {
		return this;
	}

	/**
	 * Converts the Sopremo module to a Pact module.
	 * 
	 * @param context
	 *        the evaluation context of the Pact contracts
	 * @return the converted Pact module
	 */
	public PactModule asPactModule(final EvaluationContext context) {
		return PactModule.valueOf(this.assemblePact(context));
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.util.dag.GraphModule#getReachableNodes()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<? extends ElementaryOperator<?>> getReachableNodes() {
		return (Iterable<? extends ElementaryOperator<?>>) super.getReachableNodes();
	}

	/**
	 * Wraps the graph given by the sinks and referenced contracts in a ElementarySopremoModule.
	 * 
	 * @param name
	 *        the name of the ElementarySopremoModule
	 * @param sinks
	 *        all sinks that span the graph to wrap
	 * @return a ElementarySopremoModule representing the given graph
	 */
	public static ElementarySopremoModule valueOf(final Operator<?>... sinks) {
		return valueOf(Arrays.asList(sinks));
	}

	/**
	 * Wraps the graph given by the sinks and referenced contracts in a ElementarySopremoModule.
	 * 
	 * @param name
	 *        the name of the ElementarySopremoModule
	 * @param sinks
	 *        all sinks that span the graph to wrap
	 * @return a ElementarySopremoModule representing the given graph
	 */
	public static ElementarySopremoModule valueOf(final Collection<? extends Operator<?>> sinks) {
		final List<Operator<?>> inputs = findInputs(sinks);
		final ElementarySopremoModule module = new ElementarySopremoModule(inputs.size(), sinks.size());
		connectOutputs(module, sinks);
		connectInputs(module, inputs);
		return module;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.SopremoModule#clone()
	 */
	@Override
	public ElementarySopremoModule clone() {
		final ElementarySopremoModule module =
			new ElementarySopremoModule(this.getNumInputs(), this.getNumOutputs());
		module.copyPropertiesFrom(this);
		return module;
	}

	/**
	 * Helper class needed to assemble a Pact program from the {@link PactModule}s of several {@link Operator<?>}s.
	 * 
	 * @author Arvid Heise
	 */
	private class PactAssembler {
		private final Map<Operator<?>, PactModule> modules = new IdentityHashMap<Operator<?>, PactModule>();

		private final Map<Operator<?>, List<List<Contract>>> operatorOutputs =
			new IdentityHashMap<Operator<?>, List<List<Contract>>>();

		private final EvaluationContext context;

		public PactAssembler(final EvaluationContext context) {
			this.context = context;
		}

		public Collection<Contract> assemble(SopremoRecordLayout layout) {
			this.convertDAGToModules(layout);

			this.connectModules();

			final List<Contract> pactSinks = this.findPACTSinks();

			return pactSinks;
		}

		private void connectModules() {
			for (final Entry<Operator<?>, PactModule> operatorModule : this.modules.entrySet()) {
				final Operator<?> operator = operatorModule.getKey();
				final PactModule module = operatorModule.getValue();

				for (final Contract contract : module.getReachableNodes()) {
					final List<List<Contract>> inputLists = ContractUtil.getInputs(contract);
					for (int listIndex = 0; listIndex < inputLists.size(); listIndex++) {
						final List<Contract> connectedInputs = new ArrayList<Contract>();
						final List<Contract> inputs = inputLists.get(listIndex);
						for (int inputIndex = 0; inputIndex < inputs.size(); inputIndex++)
							this.addOutputtingPactInOperator(operator, inputs.get(inputIndex), connectedInputs);
						inputLists.set(listIndex, connectedInputs);
					}
					ContractUtil.setInputs(contract, inputLists);
				}
			}
		}

		private void convertDAGToModules(final SopremoRecordLayout layout) {
			// final Schema schema = getSchema();
			OneTimeTraverser.INSTANCE.traverse(ElementarySopremoModule.this.getAllOutputs(),
				OperatorNavigator.INSTANCE, new GraphTraverseListener<Operator<?>>() {
					@Override
					public void nodeTraversed(final Operator<?> node) {
						EvaluationContext context = PactAssembler.this.context.clone();
						if (ElementarySopremoModule.this.getName() != null)
							context.setOperatorDescription(ElementarySopremoModule.this.getName());
						else
							context.setOperatorDescription(node.toString());
						final PactModule module = node.asPactModule(context, layout);

						PactAssembler.this.modules.put(node, module);
						final List<GenericDataSink> outputStubs = module.getOutputs();
						final List<List<Contract>> outputContracts = new ArrayList<List<Contract>>();
						for (GenericDataSink sink : outputStubs)
							outputContracts.add(sink.getInputs());
						PactAssembler.this.operatorOutputs.put(node, outputContracts);
					}
				});

			for (final PactModule module : this.modules.values())
				module.validate();
		}

		private void addOutputtingPactInOperator(final Operator<?> operator, final Contract o,
				final List<Contract> connectedInputs) {
			int inputIndex = new IdentityList<GenericDataSource<?>>(this.modules.get(operator).getInputs()).indexOf(o);
			// final List<FileDataSource> inputPacts =
			// this.modules.get(operator).getInputs();
			// for (int index = 0; index < inputPacts.size(); index++)
			// if (inputPacts.get(index) == o) {
			// inputIndex = index;
			// break;
			// }

			if (inputIndex >= operator.getInputs().size() || inputIndex == -1) {
				connectedInputs.add(o);
				return;
			}

			final Operator.Output inputSource = operator.getInputs().get(inputIndex).getSource();
			final List<Contract> outputtingContracts = this.operatorOutputs.get(inputSource.getOperator()).get(
				inputSource.getIndex());
			for (final Contract outputtingContract : outputtingContracts)
				if (outputtingContract instanceof FileDataSource && !(inputSource.getOperator() instanceof Source))
					this.addOutputtingPactInOperator(inputSource.getOperator(), outputtingContract, connectedInputs);
				else
					connectedInputs.add(outputtingContract);
		}

		private List<Contract> findPACTSinks() {
			final List<Contract> pactSinks = new ArrayList<Contract>();
			for (final Operator<?> sink : ElementarySopremoModule.this.getAllOutputs())
				for (final GenericDataSink outputStub : this.modules.get(sink).getAllOutputs())
					if (sink instanceof Sink)
						pactSinks.add(outputStub);
					else
						pactSinks.addAll(outputStub.getInputs());
			return pactSinks;
		}
	}

	/**
	 * Assembles the Pacts of the contained Sopremo operators and returns a list of all Pact sinks. These sinks may
	 * either be directly a {@link FileDataSinkContract} or an unconnected {@link Contract}.
	 * 
	 * @param context
	 *        the evaluation context of the Pact contracts
	 * @return a list of Pact sinks
	 */
	public Collection<Contract> assemblePact(final EvaluationContext context) {
		return new PactAssembler(context).assemble(SopremoRecordLayout.create(this.schema.getKeyExpressions()));
	}

	/**
	 * @param schemaFactory
	 */
	public void inferSchema() {
		final Set<EvaluationExpression> keyExpressions = new HashSet<EvaluationExpression>();
		for (final ElementaryOperator<?> operator : this.getReachableNodes()) {
			for (final List<? extends EvaluationExpression> expressions : operator.getAllKeyExpressions())
				keyExpressions.addAll(expressions);
		}

		this.schema = new Schema(new ArrayList<EvaluationExpression>(keyExpressions));
	}

	/**
	 * Returns the previously inferred schema.
	 * 
	 * @return the schema
	 */
	public Schema getSchema() {
		return this.schema;
	}
}
