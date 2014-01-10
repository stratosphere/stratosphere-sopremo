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
package eu.stratosphere.sopremo.serialization;

import eu.stratosphere.api.common.operators.DualInputOperator;
import eu.stratosphere.api.common.operators.GenericDataSink;
import eu.stratosphere.api.common.operators.Ordering;
import eu.stratosphere.api.common.operators.SingleInputOperator;
import eu.stratosphere.api.common.operators.util.FieldList;
import eu.stratosphere.api.common.typeutils.TypeComparatorFactory;
import eu.stratosphere.api.common.typeutils.TypePairComparatorFactory;
import eu.stratosphere.api.common.typeutils.TypeSerializerFactory;
import eu.stratosphere.compiler.CompilerException;
import eu.stratosphere.compiler.CompilerPostPassException;
import eu.stratosphere.compiler.plan.Channel;
import eu.stratosphere.compiler.plan.DualInputPlanNode;
import eu.stratosphere.compiler.plan.OptimizedPlan;
import eu.stratosphere.compiler.plan.PlanNode;
import eu.stratosphere.compiler.plan.SingleInputPlanNode;
import eu.stratosphere.compiler.plan.SinkPlanNode;
import eu.stratosphere.compiler.postpass.ConflictingFieldTypeInfoException;
import eu.stratosphere.compiler.postpass.GenericFlatTypePostPass;
import eu.stratosphere.compiler.postpass.MissingFieldTypeInfoException;
import eu.stratosphere.sopremo.operator.PlanWithSopremoPostPass;
import eu.stratosphere.sopremo.pact.SopremoCoGroupOperator;
import eu.stratosphere.sopremo.pact.SopremoReduceOperator;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 */
public class SopremoRecordPostPass extends GenericFlatTypePostPass<Class<? extends IJsonNode>, SopremoRecordSchema> {

	private SopremoRecordLayout layout;

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.compiler.postpass.GenericRecordPostPass#createEmptySchema()
	 */
	@Override
	protected SopremoRecordSchema createEmptySchema() {
		return new SopremoRecordSchema();
	}

	private void addOrderingToSchema(final Ordering o, final SopremoRecordSchema schema) {
		for (int i = 0; i < o.getNumberOfFields(); i++)
			schema.add(o.getFieldNumber(i));
	}

	@Override
	protected void getSinkSchema(final SinkPlanNode sinkPlanNode, final SopremoRecordSchema schema)
			throws CompilerPostPassException {
		final GenericDataSink sink = sinkPlanNode.getSinkNode().getPactContract();
		final Ordering partitioning = sink.getPartitionOrdering();
		final Ordering sorting = sink.getLocalOrder();

		if (partitioning != null)
			this.addOrderingToSchema(partitioning, schema);
		if (sorting != null)
			this.addOrderingToSchema(sorting, schema);
	}

	@Override
	protected void getSingleInputNodeSchema(final SingleInputPlanNode node, final SopremoRecordSchema schema)
			throws CompilerPostPassException, ConflictingFieldTypeInfoException
	{
		// check that we got the right types
		final SingleInputOperator<?> contract = node.getSingleInputNode().getPactContract();

		// add the information to the schema
		final int[] localPositions = contract.getKeyColumns(0);
		for (int i = 0; i < localPositions.length; i++)
			schema.add(localPositions[i]);

		// this is a temporary fix, we should solve this more generic
		if (contract instanceof SopremoReduceOperator) {
			final Ordering groupOrder = ((SopremoReduceOperator) contract).getInnerGroupOrder();
			if (groupOrder != null)
				this.addOrderingToSchema(groupOrder, schema);
		}
	}

