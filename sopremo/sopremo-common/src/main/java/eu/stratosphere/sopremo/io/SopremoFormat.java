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
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.google.common.reflect.TypeToken;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.nephele.fs.FSDataInputStream;
import eu.stratosphere.nephele.fs.FSDataOutputStream;
import eu.stratosphere.nephele.fs.FileInputSplit;
import eu.stratosphere.nephele.fs.FileStatus;
import eu.stratosphere.nephele.fs.FileSystem;
import eu.stratosphere.nephele.fs.Path;
import eu.stratosphere.nephele.template.InputSplit;
import eu.stratosphere.pact.common.io.statistics.BaseStatistics;
import eu.stratosphere.pact.generic.io.FileInputFormat;
import eu.stratosphere.pact.generic.io.FileOutputFormat;
import eu.stratosphere.pact.generic.io.InputFormat;
import eu.stratosphere.pact.generic.io.OutputFormat;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.SopremoEnvironment;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.operator.ConfigurableSopremoType;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.serialization.SopremoRecord;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Base class for all file or stream formats. A format can be read-only or write-only and has a number of configuration
 * parameters that are exposed through the {@link ConfigurableSopremoType} mechanism. <br />
 * To implement a custom format, this base class should be subclassed and an input and/or output format must be provided
 * either by:
 * <ul>
 * <li>Having an inner class extending the format.</li>
 * <li>Overwriting {@link #getInputFormat()}, {@link #getOutputFormat()}.</li>
 * </ul>
 * For ease of development, {@link SopremoFileInputFormat} and {@link SopremoFileOutputFormat} may be used as a starting
 * point.
 */
public abstract class SopremoFormat extends ConfigurableSopremoType {
	private String encoding = "utf-8";

	private EvaluationExpression projection = EvaluationExpression.VALUE;

	/**
	 * Sets the encoding to the specified value.
	 * 
	 * @param encoding
	 *        the encoding to set
	 */
	@Property
	public void setEncoding(String encoding) {
		if (encoding == null)
			throw new NullPointerException("encoding must not be null");

		// validate and standardize encoding
		this.encoding = Charset.forName(encoding).name();
	}

	/**
	 * Sets the encoding to the specified value.
	 * 
	 * @param encoding
	 *        the encoding to set
	 */
	public SopremoFormat withEncoding(String encoding) {
		this.setEncoding(encoding);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#clone()
	 */
	@Override
	public SopremoFormat clone() {
		return (SopremoFormat) super.clone();
	}

	/**
	 * Returns the encoding.
	 * 
	 * @return the encoding
	 */
	public String getEncoding() {
		return this.encoding;
	}

	/**
	 * Checks if the path specifies a file and whether the ending corresponds to one entry of
	 * {@link #getPreferredFilenameExtensions()}.
	 */
	public boolean canHandleFormat(URI uri) {
		final String[] preferredFilenameExtensions = this.getPreferredFilenameExtensions();
		if (preferredFilenameExtensions.length == 0)
			return false;

		final String uriPath = uri.toString();
		if (uriPath == null)
			return false;
		final int separator = uriPath.lastIndexOf(".");
		if (separator == -1)
			return false;

		String ending = uriPath.substring(separator + 1);
		for (String extension : preferredFilenameExtensions)
			if (ending.equalsIgnoreCase(extension))
				return true;
		return false;
	}

	// protected void configure(final Configuration parameters) {
	//
	// }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.encoding.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		SopremoFormat other = (SopremoFormat) obj;
		return this.encoding.equals(other.encoding);
	}

	protected String[] getPreferredFilenameExtensions() {
		return new String[0];
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		appendable.append(this.getClass().getSimpleName());
	}

	/**
	 * Returns the implementation of the input format or null if this is a write-only format.
	 * 
	 * @return the implementation class
	 */
	@SuppressWarnings("unchecked")
	public Class<? extends SopremoInputFormat<?>> getInputFormat() {
		for (final Class<?> formatClass : this.getClass().getDeclaredClasses())
			if ((formatClass.getModifiers() & Modifier.STATIC) != 0
				&& InputFormat.class.isAssignableFrom(formatClass)) {
				final TypeToken<?> typeToken =
					TypeToken.of((Class<InputFormat<?, ?>>) formatClass).getSupertype(InputFormat.class);
				if (((ParameterizedType) typeToken.getType()).getActualTypeArguments()[0] != SopremoRecord.class)
					throw new IllegalStateException("Found input format but does not process " +
						SopremoRecord.class.getSimpleName());
				return (Class<? extends SopremoInputFormat<?>>) formatClass;
			}
		return null;
	}

	/**
	 * Returns the implementation of the output format or null if this is a read-only format.
	 * 
	 * @return the implementation class
	 */
	@SuppressWarnings("unchecked")
	public Class<? extends SopremoOutputFormat> getOutputFormat() {
		for (final Class<?> formatClass : this.getClass().getDeclaredClasses())
			if ((formatClass.getModifiers() & Modifier.STATIC) != 0
				&& OutputFormat.class.isAssignableFrom(formatClass)) {
				final TypeToken<?> typeToken =
					TypeToken.of((Class<OutputFormat<?>>) formatClass).getSupertype(OutputFormat.class);
				if (((ParameterizedType) typeToken.getType()).getActualTypeArguments()[0] != SopremoRecord.class)
					throw new IllegalStateException("Found output format but does not process " +
						SopremoRecord.class.getSimpleName());
				return (Class<? extends SopremoOutputFormat>) formatClass;
			}
		return null;
	}

	/**
	 * Sets the projection to the specified value.
	 * 
	 * @param projection
	 *        the projection to set
	 */
	@Property
	@Name(preposition = "into")
	public void setProjection(EvaluationExpression projection) {
		if (projection == null)
			throw new NullPointerException("projection must not be null");

		this.projection = projection;
	}

	/**
	 * Returns the projection.
	 * 
	 * @return the projection
	 */
	public EvaluationExpression getProjection() {
		return this.projection;
	}

	/**
	 * Base interface for Sopremo input formats.
	 */
	public static interface SopremoOutputFormat extends OutputFormat<SopremoRecord> {
		abstract void writeValue(IJsonNode value) throws IOException;
	}

	public static abstract class SopremoFileOutputFormat extends FileOutputFormat<SopremoRecord> implements
			SopremoOutputFormat {

		private SopremoRecordLayout layout;

		private EvaluationContext context;

		private String encoding;

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.pact.common.io.FileOutputFormat#configure(eu.stratosphere.nephele.configuration.Configuration)
		 */
		@Override
		public void configure(final Configuration parameters) {
			super.configure(parameters);

			SopremoEnvironment.getInstance().setConfiguration(parameters);
			this.context = SopremoEnvironment.getInstance().getEvaluationContext();
			this.layout = SopremoEnvironment.getInstance().getLayout();
			SopremoUtil.configureWithTransferredState(this, SopremoFileInputFormat.class, parameters);
			if (this.layout == null)
				throw new IllegalStateException("Could not deserialize input schema");
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.pact.common.io.FileOutputFormat#open(int)
		 */
		@Override
		public void open(int taskNumber) throws IOException {
			super.open(taskNumber);

			this.open(this.stream, taskNumber);
		}

		/**
		 * @param stream
		 * @param taskNumber
		 */
		protected abstract void open(FSDataOutputStream stream, int taskNumber) throws IOException;

		/**
		 * Returns the encoding.
		 * 
		 * @return the encoding
		 */
		protected String getEncoding() {
			return this.encoding;
		}

		/**
		 * Returns the context.
		 * 
		 * @return the context
		 */
		protected EvaluationContext getContext() {
			return this.context;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.pact.common.io.OutputFormat#writeRecord(eu.stratosphere.pact.common.type.PactRecord)
		 */
		@Override
		public void writeRecord(final SopremoRecord record) throws IOException {
			final IJsonNode value = record.getNode();
			if (SopremoUtil.DEBUG && SopremoUtil.LOG.isTraceEnabled())
				SopremoUtil.LOG.trace(String.format("%s output %s", this.context.getOperatorDescription(), value));
			this.writeValue(value);
		}
	}

	/**
	 * Base interface for Sopremo input formats.
	 */
	public static interface SopremoInputFormat<T extends InputSplit> extends InputFormat<SopremoRecord, T> {
		abstract IJsonNode nextValue() throws IOException;
	}

	/**
	 * Base class for generic input formats.
	 */
	public static abstract class AbstractSopremoInputFormat<T extends InputSplit> implements
			SopremoInputFormat<T> {

		private boolean end;

		private EvaluationContext context;

		private String encoding;

		private EvaluationExpression projection;

		private SopremoRecordLayout layout;

		/**
		 * Returns the context.
		 * 
		 * @return the context
		 */
		protected EvaluationContext getContext() {
			return this.context;
		}

		/**
		 * Returns the encoding.
		 * 
		 * @return the encoding
		 */
		protected String getEncoding() {
			return this.encoding;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.pact.common.io.FileInputFormat#open(eu.stratosphere.nephele.fs.FileInputSplit)
		 */
		@Override
		public void open(T split) throws IOException {
			this.end = false;
		}

		@Override
		public void configure(final Configuration parameters) {
			SopremoEnvironment.getInstance().setConfiguration(parameters);
			this.context = SopremoEnvironment.getInstance().getEvaluationContext();
			this.layout = SopremoEnvironment.getInstance().getLayout();
			SopremoUtil.configureWithTransferredState(this, SopremoFileInputFormat.class, parameters);
			if (this.layout == null)
				throw new IllegalStateException("Could not deserialize layout");
		}

		protected String getDefaultEncoding() {
			return "utf-8";
		}

		@Override
		public boolean nextRecord(final SopremoRecord record) throws IOException {
			if (!this.end) {
				final IJsonNode value = this.nextValue();
				if (SopremoUtil.DEBUG && SopremoUtil.LOG.isTraceEnabled())
					SopremoUtil.LOG.trace(String.format("%s input %s", this.context.getOperatorDescription(), value));
				record.setNode(this.projection.evaluate(value));
				return true;
			}

			return false;
		}

		protected void endReached() {
			this.end = true;
		}

		@Override
		public boolean reachedEnd() throws IOException {
			return this.end;
		}
	}

	/**
	 * Base class for file-based input formats.
	 */
	public static abstract class SopremoFileInputFormat extends FileInputFormat<SopremoRecord> implements
			SopremoInputFormat<FileInputSplit> {

		private boolean end;

		private EvaluationContext context;

		private String encoding;

		private EvaluationExpression projection;

		private SopremoRecordLayout layout;

		/**
		 * Returns the context.
		 * 
		 * @return the context
		 */
		protected EvaluationContext getContext() {
			return this.context;
		}

		/**
		 * Returns the encoding.
		 * 
		 * @return the encoding
		 */
		protected String getEncoding() {
			return this.encoding;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.pact.common.io.FileInputFormat#open(eu.stratosphere.nephele.fs.FileInputSplit)
		 */
		@Override
		public void open(FileInputSplit split) throws IOException {
			super.open(split);
			this.end = false;

			this.open(this.stream, split);
		}

		protected abstract void open(FSDataInputStream stream, FileInputSplit split) throws IOException;

		@Override
		public FileBaseStatistics getStatistics(BaseStatistics cachedStatistics) throws IOException {
			final ArrayList<FileStatus> files = getFileStati();

			long latestModTime = files.get(0).getModificationTime();
			for (int index = 1; index < files.size(); index++)
				latestModTime = Math.max(files.get(index).getModificationTime(), latestModTime);

			if (cachedStatistics != null && cachedStatistics instanceof FileBaseStatistics) {
				FileBaseStatistics fileStatistics = (FileBaseStatistics) cachedStatistics;

				// check whether the cached statistics are still valid, if we have any
				if (latestModTime <= fileStatistics.getLastModificationTime()) {
					return fileStatistics;
				}
			}

			// calculate the whole length
			long len = 0;
			for (FileStatus s : files) {
				len += s.getLen();
			}

			return new FileBaseStatistics(latestModTime, len,
				getAverageRecordBytes(FileSystem.get(this.filePath.toUri()), files, len));
		}

		protected float getAverageRecordBytes(FileSystem fileSystem, ArrayList<FileStatus> files, long fileSize)
				throws IOException {
			return BaseStatistics.AVG_RECORD_BYTES_UNKNOWN;
		}

		protected ArrayList<FileStatus> getFileStati() throws IOException {
			final Path filePath = this.filePath;

			// get the filesystem
			final FileSystem fs = FileSystem.get(filePath.toUri());

			// get the file info and check whether the cached statistics are still valid.
			final FileStatus file = fs.getFileStatus(filePath);

			final ArrayList<FileStatus> files = new ArrayList<FileStatus>(1);

			// enumerate all files and check their modification time stamp.
			if (file.isDir()) {
				FileStatus[] fss = fs.listStatus(filePath);
				files.ensureCapacity(fss.length);

				for (FileStatus s : fss) {
					if (!s.isDir()) {
						files.add(s);
					}
				}
			} else {
				files.add(file);
			}
			return files;
		}

		@Override
		public void configure(final Configuration parameters) {
			super.configure(parameters);

			SopremoEnvironment.getInstance().setConfiguration(parameters);
			this.context = SopremoEnvironment.getInstance().getEvaluationContext();
			this.layout = SopremoEnvironment.getInstance().getLayout();
			SopremoUtil.configureWithTransferredState(this, SopremoFileInputFormat.class, parameters);
			if (this.layout == null)
				throw new IllegalStateException("Could not deserialize layout");
		}

		protected String getDefaultEncoding() {
			return "utf-8";
		}

		@Override
		public boolean nextRecord(final SopremoRecord record) throws IOException {
			if (!this.end) {
				final IJsonNode value = this.nextValue();
				if (value != null) {
					if (SopremoUtil.DEBUG && SopremoUtil.LOG.isTraceEnabled())
						SopremoUtil.LOG.trace(String.format("%s input %s", this.context.getOperatorDescription(), value));
					record.setNode(this.projection.evaluate(value));
					return true;
				}
			}

			return false;
		}

		protected void endReached() {
			this.end = true;
		}

		@Override
		public boolean reachedEnd() {
			return this.end;
		}
	}

	public void configureForOutput(Configuration configuration, String outputPath) {
		final Class<? extends SopremoOutputFormat> outputFormat = getOutputFormat();
		if (outputPath != null)
			configuration.setString(FileOutputFormat.FILE_PARAMETER_KEY, outputPath);
		else if (FileOutputFormat.class.isAssignableFrom(outputFormat))
			throw new IllegalStateException("No input path was given for the file input format");

		SopremoUtil.transferFieldsToConfiguration(this, SopremoFormat.class, configuration,
			outputFormat, OutputFormat.class);
	}

	public void configureForInput(Configuration configuration, String inputPath) {
		final Class<? extends SopremoInputFormat<?>> inputFormat = getInputFormat();
		if (inputPath != null)
			configuration.setString(FileInputFormat.FILE_PARAMETER_KEY, inputPath);
		else if (FileInputFormat.class.isAssignableFrom(inputFormat))
			throw new IllegalStateException("No input path was given for the file input format");

		SopremoUtil.transferFieldsToConfiguration(this, SopremoFormat.class, configuration,
			inputFormat, InputFormat.class);

	}
}
