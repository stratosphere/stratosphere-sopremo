package eu.stratosphere.meteor.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;

import eu.stratosphere.configuration.GlobalConfiguration;
import eu.stratosphere.meteor.QueryParser;
import eu.stratosphere.meteor.client.web.ErrorServlet;
import eu.stratosphere.sopremo.client.DefaultClient;
import eu.stratosphere.sopremo.client.StateListener;
import eu.stratosphere.sopremo.execution.ExecutionRequest.ExecutionMode;
import eu.stratosphere.sopremo.execution.ExecutionResponse.ExecutionState;
import eu.stratosphere.sopremo.execution.SopremoConstants;
import eu.stratosphere.sopremo.execution.SopremoID;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.query.QueryParserException;

public class ClientFrontend {

	/**
	 * CLClient as base client to handle options and commands
	 */
	private final CLClient baseClient;

	private final CommandLine cmd;

	/**
	 * Sopremo plan and default client to handle process
	 */
	private SopremoPlan sopremoPlan;

	private DefaultClient sopremoClient;

	/**
	 * Save the meteor script
	 */
	private String meteorScript;

	/**
	 * the outputs of the script
	 */
	private List<String> outputs;

	private String visOutput = null;

	private SopremoID currentSopremoID = null;

	/**
	 * job status flags
	 */
	private String currentJobStatus = "{ \"jobstatus\": \"currently no jobs running\" }";

	private String jobStates = this.currentJobStatus;

	private long startTimestamp = 0L;

	private long currentTimestamp = 0L;

	/**
	 * Constructs a new client by given options and commands.
	 * See CLClient for more details.
	 * 
	 * @param args
	 */
	public ClientFrontend(final String[] args) {
		// create a base client and handle commands
		this.baseClient = new CLClient();
		this.cmd = this.baseClient.parseOptions(args);
		this.configureClient(this.cmd);
	}

	/**
	 * Configure the default client. Method is equal to configureClient in CLClient.
	 * 
	 * @param cmd
	 */
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
	}

	/**
	 * Starts the process. Creates a sopremo plan by given meteor script and submit this plan by default client.
	 * 
	 * @param meteorScript
	 */
	public void execute(final String meteorScript) throws QueryParserException, IOException {
		this.startTimestamp = System.currentTimeMillis();
		this.meteorScript = meteorScript;
		this.jobStates = "";
		this.extractHDFSWrites();

		// TODO: we should have another AJAX servlet, that is periodically queried -> will trigger complete reload

		final ByteArrayInputStream inputStream = new ByteArrayInputStream(meteorScript.getBytes("UTF-8"));
		try {
			this.sopremoPlan = new QueryParser().tryParse(inputStream);
			this.currentSopremoID = this.sopremoClient.submit(this.sopremoPlan, new SopremoStateListener(), false);
			System.out.println("submitted with sopremoID " + this.currentSopremoID);
			// sopremoClient.close();
		} catch (final IOException ioe) {
			this.configureClient(this.cmd); // resets
			throw ioe;
		} finally {
			inputStream.close();
		}
	}

	/**
	 * Returns the paths to JSON files
	 * 
	 * @return
	 */
	public List<String> getOutputPaths() {
		return Collections.unmodifiableList(this.outputs);
	}

	/**
	 * Returns the path to visualization file
	 * 
	 * @return
	 */
	public String getVisualizationDataURL() {
		return this.visOutput;
	}

	/**
	 * Returns the whole JSON representation of job states
	 * 
	 * @return
	 */
	public String getJobStates() {
		return this.jobStates;
	}

	/**
	 * Returns metadata of sopremoclient
	 * 
	 * @param key
	 * @return
	 */
	public String getMetaData(final String key) {
		System.out.println("returning metadata for " + this.currentSopremoID);
		if (this.currentSopremoID != null)
			try {
				return this.sopremoClient.getMetaData(this.currentSopremoID, key).toString();
			} catch (final Exception e) {
				System.out.println("Error retrieving metadata for " + this.currentSopremoID);
				e.printStackTrace(System.out);
			}
		return null;
	}

	/**
	 * Adds states to JSON string.
	 */
	private void addStates() {
		this.jobStates += "{";
		this.jobStates += "\"jobID\": " + this.currentSopremoID + ",";
		this.jobStates += "\"timestamp\": " + (this.currentTimestamp - this.startTimestamp) + ",";
		this.jobStates += "\"jobstatus\": \"" + this.currentJobStatus + "\"";
		this.jobStates += "}";
	}

	/**
	 * Adds error status.
	 */
	private void addError(final String detail) {
		this.jobStates += ",{";
		this.jobStates += "\"jobID\": " + this.currentSopremoID + ",";
		this.jobStates += "\"timestamp\": " + (this.currentTimestamp - this.startTimestamp) + ",";
		this.jobStates +=
			"\"jobstatus\": \"error occured, not finished job. See /error subpage for more informations.\"";
		this.jobStates += "}";
		ErrorServlet.setError(detail);
	}

	/**
	 * Returns the path to written json file
	 * 
	 * @return path to json file
	 */
	private void extractHDFSWrites() {
		// try to find matches in the entire script using regex
		// sample line that would match: write $correlation to 'hdfs://localhost/user/result.json';
		final Pattern pattern = Pattern.compile("write\\s+\\$\\w+\\s+to\\s+'\\s*(hdfs://[^']+)'\\s*;");
		final Matcher matcher = pattern.matcher(this.meteorScript);
		this.outputs = new LinkedList<String>();
		while (matcher.find())
			this.outputs.add(matcher.group(1));

		// TODO: hack to decide whether the data should be visualized... for now we just search vis_ in the url
		for (final String out : this.outputs)
			if (out.contains("vis_"))
				this.visOutput = out;
	}

	/**
	 * Private class for state listener.
	 * 
	 * @author André Greiner-Petter
	 */
	private class SopremoStateListener extends StateListener {
		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.client.StateListener#stateChanged(eu.stratosphere.sopremo.execution.
		 * ExecutionResponse.ExecutionState, java.lang.String)
		 */
		@SuppressWarnings("incomplete-switch")
		@Override
		public void stateChanged(final ExecutionState executionState, final String detail) {
			System.out.println();
			ClientFrontend.this.currentTimestamp = System.currentTimeMillis();
			switch (executionState) {
			case ENQUEUED:
				ClientFrontend.this.jobStates = "";
				ClientFrontend.this.currentJobStatus = "Submitted script from web frontend.";
				ClientFrontend.this.addStates();
				System.out.print(ClientFrontend.this.currentJobStatus);
				break;
			case RUNNING:
				ClientFrontend.this.currentJobStatus = "Executing script from web frontend.";
				ClientFrontend.this.addStates();
				System.out.print(ClientFrontend.this.currentJobStatus);
				break;
			case FINISHED:
				ClientFrontend.this.currentJobStatus = detail;
				ClientFrontend.this.addStates();
				System.out.print(detail);
				ClientFrontend.this.sopremoClient.close();
				break;
			case ERROR:
				ClientFrontend.this.addError(detail);
				System.out.print(detail);
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
	}
}
