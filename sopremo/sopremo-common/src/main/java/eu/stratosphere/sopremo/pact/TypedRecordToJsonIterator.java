package eu.stratosphere.sopremo.pact;

import java.util.Iterator;

import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.typed.TypedObjectNode;

/**
 * Iterator that allows to iterate over {@link PactRecord}s. Each record is converted to a {@link IJsonNode} before
 * returning it.
 */
public final class TypedRecordToJsonIterator<Elem extends IJsonNode> implements RecordToJsonIterator<Elem> {

	private Iterator<SopremoRecord> iterator;

	private final TypedObjectNode typedNode;

	/**
	 * Initializes TypedRecordToJsonIterator.
	 * 
	 * @param typedInputNode
	 */
	public TypedRecordToJsonIterator(final TypedObjectNode typedNode) {
		this.typedNode = typedNode;
	}

	/**
	 * Sets the iterator to the specified value.
	 * 
	 * @param iterator
	 *        the iterator to set
	 */
	@Override
	public void setIterator(final Iterator<SopremoRecord> iterator) {
		this.iterator = iterator;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Elem next() {
		final IJsonNode node = this.iterator.next().getNode();
		this.typedNode.setBackingNode((IObjectNode) node);
		return (Elem) this.typedNode;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		this.iterator.remove();
	}

}
