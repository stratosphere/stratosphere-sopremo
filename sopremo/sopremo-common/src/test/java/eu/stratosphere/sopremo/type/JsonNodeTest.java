package eu.stratosphere.sopremo.type;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import eu.stratosphere.sopremo.EqualCloneTest;
import eu.stratosphere.util.reflect.BoundTypeUtil;
import eu.stratosphere.util.reflect.ReflectUtil;

public abstract class JsonNodeTest<T extends IJsonNode> extends EqualCloneTest<T> {
	// generic tests for every JsonNode

	protected T node;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			this.node =
				(T) ReflectUtil.newInstance(
					BoundTypeUtil.getBindingOfSuperclass(this.getClass(), JsonNodeTest.class).getParameters()[0]
						.getType());
		} catch (final Exception e) {
			throw new AssertionError(e);
		}
	}

	@Test
	public void testToString() {
		Assert.assertNotSame(
			"builder did not write anything - override this test if it is indeed the desired behavior", "",
			this.node.toString());
	}

	@Test
	public void testTypeNumber() {
		Assert.assertNotNull("every JsonNode must have a TypeNumber", this.node.getType());
	}

	@Test
	public abstract void testValue();

	protected abstract IJsonNode lowerNode();

	protected abstract IJsonNode higherNode();

	@Test
	public void shouldNormalizeKeys() {
		final IJsonNode lower = this.lowerNode();
		final IJsonNode higher = this.higherNode();
		
		int length = Math.min(100, higher.getMaxNormalizedKeyLen());

		final byte[] lowerTarget = new byte[length];
		final byte[] higherTarget = new byte[length];

		lower.copyNormalizedKey(lowerTarget, 0, length);
		higher.copyNormalizedKey(higherTarget, 0, length);

		for (int i = 0; i < length; i++) {
			final byte lowerByte = lowerTarget[i];
			final byte higherByte = higherTarget[i];

			if (lowerByte < higherByte)
				break;

			Assert.assertTrue(lowerByte == higherByte);
		}
	}
}
