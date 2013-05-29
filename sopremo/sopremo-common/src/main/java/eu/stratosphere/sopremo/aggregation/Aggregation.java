package eu.stratosphere.sopremo.aggregation;

import java.io.IOException;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.ICloneable;
import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.expressions.AggregationExpression;
import eu.stratosphere.sopremo.type.IJsonNode;

public abstract class Aggregation extends AbstractSopremoType implements ISopremoType, ICloneable {
	private final String name;

	public Aggregation(final String name) {
		this.name = name;
	}

	public abstract void aggregate(IJsonNode element);

	/**
	 * Creates an {@link AggregationExpression} for this function
	 * 
	 * @return the AggregationExpression
	 */
	public AggregationExpression asExpression() {
		return new AggregationExpression(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.name.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#clone()
	 */
	@Override
	public Aggregation clone() {
		return (Aggregation) super.clone();
	}

	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final Aggregation other = (Aggregation) obj;
		return this.name.equals(other.name);
	}

	public abstract IJsonNode getFinalAggregate();

	public void initialize() {
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append(this.name);
	}
}
