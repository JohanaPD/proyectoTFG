package org.example.proyectotfg.functions;
import org.junit.jupiter.api.Test;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class FunctionsAppTest {

    @Test
    public void testCalculateAge() {
        // Prueba para calcular la edad con fechas válidas
        Date currentDate = new Date();
        Date birthDate = Date.from(LocalDate.now().minusYears(25).atStartOfDay(ZoneId.systemDefault()).toInstant());
        assertEquals(25, FunctionsApp.calculateAge(currentDate, birthDate), "La edad calculada no es la esperada");

        // Prueba para calcular la edad cuando la fecha de nacimiento es nula
        assertEquals(0, FunctionsApp.calculateAge(currentDate, null), "La edad calculada no es cero cuando la fecha de nacimiento es nula");

        // Prueba para calcular la edad cuando la fecha actual es nula
        assertEquals(0, FunctionsApp.calculateAge(null, birthDate), "La edad calculada no es cero cuando la fecha actual es nula");

        // Prueba para calcular la edad cuando ambas fechas son nulas
        assertEquals(0, FunctionsApp.calculateAge(null, null), "La edad calculada no es cero cuando ambas fechas son nulas");
    }
    @Test
    public void testGenerateStrongPasswordHash() {
        try {
            // Simular una contraseña válida
            String password = "password123";

            // Llamar al método que se está probando
            String hash = FunctionsApp.generateStrongPasswordHash(password);

            // Verificar que el hash no sea nulo y tenga el formato esperado
            assertNotNull(hash, "El hash generado no debe ser nulo");
            assertTrue(hash.contains("|"), "El hash generado debe contener el separador '|'");

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            fail("Se lanzó una excepción durante la generación del hash: " + e.getMessage());
        }
    }

    @Test
    public void testValidatePassword() {
        try {
            // Simular una contraseña válida y su hash correspondiente
            String password = "password123";
            String hash = FunctionsApp.generateStrongPasswordHash(password);

            // Verificar que el hash generado sea válido para la contraseña
            assertTrue(FunctionsApp.validatePassword(password, hash), "La contraseña validada debe ser correcta");

            // Verificar que una contraseña incorrecta no pase la validación
            assertFalse(FunctionsApp.validatePassword("password456", hash), "La contraseña invalidada debe ser incorrecta");

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            fail("Se lanzó una excepción durante la validación del hash: " + e.getMessage());
        }
    }
}
