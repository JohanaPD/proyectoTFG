package org.example.proyectotfg.functions;

import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.enumAndTypes.TypeUser;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

public class FunctionsApp {

    public static int calculateAge(Date currentDate, Date birthDate) {
        if (birthDate != null && currentDate != null) {
            LocalDate localBirthDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localCurrentDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int edad = Period.between(localBirthDate, localCurrentDate).getYears();
            return edad;
        } else {
            return 0;
        }
    }

    public static String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final int ITERATIONS = 1000;
        final int KEY_LENGTH = 256;
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return toHex(salt) + "|" + toHex(hash);
    }

    public static boolean validatePassword(String enteredPassword, String storedHashWithSalt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final int ITERATIONS = 1000;
        final int KEY_LENGTH = 256;
        String[] parts = storedHashWithSalt.split("\\|");
        if (parts.length != 2) {
            throw new IllegalArgumentException("El formato del hash almacenado no es el esperado.");
        }

        byte[] salt = fromHex(parts[0]);
        byte[] storedHash = fromHex(parts[1]);

        KeySpec spec = new PBEKeySpec(enteredPassword.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = factory.generateSecret(spec).getEncoded();

        return Arrays.equals(storedHash, testHash);
    }

    private static String toHex(byte[] array) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : array) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    private static void verificatedPassScript(String pass1, String hashedData) {
        boolean authenticated = false;
        try {
            authenticated = FunctionsApp.validatePassword(pass1, hashedData);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.err.println("Error al validar la contraseña.");
            e.printStackTrace();
            System.exit(1);
        }

        if (authenticated) {
            System.out.println("¡Acceso concedido! Bienvenido!");
        } else {
            System.out.println("¡Acceso denegado! Usuario o contraseña incorrectos.");
        }
    }

    public static String encriptPassScript(String pass1) {
        String hashedData = "";
        try {
            hashedData = FunctionsApp.generateStrongPasswordHash(pass1);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.err.println("Error al encriptar la contraseña.");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Contraseña encriptada y hash generados. Guardando en la base de datos..." +
                "contraseña encriptada" + hashedData + "  "
        );
        return hashedData;
    }

    public static String devolverStringMail(Person person) {
        String mensajeHtml1 = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "    body { font-family: Arial, sans-serif; }\n"
                + "    input, button { margin-top: 10px; }\n"
                + "</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <h1>¡Gracias por registrarte!</h1>\n"
                + "    <p>Para mantener la seguridad de tu privacidad, por favor, genera un nickname que usarás en nuestra plataforma.</p>\n"
                + "    <form action=\"https://https://edu-meetpshyc1.odoo/usuario/establecer-nickname\" method=\"POST\">\n"
                + "        <input type=\"text\" name=\"nickname\" placeholder=\"Escribe tu nickname aquí\" required>\n"
                + "        <button type=\"submit\">Enviar Nickname</button>\n"
                + "    </form>\n"
                + "    <p>Además, necesitamos que verifiques tu dirección de correo electrónico para completar el proceso de registro.</p>\n"
                + "    <p><a href=\"https://edu-meetpshyc1.odoo/usuario/verificar-email?email=email_del_destinatario\">Haz clic aquí para verificar tu correo electrónico</a></p>\n"
                + "</body>\n"
                + "</html>";

        String mensajeHtml2 = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "    body { font-family: Arial, sans-serif; }\n"
                + "    input, button { margin-top: 10px; }\n"
                + "</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <h1>¡Gracias por registrarte, [Nombre del Usuario]!</h1>\n"
                + "    <p>Estamos emocionados de tenerte a bordo. Para poder comenzar a prestar tus servicios, es necesario que acredites tu especialidad.</p>\n"
                + "    <p>Por favor, adjunta tus certificaciones y número de colegiado respondiendo a este correo con los documentos necesarios.</p>\n"
                + "    <form action=\"https://edu-meetpshyc1.odoo/usuario/acreditar-especialidad\" method=\"POST\" enctype=\"multipart/form-data\">\n"
                + "        <input type=\"hidden\" name=\"email\" value=\"email_del_usuario\">\n"
                + "        <input type=\"file\" name=\"certificaciones\" required>\n"
                + "        <input type=\"text\" name=\"numero_colegiado\" placeholder=\"Número de Colegiado\" required>\n"
                + "        <button type=\"submit\">Enviar Documentación</button>\n"
                + "    </form>\n"
                + "</body>\n"
                + "</html>";

        if (person.getTypeUser().equals(TypeUser.USUARIO_NORMAL)) {
            return mensajeHtml1;
        } else {
            return mensajeHtml2;
        }

    }

}
