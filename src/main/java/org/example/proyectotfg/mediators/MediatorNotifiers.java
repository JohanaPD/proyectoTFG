package org.example.proyectotfg.mediators;

import javafx.scene.Parent;
import org.example.proyectotfg.entities.MedicalAppointment;

public interface MediatorNotifiers {


    void backFromNotifiersToHome();
    void deleteAppointment(MedicalAppointment medicalAppointment);
    void addAppointment(MedicalAppointment medicalAppointment);
    void editAppointment(MedicalAppointment medicalAppointment);
    Parent loadProfessionalsInMediatorCalendar();

}
