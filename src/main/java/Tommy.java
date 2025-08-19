import java.util.Scanner;

public class Tommy {


    public static void main(String[] args) {

        System.out.println("Hello! I'm Tommy\nWhat can I do for you?");
        Scanner sc = new Scanner(System.in);
        String[] words = new String[100];

        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
            } else if (input.equals("list")) {
                for (int i = 0; i < words.length; i++) {
                    if (words[i] != null) {
                        System.out.printf("%d. %s%n", i + 1, words[i]);
                    }
                }
            } else {
                for (int i = 0; i < 100; i++) {
                    if (words[i] == null) {
                        words[i] = input;
                        System.out.println("added: " + input);
                        break;
                    }
                }
            }
        }
    }
}




















