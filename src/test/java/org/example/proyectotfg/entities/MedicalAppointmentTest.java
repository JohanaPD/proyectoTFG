package org.example.proyectotfg.entities;

import org.example.proyectotfg.enumAndTypes.Notificators;
import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class MedicalAppointmentTest {

    private MedicalAppointment medicalAppointment;
    private ProfessionalUser professionalUser;
    private NormalUser user;

    @BeforeEach
    public void setUp() throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        professionalUser = new ProfessionalUser(); // Inicializar según sea necesario
        user = new NormalUser(); // Inicializar según sea necesario
        medicalAppointment = new MedicalAppointment();
    }

    @Test
    public void testSetValidIdAppointment() {
        medicalAppointment.setIdAppointment(123);
        assertEquals(123, medicalAppointment.getIdAppointment());
    }

    @Test
    public void testSetInvalidIdAppointment() {
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            medicalAppointment.setIdAppointment(-1);
        });
        assertEquals("El ID de la cita debe ser un número positivo", exception.getMessage());
    }

    @Test
    public void testSetValidProfessionalUser() throws NullArgumentException {
        medicalAppointment.setProfessionalUser(professionalUser);
        assertEquals(professionalUser, medicalAppointment.getProfessionalUser());
    }

    @Test
    public void testSetNullProfessionalUser() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            medicalAppointment.setProfessionalUser(null);
        });
        assertEquals("El profesional no puede ser nulo", exception.getMessage());
    }

    @Test
    public void testSetValidUser() throws NullArgumentException {
        medicalAppointment.setUser(user);
        assertEquals(user, medicalAppointment.getUser());
    }

    @Test
    public void testSetNullUser() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            medicalAppointment.setUser(null);
        });
        assertEquals("El usuario no puede ser nulo", exception.getMessage());
    }

    @Test
    public void testSetValidVisitDate() throws ParseException, NullArgumentException, IncorrectDataException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date visitDate = sdf.parse("01-01-2023");
        medicalAppointment.setVisitDate(visitDate);
        assertEquals(visitDate, medicalAppointment.getVisitDate());
    }

    @Test
    public void testSetNullVisitDate() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            medicalAppointment.setVisitDate(null);
        });
        assertEquals("La fecha de la visita no puede ser nula", exception.getMessage());
    }

    @Test
    public void testSetInvalidVisitDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date visitDate = sdf.parse("01-01-2000"); // Suponiendo que las fechas pasadas no son válidas
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            medicalAppointment.setVisitDate(visitDate);
        });
        assertEquals("La fecha de la visita no puede ser en el pasado", exception.getMessage());
    }

    @Test
    public void testSetValidNotificator() {
        medicalAppointment.setNotificator(Notificators.CITACREADA);
        assertEquals(Notificators.CITACREADA, medicalAppointment.getNotificator());
    }

    @Test
    public void testSetNullNotificator() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            medicalAppointment.setNotificator(null);
        });
        assertEquals("El notificador no puede ser nulo", exception.getMessage());
    }

    @Test
    public void testConstructorWithValidArguments() throws ParseException, NullArgumentException, IncorrectDataException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date visitDate = sdf.parse("01-01-2023");
        MedicalAppointment appointment = new MedicalAppointment(1, professionalUser, user, visitDate, Notificators.CITACREADA);
        assertEquals(1, appointment.getIdAppointment());
        assertEquals(professionalUser, appointment.getProfessionalUser());
        assertEquals(user, appointment.getUser());
        assertEquals(visitDate, appointment.getVisitDate());
        assertEquals(Notificators.CITACREADA, appointment.getNotificator());
    }

    @Test
    public void testConstructorWithInvalidVisitDate() {
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date visitDate = sdf.parse("01-01-2000"); // Fecha pasada
            new MedicalAppointment(1, professionalUser, user, visitDate, Notificators.CITACREADA);
        });
        assertEquals("La fecha de la visita no puede ser en el pasado", exception.getMessage());
    }

    @Test
    public void testConstructorWithNullProfessionalUser() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date visitDate = sdf.parse("01-01-2023");
            new MedicalAppointment(1, null, user, visitDate, Notificators.CITACREADA);
        });
        assertEquals("El profesional no puede ser nulo", exception.getMessage());
    }

    @Test
    public void testConstructorWithNullUser() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date visitDate = sdf.parse("01-01-2023");
            new MedicalAppointment(1, professionalUser, null, visitDate, Notificators.CITACREADA);
        });
        assertEquals("El usuario no puede ser nulo", exception.getMessage());
    }

    @Test
    public void testToString() throws ParseException, NullArgumentException, IncorrectDataException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date visitDate = sdf.parse("01-01-2023");
        MedicalAppointment appointment = new MedicalAppointment(1, professionalUser, user, visitDate, Notificators.CITACREADA);
        String expected = "MedicalAppointment{idAppointment=1, professionalUser=" + professionalUser.toString() + ", user=" + user.toString() + ", visitDate=" + visitDate + ", notificator=CITACREADA}";
        assertEquals(expected, appointment.toString());
    }
}
