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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import eu.stratosphere.sopremo.client.DefaultClient;
import eu.stratosphere.sopremo.client.ProgressListener;
import eu.stratosphere.sopremo.execution.ExecutionResponse.ExecutionState;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.server.SopremoTestServer;

/**
 * @author Arvid Heise
 */
public abstract class MeteorIT extends MeteorParseTest {

	protected SopremoTestServer testServer;

	protected DefaultClient client;

	protected File inputDir;

	private String projectName;

	private File projectJar;

	private final static Map<String, File> ProjectJars = new HashMap<String, File>();

	/**
	 * Initializes DefaultClientIT.
	 */
	public MeteorIT() {
		this.projectName = MavenUtil.getProjectName();
		this.projectJar = ProjectJars.get(this.projectName);
		if (this.projectJar == null)
			ProjectJars.put(this.projectName, this.projectJar = build(this.projectName));
	}

	protected void execute(SopremoPlan plan) {
		final String[] messageHolder = new String[1];

		this.client.submit(plan, new ProgressListener() {
			@Override
			public void progressUpdate(ExecutionState state, String detail) {
				if (state == ExecutionState.ERROR)
					messageHolder[0] = detail;
			}
		}, true);
		if (messageHolder[0] != null)
			Assert.fail(messageHolder[0]);
	}

	@Before
	public final void setup() throws Exception {
		this.testServer = new SopremoTestServer(true);
		this.inputDir = this.testServer.createDir("input");

		this.client = new DefaultClient();
		this.client.setServerAddress(this.testServer.getServerAddress());
		this.client.setUpdateTime(100);
	}

	private static File build(String projectName) {
		try {
			String projectPath = (new File(".")).getCanonicalPath();
			File projectJar = MavenUtil.buildJarForProject(projectPath, projectName + "_testing");
			projectJar.deleteOnExit();
			return projectJar;
		} catch (IOException e) {
			Assert.fail(e.getMessage());
			return null;
		}
	}

	@Override
	protected void initParser(QueryParser queryParser) {
		// queryParser.setInputDirectory(new File("target"));
		queryParser.getPackageManager().importPackageFrom(this.projectName.substring("sopremo-".length()),
			this.projectJar);
	}

	@After
	public void teardown() throws Exception {
		this.client.close();
		this.testServer.close();
	}
}
