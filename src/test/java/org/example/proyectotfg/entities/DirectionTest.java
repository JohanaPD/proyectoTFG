package org.example.proyectotfg.entities;

import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    private Direction direction;

    @BeforeEach
    public void setUp() {
        direction = new Direction();
    }

    @Test
    public void testSetIdDireccion_ValidId() throws IncorrectDataException {
        direction.setIdDireccion(123456789);
        assertEquals(123456789, direction.getIdDireccion());
    }

    @Test
    public void testSetIdDireccion_InvalidId() {
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            direction.setIdDireccion(-1);
        });
        assertEquals("Verifica el id de persona, solo se aceptan números", exception.getMessage());
    }

    @Test
    public void testSetStreet_ValidStreet() throws NullArgumentException, IncorrectDataException {
        direction.setStreet("Calle Mayor");
        assertEquals("Calle Mayor", direction.getStreet());
    }

    @Test
    public void testSetStreet_NullStreet() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            direction.setStreet(null);
        });
        assertEquals("Hay argumentos nulos al crear la dirección", exception.getMessage());
    }

    @Test
    public void testSetStreet_InvalidStreet() {
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            direction.setStreet("Calle Mayor @");
        });
        assertEquals("La calle no puede tener simbolos", exception.getMessage());
    }

    @Test
    public void testSetCity_ValidCity() throws NullArgumentException, IncorrectDataException {
        direction.setCity("Madrid");
        assertEquals("Madrid", direction.getCity());
    }

    @Test
    public void testSetCity_NullCity() {
        Exception exception = assertThrows(NullArgumentException.class, () -> {
            direction.setCity(null);
        });
        assertEquals("Hay argumentos nulos al crear la dirección", exception.getMessage());
    }

    @Test
    public void testSetCity_InvalidCity() {
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            direction.setCity("Madrid123");
        });
        assertEquals("La ciudad solo puede contener letras", exception.getMessage());
    }

    @Test
    public void testSetPostalCode_ValidPostalCode() throws IncorrectDataException {
        direction.setPostalCode(28001);
        assertEquals(28001, direction.getPostalCode());
    }

    @Test
    public void testSetPostalCode_InvalidPostalCode() {
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            direction.setPostalCode(123456); // más de 5 dígitos
        });
        assertEquals("El código postal solo puede contener hasta 5 números", exception.getMessage());
    }

    @Test
    public void testConstructor_ValidArguments() throws NullArgumentException, IncorrectDataException {
        Direction dir = new Direction("Calle Mayor", "Madrid", 28001);
        assertEquals("Calle Mayor", dir.getStreet());
        assertEquals("Madrid", dir.getCity());
        assertEquals(28001, dir.getPostalCode());
    }

    @Test
    public void testConstructor_InvalidStreet() {
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            new Direction("Calle Mayor @", "Madrid", 28001);
        });
        assertEquals("La calle no puede tener simbolos", exception.getMessage());
    }

    @Test
    public void testConstructor_InvalidCity() {
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            new Direction("Calle Mayor", "Madrid123", 28001);
        });
        assertEquals("La ciudad solo puede contener letras", exception.getMessage());
    }

    @Test
    public void testConstructor_InvalidPostalCode() {
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            new Direction("Calle Mayor", "Madrid", 123456);
        });
        assertEquals("El código postal solo puede contener hasta 5 números", exception.getMessage());
    }

    @Test
    public void testToString() throws NullArgumentException, IncorrectDataException {
        Direction dir = new Direction("Calle Mayor", "Madrid", 28001);
        String expected = "Direction{idDireccion=0, street='Calle Mayor', city='Madrid', postalCode=28001}";
        assertEquals(expected, dir.toString());
    }
}
