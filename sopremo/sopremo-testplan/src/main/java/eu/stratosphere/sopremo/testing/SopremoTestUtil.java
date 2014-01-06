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
package eu.stratosphere.sopremo.testing;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import org.junit.Assert;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import eu.stratosphere.sopremo.operator.Operator;
import eu.stratosphere.sopremo.operator.SopremoPlan;

/**
 */
public class SopremoTestUtil {

	/**
	 * Initializes EqualCloneTestUtil.
	 */
	public SopremoTestUtil() {
		super();
	}

	public static void assertPlanEquals(final SopremoPlan expectedPlan, SopremoPlan actualPlan) {
		// actualPlan may contain operators from loaded packages with their own classloaders
		// thus, these operators are not equal to the expected plan as the operators are loaded by the default
		// classloader
		// one solution is to use the serializer/deserializer trick to transfer the classes to the default classloader
		final Kryo kryo = expectedPlan.getCompilationContext().getKryo();
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final Output output = new Output(baos);
		kryo.writeObject(output, actualPlan);
		output.close();
		actualPlan = kryo.readObject(new Input(baos.toByteArray()), SopremoPlan.class);

		final List<Operator<?>> unmatchingOperators = actualPlan.getUnmatchingOperators(expectedPlan);
		if (!unmatchingOperators.isEmpty())
			if (unmatchingOperators.get(0).getClass() == unmatchingOperators.get(1).getClass())
				Assert.fail("operators are different\nexpected: " + unmatchingOperators.get(1) + "\nbut was: " +
					unmatchingOperators.get(0));
			else
				Assert.fail("plans are different\nexpected: " + expectedPlan + "\nbut was: " + actualPlan);
	}

	public static String createTemporaryFile(final String prefix) {
		try {
			final File tempFile = File.createTempFile(prefix, ".json");
			tempFile.deleteOnExit();
			return tempFile.toURI().toString();
		} catch (final IOException e) {
			throw new IllegalStateException("Cannot create temporary file", e);
		}
	}

	public static String getResourcePath(final String resource) {
		try {
			final Enumeration<URL> resources = SopremoTestUtil.class.getClassLoader().getResources(resource);
			if (resources.hasMoreElements())
				return resources.nextElement().toString();
		} catch (final IOException e) {
			throw new IllegalStateException(e);
		}
		throw new IllegalArgumentException("no resources found");
	}

}