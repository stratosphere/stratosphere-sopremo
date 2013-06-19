package eu.stratosphere.sopremo.type;

import java.util.IdentityHashMap;
import java.util.Map;

import eu.stratosphere.sopremo.type.TypeCoercer.Coercer;

public final class NumberCoercer {
	/**
	 * The default instance.
	 */
	public static final NumberCoercer INSTANCE = new NumberCoercer();

	private final Map<Class<? extends INumericNode>, Coercer<? extends INumericNode, ? extends INumericNode>> coercers =
		new IdentityHashMap<Class<? extends INumericNode>, Coercer<? extends INumericNode, ? extends INumericNode>>();

	private final Map<Class<? extends INumericNode>, Map<Class<? extends INumericNode>, Class<? extends INumericNode>>> widerClass =
		new IdentityHashMap<Class<? extends INumericNode>, Map<Class<? extends INumericNode>, Class<? extends
		INumericNode>>>();

	public NumberCoercer() {
		this.coercers.put(IntNode.class, new Coercer<INumericNode, IntNode>(IntNode.class) {
			@Override
			public IntNode coerce(final INumericNode from, final IntNode target) {
				target.setValue(from.getIntValue());
				return target;
			}
		});
		this.coercers.put(LongNode.class, new Coercer<INumericNode, LongNode>(LongNode.class) {
			@Override
			public LongNode coerce(final INumericNode from, final LongNode target) {
				target.setValue(from.getLongValue());
				return target;
			}
		});
		this.coercers.put(DoubleNode.class, new Coercer<INumericNode, DoubleNode>(DoubleNode.class) {
			@Override
			public DoubleNode coerce(final INumericNode from, final DoubleNode target) {
				target.setValue(from.getDoubleValue());
				return target;
			}
		});
		this.coercers.put(DecimalNode.class, new Coercer<INumericNode, DecimalNode>(DecimalNode.class) {
			@Override
			public DecimalNode coerce(final INumericNode from, final DecimalNode target) {
				target.setValue(from.getDecimalValue());
				return target;
			}
		});
		this.coercers.put(BigIntegerNode.class, new Coercer<INumericNode, BigIntegerNode>(
			BigIntegerNode.class) {
			@Override
			public BigIntegerNode coerce(final INumericNode from, final BigIntegerNode target) {
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
		final Coercer<From, To> coercer = (Coercer<From, To>) this.coercers.get(targetType);
		return coercer.coerce(node, target);
	}

	/**
	 * Returns the coercers.
	 * 
	 * @return the coercers
	 */
	Map<Class<? extends INumericNode>, Coercer<? extends INumericNode, ? extends INumericNode>> getCoercers() {
		return this.coercers;
	}
}
