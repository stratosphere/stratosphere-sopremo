package eu.stratosphere.sopremo.expressions;

import java.io.IOException;

import junit.framework.Assert;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import eu.stratosphere.sopremo.EqualCloneTest;
import eu.stratosphere.sopremo.EvaluationContext;

@Ignore
public abstract class EvaluableExpressionTest<T extends EvaluationExpression> extends EqualCloneTest<T> {
	protected EvaluationContext context;

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualCloneTest#shouldComplyEqualsContract()
	 */
	@Override
	@Test
	public void shouldComplyEqualsContract() {
		super.shouldComplyEqualsContract();
	}

	@Before
	public void initContext() {
		this.context = new EvaluationContext();
	}

	@Override
	protected void initVerifier(final EqualsVerifier<T> equalVerifier) {
		super.initVerifier(equalVerifier);
		equalVerifier.suppress(Warning.TRANSIENT_FIELDS);
	}

	@Test
	public void testToString() throws IOException {
		final StringBuilder builder = new StringBuilder();
		this.first.appendAsString(builder);
		Assert.assertFalse(
			"builder did not write anything - override this test if it is indeed the desired behavior",
			builder.length() == 0);
	}

}
