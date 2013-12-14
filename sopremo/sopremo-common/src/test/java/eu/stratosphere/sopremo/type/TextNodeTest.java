package eu.stratosphere.sopremo.type;

import org.junit.Assert;
import org.junit.Test;

public class TextNodeTest extends JsonNodeTest<TextNode> {
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.EqualVerifyTest#createDefaultInstance(int)
	 */
	@Override
	protected TextNode createDefaultInstance(final int index) {
		return new TextNode(String.valueOf(index));
	}

	@Test
	public void shouldFindIndexOfSubstring() {
		Assert.assertEquals(2, new TextNode("abcdef").indexOf(new TextNode("cdef")));
		Assert.assertEquals(2, new TextNode("abcdef").indexOf(new TextNode("cde")));
		Assert.assertEquals(2, new TextNode("abcdef").indexOf(new TextNode("c")));
		Assert.assertEquals(0, new TextNode("abcdef").indexOf(new TextNode("ab")));
		Assert.assertEquals(0, new TextNode("abcdef").indexOf(new TextNode("")));
		Assert.assertEquals(5, new TextNode("abcdef").indexOf(new TextNode("f")));
	}

	@Test
	public void shouldIndicateAbsenceOfSubstring() {
		Assert.assertEquals(-1, new TextNode("abcdef").indexOf(new TextNode("cdefg")));
		Assert.assertEquals(-1, new TextNode("abcdef").indexOf(new TextNode("ac")));
		Assert.assertEquals(-1, new TextNode("abcdef").indexOf(new TextNode("xab")));
		Assert.assertEquals(-1, new TextNode("abcdef").indexOf(new TextNode("cdf")));
	}
}
