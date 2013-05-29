package eu.stratosphere.util.dag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TexTagProvider {

	private GraphTexPrinter.NodeConfiguration config;

	private static String START_TAG = "\\begin{tikzpicture} [>=stealth%s]";

	private static String END_TAG = "\\end{tikzpicture}";

	// private static String EDGE_TAG = "\\draw[->] (%s) -- (%s);";

	private static String DOCUMENT_TEMPLATE = "\\documentclass{scrartcl}\n\\usepackage{tikz}\n\\usetikzlibrary{positioning}\n\\usepackage{varwidth}\n\\begin{document}\n\n%s\n\n\\end{document}";

	public TexTagProvider(GraphTexPrinter.NodeConfiguration config) {
		this.config = config;
	}

	public static enum CONSTANT {
		MINIMUM_OPERATOR_WIDTH("\\minOpWidth", "0pt"),
		OPERATOR_COLOR("\\operatorColor", "black"),
		SINK_COLOR("\\sinkColor", "black"),
		SOURCE_COLOR("\\sourceColor", "black");

		private static String CONSTANT_TAG = "\\newcommand{%s}{%s}";

		private String command;

		private String defaultValue;

		private CONSTANT(String command, String defaultValue) {
			this.command = command;
			this.defaultValue = defaultValue;
		}

		public String getDefaultValue() {
			return this.defaultValue;
		}

		public String createConstant(String value) {
			return String.format(CONSTANT_TAG, this.command, value);
		}

		public String useCommand() {
			return this.command;
		}
	}

	private static enum MACRO {
		SOURCE_ABSOLUTE("source", 3,
				"\\node [draw= " + CONSTANT.SOURCE_COLOR.useCommand()
					+ " , circle] (#1) at (#2) { \\begin{varwidth}{8em} {#3} \\end{varwidth}}"
		),
		SOURCE_RELATIVE("sourceRelativePosition", 3,
				"\\node [draw= " + CONSTANT.SOURCE_COLOR.useCommand()
					+ " , circle, #2] (#1) { \\begin{varwidth}{8em} {#3} \\end{varwidth}}"
		),
		OPERATOR(
				"operator",
				4,
				"\\node [matrix, #2,inner ysep=0pt, minimum size=0pt, fill= "
					+ CONSTANT.OPERATOR_COLOR.useCommand()
					+ " !10, thick, draw] (#1) {\\node[minimum size=0pt, minimum width= "
					+ CONSTANT.MINIMUM_OPERATOR_WIDTH.useCommand()
					+ ", inner ysep=3pt, inner xsep=0pt]{#3};\\\\ \\node[minimum height=1.5em, minimum width= "
					+ CONSTANT.MINIMUM_OPERATOR_WIDTH.useCommand() + ", inner sep=3pt, fill= "
					+ CONSTANT.OPERATOR_COLOR.useCommand() + " !20, draw, font=\\footnotesize]{#4}; \\\\};"
		// "\\node [minimum width=4cm, minimum height=1cm, draw=black, rectangle, #2] (#1) {#3};"
		),
		SINK("sink", 3,
				"\\node [draw= " + CONSTANT.SINK_COLOR.useCommand() + " , circle, #2] (#1) {#3};"
		),
		EDGE("edge", 2,
				"\\draw[->] (#1) -- (#2);"
		);

		private String NEW_COMMAND_TAG = "\\newcommand{\\%s}[%d]{%s}";

		private String macro;

		private String command;

		private MACRO(String command, int parameterNumber, String macroContent) {
			StringBuilder builder = new StringBuilder();
			builder.append(String.format(NEW_COMMAND_TAG, command, parameterNumber, macroContent));
			this.macro = builder.toString();

			StringBuilder commandBuilder = new StringBuilder().append("\\").append(command);
			for (int i = 0; i < parameterNumber; i++) {
				commandBuilder.append("{%s}");
			}
			commandBuilder.append(";");
			this.command = commandBuilder.toString();
		}

		public String useMacro(String id, String pos, String... labels) {
			if (labels.length == 0) {
				return "\t" + String.format(this.command, id, pos);
			} else if (labels.length == 1) {
				return "\t" + String.format(this.command, id, pos, labels[0]);
			} else {
				return "\t" + String.format(this.command, id, pos, labels[0], labels[1]);
			}
		}

		public String getMacro() {
			return this.macro;
		}
	}

	public static abstract class Position {
		public abstract boolean isAbsolute();

		public abstract String getTex();
	}

	public static class AbsolutePosition extends Position {
		private int x;

		private int y;

		public AbsolutePosition(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public String getTex() {
			return this.x + ", " + this.y;
		}

		public boolean isAbsolute() {
			return true;
		}
	}

	public static class RelativePosition extends Position {

		private POSITION_TAG position;

		private String anchor;

		public RelativePosition(POSITION_TAG position, String anchor) {
			this.position = position;
			this.anchor = anchor;
		}

		public String getTex() {
			return this.position.getTex() + "=of {" + this.anchor + "}";
		}

		public boolean isAbsolute() {
			return false;
		}
	}

	public static enum NODE {
		SOURCE("source", MACRO.SOURCE_RELATIVE, MACRO.SOURCE_ABSOLUTE),
		OPERATOR("operator", MACRO.OPERATOR),
		SINK("sink", MACRO.SINK);

		// private static String NODE_TAG_ABSOLUTE = "\\node [draw=%s, %s, %s] (%s) at (%s,%s) {%s};";
		//
		// private static String NODE_TAG_RELATIVE = "\\node [draw=%s, %s, %s] (%s) {%s};";
		private String stringRep;

		private MACRO[] macros;

		private NODE(String stringRep, MACRO... macro) {
			this.stringRep = stringRep;
			this.macros = macro;
		}

		public String getTexRep(String ID, Position position, String... labels) {
			MACRO macro = (position.isAbsolute()) ? this.macros[1] : this.macros[0];
			return macro.useMacro(ID, position.getTex(), labels);
		}

		public String getStringRep() {
			return this.stringRep;
		}
		// public String getTexRep(String ID, String label, POSITION pos, String anchorID) {
		// String tag = String.format(NODE_TAG_RELATIVE, this.color.getTexRep(), this.shape.getTexRep(),
		// pos.getTex(anchorID), ID, label);
		// return tag;
		// }
	}

	public enum POSITION_TAG {
		BELOW("below"), RIGHT("right");

		private String tag;

		private POSITION_TAG(String tag) {
			this.tag = tag;
		}

		public String getTex() {
			return this.tag;
		}
	}

	public String getStartTag(GraphTexPrinter.TexPictureConfiguration config) {
		return String.format(START_TAG, String.valueOf(config.getOptions()));
	}

	public String getEndTag() {
		return END_TAG;
	}

	public String getEdgeTag(String fromID, String toID) {
		return MACRO.EDGE.useMacro(fromID, toID);
	}

	public String getDocumentWithContent(String content) {
		return String.format(DOCUMENT_TEMPLATE, content);
	}

	public void createMacros(Appendable appendable) {
		try {
			this.createConstants(appendable);
			for (MACRO macro : MACRO.values()) {
				appendable.append(macro.getMacro()).append("\n");
			}
			appendable.append("\n");
		} catch (IOException e) {
			// cannot happen since we use a StringBuilder
		}
	}

	private void createConstants(Appendable appendable) throws IOException {
		for (CONSTANT constant : CONSTANT.values()) {
			appendable.append(constant.createConstant(this.config.getValue(constant))).append("\n");
		}
		appendable.append("\n");
	}
}
