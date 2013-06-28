package eu.stratosphere.sopremo.operator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.common.plan.ContractUtil;
import eu.stratosphere.pact.common.stubs.Stub;
import eu.stratosphere.pact.generic.contract.Contract;
import eu.stratosphere.pact.generic.contract.GenericMapContract;
import eu.stratosphere.pact.generic.contract.GenericReduceContract;
import eu.stratosphere.pact.generic.contract.SingleInputContract;
import eu.stratosphere.pact.generic.stub.AbstractStub;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoMap;
import eu.stratosphere.sopremo.pact.SopremoReduce;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;

/**
 * The class <code>ElementaryOperatorTest</code> contains tests for the class <code>{@link ElementaryOperator}</code>.
 * 
 * @author Arvid Heise
 */
@RunWith(PowerMockRunner.class)
public class ElementaryOperatorTest {
	/**
	 * 
	 */
	private static final SopremoRecordLayout LAYOUT = SopremoRecordLayout.create();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test(expected = IllegalStateException.class)
	@PrepareForTest(ContractUtil.class)
	public void getContractShouldFailIfContractNotInstancable() {
		PowerMockito.mockStatic(ContractUtil.class);
		Mockito.when(ContractUtil.getContractClass(OperatorWithOneStub.Implementation.class)).thenReturn(
			(Class) UninstanceableContract.class);
		new OperatorWithOneStub().getContract(LAYOUT);
	}

	@Test(expected = IllegalStateException.class)
	public void getContractShouldFailIfNoStub() {
		new OperatorWithNoStubs().getContract(LAYOUT);
	}

	@Test(expected = IllegalStateException.class)
	public void getContractShouldFailIfOnlyInstanceStub() {
		new OperatorWithInstanceStub().getContract(LAYOUT);
	}

	@Test(expected = IllegalStateException.class)
	public void getContractShouldFailIfOnlyUnknownStub() {
		new OperatorWithUnknownStub().getContract(LAYOUT);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getContractShouldReturnTheMatchingContractToTheFirstStub() {
		final SopremoRecordLayout layout = SopremoRecordLayout.create(new ObjectAccess("someField"));
		final Contract contract = new OperatorWithTwoStubs().getContract(layout);
		assertEquals(GenericReduceContract.class, contract.getClass());
		assertTrue(Arrays.asList(OperatorWithTwoStubs.Implementation1.class,
			OperatorWithTwoStubs.Implementation2.class).contains(contract.getUserCodeClass()));
	}

	@Test
	public void getContractShouldReturnTheMatchingContractToTheOnlyStub() {
		final Contract contract = new OperatorWithOneStub().getContract(LAYOUT);
		assertEquals(GenericMapContract.class, contract.getClass());
		assertEquals(OperatorWithOneStub.Implementation.class, contract.getUserCodeClass());
	}

	@SuppressWarnings({ "rawtypes" })
	public ElementaryOperator<?> getDefault() {
		return new ElementaryOperator() {
		};
	}

	@Test
	public void getStubClassShouldReturnNullIfNoStub() {
		assertEquals(null, new OperatorWithNoStubs().getStubClass());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getStubClassShouldReturnTheFirstStub() {
		final Class<? extends eu.stratosphere.pact.common.stubs.Stub> stubClass = new OperatorWithTwoStubs()
			.getStubClass();
		assertEquals(OperatorWithTwoStubs.class, stubClass.getDeclaringClass());
		assertTrue(Arrays.asList(OperatorWithTwoStubs.Implementation1.class,
			OperatorWithTwoStubs.Implementation2.class).contains(stubClass));
	}

	@Test
	public void getStubClassShouldReturnTheOnlyStub() {
		assertEquals(OperatorWithOneStub.Implementation.class,
			new OperatorWithOneStub().getStubClass());
	}

	@InputCardinality(1)
	static class OperatorWithInstanceStub extends ElementaryOperator<OperatorWithInstanceStub> {
		class Implementation extends SopremoMap {
			/*
			 * (non-Javadoc)
			 * @see eu.stratosphere.sopremo.pact.SopremoMap#map(eu.stratosphere.sopremo.type.IJsonNode,
			 * eu.stratosphere.sopremo.pact.JsonCollector)
			 */
			@Override
			protected void map(final IJsonNode value, final JsonCollector out) {
			}
		}
	}

	@InputCardinality(1)
	static class OperatorWithNoStubs extends ElementaryOperator<OperatorWithNoStubs> {
	}

	@InputCardinality(1)
	static class OperatorWithOneStub extends ElementaryOperator<OperatorWithOneStub> {
		static class Implementation extends SopremoMap {
			/*
			 * (non-Javadoc)
			 * @see eu.stratosphere.sopremo.pact.SopremoMap#map(eu.stratosphere.sopremo.type.IJsonNode,
			 * eu.stratosphere.sopremo.pact.JsonCollector)
			 */
			@Override
			protected void map(final IJsonNode value, final JsonCollector out) {
			}
		}
	}

	@InputCardinality(1)
	static class OperatorWithTwoStubs extends ElementaryOperator<OperatorWithTwoStubs> {
		/**
		 * Initializes ElementaryOperatorTest.OperatorWithTwoStubs.
		 */
		public OperatorWithTwoStubs() {
			this.setKeyExpressions(0, new ObjectAccess("someField"));
		}

		static class Implementation1 extends SopremoReduce {
			/*
			 * (non-Javadoc)
			 * @see eu.stratosphere.sopremo.pact.SopremoReduce#reduce(eu.stratosphere.sopremo.type.ArrayNode,
			 * eu.stratosphere.sopremo.pact.JsonCollector)
			 */
			@Override
			protected void reduce(final IStreamNode<IJsonNode> values, final JsonCollector out) {
			}
		}

		static class Implementation2 extends SopremoReduce {
			/*
			 * (non-Javadoc)
			 * @see eu.stratosphere.sopremo.pact.SopremoReduce#reduce(eu.stratosphere.sopremo.type.ArrayNode,
			 * eu.stratosphere.sopremo.pact.JsonCollector)
			 */
			@Override
			protected void reduce(final IStreamNode<IJsonNode> values, final JsonCollector out) {
			}
		}
	}

	@InputCardinality(1)
	static class OperatorWithUnknownStub extends ElementaryOperator<OperatorWithUnknownStub> {
		static class Implementation extends AbstractStub {

			/*
			 * (non-Javadoc)
			 * @see eu.stratosphere.pact.common.stubs.Stub#open(eu.stratosphere.nephele.configuration.Configuration)
			 */
			@Override
			public void open(final Configuration parameters) throws Exception {
			}

			/*
			 * (non-Javadoc)
			 * @see eu.stratosphere.pact.common.stubs.Stub#close()
			 */
			@Override
			public void close() throws Exception {
			}
		}
	}

	@InputCardinality(1)
	static class UninstanceableContract extends SingleInputContract<Stub> {

		public UninstanceableContract(final Class<? extends Stub> clazz, final String name) {
			super(clazz, name);
			throw new IllegalStateException("not instanceable");
		}

	}
}
