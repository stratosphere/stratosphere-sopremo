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

import java.io.IOException;

import eu.stratosphere.nephele.services.memorymanager.DataInputView;
import eu.stratosphere.nephele.services.memorymanager.DataOutputView;
import eu.stratosphere.nephele.services.memorymanager.MemorySegment;
import eu.stratosphere.pact.generic.types.TypeComparator;
import eu.stratosphere.sopremo.cache.NodeCache;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.type.IJsonNode;

public final class SopremoRecordComparator extends TypeComparator<SopremoRecord> {
	private SopremoRecord reference;

	private final int[] keyExpressionIndices;

	private final NodeCache[] nodeCache1, nodeCache2;

	private final IJsonNode[] keys;

	private final SopremoRecord temp1, temp2;

	private final boolean[] ascending;

	private SopremoRecordLayout layout;

	public SopremoRecordComparator(SopremoRecordLayout layout, EvaluationExpression[] keyExpressions,
			boolean[] ascending) {
		this(layout, layout.getIndices(keyExpressions), ascending);
	}

	/**
	 * Initializes SopremoRecordComparator.
	 * 
	 * @param layout2
	 * @param keyExpressionIndices2
	 * @param ascending2
	 */
	public SopremoRecordComparator(SopremoRecordLayout layout, int[] keyExpressionIndices, boolean[] ascending) {
		this.layout = layout;
		this.keyExpressionIndices = keyExpressionIndices;
		this.keys = new IJsonNode[this.keyExpressionIndices.length];
		this.nodeCache1 = new NodeCache[this.keyExpressionIndices.length];
		this.nodeCache2 = new NodeCache[this.keyExpressionIndices.length];
		for (int index = 0; index < this.keyExpressionIndices.length; index++) {
			this.nodeCache1[index] = new NodeCache(CachingNodeFactory.getInstance());
			this.nodeCache2[index] = new NodeCache(CachingNodeFactory.getInstance());
		}
		this.temp1 = new SopremoRecord(layout);
		this.temp2 = new SopremoRecord(layout);
		this.ascending = ascending;
	}

	/**
	 * Returns the layout.
	 * 
	 * @return the layout
	 */
	public SopremoRecordLayout getLayout() {
		return this.layout;
	}

	/**
	 * Returns the keyExpressionIndices.
	 * 
	 * @return the keyExpressionIndices
	 */
	public int[] getKeyExpressionIndices() {
		return this.keyExpressionIndices;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#hash(java.lang.Object)
	 */
	@Override
	public int hash(SopremoRecord record) {
		final int prime = 37;
		int hash = prime;
		for (int index = 0; index < this.nodeCache2.length; index++)
			hash = hash + prime * record.getKey(this.keyExpressionIndices[index], this.nodeCache2[index]).hashCode();
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#setReference(java.lang.Object)
	 */
	@Override
	public void setReference(SopremoRecord toCompare) {
		this.reference = toCompare;
		for (int index = 0; index < this.nodeCache1.length; index++)
			this.keys[index] = this.reference.getKey(this.keyExpressionIndices[index], this.nodeCache1[index]);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#equalToReference(java.lang.Object)
	 */
	@Override
	public boolean equalToReference(SopremoRecord candidate) {
		for (int index = 0; index < this.nodeCache1.length; index++)
			if (!candidate.getKey(this.keyExpressionIndices[index], this.nodeCache2[index]).equals(this.keys[index]))
				return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#compareToReference(eu.stratosphere.pact.generic.types.
	 * TypeComparator)
	 */
	@Override
	public int compareToReference(TypeComparator<SopremoRecord> referencedComparator) {
		SopremoRecordComparator other = (SopremoRecordComparator) referencedComparator;
		for (int index = 0; index < this.nodeCache1.length; index++) {
			int comparison = other.keys[index].compareTo(this.keys[index]);
			if (comparison != 0)
				return comparison;
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#compare(eu.stratosphere.nephele.services.memorymanager.
	 * DataInputView, eu.stratosphere.nephele.services.memorymanager.DataInputView)
	 */
	@Override
	public int compare(DataInputView firstSource, DataInputView secondSource) throws IOException {
		this.temp1.read(firstSource);
		this.temp2.read(secondSource);

		for (int index = 0; index < this.keyExpressionIndices.length; index++) {
			final IJsonNode k1 = this.temp1.getKey(this.keyExpressionIndices[index], this.nodeCache1[index]);
			final IJsonNode k2 = this.temp2.getKey(this.keyExpressionIndices[index], this.nodeCache2[index]);

			final int comparison = k1.compareTo(k2);
			if (comparison != 0)
				return this.ascending[index] ? comparison : -comparison;
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#supportsNormalizedKey()
	 */
	@Override
	public boolean supportsNormalizedKey() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#supportsSerializationWithKeyNormalization()
	 */
	@Override
	public boolean supportsSerializationWithKeyNormalization() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#getNormalizeKeyLen()
	 */
	@Override
	public int getNormalizeKeyLen() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#isNormalizedKeyPrefixOnly(int)
	 */
	@Override
	public boolean isNormalizedKeyPrefixOnly(int keyBytes) {
		return false;
	}

	/* (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#putNormalizedKey(java.lang.Object, eu.stratosphere.nephele.services.memorymanager.MemorySegment, int, int)
	 */
	@Override
	public void putNormalizedKey(SopremoRecord record, MemorySegment target, int offset, int numBytes) {
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#writeWithKeyNormalization(java.lang.Object,
	 * eu.stratosphere.nephele.services.memorymanager.DataOutputView)
	 */
	@Override
	public void writeWithKeyNormalization(SopremoRecord record, DataOutputView target) throws IOException {
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#readWithKeyDenormalization(java.lang.Object,
	 * eu.stratosphere.nephele.services.memorymanager.DataInputView)
	 */
	@Override
	public void readWithKeyDenormalization(SopremoRecord record, DataInputView source) throws IOException {
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#invertNormalizedKey()
	 */
	@Override
	public boolean invertNormalizedKey() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#duplicate()
	 */
	@Override
	public TypeComparator<SopremoRecord> duplicate() {
		return new SopremoRecordComparator(this.layout, this.keyExpressionIndices, this.ascending);
	}

}
