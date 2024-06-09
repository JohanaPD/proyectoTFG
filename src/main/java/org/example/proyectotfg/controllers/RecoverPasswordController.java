package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorProfile;
import org.example.proyectotfg.mediators.ViewController;

public class RecoverPasswordController implements ViewController {

    private MediatorProfile mediator;
    @FXML
    private TextField mail;
    @FXML
    private PasswordField pass;

    @FXML
    private PasswordField pass2;

    /**
     * Handles the action to recover the password.
     *
     * @param event the action event.
     */
    @FXML
    void recoverPassword(ActionEvent event) {
        String stringEmail = mail.getText();
        String stringPass = pass.getText();
        String stringPass2 = pass2.getText();
        if (stringEmail.isEmpty() || stringPass.isEmpty() || stringPass2.isEmpty()) {
            ((MainController) mediator).showError("Error", "No has rellenado algún campo");
        } else if (!stringPass.equals(stringPass2)) {
            ((MainController) mediator).showError("Error", "Las contraseñas no coinciden");

        } else {
            mediator.recoverPassword(stringEmail, stringPass);
        }
    }

    /**
     * Sets the mediator for communication with other controllers.
     *
     * @param mediador the mediator.
     */
    @Override
    public void setMediator(Mediator mediador) {
        mediator = (MediatorProfile) mediador;
    }

    /**
     * Handles the action to return to the first screen.
     *
     * @param actionEvent the action event.
     */
    public void firtScreen(ActionEvent actionEvent) {
        mediator.volverIncio();
    }
}


