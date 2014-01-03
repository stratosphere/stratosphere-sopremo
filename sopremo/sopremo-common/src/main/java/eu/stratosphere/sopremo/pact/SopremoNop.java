package eu.stratosphere.sopremo.pact;

import eu.stratosphere.api.common.functions.AbstractFunction;
import eu.stratosphere.api.common.functions.GenericMapper;
import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.util.Collector;

public class SopremoNop extends AbstractFunction implements GenericMapper<SopremoRecord, SopremoRecord> {

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.api.functions.GenericMapper#map(java.lang.Object,
	 * eu.stratosphere.api.record.functions.Collector)
	 */
	@Override
	public void map(final SopremoRecord record, final Collector<SopremoRecord> out) throws Exception {
		out.collect(record);
	}
}