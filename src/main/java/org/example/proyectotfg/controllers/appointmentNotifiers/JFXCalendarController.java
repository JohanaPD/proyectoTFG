package org.example.proyectotfg.controllers.appointmentNotifiers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.example.proyectotfg.controllers.MainController;
import org.example.proyectotfg.controllers.appointmentNotifiers.model.CalendarEventManager;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.ViewController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class JFXCalendarController implements ViewController, Initializable {
    @FXML
    private Button menuButton;
    @FXML
    private Button todayButton;
    @FXML
    private Label calendarLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private VBox leftPane;
    @FXML
    private CheckBox optionalCheckBox;
    @FXML
    private CheckBox standardCheckBox;
    @FXML
    private CheckBox importantCheckBox;
    @FXML
    private CheckBox criticalCheckBox;
    @FXML
    private CheckBox completedCheckBox;

    private LocalDate selectedDate;
    private CalendarEventManager eventManager;

    public void JFXCalendar(CalendarEventManager eventManager) {
        this.eventManager = eventManager;
        selectedDate = LocalDate.now();
    }

    @Override
    public void setMediator(Mediator mediador) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        todayButton.setOnAction(e -> selectToday());
        menuButton.setOnAction(e -> toggleLeftPaneVisibility());
        updateDateDisplay();
    }

    private void selectToday() {
        selectedDate = LocalDate.now();
        updateDateDisplay();
        refreshCalendarViews();
    }

    private void toggleLeftPaneVisibility() {
        leftPane.setVisible(!leftPane.isVisible());
    }

    private void updateDateDisplay() {
        dateLabel.setText(selectedDate.getMonth() + " " + selectedDate.getYear());
    }

    private void refreshCalendarViews() {
        // Logic to refresh the views based on the current view mode
    }

    public void setEventManager(CalendarEventManager eventManager) {
        this.eventManager = eventManager;
        // Additional setup when setting a new event manager
    }
}
