package eu.stratosphere.meteor.client.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.stratosphere.meteor.client.common.MeteorContextHandler;

/**
 * 
 * Creates a servlet for error messages.
 * 
 * @author mleich
 * 
 */
public class PactVisServlet extends HttpServlet {

	private static final long serialVersionUID = -2138365419974399593L;
	
	public PactVisServlet() {}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setStatus(HttpServletResponse.SC_OK);

		PrintWriter writer = resp.getWriter();
		writer.println ("<!DOCTYPE html>\n" +
				"<html>\n<head>\n" +
				"    <meta charset=\"utf-8\">\n" +
				"    <title></title>\n" +
				"    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/nephelefrontend.css\" />\n" +
				"    <link href=\"css/js-graph-it.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
				"    <link href=\"css/pactgraphs_small.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
				"    <script type=\"text/javascript\" src=\"js/jquery.js\"></script>\n" +
				"    <script type=\"text/javascript\" src=\"js/js-graph-it.js\"></script>\n" +
				"    <script type=\"text/javascript\" src=\"js/progressbar.js\"></script>\n" +
				"    <script type=\"text/javascript\" src=\"js/pactgraph.js\"></script>\n" +
				"</head>\n" +
				"<body>\n" +
				"<div class=\"mainHeading\"><h1><img src=\"img/StratosphereLogo.png\" width=\"326\" height=\"100\" alt=\"Stratosphere Logo\" align=\"middle\"/>Pact Visualization</h1></div>\n\n" +
				"    <div style=\"position: relative;\">\n" +
				"      <div id=\"mainCanvas\" class=\"canvas boxed\" style=\"height: 500px;\">\n" +
				"        <div align=\"center\" id=\"progressContainer\" style=\"margin: auto; margin-top: 200px;\"></div>\n" +
				"      </div>\n" +
				"            <div style=\"position: absolute; right: 20px; bottom: 20px;\">\n" +
				"        <input id=\"back_button\" type=\"button\" value=\"&lt; Back\"/>\n\n" +
				"      </div>    </div>\n" +
				"      \n" +
				"      <div id=\"propertyCanvas\" class=\"propertyCanvas\">\n" +
				"      <p class=\"fadedPropertiesText\">Click a node to show the properties...</p></div>\n\n" +
				"    <script type=\"text/javascript\">\n" +
				"        <!--      \n" +
				"        var maxColumnWidth = 350;\n" +
				"        var minColumnWidth = 150;\n" +
				"        var data = " + MeteorContextHandler.getClient().getMetaData("pre.optmized.pact.plan.json") + ";\n" +
				"        $(document).ready(function() {\n" +
				"            \n" +
				"        // create the progress bar that animates the waiting until the plan is\n" +
				"        // retrieved and displayed\n" +
				"        progBar = new ProgressBar(\"progressContainer\", 10);\n" +
				"        progBar.Init();\n" +
				"        progBar.Start();\n" +
				"        \n" +
				"        drawPactPlan(data, true, \"arr.gif\");\n" +
				"        \n" +
				"        });\n" +
				"        </script>\n" +
				"</body>\n" +
				"</html>\n");

	}

}
