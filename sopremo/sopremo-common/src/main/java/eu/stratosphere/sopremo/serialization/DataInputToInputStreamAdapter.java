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
package eu.stratosphere.sopremo.serialization;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author arv
 */
public class DataInputToInputStreamAdapter extends InputStream {
	private DataInput dataInput;

	/**
	 * Sets the dataInput to the specified value.
	 * 
	 * @param dataInput
	 *        the dataInput to set
	 */
	public void setDataInput(DataInput dataInput) {
		if (dataInput == null)
			throw new NullPointerException("dataInput must not be null");

		this.dataInput = dataInput;
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.InputStream#read(byte[], int, int)
	 */
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		try {
			this.dataInput.readFully(b, off, len);
			return len;
		} catch (EOFException e) {
			return super.read(b, off, len);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.InputStream#read(byte[])
	 */
	@Override
	public int read(byte[] b) throws IOException {
		try {
			this.dataInput.readFully(b);
			return b.length;
		} catch (EOFException e) {
			return super.read(b);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.InputStream#read()
	 */
	@Override
	public int read() throws IOException {
		try {
			return this.dataInput.readByte();
		} catch (EOFException e) {
			return -1;
		}
	}
}
