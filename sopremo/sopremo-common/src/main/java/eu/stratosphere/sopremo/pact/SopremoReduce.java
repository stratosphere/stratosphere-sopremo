package eu.stratosphere.sopremo.pact;

import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * An abstract implementation of the {@link ReduceStub}. SopremoReduce provides the functionality to convert the
 * standard input of the ReduceStub to a more manageable representation (the input is converted to an {@link IArrayNode}
 * ).
 */
public abstract class SopremoReduce extends TypedSopremoReduce<IJsonNode> {

}
