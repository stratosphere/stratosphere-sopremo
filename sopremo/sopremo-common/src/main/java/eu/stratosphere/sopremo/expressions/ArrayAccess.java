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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javolution.text.TypeFormat;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.sopremo.type.NullNode;

/**
 * Returns one or more elements of an array.<br>
 * There are two special cases supported when specifying the indices.
 * <ul>
 * <li>When one or both indices are negatives, the position is counted from the rear of the list. More specifically, the
 * index will be added to the size of the array.
 * <li>If the first index is higher than the second index, the returned list will still contain elements within the
 * range but in reversed order.
 * </ul>
 * 
 * @author Arvid Heise
 */
@OptimizerHints(scope = Scope.ARRAY, iterating = true)
public class ArrayAccess extends PathSegmentExpression {

	private final int startIndex, endIndex;

	/**
	 * Initializes ArrayAccess that reproduces any input array.
	 */
	public ArrayAccess() {
		this(0, -1);
	}

	/**
	 * Initializes ArrayAccess that selects one element at a given location. If the location is negative, it will be
	 * added to the size of the array to allow selection of rear elements of arrays with unknown size.
	 * 
	 * @param index
	 *        the index of the element
	 */
	public ArrayAccess(final int index) {
		this(index, index);
	}

	/**
	 * Initializes ArrayAccess to return a subarray ranging from the start to the end location. If a location is
	 * negative, it will be added to the size of the array to allow selection of rear elements of arrays with unknown
	 * size.
	 * 
	 * @param startIndex
	 *        the start index
	 * @param endIndex
	 *        the end index (inclusive)
	 */
	public ArrayAccess(final int startIndex, final int endIndex) {
		// if (0 <= startIndex && 0 <= endIndex && endIndex < startIndex)
		// throw new IllegalArgumentException("startIndex < endIndex");
		// if (startIndex < 0 && endIndex < 0 && startIndex < endIndex)
		// throw new IllegalArgumentException("negative endIndex < negative startIndex");
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#equalsSameClass(eu.stratosphere.sopremo.expressions
	 * .PathSegmentExpression)
	 */
	@Override
	public boolean equalsSameClass(PathSegmentExpression obj) {
		final ArrayAccess other = (ArrayAccess) obj;
		return this.startIndex == other.startIndex && this.endIndex == other.endIndex;
	}

	private final transient IArrayNode<IJsonNode> result = new ArrayNode<IJsonNode>();

	@Override
	protected IJsonNode evaluateSegment(final IJsonNode node) {
		if (!(node instanceof IArrayNode<?>))
			return MissingNode.getInstance();

		final IArrayNode<?> arrayNode = (IArrayNode<?>) node;
		if (this.isSelectingAll()) {
			this.result.clear();
			this.result.addAll(arrayNode);
			return this.result;
		}
		final int size = arrayNode.size();
		if (this.isSelectingRange()) {
			this.result.clear();
			int index = this.resolveIndex(this.startIndex, size);
			final int endIndex = this.resolveIndex(this.endIndex, size);
			final int increment = index < endIndex ? 1 : -1;

			for (boolean moreElements = true; moreElements; index += increment) {
				this.result.add(arrayNode.get(index));
				moreElements = index != endIndex;
			}
			return this.result;
		}

		final IJsonNode value = arrayNode.get(this.resolveIndex(this.startIndex, size));
		return value == null ? NullNode.getInstance() : value;
	}

	/**
	 * Returns the endIndex.
	 * 
	 * @return the endIndex
	 */
	public int getEndIndex() {
		return this.endIndex;
	}

	/**
	 * Returns the startIndex.
	 * 
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return this.startIndex;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#segmentHashCode()
	 */
	@Override
	protected int segmentHashCode() {
		return this.startIndex * 47 + this.endIndex;
	}

	/**
	 * Returns true if any incoming array would be wholly reproduced.
	 * 
	 * @return true if any incoming array would be wholly reproduced
	 */
	public boolean isSelectingAll() {
		return this.startIndex == 0 && this.endIndex == -1;
	}

