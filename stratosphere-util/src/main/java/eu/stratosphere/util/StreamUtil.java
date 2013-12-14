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
package eu.stratosphere.util;

import it.unimi.dsi.fastutil.bytes.ByteList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author arv
 */
public class StreamUtil {

	public static File cacheFile(final InputStream inputStream) throws IOException {
		OutputStream outputStream = null;
		File cachedFile = null;
		try {
			cachedFile = File.createTempFile("strat_cache", null);
			cachedFile.deleteOnExit();
			outputStream = new FileOutputStream(cachedFile);
			copyStreams(inputStream, outputStream);
			return cachedFile;
		} catch (final IOException e) {
			if (cachedFile != null)
				cachedFile.delete();
			throw e;
		} finally {
			inputStream.close();
			if (outputStream != null)
				outputStream.close();
		}
	}

	public static void copyStreams(final InputStream inputStream, final OutputStream outputStream) throws IOException {
		int read;
		final byte[] buf = new byte[8192];
		while ((read = inputStream.read(buf)) != -1)
			outputStream.write(buf, 0, read);
	}

	public static void readFully(final InputStream inputStream, final ByteList buffer) throws IOException {
		int read, total = 0;
		final byte[] buf = new byte[8192];
		while ((read = inputStream.read(buf)) != -1) {
			buffer.addElements(total, buf, 0, read);
			total += read;
		}
	}
}
