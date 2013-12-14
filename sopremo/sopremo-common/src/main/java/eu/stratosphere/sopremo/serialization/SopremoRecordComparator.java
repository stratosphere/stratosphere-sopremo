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

	private final SopremoRecordLayout layout;

	private final EvaluationExpression[] keyExpressions;

	/**
	 * Initializes SopremoRecordComparator.
	 * 
	 * @param layout2
	 * @param keyExpressionIndices2
	 * @param ascending2
	 */
	public SopremoRecordComparator(final SopremoRecordLayout layout, final int[] keyExpressionIndices,
			final boolean[] ascending) {
		this.layout = layout;
		this.keyExpressionIndices = keyExpressionIndices;
		this.keyExpressions = new EvaluationExpression[keyExpressionIndices.length];
		this.keys = new IJsonNode[this.keyExpressionIndices.length];
		this.nodeCache1 = new NodeCache[this.keyExpressionIndices.length];
		this.nodeCache2 = new NodeCache[this.keyExpressionIndices.length];
		for (int index = 0; index < this.keyExpressionIndices.length; index++) {
			this.nodeCache1[index] = new NodeCache(CachingNodeFactory.getInstance());
			this.nodeCache2[index] = new NodeCache(CachingNodeFactory.getInstance());
			this.keyExpressions[index] = layout.getExpression(this.keyExpressionIndices[index]);
		}
		this.temp1 = new SopremoRecord();
		this.temp2 = new SopremoRecord();
		this.ascending = ascending;
	}

	/**
	 * Returns the keyExpressions.
	 * 
	 * @return the keyExpressions
	 */
	public EvaluationExpression[] getKeyExpressions() {
		return this.keyExpressions;
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
	public int hash(final SopremoRecord record) {
		final int prime = 37;
		int hash = prime;
		final IJsonNode node = record.getNode();
		if (node == null)
			for (int index = 0; index < this.keyExpressionIndices.length; index++)
				hash =
					hash + prime * record.getKey(this.keyExpressionIndices[index], this.nodeCache2[index]).hashCode();
		else
			for (int index = 0; index < this.keyExpressionIndices.length; index++)
				hash = hash + prime * this.keyExpressions[index].evaluate(node).hashCode();
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#setReference(java.lang.Object)
	 */
	@Override
	public void setReference(final SopremoRecord toCompare) {
		this.reference = toCompare;
		final IJsonNode node = toCompare.getNode();
		if (node == null)
			for (int index = 0; index < this.keyExpressionIndices.length; index++)
				this.keys[index] = this.reference.getKey(this.keyExpressionIndices[index], this.nodeCache1[index]);
		else
			for (int index = 0; index < this.keyExpressionIndices.length; index++)
				this.keys[index] = this.keyExpressions[index].evaluate(node);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#equalToReference(java.lang.Object)
	 */
	@Override
	public boolean equalToReference(final SopremoRecord candidate) {
		final IJsonNode node = candidate.getNode();
		if (node == null) {
			for (int index = 0; index < this.keyExpressionIndices.length; index++)
				if (!candidate.getKey(this.keyExpressionIndices[index], this.nodeCache2[index]).equals(this.keys[index]))
					return false;
		}
		else
			for (int index = 0; index < this.keyExpressionIndices.length; index++)
				if (!this.keyExpressions[index].evaluate(node).equals(this.keys[index]))
					return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#compareToReference(eu.stratosphere.pact.generic.types.
	 * TypeComparator)
	 */
	@Override
	public int compareToReference(final TypeComparator<SopremoRecord> referencedComparator) {
		final SopremoRecordComparator other = (SopremoRecordComparator) referencedComparator;
		for (int index = 0; index < this.nodeCache1.length; index++) {
			final int comparison = other.keys[index].compareTo(this.keys[index]);
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
	public int compare(final DataInputView firstSource, final DataInputView secondSource) throws IOException {
		this.temp1.read(firstSource, this.layout);
		this.temp2.read(secondSource, this.layout);

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
	public boolean isNormalizedKeyPrefixOnly(final int keyBytes) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#putNormalizedKey(java.lang.Object,
	 * eu.stratosphere.nephele.services.memorymanager.MemorySegment, int, int)
	 */
	@Override
	public void putNormalizedKey(final SopremoRecord record, final MemorySegment target, final int offset,
			final int numBytes) {
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#writeWithKeyNormalization(java.lang.Object,
	 * eu.stratosphere.nephele.services.memorymanager.DataOutputView)
	 */
	@Override
	public void writeWithKeyNormalization(final SopremoRecord record, final DataOutputView target) throws IOException {
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.types.TypeComparator#readWithKeyDenormalization(java.lang.Object,
	 * eu.stratosphere.nephele.services.memorymanager.DataInputView)
	 */
	@Override
	public void readWithKeyDenormalization(final SopremoRecord record, final DataInputView source) throws IOException {
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
