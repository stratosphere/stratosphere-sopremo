package eu.stratosphere.sopremo.base;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.generic.contract.Contract;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.expressions.PathSegmentExpression;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoMap;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.INumericNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.LongNode;
import eu.stratosphere.sopremo.type.ObjectNode;
import eu.stratosphere.sopremo.type.ReusingSerializer;
import eu.stratosphere.sopremo.type.TextNode;

@InputCardinality(1)
public class GlobalEnumeration extends ElementaryOperator<GlobalEnumeration> {
	public static final EvaluationExpression CONCATENATION = new ConcatenatingExpression();

	public static final EvaluationExpression LONG_COMBINATION = new LongExpression();

	public static final EvaluationExpression AUTO_ENUMERATION = null;

	private EvaluationExpression enumerationExpression = AUTO_ENUMERATION;

	private EvaluationExpression idGeneration = CONCATENATION;

	private String idFieldName = "_ID", valueFieldName = "value";

	public EvaluationExpression getEnumerationExpression() {
		return this.enumerationExpression;
	}

	public String getIdFieldName() {
		return this.idFieldName;
	}

	public EvaluationExpression getIdGeneration() {
		return this.idGeneration;
	}

	public ObjectAccess getIdAccess() {
		return new ObjectAccess(this.idFieldName);
	}
	
	/* (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.ElementaryOperator#configureContract(eu.stratosphere.pact.generic.contract.Contract, eu.stratosphere.nephele.configuration.Configuration, eu.stratosphere.sopremo.EvaluationContext, eu.stratosphere.sopremo.serialization.SopremoRecordLayout)
	 */
	@Override
	protected void configureContract(Contract contract, Configuration stubConfiguration, EvaluationContext context,
			SopremoRecordLayout layout) {
		if(this.enumerationExpression == AUTO_ENUMERATION)
			this.enumerationExpression = new AutoProjection(this.idFieldName, this.valueFieldName);
		super.configureContract(contract, stubConfiguration, context, layout);
		if(this.enumerationExpression instanceof AutoProjection)
			this.enumerationExpression = AUTO_ENUMERATION;
	}

	@Property
	public void setEnumerationExpression(final EvaluationExpression enumerationExpression) {
		if (enumerationExpression == null)
			throw new NullPointerException();

		this.enumerationExpression = enumerationExpression;
	}

	@Property
	public void setIdFieldName(final String enumerationFieldName) {
		if (enumerationFieldName == null)
			throw new NullPointerException();

		this.idFieldName = enumerationFieldName;
	}

	public GlobalEnumeration withIdFieldName(String enumerationFieldName) {
		this.setIdFieldName(enumerationFieldName);
		return this;
	}

	public GlobalEnumeration withValueFieldName(String valueFieldName) {
		this.setValueFieldName(valueFieldName);
		return this;
	}

	public GlobalEnumeration withEnumerationExpression(EvaluationExpression enumerationExpression) {
		this.setEnumerationExpression(enumerationExpression);
		return this;
	}

	public GlobalEnumeration withIdGeneration(EvaluationExpression idGeneration) {
		this.setIdGeneration(idGeneration);
		return this;
	}

	@Property
	public void setIdGeneration(final EvaluationExpression idGeneration) {
		if (idGeneration == null)
			throw new NullPointerException("idGeneration must not be null");

		this.idGeneration = idGeneration;
	}

	public String getValueFieldName() {
		return this.valueFieldName;
	}

	@Property
	public void setValueFieldName(String valueFieldName) {
		if (valueFieldName == null)
			throw new NullPointerException("valueFieldName must not be null");

		this.valueFieldName = valueFieldName;
	}

	/**
	 * Adds the id field if object; wraps the value into an object otherwise.
	 */
	static final class AutoProjection extends PathSegmentExpression {
		private final String idFieldName, valueFieldName;

		AutoProjection(String idFieldName, String valueFieldName) {
			this.idFieldName = idFieldName;
			this.valueFieldName = valueFieldName;
		}

		/**
		 * Initializes GlobalEnumeration.AutoProjection.
		 */
		AutoProjection() {
			this(null, null);
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.expressions.PathSegmentExpression#setSegment(eu.stratosphere.sopremo.type.IJsonNode,
		 * eu.stratosphere.sopremo.type.IJsonNode)
		 */
		@Override
		protected IJsonNode setSegment(IJsonNode node, IJsonNode value) {
			if (node instanceof IObjectNode) {
				((IObjectNode) node).put(this.idFieldName, value);
				return node;
			}
			ObjectNode objectNode = new ObjectNode();
			objectNode.put(this.idFieldName, value);
			objectNode.put(this.valueFieldName, node);
			return objectNode;
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
		protected boolean equalsSameClass(PathSegmentExpression other) {
			return true;
		}
	}

	/**
	 * @author Arvid Heise
	 */
	static final class LongExpression extends EvaluationExpression {
		private final transient LongNode result = new LongNode();

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.expressions.EvaluationExpression#evaluate(eu.stratosphere.sopremo.type.IJsonNode,
		 * eu.stratosphere.sopremo.type.IJsonNode, eu.stratosphere.sopremo.EvaluationContext)
		 */
		@Override
		public IJsonNode evaluate(IJsonNode node) {
			@SuppressWarnings("unchecked")
			final IArrayNode<INumericNode> values = (IArrayNode<INumericNode>) node;
			this.result.setValue((values.get(0).getLongValue() << 48) + values.get(1).getLongValue());
			return this.result;
		}
	}

	/**
	 * @author Arvid Heise
	 */
	static final class ConcatenatingExpression extends EvaluationExpression {
		private final transient StringBuilder builder = new StringBuilder();

		private final transient TextNode result = new TextNode();

		@Override
		public IJsonNode evaluate(final IJsonNode node) {
			@SuppressWarnings("unchecked")
			final IArrayNode<INumericNode> values = (IArrayNode<INumericNode>) node;
			this.builder.setLength(0);
			this.builder.append(values.get(0).getIntValue());
			this.builder.append('_');
			this.builder.append(values.get(1).getIntValue());
			this.result.setValue(this.builder);
			return this.result;
		}
	}

	public static class Implementation extends SopremoMap {
		private PathSegmentExpression enumerationExpression;

		private EvaluationExpression idGeneration;

		private LongNode counter;

		private IArrayNode<INumericNode> params;

		@Override
		public void open(Configuration parameters) {
			super.open(parameters);
			final IntNode taskId = new IntNode(parameters.getInteger("pact.parallel.task.id", 0));
			this.counter = LongNode.valueOf(0);
			this.params = new ArrayNode<INumericNode>(taskId, this.counter);
		}

		@Override
		protected void map(final IJsonNode value, final JsonCollector out) {
			this.counter.setValue(this.counter.getLongValue() + 1);
			final IJsonNode id = this.idGeneration.evaluate(this.params);
			out.collect(this.enumerationExpression.set(value, id));
		}
	}

}
