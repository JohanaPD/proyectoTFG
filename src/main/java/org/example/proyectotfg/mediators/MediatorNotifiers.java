package org.example.proyectotfg.mediators;

import javafx.scene.Parent;
import org.example.proyectotfg.entities.MedicalAppointment;

import java.util.Date;
import java.util.List;

public interface MediatorNotifiers {


    void backFromNotifiersToHome();
    void deleteAppointment(MedicalAppointment medicalAppointment);
    void addAppointment(MedicalAppointment medicalAppointment);
    void editAppointment(MedicalAppointment medicalAppointment);

    void searchAppointments(int idPerson, Date date);

    Parent loadProfessionalsInMediatorCalendar();
    Parent loadAvailableAppointmentsInCalendar(List<MedicalAppointment> medicalAppointments);
    Parent loadNotAvailableAppointmentsInCalendar(List<MedicalAppointment> medicalAppointments);

}
