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
package eu.stratosphere.sopremo.expressions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.stratosphere.sopremo.expressions.tree.ChildIterator;
import eu.stratosphere.sopremo.expressions.tree.ListChildIterator;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Creates an array of the given expressions.
 * 
 * @author Arvid Heise
 */
@OptimizerHints(scope = Scope.ANY)
public class ArrayCreation extends EvaluationExpression {
	private final List<EvaluationExpression> elements;

	/**
	 * Initializes ArrayCreation to create an array of the given expressions.
	 * 
	 * @param elements
	 *        the expressions that evaluate to the elements in the array
	 */
	public ArrayCreation(final EvaluationExpression... elements) {
		this.elements = new ArrayList<EvaluationExpression>(Arrays.asList(elements));
	}

	/**
	 * Initializes ArrayCreation to create an array of the given expressions.
	 * 
	 * @param elements
	 *        the expressions that evaluate to the elements in the array
	 */
	public ArrayCreation(final List<EvaluationExpression> elements) {
		this.elements = new ArrayList<EvaluationExpression>(elements);
	}

	/**
	 * Initializes ArrayCreation.
	 */
	public ArrayCreation() {
		this.elements = new ArrayList<EvaluationExpression>();
	}

	public ArrayCreation add(EvaluationExpression expression) {
		this.elements.add(expression);
		return this;
	}

	public int size() {
		return this.elements.size();
	}
	
	public EvaluationExpression get(int index) {
		return elements.get(index);
	}

	@Override
	public boolean equals(final Object obj) {
		if (!super.equals(obj))
			return false;
		final ArrayCreation other = (ArrayCreation) obj;
		return this.elements.equals(other.elements);
	}

	private final IArrayNode<IJsonNode> result = new ArrayNode<IJsonNode>();

	/**
	 * Returns the elements.
	 * 
	 * @return the elements
	 */
	public List<EvaluationExpression> getElements() {
		return this.elements;
	}
	
	@Override
	public IJsonNode evaluate(final IJsonNode node) {
		this.result.clear();

		for (int index = 0; index < this.elements.size(); index++)
			this.result.add(this.elements.get(index).evaluate(node));

		return this.result;
	}

	@Override
	public int hashCode() {
		return 53 * super.hashCode() + this.elements.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.ExpressionParent#iterator()
	 */
	@Override
	public ChildIterator iterator() {
		return new ListChildIterator(this.elements.listIterator());
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.append(appendable, this.elements, ", ");
	}
}