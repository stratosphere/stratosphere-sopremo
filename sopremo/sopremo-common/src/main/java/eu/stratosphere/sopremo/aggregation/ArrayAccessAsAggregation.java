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
package eu.stratosphere.sopremo.aggregation;

import java.io.IOException;

import javolution.text.TypeFormat;
import eu.stratosphere.sopremo.type.CachingArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * @author Arvid Heise
 */
public class ArrayAccessAsAggregation extends Aggregation {
	private int startIndex, endIndex;

	private transient int elementsToSkip, remainingElements;

	private boolean range;

	/**
	 * Initializes ArrayAccessAsAggregation.
	 * 
	 * @param name
	 */
	public ArrayAccessAsAggregation(int startIndex, int endIndex, boolean range) {
		super("Array access");
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.range = range;
	}

	public ArrayAccessAsAggregation(int index) {
		this(index, index, false);
	}

	/**
	 * Initializes ArrayAccessAsAggregation.
	 */
	ArrayAccessAsAggregation() {
		this(0, 0, false);
	}

	private transient final CachingArrayNode<IJsonNode> arrayResult = new CachingArrayNode<IJsonNode>();

	// private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
	// ois.defaultReadObject();
	// aggregator = new ArrayNode<IJsonNode> ();
	// }
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.aggregation.Aggregation#initialize()
	 */
	@Override
	public void initialize() {
		this.elementsToSkip = this.startIndex;
		this.remainingElements = this.endIndex - this.startIndex + 1;
		this.arrayResult.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.aggregation.Aggregation#aggregate(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public void aggregate(IJsonNode element) {
		if (this.elementsToSkip > 0)
			this.elementsToSkip--;
		else if (this.remainingElements > 0) {
			this.arrayResult.addClone(element);
			this.remainingElements--;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.aggregation.Aggregation#clone()
	 */
	@Override
	public Aggregation clone() {
		return new ArrayAccessAsAggregation(this.startIndex, this.endIndex, this.range);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.aggregation.Aggregation#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		// super.appendAsString(appendable);
		appendable.append("@[");
		TypeFormat.format(this.startIndex, appendable);
		if (this.startIndex != this.endIndex) {
			appendable.append(':');
			TypeFormat.format(this.endIndex, appendable);
		}
		appendable.append(']');
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.aggregation.Aggregation#getFinalAggregate()
	 */
	@Override
	public IJsonNode getFinalAggregate() {
		if (this.range)
			return this.arrayResult;
		return this.arrayResult.get(0);
	}
}
