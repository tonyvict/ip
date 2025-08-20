
package cs2103;

import java.util.Scanner;

public class Tommy {

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
                for (int i = 0; i < tasks.length; i++) {
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
                        tasks[i] = new Task(input);
                        System.out.println("added: " + input);
                        break;
                    }
                }
            }
        }
        sc.close();
    }
}





















