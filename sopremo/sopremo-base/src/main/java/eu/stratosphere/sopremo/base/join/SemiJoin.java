package eu.stratosphere.sopremo.base.join;

import eu.stratosphere.sopremo.operator.Internal;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoCoGroup;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;
import eu.stratosphere.sopremo.type.NullNode;

@Internal
public class SemiJoin extends TwoSourceJoinBase<SemiJoin> {
	/**
	 * Initializes SemiJoin.
	 */
	public SemiJoin() {
		this.setKeyExpressions(0, ALL_KEYS);
		this.setKeyExpressions(1, ALL_KEYS);
	}

	public static class Implementation extends SopremoCoGroup {
		private final IArrayNode<IJsonNode> result = new ArrayNode<IJsonNode>(NullNode.getInstance());

		@Override
		protected void coGroup(final IStreamNode<IJsonNode> values1, final IStreamNode<IJsonNode> values2,
				final JsonCollector<IJsonNode> out) {
			if (!values2.isEmpty())
				for (final IJsonNode value : values1) {
					this.result.set(0, value);
					out.collect(this.result);
				}
		}
	}
}