package eu.stratosphere.sopremo.type;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.common.primitives.Primitives;
import com.google.common.reflect.TypeToken;

import eu.stratosphere.util.reflect.ReflectUtil;

/**
 * This class allows the conversion between java classes and JsonNodes.
 */
public class JavaToJsonMapper extends AbstractTypeMapper<TypeMapper<?, ?>> {
	/**
	 * @author arv
	 */
	@SuppressWarnings("rawtypes")
	private static final class CollectionToArrayMapper extends TypeMapper<Collection, IArrayNode> {
		private final Class<?> elemType;

		/**
		 * Initializes CollectionToArrayMapper.
		 * 
		 * @param defaultType
		 */
		private CollectionToArrayMapper(final Type targetType) {
			super(CachingArrayNode.class);
			Class<?> elemType = targetType instanceof ParameterizedType
				? TypeToken.of(((ParameterizedType) targetType).getActualTypeArguments()[0]).getRawType()
				: IJsonNode.class;
			if (elemType == Object.class)
				elemType = IJsonNode.class;
			this.elemType = elemType;
		}

		@SuppressWarnings("unchecked")
		@Override
		public IArrayNode mapTo(final Collection from, final IArrayNode target) {
			final int targetSize = from.size();

			final Iterator iterator = from.iterator();
			((AbstractArrayNode) target).setSize(targetSize);

			for (int index = 0; index < targetSize; index++)
				target.set(index, INSTANCE.map(iterator.next(), target.get(index), this.elemType));
			return target;
		}
	}

	private final static Set<Class<?>> PrimitiveNumbers;

	static {
		PrimitiveNumbers = new HashSet<Class<?>>();
		for (final Class<?> primitive : Primitives.allPrimitiveTypes())
			if (Number.class.isAssignableFrom(primitive))
				PrimitiveNumbers.add(primitive);
	}

	public static final JavaToJsonMapper INSTANCE = new JavaToJsonMapper();

