package eu.stratosphere.sopremo.type;

import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;

import eu.stratosphere.sopremo.type.IJsonNode.Type;
import eu.stratosphere.sopremo.type.TypeCoercer.Coercer;

public final class NumberCoercer {
	/**
	 * The default instance.
	 */
	public static final NumberCoercer INSTANCE = new NumberCoercer();

	private final Map<AbstractJsonNode.Type, Coercer<? extends INumericNode, ? extends INumericNode>> coercers =
		new EnumMap<AbstractJsonNode.Type, Coercer<? extends INumericNode, ? extends INumericNode>>(
			AbstractJsonNode.Type.class);

	private final Map<Class<? extends IJsonNode>, Coercer<? extends INumericNode, ? extends INumericNode>> classCoercers =
		new IdentityHashMap<Class<? extends IJsonNode>, Coercer<? extends INumericNode, ? extends INumericNode>>();

	// private final Map<Class<? extends INumericNode>, Map<Class<? extends INumericNode>, Class<? extends
	// INumericNode>>> widerClass =
	// new IdentityHashMap<Class<? extends INumericNode>, Map<Class<? extends INumericNode>, Class<? extends
	// INumericNode>>>();

	public NumberCoercer() {
		this.coercers.put(AbstractJsonNode.Type.IntNode, new Coercer<INumericNode, IntNode>(IntNode.class) {
			@Override
			public IntNode coerce(final INumericNode from, final IntNode target) {
				target.setValue(from.getIntValue());
				return target;
			}
		});
		this.coercers.put(AbstractJsonNode.Type.LongNode, new Coercer<INumericNode, LongNode>(LongNode.class) {
			@Override
			public LongNode coerce(final INumericNode from, final LongNode target) {
				target.setValue(from.getLongValue());
				return target;
			}
		});
		this.coercers.put(AbstractJsonNode.Type.DoubleNode, new Coercer<INumericNode, DoubleNode>(DoubleNode.class) {
			@Override
			public DoubleNode coerce(final INumericNode from, final DoubleNode target) {
				target.setValue(from.getDoubleValue());
				return target;
			}
		});
		this.coercers.put(AbstractJsonNode.Type.DecimalNode, new Coercer<INumericNode, DecimalNode>(DecimalNode.class) {
			@Override
			public DecimalNode coerce(final INumericNode from, final DecimalNode target) {
				target.setValue(from.getDecimalValue());
				return target;
			}
		});
		this.coercers.put(AbstractJsonNode.Type.BigIntegerNode, new Coercer<INumericNode, BigIntegerNode>(
			BigIntegerNode.class) {
			@Override
			public BigIntegerNode coerce(final INumericNode from, final BigIntegerNode target) {
				target.setValue(from.getBigIntegerValue());
				return target;
			}
		});

		for (final Entry<AbstractJsonNode.Type, Coercer<? extends INumericNode, ? extends INumericNode>> entry : this.coercers
			.entrySet())
			this.classCoercers.put(entry.getKey().getClazz(), entry.getValue());
	}

	@SuppressWarnings("unchecked")
	public <From extends INumericNode, To extends INumericNode> To coerce(final From node, final To target,
			final Class<To> targetType) {
		if (node.getClass() == targetType)
			return (To) node;
		final Coercer<From, To> coercer = (Coercer<From, To>) this.classCoercers.get(targetType);
		return coercer.coerce(node, target);
	}

	@SuppressWarnings("unchecked")
	public <From extends INumericNode, To extends INumericNode> To coerce(final From node, final To target,
			final AbstractJsonNode.Type targetType) {
		if (node.getType() == targetType)
			return (To) node;
		final Coercer<From, To> coercer = (Coercer<From, To>) this.coercers.get(targetType);
		return coercer.coerce(node, target);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	Map<Class<? extends IJsonNode>, Coercer<?, ?>> getClassCoercers() {
		return (Map) this.classCoercers;
	}

	public AbstractJsonNode.Type getWiderType(final IJsonNode leftNode, final IJsonNode rightNode) {
		final Type leftType = leftNode.getType(), rightType = rightNode.getType();
		return leftType.ordinal() >= rightType.ordinal() ? leftType : rightType;
	}

	// public Class<? extends AbstractJsonNode> getWiderClass(final IJsonNode leftType, final IJsonNode rightType) {
	// return this.widerClass.get(leftType.getClass()).get(rightType.getClass());
	// }
}
