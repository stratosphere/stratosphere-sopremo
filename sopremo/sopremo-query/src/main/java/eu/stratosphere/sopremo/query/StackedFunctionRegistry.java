package eu.stratosphere.sopremo.query;

import java.lang.reflect.Method;

import eu.stratosphere.sopremo.function.Callable;
import eu.stratosphere.sopremo.packages.DefaultFunctionRegistry;
import eu.stratosphere.sopremo.packages.IFunctionRegistry;
import eu.stratosphere.sopremo.packages.NameChooser;

public class StackedFunctionRegistry extends StackedRegistry<Callable<?, ?>, IFunctionRegistry> implements
		IFunctionRegistry {

	public StackedFunctionRegistry(final NameChooser functionNameChooser) {
		super(functionNameChooser, new DefaultFunctionRegistry(functionNameChooser));
	}

	/**
	 * Initializes StackedFunctionRegistry.
	 */
	StackedFunctionRegistry() {
	}

	@Override
	public void put(final Class<?> javaFunctions) {
		this.getTopRegistry().put(javaFunctions);
	}

	@Override
	public void put(final Method method) {
		this.getTopRegistry().put(method);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.packages.IFunctionRegistry#put(java.lang.String, java.lang.Class, java.lang.String)
	 */
	@Override
	public void put(final String registeredName, final Class<?> clazz, final String staticMethodName) {
		this.getTopRegistry().put(registeredName, clazz, staticMethodName);
	}
}