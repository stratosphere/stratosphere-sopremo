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

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.io.IOException;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javolution.text.TypeFormat;

import com.google.common.base.Predicate;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.expressions.ObjectAccess;

/**
 * Allows to quickly find the values corresponding to key expressions during serialization.
 */
public class ExpressionIndex extends AbstractSopremoType {

	private final EvaluationExpression expression;

	private final Object2ObjectMap<String, ExpressionIndex> objectAccesses =
		new Object2ObjectOpenHashMap<String, ExpressionIndex>();

	private final Int2ObjectMap<ExpressionIndex> arrayAccesses = new Int2ObjectOpenHashMap<ExpressionIndex>();

	private final static ExpressionIndex EMPTY_INDEX = null;

	private final int keyIndex;

	private final static Set<Class<?>> SupportedClasses = new HashSet<Class<?>>();

	static {
		SupportedClasses.add(EvaluationExpression.VALUE.getClass());
		SupportedClasses.add(ArrayAccess.class);
		SupportedClasses.add(ObjectAccess.class);
		SupportedClasses.add(InputSelection.class);
	}

	/**
	 * Initializes ExpressionIndex.
	 */
	public ExpressionIndex() {
		this(EvaluationExpression.VALUE, -1);
	}

	protected ExpressionIndex(final EvaluationExpression evaluationExpression, final int keyIndex) {
		this.expression = evaluationExpression;
		this.objectAccesses.defaultReturnValue(EMPTY_INDEX);
		this.arrayAccesses.defaultReturnValue(EMPTY_INDEX);
		this.keyIndex = keyIndex;
	}

	public boolean add(final EvaluationExpression expression, final int keyIndex) {
		final Class<?> type = expression.getClass();
		final Deque<EvaluationExpression> expressionChain = new LinkedList<EvaluationExpression>();
		if (expression.findFirst(new Predicate<EvaluationExpression>() {
			@Override
			public boolean apply(final EvaluationExpression expression) {
				expressionChain.add(expression);
				return !SupportedClasses.contains(type) ||
					expression instanceof ArrayAccess && ((ArrayAccess) expression).isSelectingRange();
			}
		}) != null)
			return false;

		this.add(expressionChain, expression, keyIndex);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(final Appendable builder) throws IOException {
		builder.append("ExpressionIndex ");
		if (this.expression != null)
			this.expression.appendAsString(builder);
		if (!this.objectAccesses.isEmpty()) {
			builder.append(", objectAccesses=");
			this.append(builder, this.objectAccesses.values(), ", ");
		}
		if (!this.arrayAccesses.isEmpty()) {
			builder.append(", arrayAccesses=");
			this.append(builder, this.arrayAccesses.values(), ", ");
		}
		builder.append(", keyIndex=");
		TypeFormat.format(this.keyIndex, builder);
		builder.append("]");
	}

	/**
	 * @param index
	 */
	public ExpressionIndex subIndex(final int index) {
		return this.arrayAccesses.get(index);
	}

	public ExpressionIndex subIndex(final int index, final int size) {
		final ExpressionIndex subIndex = this.arrayAccesses.get(index);
		if (subIndex != null)
			return subIndex;
		return this.arrayAccesses.get(index - size);
	}

	/**
	 * Returns the expression.
	 * 
	 * @return the expression
	 */
	public EvaluationExpression getExpression() {
		return this.expression;
	}

	/**
	 * Returns the keyIndex.
	 * 
	 * @return the keyIndex
	 */
	public int getKeyIndex() {
		return this.keyIndex;
	}

	/**
	 * @param fieldName
	 */
	public ExpressionIndex subIndex(final String fieldName) {
		return this.objectAccesses.get(fieldName);
	}

	private void add(final Deque<EvaluationExpression> expressionChain, final EvaluationExpression expression,
			final int keyIndex) {
		if (expressionChain.isEmpty())
			return;

		final EvaluationExpression currentExpression = expressionChain.removeLast();
		if (currentExpression instanceof ObjectAccess) {
			final ObjectAccess objectAccess = (ObjectAccess) currentExpression;
			ExpressionIndex subIndex = this.objectAccesses.get(objectAccess.getField());
			if (subIndex == EMPTY_INDEX)
				this.objectAccesses.put(objectAccess.getField(),
					subIndex = new ExpressionIndex(objectAccess.cloneSegment(), keyIndex));
			subIndex.add(expressionChain, expression, keyIndex);
		} else if (currentExpression instanceof InputSelection) {
			final InputSelection inputSelection = (InputSelection) currentExpression;
			ExpressionIndex subIndex = this.arrayAccesses.get(inputSelection.getIndex());
			if (subIndex == EMPTY_INDEX)
				this.arrayAccesses.put(inputSelection.getIndex(),
					subIndex = new ExpressionIndex(inputSelection.asArrayAccess(), keyIndex));
			subIndex.add(expressionChain, expression, keyIndex);
		} else if (currentExpression instanceof ArrayAccess) {
			final ArrayAccess arrayAccess = (ArrayAccess) currentExpression;
			final int index = arrayAccess.getStartIndex();
			ExpressionIndex subIndex = this.arrayAccesses.get(index);
			if (subIndex == EMPTY_INDEX)
				this.arrayAccesses.put(index, subIndex = new ExpressionIndex(new ArrayAccess(index), keyIndex));
			subIndex.add(expressionChain, expression, keyIndex);
		} else if (currentExpression == EvaluationExpression.VALUE)
			this.add(expressionChain, currentExpression, keyIndex);
	}

}
