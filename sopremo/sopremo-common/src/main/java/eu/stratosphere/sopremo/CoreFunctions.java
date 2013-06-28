package eu.stratosphere.sopremo;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.stratosphere.nephele.fs.Path;
import eu.stratosphere.sopremo.aggregation.FixedTypeTransitiveAggregation;
import eu.stratosphere.sopremo.aggregation.MaterializingAggregation;
import eu.stratosphere.sopremo.aggregation.TransitiveAggregation;
import eu.stratosphere.sopremo.cache.ArrayCache;
import eu.stratosphere.sopremo.cache.NodeCache;
import eu.stratosphere.sopremo.cache.PatternCache;
import eu.stratosphere.sopremo.expressions.ArithmeticExpression;
import eu.stratosphere.sopremo.expressions.ConstantExpression;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.TernaryExpression;
import eu.stratosphere.sopremo.expressions.ArithmeticExpression.ArithmeticOperator;
import eu.stratosphere.sopremo.expressions.ComparativeExpression;
import eu.stratosphere.sopremo.function.ExpressionFunction;
import eu.stratosphere.sopremo.function.SopremoFunction;
import eu.stratosphere.sopremo.function.SopremoFunction1;
import eu.stratosphere.sopremo.function.SopremoFunction2;
import eu.stratosphere.sopremo.function.SopremoFunction3;
import eu.stratosphere.sopremo.function.SopremoVarargFunction;
import eu.stratosphere.sopremo.function.SopremoVarargFunction1;
import eu.stratosphere.sopremo.io.JsonParseException;
import eu.stratosphere.sopremo.io.JsonParser;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.packages.BuiltinProvider;
import eu.stratosphere.sopremo.tokenizer.RegexTokenizer;
import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.BooleanNode;
import eu.stratosphere.sopremo.type.CachingArrayNode;
import eu.stratosphere.sopremo.type.IArrayNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.INumericNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.IStreamNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.MissingNode;
import eu.stratosphere.sopremo.type.NullNode;
import eu.stratosphere.sopremo.type.ObjectNode;
import eu.stratosphere.sopremo.type.TextNode;


/**
 * Core functions.
 * 
 * @author Arvid Heise
 */
public class CoreFunctions implements BuiltinProvider {
	public static final CONCAT CONCAT = new CONCAT();

	@Name(verb = "concat", noun = "concatenation")
	public static class CONCAT extends FixedTypeTransitiveAggregation<TextNode> {
		CONCAT() {
			super("concat", new TextNode());
		}

		@Override
		protected void aggregateInto(TextNode aggregator, IJsonNode element) {
			aggregator.append((TextNode) element);
		}
	};

	/**
	 * Repeatedly applies the {@link ArithmeticOperator#ADDITION} to the
	 * children of the given node.
	 */
	public static final SUM SUM = new SUM();

	@Name(verb = "sum", noun = "sum")
	public static class SUM extends TransitiveAggregation<INumericNode> {
		SUM() {
			super("sum", IntNode.ZERO);
		}

		private final transient NodeCache nodeCache = new NodeCache();

		@Override
		protected INumericNode aggregate(INumericNode aggregator,
				IJsonNode element) {
			return ArithmeticExpression.ArithmeticOperator.ADDITION.evaluate(
					aggregator, (INumericNode) element, this.nodeCache);
		}
	};

	public static final COUNT COUNT = new COUNT();

	@Name(verb = "count", noun = "count")
	public static class COUNT extends FixedTypeTransitiveAggregation<IntNode> {
		COUNT() {
			super("count", IntNode.ZERO);
		}

		@Override
		protected void aggregateInto(IntNode aggregator, IJsonNode element) {
			aggregator.increment();
		}
	};

	public static final FIRST FIRST = new FIRST();

	@Name(noun = "first")
	public static class FIRST extends TransitiveAggregation<IJsonNode> {
		FIRST() {
			super("first", NullNode.getInstance());
		}

		@Override
		protected IJsonNode aggregate(IJsonNode aggregator, IJsonNode element) {
			return aggregator == NullNode.getInstance() ? element : aggregator;
		}
	};

	public static final SORT SORT = new SORT();

