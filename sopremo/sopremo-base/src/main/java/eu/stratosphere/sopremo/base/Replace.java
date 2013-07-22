package eu.stratosphere.sopremo.base;

import java.io.IOException;

import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.base.replace.AssembleArray;
import eu.stratosphere.sopremo.base.replace.ReplaceBase;
import eu.stratosphere.sopremo.base.replace.ReplaceWithDefaultValue;
import eu.stratosphere.sopremo.base.replace.StrictReplace;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.JsonStreamExpression;
import eu.stratosphere.sopremo.expressions.PathSegmentExpression;
import eu.stratosphere.sopremo.expressions.UnaryExpression;
import eu.stratosphere.sopremo.expressions.UnevaluableExpression;
import eu.stratosphere.sopremo.operator.CompositeOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.OutputCardinality;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.operator.SopremoModule;

@InputCardinality(min = 2, max = 2)
@OutputCardinality(1)
@Name(verb = "replace")
public class Replace extends CompositeOperator<Replace> {

	private PathSegmentExpression replaceExpression = EvaluationExpression.VALUE;

	public final static EvaluationExpression FILTER_RECORDS = new UnevaluableExpression("<filter>");

	private EvaluationExpression dictionaryKeyExtraction = new ArrayAccess(0),
			dictionaryValueExtraction = new ArrayAccess(1),
			defaultExpression = EvaluationExpression.VALUE;

	private boolean arrayElementsReplacement = false;

	public JsonStreamExpression getDictionary() {
		return new JsonStreamExpression(this.getInput(1));
	}

	public Replace withDictionary(JsonStreamExpression dictionary) {
		this.setDictionary(dictionary);
		return this;
	}

