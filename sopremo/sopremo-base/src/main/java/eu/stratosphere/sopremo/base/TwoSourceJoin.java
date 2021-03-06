package eu.stratosphere.sopremo.base;

import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Predicates;

import eu.stratosphere.api.common.operators.util.OperatorUtil;
import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.sopremo.base.join.AntiJoin;
import eu.stratosphere.sopremo.base.join.EquiJoin;
import eu.stratosphere.sopremo.base.join.EquiJoin.Mode;
import eu.stratosphere.sopremo.base.join.SemiJoin;
import eu.stratosphere.sopremo.base.join.ThetaJoin;
import eu.stratosphere.sopremo.base.join.TwoSourceJoinBase;
import eu.stratosphere.sopremo.expressions.ArrayCreation;
import eu.stratosphere.sopremo.expressions.BinaryBooleanExpression;
import eu.stratosphere.sopremo.expressions.ComparativeExpression;
import eu.stratosphere.sopremo.expressions.ElementInSetExpression;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.expressions.TransformFunction;
import eu.stratosphere.sopremo.operator.Internal;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.Property;

@Internal
public class TwoSourceJoin extends TwoSourceJoinBase<TwoSourceJoin> {
	private BinaryBooleanExpression condition = new ComparativeExpression(new InputSelection(0),
		ComparativeExpression.BinaryOperator.EQUAL, new InputSelection(1));

	private TwoSourceJoinBase<?> strategy;

	private boolean inverseInputs;

	private final IntSet outerJoinSources = new IntOpenHashSet();

