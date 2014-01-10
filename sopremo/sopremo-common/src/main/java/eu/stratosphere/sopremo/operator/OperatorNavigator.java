package eu.stratosphere.sopremo.operator;

import java.util.List;

import eu.stratosphere.util.dag.ConnectionNavigator;

/**
 * Provides a mean to traverse the directed acyclic graph of interconnected {@link Operator<?>}s.
 */
public final class OperatorNavigator<Op extends Operator<?>> implements ConnectionNavigator<Op> {
	/**
	 * The default, stateless instance.
	 */
	public final static OperatorNavigator<Operator<?>> INSTANCE = new OperatorNavigator<Operator<?>>();

	public final static OperatorNavigator<ElementaryOperator<?>> ELEMENTARY =
		new OperatorNavigator<ElementaryOperator<?>>();

	@SuppressWarnings("unchecked")
	@Override
	public List<Op> getConnectedNodes(final Op node) {
		return (List<Op>) node.getInputOperators();
	}
}