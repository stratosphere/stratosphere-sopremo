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
package eu.stratosphere.sopremo.base.replace;

import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.PathSegmentExpression;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.Internal;
import eu.stratosphere.sopremo.operator.Property;

@Internal
public abstract class AtomarReplaceBase<Self extends AtomarReplaceBase<Self>> extends ElementaryOperator<Self> {

	protected PathSegmentExpression replaceExpression = EvaluationExpression.VALUE;

	protected EvaluationExpression dictionaryValueExtraction = new ArrayAccess(1);

	public EvaluationExpression getDictionaryValueExtraction() {
		return this.dictionaryValueExtraction;
	}

	public PathSegmentExpression getReplaceExpression() {
		return this.replaceExpression;
	}

	@Property
	public void setDictionaryValueExtraction(final EvaluationExpression dictionaryValueExtraction) {
		if (dictionaryValueExtraction == null)
			throw new NullPointerException("dictionaryValueExtraction must not be null");

		this.dictionaryValueExtraction = dictionaryValueExtraction;
	}

	@Property
	public void setReplaceExpression(final PathSegmentExpression inputKeyExtractor) {
		if (inputKeyExtractor == null)
			throw new NullPointerException("inputKeyExtractor must not be null");

		this.replaceExpression = inputKeyExtractor;
	}

	public Self withDictionaryValueExtraction(final EvaluationExpression dictionaryValueExtraction) {
		this.setDictionaryValueExtraction(dictionaryValueExtraction);
		return this.self();
	}

	public Self withReplaceExpression(final PathSegmentExpression replaceExpression) {
		this.setReplaceExpression(replaceExpression);
		return this.self();
	}

}