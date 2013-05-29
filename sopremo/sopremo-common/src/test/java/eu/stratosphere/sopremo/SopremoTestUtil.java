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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import junit.framework.Assert;
import eu.stratosphere.sopremo.operator.Operator;
import eu.stratosphere.sopremo.operator.SopremoPlan;
//import eu.stratosphere.sopremo.testing.SopremoTestPlan;

/**
 * @author Arvid Heise
 */
public class SopremoTestUtil {

	/**
	 * Initializes SopremoTestUtil.
	 */
	public SopremoTestUtil() {
		super();
	}

	public static void assertPlanEquals(SopremoPlan expectedPlan, SopremoPlan actualPlan) {
		final List<Operator<?>> unmatchingOperators = expectedPlan.getUnmatchingOperators(actualPlan);

		if (!unmatchingOperators.isEmpty())
			Assert.failNotEquals(String.format("At least two operators do not match "), unmatchingOperators.get(0),
				unmatchingOperators.get(1));
	}

	public static String createTemporaryFile(String prefix) {
		try {
			final File tempFile = File.createTempFile(prefix, ".json");
			tempFile.deleteOnExit();
			return tempFile.toURI().toString();
		} catch (final IOException e) {
			throw new IllegalStateException("Cannot create temporary file", e);
		}
	}

	// TODO mleich: re-enable tests!
//	public static String getResourcePath(final String resource) {
//		try {
//			final Enumeration<URL> resources = SopremoTestPlan.class.getClassLoader().getResources(resource);
//			if (resources.hasMoreElements())
//				return resources.nextElement().toString();
//		} catch (final IOException e) {
//			throw new IllegalStateException(e);
//		}
//		throw new IllegalArgumentException("no resources found");
//	}

}