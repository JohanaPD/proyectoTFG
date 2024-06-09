package org.example.proyectotfg;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.proyectotfg.controllers.MainController;

import java.io.IOException;

/**
 * Main application class extending JavaFX Application.
 *
 * <p>Authors: [Author Name]</p>
 * <p>Version: [Version Number]</p>
 * <p>Since: [Date of Creation]</p>
 */
public class MainApplication extends Application {

    /**
     * Starts the application by initializing the main controller.
     *
     * @param stage The primary stage for the application.
     * @throws IOException If an error occurs during initialization.
     */
    @Override
    public void start(Stage stage) throws IOException {
        MainController mainController = new MainController(stage);
    }

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}
