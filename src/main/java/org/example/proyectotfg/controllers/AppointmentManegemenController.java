package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import org.example.proyectotfg.entities.MedicalAppointment;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.OperationsDBException;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorNotifiers;
import org.example.proyectotfg.mediators.ViewController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class AppointmentManegemenController implements ViewController {

    @FXML
    private Label username;
    @FXML
    private AnchorPane availableAppointmentsList;

    @FXML
    private DatePicker datePicker;

    @FXML
    private AnchorPane myAppointments;
    @FXML
    private Label textAppointmentToSelect;
    @FXML
    private Label textAppointment;
    @FXML
    private ScrollPane professionalsList;

    private MedicalAppointment actualMediacalAppointment;
    private MediatorNotifiers mediatorNotifiers;
    private Person person;
    private ProfessionalUser professionalUser;
    private Date appointmentDate;


    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
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

    public MedicalAppointment getActualMediacalAppointment() {
        return actualMediacalAppointment;
    }

    public void setActualMediacalAppointment(MedicalAppointment actualMediacalAppointment) {
        this.actualMediacalAppointment = actualMediacalAppointment;
    }

    public void setTextConfirm(String text) {
        textAppointmentToSelect.setText(text);
    }

    public void setTextAppointment(String textAppointment) {
        this.textAppointment.setText(textAppointment);
    }

    public void loadProfessionals() {
        Parent professionalUserBox = mediatorNotifiers.loadProfessionalsInMediatorCalendar();
        professionalsList.setContent(professionalUserBox);
    }

    public void loadMyAppointments() {
        Parent parent = mediatorNotifiers.myNextAppoinments();
        myAppointments.getChildren().add(parent);
    }

    public void loadAppointments(List<Date> medicalAppointmentsAvailable, boolean updateAppointment) {
        Parent availableAppointments = mediatorNotifiers.loadAvailableAppointmentsInCalendar(medicalAppointmentsAvailable, updateAppointment);
        availableAppointmentsList.getChildren().add(availableAppointments);
    }

    public ProfessionalUser getProfessionalUser() {
        return professionalUser;
    }

    public void setProfessionalUser(ProfessionalUser professionalUser) {
        this.professionalUser = professionalUser;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    @Override
    public void setMediator(Mediator mediador) {
        this.mediatorNotifiers = (MediatorNotifiers) mediador;
    }

    @FXML
    void deleteAppoinment(ActionEvent event) throws OperationsDBException {
        mediatorNotifiers.deleteAppointment(actualMediacalAppointment);
    }

    @FXML
    void editAppoinment(ActionEvent event) {
        //necesitamos tener el medical app, que ya estaría instanciado en el momento de hacer el callback
        if (actualMediacalAppointment == null) {
            ((MainController) mediatorNotifiers).showInfo("Error", "Selecciona una de tus citas y pulsa \"editar\"");
        } else {
            datePicker.setValue(null);

            if (datePicker.getValue() == null) {
                ((MainController) mediatorNotifiers).showInfo("Error", "Necesita seleccionar una nueva fecha");
            }
            datePicker.setOnAction(e -> {

                LocalDate localDate = datePicker.getValue();
                Date nuevaFecha = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                ((MainController) mediatorNotifiers).searchAppointments(actualMediacalAppointment.getProfessionalUser().getIdPerson(), nuevaFecha, true);

          /*  try {
                mediatorNotifiers.editAppointment(actualMediacalAppointment, appointmentDate);
            } catch (OperationsDBException ex) {
                ((MainController) mediatorNotifiers).showError("Error", ex.getMessage());
            }*/

            });
        }
    }

    @FXML
    void saveAppoinment(ActionEvent event) {
        mediatorNotifiers.addAppointment(professionalUser, appointmentDate);

    }

    @FXML
    void goToHome(ActionEvent event) {
        mediatorNotifiers.backFromNotifiersToHome();
    }
}


