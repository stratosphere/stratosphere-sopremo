package eu.stratosphere.sopremo.pact;

import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * An abstract implementation of the {@link CrossFunction}. SopremoCross provides the functionality to convert the
 * standard input of the CrossFunction to a more manageable representation (both inputs are converted to an
 * {@link IJsonNode}).
 */
public abstract class SopremoCross extends GenericSopremoCross<IJsonNode, IJsonNode, IJsonNode> {
}
