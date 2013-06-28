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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.EvaluationException;
import eu.stratosphere.sopremo.ICloneable;
import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.expressions.tree.ChildIterator;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.sopremo.type.NullNode;
import eu.stratosphere.sopremo.type.ObjectNode;

/**
 * Creates an object with the given {@link Mapping}s.
 */
@OptimizerHints(scope = Scope.ANY)
public class ObjectCreation extends EvaluationExpression {
	/**
	 * An ObjectCreation which copies the fields of all given {IObjectNode}s into a single node.
	 */
	public static final ObjectCreation CONCATENATION = new Concatenation();

	private List<Mapping<?>> mappings;

	/**
	 * Initializes an ObjectCreation with empty mappings.
	 */
	public ObjectCreation() {
		this(new ArrayList<Mapping<?>>());
	}

	/**
	 * Initializes an ObjectCreation with the given {@link Mapping}s.
	 * 
	 * @param mappings
	 *        the mappings that should be used
	 */
	public ObjectCreation(final List<Mapping<?>> mappings) {
		this.mappings = mappings;
	}

	/**
	 * Initializes an ObjectCreation with the given {@link FieldAssignment}s.
	 * 
	 * @param mappings
	 *        the assignments that should be used
	 */
	public ObjectCreation(final FieldAssignment... mappings) {
		this.mappings = new ArrayList<Mapping<?>>(Arrays.asList(mappings));
	}

	/**
	 * Adds a new {@link Mapping}
	 * 
	 * @param mapping
	 *        the new mapping
	 */
	public ObjectCreation addMapping(final Mapping<?> mapping) {
		if (mapping == null)
			throw new NullPointerException();
		this.mappings.add(mapping);
		return this;
	}

	/**
	 * Creates a new {@link FieldAssignment} and adds it to this expressions mappings.
	 * 
	 * @param target
	 *        the fieldname
	 * @param expression
	 *        the expression that should be used for the created FieldAssignemt
	 */
	public ObjectCreation addMapping(final String target, final EvaluationExpression expression) {
		if (target == null || expression == null)
			throw new NullPointerException();
		this.mappings.add(new FieldAssignment(target, expression));
		return this;
	}

