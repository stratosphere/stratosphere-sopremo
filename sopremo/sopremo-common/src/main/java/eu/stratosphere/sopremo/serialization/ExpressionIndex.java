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
import java.util.Map.Entry;
import java.util.Set;

import javolution.text.TypeFormat;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.base.Predicate;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.expressions.ObjectAccess;

/**
 * @author arv
 */
public class ExpressionIndex extends AbstractSopremoType {

	private final EvaluationExpression expression;

	private final Object2ObjectMap<String, ExpressionIndex> objectAccesses =
		new Object2ObjectOpenHashMap<String, ExpressionIndex>();

	private final Int2ObjectMap<ExpressionIndex> arrayAccesses = new Int2ObjectOpenHashMap<ExpressionIndex>();

	private final static ExpressionIndex EMPTY_INDEX = new ExpressionIndex(null, -1);

	private final int keyIndex;

	/**
	 * Returns the expression.
	 * 
	 * @return the expression
	 */
	public EvaluationExpression getExpression() {
		return this.expression;
	}

	/**
	 * Initializes ExpressionIndex.
	 */
	public ExpressionIndex() {
		this(EvaluationExpression.VALUE, -1);
	}

	protected ExpressionIndex(EvaluationExpression evaluationExpression, int keyIndex) {
		this.expression = evaluationExpression;
		this.objectAccesses.defaultReturnValue(EMPTY_INDEX);
		this.arrayAccesses.defaultReturnValue(EMPTY_INDEX);
		this.keyIndex = keyIndex;
	}

	/**
	 * @param fieldName
	 * @return
	 */
	public ExpressionIndex subIndex(String fieldName) {
		return this.objectAccesses.get(fieldName);
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
	 * @param index
	 * @return
	 */
	public ExpressionIndex get(int index) {
		return this.arrayAccesses.get(index);
	}

	private final static Set<Class<?>> SupportedClasses = new HashSet<Class<?>>();

	static {
		SupportedClasses.add(EvaluationExpression.VALUE.getClass());
		SupportedClasses.add(ArrayAccess.class);
		SupportedClasses.add(ObjectAccess.class);
		SupportedClasses.add(InputSelection.class);
	}

	public boolean add(EvaluationExpression expression, int keyIndex) {
		final Class<?> type = expression.getClass();
		final Deque<EvaluationExpression> expressionChain = new LinkedList<EvaluationExpression>();
		if (expression.findFirst(new Predicate<EvaluationExpression>() {
			@Override
			public boolean apply(EvaluationExpression expression) {
				expressionChain.add(expression);
				return !SupportedClasses.contains(type) ||
					(expression instanceof ArrayAccess && ((ArrayAccess) expression).isSelectingRange());
			}
		}) != null)
			return false;

		add(expressionChain, expression, keyIndex);
		return true;
	}

	private void add(Deque<EvaluationExpression> expressionChain, EvaluationExpression expression, int keyIndex) {
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
			int index = arrayAccess.getStartIndex();
			ExpressionIndex subIndex = this.arrayAccesses.get(index);
			if (subIndex == EMPTY_INDEX)
				this.arrayAccesses.put(index, subIndex = new ExpressionIndex(new ArrayAccess(index), keyIndex));
			subIndex.add(expressionChain, expression, keyIndex);
		} else if(currentExpression == EvaluationExpression.VALUE)
			add(expressionChain, currentExpression, keyIndex);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(Appendable builder) throws IOException {
		builder.append("ExpressionIndex ");
		if (this.expression != null)
			this.expression.appendAsString(builder);
		if (!this.objectAccesses.isEmpty()) {
			builder.append(", objectAccesses=");
			append(builder, this.objectAccesses.values(), ", ");
		}
		if (!this.arrayAccesses.isEmpty()) {
			builder.append(", arrayAccesses=");
			append(builder, this.arrayAccesses.values(), ", ");
		}
		builder.append(", keyIndex=");
		TypeFormat.format(this.keyIndex, builder);
		builder.append("]");
	}

}
