package org.example.proyectotfg.controllers.appointmentNotifiers;

import javafx.beans.binding.DoubleBinding;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.example.proyectotfg.controllers.MainController;
import org.example.proyectotfg.controllers.appointmentNotifiers.model.CalendarEvent;
import org.example.proyectotfg.controllers.appointmentNotifiers.model.CalendarEventManager;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorCalendar;
import org.example.proyectotfg.mediators.ViewController;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.ResourceBundle;

public class CalendarMonthController implements ViewController, Initializable {


    private final int INACTIVE = 0;
    private final int ACTIVE = 1;

    @FXML
    private TilePane calendarPage;

    @FXML
    private ScrollPane mainPane;

    private MediatorCalendar mediator;
    private LocalDate selectedDate;

    @Override
    public void setMediator(Mediator mediador) {
        this.mediator = (MediatorCalendar) mediador;
    }


    private CalendarEventManager eventManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDate selectedDate = LocalDate.now();  // Or another appropriate default
        initializeCalendar();
        refreshCalendar(selectedDate);
    }

    private void initializeCalendar() {
        calendarPage.getChildren().clear();
        for (int i = 0; i < 42; i++) {  // Create placeholders for maximum days plus padding
            VBox dayBox = new VBox();
            dayBox.getStyleClass().add("calendar-day");
            calendarPage.getChildren().add(dayBox);
        }
    }

    public void refreshCalendar(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        LocalDate firstOfMonth = LocalDate.of(year, month, 1);
        LocalDate start = firstOfMonth.with(TemporalAdjusters.firstDayOfMonth()).minusDays(firstOfMonth.getDayOfWeek().getValue() % 7);
        LocalDate end = firstOfMonth.with(TemporalAdjusters.lastDayOfMonth()).plusDays(6 - firstOfMonth.with(TemporalAdjusters.lastDayOfMonth()).getDayOfWeek().getValue() % 7);

        int index = 0;
        for (LocalDate day = start; !day.isAfter(end); day = day.plusDays(1)) {
            VBox dayBox = (VBox) calendarPage.getChildren().get(index++);
            dayBox.getChildren().clear();  // Clear previous entries

            Label dayLabel = new Label(String.valueOf(day.getDayOfMonth()));
            dayBox.getChildren().add(dayLabel);
            if (day.getMonthValue() != month) {
                dayBox.getStyleClass().add("inactive");
            } else {
                dayBox.getStyleClass().add("active");
                dayBox.setOnMouseClicked(e -> selectDate(date));
                addEvents(dayBox, day);
            }
        }
    }

    private void addEvents(VBox dayBox, LocalDate day) {
        ObservableList<CalendarEvent> events = eventManager.getEventsOn(day);
        if (!events.isEmpty()) {
            // Example of adding a simple indicator for days with events
            Pane eventIndicator = new Pane();
            eventIndicator.getStyleClass().add("event-indicator");
            dayBox.getChildren().add(eventIndicator);
        }
    }

    private void selectDate(LocalDate date) {
        this.selectedDate = date;
        // Additional logic to update selected date visuals or show details
    }

    public void setEventManager(CalendarEventManager eventManager) {
        this.eventManager = eventManager;
        refreshCalendar(selectedDate);
    }
}