	/**
	 * Creates a new {@link ExpressionAssignment} and adds it to this expressions mappings.
	 * 
	 * @param target
	 *        the expression that specifies the target location
	 * @param expression
	 *        the expression that should be used for the created FieldAssignemt
	 */
	public ObjectCreation addMapping(final PathSegmentExpression target, final EvaluationExpression expression) {
		if (target == null || expression == null)
			throw new NullPointerException();
		this.mappings.add(new ExpressionAssignment(target, expression));
		return this;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!super.equals(obj))
			return false;
		final ObjectCreation other = (ObjectCreation) obj;
		return this.mappings.equals(other.mappings);
	}

	private transient final IObjectNode result = new ObjectNode();

	@Override
	public IJsonNode evaluate(final IJsonNode node) {
		this.result.clear();
		for (final Mapping<?> mapping : this.mappings)
			mapping.evaluate(node, this.result);
		return this.result;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.ExpressionParent#iterator()
	 */
	@Override
	public ChildIterator iterator() {
		return new MappingIterator();
	}

	/**
	 * Returns the mapping at the specified index
	 * 
	 * @param index
	 *        the index of the mapping that should be returned
	 * @return the mapping at the specified index
	 */
	public Mapping<?> getMapping(final int index) {
		return this.mappings.get(index);
	}

	/**
	 * Returns the mappings
	 * 
	 * @return the mappings
	 */
	public List<Mapping<?>> getMappings() {
		return this.mappings;
	}

	/**
	 * Returns how many mappings are specified
	 * 
	 * @return the mapping count
	 */
	public int getMappingSize() {
		return this.mappings.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.mappings.hashCode();
		return result;
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append("{");
		final Iterator<Mapping<?>> mappingIterator = this.mappings.iterator();
		while (mappingIterator.hasNext()) {
			final Mapping<?> entry = mappingIterator.next();
			entry.appendAsString(appendable);
			if (mappingIterator.hasNext())
				appendable.append(", ");
		}
		appendable.append("}");
	}

	/**
	 * @author Arvid Heise
	 */
	private static final class Concatenation extends ObjectCreation {
		private transient final IObjectNode result = new ObjectNode();

		@Override
		public IJsonNode evaluate(final IJsonNode node) {
			this.result.clear();
			for (final IJsonNode jsonNode : (IArrayNode<?>) node)
				if (jsonNode != MissingNode.getInstance())
					this.result.putAll((IObjectNode) jsonNode);
			return this.result;
		}
	}

	/**
	 * @author Arvid Heise
	 */
	private final class MappingIterator implements ChildIterator {
		private boolean lastReturnedWasKey = false;

		private ListIterator<Mapping<?>> iterator = ObjectCreation.this.mappings.listIterator();

		private Mapping<?> lastMapping;

		private int index = 0;

		/*
		 * (non-Javadoc)
		 * @see java.util.ListIterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return this.lastReturnedWasKey || this.iterator.hasNext();
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.ListIterator#next()
		 */
		@Override
		public EvaluationExpression next() {
			this.index++;
			if (this.lastReturnedWasKey)
				return this.lastMapping.expression;
			this.lastMapping = this.iterator.next();
			if (this.lastReturnedWasKey = this.lastMapping.target instanceof EvaluationExpression)
				return (EvaluationExpression) this.lastMapping.target;
			return this.lastMapping.expression;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.ListIterator#hasPrevious()
		 */
		@Override
		public boolean hasPrevious() {
			return this.iterator.hasPrevious() ||
				!this.lastReturnedWasKey && this.lastMapping.target instanceof EvaluationExpression;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.ListIterator#previous()
		 */
		@Override
		public EvaluationExpression previous() {
			this.index--;
			if (!this.lastReturnedWasKey &&
				(this.lastReturnedWasKey = this.lastMapping.target instanceof EvaluationExpression))
				return (EvaluationExpression) this.lastMapping.target;
			return this.lastMapping.expression;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.ListIterator#nextIndex()
		 */
		@Override
		public int nextIndex() {
			return this.index;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.ListIterator#previousIndex()
		 */
		@Override
		public int previousIndex() {
			return this.index;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.ListIterator#remove()
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.ListIterator#set(java.lang.Object)
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void set(EvaluationExpression e) {
			if (this.lastReturnedWasKey)
				((Mapping) this.lastMapping).target = e;
			else
				this.lastMapping.expression = e;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.ListIterator#add(java.lang.Object)
		 */
		@Override
		public void add(EvaluationExpression e) {
			throw new UnsupportedOperationException();
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.tree.ChildIterator#isNamed()
		 */
		@Override
		public boolean canChildBeRemoved() {
			return false;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.tree.ChildIterator#getChildName()
		 */
		@Override
		public String getChildName() {
			return null;
		}
	}

	/**
	 * An ObjectCreation which copies the fields of an {@link IObjectNode}.
	 */
	public static class CopyFields extends FieldAssignment {
		public CopyFields(final EvaluationExpression expression) {
			super("*", expression);
		}

		/**
		 * Initializes ObjectCreation.CopyFields.
		 */
		CopyFields() {
		}
		
		/* (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.ObjectCreation.FieldAssignment#getTargetExpression()
		 */
		@Override
		public PathSegmentExpression getTargetExpression() {
			return EvaluationExpression.VALUE;
		}

		@Override
		protected void evaluate(final IJsonNode node, final IObjectNode target) {
			final IJsonNode exprNode = this.getExpression().evaluate(node);
			target.putAll((IObjectNode) exprNode);
		}

		@Override
		public void appendAsString(final Appendable appendable) throws IOException {
			this.getExpression().appendAsString(appendable);
			appendable.append(".*");
		}
	}

	public static class FieldAssignment extends Mapping<String> {
		/**
		 * Initializes FieldAssignment.
		 * 
		 * @param target
		 *        the fieldname
		 * @param expression
		 *        the expression that evaluates to this fields value
		 */
		public FieldAssignment(final String target, final EvaluationExpression expression) {
			super(target, expression);
		}

		/**
		 * Initializes ObjectCreation.FieldAssignment.
		 */
		FieldAssignment() {
		}

		@Override
		protected void evaluate(final IJsonNode node, final IObjectNode target) {
			final IJsonNode value = this.expression.evaluate(node);
			// if (!value.isNull())
			target.put(this.target, value);
		}

		/* (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.ObjectCreation.Mapping#getTargetExpression()
		 */
		@Override
		public PathSegmentExpression getTargetExpression() {
			return new ObjectAccess(this.target);
		}
		
		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.ObjectCreation.Mapping#appendTarget(java.lang.Appendable)
		 */
		@Override
		protected void appendTarget(Appendable appendable) throws IOException {
			appendable.append(this.target);
		}
	}

	public static class TagMapping extends Mapping<EvaluationExpression> {
		/**
		 * Initializes TagMapping.
		 * 
		 * @param target
		 * @param expression
		 */
		public TagMapping(final EvaluationExpression target, final EvaluationExpression expression) {
			super(target, expression);
		}

		/**
		 * Initializes ObjectCreation.TagMapping.
		 */
		TagMapping() {
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.expressions.ObjectCreation.Mapping#evaluate(eu.stratosphere.sopremo.type.ObjectNode,
		 * eu.stratosphere.sopremo.type.IJsonNode, eu.stratosphere.sopremo.EvaluationContext)
		 */
		@Override
		protected void evaluate(final IJsonNode node, final IObjectNode target) {
			throw new EvaluationException("Only tag mapping");
		}
		
		/* (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.ObjectCreation.Mapping#getTargetExpression()
		 */
		@Override
		public PathSegmentExpression getTargetExpression() {
			return (PathSegmentExpression) this.target;
		}
		
		/* (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.ObjectCreation.Mapping#getTargetTagExpression()
		 */
		@Override
		public EvaluationExpression getTargetTagExpression() {
			return this.target;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.ObjectCreation.Mapping#appendTarget(java.lang.Appendable)
		 */
		@Override
		protected void appendTarget(Appendable appendable) throws IOException {
			this.target.appendAsString(appendable);
		}
	}

	public static class ExpressionAssignment extends Mapping<PathSegmentExpression> {

		public ExpressionAssignment(final PathSegmentExpression target, final EvaluationExpression expression) {
			super(target, expression);
		}

		/**
		 * Initializes ObjectCreation.ExpressionAssignment.
		 */
		ExpressionAssignment() {
		}

		private IJsonNode lastResult;

		@Override
		protected void evaluate(final IJsonNode node, final IObjectNode target) {
			this.lastResult = this.expression.evaluate(node);
			this.target.set(target, this.lastResult);
		}

		/* (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.ObjectCreation.Mapping#getTargetExpression()
		 */
		@Override
		public PathSegmentExpression getTargetExpression() {
			return this.target;
		}
		
		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.expressions.ObjectCreation.Mapping#appendTarget(java.lang.Appendable)
		 */
		@Override
		protected void appendTarget(Appendable appendable) throws IOException {
			this.target.appendAsString(appendable);
		}
	}

	public abstract static class Mapping<Target> extends AbstractSopremoType implements ISopremoType,
			ICloneable {
		protected Target target;

		protected EvaluationExpression expression;

		/**
		 * Initializes Mapping with the given {@link Target} and the given {@link EvaluationExpression}.
		 * 
		 * @param target
		 *        the target of this mapping
		 * @param expression
		 *        the expression that evaluates to this mappings value
		 */
		public Mapping(final Target target, final EvaluationExpression expression) {
			this.target = target;
			this.expression = expression;
		}

		/**
		 * Initializes ObjectCreation.Mapping.
		 */
		Mapping() {
		}

		/**
		 * Sets the expression to the specified value.
		 * 
		 * @param expression
		 *        the expression to set
		 */
		public void setExpression(final EvaluationExpression expression) {
			if (expression == null)
				throw new NullPointerException("expression must not be null");

			this.expression = expression;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (this.getClass() != obj.getClass())
				return false;
			final Mapping<?> other = (Mapping<?>) obj;
			return this.target.equals(other.target) && this.expression.equals(other.expression);
		}

		protected abstract void evaluate(IJsonNode node, IObjectNode target);

		/**
		 * Returns the expression
		 * 
		 * @return the expression
		 */
		public EvaluationExpression getExpression() {
			return this.expression;
		}

		/**
		 * Returns the target
		 * 
		 * @return the target
		 */
		public Target getTarget() {
			return this.target;
		}
		
		public abstract PathSegmentExpression getTargetExpression();
		
		public EvaluationExpression getTargetTagExpression() {
			return getTargetExpression();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + this.expression.hashCode();
			result = prime * result + this.target.hashCode();
			return result;
		}

		@Override
		public void appendAsString(final Appendable appendable) throws IOException {
			this.appendTarget(appendable);
			appendable.append("=");
			this.expression.appendAsString(appendable);
		}

		protected abstract void appendTarget(Appendable appendable) throws IOException;
	}

}
