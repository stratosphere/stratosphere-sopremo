package eu.stratosphere.sopremo.query;

import eu.stratosphere.sopremo.operator.ConfigurableSopremoType;

public class StackedConfObjectRegistry<T extends ConfigurableSopremoType> extends
		StackedRegistry<ConfObjectInfo<T>, IConfObjectRegistry<T>> implements
		IConfObjectRegistry<T> {

	public StackedConfObjectRegistry() {
		super(new DefaultConfObjectRegistry<T>());
	}

	@Override
	public void put(Class<? extends T> clazz) {
		this.getTopRegistry().put(clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.query.IOperatorRegistry#getName(java.lang.Class)
	 */
	@Override
	public String getName(Class<? extends T> operatorClass) {
		return this.getTopRegistry().getName(operatorClass);
	}
}