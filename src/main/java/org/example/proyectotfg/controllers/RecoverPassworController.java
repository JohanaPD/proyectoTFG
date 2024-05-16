package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorProfile;
import org.example.proyectotfg.mediators.ViewController;

public class RecoverPassworController  implements ViewController {

    private MediatorProfile mediator;

    @FXML
    private TextField recivepass;

    @FXML
    private TextField recivepass2;



    @FXML
    void recoveryPassw(ActionEvent event) {

    }

    @Override
    public void setMediator(Mediator mediador) {

        mediator=(MediatorProfile)mediador;
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }
}
