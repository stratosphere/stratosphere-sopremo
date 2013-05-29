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

import java.util.NoSuchElementException;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;

/**
 * @author Arvid Heise
 */
public class ConcatenatingChildIterator implements ChildIterator {
	private ChildIterator[] iterators;

	private int currentIterator = 0, index = -1;

	public ConcatenatingChildIterator(ChildIterator... iterators) {
		this.iterators = iterators;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.ListIterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		while (this.currentIterator < this.iterators.length) {
			if (this.iterators[this.currentIterator].hasNext())
				return true;
			this.currentIterator++;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.ListIterator#next()
	 */
	@Override
	public EvaluationExpression next() {
		while (this.currentIterator < this.iterators.length) {
			if (this.iterators[this.currentIterator].hasNext()) {
				this.index++;
				return this.iterators[this.currentIterator].next();
			}
			this.currentIterator++;
		}
		throw new NoSuchElementException();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.ListIterator#hasPrevious()
	 */
	@Override
	public boolean hasPrevious() {
		while (this.currentIterator > 0) {
			if (this.iterators[this.currentIterator].hasPrevious())
				return true;
			this.currentIterator--;
		}
		return this.iterators[this.currentIterator].hasPrevious();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.ListIterator#previous()
	 */
	@Override
	public EvaluationExpression previous() {
		while (this.currentIterator > 0) {
			if (this.iterators[this.currentIterator].hasPrevious()) {
				this.index--;
				return this.iterators[this.currentIterator].previous();
			}
			this.currentIterator--;
		}
		if (this.iterators[this.currentIterator].hasPrevious()) {
			this.index--;
			return this.iterators[this.currentIterator].previous();
		}
		throw new NoSuchElementException();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.ListIterator#nextIndex()
	 */
	@Override
	public int nextIndex() {
		return this.index + 1;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.ListIterator#previousIndex()
	 */
	@Override
	public int previousIndex() {
		return this.index - 1;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.ListIterator#remove()
	 */
	@Override
	public void remove() {
		this.checkValidState();
		this.iterators[this.currentIterator].remove();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.ListIterator#set(java.lang.Object)
	 */
	@Override
	public void set(EvaluationExpression e) {
		this.checkValidState();
		this.iterators[this.currentIterator].set(e);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.ListIterator#add(java.lang.Object)
	 */
	@Override
	public void add(EvaluationExpression e) {
		this.checkValidState();
		this.iterators[this.currentIterator].add(e);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.tree.ChildIterator#canChildrenBeRemoved()
	 */
	@Override
	public boolean canChildBeRemoved() {
		this.checkValidState();
		return this.iterators[this.currentIterator].canChildBeRemoved();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.tree.ChildIterator#getChildName()
	 */
	@Override
	public String getChildName() {
		this.checkValidState();
		return this.iterators[this.currentIterator].getChildName();
	}

	/**
	 * 
	 */
	private void checkValidState() {
		if (this.index < 0)
			throw new IllegalStateException("Not initialized yet");
		if (this.currentIterator >= this.iterators.length)
			throw new IllegalStateException("No more iterators");
	}

}
