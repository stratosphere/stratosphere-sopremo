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
package eu.stratosphere.sopremo.base.replace;

import java.io.IOException;

import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.JsonStreamExpression;
import eu.stratosphere.sopremo.expressions.PathSegmentExpression;
import eu.stratosphere.sopremo.expressions.UnevaluableExpression;
import eu.stratosphere.sopremo.operator.CompositeOperator;
import eu.stratosphere.sopremo.operator.Internal;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.Property;

/**
 */
@Internal
public abstract class ReplaceBase<Op extends ReplaceBase<Op>> extends CompositeOperator<Op> {

	private PathSegmentExpression replaceExpression = EvaluationExpression.VALUE;

	public static final EvaluationExpression FILTER_RECORDS = new UnevaluableExpression("<filter>");

	private EvaluationExpression dictionaryKeyExtraction = new ArrayAccess(0);

	private EvaluationExpression dictionaryValueExtraction = new ArrayAccess(1);

	private EvaluationExpression defaultExpression = EvaluationExpression.VALUE;

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append(this.getName());
		this.getReplaceExpression().appendAsString(appendable);
		if (this.getInput(1) != null) {
			appendable.append(" with ");
			this.getDictionary().appendAsString(appendable);
		}
		appendable.append(" default ");
		this.getDefaultExpression().appendAsString(appendable);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final ReplaceBase<?> other = (ReplaceBase<?>) obj;
		return this.defaultExpression.equals(other.defaultExpression) &&
			this.dictionaryKeyExtraction.equals(other.dictionaryKeyExtraction) &&
			this.dictionaryValueExtraction.equals(other.dictionaryValueExtraction) &&
			this.replaceExpression.equals(other.replaceExpression);
	}

	public EvaluationExpression getDefaultExpression() {
		return this.defaultExpression;
	}

	public JsonStreamExpression getDictionary() {
		return new JsonStreamExpression(this.getInput(1));
	}

	public EvaluationExpression getDictionaryKeyExtraction() {
		return this.dictionaryKeyExtraction;
	}

	public EvaluationExpression getDictionaryValueExtraction() {
		return this.dictionaryValueExtraction;
	}

	public PathSegmentExpression getReplaceExpression() {
		return this.replaceExpression;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.defaultExpression.hashCode();
		result = prime * result + this.dictionaryKeyExtraction.hashCode();
		result = prime * result + this.dictionaryValueExtraction.hashCode();
		result = prime * result + this.replaceExpression.hashCode();
		return result;
	}

	@Property
	@Name(noun = "default")
	public void setDefaultExpression(final EvaluationExpression defaultExpression) {
		if (defaultExpression == null)
			throw new NullPointerException("defaultExpression must not be null");

		this.defaultExpression = defaultExpression;
	}

	@Property
	@Name(noun = "dictionary", preposition = "with")
	public void setDictionary(final JsonStreamExpression dictionary) {
		if (dictionary == null)
			throw new NullPointerException("dictionary must not be null");

		this.setInput(1, dictionary.getStream());
	}

	@Property
	public void setDictionaryKeyExtraction(final EvaluationExpression dictionaryKeyExtraction) {
		if (dictionaryKeyExtraction == null)
			throw new NullPointerException("dictionaryKeyExtraction must not be null");

		this.dictionaryKeyExtraction = dictionaryKeyExtraction;
	}

	@Property
	public void setDictionaryValueExtraction(final EvaluationExpression dictionaryValueExtraction) {
		if (dictionaryValueExtraction == null)
			throw new NullPointerException("dictionaryValueExtraction must not be null");

		this.dictionaryValueExtraction = dictionaryValueExtraction;
	}

	@Property()
	@Name(preposition = "on")
	public void setReplaceExpression(final PathSegmentExpression inputKeyExtract) {
		if (inputKeyExtract == null)
			throw new NullPointerException("inputKeyExtract must not be null");

		this.replaceExpression = inputKeyExtract;
	}

	public Op withDefaultExpression(final EvaluationExpression defaultExpression) {
		this.setDefaultExpression(defaultExpression);
		return this.self();
	}

	public Op withDictionary(final JsonStreamExpression dictionary) {
		this.setDictionary(dictionary);
		return this.self();
	}

	public Op withDictionaryKeyExtraction(final EvaluationExpression dictionaryKeyExtraction) {
		this.setDictionaryKeyExtraction(dictionaryKeyExtraction);
		return this.self();
	}

	public Op withDictionaryValueExtraction(final EvaluationExpression dictionaryValueExtraction) {
		this.setDictionaryValueExtraction(dictionaryValueExtraction);
		return this.self();
	}

	public Op withReplaceExpression(final PathSegmentExpression inputKeyExtract) {
		this.setReplaceExpression(inputKeyExtract);
		return this.self();
	}

}