package eu.stratosphere.meteor.client;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import eu.stratosphere.meteor.QueryParser;
import eu.stratosphere.nephele.configuration.GlobalConfiguration;
import eu.stratosphere.sopremo.client.DefaultClient;
import eu.stratosphere.sopremo.client.StateListener;
import eu.stratosphere.sopremo.execution.ExecutionRequest.ExecutionMode;
import eu.stratosphere.sopremo.execution.ExecutionResponse.ExecutionState;
import eu.stratosphere.sopremo.execution.SopremoConstants;
import eu.stratosphere.sopremo.operator.SopremoPlan;
import eu.stratosphere.sopremo.query.QueryParserException;

public class UiClient extends JPanel implements ActionListener, HyperlinkListener {
	private static final long serialVersionUID = -7604717315288253894L;
	static public final String newline = "";
	JButton openButton, runButton;
	JTextPane logArea;
	HTMLEditorKit kit;
    HTMLDocument doc;
	JFileChooser fc;

	private DefaultClient sopremoClient;
	private File file;

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}

	public UiClient() {
		super(new BorderLayout());

		this.logArea = new JTextPane();
	    this.kit = new HTMLEditorKit();
	    this.doc = new HTMLDocument();
	    this.logArea.setEditorKit(this.kit);
	    this.logArea.setDocument(this.doc);
	    this.logArea.setEditable(false);
		this.logArea.addHyperlinkListener(this);

		JScrollPane logScrollPane = new JScrollPane(this.logArea);
		logScrollPane.setPreferredSize( new Dimension( 400, 200 ) );

		this.fc = new JFileChooser();

		this.openButton = new JButton("Open a File...");
		this.openButton.addActionListener(this);

		this.runButton = new JButton("RunScript...");
		this.runButton.addActionListener(this);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(this.openButton);
		buttonPanel.add(this.runButton);

		add(buttonPanel, BorderLayout.PAGE_START);
		add(logScrollPane, BorderLayout.CENTER);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Meteor UI - Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(new UiClient());

		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.openButton) {
			int returnVal = this.fc.showOpenDialog(UiClient.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.file = this.fc.getSelectedFile();
				clearLog();
				log("Selected script: " + this.file.getAbsolutePath() + "." + newline);
			} else {
				log("No script selected." + newline);
			}

			// Handle run button action.
		} else if (e.getSource() == this.runButton) {
			configureClient();
			if (this.file == null || !this.file.exists()) {
				log("No file given or does not exist." + newline);
			} else {
				MeteorScriptRun task = new MeteorScriptRun();
				task.execute();
				this.runButton.setEnabled(false);
				clearLog();
			}
		}
	}

	public void log(String logEntry) {
		Pattern pathPattern = Pattern.compile("/([a-zA-Z_\\d]*/)*[a-zA-Z_\\d]+.[a-zA-Z\\d]+");
		Matcher m = pathPattern.matcher(logEntry);
		String logEntryWithLinks = logEntry;
		while (m.find()) {
		    String s = m.group(0);
		    //TODO handle duplicate links within the same logEntry
		    logEntryWithLinks = logEntryWithLinks.replace(s, "<font color='red'><a href='"+s+"'>"+s+"</a></font>");
		}
	    try {
	    	this.kit.insertHTML(this.doc, this.doc.getLength(), logEntryWithLinks, 0, 0, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void clearLog() {
		this.doc = new HTMLDocument();
	    this.logArea.setDocument(this.doc);
	}

	public void reenableRunButton() {
		this.runButton.setEnabled(true);
	}

	private void configureClient() {
		GlobalConfiguration.loadConfiguration(null);
		this.sopremoClient = new DefaultClient(GlobalConfiguration.getConfiguration());

		int updateTime = 1000;
		this.sopremoClient.setUpdateTime(updateTime);

		this.sopremoClient.setServerAddress(new InetSocketAddress("localhost", SopremoConstants.DEFAULT_SOPREMO_SERVER_IPC_PORT));

		this.sopremoClient.setExecutionMode(ExecutionMode.RUN_WITH_STATISTICS);
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent event) {
		if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				if (desktop.isSupported(Desktop.Action.OPEN)) {
					try {
						File file = new File(event.getDescription());
						desktop.open(file);
					} catch (IOException e) {
					}
				}
			}
		}

	}

	class MeteorScriptRun extends SwingWorker<Object, String> {
		private SopremoPlan parseScript(File file) {

			try {
				return new QueryParser().withInputDirectory(file.getAbsoluteFile().getParentFile()).tryParse(new FileInputStream(file));
			} catch (IOException e) {
				publish("Error while parsing script" + UiClient.newline);
				publish(e.getMessage()+UiClient.newline);
				return null;
			} catch (QueryParserException e){
				publish("Error while parsing script" + UiClient.newline);
				publish(e.getMessage()+UiClient.newline);
				return null;
			}
		}

		@Override
		protected void process(List<String> chunks) {
			for (String entry : chunks) {
				log(entry);
			}
		}

		@Override
		protected String doInBackground() throws Exception {
			final SopremoPlan plan = this.parseScript(UiClient.this.file);
			UiClient.this.sopremoClient.submit(plan, new StateListener() {
				@Override
				public void stateChanged(ExecutionState executionState, String detail) {
					switch (executionState) {
					case ENQUEUED:
						publish("Submitted script " + UiClient.this.file.getAbsolutePath() + UiClient.newline);
						break;
					case RUNNING:
						publish(UiClient.newline + "Executing script " + UiClient.this.file.getAbsolutePath() + UiClient.newline);
						break;
					case FINISHED:
						publish(UiClient.newline + detail + UiClient.newline);
						break;
					case ERROR:
						publish(detail + UiClient.newline);
						break;
					default:
						break;
					}
				}

				@Override
				protected void stateNotChanged(ExecutionState state, String detail) {
					publish(".");
				}
			}, true);

			UiClient.this.sopremoClient.close();
			return null;
		}

		@Override
		protected void done() {
			reenableRunButton();
		}
	}
}