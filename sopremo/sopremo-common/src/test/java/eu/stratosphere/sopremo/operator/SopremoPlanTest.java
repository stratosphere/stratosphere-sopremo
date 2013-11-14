package eu.stratosphere.sopremo.operator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import eu.stratosphere.pact.common.contract.GenericDataSink;
import eu.stratosphere.pact.common.contract.GenericDataSource;
import eu.stratosphere.pact.common.contract.ReduceContract.Combinable;
import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.pact.common.plan.Plan;
import eu.stratosphere.pact.common.stubs.Stub;
import eu.stratosphere.pact.generic.contract.Contract;
import eu.stratosphere.sopremo.EqualCloneTest;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoMap;
import eu.stratosphere.sopremo.pact.SopremoReduce;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.IStreamNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.JsonUtil;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.sopremo.type.TextNode;

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
		Assert.assertSame(pactStub, contract.getUserCodeWrapper().getUserCodeClass());
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

/**
 * Converts a (String,Integer)-KeyValuePair into multiple KeyValuePairs. The
 * key string is tokenized by spaces. For each token a new
 * (String,Integer)-KeyValuePair is emitted where the Token is the key and
 * an Integer(1) is the value.<br>
 * Expected input: { line: "word1 word2 word1" }<br>
 * Output: [{ word: "word1"}, { word: "word2"}, { word: "word1"}]
 */
@InputCardinality(1)
class TokenizeLine extends ElementaryOperator<TokenizeLine> {
	public static class Implementation extends SopremoMap {
		private static Pattern WORD_PATTERN = Pattern.compile("\\w+");

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.pact.SopremoMap#map(eu.stratosphere.sopremo.type.IJsonNode,
		 * eu.stratosphere.sopremo.pact.JsonCollector)
		 */
		@Override
		protected void map(final IJsonNode value, final JsonCollector<IJsonNode> out) {
			final Matcher matcher = WORD_PATTERN.matcher(((TextNode) ((IObjectNode) value).get("line")));
			while (matcher.find())
				out.collect(JsonUtil.createObjectNode("word", TextNode.valueOf(matcher.group())));
		}
	}
}

/**
 * Counts the number of values for a given key. Hence, the number of
 * occurences of a given token (word) is computed and emitted. The key is
 * not modified, hence a SameKey OutputContract is attached to this class.<br>
 * Expected input: [{ word: "word1"}, { word: "word1"}] <br>
 * Output: [{ word: "word1", count: 2}]
 */
@InputCardinality(1)
class CountWords extends ElementaryOperator<CountWords> {
	/**
	 * Initializes SopremoTestPlanTest.CountWords.
	 */
	public CountWords() {
		this.setKeyExpressions(0, new ObjectAccess("word"));
	}

	@Combinable
	public static class Implementation extends SopremoReduce {
		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.pact.SopremoReduce#reduce(eu.stratosphere.sopremo.type.IArrayNode,
		 * eu.stratosphere.sopremo.pact.JsonCollector)
		 */
		@Override
		protected void reduce(final IStreamNode<IJsonNode> values, final JsonCollector<IJsonNode> out) {
			final Iterator<IJsonNode> valueIterator = values.iterator();
			final IObjectNode firstEntry = (IObjectNode) valueIterator.next();
			int sum = this.getCount(firstEntry);
			while (valueIterator.hasNext())
				sum += this.getCount((IObjectNode) valueIterator.next());
			out.collect(JsonUtil.createObjectNode("word", firstEntry.get("word"), "count", sum));
		}

		protected int getCount(final IObjectNode entry) {
			final IJsonNode countNode = entry.get("count");
			if (countNode == MissingNode.getInstance())
				return 1;
			return ((IntNode) countNode).getIntValue();
		}
	}
}

@InputCardinality(1)
class Identity extends ElementaryOperator<Identity> {
	public static class Implementation extends SopremoMap {
		@Override
		protected void map(final IJsonNode value, final JsonCollector<IJsonNode> out) {
			out.collect(value);
		}
	}
}