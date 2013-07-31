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
package eu.stratosphere.sopremo.io;

import org.junit.Test;

/**
 * 
 */
public class JsonParserFailureTest {
	@Test(expected = JsonParseException.class)
	public void shouldFailOnMissingComma() throws JsonParseException {

		final JsonParser parser = new JsonParser("[{id:1},{id:2}{id:3},{id:4}]");

		for (int index = 0; index < 3; index++) 
			parser.readValueAsTree();
	}
}
