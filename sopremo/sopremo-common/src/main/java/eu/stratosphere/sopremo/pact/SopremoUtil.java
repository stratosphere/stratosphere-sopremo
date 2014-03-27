package eu.stratosphere.sopremo.pact;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.SimpleLog;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.reflect.TypeToken;

import eu.stratosphere.configuration.Configuration;
import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.cache.NodeCache;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.UnevaluableExpression;
import eu.stratosphere.sopremo.function.SopremoFunction;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.ReusingSerializer;
import eu.stratosphere.sopremo.type.typed.ITypedObjectNode;
import eu.stratosphere.sopremo.type.typed.TypedObjectNode;
import eu.stratosphere.sopremo.type.typed.TypedObjectNodeFactory;
import eu.stratosphere.util.ICloneable;
import eu.stratosphere.util.KryoUtil;
import eu.stratosphere.util.StringUtils;

/**
 * Provides utility methods for sopremo
 */
public class SopremoUtil {

	public static final boolean DEBUG = true;

	public static final Log NORMAL_LOG = LogFactory.getLog(SopremoUtil.class), TRACE_LOG = new SimpleLog(
		SopremoUtil.class.getName()), USER_LOG = new SimpleLog("Sopremo");

	static {
		((SimpleLog) TRACE_LOG).setLevel(SimpleLog.LOG_LEVEL_TRACE);
		((SimpleLog) USER_LOG).setLevel(SimpleLog.LOG_LEVEL_ERROR);
	}

	public static Log LOG = NORMAL_LOG;

	private final static TypeToken<?> ITypedObjectNodeType = TypeToken.of(ITypedObjectNode.class);

	// Hack found in http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6400767
	private final static Charset BINARY_CHARSET = Charset.forName("ISO-8859-1");

	/**
	 * Appends the textual representation of the given objects to the appendable.
	 */
	public static void append(final Appendable appendable, final Object... objects) throws IOException {
		for (final Object object : objects)
			if (object instanceof CharSequence)
				appendable.append((CharSequence) object);
			else if (object instanceof ISopremoType)
				((ISopremoType) object).appendAsString(appendable);
			else if (object instanceof Character)
				appendable.append((Character) object);
			else
				appendable.append(String.valueOf(object));
	}

	public static void assertArguments(final SopremoFunction function, final int numberOfArguments) {
		if (!function.accepts(numberOfArguments))
			throw new IllegalArgumentException(
				String.format("Cannot use the given function as it does not accept %d arguments", numberOfArguments));
	}

