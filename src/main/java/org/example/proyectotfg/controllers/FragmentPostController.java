package org.example.proyectotfg.controllers;

import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorPost;
import org.example.proyectotfg.mediators.ViewController;

public class FragmentPostController implements ViewController {
    private MediatorPost mediator;
    @Override
    public void setMediator(Mediator mediador) {
            this.mediator=(MediatorPost)mediador;
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }
}