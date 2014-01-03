package eu.stratosphere.sopremo.type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javolution.text.TypeFormat;
import eu.stratosphere.sopremo.cache.NodeCache;
import eu.stratosphere.util.Reference;
import eu.stratosphere.util.reflect.ReflectUtil;
import eu.stratosphere.util.reflect.TypeHierarchyBrowser;
import eu.stratosphere.util.reflect.TypeHierarchyBrowser.Mode;
import eu.stratosphere.util.reflect.Visitor;

public class TypeCoercer {

	private final Map<Class<? extends IJsonNode>, Map<Class<? extends IJsonNode>, TypeMapper<?, ?>>> coercers =
		new IdentityHashMap<Class<? extends IJsonNode>, Map<Class<? extends IJsonNode>, TypeMapper<?, ?>>>();

	public static final List<Class<? extends INumericNode>> NUMERIC_TYPES = Arrays.asList(
		(Class<? extends INumericNode>)
		IntNode.class, DoubleNode.class, LongNode.class, DecimalNode.class, BigIntegerNode.class);

	private static final TypeMapper<IJsonNode, IJsonNode> NULL_COERCER = new TypeMapper<IJsonNode, IJsonNode>(null) {
		@Override
		public IJsonNode mapTo(final IJsonNode node, final IJsonNode target) {
			return null;
		}
	};

	/**
	 * The default instance.
	 */
	public static final TypeCoercer INSTANCE = new TypeCoercer();

