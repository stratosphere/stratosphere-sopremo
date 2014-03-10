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
package eu.stratosphere.meteor.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import org.apache.commons.cli.*;

import eu.stratosphere.configuration.ConfigConstants;
import eu.stratosphere.configuration.GlobalConfiguration;
import eu.stratosphere.meteor.QueryParser;
import eu.stratosphere.sopremo.client.DefaultClient;
import eu.stratosphere.sopremo.client.StateListener;
import eu.stratosphere.sopremo.execution.ExecutionRequest.ExecutionMode;
import eu.stratosphere.sopremo.execution.ExecutionResponse.ExecutionState;
import eu.stratosphere.sopremo.execution.SopremoConstants;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.server.SopremoJobInfo;

/**
 */
public class CLClient {
	private final Options options = new Options();

	private DefaultClient sopremoClient;

	/**
	 * Initializes CLClient.
	 */
	public CLClient() {
		this.initOptions();
	}

	@SuppressWarnings("static-access")
	private void initOptions() {
		this.options.addOption(OptionBuilder.
			withArgName("config").hasArg(true).
			withDescription("Uses the given configuration").withLongOpt("configDir").create());
		this.options.addOption(OptionBuilder.
			withArgName("server").hasArg(true).
			withDescription("Uses the specified server").withLongOpt("server").create());
		this.options.addOption(OptionBuilder.
			withArgName("port").hasArg(true).
			withDescription("Uses the specified port").withLongOpt("port").create());
		this.options.addOption(OptionBuilder.
			withArgName("dop").hasArg(true).
			withDescription("Overall degree of parallelism").withLongOpt("dop").create('d'));
		this.options.addOption(OptionBuilder.
			withArgName("dop").hasArg(true).
			withDescription("Intranode degree of parallelism").withLongOpt("intra").create('i'));
		this.options.addOption(OptionBuilder.
			withArgName("numMachines").hasArg(true).
			withDescription("Maximum number of machines").withLongOpt("numMachines").create('m'));
		this.options.addOption(OptionBuilder.
			withArgName("updateTime").hasArg(true).
			withDescription("Checks with the given update time in ms for the current status").withLongOpt("updateTime").create());
		this.options.addOption(OptionBuilder.
			hasArg(false).
			withDescription("Waits until the script terminates on the server").withLongOpt("wait").create('w'));
	}

	public static void main(final String[] args) {
		new CLClient().process(args);
	}

	private void process(final String[] args) {
		final CommandLine cmd = this.parseOptions(args);
		@SuppressWarnings("unchecked")
		final List<String> scripts = cmd.getArgList();
		if (scripts.size() == 0)
			this.dealWithError(null, "No scripts to execute");

		this.configureClient(cmd);
		for (final String script : scripts) {
			final SopremoPlan plan = this.parseScript(script);

			System.out.println("Submitting plan");
			this.sopremoClient.submit(plan, new StateListener() {
				@Override
				public void stateChanged(final ExecutionState executionState, final String detail) {
					System.out.println();
					switch (executionState) {
					case ENQUEUED:
						System.out.print("Submitted script " + script);
						break;
					case SETUP:
						System.out.print("Setting script up " + script);
						break;
					case RUNNING:
						System.out.print("Executing script " + script);
						break;
					case FINISHED:
						System.out.print(detail);
						break;
					case ERROR:
						System.out.print(detail);
						break;
					default:
						break;
					}
				}

				/*
				 * (non-Javadoc)
				 * @see eu.stratosphere.sopremo.client.StateListener#stateNotChanged(eu.stratosphere.sopremo.execution.
				 * ExecutionResponse.ExecutionState, java.lang.String)
				 */
				@Override
				protected void stateNotChanged(final ExecutionState state, final String detail) {
					System.out.print(".");
				}
			}, cmd.hasOption("wait"));
		}
		System.out.println("Plan submitted");

		this.sopremoClient.close();
	}

	private void configureClient(final CommandLine cmd) {
		final String configDir = cmd.getOptionValue("configDir");
		GlobalConfiguration.loadConfiguration(configDir);
		this.sopremoClient = new DefaultClient(GlobalConfiguration.getConfiguration());

		int updateTime = 1000;
		if (cmd.hasOption("updateTime"))
			updateTime = Integer.parseInt(cmd.getOptionValue("updateTime"));
		this.sopremoClient.setUpdateTime(updateTime);
		final String address = cmd.getOptionValue("server"), port = cmd.getOptionValue("port");
		if (address != null || port != null)
			this.sopremoClient.setServerAddress(new InetSocketAddress(
				address == null ? "localhost" : address,
				port == null ? SopremoConstants.DEFAULT_SOPREMO_SERVER_IPC_PORT : Integer.parseInt(port)));

		this.sopremoClient.setExecutionMode(ExecutionMode.RUN_WITH_STATISTICS);

		if (cmd.hasOption('d')) 
			this.sopremoClient.getJobConfig().setInteger(ConfigConstants.DEFAULT_PARALLELIZATION_DEGREE_KEY,
				parseInt(cmd, 'd'));
		if (cmd.hasOption('i')) 
			this.sopremoClient.getJobConfig().setInteger(ConfigConstants.PARALLELIZATION_MAX_INTRA_NODE_DEGREE_KEY,
				parseInt(cmd, 'i'));
		if (cmd.hasOption('m')) 
			this.sopremoClient.getJobConfig().setInteger(SopremoJobInfo.MAX_MACHINES,
				parseInt(cmd, 'm'));
	}

	private int parseInt(CommandLine cmd, char c) {
		try {
			return Integer.parseInt(cmd.getOptionValue(c));
		} catch (NumberFormatException e) {
			dealWithError(e, "Cannot parse int of option " + c);
			return 0;
		}
	}

	protected void sleepSafely(final int updateTime) {
		try {
			Thread.sleep(updateTime);
		} catch (final InterruptedException e) {
		}
	}

	protected void dealWithError(final Exception e, final String message, final Object... args) {
		System.err.print(String.format(message, args));
		if (e != null) {
			System.err.print(": ");
			System.err.print(e);
		}
		System.err.println();
		System.exit(1);
	}

	private SopremoPlan parseScript(final String script) {
		final File file = new File(script);
		if (!file.exists())
			this.dealWithError(null, "Given file %s not found", file);

		try {
			return new QueryParser().withInputDirectory(new File(script).getAbsoluteFile().getParentFile()).tryParse(
				new FileInputStream(file));
		} catch (final IOException e) {
			this.dealWithError(e, "Error while parsing script");
			return null;
		}
	}

	protected CommandLine parseOptions(final String[] args) {
		final CommandLineParser parser = new PosixParser();
		try {
			return parser.parse(this.options, args);
		} catch (final ParseException e) {
			System.err.println("Cannot process the given arguments: " + e);
			final HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("meteor-client.sh <scripts>", this.options);
			System.exit(1);
			return null;
		}
	}
}
