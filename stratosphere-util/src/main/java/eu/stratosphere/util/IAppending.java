package eu.stratosphere.util;

import java.io.IOException;

/**
 * Interface for all types.
 * 
 * @author Arvid Heise
 */
public interface IAppending {
	public void appendAsString(Appendable appendable) throws IOException;
}