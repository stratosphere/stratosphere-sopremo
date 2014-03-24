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
package eu.stratosphere.meteor.base;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import eu.stratosphere.meteor.MeteorIT;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.type.JsonUtil;

/**
 * 
 */
public class SortTestIT extends MeteorIT {
	@Test
	public void test() throws IOException {
		File output = this.testServer.getOutputFile("output.json");
		
		SopremoUtil.trace();
		final SopremoPlan plan =
			parseScript("$data = [{name:'test1', k:7},{name:'test2', k:3}, {name:'test3', k:6}, {name:'test4', k:1}, {name:'test5', k:4}, {name:'test6', k:5}, {name:'test7', k:2}];"
				+
				"$sorted = sort $data on $data.k order 'ASCENDING';" +
				"write $sorted to '" + output.toURI() + "';");
		

		Assert.assertNotNull(this.client.submit(plan, null, true));

		this.testServer.checkOrderedContentsOf("output.json",
			JsonUtil.createObjectNode("name", "test4", "k", 1),
			JsonUtil.createObjectNode("name", "test7", "k", 2),
			JsonUtil.createObjectNode("name", "test2", "k", 3),
			JsonUtil.createObjectNode("name", "test5", "k", 4),
			JsonUtil.createObjectNode("name", "test6", "k", 5),
			JsonUtil.createObjectNode("name", "test3", "k", 6),
			JsonUtil.createObjectNode("name", "test1", "k", 7));
	}
}
