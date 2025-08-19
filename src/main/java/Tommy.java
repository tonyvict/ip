import java.util.Scanner;

public class Tommy {
    public static void main(String[] args) {

        System.out.println("Hello! I'm Tommy\nWhat can I do for you?");

        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine(); //reads until the user hits enter

        while (!input.equals("bye")) {
            System.out.println(input);
            input = sc.nextLine(); //scanner takes in the next input
        }
                System.out.println("Bye. Hope to see you again soon!");
            }

        }









