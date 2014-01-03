package eu.stratosphere.pact.common.plan;

import java.util.List;

import eu.stratosphere.api.common.operators.Operator;
import eu.stratosphere.api.common.operators.util.ContractUtil;
import eu.stratosphere.util.dag.ConnectionNavigator;

/**
 * {@link Navigator} for traversing a graph of {@link Operator}s.
 * 
 * @author Arvid Heise
 * @see Navigator
 */
public class OperatorNavigator implements ConnectionNavigator<Operator> {
	/**
	 * The default stateless instance that should be used in most cases.
	 */
	public static final OperatorNavigator INSTANCE = new OperatorNavigator();

	@Override
	public List<Operator> getConnectedNodes(final Operator node) {
		return ContractUtil.getFlatInputs(node);
	}

}
