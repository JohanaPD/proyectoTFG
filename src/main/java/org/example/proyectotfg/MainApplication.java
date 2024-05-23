package org.example.proyectotfg;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.proyectotfg.controllers.MainController;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MainController mainController = new MainController(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}