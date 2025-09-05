package csproject.gui;

import javafx.application.Application;

/**
 * Application entry point that delegates to {@link Main}.
 */
public class Launcher {
    /**
     * Starts the JavaFX application.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
