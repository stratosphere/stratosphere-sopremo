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

import eu.stratosphere.api.common.operators.FileDataSource;
import eu.stratosphere.api.common.operators.GenericDataSink;
import eu.stratosphere.api.common.operators.GenericDataSource;
import eu.stratosphere.api.common.operators.util.OperatorUtil;
import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.sopremo.Schema;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.util.IdentityList;
import eu.stratosphere.util.dag.GraphTraverseListener;
import eu.stratosphere.util.dag.OneTimeTraverser;

/**
 * A {@link SopremoModule} that only contains {@link ElementaryOperator}s.
 */
public class ElementarySopremoModule extends SopremoModule {

	/**
	 * Initializes ElementarySopremoModule.
	 * 
	 * @param numberOfInputs
	 * @param numberOfOutputs
	 */
	public ElementarySopremoModule(final int numberOfInputs, final int numberOfOutputs) {
		super(numberOfInputs, numberOfOutputs);
	}

	/**
	 * Initializes ElementarySopremoModule.
	 */
	ElementarySopremoModule() {
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.SopremoModule#asElementary()
	 */
	@Override
	public ElementarySopremoModule asElementary() {
		return this;
	}

	/**
	 * Converts the Sopremo module to a Pact module.
	 * 
	 * @return the converted Pact module
	 */
	public PactModule asPactModule() {
		return PactModule.valueOf(this.assemblePact());
	}

	/**
	 * Assembles the Pacts of the contained Sopremo operators and returns a list of all Pact sinks. These sinks may
	 * either be directly a {@link GenericDataSource} or an unconnected {@link eu.stratosphere.api.common.operators.Operator}.
	 * 
	 * @return a list of Pact sinks
	 */
	public Collection<eu.stratosphere.api.common.operators.Operator> assemblePact() {
		// if(layout == null)
		// layout = SopremoRecordLayout.create(this.schema.getKeyExpressions());
		return new PactAssembler().assemble();
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
	 */
	public Schema getSchema() {
		final Set<EvaluationExpression> keyExpressions = new HashSet<EvaluationExpression>();
		for (final ElementaryOperator<?> operator : this.getReachableNodes())
			keyExpressions.addAll(operator.getAllKeyExpressions());

		return new Schema(new ArrayList<EvaluationExpression>(keyExpressions));
	}

	/**
	 * Wraps the graph given by the sinks and referenced contracts in a ElementarySopremoModule.
	 * 
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

	/**
	 * Wraps the graph given by the sinks and referenced contracts in a ElementarySopremoModule.
	 * 
	 * @param sinks
	 *        all sinks that span the graph to wrap
	 * @return a ElementarySopremoModule representing the given graph
	 */
	public static ElementarySopremoModule valueOf(final Operator<?>... sinks) {
		return valueOf(Arrays.asList(sinks));
	}

	/**
	 * Helper class needed to assemble a Pact program from the {@link PactModule}s of several {@link eu.stratosphere.api.common.operators.Operator}s.
	 */
	private class PactAssembler {
		private final Map<Operator<?>, PactModule> modules = new IdentityHashMap<Operator<?>, PactModule>();

		private final Map<Operator<?>, List<List<eu.stratosphere.api.common.operators.Operator>>> operatorOutputs =
			new IdentityHashMap<Operator<?>, List<List<eu.stratosphere.api.common.operators.Operator>>>();

		public Collection<eu.stratosphere.api.common.operators.Operator> assemble() {
			this.convertDAGToModules();

			this.connectModules();

			final List<eu.stratosphere.api.common.operators.Operator> pactSinks = this.findPACTSinks();

			return pactSinks;
		}

		private void addOutputtingPactInOperator(final Operator<?> operator,
				final eu.stratosphere.api.common.operators.Operator o,
				final List<eu.stratosphere.api.common.operators.Operator> connectedInputs) {
			final int inputIndex =
				new IdentityList<GenericDataSource<?>>(this.modules.get(operator).getInputs()).indexOf(o);
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
			final List<eu.stratosphere.api.common.operators.Operator> outputtingOperators =
				this.operatorOutputs.get(inputSource.getOperator()).get(inputSource.getIndex());
			for (final eu.stratosphere.api.common.operators.Operator outputtingOperator : outputtingOperators)
				if (outputtingOperator instanceof FileDataSource && !(inputSource.getOperator() instanceof Source))
					this.addOutputtingPactInOperator(inputSource.getOperator(), outputtingOperator, connectedInputs);
				else
					connectedInputs.add(outputtingOperator);
		}

		private void connectModules() {
			for (final Entry<Operator<?>, PactModule> operatorModule : this.modules.entrySet()) {
				final Operator<?> operator = operatorModule.getKey();
				final PactModule module = operatorModule.getValue();

				for (final eu.stratosphere.api.common.operators.Operator contract : module.getReachableNodes()) {
					final List<List<eu.stratosphere.api.common.operators.Operator>> inputLists =
						OperatorUtil.getInputs(contract);
					for (int listIndex = 0; listIndex < inputLists.size(); listIndex++) {
						final List<eu.stratosphere.api.common.operators.Operator> connectedInputs =
							new ArrayList<eu.stratosphere.api.common.operators.Operator>();
						final List<eu.stratosphere.api.common.operators.Operator> inputs = inputLists.get(listIndex);
						for (int inputIndex = 0; inputIndex < inputs.size(); inputIndex++)
							this.addOutputtingPactInOperator(operator, inputs.get(inputIndex), connectedInputs);
						inputLists.set(listIndex, connectedInputs);
					}
					OperatorUtil.setInputs(contract, inputLists);
				}
			}
		}

		private void convertDAGToModules() {
			OneTimeTraverser.INSTANCE.traverse(ElementarySopremoModule.this.getAllOutputs(),
				OperatorNavigator.ELEMENTARY, new GraphTraverseListener<ElementaryOperator<?>>() {
					@Override
					public void nodeTraversed(final ElementaryOperator<?> node) {
						SopremoEnvironment.getInstance().getEvaluationContext().setOperatorDescription(node.getName());
						final PactModule module = node.asPactModule();

						PactAssembler.this.modules.put(node, module);
						final List<GenericDataSink> outputFunctions = module.getOutputs();
						final List<List<eu.stratosphere.api.common.operators.Operator>> outputOperators =
							new ArrayList<List<eu.stratosphere.api.common.operators.Operator>>();
						for (final GenericDataSink sink : outputFunctions)
							outputOperators.add(sink.getInputs());
						PactAssembler.this.operatorOutputs.put(node, outputOperators);
					}
				});

			for (final PactModule module : this.modules.values())
				module.validate();
		}

		private List<eu.stratosphere.api.common.operators.Operator> findPACTSinks() {
			final List<eu.stratosphere.api.common.operators.Operator> pactSinks =
				new ArrayList<eu.stratosphere.api.common.operators.Operator>();
			for (final Operator<?> sink : ElementarySopremoModule.this.getAllOutputs())
				for (final GenericDataSink outputFunction : this.modules.get(sink).getAllOutputs())
					if (sink instanceof Sink)
						pactSinks.add(outputFunction);
					else
						pactSinks.addAll(outputFunction.getInputs());
			return pactSinks;
		}
	}
}
