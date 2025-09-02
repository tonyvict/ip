package cs2103;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Hello! I'm Tommy\nWhat can I do for you?");
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showLoadedTasks(int size) {
        if (size > 0) {
            System.out.println("Loaded " + size + " tasks from memory successfully");
        }
    }

    public void showTaskList(Task[] tasks, int size) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < size; i++) {
            if (tasks[i] != null) {
                System.out.printf("%d. %s%n", i + 1, tasks[i].toString());
            }
        }
    }

    public void showMarkedTask(Task task, boolean mark) {
        if (mark) {
            System.out.println("Nice! I've marked this task as done:");
        } else {
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println(" " + task);
    }

    public void showDeletedTask(Task deletedTask, int newSize) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(" " + deletedTask);
        System.out.println("Now you have " + newSize + " tasks in the list.");
    }

    public void showAddedTask(Task task, int size) {
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays the found tasks that match the search keywords.
     * 
     * @param foundTasks Array of tasks that match the search criteria
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

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public String getUserInput() {
        try {
            return scanner.nextLine();
        } catch (java.util.NoSuchElementException e) {
            return null;
        }
    }

    public void close() {
        scanner.close();
    }
}
