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
import java.util.Collections;
import java.util.List;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.ICloneable;
import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.Immutable;
import eu.stratosphere.sopremo.SingletonSerializer;
import eu.stratosphere.sopremo.expressions.tree.ChildIterator;
import eu.stratosphere.sopremo.expressions.tree.ListChildIterator;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Base class for all evaluable expressions that can form an expression tree.<br>
 * All implementing classes are not thread-safe unless otherwise noted.
 */
public abstract class EvaluationExpression extends AbstractSopremoType implements ISopremoType,
		Iterable<EvaluationExpression>, ICloneable {

	@DefaultSerializer(ValueSerializer.class)
	@Immutable
	public static final class ValueExpression extends PathSegmentExpression  {
		/**
		 * Initializes ValueExpression.
		 * 
		 * @param textualRepresentation
		 */
		public ValueExpression() {
		}

		@Override
		public IJsonNode evaluate(final IJsonNode node) {
			return node;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#iterator()
		 */
		@Override
		public ChildIterator iterator() {
			final List<EvaluationExpression> emptyList = Collections.emptyList();
			return new ListChildIterator(emptyList.listIterator());
		}

		@Override
		public IJsonNode set(final IJsonNode node, final IJsonNode value) {
			return value;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#setInputExpression(eu.stratosphere.sopremo.expressions
		 * .EvaluationExpression)
		 */
		@Override
		public void setInputExpression(EvaluationExpression inputExpression) {
			throw new IllegalStateException();
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#getLast()
		 */
		@Override
		public PathSegmentExpression getLast() {
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#withTail(eu.stratosphere.sopremo.expressions.
		 * EvaluationExpression)
		 */
		@Override
		public PathSegmentExpression withTail(EvaluationExpression tail) {
			if (tail instanceof PathSegmentExpression)
				return (PathSegmentExpression) tail;
			return new ChainedSegmentExpression(tail);
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#hashCode()
		 */
		@Override
		public int hashCode() {
			return System.identityHashCode(this);
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#segmentHashCode()
		 */
		@Override
		protected int segmentHashCode() {
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#equalsSameClass(eu.stratosphere.sopremo.expressions
		 * .PathSegmentExpression)
		 */
		@Override
		public boolean equalsSameClass(PathSegmentExpression other) {
			return true;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.PathSegmentExpression#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			return obj == this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#evaluateSegment(eu.stratosphere.sopremo.type.IJsonNode
		 * )
		 */
		@Override
		protected IJsonNode evaluateSegment(IJsonNode node) {
			return node;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.EvaluationExpression#appendAsString(java.lang.Appendable)
		 */
		@Override
		public void appendAsString(Appendable appendable) throws IOException {
			appendable.append('x');
		}
	}

	public static class ValueSerializer extends SingletonSerializer {
		/**
		 * Initializes EvaluationExpression.ValueSerializer.
		 */
		public ValueSerializer() {
			super(VALUE);
		}
	}

	/**
	 * Represents an expression that returns the input node without any modifications. The constant is mostly used for
	 * {@link Operator}s that do not perform any transformation to the input, such as a filter operator.
	 */
	public static final PathSegmentExpression VALUE = new ValueExpression();

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public ChildIterator iterator() {
		@SuppressWarnings("unchecked")
		final List<EvaluationExpression> emptyList = Collections.EMPTY_LIST;
		return new ListChildIterator(emptyList.listIterator());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public EvaluationExpression clone() {
		return (EvaluationExpression) super.clone();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		return true;
	}

	public EvaluationExpression findFirst(final Predicate<? super EvaluationExpression> predicate) {
		if (predicate.apply(this))
			return this;
		for (EvaluationExpression child : this) {
			final EvaluationExpression expr = child.findFirst(predicate);
			if (expr != null)
				return child.findFirst(predicate);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends EvaluationExpression> T findFirst(final Class<T> evaluableClass) {
		return (T) this.findFirst(Predicates.instanceOf(evaluableClass));
	}

	@SuppressWarnings("unchecked")
	public <T extends EvaluationExpression> List<T> findAll(final Class<T> evaluableClass) {
		return (List<T>) this.findAll(Predicates.instanceOf(evaluableClass));
	}

	public List<EvaluationExpression> findAll(final Predicate<? super EvaluationExpression> predicate) {
		final ArrayList<EvaluationExpression> expressions = new ArrayList<EvaluationExpression>();
		this.findAll(predicate, expressions);
		return expressions;
	}

	private void findAll(final Predicate<? super EvaluationExpression> predicate,
			final ArrayList<EvaluationExpression> expressions) {
		if (predicate.apply(this))
			expressions.add(this);

		for (EvaluationExpression child : this)
			child.findAll(predicate, expressions);
	}

	/**
	 * Recursively invokes the transformation function on all children and on the expression itself.<br>
	 * In general, this method can modify this expression in-place.<br>
	 * To retain the original expression, next to the transformed expression, use {@link #clone()}.
	 * 
	 * @param function
	 *        the transformation function
	 * @return the transformed expression
	 */
	public EvaluationExpression transformRecursively(final Function<EvaluationExpression, EvaluationExpression> function) {
		final ChildIterator iterator = this.iterator();
		while (iterator.hasNext()) {
			EvaluationExpression evaluationExpression = iterator.next();
			iterator.set(evaluationExpression.transformRecursively(function));
		}
		return function.apply(this);
	}

	/**
	 * Replaces all expressions that satisfy the <code>replacePredicate</code> with the given
	 * <code>replaceFragment</code> .
	 * 
	 * @param replacePredicate
	 *        the predicate that indicates whether an expression should be replaced
	 * @param replaceFragment
	 *        the expression which should replace another one
	 * @return the expression with the replaces
	 */
	public EvaluationExpression replace(final Predicate<? super EvaluationExpression> replacePredicate,
			final EvaluationExpression replaceFragment) {
		return this.replace(replacePredicate, new TransformFunction() {
			@Override
			public EvaluationExpression apply(final EvaluationExpression argument) {
				return replaceFragment;
			}
		});
	}

	/**
	 * Replaces all expressions that satisfy the <code>replacePredicate</code> with the given
	 * <code>replaceFunction</code> .
	 * 
	 * @param replacePredicate
	 *        the predicate that indicates whether an expression should be replaced
	 * @param replaceFunction
	 *        the function that is used to replace an expression
	 * @return the expression with the replaces
	 */
	public EvaluationExpression replace(final Predicate<? super EvaluationExpression> replacePredicate,
			final Function<EvaluationExpression, EvaluationExpression> replaceFunction) {
		return this.transformRecursively(new TransformFunction() {
			@Override
			public EvaluationExpression apply(final EvaluationExpression evaluationExpression) {
				return replacePredicate.apply(evaluationExpression) ? replaceFunction.apply(evaluationExpression)
					: evaluationExpression;
			}
		});
	}

	/**
	 * Replaces all expressions that are equal to <code>toReplace</code> with the given <code>replaceFragment</code> .
	 * 
	 * @param toReplace
	 *        the expressions that should be replaced
	 * @param replaceFragment
	 *        the expression which should replace another one
	 * @return the expression with the replaces
	 */
	public EvaluationExpression replace(final EvaluationExpression toReplace, final EvaluationExpression replaceFragment) {
		return this.replace(Predicates.equalTo(toReplace), replaceFragment);
	}

	/**
	 * Removes all sub-trees in-place that satisfy the predicate.<br>
	 * If expressions cannot be completely removed, they are replaced by {@link EvaluationExpression#VALUE}.
	 * 
	 * @param predicate
	 *        the predicate that determines whether to remove an expression
	 * @return this expression without removed sub-expressions
	 */
	public EvaluationExpression remove(final Predicate<? super EvaluationExpression> predicate) {
		if (predicate.apply(this))
			return VALUE;

		this.removeRecursively(this, predicate);
		return this;
	}

	private void removeRecursively(final EvaluationExpression expressionParent,
			final Predicate<? super EvaluationExpression> predicate) {
		final ChildIterator iterator = expressionParent.iterator();
		while (iterator.hasNext()) {
			EvaluationExpression child = iterator.next();
			if (predicate.apply(child)) {
				if (!iterator.canChildBeRemoved())
					iterator.set(VALUE);
				else
					iterator.remove();
			} else
				child.removeRecursively(this, predicate);
		}
	}

	/**
	 * Removes all sub-trees in-place that are equal to the given expression.<br>
	 * If expressions cannot be completely removed, they are replaced by {@link EvaluationExpression#VALUE}.
	 * 
	 * @param expressionToRemove
	 *        the expression to compare to
	 * @return this expression without removed sub-expressions
	 */
	public EvaluationExpression remove(final EvaluationExpression expressionToRemove) {
		return this.remove(Predicates.equalTo(expressionToRemove));
	}

	/**
	 * Removes all sub-trees in-place that start with the given expression type.<br>
	 * If expressions cannot be completely removed, they are replaced by {@link EvaluationExpression#VALUE}.
	 * 
	 * @param expressionType
	 *        the expression type to remove
	 * @return this expression without removed sub-expressions
	 */
	public EvaluationExpression remove(final Class<?> expressionType) {
		return this.remove(Predicates.instanceOf(expressionType));
	}

	/**
	 * Evaluates the given node in the provided context.<br>
	 * The given node can either be a normal {@link JsonNode} or one of the following special nodes:
	 * <ul>
	 * <li>{@link CompactArrayNode} wrapping an array of nodes if the evaluation is performed for more than one
	 * {@link JsonStream},
	 * <li>{@link TypedStreamNode} wrapping an iterator of incoming nodes which is most likely the content of a complete
	 * {@link JsonStream} that is going to be aggregated, or
	 * <li>CompactArrayNode<IJsonNode> of StreamArrayNodes when aggregating multiple JsonStreams.
	 * </ul>
	 * <br>
	 * Consequently, the result may also be of one of the previously mentioned types.<br>
	 * The ContextType provides additional information that is relevant for the evaluation, for instance all registered
	 * functions in the {@link FunctionRegistry}.
	 * 
	 * @param node
	 *        the node that should be evaluated or a special node representing containing several nodes
	 * @param target
	 *        the target that should be used
	 * @param context
	 *        the context in which the node should be evaluated
	 * @return the node resulting from the evaluation or several nodes wrapped in a special node type
	 */
	public abstract IJsonNode evaluate(IJsonNode node);

	@Override
	public int hashCode() {
		return 37;
	}

	public EvaluationExpression simplify() {
		{
			final ChildIterator iterator = this.iterator();
			while (iterator.hasNext()) {
				EvaluationExpression evaluationExpression = iterator.next();
				iterator.set(evaluationExpression.simplify());
			}
		}
		return this;
	}

	/**
	 * Appends a string representation of this expression to the builder. The method should return the same result as
	 * {@link #toString()} but provides a better performance when a string is composed of several child expressions.
	 * 
	 * @param builder
	 *        the builder to append to
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
	}

	public String printAsTree() {
		StringBuilder builder = new StringBuilder();
		try {
			this.printAsTree(builder, 0);
		} catch (IOException e) {
		}
		return builder.toString();
	}

	protected void printAsTree(final Appendable appendable, int level) throws IOException {
		for (int index = 0; index < level; index++)
			appendable.append(' ');
		appendable.append(this.getClass().getSimpleName()).append(' ');
		this.appendAsString(appendable);
		appendable.append('\n');

		for (EvaluationExpression child : this)
			child.printAsTree(appendable, level + 1);
	}

}