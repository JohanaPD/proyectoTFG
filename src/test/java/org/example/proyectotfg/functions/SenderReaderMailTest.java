package org.example.proyectotfg.functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class SenderReaderMailTest {

    @Test
    void testSetPropiedadesServidorSMTP() {
        SenderReaderMail sender = new SenderReaderMail();
        sender.setPropiedadesServidorSMTP();
        Properties props = sender.getPropiedades();

        assertEquals("true", props.getProperty("mail.smtp.auth"));
        assertEquals("smtp.gmail.com", props.getProperty("mail.smtp.host"));
        assertEquals("587", props.getProperty("mail.smtp.port"));
        assertEquals("true", props.getProperty("mail.smtp.starttls.enable"));
    }

    @Test
    void testCrearMensajeConAdjunto() throws Exception {
        SenderReaderMail sender = new SenderReaderMail();
        sender.setPropiedadesServidorSMTP();
        sender.setSesion(Session.getDefaultInstance(sender.getPropiedades()));

        String pathFichero = "path/to/your/testfile.txt"; // Asegúrate de que este archivo exista o mockea esta parte
        Message mensaje = sender.crearMensajeConAdjunto("from@example.com", "to@example.com", "Test Subject", "Hello, this is a test message.", pathFichero);

        assertTrue(mensaje.getContent() instanceof MimeMultipart);
        MimeMultipart multipart = (MimeMultipart) mensaje.getContent();
        assertEquals(2, multipart.getCount()); // Verifica que hay texto y adjunto
    }

    @Test
    void testEnviarMensajeTexto() {
        // Esta prueba asume que la red está disponible y configurada correctamente. Idealmente debería ser una prueba de integración.
        SenderReaderMail sender = new SenderReaderMail();

        String emisor = "meetpsychproject@gmail.com"; // Asegúrate de que es un correo válido.
        String destinatario = "lara.crof.shs@gmail.com"; // Asegúrate de que es un correo válido.
        String asunto = "Test Subject";
        String textoMensaje = "Hello, World!";
        String direccionEmail = "meetpsychproject@gmail.com"; // Asegúrate de que es un correo válido.
        String password = "Meetpsych1234"; // Asegúrate de que es una contraseña válida.

        assertDoesNotThrow(() -> {
            sender.enviarMensajeTexto(emisor, destinatario, asunto, textoMensaje, direccionEmail, password);
        });

    }

    @Test
    void testEnviarMensajeConAdjunto() {
        // Similar a testEnviarMensajeTexto, debería ser una prueba de integración.
        SenderReaderMail sender = new SenderReaderMail();
        String pathFichero = "org/example/proyectotfg/img/consulta.png";
        assertDoesNotThrow(() -> {
            sender.enviarMensajeConAdjunto("from@example.com", "to@example.com", "Test Subject", "Hello, this is a test message.", "your.email@example.com", "yourPassword", pathFichero);
        });
    }

    @Test
    void testLeerCarpetaInbox() {
        // Esta prueba también debería ser de integración o usar mocking para simular la conexión IMAP.
        SenderReaderMail sender = new SenderReaderMail();
        assertDoesNotThrow(() -> {
            sender.leerCarpetaInbox("your.email@example.com", "yourPassword");
        });
    }
}
