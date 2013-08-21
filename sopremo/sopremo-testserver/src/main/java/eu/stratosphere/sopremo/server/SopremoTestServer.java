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
package eu.stratosphere.sopremo.server;

import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.AssertionFailedError;

import org.apache.hadoop.fs.FileSystem;
import org.junit.Assert;
import org.junit.Ignore;

import eu.stratosphere.nephele.execution.librarycache.LibraryCacheProfileRequest;
import eu.stratosphere.nephele.execution.librarycache.LibraryCacheProfileResponse;
import eu.stratosphere.nephele.execution.librarycache.LibraryCacheUpdate;
import eu.stratosphere.nephele.rpc.RPCService;
import eu.stratosphere.pact.client.minicluster.NepheleMiniCluster;
import eu.stratosphere.sopremo.execution.ExecutionRequest;
import eu.stratosphere.sopremo.execution.ExecutionResponse;
import eu.stratosphere.sopremo.execution.ExecutionResponse.ExecutionState;
import eu.stratosphere.sopremo.execution.SopremoConstants;
import eu.stratosphere.sopremo.execution.SopremoExecutionProtocol;
import eu.stratosphere.sopremo.execution.SopremoID;
import eu.stratosphere.sopremo.io.JsonGenerator;
import eu.stratosphere.sopremo.io.JsonParser;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * @author Arvid Heise
 */
@Ignore
public class SopremoTestServer implements Closeable, SopremoExecutionProtocol {

	private RPCService rpcService;

	private SopremoServer server;

	private SopremoExecutionProtocol executor;

	private NepheleMiniCluster cluster = new NepheleMiniCluster();

	private Set<String> filesToCleanup = new HashSet<String>();

	private String tempDir;

	/**
	 * Initializes EqualCloneTestServer.
	 */
	public SopremoTestServer(boolean rpc) {

		try {
			this.cluster.start();
		} catch (Exception e) {
			fail(e, "Cannot start mini cluster");
		}
		this.server = new SopremoServer();
		this.server.setJobManagerAddress(
			new InetSocketAddress("localhost", this.cluster.getJobManagerRpcPort()));

		if (rpc) {
			try {
				this.server.setServerAddress(
					new InetSocketAddress("localhost", SopremoConstants.DEFAULT_SOPREMO_SERVER_IPC_PORT));
				this.server.start();
				this.rpcService = new RPCService();
				this.executor =
					this.rpcService.getProxy(this.server.getServerAddress(), SopremoExecutionProtocol.class);
			} catch (IOException e) {
				fail(e, "Cannot start rpc sopremo server");
			}
		} else {
			this.executor = this.server;
		}

		this.tempDir = System.getProperty("java.io.tmpdir");
		if (!this.tempDir.endsWith(File.separator))
			this.tempDir += File.separator;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.execution.LibraryTransferProtocol#getLibraryCacheProfile(eu.stratosphere.nephele.execution
	 * .librarycache.LibraryCacheProfileRequest)
	 */
	@Override
	public LibraryCacheProfileResponse getLibraryCacheProfile(LibraryCacheProfileRequest request) throws IOException {
		LibraryCacheProfileResponse response = new LibraryCacheProfileResponse(request);
		String[] requiredLibraries = request.getRequiredLibraries();

		// since the test server is executed locally, all libraries are available
		for (int i = 0; i < requiredLibraries.length; i++)
			response.setCached(i, true);

		return response;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.execution.LibraryTransferProtocol#updateLibraryCache(eu.stratosphere.nephele.execution
	 * .librarycache.LibraryCacheUpdate)
	 */
	@Override
	public void updateLibraryCache(LibraryCacheUpdate update) throws IOException {
	}

