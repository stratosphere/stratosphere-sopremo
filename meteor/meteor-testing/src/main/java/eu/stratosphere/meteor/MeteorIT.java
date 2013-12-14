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
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import eu.stratosphere.sopremo.client.DefaultClient;
import eu.stratosphere.sopremo.client.ProgressListener;
import eu.stratosphere.sopremo.execution.ExecutionResponse.ExecutionState;
import eu.stratosphere.sopremo.io.JsonParseException;
import eu.stratosphere.sopremo.io.JsonParser;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.server.SopremoTestServer;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * @author Arvid Heise
 */
public abstract class MeteorIT extends MeteorParseTest {

	protected SopremoTestServer testServer;

	protected DefaultClient client;

	protected File inputDir;

	protected void execute(final SopremoPlan plan) {
		final String[] messageHolder = new String[1];

		this.client.submit(plan, new ProgressListener() {
			@Override
			public void progressUpdate(final ExecutionState state, final String detail) {
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

	//
	// @Override
	// protected void initParser(QueryParser queryParser) {
	// // queryParser.setInputDirectory(new File("target"));
	// }

	@After
	public void teardown() throws Exception {
		this.client.close();
		this.testServer.close();
	}

	protected IJsonNode[] getContentsToCheckFrom(final String fileName) throws JsonParseException,
			FileNotFoundException {
		final JsonParser parser = new JsonParser(new FileReader(fileName));
		parser.setWrappingArraySkipping(false);
		final IJsonNode expectedValues = parser.readValueAsTree();

		final IJsonNode[] content = new IJsonNode[((IArrayNode) expectedValues).size()];
		for (int i = 0; i < content.length; i++)
			content[i] = ((IArrayNode) expectedValues).get(i);
		return content;
	}
}
