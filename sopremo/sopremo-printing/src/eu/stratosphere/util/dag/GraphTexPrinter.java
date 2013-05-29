package eu.stratosphere.util.dag;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;

import eu.stratosphere.sopremo.operator.CompositeOperator;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.SopremoModule;
import eu.stratosphere.util.dag.GraphLevelPartitioner.Level;
import eu.stratosphere.util.dag.TexTagProvider.CONSTANT;

public class GraphTexPrinter<Node> {

	private boolean GENERATE_COMPLETE_DOKUMENT = false;

	private boolean COMPILE_RESULT = false;

	private String directory = System.getProperty("user.dir") + File.separatorChar;

	private SopremoModule plan;

	private Map<TexTagProvider.NODE, Integer> idMap = new HashMap<TexTagProvider.NODE, Integer>();

	private TexTagProvider tags;

	private TexPictureConfiguration globalConfig;

	private NodeConfiguration nodeConfig;

	private String result;

	public GraphTexPrinter(SopremoModule plan) {
		this.initialize();
		this.plan = plan;
	}

	public void generateCompleteDokument(boolean mode) {
		this.GENERATE_COMPLETE_DOKUMENT = mode;
	}

	public void compileResult(boolean mode) {
		this.COMPILE_RESULT = mode;
	}

	public void configure(TexPictureConfiguration conf) {
		this.globalConfig = conf;
	}

	public void configure(NodeConfiguration config) {
		this.nodeConfig = config;
	}

	public String convert(ConnectionNavigator<Node> navigator) {
		String result = this.convert((Iterable<? extends Node>) this.plan.getAllOutputs(), navigator);
		if (this.COMPILE_RESULT) {
			this.startLatexProcess(result);
		}
		return result;
	}

	public void changeFileDirectory(String newDirectory) {
		this.directory = newDirectory;
	}

	private void startLatexProcess(String tex) {
		String filename = "GeneratedTexFile" + new Date().getTime();
		String texFileEnding = ".tex";
		String pdfFileEnding = ".pdf";
		File texFile = new File(this.directory + filename + texFileEnding);

		try {
			this.writeToFile(texFile, tex);
			this.compileTex(this.directory + filename + texFileEnding);
			this.showPdf(this.directory + filename + pdfFileEnding);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showPdf(String filename) throws IOException {
		ProcessBuilder pdfViewer = new ProcessBuilder("gnome-open", filename);
		Process p = pdfViewer.start();
	}

	private void writeToFile(File file, String tex) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(tex);
		bw.close();
	}

	private void compileTex(String filename) throws IOException {
		ProcessBuilder pb = new ProcessBuilder("pdflatex", "-output-directory", this.directory, filename);
		Process p = pb.start();
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void printProcessOutput(InputStream err) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(err));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
	}

	private void initialize() {
		for (TexTagProvider.NODE nodeType : TexTagProvider.NODE.values()) {
			idMap.put(nodeType, 0);
		}
		this.globalConfig = new TexPictureConfiguration();
		this.nodeConfig = new NodeConfiguration();
		this.generateCompleteDokument(true);
	}

	private String convert(Iterable<? extends Node> startNodes, ConnectionNavigator<Node> navigator) {
		StringBuilder builder = new StringBuilder();
		this.tags = new TexTagProvider(this.nodeConfig);
		try {
			this.print(builder, startNodes.iterator(), navigator);
		} catch (IOException e) {
			// cannot happen since we use a StringBuilder
		}
		return this.generateResult(builder);
	}

	private String generateResult(StringBuilder builder) {
		return (GENERATE_COMPLETE_DOKUMENT) ? this.tags.getDocumentWithContent(builder.toString())
			: builder.toString();
	}

	private void print(Appendable appendable, Iterator<? extends Node> startNodes, ConnectionNavigator<Node> navigator)
			throws IOException {
		this.createMacros(appendable);
		appendable.append(this.tags.getStartTag(this.globalConfig)).append("\n");
		new PrintState(appendable, GraphLevelPartitioner.getLevels(startNodes, navigator)).printDAG(this.globalConfig
			.getOption(TexPictureConfiguration.OPTION.SCALE));
		appendable.append(this.tags.getEndTag());
	}

