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
		Assert.assertNotNull("every JsonNode must have a Type", this.node.getType());
	}
}
