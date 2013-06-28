/***********************************************************************************************************************
 *
 * Copyright (C) 2012 by the Stratosphere project (http://stratosphere.eu)
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
package eu.stratosphere.sopremo.serialization;

import java.util.Iterator;

import com.google.common.collect.Iterables;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.common.contract.GenericDataSink;
import eu.stratosphere.pact.common.contract.Ordering;
import eu.stratosphere.pact.common.type.Key;
import eu.stratosphere.pact.common.util.FieldList;
import eu.stratosphere.pact.common.util.Visitor;
import eu.stratosphere.pact.compiler.CompilerException;
import eu.stratosphere.pact.compiler.CompilerPostPassException;
import eu.stratosphere.pact.compiler.plan.candidate.Channel;
import eu.stratosphere.pact.compiler.plan.candidate.DualInputPlanNode;
import eu.stratosphere.pact.compiler.plan.candidate.OptimizedPlan;
import eu.stratosphere.pact.compiler.plan.candidate.PlanNode;
import eu.stratosphere.pact.compiler.plan.candidate.SingleInputPlanNode;
import eu.stratosphere.pact.compiler.plan.candidate.SinkPlanNode;
import eu.stratosphere.pact.compiler.plan.candidate.SourcePlanNode;
import eu.stratosphere.pact.compiler.plan.candidate.UnionPlanNode;
import eu.stratosphere.pact.compiler.postpass.ConflictingFieldTypeInfoException;
import eu.stratosphere.pact.compiler.postpass.GenericRecordPostPass;
import eu.stratosphere.pact.compiler.postpass.MissingFieldTypeInfoException;
import eu.stratosphere.pact.compiler.postpass.OptimizerPostPass;
import eu.stratosphere.pact.compiler.postpass.PostPassUtils;
import eu.stratosphere.pact.generic.contract.DualInputContract;
import eu.stratosphere.pact.generic.contract.SingleInputContract;
import eu.stratosphere.pact.runtime.plugable.pactrecord.PactRecordComparatorFactory;
import eu.stratosphere.pact.runtime.plugable.pactrecord.PactRecordPairComparatorFactory;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Post pass implementation for the PactRecord data model. Does only type inference and creates
 * serializers and comparators.
 */
public class SopremoRecordPostPass implements OptimizerPostPass {

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.pact.compiler.postpass.OptimizerPostPass#postPass(eu.stratosphere.pact.compiler.plan.candidate
	 * .OptimizedPlan)
	 */
	@Override
	public void postPass(OptimizedPlan plan) {
		final Configuration parameters =
			Iterables.getFirst(plan.getDataSinks(), null).getPactContract().getParameters();
		final SopremoRecordLayout layout = SopremoUtil.getLayout(parameters);

		plan.accept(new Visitor<PlanNode>() {
			@Override
			public boolean preVisit(PlanNode node) {
				processNode(layout, node);
				return true;
			}

			@Override
			public void postVisit(PlanNode visitable) {
			}
		});
	}

	protected void processNode(final SopremoRecordLayout layout, PlanNode node) {
		if (node instanceof SingleInputPlanNode) {
			SingleInputPlanNode sn = (SingleInputPlanNode) node;
			// parameterize the node's driver strategy
			if (sn.getDriverStrategy().requiresComparator()) {
				sn.setComparator(createComparator(sn.getKeys(), sn.getSortOrders(), layout));
			}
			// processChannel(layout, sn.getInput());
		} else if (node instanceof DualInputPlanNode) {
			DualInputPlanNode dn = (DualInputPlanNode) node;
			// parameterize the node's driver strategy
			if (dn.getDriverStrategy().requiresComparator()) {
				dn.setComparator1(createComparator(dn.getKeysForInput1(), dn.getSortOrders(), layout));
				dn.setComparator2(createComparator(dn.getKeysForInput2(), dn.getSortOrders(), layout));
				dn.setPairComparator(SopremoRecordPairComparatorFactory.get());
			}
			// processChannel(layout, dn.getInput1());
			// processChannel(layout, dn.getInput2());
		} else if (node instanceof SourcePlanNode) {
			((SourcePlanNode) node).setSerializer(new SopremoRecordSerializerFactory(layout));
		}

		final Iterator<Channel> inputs = node.getInputs();
		while (inputs.hasNext())
			processChannel(layout, inputs.next());
	}

	private void processChannel(SopremoRecordLayout layout, Channel channel) {
		channel.setSerializer(new SopremoRecordSerializerFactory(layout));
		if (channel.getLocalStrategy().requiresComparator())
			channel.setLocalStrategyComparator(createComparator(channel.getLocalStrategyKeys(),
				channel.getLocalStrategySortOrder(), layout));
		if (channel.getShipStrategy().requiresComparator())
			channel.setShipStrategyComparator(createComparator(channel.getShipStrategyKeys(),
				channel.getShipStrategySortOrder(), layout));
	}

	private SopremoRecordComparatorFactory createComparator(FieldList fields, boolean[] directions,
			SopremoRecordLayout layout) {
		return new SopremoRecordComparatorFactory(layout, fields.toArray(), directions);
	}

}
