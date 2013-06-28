package eu.stratosphere.sopremo.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import eu.stratosphere.sopremo.packages.DefaultTypeRegistry;
import eu.stratosphere.sopremo.packages.ITypeRegistry;
import eu.stratosphere.sopremo.type.IJsonNode;

public class StackedTypeRegistry extends StackedRegistry<Class<? extends IJsonNode>, ITypeRegistry> implements
		ITypeRegistry {

	public StackedTypeRegistry() {
		super(new DefaultTypeRegistry());
	}

	/* (non-Javadoc)
	 * @see eu.stratosphere.sopremo.packages.ITypeRegistry#getTypes()
	 */
	@Override
	public List<Class<? extends IJsonNode>> getTypes() {
		final ArrayList<Class<? extends IJsonNode>> typeList = new ArrayList<Class<? extends IJsonNode>>();
		final Queue<ITypeRegistry> registryStack = getRegistryStack();
		for (ITypeRegistry registry : registryStack) 
			typeList.addAll(registry.getTypes());
		return typeList;
	}

	/**
	 * @param type
	 */
	@Override
	public void put(Class<? extends IJsonNode> type) {
	}
}