	@Property
	@Name(noun = "dictionary", preposition = "with")
	public void setDictionary(JsonStreamExpression dictionary) {
		if (dictionary == null)
			throw new NullPointerException("dictionary must not be null");

		this.setInput(1, dictionary.getStream());
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.CompositeOperator#asModule(eu.stratosphere.sopremo.EvaluationContext)
	 */
	@Override
	public void addImplementation(SopremoModule module, EvaluationContext context) {
		if (this.arrayElementsReplacement) {
			final ArraySplit arraySplit =
				new ArraySplit().
					withArrayPath(this.replaceExpression).
					withSplitProjection(ArraySplit.ResultField.Element, ArraySplit.ResultField.Index,
						ArraySplit.ResultField.Array).
					withInputs(module.getInput(0));

			EvaluationExpression defaultExpression;
			if (this.defaultExpression == EvaluationExpression.VALUE)
				defaultExpression = EvaluationExpression.VALUE;
			else if (this.defaultExpression.equals(FILTER_RECORDS))
				defaultExpression = this.defaultExpression;
			else
				defaultExpression =
					this.defaultExpression.clone().replace(EvaluationExpression.VALUE, new ArrayAccess(0));
			Replace replacedElements = new Replace().
				withName(String.format("%s element", this.getName())).
				withInputs(arraySplit, module.getInput(1)).
				withDefaultExpression(defaultExpression).
				withDictionaryValueExtraction(this.dictionaryValueExtraction).
				withDictionaryKeyExtraction(this.dictionaryKeyExtraction).
				withReplaceExpression(new ArrayAccess(0));

			final AssembleArray arrayDictionary = new AssembleArray().
				withInputs(replacedElements);

			final Replace arrayLookup = new Replace().
				withName(String.format("%s array", this.getName())).
				withInputs(module.getInput(0), arrayDictionary).
				withReplaceExpression(this.replaceExpression).
				withDefaultExpression(FILTER_RECORDS);
			// empty arrays will not be replaced
			Selection emptyArrays = new Selection().
				withCondition(new UnaryExpression(this.replaceExpression, true)).
				withInputs(module.getInput(0));
			module.getOutput(0).setInput(0, new UnionAll().withInputs(arrayLookup, emptyArrays));
		} else {
			EvaluationExpression defaultExpression =
				this.defaultExpression == EvaluationExpression.VALUE ? this.replaceExpression : this.defaultExpression;
			ReplaceBase<?> replaceAtom;
			if (defaultExpression == FILTER_RECORDS)
				replaceAtom = new StrictReplace();
			else
				replaceAtom = new ReplaceWithDefaultValue().withDefaultExpression(defaultExpression);

			replaceAtom.withInputs(module.getInputs()).
				withReplaceExpression(this.replaceExpression).
				withDictionaryValueExtraction(this.dictionaryValueExtraction).
				withKeyExpression(0, this.getReplaceExpression()).
				withKeyExpression(1, this.getDictionaryKeyExtraction());
			module.getOutput(0).setInput(0,
				replaceAtom.withInputs(module.getInput(0), module.getInput(1)));
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Replace other = (Replace) obj;
		return this.arrayElementsReplacement == other.arrayElementsReplacement &&
			this.defaultExpression.equals(other.defaultExpression) &&
			this.dictionaryKeyExtraction.equals(other.dictionaryKeyExtraction) &&
			this.dictionaryValueExtraction.equals(other.dictionaryValueExtraction) &&
			this.replaceExpression.equals(other.replaceExpression);
	}

	public EvaluationExpression getDefaultExpression() {
		return this.defaultExpression;
	}

	public EvaluationExpression getDictionaryKeyExtraction() {
		return this.dictionaryKeyExtraction;
	}

	public EvaluationExpression getDictionaryValueExtraction() {
		return this.dictionaryValueExtraction;
	}

	public PathSegmentExpression getReplaceExpression() {
		return this.replaceExpression;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.arrayElementsReplacement ? 1231 : 1237);
		result = prime * result + this.defaultExpression.hashCode();
		result = prime * result + this.dictionaryKeyExtraction.hashCode();
		result = prime * result + this.dictionaryValueExtraction.hashCode();
		result = prime * result + this.replaceExpression.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.Operator#toString(java.lang.StringBuilder)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		appendable.append(this.getName());
		if (this.isArrayElementsReplacement())
			appendable.append(" all ");
		this.getReplaceExpression().appendAsString(appendable);
		if (this.getInput(1) != null) {
			appendable.append(" with ");
			this.getDictionary().appendAsString(appendable);
		}
		appendable.append(" default ");
		this.getDefaultExpression().appendAsString(appendable);
	}

	public boolean isArrayElementsReplacement() {
		return this.arrayElementsReplacement;
	}

	@Property(flag = true)
	@Name(adjective = "all")
	public void setArrayElementsReplacement(boolean replaceElementsInArray) {
		this.arrayElementsReplacement = replaceElementsInArray;
	}

	@Property
	@Name(noun = "default")
	public void setDefaultExpression(EvaluationExpression defaultExpression) {
		if (defaultExpression == null)
			throw new NullPointerException("defaultExpression must not be null");

		this.defaultExpression = defaultExpression;
	}

	@Property
	public void setDictionaryKeyExtraction(EvaluationExpression dictionaryKeyExtraction) {
		if (dictionaryKeyExtraction == null)
			throw new NullPointerException("dictionaryKeyExtraction must not be null");

		this.dictionaryKeyExtraction = dictionaryKeyExtraction;
	}

	@Property
	public void setDictionaryValueExtraction(EvaluationExpression dictionaryValueExtraction) {
		if (dictionaryValueExtraction == null)
			throw new NullPointerException("dictionaryValueExtraction must not be null");

		this.dictionaryValueExtraction = dictionaryValueExtraction;
	}

	@Property()
	@Name(preposition = "on")
	public void setReplaceExpression(PathSegmentExpression inputKeyExtract) {
		if (inputKeyExtract == null)
			throw new NullPointerException("inputKeyExtract must not be null");

		this.replaceExpression = inputKeyExtract;
	}

	public Replace withArrayElementsReplacement(boolean replaceArrays) {
		this.setArrayElementsReplacement(replaceArrays);
		return this;
	}

	public Replace withDictionaryKeyExtraction(EvaluationExpression dictionaryKeyExtraction) {
		this.setDictionaryKeyExtraction(dictionaryKeyExtraction);
		return this;
	}

	public Replace withDefaultExpression(EvaluationExpression defaultExpression) {
		this.setDefaultExpression(defaultExpression);
		return this;
	}

	public Replace withDictionaryValueExtraction(EvaluationExpression dictionaryValueExtraction) {
		this.setDictionaryValueExtraction(dictionaryValueExtraction);
		return this;
	}

	public Replace withReplaceExpression(PathSegmentExpression inputKeyExtract) {
		this.setReplaceExpression(inputKeyExtract);
		return this;
	}

}