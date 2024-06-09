package org.example.proyectotfg.mediators;

import javafx.scene.Parent;
import org.example.proyectotfg.entities.MedicalAppointment;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.OperationsDBException;

import java.util.Date;
import java.util.List;

/**
 * MediatorNotifiers interface for notifier mediator functionality.
 *
 * <p>Authors: [Author Name]</p>
 * <p>Version: [Version Number]</p>
 * <p>Since: [Date of Creation]</p>
 */
public interface MediatorNotifiers {

    /**
     * Navigates back from the notifier interface to the home interface.
     */
    void backFromNotifiersToHome();

    /**
     * Deletes the specified medical appointment.
     *
     * @param medicalAppointment The medical appointment to delete.
     */
    void deleteAppointment(MedicalAppointment medicalAppointment);

    /**
     * Adds an appointment for the specified professional user and date.
     *
     * @param professionalUser The professional user.
     * @param date             The appointment date.
     */
    void addAppointment(ProfessionalUser professionalUser, Date date);

    /**
     * Edits the specified medical appointment with the new appointment date.
     *
     * @param medicalAppointment The medical appointment to edit.
     * @param appointmentDate    The new appointment date.
     */
    void editAppointment(MedicalAppointment medicalAppointment, Date appointmentDate);

    /**
     * Searches appointments for the specified person ID and date.
     *
     * @param idPerson          The ID of the person.
     * @param date              The date to search for appointments.
     * @param updateAppointment Indicates whether to update appointments.
     */
    void searchAppointments(int idPerson, Date date, boolean updateAppointment);

    /**
     * Loads professionals in the mediator calendar.
     *
     * @return The loaded professionals in the calendar.
     */
    Parent loadProfessionalsInMediatorCalendar();

    /**
     * Loads available appointments in the calendar with the provided list of medical appointments.
     *
     * @param medicalAppointments  The list of medical appointments.
     * @param updateAppointment    Indicates whether to update appointments.
     * @return The loaded available appointments in the calendar.
     */
    Parent loadAvailableAppointmentsInCalendar(List<Date> medicalAppointments, boolean updateAppointment);

    /**
     * Loads the next appointments.
     *
     * @return The loaded next appointments.
     */
    Parent myNextAppoinments();
}