	public TypeCoercer() {
		this.addCoercers(MissingNode.class,
			new IdentityHashMap<Class<? extends IJsonNode>, TypeMapper<?, MissingNode>>());
		this.addCoercers(BooleanNode.class, this.getToBooleanCoercers());
		this.addCoercers(TextNode.class, this.getToStringCoercers());
		this.addCoercers(IArrayNode.class, this.getToArrayCoercers());
		this.addCoercers(IObjectNode.class,
			new IdentityHashMap<Class<? extends IJsonNode>, TypeMapper<?, IObjectNode>>());
		this.addNumericCoercers(this.coercers);
		this.addCoercers(IJsonNode.class, new IdentityHashMap<Class<? extends IJsonNode>, TypeMapper<?, IJsonNode>>());
		this.addSelfCoercers();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <To extends IJsonNode> void addCoercers(final Class<To> targetClass, final Map<?, TypeMapper<?, To>> coercers) {
		this.coercers.put(targetClass, (Map) coercers);
	}

	private void addNumericCoercers(
			final Map<Class<? extends IJsonNode>, Map<Class<? extends IJsonNode>, TypeMapper<?, ?>>> coercers) {
		coercers.put(AbstractNumericNode.class, new IdentityHashMap<Class<? extends IJsonNode>, TypeMapper<?, ?>>());

		// init number to number
		for (final Class<? extends INumericNode> numericType : NUMERIC_TYPES) {
			final IdentityHashMap<Class<? extends IJsonNode>, TypeMapper<?, ?>> typeCoercers =
				new IdentityHashMap<Class<? extends IJsonNode>, TypeMapper<?, ?>>();
			coercers.put(numericType, typeCoercers);
			typeCoercers.put(AbstractNumericNode.class, NumberCoercer.INSTANCE.getCoercers().get(numericType));
		}

		// boolean to number
		coercers.get(AbstractNumericNode.class).put(BooleanNode.class,
			new TypeMapper<BooleanNode, IntNode>(IntNode.class) {
				@Override
				public IntNode mapTo(final BooleanNode from, final IntNode target) {
					target.setValue(from == BooleanNode.TRUE ? 1 : 0);
					return target;
				}
			});
		coercers.get(IntNode.class).put(BooleanNode.class, new TypeMapper<BooleanNode, IntNode>(IntNode.class) {
			@Override
			public IntNode mapTo(final BooleanNode from, final IntNode target) {
				target.setValue(from == BooleanNode.TRUE ? 1 : 0);
				return target;
			}
		});
		coercers.get(DoubleNode.class).put(BooleanNode.class,
			new TypeMapper<BooleanNode, DoubleNode>(DoubleNode.class) {
				@Override
				public DoubleNode mapTo(final BooleanNode from, final DoubleNode target) {
					target.setValue(from == BooleanNode.TRUE ? 1 : 0);
					return target;
				}
			});
		coercers.get(LongNode.class).put(BooleanNode.class, new TypeMapper<BooleanNode, LongNode>(LongNode.class) {
			@Override
			public LongNode mapTo(final BooleanNode from, final LongNode target) {
				target.setValue(from == BooleanNode.TRUE ? 1 : 0);
				return target;
			}
		});
		coercers.get(DecimalNode.class).put(BooleanNode.class,
			new TypeMapper<BooleanNode, DecimalNode>(DecimalNode.class) {
				@Override
				public DecimalNode mapTo(final BooleanNode from, final DecimalNode target) {
					target.setValue(from == BooleanNode.TRUE ? BigDecimal.ONE : BigDecimal.ZERO);
					return target;
				}
			});
		coercers.get(BigIntegerNode.class).put(BooleanNode.class,
			new TypeMapper<BooleanNode, BigIntegerNode>(BigIntegerNode.class) {
				@Override
				public BigIntegerNode mapTo(final BooleanNode from, final BigIntegerNode target) {
					target.setValue(from == BooleanNode.TRUE ? BigInteger.ONE : BigInteger.ZERO);
					return target;
				}
			});
		// default boolean to number conversion -> int
		coercers.get(AbstractNumericNode.class).put(BooleanNode.class,
			coercers.get(IntNode.class).get(BooleanNode.class));

		// string to number
		coercers.get(IntNode.class).put(TextNode.class, new TypeMapper<TextNode, IntNode>(IntNode.class) {
			@Override
			public IntNode mapTo(final TextNode from, final IntNode target) {
				try {
					target.setValue(TypeFormat.parseInt(from));
					return target;
				} catch (final Exception e) {
					return null;
				}
			}
		});
		coercers.get(DoubleNode.class).put(TextNode.class, new TypeMapper<TextNode, DoubleNode>(DoubleNode.class) {
			@Override
			public DoubleNode mapTo(final TextNode from, final DoubleNode target) {
				try {
					target.setValue(TypeFormat.parseDouble(from));
					return target;
				} catch (final Exception e) {
					return null;
				}
			}
		});
		coercers.get(LongNode.class).put(TextNode.class, new TypeMapper<TextNode, LongNode>(LongNode.class) {
			@Override
			public LongNode mapTo(final TextNode from, final LongNode target) {
				try {
					target.setValue(TypeFormat.parseLong(from));
					return target;
				} catch (final Exception e) {
					return null;
				}
			}
		});
		coercers.get(DecimalNode.class).put(TextNode.class, new TypeMapper<TextNode, DecimalNode>(DecimalNode.class) {
			@Override
			public DecimalNode mapTo(final TextNode from, final DecimalNode target) {
				try {
					// TODO: bottleneck
					target.setValue(new BigDecimal(from.toString()));
					return target;
				} catch (final NumberFormatException e) {
					return null;
				}
			}
		});
		coercers.get(BigIntegerNode.class).put(TextNode.class,
			new TypeMapper<TextNode, BigIntegerNode>(BigIntegerNode.class) {
				@Override
				public BigIntegerNode mapTo(final TextNode from, final BigIntegerNode target) {
					try {
						// TODO: bottleneck
						target.setValue(new BigInteger(from.toString()));
						return target;
					} catch (final NumberFormatException e) {
						return null;
					}
				}
			});
		// default text to number conversion -> decimal
		coercers.get(AbstractNumericNode.class).put(TextNode.class,
			coercers.get(DecimalNode.class).get(TextNode.class));
	}

	private void addSelfCoercers() {
		for (final Entry<Class<? extends IJsonNode>, Map<Class<? extends IJsonNode>, TypeMapper<?, ?>>> toCoercers : this.coercers
			.entrySet()) {
			final Class<? extends IJsonNode> targetClass = toCoercers.getKey();
			final Map<Class<? extends IJsonNode>, TypeMapper<?, ?>> coercers = toCoercers.getValue();
			if (coercers.containsKey(targetClass))
				continue;
			if (targetClass.isInterface())
				coercers.put(targetClass, new CopyCoercer());
			else
				coercers.put(targetClass, new SelfCoercer(targetClass));
		}
	}

	public <From extends IJsonNode, To extends IJsonNode> To coerce(final From node, final NodeCache nodeCache,
			final Class<To> targetType) {
		final To result = this.coerce(node, nodeCache, targetType, null);
		if (result == null)
			throw new CoercionException(String.format("Cannot coerce %s to %s", node, targetType));
		return result;
	}

	@SuppressWarnings("unchecked")
	public <From extends IJsonNode, To extends IJsonNode> To coerce(final From node, final NodeCache nodeCache,
			final Class<To> targetClass, final To defaultValue) {
		Map<Class<? extends IJsonNode>, TypeMapper<?, ?>> toCoercer = this.coercers.get(targetClass);
		if (toCoercer == null) {
			final Map<Class<? extends IJsonNode>, TypeMapper<?, ?>> superclassCoercers = this
				.findSuperclassCoercers(targetClass);
			if (superclassCoercers == null)
				toCoercer = new IdentityHashMap<Class<? extends IJsonNode>, TypeMapper<?, ?>>();
			else
				toCoercer = new IdentityHashMap<Class<? extends IJsonNode>, TypeMapper<?, ?>>(superclassCoercers);

			this.coercers.put(targetClass, toCoercer);
		}
		TypeMapper<From, To> fromCoercer = (TypeMapper<From, To>) toCoercer.get(node.getClass());
		if (fromCoercer == null) {
			fromCoercer = this.findJoiningCoercer(node, toCoercer);
			if (fromCoercer == null)
				fromCoercer = (TypeMapper<From, To>) NULL_COERCER;
			toCoercer.put(node.getClass(), fromCoercer);
		}

		final Class<? extends To> defaultType = fromCoercer.getDefaultType();
		To result = defaultType != null ? nodeCache.getNode(defaultType) : null;
		result = fromCoercer.mapTo(node, result);
		if (result == null)
			return defaultValue;
		return result;
	}

	protected <To, From> Map<Class<? extends IJsonNode>, TypeMapper<?, ?>> findSuperclassCoercers(final Class<?> toClass) {
		final Reference<Map<Class<? extends IJsonNode>, TypeMapper<?, ?>>> toCoercers =
			new Reference<Map<Class<? extends IJsonNode>, TypeMapper<?, ?>>>();

		TypeHierarchyBrowser.INSTANCE.visit(toClass, Mode.CLASS_FIRST, new Visitor<Class<?>>() {
			@Override
			public boolean visited(final Class<?> superClass, final int distance) {
				final Map<Class<? extends IJsonNode>, TypeMapper<?, ?>> froms =
					TypeCoercer.this.coercers.get(superClass);
				if (froms == null)
					return true;
				// found a matching coercer; terminate browsing
				toCoercers.setValue(froms);
				return false;
			}
		});

		return toCoercers.getValue();
	}

	@SuppressWarnings("unchecked")
	protected <To, From> TypeMapper<From, To> findJoiningCoercer(final From node,
			final Map<Class<? extends IJsonNode>, TypeMapper<?, ?>> toCoercer) {
		final Reference<TypeMapper<From, To>> fromCoercer = new Reference<TypeMapper<From, To>>();

		TypeHierarchyBrowser.INSTANCE.visit(node.getClass(), Mode.CLASS_FIRST, new Visitor<Class<?>>() {
			@Override
			public boolean visited(final Class<?> superClass, final int distance) {
				final TypeMapper<From, To> coercer = (TypeMapper<From, To>) toCoercer.get(superClass);
				if (coercer == null)
					return true;
				// found a matching coercer; terminate browsing
				fromCoercer.setValue(coercer);
				return false;
			}
		});

		return fromCoercer.getValue();
	}

	@SuppressWarnings("unchecked")
	public <From extends IJsonNode, To extends IJsonNode> To coerce(final From node, final NodeCache nodeCache,
			final To defaultValue) {
		return this.coerce(node, nodeCache, (Class<To>) defaultValue.getClass(), defaultValue);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<Class<? extends IJsonNode>, TypeMapper<?, IArrayNode>> getToArrayCoercers() {
		final Map<Class<? extends IJsonNode>, TypeMapper<?, IArrayNode>> toArrayCoercers =
			new IdentityHashMap<Class<? extends IJsonNode>, TypeMapper<?, IArrayNode>>();
		toArrayCoercers.put(IJsonNode.class, new TypeMapper<IJsonNode, IArrayNode>(ArrayNode.class) {
			@Override
			public IArrayNode<IJsonNode> mapTo(final IJsonNode from, final IArrayNode target) {
				target.clear();
				target.add(from);
				return target;
			}
		});
		toArrayCoercers.put(IArrayNode.class, new TypeMapper<IArrayNode, IArrayNode>(ArrayNode.class) {
			@Override
			public IArrayNode<IJsonNode> mapTo(final IArrayNode from, final IArrayNode target) {
				target.clear();
				target.addAll(from);
				return target;
			}
		});
		toArrayCoercers.put(IObjectNode.class, new TypeMapper<IObjectNode, IArrayNode>(ArrayNode.class) {
			@Override
			public IArrayNode<IJsonNode> mapTo(final IObjectNode from, final IArrayNode target) {
				target.clear();
				for (final Entry<String, IJsonNode> entry : from)
					target.add(entry.getValue());
				return target;
			}
		});
		return toArrayCoercers;
	}

	private Map<Class<? extends IJsonNode>, TypeMapper<?, BooleanNode>> getToBooleanCoercers() {
		final Map<Class<? extends IJsonNode>, TypeMapper<?, BooleanNode>> toBooleanCoercers =
			new IdentityHashMap<Class<? extends IJsonNode>, TypeMapper<?, BooleanNode>>();
		toBooleanCoercers.put(BooleanNode.class, new TypeMapper<BooleanNode, BooleanNode>(BooleanNode.class) {
			@Override
			public BooleanNode mapTo(final BooleanNode from, final BooleanNode target) {
				return from;
			}
		});
		toBooleanCoercers.put(INumericNode.class, new TypeMapper<INumericNode, BooleanNode>(BooleanNode.class) {
			@Override
			public BooleanNode mapTo(final INumericNode from, final BooleanNode target) {
				return BooleanNode.valueOf(from.getDoubleValue() != 0);
			}
		});
		toBooleanCoercers.put(TextNode.class, new TypeMapper<TextNode, BooleanNode>(BooleanNode.class) {
			@Override
			public BooleanNode mapTo(final TextNode from, final BooleanNode target) {
				return BooleanNode.valueOf(from.length() > 0);
			}
		});
		toBooleanCoercers.put(NullNode.class, new TypeMapper<NullNode, BooleanNode>(BooleanNode.class) {
			@Override
			public BooleanNode mapTo(final NullNode from, final BooleanNode target) {
				return BooleanNode.FALSE;
			}
		});
		toBooleanCoercers.put(MissingNode.class, new TypeMapper<MissingNode, BooleanNode>(BooleanNode.class) {
			@Override
			public BooleanNode mapTo(final MissingNode from, final BooleanNode target) {
				return BooleanNode.FALSE;
			}
		});
		toBooleanCoercers.put(IArrayNode.class, new TypeMapper<IArrayNode<?>, BooleanNode>(BooleanNode.class) {
			@Override
			public BooleanNode mapTo(final IArrayNode<?> from, final BooleanNode target) {
				return BooleanNode.valueOf(from.size() > 0);
			}
		});
		toBooleanCoercers.put(IObjectNode.class, new TypeMapper<IObjectNode, BooleanNode>(BooleanNode.class) {
			@Override
			public BooleanNode mapTo(final IObjectNode from, final BooleanNode target) {
				return BooleanNode.valueOf(from.size() > 0);
			}
		});
		return toBooleanCoercers;
	}

	private Map<Class<? extends IJsonNode>, TypeMapper<?, TextNode>> getToStringCoercers() {
		final Map<Class<? extends IJsonNode>, TypeMapper<?, TextNode>> toStringCoercers =
			new IdentityHashMap<Class<? extends IJsonNode>, TypeMapper<?, TextNode>>();
		toStringCoercers.put(IJsonNode.class, new TypeMapper<IJsonNode, TextNode>(TextNode.class) {
			@Override
			public TextNode mapTo(final IJsonNode from, final TextNode target) {
				target.setValue(from.toString());
				return target;
			}
		});
		toStringCoercers.put(NullNode.class, new TypeMapper<IJsonNode, TextNode>(TextNode.class) {
			@Override
			public TextNode mapTo(final IJsonNode from, final TextNode target) {
				target.setValue("");
				return target;
			}
		});
		return toStringCoercers;
	}

	public <From extends IJsonNode, To extends IJsonNode> void setCoercer(final Class<From> from, final Class<To> to,
			final TypeMapper<From, To> coercer) {
		Map<Class<? extends IJsonNode>, TypeMapper<?, ?>> toCoercers = this.coercers.get(to);
		if (toCoercers == null)
			this.coercers.put(to, toCoercers = new IdentityHashMap<Class<? extends IJsonNode>, TypeMapper<?, ?>>());
		toCoercers.put(from, coercer);
	}

	/**
	 * @author Arvid Heise
	 */
	private static final class SelfCoercer extends TypeMapper<IJsonNode, IJsonNode> {
		/**
		 * Initializes SelfCoercer.
		 */
		public SelfCoercer(final Class<? extends IJsonNode> defaultType) {
			super(defaultType);
		}

		@Override
		public IJsonNode mapTo(final IJsonNode node, final IJsonNode target) {
			target.copyValueFrom(node);
			return target;
		}
	}

	/**
	 * @author Arvid Heise
	 */
	private static final class CopyCoercer extends TypeMapper<IJsonNode, IJsonNode> {
		/**
		 * Initializes SuperCoercer.
		 */
		public CopyCoercer() {
			super(null);
		}

		@Override
		public IJsonNode mapTo(final IJsonNode node, IJsonNode target) {
			if (target == null)
				target = ReflectUtil.newInstance(node.getClass());
			target.copyValueFrom(node);
			return target;
		}
	}
}
