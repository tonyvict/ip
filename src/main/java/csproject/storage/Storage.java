package csproject.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import csproject.Deadline;
import csproject.Event;
import csproject.Task;
import csproject.Todo;
import csproject.exception.TommyException;


/**
 * Handles file storage operations for the Tommy task management system.
 * Manages saving tasks to and loading tasks from persistent storage.
 *
 * @author Tony
 * @version 1.0
 */
public class Storage {

    private final Path filePath; //path is a java object that represents a location in my com

    /**
     * Creates a new Storage instance with the specified file path.
     *
     * @param relative Relative path to the storage file
     */
    public Storage(String relative) { //relative is the intended location for storage
        this.filePath = Paths.get(relative);
    }

    /**
     * Saves tasks to the storage file.
     *
     * @param tasks Array of tasks to save
     * @param size Number of tasks to save
     * @throws TommyException If there's an error during saving
     */
    public void save(Task[] tasks, int size) throws TommyException {
        try {
            java.nio.file.Path parent = filePath.getParent();
            if (parent != null) {
                java.nio.file.Files.createDirectories(parent); //creating a directory for storage on other persons com
            }
        } catch (IOException e) {
            System.err.println("Error creating directory: " + e.getMessage());
            return;
        }

        java.io.BufferedWriter writer = null;
        try {
            writer = java.nio.file.Files.newBufferedWriter(filePath);

            for (int i = 0; i < size; i++) {
                Task task = tasks[i];
                if (task != null) {
                    writer.write(stringer(task)); //stringer func to convert input into valid storage strings
                    writer.newLine();
                }
            }
        } catch (java.io.IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (java.io.IOException e) {
                    System.err.println("Error closing file: " + e.getMessage());
                }
            }
        }
    }

    private static void ensureSafe(String s) throws TommyException {
        if (s == null) {
            return;
        }
        if (s.contains("|") || s.contains("\n") || s.contains("\r")) {
            throw new TommyException("Do avoid '|' symbol and new lines when writing tasks leh");
        }
    }

    /**
     * Converts a Task object to a storage string format.
     *
     * @param t Task to convert to string
     * @return String representation for storage
     * @throws TommyException If task contains invalid characters
     */
    public String stringer(Task t) throws TommyException {

        ensureSafe(t.getName());

        String head = t.typeIcon() + " | " + (t.isMarked() ? "1" : "0") + " | " + t.getName();
        if (t instanceof Deadline) {
            head += " | " + ((Deadline) t).getByString();
        }
        if (t instanceof Event) {
            head += " | " + ((Event) t).getFrom() + " | " + ((Event) t).getTo();
        }

        return head;

    }

    /**
     * Loads tasks from the storage file and returns the count.
     *
     * @param tasks Array to store loaded tasks
     * @return Number of tasks loaded
     * @throws TommyException If there's an error during loading
     */
    public int retrieveSize(Task[] tasks) throws TommyException {
        if (!Files.exists(filePath)) {
            return 0;
        }

        List<Task> taskList = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line; //reading from the saved file

            while ((line = reader.readLine()) != null) { //as long as there is a readable line
                Task t = destringer(line);
                if (t != null) {
                    taskList.add(t);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        int i = 0;
        for (Task task : taskList) {
            if (i < tasks.length) {
                tasks[i++] = task;
            } else {
                break; //to avoid overflow of array
            }
        }
        return i;
    }


    /**
     * Converts a storage line back to a Task object.
     *
     * @param line Storage line containing task data
     * @return Task object created from the line, or null if line is invalid
     * @throws TommyException If the line format is invalid
     */
    public Task destringer(String line) throws TommyException {
        if (line == null || line.isEmpty()) {
            return null;
        }


        String[] tasky = line.split("\\s*\\|\\s*", -1);
        if (tasky.length < 3) {
            throw new TommyException("Corrupted save line: " + line);
        }

        String kind = tasky[0]; // "T","D","E"
        boolean done = "1".equals(tasky[1]); // 1 done 0 not done
        String name = tasky[2];

        Task t;
        switch (kind) {
        case "T": {
            t = new Todo(name);
            break;
        }
        case "D": {
            String by = (tasky.length > 3) ? tasky[3] : "";
            t = new Deadline(name, by);
            break;
        }
        case "E": {
            String from = (tasky.length > 3) ? tasky[3] : "";
            String to = (tasky.length > 4) ? tasky[4] : "";
            t = new Event(name, from, to);
            break;
        }
        default:
            throw new TommyException("Unknown task kind: " + kind);
        }
        if (done) {
            t.mark();
        }
        return t;
    }
}