	public void checkContentsOf(String fileName, IJsonNode... expected) throws IOException {
		List<IJsonNode> remainingValues = new ArrayList<IJsonNode>(Arrays.asList(expected));

		final String outputFile = this.tempDir + fileName;
		this.filesToCleanup.add(outputFile);
		final File file = new File(outputFile);
		Assert.assertTrue("output " + fileName + " not written", file.exists());
		final JsonParser parser = new JsonParser(new FileReader(file));
		try {
			parser.setWrappingArraySkipping(true);
			int index = 0;

			for (; index < expected.length && !parser.checkEnd(); index++) {
				final IJsonNode actual = parser.readValueAsTree();
				Assert.assertTrue(String.format("Unexpected value %s; remaining %s", actual, remainingValues),
					remainingValues.remove(actual));
			}
			if (!remainingValues.isEmpty())
				Assert.fail("More elements expected " + remainingValues);
			if (!parser.checkEnd())
				Assert.fail("Less elements expected " + parser.readValueAsTree());
		} finally {
			parser.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		for (String fileToClean : this.filesToCleanup)
			try {
				delete(fileToClean, true);
			} catch (IOException e) {
			}

		try {
			this.cluster.stop();
		} catch (Exception e) {
		}
		this.server.close();
		if (this.executor != this.server)
			this.rpcService.shutDown();
		FileSystem.closeAll();
	}

	public File createDir(String dirName) {
		this.filesToCleanup.add(getTempName(dirName));
		final File file = new File(getTempName(dirName));
		file.mkdirs();
		return file;
	}

	public File createFile(String fileName, IJsonNode... nodes) throws IOException {
		this.filesToCleanup.add(getTempName(fileName));
		return createFile(getTempName(fileName), getJsonString(nodes));
	}

	public File getOutputFile(String fileName) {
		this.filesToCleanup.add(getTempName(fileName));
		final File file = new File(getTempName(fileName));
		file.delete();
		return file;
	}

	private File createFile(String fileName, String fileContent) throws IOException {
		File f = new File(fileName);
		if (this.filesToCleanup.contains(fileContent))
			throw new IllegalArgumentException("file already exists");

		FileWriter fw = new FileWriter(f);
		fw.write(fileContent);
		fw.close();

		return f;
	}

	public boolean delete(String path, boolean recursive) throws IOException {
		final File file = new File(getTempName(path));
		if (recursive && file.isDirectory())
			for (String subFile : file.list())
				delete(path + File.separator + subFile, recursive);
		return file.delete();
	}

	@Override
	public ExecutionResponse execute(ExecutionRequest request) throws IOException, InterruptedException {
		return this.executor.execute(request);
	}

	public InetSocketAddress getServerAddress() {
		return this.server.getServerAddress();
	}

	@Override
	public ExecutionResponse getState(SopremoID jobId) throws IOException, InterruptedException {
		return this.executor.getState(jobId);
	}

	private void fail(Exception e, final String message) throws AssertionFailedError {
		final AssertionFailedError assertionFailedError = new AssertionFailedError(message);
		assertionFailedError.initCause(e);
		throw assertionFailedError;
	}

	private String getJsonString(IJsonNode... nodes) throws IOException {
		final StringWriter jsonWriter = new StringWriter();
		JsonGenerator generator = new JsonGenerator(jsonWriter);
		generator.writeStartArray();
		for (IJsonNode node : nodes)
			generator.writeTree(node);
		generator.writeEndArray();
		generator.close();
		final String jsonString = jsonWriter.toString();
		return jsonString;
	}

	private String getTempName(String name) {
		return this.tempDir + name;
	}

	public static ExecutionResponse waitForStateToFinish(SopremoExecutionProtocol server, ExecutionResponse response,
			ExecutionState status) throws IOException, InterruptedException {
		for (int waits = 0; response.getState() == status && waits < 1000; waits++) {
			Thread.sleep(100);
			response = server.getState(response.getJobId());
		}
		return response;
	}

	@Override
	public Object getMetaData(SopremoID jobId, String key) throws IOException, InterruptedException {
		return this.executor.getMetaData(jobId, key);
	}

}
