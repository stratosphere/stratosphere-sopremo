package eu.stratosphere.sopremo.pact;

import eu.stratosphere.api.java.record.functions.MapFunction;
import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * An abstract implementation of the {@link MapFunction}. SopremoMap provides the functionality to convert the
 * standard input of the MapFunction to a more manageable representation (the input is converted to an {@link IJsonNode}
 * ).
 */
public abstract class SopremoMap extends GenericSopremoMap<IJsonNode, IJsonNode> {
}
