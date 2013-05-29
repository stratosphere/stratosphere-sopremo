package eu.stratosphere.sopremo.aggregation;

import eu.stratosphere.sopremo.type.CachingArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

public class MaterializingAggregation extends Aggregation {

	/**
	 * Initializes a new MaterializingAggregation with the given name.
	 * 
	 * @param name
	 *        the name that should be used
	 */
	protected MaterializingAggregation(final String name) {
		super(name);
	}

	protected transient final CachingArrayNode<IJsonNode> aggregator = new CachingArrayNode<IJsonNode>();

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.aggregation.Aggregation#initialize()
	 */
	@Override
	public void initialize() {
		this.aggregator.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.aggregation.Aggregation#aggregate(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public void aggregate(IJsonNode element) {
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

	protected IJsonNode processNodes(final CachingArrayNode<IJsonNode> nodeArray) {
		return nodeArray;
	}
}
