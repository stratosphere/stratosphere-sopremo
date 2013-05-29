package eu.stratosphere.sopremo.pact;

import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * An abstract implementation of the {@link MatchStub}. SopremoMatch provides the functionality to convert the
 * standard input of the MatchStub to a more manageable representation (both inputs are converted to an
 * {@link IJsonNode}).
 */
public abstract class SopremoMatch extends TypedSopremoMatch<IJsonNode, IJsonNode> {
}