	/**
	 * Initializes TwoSourceJoin.
	 */
	public TwoSourceJoin() {
		this.chooseStrategy();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.ElementaryOperator#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		appendable.append("Join on ");
		this.condition.appendAsString(appendable);
		if (this.getResultProjection() != EvaluationExpression.VALUE) {
			appendable.append(" to ");
			this.getResultProjection().appendAsString(appendable);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.operator.ElementaryOperator#asPactModule(eu.stratosphere.sopremo.EvaluationContext,
	 * eu.stratosphere.sopremo.serialization.SopremoRecordLayout)
	 */
	@Override
	public PactModule asPactModule() {
		if (this.inverseInputs)
			this.strategy.setResultProjection(this.getResultProjection().clone().replace(
				Predicates.instanceOf(InputSelection.class), new TransformFunction() {
					@Override
					public EvaluationExpression apply(final EvaluationExpression argument) {
						return new InputSelection(1 - ((InputSelection) argument).getIndex());
					}
				}));
		else
			this.strategy.setResultProjection(this.getResultProjection());
		if (!this.outerJoinSources.isEmpty() && this.strategy instanceof EquiJoin)
			((EquiJoin) this.strategy).withMode(
				this.outerJoinSources.contains(this.inverseInputs ? 1 : 0),
				this.outerJoinSources.contains(this.inverseInputs ? 0 : 1));

		if (this.getDegreeOfParallelism() != STANDARD_DEGREE_OF_PARALLELISM)
			this.strategy.setDegreeOfParallelism(this.getDegreeOfParallelism());

		final PactModule pactModule = this.strategy.asPactModule();
		if (this.inverseInputs)
			OperatorUtil.swapInputs(pactModule.getOutput(0).getInputs().get(0), 0, 1);
		return pactModule;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final TwoSourceJoin other = (TwoSourceJoin) obj;
		return this.condition.equals(other.condition) && this.inverseInputs == other.inverseInputs
			&& this.outerJoinSources.equals(other.outerJoinSources) && this.strategy.equals(other.strategy);
	}

	public BinaryBooleanExpression getCondition() {
		return this.condition;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ElementaryOperator#getKeyExpressions(int)
	 */
	@Override
	public List<? extends EvaluationExpression> getKeyExpressions(final int inputIndex) {
		return this.strategy.getKeyExpressions(inputIndex);
	}

	public int[] getOuterJoinIndices() {
		return this.outerJoinSources.toIntArray();
	}

	public EvaluationExpression getOuterJoinSources() {
		final EvaluationExpression[] expressions = new EvaluationExpression[this.outerJoinSources.size()];
		final IntIterator iterator = this.outerJoinSources.iterator();
		for (int index = 0; iterator.hasNext(); index++) {
			final int inputIndex = iterator.nextInt();
			expressions[index] = new InputSelection(inputIndex);
		}
		return new ArrayCreation(expressions);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.condition.hashCode();
		result = prime * result + (this.inverseInputs ? 1231 : 1237);
		result = prime * result + this.outerJoinSources.hashCode();
		result = prime * result + this.strategy.hashCode();
		return result;
	}

	// TODO name inconsistency with Join.setJoinCondition()
	@Property
	public void setCondition(final BinaryBooleanExpression condition) {
		if (condition == null)
			throw new NullPointerException("condition must not be null");

		EvaluationExpression expr1, expr2;
		if (condition instanceof ComparativeExpression) {
			expr1 = ((ComparativeExpression) condition).getExpr1();
			expr2 = ((ComparativeExpression) condition).getExpr2();
		} else if (condition instanceof ElementInSetExpression) {
			expr1 = ((ElementInSetExpression) condition).getElementExpr();
			expr2 = ((ElementInSetExpression) condition).getSetExpr();
		} else
			throw new IllegalArgumentException(String.format("Type of condition %s not supported",
				condition.getClass().getSimpleName()));

		final int inputIndex1 = expr1.findFirst(InputSelection.class).getIndex();
		final int inputIndex2 = expr2.findFirst(InputSelection.class).getIndex();
		if (inputIndex1 == inputIndex2)
			throw new IllegalArgumentException(String.format("Condition input selection is invalid %s", condition));
		else if (inputIndex1 < 0 || inputIndex1 > 1 || inputIndex2 < 0 || inputIndex2 > 1)
			throw new IllegalArgumentException(String.format("Condition input selection out of bounds %s", condition));
		this.condition = condition;
		this.chooseStrategy();
	}

	public void setOuterJoinIndices(final int... outerJoinIndices) {
		if (outerJoinIndices == null)
			throw new NullPointerException("outerJoinIndices must not be null");

		this.outerJoinSources.clear();
		for (final int index : outerJoinIndices)
			this.outerJoinSources.add(index);
	}

	@Property
	@Name(verb = "preserve")
	public void setOuterJoinSources(final EvaluationExpression outerJoinSources) {
		if (outerJoinSources == null)
			throw new NullPointerException("outerJoinSources must not be null");

		final Iterable<? extends EvaluationExpression> expressions;
		if (outerJoinSources instanceof InputSelection)
			expressions = Collections.singleton(outerJoinSources);
		else if (outerJoinSources instanceof ArrayCreation)
			expressions = outerJoinSources;
		else
			throw new IllegalArgumentException(String.format("Cannot interpret %s", outerJoinSources));

		this.outerJoinSources.clear();
		for (final EvaluationExpression expression : expressions)
			this.outerJoinSources.add(((InputSelection) expression).getIndex());
	}

	public TwoSourceJoin withCondition(final BinaryBooleanExpression condition) {
		this.setCondition(condition);
		return this;
	}

	public TwoSourceJoin withOuterJoinIndices(final int... outerJoinIndices) {
		this.setOuterJoinIndices(outerJoinIndices);
		return this;
	}

	public TwoSourceJoin withOuterJoinSources(final EvaluationExpression outerJoinSources) {
		this.setOuterJoinSources(outerJoinSources);
		return this;
	}

	/**
	 * Returns the strategy. For testing only.
	 * 
	 * @return the strategy
	 */
	TwoSourceJoinBase<?> getStrategy() {
		return this.strategy;
	}

	private void chooseStrategy() {
		this.inverseInputs = false;
		this.strategy = null;
		// choose the strategy, just probably generalized in a kind of factory
		if (this.condition instanceof ComparativeExpression) {
			final ComparativeExpression comparison = (ComparativeExpression) this.condition.clone();
			switch (comparison.getBinaryOperator()) {
			case EQUAL:
				this.inverseInputs = comparison.getExpr1().findFirst(InputSelection.class).getIndex() == 1;
				this.strategy = new EquiJoin().withMode(Mode.NONE).
					withKeyExpression(0, comparison.getExpr1().remove(InputSelection.class)).
					withKeyExpression(1, comparison.getExpr2().remove(InputSelection.class));
				break;
			default:
				this.strategy = new ThetaJoin().withCondition(comparison);
			}
		} else if (this.condition instanceof ElementInSetExpression) {
			final ElementInSetExpression elementInSetExpression = (ElementInSetExpression) this.condition.clone();
			this.inverseInputs =
				elementInSetExpression.getElementExpr().findFirst(InputSelection.class).getIndex() == 1;
			switch (elementInSetExpression.getQuantor()) {
			case EXISTS_NOT_IN:
				this.strategy = new AntiJoin().
					withKeyExpression(0, elementInSetExpression.getElementExpr().remove(InputSelection.class)).
					withKeyExpression(1, elementInSetExpression.getSetExpr().remove(InputSelection.class));
				break;
			case EXISTS_IN:
				this.strategy = new SemiJoin().
					withKeyExpression(0, elementInSetExpression.getElementExpr().remove(InputSelection.class)).
					withKeyExpression(1, elementInSetExpression.getSetExpr().remove(InputSelection.class));
				break;
			}
		}
		if (this.strategy == null)
			throw new UnsupportedOperationException("condition " + this.condition + " not supported");
	}
}
