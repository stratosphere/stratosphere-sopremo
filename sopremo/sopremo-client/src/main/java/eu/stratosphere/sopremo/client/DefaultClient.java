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
package eu.stratosphere.sopremo.client;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import eu.stratosphere.configuration.Configuration;
import eu.stratosphere.configuration.GlobalConfiguration;
import eu.stratosphere.core.fs.Path;
import eu.stratosphere.nephele.execution.librarycache.LibraryCacheManager;
import eu.stratosphere.nephele.execution.librarycache.LibraryCacheProfileRequest;
import eu.stratosphere.nephele.execution.librarycache.LibraryCacheProfileResponse;
import eu.stratosphere.nephele.execution.librarycache.LibraryCacheUpdate;
import eu.stratosphere.nephele.jobgraph.JobID;
import eu.stratosphere.nephele.rpc.RPCService;
import eu.stratosphere.sopremo.execution.ExecutionRequest;
import eu.stratosphere.sopremo.execution.ExecutionRequest.ExecutionMode;
import eu.stratosphere.sopremo.execution.ExecutionResponse;
import eu.stratosphere.sopremo.execution.ExecutionResponse.ExecutionState;
import eu.stratosphere.sopremo.execution.SopremoConstants;
import eu.stratosphere.sopremo.execution.SopremoExecutionProtocol;
import eu.stratosphere.sopremo.execution.SopremoID;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.util.StringUtils;

/**
 */
public class DefaultClient implements Closeable {
	private Configuration configuration, jobConfig = new Configuration();

	private RPCService rpcService;

	private SopremoExecutionProtocol executor;

	private InetSocketAddress serverAddress;

	private int updateTime = 5000;

	private ExecutionMode executionMode = ExecutionMode.RUN;

	/**
	 * Initializes DefaultClient.
	 */
	public DefaultClient() {
		this(GlobalConfiguration.getConfiguration());
	}

