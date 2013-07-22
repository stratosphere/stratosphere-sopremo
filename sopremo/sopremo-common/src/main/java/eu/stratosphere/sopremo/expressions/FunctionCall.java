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
import eu.stratosphere.sopremo.packages.EvaluationScope;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Calls the specified function with the provided parameters and returns the
 * result.
 */
@OptimizerHints(scope = Scope.ANY, minNodes = 0, maxNodes = OptimizerHints.UNBOUND)
public class FunctionCall extends EvaluationExpression {

	private final String functionName;

	private final SopremoFunction function;

	private List<EvaluationExpression> paramExprs;

	/**
	 * Initializes a MethodCall with the given function name and expressions
	 * which evaluate to the method parameters.
	 * 
	 * @param functionName
	 *        the name of the function that should be called
	 * @param function
	 *        the function itself
	 * @param params
	 *        expressions which evaluate to the method parameters
	 */
	public FunctionCall(final String functionName, final SopremoFunction function, final EvaluationExpression... params) {
		this(functionName, function, Arrays.asList(params));
	}

	/**
	 * Initializes a MethodCall with the given function name and expressions
	 * which evaluate to the method parameters.
	 * 
	 * @param functionName
	 *        the name of the function that should be called
	 * @param function
	 *        the function itself
	 * @param params
	 *        expressions which evaluate to the method parameters
	 */
	public FunctionCall(final String functionName, final SopremoFunction function,
			final List<EvaluationExpression> params) {
		if (functionName == null)
			throw new NullPointerException("Function name must not be null");
		if (function == null)
			throw new NullPointerException("Function must not be null");
		for (EvaluationExpression param : params)
			if (param == null)
				throw new NullPointerException("Params must not be null " + params);
		this.functionName = functionName;
		this.function = function;
		this.paramExprs = new ArrayList<EvaluationExpression>(params);
	}

	/**
	 * Initializes a MethodCall with the given function name and expressions
	 * which evaluate to the method parameters.
	 * 
	 * @param functionName
	 *        the name of the function that should be called
	 * @param context
	 *        the {@link EvaluationContext} which contains the
	 *        {@link eu.stratosphere.sopremo.packages.IFunctionRegistry} that contains the definition under the function
	 *        name
	 * @param params
	 *        expressions which evaluate to the method parameters
	 */
	public FunctionCall(final String functionName, final EvaluationScope scope, final EvaluationExpression... params) {
		this(functionName, checkIfMethodExists(functionName, scope), Arrays.asList(params));
	}

	/**
	 * Initializes FunctionCall.
	 */
	FunctionCall() {
		this.functionName = null;
		this.function = null;
		this.paramExprs = null;
	}

	private static SopremoFunction checkIfMethodExists(final String functionName, final EvaluationScope scope) {
		final SopremoFunction function = (SopremoFunction) scope.getFunctionRegistry().get(functionName);
		if (function == null)
			throw new IllegalArgumentException(String.format("No method %s found", functionName));
		return function;
	}

	/**
	 * Initializes a MethodCall with the given function name and expressions
	 * which evaluate to the method parameters.
	 * 
	 * @param functionName
	 *        the name of the function that should be called
	 * @param context
	 *        the {@link EvaluationContext} which contains the
	 *        {@link eu.stratosphere.sopremo.packages.IFunctionRegistry} that contains the definition under the function
	 *        name
	 * @param params
	 *        expressions which evaluate to the method parameters
	 */
	public FunctionCall(final String functionName, final EvaluationScope scope, final List<EvaluationExpression> params) {
		this(functionName, checkIfMethodExists(functionName, scope), params);
	}

	@Override
	public boolean equals(final Object obj) {
		if (!super.equals(obj))
			return false;
		final FunctionCall other = (FunctionCall) obj;
		return this.function.equals(other.function) && this.paramExprs.equals(other.paramExprs);
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
	 * Returns the functionName.
	 * 
	 * @return the functionName
	 */
	public String getFunctionName() {
		return this.functionName;
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

	private transient final IArrayNode<IJsonNode> params = new ArrayNode<IJsonNode>();

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.EvaluationExpression#evaluate(eu.
	 * stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public IJsonNode evaluate(IJsonNode node) {
		final List<EvaluationExpression> paramExprs = this.paramExprs;
		this.params.clear();
		for (int index = 0; index < paramExprs.size(); index++)
			this.params.add(paramExprs.get(index).evaluate(node));

		try {
			return this.function.call(this.params);
		} catch (Exception e) {
			throw new EvaluationException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.ExpressionParent#iterator()
	 */
	@Override
	public ChildIterator iterator() {
		String[] paramNames = new String[this.paramExprs.size()];
		for (int index = 0; index < paramNames.length; index++)
			paramNames[index] = String.format("Param %d", index);
		return new ConcatenatingChildIterator(super.iterator(), new NamedChildIterator(paramNames) {
			@Override
			protected void set(int index, EvaluationExpression childExpression) {
				FunctionCall.this.paramExprs.set(index, childExpression);
			}

			@Override
			protected EvaluationExpression get(int index) {
				return FunctionCall.this.paramExprs.get(index);
			}
		});
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.function.appendAsString(appendable);
		appendable.append('(');
		this.append(appendable, this.paramExprs, ", ");
		appendable.append(')');
	}

}