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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import javolution.util.FastList;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import eu.stratosphere.core.memory.DataInputView;
import eu.stratosphere.core.memory.DataOutputView;
import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.ISopremoType;
import eu.stratosphere.sopremo.cache.NodeCache;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.packages.DefaultTypeRegistry;
import eu.stratosphere.sopremo.packages.ITypeRegistry;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.BooleanNode;
import eu.stratosphere.sopremo.type.CachingArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.sopremo.type.NullNode;
import eu.stratosphere.sopremo.type.ObjectNode;
import eu.stratosphere.sopremo.type.ReusingSerializer;
import eu.stratosphere.sopremo.type.TextNode;
import eu.stratosphere.sopremo.type.TypeCoercer;

/**
 */
@DefaultSerializer(value = SopremoRecord.SopremoRecordKryoSerializer.class)
public class SopremoRecord extends AbstractSopremoType implements ISopremoType {

	/**
	 * 
	 */
	private static final int MISSING = -1;

	private final ByteArrayList binaryRepresentation = new ByteArrayList();

	private final transient Input input = new Input();

	private final transient Output output = new Output(new OutputStream() {
		@Override
		public void write(final byte[] b) throws IOException {
			SopremoRecord.this.binaryRepresentation.addElements(SopremoRecord.this.binaryRepresentation.size(), b);
		};

		@Override
		public void write(final byte[] b, final int off, final int len) throws IOException {
			SopremoRecord.this.binaryRepresentation.addElements(SopremoRecord.this.binaryRepresentation.size(), b, off,
				len);
		};

		@Override
		public void write(final int b) throws IOException {
			SopremoRecord.this.binaryRepresentation.add((byte) b);
		}
	});

	private IJsonNode node;

	private transient int offsets[];

	private transient final FastList<ExpressionIndex> currentExpressionIndex = new FastList<ExpressionIndex>();

	private transient SopremoRecordLayout layout;

	private transient DataKryo kryo;

	public SopremoRecord(SopremoRecordLayout layout, ITypeRegistry registry) {
		init(layout, registry);
	}

	void init(SopremoRecordLayout layout, ITypeRegistry registry) {
		this.layout = layout;
		this.offsets = new int[layout.getNumKeys()];

		this.kryo = new DataKryo();
		this.kryo.setReferences(false);
		for (final Class<? extends IJsonNode> type : TypeCoercer.NUMERIC_TYPES)
			this.kryo.register(type);
		final List<Class<? extends Cloneable>> defaultTypes =
			Arrays.asList(BooleanNode.class, TextNode.class, NullNode.class, ObjectNode.class, CachingArrayNode.class,
				MissingNode.class, TreeMap.class, ArrayList.class);
		for (final Class<?> type : defaultTypes)
			this.kryo.register(type);
		this.kryo.getRegistration(ObjectNode.class).setSerializer(new ObjectSerializer());
		this.kryo.getRegistration(CachingArrayNode.class).setSerializer(new CachingArraySerializer());
		this.kryo.registerAlias(IObjectNode.class, ObjectNode.class);
		this.kryo.registerAlias(IArrayNode.class, CachingArrayNode.class);
		this.kryo.registerAlias(ArrayNode.class, CachingArrayNode.class);
		this.kryo.registerAlias(BooleanNode.UnmodifiableBoolean.class, BooleanNode.class);

		final List<Class<? extends IJsonNode>> types = registry.getTypes();
		for (final Class<? extends IJsonNode> type : types)
			this.kryo.register(type);
	}

