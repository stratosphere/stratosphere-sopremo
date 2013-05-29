package eu.stratosphere.sopremo.base.join;

import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoCoGroup;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;

public class LeftOuterJoin extends TwoSourceJoinBase<LeftOuterJoin> {
	public static class Implementation extends FullOuterJoin.Implementation {

		@Override
		protected void coGroup(IStreamNode<IJsonNode> values1, IStreamNode<IJsonNode> values2, JsonCollector out) {
			if (values1.isEmpty())
				// special case: no items from first source
				return;

			if (values2.isEmpty()) {
				// special case: no items from second source
				// emit all values of the first source
				leftOuterJoin(this.result, values1, out);
				return;
			}

			cogroupJoin(this.result, values1, values2, out);
		}
	}
}