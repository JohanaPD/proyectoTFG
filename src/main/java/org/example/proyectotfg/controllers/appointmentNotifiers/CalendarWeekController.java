package org.example.proyectotfg.controllers.appointmentNotifiers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.proyectotfg.controllers.MainController;
import org.example.proyectotfg.controllers.appointmentNotifiers.model.CalendarEvent;
import org.example.proyectotfg.controllers.appointmentNotifiers.model.CalendarEventManager;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.ViewController;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

public class CalendarWeekController extends CalendarViewController implements ViewController {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private HBox daysPane;
    private CalendarEventManager eventManager;

    public CalendarWeekController(Calendar parentPane) {
        super(parentPane);
    }

    @Override
    public void setMediator(Mediator mediador) {

    }

    public void initialize() {
        setupWeekView();
        refreshCalendar(LocalDate.now());  // Load the current week on initialization
    }

    private void setupWeekView() {
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: WHITE;");
    }

    public void refreshCalendar(LocalDate selectedDate) {
        clearEvents();
        LocalDate monday = selectedDate;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        Queue<LocalDate> weekDays = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            createDayCell(monday.plusDays(i), i);
            weekDays.add(monday.plusDays(i));
        }
        addEvents(weekDays);
    }

    private void createDayCell(LocalDate date, int index) {
        VBox dayCell = new VBox();
        dayCell.setSpacing(25);
        dayCell.setPadding(new Insets(3, 0, 0, 5));
        dayCell.getStyleClass().add("calendar_cell");
        Label dayName = new Label(CalendarEvent.DAYS_FULL_NAMES[date.getDayOfWeek().ordinal()]);
        dayName.setPadding(new Insets(5, 0, 0, 5));
        Label dayCount = new Label(String.valueOf(date.getDayOfMonth()));
        dayCount.getStyleClass().add("calendar_label_xl");
        dayCell.getChildren().addAll(dayName, dayCount);
        daysPane.getChildren().add(dayCell);
    }

    private void addEvents(Queue<LocalDate> days) {
        // Similar to previous implementation, iterating through days and adding events
    }

    private void clearEvents() {
        daysPane.getChildren().clear();
    }

    public void setEventManager(CalendarEventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }


}
