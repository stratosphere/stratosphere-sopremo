package eu.stratosphere.sopremo.serialization;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

import com.google.common.collect.Iterators;

import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.type.AbstractArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.util.AbstractIterator;

/**
 * This {@link IArrayNode} supports {@link PactRecord}s more efficient by working directly with the record instead of
 * transforming it to a JsonNode. The record is handled by a {@link HeadArraySchema}.
 */
class LazyHeadArrayNode extends AbstractArrayNode<IJsonNode> {
	private SopremoRecord record;

	private final int headSize;

	/**
	 * Initializes a LazyHeadArrayNode<IJsonNode> with the given head size.
	 * 
	 * @param headSize
	 *        the headSize that should be used for transformations
	 */
	public LazyHeadArrayNode(final int headSize) {
		this.headSize = headSize;
	}

	/**
	 * Initializes LazyHeadArrayNode.
	 */
	LazyHeadArrayNode() {
		this.headSize = 0;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#copyPropertiesFrom(eu.stratosphere.sopremo.ISopremoType)
	 */
	@Override
	public void copyPropertiesFrom(ISopremoType original) {
		this.record = (SopremoRecord) ((LazyHeadArrayNode) original).record.clone();
	}

	/**
	 * Returns the record.
	 * 
	 * @return the record
	 */
	SopremoRecord getRecord() {
		return this.record;
	}

	/**
	 * Sets the record to the specified value.
	 * 
	 * @param record
	 *        the record to set
	 */
	void setRecord(SopremoRecord record) {
		if (record == null)
			throw new NullPointerException("record must not be null");

		this.record = record;
	}

	@Override
	public IArrayNode<IJsonNode> add(final IJsonNode node) {
		if (node == null)
			throw new NullPointerException();

		for (int i = 0; i < this.headSize; i++)
			if (this.record.isEmpty(i)) {
				this.record.setField(i, node);
				return this;
			}

		this.getOtherFieldForUpdate().add(node);
		return this;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private IArrayNode<IJsonNode> getOtherFieldForUpdate() {
		return this.record.getFieldForUpdate(this.headSize, IArrayNode.class);
	}

	@Override
	public IArrayNode<IJsonNode> add(final int index, final IJsonNode element) {
		if (element == null)
			throw new NullPointerException();

		if (index < 0 || index > this.size())
			throw new ArrayIndexOutOfBoundsException(index);

		if (index < this.headSize) {
			// shift existing elements
			final int lastIndex = this.headSize - 1;
			if (!this.record.isEmpty(lastIndex))
				this.getOtherFieldForUpdate().add(0, this.record.getField(lastIndex));

			this.record.shiftRight(index, this.headSize, 1);

			this.record.setField(index, element);
		} else
			this.getOtherFieldForUpdate().add(index - this.headSize, element);

		return this;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.headSize; i++)
			this.record.setField(i, MissingNode.getInstance());

		final IArrayNode<?> otherField = this.getOtherField();
		if (!otherField.isEmpty()) {
			otherField.clear();
			this.record.setFieldChanged(this.headSize);
		}
	}

	@Override
	public IJsonNode get(final int index) {
		if (index < 0 || index >= this.size())
			return MissingNode.getInstance();

		if (index < this.headSize)
			return this.record.getField(index);
		return this.getOtherField().get(index - this.headSize);
	}

	/**
	 * Returns the last field of the record. This 'other' field stores all nodes after reaching the defined head size of
	 * the schema.
	 * 
	 * @return the 'other' field of the record
	 */
	@SuppressWarnings("unchecked")
	IArrayNode<IJsonNode> getOtherField() {
		return this.record.getField(this.headSize, IArrayNode.class);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IArrayNode#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.headSize == 0 ? this.getOtherField().isEmpty() : this.record.isEmpty(0);
	}

	@Override
	public Iterator<IJsonNode> iterator() {
		final Iterator<IJsonNode> iterator2 = this.getOtherField().iterator();
		final Iterator<IJsonNode> iterator1 = new AbstractIterator<IJsonNode>() {

			int lastIndex = 0;

			@Override
			protected IJsonNode loadNext() {
				while (this.lastIndex < LazyHeadArrayNode.this.headSize) {
					if (!LazyHeadArrayNode.this.record.isEmpty(this.lastIndex)) {
						final IJsonNode value = LazyHeadArrayNode.this.record.getField(this.lastIndex);
						this.lastIndex++;
						return value;
					}

					return this.noMoreElements();
				}
				return this.noMoreElements();
			}

		};

		return Iterators.concat(iterator1, iterator2);
	}

	@Override
	public IJsonNode readResolve(final DataInput in) throws IOException {
		throw new UnsupportedOperationException("Use other ArrayNode<IJsonNode>  Implementation instead");
	}

	@Override
	public void remove(final int index) {
		if (index < 0 || index >= this.size())
			return;

		if (index < this.headSize) {
			this.record.shiftLeft(index, this.headSize, 1);
			final IArrayNode<IJsonNode> otherField = this.getOtherField();
			if (!otherField.isEmpty()) {
				IJsonNode oldNode = otherField.get(0);
				otherField.remove(0);
				this.record.setField(this.headSize - 1, oldNode);
			} else
				this.record.setField(this.headSize - 1, MissingNode.getInstance());
		} else
			this.getOtherFieldForUpdate().remove(index - this.headSize);
	}

	@Override
	public void set(final int index, final IJsonNode node) {
		if (node == null)
			throw new NullPointerException();

		if (index < this.headSize)
			this.record.setField(index, node);
		else
			this.getOtherFieldForUpdate().set(index - this.headSize, node);
	}

	@Override
	public int size() {
		final IArrayNode<IJsonNode> others = this.getOtherField();
		// we have to manually iterate over our record to get his size
		// because there is a difference between NullNode and MissingNode
		int count = 0;
		for (int i = 0; i < this.headSize; i++)
			if (!this.record.isEmpty(i))
				count++;
			else
				return count;
		return count + others.size();
	}

	@Override
	public void appendAsString(final Appendable sb) throws IOException {
		sb.append('[');

		int count = 0;
		for (final IJsonNode node : this) {
			if (count > 0)
				sb.append(',');
			++count;

			node.appendAsString(sb);
		}

		sb.append(']');
	}

	@Override
	public void write(final DataOutput out) throws IOException {
		throw new UnsupportedOperationException("Use other ArrayNode<IJsonNode>  Implementation instead");
	}

	@Override
	public int getMaxNormalizedKeyLen() {
		return 0;
	}

	@Override
	public void copyNormalizedKey(final byte[] target, final int offset, final int len) {
		throw new UnsupportedOperationException("Use other ArrayNode<IJsonNode>  Implementation instead");
	}
}
