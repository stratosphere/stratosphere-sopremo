package eu.stratosphere.sopremo.operator;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import eu.stratosphere.pact.common.contract.GenericDataSink;
import eu.stratosphere.pact.common.plan.Plan;
import eu.stratosphere.pact.generic.contract.Contract;
import eu.stratosphere.sopremo.AbstractSopremoType;
import eu.stratosphere.sopremo.CoreFunctions;
import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.ICloneable;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.packages.EvaluationScope;
import eu.stratosphere.sopremo.packages.IConstantRegistry;
import eu.stratosphere.sopremo.packages.IFunctionRegistry;
import eu.stratosphere.sopremo.packages.ITypeRegistry;
import eu.stratosphere.sopremo.serialization.SopremoRecordLayout;

/**
 * Encapsulate a complete query in Sopremo and translates it to a Pact {@link Plan}.
 * 
 * @author Arvid Heise
 */
public class SopremoPlan extends AbstractSopremoType implements ICloneable, Serializable, EvaluationScope {

	private static final long serialVersionUID = 5702832506916907827L;

	private final SopremoModule module;

	private EvaluationContext context = new EvaluationContext();

	private List<String> requiredPackages = new ArrayList<String>();

	private SopremoRecordLayout layout;

	public SopremoPlan() {
		this.module = new SopremoModule(0, 0);
		this.context.getFunctionRegistry().put(CoreFunctions.class);
	}

	/**
	 * Converts the Sopremo module to a Pact {@link Plan}.
	 * 
	 * @return the converted Pact plan
	 */
	public Plan asPactPlan() {
		final Plan plan = new PlanWithSopremoPostPass(this.checkForSinks(this.assemblePact()));
		return plan;
	}

	/**
	 * Returns the requiredPackages.
	 * 
	 * @return the requiredPackages
	 */
	public List<String> getRequiredPackages() {
		return this.requiredPackages;
	}

	@Override
	public ITypeRegistry getTypeRegistry() {
		return this.context.getTypeRegistry();
	}

	/**
	 * Sets the requiredPackages to the specified value.
	 * 
	 * @param requiredPackages
	 *        the requiredPackages to set
	 */
	public void setRequiredPackages(List<String> packageNames) {
		if (packageNames == null)
			throw new NullPointerException("requiredPackages must not be null");

		this.requiredPackages = packageNames;
	}

	public void addRequiredPackage(String packageName) {
		this.requiredPackages.add(packageName);
	}

	/**
	 * Checks if all contracts are {@link GenericDataSink}s.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Collection<GenericDataSink> checkForSinks(final Collection<Contract> contracts) {
		for (final Contract contract : contracts)
			if (!GenericDataSink.class.isInstance(contract))
				throw new IllegalStateException("Contract without connected sink detected " + contract);
		return (Collection) contracts;
	}

	public void setSinks(final Sink... sinks) {
		this.setSinks(Arrays.asList(sinks));
	}

	public void setSinks(final List<Sink> sinks) {
		for (final Sink sink : sinks)
			this.module.addInternalOutput(sink);
	}

	public SopremoRecordLayout getLayout() {
		return this.layout;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.ISopremoType#appendAsString(java.lang.Appendable)
	 */
	@Override
	public void appendAsString(Appendable appendable) throws IOException {
		this.module.appendAsString(appendable);
	}

	/**
	 * Assembles the Pacts of the contained Sopremo operators and returns a list
	 * of all Pact sinks. These sinks may either be directly a {@link FileDataSinkContract} or an unconnected
	 * {@link Contract}.
	 * 
	 * @return a list of Pact sinks
	 */
	public Collection<Contract> assemblePact() {
		final ElementarySopremoModule elementaryModule = this.module.asElementary(this.context);
		elementaryModule.inferSchema();
		this.layout = SopremoRecordLayout.create(elementaryModule.getSchema().getKeyExpressions());
		return elementaryModule.assemblePact(this.context);
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

	/**
	 * Returns the evaluation context of this plan.
	 * 
	 * @return the evaluation context
	 */
	public EvaluationContext getEvaluationContext() {
		return this.context;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.module.hashCode();
		return result;
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

	public List<Operator<?>> getUnmatchingOperators(final SopremoPlan other) {
		return this.module.getUnmatchingNodes(other.module);
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.packages.EvaluationScope#getConstantRegistry()
	 */
	@Override
	public IConstantRegistry getConstantRegistry() {
		return this.context.getConstantRegistry();
	}

	/*
	 * (non-Javadoc)
	 * @see eu.stratosphere.sopremo.packages.EvaluationScope#getFunctionRegistry()
	 */
	@Override
	public IFunctionRegistry getFunctionRegistry() {
		return this.context.getFunctionRegistry();
	}
}
