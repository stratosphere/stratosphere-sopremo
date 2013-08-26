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
		private CollectionToArrayMapper(Type targetType) {
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
		public IArrayNode mapTo(Collection from, IArrayNode target) {
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
		for (Class<?> primitive : Primitives.allPrimitiveTypes())
			if (Number.class.isAssignableFrom(primitive))
				PrimitiveNumbers.add(primitive);
	}

	public static final JavaToJsonMapper INSTANCE = new JavaToJsonMapper();

	static final TypeMapper<?, ?> SelfMapper = new TypeMapper<Object, Object>(null) {
		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.type.TypeMapper#mapTo(java.lang.Object, java.lang.Object, java.lang.reflect.Type)
		 */
		@Override
		public Object mapTo(Object from, Object target) {
			return from;
		}
	};

	protected JavaToJsonMapper() {
		addDefaultTypeMapping(Void.class, NullNode.class);
		addDefaultTypeMapping(Integer.class, IntNode.class);
		addDefaultTypeMapping(Integer.TYPE, IntNode.class);
		addDefaultTypeMapping(Short.class, IntNode.class);
		addDefaultTypeMapping(Short.TYPE, IntNode.class);
		addDefaultTypeMapping(Byte.class, IntNode.class);
		addDefaultTypeMapping(Byte.TYPE, IntNode.class);
		addDefaultTypeMapping(Long.class, LongNode.class);
		addDefaultTypeMapping(Long.TYPE, LongNode.class);
		addDefaultTypeMapping(BigInteger.class, BigIntegerNode.class);
		addDefaultTypeMapping(BigDecimal.class, DecimalNode.class);
		addDefaultTypeMapping(Double.class, DoubleNode.class);
		addDefaultTypeMapping(Double.TYPE, DoubleNode.class);
		addDefaultTypeMapping(Float.class, DoubleNode.class);
		addDefaultTypeMapping(Float.TYPE, DoubleNode.class);
		addDefaultTypeMapping(CharSequence.class, TextNode.class);
		addDefaultTypeMapping(Boolean.class, BooleanNode.class);
		addDefaultTypeMapping(Boolean.TYPE, BooleanNode.class);
		addDefaultTypeMapping(Enum.class, TextNode.class);
		addDefaultTypeMapping(Map.class, ObjectNode.class);
		addDefaultTypeMapping(Collection.class, CachingArrayNode.class);

		addToIntMapper();
		addToDoubleMapper();
		addToLongMapper();
		addToBigDecimalMapper();
		addToBigIntegerMapper();
		addToStringMapper();
		addToBooleanMapper();
		addToObjectMapper();
	}

	@Override
	protected Type findDefaultMappingType(final Class<?> fromClass) {
		if (fromClass.isArray() || Collection.class.isAssignableFrom(fromClass)) {
			return CachingArrayNode.class;
		} else if (IJsonNode.class.isAssignableFrom(fromClass)) {
			return fromClass;
		}

		return super.findDefaultMappingType(fromClass);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractTypeMapper#findMapper(java.lang.Class, java.lang.Class,
	 * java.lang.reflect.Type, java.lang.Class)
	 */
	@Override
	protected TypeMapper<?, ?> findMapper(Class<?> fromClass, Class<?> originalFromClass, Type targetType,
			Class<?> rawTarget) {
		if (fromClass.isArray()) {
			final ArrayToArrayMapper mapper = new ArrayToArrayMapper(targetType);
			addMapper(fromClass, targetType, mapper);
			return mapper;
		} else if (Collection.class.isAssignableFrom(fromClass)) {
			final CollectionToArrayMapper mapper = new CollectionToArrayMapper(targetType);
			addMapper(fromClass, targetType, mapper);
			return mapper;
		} else if (fromClass == rawTarget) {
			addMapper(fromClass, fromClass, SelfMapper);
			return SelfMapper;
		}

		return super.findMapper(fromClass, originalFromClass, targetType, rawTarget);
	}

	@SuppressWarnings("rawtypes")
	private void addToObjectMapper() {
		final TypeMapper<Map<?, ?>, IObjectNode> toObjectMapper = new TypeMapper<Map<?, ?>, IObjectNode>(ObjectNode.class) {
			Set<String> unusedKeys = new HashSet<String>();

			@Override
			public IObjectNode mapTo(Map<?, ?> from, IObjectNode target) {
				this.unusedKeys.addAll(target.getFieldNames());

				for (Map.Entry entry : from.entrySet()) {
					final String targetKey = entry.getKey().toString();
					target.put(targetKey, INSTANCE.map(entry.getValue()));
					this.unusedKeys.remove(targetKey);
				}

				for (String key : this.unusedKeys)
					target.remove(key);
				return target;
			}
		};
		addMapper(Map.class, ObjectNode.class, toObjectMapper);
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
		public ArrayToArrayMapper(Type targetType) {
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
		public IArrayNode<IJsonNode> mapTo(Object from, IArrayNode target) {
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
		final TypeMapper<CharSequence, TextNode> charSeqToTextMapper = new TypeMapper<CharSequence, TextNode>(TextNode.class) {
			@Override
			public TextNode mapTo(CharSequence from, TextNode target) {
				target.setValue(from);
				return target;
			}
		};
		addMapper(CharSequence.class, TextNode.class, charSeqToTextMapper);
		final TypeMapper<Object, TextNode> anyToTextMapper = new TypeMapper<Object, TextNode>(TextNode.class) {
			@Override
			public TextNode mapTo(Object from, TextNode target) {
				target.setValue(from.toString());
				return target;
			}
		};
		addMapper(Object.class, TextNode.class, anyToTextMapper);
		final TypeMapper<Enum<?>, TextNode> enumToTextMapper = new TypeMapper<Enum<?>, TextNode>(TextNode.class) {
			@Override
			public TextNode mapTo(Enum<?> from, TextNode target) {
				target.setValue(from.name());
				return target;
			}
		};
		addMapper(Enum.class, TextNode.class, enumToTextMapper);
	}

	private void addToBooleanMapper() {
		final TypeMapper<Boolean, BooleanNode> toBooleanMapper = new TypeMapper<Boolean, BooleanNode>(null) {
			@Override
			public BooleanNode mapTo(Boolean from, BooleanNode target) {
				return BooleanNode.valueOf(from);
			}
		};
		addMapper(Boolean.class, BooleanNode.class, toBooleanMapper);
		addMapper(Boolean.TYPE, BooleanNode.class, toBooleanMapper);
	}

	private void addToIntMapper() {
		final TypeMapper<Number, IntNode> toIntMapper = new TypeMapper<Number, IntNode>(IntNode.class) {
			@Override
			public IntNode mapTo(Number from, IntNode target) {
				target.setValue(from.intValue());
				return target;
			}
		};
		addMapper(Number.class, IntNode.class, toIntMapper);
		for (Class<?> primitiveNumber : PrimitiveNumbers)
			addMapper(primitiveNumber, IntNode.class, toIntMapper);
	}

	private void addToDoubleMapper() {
		final TypeMapper<Number, DoubleNode> toDoubleMapper = new TypeMapper<Number, DoubleNode>(DoubleNode.class) {
			@Override
			public DoubleNode mapTo(Number from, DoubleNode target) {
				target.setValue(from.doubleValue());
				return target;
			}
		};
		addMapper(Number.class, DoubleNode.class, toDoubleMapper);
		for (Class<?> primitiveNumber : PrimitiveNumbers)
			addMapper(primitiveNumber, DoubleNode.class, toDoubleMapper);
	}

	private void addToLongMapper() {
		final TypeMapper<Number, LongNode> toLongMapper = new TypeMapper<Number, LongNode>(LongNode.class) {
			@Override
			public LongNode mapTo(Number from, LongNode target) {
				target.setValue(from.longValue());
				return target;
			}
		};
		addMapper(Number.class, LongNode.class, toLongMapper);
		for (Class<?> primitiveNumber : PrimitiveNumbers)
			addMapper(primitiveNumber, LongNode.class, toLongMapper);
	}

	private void addToBigDecimalMapper() {
		final TypeMapper<BigDecimal, DecimalNode> toBigDecimalMapper =
			new TypeMapper<BigDecimal, DecimalNode>(DecimalNode.class) {
				@Override
				public DecimalNode mapTo(BigDecimal from, DecimalNode target) {
					target.setValue(from);
					return target;
				}
			};
		addMapper(BigDecimal.class, DecimalNode.class, toBigDecimalMapper);
	}

	private void addToBigIntegerMapper() {
		final TypeMapper<BigInteger, BigIntegerNode> toBigIntegerMapper =
			new TypeMapper<BigInteger, BigIntegerNode>(BigIntegerNode.class) {
				@Override
				public BigIntegerNode mapTo(BigInteger from, BigIntegerNode target) {
					target.setValue(from);
					return target;
				}
			};
		addMapper(BigInteger.class, BigIntegerNode.class, toBigIntegerMapper);
	}

	@SuppressWarnings("unchecked")
	public <T extends IJsonNode> T map(Object from, T to, Type targetType) {
		if (from == null)
			return (T) NullNode.getInstance();

		TypeMapper<Object, T> targetMapper = (TypeMapper<Object, T>) getMapper(from.getClass(), targetType);
		if (targetMapper == null)
			throw new IllegalArgumentException(String.format("Cannot map %s to %s ", from, targetType));

		return map(from, to, targetMapper);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractTypeMapper#getMapper(java.lang.Object, java.lang.reflect.Type)
	 */
	@SuppressWarnings("unchecked")
	public <F, T extends IJsonNode> TypeMapper<F, T> getMapper(Class<?> fromClass, Class<T> targetType) {
		return (TypeMapper<F, T>) super.getMapper(fromClass, targetType);
	}

	private <T extends IJsonNode> T map(Object from, T to, TypeMapper<Object, T> targetMapper) {
		if (to == null && targetMapper.getDefaultType() != null)
			to = ReflectUtil.newInstance(targetMapper.getDefaultType());

		return targetMapper.mapTo(from, to);
	}

	public IJsonNode map(final Object value) {
		if (value == null)
			return NullNode.getInstance();
		Type type = getDefaultMappingType(value.getClass());
		if (type == null)
			throw new IllegalArgumentException("Cannot find appropriate json type for " + value);
		return map(value, null, type);
	}

}
