package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.example.proyectotfg.exceptions.ThereIsNoView;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorAcceso;
import org.example.proyectotfg.mediators.ViewController;

public class EntryController implements ViewController {
    private MediatorAcceso mediador;
    @FXML
    void introFirstView(ActionEvent event) throws ThereIsNoView {
       mediador.openLogin();
    }
    @Override
    public void setMediator(Mediator mediador) {
        this.mediador = (MediatorAcceso) mediador;
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }
}