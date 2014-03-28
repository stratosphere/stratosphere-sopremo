package eu.stratosphere.sopremo.base;

import java.io.IOException;

import eu.stratosphere.api.common.operators.Operator;
import eu.stratosphere.configuration.Configuration;
import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.expressions.PathSegmentExpression;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoMap;
import eu.stratosphere.sopremo.type.*;

@Name(verb = "enumerate")
@InputCardinality(1)
public class GlobalEnumeration extends ElementaryOperator<GlobalEnumeration> {
	public static final EvaluationExpression AUTO_ENUMERATION = null, PREPEND_ID = new PrependId();

	private EvaluationExpression enumerationExpression = AUTO_ENUMERATION;

	private IdGenerator idGenerator = IdGeneration.LONG.getGenerator();

	private String idFieldName = "_ID", valueFieldName = "value";

	public EvaluationExpression getEnumerationExpression() {
		return this.enumerationExpression;
	}

	public ObjectAccess getIdAccess() {
		return new ObjectAccess(this.idFieldName);
	}

	public String getIdFieldName() {
		return this.idFieldName;
	}

	public IdGeneration getIdGeneration() {
		for (final IdGeneration idGeneration : IdGeneration.values())
			if (idGeneration.getGenerator().equals(this.getIdGenerator()))
				return idGeneration;

		return null;
	}

	/**
	 * Returns the idGenerator.
	 * 
	 * @return the idGenerator
	 */
	public IdGenerator getIdGenerator() {
		return this.idGenerator;
	}

	public String getValueFieldName() {
		return this.valueFieldName;
	}

	@Property
	@Name(preposition = "by")
	public void setEnumerationExpression(final EvaluationExpression enumerationExpression) {
		if (enumerationExpression == null)
			throw new NullPointerException();

		this.enumerationExpression = enumerationExpression;
	}

	@Property
	@Name(preposition = "with key")
	public void setIdFieldName(final String enumerationFieldName) {
		if (enumerationFieldName == null)
			throw new NullPointerException();

		this.idFieldName = enumerationFieldName;
	}

	@Property
	@Name(preposition = "by")
	public void setIdGeneration(final IdGeneration idGeneration) {
		if (idGeneration == null)
			throw new NullPointerException("idGeneration must not be null");

		this.idGenerator = idGeneration.getGenerator();
	}

	/**
	 * Sets the idGenerator to the specified value.
	 * 
	 * @param idGenerator
	 *        the idGenerator to set
	 */
	public void setIdGenerator(final IdGenerator idGenerator) {
		if (idGenerator == null)
			throw new NullPointerException("idGenerator must not be null");

		this.idGenerator = idGenerator;
	}

	@Property
	@Name(verb = "retain value in")
	public void setValueFieldName(final String valueFieldName) {
		if (valueFieldName == null)
			throw new NullPointerException("valueFieldName must not be null");

		this.valueFieldName = valueFieldName;
	}

	public GlobalEnumeration withEnumerationExpression(final EvaluationExpression enumerationExpression) {
		this.setEnumerationExpression(enumerationExpression);
		return this;
	}

	public GlobalEnumeration withIdFieldName(final String enumerationFieldName) {
		this.setIdFieldName(enumerationFieldName);
		return this;
	}

	public GlobalEnumeration withIdGeneration(final IdGeneration idGeneration) {
		this.setIdGeneration(idGeneration);
		return this;
	}

