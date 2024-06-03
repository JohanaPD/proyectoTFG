package org.example.proyectotfg.mediators;

import javafx.scene.Parent;
import org.example.proyectotfg.entities.MedicalAppointment;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.OperationsDBException;

import java.util.Date;
import java.util.List;

public interface MediatorNotifiers {


    void backFromNotifiersToHome();

    void deleteAppointment(MedicalAppointment medicalAppointment) throws OperationsDBException;

    void addAppointment(ProfessionalUser professionalUser, Date date) ;

    void editAppointment(MedicalAppointment medicalAppointment, Date appointmentDate) throws OperationsDBException;

    void searchAppointments(int idPerson, Date date);

    Parent loadProfessionalsInMediatorCalendar();

    Parent loadAvailableAppointmentsInCalendar(List<Date> medicalAppointments);

    Parent loadNotAvailableAppointmentsInCalendar(List<MedicalAppointment> medicalAppointments);

    Parent myNextAppoinments();
}
