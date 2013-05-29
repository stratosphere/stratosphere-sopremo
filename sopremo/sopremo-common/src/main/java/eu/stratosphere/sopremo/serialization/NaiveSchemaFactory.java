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
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Set;

import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.ObjectAccess;

/**
 * This Factory provides the functionality described in {@link SchemaFactory} in an simple way.
 * Only the following conditions are checked:</br>
 * <ul>
 * <li>no key expressions are provided: {@link DirectSchema}</br>
 * <li>all key expressions are {@link ObjectAccess}: {@link ObjectSchema}</br>
 * <li>all key expressions are {@link ArrayAccess}:</br> - startIndex of the first key expression is 0:
 * {@link HeadArraySchema}</br> - else: {@link TailArraySchema}</br>
 * <li>non of the conditions described above are true: {@link GeneralSchema}
 * 
 * @author Arvid Heise
 */
public class NaiveSchemaFactory implements SchemaFactory {
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.SchemaFactory#create(java.lang.Iterable)
	 */
	@Override
	public Schema create(final Set<EvaluationExpression> keyExpressions) {

		final List<ObjectAccess> objectAccesses = new ArrayList<ObjectAccess>();
		final List<ArrayAccess> arrayAccesses = new ArrayList<ArrayAccess>();
		final List<EvaluationExpression> actualKeyExpressions = new ArrayList<EvaluationExpression>();

		for (final EvaluationExpression evaluationExpression : keyExpressions) {
			if (evaluationExpression instanceof ObjectAccess &&
				((ObjectAccess) evaluationExpression).getInputExpression() == EvaluationExpression.VALUE)
				objectAccesses.add((ObjectAccess) evaluationExpression);
			else if (evaluationExpression instanceof ArrayAccess &&
				((ArrayAccess) evaluationExpression).getInputExpression() == EvaluationExpression.VALUE)
				arrayAccesses.add((ArrayAccess) evaluationExpression);
			if (evaluationExpression != EvaluationExpression.VALUE)
				actualKeyExpressions.add(evaluationExpression);
		}

		if (actualKeyExpressions.isEmpty())
			return new DirectSchema();

		if (objectAccesses.size() == actualKeyExpressions.size())
			// all keyExpressions are ObjectAccesses
			return new ObjectSchema(objectAccesses);
		else if (arrayAccesses.size() == actualKeyExpressions.size()) {
			// all keyExpressions are ArrayAccesses
			BitSet positiveIndices = new BitSet(), negativeIndices = new BitSet();
			boolean applicable = true;

			for (ArrayAccess arrayAccess : arrayAccesses) {
				if (!arrayAccess.isFixedSize()) {
					applicable = false;
					break;
				}
				if (arrayAccess.getStartIndex() >= 0)
					for (int index = arrayAccess.getStartIndex(), end = arrayAccess.getEndIndex(); index <= end; index++)
						positiveIndices.set(index);
				else
					for (int index = arrayAccess.getStartIndex(), end = arrayAccess.getEndIndex(); index <= end; index++)
						negativeIndices.set(-index - 1);
			}
			if (applicable) {
				// can only use a special schema if either head or tail of an array are used; not both at once
				if (negativeIndices.isEmpty())
					return new HeadArraySchema(positiveIndices.length());

				if (positiveIndices.isEmpty())
					return new TailArraySchema(negativeIndices.length());
			}
		}

		return new GeneralSchema(actualKeyExpressions);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public NaiveSchemaFactory clone() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#copyPropertiesFrom(eu.stratosphere.sopremo.ISopremoType)
	 */
	@Override
	public void copyPropertiesFrom(ISopremoType original) {
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		appendable.append("naive schema");
	}
}
