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

import eu.stratosphere.api.common.typeutils.TypePairComparator;
import eu.stratosphere.sopremo.cache.NodeCache;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.type.IJsonNode;

public class SopremoRecordPairComparator extends TypePairComparator<SopremoRecord, SopremoRecord> {
	private final int[] keyFields1, keyFields2;

	private final EvaluationExpression[] keyExpressions1, keyExpressions2;

	private final IJsonNode[] keyHolders1;

	private final NodeCache[] nodeCache1, nodeCache2;

	private final int numKeys;

	private final static boolean DEBUG = false & SopremoUtil.DEBUG;

	public SopremoRecordPairComparator(final int[] keyFieldsReference, final EvaluationExpression[] keyExpressions1,
			final int[] keyFieldsCandidate,
			final EvaluationExpression[] keyExpressions2) {
		this.numKeys = keyFieldsReference.length;
		if (this.numKeys != keyFieldsCandidate.length)
			throw new IllegalArgumentException(
				"The arrays describing the key expressions must be of the same length.");
		this.keyFields1 = keyFieldsReference;
		this.keyFields2 = keyFieldsCandidate;
		this.keyExpressions1 = keyExpressions1;
		this.keyExpressions2 = keyExpressions2;

		// instantiate fields to extract keys into
		this.keyHolders1 = new IJsonNode[this.numKeys];
		this.nodeCache1 = new NodeCache[this.numKeys];
		this.nodeCache2 = new NodeCache[this.numKeys];

		for (int index = 0; index < this.numKeys; index++) {
			this.nodeCache1[index] = new NodeCache(CachingNodeFactory.getInstance());
			this.nodeCache2[index] = new NodeCache(CachingNodeFactory.getInstance());
		}
	}

	// --------------------------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypePairComparator#compareToReference(java.lang.Object)
	 */
	@Override
	public int compareToReference(final SopremoRecord candidate) {
		final IJsonNode node = candidate.getNode();
		if (node == null)
			for (int index = 0; index < this.numKeys; index++) {
				final IJsonNode k = candidate.getKey(this.keyFields2[index], this.nodeCache2[index]);
				final int comparison = k.compareTo(this.keyHolders1[index]);
				if (DEBUG)
					SopremoUtil.LOG.debug(String.format("pair#compareToReference1: %s <=> %s = %d", k,
						this.keyHolders1[index], comparison));
				if (comparison != 0)
					return comparison;
			}
		else
			for (int index = 0; index < this.numKeys; index++) {
				final IJsonNode k = this.keyExpressions2[index].evaluate(node);
				final int comparison = k.compareTo(this.keyHolders1[index]);
				if (DEBUG)
					SopremoUtil.LOG.debug(String.format("pair#compareToReference1: %s <=> %s = %d", k,
						this.keyHolders1[index], comparison));
				if (comparison != 0)
					return comparison;
			}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeComparator#equalToReference(java.lang.Object)
	 */
	@Override
	public boolean equalToReference(final SopremoRecord candidate) {
		final IJsonNode node = candidate.getNode();
		if (node == null)
			for (int index = 0; index < this.numKeys; index++) {
				final IJsonNode k = candidate.getKey(this.keyFields2[index], this.nodeCache2[index]);
				if (DEBUG)
					SopremoUtil.LOG.debug(String.format("pair#equalToReference1: %s <=> %s = %s", k,
						this.keyHolders1[index], k.equals(this.keyHolders1[index])));
				if (!k.equals(this.keyHolders1[index]))
					return false;
			}
		else
			for (int index = 0; index < this.numKeys; index++) {
				final IJsonNode k = this.keyExpressions2[index].evaluate(node);
				if (DEBUG)
					SopremoUtil.LOG.debug(String.format("pair#equalToReference2: %s <=> %s = %s", k,
						this.keyHolders1[index], k.equals(this.keyHolders1[index])));
				if (!k.equals(this.keyHolders1[index]))
					return false;
			}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeComparator#setReference(java.lang.Object)
	 */
	@Override
	public void setReference(final SopremoRecord reference) {
		final IJsonNode node = reference.getNode();
		if (node == null)
			for (int index = 0; index < this.numKeys; index++)
				this.keyHolders1[index] = reference.getKey(this.keyFields1[index], this.nodeCache1[index]);
		else
			for (int index = 0; index < this.numKeys; index++)
				this.keyHolders1[index] =
					SopremoUtil.copyInto(this.keyExpressions1[index].evaluate(node), this.nodeCache1[index]);
	}
}
