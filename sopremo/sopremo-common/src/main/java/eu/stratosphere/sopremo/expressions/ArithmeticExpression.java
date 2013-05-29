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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.EnumMap;
import java.util.Map;

import eu.stratosphere.sopremo.cache.NodeCache;
import eu.stratosphere.sopremo.expressions.tree.ChildIterator;
import eu.stratosphere.sopremo.expressions.tree.NamedChildIterator;
import eu.stratosphere.sopremo.type.AbstractJsonNode;
import eu.stratosphere.sopremo.type.BigIntegerNode;
import eu.stratosphere.sopremo.type.DecimalNode;
import eu.stratosphere.sopremo.type.DoubleNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IJsonNode.Type;
import eu.stratosphere.sopremo.type.INumericNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.LongNode;
import eu.stratosphere.sopremo.type.NumberCoercer;

/**
 * Represents all basic arithmetic expressions covering the addition, subtraction, division, and multiplication for
 * various types of numbers.
 * 
 * @author Arvid Heise
 */
@OptimizerHints(scope = Scope.NUMBER, minNodes = 2, maxNodes = 2, transitive = true)
public class ArithmeticExpression extends EvaluationExpression {
	private final ArithmeticExpression.ArithmeticOperator operator;

	private EvaluationExpression firstOperand, secondOperand;

	private final transient NodeCache cache = new NodeCache();

	/**
	 * Initializes Arithmetic with two {@link EvaluationExpression}s and an {@link ArithmeticOperator} in infix
	 * notation.
	 * 
	 * @param op1
	 *        the first operand
	 * @param operator
	 *        the operator
	 * @param op2
	 *        the
	 */
	public ArithmeticExpression(final EvaluationExpression op1, final ArithmeticOperator operator,
			final EvaluationExpression op2) {
		this.operator = operator;
		this.firstOperand = op1;
		this.secondOperand = op2;
	}

