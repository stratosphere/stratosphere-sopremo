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
import java.io.ObjectInputStream;
import java.util.Formatter;

import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.TextNode;

/**
 * Generates an unique, pattern-based ID.
 */
public class GenerateExpression extends EvaluationExpression {
	private final String pattern;

	private long id;

	private transient EvaluationContext context;

	/**
	 * Initializes a GenerateExpression with the given pattern.
	 * 
	 * @param patternString
	 *        The pattern that should be used to generate the ID's.
	 *        Use '%s' inside the pattern string to specify the positions of the context based part and the expression
	 *        based part of the generated ID's.
	 */
	public GenerateExpression(String patternString) {
		final int patternPos = patternString.indexOf("%");
		if (patternPos == -1)
			patternString += "%s_%s";
		else if (patternString.indexOf("%", patternPos + 1) == -1)
			patternString = patternString.replaceAll("%", "%s_%");
		this.pattern = patternString;
		this.context = SopremoEnvironment.getInstance().getEvaluationContext();
	}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		this.context = SopremoEnvironment.getInstance().getEvaluationContext();
	}

	private transient final TextNode result = new TextNode();

	private transient final Formatter formatter = new Formatter(this.result);

	@Override
	public IJsonNode evaluate(final IJsonNode node) {
		this.result.clear();
		this.formatter.format(this.pattern, this.context.getTaskId(), this.id++);
		return this.result;
	}
}
