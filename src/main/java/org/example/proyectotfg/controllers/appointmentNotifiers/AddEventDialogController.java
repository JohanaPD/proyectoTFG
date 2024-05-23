package org.example.proyectotfg.controllers.appointmentNotifiers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.proyectotfg.controllers.MainController;
import org.example.proyectotfg.controllers.appointmentNotifiers.model.CalendarEvent;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.ViewController;

public class AddEventDialogController implements ViewController {

    @FXML
    private Button criticalEventButton;

    @FXML
    private DatePicker dateField;

    @FXML
    private CheckBox endOfTheMonthCB;

    @FXML
    private TextArea eventNote1;

    @FXML
    private TextArea eventNote2;

    @FXML
    private RadioButton everyMonthRB;

    @FXML
    private RadioButton everyWeekRB;

    @FXML
    private RadioButton everyYearRB;

    @FXML
    private CheckBox fridayCB;

    @FXML
    private Button importantEventButton;

    @FXML
    private CheckBox mondayCB;

    @FXML
    private Button optionalEventButton;

    @FXML
    private ToggleGroup periodicToggleGroup;

    @FXML
    private CheckBox saturdayCB;

    @FXML
    private Button standardEventButton;

    @FXML
    private CheckBox startOfTheMonthCB;

    @FXML
    private CheckBox sundayCB;

    @FXML
    private TabPane tabPane;

    @FXML
    private CheckBox thursdayCB;

    @FXML
    private TextField titleField;

    @FXML
    private CheckBox tuesdayCB;

    @FXML
    private CheckBox wednesdayCB;

    @FXML
    private DatePicker yearlyDatePicker;
    private int eventType;


    @FXML
    public void initialize() {
        addPriorityButtonListeners();

        addPeriodicChoisesListeners();

        optionalEventButton.fire();
        everyWeekRB.fire();
    }

    private void addPeriodicChoisesListeners() {
        everyWeekRB.setOnAction(e -> {
            resetAndDisableAll();
            mondayCB.setDisable(false);
            tuesdayCB.setDisable(false);
            wednesdayCB.setDisable(false);
            thursdayCB.setDisable(false);
            fridayCB.setDisable(false);
            saturdayCB.setDisable(false);
            sundayCB.setDisable(false);
        });

        everyMonthRB.setOnAction(e -> {
            resetAndDisableAll();
            startOfTheMonthCB.setDisable(false);
            endOfTheMonthCB.setDisable(false);
        });

        everyYearRB.setOnAction(e -> {
            resetAndDisableAll();
            yearlyDatePicker.setDisable(false);
        });
    }

    private void resetAndDisableAll() {
        mondayCB.setSelected(false);
        tuesdayCB.setSelected(false);
        wednesdayCB.setSelected(false);
        thursdayCB.setSelected(false);
        fridayCB.setSelected(false);
        saturdayCB.setSelected(false);
        sundayCB.setSelected(false);
        startOfTheMonthCB.setSelected(false);
        endOfTheMonthCB.setSelected(false);
        yearlyDatePicker.setValue(null);

        mondayCB.setDisable(true);
        tuesdayCB.setDisable(true);
        wednesdayCB.setDisable(true);
        thursdayCB.setDisable(true);
        fridayCB.setDisable(true);
        saturdayCB.setDisable(true);
        sundayCB.setDisable(true);
        startOfTheMonthCB.setDisable(true);
        endOfTheMonthCB.setDisable(true);
        yearlyDatePicker.setDisable(true);
    }

    private void addPriorityButtonListeners() {
        // Event Button Listeners
        optionalEventButton.setOnAction(e -> {
            cleanSelection();
            eventType = CalendarEvent.OPTIONAL;
            optionalEventButton.setStyle("-fx-background-color : #4C95CE; -fx-background-radius:15;");
        });

        standardEventButton.setOnAction(e -> {
            cleanSelection();
            eventType = CalendarEvent.STANDARD;
            standardEventButton.setStyle("-fx-background-color : #81C457; -fx-background-radius:15;");
        });

        importantEventButton.setOnAction(e -> {
            cleanSelection();
            eventType = CalendarEvent.IMPORTANT;
            importantEventButton.setStyle("-fx-background-color : #F8D500; -fx-background-radius:15;");
        });

        criticalEventButton.setOnAction(e -> {
            cleanSelection();
            eventType = CalendarEvent.URGENT;
            criticalEventButton.setStyle("-fx-background-color : #E85569; -fx-background-radius:15;");
        });
    }

