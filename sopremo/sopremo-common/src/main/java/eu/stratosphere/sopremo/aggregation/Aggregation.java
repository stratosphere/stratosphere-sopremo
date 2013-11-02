package eu.stratosphere.sopremo.aggregation;

import java.io.IOException;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.expressions.AggregationExpression;
import eu.stratosphere.sopremo.expressions.ArrayCreation;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Aggregates a {@link eu.stratosphere.sopremo.type.IStreamNode} to one final {@link IJsonNode}.<br/>
 * Given a (stream) array node [x<sub>1</sub>, x<sub>2</sub>, ..., x<sub>n</sub>], the following three steps need to be
 * performed.
 * <ol>
 * <li>The aggregator is intialized once by invoking {@link Aggregation#initialize()}.
 * <li>For each element {@link Aggregation#aggregate(IJsonNode)} is invoked, which combines the aggregator with the new
 * element.
 * <li>Finally, the aggregator is retrieved with {@link Aggregation#getFinalAggregate()}.
 * </ol>
 * 
 * @author Arvid Heise
 */
public abstract class Aggregation extends AbstractSopremoType implements ISopremoType {
	private final transient String name;

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

	//
	// /**
	// * @param arrayCreation
	// * @return
	// */
	// public EvaluationExpression asExpression(EvaluationExpression preprocessing) {
	// return new AggregationExpression(this, preprocessing);
	// }

	@Override
	public int hashCode() {
		return 1;
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
		return true;
	}

	public abstract IJsonNode getFinalAggregate();

	public abstract void initialize();

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append(this.name);
	}
}
