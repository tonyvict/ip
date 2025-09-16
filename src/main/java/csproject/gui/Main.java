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
        dialogContainer = new VBox(8);
        dialogContainer.setPadding(new Insets(12));
        dialogContainer.setStyle("-fx-background-color: #FAFAFA;");
        scrollPane.setContent(dialogContainer);
        scrollPane.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: #E0E0E0; -fx-border-width: 1;");

        userInput = new TextField();
        userInput.setPromptText("Type your command here...");
        userInput.setStyle("-fx-background-color: white; -fx-border-color: #E0E0E0; "
                + "-fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8 12;");

        sendButton = new Button("Send");
        sendButton.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; "
                + "-fx-background-radius: 6; -fx-padding: 8 16; -fx-font-weight: bold;");
        sendButton.setOnMouseEntered(e -> sendButton.setStyle("-fx-background-color: #1565C0; "
                + "-fx-text-fill: white; -fx-background-radius: 6; -fx-padding: 8 16; -fx-font-weight: bold;"));
        sendButton.setOnMouseExited(e -> sendButton.setStyle("-fx-background-color: #1976D2; "
                + "-fx-text-fill: white; -fx-background-radius: 6; -fx-padding: 8 16; -fx-font-weight: bold;"));

        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #FAFAFA;");
        root.getChildren().addAll(scrollPane, userInput, sendButton);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Tommy - Task Manager");
        stage.setResizable(true);
        stage.setMinHeight(500.0);
        stage.setMinWidth(450.0);
        stage.setWidth(500.0);
        stage.setHeight(650.0);

        // Make components responsive to window resizing
        scrollPane.prefWidthProperty().bind(root.widthProperty().subtract(2));
        scrollPane.prefHeightProperty().bind(root.heightProperty().subtract(60));
        userInput.prefWidthProperty().bind(root.widthProperty().subtract(80));
        sendButton.prefWidthProperty().bind(root.widthProperty().divide(8));

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.setVvalue(1.0);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setLeftAnchor(scrollPane, 1.0);
        AnchorPane.setRightAnchor(scrollPane, 1.0);
        AnchorPane.setLeftAnchor(userInput, 8.0);
        AnchorPane.setBottomAnchor(userInput, 8.0);
        AnchorPane.setBottomAnchor(sendButton, 8.0);
        AnchorPane.setRightAnchor(sendButton, 8.0);

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

        // Check if the reply is an error message
        boolean isError = reply.startsWith("Error:") || reply.contains("dun say random bs")
                || reply.contains("Invalid") || reply.contains("InvalidCmdException");

        if (isError) {
            dialogContainer.getChildren().add(DialogBox.getTommyErrorDialog(reply, tommyImage));
        } else {
            dialogContainer.getChildren().add(DialogBox.getTommyDialog(reply, tommyImage));
        }

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
            } else if (Parser.isTagCommand(input)) {
                String[] tagData = Parser.parseTagCommand(input);
                if (tagData != null) {
                    int taskNumber = Integer.parseInt(tagData[0]);
                    String tag = tagData[1];
                    taskList.addTagToTask(taskNumber, tag);
                    Task taggedTask = taskList.getTask(taskNumber - 1);
                    storage.save(taskList.getTasks(), size);
                    return "Nice! I've added the tag '" + tag + "' to this task:\n  " + taggedTask;
                } else {
                    return "Invalid tag command format. Use: tag [number] [tag]";
                }
            } else if (Parser.isUntagCommand(input)) {
                String[] tagData = Parser.parseTagCommand(input);
                if (tagData != null) {
                    int taskNumber = Integer.parseInt(tagData[0]);
                    String tag = tagData[1];
                    taskList.removeTagFromTask(taskNumber, tag);
                    Task untaggedTask = taskList.getTask(taskNumber - 1);
                    storage.save(taskList.getTasks(), size);
                    return "OK, I've removed the tag '" + tag + "' from this task:\n  " + untaggedTask;
                } else {
                    return "Invalid untag command format. Use: untag [number] [tag]";
                }
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

