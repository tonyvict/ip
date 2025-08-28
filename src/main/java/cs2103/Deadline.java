package cs2103;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;




public class Deadline extends Task {

    private final LocalDateTime by;
    private static final DateTimeFormatter IN  = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUT = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    public Deadline(String fullname, String byRaw) throws TommyException {
        super(fullname);
        try {
            this.by = LocalDateTime.parse(byRaw.trim(), IN);
        } catch (DateTimeParseException e) {
            throw new TommyException("Invalid date/time. Use yyyy-MM-dd HHmm, e.g., 2019-10-15 1800.");
        }
    }

    public Deadline(String fullname, LocalDateTime by) {
        super(fullname);
        this.by =by;
    }

    public LocalDateTime getByDateTime() {
        return by;
    }

    public String getByString() {
        return by.toString();
    }

    @Override
    public String typeIcon() {
        return "D";
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s (by: %s)", typeIcon(), statusIcon(), this.getName(), by.format(OUT));
    }
}

