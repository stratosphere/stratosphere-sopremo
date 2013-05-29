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
package eu.stratosphere.sopremo.expressions;

import java.io.IOException;

import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.NullNode;

/**
 * Merges several arrays by taking the first non-null value for each respective array.
 * 
 * @author Arvid Heise
 */
@OptimizerHints(scope = Scope.ARRAY, transitive = true, minNodes = 1, maxNodes = OptimizerHints.UNBOUND, iterating = true)
public class ArrayMerger extends EvaluationExpression {
	private final transient IArrayNode<IJsonNode> result = new ArrayNode<IJsonNode>();

	@Override
	public IJsonNode evaluate(final IJsonNode node) {
		this.result.clear();

		for (final IJsonNode nextNode : (IArrayNode<?>) node)
			if (nextNode != NullNode.getInstance()) {
				final IArrayNode<?> array = (IArrayNode<?>) nextNode;
				for (int index = 0; index < array.size(); index++)
					if (this.result.size() <= index)
						this.result.add(array.get(index));
					else if (this.isNull(this.result.get(index)) && !this.isNull(array.get(index)))
						this.result.set(index, array.get(index));
			}

		return this.result;
	}

	private boolean isNull(final IJsonNode value) {
		return value == null || value.isNull();
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append("[*]+...+[*]");
	}

}
