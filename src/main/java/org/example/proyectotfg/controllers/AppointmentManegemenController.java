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

/**
 * Manages appointment-related operations.
 *
 * <p>Authors: Johana Pardo, Daniel Ocaña</p>
 * <p>Version: Java 21</p>
 * <p>Since: 2024-06-08</p>
 */
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


    /**
     * Gets the DatePicker object.
     *
     * @return the date picker.
     */
    public DatePicker getDatePicker() {
        return datePicker;
    }

    /**
     * Sets the DatePicker object.
     *
     * @param datePicker the date picker to set.
     */
    public void setDatePicker(DatePicker datePicker) {
    }

    /**
     * Gets the Person object.
     *
     * @return the person.
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the Person object.
     *
     * @param person the person to set.
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Sets the title post.
     *
     * @param names the name to set as the title.
     */
    public void setTitlePost(String names) {
        username.setText(names);
    }

    /**
     * Gets the current medical appointment.
     *
     * @return the current medical appointment.
     */
    public MedicalAppointment getActualMediacalAppointment() {
        return actualMediacalAppointment;
    }

    /**
     * Sets the current medical appointment.
     *
     * @param actualMediacalAppointment the medical appointment to set.
     */
    public void setActualMediacalAppointment(MedicalAppointment actualMediacalAppointment) {
        this.actualMediacalAppointment = actualMediacalAppointment;
    }

    /**
     * Sets the confirmation text.
     *
     * @param text the text to set.
     */
    public void setTextConfirm(String text) {
        textAppointmentToSelect.setText(text);
    }

    /**
     * Sets the appointment text.
     *
     * @param textAppointment the text to set.
     */
    public void setTextAppointment(String textAppointment) {
        this.textAppointment.setText(textAppointment);
    }

    /**
     * Loads the list of professionals.
     */
    public void loadProfessionals() {
        Parent professionalUserBox = mediatorNotifiers.loadProfessionalsInMediatorCalendar();
        professionalsList.setContent(professionalUserBox);
    }

    /**
     * Loads the user's appointments.
     */
    public void loadMyAppointments() {
        Parent parent = mediatorNotifiers.myNextAppoinments();
        myAppointments.getChildren().add(parent);
    }

    /**
     * Loads the available appointments.
     *
     * @param medicalAppointmentsAvailable the list of available medical appointments.
     * @param updateAppointment a flag indicating whether to update the appointment.
     */
    public void loadAppointments(List<Date> medicalAppointmentsAvailable, boolean updateAppointment) {
        Parent availableAppointments = mediatorNotifiers.loadAvailableAppointmentsInCalendar(medicalAppointmentsAvailable, updateAppointment);
        availableAppointmentsList.getChildren().add(availableAppointments);
    }

    /**
     * Gets the professional user.
     *
     * @return the professional user.
     */
    public ProfessionalUser getProfessionalUser() {
        return professionalUser;
    }

    /**
     * Sets the professional user.
     *
     * @param professionalUser the professional user to set.
     */
    public void setProfessionalUser(ProfessionalUser professionalUser) {
        this.professionalUser = professionalUser;
    }

    /**
     * Gets the appointment date.
     *
     * @return the appointment date.
     */
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Sets the appointment date.
     *
     * @param appointmentDate the appointment date to set.
     */
    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * Sets the mediator.
     *
     * @param mediador the mediator to set.
     */
    @Override
    public void setMediator(Mediator mediador) {
        this.mediatorNotifiers = (MediatorNotifiers) mediador;
    }

    /**
     * Deletes the appointment.
     *
     * @param event the action event.
     * @throws OperationsDBException if there is an error deleting the appointment.
     */
    @FXML
    void deleteAppoinment(ActionEvent event) throws OperationsDBException {
        mediatorNotifiers.deleteAppointment(actualMediacalAppointment);
    }

    /**
     * Edits the appointment.
     *
     * @param event the action event.
     */
    @FXML
    void editAppoinment(ActionEvent event) {

        if (actualMediacalAppointment == null) {
            MainController.showInfo("Error", "Selecciona una de tus citas y pulsa \"editar\"");
        } else {
            datePicker.setValue(null);

            if (datePicker.getValue() == null) {
                MainController.showInfo("Actualizando cita", "Selecciona la fecha en la parte de arriba para cambiar la cita");
            }
            datePicker.setOnAction(e -> {
                LocalDate localDate = datePicker.getValue();
                MainController.showInfo("Actualizando cita", "Ahora elige la hora de la cita abajo para que se actualice");

                Date nuevaFecha = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                mediatorNotifiers.searchAppointments(actualMediacalAppointment.getProfessionalUser().getIdPerson(), nuevaFecha, true);
            });
        }
    }

    /**
     * Saves the appointment.
     *
     * @param event the action event.
     */
    @FXML
    void saveAppoinment(ActionEvent event) {
        mediatorNotifiers.addAppointment(professionalUser, appointmentDate);
    }

    /**
     * Navigates back to the home screen.
     *
     * @param event the action event.
     */
    @FXML
    void goToHome(ActionEvent event) {
        mediatorNotifiers.backFromNotifiersToHome();
    }

}


