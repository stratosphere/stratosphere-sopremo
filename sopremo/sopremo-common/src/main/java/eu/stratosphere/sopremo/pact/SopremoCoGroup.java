package eu.stratosphere.sopremo.pact;

import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * An abstract implementation of the {@link CoGroupStub}. SopremoCoGroup provides the functionality to convert the
 * standard input of the CoGroupStub to a more manageable representation (both inputs are converted to an
 * {@link eu.stratosphere.sopremo.type.IStreamNode}).
 */
public abstract class SopremoCoGroup extends TypedSopremoCoGroup<IJsonNode, IJsonNode> {

}
