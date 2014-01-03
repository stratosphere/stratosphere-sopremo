package eu.stratosphere.sopremo.base.join;

import eu.stratosphere.sopremo.operator.Internal;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoJoin;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

@Internal
public class InnerJoin extends TwoSourceJoinBase<InnerJoin> {
	public static class Implementation extends SopremoJoin {
		private final IArrayNode<IJsonNode> result = new ArrayNode<IJsonNode>();

		@Override
		protected void match(final IJsonNode value1, final IJsonNode value2, final JsonCollector<IJsonNode> out) {
			this.result.set(0, value1);
			this.result.set(1, value2);
			out.collect(this.result);
		}
	}
}