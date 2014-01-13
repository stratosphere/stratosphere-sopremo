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
package eu.stratosphere.sopremo;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javolution.text.TextFormat;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.operator.SopremoPlan;

/**
 * The schema of a {@link SopremoPlan} is the distinct set of its decomposed key expressions.
 */
public class Schema extends AbstractSopremoType {
	private final List<EvaluationExpression> keyExpressions = new ArrayList<EvaluationExpression>();

	/**
	 * Initializes Schema.
	 */
	public Schema() {
	}

	public Schema(final List<EvaluationExpression> keyExpressions) {
		for (final EvaluationExpression keyExpression : keyExpressions)
			if (keyExpression instanceof ArrayAccess && ((ArrayAccess) keyExpression).isFixedSize())
				this.keyExpressions.addAll(((ArrayAccess) keyExpression).decompose());
			else
				this.keyExpressions.add(keyExpression);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		TextFormat.getInstance(this.keyExpressions.getClass()).format(this.keyExpressions, appendable);
	}

	/**
	 * Returns the keyExpressions.
	 * 
	 * @return the keyExpressions
	 */
	public List<EvaluationExpression> getKeyExpressions() {
		return this.keyExpressions;
	}

	/**
	 * Returns all indices that corresponds to the (decomposable) expression.
	 */
	public IntCollection indicesOf(final EvaluationExpression expression) {
		final IntArrayList indices = new IntArrayList();
		if (expression instanceof ArrayAccess && ((ArrayAccess) expression).isFixedSize())
			for (final ArrayAccess arrayAccess : ((ArrayAccess) expression).decompose())
				indices.add(this.keyExpressions.indexOf(arrayAccess));
		else
			indices.add(this.keyExpressions.indexOf(expression));
		return indices;
	}

}