	/**
	 * Configures an object with the given {@link Configuration} that has been initialized with
	 * {@link #transferFieldsToConfiguration(Object, Class, Configuration, Class, Class)}.
	 * 
	 * @param instance
	 *        the instance that should be configured
	 * @param stopClass
	 *        the class in the hierarchy of instance, up to which the state should be configured.
	 * @param parameters
	 *        the configuration that should be used
	 */
	public static void configureWithTransferredState(final Object instance, final Class<?> stopClass,
			final Configuration parameters) {
		for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
			for (final Field stubField : clazz.getDeclaredFields())
				if ((stubField.getModifiers() & (Modifier.TRANSIENT | Modifier.STATIC)) == 0) {
					final Object fieldValue = SopremoUtil.getObject(parameters, stubField.getName(), null);
					if (fieldValue != null)
						try {
							stubField.setAccessible(true);
							stubField.set(instance, SopremoUtil.getObject(parameters, stubField.getName(), null));
						} catch (final Exception e) {
							LOG.error(String.format("Could not set field %s of class %s: %s",
								stubField.getName(), clazz, StringUtils.stringifyException(e)));
						}
				}
			if (clazz == stopClass)
				break;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends IJsonNode> T copyInto(final T node, final IJsonNode possibleTarget) {
		if (possibleTarget == null || possibleTarget.getType() != node.getType())
			return (T) node.clone();
		possibleTarget.copyValueFrom(node);
		return (T) possibleTarget;
	}

	@SuppressWarnings("unchecked")
	public static <T extends IJsonNode> T copyInto(final T node, final NodeCache nodeCache) {
		final IJsonNode target = nodeCache.getNode(node.getType());
		target.copyValueFrom(node);
		return (T) target;
	}

	@SuppressWarnings("unchecked")
	public static <T extends ICloneable> List<T> deepClone(final List<T> originals) {
		final ArrayList<T> clones = new ArrayList<T>(originals.size());
		for (final T t : originals)
			clones.add((T) t.clone());
		return clones;
	}

	@SuppressWarnings("unchecked")
	public static <K, V extends ICloneable> Map<K, V> deepClone(final Map<K, V> originals) {
		final Map<K, V> clones = new HashMap<K, V>(originals.size());
		for (final Entry<K, V> original : originals.entrySet())
			clones.put(original.getKey(), (V) original.getValue().clone());
		return clones;
	}

	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> deepCloneIfPossible(final Map<K, V> originals) {
		final Map<K, V> clones = new HashMap<K, V>(originals.size());
		for (final Entry<K, V> original : originals.entrySet()) {
			V value = original.getValue();
			if (value instanceof ICloneable)
				value = (V) ((ICloneable) value).clone();
			clones.put(original.getKey(), value);
		}
		return clones;
	}

	public static <T> T deserialize(final byte[] bytes, final Class<T> clazz) {
		final Kryo kryo = KryoUtil.getKryo();
		final Input input = new Input(bytes);
		kryo.reset();
		kryo.setClassLoader(SopremoEnvironment.getInstance().getClassLoader());
		return clazz.cast(kryo.readClassAndObject(input));
	}

	/**
	 * Deserializes an {@link Serializable} from a {@link DataInput}.<br>
	 * Please note that this method is not very efficient.
	 */
	public static <T> T deserialize(final DataInput in, final Class<T> clazz) throws IOException {
		final byte[] buffer = new byte[in.readInt()];
		in.readFully(buffer);
		return deserialize(buffer, clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserializeInto(final Kryo kryo, final Input input, final T oldNode) {
		final Registration registration = kryo.readClass(input);

		final Serializer<T> serializer = registration.getSerializer();
		if (serializer instanceof ReusingSerializer<?> && registration.getType() == oldNode.getClass())
			return ((ReusingSerializer<T>) serializer).read(kryo, input, oldNode, registration.getType());
		return serializer.read(kryo, input, registration.getType());
	}

	@SuppressWarnings("unchecked")
	public static <T> T getObject(final Configuration config, final String keyName, final T defaultValue) {
		final String stringRepresentation = config.getString(keyName, null);
		if (stringRepresentation == null)
			return defaultValue;
		final byte[] bytes = stringRepresentation.getBytes(BINARY_CHARSET);
		return (T) deserialize(bytes, Object.class);
	}

	@SuppressWarnings("unchecked")
	public static <T extends IJsonNode> void replaceWithCopy(final IArrayNode<T> arrayNode, final int index,
			final T element) {
		final T oldValue = arrayNode.get(index);
		if (oldValue.getType() == element.getType())
			oldValue.copyValueFrom(element);
		else
			arrayNode.set(index, (T) element.clone());
	}

	public static void replaceWithCopy(final IObjectNode node, final String field, final IJsonNode element) {
		final IJsonNode oldValue = node.get(field);
		if (oldValue.getType() == node.getType())
			oldValue.copyValueFrom(node);
		else
			node.put(field, element.clone());
	}

	public static byte[] serializable(final Object object) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final Output output = new Output(baos);
		final Kryo kryo = KryoUtil.getKryo();
		kryo.reset();
		kryo.writeClassAndObject(output, object);
		output.close();
		return baos.toByteArray();
	}

	public static void setObject(final Configuration config, final String keyName, final Object object) {
		config.setString(keyName, new String(serializable(object), BINARY_CHARSET));
	}

	public static void trace() {
		LOG = TRACE_LOG;
	}

	/**
	 * Transfers the state of an object to the given {@link Configuration}, so that the state can be used to initialize
	 * a class of the given target class.
	 * 
	 * @param sourceInstance
	 *        the instance, of which the state should be saved
	 * @param targetClass
	 *        a reference class, of which the fields need to be initialized
	 * @param stopClass
	 *        the class in the hierarchy of instance, up to which the state should be configured.
	 * @param configuration
	 *        the configuration that should be used
	 */
	public static <S, T> void transferFieldsToConfiguration(final S sourceInstance, final Class<? super S> stopClass,
			final Configuration configuration, final Class<T> targetClass, final Class<? super T> targetStopClass) {

		for (Class<?> tarClass = targetClass; tarClass != null; tarClass = tarClass.getSuperclass()) {
			for (final Field stubField : tarClass.getDeclaredFields())
				if ((stubField.getModifiers() & (Modifier.TRANSIENT | Modifier.STATIC)) == 0)
					for (Class<?> srcClass = sourceInstance.getClass(); srcClass != null; srcClass = srcClass
						.getSuperclass()) {
						Field thisField;
						try {
							thisField = srcClass.getDeclaredField(stubField.getName());
							thisField.setAccessible(true);
							final Object value = thisField.get(sourceInstance);
							if (SopremoUtil.DEBUG && value instanceof EvaluationExpression &&
								((EvaluationExpression) value).findFirst(UnevaluableExpression.class) != null)
								throw new IllegalStateException(String.format(
									"Cannot serialize field %s with unevaluable expressions %s",
									thisField.getName(), value));
							if (value != null)
								SopremoUtil.setObject(configuration, stubField.getName(), value);
						} catch (final NoSuchFieldException e) {
							// ignore field of stub if the field does not exist in
							// this operator
						} catch (final Exception e) {
							SopremoUtil.LOG.error(String.format(
								"Could not serialize field %s of class %s: %s",
								stubField.getName(), sourceInstance.getClass().getSimpleName(), e));
							throw new RuntimeException(String.format(
								"Could not serialize field %s of class %s: %s",
								stubField.getName(), sourceInstance.getClass().getSimpleName(), e), e);
						}
						if (srcClass == stopClass)
							break;
					}

			if (tarClass == targetStopClass)
				break;
		}
	}

	public static void untrace() {
		LOG = NORMAL_LOG;
	}

	@SuppressWarnings("unchecked")
	static TypedObjectNode[] getTypedNodes(final TypeToken<?> boundFunction) {
		final Type[] actualTypeArguments = ((ParameterizedType) boundFunction.getType()).getActualTypeArguments();
		final TypedObjectNode[] nodes = new TypedObjectNode[actualTypeArguments.length - 1];
		for (int index = 0; index < nodes.length; index++)
			if (ITypedObjectNodeType.isAssignableFrom(actualTypeArguments[0]))
				nodes[index] =
					(TypedObjectNode) TypedObjectNodeFactory.getInstance().
						getTypedObjectForInterface(
							(Class<ITypedObjectNode>) TypeToken.of(actualTypeArguments[0]).getRawType());
		return nodes;
	}
}