	@Override
	protected void getDualInputNodeSchema(final DualInputPlanNode node, final SopremoRecordSchema input1Schema,
			final SopremoRecordSchema input2Schema)
	{
		// add the nodes local information. this automatically consistency checks
		final DualInputOperator<?> contract = node.getTwoInputNode().getPactContract();

		final int[] localPositions1 = contract.getKeyColumns(0);
		final int[] localPositions2 = contract.getKeyColumns(1);

		if (localPositions1.length != localPositions2.length)
			throw new CompilerException(
				"Error: The keys for the first and second input have a different number of fields.");

		for (int i = 0; i < localPositions1.length; i++)
			input1Schema.add(localPositions1[i]);
		for (int i = 0; i < localPositions2.length; i++)
			input2Schema.add(localPositions2[i]);

		// this is a temporary fix, we should solve this more generic
		if (contract instanceof SopremoCoGroupOperator) {
			final Ordering groupOrder1 = ((SopremoCoGroupOperator) contract).getFirstInnerGroupOrdering();
			final Ordering groupOrder2 = ((SopremoCoGroupOperator) contract).getSecondInnerGroupOrdering();

			if (groupOrder1 != null)
				this.addOrderingToSchema(groupOrder1, input1Schema);
			if (groupOrder2 != null)
				this.addOrderingToSchema(groupOrder2, input2Schema);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.compiler.postpass.GenericRecordPostPass#createSerializer(eu.stratosphere.compiler.postpass
	 * .AbstractSchema)
	 */
	@Override
	protected TypeSerializerFactory<?> createSerializer(final SopremoRecordSchema schema)
			throws MissingFieldTypeInfoException {
		return new SopremoRecordSerializerFactory(this.layout);
//		return new SopremoRecordSerializerFactory(this.layout.project(schema.getUsedKeys().toIntArray()));
	}

	{
		this.setPropagateParentSchemaDown(false);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.compiler.postpass.GenericRecordPostPass#postPass(eu.stratosphere.compiler.dag.candidate
	 * .OptimizedPlan)
	 */
	@Override
	public void postPass(final OptimizedPlan plan) {
		this.layout = ((PlanWithSopremoPostPass) plan.getOriginalPactPlan()).getLayout();

		super.postPass(plan);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.compiler.postpass.GenericRecordPostPass#createComparator(eu.stratosphere.util
	 * .FieldList, boolean[], eu.stratosphere.compiler.postpass.AbstractSchema)
	 */
	@Override
	protected TypeComparatorFactory<?> createComparator(final FieldList fields, final boolean[] directions,
			final SopremoRecordSchema schema) {
//		final int[] usedKeys = schema.getUsedKeys().toIntArray();
//		final int[] sortFields = fields.toArray();
//
//		for (int index = 0; index < sortFields.length; index++)
//			sortFields[index] = Arrays.binarySearch(usedKeys, sortFields[index]);
//		return new SopremoRecordComparatorFactory(this.layout.project(usedKeys), sortFields, directions);
		return new SopremoRecordComparatorFactory(this.layout, fields.toArray(), directions);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.compiler.postpass.GenericRecordPostPass#traverse(eu.stratosphere.compiler.dag.candidate
	 * .PlanNode, eu.stratosphere.compiler.postpass.AbstractSchema, boolean)
	 */
	@Override
	protected void traverse(final PlanNode node, final SopremoRecordSchema parentSchema, final boolean createUtilities) {
		// FIXME: workaround for Stratosphere #206
		if (node instanceof SinkPlanNode)
			this.setOrdering(((SingleInputPlanNode) node).getInput(),
				((GenericDataSink) node.getPactContract()).getLocalOrder());
		else if (node.getPactContract() instanceof SopremoReduceOperator)
			this.setOrdering(((SingleInputPlanNode) node).getInput(),
				((SopremoReduceOperator) node.getPactContract()).getInnerGroupOrder());
		else if (node.getPactContract() instanceof SopremoCoGroupOperator) {
			this.setOrdering(((DualInputPlanNode) node).getInput1(),
				((SopremoCoGroupOperator) node.getPactContract()).getFirstInnerGroupOrdering());
			this.setOrdering(((DualInputPlanNode) node).getInput2(),
				((SopremoCoGroupOperator) node.getPactContract()).getSecondInnerGroupOrdering());
		}
		super.traverse(node, parentSchema, createUtilities);
	}

	private void setOrdering(final Channel input, final Ordering localOrder) {
		if (localOrder != null) {
			input.getLocalProperties().setOrdering(localOrder);
			input.setLocalStrategy(input.getLocalStrategy(), new FieldList(localOrder.getFieldPositions()),
				localOrder.getFieldSortDirections());
		}
	}

	//
	// private SopremoRecordLayout getProjectedLayout(BitSet keyIndices) {
	// SopremoRecordLayout layout = this.projectedLayouts.get(keyIndices);
	// if (layout == null)
	// this.projectedLayouts.put(keyIndices, layout = this.layout.project(keyIndices));
	// return layout;
	// }

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.compiler.postpass.GenericRecordPostPass#createPairComparator(eu.stratosphere.pact.common
	 * .util.FieldList, eu.stratosphere.util.FieldList, boolean[],
	 * eu.stratosphere.compiler.postpass.AbstractSchema, eu.stratosphere.compiler.postpass.AbstractSchema)
	 */
	@Override
	protected TypePairComparatorFactory<?, ?> createPairComparator(final FieldList fields1, final FieldList fields2,
			final boolean[] sortDirections,
			final SopremoRecordSchema schema1, final SopremoRecordSchema schema2) throws MissingFieldTypeInfoException {
		return new SopremoRecordPairComparatorFactory();
	}

}
