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
package eu.stratosphere.sopremo.expressions.tree;

import java.util.Arrays;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;

/**
 * @author Arvid Heise
 */
public class ConcatenatingNamedChildIterator extends NamedChildIterator {
	private NamedChildIterator[] iterators;

	private int[] startIndexes;

	public ConcatenatingNamedChildIterator(NamedChildIterator... iterators) {
		this.iterators = iterators;
		this.startIndexes = new int[iterators.length];

		this.startIndexes[0] = iterators[0].getSize();
		for (int index = 1; index < iterators.length; index++)
			this.startIndexes[index + 1] = this.startIndexes[index] + iterators[index].getSize();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.tree.NamedChildIterator#get(int)
	 */
	@Override
	protected EvaluationExpression get(int index) {
		int iteratorIndex = this.getIteratorIndex(index);
		return this.iterators[iteratorIndex].get(index - this.startIndexes[iteratorIndex]);
	}

	private int getIteratorIndex(int index) {
		int iteratorIndex = Arrays.binarySearch(this.startIndexes, index);
		if (iteratorIndex < 0)
			iteratorIndex = -iteratorIndex - 1;
		return iteratorIndex;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.tree.NamedChildIterator#set(int,
	 * eu.stratosphere.sopremo.expressions.EvaluationExpression)
	 */
	@Override
	protected void set(int index, EvaluationExpression childExpression) {
		int iteratorIndex = this.getIteratorIndex(index);
		this.iterators[iteratorIndex].set(index - this.startIndexes[iteratorIndex], childExpression);
	}

}
