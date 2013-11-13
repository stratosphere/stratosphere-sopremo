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
 * @author Arvid Heise
 */
public class ConcatenatingNamedChildIterator extends NamedChildIterator {
	private NamedChildIterator[] iterators;

	private int[] endIndexes;

	public ConcatenatingNamedChildIterator(NamedChildIterator... iterators) {
		super(concatenateNames(iterators));
		this.iterators = iterators;
		this.endIndexes = new int[iterators.length];

		this.endIndexes[0] = iterators[0].getSize();
		for (int index = 1; index < iterators.length; index++)
			this.endIndexes[index] = this.endIndexes[index - 1] + iterators[index].getSize();
	}
	
	private static String[] concatenateNames(NamedChildIterator[] iterators) {
		ObjectList<String> names = new ObjectArrayList<String>();
		for (NamedChildIterator namedChildIterator : iterators) 
			names.addElements(names.size(), namedChildIterator.getChildNames());
		return names.toArray(new String[names.size()]);
	}

	/* (non-Javadoc)
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
	protected EvaluationExpression get(int index) {
		int iteratorIndex = this.getIteratorIndex(index);
		return this.iterators[iteratorIndex].get(index - this.endIndexes[iteratorIndex]);
	}

	private int getIteratorIndex(int index) {
		int iteratorIndex = Arrays.binarySearch(this.endIndexes, index);
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
		this.iterators[iteratorIndex].set(index - this.endIndexes[iteratorIndex], childExpression);
	}

}
