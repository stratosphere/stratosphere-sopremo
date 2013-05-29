package eu.stratosphere.sopremo.base.join;

import java.util.ArrayList;

import eu.stratosphere.sopremo.expressions.ObjectCreation;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.type.CachingArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;
import eu.stratosphere.sopremo.type.NullNode;

@InputCardinality(2)
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
