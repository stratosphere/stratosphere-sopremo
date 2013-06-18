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
package eu.stratosphere.sopremo.expressions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoCopyable;
import com.esotericsoftware.kryo.serializers.FieldSerializer;

import eu.stratosphere.sopremo.EvaluationException;
import eu.stratosphere.sopremo.aggregation.Aggregation;
import eu.stratosphere.sopremo.expressions.tree.ChildIterator;
import eu.stratosphere.sopremo.expressions.tree.ConcatenatingChildIterator;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;

/**
 * Batch aggregates one stream of {@link IJsonNode} with several {@link AggregationFunction}s.
 * 
 * @author Arvid Heise
 */
public class BatchAggregationExpression extends PathSegmentExpression implements
		KryoCopyable<BatchAggregationExpression> {
	private final List<Partial> partials;

	private final transient IArrayNode<IJsonNode> results = new ArrayNode<IJsonNode>();

	/**
	 * Initializes a BatchAggregationExpression with the given {@link AggregationFunction}s.
	 * 
	 * @param functions
	 *        all functions that should be used
	 */
	public BatchAggregationExpression(final Aggregation... functions) {
		this(Arrays.asList(functions));
	}

	/**
	 * Initializes a BatchAggregationExpression with the given {@link AggregationFunction}s.
	 * 
	 * @param functions
	 *        a set of all functions that should be used
	 */
	public BatchAggregationExpression(final List<Aggregation> functions) {
		this.partials = new ArrayList<Partial>(functions.size());
		for (final Aggregation function : functions)
			this.partials.add(new Partial(this, function, this.partials.size()));
	}

	/**
	 * Initializes BatchAggregationExpression.
	 */
	public BatchAggregationExpression() {
		this.partials = new ArrayList<Partial>();
	}

	/**
	 * Adds a new {@link AggregationFunction}.
	 * 
	 * @param function
	 *        the function that should be added
	 * @return the function which has been added as a {@link Partial}
	 */
	public AggregationExpression add(final Aggregation function) {
		return this.add(function.clone(), EvaluationExpression.VALUE);
	}

	/**
	 * Adds a new {@link AggregationFunction} with the given preprocessing.
	 * 
	 * @param function
	 *        the function that should be added
	 * @param preprocessing
	 *        the preprocessing that should be used for this function
	 * @return the function which has been added as a {@link Partial}
	 */
	public AggregationExpression add(final Aggregation function, final EvaluationExpression preprocessing) {
		final Partial partial = new Partial(this, function.clone(), this.partials.size())
			.withInputExpression(preprocessing);
		this.partials.add(partial);
		return partial;
	}

	/**
	 * Returns the partial aggregation at the given index.
	 * 
	 * @param index
	 *        the index
	 * @return the partial aggregation
	 */
	public EvaluationExpression get(int index) {
		return this.partials.get(index);
	}

	/**
	 * Returns the partial aggregation at the given index.
	 * 
	 * @param index
	 *        the index
	 * @return the partial aggregation
	 */
	Partial getPartial(int index) {
		return this.partials.get(index);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#evaluateSegment(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	protected IJsonNode evaluateSegment(final IJsonNode node) {
		final IStreamNode<?> stream = (IStreamNode<?>) node;
		if (stream.isEmpty())
			return this.results;

		this.results.clear();

		for (Partial partial : this.partials)
			partial.getAggregation().initialize();
		for (final IJsonNode input : stream)
			for (Partial partial : this.partials) {
				final IJsonNode preprocessedValue = partial.getInputExpression().evaluate(input);
				if (preprocessedValue.isMissing())
					throw new EvaluationException(String.format("Cannot access %s for aggregation %s",
						partial.getInputExpression(), partial));
				partial.getAggregation().aggregate(preprocessedValue);
			}

		for (int index = 0; index < this.partials.size(); index++)
			this.results.set(index, this.partials.get(index).getAggregation().getFinalAggregate());

		return this.results;
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoCopyable#copy(com.esotericsoftware.kryo.Kryo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BatchAggregationExpression copy(Kryo kryo) {
		final BatchAggregationExpression copy = (BatchAggregationExpression)
			kryo.getRegistration(PathSegmentExpression.class).getSerializer().copy(kryo, this);
		copy.partials.clear();
		for (Partial partial : this.partials)
			copy.partials.add(partial.partialClone(kryo, copy));
		return copy;
	}

	private static class CloneHelper {
		private BatchAggregationExpression clone;

		public CloneHelper(BatchAggregationExpression clone) {
			this.clone = clone;
		}

		private BitSet clonedPartials = new BitSet();
	}

	private static final ThreadLocal<Map<BatchAggregationExpression, CloneHelper>> CLONE_MAP =
		new ThreadLocal<Map<BatchAggregationExpression, CloneHelper>>() {
			@Override
			protected Map<BatchAggregationExpression, CloneHelper> initialValue() {
				return new IdentityHashMap<BatchAggregationExpression, CloneHelper>();
			}
		};

	private static BatchAggregationExpression getClone(BatchAggregationExpression expression, Partial partial) {
		final Map<BatchAggregationExpression, CloneHelper> map = CLONE_MAP.get();
		CloneHelper cloneHelper = map.get(expression);
		if (cloneHelper == null || cloneHelper.clonedPartials.get(partial.index))
			map.put(expression, cloneHelper = new CloneHelper((BatchAggregationExpression) expression.clone()));
		cloneHelper.clonedPartials.set(partial.index);
		return cloneHelper.clone;
	}

	final static class Partial extends AggregationExpression {
		private final int index;

		private final BatchAggregationExpression bae;

		/**
		 * Initializes a Partial with the given function, preprocessing and index.
		 * 
		 * @param function
		 *        an {@link AggregationFunction} that should be used by this Partial
		 * @param preprocessing
		 *        the preprocessing that should be used by this Partial
		 * @param index
		 *        the index of this Partial
		 */
		public Partial(BatchAggregationExpression bae, final Aggregation function, final int index) {
			super(function);
			this.bae = bae;
			this.index = index;
		}

		/**
		 * Initializes BatchAggregationExpression.Partial.
		 */
		Partial() {
			this.bae = null;
			this.index = 0;
		}

		BatchAggregationExpression getBatch() {
			return this.bae;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#withInputExpression(eu.stratosphere.sopremo.expressions
		 * .EvaluationExpression)
		 */
		@Override
		public Partial withInputExpression(EvaluationExpression inputExpression) {
			return (Partial) super.withInputExpression(inputExpression);
		}

		@SuppressWarnings("unchecked")
		private Partial partialClone(Kryo kryo, BatchAggregationExpression outer) {
			final Partial copy = new Partial(outer, this.getAggregation().clone(), this.index);
			FieldSerializer<PathSegmentExpression> serializer =
				(FieldSerializer<PathSegmentExpression>) kryo.getSerializer(PathSegmentExpression.class);
			for (FieldSerializer<PathSegmentExpression>.CachedField<?> field : serializer.getFields())
				field.copy(this, copy);
			return copy;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#evaluate(eu.stratosphere.sopremo.type.IJsonNode)
		 */
		@Override
		public IJsonNode evaluate(IJsonNode node) {
			return ((IArrayNode<?>) this.bae.evaluate(node)).get(this.index);
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.AggregationExpression#toString(java.lang.StringBuilder)
		 */
		@Override
		public void appendAsString(Appendable appendable) throws IOException {
			this.getAggregation().appendAsString(appendable);
			appendable.append('(');
			this.bae.appendAsString(appendable);
			if (this.getInputExpression() != EvaluationExpression.VALUE)
				this.getInputExpression().appendAsString(appendable);
			appendable.append(')');
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#iterator()
		 */
		@Override
		public ChildIterator iterator() {
			return new ConcatenatingChildIterator(super.iterator(), this.bae.iterator());
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.AggregationExpression#createCopy()
		 */
		@Override
		public PathSegmentExpression clone() {
			return getClone(this.bae, this).partials.get(this.index);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#segmentHashCode()
	 */
	@Override
	protected int segmentHashCode() {
		return this.partials.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#equalsSameClass(eu.stratosphere.sopremo.expressions
	 * .PathSegmentExpression)
	 */
	@Override
	public boolean equalsSameClass(PathSegmentExpression other) {
		return this.partials.equals(((BatchAggregationExpression) other).partials);
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.getInputExpression().appendAsString(appendable);
		appendable.append('^');
	}
}