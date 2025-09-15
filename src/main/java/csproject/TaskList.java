package csproject;

import csproject.exception.TommyException;

/**
 * Manages a collection of tasks with operations for adding, removing, marking, and searching.
 * Uses a fixed-size array to store tasks with a maximum capacity of 100.
 *
 * @author Tony
 * @version 1.0
 */
public class TaskList {
    private static final int MAX_TASKS = 100;

    private Task[] tasks;
    private int size;

    /**
     * Creates an empty TaskList with default capacity.
     */
    public TaskList() {
        this.tasks = new Task[MAX_TASKS];
        this.size = 0;
    }

    /**
     * Creates a TaskList with existing tasks and size.
     *
     * @param tasks Array of existing tasks
     * @param size Current number of tasks
     */
    public TaskList(Task[] tasks, int size) {
        this.tasks = tasks;
        this.size = size;
    }

    /**
     * Gets the array of tasks.
     *
     * @return Array of tasks
     */
    public Task[] getTasks() {
        return tasks;
    }

    /**
     * Gets the current number of tasks.
     *
     * @return Number of tasks
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the number of tasks.
     *
     * @param size New size value
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Marks or unmarks a task based on the input command.
     *
     * @param input User input containing task number
     * @param mark True to mark as complete, false to unmark
     * @throws TommyException If task number is invalid
     */
    public void markTask(String input, boolean mark) throws TommyException {
        Integer no = Parser.parseNo(input);
        if (no == null || no < 1 || no > size) {
            throw new TommyException("Invalid task number");
        }
        Task t = tasks[no - 1];
        if (mark) {
            t.mark();
        } else {
            t.unmark();
        }
    }

    /**
     * Gets a task at the specified index.
     *
     * @param index Index of the task
     * @return Task at index, or null if index is invalid
     */
    public Task getTask(int index) {
        if (index >= 0 && index < size) {
            return tasks[index];
        }
        return null;
    }

    /**
     * Deletes a task based on the input command.
     *
     * @param input User input containing task number
     * @return New size after deletion
     * @throws TommyException If task number is invalid
     */
    public int deleteTask(String input) throws TommyException {
        Integer no = Parser.parseNo(input);
        if (no == null || no < 1 || no > size) {
            throw new TommyException("Invalid task number");
        }

        Task deletedTask = tasks[no - 1];
        for (int i = no - 1; i < size - 1; i++) {
            tasks[i] = tasks[i + 1];
        }
        tasks[size - 1] = null;
        size--;

        return size;
    }

    /**
     * Adds a new task to the list.
     *
     * @param task Task to add
     * @throws TommyException If task list is full
     */
    public void addTask(Task task) throws TommyException {
        if (size >= MAX_TASKS) {
            throw new TommyException("Task list is full");
        }

        for (int i = 0; i < MAX_TASKS; i++) {
            if (tasks[i] == null) {
                tasks[i] = task;
                size++;
                break;
            }
        }
    }

    /**
     * Adds a new Todo task from user input.
     *
     * @param input User input containing todo description
     * @throws TommyException If input is invalid or list is full
     */
    public void addTodo(String input) throws TommyException {
        String description = Parser.splitter(input, 1);
        Todo todo = new Todo(description);
        addTask(todo);
    }

    /**
     * Adds a new Deadline task from user input.
     *
     * @param input User input containing deadline description and time
     * @throws TommyException If input is invalid or list is full
     */
    public void addDeadline(String input) throws TommyException {
        String name = Parser.splitter(input, 2);
        String by = Parser.deadlineRaw(Parser.splitter(input, 3));
        Deadline deadline = new Deadline(name, by);
        addTask(deadline);
    }

    /**
     * Adds a new Event task from user input.
     *
     * @param input User input containing event description and timing
     * @throws TommyException If input is invalid or list is full
     */
    public void addEvent(String input) throws TommyException {
        String name = Parser.splitter(input, 2);
        String from = Parser.eventtiming(Parser.splitter(input, 3), 0);
        String to = Parser.eventtiming(Parser.splitter(input, 3), 1);
        Event event = new Event(name, from, to);
        addTask(event);
    }

    /**
     * Checks if the task list is empty.
     *
     * @return True if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the task list is full.
     *
     * @return True if full, false otherwise
     */
    public boolean isFull() {
        return size >= MAX_TASKS;
    }

    /**
     * Finds tasks that contain the given keyword in their description.
     *
     * @param keyword The search term to look for in task descriptions
     * @return Array of matching tasks
     */
    public Task[] findTasks(String keyword) {
        Task[] matchingTasks = new Task[MAX_TASKS];
        int matchCount = 0;

        for (int i = 0; i < size; i++) {
            if (tasks[i] != null && tasks[i].getName().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks[matchCount] = tasks[i]; //the first task is the first one that matches the keyword
                matchCount++;
            }
        }

        // to create a properly sized array with only the matching tasks,minus all the nulls
        Task[] result = new Task[matchCount];
        for (int i = 0; i < matchCount; i++) {
            result[i] = matchingTasks[i];
        }

        return result;
    }

    /**
     * Adds a tag to a specific task.
     *
     * @param taskNumber The task number (1-based)
     * @param tag The tag to add
     * @throws TommyException If task number is invalid
     */
    public void addTagToTask(int taskNumber, String tag) throws TommyException {
        if (taskNumber < 1 || taskNumber > size) {
            throw new TommyException("Invalid task number");
        }
        Task task = tasks[taskNumber - 1];
        if (task != null) {
            task.addTag(tag);
        }
    }

    /**
     * Removes a tag from a specific task.
     *
     * @param taskNumber The task number (1-based)
     * @param tag The tag to remove
     * @throws TommyException If task number is invalid
     */
    public void removeTagFromTask(int taskNumber, String tag) throws TommyException {
        if (taskNumber < 1 || taskNumber > size) {
            throw new TommyException("Invalid task number");
        }
        Task task = tasks[taskNumber - 1];
        if (task != null) {
            task.removeTag(tag);
        }
    }
}

