package cs2103;

/**
 * Abstract base class for tasks in the Tommy task management system.
 * 
 * 
 * <p>Display format: [Type][Status] Description</p>
 * 
 * @author  Tony
 * @version 1.0
 * @see Todo
 * @see Deadline
 * @see Event
 */
public abstract class Task {

    /** Task description or name */
    private final String fullname;
    
    /** Task completion status (true = completed, false = pending) */
    private boolean status;

    /**
     * Creates a new task with the given description.
     * New tasks start as incomplete by default.
     * 
     * @param fullname The task description
     */
    Task(String fullname) {
        this.fullname = fullname;
        this.status = false;
    }

    /**
     * Marks this task as completed.
     */
    public void mark() {
        this.status = true;
    }

    /**
     * Marks this task as incomplete/pending.
     */
    public void unmark() {
        this.status = false;
    }

    /**
     * Checks if this task is completed.
     * 
     * @return true if completed, false if pending
     */
    public boolean isMarked() {
        return this.status;
    }

    /**
     * Gets the task description.
     * 
     * @return The task description
     */
    public String getName() {
        return this.fullname;
    }

    /**
     * Gets visual indicator for task completion status.
     * 
     * @return "X" if completed, " " (space) if pending
     */
    public String statusIcon() {
        return status ? "X" : " ";
    }

    /**

     * @return Single character representing task type
     */
    public abstract String typeIcon();

    /**
     * Returns formatted string representation of the task.
     * Format: [Type][Status] Description
     * 
     * @return Formatted task string
     */
    @Override
    public String toString() {
        return String.format("[%s][%s] %s", typeIcon(), statusIcon(), fullname);
    }
}



