package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorNotifiers;
import org.example.proyectotfg.mediators.ViewController;

public class AppointmentManegemenController implements ViewController {

    @FXML
    private Label username;

    @FXML
    private AnchorPane appointmentsList;

    @FXML
    private AnchorPane availableAppointmentsList;

    @FXML
    private DatePicker datePicker;

    @FXML
    private AnchorPane myAppointments;
    private MediatorNotifiers mediatorNotifiers;
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setTitlePost(String names) {
        username.setText(names);
    }

    @Override
    public void setMediator(Mediator mediador) {
        this.mediatorNotifiers = (MediatorNotifiers) mediador;
    }

    @FXML
    void deleteAppoinment(ActionEvent event) {
    }

    @FXML
    void editAppoinment(ActionEvent event) {

    }

    @FXML
    void goToHome(ActionEvent event) {
        mediatorNotifiers.volver();
    }


}


