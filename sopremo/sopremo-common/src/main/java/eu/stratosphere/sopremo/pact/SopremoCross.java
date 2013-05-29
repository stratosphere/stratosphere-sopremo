package eu.stratosphere.sopremo.pact;

import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * An abstract implementation of the {@link CrossStub}. SopremoCross provides the functionality to convert the
 * standard input of the CrossStub to a more manageable representation (both inputs are converted to an
 * {@link IJsonNode}).
 */
public abstract class SopremoCross extends TypedSopremoCross<IJsonNode, IJsonNode> {
}
