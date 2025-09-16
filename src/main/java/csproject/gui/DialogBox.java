package csproject.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * A reusable chat bubble with asymmetric design for user vs Tommy messages.
 * <p>
 * User messages are compact and right-aligned, while Tommy's replies are larger and left-aligned.
 * </p>
 */
public class DialogBox extends HBox {

    /** Text bubble content. */
    private final Label text = new Label();

    /** Avatar image view (may be empty if avatar is {@code null}). */
    private final ImageView displayPicture = new ImageView();

    /**
     * Creates a dialog box with message, optional avatar, and alignment.
     *
     * @param message the message text to display
     * @param avatar  the avatar image (nullable)
     * @param isUser  whether this is a user bubble (right-aligned) or a Tommy bubble (left-aligned)
     * @param isError whether this is an error message (special styling)
     */
    private DialogBox(String message, Image avatar, boolean isUser, boolean isError) {
        text.setText(message);
        text.setWrapText(true);
        text.setMinHeight(Region.USE_PREF_SIZE);
        text.setPadding(new Insets(8, 12, 8, 12));

        setSpacing(8);
        setPadding(new Insets(4, 8, 4, 8));
        setFillHeight(true);

        if (avatar != null) {
            displayPicture.setImage(avatar);
            displayPicture.setFitWidth(32);
            displayPicture.setFitHeight(32);
            displayPicture.setPreserveRatio(true);
            displayPicture.setSmooth(true);
        }

        if (isUser) {
            // User messages: compact, right-aligned, light blue
            text.setMaxWidth(200);
            text.setStyle("-fx-background-color: #E3F2FD; -fx-background-radius: 8; -fx-text-fill: #1976D2;");
            text.setFont(Font.font("System", 12));

            if (avatar != null) {
                getChildren().addAll(text, displayPicture);
            } else {
                getChildren().addAll(text);
            }
            setAlignment(Pos.TOP_RIGHT);
        } else {
            // Tommy messages: larger, left-aligned, with different styling for errors
            text.setMaxWidth(350);

            if (isError) {
                // Error messages: red background, bold text
                text.setStyle("-fx-background-color: #FFEBEE; -fx-background-radius: 8; "
                        + "-fx-text-fill: #D32F2F; -fx-font-weight: bold;");
                text.setFont(Font.font("System", FontWeight.BOLD, 13));
            } else {
                // Normal Tommy messages: light gray, slightly larger, italic
                text.setStyle("-fx-background-color: #F5F5F5; -fx-background-radius: 8; -fx-text-fill: #424242;");
                text.setFont(Font.font("System", FontPosture.ITALIC, 13));
            }

            if (avatar != null) {
                getChildren().addAll(displayPicture, text);
            } else {
                getChildren().addAll(text);
            }
            setAlignment(Pos.TOP_LEFT);
        }
    }

    /**
     * Creates a right-aligned bubble representing a user's message.
     *
     * @param message the message text
     * @param avatar  the user's avatar image (nullable)
     * @return a new user dialog box
     */
    public static DialogBox getUserDialog(String message, Image avatar) {
        return new DialogBox(message, avatar, true, false);
    }

    /**
     * Creates a left-aligned bubble representing Tommy's message.
     *
     * @param message the message text
     * @param avatar  Tommy's avatar image (nullable)
     * @return a new Tommy dialog box
     */
    public static DialogBox getTommyDialog(String message, Image avatar) {
        return new DialogBox(message, avatar, false, false);
    }

    /**
     * Creates a left-aligned bubble representing Tommy's error message.
     *
     * @param message the error message text
     * @param avatar  Tommy's avatar image (nullable)
     * @return a new Tommy error dialog box
     */
    public static DialogBox getTommyErrorDialog(String message, Image avatar) {
        return new DialogBox(message, avatar, false, true);
    }
}
