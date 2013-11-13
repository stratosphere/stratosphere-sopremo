package eu.stratosphere.sopremo.base.join;

import eu.stratosphere.sopremo.expressions.ObjectCreation;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Internal;

@InputCardinality(2)
@Internal
public abstract class TwoSourceJoinBase<Self extends TwoSourceJoinBase<Self>> extends ElementaryOperator<Self> {
	public TwoSourceJoinBase() {
		super();
		this.setResultProjection(ObjectCreation.CONCATENATION);
	}

	public TwoSourceJoinBase(int minInputs, int maxInputs) {
		super(minInputs, maxInputs);
		this.setResultProjection(ObjectCreation.CONCATENATION);
	}

	public TwoSourceJoinBase(int inputs) {
		super(inputs);
		this.setResultProjection(ObjectCreation.CONCATENATION);
	}
}
