package eu.stratosphere.sopremo.type;

import org.junit.Assert;
import org.junit.Test;

public class MissingNodeTest {

	@Test
	public void shouldBeEqualWithItself() {
		Assert.assertEquals(MissingNode.getInstance(), MissingNode.getInstance());
	}

	@Test
	public void shouldNotBeEqualWithAnotherInstance() {
		Assert.assertFalse(MissingNode.getInstance().equals(new MissingNode()));
	}
}
