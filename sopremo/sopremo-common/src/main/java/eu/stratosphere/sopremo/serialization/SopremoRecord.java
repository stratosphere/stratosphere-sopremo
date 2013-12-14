/***********************************************************************************************************************
 *
 * Copyright (C) 2010 by the Stratosphere project (http://stratosphere.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 **********************************************************************************************************************/
package eu.stratosphere.sopremo.serialization;

import it.unimi.dsi.fastutil.bytes.ByteArrayList;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.SortedSet;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import eu.stratosphere.nephele.services.memorymanager.DataInputView;
import eu.stratosphere.nephele.services.memorymanager.DataOutputView;
import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.cache.NodeCache;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.type.CachingArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.sopremo.type.ObjectNode;
import eu.stratosphere.sopremo.type.ReusingSerializer;
import eu.stratosphere.sopremo.type.typed.TypedObjectNode;

/**
 * @author Arvid Heise
 */
@DefaultSerializer(value = SopremoRecord.SopremoRecordKryoSerializer.class)
public class SopremoRecord extends AbstractSopremoType implements ISopremoType {

	/**
	 * 
	 */
	private static final int MISSING = -1;

	private final transient ByteArrayList binaryRepresentation = new ByteArrayList();

	private final transient Input input = new Input();

	private final transient Output output = new Output(new OutputStream() {
		@Override
		public void write(final byte[] b, final int off, final int len) throws IOException {
			SopremoRecord.this.binaryRepresentation.addElements(SopremoRecord.this.binaryRepresentation.size(), b, off,
				len);
		};

		@Override
		public void write(final byte[] b) throws IOException {
			SopremoRecord.this.binaryRepresentation.addElements(SopremoRecord.this.binaryRepresentation.size(), b);
		};

		@Override
		public void write(final int b) throws IOException {
			SopremoRecord.this.binaryRepresentation.add((byte) b);
		}
	});

	private transient IJsonNode node;

	private final transient Kryo kryo;

	private transient int offsets[];

	private final transient Map<Class<? extends IJsonNode>, NodeSerializer<IJsonNode>> serializers =
		new IdentityHashMap<Class<? extends IJsonNode>, NodeSerializer<IJsonNode>>();

	private final transient Map<Class<? extends IJsonNode>, NodeDeserializer<IJsonNode>> deserializers =
		new IdentityHashMap<Class<? extends IJsonNode>, NodeDeserializer<IJsonNode>>();

	/**
	 * Initializes SopremoRecord.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SopremoRecord() {
		this.offsets = new int[0];
		this.kryo = SopremoEnvironment.getInstance().getEvaluationContext().getKryoForDataSerialization();

		this.serializers.put(IObjectNode.class, (NodeSerializer) new ObjectSerializer());
		this.serializers.put(IArrayNode.class, (NodeSerializer) new ArraySerializer());
		this.serializers.put(IJsonNode.class, new PrimitiveSerializer());
		this.deserializers.put(IObjectNode.class, (NodeDeserializer) new ObjectSerializer());
		this.deserializers.put(IArrayNode.class, (NodeDeserializer) new CachingArrayDeserializer());
		this.deserializers.put(IJsonNode.class, new PrimitiveSerializer());
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.getOrParseNode().appendAsString(appendable);
	}

	/**
	 * Returns the node.
	 * 
	 * @return the node
	 */
	public IJsonNode getNode() {
		return this.node;
	}

	public IJsonNode getOrParseNode() {
		if (this.node == null)
			return this.node = this.parseNode();
		return this.node;
	}

	public IJsonNode parseNode() {
		this.input.setBuffer(this.binaryRepresentation.elements(), 0, this.binaryRepresentation.size());
		return this.readRecursively(this.node);
	}

	@SuppressWarnings("unchecked")
	private IJsonNode readRecursively(final IJsonNode possibleTarget) {
		final Registration registration = this.kryo.readClass(this.input);
		final Class<IJsonNode> type = registration.getType();
		return this.getDeserializer(type).read(
			possibleTarget == null || possibleTarget.getType() != type ? null : possibleTarget, registration);
	}

	@SuppressWarnings("unchecked")
	private IJsonNode readRecursively(final NodeCache nodeCache) {
		final Registration registration = this.kryo.readClass(this.input);
		final Class<IJsonNode> type = registration.getType();
		return this.getDeserializer(type).read(nodeCache.getNode(type), registration);
	}

	// private Class<? extends IJsonNode> getImplementation(Class<? extends IJsonNode> interfaceType) {
	// if (interfaceType == IObjectNode.class)
	// return ObjectNode.class;
	// if (interfaceType == IArrayNode.class)
	// return ArrayNode.class;
	// return interfaceType;
	// }

