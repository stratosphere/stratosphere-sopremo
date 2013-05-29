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
package eu.stratosphere.sopremo.serialization;

import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * {@link Schema} that specifies a record with only a single node.
 * 
 * @author Arvid Heise
 */
public class DirectSchema extends AbstractSchema {
	public DirectSchema() {
		super(1, IntSets.EMPTY_SET);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.serialization.Schema#indicesOf(eu.stratosphere.sopremo.expressions.EvaluationExpression)
	 */
	@Override
	public IntSet indicesOf(final EvaluationExpression expression) {
		return IntSets.EMPTY_SET;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.AbstractSchema#jsonToRecord(eu.stratosphere.sopremo.type.IJsonNode,
	 * eu.stratosphere.sopremo.serialization.SopremoRecord)
	 */
	@Override
	public void jsonToRecord(IJsonNode value, SopremoRecord target) {
		target.addField(value);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.serialization.AbstractSchema#recordToJson(eu.stratosphere.sopremo.serialization.SopremoRecord
	 * )
	 */
	@Override
	public IJsonNode recordToJson(SopremoRecord record) {
		return record.getField(0);
	}
}
