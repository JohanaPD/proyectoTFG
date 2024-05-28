package org.example.proyectotfg.controllers.appointmentNotifiers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.proyectotfg.controllers.MainController;
import org.example.proyectotfg.controllers.appointmentNotifiers.model.CalendarEvent;
import org.example.proyectotfg.controllers.appointmentNotifiers.model.CalendarEventManager;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.ViewController;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import java.net.URL;
import java.util.ResourceBundle;

public class EventDialogController extends JFXDialog implements ViewController, Initializable {

    @FXML
    private BorderPane mainContainer;
    @FXML
    private VBox bodyPane;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addButton;
    CalendarEventManager manager = new CalendarEventManager();

    @Override
    public void setMediator(Mediator mediador) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupDialog();
    }

    private void setupDialog() {
        try {
            VBox loadedContent = FXMLLoader.load(getClass().getResource("org/example/proyectotfg/appointmentNotifiers/event-dialog-view.fxml"));
            bodyPane.getChildren().add(loadedContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        cancelButton.setOnAction(event -> closeDialog());
        // addButton.setOnAction(event -> addEvent(manager));
    }

    private void closeDialog() {
        mainContainer.setVisible(false);  // This would hide the dialog, implement actual close logic as needed
    }

    private void addEvent(CalendarEvent event) {
        manager.addEvent(event);
    }

    public void clear() {

    }

    public void setCalendarEventManager(CalendarEventManager manager) {
        this.manager = manager;
    }


}
