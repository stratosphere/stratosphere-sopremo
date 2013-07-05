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

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Ignore;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.common.type.PactRecord;
import eu.stratosphere.pact.generic.io.FormatUtil;
import eu.stratosphere.pact.testing.AssertUtil;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.io.SopremoFileFormat.SopremoOutputFormat;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.serialization.Schema;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Base class for testing output formats.
 */
@Ignore
public class OutputFormatTest {

	public static void writeToFile(final File file, final SopremoFileFormat format, final Schema schema,
			IJsonNode... values)
			throws IOException {
		Configuration config = new Configuration();
		final EvaluationContext context = new EvaluationContext();
		context.setSchema(schema);
		context.setInputsAndOutputs(1, 0);
		SopremoUtil.setObject(config, SopremoUtil.CONTEXT, context);
		SopremoUtil.transferFieldsToConfiguration(format, SopremoFileFormat.class, config,
			format.getOutputFormat(), SopremoOutputFormat.class);
		final SopremoOutputFormat outputFormat =
			FormatUtil.openOutput(format.getOutputFormat(), file.toURI().toString(), config);

		for (IJsonNode value : values) {
			final PactRecord record = new PactRecord();
			schema.jsonToRecord(value, record);
			outputFormat.writeRecord(record);
		}
		outputFormat.close();
	}

	/**
	 * @param file
	 * @param format
	 * @param schema2
	 * @param values
	 */
	public static void writeAndRead(SopremoFileFormat format, Schema schema, IJsonNode... values) throws IOException {

		final File file = File.createTempFile("csvTest.csv", null);
		file.delete();

		writeToFile(file, format, schema, values);
		final Collection<IJsonNode> readValues = InputFormatTest.readFromFile(file, format, schema);

		file.delete();

		AssertUtil.assertIteratorEquals(Arrays.asList(values).iterator(), readValues.iterator());
	}
}
