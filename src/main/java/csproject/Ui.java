package csproject;

import java.util.Scanner;

/**
 * Handles user interface interactions for the Tommy task management system.
 * Manages input/output operations and displays formatted messages to the user.
 *
 * @author Tony
 * @version 1.0
 */
public class Ui {
    private Scanner scanner;

    /**
     * Creates a new Ui instance with a Scanner for user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message to the user.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm Tommy\nWhat can I do for you?");
    }

    /**
     * Displays the goodbye message to the user.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays the number of tasks loaded from storage.
     *
     * @param size Number of tasks loaded
     */
    public void showLoadedTasks(int size) {
        if (size > 0) {
            System.out.println("Loaded " + size + " tasks from memory successfully");
        }
    }

    /**
     * Displays the list of tasks to the user.
     *
     * @param tasks Array of tasks to display
     * @param size Number of tasks in the array
     */
    public void showTaskList(Task[] tasks, int size) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < size; i++) {
            if (tasks[i] != null) {
                System.out.printf("%d. %s%n", i + 1, tasks[i].toString());
            }
        }
    }

    /**
     * Displays a message when a task is marked or unmarked.
     *
     * @param task The task that was marked/unmarked
     * @param mark True if task was marked, false if unmarked
     */
    public void showMarkedTask(Task task, boolean mark) {
        if (mark) {
            System.out.println("Nice! I've marked this task as done:");
        } else {
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println(" " + task);
    }

    /**
     * Displays a message when a task is deleted.
     *
     * @param deletedTask The task that was deleted
     * @param newSize Number of tasks remaining after deletion
     */
    public void showDeletedTask(Task deletedTask, int newSize) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(" " + deletedTask);
        System.out.println("Now you have " + newSize + " tasks in the list.");
    }

    /**
     * Displays a message when a new task is added.
     *
     * @param task The task that was added
     * @param size Total number of tasks after addition
     */
    public void showAddedTask(Task task, int size) {
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays the results of a task search.
     *
     * @param foundTasks Array of matching tasks found
     */
    public void showFoundTasks(Task[] foundTasks) {
        System.out.println("__________________________________________________________");
        System.out.println(" Here are the matching tasks in your list:");

        if (foundTasks.length == 0) {
            System.out.println(" No tasks found matching your search.");
        } else {
            for (int i = 0; i < foundTasks.length; i++) {
                if (foundTasks[i] != null) {
                    System.out.printf("%d.%s%n", i + 1, foundTasks[i].toString());
                }
            }
        }

        System.out.println("_____________________________________________________");
    }

    /**
     * Displays a message when a tag is added or removed from a task.
     *
     * @param task The task that was tagged/untagged
     * @param tag The tag that was added/removed
     * @param added True if tag was added, false if removed
     */
    public void showTaggedTask(Task task, String tag, boolean added) {
        if (added) {
            System.out.println("Nice! I've added the tag '" + tag + "' to this task:");
        } else {
            System.out.println("OK, I've removed the tag '" + tag + "' from this task:");
        }
        System.out.println(" " + task);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message Error message to display
     */
    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Gets user input from the console.
     *
     * @return User input string, or null if no input available
     */
    public String getUserInput() {
        try {
            return scanner.nextLine();
        } catch (java.util.NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Closes the scanner and releases resources.
     */
    public void close() {
        scanner.close();
    }
}
