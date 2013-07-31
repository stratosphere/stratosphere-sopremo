package eu.stratosphere.util;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

import java.util.Collection;

public class CollectionUtil {
	/**
	 * Pads the given collection with <code>null</code>s until the collection has at least the given size.
	 * 
	 * @param collection
	 *        the collection to pad
	 * @param size
	 *        the desired minimum size
	 */
	public static void ensureSize(final Collection<?> collection, final int size) {
		ensureSize(collection, size, null);
	}

	/**
	 * Pads the given collection with the given default value until the collection has at least the given size.
	 * 
	 * @param collection
	 *        the collection to pad
	 * @param size
	 *        the desired minimum size
	 * @param defaultValue
	 *        the default value
	 */
	public static <T> void ensureSize(final Collection<T> collection, final int size, final T defaultValue) {
		while (collection.size() < size)
			collection.add(defaultValue);
	}

	/**
	 * Creates a set containing all ints in the given range.
	 * 
	 * @param start
	 *        the start of the range
	 * @param exclusiveEnd
	 *        the end of the range (exclusive)
	 * @return a set containing all ints in the given range.
	 */
	public static IntSet setRangeFrom(final int start, final int exclusiveEnd) {
		final IntOpenHashSet range = new IntOpenHashSet(exclusiveEnd - start);
		for (int index = start; index < exclusiveEnd; index++)
			range.add(index);
		return range;
	}

}
