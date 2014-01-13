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
package eu.stratosphere.pact.common.plan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import eu.stratosphere.api.common.Program;
import eu.stratosphere.api.common.io.FileInputFormat;
import eu.stratosphere.api.common.io.FileOutputFormat;
import eu.stratosphere.api.common.operators.FileDataSink;
import eu.stratosphere.api.common.operators.FileDataSource;
import eu.stratosphere.api.common.operators.GenericDataSink;
import eu.stratosphere.api.common.operators.GenericDataSource;
import eu.stratosphere.api.common.operators.Operator;
import eu.stratosphere.api.common.operators.util.OperatorUtil;
import eu.stratosphere.util.Visitable;
import eu.stratosphere.util.Visitor;
import eu.stratosphere.util.dag.GraphModule;
import eu.stratosphere.util.dag.GraphPrinter;
import eu.stratosphere.util.dag.GraphTraverseListener;
import eu.stratosphere.util.dag.NodePrinter;
import eu.stratosphere.util.dag.OneTimeTraverser;

/**
 * The PactModule is a subgraph of a {@link Program} with an arbitrary but
 * well-defined number of inputs and outputs. It is designed to facilitate
 * modularization and thus to increase the maintainability of large
 * PactPrograms. While the interface of the module are the number of inputs and
 * outputs, the actual implementation consists of several interconnected {@link Operator}s that are connected to the
 * inputs and outputs of the
 * PactModule.
 */
public class PactModule extends GraphModule<Operator, GenericDataSource<?>, GenericDataSink> implements
		Visitable<Operator> {
	/**
	 * Initializes a PactModule having the given name, number of inputs, and
	 * number of outputs.
	 * 
	 * @param numberOfInputs
	 *        the number of inputs
	 * @param numberOfOutputs
	 *        the number of outputs.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PactModule(final int numberOfInputs, final int numberOfOutputs) {
		super(numberOfInputs, numberOfOutputs, OperatorNavigator.INSTANCE);
		for (int index = 0; index < numberOfInputs; index++)
			this.setInput(index,
				new FileDataSource((Class) FileInputFormat.class, String.format("file:///%d", index),
					"Source " + index));
		for (int index = 0; index < numberOfOutputs; index++)
			this.setOutput(index, new FileDataSink((Class) FileOutputFormat.class, String.format("file:///%d", index),
				"Sink " + index));
	}

	/**
	 * Traverses the pact plan, starting from the data outputs that were added
	 * to this program.
	 * 
	 * @see Visitable#accept(Visitor)
	 */
	@Override
	public void accept(final Visitor<Operator> visitor) {
		final OneTimeVisitor<Operator> oneTimeVisitor = new OneTimeVisitor<Operator>(visitor);
		for (final Operator output : this.getAllOutputs())
			output.accept(oneTimeVisitor);
	}

	@Override
	public String toString() {
		final GraphPrinter<Operator> dagPrinter = new GraphPrinter<Operator>();
		dagPrinter.setNodePrinter(new NodePrinter<Operator>() {
			@Override
			public String toString(final Operator node) {
				final int inputIndex = PactModule.this.inputNodes.indexOf(node);
				if (inputIndex != -1)
					return String.format("Input %d", inputIndex);
				final int outputIndex = PactModule.this.outputNodes.indexOf(node);
				if (outputIndex != -1)
					return String.format("Output %d", outputIndex);
				return String.format("%s [%s]", node.getClass().getSimpleName(), node.getName());
			}
		});
		dagPrinter.setWidth(40);
		return dagPrinter.toString(this.getAllOutputs(), OperatorNavigator.INSTANCE);
	}

	/**
	 * Wraps the graph given by the sinks and referenced contracts in a
	 * PactModule.
	 * 
	 * @param sinks
	 *        all sinks that span the graph to wrap
	 * @return a PactModule representing the given graph
	 */
	public static PactModule valueOf(final Collection<? extends Operator> sinks) {
		final List<Operator> inputs = new ArrayList<Operator>();

		OneTimeTraverser.INSTANCE.traverse(sinks, OperatorNavigator.INSTANCE,
			new GraphTraverseListener<Operator>() {
				@Override
				public void nodeTraversed(final Operator node) {
					final List<List<Operator>> contractInputs = OperatorUtil.getInputs(node);
					if (contractInputs.size() == 0)
						inputs.add(node);
					else
						for (final List<Operator> input : contractInputs)
							if (input.size() == 0)
								inputs.add(node);
				};
			});

		final PactModule module = new PactModule(inputs.size(), sinks.size());
		int sinkIndex = 0;
		for (final Operator sink : sinks) {
			if (sink instanceof GenericDataSink)
				module.setOutput(sinkIndex, (GenericDataSink) sink);
			else
				module.getOutput(sinkIndex).addInput(sink);
			sinkIndex++;
		}

		for (int index = 0; index < inputs.size();) {
			final Operator node = inputs.get(index);
			final List<List<Operator>> contractInputs = OperatorUtil.getInputs(node);
			if (contractInputs.isEmpty())
				module.setInput(index++, (GenericDataSource<?>) node);
			else {
				for (int unconnectedIndex = 0; unconnectedIndex < contractInputs.size(); unconnectedIndex++)
					if (contractInputs.get(unconnectedIndex).isEmpty())
						contractInputs.get(unconnectedIndex).add(module.getInput(index++));
				OperatorUtil.setInputs(node, contractInputs);
			}
		}
		return module;
	}

	/**
	 * Wraps the graph given by the sinks and referenced contracts in a
	 * PactModule.
	 * 
	 * @param sinks
	 *        all sinks that span the graph to wrap
	 * @return a PactModule representing the given graph
	 */
	public static PactModule valueOf(final Operator... sinks) {
		return valueOf(Arrays.asList(sinks));
	}

}
