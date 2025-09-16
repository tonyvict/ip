package csproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import csproject.exception.TommyException;

/**
 * Minimal test for Parser class.
 */
class ParserTest {

    @Test
    void parseTagCommand_validInput() throws TommyException {
        String[] result = Parser.parseTagCommand("tag 1 urgent");
        assertEquals("1", result[0]);
        assertEquals("urgent", result[1]);
    }
}
