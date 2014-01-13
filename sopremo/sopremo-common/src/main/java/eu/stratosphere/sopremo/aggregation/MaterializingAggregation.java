package eu.stratosphere.sopremo.aggregation;

import eu.stratosphere.sopremo.type.CachingArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

public class MaterializingAggregation extends AssociativeAggregation<CachingArrayNode<IJsonNode>> {

	/**
	 * Initializes a new MaterializingAggregation.
	 */
	protected MaterializingAggregation() {
		super(new CachingArrayNode<IJsonNode>());
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.aggregation.Aggregation#aggregate(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public void aggregate(final IJsonNode element) {
		this.aggregator.addClone(element);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.aggregation.Aggregation#getFinalAggregate()
	 */
	@Override
	public IJsonNode getFinalAggregate() {
		return this.processNodes(this.aggregator);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.aggregation.Aggregation#initialize()
	 */
	@Override
	public void initialize() {
		this.aggregator.setSize(0);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.aggregation.AssociativeAggregation#aggregate(eu.stratosphere.sopremo.type.IJsonNode,
	 * eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	protected CachingArrayNode<IJsonNode> aggregate(final CachingArrayNode<IJsonNode> aggregator,
			final IJsonNode element) {
		this.aggregator.addClone(element);
		return this.aggregator;
	}

	protected IJsonNode processNodes(final CachingArrayNode<IJsonNode> nodeArray) {
		return nodeArray;
	}
}
