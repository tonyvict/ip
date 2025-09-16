package csproject;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import csproject.exception.TommyException;
import csproject.storage.Storage;

/**
 * Minimal test for CommandHandler class.
 */
class CommandHandlerTest {

    private CommandHandler commandHandler;

    @BeforeEach
    void setUp() {
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test_data.txt");
        commandHandler = new CommandHandler(taskList, ui, storage, 0);
    }

    @Test
    void processCommand_byeCommand_returnsFalse() throws TommyException {
        boolean result = commandHandler.processCommand("bye");
        assertFalse(result);
    }
}
