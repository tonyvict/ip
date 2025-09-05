package csproject.gui;

import java.io.InputStream;

import csproject.Parser;
import csproject.Task;
import csproject.TaskList;
import csproject.exception.InvalidCmdException;
import csproject.exception.TommyException;
import csproject.storage.Storage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX UI for Tommy. Uses existing Storage/TaskList/Parser logic.
 */
public class Main extends Application {

    private static final String SAVE_PATH = "data/tommy.txt";

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;

    private Image userImage;
    private Image tommyImage;

    private final Storage storage = new Storage(SAVE_PATH);
    private TaskList taskList;
    private int size;

    @Override
    public void start(Stage stage) {
        Task[] tasks = new Task[100];
        try {
            size = storage.retrieveSize(tasks);
            taskList = new TaskList(tasks, size);
        } catch (TommyException e) {
            size = 0;
            taskList = new TaskList(tasks, 0);
            System.err.println("Failed to load tasks: " + e.getMessage());
        }

        userImage = safeLoad("/images/DaUser.png");
        tommyImage = safeLoad("/images/DaTommy.png");

        scrollPane = new ScrollPane();
        dialogContainer = new VBox(10);
        dialogContainer.setPadding(new Insets(10));
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(scrollPane, userInput, sendButton);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Tommy");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        root.setPrefSize(400.0, 600.0);
        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToWidth(true);
        scrollPane.setVvalue(1.0);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);
        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setLeftAnchor(scrollPane, 1.0);
        AnchorPane.setRightAnchor(scrollPane, 1.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        String greeting = "By order of the peaky blinders, Cheers from Tommy! Loaded " + size + " task"
                + (size == 1 ? "" : "s") + ". How can I help?";
        dialogContainer.getChildren().add(DialogBox.getTommyDialog(greeting, tommyImage));

        sendButton.setOnAction(e -> handleUserInput(stage));
        userInput.setOnAction(e -> handleUserInput(stage));

        stage.show();
        userInput.requestFocus();
    }

    private Image safeLoad(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("Resource not found: " + path);
                return null;
            }
            return new Image(is);
        } catch (Exception e) {
            System.err.println("Failed to load " + path + ": " + e.getMessage());
            return null;
        }
    }

    private void handleUserInput(Stage stage) {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));
        String reply = processInput(input);
        dialogContainer.getChildren().add(DialogBox.getTommyDialog(reply, tommyImage));

        if (Parser.isByeCommand(input)) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
            // stage.close();
        }

        userInput.clear();
    }

    private String processInput(String input) {
        try {
            if (Parser.isByeCommand(input)) {
                return "Bye! See you soon in France.";
            } else if (Parser.isListCommand(input)) {
                return buildListMessage();
            } else if (Parser.isMarkCommand(input)) {
                taskList.markTask(input, true);
                Task t = taskList.getTask(Parser.parseNo(input) - 1);
                size = taskList.getSize();
                storage.save(taskList.getTasks(), size);
                return "Nice! I’ve marked this task as done:\n  " + t;
            } else if (Parser.isUnmarkCommand(input)) {
                taskList.markTask(input, false);
                Task t = taskList.getTask(Parser.parseNo(input) - 1);
                size = taskList.getSize();
                storage.save(taskList.getTasks(), size);
                return "OK, I’ve marked this task as not done yet:\n  " + t;
            } else if (Parser.isDeleteCommand(input)) {
                Task t = taskList.getTask(Parser.parseNo(input) - 1);
                taskList.deleteTask(input);
                size = taskList.getSize();
                storage.save(taskList.getTasks(), size);
                return "Noted. I’ve removed this task:\n  " + t + "\nNow you have " + size + " task"
                        + (size == 1 ? "" : "s") + " in the list.";
            } else if (Parser.isFindCommand(input)) {
                String keyword = Parser.getFindKeyword(input);
                Task[] found = taskList.findTasks(keyword);
                return buildFoundMessage(found);
            } else if (Parser.isTodoCommand(input)) {
                taskList.addTodo(input);
                size = taskList.getSize();
                storage.save(taskList.getTasks(), size);
                return "Got it. I’ve added this task:\n  " + taskList.getTasks()[size - 1]
                        + "\nNow you have " + size + " task" + (size == 1 ? "" : "s") + " in the list.";
            } else if (Parser.isDeadlineCommand(input)) {
                taskList.addDeadline(input);
                size = taskList.getSize();
                storage.save(taskList.getTasks(), size);
                return "Got it. I’ve added this task:\n  " + taskList.getTasks()[size - 1]
                        + "\nNow you have " + size + " task" + (size == 1 ? "" : "s") + " in the list.";
            } else if (Parser.isEventCommand(input)) {
                taskList.addEvent(input);
                size = taskList.getSize();
                storage.save(taskList.getTasks(), size);
                return "Got it. I’ve added this task:\n  " + taskList.getTasks()[size - 1]
                        + "\nNow you have " + size + " task" + (size == 1 ? "" : "s") + " in the list.";
            } else {
                throw new InvalidCmdException("dun say random bs");
            }
        } catch (TommyException e) {
            return e.getMessage();
        }
    }

    private String buildListMessage() {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        Task[] tasks = taskList.getTasks();
        for (int i = 0; i < size; i++) {
            sb.append(i + 1).append(". ").append(tasks[i]).append('\n');
        }
        return sb.toString().trim();
    }

    private String buildFoundMessage(Task[] found) {
        if (found == null || found.length == 0 || found[0] == null) {
            return "No matching tasks found.";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        int idx = 1;
        for (Task t : found) {
            if (t == null) {
                break;
            }
            sb.append(idx++).append(". ").append(t).append('\n');
        }
        return sb.toString().trim();
    }
}

