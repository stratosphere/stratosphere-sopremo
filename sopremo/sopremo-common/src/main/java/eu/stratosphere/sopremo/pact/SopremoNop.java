package eu.stratosphere.sopremo.pact;

import eu.stratosphere.pact.common.stubs.Collector;
import eu.stratosphere.pact.generic.stub.AbstractStub;
import eu.stratosphere.pact.generic.stub.GenericMapper;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.serialization.SopremoRecord;

public class SopremoNop extends AbstractStub implements GenericMapper<SopremoRecord, SopremoRecord>, SopremoStub {

	/* (non-Javadoc)
	 * @see eu.stratosphere.pact.generic.stub.GenericMapper#map(java.lang.Object, eu.stratosphere.pact.common.stubs.Collector)
	 */
	@Override
	public void map(SopremoRecord record, Collector<SopremoRecord> out) throws Exception {
		out.collect(record);
	}

	/* (non-Javadoc)
	 * @see eu.stratosphere.sopremo.pact.SopremoStub#getContext()
	 */
	@Override
	public EvaluationContext getContext() {
		return null;
	}
}