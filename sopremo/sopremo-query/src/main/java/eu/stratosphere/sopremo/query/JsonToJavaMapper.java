package eu.stratosphere.sopremo.query;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.primitives.Primitives;

import eu.stratosphere.sopremo.cache.NodeCache;
import eu.stratosphere.sopremo.type.BooleanNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.INumericNode;
import eu.stratosphere.sopremo.type.JavaToJsonMapper;
import eu.stratosphere.sopremo.type.NullNode;
import eu.stratosphere.sopremo.type.TextNode;
import eu.stratosphere.sopremo.type.TypeCoercer;

/**
 * This class allows the conversion between java classes and JsonNodes.
 */
public class JsonToJavaMapper {
	public final static JsonToJavaMapper INSTANCE = new JsonToJavaMapper();

	private NodeCache nodeCache = new NodeCache();

	private final static InputSuggestion InputSuggestion = new InputSuggestion().withMaxSuggestions(3)
		.withMinSimilarity(0.5);

	@SuppressWarnings("unchecked")
	public <T> T treeToValue(IJsonNode node, Class<T> targetClass) {
		if (node == NullNode.getInstance())
			return null;

		if (IJsonNode.class.isAssignableFrom(targetClass))
			return (T) TypeCoercer.INSTANCE.coerce(node, this.nodeCache, (Class<IJsonNode>) targetClass);

		if (CharSequence.class == targetClass || String.class == targetClass)
			return (T) node.toString();

		if (Primitives.wrap(targetClass) == Boolean.class)
			return (T) Boolean.valueOf(TypeCoercer.INSTANCE.coerce(node, this.nodeCache, BooleanNode.class)
				.getBooleanValue());

		if (targetClass.isArray()) {
			final IArrayNode<?> arrayNode = TypeCoercer.INSTANCE.coerce(node, this.nodeCache, IArrayNode.class);
			final int length = arrayNode.size();
			final Class<?> componentType = targetClass.getComponentType();
			final T array = (T) Array.newInstance(componentType, length);
			for (int index = 0; index < length; index++)
				Array.set(array, index, this.treeToValue(arrayNode.get(index), componentType));
			return array;
		}

		if (Collection.class.isAssignableFrom(targetClass))
			// TODO: how to translate to component type?
			throw new UnsupportedOperationException();
		// final IArrayNode<?> arrayNode = TypeCoercer.INSTANCE.coerce(node, this.nodeCache, IArrayNode.class);
		// final List arrayNode = new ArrayNode<IJsonNode>();
		// for (final Object element : (Collection<?>) value)
		// arrayNode.add(this.valueToTree(element));
		// return arrayNode;

		if (Map.class.isAssignableFrom(targetClass))
			// TODO: how to translate to value type?
			throw new UnsupportedOperationException();
		// final ObjectNode objectNode = new ObjectNode();
		// for (final Entry<?, ?> element : ((Map<?, ?>) value).entrySet())
		// objectNode.put(element.getKey().toString(), this.valueToTree(element.getValue()));
		// return objectNode;
		if (targetClass.isEnum()) {
			T[] constants = targetClass.getEnumConstants();
			if (!(node instanceof TextNode))
				throw new IllegalArgumentException("Cannot convert " + node + " to " + targetClass);
			CharSequence nodeString = ((TextNode) node).getTextValue();
			for (T constant : constants)
				if (StringUtils.equalsIgnoreCase(constant.toString(), nodeString))
					return constant;

			throw new IllegalArgumentException(String.format(
				"Unknown nominal value %s; possible alternatives %s", nodeString,
				InputSuggestion.suggest(nodeString, Arrays.asList(constants))));
		}

		Class<T> wrapperType = Primitives.wrap(targetClass);
		if (Primitives.isWrapperType(wrapperType)) {
			final Class<? extends IJsonNode> type = JavaToJsonMapper.INSTANCE.classToJsonType(targetClass);
			IJsonNode equivalentValue = TypeCoercer.INSTANCE.coerce(node, this.nodeCache, type);

			return (T) ((INumericNode) equivalentValue).getJavaValue();
		}

		throw new UnsupportedOperationException();
	}

}
