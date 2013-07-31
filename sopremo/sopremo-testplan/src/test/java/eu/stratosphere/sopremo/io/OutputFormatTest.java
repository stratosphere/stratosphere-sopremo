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
import eu.stratosphere.pact.generic.io.FormatUtil;
import eu.stratosphere.pact.generic.io.OutputFormat;
import eu.stratosphere.pact.testing.AssertUtil;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.io.SopremoFormat.SopremoFileOutputFormat;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Base class for testing output formats.
 */
@Ignore
public class OutputFormatTest {
	public static final SopremoRecordLayout NULL_LAYOUT = SopremoRecordLayout.create();

	public static void writeToFile(final File file, final SopremoFormat format, final SopremoRecordLayout layout,
			IJsonNode... values)
			throws IOException {
		Configuration config = new Configuration();
		final EvaluationContext context = new EvaluationContext();
		SopremoUtil.setEvaluationContext(config, context);
		SopremoUtil.setLayout(config, layout);
		SopremoUtil.transferFieldsToConfiguration(format, SopremoFormat.class, config,
			format.getOutputFormat(), OutputFormat.class);
		@SuppressWarnings("unchecked")
		final SopremoFileOutputFormat outputFormat =
			FormatUtil.openOutput((Class<? extends SopremoFileOutputFormat>) format.getOutputFormat(),
				file.toURI().toString(), config);

		for (IJsonNode value : values) {
			final SopremoRecord record = new SopremoRecord(layout);
			record.setNode(value);
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
	public static void writeAndRead(SopremoFormat format, SopremoRecordLayout layout, IJsonNode... values)
			throws IOException {

		final File file = File.createTempFile("csvTest.csv", null);
		file.delete();

		writeToFile(file, format, layout, values);
		final Collection<IJsonNode> readValues = InputFormatTest.readFromFile(file, format, layout);

		file.delete();

		AssertUtil.assertIteratorEquals(Arrays.asList(values).iterator(), readValues.iterator());
	}
}
