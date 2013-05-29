package eu.stratosphere.sopremo.packages;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.util.reflect.ReflectUtil;

public class DefaultConstantRegistry extends DefaultRegistry<EvaluationExpression> implements IConstantRegistry {

	@Override
	public void put(final Class<?> javaConstants) {
		final List<Field> fields =
			ReflectUtil.getFields(javaConstants, null, Modifier.STATIC | Modifier.FINAL | Modifier.PUBLIC);

		for (final Field field : fields)
			if (EvaluationExpression.class.isAssignableFrom(field.getType()))
				try {
					this.put(field.getName(), (EvaluationExpression) field.get(null));
				} catch (Exception e) {
					SopremoUtil.LOG.warn(String.format("Cannot access constant %s: %s", field, e));
				}

		if (ConstantRegistryCallback.class.isAssignableFrom(javaConstants))
			((ConstantRegistryCallback) ReflectUtil.newInstance(javaConstants)).registerConstants(this);
	}
}
