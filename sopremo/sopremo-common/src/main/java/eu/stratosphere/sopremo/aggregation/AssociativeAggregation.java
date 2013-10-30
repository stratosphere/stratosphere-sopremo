package eu.stratosphere.sopremo.aggregation;

import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.util.reflect.ReflectUtil;

public abstract class AssociativeAggregation<ElementType extends IJsonNode> extends Aggregation {
	protected final ElementType initialAggregate;

	protected transient ElementType aggregator;

	@SuppressWarnings("unchecked")
	public AssociativeAggregation(final String name, final ElementType initialAggregate) {
		super(name);
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

	@Override
	public Aggregation clone() {
		try {
			// initial aggregate does not need to be cloned as it is never modified
			return ReflectUtil.newInstance(this.getClass());
		} catch (Exception e) {
			throw new IllegalStateException("Aggregation must implement no-arg ctor", e);
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
