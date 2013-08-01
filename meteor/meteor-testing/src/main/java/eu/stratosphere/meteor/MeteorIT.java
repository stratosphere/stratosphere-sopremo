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

import org.junit.After;
import org.junit.Before;

import eu.stratosphere.sopremo.client.DefaultClient;
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

	/**
	 * Initializes DefaultClientIT.
	 */
	public MeteorIT() {
		projectName = MavenUtil.getProjectName();
		init();
	}

	@Before
	public final void setup() throws Exception {
		this.testServer = new SopremoTestServer(true);
		this.inputDir = this.testServer.createDir("input");

		this.client = new DefaultClient();
		this.client.setServerAddress(this.testServer.getServerAddress());
		this.client.setUpdateTime(100);
	}

	private void init() {

		try {
			String projectPath = (new File(".")).getCanonicalPath();
			 projectJar = MavenUtil.buildJarForProject(projectPath, projectName+"_testing");
			projectJar.deleteOnExit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void initParser(QueryParser queryParser) {
		//queryParser.setInputDirectory(new File("target"));
		queryParser.getPackageManager().importPackageFrom(projectName.substring("sopremo-".length()), projectJar);
	}

	protected abstract SopremoPlan getPlan() throws IOException;

	@After
	public void teardown() throws Exception {
		this.client.close();
		this.testServer.close();
	}
}
