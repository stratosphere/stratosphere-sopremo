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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;

import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.query.QueryParserException;
import eu.stratosphere.sopremo.testing.SopremoTestUtil;

/**
 */
@Ignore
public class MeteorParseTest {

	private final String projectName;

	private File projectJar;

	private final static Map<String, File> ProjectJars = new HashMap<String, File>();

	/**
	 * Initializes DefaultClientIT.
	 */
	public MeteorParseTest() {
		this.projectName = MavenUtil.getProjectName();
		this.projectJar = ProjectJars.get(this.projectName);
		if (this.projectJar == null)
			ProjectJars.put(this.projectName, this.projectJar = build(this.projectName));
	}
	
	public File getResource(final String name) {
		try {
			return new File(MeteorParseTest.class.getClassLoader().getResources(name)
				.nextElement().getFile());
		} catch (IOException e) {
			throw newAssertionError("Could not locate resource " + name, e);
		}
	}

	private AssertionError newAssertionError(String message, Throwable cause) {
		final AssertionError assertionError = new AssertionError(message);
		assertionError.initCause(cause);
		return assertionError;
	}

	public SopremoPlan parseScript(final String script) {
		try {
			final QueryParser queryParser = new QueryParser().withInputDirectory(new File("."));
			this.initParser(queryParser);
			return queryParser.tryParse(script);
		} catch (final QueryParserException e) {
			throw newAssertionError("could not parse script", e);
		}
	}

	protected void initParser(final QueryParser queryParser) {
		queryParser.getPackageManager().importPackageFrom(this.projectName.substring("sopremo-".length()),
			this.projectJar);
	}

	public SopremoPlan parseScript(final File script) {
		try {
			final QueryParser queryParser = new QueryParser().withInputDirectory(new File("."));
			this.initParser(queryParser);
			return queryParser.tryParse(script);
		} catch (final Exception e) {
			throw newAssertionError("could not parse script", e);
		}
	}

	public static void assertPlanEquals(final SopremoPlan expectedPlan, final SopremoPlan actualPlan) {
		SopremoTestUtil.assertPlanEquals(expectedPlan, actualPlan);
	}

	private static File build(final String projectName) {
		try {
			final String projectPath = new File(".").getCanonicalPath();
			final File projectJar = MavenUtil.buildJarForProject(projectPath, projectName + "_testing");
			projectJar.deleteOnExit();
			return projectJar;
		} catch (final IOException e) {
			Assert.fail(e.getMessage());
			return null;
		}
	}
}
