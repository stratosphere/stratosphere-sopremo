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
package eu.stratosphere.sopremo.io;

import java.io.IOException;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.nephele.fs.FSDataInputStream;
import eu.stratosphere.nephele.fs.FileInputSplit;
import eu.stratosphere.nephele.fs.Path;
import eu.stratosphere.pact.common.io.statistics.BaseStatistics;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.util.reflect.ReflectUtil;

/**
 * @author Arvid Heise
 */
public class SampleFormat extends SopremoFileFormat {
	private SopremoFileFormat originalFormat = new JsonFormat();

	public static final long DEFAULT_SAMPLE_SIZE = 10;

	private long sampleSize = DEFAULT_SAMPLE_SIZE;

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.io.SopremoFileFormat#canHandleFormat(eu.stratosphere.nephele.fs.FileSystem,
	 * eu.stratosphere.nephele.fs.Path)
	 */
	@Override
	public boolean canHandleFormat(Path path) {
		return this.originalFormat.canHandleFormat(path);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.io.SopremoFileFormat#getPreferredFilenameExtension()
	 */
	@Override
	protected String getPreferredFilenameExtension() {
		return this.originalFormat.getPreferredFilenameExtension();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.io.SopremoFileFormat#getInputFormat()
	 */
	@Override
	public Class<? extends SopremoInputFormat> getInputFormat() {
		return SampleInputFormat.class;
	}

	/**
	 * Sets the sampleSize to the specified value.
	 * 
	 * @param sampleSize
	 *        the sampleSize to set
	 */
	@Property
	public void setSampleSize(long sampleSize) {
		this.sampleSize = sampleSize;
	}

	/**
	 * Returns the sampleSize.
	 * 
	 * @return the sampleSize
	 */
	public long getSampleSize() {
		return this.sampleSize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.originalFormat.hashCode();
		result = prime * result + (int) (this.sampleSize ^ this.sampleSize >>> 32);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		SampleFormat other = (SampleFormat) obj;
		return this.sampleSize == other.sampleSize && this.originalFormat.equals(other.originalFormat);
	}

	public static class SampleInputFormat extends SopremoInputFormat {

		private SopremoFileFormat originalFormat;

		private SopremoInputFormat originalInputFormat;

		private long currentSample, sampleSize;

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.pact.common.io.FileInputFormat#configure(eu.stratosphere.nephele.configuration.Configuration)
		 */
		@Override
		public void configure(Configuration parameters) {
			super.configure(parameters);

			this.originalInputFormat = ReflectUtil.newInstance(this.originalFormat.getInputFormat());
			this.currentSample = 0;
			Configuration originalConfiguration = new Configuration();
			SopremoUtil.transferFieldsToConfiguration(this.originalFormat, SopremoFileFormat.class,
				originalConfiguration, this.originalFormat.getInputFormat(), SopremoInputFormat.class);
			this.originalInputFormat.configure(originalConfiguration);
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.pact.common.io.FileInputFormat#open(eu.stratosphere.nephele.fs.FileInputSplit)
		 */
		@Override
		public void open(FileInputSplit split) throws IOException {
			super.open(split);
			this.originalInputFormat.open(split);
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.pact.common.generic.io.InputFormat#getStatistics(eu.stratosphere.pact.common.io.statistics.
		 * BaseStatistics)
		 */
		@Override
		public FileBaseStatistics getStatistics(BaseStatistics cachedStatistics) throws IOException {
			final BaseStatistics statistics = this.originalInputFormat.getStatistics(cachedStatistics);

			FileBaseStatistics stats = null;

			if (cachedStatistics != null && statistics instanceof FileBaseStatistics)
				stats = (FileBaseStatistics) statistics;
			else
				stats = new FileBaseStatistics(-1, statistics.getTotalInputSize(), statistics.getAverageRecordWidth());

			long sampleSize = Math.min(this.sampleSize, stats.getNumberOfRecords());
			stats.setTotalInputSize((long) Math.ceil(sampleSize * stats.getAverageRecordWidth()));

			return stats;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.io.SopremoFileFormat.SopremoInputFormat#open(eu.stratosphere.nephele.fs.FSDataInputStream
		 * , eu.stratosphere.nephele.fs.FileInputSplit)
		 */
		@Override
		protected void open(FSDataInputStream stream, FileInputSplit split) throws IOException {
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.io.SopremoFileFormat.SopremoInputFormat#nextValue()
		 */
		@Override
		protected IJsonNode nextValue() throws IOException {
			this.currentSample++;
			final IJsonNode value = this.originalInputFormat.nextValue();
			if (this.currentSample >= this.sampleSize || this.originalInputFormat.reachedEnd()) {
				this.endReached();
				return null;
			}
			return value;
		}

	}
}
