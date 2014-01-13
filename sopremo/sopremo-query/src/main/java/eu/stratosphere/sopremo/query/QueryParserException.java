package eu.stratosphere.sopremo.query;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;

public class QueryParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5142011370807158960L;

	private int line = -1, charIndex = -1;

	private Token invalidToken;

	public QueryParserException() {
		super();
	}

	/**
	 * Initializes QueryParserException.
	 * 
	 * @param message
	 */
	public QueryParserException(final String message) {
		super(message);
	}

	/**
	 * Initializes QueryParserException.
	 * 
	 * @param message
	 * @param cause
	 */
	public QueryParserException(final String message, final Throwable cause) {
		super(message, cause);

		if (cause instanceof RecognitionException) {
			this.line = ((RecognitionException) cause).line;
			this.invalidToken = ((RecognitionException) cause).token;
			this.charIndex = ((RecognitionException) cause).charPositionInLine;
		}
	}

	public int getCharIndex() {
		return this.charIndex;
	}

	public Token getInvalidToken() {
		return this.invalidToken;
	}

	public int getLine() {
		return this.line;
	}

	@Override
	public String getMessage() {
		if (this.getInvalidToken() == null)
			return super.getMessage();
		return String.format("%s: %s @ (%d, %d)", super.getMessage(), this.getInvalidToken().getText(), this.getLine(),
			this.getCharIndex());
	}

	public String getRawMessage() {
		return super.getMessage();
	}

	public void setCharIndex(final int charIndex) {
		this.charIndex = charIndex;
	}

	public void setInvalidToken(final Token token) {
		if (token == null)
			throw new NullPointerException("token must not be null");

		this.invalidToken = token;
	}

	public void setLine(final int line) {
		this.line = line;
	}
}