	/**
	 * Sets the node to the specified value.
	 * 
	 * @param node
	 *        the node to set
	 */
	public void setNode(final IJsonNode node) {
		if (node == null)
			throw new NullPointerException("node must not be null");

		this.node = node;
	}

	void write(final DataOutputView out, final SopremoRecordLayout layout) throws IOException {
		if (this.node != null) {
			this.binaryRepresentation.clear();
			final int numKeys = layout.getNumKeys();
			if (numKeys != this.offsets.length)
				this.offsets = new int[numKeys];
			Arrays.fill(this.offsets, MISSING);
			this.writeRecursivelyToBuffer(this.node, layout.getExpressionIndex());

			final EvaluationExpression[] calculatedKeyExpressions = layout.getCalculatedKeyExpressions();
			for (int index = 0; index < calculatedKeyExpressions.length; index++) {
				this.offsets[index + layout.getNumDirectDataKeys()] = this.position();
				final IJsonNode calculatedValue = calculatedKeyExpressions[index].evaluate(this.node);
				this.kryo.writeClass(this.output, calculatedValue.getType());
				this.kryo.writeObject(this.output, calculatedValue);
			}
			this.output.flush();
		} else if (SopremoUtil.DEBUG && this.binaryRepresentation.size() == 0)
			throw new IllegalStateException("Attempt to write zero length binary representation");

		for (int index = 0; index < this.offsets.length; index++) {
			if (SopremoUtil.DEBUG && this.offsets[index] == 0)
				throw new IllegalStateException();
			out.writeInt(this.offsets[index]);
		}
		final int size = this.binaryRepresentation.size();
		out.writeInt(size);
		out.write(this.binaryRepresentation.elements(), 0, size);
	}

	private void writeRecursivelyToBuffer(final IJsonNode node, final ExpressionIndex expressionIndex) {
		final NodeSerializer<IJsonNode> serializer = this.getSerializer(node.getType());
		SopremoRecord.this.kryo.writeClass(SopremoRecord.this.output, node.getType());
		if (node instanceof TypedObjectNode)
			serializer.write(((TypedObjectNode) node).getBackingNode(), expressionIndex);
		else
			serializer.write(node, expressionIndex);
	}

	/**
	 * @param node2
	 * @return
	 */
	private NodeSerializer<IJsonNode> getSerializer(final Class<? extends IJsonNode> type) {
		final NodeSerializer<IJsonNode> serializer = this.serializers.get(type);
		if (serializer == null) {
			final NodeSerializer<IJsonNode> defaultSerializer = this.serializers.get(IJsonNode.class);
			this.serializers.put(type, defaultSerializer);
			return defaultSerializer;
		}
		return serializer;
	}

	private NodeDeserializer<IJsonNode> getDeserializer(final Class<? extends IJsonNode> type) {
		final NodeDeserializer<IJsonNode> deserializer = this.deserializers.get(type);
		if (deserializer == null) {
			final NodeDeserializer<IJsonNode> defaultSerializer = this.deserializers.get(IJsonNode.class);
			this.deserializers.put(type, defaultSerializer);
			return defaultSerializer;
		}
		return deserializer;
	}

	private static interface NodeSerializer<T extends IJsonNode> {
		public void write(T node, ExpressionIndex expressionIndex);
	}

	private static interface NodeDeserializer<T extends IJsonNode> {
		public T read(T node, Registration registration);
	}

	private int position() {
		return this.binaryRepresentation.size() + this.output.position();
	}

	private class ObjectSerializer implements NodeSerializer<IObjectNode>, NodeDeserializer<IObjectNode> {
		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.serialization.SopremoRecord.NodeSerializer#write(eu.stratosphere.sopremo.type.IJsonNode
		 * ,
		 * java.util.List)
		 */
		@Override
		public void write(final IObjectNode node, final ExpressionIndex expressionIndex) {
			final SortedSet<String> fieldNames = node.getFieldNames();
			SopremoRecord.this.output.writeInt(fieldNames.size());
			for (final String fieldName : fieldNames) {
				SopremoRecord.this.output.writeString(fieldName);
				final ExpressionIndex subIndex;
				if (expressionIndex != null) {
					subIndex = expressionIndex.subIndex(fieldName);
					if (subIndex != null && subIndex.getExpression() != null)
						SopremoRecord.this.offsets[subIndex.getKeyIndex()] = SopremoRecord.this.position();
				} else
					subIndex = null;
				SopremoRecord.this.writeRecursivelyToBuffer(node.get(fieldName), subIndex);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.serialization.SopremoRecord.NodeSerializer#read(eu.stratosphere.sopremo.type.IJsonNode
		 * , com.esotericsoftware.kryo.Registration)
		 */
		@Override
		public IObjectNode read(IObjectNode target, final Registration registration) {
			if (target != null)
				target.clear();
			else
				target = new ObjectNode();

			final int size = SopremoRecord.this.input.readInt();
			for (int index = 0; index < size; index++) {
				final String key = SopremoRecord.this.input.readString();
				// add caching
				target.put(key, SopremoRecord.this.readRecursively((IJsonNode) null));
			}
			return target;
		}
	}

