package eu.stratosphere.sopremo.pact;

import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * An abstract implementation of the {@link ReduceFunction}. SopremoReduce provides the functionality to convert the
 * standard input of the ReduceFunction to a more manageable representation (the input is converted to an {@link IArrayNode}
 * ).
 */
public abstract class SopremoReduce extends GenericSopremoReduce<IJsonNode, IJsonNode> {

}