	/**
	 * Initializes ArithmeticExpression.
	 */
	ArithmeticExpression() {
		this.operator = null;
		this.firstOperand = null;
		this.secondOperand = null;
	}

	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.firstOperand.appendAsString(appendable);
		appendable.append(' ');
		appendable.append(this.operator.name());
		appendable.append(' ');
		this.secondOperand.appendAsString(appendable);
	}

	@Override
	public boolean equals(final Object obj) {
		if (!super.equals(obj))
			return false;
		final ArithmeticExpression other = (ArithmeticExpression) obj;
		return this.firstOperand.equals(other.firstOperand)
			&& this.operator.equals(other.operator)
			&& this.secondOperand.equals(other.secondOperand);
	}

	@Override
	public IJsonNode evaluate(final IJsonNode node) {
		return this.operator.evaluate((INumericNode) this.firstOperand.evaluate(node),
			(INumericNode) this.secondOperand.evaluate(node), this.cache);
	}

	/**
	 * Returns the first operand.
	 * 
	 * @return the first operand
	 */
	public EvaluationExpression getFirstOperand() {
		return this.firstOperand;
	}

	/**
	 * Returns the operator.
	 * 
	 * @return the operator
	 */
	public ArithmeticExpression.ArithmeticOperator getOperator() {
		return this.operator;
	}

	/**
	 * Returns the second operand.
	 * 
	 * @return the second operand
	 */
	public EvaluationExpression getSecondOperand() {
		return this.secondOperand;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 59 * result + this.firstOperand.hashCode();
		result = 59 * result + this.operator.hashCode();
		result = 59 * result + this.secondOperand.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.expressions.ExpressionParent#iterator()
	 */
	@Override
	public ChildIterator iterator() {
		return new NamedChildIterator("firstOperand", "second") {

			@Override
			protected EvaluationExpression get(int index) {
				if (index == 0)
					return ArithmeticExpression.this.firstOperand;
				return ArithmeticExpression.this.secondOperand;
			}

			@Override
			protected void set(int index, EvaluationExpression childExpression) {
				if (index == 0)
					ArithmeticExpression.this.firstOperand = childExpression;
				else
					ArithmeticExpression.this.secondOperand = childExpression;
			}
		};
	}

	/**
	 * Closed set of basic arithmetic operators.
	 * 
	 * @author Arvid Heise
	 */
	public static enum ArithmeticOperator {
		/**
		 * Addition
		 */
		ADDITION("+", new IntegerEvaluator() {
			@Override
			protected int evaluate(final int left, final int right) {
				return left + right;
			}
		}, new LongEvaluator() {
			@Override
			protected long evaluate(final long left, final long right) {
				return left + right;
			}
		}, new DoubleEvaluator() {
			@Override
			protected double evaluate(final double left, final double right) {
				return left + right;
			}
		}, new BigIntegerEvaluator() {
			@Override
			protected BigInteger evaluate(final BigInteger left, final BigInteger right) {
				return left.add(right);
			}
		}, new BigDecimalEvaluator() {
			@Override
			protected BigDecimal evaluate(final BigDecimal left, final BigDecimal right) {
				return left.add(right);
			}
		}),
		/**
		 * Subtraction
		 */
		SUBTRACTION("-", new IntegerEvaluator() {
			@Override
			protected int evaluate(final int left, final int right) {
				return left - right;
			}
		}, new LongEvaluator() {
			@Override
			protected long evaluate(final long left, final long right) {
				return left - right;
			}
		}, new DoubleEvaluator() {
			@Override
			protected double evaluate(final double left, final double right) {
				return left - right;
			}
		}, new BigIntegerEvaluator() {
			@Override
			protected BigInteger evaluate(final BigInteger left, final BigInteger right) {
				return left.subtract(right);
			}
		}, new BigDecimalEvaluator() {
			@Override
			protected BigDecimal evaluate(final BigDecimal left, final BigDecimal right) {
				return left.subtract(right);
			}
		}),
		/**
		 * Multiplication
		 */
		MULTIPLICATION("*", new IntegerEvaluator() {
			@Override
			protected int evaluate(final int left, final int right) {
				return left * right;
			}
		}, new LongEvaluator() {
			@Override
			protected long evaluate(final long left, final long right) {
				return left * right;
			}
		}, new DoubleEvaluator() {
			@Override
			protected double evaluate(final double left, final double right) {
				return left * right;
			}
		}, new BigIntegerEvaluator() {
			@Override
			protected BigInteger evaluate(final BigInteger left, final BigInteger right) {
				return left.multiply(right);
			}
		}, new BigDecimalEvaluator() {
			@Override
			protected BigDecimal evaluate(final BigDecimal left, final BigDecimal right) {
				return left.multiply(right);
			}
		}),
		/**
		 * Division
		 */
		DIVISION("/", DivisionEvaluator.INSTANCE, DivisionEvaluator.INSTANCE, new DoubleEvaluator() {
			@Override
			protected double evaluate(final double left, final double right) {
				return left / right;
			}
		}, DivisionEvaluator.INSTANCE, DivisionEvaluator.INSTANCE);

		private final String sign;

		private final Map<AbstractJsonNode.Type, NumberEvaluator<INumericNode>> typeEvaluators =
			new EnumMap<AbstractJsonNode.Type, NumberEvaluator<INumericNode>>(
				AbstractJsonNode.Type.class);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private ArithmeticOperator(final String sign, final NumberEvaluator integerEvaluator,
				final NumberEvaluator longEvaluator,
				final NumberEvaluator doubleEvaluator, final NumberEvaluator bigIntegerEvaluator,
				final NumberEvaluator bigDecimalEvaluator) {
			this.sign = sign;
			this.typeEvaluators.put(AbstractJsonNode.Type.IntNode, integerEvaluator);
			this.typeEvaluators.put(AbstractJsonNode.Type.LongNode, longEvaluator);
			this.typeEvaluators.put(AbstractJsonNode.Type.DoubleNode, doubleEvaluator);
			this.typeEvaluators.put(AbstractJsonNode.Type.BigIntegerNode, bigIntegerEvaluator);
			this.typeEvaluators.put(AbstractJsonNode.Type.DecimalNode, bigDecimalEvaluator);
		}

		/**
		 * Performs the binary operation on the two operands after coercing both values to a common number type.
		 * 
		 * @param left
		 *        the left operand
		 * @param right
		 *        the right operand
		 * @return the result of the operation
		 */
		public INumericNode evaluate(final INumericNode left, final INumericNode right, final NodeCache cache) {
			final Type widerType = NumberCoercer.INSTANCE.getWiderType(left, right);
			final NumberEvaluator<INumericNode> evaluator = this.typeEvaluators.get(widerType);
			final Class<? extends INumericNode> implementationType = evaluator.getReturnType();
			final INumericNode numericTarget = cache.getNode(implementationType);
			evaluator.evaluate(left, right, numericTarget);
			return numericTarget;
		}

		@Override
		public String toString() {
			return this.sign;
		}
	}

	/**
	 * Taken from Groovy's org.codehaus.groovy.runtime.typehandling.BigDecimalMath
	 * 
	 * @author Arvid Heise
	 */
	static class DivisionEvaluator implements NumberEvaluator<DecimalNode> {
		private static final DivisionEvaluator INSTANCE = new DivisionEvaluator();

		// This is an arbitrary value, picked as a reasonable choice for a precision
		// for typical user math when a non-terminating result would otherwise occur.
		public static final int DIVISION_EXTRA_PRECISION = 10;

		// This is an arbitrary value, picked as a reasonable choice for a rounding point
		// for typical user math.
		public static final int DIVISION_MIN_SCALE = 10;

		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.expressions.ArithmeticExpression.NumberEvaluator#evaluate(eu.stratosphere.sopremo
		 * .type.INumericNode, eu.stratosphere.sopremo.type.INumericNode, eu.stratosphere.sopremo.type.NumericNode)
		 */
		@Override
		public void evaluate(final INumericNode left, final INumericNode right, final DecimalNode numericTarget) {
			numericTarget.setValue(divideImpl(left.getDecimalValue(), right.getDecimalValue()));
		}

		@Override
		public Class<DecimalNode> getReturnType() {
			return DecimalNode.class;
		}

		public static BigDecimal divideImpl(final BigDecimal bigLeft, final BigDecimal bigRight) {
			try {
				return bigLeft.divide(bigRight);
			} catch (final ArithmeticException e) {
				// set a DEFAULT precision if otherwise non-terminating
				final int precision = Math.max(bigLeft.precision(), bigRight.precision()) + DIVISION_EXTRA_PRECISION;
				BigDecimal result = bigLeft.divide(bigRight, new MathContext(precision));
				final int scale = Math.max(Math.max(bigLeft.scale(), bigRight.scale()), DIVISION_MIN_SCALE);
				if (result.scale() > scale)
					result = result.setScale(scale, BigDecimal.ROUND_HALF_UP);
				return result;
			}
		}
	}

	private abstract static class BigDecimalEvaluator implements NumberEvaluator<DecimalNode> {
		@Override
		public void evaluate(final INumericNode left, final INumericNode right, final DecimalNode numericTarget) {
			numericTarget.setValue(this.evaluate(left.getDecimalValue(), right.getDecimalValue()));
		}

		@Override
		public Class<DecimalNode> getReturnType() {
			return DecimalNode.class;
		}

		protected abstract BigDecimal evaluate(BigDecimal left, BigDecimal right);
	}

	private abstract static class BigIntegerEvaluator implements NumberEvaluator<BigIntegerNode> {
		/*
		 * (non-Javadoc)
		 * @see
		 * eu.stratosphere.sopremo.expressions.ArithmeticExpression.NumberEvaluator#evaluate(eu.stratosphere.sopremo
		 * .type.INumericNode, eu.stratosphere.sopremo.type.INumericNode, eu.stratosphere.sopremo.type.NumericNode)
		 */
		@Override
		public void evaluate(final INumericNode left, final INumericNode right, final BigIntegerNode numericTarget) {
			numericTarget.setValue(this.evaluate(left.getBigIntegerValue(), right.getBigIntegerValue()));
		}

		@Override
		public Class<BigIntegerNode> getReturnType() {
			return BigIntegerNode.class;
		}

		protected abstract BigInteger evaluate(BigInteger left, BigInteger right);
	}

	private abstract static class DoubleEvaluator implements NumberEvaluator<DoubleNode> {
		@Override
		public void evaluate(final INumericNode left, final INumericNode right, final DoubleNode numericTarget) {
			numericTarget.setValue(this.evaluate(left.getDoubleValue(), right.getDoubleValue()));
		}

		@Override
		public Class<DoubleNode> getReturnType() {
			return DoubleNode.class;
		}

		protected abstract double evaluate(double left, double right);
	}

	private abstract static class IntegerEvaluator implements NumberEvaluator<IntNode> {
		@Override
		public void evaluate(final INumericNode left, final INumericNode right, final IntNode numericTarget) {
			numericTarget.setValue(this.evaluate(left.getIntValue(), right.getIntValue()));
		}

		@Override
		public Class<IntNode> getReturnType() {
			return IntNode.class;
		}

		protected abstract int evaluate(int left, int right);
	}

	private abstract static class LongEvaluator implements NumberEvaluator<LongNode> {
		@Override
		public void evaluate(final INumericNode left, final INumericNode right, final LongNode numericTarget) {
			numericTarget.setValue(this.evaluate(left.getLongValue(), right.getLongValue()));
		}

		@Override
		public Class<LongNode> getReturnType() {
			return LongNode.class;
		}

		protected abstract long evaluate(long left, long right);
	}

	private static interface NumberEvaluator<ReturnType extends INumericNode> {
		public void evaluate(INumericNode left, INumericNode right, ReturnType numericTarget);

		public Class<ReturnType> getReturnType();
	}
}