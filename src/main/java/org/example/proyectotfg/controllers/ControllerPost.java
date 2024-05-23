package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import org.example.proyectotfg.DAO.SqliteConnector;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.Post;
import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.exceptions.OperationsDBException;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorPost;
import org.example.proyectotfg.mediators.ViewController;

import java.util.ArrayList;
import java.util.List;

public class ControllerPost implements ViewController {

    @FXML
    private VBox mainContainer;

    private MediatorPost mediator;
    Person person;
    List<Post> post= new ArrayList<>();
    @Override
    public void setMediator(Mediator mediador) {
        this.mediator=(MediatorPost)mediador;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void initialize() throws IncorrectDataException, NullArgumentException, OperationsDBException {
            post= SqliteConnector.serchPostByPerson(person);
    }

    public void loadPost(List<Post> posts) {
        Parent listaServicios = mediator.viewPost(posts);
        mainContainer.getChildren().add(listaServicios);
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }

    public void volverIncio(ActionEvent actionEvent) {
        mediator.returnHome();
    }
}
