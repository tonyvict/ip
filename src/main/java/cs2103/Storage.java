package cs2103;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class Storage {

    private final Path filePath; //path is a java object that represents a location in my com

    public Storage(String relative) { //relative is the intended location for storage
        this.filePath = Paths.get(relative);
    }

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
        if (s == null)  return;
        if(s.contains("|") || s.contains("\n") || s.contains("\r") ) {
            throw new TommyException("Do avoid '|' symbol and new lines when writing tasks leh");
        }
    }

    public String stringer(Task t) throws TommyException {  //converting tasks into valid strings for writing into storage

        ensureSafe(t.getName());

        String head = t.typeIcon() + " | " + ( t.isMarked()? "1" : "0" ) + " | " + t.getName();
        if(t instanceof Deadline) {
            head += " | " + ((Deadline) t).getByString();
        }
        if(t instanceof Event) {
            head += " | " + ((Event) t).getFrom() + " | " + ((Event)t).getTo();
        }

        return head;

    }

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
        int i =0;
        for (Task task : taskList) {
            if (i < tasks.length) {
                tasks[i++] = task;
            } else {
                break; //to avoid overflow of array
            }
        }
        return i;
        }


    public Task destringer(String line) throws TommyException {
        if (line == null || line.isEmpty()) return null;


        String[] tasky = line.split("\\s*\\|\\s*", -1);
        if (tasky.length < 3)
            throw new TommyException("Corrupted save line: " + line);

        String kind  = tasky[0];                // "T","D","E"
        boolean done = "1".equals(tasky[1]);    // 1 done 0 not done
        String name  = tasky[2];

        Task t;
        switch (kind) {
            case "T": {
                t = new Todo(name);
                break;
            }
            case "D": {
                String by = (tasky.length > 3) ? tasky[3] : "";
                java.time.LocalDateTime dt = java.time.LocalDateTime.parse(by);
                t = new Deadline(name, by);
                break;
            }
            case "E": {
                String from = (tasky.length > 3) ? tasky[3] : "";
                String to   = (tasky.length > 4) ? tasky[4] : "";
                t = new Event(name, from, to);
                break;
            }
            default:
                throw new TommyException("Unknown task kind: " + kind);
        }
        if (done) t.mark();
        return t;
    }
    }







