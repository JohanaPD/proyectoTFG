package org.example.proyectotfg.controllers.appointmentNotifiers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.example.proyectotfg.controllers.MainController;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.ViewController;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class NavigationCalendarController  implements ViewController, Initializable {
    @FXML
    private VBox mainContainer;
    @FXML private Label dateLabel;
    @FXML private Button prevMonthButton;
    @FXML private Button nextMonthButton;
    @FXML private TilePane navigationCalendarGrid;

    private LocalDate selectedDate;

    @Override
    public void setMediator(Mediator mediador) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedDate = LocalDate.now();
        updateDateLabel();
        setupMonthNavigation();
        populateCalendarGrid();
    }

    private void updateDateLabel() {
        String monthName = selectedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        int year = selectedDate.getYear();
        dateLabel.setText(monthName + " " + year);
    }

    private void setupMonthNavigation() {
        prevMonthButton.setOnAction(e -> changeMonth(-1));
        nextMonthButton.setOnAction(e -> changeMonth(1));
    }

    private void changeMonth(int delta) {
        selectedDate = selectedDate.plusMonths(delta);
        updateDateLabel();
        populateCalendarGrid();
    }

    private void populateCalendarGrid() {
        navigationCalendarGrid.getChildren().clear();
        // Logic to populate the calendar grid goes here
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }
}
