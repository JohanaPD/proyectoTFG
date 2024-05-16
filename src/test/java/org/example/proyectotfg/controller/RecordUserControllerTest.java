package org.example.proyectotfg.controller;

import org.example.proyectotfg.controllers.RecordUserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecordUserControllerTest {

    private RecordUserController controller;

    @BeforeEach
    public void setUp() {
        controller = new RecordUserController();
    }

    @Test
    public void testVerificatorData() throws Exception {
        // Simular datos de entrada para la prueba
        String names = "John";
        String lastNames = "Doe";
        String mail = "john.doe@example.com";
        String confirMail = "john.doe@example.com";
        // Simular una fecha de nacimiento válida
        // Simular una dirección válida
        // Simular contraseñas válidas
        // Simular instancia de Direction válida

        // Llamar al método que se está probando


        // Verificar que no se hayan producido errores
       // assertTrue(errores.isEmpty(), "Se esperaba que no hubiera errores");

        // Verificar más condiciones según sea necesario
    }

    @Test
    public void testMakeRegister() throws Exception {
        // Configurar el entorno de la prueba, si es necesario
        // Llamar al método que se está probando
        controller.makeRegister(null);

        // Verificar el resultado esperado, por ejemplo, si se llama a un método específico
        //assertEquals("Mensaje esperado", controller.getMainController().getMensaje(), "Mensaje incorrecto mostrado");
    }

    // Agregar más métodos de prueba según sea necesario para cubrir otros métodos del controlador
}

