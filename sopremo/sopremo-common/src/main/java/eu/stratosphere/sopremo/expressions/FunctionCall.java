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

import eu.stratosphere.sopremo.EvaluationException;
import eu.stratosphere.sopremo.expressions.tree.ChildIterator;
import eu.stratosphere.sopremo.expressions.tree.ConcatenatingChildIterator;
import eu.stratosphere.sopremo.expressions.tree.NamedChildIterator;
import eu.stratosphere.sopremo.function.SopremoFunction;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Calls the specified function with the provided parameters and returns the
 * result.
 */
@OptimizerHints(scope = Scope.ANY, minNodes = 0, maxNodes = OptimizerHints.UNBOUND)
public class FunctionCall extends EvaluationExpression {

	private final SopremoFunction function;

	private final List<EvaluationExpression> paramExprs;

	private transient final IArrayNode<IJsonNode> params = new ArrayNode<IJsonNode>();

	/**
	 * Initializes a MethodCall with the given function name and expressions
	 * which evaluate to the method parameters.
	 * 
	 * @param function
	 *        the function itself
	 * @param params
	 *        expressions which evaluate to the method parameters
	 */
	public FunctionCall(final SopremoFunction function, final EvaluationExpression... params) {
		this(function, Arrays.asList(params));
	}

	/**
	 * Initializes a MethodCall with the given function name and expressions
	 * which evaluate to the method parameters.
	 * 
	 * @param function
	 *        the function itself
	 * @param params
	 *        expressions which evaluate to the method parameters
	 */
	public FunctionCall(final SopremoFunction function, final List<EvaluationExpression> params) {
		if (function == null)
			throw new NullPointerException("Function must not be null");
		for (final EvaluationExpression param : params)
			if (param == null)
				throw new NullPointerException("Params must not be null " + params);
		this.function = function;
		this.paramExprs = new ArrayList<EvaluationExpression>(params);
	}

	/**
	 * Initializes FunctionCall.
	 */
	FunctionCall() {
		this.function = null;
		this.paramExprs = null;
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.function.appendAsString(appendable);
		appendable.append('(');
		this.append(appendable, this.paramExprs, ", ");
		appendable.append(')');
	}

	@Override
	public boolean equals(final Object obj) {
		if (!super.equals(obj))
			return false;
		final FunctionCall other = (FunctionCall) obj;
		return this.function.equals(other.function) && this.paramExprs.equals(other.paramExprs);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.EvaluationExpression#evaluate(eu.
	 * stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public IJsonNode evaluate(final IJsonNode node) {
		final List<EvaluationExpression> paramExprs = this.paramExprs;
		this.params.clear();
		for (int index = 0; index < paramExprs.size(); index++)
			this.params.add(paramExprs.get(index).evaluate(node));

		try {
			return this.function.call(this.params);
		} catch (final Exception e) {
			throw new EvaluationException(e);
		}
	}

	/**
	 * Returns the function.
	 * 
	 * @return the function
	 */
	public SopremoFunction getFunction() {
		return this.function;
	}

	/**
	 * Returns the paramExprs.
	 * 
	 * @return the paramExprs
	 */
	public List<EvaluationExpression> getParameters() {
		return this.paramExprs;
	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = hash * 53 + this.function.hashCode();
		hash = hash * 53 + this.paramExprs.hashCode();
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.ExpressionParent#iterator()
	 */
	@Override
	public ChildIterator iterator() {
		final String[] paramNames = new String[this.paramExprs.size()];
		for (int index = 0; index < paramNames.length; index++)
			paramNames[index] = String.format("Param %d", index);
		return new ConcatenatingChildIterator(super.iterator(), new NamedChildIterator(paramNames) {
			@Override
			protected EvaluationExpression get(final int index) {
				return FunctionCall.this.paramExprs.get(index);
			}

			@Override
			protected void set(final int index, final EvaluationExpression childExpression) {
				FunctionCall.this.paramExprs.set(index, childExpression);
			}
		});
	}

}