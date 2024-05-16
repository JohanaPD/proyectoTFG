package org.example.proyectotfg.entities;

import org.example.proyectotfg.enumAndTypes.StatesUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonTest {
    private Person person;

    @BeforeEach
    public void setUp() throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        person = new NormalUser();
    }

    @Test
    void testConstructorRegistration() {
        try {
            // Prueba del constructor para el registro
            // Verificar que se crea correctamente una instancia de Person con los datos proporcionados.
            Direction direction = new Direction("Pedriza, 28", "Galapagar", 28260);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date birthDate = sdf.parse("28-10-2004");
            person = new NormalUser("Monica", "Delgado", "1234", birthDate, new Date(), "monicadelgado@gmail.com", TypeUser.USUARIO_NORMAL, direction);
        } catch (ParseException e) {
            throw new AssertionError("Error al parsear los datos");
        } catch (IncorrectDataException | NoSuchAlgorithmException | InvalidKeySpecException |
                 NullArgumentException e) {
            throw new AssertionFailedError(e.getMessage());
        }
    }

    @Test
    void testConstructorCompleto() {
        try {
            // Prueba del constructor para el registro
            // Verificar que se crea correctamente una instancia de Person con los datos proporcionados.
            Direction direction = new Direction(1, "Pedriza, 28", "Galapagar", 28260);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date birthDate = sdf.parse("28-10-2004");
            person = new NormalUser(1, "Monica", "Delgado", "1234", birthDate, new Date(), "monicadelgado@gmail.com", TypeUser.USUARIO_NORMAL, StatesUser.VERIFIED, direction, new Date());
            //Si llega hasta aquí no debe de haber dado ninguna excepción a la hora de crear el objeto, por lo tanto estaría perfectamente creado
        } catch (ParseException e) {
            throw new AssertionError("Error al parsear los datos");
        } catch (IncorrectDataException | NoSuchAlgorithmException | InvalidKeySpecException |
                 NullArgumentException e) {
            throw new AssertionFailedError(e.getMessage());
        }
    }

    @Test
    void testSetIdPerson() {
        // Prueba del método setIdPerson()
        // Verificar que se establece correctamente el ID de la persona.
        try {
            person.setIdPerson(1);
        } catch (IncorrectDataException e) {
            throw new AssertionFailedError(e.getMessage());
        }
    }

    @Test
    void testSetNames() {
        // Prueba del método setNames()
        // Verificar que se establecen correctamente los nombres de la persona.try
        try {
            person.setNames("Monica");
        } catch (IncorrectDataException | NullArgumentException e) {
            throw new AssertionFailedError(e.getMessage());
        }


    }

    @Test
    void testSetLastNames() {
        // Prueba del método setLastNames()
        // Verificar que se establecen correctamente los apellidos de la persona.try
        try {
            person.setLastNames("Delgado");
        } catch (NullArgumentException e) {
            throw new AssertionFailedError(e.getMessage());
        }


    }

    @Test
    void testSetDirection() {
        // Prueba del método setDirection()
        // Verificar que se establece correctamente la dirección de la persona.try
        try {
            Direction direction = new Direction(1, "Pedriza, 28", "Galapagar", 28260);
            person.setDirection(direction);
        } catch (IncorrectDataException | NullArgumentException e) {
            throw new AssertionFailedError(e.getMessage());
        }


    }

    @Test
    void testSetEmail() {
        // Prueba del método setEmail()
        // Verificar que se establece correctamente el correo electrónico de la persona.try
        try {
            person.setEmail("monicadelgado@gmail.com");
        } catch (IncorrectDataException | NullArgumentException e) {
            throw new AssertionFailedError(e.getMessage());
        }


    }

    @Test
    void testSetBirthDate() {
        // Prueba del método setBirthDate()
        // Verificar que se establece correctamente la fecha de nacimiento de la persona.try
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date birthDate = sdf.parse("28-10-2004");
            person.setBirthDate(birthDate);
        } catch (IncorrectDataException | ParseException e) {
            throw new AssertionFailedError("Error al parsear");
        } catch (NullArgumentException e) {
            throw new AssertionFailedError(e.getMessage());
        }


    }

    @Test
    void testSetRegistrationDate() {
        // Prueba del método setRegistrationDate()
        // Verificar que se establece correctamente la fecha de registro de la persona.try
        try {
            person.setRegistrationDate(new Date());
        } catch (IncorrectDataException | NullArgumentException e) {
            throw new AssertionFailedError(e.getMessage());
        }


    }

    @Test
    void testSetTypeUser() {
        // Prueba del método setTypeUser()
        // Verificar que se establece correctamente el tipo de usuario de la persona.try
        try {
            person.setTypeUser(TypeUser.USUARIO_NORMAL);
        } catch (NullArgumentException e) {
            throw new AssertionFailedError(e.getMessage());
        }


    }

    @Test
    void testSetPassScript() {
        // Prueba del método setPassScript()
        // Verificar que se establece correctamente la contraseña encriptada de la persona.try
        try {
            person.setPassScript("1234");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NullArgumentException e) {
            throw new AssertionFailedError(e.getMessage());
        }


    }

    @Test
    void testSetLastActivityDate() {
        // Prueba del método setLastActivityDate()
        // Verificar que se establece correctamente la fecha de última actividad de la persona.try
        try {
            person.setLastActivityDate(new Date());
        } catch (NullArgumentException e) {
            throw new AssertionFailedError(e.getMessage());
        }
    }

    @Test
    void testSetState() {
        // Prueba del método setState()
        // Verificar que se establece correctamente el estado de la persona.try
        person.setState(StatesUser.VERIFIED);

    }


}
