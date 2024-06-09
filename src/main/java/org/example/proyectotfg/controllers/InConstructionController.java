package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorConstruction;
import org.example.proyectotfg.mediators.ViewController;

/**
 * Manages construction processes as a ViewController.
 * <p>Authors: Johana Pardo, Daniel Ocaña</p>
 * <p>Version: Java 21</p>
 * <p>Since: 2024-06-08</p>
 */
public class InConstructionController implements ViewController {
    MediatorConstruction mediatorConstruction;

/**
 * Sets the mediator.
 *
 * @param mediador the mediator to set.
 */
    @Override
    public void setMediator(Mediator mediador) {
        this.mediatorConstruction = (MediatorConstruction) mediador;
    }

    /**
     * Returns to the home view.
     *
     * @param actionEvent the action event.
     */
    public void volverAInicio(ActionEvent actionEvent) {
        mediatorConstruction.backToHome();
    }
}
