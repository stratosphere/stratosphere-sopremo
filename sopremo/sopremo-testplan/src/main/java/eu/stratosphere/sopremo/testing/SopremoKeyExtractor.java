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
package eu.stratosphere.sopremo.testing;

import eu.stratosphere.core.testing.KeyExtractor;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.serialization.SopremoRecord;

/**
 */
public class SopremoKeyExtractor implements KeyExtractor<SopremoRecord> {
	private final EvaluationExpression[] expressions;

	public SopremoKeyExtractor(final EvaluationExpression[] expressions) {
		this.expressions = expressions;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.core.testing.KeyExtractor#fill(java.lang.Comparable<?>[],
	 * eu.stratosphere.nephele.types.Record)
	 */
	@Override
	public void fill(final Comparable<?>[] keys, final SopremoRecord record) {
		for (int index = 0; index < keys.length; index++)
			keys[index] = this.expressions[index].evaluate(record.getNode());
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.core.testing.KeyExtractor#getKeySize()
	 */
	@Override
	public int getNumKeys() {
		return this.expressions.length;
	}

}
