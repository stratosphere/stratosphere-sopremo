package eu.stratosphere.util;

import java.io.IOException;

/**
 * Interface for all types.
 * 
 */
public interface IAppending {
	public void appendAsString(Appendable appendable) throws IOException;
}