package eu.stratosphere.sopremo.serialization;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.type.AbstractArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.util.AbstractIterator;
import eu.stratosphere.util.ConcatenatingIterator;

/**
 * <p>
 * This implementation builds on the fixed-size PactRecord. It needs therefore a {@link TailArraySchema}.Because it is
 * fixed-size, it has leading field, called "others", which is also an implementation of the IArrayNode<IJsonNode>
 * interface. All fields behind that are either <code>null</code> (when the tail is not filled completely yet) or with
 * any JsonNode. If the tail gets fully filled, each upcoming JsonNode, which gets added, goes into the "others" field.<br/>
 * So this is an abstraction of an array due to the PactRecord serialization.
 * </p>
 * *
 * <p>
 * Visualization with a tailSize of 5:<br/>
 * <ul>
 * <li>intern representation: <code>[[], null, null, null, IJsonNode, IJsonNode]</code></li>
 * <li>extern representation: <code>[IJsonNode, IJsonNode]</code></li>
 * </ul>
 * Tail filled:<br/>
 * <ul>
 * <li>intern representation: <code>[[IJsonNode, ...], IJsonNode, IJsonNode, IJsonNode, IJsonNode, IJsonNode]</code></li>
 * <li>extern representation: <code>[IJsonNode, ..., IJsonNode, IJsonNode, IJsonNode, IJsonNode, IJsonNode]</code></li>
 * </ul>
 * </p>
 * 
 * @author Michael Hopstock
 * @author Arvid Heise
 */
final class LazyTailArrayNode extends AbstractArrayNode<IJsonNode> {

	private SopremoRecord record;

	private final int tailSize;

	public LazyTailArrayNode(int tailSize) {
		this.tailSize = tailSize;
	}

	/**
	 * Initializes LazyTailArrayNode.
	 */
	LazyTailArrayNode() {
		this.tailSize = 0;
	}

	/**
	 * Sets the record to the specified value.
	 * 
	 * @param record
	 *        the record to set
	 */
	public void setRecord(SopremoRecord record) {
		if (record == null)
			throw new NullPointerException("record must not be null");

		this.record = record;
	}

	/**
	 * Returns the record.
	 * 
	 * @return the record
	 */
	public SopremoRecord getRecord() {
		return this.record;
	}

	@Override
	public IArrayNode<IJsonNode> add(final IJsonNode node) {
		this.add(this.size(), node);
		return this;
	}

	@Override
	public IArrayNode<IJsonNode> add(final int index, final IJsonNode element) {
		if (element == null)
			throw new NullPointerException();

		final int size = this.size();
		if (index < 0 || index > size)
			throw new ArrayIndexOutOfBoundsException(index);

		final int recordPosition = this.getRecordPosition(index, size + 1);
		if (recordPosition > 0) {
			// shift existing elements
			if (!this.record.isEmpty(1))
				this.getOtherFieldForUpdate().add(this.record.getField(1));

			this.record.shiftLeft(1, recordPosition + 1, 1);

			this.record.setField(recordPosition, element);
		} else
			this.getOtherFieldForUpdate().add(index, element);

		return this;
	}

	@Override
	public void clear() {
		for (int i = 1; i <= this.tailSize; i++)
			this.record.setField(i, MissingNode.getInstance());

		final IArrayNode<?> otherField = this.getOtherField();
		if (!otherField.isEmpty()) {
			otherField.clear();
			this.record.setFieldChanged(0);
		}
	}

	@Override
	public IJsonNode get(final int index) {
		final int size = this.size();
		if (index < 0 || index >= size)
			return MissingNode.getInstance();
		final int recordPosition = this.getRecordPosition(index, size);
		if (recordPosition > 0)
			return this.record.getField(recordPosition);
		return this.getOtherField().get(index);
	}

	private int getRecordPosition(final int index, final int size) {
		final int recordPosition = this.tailSize - size + index + 1;
		return recordPosition;
	}
	
	/**
	 * Returns the arrayNode "others", which is the first in the PactRecord before the tail starts.
	 * 
	 * @return the field "others" of the PactRecord
	 */
	@SuppressWarnings("unchecked")
	IArrayNode<IJsonNode> getOtherField() {
		return this.record.getField(0, IArrayNode.class);
	}

	/**
	 * Returns the arrayNode "others", which is the first in the PactRecord before the tail starts.
	 * 
	 * @return the field "others" of the PactRecord
	 */
	@SuppressWarnings("unchecked")
	IArrayNode<IJsonNode> getOtherFieldForUpdate() {
		return this.record.getFieldForUpdate(0, IArrayNode.class);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IArrayNode#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.tailSize == 0 ? this.getOtherField().isEmpty() : this.record.isEmpty(this.tailSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<IJsonNode> iterator() {
		final Iterator<IJsonNode> othersIterator = this.getOtherField().iterator();
		final Iterator<IJsonNode> tailIterator = new AbstractIterator<IJsonNode>() {
			int index = 1;

			{
				// find first non-empty index
				for (; this.index <= LazyTailArrayNode.this.tailSize; this.index++)
					if (!LazyTailArrayNode.this.record.isEmpty(this.index))
						break;
			}

			/*
			 * (non-Javadoc)
			 * @see eu.stratosphere.util.AbstractIterator#loadNext()
			 */
			@Override
			protected IJsonNode loadNext() {
				if (this.index <= LazyTailArrayNode.this.tailSize)
					return LazyTailArrayNode.this.record.getField(this.index++);
				return this.noMoreElements();
			}
		};

		return new ConcatenatingIterator<IJsonNode>(othersIterator, tailIterator);
	}

	@Override
	public IJsonNode readResolve(final DataInput in) throws IOException {
		throw new UnsupportedOperationException("Use other ArrayNode<IJsonNode>  Implementation instead");
	}

	@Override
	public void remove(final int index) {
		final int size = this.size();
		if (index < 0 || index >= size)
			return;

		final int recordPosition = this.getRecordPosition(index, size);
		if (recordPosition > 0) {
			this.record.shiftRight(1, recordPosition, 1);
			final IArrayNode<IJsonNode> otherField = this.getOtherField();
			if (!otherField.isEmpty()) {
				final int lastIndex = otherField.size() - 1;
				IJsonNode oldNode = otherField.get(lastIndex);
				otherField.remove(lastIndex);
				this.record.setField(1, oldNode);
			} else
				this.record.setField(recordPosition, MissingNode.getInstance());
		} else
			this.getOtherFieldForUpdate().remove(index);
	}

	@Override
	public void set(final int index, final IJsonNode node) {
		if (node == null)
			throw new NullPointerException();

		final int size = this.size();
		final int recordPosition = this.getRecordPosition(index, size);

		if (recordPosition > 0)
			this.record.setField(recordPosition, node);
		else
			this.getOtherFieldForUpdate().set(index, node);
	}

	@Override
	public int size() {
		// we have to manually iterate over our record to get his size
		// because there is a difference between NullNode and MissingNode
		int count = 0;
		for (int i = this.tailSize; i > 0; i--)
			if (!this.record.isEmpty(i))
				count++;
			else
				return count;
		final IArrayNode<IJsonNode> others = this.getOtherField();
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

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#copyPropertiesFrom(eu.stratosphere.sopremo.ISopremoType)
	 */
	@Override
	public void copyPropertiesFrom(ISopremoType original) {
		this.record = (SopremoRecord) ((LazyTailArrayNode) original).record.clone();
	}

}
