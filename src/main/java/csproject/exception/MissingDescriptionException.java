package csproject.exception;
/**
 * Exception thrown when the user enters an invalid command in Tommy.
 */
public class MissingDescriptionException extends TommyException {

    public MissingDescriptionException(String message) {
        super(message);
    }
}
