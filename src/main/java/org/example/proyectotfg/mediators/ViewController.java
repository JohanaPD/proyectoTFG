package org.example.proyectotfg.mediators;

import org.example.proyectotfg.controllers.MainController;

public interface ViewController {
        void setMediator(Mediator mediador);
        MainController getMainController() ;
        void setMainController(MainController mainController);
}

