package eu.stratosphere.sopremo.io;

import java.io.IOException;

/**
 * If something went wrong during the parsing of JsonNodes this exception will be thrown.
 */
public class JsonParseException extends IOException {

	private static final long serialVersionUID = -200084994943556971L;

	public JsonParseException() {
		super();
	}

	public JsonParseException(final String message) {
		super(message);
	}

	public JsonParseException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public JsonParseException(final Throwable cause) {
		super(cause);
	}
}