package eu.stratosphere.util;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Provides a {@link Set} that uses reference-equality and thus intentionally violates the general Set contract.
 * 
 * @param <E>
 *        the type of the elements
 * @see Set
 * @see IdentityHashMap
 */
public class IdentitySet<E> extends AbstractSet<E> {
	private final Map<E, Object> backing = new IdentityHashMap<E, Object>();

	public IdentitySet() {
		super();
	}

	public IdentitySet(final Collection<? extends E> set) {
		for (final E e : set)
			this.backing.put(e, null);
	}

	@Override
	public boolean add(final E e) {
		return this.backing.put(e, null) == null;
	}

	@Override
	public void clear() {
		this.backing.clear();
	}

	@Override
	public boolean contains(final Object o) {
		return this.backing.containsKey(o);
	};

	@Override
	public Iterator<E> iterator() {
		return this.backing.keySet().iterator();
	}

	@Override
	public boolean remove(final Object o) {
		return this.backing.keySet().remove(o);
	}

	@Override
	public int size() {
		return this.backing.size();
	}
}
