package eu.stratosphere.sopremo.aggregation;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.util.reflect.ReflectUtil;

@DefaultSerializer(AssociativeAggregation.AssociativeAggregationSerializer.class)
public abstract class AssociativeAggregation<ElementType extends IJsonNode> extends Aggregation {
	protected final transient ElementType initialAggregate;

	protected transient ElementType aggregator;

	@SuppressWarnings("unchecked")
	public AssociativeAggregation(final ElementType initialAggregate) {
		this.initialAggregate = initialAggregate;
		this.aggregator = (ElementType) initialAggregate.clone();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.aggregation.AggregationFunction#getFinalAggregate(eu.stratosphere.sopremo.type.IJsonNode,
	 * eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public IJsonNode getFinalAggregate() {
		return this.aggregator;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize() {
		if (this.aggregator.getType() != this.initialAggregate.getType())
			this.aggregator = (ElementType) this.initialAggregate.clone();
		else
			this.aggregator.copyValueFrom(this.initialAggregate);
	}

	/**
	 * @author arv
	 */
	public static class AssociativeAggregationSerializer extends Serializer<AssociativeAggregation<?>> {
		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#read(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Input, java.lang.Class)
		 */
		@Override
		public AssociativeAggregation<?> read(Kryo kryo, Input input, Class<AssociativeAggregation<?>> type) {
			return ReflectUtil.newInstance(type, kryo.readClassAndObject(input));
		}

		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#write(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Output, java.lang.Object)
		 */
		@Override
		public void write(Kryo kryo, Output output, AssociativeAggregation<?> object) {
			kryo.writeClassAndObject(output, object.initialAggregate);
		}
		
		/* (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#copy(com.esotericsoftware.kryo.Kryo, java.lang.Object)
		 */
		@Override
		public AssociativeAggregation<?> copy(Kryo kryo, AssociativeAggregation<?> original) {
			return ReflectUtil.newInstance(original.getClass(), original.initialAggregate);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.aggregation.Aggregation#aggregate(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public void aggregate(IJsonNode element) {
		this.aggregator = this.aggregate(this.aggregator, element);
	}

	protected abstract ElementType aggregate(ElementType aggregator, IJsonNode element);
}
