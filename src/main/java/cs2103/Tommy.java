package cs2103;

import java.util.Scanner;

public class Tommy {

    private static final String Save_path = "data/tommy.txt";
    private static final Storage storage = new Storage(Save_path);

    private static String splitter(String input, int which) throws TommyException {

        if (input == null || input.isEmpty()) {
            throw new InvalidCmdException("Bro, i look like what to u, type smt first eh");
        }
        String[] parts = input.split("\\s+", 2); //first split to get the name
        if (which == 0) {
            return parts[0]; //type of task
        }
        if (which == 1 ){
            if (parts.length < 2) throw new MissingDescriptionException("Woiii, todo what?!");
            else {return parts[1];} //for list todoo
        }

        String[] description = parts[1].split("/", 2);
        if (which == 2) {
            return description[0];//task name
        }
        if (which == 3) {
            return description[1]; //time for event and deadline
        }
        return null;
    }

    private static String deadlineday (String input) {
        if (input == null) {
            return null;
        }

        String day = (input.split(" "))[1];
        return day;
    }

    private static String deadlineRaw(String byPart) {
        if (byPart == null) return "";
        int firstSpace = byPart.indexOf(' ');
        return (firstSpace >= 0) ? byPart.substring(firstSpace + 1).trim() : byPart.trim();
    }


    private static String eventtiming (String input, int which) {
        if (input == null) {
            return null;
        }
        String[] splitty = input.split(" ", 2);//"from" and "Mon 2pm /to 4pm"
        String[] both = splitty[1].split(" /to ",2);
        if (which == 0) {
            return both[0];
        }
        if (which == 1) {
            return both[1];
        }
        return " ";
    }

    private static Integer parseNo (String input) {
        String[] parts = input.split(" "); //identfying
        if (parts.length != 2) {
            return null;
        } else {
            return Integer.parseInt(parts[1]); //taking the task number
        }
    }
    private static void marker(Task[] tasks, int size, String input, boolean mark) {
        Integer no = parseNo(input);
        Task t = tasks[no - 1];
        if (mark) {
            t.mark();
            System.out.println("Nice! I've marked this task as done:");
        } else {
            t.unmark();
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println(" " + t);
    }

    private static int deletor (Task[] tasks, int size, String input) {
        Integer no = parseNo(input);
        Task deletedtask = tasks[no - 1];
        for (int i = no -1; i < size -1; i++) {
            tasks[i] = tasks [i + 1];
        }
        tasks[size - 1] = null;

        System.out.println("Noted. I've removed this task:");
        System.out.println(" " + deletedtask);
        System.out.println("Now you have " + (size -1) + " tasks in the list.");
        return size -1;
    }

    public static void main(String[] args) throws TommyException {



        System.out.println("Hello! I'm Tommy\nWhat can I do for you?");
        Scanner sc = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int size = storage.retrieveSize(tasks);
        if (size >0) {
            System.out.println("Loaded " + size + " tasks from memory successfully");
        }


        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < size; i++) {
                    if (tasks[i] != null) {

                        System.out.printf("%d. %s%n", i + 1, tasks[i].toString());
                    }
                }
            } else if (input.startsWith("mark ")) {
                marker(tasks, size, input, true);
                storage.save(tasks, size);
            } else if (input.startsWith("unmark ")) {
                marker(tasks, size, input, false);
                storage.save(tasks, size);
            } else if (input.startsWith("delete")) {
                size = deletor(tasks, size, input);
                storage.save(tasks, size);

            }


            else {
                for (int i = 0; i < 100; i++) {
                    if (tasks[i] == null) {
                        size++;

                        try {

                            if (splitter(input, 0).equals("todo")) {
                                tasks[i] = new Todo(splitter(input, 1));
                                System.out.println("Got it. I've added this task:");
                                System.out.println(" " + tasks[i]);
                                System.out.println("Now you have " + size + " tasks in the list.");
                                storage.save(tasks, size);
                            } else if (splitter(input, 0).equals("deadline")) {
                                tasks[i] = new Deadline(splitter(input, 2), deadlineRaw(splitter(input, 3)));
                                System.out.println("Got it. I've added this task:");
                                System.out.println(" " + tasks[i]);
                                System.out.println("Now you have " + size + " tasks in the list.");
                                storage.save(tasks, size);
                            } else if (splitter(input, 0).equals("event")) {
                                tasks[i] = new Event(splitter(input, 2), eventtiming(splitter(input, 3), 0), eventtiming(splitter(input, 3), 1));
                                System.out.println("Got it. I've added this task:");
                                System.out.println(" " + tasks[i]);
                                System.out.println("Now you have " + size + " tasks in the list.");
                                storage.save(tasks, size);
                            } else {
                                throw new InvalidCmdException("dun say random bs");
                            }

                        } catch (TommyException e) {
                            System.out.println("Error: " + e.getMessage());
                            size --;
                        }
                        break;
                    }
                }
            }
        }
        sc.close();
    }
}





















