	/**
	 * Returns true if more than one element is selected.
	 * 
	 * @return true if more than one element is selected
	 */
	public boolean isSelectingRange() {
		return this.startIndex != this.endIndex;
	}

	public boolean isFixedSize() {
		return this.startIndex >= 0 == this.endIndex >= 0;
	}

	public int[] getIndices() {
		if (!isFixedSize())
			return null;
		if (this.startIndex >= 0) { // normal access
			int[] indices = new int[this.endIndex - this.startIndex + 1];
			for (int index = this.startIndex; index <= this.endIndex; index++)
				indices[index - this.startIndex] = index;
			return indices;
		}
		// backward array
		int[] indices = new int[-this.startIndex - this.endIndex + 1];
		for (int index = this.startIndex; index <= this.endIndex; index++)
			indices[this.startIndex - index] = index;
		return indices;
	}

	public Collection<ArrayAccess> decompose() {
		if (!isFixedSize())
			throw new IllegalStateException("Not decomposable");

		if (!isSelectingRange())
			return Arrays.asList(this);

		final ArrayList<ArrayAccess> accesses = new ArrayList<ArrayAccess>();
		for (int index = this.startIndex; index <= this.endIndex; index++)
			accesses.add(new ArrayAccess(index));
		return accesses;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#setSegment(eu.stratosphere.sopremo.type.IJsonNode,
	 * eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected IJsonNode setSegment(IJsonNode node, IJsonNode value) {
		if (this.isSelectingAll())
			return value;
		final IArrayNode<IJsonNode> arrayNode = (IArrayNode<IJsonNode>) node;
		final int size = arrayNode.size();
		if (this.isSelectingRange()) {
			int index = this.resolveIndex(this.startIndex, size), replaceIndex = 0;
			final int endIndex = this.resolveIndex(this.endIndex, size);

			final int increment = index < endIndex ? 1 : -1;

			final IArrayNode<?> otherArray = (IArrayNode<?>) node;
			for (boolean moreElements = true; moreElements; index += increment, replaceIndex++) {
				SopremoUtil.replaceWithCopy(arrayNode, index, otherArray.get(replaceIndex));
				moreElements = index != endIndex;
			}
		} else
			SopremoUtil.replaceWithCopy(arrayNode, this.resolveIndex(this.startIndex, size), value);
		return node;
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.getInputExpression().appendAsString(appendable);
		appendable.append('[');
		if (this.isSelectingAll())
			appendable.append('*');
		else {
			TypeFormat.format(this.startIndex, appendable);
			if (this.startIndex != this.endIndex) {
				appendable.append(':');
				TypeFormat.format(this.endIndex, appendable);
			}
		}
		appendable.append(']');
	}

	private int resolveIndex(final int index, final int size) {
		if (index < 0)
			return size + index;
		return index;
	}

	/**
	 * Returns an optimal expression that returns an array that aggregates the given indices.<br>
	 * Please note that the result of this expression is always an array in contrast to an ArrayAccess with only one
	 * index.
	 * 
	 * @param indices
	 *        the indices in the original array that should be concatenated to a new array
	 * @return an optimal expression that evaluates to an array
	 */
	public static EvaluationExpression arrayWithIndices(final int... indices) {
		switch (indices.length) {
		case 0:
			return new ArrayCreation();
		case 1:
			return new ArrayCreation(new ArrayAccess(indices[0]));
		default:
			boolean monoton = true;
			final int step = indices[1] - indices[0];
			if (Math.abs(step) != 1)
				monoton = false;

			for (int index = 2; monoton && index < indices.length; index += step)
				monoton = indices[index] - indices[index - 1] == step;

			if (monoton)
				return new ArrayAccess(indices[0], indices[indices.length - 1]);

			final ArrayAccess[] accesses = new ArrayAccess[indices.length];
			for (int index = 0; index < indices.length; index++)
				accesses[index] = new ArrayAccess(indices[index]);
			return new ArrayCreation();
		}
	}
}