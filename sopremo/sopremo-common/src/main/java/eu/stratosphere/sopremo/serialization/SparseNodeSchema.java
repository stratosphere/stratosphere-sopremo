/***********************************************************************************************************************
 *
 * Copyright (C) 2012 by the Stratosphere project (http://stratosphere.eu)
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

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import eu.stratosphere.compiler.postpass.AbstractSchema;
import eu.stratosphere.compiler.postpass.ConflictingFieldTypeInfoException;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.util.CollectionUtil;

/**
 * Class encapsulating a schema map (int column position -> column type) and a reference counter.
 */
public class SparseNodeSchema extends AbstractSchema<EvaluationExpression> {
	private final List<EvaluationExpression> keyExpressions = new ArrayList<EvaluationExpression>();

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.compiler.postpass.AbstractSchema#addType(int, java.lang.Object)
	 */
	@Override
	public void addType(final int pos, final EvaluationExpression expression) throws ConflictingFieldTypeInfoException {
		CollectionUtil.ensureSize(this.keyExpressions, pos + 1, EvaluationExpression.VALUE);
		this.keyExpressions.set(pos, expression);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.compiler.postpass.AbstractSchema#getType(int)
	 */
	@Override
	public EvaluationExpression getType(final int field) {
		return this.keyExpressions.get(field);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Entry<Integer, EvaluationExpression>> iterator() {
		return new Iterator<Entry<Integer, EvaluationExpression>>() {

			private int pos = 0;

			@Override
			public boolean hasNext() {
				return this.pos < SparseNodeSchema.this.keyExpressions.size();
			}

			@Override
			public Entry<Integer, EvaluationExpression> next() {
				return new AbstractMap.SimpleEntry<Integer, EvaluationExpression>(this.pos,
					SparseNodeSchema.this.keyExpressions.get(this.pos++));
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

}