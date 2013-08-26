package eu.stratosphere.sopremo.base;

import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.base.replace.AssembleArray;
import eu.stratosphere.sopremo.base.replace.ReplaceBase;
import eu.stratosphere.sopremo.expressions.ArrayAccess;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.UnaryExpression;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.OutputCardinality;
import eu.stratosphere.sopremo.operator.SopremoModule;

@InputCardinality(min = 2, max = 2)
@OutputCardinality(1)
@Name(verb = "replace all")
public class ReplaceAll extends ReplaceBase<ReplaceAll> {
	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.CompositeOperator#asModule(eu.stratosphere.sopremo.EvaluationContext)
	 */
	@Override
	public void addImplementation(SopremoModule module, EvaluationContext context) {
		final ArraySplit arraySplit =
			new ArraySplit().
				withArrayPath(this.getReplaceExpression()).
				withSplitProjection(ArraySplit.ResultField.Element, ArraySplit.ResultField.Index,
					ArraySplit.ResultField.Array).
				withInputs(module.getInput(0));

		EvaluationExpression defaultExpression;
		if (this.getDefaultExpression() == EvaluationExpression.VALUE)
			defaultExpression = EvaluationExpression.VALUE;
		else if (this.getDefaultExpression().equals(FILTER_RECORDS))
			defaultExpression = this.getDefaultExpression();
		else
			defaultExpression =
				this.getDefaultExpression().clone().replace(EvaluationExpression.VALUE, new ArrayAccess(0));
		Replace replacedElements = new Replace().
			withName(String.format("%s element", this.getName())).
			withInputs(arraySplit, module.getInput(1)).
			withDefaultExpression(defaultExpression).
			withDictionaryValueExtraction(this.getDictionaryValueExtraction()).
			withDictionaryKeyExtraction(this.getDictionaryKeyExtraction()).
			withReplaceExpression(new ArrayAccess(0));

		final AssembleArray arrayDictionary = new AssembleArray().
			withInputs(replacedElements);

		final Replace arrayLookup = new Replace().
			withName(String.format("%s array", this.getName())).
			withInputs(module.getInput(0), arrayDictionary).
			withReplaceExpression(this.getReplaceExpression()).
			withDefaultExpression(FILTER_RECORDS);
		// empty arrays will not be replaced
		Selection emptyArrays = new Selection().
			withCondition(new UnaryExpression(this.getReplaceExpression(), true)).
			withInputs(module.getInput(0));
		module.getOutput(0).setInput(0, new UnionAll().withInputs(arrayLookup, emptyArrays));
	}

}