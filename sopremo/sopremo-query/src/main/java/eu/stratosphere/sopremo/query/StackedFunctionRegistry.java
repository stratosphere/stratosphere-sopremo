package eu.stratosphere.sopremo.query;

import java.lang.reflect.Method;

import eu.stratosphere.sopremo.function.Callable;
import eu.stratosphere.sopremo.packages.DefaultFunctionRegistry;
import eu.stratosphere.sopremo.packages.IFunctionRegistry;
import eu.stratosphere.sopremo.packages.NameChooser;

public class StackedFunctionRegistry extends StackedRegistry<Callable<?, ?>, IFunctionRegistry> implements
		IFunctionRegistry {

	public StackedFunctionRegistry(NameChooser functionNameChooser) {
		super(functionNameChooser, new DefaultFunctionRegistry(functionNameChooser));
	}

	@Override
	public void put(Method method) {
		this.getTopRegistry().put(method);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.packages.IFunctionRegistry#put(java.lang.String, java.lang.Class, java.lang.String)
	 */
	@Override
	public void put(String registeredName, Class<?> clazz, String staticMethodName) {
		this.getTopRegistry().put(registeredName, clazz, staticMethodName);
	}

	@Override
	public void put(Class<?> javaFunctions) {
		this.getTopRegistry().put(javaFunctions);
	}
}