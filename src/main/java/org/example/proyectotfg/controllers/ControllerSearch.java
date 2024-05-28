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
    private Label detalleBusqueda;
    @FXML
    private VBox listaResultados;
    MediatorFirstScreen mediator;
    private Person person;

    public void initialize() {
        System.out.println("Inicializando el Controller search");
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public void setMediator(Mediator mediador) {
        mediator = (MediatorFirstScreen) mediador;
    }

    public void setDetalleBusqueda(Label detalleBusqueda) {
        detalleBusqueda.setText(person.getNames());
    }


    public void loadSearchs(List<ProfessionalUser> professionalUsers) throws ThereIsNoView {
        Parent listResults = mediator.loadSearchs(professionalUsers);
        listaResultados.getChildren().add(listResults);
    }

    public void setStringSearch(String search) {
        detalleBusqueda.setText("Resultados de busqueda [" + search + "]");
    }

    public void volverIncio(ActionEvent actionEvent) {
        mediator.fromFirstScreenToHome();
    }
}
