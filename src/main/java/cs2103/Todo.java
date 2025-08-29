package cs2103;

/**
 * Simple task without time constraints.
 
 * 
 * <p>Display format: [T][Status] Description</p>
 * 
 * @author Tony
 * @version 1.0
 * @see Task
 */
public class Todo extends Task {

    /**
     * Creates a new Todo task.
     * 
     * @param fullname The task description
     */
    public Todo(String fullname) {
        super(fullname);
    }

    /**
     * Returns the Todo type identifier.
     * 
     * @return "T" representing Todo task type
     */
    @Override
    public String typeIcon() {
        return "T";
    }
}
