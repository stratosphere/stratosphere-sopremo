package eu.stratosphere.sopremo.type;

import java.util.IdentityHashMap;
import java.util.Map;

public final class NumberCoercer {
	/**
	 * The default instance.
	 */
	public static final NumberCoercer INSTANCE = new NumberCoercer();

	private final Map<Class<? extends INumericNode>, TypeMapper<? extends INumericNode, ? extends INumericNode>> coercers =
		new IdentityHashMap<Class<? extends INumericNode>, TypeMapper<? extends INumericNode, ? extends INumericNode>>();

	public NumberCoercer() {
		this.coercers.put(IntNode.class, new TypeMapper<INumericNode, IntNode>(IntNode.class) {
			@Override
			public IntNode mapTo(final INumericNode from, final IntNode target) {
				target.setValue(from.getIntValue());
				return target;
			}
		});
		this.coercers.put(LongNode.class, new TypeMapper<INumericNode, LongNode>(LongNode.class) {
			@Override
			public LongNode mapTo(final INumericNode from, final LongNode target) {
				target.setValue(from.getLongValue());
				return target;
			}
		});
		this.coercers.put(DoubleNode.class, new TypeMapper<INumericNode, DoubleNode>(DoubleNode.class) {
			@Override
			public DoubleNode mapTo(final INumericNode from, final DoubleNode target) {
				target.setValue(from.getDoubleValue());
				return target;
			}
		});
		this.coercers.put(DecimalNode.class, new TypeMapper<INumericNode, DecimalNode>(DecimalNode.class) {
			@Override
			public DecimalNode mapTo(final INumericNode from, final DecimalNode target) {
				target.setValue(from.getDecimalValue());
				return target;
			}
		});
		this.coercers.put(BigIntegerNode.class, new TypeMapper<INumericNode, BigIntegerNode>(
			BigIntegerNode.class) {
			@Override
			public BigIntegerNode mapTo(final INumericNode from, final BigIntegerNode target) {
				target.setValue(from.getBigIntegerValue());
				return target;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public <From extends INumericNode, To extends INumericNode> To coerce(final From node, final To target,
			final Class<To> targetType) {
		if (node.getClass() == targetType)
			return (To) node;
		final TypeMapper<From, To> coercer = (TypeMapper<From, To>) this.coercers.get(targetType);
		return coercer.mapTo(node, target);
	}

	/**
	 * Returns the coercers.
	 * 
	 * @return the coercers
	 */
	Map<Class<? extends INumericNode>, TypeMapper<? extends INumericNode, ? extends INumericNode>> getCoercers() {
		return this.coercers;
	}
}
