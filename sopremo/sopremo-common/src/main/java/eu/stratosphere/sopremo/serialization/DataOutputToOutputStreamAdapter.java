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

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author arv
 */
public class DataOutputToOutputStreamAdapter extends OutputStream {
	private DataOutput dataOutput;

	/**
	 * Sets the dataOutput to the specified value.
	 * 
	 * @param dataOutput
	 *        the dataOutput to set
	 */
	public void setDataOutput(DataOutput dataOutput) {
		if (dataOutput == null)
			throw new NullPointerException("dataOutput must not be null");

		this.dataOutput = dataOutput;
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
		this.dataOutput.writeByte(b);
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[])
	 */
	@Override
	public void write(byte[] b) throws IOException {
		this.dataOutput.write(b);
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		this.dataOutput.write(b, off, len);
	}
}
