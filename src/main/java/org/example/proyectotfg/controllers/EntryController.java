package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.example.proyectotfg.exceptions.ThereIsNoView;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorAccess;
import org.example.proyectotfg.mediators.ViewController;

/**
 * Manages entries as a ViewController.
 * <p>Authors: Johana Pardo, Daniel Ocaña</p>
 * <p>Version: Java 21</p>
 * <p>Since: 2024-06-08</p>
 */
public class EntryController implements ViewController {
    private MediatorAccess mediador;

    /**
     * Handles the action to open the first view.
     *
     * @param event the action event.
     * @throws ThereIsNoView if there is no view to load.
     */
    @FXML
    void introFirstView(ActionEvent event) throws ThereIsNoView {
        mediador.openLogin();
    }

    /**
     * Sets the mediator.
     *
     * @param mediador the mediator to set.
     */
    @Override
    public void setMediator(Mediator mediador) {
        this.mediador = (MediatorAccess) mediador;
    }


}