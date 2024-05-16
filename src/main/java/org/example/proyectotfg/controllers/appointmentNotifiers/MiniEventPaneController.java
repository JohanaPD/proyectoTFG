package org.example.proyectotfg.controllers.appointmentNotifiers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.example.proyectotfg.controllers.MainController;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.ViewController;

import java.awt.*;

public class MiniEventPaneController implements ViewController {
    @FXML
    private VBox priorityBox;
    @FXML
    private Label titleLabel;
    @FXML
    private Image icon;
    @FXML
    private VBox root;

    @Override
    public void setMediator(Mediator mediador) {

    }



    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }
}
