package eu.stratosphere.sopremo.io;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;

import eu.stratosphere.nephele.fs.FSDataInputStream;
import eu.stratosphere.nephele.fs.FSDataOutputStream;
import eu.stratosphere.nephele.fs.FileInputSplit;
import eu.stratosphere.nephele.fs.FileStatus;
import eu.stratosphere.nephele.fs.FileSystem;
import eu.stratosphere.nephele.fs.LineReader;
import eu.stratosphere.nephele.fs.Path;
import eu.stratosphere.pact.common.io.statistics.BaseStatistics;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.ObjectNode;
import eu.stratosphere.sopremo.type.TextNode;
import eu.stratosphere.sopremo.util.Equaler;

@Name(noun = { "csv", "tsv" })
public class CsvFormat extends SopremoFileFormat {

	/**
	 * The default number of sample lines to consider when calculating the line width.
	 */
	private static final int DEFAULT_NUM_SAMPLES = 10;

	private char fieldDelimiter = ',';

	private Boolean quotation;

	private String[] keyNames = new String[0];

	private int numLineSamples = DEFAULT_NUM_SAMPLES;

	private enum State {
		TOP_LEVEL, QUOTED, ESCAPED, UNICODE;
	}

	/**
	 * Sets the fieldDelimiter to the specified value.
	 * 
	 * @param fieldDelimiter
	 *        the fieldDelimiter to set
	 */
	@Property
	@Name(noun = "delimiter")
	public void setFieldDelimiter(String fieldDelimiter) {
		if (fieldDelimiter.length() != 1)
			throw new IllegalArgumentException("field delimiter needs to be exactly one character");
		this.fieldDelimiter = fieldDelimiter.charAt(0);
	}

	public CsvFormat withFieldDelimiter(String fieldDelimiter) {
		this.setFieldDelimiter(fieldDelimiter);
		return this;
	}

	/**
	 * Returns the fieldDelimiter.
	 * 
	 * @return the fieldDelimiter
	 */
	public String getFieldDelimiter() {
		return String.valueOf(this.fieldDelimiter);
	}

	/**
	 * Sets the keyNames to the specified value.
	 * 
	 * @param keyNames
	 *        the keyNames to set
	 */
	@Property
	@Name(noun = "columns")
	public void setKeyNames(String... keyNames) {
		if (keyNames == null)
			throw new NullPointerException("keyNames must not be null");

		this.keyNames = keyNames;
	}

	/**
	 * Sets the keyNames to the specified value.
	 * 
	 * @param keyNames
	 *        the keyNames to set
	 */
	public CsvFormat withKeyNames(String... keyNames) {
		this.setKeyNames(keyNames);
		return this;
	}

	/**
	 * Returns the keyNames.
	 * 
	 * @return the keyNames
	 */
	public String[] getKeyNames() {
		return this.keyNames;
	}

	/**
	 * Sets the quotation to the specified value.
	 * 
	 * @param quotation
	 *        the quotation to set
	 */
	@Property
	@Name(verb = "quote")
	public void setQuotation(Boolean quotation) {
		this.quotation = quotation;
	}

	/**
	 * Sets the quotation to the specified value.
	 * 
	 * @param quotation
	 *        the quotation to set
	 */
	public CsvFormat withQuotation(Boolean quotation) {
		this.setQuotation(quotation);
		return this;
	}

