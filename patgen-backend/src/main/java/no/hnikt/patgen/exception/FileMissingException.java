package no.hnikt.patgen.exception;

/**
 * @author Jean-Claude Van Damme
 *
 */
public class FileMissingException extends RuntimeException {

	private static final long serialVersionUID = 1577968317828379641L;

	public FileMissingException(String message) {
		super(message);
	}
	
}
