package eu.stratosphere.meteor.client.web;

import java.io.PrintWriter;

/**
 * 
 * Creates a servlet for error messages.
 * 
 * @author André Greiner-Petter
 *
 */
public class ErrorServlet extends AbstractServletGUI {
	private static final long serialVersionUID = 2966049298462105087L;
	
	/**
	 * Error message
	 */
	private static String errorMessage = "No error!";
	
	/**
	 * Creates a new servlet by name error
	 */
	public ErrorServlet() {
		super( "Error" );
		addStylesheet( "jsonHighlightBrushes.css" );
		addStylesheet( "meteorFrontend.css" );
	}
	
	/**
	 * Sets the error message.
	 * @param error
	 */
	public static void setError( String error ){
		ErrorServlet.errorMessage = error;
	}
	
	/**
	 * Resets error message.
	 */
	public static void reset(){
		errorMessage = "No error!";
	}

	@Override
	protected void writePage(PrintWriter writer) {
		writer.println("<div class=\"main\">");
		writer.println( "  <h1>Error</h1>" );
		writer.println( "  <pre class=\"outputScript\">" + errorMessage + "</pre>" );
		writer.println( "  <div class=\"footer\" align=\"left\">Back to start: <a href=\"/\" target=\"_top\">click here!</a></div>" );
		writer.println("</div>");
	}
}
