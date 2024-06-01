package org.example.proyectotfg.entities;

import org.example.proyectotfg.enumAndTypes.Notificators;

import java.util.Date;

public class MedicalAppointment {
    // que notificaciones vamos a enviar... serán enum? son unas para usuario profesional y otras para profesional
    private int idCita;
    private ProfessionalUser psicologo;
    private NormalUser usuario;
    private Date visitDate;
    private Notificators notificator;

    public MedicalAppointment(int idCita, ProfessionalUser psicologo, NormalUser usuario, Date visitDate, Notificators notificator) {
        this.idCita = idCita;
        this.psicologo = psicologo;
        this.usuario = usuario;
        this.visitDate = visitDate;
        this.notificator = notificator;
    }

    // Getters y Setters
    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public ProfessionalUser getPsicologo() {
        return psicologo;
    }

    public void setPsicologo(ProfessionalUser psicologo) {
        this.psicologo = psicologo;
    }

    public NormalUser getUsuario() {
        return usuario;
    }

    public void setUsuario(NormalUser usuario) {
        this.usuario = usuario;
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



