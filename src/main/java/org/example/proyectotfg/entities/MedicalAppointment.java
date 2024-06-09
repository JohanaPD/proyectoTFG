package org.example.proyectotfg.entities;

import org.example.proyectotfg.enumAndTypes.Notificators;

import java.util.Date;
/**
 * Represents a direction, containing street, city, and postal code.
 */

public class MedicalAppointment {
    private int idAppointment;
    private ProfessionalUser professionalUser;
    private NormalUser user;
    private Date visitDate;
    private Notificators notificator;
    public final static  int MAX_APPOINTMENTS = 6;


    /**
     * Constructs a new MedicalAppointment object with the specified ID, professional user, normal user, visit date, and notificator.
     *
     * @param idAppointment      The appointment ID.
     * @param professionalUser   The professional user associated with the appointment.
     * @param user               The normal user associated with the appointment.
     * @param visitDate          The date of the appointment.
     * @param notificator        The notificator used for the appointment.
     */
    public MedicalAppointment(int idAppointment, ProfessionalUser professionalUser, NormalUser user, Date visitDate, Notificators notificator) {
        this.idAppointment = idAppointment;
        this.professionalUser = professionalUser;
        this.user = user;
        this.visitDate = visitDate;
        this.notificator = notificator;
    }

    /**
     * Constructs a new MedicalAppointment object with the specified ID, professional user, normal user, and visit date.
     *
     * @param idAppointment      The appointment ID.
     * @param professionalUser   The professional user associated with the appointment.
     * @param user               The normal user associated with the appointment.
     * @param visitDate          The date of the appointment.
     */

    public MedicalAppointment(int idAppointment, ProfessionalUser professionalUser, NormalUser user, Date visitDate) {
        this.idAppointment = idAppointment;
        this.professionalUser = professionalUser;
        this.user = user;
        this.visitDate = visitDate;
    }

    /**
     * Constructs a new MedicalAppointment object with the specified ID, professional user, normal user, and visit date.
     *
     * @param professionalUser   The professional user associated with the appointment.
     * @param user               The normal user associated with the appointment.
     * @param visitDate          The date of the appointment.
     * @param notificator        The notificator associated with the appointment.
     */
    public MedicalAppointment(ProfessionalUser professionalUser, NormalUser user, Date visitDate, Notificators notificator) {
        this.professionalUser = professionalUser;
        this.user = user;
        this.visitDate = visitDate;
        this.notificator = notificator;
    }
    /**
     * Constructs a new MedicalAppointment object with the same attributes as the provided MedicalAppointment object.
     *
     * @param medicalAppointment The MedicalAppointment object from which to copy attributes.
     */
    public MedicalAppointment(MedicalAppointment medicalAppointment) {
        this.idAppointment = medicalAppointment.getIdAppointment();
        this.professionalUser = medicalAppointment.getProfessionalUser();
        this.user = medicalAppointment.getUser();
        this.visitDate = medicalAppointment.getVisitDate();
        this.notificator = medicalAppointment.getNotificator();
    }


    /**
     * Constructs a new MedicalAppointment object with default values.
     */
    public MedicalAppointment() {

    }

    /**
     * Retrieves the ID of the medical appointment.
     *
     * @return The ID of the medical appointment.
     */
    public int getIdAppointment() {
        return idAppointment;
    }

    /**
     * Sets the ID of the medical appointment.
     *
     * @param idAppointment The ID to set for the medical appointment.
     */
    public void setIdAppointment(int idAppointment) {
        this.idAppointment = idAppointment;
    }

    /**
     * Retrieves the professional user associated with the medical appointment.
     *
     * @return The professional user associated with the medical appointment.
     */
    public ProfessionalUser getProfessionalUser() {
        return professionalUser;
    }

    /**
     * Sets the professional user associated with the medical appointment.
     *
     * @param professionalUser The professional user to set for the medical appointment.
     */
    public void setProfessionalUser(ProfessionalUser professionalUser) {
        this.professionalUser = professionalUser;
    }

    /**
     * Retrieves the normal user associated with the medical appointment.
     *
     * @return The normal user associated with the medical appointment.
     */
    public NormalUser getUser() {
        return user;
    }

    /**
     * Sets the normal user associated with the medical appointment.
     *
     * @param user The normal user to set for the medical appointment.
     */
    public void setUser(NormalUser user) {
        this.user = user;
    }

    /**
     * Retrieves the visit date of the medical appointment.
     *
     * @return The visit date of the medical appointment.
     */
    public Date getVisitDate() {
        return visitDate;
    }

    /**
     * Sets the visit date of the medical appointment.
     *
     * @param visitDate The visit date to set for the medical appointment.
     */
    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    /**
     * Retrieves the notificator associated with the medical appointment.
     *
     * @return The notificator associated with the medical appointment.
     */
    public Notificators getNotificator() {
        return notificator;
    }

    /**
     * Sets the notificator associated with the medical appointment.
     *
     * @param notificator The notificator to set for the medical appointment.
     */
    public void setNotificator(Notificators notificator) {
        this.notificator = notificator;
    }


}



