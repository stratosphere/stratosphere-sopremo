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

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.util.AppendUtil;

/**
 */
@DefaultSerializer(SopremoRecordLayout.KryoSerializer.class)
public class SopremoRecordLayout extends AbstractSopremoType {

	/**
	 * 
	 */
	public static final int VALUE_INDEX = Integer.MAX_VALUE;

	public final static SopremoRecordLayout EMPTY = SopremoRecordLayout.create();

	/**
	 * 
	 */
	private static final int UNKNOWN_KEY_EXPRESSION = -1;

	private final transient Object2IntMap<EvaluationExpression> indexedDirectDataExpression =
			new Object2IntOpenHashMap<EvaluationExpression>(), indexedCalculatedKeyExpressions =
			new Object2IntOpenHashMap<EvaluationExpression>();

	private final EvaluationExpression[] directDataExpression, calculatedKeyExpressions;

	private final transient ExpressionIndex expressionIndex;

	/**
	 * Initializes SopremoRecordLayout.
	 */
	public SopremoRecordLayout(final ExpressionIndex expressionIndex,
			final EvaluationExpression[] directDataExpression,
			final EvaluationExpression[] calculatedKeyExpressions) {
		this.expressionIndex = expressionIndex;
		this.directDataExpression = directDataExpression;
		this.calculatedKeyExpressions = calculatedKeyExpressions;

		this.index(directDataExpression, calculatedKeyExpressions);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		AppendUtil.append(appendable, this.directDataExpression);
		AppendUtil.append(appendable, this.calculatedKeyExpressions);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final SopremoRecordLayout other = (SopremoRecordLayout) obj;
		return Arrays.equals(this.directDataExpression, other.directDataExpression) &&
			Arrays.equals(this.calculatedKeyExpressions, other.calculatedKeyExpressions);
	}

	/**
	 * Returns the calculatedKeyExpressions.
	 * 
	 * @return the calculatedKeyExpressions
	 */
	public EvaluationExpression[] getCalculatedKeyExpressions() {
		return this.calculatedKeyExpressions;
	}

	/**
	 * Returns the directDataExpression.
	 * 
	 * @return the directDataExpression
	 */
	public EvaluationExpression[] getDirectDataExpression() {
		return this.directDataExpression;
	}

	public EvaluationExpression getExpression(final int expressionIndex) {
		if (expressionIndex == SopremoRecordLayout.VALUE_INDEX)
			return EvaluationExpression.VALUE;
		final int numDirectDataKeys = this.getNumDirectDataKeys();
		if (expressionIndex < numDirectDataKeys)
			return this.directDataExpression[expressionIndex];
		return this.calculatedKeyExpressions[expressionIndex - numDirectDataKeys];
	}

	/**
	 * Returns the expressionIndex.
	 * 
	 * @return the expressionIndex
	 */
	public ExpressionIndex getExpressionIndex() {
		return this.expressionIndex;
	}

	public int[] getIndices(final EvaluationExpression... keyExpressions) {
		final int[] indices = new int[keyExpressions.length];
		for (int index = 0; index < indices.length; index++)
			indices[index] = this.getKeyIndex(keyExpressions[index]);
		return indices;
	}

	/**
	 * Returns the keyExpressions.
	 * 
	 * @return the keyExpressions
	 */
	public List<EvaluationExpression> getKeyExpressions() {
		return Lists.newArrayList(Iterables.concat(Arrays.asList(this.directDataExpression),
			Arrays.asList(this.calculatedKeyExpressions)));
	}

	public int getKeyIndex(final EvaluationExpression expression) {
		if (expression == EvaluationExpression.VALUE)
			return -1;

		int offset = this.indexedDirectDataExpression.getInt(expression);
		if (offset == UNKNOWN_KEY_EXPRESSION)
			offset = this.indexedCalculatedKeyExpressions.getInt(expression);
		if (offset == UNKNOWN_KEY_EXPRESSION)
			throw new IllegalArgumentException(String.format(
				"Unknown key expression %s; registered expressions: %s", expression,
				this.getKeyExpressions()));
		return offset;
	}

	public int getNumDirectDataKeys() {
		return this.directDataExpression.length;
	}

	public int getNumKeys() {
		return this.directDataExpression.length + this.calculatedKeyExpressions.length;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(this.calculatedKeyExpressions);
		result = prime * result + Arrays.hashCode(this.directDataExpression);
		return result;
	}

