package eu.stratosphere.meteor.client.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.stratosphere.meteor.client.common.HDFSUtil;

/**
 * 
 * Creates a servlet for error messages.
 * 
 * @author mleich
 * 
 */
public class HDFSServelet extends HttpServlet {

	private static final long serialVersionUID = -2138365419974399593L;

	public HDFSServelet() {

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setStatus(HttpServletResponse.SC_OK);

		PrintWriter writer = resp.getWriter();
		String hdfsPath = req.getParameter("path");
		
			try {
				HDFSUtil.getHDFSContent(hdfsPath, writer);
				
			} catch (Exception e) {
				writer.write("Could not read file:" + "\n");
				writer.write(e.getMessage() + "\n");
				e.printStackTrace(writer);
			}
		

	}

}
