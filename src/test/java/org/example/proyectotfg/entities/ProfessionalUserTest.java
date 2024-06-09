package org.example.proyectotfg.entities;

import org.example.proyectotfg.enumAndTypes.StatesUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProfessionalUserTest {

    private ProfessionalUser professionalUser;
    private Direction direction;

    @BeforeEach
    public void setUp() throws NullArgumentException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException {
        direction = new Direction("Calle Real", "Madrid", 28001);
        professionalUser = new ProfessionalUser(1, "Maria", "Lopez", "securePass", new Date(), new Date(), "maria@example.com", TypeUser.PSICOLOGO, StatesUser.VERIFIED, direction, new Date(), "12345", "Psychology", "Experienced psychologist");
    }

    @Test
    public void testSetCollegiateValid() throws NullArgumentException, IncorrectDataException {
        professionalUser.setCollegiate("67890");
        assertEquals("67890", professionalUser.getCollegiate());
    }

    @Test
    public void testSetCollegiateInvalid() {
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            professionalUser.setCollegiate("!@#$%");
        });
        assertEquals("Verifica los datos introducidos, solo se aceptan letras", exception.getMessage());
    }

    @Test
    public void testSetCollegiateNull() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            professionalUser.setCollegiate(null);
        });
        assertEquals("Has pasado argumentos nulos a la hora de crear el psicólogo", exception.getMessage());
    }

    @Test
    public void testSetSpecialtyValid() throws NullArgumentException, IncorrectDataException {
        professionalUser.setSpecialty("Neurology");
        assertEquals("Neurology", professionalUser.getSpecialty());
    }

    @Test
    public void testSetSpecialtyInvalid() {
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            professionalUser.setSpecialty("Neuro123");
        });
        assertEquals("Verifica los datos introducidos, solo se aceptan letras", exception.getMessage());
    }

    @Test
    public void testSetSpecialtyNull() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            professionalUser.setSpecialty(null);
        });
        assertEquals("Has pasado argumentos nulos a la hora de crear el psicólogo", exception.getMessage());
    }

    @Test
    public void testSetDescriptionValid() throws NullArgumentException, IncorrectDataException {
        professionalUser.setDescription("New description");
        assertEquals("New description", professionalUser.getDescription());
    }

    @Test
    public void testSetDescriptionInvalid() {
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            professionalUser.setDescription("Invalid description with numbers 123");
        });
        assertEquals("Verifica los datos introducidos, solo se aceptan letras", exception.getMessage());
    }

    @Test
    public void testSetDescriptionNull() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            professionalUser.setDescription(null);
        });
        assertEquals("Has pasado argumentos nulos a la hora de crear el psicólogo", exception.getMessage());
    }

    @Test
    public void testSetPatientsValid() throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        List<NormalUser> patients = new ArrayList<>();
        patients.add(new NormalUser());
        professionalUser.setPatients(patients);
        assertEquals(patients, professionalUser.getPatients());
    }

    @Test
    public void testToString() {
        String expected = "ProfessionalUser{collegiate='12345', speciality='Psychology', description='Experienced psychologist', patients=[]}";
        assertEquals(expected, professionalUser.toString());
    }

    @Test
    public void testConstructorValidArguments() throws NullArgumentException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException {
        ProfessionalUser newProfessionalUser = new ProfessionalUser("Juan", "Gomez", "securePass2", new Date(), new Date(), "juan@example.com", TypeUser.PSICOLOGO, direction, "54321", "Cardiology", "Expert cardiologist");
        assertEquals("Juan", newProfessionalUser.getNames());
        assertEquals("Gomez", newProfessionalUser.getLastNames());
        assertEquals("securePass2", newProfessionalUser.getPassScript());
        assertEquals("juan@example.com", newProfessionalUser.getEmail());
        assertEquals("54321", newProfessionalUser.getCollegiate());
        assertEquals("Cardiology", newProfessionalUser.getSpecialty());
        assertEquals("Expert cardiologist", newProfessionalUser.getDescription());
    }
}
