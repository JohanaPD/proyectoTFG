package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.ViewController;

public class UpdatePersonController implements ViewController {
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

    public void updateData(ActionEvent actionEvent) {
    }

    public void volverHome(ActionEvent actionEvent) {

    }
}
