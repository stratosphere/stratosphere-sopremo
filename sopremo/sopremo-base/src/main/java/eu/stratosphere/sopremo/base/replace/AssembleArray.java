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
package eu.stratosphere.sopremo.base.replace;

import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.pact.TypedSopremoReduce;
import eu.stratosphere.sopremo.type.CachingArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.INumericNode;
import eu.stratosphere.sopremo.type.IStreamNode;
import eu.stratosphere.sopremo.type.JsonUtil;

/**
 * @author Arvid Heise
 */
@InputCardinality(1)
public class AssembleArray extends ElementaryOperator<AssembleArray> {
	/**
	 * Initializes AssembleArray.
	 */
	public AssembleArray() {
		this.setKeyExpressions(0, new ArrayAccess(2));
	}

	public static class Implementation extends TypedSopremoReduce<IArrayNode<?>> {
		private CachingArrayNode<IJsonNode> assembledArray = new CachingArrayNode<IJsonNode>();

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.pact.SopremoReduce#reduce(eu.stratosphere.sopremo.type.IArrayNode,
		 * eu.stratosphere.sopremo.pact.JsonCollector)
		 */
		@Override
		protected void reduce(IStreamNode<IArrayNode<?>> values, JsonCollector out) {

			int replacedCount = 0;
			IArrayNode<?> lastValue = null;
			for (IArrayNode<?> value : values) {
				final IArrayNode<?> arrayValue = value;
				int index = ((INumericNode) arrayValue.get(1)).getIntValue();
				IJsonNode element = arrayValue.get(0);
				SopremoUtil.replaceWithCopy(this.assembledArray, index, element);
				replacedCount++;
				lastValue = arrayValue;
			}

			// cannot be null by definition of reduce
			@SuppressWarnings("null")
			final IArrayNode<?> originalArray = (IArrayNode<?>) lastValue.get(2);
			// check if all values replaced; if not filter array
			if (originalArray.size() == replacedCount) {
				this.assembledArray.setSize(replacedCount);
				out.collect(JsonUtil.asArray(originalArray, this.assembledArray));
			}
		}
	}
}