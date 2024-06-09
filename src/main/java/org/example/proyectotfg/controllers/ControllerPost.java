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
    private AnchorPane mainContainer;
    private MediatorPost mediator;
    Person person;
    List<Post> posts = new ArrayList<>();
    /**
     * Sets the mediator.
     *
     * @param mediador the mediator to set.
     */
    @Override
    public void setMediator(Mediator mediador) {
        this.mediator = (MediatorPost) mediador;
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
     * Loads the posts and adds them to the main container.
     */
    public void loadPosts() {
        mainContainer.getChildren().add(mediator.viewPost(posts));
    }

    /**
     * Returns to the home view.
     *
     * @param actionEvent the action event.
     */
    public void volverIncio(ActionEvent actionEvent) {
        mediator.returnHome();
    }

    /**
     * Sets the list of posts.
     *
     * @param posts the list of posts to set.
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
