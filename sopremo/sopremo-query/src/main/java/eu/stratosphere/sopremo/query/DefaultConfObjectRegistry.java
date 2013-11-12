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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void put(final Class<? extends T> operatorClass) {
		String name = this.getName(operatorClass);

		if (this.get(name) != null)
			throw new IllegalStateException("Duplicate operator " + name);

		this.put(name, new ConfObjectInfo(this, operatorClass, name, this.propertyNameChooser));
	}

}