	static final TypeMapper<?, ?> SelfMapper = new TypeMapper<Object, Object>(null) {
		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.type.TypeMapper#mapTo(java.lang.Object, java.lang.Object,
		 * java.lang.reflect.Type)
		 */
		@Override
		public Object mapTo(final Object from, final Object target) {
			return from;
		}
	};

	protected JavaToJsonMapper() {
		this.addDefaultTypeMapping(Void.class, NullNode.class);
		this.addDefaultTypeMapping(Integer.class, IntNode.class);
		this.addDefaultTypeMapping(Integer.TYPE, IntNode.class);
		this.addDefaultTypeMapping(Short.class, IntNode.class);
		this.addDefaultTypeMapping(Short.TYPE, IntNode.class);
		this.addDefaultTypeMapping(Byte.class, IntNode.class);
		this.addDefaultTypeMapping(Byte.TYPE, IntNode.class);
		this.addDefaultTypeMapping(Long.class, LongNode.class);
		this.addDefaultTypeMapping(Long.TYPE, LongNode.class);
		this.addDefaultTypeMapping(BigInteger.class, BigIntegerNode.class);
		this.addDefaultTypeMapping(BigDecimal.class, DecimalNode.class);
		this.addDefaultTypeMapping(Double.class, DoubleNode.class);
		this.addDefaultTypeMapping(Double.TYPE, DoubleNode.class);
		this.addDefaultTypeMapping(Float.class, DoubleNode.class);
		this.addDefaultTypeMapping(Float.TYPE, DoubleNode.class);
		this.addDefaultTypeMapping(CharSequence.class, TextNode.class);
		this.addDefaultTypeMapping(Boolean.class, BooleanNode.class);
		this.addDefaultTypeMapping(Boolean.TYPE, BooleanNode.class);
		this.addDefaultTypeMapping(Enum.class, TextNode.class);
		this.addDefaultTypeMapping(Map.class, ObjectNode.class);
		this.addDefaultTypeMapping(Collection.class, CachingArrayNode.class);

		this.addToIntMapper();
		this.addToDoubleMapper();
		this.addToLongMapper();
		this.addToBigDecimalMapper();
		this.addToBigIntegerMapper();
		this.addToStringMapper();
		this.addToBooleanMapper();
		this.addToObjectMapper();
	}

	@Override
	protected Type findDefaultMappingType(final Class<?> fromClass) {
		if (fromClass.isArray() || Collection.class.isAssignableFrom(fromClass))
			return CachingArrayNode.class;
		else if (IJsonNode.class.isAssignableFrom(fromClass))
			return fromClass;

		return super.findDefaultMappingType(fromClass);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractTypeMapper#findMapper(java.lang.Class, java.lang.Class,
	 * java.lang.reflect.Type, java.lang.Class)
	 */
	@Override
	protected TypeMapper<?, ?> findMapper(final Class<?> fromClass, final Class<?> originalFromClass,
			final Type targetType,
			final Class<?> rawTarget) {
		if (fromClass.isArray()) {
			final ArrayToArrayMapper mapper = new ArrayToArrayMapper(targetType);
			this.addMapper(fromClass, targetType, mapper);
			return mapper;
		} else if (Collection.class.isAssignableFrom(fromClass)) {
			final CollectionToArrayMapper mapper = new CollectionToArrayMapper(targetType);
			this.addMapper(fromClass, targetType, mapper);
			return mapper;
		} else if (fromClass == rawTarget) {
			this.addMapper(fromClass, fromClass, SelfMapper);
			return SelfMapper;
		}

		return super.findMapper(fromClass, originalFromClass, targetType, rawTarget);
	}

	@SuppressWarnings("rawtypes")
	private void addToObjectMapper() {
		final TypeMapper<Map<?, ?>, IObjectNode> toObjectMapper =
			new TypeMapper<Map<?, ?>, IObjectNode>(ObjectNode.class) {
				Set<String> unusedKeys = new HashSet<String>();

				@Override
				public IObjectNode mapTo(final Map<?, ?> from, final IObjectNode target) {
					this.unusedKeys.addAll(target.getFieldNames());

					for (final Map.Entry entry : from.entrySet()) {
						final String targetKey = entry.getKey().toString();
						target.put(targetKey, INSTANCE.map(entry.getValue()));
						this.unusedKeys.remove(targetKey);
					}

					for (final String key : this.unusedKeys)
						target.remove(key);
					return target;
				}
			};
		this.addMapper(Map.class, ObjectNode.class, toObjectMapper);
	}

	/**
	 * @author arvid
	 */
	@SuppressWarnings("rawtypes")
	private static class ArrayToArrayMapper extends TypeMapper<Object, IArrayNode> {
		private final Class<?> elemType;

		/**
		 * Initializes JavaToJsonMapper.ArrayToArrayMapper.
		 */
		public ArrayToArrayMapper(final Type targetType) {
			super(CachingArrayNode.class);
			Class<?> elemType =
				targetType instanceof Class ? ((Class<?>) targetType).getComponentType()
					: TypeToken.of(((ParameterizedType) targetType).getActualTypeArguments()[0]).getRawType();
			if (elemType == null || elemType == Object.class)
				elemType = IJsonNode.class;
			this.elemType = elemType;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.type.JsonToJavaMapper.Mapper#mapTo(java.lang.Object, java.lang.Object,
		 * java.lang.reflect.Type)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public IArrayNode<IJsonNode> mapTo(final Object from, final IArrayNode target) {
			final int targetSize = Array.getLength(from);

			if (target instanceof AbstractArrayNode) {
				((AbstractArrayNode) target).setSize(targetSize);

				for (int index = 0; index < targetSize; index++)
					target.set(index, INSTANCE.map(Array.get(from, index), target.get(index), this.elemType));
			}
			return target;
		}
	};

	private void addToStringMapper() {
		final TypeMapper<CharSequence, TextNode> charSeqToTextMapper =
			new TypeMapper<CharSequence, TextNode>(TextNode.class) {
				@Override
				public TextNode mapTo(final CharSequence from, final TextNode target) {
					target.setValue(from);
					return target;
				}
			};
		this.addMapper(CharSequence.class, TextNode.class, charSeqToTextMapper);
		final TypeMapper<Object, TextNode> anyToTextMapper = new TypeMapper<Object, TextNode>(TextNode.class) {
			@Override
			public TextNode mapTo(final Object from, final TextNode target) {
				target.setValue(from.toString());
				return target;
			}
		};
		this.addMapper(Object.class, TextNode.class, anyToTextMapper);
		final TypeMapper<Enum<?>, TextNode> enumToTextMapper = new TypeMapper<Enum<?>, TextNode>(TextNode.class) {
			@Override
			public TextNode mapTo(final Enum<?> from, final TextNode target) {
				target.setValue(from.name());
				return target;
			}
		};
		this.addMapper(Enum.class, TextNode.class, enumToTextMapper);
	}

	private void addToBooleanMapper() {
		final TypeMapper<Boolean, BooleanNode> toBooleanMapper = new TypeMapper<Boolean, BooleanNode>(null) {
			@Override
			public BooleanNode mapTo(final Boolean from, final BooleanNode target) {
				return BooleanNode.valueOf(from);
			}
		};
		this.addMapper(Boolean.class, BooleanNode.class, toBooleanMapper);
		this.addMapper(Boolean.TYPE, BooleanNode.class, toBooleanMapper);
	}

	private void addToIntMapper() {
		final TypeMapper<Number, IntNode> toIntMapper = new TypeMapper<Number, IntNode>(IntNode.class) {
			@Override
			public IntNode mapTo(final Number from, final IntNode target) {
				target.setValue(from.intValue());
				return target;
			}
		};
		this.addMapper(Number.class, IntNode.class, toIntMapper);
		for (final Class<?> primitiveNumber : PrimitiveNumbers)
			this.addMapper(primitiveNumber, IntNode.class, toIntMapper);
	}

	private void addToDoubleMapper() {
		final TypeMapper<Number, DoubleNode> toDoubleMapper = new TypeMapper<Number, DoubleNode>(DoubleNode.class) {
			@Override
			public DoubleNode mapTo(final Number from, final DoubleNode target) {
				target.setValue(from.doubleValue());
				return target;
			}
		};
		this.addMapper(Number.class, DoubleNode.class, toDoubleMapper);
		for (final Class<?> primitiveNumber : PrimitiveNumbers)
			this.addMapper(primitiveNumber, DoubleNode.class, toDoubleMapper);
	}

	private void addToLongMapper() {
		final TypeMapper<Number, LongNode> toLongMapper = new TypeMapper<Number, LongNode>(LongNode.class) {
			@Override
			public LongNode mapTo(final Number from, final LongNode target) {
				target.setValue(from.longValue());
				return target;
			}
		};
		this.addMapper(Number.class, LongNode.class, toLongMapper);
		for (final Class<?> primitiveNumber : PrimitiveNumbers)
			this.addMapper(primitiveNumber, LongNode.class, toLongMapper);
	}

	private void addToBigDecimalMapper() {
		final TypeMapper<BigDecimal, DecimalNode> toBigDecimalMapper =
			new TypeMapper<BigDecimal, DecimalNode>(DecimalNode.class) {
				@Override
				public DecimalNode mapTo(final BigDecimal from, final DecimalNode target) {
					target.setValue(from);
					return target;
				}
			};
		this.addMapper(BigDecimal.class, DecimalNode.class, toBigDecimalMapper);
	}

	private void addToBigIntegerMapper() {
		final TypeMapper<BigInteger, BigIntegerNode> toBigIntegerMapper =
			new TypeMapper<BigInteger, BigIntegerNode>(BigIntegerNode.class) {
				@Override
				public BigIntegerNode mapTo(final BigInteger from, final BigIntegerNode target) {
					target.setValue(from);
					return target;
				}
			};
		this.addMapper(BigInteger.class, BigIntegerNode.class, toBigIntegerMapper);
	}

	@SuppressWarnings("unchecked")
	public <T extends IJsonNode> T map(final Object from, final T to, final Type targetType) {
		if (from == null)
			return (T) NullNode.getInstance();

		final TypeMapper<Object, T> targetMapper = (TypeMapper<Object, T>) this.getMapper(from.getClass(), targetType);
		if (targetMapper == null)
			throw new IllegalArgumentException(String.format("Cannot map %s to %s ", from, targetType));

		return this.map(from, to, targetMapper);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractTypeMapper#getMapper(java.lang.Object, java.lang.reflect.Type)
	 */
	@SuppressWarnings("unchecked")
	public <F, T extends IJsonNode> TypeMapper<F, T> getMapper(final Class<?> fromClass, final Class<T> targetType) {
		return (TypeMapper<F, T>) super.getMapper(fromClass, targetType);
	}

	private <T extends IJsonNode> T map(final Object from, final T to, final TypeMapper<Object, T> targetMapper) {
		final T initializedTo;
		if (to != null && targetMapper.isValidTarget(to))
			initializedTo = to;
		else if (targetMapper.getDefaultType() != null)
			initializedTo = ReflectUtil.newInstance(targetMapper.getDefaultType());
		else
			initializedTo = null;

		return targetMapper.mapTo(from, initializedTo);
	}

	public IJsonNode map(final Object value) {
		if (value == null)
			return NullNode.getInstance();
		final Type type = this.getDefaultMappingType(value.getClass());
		if (type == null)
			throw new IllegalArgumentException("Cannot find appropriate json type for " + value);
		return this.map(value, null, type);
	}

}
