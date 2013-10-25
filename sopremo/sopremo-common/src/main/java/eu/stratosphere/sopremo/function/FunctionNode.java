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
package eu.stratosphere.sopremo.function;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.serializers.FieldSerializer;

import eu.stratosphere.sopremo.type.AbstractJsonNode;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * @author Arvid Heise
 */
//@DefaultSerializer(FunctionNode.FunctionNodeSerializer.class)
public class FunctionNode extends AbstractJsonNode {
	private SopremoFunction function;

	/**
	 * Initializes FunctionNode.
	 */
	public FunctionNode() {
	}

	public FunctionNode(SopremoFunction function) {
		this.function = function;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		appendable.append('&');
		this.function.appendAsString(appendable);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IJsonNode#clear()
	 */
	@Override
	public void clear() {
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#compareToSameType(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public int compareToSameType(IJsonNode other) {
		return this.function.getName().compareTo(((FunctionNode) other).function.getName());
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.IJsonNode#copyValueFrom(eu.stratosphere.sopremo.type.IJsonNode)
	 */
	@Override
	public void copyValueFrom(IJsonNode otherNode) {
		this.function = ((FunctionNode) otherNode).function.clone();
	}

	/**
	 * Returns the function.
	 * 
	 * @return the function
	 */
	public SopremoFunction getFunction() {
		return this.function;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.type.AbstractJsonNode#getType()
	 */
	@Override
	public Class<FunctionNode> getType() {
		return FunctionNode.class;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.function.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		FunctionNode other = (FunctionNode) obj;
		return this.function.equals(other.function);
	}

	/**
	 * Sets the function to the specified value.
	 * 
	 * @param function
	 *        the function to set
	 */
	public void setFunction(SopremoFunction function) {
		if (function == null)
			throw new NullPointerException("function must not be null");

		this.function = function;
	}

	public static class FunctionNodeSerializer extends Serializer<FunctionNode>{
		FieldSerializer<FunctionNode> fieldSerializer;
		
		public FunctionNodeSerializer(Kryo kryo, Class<FunctionNode> type) {
			fieldSerializer = new FieldSerializer<FunctionNode>(kryo, type);
		}
		
		@Override
		public void write(Kryo kryo, com.esotericsoftware.kryo.io.Output output, FunctionNode object) {
			fieldSerializer.write(kryo, output, object);
		}

		@Override
		public FunctionNode read(Kryo kryo, Input input, Class<FunctionNode> type) {
			FunctionNode object =  fieldSerializer.read(kryo, input, type);
			return object;
		}
		@Override
		public FunctionNode copy (Kryo kryo, FunctionNode original) {
			FunctionNode copy = this.fieldSerializer.copy(kryo, original);
			return copy;
		}
	}
}
