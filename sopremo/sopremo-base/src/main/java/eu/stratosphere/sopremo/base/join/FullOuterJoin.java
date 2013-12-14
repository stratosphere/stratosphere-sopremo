package eu.stratosphere.sopremo.base.join;

import eu.stratosphere.sopremo.operator.Internal;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoCoGroup;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.CachingArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;
import eu.stratosphere.sopremo.type.MissingNode;

@Internal
public class FullOuterJoin extends TwoSourceJoinBase<FullOuterJoin> {
	public static class Implementation extends SopremoCoGroup {
		protected final IArrayNode<IJsonNode> result = new ArrayNode<IJsonNode>();

		protected void leftOuterJoin(final IArrayNode<IJsonNode> result, final IStreamNode<IJsonNode> values2,
				final JsonCollector<IJsonNode> out) {
			result.set(1, MissingNode.getInstance());
			for (final IJsonNode value : values2) {
				result.set(0, value);
				out.collect(result);
			}
		}

		protected void rightOuterJoin(final IArrayNode<IJsonNode> result, final IStreamNode<IJsonNode> values2,
				final JsonCollector<IJsonNode> out) {
			result.set(0, MissingNode.getInstance());
			for (final IJsonNode value : values2) {
				result.set(1, value);
				out.collect(result);
			}
		}

		private transient CachingArrayNode<IJsonNode> firstSourceNodes = new CachingArrayNode<IJsonNode>();

		protected void cogroupJoin(final IArrayNode<IJsonNode> result, final IStreamNode<IJsonNode> values1,
				final IStreamNode<IJsonNode> values2, final JsonCollector<IJsonNode> out) {
			this.firstSourceNodes.setSize(0);
			// TODO: use resettable iterator to avoid OOME
			// TODO: can we estimate if first or second source is smaller?
			for (final IJsonNode value : values1)
				this.firstSourceNodes.addClone(value);

			for (final IJsonNode secondSourceNode : values2) {
				result.set(1, secondSourceNode);
				for (final IJsonNode firstSourceNode : this.firstSourceNodes) {
					result.set(0, firstSourceNode);
					out.collect(result);
				}
			}
		}

		@Override
		protected void coGroup(final IStreamNode<IJsonNode> values1, final IStreamNode<IJsonNode> values2,
				final JsonCollector<IJsonNode> out) {
			if (values1.isEmpty()) {
				// special case: no items from first source
				// emit all values of the second source
				this.rightOuterJoin(this.result, values2, out);
				return;
			}

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