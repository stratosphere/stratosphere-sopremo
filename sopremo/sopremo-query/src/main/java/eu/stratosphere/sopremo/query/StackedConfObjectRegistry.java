package eu.stratosphere.sopremo.query;

import eu.stratosphere.sopremo.operator.ConfigurableSopremoType;
import eu.stratosphere.sopremo.packages.NameChooser;

public class StackedConfObjectRegistry<T extends ConfigurableSopremoType> extends
		StackedRegistry<ConfObjectInfo<T>, IConfObjectRegistry<T>> implements
		IConfObjectRegistry<T> {

	public StackedConfObjectRegistry(NameChooser nameChooser, NameChooser propertyNameChooser) {
		super(new DefaultConfObjectRegistry<T>(nameChooser, propertyNameChooser));
	}

	@Override
	public void put(Class<? extends T> clazz) {
		this.getTopRegistry().put(clazz);
	}
}