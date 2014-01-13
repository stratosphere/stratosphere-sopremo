package eu.stratosphere.sopremo.operator;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import eu.stratosphere.api.common.Plan;
import eu.stratosphere.api.common.operators.GenericDataSink;
import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.packages.ITypeRegistry;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;

/**
 * Encapsulate a complete query in Sopremo and translates it to a Pact {@link Plan}.
 */
public class SopremoPlan extends AbstractSopremoType implements Serializable {

	private static final long serialVersionUID = 5702832506916907827L;

	private final SopremoModule module;

	private EvaluationContext context = new EvaluationContext();

	private List<String> requiredPackages = new ArrayList<String>();

	private SopremoRecordLayout layout;

	public SopremoPlan() {
		this.module = new SopremoModule(0, 0);
	}

	public void addRequiredPackage(final String packageName) {
		this.requiredPackages.add(packageName);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(final Appendable appendable) throws IOException {
		this.module.appendAsString(appendable);
	}

	/**
	 * Converts the Sopremo module to a Pact {@link Plan}.
	 * 
	 * @return the converted Pact plan
	 */
	public Plan asPactPlan() {
		final Collection<GenericDataSink> sinks = this.checkForSinks(this.assemblePact());
		return new PlanWithSopremoPostPass(this.layout, sinks);
	}

	/**
	 * Assembles the Pacts of the contained Sopremo operators and returns a list
	 * of all Pact sinks. These sinks may either be directly a {@link GenericDataSink} or an unconnected
	 * {@link eu.stratosphere.api.common.operators.Operator}.
	 * 
	 * @return a list of Pact sinks
	 */
	public Collection<eu.stratosphere.api.common.operators.Operator> assemblePact() {
		final ElementarySopremoModule elementaryModule = this.module.asElementary();
		this.layout = SopremoRecordLayout.create(elementaryModule.getSchema().getKeyExpressions());
		return elementaryModule.assemblePact(this.context, this.layout);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final SopremoPlan other = (SopremoPlan) obj;
		return this.module.equals(other.module);
	}

	/**
	 * Returns the evaluation context of this plan.
	 * 
	 * @return the evaluation context
	 */
	public EvaluationContext getCompilationContext() {
		return this.context;
	}

	/**
	 * Returns all operators that are either (internal) {@link Sink}s or
	 * included in the reference graph.
	 * 
	 * @return all operators in this module
	 */
	public Iterable<? extends Operator<?>> getContainedOperators() {
		return this.module.getReachableNodes();
	}

	public SopremoRecordLayout getLayout() {
		return this.layout;
	}

	/**
	 * Returns the requiredPackages.
	 * 
	 * @return the requiredPackages
	 */
	public List<String> getRequiredPackages() {
		return this.requiredPackages;
	}

	public List<Sink> getSinks() {
		return this.module.getInternalOutputNodes();
	}

	public ITypeRegistry getTypeRegistry() {
		return this.context.getTypeRegistry();
	}

	public List<Operator<?>> getUnmatchingOperators(final SopremoPlan other) {
		return this.module.getUnmatchingNodes(other.module);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.module.hashCode();
		return result;
	}

	/**
	 * Sets the evaluation context of this plan.
	 * 
	 * @param context
	 *        the evaluation context
	 */
	public void setContext(final EvaluationContext context) {
		if (context == null)
			throw new NullPointerException("context must not be null");

		this.context = context;
	}

	/**
	 * Sets the requiredPackages to the specified value.
	 * 
	 * @param packageNames
	 *        the packageNames to set
	 */
	public void setRequiredPackages(final List<String> packageNames) {
		if (packageNames == null)
			throw new NullPointerException("requiredPackages must not be null");

		this.requiredPackages = packageNames;
	}

	public void setSinks(final List<Sink> sinks) {
		for (final Sink sink : sinks)
			this.module.addInternalOutput(sink);
	}

	public void setSinks(final Sink... sinks) {
		this.setSinks(Arrays.asList(sinks));
	}

	/**
	 * Checks if all contracts are {@link GenericDataSink}s.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Collection<GenericDataSink> checkForSinks(
			final Collection<eu.stratosphere.api.common.operators.Operator> contracts) {
		for (final eu.stratosphere.api.common.operators.Operator contract : contracts)
			if (!GenericDataSink.class.isInstance(contract))
				throw new IllegalStateException("Operator without connected sink detected " + contract);
		return (Collection) contracts;
	}
}