	private void createMacros(Appendable appendable) {
		this.tags.createMacros(appendable);
	}

	private class PrintState {
		private final Appendable appender;

		private Map<Object, String> ids = new IdentityHashMap<Object, String>();

		private final List<Level<Object>> levels;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		private PrintState(final Appendable builder, final List<Level<Node>> levels) {
			this.appender = builder;
			this.levels = (List) levels;
		}

		private void printDAG(String params) throws IOException {
			for (int levelIndex = 0; levelIndex < this.levels.size(); levelIndex++) {
				final Level<Object> level = this.levels.get(levelIndex);
				for (int sourceIndex = 0; sourceIndex < level.getLevelNodes().size(); sourceIndex++) {
					final Object node = level.getLevelNodes().get(sourceIndex);
					this.printNode(node, sourceIndex, levelIndex, params);
					this.printEdges(level, node, levelIndex);
				}
			}

			// for (int levelIndex = this.levels.size() - 1; levelIndex > 0; levelIndex--) {
			// final Level<Object> level = this.levels.get(levelIndex);
			// for (int sourceIndex = 0; sourceIndex < level.getLevelNodes().size(); sourceIndex++) {
			// final Object node = level.getLevelNodes().get(sourceIndex);
			// this.printEdges(level, node, levelIndex);
			// }
			// }

		}

		private void printEdges(Level<Object> level, Object node, int levelIndex) throws IOException {

			// String id = String.valueOf(levelIndex - 1);
			// String id2 = String.valueOf(levelIndex);

			List<Object> edges = level.getLinks(node);
			for (Object edge : edges) {
				this.appender.append(String.format(GraphTexPrinter.this.tags.getEdgeTag(this.ids.get(edge),
					this.ids.get(node))));
				this.appender.append('\n');
			}
		}

		private void printNode(Object node, int sourceIndex, int levelIndex, String params) throws IOException {
			TexTagProvider.NODE nodeTemplate = this.findCorrectTexTemplate(node, levelIndex);

			// String id = String.valueOf(levelIndex);

			int counter = GraphTexPrinter.this.idMap.get(nodeTemplate);
			String id = this.getNameForNode(node) + "_" + counter;

			// if (levelIndex == 0 && sourceIndex == 0) {
			// this.appender.append(nodeTemplate.getTexRep(id, 0, 2 * (this.levels.size() - 1),
			// node.toString()));
			TexTagProvider.Position position;
			if (levelIndex == 0 && sourceIndex == 0) {
				// position = new TexTagProvider.AbsolutePosition(sourceIndex * 3, 2 * (-levelIndex
				// + this.levels.size() - 1));
				position = new TexTagProvider.AbsolutePosition(0, 0);
			} else {
				String anchorId;
				TexTagProvider.POSITION_TAG positionTag;
				if (sourceIndex == 0) {
					anchorId = this.ids.get(this.levels.get(levelIndex - 1).getLevelNodes().get(sourceIndex));
					positionTag = TexTagProvider.POSITION_TAG.BELOW;
				} else {
					anchorId = this.ids.get(this.levels.get(levelIndex).getLevelNodes().get(sourceIndex - 1));
					positionTag = TexTagProvider.POSITION_TAG.RIGHT;
				}
				position = new TexTagProvider.RelativePosition(positionTag, anchorId);
			}

			if (nodeTemplate == TexTagProvider.NODE.OPERATOR) {
				this.appender.append(nodeTemplate.getTexRep(id, position, this.getNameForNode(node),
					this.escapeMathSymbols(node.toString())));
			} else {
				this.appender.append(nodeTemplate.getTexRep(id, position, this.escapeMathSymbols(node.toString())));
			}
			// } else {
			// TexTagProvider.POSITION pos;
			// String anchorID;
			//
			// if (sourceIndex == 0) {
			// pos = TexTagProvider.POSITION.BELOW;
			// anchorID = this.ids.get(this.levels.get(levelIndex - 1).getLevelNodes().get(0));
			// } else {
			// pos = TexTagProvider.POSITION.RIGHT;
			// anchorID = this.ids.get(this.levels.get(levelIndex).getLevelNodes().get(sourceIndex - 1));
			// }
			//
			// this.appender.append(nodeTemplate.getTexRep(id, node.toString(), pos, anchorID));
			// }
			this.appender.append('\n');

			GraphTexPrinter.this.idMap.put(nodeTemplate, counter + 1);
			this.ids.put(node, id);
		}

