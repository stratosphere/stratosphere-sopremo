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
package eu.stratosphere.sopremo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import com.esotericsoftware.kryo.Kryo;

import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.BooleanNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.sopremo.type.NullNode;
import eu.stratosphere.sopremo.type.ObjectNode;
import eu.stratosphere.sopremo.type.TextNode;
import eu.stratosphere.sopremo.type.TypeCoercer;

/**
 * @author arv
 */
public class KryoFactory {
	public static Kryo getKryo() {
		final Kryo kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setAutoReset(true);
		for (Class<? extends IJsonNode> type : TypeCoercer.NUMERIC_TYPES)
			kryo.register(type);
		@SuppressWarnings("unchecked")
		List<Class<? extends Object>> defaultTypes =
			Arrays.asList(BooleanNode.class, TextNode.class, ArrayNode.class, ObjectNode.class, NullNode.class,
				MissingNode.class, TreeMap.class, ArrayList.class, BigInteger.class, BigDecimal.class);
		for (Class<?> type : defaultTypes)
			kryo.register(type);

		return kryo;
	}
}
