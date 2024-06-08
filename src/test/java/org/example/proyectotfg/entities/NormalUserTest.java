package org.example.proyectotfg.entities;

import org.example.proyectotfg.enumAndTypes.StatesUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;
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

public class NormalUserTest {

    private NormalUser normalUser;
    private Direction direction;
    private TypeUser typeUser;
    private StatesUser statesUser;
    private Date birthday;
    private Date registrationDate;
    private Date lastActivityDate;

    @BeforeEach
    public void setUp() throws ParseException, IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        birthday = sdf.parse("01-01-2000");
        registrationDate = sdf.parse("01-01-2022");
        lastActivityDate = sdf.parse("01-01-2023");
        direction = new Direction("Calle Mayor", "Madrid", 28001);
        typeUser = TypeUser.USUARIO_NORMAL;
        statesUser = StatesUser.VERIFIED;
        normalUser = new NormalUser(1, "Juan", "Pérez", "password", birthday, registrationDate, "juan@example.com", typeUser, statesUser, direction, lastActivityDate, "Juanito", false);
    }

    @Test
    public void testSetValidNickname() throws NullArgumentException {
        normalUser.setNickname("Juanito123");
        assertEquals("Juanito123", normalUser.getNickname());
    }

    @Test
    public void testSetNullNickname() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            normalUser.setNickname(null);
        });
        assertEquals("Has introducido uno o más argumentos nulo a la hora de crear el usuario", exception.getMessage());
    }

    @Test
    public void testSetInTherapySessionTrue() {
        normalUser.setInTherapySession(true);
        assertTrue(normalUser.isInTherapySession());
    }

    @Test
    public void testSetInTherapySessionFalse() {
        normalUser.setInTherapySession(false);
        assertFalse(normalUser.isInTherapySession());
    }

    @Test
    public void testConstructorWithValidArguments() throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        NormalUser user = new NormalUser(2, "Ana", "López", "password", birthday, registrationDate, "ana@example.com", typeUser, statesUser, direction, lastActivityDate, "Anita", true);
        assertEquals("Ana", user.getNames());
        assertEquals("López", user.getLastNames());
        assertEquals("ana@example.com", user.getEmail());
        assertEquals("Anita", user.getNickname());
        assertTrue(user.isInTherapySession());
    }

    @Test
    public void testConstructorWithNullNickname() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            new NormalUser(2, "Ana", "López", "password", birthday, registrationDate, "ana@example.com", typeUser, statesUser, direction, lastActivityDate, null, true);
        });
        assertEquals("Has introducido uno o más argumentos nulo a la hora de crear el usuario", exception.getMessage());
    }

    @Test
    public void testConstructorWithEmptyNickname() throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        NormalUser user = new NormalUser(2, "Ana", "López", "password", birthday, registrationDate, "ana@example.com", typeUser, statesUser, direction, lastActivityDate, "", true);
        assertEquals("null", user.getNickname());
    }

    @Test
    public void testToString() {
        String expected = "NormalUser{nickname='Juanito', serchs=[], psychologists=[], inTherapySession=false}";
        assertEquals(expected, normalUser.toString());
    }
}
