package eu.stratosphere.sopremo.base;

import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.base.replace.AtomarReplaceBase;
import eu.stratosphere.sopremo.base.replace.AtomarReplaceWithDefaultValue;
import eu.stratosphere.sopremo.base.replace.ReplaceBase;
import eu.stratosphere.sopremo.base.replace.StrictAtomarReplace;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.OutputCardinality;
import eu.stratosphere.sopremo.operator.SopremoModule;

@InputCardinality(min = 2, max = 2)
@OutputCardinality(1)
@Name(verb = "replace")
public class Replace extends ReplaceBase<Replace> {

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.CompositeOperator#asModule(eu.stratosphere.sopremo.EvaluationContext)
	 */
	@Override
	public void addImplementation(final SopremoModule module, final EvaluationContext context) {
		final EvaluationExpression defaultExpression =
			this.getDefaultExpression() == EvaluationExpression.VALUE ? this.getReplaceExpression()
				: this.getDefaultExpression();
		AtomarReplaceBase<?> replaceAtom;
		if (defaultExpression == FILTER_RECORDS)
			replaceAtom = new StrictAtomarReplace();
		else
			replaceAtom = new AtomarReplaceWithDefaultValue().withDefaultExpression(defaultExpression);

		replaceAtom.withInputs(module.getInputs()).
			withReplaceExpression(this.getReplaceExpression()).
			withDictionaryValueExtraction(this.getDictionaryValueExtraction()).
			withKeyExpression(0, this.getReplaceExpression()).
			withKeyExpression(1, this.getDictionaryKeyExtraction());
		module.getOutput(0).setInput(0,
			replaceAtom.withInputs(module.getInput(0), module.getInput(1)));
	}

}