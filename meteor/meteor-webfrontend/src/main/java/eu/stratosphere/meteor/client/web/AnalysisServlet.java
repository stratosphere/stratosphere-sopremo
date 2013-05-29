package eu.stratosphere.meteor.client.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.stratosphere.meteor.client.common.MeteorContextHandler;

/**
 * 
 * The analysis servlet with textarea for meteor script.
 * 
 * @author André Greiner-Petter
 *
 */
public class AnalysisServlet extends AbstractServletGUI {
	
	/**
	 * generated UID
	 */
	private static final long serialVersionUID = -2766348331251047178L;
	
	/** Input **/
	private String script = "";
	
	/**
	 * Construct a new servlet with empty input
	 */
	public AnalysisServlet(){
		super( "meteor" );
		addStylesheet( "meteorFrontend.css" );
	}
	
	@Override
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		// get input
		@SuppressWarnings("unchecked")
		Map<String, String[]> map = request.getParameterMap();
		if ( map.isEmpty() ) return;
		
		// save input
		script = map.get("meteorInput")[0];
		
		// update output servlets
		MeteorContextHandler.update( script );
		
		// actualize start page (reload all visual sub pages)
		response.sendRedirect("");
	}

	@Override
	protected void writePage(PrintWriter writer) {
		writer.println("<form action=\"\" method=\"post\" target=\"_parent\" accept-charset=\"UTF-8\">");
		writer.println("  <div class=\"main\">");
		writer.println("    <h1>Analysis Program</h1>");
		
		// textarea with meteor script
		// TODO ACE editor?
		writer.println("    <div align=\"center\" class=\"inputScript\">");
		writer.print("      <textarea name=\"meteorInput\" class=\"script\" wrap=\"off\" placeholder=\"Type your meteor script here!\">");
		writer.print( script );
		writer.println("</textarea></div>");
		
		writer.println("    <div class=\"footer\" align=\"right\"><input type=\"submit\" class=\"Button\" value=\"submit\"/></div>");
		writer.println("  </div>");
		writer.println("</form>");
	}
}
