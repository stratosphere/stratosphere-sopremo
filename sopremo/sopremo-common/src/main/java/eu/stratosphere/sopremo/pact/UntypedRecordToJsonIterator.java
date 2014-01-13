package eu.stratosphere.sopremo.pact;

import java.util.Iterator;

import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Iterator that allows to iterate over {@link SopremoRecord}s. Each record is converted to a {@link IJsonNode} before
 * returning it.
 */
public final class UntypedRecordToJsonIterator<Elem extends IJsonNode> implements RecordToJsonIterator<Elem> {

	private Iterator<SopremoRecord> iterator;

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
		return (Elem) this.iterator.next().getNode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		this.iterator.remove();
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

}
