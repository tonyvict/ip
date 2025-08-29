package cs2103;

import cs2103.exception.TommyException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class DeadlineTest {

    private static final DateTimeFormatter IN  = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUT = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    @Test //checking the parsing process
    void constructor_parsesValidRawString_andTrims() throws TommyException {
        Deadline d = new Deadline("submit report", " 2019-10-15 1800 ");

        LocalDateTime expected = LocalDateTime.parse("2019-10-15 1800", IN);
        assertEquals(expected, d.getByDateTime());
        assertEquals("2019-10-15 1800", d.getByString());
    }

    @Test //checking the throwing of exception
    void constructor_invalidFormat_throwsTommyExceptionWithHelpfulMessage() {
        TommyException ex = assertThrows(
                TommyException.class,
                () -> new Deadline("oops", "15-10-2019 6pm")
        );
        assertEquals(
                "Invalid date/time. Use yyyy-MM-dd HHmm, e.g., 2019-10-15 1800.",
                ex.getMessage()
        );
    }


    @Test //check for icon
    void typeIcon_isD() throws TommyException {
        Deadline d = new Deadline("x", "2019-10-15 1800");
        assertEquals("D", d.typeIcon());
    }

    @Test //printed output in the tight structure
    void toString_containsTypeStatusNameAndFormattedDate() throws TommyException {
        Deadline d = new Deadline("submit report", "2019-10-15 1800");
        String s = d.toString();
        assertTrue(s.startsWith("[D]["),
                "toString should start with [D][status]");
        assertTrue(s.contains("submit report"),
                "toString should contain the task name");
        String formatted = LocalDateTime.parse("2019-10-15 1800", IN).format(OUT);
        assertTrue(s.contains("(by: " + formatted + ")"),
                "toString should contain correctly formatted by-date");
    }
}
