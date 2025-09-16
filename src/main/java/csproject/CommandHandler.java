package csproject;

import csproject.exception.InvalidCmdException;
import csproject.exception.TommyException;
import csproject.storage.Storage;

/**
 * Handles command processing and execution for the Tommy task management system.
 * Separates command logic from the main application loop for better code organization.
 *
 * @author Tony
 * @version 1.0
 */
public class CommandHandler {

    private final TaskList taskList;
    private final Ui ui;
    private final Storage storage;
    private int size;

    /**
     * Creates a new CommandHandler with the given components.
     *
     * @param taskList The task list to operate on
     * @param ui The user interface component
     * @param storage The storage component
     * @param size Initial number of tasks
     */
    public CommandHandler(TaskList taskList, Ui ui, Storage storage, int size) {
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
        this.size = size;
    }

    /**
     * Updates the current task count.
     *
     * @param newSize The new task count
     */
    public void updateSize(int newSize) {
        this.size = newSize;
    }

    /**
     * Gets the current task count.
     *
     * @return Current number of tasks
     */
    public int getSize() {
        return size;
    }

    /**
     * Processes a user command and returns the result.
     *
     * @param input The user input command
     * @return True if the application should continue, false if it should exit
     * @throws TommyException If there's an error processing the command
     */
    public boolean processCommand(String input) throws TommyException {
        if (input == null) {
            return false;
        }

        if (Parser.isByeCommand(input)) {
            ui.showGoodbye();
            return false;
        } else if (Parser.isListCommand(input)) {
            handleListCommand();
        } else if (Parser.isMarkCommand(input)) {
            handleMarkCommand(input, true);
        } else if (Parser.isUnmarkCommand(input)) {
            handleMarkCommand(input, false);
        } else if (Parser.isDeleteCommand(input)) {
            handleDeleteCommand(input);
        } else if (Parser.isFindCommand(input)) {
            handleFindCommand(input);
        } else if (Parser.isTagCommand(input)) {
            handleTagCommand(input, true);
        } else if (Parser.isUntagCommand(input)) {
            handleTagCommand(input, false);
        } else {
            handleTaskCreationCommand(input);
        }

        return true;
    }

    /**
     * Handles the list command.
     */
    private void handleListCommand() {
        ui.showTaskList(taskList.getTasks(), size);
    }

    /**
     * Handles mark/unmark commands.
     *
     * @param input The user input
     * @param mark True to mark, false to unmark
     * @throws TommyException If there's an error
     */
    private void handleMarkCommand(String input, boolean mark) throws TommyException {
        taskList.markTask(input, mark);
        Task task = taskList.getTask(Parser.parseNo(input) - 1);
        ui.showMarkedTask(task, mark);
        updateAndSave();
    }

    /**
     * Handles the delete command.
     *
     * @param input The user input
     * @throws TommyException If there's an error
     */
    private void handleDeleteCommand(String input) throws TommyException {
        Task deletedTask = taskList.getTask(Parser.parseNo(input) - 1);
        taskList.deleteTask(input);
        updateAndSave();
        ui.showDeletedTask(deletedTask, size);
    }

    /**
     * Handles the find command.
     *
     * @param input The user input
     */
    private void handleFindCommand(String input) {
        String keyword = Parser.getFindKeyword(input);
        Task[] foundTasks = taskList.findTasks(keyword);
        ui.showFoundTasks(foundTasks);
    }

    /**
     * Handles tag/untag commands.
     *
     * @param input The user input
     * @param add True to add tag, false to remove tag
     * @throws TommyException If there's an error
     */
    private void handleTagCommand(String input, boolean add) throws TommyException {
        String[] tagData = Parser.parseTagCommand(input);
        if (tagData != null) {
            int taskNumber = Integer.parseInt(tagData[0]);
            String tag = tagData[1];

            if (add) {
                taskList.addTagToTask(taskNumber, tag);
            } else {
                taskList.removeTagFromTask(taskNumber, tag);
            }

            Task task = taskList.getTask(taskNumber - 1);
            ui.showTaggedTask(task, tag, add);
            try {
                storage.save(taskList.getTasks(), size);
            } catch (TommyException e) {
                ui.showError("Error saving tasks: " + e.getMessage());
            }
        } else {
            String command = add ? "tag" : "untag";
            ui.showError("Invalid " + command + " command format. Use: " + command + " [number] [tag]");
        }
    }

    /**
     * Handles task creation commands (todo, deadline, event).
     *
     * @param input The user input
     * @throws TommyException If there's an error
     */
    private void handleTaskCreationCommand(String input) throws TommyException {
        try {
            if (Parser.isTodoCommand(input)) {
                taskList.addTodo(input);
                ui.showAddedTask(taskList.getTasks()[size], size + 1);
                updateAndSave();
            } else if (Parser.isDeadlineCommand(input)) {
                taskList.addDeadline(input);
                ui.showAddedTask(taskList.getTasks()[size], size + 1);
                updateAndSave();
            } else if (Parser.isEventCommand(input)) {
                taskList.addEvent(input);
                ui.showAddedTask(taskList.getTasks()[size], size + 1);
                updateAndSave();
            } else {
                throw new InvalidCmdException("dun say random bs");
            }
        } catch (TommyException e) {
            ui.showError(e.getMessage());
        }
    }

    /**
     * Updates the size and saves the task list.
     */
    private void updateAndSave() {
        size = taskList.getSize();
        try {
            storage.save(taskList.getTasks(), size);
        } catch (TommyException e) {
            ui.showError("Error saving tasks: " + e.getMessage());
        }
    }
}
