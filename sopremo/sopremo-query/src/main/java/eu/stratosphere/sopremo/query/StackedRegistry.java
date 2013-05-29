package eu.stratosphere.sopremo.query;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.packages.IRegistry;
import eu.stratosphere.sopremo.pact.SopremoUtil;

public class StackedRegistry<T extends ISopremoType, R extends IRegistry<T>> extends AbstractSopremoType
		implements IRegistry<T> {
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

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#copyPropertiesFrom(eu.stratosphere.sopremo.AbstractSopremoType)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void copyPropertiesFrom(ISopremoType original) {
		super.copyPropertiesFrom(original);
		final LinkedList<R> stack = ((StackedRegistry<T, R>) original).registryStack;
		this.registryStack.addAll(SopremoUtil.deepClone(stack.subList(1, stack.size())));
	}

	@Override
	public void put(String name, T element) {
		this.getTopRegistry().put(name, element);
	}

	protected R getTopRegistry() {
		return this.registryStack.peek();
	}

	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		appendable.append("Registry with ");
		for (R registry : this.registryStack) {
			appendable.append("\n ");
			registry.appendAsString(appendable);
		}
	}
}
