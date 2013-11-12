package eu.stratosphere.sopremo.query;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.packages.DefaultConstantRegistry;
import eu.stratosphere.sopremo.packages.IConstantRegistry;
import eu.stratosphere.sopremo.packages.NameChooser;

public class StackedConstantRegistry extends StackedRegistry<EvaluationExpression, IConstantRegistry> implements
		IConstantRegistry {
	public StackedConstantRegistry(NameChooser constantNameChooser) {
		super(new DefaultConstantRegistry(constantNameChooser));
	}
	
	/**
	 * Initializes StackedConstantRegistry.
	 *
	 */
	StackedConstantRegistry() {
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