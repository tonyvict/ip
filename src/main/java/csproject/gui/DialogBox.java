package csproject.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * A reusable chat bubble composed of an optional avatar and a wrapped text label.
 * <p>
 * Use {@link #getUserDialog(String, Image)} for right-aligned messages and
 * {@link #getTommyDialog(String, Image)} for left-aligned messages.
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
     */
    private DialogBox(String message, Image avatar, boolean isUser) {
        text.setText(message);
        text.setWrapText(true);
        text.setMaxWidth(280);
        text.setMinHeight(Region.USE_PREF_SIZE);
        text.setPadding(new Insets(8, 12, 8, 12));
        text.setStyle("-fx-background-color: #F2F2F7; -fx-background-radius: 12;");

        setSpacing(8);
        setPadding(new Insets(4, 8, 4, 8));
        setFillHeight(true);

        if (avatar != null) {
            displayPicture.setImage(avatar);
            displayPicture.setFitWidth(42);
            displayPicture.setFitHeight(42);
            displayPicture.setPreserveRatio(true);
            displayPicture.setSmooth(true);
        }

        if (isUser) {
            if (avatar != null) {
                getChildren().addAll(text, displayPicture);
            } else {
                getChildren().addAll(text);
            }
            setAlignment(Pos.TOP_RIGHT);
        } else {
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
        return new DialogBox(message, avatar, true);
    }

    /**
     * Creates a left-aligned bubble representing Tommy's message.
     *
     * @param message the message text
     * @param avatar  Tommy's avatar image (nullable)
     * @return a new Tommy dialog box
     */
    public static DialogBox getTommyDialog(String message, Image avatar) {
        return new DialogBox(message, avatar, false);
    }
}
