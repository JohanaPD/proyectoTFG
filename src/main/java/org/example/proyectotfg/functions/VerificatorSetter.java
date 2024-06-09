package org.example.proyectotfg.functions;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Utility class for verifying and setting data.
 */
public class VerificatorSetter {
    /**
     * Verifies if a string consists of letters and spaces only.
     *
     * @param string The string to verify.
     * @param length The maximum length allowed for the string.
     * @return True if the string consists of letters and spaces only and has a length between 1 and the specified maximum length, otherwise false.
     */
    public static boolean stringVerificator(String string, int length) {
        if (string != null) {
            if (string.length() >= 1 || string.length() < length) {
                Pattern patron = Pattern.compile("^[a-zA-ZñÑ\\s]+$");
                Matcher matcher = patron.matcher(string);
                if (matcher.matches()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
    /**
     * Verifies if a string consists of letters, numbers, and certain special characters only.
     *
     * @param string The string to verify.
     * @param length The maximum length allowed for the string.
     * @return True if the string consists of letters, numbers, and certain special characters only and has a length between 1 and the specified maximum length, otherwise false.
     */
    public static boolean stringVerificatorletterAndNumbers(String string, int length) {
        if (string != null) {
            if (string.length() >= 1 && string.length() <= length) {
                Pattern patron = Pattern.compile("^[a-zA-Z0-9\\s,áéíóúÁÉÍÓÚñÑ.\\-:'\\r\\n]+$");

                Matcher matcher = patron.matcher(string);
                if (matcher.matches()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Verifies if a number is positive and has a length less than or equal to the specified maximum length.
     *
     * @param number The number to verify.
     * @param length The maximum length allowed for the number.
     * @return True if the number is positive and has a length less than or equal to the specified maximum length, otherwise false.
     */
    public static boolean numberVerificator(int number, int length) {
        if (number > 0) {
            if (String.valueOf(number).length() <= length) {
                return true;
            }
        }
        return false;
    }
    /**
     * Verifies if an email address has a valid format.
     *
     * @param mail The email address to verify.
     * @return True if the email address has a valid format, otherwise false.
     */
    public static boolean validateEmailAddress(String mail) {
        String pattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(pattern, mail);
    }
    /**
     * Verifies if a given age is greater than 18.
     *
     * @param edad The age to verify.
     * @return True if the age is greater than 18, otherwise false.
     */
    public static boolean esMayorDeEdad(int edad) {
        return edad > 18;
    }

    /**
     * Verifies if a string can be parsed into a number.
     *
     * @param cadena The string to verify.
     * @return True if the string can be parsed into a number, otherwise false.
     */
    public static boolean esNumero(String cadena) {
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * Verifies if a given date string has a valid format (dd/MM/yyyy).
     *
     * @param fecha The date string to verify.
     * @return True if the date string has a valid format, otherwise false.
     */
    public static boolean formatoFechaValido(String fecha) {
        String pattern = "\\d{2}/\\d{2}/\\d{4}";
        return Pattern.matches(pattern, fecha);
    }
    /**
     * Verifies if a given birthday date is valid.
     *
     * @param birthday The birthday date to verify.
     * @return True if the birthday date is valid, otherwise false.
     */
    public static boolean isValidBirthday(Date birthday) {
        Calendar now = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(birthday);
        Calendar earliestDate = Calendar.getInstance();
        earliestDate.set(1900, 0, 1);

        if (birthDate.after(now) || birthDate.before(earliestDate)) {
            return false;
        }
        return true;
    }

}
