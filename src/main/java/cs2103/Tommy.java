package cs2103;

public class Tommy {

    private static final String Save_path = "data/tommy.txt";
    private static final Storage storage = new Storage(Save_path);
    private static final Ui ui = new Ui();
    private static TaskList taskList;

    public static void main(String[] args) throws TommyException {

        ui.showWelcome();
        Task[] tasks = new Task[100];
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
                ui.showMarkedTask(unmarkedTask, false);
                size = taskList.getSize();
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





















































