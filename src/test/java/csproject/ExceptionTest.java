package csproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import csproject.exception.InvalidCmdException;

/**
 * Minimal test for exception classes.
 */
class ExceptionTest {

    @Test
    void invalidCmdException_constructorWithMessage() {
        String message = "Invalid command";
        InvalidCmdException exception = new InvalidCmdException(message);
        assertEquals(message, exception.getMessage());
    }
}
