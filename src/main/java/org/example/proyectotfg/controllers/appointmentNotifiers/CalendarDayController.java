package org.example.proyectotfg.controllers.appointmentNotifiers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.example.proyectotfg.controllers.MainController;
import org.example.proyectotfg.controllers.appointmentNotifiers.model.CalendarEventManager;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.ViewController;

import java.time.LocalDate;

public class CalendarDayController implements ViewController {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox eventPane;
    @FXML
    private Label dayName;
    @FXML
    private Label dayCount;

    private CalendarEventManager eventManager;

    public LocalDate currectDate;


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
