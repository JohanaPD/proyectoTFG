package org.example.proyectotfg.functions;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificatorSetter {


    public static boolean stringVerificator(String cadena, int lengt) {
        if (cadena != null) {
            if (cadena.length() >= 1 || cadena.length() < lengt) {
                Pattern patron = Pattern.compile("^[a-zA-ZñÑ\\s]+$");
                Matcher matcher = patron.matcher(cadena);
                if (matcher.matches()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean stringVerificatorletterAndNumbers(String cadena, int length) {
        if (cadena != null) {
            if (cadena.length() >= 1 && cadena.length() <= length) {
                // La expresión regular incluye letras, números, espacios, comas y caracteres con tilde
               // Pattern patron = Pattern.compile("^[a-zA-Z0-9\\s,áéíóúÁÉÍÓÚñÑ.\\-']+$");
                Pattern patron = Pattern.compile("^[a-zA-Z0-9\\s,áéíóúÁÉÍÓÚñÑ.\\-'\\r\\n]+$");

                Matcher matcher = patron.matcher(cadena);
                if (matcher.matches()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    public static boolean numberVerificator(int number, int leng) {
        if (number > 0) {
            if (String.valueOf(number).length() <= leng) {
                return true;
            }
        }
        return false;
    }

    public static boolean validarCorreoElectronico(String correo) {
        // Patrón de expresión regular para validar correo electrónico
        String pattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(pattern, correo);
    }

    public static boolean esMayorDeEdad(int edad) {
        return edad > 18;
    }

    public static boolean esNumero(String cadena) {
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean formatoFechaValido(String fecha) {
        String pattern = "\\d{2}/\\d{2}/\\d{4}";
        return Pattern.matches(pattern, fecha);
    }

    public static boolean isValidBirthday(Date birthday) {
        // instancia actual
        Calendar now = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(birthday);

        // Año mínimo razonable para una fecha de nacimiento, por ejemplo, 1900
        Calendar earliestDate = Calendar.getInstance();
        earliestDate.set(1900, 0, 1);

        if (birthDate.after(now) || birthDate.before(earliestDate)) {
            return false;
        }

        return true;
    }

}
