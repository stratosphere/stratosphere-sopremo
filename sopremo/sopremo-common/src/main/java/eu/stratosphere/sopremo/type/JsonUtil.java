package eu.stratosphere.sopremo.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.ArrayProjection;
import eu.stratosphere.sopremo.expressions.ExpressionUtil;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.expressions.PathSegmentExpression;
import eu.stratosphere.util.reflect.ReflectUtil;

/**
 * Provides a set of utility functions and objects to handle json data.
 * 
 * @author Arvid Heise
 */
public class JsonUtil {
	/**
	 * A general purpose {@link ObjectMapper}. No state of this mapper should be changed. If a specifically configured
	 * ObjectMapper is needed, a new instance should be created.
	 */
	public static final JavaToJsonMapper OBJECT_MAPPER = new JavaToJsonMapper();

	// /**
	// * A general purpose {@link JsonNodeFactory}. No state of this node factory should be changed. If a specifically
	// * configured JsonNodeFactory is needed, a new instance should be created.
	// */
	// public static final JsonNodeFactory NODE_FACTORY = JsonNodeFactory.instance;

	// /**
	// * A general purpose {@link JsonFactory}. No state of this factory should be changed. If a specifically
	// * configured JsonFactory is needed, a new instance should be created.
	// */
	// public static final JsonFactory FACTORY = new JsonFactory();

	/**
	 * Creates an efficient read-only wrapper for the given node array.
	 * 
	 * @param nodes
	 *        the nodes to wrap
	 * @return an efficient wrapper
	 */
	public static IArrayNode<IJsonNode> asArray(final IJsonNode... nodes) {
		return new ArrayNode<IJsonNode>(nodes);
	}

	/**
	 * Creates a new {@link PathExpression} from the given parts.
	 * 
	 * @param parts
	 *        the parts that should be used
	 * @return the expression
	 */
	public static PathSegmentExpression createPath(final List<String> parts) {
		final List<PathSegmentExpression> fragments = new ArrayList<PathSegmentExpression>();
		for (int index = 0; index < parts.size(); index++) {
			PathSegmentExpression segment;
			final String part = parts.get(index);
			if (part.matches("[0-9]+"))
				segment = new InputSelection(Integer.parseInt(part));
			else if (part.matches("\\[.*\\]")) {
				if (part.charAt(1) == '*') {
					segment = new ArrayProjection(createPath(parts.subList(index + 1, parts.size())));
					index = parts.size();
				} else if (part.contains(":")) {
					final int delim = part.indexOf(":");
					segment = new ArrayAccess(Integer.parseInt(part.substring(1, delim)),
						Integer.parseInt(part.substring(delim + 1, part.length() - 1)));
				} else
					segment = new ArrayAccess(Integer.parseInt(part.substring(1, part.length() - 1)));
			} else
				segment = new ObjectAccess(part);
			fragments.add(segment);
		}
		return ExpressionUtil.makePath(fragments);
	}

	/**
	 * Creates a new {@link PathExpression} from the given parts.
	 * 
	 * @param parts
	 *        the parts that should be used
	 * @return the expression
	 */
	public static PathSegmentExpression createPath(final String... parts) {
		return createPath(Arrays.asList(parts));
	}

	/**
	 * Creates an {@link ArrayNode} that contains all given constants as elements. This method converts the whole array
	 * of parameters to an ArrayNode.
	 * 
	 * @param constants
	 *        the constants that should be used to fill the array
	 * @return the array
	 */
	public static IArrayNode<?> createArrayNode(final Object... constants) {
		return (IArrayNode<?>) JsonUtil.OBJECT_MAPPER.valueToTree(constants);
	}

	/**
	 * Creates an {@link TypedStreamNode} that contains all given constants as elements. This method converts the whole
	 * array
	 * of parameters to an TypedStreamNode.
	 * 
	 * @param constants
	 *        the constants that should be used to fill the array
	 * @return the array
	 */
	public static IStreamNode<?> createStreamArrayNode(final Object... constants) {
		return new StreamNode<IJsonNode>(createArrayNode(constants).iterator());
	}

	/**
	 * Creates an {@link ArrayNode} that contains all given constants as elements. This method converts each parameter
	 * to an appropriate JsonNode before creating the ArrayNode.
	 * 
	 * @param constants
	 *        the constants that should be used to fill the array
	 * @return the array
	 * @param constants
	 * @return
	 */
	public static IArrayNode<IJsonNode> createCompactArray(final Object... constants) {
		final IJsonNode[] nodes = new IJsonNode[constants.length];
		for (int index = 0; index < nodes.length; index++)
			nodes[index] = createValueNode(constants[index]);
		return JsonUtil.asArray(nodes);
	}

	/**
	 * Creates an {@link ObjectNode} that contains all given fields with there related value. The key and value of each
	 * field has do be specified like the following example:</br>
	 * createObjectNode("key1", value1, "key2", value2, ...)
	 * 
	 * @param fields
	 *        the key-value pairs that should be used
	 * @return the object node
	 */
	public static ObjectNode createObjectNode(final Object... fields) {
		if (fields.length % 2 != 0)
			throw new IllegalArgumentException("must have an even number of params");
		final ObjectNode objectNode = new ObjectNode();
		for (int index = 0; index < fields.length; index += 2)
			objectNode.put(fields[index].toString(), JsonUtil.OBJECT_MAPPER.valueToTree(fields[index + 1]));
		return objectNode;
	}

	public static IJsonNode createValueNode(final Object value) {
		return JsonUtil.OBJECT_MAPPER.valueToTree(value);
	}

}
