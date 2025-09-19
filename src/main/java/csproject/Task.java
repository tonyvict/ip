package csproject;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstract base class for tasks in the Tommy task management system.
 * Inheritance to other subclasses.
 *
 * <p>Display format: [Type][Status] Description #tag1 #tag2</p>
 *
 * @author  Tony
 * @version 1.0
 * @see Todo
 * @see Deadline
 * @see Event
 */
public abstract class Task {

    /** Task description or name */
    private final String description;

    /** Task completion status (true = completed, false = pending) */
    private boolean completed;

    /** Set of tags associated with this task */
    private final Set<String> tags;

    /**
     * Creates a new task with the given description.
     * New tasks start as incomplete by default.
     *
     * @param description The task description
     */
    Task(String description) {
        this.description = description;
        this.completed = false;
        this.tags = new HashSet<>();
    }

    /**
     * Marks this task as completed.
     */
    public void mark() {
        this.completed = true;
    }

    /**
     * Marks this task as incomplete/pending.
     */
    public void unmark() {
        this.completed = false;
    }

    /**
     * Checks if this task is completed.
     *
     * @return true if completed, false if pending
     */
    public boolean isMarked() {
        return this.completed;
    }

    /**
     * Gets the task description.
     *
     * @return The task description
     */
    public String getName() {
        return this.description;
    }

    /**
     * Gets visual indicator for task completion status.
     *
     * @return "X" if completed, " " (space) if pending
     */
    public String statusIcon() {
        return completed ? "X" : " ";
    }

    /**
     * Returns the task type identifier.
     *
     * @return Single character representing task type
     */
    public abstract String typeIcon();

    /**
     * Adds a tag to this task.
     *
     * @param tag The tag to add
     */
    public void addTag(String tag) {
        if (tag != null && !tag.trim().isEmpty()) {
            this.tags.add(tag.trim());
        }
    }

    /**
     * Removes a tag from this task.
     *
     * @param tag The tag to remove
     */
    public void removeTag(String tag) {
        this.tags.remove(tag);
    }

    /**
     * Gets all tags associated with this task.
     *
     * @return Set of tags
     */
    public Set<String> getTags() {
        return new HashSet<>(this.tags);
    }

    /**
     * Checks if this task has a specific tag.
     *
     * @param tag The tag to check for
     * @return True if task has the tag, false otherwise
     */
    public boolean hasTag(String tag) {
        return this.tags.contains(tag);
    }

    /**
     * Gets formatted string of all tags.
     *
     * @return String representation of tags with # prefix
     */
    public String getTagsString() {
        if (tags.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String tag : tags) {
            sb.append(" #").append(tag);
        }
        return sb.toString();
    }

    /**
     * Returns formatted string representation of the task.
     * Format: [Type][Status] Description #tag1 #tag2
     *
     * @return Formatted task string
     */
    @Override
    public String toString() {
        return String.format("[%s][%s] %s%s", typeIcon(), statusIcon(), description, getTagsString());
    }
}



