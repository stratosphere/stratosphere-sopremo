package eu.stratosphere.sopremo.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.reflect.TypeToken;

import eu.stratosphere.sopremo.EqualCloneTest;
import eu.stratosphere.util.reflect.ReflectUtil;

public abstract class JsonNodeTest<T extends IJsonNode> extends EqualCloneTest<T> {
	// generic tests for every JsonNode

	protected T node;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			final Type boundParamType =
				((ParameterizedType) TypeToken.of(this.getClass()).getSupertype(JsonNodeTest.class).getType()).getActualTypeArguments()[0];
			this.node = (T) ReflectUtil.newInstance(TypeToken.of(boundParamType).getRawType());
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
