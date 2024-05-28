package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.example.proyectotfg.exceptions.ThereIsNoView;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorAccess;
import org.example.proyectotfg.mediators.ViewController;

public class EntryController implements ViewController {
    private MediatorAccess mediador;

    @FXML
    void introFirstView(ActionEvent event) throws ThereIsNoView {
        mediador.openLogin();
    }

    @Override
    public void setMediator(Mediator mediador) {
        this.mediador = (MediatorAccess) mediador;
    }


}