package eu.stratosphere.sopremo.pact;

import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * An abstract implementation of the {@link JoinFunction}. SopremoJoin provides the functionality to convert the
 * standard input of the JoinFunction to a more manageable representation (both inputs are converted to an
 * {@link IJsonNode}).
 */
public abstract class SopremoJoin extends GenericSopremoJoin<IJsonNode, IJsonNode, IJsonNode> {
}
