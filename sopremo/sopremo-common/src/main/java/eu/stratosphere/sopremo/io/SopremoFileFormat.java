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
import java.nio.charset.Charset;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.nephele.fs.FSDataInputStream;
import eu.stratosphere.nephele.fs.FSDataOutputStream;
import eu.stratosphere.nephele.fs.FileInputSplit;
import eu.stratosphere.nephele.fs.Path;
import eu.stratosphere.pact.generic.io.FileInputFormat;
import eu.stratosphere.pact.generic.io.FileOutputFormat;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.Schema;
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
 */
public abstract class SopremoFileFormat extends ConfigurableSopremoType {
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
	public SopremoFileFormat withEncoding(String encoding) {
		this.setEncoding(encoding);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.AbstractSopremoType#clone()
	 */
	@Override
	public SopremoFileFormat clone() {
		return (SopremoFileFormat) super.clone();
	}

	/**
	 * Returns the encoding.
	 * 
	 * @return the encoding
	 */
	public String getEncoding() {
		return this.encoding;
	}

	public boolean canHandleFormat(Path path) {
		final String preferredFilenameExtension = this.getPreferredFilenameExtension();
		if (preferredFilenameExtension == null)
			return false;
		return path.toString().endsWith("." + preferredFilenameExtension);
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
		SopremoFileFormat other = (SopremoFileFormat) obj;
		return this.encoding.equals(other.encoding);
	}

	protected abstract String getPreferredFilenameExtension();

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
	public Class<? extends SopremoInputFormat> getInputFormat() {
		return null;
	}

	/**
	 * Returns the implementation of the output format or null if this is a read-only format.
	 * 
	 * @return the implementation class
	 */
	public Class<? extends SopremoOutputFormat> getOutputFormat() {
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

	public static abstract class SopremoOutputFormat extends FileOutputFormat<SopremoRecord> {

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

			SopremoEnvironment.getInstance().setClassLoader(getClass().getClassLoader());
			this.context = SopremoUtil.getEvaluationContext(parameters);
			this.layout = SopremoUtil.getLayout(parameters);
			SopremoEnvironment.getInstance().setEvaluationContext(this.context);
			SopremoUtil.configureWithTransferredState(this, SopremoInputFormat.class, parameters);
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

		/**
		 * @param value
		 */
		protected abstract void writeValue(IJsonNode value) throws IOException;

	}

	public static abstract class SopremoInputFormat extends FileInputFormat<SopremoRecord> {

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

		/**
		 * @param stream
		 */
		protected abstract void open(FSDataInputStream stream, FileInputSplit split) throws IOException;

		@Override
		public void configure(final Configuration parameters) {
			super.configure(parameters);

			SopremoEnvironment.getInstance().setClassLoader(getClass().getClassLoader());
			this.context = SopremoUtil.getEvaluationContext(parameters);
			this.layout = SopremoUtil.getLayout(parameters);
			SopremoEnvironment.getInstance().setEvaluationContext(this.context);
			SopremoUtil.configureWithTransferredState(this, SopremoInputFormat.class, parameters);
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

		/**
		 * @return
		 */
		protected void endReached() {
			this.end = true;
		}

		/**
		 * @return
		 */
		protected abstract IJsonNode nextValue() throws IOException;

		@Override
		public boolean reachedEnd() {
			return this.end;
		}

	}
}