	/**
	 * Initializes CLClient.
	 */
	public DefaultClient(final Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Returns the jobConfig.
	 * 
	 * @return the jobConfig
	 */
	public Configuration getJobConfig() {
		return this.jobConfig;
	}

	/**
	 * Sets the jobConfig to the specified value.
	 * 
	 * @param jobConfig
	 *        the jobConfig to set
	 */
	public void setJobConfig(Configuration jobConfig) {
		if (jobConfig == null)
			throw new NullPointerException("jobConfig must not be null");

		this.jobConfig = jobConfig;
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() {
		if (this.rpcService != null)
			this.rpcService.shutDown();
	}

	/**
	 * Returns the configuration.
	 * 
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		return this.configuration;
	}

	/**
	 * Returns the executionMode.
	 * 
	 * @return the executionMode
	 */
	public ExecutionMode getExecutionMode() {
		return this.executionMode;
	}

	public Object getMetaData(final SopremoID id, final String key) throws IOException, InterruptedException {
		final Object result = this.executor.getMetaData(id, key);
		return result;
	}

	/**
	 * Returns the serverAddress.
	 * 
	 * @return the serverAddress
	 */
	public InetSocketAddress getServerAddress() {
		return this.serverAddress;
	}

	/**
	 * Returns the updateTime.
	 * 
	 * @return the updateTime
	 */
	public int getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * Sets the configuration to the specified value.
	 * 
	 * @param configuration
	 *        the configuration to set
	 */
	public void setConfiguration(final Configuration configuration) {
		if (configuration == null)
			throw new NullPointerException("configuration must not be null");

		this.configuration = configuration;
	}

	/**
	 * Sets the executionMode to the specified value.
	 * 
	 * @param executionMode
	 *        the executionMode to set
	 */
	public void setExecutionMode(final ExecutionMode executionMode) {
		if (executionMode == null)
			throw new NullPointerException("executionMode must not be null");

		this.executionMode = executionMode;
	}

	/**
	 * Sets the serverAddress to the specified value.
	 * 
	 * @param serverAddress
	 *        the serverAddress to set
	 */
	public void setServerAddress(final InetSocketAddress serverAddress) {
		if (serverAddress == null)
			throw new NullPointerException("serverAddress must not be null");

		this.serverAddress = serverAddress;
	}

	/**
	 * Sets the updateTime to the specified value.
	 * 
	 * @param updateTime
	 *        the updateTime to set
	 */
	public void setUpdateTime(final int updateTime) {
		this.updateTime = updateTime;
	}

	public SopremoID submit(final SopremoPlan plan, final ProgressListener progressListener) {
		return this.submit(plan, progressListener, true);
	}

	public SopremoID submit(final SopremoPlan plan, ProgressListener progressListener, final boolean wait) {
		if (plan == null)
			throw new NullPointerException();

		if (progressListener == null)
			progressListener = new ThrowingListener();
		this.initConnection(progressListener);
		if (!this.transferLibraries(plan, progressListener)) {
			this.dealWithError(progressListener, null, "Could not transfer libraries - aborting");
			return null;
		}

		final ExecutionResponse response = this.sendPlan(plan, progressListener);
		if (response == null)
			return null;

		if (response.getState() == ExecutionState.ERROR) {
			this.dealWithError(progressListener, null, "Error while submitting to job execution");
			return null;
		}

		if (wait)
			return this.waitForCompletion(response, progressListener);
		return response.getJobId();
	}

	protected void sleepSafely(final int updateTime) {
		try {
			Thread.sleep(updateTime);
		} catch (final InterruptedException e) {
		}
	}

	private void dealWithError(final ProgressListener progressListener, final Exception e, final String detail) {
		if (e != null)
			progressListener.progressUpdate(ExecutionState.ERROR, String.format("%s: %s", detail,
				StringUtils.stringifyException(e)));
		else
			progressListener.progressUpdate(ExecutionState.ERROR, detail);
	}

	private void initConnection(final ProgressListener progressListener) {
		InetSocketAddress serverAddress = this.serverAddress;

		if (serverAddress == null) {
			final String address =
				this.configuration.getString(SopremoConstants.SOPREMO_SERVER_IPC_ADDRESS_KEY, "localhost");
			final int port = this.configuration.getInteger(SopremoConstants.SOPREMO_SERVER_IPC_PORT_KEY,
				SopremoConstants.DEFAULT_SOPREMO_SERVER_IPC_PORT);
			serverAddress = new InetSocketAddress(address, port);
		}

		try {
			this.rpcService = new RPCService();
			this.executor = this.rpcService.getProxy(serverAddress, SopremoExecutionProtocol.class);
		} catch (final IOException e) {
			this.dealWithError(progressListener, e, "Error while connecting to the server");
		}
	}

	private ExecutionResponse sendPlan(final SopremoPlan query, final ProgressListener progressListener) {
		try {
			final ExecutionRequest request = new ExecutionRequest(query);
			request.setMode(this.executionMode);
			request.getConfiguration().addAll(this.jobConfig);			
			return this.executor.execute(request);
		} catch (final Exception e) {
			this.dealWithError(progressListener, e, "Error while sending the query to the server");
			return null;
		}
	}

	private boolean transferLibraries(final SopremoPlan plan, final ProgressListener progressListener) {
		final JobID dummyKey = JobID.generate();
		final List<String> requiredLibraries = new ArrayList<String>(plan.getRequiredPackages());
		try {
			progressListener.progressUpdate(ExecutionState.SETUP, "");
			final List<Path> libraryPaths = new ArrayList<Path>();
			for (final String library : requiredLibraries) {
				final DataInputStream dis = new DataInputStream(new FileInputStream(library));
				try {
					final Path libraryPath = new Path(library);
					final File libraryFile = new File(library);
					if (libraryFile.isDirectory())
						throw new IllegalStateException("The package " + libraryFile.getName() +
							" is not as present as a jar");
					LibraryCacheManager.addLibrary(dummyKey, libraryPath, (int) libraryFile.length(), dis);
					libraryPaths.add(libraryPath);
				} finally {
					dis.close();
				}
			}

			LibraryCacheManager.register(dummyKey, libraryPaths.toArray(new Path[libraryPaths.size()]));
			final LibraryCacheProfileRequest request = new LibraryCacheProfileRequest();
			final String[] internalJarNames = LibraryCacheManager.getRequiredJarFiles(dummyKey);
			request.setRequiredLibraries(internalJarNames);

			// Send the request
			LibraryCacheProfileResponse response = null;
			response = this.executor.getLibraryCacheProfile(request);

			// Check response and transfer libraries if necessary
			for (int k = 0; k < internalJarNames.length; k++)
				if (!response.isCached(k)) {
					final String library = internalJarNames[k];
					progressListener.progressUpdate(ExecutionState.SETUP, "Transfering " + requiredLibraries.get(k));
					final LibraryCacheUpdate update = new LibraryCacheUpdate(library);
					this.executor.updateLibraryCache(update);
				}

			// now change the names of the required libraries to the internal names
			for (int index = 0; index < internalJarNames.length; index++)
				requiredLibraries.set(index, internalJarNames[index]);
			plan.setRequiredPackages(requiredLibraries);

			return true;
		} catch (final Exception e) {
			this.dealWithError(progressListener, e, "Cannot transfer libraries");
			return false;
		} finally {
			try {
				LibraryCacheManager.unregister(dummyKey);
			} catch (final IOException e) {
			}
		}
	}

	private SopremoID waitForCompletion(final ExecutionResponse submissionResponse,
			final ProgressListener progressListener) {
		ExecutionResponse lastResponse = submissionResponse;

		final SopremoID submittedJobId = submissionResponse.getJobId();

		try {
			progressListener.progressUpdate(ExecutionState.ENQUEUED, lastResponse.getDetails());
			while (lastResponse.getState() == ExecutionState.ENQUEUED) {
				lastResponse = this.executor.getState(submittedJobId);
				this.sleepSafely(this.updateTime);
				progressListener.progressUpdate(ExecutionState.ENQUEUED, lastResponse.getDetails());
			}

			progressListener.progressUpdate(ExecutionState.RUNNING, lastResponse.getDetails());
			while (lastResponse.getState() == ExecutionState.RUNNING) {
				lastResponse = this.executor.getState(submittedJobId);
				this.sleepSafely(this.updateTime);
				progressListener.progressUpdate(ExecutionState.RUNNING, lastResponse.getDetails());
			}

			if (lastResponse.getState() == ExecutionState.ERROR) {
				progressListener.progressUpdate(ExecutionState.ERROR, lastResponse.getDetails());
				return null;
			}

			progressListener.progressUpdate(ExecutionState.FINISHED, lastResponse.getDetails());
			return submittedJobId;
		} catch (final Exception e) {
			this.dealWithError(progressListener, e, "Error while waiting for job execution");
			return null;
		}
	}

	/**
	 * Avoids plenty of null checks.
	 */
	private static class ThrowingListener implements ProgressListener {
		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.client.ProgressListener#progressUpdate(eu.stratosphere.sopremo.execution.
		 * ExecutionResponse.ExecutionState, java.lang.String)
		 */
		@Override
		public void progressUpdate(final ExecutionState state, final String detail) {
			if (state == ExecutionState.ERROR)
				throw new RuntimeException(detail);
		}
	}

}
