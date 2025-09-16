package csproject;

import csproject.exception.TommyException;
import csproject.storage.Storage;

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
    private static final String Save_path = "data/tommy.txt";

    /** Storage component for persisting tasks */
    private static final Storage storage = new Storage(Save_path);

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
     *   <li><code>tag [number] [tag]</code> - Add tag to task</li>
     *   <li><code>untag [number] [tag]</code> - Remove tag from task</li>
     *   <li><code>list</code> - Show all tasks</li>
     *   <li><code>bye</code> - Exit application</li>
     * </ul>
     *
     * @param args Command line arguments (not used)
     * @throws TommyException If there's an error during task processing
     */
    public static void main(String[] args) throws TommyException {
        // Initialize application components
        ui.showWelcome();
        Task[] tasks = new Task[100];
        int size = storage.retrieveSize(tasks);
        taskList = new TaskList(tasks, size);
        ui.showLoadedTasks(size);

        // Create command handler
        CommandHandler commandHandler = new CommandHandler(taskList, ui, storage, size);

        // Main application loop
        while (true) {
            String input = ui.getUserInput();
            if (input == null) {
                break;
            }

            try {
                boolean shouldContinue = commandHandler.processCommand(input);
                if (!shouldContinue) {
                    break;
                }
            } catch (TommyException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.close();
    }
}





















