	public GlobalEnumeration withValueFieldName(final String valueFieldName) {
		this.setValueFieldName(valueFieldName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.operator.ElementaryOperator#configureOperator(eu.stratosphere.pact.generic.contract.Operator
	 * , eu.stratosphere.configuration.Configuration, eu.stratosphere.sopremo.EvaluationContext,
	 * eu.stratosphere.sopremo.serialization.SopremoRecordLayout)
	 */
	@Override
	protected void configureOperator(final Operator contract, final Configuration stubConfiguration) {
		if (this.enumerationExpression == AUTO_ENUMERATION)
			this.enumerationExpression = new AutoProjection(this.idFieldName, this.valueFieldName);
		super.configureOperator(contract, stubConfiguration);
		if (this.enumerationExpression instanceof AutoProjection)
			this.enumerationExpression = AUTO_ENUMERATION;
	}

	public static abstract class AbstractIdGenerator extends AbstractSopremoType implements IdGenerator {
		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.util.IAppending#appendAsString(java.lang.Appendable)
		 */
		@Override
		public void appendAsString(final Appendable appendable) throws IOException {
			appendable.append(this.getClass().getSimpleName());
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(final Object obj) {
			return this.getClass().equals(obj.getClass());
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return this.getClass().getSimpleName().hashCode();
		}
	}

	public enum IdGeneration {
		LONG(new LongGenerator()), STRING(new StringGenerator()), MAPPER(new MapperNumberGenerator());

		private final IdGenerator generator;

		private IdGeneration(final IdGenerator generator) {
			this.generator = generator;
		}

		/**
		 * Returns the generator.
		 * 
		 * @return the generator
		 */
		public IdGenerator getGenerator() {
			return this.generator;
		}
	}

	public static interface IdGenerator {
		public IJsonNode generate(long localId);

		public void setup(int taskId, int numTasks);
	}

	public static class Implementation extends SopremoMap {
		private PathSegmentExpression enumerationExpression;

		private IdGenerator idGenerator;

		private long counter;

		@Override
		public void open(final Configuration parameters) {
			super.open(parameters);
			this.counter = 0;
			this.idGenerator.setup(this.getRuntimeContext().getIndexOfThisSubtask(),
				this.getRuntimeContext().getNumberOfParallelSubtasks());
		}

		@Override
		protected void map(final IJsonNode value, final JsonCollector<IJsonNode> out) {
			final IJsonNode id = this.idGenerator.generate(this.counter++);
			out.collect(this.enumerationExpression.set(value, id));
		}
	}

	public static class LongGenerator extends AbstractIdGenerator {
		private final LongNode result = new LongNode();

		private long prefix;

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.base.GlobalEnumeration.IdGenerator#generate(long)
		 */
		@Override
		public IJsonNode generate(final long localId) {
			this.result.setValue(localId | this.prefix);
			return this.result;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.base.GlobalEnumeration.IdGenerator#setup(int, int)
		 */
		@Override
		public void setup(final int taskId, final int numTasks) {
			final int freeBits = Long.numberOfLeadingZeros(numTasks - 1);
			this.prefix = (long) taskId << freeBits;
		}
	}

	public static class MapperNumberGenerator extends AbstractIdGenerator {
		private final IntNode result = new IntNode();

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.base.GlobalEnumeration.IdGenerator#generate(long)
		 */
		@Override
		public IJsonNode generate(final long localId) {
			return this.result;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.base.GlobalEnumeration.IdGenerator#setup(int, int)
		 */
		@Override
		public void setup(final int taskId, final int numTasks) {
			this.result.setValue(taskId);
		}
	}

	/**
	 */
	public static final class StringGenerator extends AbstractIdGenerator {

		private final transient TextNode result = new TextNode();

		private int prefixLength;

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.base.GlobalEnumeration.IdGenerator#generate(long)
		 */
		@Override
		public IJsonNode generate(final long localId) {
			this.result.setLength(this.prefixLength);
			this.result.append(localId);
			return this.result;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.base.GlobalEnumeration.IdGenerator#setup(int, int)
		 */
		@Override
		public void setup(final int taskId, final int numTasks) {
			this.result.setLength(0);
			this.result.append(taskId);
			this.result.append('_');
			this.prefixLength = this.result.length();
		}
	}

	/**
	 * Adds the id field if object; wraps the value into an object otherwise.
	 */
	static final class AutoProjection extends PathSegmentExpression {
		private final String idFieldName, valueFieldName;

		/**
		 * Initializes GlobalEnumeration.AutoProjection.
		 */
		AutoProjection() {
			this(null, null);
		}

		AutoProjection(final String idFieldName, final String valueFieldName) {
			this.idFieldName = idFieldName;
			this.valueFieldName = valueFieldName;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#equalsSameClass(eu.stratosphere.sopremo.expressions
		 * .PathSegmentExpression)
		 */
		@Override
		protected boolean equalsSameClass(final PathSegmentExpression other) {
			return true;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#evaluateSegment(eu.stratosphere.sopremo.type.IJsonNode
		 * )
		 */
		@Override
		protected IJsonNode evaluateSegment(final IJsonNode node) {
			return node;
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
		 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#setSegment(eu.stratosphere.sopremo.type.IJsonNode,
		 * eu.stratosphere.sopremo.type.IJsonNode)
		 */
		@Override
		protected IJsonNode setSegment(final IJsonNode node, final IJsonNode value) {
			if (node instanceof IObjectNode) {
				((IObjectNode) node).put(this.idFieldName, value);
				return node;
			}
			final ObjectNode objectNode = new ObjectNode();
			objectNode.put(this.idFieldName, value);
			objectNode.put(this.valueFieldName, node);
			return objectNode;
		}
	}

	/**
	 * Adds the id field if object; wraps the value into an object otherwise.
	 */
	static final class PrependId extends PathSegmentExpression {

		/**
		 * Initializes GlobalEnumeration.AutoProjection.
		 */
		PrependId() {
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#equalsSameClass(eu.stratosphere.sopremo.expressions
		 * .PathSegmentExpression)
		 */
		@Override
		protected boolean equalsSameClass(final PathSegmentExpression other) {
			return true;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#evaluateSegment(eu.stratosphere.sopremo.type.IJsonNode
		 * )
		 */
		@Override
		protected IJsonNode evaluateSegment(final IJsonNode node) {
			return node;
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
		 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#setSegment(eu.stratosphere.sopremo.type.IJsonNode,
		 * eu.stratosphere.sopremo.type.IJsonNode)
		 */
		@SuppressWarnings("unchecked")
		@Override
		protected IJsonNode setSegment(final IJsonNode node, final IJsonNode value) {
			if (!(node instanceof IArrayNode<?>))
				throw new IllegalArgumentException("Can only prepend to arrays, but was " + node);
			((IArrayNode<IJsonNode>) node).add(0, value);
			return node;
		}
	}
}
