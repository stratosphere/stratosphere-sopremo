package eu.stratosphere.sopremo.query;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import eu.stratosphere.sopremo.packages.AbstractRegistry;
import eu.stratosphere.sopremo.packages.IRegistry;
import eu.stratosphere.sopremo.packages.NameChooser;

public class StackedRegistry<T, R extends IRegistry<T>> extends AbstractRegistry<T> implements IRegistry<T> {
	private final LinkedList<R> registryStack = new LinkedList<R>();

	StackedRegistry() {
		super();
	}

	public StackedRegistry(final NameChooser nameChooser, final R defaultRegistry) {
		super(nameChooser);
		this.registryStack.add(defaultRegistry);
	}

	public StackedRegistry(final R defaultRegistry) {
		super(defaultRegistry.getNameChooser());
		this.registryStack.add(defaultRegistry);
	}

	@Override
	public T get(final String name) {
		for (final R registry : this.registryStack) {
			final T element = registry.get(name);
			if (element != null)
				return element;
		}
		return null;
	}

	public void push(final R e) {
		this.registryStack.push(e);
	}

	public void addLast(final R e) {
		this.registryStack.addLast(e);
	}

	public R pop() {
		return this.registryStack.pop();
	}

	/**
	 * Returns the registryStack.
	 * 
	 * @return the registryStack
	 */
	public R getRegistry(final int level) {
		return this.registryStack.get(level);
	}

	@Override
	public Set<String> keySet() {
		final HashSet<String> keys = new HashSet<String>();
		for (final R registry : this.registryStack)
			keys.addAll(registry.keySet());
		return keys;
	}

	@Override
	public void put(final String name, final T element) {
		this.getTopRegistry().put(name, element);
	}

	protected R getTopRegistry() {
		return this.registryStack.peek();
	}

	/**
	 * Returns the registryStack.
	 * 
	 * @return the registryStack
	 */
	protected Queue<R> getRegistryStack() {
		return this.registryStack;
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append("Registry with ");
		for (final R registry : this.registryStack) {
			appendable.append("\n ");
			registry.appendAsString(appendable);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.registryStack.hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final StackedRegistry<?, ?> other = (StackedRegistry<?, ?>) obj;
		return this.registryStack.equals(other.registryStack);
	}
}
