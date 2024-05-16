package org.example.proyectotfg.controllers.appointmentNotifiers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import org.example.proyectotfg.controllers.MainController;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.ViewController;

import java.util.Calendar;

public class CalendarViewController implements ViewController {
    @FXML
    private StackPane mainContainer;
    @FXML
    private Button addEventButton;

    private Calendar rootParentPane;
    private EventDialogController addEventDialog;

    public CalendarViewController(Calendar parentPane) {
    }

    @FXML
    private void initialize() {
        configureButton();
    }

    @Override
    public void setMediator(Mediator mediador) {

    }

    private void configureButton() {
        addEventButton.setOnAction(event -> {
            openAddEventDialog();
        });
    }

    private void openAddEventDialog() {
        if (addEventDialog == null) {
            addEventDialog = new EventDialogController();
        }
        addEventDialog.clear();
        addEventDialog.show();
        addEventDialog.setOnDialogClosed(evt -> {
          //ojito
           rootParentPane.clone();
        });
    }

    public void setRootParentPane(Calendar parentPane) {
        this.rootParentPane = parentPane;
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }
}