	@Name(verb = "sort")
	public static class SORT extends MaterializingAggregation {
		SORT() {
			super("sort");
		}

		private final transient ArrayCache<IJsonNode> arrayCache = new ArrayCache<IJsonNode>(
				IJsonNode.class);

		@Override
		protected IJsonNode processNodes(
				final CachingArrayNode<IJsonNode> nodeArray) {
			final IJsonNode[] nodes = nodeArray.toArray(this.arrayCache);
			Arrays.sort(nodes);
			nodeArray.setAll(nodes);
			return nodeArray;
		}
	};

	public static final ALL ALL = new ALL();

	@Name(adjective = "all")
	public static class ALL extends MaterializingAggregation {
		ALL() {
			super("all");
		}
	};

	@Name(noun = "mean")
	public static final SopremoFunction MEAN = new ExpressionFunction(1, new TernaryExpression(EvaluationExpression.VALUE,
			new ArithmeticExpression(SUM.asExpression(),ArithmeticOperator.DIVISION, COUNT.asExpression()),
			ConstantExpression.MISSING));

	public static final MIN MIN = new MIN();

	@Name(noun = "min")
	public static class MIN extends TransitiveAggregation<IJsonNode> {
		MIN() {
			super("min", NullNode.getInstance());
		}

		@Override
		public IJsonNode aggregate(final IJsonNode aggregator,
				final IJsonNode node) {
			if (aggregator == NullNode.getInstance())
				return node.clone();
			else if (ComparativeExpression.BinaryOperator.LESS.evaluate(node,
					aggregator))
				return node;
			return aggregator;
		}
	};

	public static final MAX MAX = new MAX();

	@Name(noun = "max")
	public static class MAX extends TransitiveAggregation<IJsonNode> {
		MAX() {
			super("max", NullNode.getInstance());
		}

		@Override
		public IJsonNode aggregate(final IJsonNode aggregator,
				final IJsonNode node) {
			if (aggregator == NullNode.getInstance())
				return node.clone();
			else if (ComparativeExpression.BinaryOperator.LESS.evaluate(
					aggregator, node))
				aggregator.copyValueFrom(node);
			return aggregator;
		}
	};

	/**
	 * Creates a new array by combining sparse array information.<br />
	 * For example: [[0, "a"], [3, "d"], [2, "c"]] -&lt; ["a", missing, "c",
	 * "d"]
	 */
	public static final ASSEMBLE_ARRAY ASSEMBLE_ARRAY = new ASSEMBLE_ARRAY();

	@Name(verb = "assemble")
	public static class ASSEMBLE_ARRAY extends
			FixedTypeTransitiveAggregation<ArrayNode<IJsonNode>> {
		ASSEMBLE_ARRAY() {
			super("assemble", new ArrayNode<IJsonNode>());
		}

		@Override
		protected void aggregateInto(ArrayNode<IJsonNode> aggregator,
				IJsonNode element) {
			IArrayNode<?> part = (IArrayNode<?>) element;
			aggregator.add(((INumericNode) part.get(0)).getIntValue(),
					part.get(1));
		}
	};

	/**
	 * Adds the specified node to the array at the given index
	 * 
	 * @param array
	 *            the array that should be extended
	 * @param index
	 *            the position of the insert
	 * @param node
	 *            the node to add
	 * @return array with the added node
	 */
	public static final ADD ADD = new ADD();

	@Name(verb = "add")
	public static class ADD extends
			SopremoFunction3<IArrayNode<IJsonNode>, IntNode, IJsonNode> {
		ADD() {
			super("add");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * eu.stratosphere.sopremo.function.SopremoFunction3#call(eu.stratosphere
		 * .sopremo.type.IJsonNode, eu.stratosphere.sopremo.type.IJsonNode,
		 * eu.stratosphere.sopremo.type.IJsonNode)
		 */
		@Override
		protected IJsonNode call(final IArrayNode<IJsonNode> array,
				final IntNode index, final IJsonNode node) {
			array.add(resolveIndex(index.getIntValue(), array.size()), node);
			return array;
		}
	};

	public static final CAMEL_CASE CAMEL_CASE = new CAMEL_CASE();

	@Name(noun = "camelCase")
	public static class CAMEL_CASE extends SopremoFunction1<TextNode> {
		CAMEL_CASE() {
			super("camelCase");
		}