		private String escapeMathSymbols(String label) {
			if (label.contains("<"))
				label = label.replaceAll("<", Matcher.quoteReplacement("$<$"));

			if (label.contains(">"))
				label = label.replaceAll(">", Matcher.quoteReplacement("$>$"));

			return label;
		}

		private String getNameForNode(Object node) {
			if (node instanceof ElementaryOperator) {
				return ((ElementaryOperator) node).getName();
			} else if (node instanceof CompositeOperator) {
				return ((CompositeOperator) node).getName();
			} else {
				return "UNKNOWN OPERATOR: this operator is neither an eu.stratosphere.sopremo.operator.ElementaryOperator nor an eu.stratosphere.sopremo.operator.CompositeOperator";
			}
		}

		private TexTagProvider.NODE findCorrectTexTemplate(Object node, int levelIndex) {
			TexTagProvider.NODE nodeTemplate;
			if (levelIndex == 0) {
				nodeTemplate = TexTagProvider.NODE.SOURCE;
			} else {
				boolean isSink = true;
				for (int i = levelIndex + 1; i < levels.size(); i++) {
					for (Object lvlNode : this.levels.get(i).getLevelNodes()) {
						if (this.levels.get(i).getLinks(lvlNode).contains(node)) {
							isSink = false;
							break;
						}
					}
				}
				if (isSink) {
					nodeTemplate = TexTagProvider.NODE.SINK;
				} else {
					nodeTemplate = TexTagProvider.NODE.OPERATOR;
					// // zum Debuggen
					// if (node instanceof ElementaryOperator) {
					// System.out.println(((ElementaryOperator) node).toString());
					// } else if (node instanceof CompositeOperator) {
					// System.out.println(((CompositeOperator) node).toString());
					// }
					// // -------------
				}
			}
			return nodeTemplate;
		}
	}

	public static class TexPictureConfiguration {
		private Map<OPTION, Double> options = new HashMap<OPTION, Double>();

		public enum OPTION {
			NODE_DISTANCE("node distance=%sem"),
			SCALE("scale=%s", "every node/.style={scale=%s}");

			private String[] tex;

			private OPTION(String... tex) {
				this.tex = tex;
			}

			public String getTex(Double param) {
				StringBuilder result = new StringBuilder();
				for (int i = 0; i < tex.length; i++) {
					result.append(String.format(tex[i], String.valueOf(param)));
					if (i != tex.length) {
						result.append(", ");
					}
				}
				return result.toString();
			}
		}

		public void configure(OPTION option, double d) {
			this.options.put(option, d);
		}

		public String getOption(OPTION option) {
			double value = (this.options.containsKey(option)) ? this.options.get(option) : 1;

			return String.format(option.getTex(value));
		}

		public String getOptions() {
			StringBuilder builder = new StringBuilder();
			for (Entry<OPTION, Double> entry : this.options.entrySet()) {
				builder.append(", ").append(entry.getKey().getTex(entry.getValue()));
			}
			return builder.toString();
		}
	}

	public static class NodeConfiguration {
		Map<CONSTANT, String> config = new HashMap<CONSTANT, String>();

		public NodeConfiguration() {
			for (CONSTANT c : CONSTANT.values()) {
				this.config.put(c, c.getDefaultValue());
			}
		}

		public void configure(CONSTANT constant, String value) {
			this.config.put(constant, value);
		}

		public String getValue(CONSTANT con) {
			return this.config.get(con);
		}
	}
}