package eu.stratosphere.sopremo.operator;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import eu.stratosphere.pact.common.contract.FileDataSink;
import eu.stratosphere.pact.common.contract.FileDataSource;
import eu.stratosphere.pact.common.contract.GenericDataSink;
import eu.stratosphere.pact.common.contract.GenericDataSource;
import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.pact.common.plan.Plan;
import eu.stratosphere.pact.common.stubs.Stub;
import eu.stratosphere.pact.generic.contract.Contract;
import eu.stratosphere.sopremo.EqualCloneTest;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.testing.SopremoTestPlanTest.Identity;
import eu.stratosphere.sopremo.testing.SopremoTestPlanTest.TokenizeLine;

public class SopremoPlanTest extends EqualCloneTest<SopremoPlan> {
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected SopremoPlan createDefaultInstance(int index) {
		final SopremoPlan plan = new SopremoPlan();
		final Source source = new Source();
		plan.setSinks(new Sink("file:///" + String.valueOf(index)).withInputs(source));
		return plan;
	}

	@Test
	public void shouldTranslateDifferentStrategies() {
		final SopremoPlan plan = new SopremoPlan();
		final Source source = new Source("file:///input.json");
		PolymorphOperator operator = new PolymorphOperator().withInputs(source);
		plan.setSinks(new Sink("file:///output.json").withInputs(operator));

		operator.setMethod(PolymorphOperator.Mode.TOKENIZE);
		expectPact(plan.asPactPlan(), TokenizeLine.Implementation.class);

		operator.setMethod(PolymorphOperator.Mode.IDENTITY);
		expectPact(plan.asPactPlan(), Identity.Implementation.class);
	}

	private void expectPact(Plan plan, Class<?> pactStub) {
		final PactModule module = PactModule.valueOf(plan.getDataSinks());
		final ArrayList<Contract> pacts = Lists.newArrayList(module.getReachableNodes());

		Assert.assertEquals(3, pacts.size());

		Assert.assertTrue(Iterables.removeIf(pacts, Predicates.instanceOf(GenericDataSource.class)));
		Assert.assertTrue(Iterables.removeIf(pacts, Predicates.instanceOf(GenericDataSink.class)));
		final Contract contract = Iterables.find(pacts, Predicates.instanceOf(Contract.class));
		Assert.assertNotNull(contract);
		Assert.assertSame(pactStub, contract.getUserCodeClass());
	}
}

@InputCardinality(1)
@OutputCardinality(1)
class PolymorphOperator extends ElementaryOperator<PolymorphOperator> {
	public Mode method = Mode.TOKENIZE;

	public enum Mode {
		TOKENIZE, IDENTITY;
	}

	/**
	 * Sets the method to the specified value.
	 * 
	 * @param method
	 *        the method to set
	 */
	@Property
	public void setMethod(Mode method) {
		if (method == null)
			throw new NullPointerException("method must not be null");

		this.method = method;
	}

	@Override
	protected Class<? extends Stub> getStubClass() {
		switch (this.method) {
		// the dafault case should do the same as the OpenNLP case
		case TOKENIZE:
			return TokenizeLine.Implementation.class;
		case IDENTITY:
			return Identity.Implementation.class;
		default:
			throw new IllegalStateException();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.method.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PolymorphOperator other = (PolymorphOperator) obj;
		return this.method == other.method;
	}

}
