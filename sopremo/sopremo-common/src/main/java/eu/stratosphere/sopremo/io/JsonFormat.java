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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import eu.stratosphere.api.common.io.statistics.BaseStatistics;
import eu.stratosphere.core.fs.BlockLocation;
import eu.stratosphere.core.fs.FSDataInputStream;
import eu.stratosphere.core.fs.FSDataOutputStream;
import eu.stratosphere.core.fs.FileInputSplit;
import eu.stratosphere.core.fs.FileStatus;
import eu.stratosphere.core.fs.FileSystem;
import eu.stratosphere.core.fs.Path;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Format for reading and writing json files. The structure of the file naturally translates into the Sopremo data model
 * as it is based on json.<br />
 * Splits are assumed to be an array of json values. Each value is successively returned by the input iterator.
 */
@Name(noun = "json")
public class JsonFormat extends SopremoFormat {

	public static class JsonInputFormat extends SopremoFileInputFormat {

		/**
		 * 
		 */
		private static final long serialVersionUID = -642104267156446471L;

		private JsonParser parser;

		@Override
		public void close() throws IOException {
			super.close();
			this.parser.close();
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.io.SopremoFormat.SopremoFileInputFormat#open(eu.stratosphere.core.fs.FSDataInputStream
		 * , eu.stratosphere.core.fs.FileInputSplit)
		 */
		@Override
		protected void open(final FSDataInputStream stream, final FileInputSplit split) {
			// we currently have no method to handle multiple splits
			if (split.getStart() != 0) {
				this.endReached();
				return;
			}

			try {
				this.parser = new JsonParser(new InputStreamReader(stream, this.getEncoding()));
				this.parser.setWrappingArraySkipping(true);

				if (this.parser.checkEnd())
					this.endReached();
			} catch (final UnsupportedEncodingException e) {
				// cannot happen as encoding is validated in SopremoFormat
			}
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.pact.SopremoInputFormat#nextValue()
		 */
		@Override
		public IJsonNode nextValue() throws IOException {
			final IJsonNode value = this.parser.readValueAsTree();
			if (this.parser.checkEnd())
				this.endReached();
			return value;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.api.io .FileInputFormat#createInputSplits(int)
		 */
		@Override
		public FileInputSplit[] createInputSplits(final int minNumSplits) throws IOException {
			final Path path = this.filePath;
			final FileSystem fs = path.getFileSystem();
			final FileStatus pathFile = fs.getFileStatus(path);

			if (pathFile.isDir()) {
				// input is directory. list all contained files
				final FileStatus[] files = fs.listStatus(path);
				final FileInputSplit[] splits = new FileInputSplit[files.length];

				for (int index = 0; index < splits.length; index++) {
					final FileStatus fileStatus = files[index];
					final long len = fileStatus.getLen();
					final BlockLocation[] blocks = fs.getFileBlockLocations(fileStatus, 0, len);
					splits[index] = new FileInputSplit(index, fileStatus.getPath(), 0, len, this.getHosts(blocks));
				}

				return splits;
			}

			final BlockLocation[] blocks = fs.getFileBlockLocations(pathFile, 0, pathFile.getLen());
			return new FileInputSplit[] { new FileInputSplit(0, pathFile.getPath(), 0, pathFile.getLen(),
				this.getHosts(blocks)) };
		}

		protected String[] getHosts(final BlockLocation[] blocks) throws IOException {
			return blocks.length > 0 ? blocks[0].getHosts() : new String[0];
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.api.io .InputFormat#getStatistics()
		 */
		@Override
		public FileBaseStatistics getStatistics(final BaseStatistics cachedStatistics) {
			return null;
		}
	}

	/**
	 * Writes json files with {@link JsonGenerator}.
	 * 
	 */
	public static class JsonOutputFormat extends SopremoFileOutputFormat {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1775609830466072732L;

		private JsonGenerator generator;

		@Override
		public void close() throws IOException {
			this.generator.writeEndArray();
			this.generator.close();
			super.close();
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.io.SopremoFormat.SopremoFileOutputFormat#open(eu.stratosphere.core.fs.
		 * FSDataOutputStream, int)
		 */
		@Override
		protected void open(final FSDataOutputStream stream, final int taskNumber) throws IOException {
			this.generator = new JsonGenerator(new OutputStreamWriter(stream, this.getEncoding()));
			this.generator.writeStartArray();
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.io.SopremoFormat.SopremoFileOutputFormat#writeValue(eu.stratosphere.sopremo.type.
		 * IJsonNode)
		 */
		@Override
		public void writeValue(final IJsonNode value) throws IOException {
			this.generator.writeTree(value);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.io.SopremoFormat#getPreferredFilenameExtensions()
	 */
	@Override
	protected String[] getPreferredFilenameExtensions() {
		return new String[] { "json" };
	}
}
