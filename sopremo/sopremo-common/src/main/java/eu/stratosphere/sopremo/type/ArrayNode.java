package eu.stratosphere.sopremo.type;

import it.unimi.dsi.fastutil.objects.AbstractObjectList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.google.common.collect.Iterators;

import eu.stratosphere.util.CollectionUtil;

/**
 * This node represents an array and can store all types of {@link IJsonNode}s.
 * In addition, the size of the array increases when needed.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
@DefaultSerializer(AbstractArrayNode.ArraySerializer.class)
public class ArrayNode<T extends IJsonNode> extends AbstractArrayNode<T> {
	private final ObjectArrayList<T> children;

	@SuppressWarnings("unchecked")
	public final static IArrayNode<?> EMPTY = new ArrayNode<IJsonNode>(
		Collections.EMPTY_LIST);

	/**
	 * Initializes an empty ArrayNode.
	 */
	@SuppressWarnings("unchecked")
	public ArrayNode() {
		this((Class<T>) IJsonNode.class);
	}

	/**
	 * Initializes an empty ArrayNode<T> with {@link MissingNode}s.<br>
	 * This method is only applicable if ArrayNode<T> is untyped.
	 */
	@SuppressWarnings("unchecked")
	public ArrayNode(int size) {
		this(new ArrayList<T>(size));
		for (int index = 0; index < size; index++)
			this.add((T) MissingNode.getInstance());
	}

	/**
	 * Initializes an empty ArrayNode<T> directly with the given list.
	 */
	protected ArrayNode(ObjectArrayList<T> children) {
		this.children = children;
	}

	/**
	 * Initializes an empty ArrayNode<T> directly with the given list.
	 */
	@SuppressWarnings("unchecked")
	protected ArrayNode(Class<T> elemType) {
		this(ObjectArrayList.wrap((T[]) Array.newInstance(elemType, 0)));
	}

	/**
	 * Initializes an ArrayNode<T> which contains the given {@link IJsonNode}s
	 * in proper sequence.
	 * 
	 * @param nodes
	 *        the nodes that should be added to this ArrayNode
	 */
	@SuppressWarnings("unchecked")
	public ArrayNode(final T... nodes) {
		this((Class<T>) nodes.getClass().getComponentType());
		for (final T node : nodes) {
			if (node == null)
				throw new NullPointerException();
			this.children.add(node);
		}
	}

	/**
	 * Initializes an ArrayNode<T> which cointains all {@link IJsonNode}s from
	 * the given Iterable in proper sequence.
	 * 
	 * @param nodes
	 *        a Collection of nodes that should be added to this ArrayNode
	 */
	public ArrayNode(final Iterable<? extends T> nodes) {
		this();
		for (final T node : nodes) {
			if (node == null)
				throw new NullPointerException();
			this.children.add(node);
		}
	}

	/**
	 * Creates an ArrayNode<T> which contains clones of all {@link IJsonNode}s from
	 * the given stream array node in proper sequence.
	 * 
	 * @param nodes
	 *        a Collection of nodes that should be added to this ArrayNode
	 */
	@SuppressWarnings("unchecked")
	public static <T extends IJsonNode> ArrayNode<T> deepClone(final IStreamNode<? extends T> nodes) {
		ArrayNode<T> clone = new ArrayNode<T>();
		for (final T node : nodes)
			clone.children.add((T) node.clone());
		return clone;
	}
	/**
	 * Creates an ArrayNode<T> which contains clones of all {@link IJsonNode}s from
	 * the given stream array node in proper sequence.
	 * 
	 * @param nodes
	 *        a Collection of nodes that should be added to this ArrayNode
	 */
	@SuppressWarnings("unchecked")
	public static <T extends IJsonNode> ArrayNode<T> deepClone(final Class<T> elemType, final IStreamNode<? extends T> nodes) {
		ArrayNode<T> clone = new ArrayNode<T>(elemType);
		for (final T node : nodes)
			clone.children.add((T) node.clone());
		return clone;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#clone()
	 */
	@Override
	public ArrayNode<T> clone() {
		return (ArrayNode<T>) super.clone();
	}

	/**
	 * Returns the backing array for access intensive-operations.<br>
	 * Note that the array is usually large than the size of this array. All values after {@link #size()} elements are
	 * undefined.
	 */
	public T[] getBackingArray() {
		return this.children.elements();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IArrayNode#asCollection()
	 */
	@Override
	public List<T> asList() {
		return this.children;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonArray#size()
	 */
	@Override
	public int size() {
		int size = this.children.size();
		while (size > 0
			&& this.children.get(size - 1) == MissingNode.getInstance())
			size--;
		return size;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.type.JsonArray#add(eu.stratosphere.sopremo.type
	 * .IJsonNode)
	 */
	@Override
	public ArrayNode<T> add(final T node) {
		if (node == null)
			throw new NullPointerException();

		this.children.add(node);

		return this;
	}

	/**
	 * Returns the children.
	 * 
	 * @return the children
	 */
	protected AbstractObjectList<T> getChildren() {
		return this.children;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonArray#add(int,
	 * eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public ArrayNode<T> add(final int index, final T element) {
		if (element == null)
			throw new NullPointerException();

		this.children.add(index, element);

		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.type.AbstractArrayNode#contains(eu.stratosphere
	 * .sopremo.type.IJsonNode)
	 */
	@Override
	public boolean contains(IJsonNode node) {
		return this.children.contains(node);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonArray#get(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(final int index) {
		if (0 <= index && index < this.children.size())
			return this.children.get(index);
		return (T) MissingNode.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonArray#set(int,
	 * eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void set(final int index, final T node) {
		if (node == null)
			throw new NullPointerException();
		CollectionUtil.ensureSize(this.children, index + 1,
			(T) MissingNode.getInstance());
		this.children.set(index, node);
	}

	@Override
	public void remove(final int index) {
		if (0 <= index && index < this.children.size())
			this.children.remove(index);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.JsonArray#clear()
	 */
	@Override
	public void clear() {
		this.children.clear();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
			+ (this.children == null ? 0 : this.children.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (this.getClass() != obj.getClass())
			return super.equals(obj);
		ArrayNode<?> other = (ArrayNode<?>) obj;
		int size = this.size();
		if (other.size() != size)
			return false;
		for (int index = 0; index < size; index++)
			if (!this.children.get(index).equals(other.children.get(index)))
				return false;
		return true;
	}

	@Override
	public Iterator<T> iterator() {
		return Iterators.limit(this.children.iterator(), size());
	}

	/**
	 * Checks if this node is currently empty.
	 */
	@Override
	public boolean isEmpty() {
		return this.children.isEmpty();
	}

	/**
	 * Initializes a new ArrayNode<T> which contains all {@link IJsonNode}s from
	 * the provided Iterator.
	 * 
	 * @param iterator
	 *        an Iterator over IJsonNodes that should be added to the new
	 *        ArrayNode
	 * @return the created ArrayNode
	 */
	public static <T extends IJsonNode> ArrayNode<T> valueOf(
			final Iterator<T> iterator) {
		final ArrayNode<T> array = new ArrayNode<T>();
		while (iterator.hasNext())
			array.add(iterator.next());
		return array;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.type.AbstractArrayNode#fillArray(eu.stratosphere
	 * .sopremo.type.IJsonNode[])
	 */
	@Override
	protected void fillArray(IJsonNode[] result) {
		IJsonNode[] array = this.children.toArray(new IJsonNode[this.children
			.size()]);
		for (int i = 0; i < this.children.size(); i++)
			result[i] = array[i];
	}

	@Override
	public int compareToSameType(final IJsonNode other) {
		// if(!(other instanceof ArrayNode)){
		// return -1;
		// }
		final IArrayNode<?> node = (IArrayNode<?>) other;
		if (node.size() != this.size())
			return this.size() - node.size();
		for (int i = 0; i < this.size(); i++) {
			final int comp = this.get(i).compareTo(node.get(i));
			if (comp != 0)
				return comp;
		}
		return 0;
	}

	/**
	 * Returns a view of the portion of this ArrayNode<T> between the specified
	 * fromIndex, inclusive, and toIndex, exclusive. (If fromIndex and toIndex
	 * are equal, the returned ArrayNode<T> is empty.)
	 * 
	 * @param fromIndex
	 *        the index where the new ArrayNode<T> should start (inclusive)
	 * @param toIndex
	 *        the index where the new ArrayNode<T> should stop (exclusive)
	 * @return the new ArrayNode<T> (subarray)
	 */
	public IJsonNode subArray(final int fromIndex, final int toIndex) {
		return new ArrayNode<T>(this.children.subList(fromIndex, toIndex));
	}
}
