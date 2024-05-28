package org.example.proyectotfg.mediators;

import org.example.proyectotfg.entities.MedicalAppointment;

public interface MediatorNotifiers {


    void backFromNotifiersToHome();
    void deleteAppointment(MedicalAppointment medicalAppointment);
    void addAppointment(MedicalAppointment medicalAppointment);
    void editAppointment(MedicalAppointment medicalAppointment);

}
