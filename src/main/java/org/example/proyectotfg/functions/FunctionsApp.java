package org.example.proyectotfg.functions;

import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.enumAndTypes.TypeUser;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
/**
 * This class contains utility functions used in the application.
 */
public class FunctionsApp {

    /**
     * Calculates the age based on the current date and the birth date.
     *
     * @param currentDate The current date.
     * @param birthDate   The birth date.
     * @return The calculated age, or 0 if either currentDate or birthDate is null.
     */
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

    /**
     * Generates a strong password hash using PBKDF2 algorithm.
     *
     * @param password The password to hash.
     * @return The hashed password in hexadecimal format.
     * @throws NoSuchAlgorithmException  If the algorithm used for the hash is not available.
     * @throws InvalidKeySpecException   If the key specification is invalid.
     */
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
    /**
     * Validates a password against a stored hash with salt.
     *
     * @param enteredPassword       The password entered by the user.
     * @param storedHashWithSalt   The stored hash with salt in hexadecimal format.
     * @return                      True if the entered password matches the stored hash, false otherwise.
     * @throws NoSuchAlgorithmException   If the algorithm used for the hash is not available.
     * @throws InvalidKeySpecException    If the key specification is invalid.
     * @throws IllegalArgumentException   If the format of the stored hash is not as expected.
     */
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
    /**
     * Converts a byte array to a hexadecimal string representation.
     *
     * @param array The byte array to be converted.
     * @return The hexadecimal string representation of the byte array.
     */
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

    /**
     * Converts a hexadecimal string to a byte array.
     *
     * @param hex The hexadecimal string to be converted.
     * @return The byte array representation of the hexadecimal string.
     */
    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    /**
     * Generates an HTML message based on the type of user.
     *
     * @param person The person object representing the user.
     * @return The HTML message.
     */
    public static String devolverStringMail(Person person) {
        String mensajeHtml1 = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n" + "<style>\n"
                + "    body { font-family: Arial, sans-serif; }\n"
                + "    input, button { margin-top: 10px; }\n"
                + "</style>\n"
                + "</head>\n" +
                "<body>\n" + "    " +
                "<h1>¡Gracias por registrarte "+person.getNames()+"!</h1>\n" + "    " +
                "<p> A partir de este momento, ya puedes contactar con los mejores profesionales.</p>\n"
               + "</body>\n"
                + "</html>";

        String mensajeHtml2 = "<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<style>\n" + "    body { font-family: Arial, sans-serif; }\n"
                + "    input, button { margin-top: 10px; }\n" + "</style>\n" + "</head>\n" + "<body>\n"
                + "    <h1>¡Gracias por registrarte, " +person.getNames()+"!</h1>\n"
                + "    <p>Gracias por registrarte , ya puedes prestar tus servicios a nuestra comunidad.</p>"
                +"</body>\n" + "</html>";

        if (person.getTypeUser().equals(TypeUser.USUARIO_NORMAL)) {
            return mensajeHtml1;
        } else {
            return mensajeHtml2;
        }

    }



    /**
     * Generates an HTML string for password recovery email.
     *
     * @param person The person for whom the password recovery email is being generated.
     * @return The HTML string for the password recovery email.
     */
    public static String returnPasswordRecoverString(Person person) {
        String htmlPasswordRecoverString =  "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "    body { font-family: Arial, sans-serif; }\n" +
                "    input, button { margin-top: 10px; }\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Cambio de Contraseña</h1>\n" +
                "    <p>Hola " + person.getNames() + ",</p>\n" +
                "    <p>Su contraseña se ha cambiado correctamente.</p>\n" +
                "</body>\n" +
                "</html>";
        return htmlPasswordRecoverString;
    }

    /**
     * Fills an array with dates based on the given parameters.
     *
     * @param maxValues The maximum number of values in the array.
     * @param day       The day of the month for the dates.
     * @param month     The month of the year for the dates.
     * @param year      The year for the dates.
     * @return An array filled with dates.
     * @throws ParseException If there is an error parsing the dates.
     */
    public static Date[] fillArray(int maxValues, int day, int month, int year) throws ParseException {
        Date[] fillArray = new Date[maxValues];

        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<Date> timesList = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month); // Change this according to the desired month
            calendar.set(Calendar.DAY_OF_MONTH, day); // Change this according to the desired day

            for (int hour = 10; hour <= 18; hour++) {
                if (hour >= 14 && hour < 16) {
                    continue; // Skip hours from 14:00 to 16:00
                }
                String timeString = String.format("%02d:00:00", hour);
                Date timeDate = timeFormat.parse(timeString);

                calendar.set(Calendar.HOUR_OF_DAY, timeDate.getHours());
                calendar.set(Calendar.MINUTE, timeDate.getMinutes());
                calendar.set(Calendar.SECOND, timeDate.getSeconds());

                timesList.add(calendar.getTime());
            }

            fillArray = timesList.toArray(new Date[0]);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fillArray;
    }

}
