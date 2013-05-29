/***********************************************************************************************************************
 *
 * Copyright (C) 2010 by the Stratosphere project (http://stratosphere.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 **********************************************************************************************************************/
package eu.stratosphere.sopremo.expressions;

import java.io.IOException;

import eu.stratosphere.sopremo.expressions.tree.ChildIterator;
import eu.stratosphere.sopremo.expressions.tree.NamedChildIterator;
import eu.stratosphere.sopremo.type.AbstractNumericNode;
import eu.stratosphere.sopremo.type.BooleanNode;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * Represents basic binary comparative expressions covering all operators specified in {@link BinaryOperator}.
 */
@OptimizerHints(scope = Scope.ANY, minNodes = 2, maxNodes = 2)
public class ComparativeExpression extends BinaryBooleanExpression {
	private EvaluationExpression expr1;

	private EvaluationExpression expr2;

	private final BinaryOperator binaryOperator;

	/**
	 * Initializes a ComparativeExpression with the given binaryOperator and both expressions.
	 * 
	 * @param expr1
	 *        the first expression for the comparison
	 * @param binaryOperator
	 *        the {@link BinaryOperator} that should be used for comparison
	 * @param expr2
	 *        the second expression for the comparison
	 */
	public ComparativeExpression(final EvaluationExpression expr1, final BinaryOperator binaryOperator,
			final EvaluationExpression expr2) {
		this.expr1 = expr1;
		this.binaryOperator = binaryOperator;
		this.expr2 = expr2;
	}

	/**
	 * Initializes ComparativeExpression.
	 */
	ComparativeExpression() {
		this.expr1 = null;
		this.binaryOperator = null;
		this.expr2 = null;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!super.equals(obj))
			return false;
		final ComparativeExpression other = (ComparativeExpression) obj;
		return this.binaryOperator == other.binaryOperator && this.expr1.equals(other.expr1)
			&& this.expr2.equals(other.expr2);
	}

	// @Override
	// public Iterator<IJsonNode> evaluate(Iterator<IJsonNode> input) {
	// return binaryOperator.evaluate(expr1.evaluate(input), expr2.evaluate(input));
	// }
	@Override
	public BooleanNode evaluate(final IJsonNode node) {
		// // we can ignore 'target' because no new Object is created
		return BooleanNode.valueOf(this.binaryOperator.evaluate(this.expr1.evaluate(node),
			this.expr2.evaluate(node)));
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.ExpressionParent#iterator()
	 */
	@Override
	public ChildIterator iterator() {
		return new NamedChildIterator("expr1", "expr2") {

			@Override
			protected void set(int index, EvaluationExpression childExpression) {
				if (index == 0)
					ComparativeExpression.this.expr1 = childExpression;
				else
					ComparativeExpression.this.expr2 = childExpression;
			}

			@Override
			protected EvaluationExpression get(int index) {
				if (index == 0)
					return ComparativeExpression.this.expr1;
				return ComparativeExpression.this.expr2;
			}
		};
	}

	/**
	 * Returns the binaryOperator.
	 * 
	 * @return the binaryOperator
	 */
	public BinaryOperator getBinaryOperator() {
		return this.binaryOperator;
	}

	/**
	 * Returns the first expression.
	 * 
	 * @return the first expression
	 */
	public EvaluationExpression getExpr1() {
		return this.expr1;
	}

	/**
	 * Returns the second expression.
	 * 
	 * @return the second expression
	 */
	public EvaluationExpression getExpr2() {
		return this.expr2;
	}

	@Override
	public int hashCode() {
		final int prime = 47;
		int result = super.hashCode();
		result = prime * result + this.binaryOperator.hashCode();
		result = prime * result + this.expr1.hashCode();
		result = prime * result + this.expr2.hashCode();
		return result;
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.expr1.appendAsString(appendable);
		appendable.append(' ').append(this.binaryOperator.sign).append(' ');
		this.expr2.appendAsString(appendable);
	}

	/**
	 * All supported binary operators.
	 */
	public static enum BinaryOperator {
		EQUAL("=") {
			@Override
			public boolean isTrue(final int comparisonResult) {
				return comparisonResult == 0;
			}
			// @Override
			// public boolean evaluate(IJsonNode e1, IJsonNode e2) {
			// return e1.equals(e2);
			// };
		},
		NOT_EQUAL("<>") {
			@Override
			public boolean isTrue(final int comparisonResult) {
				return comparisonResult != 0;
			}
			// @Override
			// public boolean evaluate(IJsonNode e1, IJsonNode e2) {
			// return !e1.equals(e2);
			// };
		},
		LESS("<") {
			@Override
			public boolean isTrue(final int comparisonResult) {
				return comparisonResult < 0;
			}
			// @Override
			// public <T extends java.lang.Comparable<T>> boolean evaluateComparable(final T e1, final T e2) {
			// return e1.compareTo(e2) < 0;
			// };
		},
		LESS_EQUAL("<=") {
			@Override
			public boolean isTrue(final int comparisonResult) {
				return comparisonResult <= 0;
			}
			// @Override
			// public <T extends java.lang.Comparable<T>> boolean evaluateComparable(final T e1, final T e2) {
			// return e1.compareTo(e2) <= 0;
			// };
		},
		GREATER(">") {
			@Override
			public boolean isTrue(final int comparisonResult) {
				return comparisonResult > 0;
			}
			// @Override
			// public <T extends java.lang.Comparable<T>> boolean evaluateComparable(final T e1, final T e2) {
			// return e1.compareTo(e2) > 0;
			// };
		},
		GREATER_EQUAL(">=") {
			@Override
			public boolean isTrue(final int comparisonResult) {
				return comparisonResult >= 0;
			}
			// @Override
			// public <T extends java.lang.Comparable<T>> boolean evaluateComparable(final T e1, final T e2) {
			// return e1.compareTo(e2) >= 0;
			// };
		};

		private final String sign;

		/**
		 * Initializes a BinaryOperator with the given sign.
		 * 
		 * @param sign
		 *        the string representation of this operator
		 */
		BinaryOperator(final String sign) {
			this.sign = sign;
		}

		public boolean evaluate(final IJsonNode e1, final IJsonNode e2) {
			if (e1.getClass() != e2.getClass()) {
				if (e1 instanceof AbstractNumericNode && e2 instanceof AbstractNumericNode)
					return this.isTrue(e1.compareTo(e2));

				// throw new EvaluationException(String.format("Cannot compare %s %s %s", e1, this, e2));
				return false;
			}

			return this.isTrue(e1.compareToSameType(e2));
		}

		public abstract boolean isTrue(final int comparisonResult);

		//
		// public boolean evaluateComparable(final T e1, final T e2) {
		// return false;
		// }

		@Override
		public String toString() {
			return this.sign;
		}

		/**
		 * Returns the BinaryOperator for the given sign.
		 * 
		 * @param sign
		 *        the sign of the operator that should be returned
		 * @return the operator or null if no operator has been found for the given sign
		 */
		public static BinaryOperator valueOfSymbol(final String sign) {
			for (final BinaryOperator operator : BinaryOperator.values())
				if (operator.sign.equals(sign))
					return operator;

			return null;
		}
	}

}