	/**
	 * Returns the quotation.
	 * 
	 * @return the quotation
	 */
	public Boolean getQuotation() {
		return this.quotation;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.io.SopremoFileFormat#getPreferredFilenameExtension()
	 */
	@Override
	protected String getPreferredFilenameExtension() {
		return "csv";
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.io.SopremoFileFormat#getInputFormat()
	 */
	@Override
	public Class<? extends SopremoInputFormat> getInputFormat() {
		return CsvInputFormat.class;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.io.SopremoFileFormat#getOutputFormat()
	 */
	@Override
	public Class<? extends SopremoOutputFormat> getOutputFormat() {
		return CsvOutputFormat.class;
	}

	/**
	 * Sets the numLineSamples to the specified value.
	 * 
	 * @param numLineSamples
	 *        the numLineSamples to set
	 */
	public void setNumLineSamples(int numLineSamples) {
		if (numLineSamples <= 0)
			throw new IllegalArgumentException("numLineSamples must be positive");

		this.numLineSamples = numLineSamples;
	}

	/**
	 * Returns the numLineSamples.
	 * 
	 * @return the numLineSamples
	 */
	public int getNumLineSamples() {
		return this.numLineSamples;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.fieldDelimiter;
		result = prime * result + Arrays.hashCode(this.keyNames);
		result = prime * result + this.numLineSamples;
		result = prime * result + (this.quotation == null ? 0 : this.quotation.hashCode());
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
		CsvFormat other = (CsvFormat) obj;
		return this.fieldDelimiter == other.fieldDelimiter
			&& this.numLineSamples == other.numLineSamples
			&& Equaler.SafeEquals.equal(this.quotation, other.quotation)
			&& Arrays.equals(this.keyNames, other.keyNames);
	}

	public static class CsvOutputFormat extends SopremoOutputFormat {
		private char fieldDelimiter;

		private String[] keyNames;

		private Boolean quotation;

		private Writer writer;

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.io.SopremoFileFormat.SopremoOutputFormat#open(eu.stratosphere.nephele.fs.
		 * FSDataOutputStream, int)
		 */
		@Override
		protected void open(FSDataOutputStream stream, int taskNumber) throws IOException {
			this.writer = new BufferedWriter(new OutputStreamWriter(stream, this.getEncoding()));
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.pact.common.io.FileOutputFormat#close()
		 */
		@Override
		public void close() throws IOException {
			this.writer.close();
			super.close();
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.io.SopremoFileFormat.SopremoOutputFormat#writeValue(eu.stratosphere.sopremo.type.
		 * IJsonNode)
		 */
		@Override
		protected void writeValue(IJsonNode value) throws IOException {
			if (this.keyNames.length == 0)
				if (value instanceof IArrayNode<?>) {
					this.writeArray(value);
					return;
				}
				else if (value instanceof IObjectNode) {
					this.write(value);
					this.writeLineTerminator();
					return;
				} else
					this.detectKeyNames(value);
			this.writeObject((IObjectNode) value);
		}

		/**
		 * @param value
		 */
		private void writeObject(IObjectNode value) throws IOException {
			this.write(value.get(this.keyNames[0]));
			for (int index = 1; index < this.keyNames.length; index++) {
				this.writeSeparator();
				this.write(value.get(this.keyNames[index]));
			}
			this.writeLineTerminator();
		}

		protected void detectKeyNames(IJsonNode value) {
			final List<Entry<String, IJsonNode>> values = Lists.newArrayList(((IObjectNode) value).iterator());
			this.keyNames = new String[values.size()];
			for (int index = 0; index < this.keyNames.length; index++)
				this.keyNames[index] = values.get(index).getKey();
			if (this.keyNames.length == 0)
				throw new IllegalStateException("Found empty object and cannot detect key names");
		}

		private void writeArray(IJsonNode value) throws IOException {
			IArrayNode<?> array = (IArrayNode<?>) value;
			if (!array.isEmpty()) {
				this.write(array.get(0));
				for (int index = 1; index < array.size(); index++) {
					this.writeSeparator();
					this.write(array.get(0));
				}
			}
			this.writeLineTerminator();
		}

		/**
		 * 
		 */
		private void writeLineTerminator() throws IOException {
			this.writer.write('\n');
		}

		/**
		 * 
		 */
		private void writeSeparator() throws IOException {
			this.writer.write(this.fieldDelimiter);
		}

		/**
		 * @param object
		 */
		private void write(IJsonNode node) throws IOException {
			String string = node.toString();
			if (this.quotation != Boolean.FALSE) {
				this.writer.write('"');
				this.writer.write(this.escapeString(string));
				this.writer.write('"');
			} else
				this.writer.write(string);
		}

		private transient IntList escapePositions = new IntArrayList();

		protected String escapeString(String string) {
			this.escapePositions.clear();
			for (int index = 0, count = string.length(); index < count; index++) {
				char ch = string.charAt(index);
				if (ch == '\"' || ch == '\\')
					this.escapePositions.add(index);
			}

			if (this.escapePositions.size() > 0) {
				final char[] source = string.toCharArray();
				final char[] result = new char[string.length() + this.escapePositions.size()];

				int srcPos = 0;
				for (int index = 0, size = this.escapePositions.size(); index < size; index++) {
					final int endPos = this.escapePositions.getInt(index);
					final int length = endPos - srcPos;
					final int targetPos = srcPos + index;
					System.arraycopy(source, srcPos, result, targetPos, length);
					srcPos = endPos;
					result[targetPos + length] = '\\';
				}
				System.arraycopy(source, srcPos, result, srcPos + this.escapePositions.size(), source.length - srcPos);
				string = new String(result);
			}
			return string;
		}

	}

	/**
	 * InputFormat that interpretes the input data as a csv representation.
	 */
	public static class CsvInputFormat extends SopremoInputFormat {
		/**
		 * The log.
		 */
		private static final Log LOG = LogFactory.getLog(CsvInputFormat.class);

		private char fieldDelimiter;

		private Boolean quotation;

		private boolean usesQuotation;

		private String[] keyNames;

		private int numLineSamples;

		private Deque<State> state = new LinkedList<State>();

		private CountingReader reader;

		// @Override
		// public void configure(final Configuration parameters) {
		// super.configure(parameters);
		//
		// this.context = (EvaluationContext) SopremoUtil.getObject(parameters,SopremoUtil.CONTEXT, null);
		// this.targetSchema = this.context.getOutputSchema(0);
		//
		// final Boolean useQuotation = (Boolean) SopremoUtil.getObject(parameters,USE_QUOTATION, null);
		// this.quotation = useQuotation == null ? Quotation.AUTO : useQuotation ? Quotation.ON : Quotation.OFF;
		// this.keyNames = (String[]) SopremoUtil.getObject(parameters,COLUMN_NAMES, null);
		// // this.targetSchema = SopremoUtil.deserialize(parameters, SCHEMA, Schema.class);
		// this.encoding = Charset.forName(parameters.getString(ENCODING, "utf-8"));
		// final Character delimiter = (Character) SopremoUtil.getObject(parameters,FIELD_DELIMITER, null);
		// this.fieldDelimiter = delimiter != null ? delimiter : DEFAULT_DELIMITER;
		//
		// this.numLineSamples = parameters.getInteger(NUM_STATISTICS_SAMPLES, DEFAULT_NUM_SAMPLES);
		// }

		@Override
		protected void open(FSDataInputStream stream, FileInputSplit split) throws IOException {
			this.setState(State.TOP_LEVEL);

			this.reader = new CountingReader(stream, this.getEncoding(), split.getStart(), split.getStart()
				+ split.getLength());
			this.usesQuotation = this.quotation == Boolean.TRUE;
			if (this.quotation == null) {
				// very simple heuristic
				for (int index = 0, ch; !this.usesQuotation && index < 1000 && (ch = this.reader.read()) != -1; index++)
					this.usesQuotation = ch == '"';

				this.reader.seek(this.splitStart);
			}

			if (this.keyNames.length == 0) {
				if (split.getSplitNumber() > 0)
					this.reader.seek(0);
				this.pos = 0;
				this.keyNames = this.extractKeyNames();
			}

			// skip to beginning of the first record
			if (this.splitStart > 0)
				if (this.usesQuotation) {
					// TODO: how to detect if where are inside a quotation?
					this.reader.seek(this.pos = this.splitStart - 1);

					int ch;
					for (; (ch = this.reader.read()) != -1 && ch != '\n'; this.pos++)
						;
					if (ch == -1)
						this.endReached();
				} else {
					this.reader.seek(this.pos = this.splitStart - 1);

					int ch;
					for (; (ch = this.reader.read()) != -1 && ch != '\n'; this.pos++)
						;
					if (ch == -1)
						this.endReached();
				}
		}

		/**
		 * Reads the key names from the first line of the first split.
		 */
		private String[] extractKeyNames() throws IOException {
			List<String> keyNames = new ArrayList<String>();
			int lastCharacter;
			do {
				lastCharacter = this.fillBuilderWithNextField();
				keyNames.add(this.builder.toString());
				this.builder.setLength(0);
			} while (lastCharacter != -1 && lastCharacter != '\n');

			return keyNames.toArray(new String[keyNames.size()]);
		}

		private final IObjectNode objectNode = new ObjectNode();

		private final StringBuilder builder = new StringBuilder();

		private char unicodeChar, unicodeCount;

		private long pos = 0;

		private int fillBuilderWithNextField() throws IOException {
			int character = 0;
			readLoop: for (; (character = this.reader.read()) != -1; this.pos++) {
				final char ch = (char) character;
				switch (this.getCurrentState()) {
				case TOP_LEVEL:
					if (ch == this.fieldDelimiter) {
						this.builder.toString();
						break readLoop;
					} else if (ch == '\n') {
						final int lastCharPos = this.builder.length() - 1;
						if (this.builder.charAt(lastCharPos) == '\r')
							this.builder.setLength(lastCharPos);
						break readLoop;
					} else if (this.usesQuotation && ch == '"')
						this.setState(State.QUOTED);
					else
						this.builder.append(ch);
					break;
				case ESCAPED:
					if (ch == 'u')
						this.setState(State.UNICODE);
					else {
						this.builder.append(ch);
						this.revertToPreviousState();
					}
					break;
				case QUOTED:
					switch (ch) {
					case '"':
						this.revertToPreviousState();
						break;
					case '\\':
						this.setState(State.ESCAPED);
						break;
					default:
						this.builder.append(ch);
					}
					break;
				case UNICODE:
					int digit = Character.digit(ch, 16);
					if (digit != -1)
						this.unicodeChar = (char) ((this.unicodeChar << 4) | digit);
					else
						throw new IOException("Cannot parse unicode character at position: " + this.pos +
							" split start: " + this.splitStart);
					if (++this.unicodeCount >= 4) {
						this.builder.append(this.unicodeChar);
						this.unicodeChar = 0;
						this.unicodeCount = 0;
						revertToPreviousState();
						revertToPreviousState();
					}
					break;
				}
			}
			return character;
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.io.SopremoFileFormat.SopremoInputFormat#nextValue()
		 */
		@Override
		protected IJsonNode nextValue() throws IOException {
			int lastCharacter, fieldIndex = 0;
			do {
				lastCharacter = this.fillBuilderWithNextField();
				// ignore empty line
				if (lastCharacter == 0 && fieldIndex == 0 && this.builder.length() == 0)
					break;
				this.addToObject(fieldIndex++, this.builder.toString());
				this.builder.setLength(0);
			} while (lastCharacter != -1 && lastCharacter != '\n');

			if (this.objectNode.size() == 0)
				return null;
			if (lastCharacter == -1 || this.reader.reachedLimit())
				this.endReached();
			return this.objectNode;
		}

		/**
		 * @param escaped
		 */
		private void setState(State newState) {
			this.state.push(newState);
		}

		/**
		 * @param fieldIndex
		 * @param string
		 */
		private void addToObject(int fieldIndex, String string) {
			if (fieldIndex < this.keyNames.length)
				this.objectNode.put(this.keyNames[fieldIndex], TextNode.valueOf(string));
		}

		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.pact.common.io.FileInputFormat#close()
		 */
		@Override
		public void close() throws IOException {
			this.revertToPreviousState();
			this.reader.close();
			super.close();
		}

		/**
		 * @return
		 */
		private State getCurrentState() {
			return this.state.peek();
		}

		private State revertToPreviousState() {
			return this.state.pop();
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.pact.common.generic.io.InputFormat#getStatistics(eu.stratosphere.pact.common.io.statistics.
		 * BaseStatistics)
		 */
		@Override
		public FileBaseStatistics getStatistics(BaseStatistics cachedStatistics) {
			// check the cache
			FileBaseStatistics stats = null;

			if (cachedStatistics != null && cachedStatistics instanceof FileBaseStatistics)
				stats = (FileBaseStatistics) cachedStatistics;
			else
				stats =
					new FileBaseStatistics(-1, BaseStatistics.SIZE_UNKNOWN, BaseStatistics.AVG_RECORD_BYTES_UNKNOWN);

			try {
				final Path file = this.filePath;
				final URI uri = file.toUri();

				// get the filesystem
				final FileSystem fs = FileSystem.get(uri);
				List<FileStatus> files = null;

				// get the file info and check whether the cached statistics are still
				// valid.
				{
					FileStatus status = fs.getFileStatus(file);

					if (status.isDir()) {
						FileStatus[] fss = fs.listStatus(file);
						files = new ArrayList<FileStatus>(fss.length);
						boolean unmodified = true;

						for (FileStatus s : fss)
							if (!s.isDir()) {
								files.add(s);
								if (s.getModificationTime() > stats.getLastModificationTime()) {
									stats.setLastModificationTime(s.getModificationTime());
									unmodified = false;
								}
							}

						if (unmodified)
							return stats;
					}
					else {
						// check if the statistics are up to date
						long modTime = status.getModificationTime();
						if (stats.getLastModificationTime() == modTime)
							return stats;

						stats.setLastModificationTime(modTime);

						files = new ArrayList<FileStatus>(1);
						files.add(status);
					}
				}

				long fileSize = 0;

				// calculate the whole length
				for (FileStatus s : files)
					fileSize += s.getLen();

				// sanity check
				if (fileSize <= 0) {
					fileSize = BaseStatistics.SIZE_UNKNOWN;
					return stats;
				}
				stats.setTotalInputSize(fileSize);

				// make the samples small for very small files
				int numSamples = Math.min(this.numLineSamples, (int) (fileSize / 1024));
				if (numSamples < 2)
					numSamples = 2;

				long offset = 0;
				long bytes = 0; // one byte for the line-break
				long stepSize = fileSize / numSamples;

				int fileNum = 0;
				int samplesTaken = 0;

				// take the samples
				for (int sampleNum = 0; sampleNum < numSamples && fileNum < files.size(); sampleNum++) {
					FileStatus currentFile = files.get(fileNum);
					FSDataInputStream inStream = null;

					try {
						inStream = fs.open(currentFile.getPath());
						LineReader lineReader = new LineReader(inStream, offset, currentFile.getLen() - offset, 1024);
						byte[] line = lineReader.readLine();
						lineReader.close();

						if (line != null && line.length > 0) {
							samplesTaken++;
							bytes += line.length + 1; // one for the linebreak
						}
					} finally {
						// make a best effort to close
						if (inStream != null)
							try {
								inStream.close();
							} catch (Throwable t) {
							}
					}

					offset += stepSize;

					// skip to the next file, if necessary
					while (fileNum < files.size() && offset >= (currentFile = files.get(fileNum)).getLen()) {
						offset -= currentFile.getLen();
						fileNum++;
					}
				}

				stats.setAverageRecordWidth(bytes / (float) samplesTaken);
			} catch (IOException ioex) {
				if (LOG.isWarnEnabled())
					LOG.warn("Could not determine complete statistics for file '" + this.filePath
						+ "' due to an io error: "
						+ ioex.getMessage());
			} catch (Throwable t) {
				if (LOG.isErrorEnabled())
					LOG.error("Unexpected problen while getting the file statistics for file '" + this.filePath + "': "
						+ t.getMessage(), t);
			}

			return stats;
		}

	}

	/**
	 * @author Arvid Heise
	 */
	public static class CountingReader extends Reader {
		private long start = 0, relativePos = 0, limit = 0;

		private boolean reachedLimit = false;

		private ByteBuffer streamBuffer = ByteBuffer.allocate(100);

		private CharBuffer charBuffer = CharBuffer.allocate(100);

		private FSDataInputStream stream;

		protected CharsetDecoder decoder;

		public CountingReader(FSDataInputStream stream, String charset, long start, long limit) {
			this.stream = stream;
			this.decoder = Charset.forName(charset).newDecoder();
			this.start = start;
			this.limit = limit;
			// mark as empty
			this.charBuffer.limit(0);
		}

		/**
		 * Returns the pos.
		 * 
		 * @return the pos
		 */
		public long getRelativePos() {
			return this.relativePos;
		}

		public boolean reachedLimit() {
			return this.reachedLimit;
		}

		public void seek(long absolutePos) throws IOException {
			this.relativePos = Math.max(absolutePos - this.start, 0);
			this.stream.seek(absolutePos);
			// mark as empty
			this.charBuffer.limit(0);
		}

		/*
		 * (non-Javadoc)
		 * @see java.io.Reader#read(char[], int, int)
		 */
		@Override
		public int read(char[] cbuf, int off, int len) throws IOException {
			int toRead = len - off;
			while (toRead > 0) {
				this.fillCharBufferIfEmpty();
				int currentReadCount = Math.min(toRead, this.charBuffer.length());
				this.charBuffer.get(cbuf, off, currentReadCount);
				toRead -= currentReadCount;
			}
			return len - toRead;
		}

		private void fillCharBufferIfEmpty() throws IOException {
			if (this.charBuffer.remaining() == 0) {
				final int maxLen = this.streamBuffer.capacity();
				this.streamBuffer.clear();
				if (this.reachedLimit) {
					final int read = this.stream.read(this.streamBuffer.array(), 0, maxLen);
					this.streamBuffer.limit(read);
				} else {
					final int read = this.stream.read(this.streamBuffer.array(), 0,
						(int) Math.min(maxLen, this.limit - this.relativePos));
					if (read == -1) 
						this.reachedLimit = true;
					else {
						this.relativePos += read;
						this.streamBuffer.limit(read);
						this.reachedLimit = this.limit <= this.relativePos;
					} 
				}

				this.charBuffer.clear();
				this.decoder.decode(this.streamBuffer, this.charBuffer, false);
				this.charBuffer.flip();
			}
		}

		/*
		 * (non-Javadoc)
		 * @see java.io.Reader#read()
		 */
		@Override
		public int read() throws IOException {
			this.fillCharBufferIfEmpty();
			if (this.charBuffer.remaining() == 0)
				return -1;
			return this.charBuffer.get();
		}

		/*
		 * (non-Javadoc)
		 * @see java.io.Reader#close()
		 */
		@Override
		public void close() throws IOException {
			this.stream.close();
		}
	}
}