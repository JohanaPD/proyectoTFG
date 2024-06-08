package org.example.proyectotfg.entities;

import org.example.proyectotfg.enumAndTypes.Notificators;

import java.util.Date;

public class MedicalAppointment {
    private int idAppointment;
    private ProfessionalUser professionalUser;
    private NormalUser user;
    private Date visitDate;
    private Notificators notificator;
    public final static  int MAX_APPOINTMENTS = 6;

    public MedicalAppointment(int idAppointment, ProfessionalUser professionalUser, NormalUser user, Date visitDate, Notificators notificator) {
        this.idAppointment = idAppointment;
        this.professionalUser = professionalUser;
        this.user = user;
        this.visitDate = visitDate;
        this.notificator = notificator;
    }

    public MedicalAppointment(int idAppointment, ProfessionalUser professionalUser, NormalUser user, Date visitDate) {
        this.idAppointment = idAppointment;
        this.professionalUser = professionalUser;
        this.user = user;
        this.visitDate = visitDate;
    }
    public MedicalAppointment(ProfessionalUser professionalUser, NormalUser user, Date visitDate, Notificators notificator) {
        this.professionalUser = professionalUser;
        this.user = user;
        this.visitDate = visitDate;
        this.notificator = notificator;
    }

    public MedicalAppointment(MedicalAppointment medicalAppointment) {
        this.idAppointment = medicalAppointment.getIdAppointment();
        this.professionalUser = medicalAppointment.getProfessionalUser();
        this.user = medicalAppointment.getUser();
        this.visitDate = medicalAppointment.getVisitDate();
        this.notificator = medicalAppointment.getNotificator();
    }


    public MedicalAppointment() {

    }



    // Getters y Setters
    public int getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(int idAppointment) {
        this.idAppointment = idAppointment;
    }

    public ProfessionalUser getProfessionalUser() {
        return professionalUser;
    }

    public void setProfessionalUser(ProfessionalUser professionalUser) {
        this.professionalUser = professionalUser;
    }

    public NormalUser getUser() {
        return user;
    }

    public void setUser(NormalUser user) {
        this.user = user;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Notificators getNotificator() {
        return notificator;
    }

    public void setNotificator(Notificators notificator) {
        this.notificator = notificator;
    }

}



