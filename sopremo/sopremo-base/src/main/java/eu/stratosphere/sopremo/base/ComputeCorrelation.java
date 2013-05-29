package eu.stratosphere.sopremo.base;

import java.util.Iterator;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.common.contract.ReduceContract;
import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoReduce;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.type.DoubleNode;
import eu.stratosphere.sopremo.type.IJsonNode;
import eu.stratosphere.sopremo.type.IStreamNode;
import eu.stratosphere.sopremo.type.ObjectNode;

@Name(verb = "computecorelation")
@InputCardinality(1)
public class ComputeCorrelation extends ElementaryOperator<ComputeCorrelation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5698607216019778850L;

	protected static final String FIRST_VALUE = "ser_key_first_value";

	protected static final String SECOND_VALUE = "ser_key_second_value";

	private EvaluationExpression firstValue;

	private EvaluationExpression secondValue;

	public static class FirstReduce extends SopremoReduce {

		private EvaluationExpression firstValue;

		private EvaluationExpression secondValue;

		@Override
		public void open(Configuration parameters) throws Exception {
			super.open(parameters);
			// TODO dirty hack
			firstValue = SopremoUtil.getObject(parameters, FIRST_VALUE, null);
			// firstValue = firstValue.replace(new InputSelection(0),
			// EvaluationExpression.VALUE);
			secondValue = SopremoUtil.getObject(parameters, SECOND_VALUE, null);
			// secondValue = secondValue.replace(new InputSelection(0),
			// EvaluationExpression.VALUE);
		}
		
		@Override
		protected void reduce(IStreamNode<IJsonNode> values, JsonCollector out) {
			Iterator<IJsonNode> it = values.iterator();
			IJsonNode last = null;
			double sum_sq_x = 0;
			double sum_sq_y = 0;
			double sum_x = 0.0;
			double sum_y = 0.0;
			double i = 0;
			double sum_xy = 0;

			while (it.hasNext()) {
				last = it.next();
				double x = getDoubleValueSave(firstValue.evaluate(last));
				double y = getDoubleValueSave(secondValue.evaluate(last));
				
				sum_x += x;
				sum_y += y;
				
				sum_sq_x += x*x;
				sum_sq_y += y*y;
				
				sum_xy += x*y;
				
				i++;
			}

			double a = i*sum_xy - sum_x*sum_y;
			double b = Math.sqrt(i*sum_sq_x-(sum_x*sum_x)) * Math.sqrt(i*sum_sq_y-(sum_y*sum_y));
			double result = a/b;
			ObjectNode resultnode = new ObjectNode();
			resultnode.put("correlation", new DoubleNode(result));
			out.collect(resultnode);
		}
		
		
		
		private double getDoubleValueSave (IJsonNode node) {
			if (!node.isMissing() && !node.isNull()){
				return Double.parseDouble(node.toString());
			}
			return 0.0;
		}

		/*
		 * calculate the number of all elements in private int
		 * getCount(IStreamArrayNode values){ Iterator<IJsonNode> it =
		 * values.iterator(); int n=0; while (it.hasNext()) { n+=1; } return n;
		 * }
		 * 
		 * private Double sum(IStreamArrayNode values) { Iterator<IJsonNode> it
		 * = values.iterator();
		 * 
		 * double sum = 0; while (it.hasNext()) { IJsonNode tmp = it.next(); sum
		 * += Double.parseDouble(tmp.toString()); } //DoubleNode sumEnd =new
		 * DoubleNode(sum); //return sumEnd; return sum; }
		 * 
		 * @SuppressWarnings("unused") private Double quadSum(IStreamArrayNode
		 * values) { Double out=sum(values)*sum(values); return out; }
		 * 
		 * private Double sumProd(IStreamArrayNode values0,IStreamArrayNode
		 * values1) { Double sumXY=(double) 0;
		 * 
		 * //ArrayAccess arrayX=new ArrayAccess(this.firstValue);
		 * //arrayX.simplify(); ArrayNode arrayY=new ArrayNode( values1); int
		 * limit=arrayY.size(); if(arrayY.size()==arrayY.size()) { for(int
		 * i=0;i<limit;i++){ //Double
		 * x=Double.parseDouble(arrayX.get(i).toString()); Double
		 * y=Double.parseDouble(arrayY.get(i).toString()); //sumXY+=x*y; } }
		 * return sumXY; }
		 */// *********************************************************************************************

	}

	@Override
	public int hashCode() {
		final int prime = 37;
		int result = super.hashCode();
		result = prime * result;
		return result;
	}

	@Override
	public PactModule asPactModule(EvaluationContext context) {
		context.setInputsAndOutputs(this.getNumInputs(), this.getNumOutputs());
		PactModule module = new PactModule(1, 1);
		ReduceContract.Builder builder = ReduceContract
				.builder(FirstReduce.class);
		builder.name(this.toString());
		builder.input(module.getInput(0));
		ReduceContract reducecontract = builder.build();

		SopremoUtil.setObject(reducecontract.getParameters(),
				SopremoUtil.CONTEXT, context);
		SopremoUtil.setObject(reducecontract.getParameters(), FIRST_VALUE,
				firstValue);
		SopremoUtil.setObject(reducecontract.getParameters(), SECOND_VALUE,
				secondValue);

		module.getOutput(0).setInput(reducecontract);
		return module;
	}

	@Property(preferred = true)
	@Name(preposition = "for")
	public void setFirstValue(EvaluationExpression value) {
		if (value == null)
			throw new NullPointerException("value expression must not be null");
		this.firstValue = value.clone();
		this.firstValue = firstValue.replace(new InputSelection(0),
				EvaluationExpression.VALUE);
		System.out.println("set first expression " + firstValue.toString());
	}

	@Property(preferred = true)
	@Name(preposition = "with")
	public void setSecondValue(EvaluationExpression value) {
		if (value == null)
			throw new NullPointerException("value expression must not be null");
		this.secondValue = value.clone();
		this.secondValue = secondValue.replace(new InputSelection(0),
				EvaluationExpression.VALUE);
		System.out.println("set second expression " + secondValue.toString());
	}

}
