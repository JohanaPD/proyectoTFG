package org.example.proyectotfg.functions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountVerificationService{

    private Map<String, String> verificationCodes; // Almacena el código de verificación por correo electrónico

    public AccountVerificationService() {
        this.verificationCodes = new HashMap<>();
    }

    // Genera un código de verificación único y lo asocia al correo electrónico del usuario
    /*public String generateVerificationCode(String emailAddress) {
        String verificationCode = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        verificationCodes.put(emailAddress, verificationCode);
        return verificationCode;
    }*/
    public String generateVerificationCode(String emailAddress) {
        String verificationCode = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        verificationCodes.put(emailAddress, verificationCode);
        return verificationCode;
    }

    // Envia un correo electrónico de verificación al usuario
    public void sendVerificationEmail(String emailAddress) {
        String verificationCode = generateVerificationCode(emailAddress);
        String emailMessage = "Por favor, utilice el siguiente código de verificación para completar su registro: " + verificationCode;
        // Lógica para enviar el correo electrónico
    }

    // Verifica si el código de verificación ingresado por el usuario es válido
    public boolean verifyCode(String emailAddress, String enteredCode) {
        String storedCode = verificationCodes.get(emailAddress);
        return storedCode != null && storedCode.equals(enteredCode);
    }

    // Elimina el código de verificación asociado al correo electrónico después de la verificación
    public void clearVerificationCode(String emailAddress) {
        verificationCodes.remove(emailAddress);
    }
}
