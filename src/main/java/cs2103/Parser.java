package cs2103;

import cs2103.exception.InvalidCmdException;
import cs2103.exception.MissingDescriptionException;
import cs2103.exception.TommyException;

public class Parser {

    public static String splitter(String input, int which) throws TommyException {
        if (input == null || input.isEmpty()) {
            throw new InvalidCmdException("Bro, i look like what to u, type smt first eh");
        }
        String[] parts = input.split("\\s+", 2); //first split to get the name
        if (which == 0) {
            return parts[0]; //type of task
        }
        if (which == 1) {
            if (parts.length < 2) throw new MissingDescriptionException("Woiii, todo what?!");
            else {
                return parts[1];
            } //for list todoo
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

    public static String deadlineday(String input) {
        if (input == null) {
            return null;
        }

        String day = (input.split(" "))[1];
        return day;
    }

    public static String deadlineRaw(String byPart) {
        if (byPart == null) return "";
        int firstSpace = byPart.indexOf(' ');
        return (firstSpace >= 0) ? byPart.substring(firstSpace + 1).trim() : byPart.trim();
    }

    public static String eventtiming(String input, int which) {
        if (input == null) {
            return null;
        }
        String[] splitty = input.split(" ", 2);//"from" and "Mon 2pm /to 4pm"
        String[] both = splitty[1].split(" /to ", 2);
        if (which == 0) {
            return both[0];
        }
        if (which == 1) {
            return both[1];
        }
        return " ";
    }

    public static Integer parseNo(String input) {
        String[] parts = input.split(" "); //identfying
        if (parts.length != 2) {
            return null;
        } else {
            return Integer.parseInt(parts[1]); //taking the task number
        }
    }

    public static boolean isByeCommand(String input) {
        return input.equals("bye");
    }

    public static boolean isListCommand(String input) {
        return input.equals("list");
    }

    public static boolean isMarkCommand(String input) {
        return input.startsWith("mark ");
    }

    public static boolean isUnmarkCommand(String input) {
        return input.startsWith("unmark ");
    }

    public static boolean isDeleteCommand(String input) {
        return input.startsWith("delete");
    }

    public static boolean isTodoCommand(String input) throws TommyException {
        return splitter(input, 0).equals("todo");
    }

    public static boolean isDeadlineCommand(String input) throws TommyException {
        return splitter(input, 0).equals("deadline");
    }

    public static boolean isEventCommand(String input) throws TommyException {
        return splitter(input, 0).equals("event");
    }
}

