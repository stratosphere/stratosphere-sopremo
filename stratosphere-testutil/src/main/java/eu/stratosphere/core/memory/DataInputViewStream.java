package eu.stratosphere.core.memory;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import eu.stratosphere.core.memory.DataInputView;

/**
 */
public class DataInputViewStream extends DataInputStream implements DataInputView {
	/**
	 * Initializes DataInputViewStream.
	 * 
	 * @param in
	 */
	public DataInputViewStream(final InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.core.memory.DataInputView#skipBytesToRead(int)
	 */
	@Override
	public void skipBytesToRead(final int numBytes) throws IOException {
		if (this.skipBytes(numBytes) != numBytes)
			throw new EOFException();
	}
}