package eu.stratosphere.sopremo.operator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.stratosphere.pact.common.plan.PactModule;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;

/**
 * A composite operator may be composed of several {@link ElementaryOperator}s and other CompositeOperators.<br>
 * This class should always be used as a base for new operators which would be translated to more than one PACT,
 * especially if some kind of projection or selection is used.
 * 
 * @author Arvid Heise
 */
public abstract class CompositeOperator<Self extends CompositeOperator<Self>> extends Operator<Self> {
	private static final Log LOG = LogFactory.getLog(CompositeOperator.class);

	/**
	 * Initializes the CompositeOperator with the given number of outputs.
	 * 
	 * @param numberOfOutputs
	 *        the number of outputs
	 */
	public CompositeOperator(final int numberOfInputs, final int numberOfOutputs) {
		super(numberOfInputs, numberOfInputs, numberOfOutputs, numberOfOutputs);
	}

	/**
	 * Initializes the CompositeOperator with the given number of inputs.
	 * 
	 * @param minInputs
	 *        the minimum number of inputs
	 * @param maxInputs
	 *        the maximum number of inputs
	 */
	public CompositeOperator(final int minInputs, final int maxInputs, final int minOutputs, final int maxOutputs) {
		super(minInputs, maxInputs, minOutputs, maxOutputs);
	}

	/**
	 * Initializes the CompositeOperator with the number of outputs set to 1.
	 */
	public CompositeOperator() {
		super();
	}

	/**
	 * Returns a {@link SopremoModule} that consists entirely of {@link ElementaryOperator}s. The module can be seen as
	 * an expanded version of this operator where all CompositeOperators are recursively translated to
	 * ElementaryOperators.
	 * 
	 * @return a module of ElementaryOperators
	 */
	@Override
	public final ElementarySopremoModule asElementaryOperators(EvaluationContext context) {
		SopremoModule module = new SopremoModule(this.getNumInputs(), this.getNumOutputs());
		module.setName(this.toString());
		this.addImplementation(module, context);

		// inherit the CompositeOperator's DoP, if it was not changed by the
		// Operator developer explicitly.
		if (this.getDegreeOfParallelism() != Operator.STANDARD_DEGREE_OF_PARALLELISM) {
			for (Operator<?> moduleOperator : module.getReachableNodes()) {
				if (moduleOperator.getDegreeOfParallelism() == Operator.STANDARD_DEGREE_OF_PARALLELISM) {
					moduleOperator.setDegreeOfParallelism(this.getDegreeOfParallelism());
				}
			}
		}
		
		module.validate();
		return module.asElementary(context);
	}

	public abstract void addImplementation(SopremoModule module, EvaluationContext context);

	@Override
	public PactModule asPactModule(final EvaluationContext context, SopremoRecordLayout layout) {
		if (LOG.isTraceEnabled())
			LOG.trace("Transforming\n" + this);
		final ElementarySopremoModule elementaryPlan = this.asElementaryOperators(context);
		if (LOG.isTraceEnabled())
			LOG.trace(" to elementary plan\n" + elementaryPlan);
		final PactModule pactModule = elementaryPlan.asPactModule(context);
		return pactModule;
	}
}
