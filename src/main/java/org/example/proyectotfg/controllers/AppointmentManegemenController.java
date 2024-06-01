package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.example.proyectotfg.entities.MedicalAppointment;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.functions.FunctionsApp;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorNotifiers;
import org.example.proyectotfg.mediators.ViewController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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

    @FXML
    private ScrollPane professionalsList;
    @FXML
    private Label textConfirm;

    private MediatorNotifiers mediatorNotifiers;
    private Person person;


    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        //  this.datePicker = datePicker;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setTitlePost(String names) {
        username.setText(names);
    }



    public void setTextConfirm(String text) {
        textConfirm.setText(text);
    }

    public void loadProfessionals() {
        Parent professionalUserBox = mediatorNotifiers.loadProfessionalsInMediatorCalendar();
        professionalsList.setContent(professionalUserBox);
    }

    public void loadAppointments(List<MedicalAppointment> medicalAppointmentsAvailable, List<MedicalAppointment> medicalAppointmentsNotAvailable) {
        Parent availableAppointments = mediatorNotifiers.loadAvailableAppointmentsInCalendar(medicalAppointmentsAvailable);
        availableAppointmentsList.getChildren().add(availableAppointments);
        Parent notAvailableAppointments = mediatorNotifiers.loadNotAvailableAppointmentsInCalendar(medicalAppointmentsNotAvailable);
        myAppointments.getChildren().add(notAvailableAppointments);
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
        mediatorNotifiers.backFromNotifiersToHome();
    }


}


