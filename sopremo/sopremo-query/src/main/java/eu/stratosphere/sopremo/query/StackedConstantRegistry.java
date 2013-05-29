package eu.stratosphere.sopremo.query;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.packages.DefaultConstantRegistry;
import eu.stratosphere.sopremo.packages.IConstantRegistry;

public class StackedConstantRegistry extends StackedRegistry<EvaluationExpression, IConstantRegistry> implements
		IConstantRegistry {
	public StackedConstantRegistry() {
		super(new DefaultConstantRegistry());
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.packages.IConstantRegistry#put(java.lang.Class)
	 */
	@Override
	public void put(Class<?> javaConstants) {
		this.getTopRegistry().put(javaConstants);
	}
}