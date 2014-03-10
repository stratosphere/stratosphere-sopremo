package eu.stratosphere.pact.common.plan;

import java.util.ArrayList;
import java.util.List;

import eu.stratosphere.api.common.operators.BulkIteration;
import eu.stratosphere.api.common.operators.DeltaIteration;
import eu.stratosphere.api.common.operators.IterationOperator;
import eu.stratosphere.api.common.operators.Operator;
import eu.stratosphere.api.common.operators.util.OperatorUtil;
import eu.stratosphere.util.dag.ConnectionNavigator;

/**
 * {@link ConnectionNavigator} for traversing a graph of {@link Operator}s.
 * 
 * @see ConnectionNavigator
 */
public class OperatorNavigator implements ConnectionNavigator<Operator> {
	/**
	 * The default stateless instance that should be used in most cases.
	 */
	public static final OperatorNavigator INSTANCE = new OperatorNavigator();

	@Override
	public List<Operator> getConnectedNodes(final Operator node) {
		if (node instanceof IterationOperator) {
			final List<Operator> inputs = new ArrayList<Operator>(OperatorUtil.getFlatInputs(node));

			if (node instanceof BulkIteration) {
				inputs.add(((BulkIteration) node).getPartialSolution());
				inputs.add(((BulkIteration) node).getTerminationCriterion());
			}
			else if (node instanceof DeltaIteration) {
				inputs.add(((DeltaIteration) node).getNextWorkset());
				inputs.add(((DeltaIteration) node).getSolutionSetDelta());
			}
			else
				throw new IllegalArgumentException("Unknown node");

			return inputs;
		}
		return OperatorUtil.getFlatInputs(node);
	}
}
