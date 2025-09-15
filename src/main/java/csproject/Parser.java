package csproject;

import csproject.exception.InvalidCmdException;
import csproject.exception.MissingDescriptionException;
import csproject.exception.TommyException;

/**
 * Utility class for parsing user input commands and extracting relevant information.
 * Handles command validation and data extraction for the Tommy task management system.
 *
 * @author Tony
 * @version 1.0
 */
public class Parser {

    /**
     * Splits user input and extracts specific parts based on the which parameter.
     *
     * @param input User input string
     * @param which Which part to extract (0=command, 1=description, 2=task name, 3=time info)
     * @return Extracted part of the input
     * @throws TommyException If input is invalid or missing required parts
     */
    @SuppressWarnings("checkstyle:WhitespaceAfter")
    public static String splitter(String input, int which) throws TommyException {
        assert which >= 0 && which <= 3 : "Invalid 'which' index for spliiter";
        if (input == null || input.isEmpty()) {
            throw new InvalidCmdException("Bro, i look like what to u, type smt first eh");
        }
        String[] parts = input.split("\\s+", 2); //first split to get the name
        assert parts.length > 0 : "Splitter should always produce at least one token";

        if (which == 0) {
            return parts[0]; //type of task
        }
        if (which == 1) {
            if (parts.length < 2) {
                throw new MissingDescriptionException("Woiii, todo what?!");
            } else {
                return parts[1];
            } //for list todoo
        }

        String[] description = parts[1].split("/", 2);
        assert description.length > 0 : "Description must contain at least one token";
        if (which == 2) {
            return description[0]; //task name
        }
        if (which == 3) {
            return description[1]; //time for event and deadline
        }
        return null;
    }

    /**
     * Extracts the day part from deadline input.
     *
     * @param input Deadline input string
     * @return Day part of the deadline, or null if input is null
     */
    public static String deadlineday(String input) {
        if (input == null) {
            return null;
        }

        String day = (input.split(" "))[1];
        return day;
    }

    /**
     * Extracts the raw deadline time from the by part of input.
     *
     * @param byPart The "by" part of deadline input
     * @return Raw deadline time string, or empty string if null
     */
    public static String deadlineRaw(String byPart) {
        if (byPart == null) {
            return "";
        }
        int firstSpace = byPart.indexOf(' ');
        return (firstSpace >= 0) ? byPart.substring(firstSpace + 1).trim() : byPart.trim();
    }

    /**
     * Extracts start or end time from event input.
     *
     * @param input Event input string
     * @param which Which time to extract (0=start, 1=end)
     * @return Start or end time string, or " " if input is null
     */
    public static String eventtiming(String input, int which) {
        if (input == null) {
            return null;
        }
        String[] splitty = input.split(" ", 2); //"from" and "Mon 2pm /to 4pm"
        String[] both = splitty[1].split(" /to ", 2);
        if (which == 0) {
            return both[0];
        }
        if (which == 1) {
            return both[1];
        }
        return " ";
    }

    /**
     * Parses task number from user input.
     *
     * @param input User input containing task number
     * @return Parsed task number, or null if invalid format
     */
    public static Integer parseNo(String input) {
        assert input != null : "parseNo input should not be null";
        String[] parts = input.split(" "); //identifying
        if (parts.length != 2) {
            return null;
        } else {
            return Integer.parseInt(parts[1]); //taking the task number
        }
    }

    /**
     * Checks if the input is a bye command.
     *
     * @param input User input string
     * @return True if input is "bye", false otherwise
     */
    public static boolean isByeCommand(String input) {
        return input.equals("bye");
    }

    /**
     * Checks if the input is a list command.
     *
     * @param input User input string
     * @return True if input is "list", false otherwise
     */
    public static boolean isListCommand(String input) {
        return input.equals("list");
    }

    /**
     * Checks if the input is a mark command.
     *
     * @param input User input string
     * @return True if input starts with "mark ", false otherwise
     */
    public static boolean isMarkCommand(String input) {
        return input.startsWith("mark ");
    }

    /**
     * Checks if the input is an unmark command.
     *
     * @param input User input string
     * @return True if input starts with "unmark ", false otherwise
     */
    public static boolean isUnmarkCommand(String input) {
        return input.startsWith("unmark ");
    }

    /**
     * Checks if the input is a delete command.
     *
     * @param input User input string
     * @return True if input starts with "delete", false otherwise
     */
    public static boolean isDeleteCommand(String input) {
        return input.startsWith("delete");
    }

    /**
     * Checks if the input is a todo command.
     *
     * @param input User input string
     * @return True if input is a todo command, false otherwise
     * @throws TommyException If input parsing fails
     */
    public static boolean isTodoCommand(String input) throws TommyException {
        return splitter(input, 0).equals("todo");
    }

    /**
     * Checks if the input is a deadline command.
     *
     * @param input User input string
     * @return True if input is a deadline command, false otherwise
     * @throws TommyException If input parsing fails
     */
    public static boolean isDeadlineCommand(String input) throws TommyException {
        return splitter(input, 0).equals("deadline");
    }

    /**
     * Checks if the input is an event command.
     *
     * @param input User input string
     * @return True if input is an event command, false otherwise
     * @throws TommyException If input parsing fails
     */
    public static boolean isEventCommand(String input) throws TommyException {
        return splitter(input, 0).equals("event");
    }

    /**
     * Checks if the input is a find command.
     *
     * @param input The user input string
     * @return true if the input starts with "find ", false otherwise
     */
    public static boolean isFindCommand(String input) {
        return input.startsWith("find ");
    }

    /**
     * Extracts the search keyword from a find command.
     *
     * @param input The find command input (e.g., "find book")
     * @return The search keyword
     */
    public static String getFindKeyword(String input) {
        if (input.startsWith("find ")) {
            return input.substring(5).trim();
        }
        return "";
    }

    /**
     * Checks if the input is a tag command.
     *
     * @param input The user input string
     * @return True if input starts with "tag ", false otherwise
     */
    public static boolean isTagCommand(String input) {
        return input.startsWith("tag ");
    }

    /**
     * Checks if the input is an untag command.
     *
     * @param input The user input string
     * @return True if input starts with "untag ", false otherwise
     */
    public static boolean isUntagCommand(String input) {
        return input.startsWith("untag ");
    }

    /**
     * Extracts task number and tag from tag/untag command.
     *
     * @param input The tag/untag command input (e.g., "tag 1 fun")
     * @return Array with [taskNumber, tag] or null if invalid format
     */
    public static String[] parseTagCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }

        String[] parts = input.trim().split("\\s+", 3);
        if (parts.length < 3) {
            return null;
        }

        try {
            int taskNumber = Integer.parseInt(parts[1]);
            String tag = parts[2].trim();
            if (tag.isEmpty()) {
                return null;
            }
            return new String[]{String.valueOf(taskNumber), tag};
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