    private void cleanSelection() {
        eventType = -1;
        optionalEventButton.setStyle("-fx-background-color : #BDC6CC ; -fx-background-radius:15; ");
        standardEventButton.setStyle("-fx-background-color : #BDC6CC ; -fx-background-radius:15;");
        importantEventButton.setStyle("-fx-background-color : #BDC6CC ; -fx-background-radius:15;");
        criticalEventButton.setStyle("-fx-background-color : #BDC6CC ; -fx-background-radius:15;");
    }

    public int getEventType() {
        return eventType;
    }

    public void clear() {

        cleanSelection();
        eventNote1.setText("");
        eventNote2.setText("");
        titleField.setText("");

        dateField.setValue(null);
        yearlyDatePicker.setValue(null);

        tabPane.getSelectionModel().select(0);

        optionalEventButton.fire();
        everyWeekRB.fire();
    }

    public CalendarEvent getEvent() {
        String title = titleField.getText();
        if (title.isEmpty()) {
            return null;
        }

        CalendarEvent event = null;

        if (tabPane.getSelectionModel().getSelectedIndex() == 0) {
            if (dateField.getValue() != null) {
                event = new CalendarEvent(title, eventType, eventNote1.getText());
                event.setType(CalendarEvent.ONE_TIME_EVENT);
                event.setDate(dateField.getValue());
            }
        } else {
            if (everyWeekRB.isSelected() && hasDaysSelected()) {
                // per week
                event = new CalendarEvent(title, eventType, eventNote2.getText());
                event.setType(CalendarEvent.RECURRING_EVENT);
                event.setPeriodicType(CalendarEvent.PER_WEEK);
                event.setDaysInWeek(collectDaysInWeek());
            } else if (everyMonthRB.isSelected() && hasMonthPlaceSelected()) {
                // per month
                event = new CalendarEvent(title, eventType, eventNote2.getText());
                event.setType(CalendarEvent.RECURRING_EVENT);
                event.setPeriodicType(CalendarEvent.PER_MONTH);
                if (startOfTheMonthCB.isSelected()) {
                    event.setPlaceInMonth(CalendarEvent.START_OF_MONTH);
                } else {
                    event.setPlaceInMonth(CalendarEvent.END_OF_MONTH);
                }
            } else if (yearlyDatePicker.getValue() != null) {
                // per year
                event = new CalendarEvent(title, eventType, eventNote2.getText());
                event.setType(CalendarEvent.RECURRING_EVENT);
                event.setPeriodicType(CalendarEvent.PER_YEAR);
                event.setYearlyDate(yearlyDatePicker.getValue());
            }
        }

        return event;
    }

    private boolean hasMonthPlaceSelected() {
        return startOfTheMonthCB.isSelected() || endOfTheMonthCB.isSelected();
    }

    private String collectDaysInWeek() {
        String days = "";
        if (mondayCB.isSelected()) {
            days += "1,";
        }
        if (tuesdayCB.isSelected()) {
            days += "2,";
        }
        if (wednesdayCB.isSelected()) {
            days += "3,";
        }
        if (thursdayCB.isSelected()) {
            days += "4,";
        }
        if (fridayCB.isSelected()) {
            days += "5,";
        }
        if (saturdayCB.isSelected()) {
            days += "6,";
        }
        if (sundayCB.isSelected()) {
            days += "7,";
        }
        return days;
    }

    private boolean hasDaysSelected() {
        return mondayCB.isSelected() || tuesdayCB.isSelected() || wednesdayCB.isSelected() || thursdayCB.isSelected() || fridayCB.isSelected() || saturdayCB.isSelected() || sundayCB.isSelected();
    }

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
