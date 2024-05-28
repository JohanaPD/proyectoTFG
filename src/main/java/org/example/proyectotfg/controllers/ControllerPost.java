package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
    private ScrollPane mainContainer;
    private MediatorPost mediator;
    Person person;
    List<Post> posts = new ArrayList<>();

    @Override
    public void setMediator(Mediator mediador) {
        this.mediator = (MediatorPost) mediador;
    }

    public void setPerson(Person person) {
        this.person = person;
    }



    public void loadPosts() {
        mainContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainContainer.setContent(mediator.viewPost(posts));
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

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
