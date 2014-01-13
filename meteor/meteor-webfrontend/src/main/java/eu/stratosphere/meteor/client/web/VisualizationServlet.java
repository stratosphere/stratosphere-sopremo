package eu.stratosphere.meteor.client.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet creates content of the visualization frame modeled in
 * WebInterfaceServlet.
 * 
 */
public class VisualizationServlet extends AbstractServletGUI {
	private static final long serialVersionUID = -549622799004779557L;

	/**
	 * The link to configure visualization
	 */
	private final String link =
		"http://dopa.dima.tu-berlin.de/admin/structure/block/manage/block/3/configure%3Fdestination%3Dnode/20";

	private String jsonPath = null;

	/**
	 * Default constructor
	 */
	public VisualizationServlet() {
		super("visualization");
		this.addJavaScript("jQueryLibaries");
		this.addStylesheet("meteorFrontend.css");
		this.addStylesheet("visualContents.css");
	}

	/**
	 * Update contents with link to svg graphic and link to configure
	 * visualization
	 * 
	 * @param jsonPath
	 *        to jsonPath
	 */
	public void update(final String jsonPath) {
		this.jsonPath = jsonPath;
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	@Override
	protected void writePage(final PrintWriter writer) {
		writer.println("<div class=\"main\">");
		writer.println("  <h1>Visualization</h1>");

		// if this json script is not visualizable
		if (this.jsonPath == null) {
			writer.println("  <div align=\"center\" class=\"visual\"><h2>nothing to visualize</h2></div>");
			writer.println("</div>");
			return;
		}

		// create timeline and link
		writer.println("  <div align=\"center\" class=\"visual\">");
		writer.print("    <div align=\"center\" id=\"graphdiv2\">");

		// writes an error message to page if writeTimeLine(writer) failed
		try {
			this.writeTimeLine(writer);
		} catch (final UnsupportedEncodingException uee) {
			writer.print("Encoding error of jsonPath occured! (No ISO-8859-1)");
		}

		// footers and link
		writer.println("    </div>");
		writer.println("  </div>");
		writer.println("  <div class=\"footer\" aling=\"left\"><a href=\"" + this.link +
			"\" target=\"_blank\">Configure Visualization</a></div>");
		writer.println("</div>");
	}

	/**
	 * Writes java scripts to visualize the timeline of json script.
	 * 
	 * @param writer
	 * @throws UnsupportedEncodingException
	 *         if path cannot encoded with ISO-8859-1
	 */
	private void writeTimeLine(final PrintWriter writer) throws UnsupportedEncodingException {
		// internal script saves url to json
		writer.println("<script type=\"text/javascript\">");
		writer.print("var graph_data_url = \"http://localhost:8080/hdfs?path=");
		writer.print(java.net.URLEncoder.encode(this.jsonPath, "ISO-8859-1"));
		writer.println("\";");
		writer.println("</script>");
		// internal script visualize timeline
		writer.println("<script type=\"text/javascript\" src=\"js/jsonTimeLine.js\"></script>");
	}
}
