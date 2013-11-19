package eu.stratosphere.sopremo.query;

import eu.stratosphere.sopremo.operator.ConfigurableSopremoType;
import eu.stratosphere.sopremo.packages.NameChooser;

public class StackedConfObjectRegistry<T extends ConfigurableSopremoType> extends
		StackedRegistry<ConfObjectInfo<T>, IConfObjectRegistry<T>> implements
		IConfObjectRegistry<T> {

	public StackedConfObjectRegistry(NameChooser nameChooser, NameChooser propertyNameChooser) {
		super(new DefaultConfObjectRegistry<T>(nameChooser, propertyNameChooser));
	}

	/**
	 * Initializes StackedConfObjectRegistry.
	 */
	StackedConfObjectRegistry() {
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.query.IConfObjectRegistry#getPropertyNameChooser()
	 */
	@Override
	public NameChooser getPropertyNameChooser() {
		return getTopRegistry().getPropertyNameChooser();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.query.IConfObjectRegistry#get(java.lang.Class)
	 */
	@Override
	public ConfObjectInfo<T> get(Class<?> clazz) {
		return get(this.getNames(clazz)[0]);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.query.IConfObjectRegistry#put(java.lang.Class,
	 * eu.stratosphere.sopremo.query.AdditionalInfoResolver)
	 */
	@Override
	public void put(Class<? extends T> clazz, AdditionalInfoResolver additionalInfoResolver) {
		this.getTopRegistry().put(clazz, additionalInfoResolver);
	}
}