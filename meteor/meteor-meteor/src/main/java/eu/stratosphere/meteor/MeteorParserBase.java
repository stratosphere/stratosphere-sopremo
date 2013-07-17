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
package eu.stratosphere.meteor;

import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.JsonStreamExpression;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.io.JsonFormat;
import eu.stratosphere.sopremo.io.SopremoFormat;
import eu.stratosphere.sopremo.packages.DefaultConstantRegistry;
import eu.stratosphere.sopremo.packages.IConstantRegistry;
import eu.stratosphere.sopremo.query.QueryWithVariablesParser;
import eu.stratosphere.sopremo.query.StackedConstantRegistry;
import eu.stratosphere.sopremo.type.BooleanNode;
import eu.stratosphere.sopremo.type.DecimalNode;
import eu.stratosphere.sopremo.type.DoubleNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.TextNode;

/**
 * @author Arvid Heise
 */
public abstract class MeteorParserBase extends QueryWithVariablesParser<JsonStreamExpression> {
	private StackedConstantRegistry constantRegistry = new StackedConstantRegistry();

	public MeteorParserBase(TokenStream input) {
		super(input);
		this.init();
	}

	public MeteorParserBase(TokenStream input, RecognizerSharedState state) {
		super(input, state);
		this.init();
	}

	protected void addConstantScope() {
		this.constantRegistry.push(new DefaultConstantRegistry());
	}

	protected String getAssignmentName(EvaluationExpression expression) {
		if (expression instanceof ObjectAccess)
			return ((ObjectAccess) expression).getField();
		return expression.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.query.AbstractQueryParser#getConstantRegistry()
	 */
	@Override
	public IConstantRegistry getConstantRegistry() {
		return this.constantRegistry;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.query.AbstractQueryParser#getDefaultFileFormat()
	 */
	@Override
	protected Class<? extends SopremoFormat> getDefaultFileFormat() {
		return JsonFormat.class;
	}

	protected JsonStreamExpression getVariable(Token name) {
		return this.getVariableRegistry().get(name.getText());
	}

	private void init() {
		this.getPackageManager().importPackage("base");

		this.addTypeAlias("int", IntNode.class);
		this.addTypeAlias("decimal", DecimalNode.class);
		this.addTypeAlias("string", TextNode.class);
		this.addTypeAlias("double", DoubleNode.class);
		this.addTypeAlias("boolean", BooleanNode.class);
		this.addTypeAlias("bool", BooleanNode.class);

		this.constantRegistry.push(super.getConstantRegistry());
	}

	protected void putVariable(Token name, JsonStreamExpression expression) {
		this.getVariableRegistry().put(name.getText(), expression);
	}

	protected void removeConstantScope() {
		this.constantRegistry.pop();
	}
}
