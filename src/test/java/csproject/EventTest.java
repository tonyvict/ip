package csproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Minimal test for Event class.
 */
class EventTest {

    @Test
    void toString_formatCorrect() {
        Event event = new Event("Team meeting", "2pm", "3pm");
        assertEquals("[E][ ] Team meeting (from: 2pm to: 3pm)", event.toString());
    }
}
