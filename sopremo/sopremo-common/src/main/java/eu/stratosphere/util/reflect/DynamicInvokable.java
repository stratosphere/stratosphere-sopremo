package eu.stratosphere.util.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoCopyable;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import eu.stratosphere.sopremo.EvaluationException;

public abstract class DynamicInvokable<MemberType extends Member, DeclaringType, ReturnType> implements
		KryoSerializable, KryoCopyable<DynamicInvokable<MemberType, DeclaringType, ReturnType>> {

	public static final Log LOG = LogFactory.getLog(DynamicInvokable.class);

	private transient Map<Signature, MemberType> cachedSignatures = new HashMap<Signature, MemberType>();

	private transient Map<Signature, MemberType> originalSignatures = new HashMap<Signature, MemberType>();

	private final String name;

	/**
	 * Initializes DynamicInvokable.
	 */
	DynamicInvokable() {
		this.name = null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoSerializable#read(com.esotericsoftware.kryo.Kryo,
	 * com.esotericsoftware.kryo.io.Input)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void read(Kryo kryo, Input input) {
		ReflectUtil.setField(this, DynamicInvokable.class, "name", kryo.readObject(input, String.class));
		final int size = input.readInt();
		this.cachedSignatures = new HashMap<Signature, MemberType>();
		this.originalSignatures = new HashMap<Signature, MemberType>();
		for (int index = 0; index < size; index++)
			try {
				Signature signature = kryo.readObject(input, Signature.class);
				String name = kryo.readObject(input, String.class);
				Class<DeclaringType> clazz = kryo.readObject(input, Class.class);
				Class<?>[] params = kryo.readObject(input, Class[].class);
				this.originalSignatures.put(signature, this.findMember(name, clazz, params));
			} catch (final NoSuchMethodException e) {
				throw new EvaluationException("Cannot find registered java function " + this.getName(), e);
			}
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoSerializable#write(com.esotericsoftware.kryo.Kryo,
	 * com.esotericsoftware.kryo.io.Output)
	 */
	@Override
	public void write(Kryo kryo, Output output) {
		kryo.writeObject(output, this.name);
		output.writeInt(this.originalSignatures.size());
		for (final Entry<Signature, MemberType> entry : this.originalSignatures.entrySet()) {
			kryo.writeObject(output, entry.getKey());
			kryo.writeObject(output, entry.getValue().getName());
			kryo.writeObject(output, entry.getValue().getDeclaringClass());
			kryo.writeObject(output, this.getParameterTypes(entry.getValue()));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryo.KryoCopyable#copy(com.esotericsoftware.kryo.Kryo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DynamicInvokable<MemberType, DeclaringType, ReturnType> copy(Kryo kryo) {
		final DynamicInvokable<MemberType, DeclaringType, ReturnType> copy = kryo.newInstance(this.getClass());
		ReflectUtil.setField(copy, DynamicInvokable.class, "name", this.name);
		copy.originalSignatures.putAll(this.originalSignatures);
		return copy;
	}

	protected abstract MemberType findMember(String name, Class<DeclaringType> clazz, Class<?>[] parameterTypes)
			throws NoSuchMethodException;

	public String getName() {
		return this.name;
	}

	public DynamicInvokable(final String name) {
		this.name = name;
	}

	public void addSignature(final MemberType member) {
		final Class<?>[] parameterTypes = this.getSignatureTypes(member);
		Signature signature;

		if (member instanceof AccessibleObject)
			((AccessibleObject) member).setAccessible(true);
		if (this.isVarargs(member))
			signature = new VarArgSignature(parameterTypes);
		else
			signature = new Signature(parameterTypes);
		this.originalSignatures.put(signature, member);
		// Cache flushing might be more intelligent in the future.
		// However, how often are method signatures actually added after first
		// invocation?
		this.cachedSignatures.clear();
		this.cachedSignatures.putAll(this.originalSignatures);
	}

	protected abstract boolean needsInstance(MemberType member);

	protected Class<?>[] getSignatureTypes(final MemberType member) {
		return this.getParameterTypes(member);
	}

	protected abstract boolean isVarargs(final MemberType member);

	protected abstract Class<?>[] getParameterTypes(final MemberType member);

	private Signature findBestSignature(final Signature signature) {
		MemberType member = this.getMember(signature);
		if (member != null)
			return signature;

		int minDistance = Integer.MAX_VALUE;
		boolean ambiguous = false;
		Signature bestSignatureSoFar = null;
		for (final Entry<Signature, MemberType> originalSignature : this.originalSignatures.entrySet()) {
			final int distance = originalSignature.getKey().getDistance(signature);
			if (distance < 0)
				continue;

			if (distance < minDistance) {
				minDistance = distance;
				bestSignatureSoFar = originalSignature.getKey();
				ambiguous = false;
			} else if (distance == minDistance)
				ambiguous = true;
		}

		if (minDistance == Integer.MAX_VALUE)
			return null;

		if (ambiguous && LOG.isWarnEnabled())
			this.warnForAmbiguity(signature, minDistance);

		member = minDistance == Signature.INCOMPATIBLE ? null : this.originalSignatures.get(bestSignatureSoFar);
		this.cachedSignatures.put(bestSignatureSoFar, member);
		return bestSignatureSoFar;
	}

	private void warnForAmbiguity(final Signature signature, final int minDistance) {
		final List<Signature> ambigiousSignatures = new ArrayList<Signature>();

		for (final Entry<Signature, MemberType> originalSignature : this.originalSignatures.entrySet()) {
			final int distance = originalSignature.getKey().getDistance(signature);
			if (distance == minDistance)
				ambigiousSignatures.add(originalSignature.getKey());
		}

		LOG.warn(String.format("multiple matching signatures found for the member %s and parameters types %s: %s",
			this.getName(),
			Arrays.toString(signature.getParameterTypes()), ambigiousSignatures));
	}

	public ReturnType invoke(final Object context, final Object... params) throws Throwable {
		final Class<?>[] paramTypes = this.getActualParameterTypes(params);
		final Signature signature = this.findBestSignature(new Signature(paramTypes));
		if (signature == null)
			throw new EvaluationException(String.format("No method %s found for parameter types %s", this.getName(),
				Arrays.toString(paramTypes)));
		return this.invokeSignature(signature, context, params);
	}

	public ReturnType invokeStatic(final Object... params) throws Throwable {
		return this.invoke(null, params);
	}

	public abstract Class<ReturnType> getReturnType();

	public boolean isInvokableFor(final Object... params) {
		final Class<?>[] paramTypes = this.getActualParameterTypes(params);
		return this.findBestSignature(new Signature(paramTypes)) != null;
	}

	public ReturnType invokeSignature(final Signature signature, final Object context, final Object... params)
			throws Throwable {
		try {
			return this.invokeDirectly(this.getMember(signature), context, signature.adjustParameters(params));
		} catch (final Error e) {
			throw e;
		} catch (final InvocationTargetException e) {
			throw e.getCause();
		}
	}

	protected MemberType getMember(final Signature signature) {
		return this.originalSignatures.get(signature);
	}

	protected abstract ReturnType invokeDirectly(final MemberType member, final Object context, Object[] params)
			throws IllegalAccessException,
			InvocationTargetException, IllegalArgumentException, InstantiationException;

	protected Class<?>[] getActualParameterTypes(final Object[] params) {
		final Class<?>[] paramTypes = new Class<?>[params.length];
		for (int index = 0; index < paramTypes.length; index++)
			paramTypes[index] = params[index] == null ? null : params[index].getClass();
		return paramTypes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.name.hashCode();
		result = prime * result + this.originalSignatures.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		DynamicInvokable<?, ?, ?> other = (DynamicInvokable<?, ?, ?>) obj;
		return this.name.equals(other.name) && this.originalSignatures.equals(other.originalSignatures);
	}

	public Collection<Signature> getSignatures() {
		return this.originalSignatures.keySet();
	}

}