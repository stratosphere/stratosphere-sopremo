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
package eu.stratosphere.sopremo.base;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoReduce;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;

@InputCardinality(min = 1, max = 1)
@Name(adjective = "unique")
public class Unique extends ElementaryOperator<Unique> {
	/**
	 * Initializes Unique.
	 */
	public Unique() {
		this.setKeyExpressions(0, EvaluationExpression.VALUE);
		this.setCombinable(true);
	}

	public static class Implementation extends SopremoReduce {
		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.pact.GenericSopremoReduce#reduce(eu.stratosphere.sopremo.type.IStreamNode,
		 * eu.stratosphere.sopremo.pact.JsonCollector)
		 */
		@Override
		protected void reduce(final IStreamNode<IJsonNode> values, final JsonCollector<IJsonNode> out) {
			out.collect(values.iterator().next());
		}
	}

}