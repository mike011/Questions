package ca.charland.test.util;

/**
 * Thrown when there is a problem with reflection.
 * @author Michael
 *
 */
public class ReflectionException extends RuntimeException {

	/**
	 * Used for threads.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Simple constructor.
	 */
	ReflectionException() {
		super();
	}

	/**
	 * Constructor with error description.
	 * @param e The error description.
	 */
	ReflectionException(final String e) {
		super(e);
	}

}
