package eu.stratosphere.sopremo.packages;

import eu.stratosphere.sopremo.operator.Name;


public interface NameChooser {
	/**
	 * Return the lookup name for the given {@link Name} annotation.
	 */
	public String[] getNames(Name nameAnnotation);
}