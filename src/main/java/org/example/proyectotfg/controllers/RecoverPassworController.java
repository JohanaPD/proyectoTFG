package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.tools.ant.Main;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorProfile;
import org.example.proyectotfg.mediators.ViewController;

public class RecoverPassworController implements ViewController {

    private MediatorProfile mediator;
    @FXML
    private TextField mail;
    @FXML
    private TextField pass;

    @FXML
    private TextField pass2;


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

    @Override
    public void setMediator(Mediator mediador) {

        mediator = (MediatorProfile) mediador;
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }

    public void firtScreen(ActionEvent actionEvent) {
        mediator.volverIncio();
    }
}
