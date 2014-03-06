package eu.stratosphere.sopremo.base.join;

import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Internal;

@InputCardinality(2)
@Internal
public abstract class TwoSourceJoinBase<Self extends TwoSourceJoinBase<Self>> extends ElementaryOperator<Self> {
	public TwoSourceJoinBase() {
		super();
	}

	public TwoSourceJoinBase(final int inputs) {
		super(inputs);
	}

	public TwoSourceJoinBase(final int minInputs, final int maxInputs) {
		super(minInputs, maxInputs);
	}
}
