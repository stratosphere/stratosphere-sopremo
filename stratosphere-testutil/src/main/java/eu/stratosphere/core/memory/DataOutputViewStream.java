package eu.stratosphere.core.memory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DataOutputViewStream extends DataOutputStream implements DataOutputView {
	/**
	 * Initializes DataOutputViewStream.
	 * 
	 * @param out
	 */
	public DataOutputViewStream(final OutputStream out) {
		super(out);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.core.memory.DataOutputView#skipBytesToWrite(int)
	 */
	@Override
	public void skipBytesToWrite(int numBytes) throws IOException {
		for (; numBytes > 0; numBytes -= 1024)
			this.write(new byte[Math.min(numBytes, 1024)]);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.core.memory.DataOutputView#write(eu.stratosphere.nephele.services.
	 * memorymanager.DataInputView, int)
	 */
	@Override
	public void write(final DataInputView source, int numBytes) throws IOException {
		for (; numBytes > 0; numBytes -= 1024) {
			final byte[] buffer = new byte[Math.min(numBytes, 1024)];
			source.readFully(buffer);
			this.write(buffer);
		}
	}
}