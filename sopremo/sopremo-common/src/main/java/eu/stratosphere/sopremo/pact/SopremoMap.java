package eu.stratosphere.sopremo.pact;

import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * An abstract implementation of the {@link MapStub}. SopremoMap provides the functionality to convert the
 * standard input of the MapStub to a more manageable representation (the input is converted to an {@link IJsonNode}).
 */
public abstract class SopremoMap extends TypedSopremoMap<IJsonNode> {
}
