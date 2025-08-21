
package cs2103;

import java.util.Scanner;

public class Tommy {

    private static String splitter(String input, int which) {
        String[] parts = input.split("\\s+", 2); //first split to get the name
        if (which == 0) {
            return parts[0]; //type of task
        }
        if (which == 1 ){
            return parts[1]; //for list todoo
        }

        String[] description = parts[1].split("/");
        if (which == 2) {
            return description[0];//task name
        }
         if (which == 3) {
            return description[1]; //time for event and deadline
        }
        return null;
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

    public static void main(String[] args) {

        System.out.println("Hello! I'm Tommy\nWhat can I do for you?");
        Scanner sc = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int size = 0;

        while (true) {
           String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (input.equals("list")) {
                for (int i = 0; i < size; i++) {
                    if (tasks[i] != null) {
                        System.out.printf("%d. %s%n", i + 1, tasks[i].toString());
                    }
                }
            } else if (input.startsWith("mark ")) {
                marker(tasks, size, input, true);
            } else if (input.startsWith("unmark ")) {
                marker(tasks, size, input, false);
            } else {
                for (int i = 0; i < 100; i++) {
                    if (tasks[i] == null) {

                        if (splitter(input,0).equals("todo")) {
                            tasks[i] = new Todo(splitter(input, 1));
                            System.out.println("Got it. I've added this task:");
                            System.out.println(" " + tasks[i]);
                            System.out.println("Now you have " + size + " tasks in the list.");
                        }

                        if (splitter(input, 0).equals("deadline")) {
                            tasks[i] = new Deadline(splitter(input, 2));
                            System.out.println("Got it. I've added this task:");
                            System.out.println(" " + tasks[i] + " (" + splitter(input, 3) + ")");
                            System.out.println("Now you have " + size + " tasks in the list.");
                        }

                        if (splitter(input, 0).equals("event")) {
                            tasks[i] = new Event(splitter(input, 2));
                            System.out.println("Got it. I've added this task:");
                            System.out.println(" " + tasks[i] + " (" + splitter(input, 3) + ")");
                            System.out.println("Now you have " + size + " tasks in the list.");

                        }

                        size++;


                        break;
                    }
                }
            }
        }
        sc.close();
    }
}





















