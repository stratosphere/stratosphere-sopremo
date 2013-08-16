package eu.stratosphere.sopremo.pact;

import java.util.Iterator;

import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Iterator that allows to iterate over {@link PactRecord}s. Each record is converted to a {@link IJsonNode} before
 * returning it.
 */
public final class RecordToJsonIterator<Elem extends IJsonNode> implements Iterator<Elem> {

	private Iterator<SopremoRecord> iterator;

	/**
	 * Sets the iterator to the specified value.
	 * 
	 * @param iterator
	 *        the iterator to set
	 */
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

}
