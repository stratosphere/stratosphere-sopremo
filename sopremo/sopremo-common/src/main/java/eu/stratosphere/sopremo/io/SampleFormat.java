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
import java.net.URI;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.nephele.template.InputSplit;
import eu.stratosphere.pact.common.io.statistics.BaseStatistics;
import eu.stratosphere.pact.generic.io.InputFormat;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.util.reflect.ReflectUtil;

/**
 * @author Arvid Heise
 */
public class SampleFormat extends SopremoFormat {
	private final SopremoFormat originalFormat = new JsonFormat();

	public static final long DEFAULT_SAMPLE_SIZE = 10;

	private long sampleSize = DEFAULT_SAMPLE_SIZE;

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.io.SopremoFormat#canHandleFormat(eu.stratosphere.nephele.fs.FileSystem,
	 * eu.stratosphere.nephele.fs.Path)
	 */
	@Override
	public boolean canHandleFormat(final URI path) {
		return this.originalFormat.canHandleFormat(path);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.io.SopremoFormat#getPreferredFilenameExtensions()
	 */
	@Override
	protected String[] getPreferredFilenameExtensions() {
		return this.originalFormat.getPreferredFilenameExtensions();
	}

	/**
	 * Sets the sampleSize to the specified value.
	 * 
	 * @param sampleSize
	 *        the sampleSize to set
	 */
	@Property
	public void setSampleSize(final long sampleSize) {
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
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final SampleFormat other = (SampleFormat) obj;
		return this.sampleSize == other.sampleSize && this.originalFormat.equals(other.originalFormat);
	}

	public static class SampleInputFormat extends AbstractSopremoInputFormat<InputSplit> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8534362304827555826L;

		private SopremoFormat originalFormat;

		private SopremoInputFormat<InputSplit> originalInputFormat;

		private long currentSample, sampleSize;

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.pact.common.io.FileInputFormat#configure(eu.stratosphere.nephele.configuration.Configuration)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void configure(final Configuration parameters) {
			super.configure(parameters);

			this.originalInputFormat =
				(SopremoInputFormat<InputSplit>) ReflectUtil.newInstance(this.originalFormat.getInputFormat());
			this.currentSample = 0;
			final Configuration originalConfiguration = new Configuration();
			SopremoUtil.transferFieldsToConfiguration(this.originalFormat, SopremoFormat.class,
				originalConfiguration, this.originalFormat.getInputFormat(), InputFormat.class);
			this.originalInputFormat.configure(originalConfiguration);
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.pact.generic.io.InputFormat#open(eu.stratosphere.nephele.template.InputSplit)
		 */
		@Override
		public void open(final InputSplit split) throws IOException {
			this.originalInputFormat.open(split);
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.pact.common.generic.io.InputFormat#getStatistics(eu.stratosphere.pact.common.io.statistics.
		 * BaseStatistics)
		 */
		@Override
		public BaseStatistics getStatistics(final BaseStatistics cachedStatistics) throws IOException {
			final BaseStatistics stats = this.originalInputFormat.getStatistics(cachedStatistics);

			return new BaseStatistics() {
				/*
				 * (non-Javadoc)
				 * @see eu.stratosphere.pact.common.io.statistics.BaseStatistics#getAverageRecordWidth()
				 */
				@Override
				public float getAverageRecordWidth() {
					return stats.getAverageRecordWidth();
				}

				/*
				 * (non-Javadoc)
				 * @see eu.stratosphere.pact.common.io.statistics.BaseStatistics#getNumberOfRecords()
				 */
				@Override
				public long getNumberOfRecords() {
					return Math.min(SampleInputFormat.this.sampleSize, stats.getNumberOfRecords());
				}

				/*
				 * (non-Javadoc)
				 * @see eu.stratosphere.pact.common.io.statistics.BaseStatistics#getTotalInputSize()
				 */
				@Override
				public long getTotalInputSize() {
					return (long) Math.ceil(this.getNumberOfRecords() * this.getAverageRecordWidth());
				}
			};
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.io.SopremoFormat.SopremoFileInputFormat#nextValue()
		 */
		@Override
		public IJsonNode nextValue() throws IOException {
			this.currentSample++;
			final IJsonNode value = this.originalInputFormat.nextValue();
			if (this.currentSample >= this.sampleSize || this.originalInputFormat.reachedEnd()) {
				this.endReached();
				return null;
			}
			return value;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.pact.generic.io.InputFormat#createInputSplits(int)
		 */
		@Override
		public InputSplit[] createInputSplits(final int minNumSplits) throws IOException {
			return this.originalInputFormat.createInputSplits(minNumSplits);
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.pact.generic.io.InputFormat#getInputSplitType()
		 */
		@Override
		public Class<? extends InputSplit> getInputSplitType() {
			return this.originalInputFormat.getInputSplitType();
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.pact.generic.io.InputFormat#close()
		 */
		@Override
		public void close() throws IOException {
			this.originalInputFormat.close();
		}

	}
}