	private class CachingArrayDeserializer implements NodeDeserializer<CachingArrayNode<IJsonNode>> {
		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.serialization.SopremoRecord.NodeSerializer#read(eu.stratosphere.sopremo.type.IJsonNode
		 * , com.esotericsoftware.kryo.Registration)
		 */
		@Override
		public CachingArrayNode<IJsonNode> read(CachingArrayNode<IJsonNode> target, final Registration registration) {
			if (target != null)
				target.clear();
			else
				target = new CachingArrayNode<IJsonNode>();

			final int size = SopremoRecord.this.input.readInt();
			target.clear();
			for (int index = 0; index < size; index++)
				target.add(SopremoRecord.this.readRecursively(target.getUnusedNode()));
			return target;
		}
	}

	private class ArraySerializer implements NodeSerializer<IArrayNode<IJsonNode>> {
		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.serialization.SopremoRecord.NodeSerializer#write(eu.stratosphere.sopremo.type.IJsonNode
		 * ,
		 * java.util.List)
		 */
		@Override
		public void write(final IArrayNode<IJsonNode> node, final ExpressionIndex expressionIndex) {
			final int size = node.size();
			SopremoRecord.this.output.writeInt(size);
			for (int index = 0; index < size; index++) {
				final ExpressionIndex subIndex;
				if (expressionIndex != null) {
					subIndex = this.getSubIndex(expressionIndex, size, index);
					if (subIndex != null && subIndex.getExpression() != null)
						SopremoRecord.this.offsets[subIndex.getKeyIndex()] = SopremoRecord.this.position();
				} else
					subIndex = null;
				SopremoRecord.this.writeRecursivelyToBuffer(node.get(index), subIndex);
			}
		}

		private ExpressionIndex getSubIndex(final ExpressionIndex expressionIndex, final int size, final int index) {
			final ExpressionIndex subIndex = expressionIndex.get(index);
			if (subIndex != null)
				return subIndex;
			return expressionIndex.get(index - size);
		}
	}

	private class PrimitiveSerializer implements NodeSerializer<IJsonNode>, NodeDeserializer<IJsonNode> {
		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.serialization.SopremoRecord.NodeSerializer#write(eu.stratosphere.sopremo.type.IJsonNode
		 * , eu.stratosphere.sopremo.serialization.ExpressionIndex)
		 */
		@Override
		public void write(final IJsonNode node, final ExpressionIndex expressionIndex) {
			SopremoRecord.this.kryo.writeObject(SopremoRecord.this.output, node);
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.serialization.SopremoRecord.NodeSerializer#read(eu.stratosphere.sopremo.type.IJsonNode
		 * , com.esotericsoftware.kryo.Registration)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public IJsonNode read(final IJsonNode target, final Registration registration) {
			final Serializer<IJsonNode> serializer = registration.getSerializer();
			if (target != null && serializer instanceof ReusingSerializer<?> &&
				registration.getType() == target.getClass())
				return ((ReusingSerializer<IJsonNode>) serializer).read(SopremoRecord.this.kryo,
					SopremoRecord.this.input, target, registration.getType());
			return serializer.read(SopremoRecord.this.kryo, SopremoRecord.this.input, registration.getType());
		}
	}

	void read(final DataInputView in, final SopremoRecordLayout layout) throws IOException {
		this.node = null;
		final int numKeys = layout.getNumKeys();
		if (numKeys != this.offsets.length)
			this.offsets = new int[numKeys];
		for (int index = 0; index < numKeys; index++) {
			this.offsets[index] = in.readInt();
			if (SopremoUtil.DEBUG && this.offsets[index] == 0)
				throw new IllegalStateException("Attempt to read zero offset");
		}

		final int size = in.readInt();
		if (SopremoUtil.DEBUG && size <= 0)
			throw new IllegalStateException("Attempt to read zero length binary representation");
		this.binaryRepresentation.size(size);
		in.readFully(this.binaryRepresentation.elements(), 0, size);
	}

	//
	// private IJsonNode getKey(EvaluationExpression expression, IJsonNode target) {
	// if (this.node == null) {
	// int offset = getKeyOffset(this.layout.getKeyIndex(expression));
	// if (offset < 0)
	// return MissingNode.getInstance();
	// return getValueAtOffset(offset, target);
	// }
	// return expression.evaluate(this.node);
	// }

	//
	// public IJsonNode getKey(EvaluationExpression expression, NodeCache nodeCache) {
	// if (this.node == null) {
	// int offset = getKeyOffset(this.layout.getKeyIndex(expression));
	// if (offset == MISSING)
	// return MissingNode.getInstance();
	// return getValueAtOffset(offset, nodeCache);
	// }
	// return expression.evaluate(this.node);
	// }
	//
	// public IJsonNode getKey(int expressionIndex, IJsonNode target) {
	// if (this.node == null) {
	// int offset = getKeyOffset(expressionIndex);
	// if (offset == MISSING)
	// return MissingNode.getInstance();
	// return getValueAtOffset(offset, target);
	// }
	// return this.layout.getExpression(expressionIndex).evaluate(this.node);
	// }

	public IJsonNode getKey(final int expressionIndex, final NodeCache nodeCache) {
		final int offset = this.getKeyOffset(expressionIndex);
		if (offset == MISSING)
			return MissingNode.getInstance();
		return this.getValueAtOffset(offset, nodeCache);
	}

	private int getKeyOffset(final int expressionIndex) {
		if (expressionIndex == SopremoRecordLayout.VALUE_INDEX)
			return 0;
		return this.offsets[expressionIndex];
	}

	public IJsonNode getValueAtOffset(final int offset, final NodeCache nodeCache) {
		if (offset == 0)
			return this.getOrParseNode();
		this.input.setBuffer(this.binaryRepresentation.elements(), offset, this.binaryRepresentation.size());
		return this.readRecursively(nodeCache);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getOrParseNode().hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final SopremoRecord other = (SopremoRecord) obj;
		return this.getOrParseNode().equals(other.getOrParseNode());
	}

	public static class SopremoRecordKryoSerializer<Node extends IJsonNode> extends ReusingSerializer<SopremoRecord> {
		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#write(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Output, java.lang.Object)
		 */
		@Override
		public void write(final Kryo kryo, final Output output, final SopremoRecord object) {
			if (object.binaryRepresentation.isEmpty()) {
				object.writeRecursivelyToBuffer(object.node, SopremoRecordLayout.EMPTY.getExpressionIndex());
				object.output.flush();
			}
			output.writeInt(object.binaryRepresentation.size(), true);
			output.writeBytes(object.binaryRepresentation.elements(), 0, object.binaryRepresentation.size());
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.type.ReusingSerializer#read(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Input, java.lang.Object, java.lang.Class)
		 */
		@Override
		public SopremoRecord read(final Kryo kryo, final Input input, final SopremoRecord oldInstance,
				final Class<SopremoRecord> type) {
			oldInstance.binaryRepresentation.clear();
			final int size = input.readInt(true);
			oldInstance.binaryRepresentation.size(size);
			input.read(oldInstance.binaryRepresentation.elements(), 0, size);
			return oldInstance;
		}

		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#copy(com.esotericsoftware.kryo.Kryo, java.lang.Object)
		 */
		@Override
		public SopremoRecord copy(final Kryo kryo, final SopremoRecord original) {
			final SopremoRecord copy = new SopremoRecord();
			copy.node = original.node;
			copy.binaryRepresentation.addElements(0, original.binaryRepresentation.elements(), 0,
				original.binaryRepresentation.size());
			return copy;
		}

		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#read(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Input, java.lang.Class)
		 */
		@Override
		public SopremoRecord read(final Kryo kryo, final Input input, final Class<SopremoRecord> type) {
			return this.read(kryo, input, new SopremoRecord(), type);
		}
	}

	/**
	 * @param to
	 */
	public void copyTo(final SopremoRecord to) {
		if (this.binaryRepresentation.size() > 0) {
			to.binaryRepresentation.clear();
			to.binaryRepresentation.addElements(0, this.binaryRepresentation.elements(), 0,
				this.binaryRepresentation.size());
			to.offsets = this.offsets.clone();
		} else
			to.binaryRepresentation.clear();
		to.node = this.node.clone();
	}
	//
	// IJsonNode getKey(EvaluationExpression expression) {
	// return getKey(expression, (IJsonNode) null);
	// }

}
