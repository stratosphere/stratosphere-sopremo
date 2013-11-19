package eu.stratosphere.sopremo.query;

import eu.stratosphere.sopremo.operator.ConfigurableSopremoType;
import eu.stratosphere.sopremo.packages.DefaultRegistry;
import eu.stratosphere.sopremo.packages.NameChooser;

public class DefaultConfObjectRegistry<T extends ConfigurableSopremoType> extends DefaultRegistry<ConfObjectInfo<T>>
		implements IConfObjectRegistry<T> {
	private final NameChooser propertyNameChooser;

	public DefaultConfObjectRegistry(NameChooser operatorNameChooser, NameChooser propertyNameChooser) {
		super(operatorNameChooser);
		this.propertyNameChooser = propertyNameChooser;
	}

	/**
	 * Initializes DefaultConfObjectRegistry.
	 */
	DefaultConfObjectRegistry() {
		this.propertyNameChooser = null;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.query.IConfObjectRegistry#getPropertyNameChooser()
	 */
	@Override
	public NameChooser getPropertyNameChooser() {
		return this.propertyNameChooser;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.query.IConfObjectRegistry#get(java.lang.Class)
	 */
	@Override
	public ConfObjectInfo<T> get(Class<?> clazz) {
		return this.get(this.getNames(clazz)[0]);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void put(final Class<? extends T> clazz, AdditionalInfoResolver additionalInfoResolver) {
		String[] names = this.getNames(clazz);

		for (String name : names) {
			if (this.get(name) != null)
				throw new IllegalStateException("Duplicate operator " + name);

			this.put(name, new ConfObjectInfo(additionalInfoResolver, getPropertyNameChooser(), clazz, name));
		}
	}

}
