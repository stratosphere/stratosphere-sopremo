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
import java.lang.ref.WeakReference;
import java.util.List;

import javolution.text.TypeFormat;
import eu.stratosphere.sopremo.operator.JsonStream;
import eu.stratosphere.sopremo.operator.Operator;
import eu.stratosphere.sopremo.operator.Operator.Output;

public class JsonStreamExpression extends UnevaluableExpression {
	private final JsonStream stream;

	private final int inputIndex;

	/**
	 * Initializes a JsonStreamExpression with the given {@link JsonStream}.
	 * 
	 * @param stream
	 *        the stream that should be used
	 */
	public JsonStreamExpression(final JsonStream stream) {
		this(stream, -1);
	}

	/**
	 * Initializes a JsonStreamExpression with the given {@link JsonStream} and index.
	 * 
	 * @param stream
	 *        the stream that should be used
	 * @param inputIndex
	 *        the index
	 */
	public JsonStreamExpression(final JsonStream stream, final int inputIndex) {
		super("JsonStream placeholder");
		this.stream = stream;
		this.inputIndex = inputIndex;
	}

	/**
	 * Initializes a JsonStreamExpression with the given index.
	 * 
	 * @param stream
	 *        the stream that should be used
	 * @param inputIndex
	 *        the index
	 */
	public JsonStreamExpression(int inputIndex) {
		this(null, inputIndex);
	}

	/**
	 * Returns the inputIndex.
	 * 
	 * @return the inputIndex
	 */
	public int getInputIndex() {
		return this.inputIndex;
	}

	/**
	 * Returns the JsonStream
	 * 
	 * @return the stream
	 */
	public JsonStream getStream() {
		return this.stream;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.UnevaluableExpression#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		if (this.stream != null)
			appendable.append(this.stream.getSource().getOperator().getName()).append("@");
		if (this.inputIndex != -1)
			TypeFormat.format(this.inputIndex, appendable);
		else if (this.stream != null)
			TypeFormat.format(this.stream.getSource().getIndex(), appendable);
	}

	/**
	 * Creates an {@link InputSelection} based on this expressions stream an index.
	 * 
	 * @param operator
	 * @return the created InputSelection
	 */
	public EvaluationExpression toInputSelection(final Operator<?> operator) {
		InputSelection inputSelection;
		if (this.inputIndex != -1)
			inputSelection = new InputSelection(this.inputIndex);
		else if (operator.getSource() == this.stream.getSource())
			inputSelection = new InputSelection(0);
		else {
			final int index = operator.getInputs().indexOf(this.stream.getSource());
			if (index == -1)
				return this;
			inputSelection = new InputSelection(index);
		}
		return inputSelection;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.stream.getSource().hashCode();
		return result;
	}

	private transient WeakReference<JsonStreamExpression> equalStream;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final JsonStreamExpression other = (JsonStreamExpression) obj;
		Output thisSource = this.stream.getSource();
		Output otherSource = other.stream.getSource();
		if( thisSource.getIndex() != otherSource.getIndex())
			return false;
		// at this point we assume that both stream expression could be equal
		if(this.equalStream != null && this.equalStream.get() == other)
			return true;

		this.equalStream = new WeakReference<JsonStreamExpression>(other);
		// here the actual recursion is very likely to occur
		return thisSource.getOperator().equals(otherSource.getOperator());
	}

	/**
	 * Returns the input index of the stream. If the inputIndex is set, it is directly return. Else the stream will be
	 * looked up in the provided list of inputs.
	 * 
	 * @param inputs
	 *        the inputs in which to look up the index
	 * @return the index of the stream wrapped in this expression
	 */
	public int getInputIndex(List<JsonStream> inputs) {
		if (this.inputIndex != -1)
			return this.inputIndex;
		return inputs.indexOf(this.stream);
	}

}
