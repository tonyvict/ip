package cs2103;

import cs2103.exception.InvalidCmdException;
import cs2103.exception.MissingDescriptionException;
import cs2103.exception.TommyException;
import cs2103.storage.Storage;

/**
 * Personal task management application supporting Todo, Deadline, and Event tasks.
 * Provides command-line interface for adding, marking, listing, and deleting tasks.
 * Automatically saves tasks to persistent storage.
 * 
 * @author Tony
 * @version 1.0
 */
public class Tommy {

    /** File path for task storage */
    private static final String SAVE_PATH = "data/tommy.txt";
    
    /** Maximum number of tasks that can be stored */
    private static final int MAX_TASKS = 100;
    
    /** Storage component for persisting tasks */
    private static final Storage storage = new Storage(SAVE_PATH);
    
    /** User interface component */
    private static final Ui ui = new Ui();
    
    /** List containing all user tasks */
    private static TaskList taskList;

    /**
     * Main entry point for the Tommy application.
     * 
     * <p>Initializes the app, loads saved tasks, and processes user commands
     * until the user chooses to exit. Supported commands:</p>
     * <ul>
     *   <li><code>todo [description]</code> - Add simple task</li>
     *   <li><code>deadline [description] /by [datetime]</code> - Add task with deadline</li>
     *   <li><code>event [description] /from [start] /to [end]</code> - Add event task</li>
     *   <li><code>mark/unmark [number]</code> - Change task completion status</li>
     *   <li><code>delete [number]</code> - Remove task</li>
     *   <li><code>list</code> - Show all tasks</li>
     *   <li><code>bye</code> - Exit application</li>
     * </ul>
     * 
     * @param args Command line arguments (not used)
     * @throws TommyException If there's an error during task processing
     */
    public static void main(String[] args) throws TommyException {

        ui.showWelcome();
        Task[] tasks = new Task[MAX_TASKS];
        int size = storage.retrieveSize(tasks);
        taskList = new TaskList(tasks, size);
        ui.showLoadedTasks(size);

        while (true) {
            String input = ui.getUserInput();
            if (input == null) {
                break;
            }

            if (Parser.isByeCommand(input)) {
                ui.showGoodbye();
                break;
            } else if (Parser.isListCommand(input)) {
                ui.showTaskList(taskList.getTasks(), size);
            } else if (Parser.isMarkCommand(input)) {
                taskList.markTask(input, true);
                Task markedTask = taskList.getTask(Parser.parseNo(input) - 1);
                ui.showMarkedTask(markedTask, true);
                size = taskList.getSize();
                storage.save(taskList.getTasks(), size);
            } else if (Parser.isUnmarkCommand(input)) {
                taskList.markTask(input, false);
                Task unmarkedTask = taskList.getTask(Parser.parseNo(input) - 1);
                size = taskList.getSize();
                ui.showMarkedTask(unmarkedTask, false);
                storage.save(taskList.getTasks(), size);
            } else if (Parser.isDeleteCommand(input)) {
                Task deletedTask = taskList.getTask(Parser.parseNo(input) - 1);
                taskList.deleteTask(input);
                size = taskList.getSize();
                ui.showDeletedTask(deletedTask, size);
                storage.save(taskList.getTasks(), size);
            } else {
                try {
                    if (Parser.isTodoCommand(input)) {
                        taskList.addTodo(input);
                        ui.showAddedTask(taskList.getTasks()[size], size + 1);
                        size = taskList.getSize();
                        storage.save(taskList.getTasks(), size);
                    } else if (Parser.isDeadlineCommand(input)) {
                        taskList.addDeadline(input);
                        ui.showAddedTask(taskList.getTasks()[size], size + 1);
                        size = taskList.getSize();
                        storage.save(taskList.getTasks(), size);
                    } else if (Parser.isEventCommand(input)) {
                        taskList.addEvent(input);
                        ui.showAddedTask(taskList.getTasks()[size], size + 1);
                        size = taskList.getSize();
                        storage.save(taskList.getTasks(), size);
                    } else {
                        throw new InvalidCmdException("dun say random bs");
                    }
                } catch (TommyException e) {
                    ui.showError(e.getMessage());
                }
            }
        }
        ui.close();
    }
}





















































