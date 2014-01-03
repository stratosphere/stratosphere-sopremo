package eu.stratosphere.meteor.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Server;

import eu.stratosphere.configuration.ConfigConstants;
import eu.stratosphere.configuration.Configuration;
import eu.stratosphere.configuration.GlobalConfiguration;
import eu.stratosphere.meteor.client.common.MeteorContextHandler;

/**
 * Webfrontend of meteor client
 * 
 * @author André Greiner-Petter
 */
public class WebFrontend {

	/**
	 * The log for this class.
	 */
	private static final Log LOG = LogFactory.getLog(WebFrontend.class);

	public static final String RESOURCEDIR = "resource.dir.key";

	/**
	 * This class starts a new jetty server at port 8080 and create a web interface
	 * for meteor client by using java servlets.
	 * 
	 * @param args
	 *        can be null
	 */
	public static void main(String[] args) {
		// get the config directory first
		String configDir = null;
		String resourceDir = null;

		// get configuration path
		if (args.length >= 2 && args[0].equals("-configDir"))
			configDir = args[1];

		// get resource path if set in parameters
		if (args.length >= 4 && args[2].equals("-resourceDir")) {
			resourceDir = args[3];
			// dirty hack... the CLC CLient doesn't like to find options it doesn't know, so we remove resourceDir form
			// args list
			final String[] newargs = new String[args.length - 2];
			System.arraycopy(args, 0, newargs, 0, 2);
			System.arraycopy(args, 4, newargs, 2, args.length - 4);
			args = newargs;
		}

		// stop if configDir doesn't exist
		if (configDir == null) {
			System.err.println("Error: Configuration directory must be specified.\nWebFrontend -configDir <directory>\n");
			System.exit(1);
			return;
		}

		// load the global configuration
		GlobalConfiguration.loadConfiguration(configDir);
		final Configuration config = GlobalConfiguration.getConfiguration();

		// add stratosphere base dir to config
		config.setString(ConfigConstants.STRATOSPHERE_BASE_DIR_PATH_KEY, configDir + "/..");
		if (resourceDir != null)
			config.setString(RESOURCEDIR, resourceDir);

		// get port of configuration
		final int port = config.getInteger(ConfigConstants.WEB_FRONTEND_PORT_KEY, 8080);

		try {
			// starts a new jetty server
			final Server server = new Server(port);

			// add context to server
			server.setHandler(new MeteorContextHandler(config, new ClientFrontend(args)));

			// starts the server
			LOG.info("Starting meteor web frontend server on port " + port + '.');
			server.start();
			server.join();
		} catch (final Exception exc) {
			LOG.error("Unexpected exception: " + exc.getMessage(), exc);
		}
	}

}
