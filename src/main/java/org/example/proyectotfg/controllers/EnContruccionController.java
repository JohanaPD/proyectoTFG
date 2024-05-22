package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorConstruction;
import org.example.proyectotfg.mediators.ViewController;

public class EnContruccionController implements ViewController {
    MediatorConstruction mediatorConstruction;

    @Override
    public void setMediator(Mediator mediador) {
        this.mediatorConstruction = (MediatorConstruction) mediador;
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }

    public void volverAInicio(ActionEvent actionEvent) {
        mediatorConstruction.backToHome();
    }
}
