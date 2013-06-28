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

import eu.stratosphere.pact.generic.types.TypePairComparator;
import eu.stratosphere.sopremo.cache.NodeCache;
import eu.stratosphere.sopremo.type.IJsonNode;

public class SopremoRecordPairComparator extends TypePairComparator<SopremoRecord, SopremoRecord>
{
	private final int[] keyFields1, keyFields2;

	private final IJsonNode[] keyHolders1, keyHolders2;

	private final NodeCache[] nodeCache1, nodeCache2;

	private final int numKeys;

	public SopremoRecordPairComparator(int[] keyFieldsReference,
			int[] keyFieldsCandidate) {
		this.numKeys = keyFieldsReference.length;
		if (this.numKeys != keyFieldsCandidate.length) {
			throw new IllegalArgumentException(
				"The arrays describing the key expressions must be of the same length.");
		}
		this.keyFields1 = keyFieldsReference;
		this.keyFields2 = keyFieldsCandidate;

		// instantiate fields to extract keys into
		this.keyHolders1 = new IJsonNode[this.numKeys];
		this.keyHolders2 = new IJsonNode[this.numKeys];
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
	 * @see eu.stratosphere.sopremo.serialization.TypeComparator#setReference(java.lang.Object)
	 */
	@Override
	public void setReference(SopremoRecord reference) {
		for (int index = 0; index < this.numKeys; index++) {
			this.keyHolders1[index] = reference.getKey(this.keyFields1[index], this.nodeCache1[index]);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypeComparator#equalToReference(java.lang.Object)
	 */
	@Override
	public boolean equalToReference(SopremoRecord candidate) {
		for (int index = 0; index < this.numKeys; index++) {
			IJsonNode k = candidate.getKey(this.keyFields2[index], this.nodeCache2[index]);
			if (!k.equals(this.keyHolders1[index]))
				return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.TypePairComparator#compareToReference(java.lang.Object)
	 */
	@Override
	public int compareToReference(SopremoRecord candidate) {
		for (int index = 0; index < this.numKeys; index++) {
			IJsonNode k = candidate.getKey(this.keyFields2[index], this.nodeCache2[index]);
			final int comparison = k.compareTo(this.keyHolders1[index]);
			if (comparison != 0)
				return comparison;
		}
		return 0;
	}
}