		private final transient StringBuilder builder = new StringBuilder();

		private final transient TextNode result = new TextNode();

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * eu.stratosphere.sopremo.function.SopremoFunction3#call(eu.stratosphere
		 * .sopremo.type.IJsonNode, eu.stratosphere.sopremo.type.IJsonNode,
		 * eu.stratosphere.sopremo.type.IJsonNode)
		 */
		@Override
		protected IJsonNode call(final TextNode input) {
			this.builder.append(input);

			boolean capitalize = true;
			for (int index = 0, length = this.builder.length(); index < length; index++) {
				final char ch = this.builder.charAt(index);
				if (Character.isWhitespace(ch))
					capitalize = true;
				else if (capitalize) {
					this.builder.setCharAt(index, Character.toUpperCase(ch));
					capitalize = false;
				} else {
					final char lowerCh = Character.toLowerCase(ch);
					if (lowerCh != ch)
						this.builder.setCharAt(index, lowerCh);
				}
			}
			this.result.setValue(this.builder);
			return this.result;
		}
	};

	public static final SopremoFunction EXTRACT = new EXTRACT()
			.withDefaultParameters(NullNode.getInstance());

	@Name(verb = "extract")
	public static class EXTRACT extends
			SopremoFunction3<TextNode, TextNode, IJsonNode> {
		EXTRACT() {
			super("extract");
		}

		private final transient PatternCache patternCache = new PatternCache();

		private final transient TextNode stringResult = new TextNode();

		private final transient CachingArrayNode<IJsonNode> arrayResult = new CachingArrayNode<IJsonNode>();

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * eu.stratosphere.sopremo.function.SopremoFunction3#call(eu.stratosphere
		 * .sopremo.type.IJsonNode, eu.stratosphere.sopremo.type.IJsonNode,
		 * eu.stratosphere.sopremo.type.IJsonNode)
		 */
		@Override
		protected IJsonNode call(final TextNode input, final TextNode pattern,
				final IJsonNode defaultValue) {
			final Pattern compiledPattern = this.patternCache
					.getPatternOf(pattern);
			final Matcher matcher = compiledPattern.matcher(input
					);

			if (!matcher.find())
				return defaultValue;

			if (matcher.groupCount() == 0) {
				this.stringResult.setValue(matcher.group(0));
				return this.stringResult;
			}

			if (matcher.groupCount() == 1) {
				this.stringResult.setValue(matcher.group(1));
				return this.stringResult;
			}

			this.arrayResult.clear();
			for (int index = 1; index <= matcher.groupCount(); index++) {
				TextNode group = (TextNode) this.arrayResult.reuseUnusedNode();
				if (group == null)
					this.arrayResult.add(group = new TextNode());
				group.setValue(matcher.group(index));
			}
			return this.arrayResult;
		}
	};//

	public static final FORMAT FORMAT = new FORMAT();

	@Name(noun = "format", verb = "format")
	public static class FORMAT extends SopremoVarargFunction1<TextNode> {
		FORMAT() {
			super("format");
		}

		private final transient TextNode result = new TextNode();

		private final transient ArrayCache<Object> arrayCache = new ArrayCache<Object>(
				Object.class);

		/*
		 * (non-Javadoc)
		 * 
		 * @see eu.stratosphere.sopremo.function.SopremoVarargFunction1#call(eu.
		 * stratosphere.sopremo.type.IJsonNode,
		 * eu.stratosphere.sopremo.type.IArrayNode<IJsonNode>)
		 */
		@Override
		protected IJsonNode call(TextNode format, IArrayNode<IJsonNode> varargs) {
			final Object[] paramsAsObjects = this.arrayCache.getArray(varargs
					.size());
			for (int index = 0; index < paramsAsObjects.length; index++)
				paramsAsObjects[index] = varargs.get(index).toString();

			this.result.clear();
			this.result.asFormatter().format(format.toString(),
					paramsAsObjects);
			return this.result;
		}
	};

	public static final SUBTRACT SUBTRACT = new SUBTRACT();

	@Name(verb = "subtract")
	public static class SUBTRACT extends
			SopremoVarargFunction1<IArrayNode<IJsonNode>> {
		SUBTRACT() {
			super("subtract");
		}

		private final transient HashSet<IJsonNode> filterSet = new HashSet<IJsonNode>();

		private final transient IArrayNode<IJsonNode> result = new ArrayNode<IJsonNode>();

		/*
		 * (non-Javadoc)
		 * 
		 * @see eu.stratosphere.sopremo.function.SopremoVarargFunction1#call(eu.
		 * stratosphere.sopremo.type.IJsonNode,
		 * eu.stratosphere.sopremo.type.IArrayNode<IJsonNode>)
		 */
		@Override
		protected IJsonNode call(IArrayNode<IJsonNode> input,
				IArrayNode<IJsonNode> elementsToRemove) {
			this.filterSet.clear();
			for (IJsonNode elementToFilter : elementsToRemove)
				this.filterSet.add(elementToFilter);

			this.result.clear();
			for (int index = 0; index < input.size(); index++)
				if (!this.filterSet.contains(input.get(index)))
					this.result.add(input.get(index));
			return this.result;
		}
	};

	public static SopremoFunction LIKE = new LIKE().withDefaultParameters(TextNode.valueOf(""));

	@Name(noun = "like")
	public static class LIKE extends SopremoFunction2<TextNode, TextNode> {

		private static final transient String PLACEHOLDER = "%%";

		private static final transient String REGEX = ".*";

		LIKE() {
			super("like");
		}

		@Override
		protected IJsonNode call(TextNode inputNode, TextNode patternNode) {
			String pattern = patternNode.toString().replaceAll(PLACEHOLDER, REGEX);
			String value = inputNode.toString();

			return BooleanNode.valueOf(value.matches(pattern));
		}

	};
	
	public static final LENGTH LENGTH = new LENGTH();

	@Name(noun = "length")
	public static class LENGTH extends SopremoFunction1<TextNode> {
		LENGTH() {
			super("length");
		}

		private final transient IntNode result = new IntNode();

		@Override
		protected IJsonNode call(TextNode node) {
			this.result.setValue(node.length());
			return this.result;
		}
	};

	public static final SopremoFunction REPLACE = new REPLACE()
			.withDefaultParameters(TextNode.EMPTY_STRING);

	@Name(verb = "replace")
	public static class REPLACE extends
			SopremoFunction3<TextNode, TextNode, TextNode> {
		REPLACE() {
			super("replace");
		}

		private final transient PatternCache patternCache = new PatternCache();

		private final transient TextNode result = new TextNode();

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * eu.stratosphere.sopremo.function.SopremoFunction3#call(eu.stratosphere
		 * .sopremo.type.IJsonNode, eu.stratosphere.sopremo.type.IJsonNode,
		 * eu.stratosphere.sopremo.type.IJsonNode)
		 */
		@Override
		protected IJsonNode call(final TextNode input, final TextNode search,
				final TextNode replace) {
			final Pattern compiledPattern = this.patternCache
					.getPatternOf(search);
			final Matcher matcher = compiledPattern.matcher(input
					);
			this.result.setValue(matcher.replaceAll(replace.toString()));
			return this.result;
		}
	};

	private static final TextNode WHITESPACES = TextNode
			.valueOf("\\p{javaWhitespace}+");

	public static final SopremoFunction SPLIT = new SPLIT()
			.withDefaultParameters(WHITESPACES);

	@Name(verb = "split")
	public static class SPLIT extends SopremoFunction2<TextNode, TextNode> {
		SPLIT() {
			super("split");
		}

		private final transient PatternCache patternCache = new PatternCache();

		private final transient CachingArrayNode<TextNode> result = new CachingArrayNode<TextNode>();

		private final transient Map<Pattern, RegexTokenizer> tokenizers =
			new IdentityHashMap<Pattern, RegexTokenizer>();

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * eu.stratosphere.sopremo.function.SopremoFunction3#call(eu.stratosphere
		 * .sopremo.type.IJsonNode, eu.stratosphere.sopremo.type.IJsonNode,
		 * eu.stratosphere.sopremo.type.IJsonNode)
		 */
		@Override
		protected IJsonNode call(final TextNode input,
				final TextNode splitString) {
			final Pattern searchPattern = this.patternCache
					.getPatternOf(splitString);
			RegexTokenizer regexTokenizer = this.tokenizers.get(searchPattern);
			if (regexTokenizer == null)
				this.tokenizers.put(searchPattern,
						regexTokenizer = new RegexTokenizer(searchPattern));
			regexTokenizer.tokenizeInto(input, this.result);
			return this.result;
		}
	};

	public static final SopremoFunction SUBSTRING = new SUBSTRING()
			.withDefaultParameters(new IntNode(-1));;

	@Name(noun = "substring")
	public static class SUBSTRING extends
			SopremoFunction3<TextNode, IntNode, IntNode> {
		SUBSTRING() {
			super("substring");
		}

		private final transient TextNode result = new TextNode();

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * eu.stratosphere.sopremo.function.SopremoFunction3#call(eu.stratosphere
		 * .sopremo.type.IJsonNode, eu.stratosphere.sopremo.type.IJsonNode,
		 * eu.stratosphere.sopremo.type.IJsonNode)
		 */
		@Override
		protected IJsonNode call(final TextNode input, final IntNode from,
				final IntNode to) {
			final int length = input.length();
			final int fromPos = resolveIndex(from.getIntValue(), length);
			final int toPos = resolveIndex(to.getIntValue(), length);
			this.result.setValue(input, fromPos, toPos);
			return this.result;
		}
	};

	public static final TRIM TRIM = new TRIM();

	@Name(verb = "trim")
	public static class TRIM extends SopremoFunction1<TextNode> {
		TRIM() {
			super("trim");
		}

		private final transient TextNode result = new TextNode();

		@Override
		protected IJsonNode call(final TextNode input) {
			int start = 0, end = input.length() - 1;
			while (start < end && input.charAt(start) == ' ')
				start++;
			while (end > start && input.charAt(end) == ' ')
				end--;
			this.result.setValue(input, start, end + 1);
			return this.result;
		}
	};// .withDefaultParameters(new IntNode(-1));

	public static final UNION_ALL UNION_ALL = new UNION_ALL();

	@Name(verb = "unionAll")
	public static class UNION_ALL extends SopremoVarargFunction {
		UNION_ALL() {
			super("unionAll", 0);
		}

		private final transient IArrayNode<IJsonNode> union = new ArrayNode<IJsonNode>();

		@Override
		public IJsonNode call(final IArrayNode<IJsonNode> params) {
			this.union.clear();
			for (final IJsonNode param : params)
				for (final IJsonNode child : (IStreamNode<?>) param)
					this.union.add(child);
			return this.union;
		}
	};

	@Name(verb = "setWorkingDirectory")
	public static MissingNode setWorkingDirectory(TextNode node) {
		String path = node.toString();
		if (!path.startsWith("hdfs://"))
			path = new File(path).toURI().toString();
		SopremoEnvironment.getInstance().getEvaluationContext().setWorkingPath(new Path(path));
		return MissingNode.getInstance();
	}

	private static int resolveIndex(final int index, final int size) {
		if (index < 0)
			return size + index;
		return index;
	}

	/*
	 * check up how many times a String is in the source(TextNode) inclusive
	 */
	public static final OCCURRENCES OCCURRENCES = new OCCURRENCES();

	@Name(noun = "occurrences")
	public static class OCCURRENCES extends SopremoFunction2<TextNode, IStreamNode<IJsonNode>> {
		OCCURRENCES() {
			super("occurrences");
		}

		private final transient IntNode result = new IntNode();

		@Override
		protected IJsonNode call(TextNode source, IStreamNode<IJsonNode> input) {
			String str = source.toString().toLowerCase();
			int sumcount = 0;
			for (IJsonNode node : input) {
				if (node instanceof TextNode) {
					String findStr = ((TextNode)node).toString().toLowerCase();
					int lastIndex = 0;
					int count = 0;
					while (lastIndex != -1) {

						lastIndex = str.indexOf(findStr, lastIndex);

						if (lastIndex != -1) {
							count++;
							lastIndex += findStr.length();
						}
					}
					sumcount += count;
				}
			}
			this.result.setValue(sumcount);
			return this.result;
		}
	};
	
	public static final WEEKOFYEAR WEEKOFYEAR = new WEEKOFYEAR();

	@Name(noun = "weekOfYear")
	public static class WEEKOFYEAR extends SopremoFunction2<TextNode, TextNode> {
		WEEKOFYEAR() {
			super("weekOfYear");
		}

		private final transient IntNode result = new IntNode();

		@Override
		protected IJsonNode call(TextNode input, TextNode dateformat) {
			String tmpIn = input.toString();

			wov(this.result, tmpIn, dateformat.toString());
			return this.result;
		}
		
		private void wov(IntNode output, String input, String dateformat) {
			// other format for given Date will be illegal
			DateFormat formatter = new SimpleDateFormat(dateformat);
			Date date;
			SimpleDateFormat f;

			try {// we parse a String/text into a Date

				date = formatter.parse(input);

				/*
				 * SimpleDateFormat f = new SimpleDateFormat("YYYY-'W'ww-u");you can
				 * also define another TimeFormat in caseSimpleDateFormat sdf = new
				 * SimpleDateFormat("dd/MM/yyyy");
				 */
				f = new SimpleDateFormat("w");
				final String s = f.format(date).intern();

				output.setValue(Integer.parseInt(s));

			} catch (ParseException e1) {
				System.out.println("Exception :" + e1);
				System.out
						.println("Exception : Given Text does not fit our Date-Format.");
			}
		}
	};
	
	
	public static final GETYEAR GETYEAR = new GETYEAR();

	@Name(noun = "getYear")
	public static class GETYEAR extends SopremoFunction2<TextNode, TextNode> {
		GETYEAR() {
			super("getYear");
		}

		private final transient IntNode result = new IntNode();

		@Override
		protected IJsonNode call(TextNode input, TextNode dateformat) {
			String tmpIn = input.toString();

			DateFormat formatter = new SimpleDateFormat(dateformat.toString());
			Date date;
			SimpleDateFormat f;

			try {
				date = formatter.parse(tmpIn);

				// SimpleDateFormat f = new SimpleDateFormat("YYYY-'W'ww-u");

				f = new SimpleDateFormat("yyyy");
				final String s = f.format(date).intern();

				this.result.setValue(Integer.parseInt(s));
				// System.out.println(result);
			} catch (ParseException e1) {
				System.out.println("Exception :" + e1);
				System.out.println("Exception : Given Text does not fit our Date-Format.");
			}
			return this.result;
		}
	};
	
	public static final FORMATDATE FORMATDATE = new FORMATDATE();

	@Name(noun = "formatDate")
	public static class FORMATDATE extends SopremoFunction3<TextNode, TextNode, TextNode> {
		FORMATDATE() {
			super("formatDate");
		}

		private final transient TextNode result = new TextNode();

		@Override
		protected IJsonNode call(TextNode input, TextNode sourceformat, TextNode targetformat) {
			String tmpIn = input.toString();
			DateFormat parsedf = new SimpleDateFormat(sourceformat.toString());
			DateFormat targetdf = new SimpleDateFormat(targetformat.toString());
			try {
				Date date = parsedf.parse(tmpIn);
				final String s = targetdf.format(date);
				this.result.setValue(s);
			} catch (ParseException e) {
				System.out.println("Exception :" + e);
				System.out.println("Exception : Given Text does not fit our Date-Format.");
			}
			return this.result;
		}
	};
	
	public static final REPLACEMISSING REPLACEMISSING = new REPLACEMISSING();

	@Name(noun = "replaceMissing")
	public static class REPLACEMISSING extends SopremoFunction2<IJsonNode, IJsonNode> {
		REPLACEMISSING() {
			super("replaceMissing");
		}

		@Override
		protected IJsonNode call(IJsonNode input, IJsonNode replacement) {
			if (input == MissingNode.getInstance() || input == NullNode.getInstance()) {
				return replacement;
			}
			return input;
		}
	};
	
	
	public static final PARSEINT PARSEINT = new PARSEINT();

	@Name(noun = "parseInt")
	public static class PARSEINT extends SopremoFunction1<TextNode> {
		PARSEINT() {
			super("parseInt");
		}

		private final transient IntNode result = new IntNode();

		@Override
		protected IJsonNode call(TextNode input) {
			String str = input.toString();
			this.result.setValue(Integer.parseInt(str));
			return this.result;
		}
	};
	//return a DM-data by given dataset ds="abcdef$%&!"
	public static final GETDMDATA GETDMDATA = new GETDMDATA();

	@Name(noun = "getdata")
	public static class GETDMDATA extends SopremoFunction1<TextNode> {
		GETDMDATA() {
			super("getdata");
		}

		private final transient TextNode result = new TextNode();

		@Override
		protected IJsonNode call(TextNode ds) {
			String dataset=ds.toString();
			 this.result.setValue(readDMtoString(dataset));
			 return  this.result;
		}
		
	private static String readDMtoString(String urlString)  {
	    BufferedReader reader = null;
	    String result="";
	    try {
	       //JsonParser jp=new JsonParser(url);	        
	       // IJsonNode out= jp.readValueAsTree();
	       // String outString=out.toString();			       		       
	       // return outString;
            URL url = new URL("http://datamarket.com/api/v1/series.json?ds="+urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));   		        
	        StringBuffer buffer = new StringBuffer();	      
	        int read;
	        char[] chars = new char[1024];
	       
	        while ((read = reader.read(chars)) != -1)
	          buffer.append(chars, 0, read); 
              result=buffer.toString();
       			        
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return result;
	}
	}
	
	public static final JSONCONVERTER JSONCONVERTER = new JSONCONVERTER();

	@Name(noun = "convertJson")
	public static class JSONCONVERTER extends SopremoFunction1<TextNode> {
		JSONCONVERTER() {
			super("convertJson");
		}

		@Override
		protected IJsonNode call(TextNode dmResult) {
			
			String jsonString = dmResult.toString();
			// remove Data Market API call around content
			Pattern pattern = Pattern.compile("jsonDataMarketApi\\(\\[(.+)\\]\\)", Pattern.DOTALL);
			Matcher matcher = pattern.matcher(jsonString);
			if (!matcher.find()) {
				return null;
			}
			String jsoncontent = matcher.group(1);
			JsonParser parser = new JsonParser(jsoncontent);
			
			// send Json content to parser
			IObjectNode obj;
			try {
				obj = (IObjectNode) parser.readValueAsTree();
			} catch (JsonParseException e) {
				return MissingNode.getInstance();
			}

			//getting the columns of file at first
			//to read data following the given hierarchy in Data-market files
			String[] dimensions;
			@SuppressWarnings("unchecked")
			IArrayNode<IObjectNode> columns = (IArrayNode<IObjectNode>) obj.get ("columns");
			dimensions = new String[columns.size()];
			for (int i = 0; i < columns.size(); i++) {
				IObjectNode subcolumn = columns.get(i);
				IJsonNode column = subcolumn.get("title");				    					
				String titel = column.toString();
				dimensions[i] = titel;
			}				

			// array for the converted data items		
			ArrayNode<IJsonNode> finalJsonArr= new ArrayNode<IJsonNode> ();
			
			// fill target array with the values from the "data" array
			@SuppressWarnings("unchecked")
			IArrayNode<IArrayNode<IJsonNode>> data = (IArrayNode<IArrayNode<IJsonNode>>) obj.get("data");
			for (int j = 0; j < data.size(); j++){
				IArrayNode<IJsonNode> subDataArray = data.get(j);

				IObjectNode extractedObj = new ObjectNode ();
				for (int n = 0; n < subDataArray.size(); n++) {
					if (dimensions[n].equals("Date")){
						extractedObj.put(dimensions[n],handleDateForDatamarket(subDataArray.get(n)));
					} else {						
						extractedObj.put(dimensions[n],subDataArray.get(n));
					}
				}
				finalJsonArr.add(extractedObj);	
			}
			return finalJsonArr;
		}
	};
		
	private static TextNode handleDateForDatamarket(IJsonNode timeIn){
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		SimpleDateFormat f=new SimpleDateFormat("dd-MMM-yyyy");;
		String time = "";
		try {
			date = formatter.parse(timeIn.toString());
			time = f.format(date).intern();
			
		} catch (ParseException e1) {
			System.out.println("Exception :" + e1);
			System.out.println("Exception : Given Text does not fit our Date-Format.");
		}
		return new TextNode(time);
	}

}