package org.example.proyectotfg.controllers.appointmentNotifiers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.proyectotfg.controllers.MainController;
import org.example.proyectotfg.controllers.appointmentNotifiers.dialog.DialogHandler;
import org.example.proyectotfg.controllers.appointmentNotifiers.model.CalendarEvent;
import org.example.proyectotfg.controllers.appointmentNotifiers.model.CalendarEventManager;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.ViewController;

import java.io.IOException;
import java.util.Collection;

public class EventPaneController implements ViewController {

    @FXML
    private Label fromLabel;
    @FXML private Label titleLabel;
    @FXML private Label messageLabel;
    @FXML private VBox priorityBox;
    @FXML private CheckBox completedCheckButton;
    @FXML private Button editButton;
    @FXML private Button removeButton;
    @FXML private StackPane eventStackPane;

    private CalendarEventManager eventManager;
    private CalendarEvent event;

    public EventPaneController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proyectotfg/appointmentNotifiers/event-pane-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        refreshStyle();
        completedCheckButton.setOnAction(e -> toggleCompleted());
        removeButton.setOnAction(e -> removeEvent());
        editButton.setOnAction(e -> editEvent());
    }

    private void toggleCompleted() {
        event.setCompleted(completedCheckButton.isSelected());
        refreshStyle();
        // Assuming there's a method to fire a refresh or update
    }

    private void refreshStyle() {
        titleLabel.setText("Title : " + event.getTitle());
        messageLabel.setText("Description : " + event.getDescription());
        // Update styles based on the event state, etc.
    }

    private void editEvent() {
        // Open edit dialog
    }

    private void removeEvent() {
        if (DialogHandler.showConfirmationDialog("Delete selected event?")) {
            eventManager.removeEvent(event);
            this.getChildrenUnmodifiable().remove(this);
        }
    }

    private Collection<Object> getChildrenUnmodifiable() {
    return  null;
    }

    public void setEvent(CalendarEvent event) {
        this.event = event;
        refreshStyle();
    }

    public void setEventManager(CalendarEventManager manager) {
        this.eventManager = manager;
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
