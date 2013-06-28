package eu.stratosphere.sopremo.type;

import it.unimi.dsi.fastutil.chars.CharArrayList;

import java.io.IOException;
import java.util.Formatter;
import java.util.Locale;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * This node represents a string value.
 * 
 * @author Michael Hopstock
 * @author Tommy Neubert
 */
@DefaultSerializer(TextNode.TextNodeSerializer.class)
public class TextNode extends AbstractJsonNode implements IPrimitiveNode, CharSequence, Appendable {

	public static class TextNodeSerializer extends ReusingSerializer<TextNode> {
		/*
		 * (non-Javadoc)
		 * @see eu.stratosphere.sopremo.type.ReusingSerializer#read(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Input, java.lang.Object, java.lang.Class)
		 */
		@Override
		public TextNode read(Kryo kryo, Input input, TextNode oldInstance, Class<TextNode> type) {
			final String string = input.readString();
			if (oldInstance == null)
				return new TextNode(string);
			oldInstance.value.clear();
			oldInstance.value.addElements(0, string.toCharArray());
			return oldInstance;
		}

		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#write(com.esotericsoftware.kryo.Kryo,
		 * com.esotericsoftware.kryo.io.Output, java.lang.Object)
		 */
		@Override
		public void write(Kryo kryo, Output output, TextNode object) {
			output.writeString(object);
		}

		/*
		 * (non-Javadoc)
		 * @see com.esotericsoftware.kryo.Serializer#copy(com.esotericsoftware.kryo.Kryo, java.lang.Object)
		 */
		@Override
		public TextNode copy(Kryo kryo, TextNode original) {
			return new TextNode(original);
		}
	}

	public final static TextNode EMPTY_STRING = new TextNode("");

	private CharArrayList value = new CharArrayList();

	private transient Formatter formatter;

	/**
	 * Initializes a TextNode which represents an empty String.
	 */
	public TextNode() {
	}

	/**
	 * Initializes a TextNode which represents the given <code>String</code>. To create new TextNodes please
	 * use TextNode.valueOf(<code>String</code>) instead.
	 * 
	 * @param v
	 *        the value that should be represented by this node
	 */
	public TextNode(final CharSequence v) {
		for (int index = 0, count = v.length(); index < count; index++)
			this.value.add(v.charAt(index));
	}

	public Formatter asFormatter() {
		if (this.formatter == null)
			this.formatter = new Formatter(this, Locale.US);
		return this.formatter;
	}

	/**
	 * Creates a new instance of TextNode. This new instance represents the given value.
	 * 
	 * @param v
	 *        the value that should be represented by the new instance
	 * @return the newly created instance of TextNode
	 */
	public static TextNode valueOf(final String v) {
		if (v == null)
			throw new NullPointerException();
		if (v.length() == 0)
			return EMPTY_STRING;
		return new TextNode(v);
	}

	public void setValue(final CharSequence value) {
		this.value.clear();
		for (int index = 0, count = value.length(); index < count; index++)
			this.value.add(value.charAt(index));
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append(this);
	}

	/**
	 * Appends the given String with a leading and ending " to the given StringBuilder.
	 * 
	 * @param sb
	 *        the StringBuilder where the quoted String should be added to
	 * @param content
	 *        the String that should be appended
	 */
	public static void appendQuoted(final StringBuilder appendable, final CharSequence content) {
		appendable.append('"');
		appendable.append(content);
		appendable.append('"');
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;

		final TextNode other = (TextNode) obj;
		return this.value.equals(other.value);
	}

	@Override
	public void clear() {
		this.value.clear();
	}

	public void setLength(int newLength) {
		this.value.size(newLength);
	}

	@Override
	public Class<TextNode> getType() {
		return TextNode.class;
	}

	@Override
	public int compareToSameType(final IJsonNode other) {
		final TextNode otherNode = (TextNode) other;
		final int len1 = this.value.size();
		final int len2 = otherNode.value.size();
		final int n = Math.min(len1, len2);
		final char v1[] = this.value.elements();
		final char v2[] = otherNode.value.elements();

		for (int index = 0; index < n; index++)
			if (v1[index] != v2[index])
				return v1[index] - v2[index];

		return len1 - len2;
	}

	@Override
	public void copyValueFrom(final IJsonNode otherNode) {
		if (this == otherNode)
			return;
		this.checkForSameType(otherNode);
		this.value.clear();
		this.value.addAll(((TextNode) otherNode).value);
	}

	public void setValue(TextNode text, int start, int end) {
		this.value.size(end - start);
		System.arraycopy(text.value.elements(), start, this.value.elements(), 0, end - start);
	}

	public void setValue(CharSequence text, int start, int end) {
		this.value.clear();
		for (int index = start; index < end; index++)
			this.value.add(text.charAt(index));
	}

	@Override
	public int length() {
		return this.value.size();
	}

	@Override
	public char charAt(int index) {
		return this.value.getChar(index);
	}

	@Override
	public CharSequence subSequence(final int start, final int end) {
		return new CharSequence() {

			@Override
			public CharSequence subSequence(int s, int e) {
				return TextNode.this.subSequence(start + e, end - e);
			}

			@Override
			public int length() {
				return end - start;
			}

			@Override
			public char charAt(int index) {
				return TextNode.this.charAt(start + index);
			}

			/*
			 * (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString() {
				return new StringBuilder(this).toString();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Appendable#append(java.lang.CharSequence)
	 */
	@Override
	public Appendable append(CharSequence csq) {
		for (int index = 0, count = csq.length(); index < count; index++)
			this.value.add(csq.charAt(index));
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Appendable#append(java.lang.CharSequence, int, int)
	 */
	@Override
	public Appendable append(CharSequence csq, int start, int end) {
		for (int index = start; index < end; index++)
			this.value.add(csq.charAt(index));
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Appendable#append(java.lang.CharSequence)
	 */
	public Appendable append(TextNode csq) {
		this.value.addAll(csq.value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Appendable#append(java.lang.CharSequence, int, int)
	 */
	public Appendable append(TextNode csq, int start, int end) {
		this.value.addElements(this.value.size(), csq.value.elements(), start, end);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Appendable#append(char)
	 */
	@Override
	public Appendable append(char c) {
		this.value.add(c);
		return this;
	}

	/**
	 * @param number
	 */
	public void append(long number) {
		this.asFormatter().format("%d", number);
	}

}
