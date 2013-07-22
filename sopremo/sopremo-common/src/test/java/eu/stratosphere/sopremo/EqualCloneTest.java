package eu.stratosphere.sopremo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Iterables;

import eu.stratosphere.util.reflect.ReflectUtil;

@Ignore
public abstract class EqualCloneTest<T extends ICloneable> extends EqualVerifyTest<T> {
	@SuppressWarnings("unchecked")
	@Test
	public void testClone() throws IllegalAccessException {
		for (T original : Iterables.concat(Arrays.asList(this.first, this.second), this.more)) {
			final Object clone = original.clone();
			this.testPropertyClone(this.first.getClass(), original, clone);
		}
	}

	protected void testPropertyClone(Class<?> type, Object original, Object clone) throws IllegalAccessException {
		for (Field field : type.getDeclaredFields()) {
			// final Class<?> propertyType = field.getType();
			if ((field.getModifiers() & (Modifier.STATIC | Modifier.TRANSIENT)) != 0)
				continue;
			field.setAccessible(true);

			final boolean immutable = this.isImmutable(field.getType());
			final Object originalValue = field.get(original);
			final Object clonedValue = field.get(clone);
			if (immutable || originalValue == null
				|| ReflectUtil.getAnnotation(originalValue.getClass(), Immutable.class) != null
				|| ReflectUtil.getAnnotation(field, Immutable.class) != null)
				Assert.assertSame(String.format("Singleton field %s is cloned improperly", field.getName()),
					clonedValue, originalValue);
			else
				Assert.assertNotSame(String.format("Field %s is not cloned properly", field.getName()),
					clonedValue, originalValue);
		}
		if (type.getSuperclass() != null)
			this.testPropertyClone(type.getSuperclass(), original, clone);
	}

	/**
	 * @param type
	 * @return
	 */
	private boolean isImmutable(Class<?> type) {
		return type == String.class
			|| type.isEnum()
			|| type == Class.class
			|| Number.class.isAssignableFrom(type);
	}
}
