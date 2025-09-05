package csproject.exception;
/**
 * Exception thrown when the user enters an invalid command in Tommy.
 */
public class InvalidCmdException extends TommyException {

    public InvalidCmdException(String message) {
        super(message);
    }
}
