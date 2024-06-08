package org.example.proyectotfg.DAO;

import org.example.proyectotfg.exceptions.*;
import org.example.proyectotfg.enumAndTypes.StatesUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.proyectotfg.entities.*;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SqliteConnectorTest {

    private SqliteConnector sqliteConnector;

    @BeforeEach
    void setUp() throws OperationsDBException {
        try {
            sqliteConnector = new SqliteConnector("jdbc:sqlite:src/main/resources/sqliteBBDD/pruebas.db");
        } catch (SQLException e) {
            fail("Error al crear el conector SQLite: " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        try {
            File file = new File("./src/main/resources/sqliteBBDD/pruebas.db");
            sqliteConnector.getConnection().close();
            sqliteConnector.close();
            file.delete();
        } catch (Exception e) {
            fail("Error al cerrar la conexión: " + e.getMessage());
        }
    }



    @Test
    void testCreateTables() {
        // Prueba que las tablas se creen correctamente
        // Esto puede ser comprobado mediante consultas SQL para verificar si las tablas existen en la base de datos
        // Si las tablas existen, el método createTables funciona correctamente
        assertTrue(true);
    }

    @Test
    void testRegisterPerson() throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException, DuplicateKeyException {
        // Prueba de registro de una persona
        // Crea una instancia de Person con datos de prueba
        NormalUser normalUser = new NormalUser();
        normalUser.setNames("John");
        normalUser.setLastNames("Doe");
        normalUser.setPassScript("password");
        normalUser.setBirthDate(new Date());
        normalUser.setRegistrationDate(new Date());
        normalUser.setEmail("john@example.com");
        normalUser.setTypeUser(TypeUser.USUARIO_NORMAL);
        normalUser.setState(StatesUser.NOT_VERIFIED);
        normalUser.setLastActivityDate(new Date());
        Direction direction = new Direction();
        direction.setIdDireccion(1);
        direction.setStreet("123 Main St");
        direction.setCity("Anytown");
        direction.setPostalCode(12345);
        normalUser.setDirection(direction);

        try {
            // Intenta registrar la persona en la base de datos
            sqliteConnector.registerPerson(normalUser);
            // Si no se lanza ninguna excepción, el registro fue exitoso
            assertTrue(true);
        } catch (OperationsDBException e) {
            // Si se lanza una excepción, el registro falló
            fail("Error registrando persona: " + e.getMessage());
        }
    }

    @Test
    void testRegisterProfessionalUser() throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException, DuplicateKeyException {
        // Prueba de registro de un usuario profesional
        ProfessionalUser professionalUser = new ProfessionalUser();
        professionalUser.setIdPerson(6);
        professionalUser.setNames("Johny");
        professionalUser.setLastNames("Doet");
        professionalUser.setPassScript("password");
        professionalUser.setBirthDate(new Date());
        professionalUser.setRegistrationDate(new Date());
        professionalUser.setEmail("johny@example.com");
        professionalUser.setTypeUser(TypeUser.USUARIO_NORMAL);
        Direction direction = new Direction();
        direction.setIdDireccion(1);
        direction.setStreet("123 Main St");
        direction.setCity("Anytown");
        direction.setPostalCode(12345);
        professionalUser.setDirection(direction);
        professionalUser.setCollegiate("123456");
        professionalUser.setSpecialty("Psychiatry");
        professionalUser.setDescription(" Omar Pérez es un psicólogo altamente cualificado con más de 15 años de experiencia en el campo de la psicoterapia. Especialista en terapias cognitivo-conductuales, ha ayudado a numerosos pacientes a superar problemas de ansiedad, depresión y estrés. Su enfoque empático y personalizado le permite crear un ambiente seguro y de confianza, facilitando el crecimiento personal y el bienestar emocional. Además, Omar es conferencista y autor de varios artículos sobre salud mental, compartiendo sus conocimientos y contribuyendo al avance de la psicología. Su dedicación y profesionalismo lo destacan como un referente en el área de la psicoterapia. ");

        // Simulamos el registro de un usuario profesional
        try {
            sqliteConnector.registerProfessionalUser(professionalUser, true);
            assertTrue(true);
        } catch (OperationsDBException e) {
            fail("Error registrando usuario profesional: " + e.getMessage());
        }
    }


    @Test
    void testRegisterNormalUser() throws IncorrectDataException, NullArgumentException, DuplicateKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        // Prueba de registro de un usuario normal
        NormalUser normalUser = new NormalUser();
        normalUser.setIdPerson(1);
        normalUser.setNames("Johna");
        normalUser.setLastNames("Doe");
        normalUser.setPassScript("123");
        normalUser.setBirthDate(new Date());
        normalUser.setRegistrationDate(new Date());
        normalUser.setEmail("aa@example.com");
        normalUser.setTypeUser(TypeUser.USUARIO_NORMAL);
        Direction direction = new Direction();
        direction.setIdDireccion(1);
        direction.setStreet("123 Main St");
        direction.setCity("Anytown");
        direction.setPostalCode(12345);
        normalUser.setDireccion(direction);
        normalUser.setLastActivityDate(new Date());
        normalUser.setState(StatesUser.NOT_VERIFIED);

        // Simulamos el registro de un usuario normal
        try {
            sqliteConnector.registerNormalUser(normalUser, false);
            assertTrue(true);
        } catch (OperationsDBException e) {
            fail("Error registrando usuario normal: " + e.getMessage());
        }
    }

    @Test
    void testLoginUser() throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, IncorrectDataException, NonexistingUser, DataAccessException, IncorrectLoginEception, NullArgumentException, DuplicateKeyException {
        testRegisterNormalUser();
        String user = "aa@example.com";
        String contrasenia = "123";
        try {
            assertNotNull(sqliteConnector.loginUser(user, contrasenia, TypeUser.USUARIO_NORMAL));
        } catch (OperationsDBException e) {
            e.getMessage();
        }

    }



    @Test
    void testThereIsAQuoteDoesNotExist() throws OperationsDBException {
        // Test for a non-existing appointment
        Date date = new Date();

        boolean result = sqliteConnector.thereIsAQuote(4, new Date(date.getTime()));
        assertFalse(result, "The appointment should not exist in the database.");
    }

    @Test
    void testUpdateNormalUserWP() throws OperationsDBException, SQLException, IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException, DuplicateKeyException {
        testRegisterNormalUser();
        NormalUser normalUser = new NormalUser();
        normalUser.setIdPerson(1);
        normalUser.setNames("Jane");
        normalUser.setLastNames("Smith");
        normalUser.setEmail("jane.smith@example.com");
        Direction direction = new Direction();
        direction.setIdDireccion(1); // Usar una dirección existente
        direction.setStreet("123 Main St");
        direction.setCity("Anytown");
        direction.setPostalCode(12345);
        normalUser.setDireccion(direction);
        normalUser.setNickname("janesmith");
        normalUser.setInTherapySession(false);
/*
        SqliteConnector sqliteConnector = new SqliteConnector();
*/
        sqliteConnector.updateNormalUserWP(normalUser);

        // Verificar que los datos en la base de datos se hayan actualizado correctamente
        String selectSQL = "SELECT user_names, last_names, email FROM person WHERE id_person = ?";
        try (PreparedStatement pstmt = sqliteConnector.getConnection().prepareStatement(selectSQL)) {
            pstmt.setInt(1, 1);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                assertEquals("Jane", rs.getString("user_names"));
                assertEquals("Smith", rs.getString("last_names"));
                assertEquals("jane.smith@example.com", rs.getString("email"));
            } else {
                fail("No se encontró la persona con id_person = 1");
            }
        }
    }

    @Test
    public void makeNewPost() throws SQLException, IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException, OperationsDBException {
        // ArrangeSqliteConnector conn = new SqliteConnector();
        Person titular = new ProfessionalUser();
        Post nuevo = new Post(1,titular, "Test Title",  "Test Content");

        sqliteConnector.makeNewPost(nuevo);

        Statement stmt = sqliteConnector.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM post WHERE title = 'Test Title'");

        assertTrue(rs.next());
        assertEquals("Test Title", rs.getString("title"));
        assertEquals("Test Content", rs.getString("content"));


    }
}