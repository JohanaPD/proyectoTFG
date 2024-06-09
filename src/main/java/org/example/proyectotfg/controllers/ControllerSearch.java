package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.ThereIsNoView;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorFirstScreen;
import org.example.proyectotfg.mediators.ViewController;

import java.util.List;

public class ControllerSearch implements ViewController {
      @FXML
    private VBox listaResultados;
    MediatorFirstScreen mediator;
    private Person person;

    /**
     * Initializes the controller.
     */
    public void initialize() {
    }

    /**
     * Sets the Person object.
     *
     * @param person the person to set.
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Sets the mediator.
     *
     * @param mediador the mediator to set.
     */
    @Override
    public void setMediator(Mediator mediador) {
        mediator = (MediatorFirstScreen) mediador;
    }

    /**
     * Loads the search results.
     *
     * @param professionalUsers the list of professional users.
     * @throws ThereIsNoView if there is no view to load.
     */
    public void loadSearchs(List<ProfessionalUser> professionalUsers) throws ThereIsNoView {
        Parent listResults = mediator.loadSearchs(professionalUsers);
        listaResultados.getChildren().add(listResults);
    }

    /**
     * Returns to the home view from the first screen.
     *
     * @param actionEvent the action event.
     */
    public void volverIncio(ActionEvent actionEvent) {
        mediator.fromFirstScreenToHome();
    }
}
