package eu.stratosphere.meteor.client.common;

import java.io.File;
import java.io.IOException;

import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import eu.stratosphere.meteor.client.ClientFrontend;
import eu.stratosphere.meteor.client.WebFrontend;
import eu.stratosphere.meteor.client.web.AnalysisServlet;
import eu.stratosphere.meteor.client.web.ErrorServlet;
import eu.stratosphere.meteor.client.web.HDFSServelet;
import eu.stratosphere.meteor.client.web.OutputServlet;
import eu.stratosphere.meteor.client.web.PactVisServlet;
import eu.stratosphere.meteor.client.web.RuntimeStateServlet;
import eu.stratosphere.meteor.client.web.VisualizationServlet;
import eu.stratosphere.configuration.Configuration;
import eu.stratosphere.util.PactConfigConstants;
import eu.stratosphere.sopremo.query.QueryParserException;

/**
 * 
 * This class is the context handler for server and handle all servlets.
 * 
 * @author André Greiner-Petter
 *
 */
public class MeteorContextHandler extends HandlerList {
	/**
	 * Path informations to each servlet
	 */
	public static final String SRC_INPUT  = "/input";
	public static final String SRC_OUTPUT = "/output";
	public static final String SRC_VISUAL = "/visualization";
	public static final String SRC_RUNTIME = "/runtime";
	public static final String SRC_ERROR  = "/error";
	public static final String SRC_HDFS  = "/hdfs";
	public static final String SRC_PACTVIS = "/pactvis";
	
	/** Servlets **/
	private AnalysisServlet analysServlet;
	private static OutputServlet outputServlet;
	private static VisualizationServlet visualServlet;
	private static RuntimeStateServlet runtimeServlet;
	private static ErrorServlet errorServlet;
	private static HDFSServelet hdfsServlet;
	private static PactVisServlet pactvisServlet;
	
	/** Client frontend **/
	private static ClientFrontend client;
	
	/** the job **/
	private static JobThread job;
	
	/** status of job **/
	private static boolean jobInProgress = false;
	
	/**
	 * Construct a new session ServletContextHandler with all servlets.
	 */
	@SuppressWarnings("static-access")
	public MeteorContextHandler( Configuration config, ClientFrontend client ){
		super();
		this.client = client;
		
		// get resources path
		String resources = getAbsolutResourcePath( config );
		
		// dynamic servlets
		outputServlet = new OutputServlet();
		visualServlet = new VisualizationServlet();
		analysServlet = new AnalysisServlet();
		runtimeServlet = new RuntimeStateServlet();
		errorServlet  = new ErrorServlet();
		hdfsServlet = new HDFSServelet();
		pactvisServlet = new PactVisServlet();
		
		// resource handler for static files
		ResourceHandler resourceH = new ResourceHandler();
		resourceH.setDirectoriesListed(false);
		resourceH.setResourceBase( resources );
		resourceH.setWelcomeFiles( new String[]{ "MeteorWeb.html" } );
		
		// context handler with servlets
		ServletContextHandler contextH = new ServletContextHandler();
		contextH.setContextPath("/");
		contextH.setResourceBase( resources );
		
		// add servlets to context
		contextH.addServlet( new ServletHolder( outputServlet ) , SRC_OUTPUT );
		contextH.addServlet( new ServletHolder( visualServlet ), SRC_VISUAL );
		contextH.addServlet( new ServletHolder( analysServlet ), SRC_INPUT );
		contextH.addServlet( new ServletHolder( runtimeServlet ), SRC_RUNTIME );
		contextH.addServlet( new ServletHolder( errorServlet ) , SRC_ERROR );
		contextH.addServlet( new ServletHolder( hdfsServlet ) , SRC_HDFS );
		contextH.addServlet( new ServletHolder( pactvisServlet ) , SRC_PACTVIS );
		
		// add resources and context
		addHandler( resourceH );
		addHandler( contextH );
	}
	
	/**
	 * Check resources folder and get absolute path to resources.
	 * @param config
	 * @return absolute path to resources
	 */
	private String getAbsolutResourcePath( Configuration config ){
		// check if path has already been set from command line
		String resourceDir = config.getString(WebFrontend.RESOURCEDIR, null);
		if (resourceDir != null) {
			return resourceDir;
		}
		
		// get base path of Stratosphere installation
		String basePath = config.getString(PactConfigConstants.STRATOSPHERE_BASE_DIR_PATH_KEY,"");
		
		// get relative resource path
		String resources = config.getString(PactConfigConstants.WEB_ROOT_PATH_KEY,
				PactConfigConstants.DEFAULT_WEB_ROOT_DIR);
		
		// get resource folder
		File resourcesFile;
		if(resources.startsWith("/")) {
			// absolute path
			resourcesFile = new File(resources);
		} else {
			// path relative to base dir
			resourcesFile = new File(basePath+"/"+resources);
		}
		
		// ensure that the directory with the web documents exists
		if (!resourcesFile.exists()) {
			System.err.println("The directory containing the web documents does not exist: "
				+ resourcesFile.getAbsolutePath());
		}
		
		// return absolute path
		return resourcesFile.getAbsolutePath();
	}
	
	/**
	 * Update output and visualization servlet and handle exceptions.
	 */
	public static void update( final String meteorScript ) {
		jobInProgress = true;
		job = new JobThread( meteorScript );
		job.start();
	}
	
	/**
	 * Returns whether the last job finished with an error or not
	 * @return true if an error occurred
	 */
	public static boolean finishedWithError(){
		if ( job == null ) return false;
		return job.errorOccurred;
	}
	
	/**
	 * Returns whether a job is currently running.
	 * @return true if any job runs
	 */
	public static boolean isInProgress(){
		return jobInProgress;
	}
	
	/**
	 * Returns the client handled jobs.
	 * @return ClientFrontend
	 */
	public static ClientFrontend getClient() {
		return client;
	}
	
	/**
	 * 
	 * A inner class to parallelize execution of meteor script.
	 * 
	 * @author André Greiner-Petter
	 *
	 */
	private static class JobThread extends Thread {
		/** script to execute **/
		private final String meteorScript;
		
		/** error occurred **/
		private boolean errorOccurred;
		
		/**
		 * A new thread to execute script parallel
		 * @param meteorScript
		 */
		private JobThread( final String meteorScript ){
			this.meteorScript = meteorScript;
			this.errorOccurred = false;
		}
		
		@Override
		public void run() {
			try { // try to execute the script and update changes
				ErrorServlet.reset();
				client.execute( meteorScript );
				visualServlet.update(client.getVisualizationDataURL());
				outputServlet.update(client.getOutputPaths());
			} catch ( QueryParserException qpe ) { // if cannot parse the script throw the exception to show errorServlet
				ErrorServlet.setError( qpe.getMessage() + "<br/>Error in line: " + qpe.getLine() + " in class QueryParser." );
				errorOccurred = true;
			} catch ( IOException ioe ) { // if cannot read throw exception to show errorServlet
				ErrorServlet.setError( "Cannot read script. Internal error: " + ioe.getMessage() );
				errorOccurred = true;
			} finally { // progress finished finally
				jobInProgress = false;
			}
		} // run
		
	} // inner class JobThread
} // MeteorContextHandler
