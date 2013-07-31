package eu.stratosphere.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class DynamicMethod<ReturnType> extends DynamicInvokable<Method, Object, ReturnType> {

	private Class<?> returnType;

	public DynamicMethod(final String name) {
		super(name);
	}

	/**
	 * Initializes DynamicMethod.
	 */
	DynamicMethod() {
	}

	@Override
	public void addSignature(final Method member) {
		super.addSignature(member);
		if (this.returnType == null)
			this.returnType = member.getReturnType();
		else if (member.getReturnType() != this.returnType)
			this.returnType = member.getReturnType().isAssignableFrom(this.returnType) ? this.returnType
				: member.getReturnType();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<ReturnType> getReturnType() {
		return (Class<ReturnType>) this.returnType;
	}

	@Override
	protected Class<?>[] getParameterTypes(final Method method) {
		return method.getParameterTypes();
	}

	@Override
	protected boolean isVarargs(final Method method) {
		return method.isVarArgs();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ReturnType invokeDirectly(final Method method, final Object context, final Object[] params)
			throws IllegalAccessException,
			InvocationTargetException {
		return (ReturnType) method.invoke(context, params);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.util.reflect.DynamicInvokable#read(com.esotericsoftware.kryo.Kryo,
	 * com.esotericsoftware.kryo.io.Input)
	 */
	@Override
	public void read(Kryo kryo, Input input) {
		super.read(kryo, input);
		this.returnType = kryo.readObject(input, Class.class);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.util.reflect.DynamicInvokable#write(com.esotericsoftware.kryo.Kryo,
	 * com.esotericsoftware.kryo.io.Output)
	 */
	@Override
	public void write(Kryo kryo, Output output) {
		super.write(kryo, output);
		kryo.writeObject(output, this.returnType);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.util.reflect.DynamicInvokable#copy(com.esotericsoftware.kryo.Kryo)
	 */
	@Override
	public DynamicMethod<ReturnType> copy(Kryo kryo) {
		final DynamicMethod<ReturnType> copy = (DynamicMethod<ReturnType>) super.copy(kryo);
		copy.returnType = this.returnType;
		return copy;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.util.reflect.DynamicInvokable#needsInstance(java.lang.reflect.Member)
	 */
	@Override
	protected boolean needsInstance(final Method member) {
		return (member.getModifiers() & Modifier.STATIC) == 0;
	}

	public Method getMethod(Signature signature) {
		return super.getMember(signature);
	}

	@Override
	protected Method findMember(final String name, final java.lang.Class<Object> clazz,
			final java.lang.Class<?>[] parameterTypes)
			throws NoSuchMethodException {
		return clazz.getDeclaredMethod(name, parameterTypes);
	}

	public static DynamicMethod<?> valueOf(final Class<?> clazz, final String name) {
		final DynamicMethod<?> method = new DynamicMethod<Object>(name);
		for (final Method m : clazz.getDeclaredMethods())
			if (m.getName().equals(name))
				method.addSignature(m);
		return method;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.returnType.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		DynamicMethod<?> other = (DynamicMethod<?>) obj;
		return this.returnType.equals(other.returnType);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}
}
