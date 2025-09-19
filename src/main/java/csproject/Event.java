package csproject;

/**
 * Task that occurs during a specific time period, one of the 3 tasks available.
 * <p>Display format: [E][Status] Description (from: Start to: End)</p>
 *
 * @author Tony
 * @version 1.0
 * @see Task
 */
public class Event extends Task {

    /** Start time of the event */
    private final String from;

    /** End time of the event */
    private final String to;

    /**
     * Creates a new Event task with description and time period.
     *
     * @param fullname The task description
     * @param from Start time (flexible format)
     * @param to End time (flexible format)
     */
    public Event(String fullname, String from, String to) {
        super(fullname);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the Event type identifier.
     *
     * @return "E" representing Event task type
     */
    @Override
    public String typeIcon() {
        return "E";
    }

    /**
     * Gets the event start time.
     *
     * @return Start time as originally specified
     */
    public String getFrom() {
        return from;
    }

    /**
     * Gets the event end time.
     *
     * @return End time as originally specified
     */
    public String getTo() {
        return to;
    }

    /**
     * Returns formatted string representation.
     * Format: [E][Status] Description (from: Start to: End)
     *
     * @return Formatted event task string
     */
    @Override
    public String toString() {
        return String.format("[%s][%s] %s (from: %s to: %s)",
                typeIcon(), statusIcon(), getName(), from, to);
    }
}

