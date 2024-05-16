package org.example.proyectotfg.controllers;

import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.ViewController;

public class PruebaController implements ViewController {
    private Mediator mediator;

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }

}
