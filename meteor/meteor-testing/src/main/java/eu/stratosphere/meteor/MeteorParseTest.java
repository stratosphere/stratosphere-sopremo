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
package eu.stratosphere.meteor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;

import eu.stratosphere.sopremo.operator.Operator;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.query.QueryParserException;

/**
 * @author Arvid Heise
 */
@Ignore
public class MeteorParseTest {
	public SopremoPlan parseScript(final String script) {
		// printBeamerSlide(script);
		SopremoPlan plan = null;
		try {
			final QueryParser queryParser = new QueryParser().withInputDirectory(new File("."));
			initParser(queryParser);
			plan = queryParser.tryParse(script);
		} catch (final QueryParserException e) {
			final AssertionError error = new AssertionError(String.format("could not parse script: %s", e.getMessage()));
			error.initCause(e);
			throw error;
		}

		Assert.assertNotNull("could not parse script", plan);

		// System.out.println(plan);
		return plan;
	}

	public SopremoPlan parseScript(final File script) {
		// printBeamerSlide(script);
		SopremoPlan plan = null;
		try {
			final QueryParser queryParser = new QueryParser().withInputDirectory(script.getParentFile());
			initParser(queryParser);
			plan = queryParser.tryParse(loadScriptFromFile(script));
		} catch (final QueryParserException e) {
			final AssertionError error = new AssertionError(String.format("could not parse script: %s", e.getMessage()));
			error.initCause(e);
			throw error;
		}

		Assert.assertNotNull("could not parse script", plan);

		// System.out.println(plan);
		return plan;
	}

	private String loadScriptFromFile(File scriptFile) {
		try {
			final BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
			StringBuilder builder = new StringBuilder();
			int ch;
			while ((ch = reader.read()) != -1)
				builder.append((char) ch);
			reader.close();
			return builder.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@SuppressWarnings("unused")
	protected void initParser(QueryParser queryParser) {

	}

	public static void assertPlanEquals(final SopremoPlan expectedPlan, final SopremoPlan actualPlan) {
		final List<Operator<?>> unmatchingOperators = actualPlan.getUnmatchingOperators(expectedPlan);
		if (!unmatchingOperators.isEmpty())
			if (unmatchingOperators.get(0).getClass() == unmatchingOperators.get(1).getClass())
				Assert.fail("operators are different\nexpected: " + unmatchingOperators.get(1) + "\nbut was: " + unmatchingOperators.get(0));
			else
				Assert.fail("plans are different\nexpected: " + expectedPlan + "\nbut was: " + actualPlan);
	}
}