	public IntCollection indicesOf(final EvaluationExpression expression) {
		final IntArrayList indices = new IntArrayList();
		if (expression == EvaluationExpression.VALUE)
			indices.add(VALUE_INDEX);
		else if (expression instanceof ArrayAccess && ((ArrayAccess) expression).isFixedSize())
			for (final ArrayAccess arrayAccess : ((ArrayAccess) expression).decompose())
				indices.add(this.indexedDirectDataExpression.getInt(arrayAccess));
		else {
			int index = this.indexedDirectDataExpression.getInt(expression);
			if (index == -1)
				index = this.getNumDirectDataKeys() + this.indexedCalculatedKeyExpressions.getInt(expression);
			indices.add(index);
		}
		return indices;
	}

	public SopremoRecordLayout project(final BitSet keyIndices) {
		final List<EvaluationExpression> keyExpressions = this.getKeyExpressions();
		final List<EvaluationExpression> projectedExpressions = new ArrayList<EvaluationExpression>();
		for (int index = keyIndices.nextSetBit(0); index != -1; index = keyIndices.nextSetBit(index + 1))
			projectedExpressions.add(keyExpressions.get(index));
		return create(projectedExpressions);
	}

	public SopremoRecordLayout project(final int... keyIndices) {
		final List<EvaluationExpression> keyExpressions = this.getKeyExpressions();
		final List<EvaluationExpression> projectedExpressions = new ArrayList<EvaluationExpression>();
		for (int index = 0; index < keyIndices.length; index++)
			projectedExpressions.add(keyExpressions.get(keyIndices[index]));
		return create(projectedExpressions);
	}

	private void index(final EvaluationExpression[] directDataExpression,
			final EvaluationExpression[] calculatedKeyExpressions) {
		this.indexedDirectDataExpression.defaultReturnValue(UNKNOWN_KEY_EXPRESSION);
		this.indexedCalculatedKeyExpressions.defaultReturnValue(UNKNOWN_KEY_EXPRESSION);

		for (int index = 0; index < directDataExpression.length; index++)
			this.indexedDirectDataExpression.put(directDataExpression[index], index);
		for (int index = 0; index < calculatedKeyExpressions.length; index++)
			this.indexedCalculatedKeyExpressions.put(calculatedKeyExpressions[index], index);
	}

	public static SopremoRecordLayout create(final EvaluationExpression keyExpressions) {
		return create(Arrays.asList(keyExpressions));
	}

	public static SopremoRecordLayout create(final EvaluationExpression... keyExpressions) {
		return create(Arrays.asList(keyExpressions));
	}

	public static SopremoRecordLayout create(final Iterable<EvaluationExpression> keyExpressions) {
		final List<EvaluationExpression> directDataExpression = new ArrayList<EvaluationExpression>(), calculatedKeyExpressions =
			new ArrayList<EvaluationExpression>();

		final ExpressionIndex expressionIndex = new ExpressionIndex();
		for (final EvaluationExpression keyExpression : keyExpressions) {
			if (keyExpression == EvaluationExpression.VALUE)
				continue;
			if (expressionIndex.add(keyExpression, directDataExpression.size()))
				directDataExpression.add(keyExpression);
			else
				calculatedKeyExpressions.add(keyExpression);
		}

		return new SopremoRecordLayout(expressionIndex,
			directDataExpression.toArray(new EvaluationExpression[directDataExpression.size()]),
			calculatedKeyExpressions.toArray(new EvaluationExpression[calculatedKeyExpressions.size()]));
	}

	public static class KryoSerializer extends Serializer<SopremoRecordLayout> {

		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#copy(com.esotericsoftware.kryo.Kryo, java.lang.Object)
		 */
		@Override
		public SopremoRecordLayout copy(final Kryo kryo, final SopremoRecordLayout original) {
			return original;
		}

		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#read(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Input, java.lang.Class)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public SopremoRecordLayout read(final Kryo kryo, final Input input, final Class<SopremoRecordLayout> type) {
			return SopremoRecordLayout.create(kryo.readObject(input, ArrayList.class));
		}

		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#write(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Output, java.lang.Object)
		 */
		@Override
		public void write(final Kryo kryo, final Output output, final SopremoRecordLayout object) {
			kryo.writeObject(output, object.getKeyExpressions());
		}
	}

}
