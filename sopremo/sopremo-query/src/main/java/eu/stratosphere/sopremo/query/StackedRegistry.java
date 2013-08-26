package eu.stratosphere.sopremo.query;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.packages.IRegistry;

public class StackedRegistry<T, R extends IRegistry<T>> extends AbstractSopremoType implements IRegistry<T> {
	private LinkedList<R> registryStack = new LinkedList<R>();

	public StackedRegistry(R defaultRegistry) {
		this.registryStack.add(defaultRegistry);
	}

	@Override
	public T get(String name) {
		for (R registry : this.registryStack) {
			T element = registry.get(name);
			if (element != null)
				return element;
		}
		return null;
	}

	public void push(R e) {
		this.registryStack.push(e);
	}

	public void addLast(R e) {
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
	public R getRegistry(int level) {
		return this.registryStack.get(level);
	}

	@Override
	public Set<String> keySet() {
		final HashSet<String> keys = new HashSet<String>();
		for (R registry : this.registryStack)
			keys.addAll(registry.keySet());
		return keys;
	}

	@Override
	public void put(String name, T element) {
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
	public void appendAsString(Appendable appendable) throws IOException {
		appendable.append("Registry with ");
		for (R registry : this.registryStack) {
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StackedRegistry<?, ?> other = (StackedRegistry<?, ?>) obj;
		return this.registryStack.equals(other.registryStack);
	}
}
