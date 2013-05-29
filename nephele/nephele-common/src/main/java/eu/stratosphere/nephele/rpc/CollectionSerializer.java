/***********************************************************************************************************************
 *
 * Copyright (C) 2010-2013 by the Stratosphere project (http://stratosphere.eu)
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

package eu.stratosphere.nephele.rpc;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * This class implements a {@link Serializer} for the {@link Collection} objects. The implementation extends the
 * built-in kryo collection serializer such that certain collections which do not have default constructor can also be
 * serialized.
 * <p>
 * This class is not thread-safe.
 * 
 * @author warneke
 */
@SuppressWarnings("rawtypes")
final class CollectionSerializer extends Serializer<Collection> {

	/**
	 * The built-in kryo serializer for collections with non-arg constructors.
	 */
	private final com.esotericsoftware.kryo.serializers.CollectionSerializer defaultSerializer;

	/**
	 * Initializes CollectionSerializer.
	 */
	CollectionSerializer() {
		this.defaultSerializer = new com.esotericsoftware.kryo.serializers.CollectionSerializer();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final Kryo kryo, final Output output, final Collection object) {

		if (hasNonArgConstructor(object.getClass())) {
			this.defaultSerializer.write(kryo, output, object);
		} else {
			@SuppressWarnings("unchecked")
			final ArrayList<Object> al = new ArrayList<Object>(object);
			kryo.writeObject(output, al, this.defaultSerializer);
		}
	}

	/**
	 * Checks if the given class has a default constructor (i.e. a constructor with no arguments).
	 * 
	 * @param class1
	 *        the class to be checked
	 * @return <code>true</code> if the given class has a default constructor, <code>false</code> otherwise
	 */
	@SuppressWarnings("unchecked")
	private static boolean hasNonArgConstructor(final Class<? extends Collection> class1) {

		try {
			final Constructor<Collection> constructor = (Constructor<Collection>) class1.getConstructor();

			if (constructor != null) {
				return true;
			}

		} catch (Exception e) {
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection read(final Kryo kryo, final Input input, final Class<Collection> type) {

		if (hasNonArgConstructor(type)) {
			return this.defaultSerializer.read(kryo, input, type);
		}

		final ArrayList al = kryo.readObject(input, ArrayList.class, this.defaultSerializer);

		try {
			Constructor<Collection> constructor = type.getDeclaredConstructor(List.class);
			constructor.setAccessible(true);
			return constructor.newInstance(al);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
