package eu.stratosphere.sopremo.serialization;

import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.util.CollectionUtil;

/**
 * This {@link Schema} handles any kind of JsonNode and provides the functionality to save the result of the evaluation
 * of given {@link EvaluationExpression}s explicitly in the resulting PactRecord. The structure of the records is as
 * follows:<br>
 * { &#60result of first expression&#62, &#60result of second expression&#62, ..., &#60source node&#62 }
 * 
 * @author Tommy Neubert
 */
public class GeneralSchema extends AbstractSchema {

	private final List<EvaluationExpression> mappings = new ArrayList<EvaluationExpression>();

	/**
	 * Initializes a new GeneralSchema with the provided {@link EvaluationExpression}s in proper sequence.
	 * 
	 * @param mappings
	 *        {@link EvaluationExpression}s that should be set as mappings
	 */
	public GeneralSchema(final EvaluationExpression... mappings) {
		this(Arrays.asList(mappings));
	}

	/**
	 * Initializes a new GeneralSchema with the provided {@link EvaluationExpression}s. The mappings will be set in the
	 * same sequence as the Iterable provides them.
	 * 
	 * @param mappings
	 *        an Iterable over all {@link EvaluationExpression}s that should be set as mappings.
	 */
	public GeneralSchema(final Collection<EvaluationExpression> mappings) {
		super(mappings.size() + 1, CollectionUtil.setRangeFrom(0, mappings.size()));
		for (final EvaluationExpression exp : mappings)
			this.mappings.add(exp);
	}

	/**
	 * Initializes GeneralSchema.
	 */
	public GeneralSchema() {
		this(new EvaluationExpression[0]);
	}

	/**
	 * Returns a {@link List} of all mappings in this schema
	 * 
	 * @return a List of all mappings
	 */
	public List<EvaluationExpression> getMappings() {
		return this.mappings;
	}

	@Override
	public IntSet indicesOf(final EvaluationExpression expression) {
		final int index = this.mappings.indexOf(expression);
		if (index == -1)
			throw new IllegalArgumentException("Field not found.");
		return IntSets.singleton(index);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.serialization.AbstractSchema#jsonToRecord(eu.stratosphere.sopremo.type.IJsonNode,
	 * eu.stratosphere.sopremo.serialization.SopremoRecord)
	 */
	@Override
	public void jsonToRecord(IJsonNode value, SopremoRecord target) {
		for (int index = 0; index < this.mappings.size(); index++)
			target.addField(this.mappings.get(index).evaluate(value));
		target.addField(value);
		// System.err.println("jtr: " + value + " -> " + target + " " + System.identityHashCode(target) + " " +
		// System.identityHashCode(this) + " " + System.identityHashCode(value) + " " + Thread.currentThread());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * eu.stratosphere.sopremo.serialization.AbstractSchema#recordToJson(eu.stratosphere.sopremo.serialization.SopremoRecord
	 * )
	 */
	@Override
	public IJsonNode recordToJson(SopremoRecord record) {
		final IJsonNode value = record.getField(this.mappings.size());
		// System.err.println("rtj: " + record + " -> " + value + " " + System.identityHashCode(record) + " " +
		// System.identityHashCode(this) + " " + System.identityHashCode(value) + " " + Thread.currentThread());
		return value;
	}
}
