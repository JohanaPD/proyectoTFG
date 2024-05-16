package org.example.proyectotfg.controllers.appointmentNotifiers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.proyectotfg.controllers.MainController;
import org.example.proyectotfg.controllers.appointmentNotifiers.model.CalendarEvent;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.ViewController;

import java.awt.event.ActionListener;

public class EventDetailPaneController implements ViewController{

    @FXML
    private Button closeDialogButton;

    @FXML
    private Button criticalEventButton;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private TextArea eventDescriptionArea;

    @FXML
    private Button importantEventButton;

    @FXML
    private Button optionalEventButton;

    @FXML
    private Button standardEventButton;

    @FXML
    private TextField titleEventField;

    @FXML
    private TextField typeField;

    @FXML
    private Label typeLabel;

    @FXML
    private Button updateEventButton;


    private Dialog dialog;

    private CalendarEvent event;

    private int selectedPriority = -1;

    private boolean hasChanged = false;

    @FXML
    private void initialize() {

        closeDialogButton.setOnAction(e -> {
            dialog.close();
        });

        optionalEventButton.setOnAction(e -> {
            selectPriority(CalendarEvent.OPTIONAL);
        });

        standardEventButton.setOnAction(e -> {
            selectPriority(CalendarEvent.STANDARD);
        });

        importantEventButton.setOnAction(e -> {
            selectPriority(CalendarEvent.IMPORTANT);
        });

        criticalEventButton.setOnAction(e -> {
            selectPriority(CalendarEvent.URGENT);
        });

        updateEventButton.setOnAction(e -> {
            // update event information

            event.setTitle(titleEventField.getText());
            event.setDescription(eventDescriptionArea.getText());
            event.setPriority(selectedPriority);
            if (event.getType() == CalendarEvent.ONE_TIME_EVENT) {
                event.setDate(eventDatePicker.getValue());
            }

            hasChanged = true;
            dialog.close();
        });
    }

    public void loadEvent(CalendarEvent event) {
        this.event = event;
        titleEventField.setText(event.getTitle());
        eventDescriptionArea.setText(event.getDescription());

        selectPriority(event.getPriority());

        if (event.getType() == CalendarEvent.ONE_TIME_EVENT) {
            typeLabel.setText("Date");
            eventDatePicker.setValue(event.getDate());

        } else {
            eventDatePicker.setVisible(false);
            typeLabel.setText("Event type");

            typeField.setVisible(true);
            if (event.getPeriodicType() == CalendarEvent.PER_WEEK) {
                typeField.setText("Weekly");
            } else if (event.getPeriodicType() == CalendarEvent.PER_MONTH) {
                typeField.setText("Monthly");
            } else {
                typeField.setText("Yearly");
            }
        }
    }

    private void clearPriorityOptions() {
        optionalEventButton
                .setStyle("-fx-background-color: #BDC6CC; -fx-background-radius:15px; ");
        standardEventButton
                .setStyle("-fx-background-color: #BDC6CC; -fx-background-radius:15px; ");
        importantEventButton
                .setStyle("-fx-background-color: #BDC6CC; -fx-background-radius:15px; ");
        criticalEventButton
                .setStyle("-fx-background-color: #BDC6CC; -fx-background-radius:15px; ");
    }

    private void selectPriority(int priority) {
        clearPriorityOptions();

        if (priority == 1) {
            optionalEventButton.setStyle(
                    "-fx-background-color: #4C95CE; -fx-background-radius:15px; ");
        } else if (priority == 2) {
            standardEventButton.setStyle(
                    "-fx-background-color: #81C457; -fx-background-radius:15px; ");
        } else if (priority == 3) {
            importantEventButton.setStyle(
                    "-fx-background-color: #F7C531; -fx-background-radius:15px; ");
        } else {
            criticalEventButton.setStyle(
                    "-fx-background-color: #E85569; -fx-background-radius:15px; ");
        }
        this.selectedPriority = priority;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public boolean hasUpdates() {
        return hasChanged;
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
