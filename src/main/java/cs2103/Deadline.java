package cs2103;

import cs2103.exception.TommyException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Task with a specific due date and time.
 * <p>Input format: yyyy-MM-dd HHmm (e.g., "2024-12-25 2359")
 * Display format: [D][Status] Description (by: MMM dd yyyy HHmm)</p>
 * 
 * @author Tony
 * @version 1.0
 * @see Task
 */
public class Deadline extends Task {

    /** The deadline date and time */
    private final LocalDateTime by;
    
    /** Input format: yyyy-MM-dd HHmm */
    private static final DateTimeFormatter IN  = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    
    /** Display format: MMM dd yyyy HHmm */
    private static final DateTimeFormatter OUT = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    /**
     * Creates a Deadline task with description and deadline string.
     * 
     * @param fullname The task description
     * @param byRaw Deadline in "yyyy-MM-dd HHmm" format
     * @throws TommyException If deadline format is invalid
     */
    public Deadline(String fullname, String byRaw) throws TommyException {
        super(fullname);
        try {
            this.by = LocalDateTime.parse(byRaw.trim(), IN);
        } catch (DateTimeParseException e) {
            throw new TommyException("Invalid date/time. Use yyyy-MM-dd HHmm, e.g., 2019-10-15 1800.");
        }
    }

    /**
     * Creates a Deadline task with LocalDateTime object.
     * 
     * @param fullname The task description
     * @param by The deadline as LocalDateTime
     */
    public Deadline(String fullname, LocalDateTime by) {
        super(fullname);
        this.by = by;
    }

    /**
     * Gets the deadline as LocalDateTime object.
     * 
     * @return The deadline LocalDateTime
     */
    public LocalDateTime getByDateTime() {
        return by;
    }

    /**
     * Gets the deadline in input format string.
     * 
     * @return Deadline in "yyyy-MM-dd HHmm" format
     */
    public String getByString() {
        return by.format(IN);
    }

    /**
     * Returns the Deadline type identifier.
     * 
     * @return "D" representing Deadline task type
     */
    @Override
    public String typeIcon() {
        return "D";
    }

    /**
     * Returns formatted string representation.
     * Format: [D][Status] Description (by: Date Time)
     * 
     * @return Formatted deadline task string
     */
    @Override
    public String toString() {
        return String.format("[%s][%s] %s (by: %s)", typeIcon(), statusIcon(), this.getName(), by.format(OUT));
    }
}

