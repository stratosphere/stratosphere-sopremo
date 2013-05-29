package eu.stratosphere.sopremo.serialization;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import eu.stratosphere.sopremo.expressions.ArithmeticExpression;
import eu.stratosphere.sopremo.expressions.ArithmeticExpression.ArithmeticOperator;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.ConstantExpression;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.ObjectAccess;
import eu.stratosphere.sopremo.type.IntNode;

public class NaiveSchemaFactoryTest {

	private NaiveSchemaFactory factory;

	@Before
	public void setUp() {
		this.factory = new NaiveSchemaFactory();
	}

	@Test
	public void shouldCreateDirectSchema() {
		final Set<EvaluationExpression> expressions = new HashSet<EvaluationExpression>();

		final Schema schema = this.factory.create(expressions);

		Assert.assertTrue(schema instanceof DirectSchema);
	}

	@Test
	public void shouldCreateGeneralSchema() {
		final Set<EvaluationExpression> expressions = new HashSet<EvaluationExpression>();
		expressions.add(new ConstantExpression(IntNode.valueOf(42)));
		expressions.add(new ArithmeticExpression(new ArrayAccess(0), ArithmeticOperator.ADDITION, new ArrayAccess(1)));

		final Schema schema = this.factory.create(expressions);

		Assert.assertTrue(schema instanceof GeneralSchema);
	}

	@Test
	public void shouldCreateHeadArraySchemaIfContigious() {
		final Set<EvaluationExpression> accesses = new HashSet<EvaluationExpression>();
		accesses.add(new ArrayAccess(0, 2));
		accesses.add(new ArrayAccess(1, 3));

		final Schema schema = this.factory.create(accesses);

		Assert.assertSame(HeadArraySchema.class, schema.getClass());
		Assert.assertEquals(4, ((HeadArraySchema) schema).getHeadSize());
	}

	@Test
	public void shouldCreateHeadArraySchemaIfNotContigious() {
		final Set<EvaluationExpression> accesses = new HashSet<EvaluationExpression>();
		accesses.add(new ArrayAccess(0, 2));
		accesses.add(new ArrayAccess(4, 5));

		final Schema schema = this.factory.create(accesses);

		Assert.assertSame(HeadArraySchema.class, schema.getClass());
		Assert.assertEquals(6, ((HeadArraySchema) schema).getHeadSize());
	}

	@Test
	public void shouldCreateObjectSchema() {
		final Set<EvaluationExpression> accesses = new HashSet<EvaluationExpression>();
		accesses.add(new ObjectAccess("firstname"));
		accesses.add(new ObjectAccess("lastname"));
		accesses.add(new ObjectAccess("age"));

		final Schema schema = this.factory.create(accesses);

		Assert.assertSame(ObjectSchema.class, schema.getClass());
		Assert.assertEquals(3, ((ObjectSchema) schema).getMappings().size());
	}

	@Test
	public void shouldCreateTailArraySchemaIfContigious() {
		final Set<EvaluationExpression> accesses = new HashSet<EvaluationExpression>();
		accesses.add(new ArrayAccess(-4, -1));

		final Schema schema = this.factory.create(accesses);

		Assert.assertSame(TailArraySchema.class, schema.getClass());
		Assert.assertEquals(4, ((TailArraySchema) schema).getTailSize());
	}

	@Test
	public void shouldCreateTailArraySchemaIfNotContigious() {
		final Set<EvaluationExpression> accesses = new HashSet<EvaluationExpression>();
		accesses.add(new ArrayAccess(-4, -1));
		accesses.add(new ArrayAccess(-6, -5));

		final Schema schema = this.factory.create(accesses);

		Assert.assertSame(TailArraySchema.class, schema.getClass());
		Assert.assertEquals(6, ((TailArraySchema) schema).getTailSize());
	}

	@Test
	public void shouldNotCreateHeadOrTailArraySchemaIfUnknownSize() {
		final Set<EvaluationExpression> accesses = new HashSet<EvaluationExpression>();
		accesses.add(new ArrayAccess(2, -1));

		final Schema schema = this.factory.create(accesses);

		Assert.assertSame(GeneralSchema.class, schema.getClass());
	}
}
