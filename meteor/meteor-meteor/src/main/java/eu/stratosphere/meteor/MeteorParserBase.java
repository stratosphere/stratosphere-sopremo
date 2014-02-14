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

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.JsonStreamExpression;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.io.JsonFormat;
import eu.stratosphere.sopremo.io.SopremoFormat;
import eu.stratosphere.sopremo.packages.DefaultConstantRegistry;
import eu.stratosphere.sopremo.packages.DefaultNameChooser;
import eu.stratosphere.sopremo.packages.DefaultNameChooserProvider;
import eu.stratosphere.sopremo.packages.IConstantRegistry;
import eu.stratosphere.sopremo.packages.NameChooserProvider;
import eu.stratosphere.sopremo.query.QueryWithVariablesParser;
import eu.stratosphere.sopremo.query.RecognitionExceptionWithUsageHint;
import eu.stratosphere.sopremo.query.StackedConstantRegistry;
import eu.stratosphere.sopremo.type.*;

/**
 */
public abstract class MeteorParserBase extends QueryWithVariablesParser<JsonStreamExpression> {
	static DefaultNameChooserProvider NameChooserProvider =
		new DefaultNameChooserProvider(new DefaultNameChooser(3, 0, 1, 2));

	private final StackedConstantRegistry constantRegistry = new StackedConstantRegistry(
		NameChooserProvider.getConstantNameChooser());

	static {
		NameChooserProvider.setFunctionNameChooser(new DefaultNameChooser(1, 0, 2, 3));
	}

	public MeteorParserBase(final TokenStream input) {
		super(input);
		this.init();
	}

	public MeteorParserBase(final TokenStream input, final RecognizerSharedState state) {
		super(input, state);
		this.init();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.query.AbstractQueryParser#getConstantRegistry()
	 */
	@Override
	public IConstantRegistry getConstantRegistry() {
		return this.constantRegistry;
	}

	protected void addConstantScope() {
		this.constantRegistry.push(new DefaultConstantRegistry());
	}

	protected void addConstant(String name, EvaluationExpression expression) {
		this.constantRegistry.put(name, expression);
	}

	protected String getAssignmentName(final EvaluationExpression expression) {
		if (expression instanceof ObjectAccess)
			return ((ObjectAccess) expression).getField();
		return expression.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.query.AbstractQueryParser#getDefaultFileFormat()
	 */
	@Override
	protected Class<? extends SopremoFormat> getDefaultFileFormat() {
		return JsonFormat.class;
	}

	@Override
	protected NameChooserProvider getNameChooserProvider() {
		return NameChooserProvider;
	}

	protected JsonStreamExpression getVariable(final Token name) {
		return this.getVariableRegistry().get(name.getText());
	}

	protected JsonStreamExpression getVariableSafely(final Token name) throws RecognitionException {
		final JsonStreamExpression variable = this.getVariable(name);
		if (variable == null)
			throw new RecognitionExceptionWithUsageHint(name, String.format(
				"Unknown varible %s; possible alternatives %s", name,
				this.inputSuggestion.suggest(name.getText(), this.getVariableRegistry().keySet())));
		return variable;
	}

	protected void putVariable(final Token name, final JsonStreamExpression expression) {
		this.getVariableRegistry().put(name.getText(), expression);
	}

	protected void putVariable(final Token name, final JsonStreamExpression expression, final int depth) {
		this.getVariableRegistry().getRegistry(depth).put(name.getText(), expression);
	}

	protected void removeConstantScope() {
		this.constantRegistry.pop();
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
}
