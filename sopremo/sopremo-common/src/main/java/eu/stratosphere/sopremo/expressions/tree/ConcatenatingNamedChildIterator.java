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

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;

import java.util.Arrays;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;

/**
 */
public class ConcatenatingNamedChildIterator extends NamedChildIterator {
	private final NamedChildIterator[] iterators;

	private final int[] startIndexes;

	public ConcatenatingNamedChildIterator(final NamedChildIterator... iterators) {
		super(concatenateNames(iterators));
		this.iterators = iterators;
		this.startIndexes = new int[iterators.length];

		this.startIndexes[0] = 0;
		for (int index = 1; index < iterators.length; index++)
			this.startIndexes[index] = this.startIndexes[index - 1] + iterators[index - 1].getSize();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.tree.NamedChildIterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return super.hasNext();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.tree.NamedChildIterator#get(int)
	 */
	@Override
	protected EvaluationExpression get(final int index) {
		final int iteratorIndex = this.getIteratorIndex(index);
		return this.iterators[iteratorIndex].get(index - this.startIndexes[iteratorIndex]);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.tree.NamedChildIterator#set(int,
	 * eu.stratosphere.sopremo.expressions.EvaluationExpression)
	 */
	@Override
	protected void set(final int index, final EvaluationExpression childExpression) {
		final int iteratorIndex = this.getIteratorIndex(index);
		this.iterators[iteratorIndex].set(index - this.startIndexes[iteratorIndex], childExpression);
	}

	private int getIteratorIndex(final int index) {
		int iteratorIndex = Arrays.binarySearch(this.startIndexes, index);
		if (iteratorIndex < 0)
			iteratorIndex = -iteratorIndex - 2;
		return iteratorIndex;
	}

	private static String[] concatenateNames(final NamedChildIterator[] iterators) {
		final ObjectList<String> names = new ObjectArrayList<String>();
		for (final NamedChildIterator namedChildIterator : iterators)
			names.addElements(names.size(), namedChildIterator.getChildNames());
		return names.toArray(new String[names.size()]);
	}

}
