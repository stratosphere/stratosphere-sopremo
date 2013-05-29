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

import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.JsonUtil;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.sopremo.type.NullNode;

/**
 * Represents all constants.
 */
@OptimizerHints(scope = Scope.ANY)
public class ConstantExpression extends EvaluationExpression {
	private final IJsonNode constant;

	public static final EvaluationExpression MISSING = new ConstantExpression(MissingNode.getInstance());

	public static final EvaluationExpression NULL = new ConstantExpression(NullNode.getInstance());

	/**
	 * Initializes a ConstantExpression with the given JsonNode.
	 * 
	 * @param constant
	 *        the node that should be represented by this ConstantExpression
	 */
	public ConstantExpression(final IJsonNode constant) {
		this.constant = constant;
	}

	/**
	 * Initializes a ConstantExpression. The given constant will be mapped to a JsonNode before initializing this
	 * expression.
	 * 
	 * @param constant
	 *        this Objects JsonNode representation should be represented by this ConstantExpression
	 */
	public ConstantExpression(final Object constant) {
		this.constant = JsonUtil.OBJECT_MAPPER.valueToTree(constant);
	}

	/**
	 * Initializes ConstantExpression.
	 */
	public ConstantExpression() {
		this.constant = null;
	}

	/**
	 * Returns the constant.
	 * 
	 * @return the constant
	 */
	public IJsonNode getConstant() {
		return this.constant;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!super.equals(obj))
			return false;
		final ConstantExpression other = (ConstantExpression) obj;
		return this.constant.equals(other.constant);
	}

	@Override
	public IJsonNode evaluate(final IJsonNode node) {
		// we can ignore 'target' because no new Object is created
		return this.constant;
	}

	@Override
	public int hashCode() {
		return 41 * super.hashCode() + this.constant.hashCode();
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		if (this.constant instanceof CharSequence) {
			appendable.append("\'");
			this.constant.appendAsString(appendable);
			appendable.append("\'");
		}
		else
			this.constant.appendAsString(appendable);
	}

}