	/**
	 * Initializes SopremoRecord.
	 */
	public SopremoRecord() {
		this(SopremoRecordLayout.create(), new DefaultTypeRegistry());
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

	public IJsonNode getKey(final int expressionIndex, final NodeCache nodeCache) {
		final int offset = this.getKeyOffset(expressionIndex);
		if (offset == MISSING)
			return MissingNode.getInstance();
		return this.getValueAtOffset(offset, nodeCache);
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

	public IJsonNode getValueAtOffset(final int offset, final NodeCache nodeCache) {
		if (offset == 0)
			return this.getOrParseNode();
		this.input.setBuffer(this.binaryRepresentation.elements(), offset, this.binaryRepresentation.size());
		return (IJsonNode) this.kryo.readClassAndObject(this.input);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getOrParseNode().hashCode();
		return result;
	}

	public IJsonNode parseNode() {
		this.input.setBuffer(this.binaryRepresentation.elements(), 0, this.binaryRepresentation.size());
		return this.node = (IJsonNode) this.kryo.readClassAndObject(this.input);
	}

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

	void read(final DataInputView in) throws IOException {
		this.node = null;
		for (int index = 0; index < this.offsets.length; index++) {
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

	void write(final DataOutputView out) throws IOException {
		if (this.node != null) {
			this.binaryRepresentation.clear();
			Arrays.fill(this.offsets, MISSING);
			this.currentExpressionIndex.addLast(this.layout.getExpressionIndex());
			this.kryo.writeClassAndObject(this.output, this.node);
			this.currentExpressionIndex.removeLast();

			final EvaluationExpression[] calculatedKeyExpressions = this.layout.getCalculatedKeyExpressions();
			if (calculatedKeyExpressions.length > 0) {
				this.currentExpressionIndex.addLast(null);
				for (int index = 0; index < calculatedKeyExpressions.length; index++) {
					this.offsets[index + this.layout.getNumDirectDataKeys()] = this.position();
					final IJsonNode calculatedValue = calculatedKeyExpressions[index].evaluate(this.node);
					this.kryo.writeClassAndObject(this.output, calculatedValue);
				}
				this.currentExpressionIndex.removeLast();
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

	private int getKeyOffset(final int expressionIndex) {
		if (expressionIndex == SopremoRecordLayout.VALUE_INDEX)
			return 0;
		return this.offsets[expressionIndex];
	}

	private int position() {
		return this.binaryRepresentation.size() + this.output.position();
	}

	public static class SopremoRecordKryoSerializer<Node extends IJsonNode> extends ReusingSerializer<SopremoRecord> {
		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#copy(com.esotericsoftware.kryo.Kryo, java.lang.Object)
		 */
		@Override
		public SopremoRecord copy(final Kryo kryo, final SopremoRecord original) {
			final SopremoRecord copy = new SopremoRecord();
			copy.node = original.node.clone();
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

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.type.ReusingSerializer#read(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Input, java.lang.Object, java.lang.Class)
		 */
		@Override
		public SopremoRecord read(final Kryo kryo, final Input input, final SopremoRecord oldInstance,
				final Class<SopremoRecord> type) {
			oldInstance.binaryRepresentation.clear();
			int size = input.readInt(true);
			oldInstance.binaryRepresentation.size(size);
			input.read(oldInstance.binaryRepresentation.elements(), 0, size);
			return oldInstance;
		}

		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#write(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Output, java.lang.Object)
		 */
		@Override
		public void write(final Kryo kryo, final Output output, final SopremoRecord object) {
			if (object.binaryRepresentation.isEmpty()) {
				object.currentExpressionIndex.addLast(null);
				object.kryo.writeClassAndObject(object.output, object.node);
				object.currentExpressionIndex.removeLast();
				object.output.flush();
			}
			output.writeInt(object.binaryRepresentation.size(), true);
			output.writeBytes(object.binaryRepresentation.elements(), 0, object.binaryRepresentation.size());
		}
	}

	private class CachingArraySerializer extends CachingArrayNode.ArraySerializer {
		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.type.AbstractArrayNode.ArraySerializer#write(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Output, eu.stratosphere.sopremo.type.ArrayNode)
		 */
		@Override
		public void write(Kryo kryo, Output output, ArrayNode<IJsonNode> array) {
			ExpressionIndex expressionIndex = SopremoRecord.this.currentExpressionIndex.getLast();
			if (expressionIndex == null) {
				super.write(kryo, output, array);
				return;
			}

			final int size = array.size();
			output.writeInt(size);

			for (int index = 0; index < size; index++) {
				final ExpressionIndex subIndex = expressionIndex.subIndex(index);
				final int keyIndex;
				if (subIndex != null && (keyIndex = subIndex.getKeyIndex()) != -1)
					SopremoRecord.this.offsets[keyIndex] = SopremoRecord.this.position();
				SopremoRecord.this.currentExpressionIndex.addLast(subIndex);
				kryo.writeClassAndObject(output, array.get(index));
				SopremoRecord.this.currentExpressionIndex.removeLast();
			}
		}
	}

	private class ObjectSerializer extends ObjectNode.ObjectSerializer {
		@Override
		public void write(Kryo kryo, Output output, IObjectNode object) {
			ExpressionIndex expressionIndex = SopremoRecord.this.currentExpressionIndex.getLast();
			if (expressionIndex == null) {
				super.write(kryo, output, object);
				return;
			}

			output.writeInt(object.size());

			for (final Entry<String, IJsonNode> entry : object) {
				final String fieldName = entry.getKey();
				output.writeString(fieldName);
				final ExpressionIndex subIndex = expressionIndex.subIndex(fieldName);
				final int keyIndex;
				if (subIndex != null && (keyIndex = subIndex.getKeyIndex()) != -1)
					SopremoRecord.this.offsets[keyIndex] = SopremoRecord.this.position();
				SopremoRecord.this.currentExpressionIndex.addLast(subIndex);
				kryo.writeClassAndObject(output, entry.getValue());
				SopremoRecord.this.currentExpressionIndex.removeLast();
			}
		}
	}
}
