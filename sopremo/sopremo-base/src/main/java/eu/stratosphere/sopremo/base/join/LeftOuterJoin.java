package eu.stratosphere.sopremo.base.join;

import eu.stratosphere.sopremo.operator.Internal;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;

@Internal
public class LeftOuterJoin extends TwoSourceJoinBase<LeftOuterJoin> {
	public static class Implementation extends FullOuterJoin.Implementation {

		@Override
		protected void coGroup(final IStreamNode<IJsonNode> values1, final IStreamNode<IJsonNode> values2,
				final JsonCollector<IJsonNode> out) {
			if (values1.isEmpty())
				// special case: no items from first source
				return;

			if (values2.isEmpty()) {
				// special case: no items from second source
				// emit all values of the first source
				this.leftOuterJoin(this.result, values1, out);
				return;
			}

			this.cogroupJoin(this.result, values1, values2, out);
		}
	}
}