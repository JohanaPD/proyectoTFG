package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.ThereIsNoView;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorFirstScreen;
import org.example.proyectotfg.mediators.ViewController;

import javax.swing.text.View;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ControllerSearch implements ViewController {
    MediatorFirstScreen mediator;
    @FXML
    private Label detalleBusqueda;
    @FXML
    private AnchorPane listaResultados;
    private Person person;

    public void initialize() {

    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public void setMediator(Mediator mediador) {
        mediator = (MediatorFirstScreen) mediador;
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }

    public void loadSearchs(List<ProfessionalUser> professionalUsers) throws ThereIsNoView {
        try {
            for (ProfessionalUser us : professionalUsers) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proyectotfg/fragment-infoSearch_view.fxml"));
                Node fragment = fxmlLoader.load();
                FragmentInfoSerchController controller = fxmlLoader.getController();
                controller.setProfessionalUser(us);
                controller.setPerson(person);
                controller.setData(String.valueOf(us.getNames()), String.valueOf(us.getSpecialty()), "/org/example/proyectotfg/imgUsuario/doctor3.png", 5);
                fragment.resize(340, 180);
                listaResultados.getChildren().add(fragment);
            }

        } catch (IOException e) {
            throw new ThereIsNoView("Error a la hora de cargar el fragmento: " + e.getMessage());
        }
    }

    public void setStringSearch(String search) {
        detalleBusqueda.setText("Resultados de busqueda [" + search + "]");
    }

    public void volverIncio(ActionEvent actionEvent) {
        mediator.regresar();
    }
}
