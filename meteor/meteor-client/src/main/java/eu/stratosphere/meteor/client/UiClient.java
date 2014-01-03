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

import eu.stratosphere.configuration.GlobalConfiguration;
import eu.stratosphere.meteor.QueryParser;
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

	public static void main(final String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
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

		final JScrollPane logScrollPane = new JScrollPane(this.logArea);
		logScrollPane.setPreferredSize(new Dimension(400, 200));

		this.fc = new JFileChooser();

		this.openButton = new JButton("Open a File...");
		this.openButton.addActionListener(this);

		this.runButton = new JButton("RunScript...");
		this.runButton.addActionListener(this);

		final JPanel buttonPanel = new JPanel();
		buttonPanel.add(this.openButton);
		buttonPanel.add(this.runButton);

		this.add(buttonPanel, BorderLayout.PAGE_START);
		this.add(logScrollPane, BorderLayout.CENTER);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		final JFrame frame = new JFrame("Meteor UI - Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(new UiClient());

		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		if (e.getSource() == this.openButton) {
			final int returnVal = this.fc.showOpenDialog(UiClient.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.file = this.fc.getSelectedFile();
				this.clearLog();
				this.log("Selected script: " + this.file.getAbsolutePath() + "." + newline);
			} else
				this.log("No script selected." + newline);

			// Handle run button action.
		} else if (e.getSource() == this.runButton) {
			this.configureClient();
			if (this.file == null || !this.file.exists())
				this.log("No file given or does not exist." + newline);
			else {
				final MeteorScriptRun task = new MeteorScriptRun();
				task.execute();
				this.runButton.setEnabled(false);
				this.clearLog();
			}
		}
	}

	public void log(final String logEntry) {
		final Pattern pathPattern = Pattern.compile("/([a-zA-Z_\\d]*/)*[a-zA-Z_\\d]+.[a-zA-Z\\d]+");
		final Matcher m = pathPattern.matcher(logEntry);
		String logEntryWithLinks = logEntry;
		while (m.find()) {
			final String s = m.group(0);
			// TODO handle duplicate links within the same logEntry
			logEntryWithLinks =
				logEntryWithLinks.replace(s, "<font color='red'><a href='" + s + "'>" + s + "</a></font>");
		}
		try {
			this.kit.insertHTML(this.doc, this.doc.getLength(), logEntryWithLinks, 0, 0, null);
		} catch (final BadLocationException e) {
			e.printStackTrace();
		} catch (final IOException e) {
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

		final int updateTime = 1000;
		this.sopremoClient.setUpdateTime(updateTime);

		this.sopremoClient.setServerAddress(new InetSocketAddress("localhost",
			SopremoConstants.DEFAULT_SOPREMO_SERVER_IPC_PORT));

		this.sopremoClient.setExecutionMode(ExecutionMode.RUN_WITH_STATISTICS);
	}

	@Override
	public void hyperlinkUpdate(final HyperlinkEvent event) {
		if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
			if (Desktop.isDesktopSupported()) {
				final Desktop desktop = Desktop.getDesktop();
				if (desktop.isSupported(Desktop.Action.OPEN))
					try {
						final File file = new File(event.getDescription());
						desktop.open(file);
					} catch (final IOException e) {
					}
			}

	}

	class MeteorScriptRun extends SwingWorker<Object, String> {
		private SopremoPlan parseScript(final File file) {

			try {
				return new QueryParser().withInputDirectory(file.getAbsoluteFile().getParentFile()).tryParse(
					new FileInputStream(file));
			} catch (final IOException e) {
				this.publish("Error while parsing script" + UiClient.newline);
				this.publish(e.getMessage() + UiClient.newline);
				return null;
			} catch (final QueryParserException e) {
				this.publish("Error while parsing script" + UiClient.newline);
				this.publish(e.getMessage() + UiClient.newline);
				return null;
			}
		}

		@Override
		protected void process(final List<String> chunks) {
			for (final String entry : chunks)
				UiClient.this.log(entry);
		}

		@Override
		protected String doInBackground() throws Exception {
			final SopremoPlan plan = this.parseScript(UiClient.this.file);
			UiClient.this.sopremoClient.submit(plan, new StateListener() {
				@Override
				public void stateChanged(final ExecutionState executionState, final String detail) {
					switch (executionState) {
					case ENQUEUED:
						MeteorScriptRun.this.publish("Submitted script " + UiClient.this.file.getAbsolutePath() +
							UiClient.newline);
						break;
					case RUNNING:
						MeteorScriptRun.this.publish(UiClient.newline + "Executing script " +
							UiClient.this.file.getAbsolutePath() + UiClient.newline);
						break;
					case FINISHED:
						MeteorScriptRun.this.publish(UiClient.newline + detail + UiClient.newline);
						break;
					case ERROR:
						MeteorScriptRun.this.publish(detail + UiClient.newline);
						break;
					default:
						break;
					}
				}

				@Override
				protected void stateNotChanged(final ExecutionState state, final String detail) {
					MeteorScriptRun.this.publish(".");
				}
			}, true);

			UiClient.this.sopremoClient.close();
			return null;
		}

		@Override
		protected void done() {
			UiClient.this.reenableRunButton();
		}
	}
}