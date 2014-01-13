package eu.stratosphere.util;

import java.io.IOException;

/**
 * Interface for all types that may append their representation to an {@link Appendable}.
 */
public interface IAppending {
	/**
	 * Appends a string representation of this expression to the builder. The method should return the same result as
	 * {@link #toString()} but provides a better performance when a string is composed of several child expressions.
	 * 
	 * @param appendable
	 *        the appendable to append to
	 */
	public void appendAsString(Appendable appendable) throws IOException;
}