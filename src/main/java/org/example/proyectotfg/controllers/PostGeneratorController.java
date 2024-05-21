package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.Post;
import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorPost;
import org.example.proyectotfg.mediators.ViewController;

public class PostGeneratorController implements ViewController {
    @FXML
    private TextArea description;

    @FXML
    private TextField descriptionPost;

    @FXML
    private Text nameTitular;

    @FXML
    private ImageView patImage;
    @FXML
    private TextField titlePost;
    MediatorPost mediator;
    Person person;

    @Override
    public void setMediator(Mediator mediador) {
        this.mediator = (MediatorPost) mediador;
    }

    @FXML
    void generarPost(ActionEvent event) throws IncorrectDataException, NullArgumentException {

        String title = titlePost.getText();
        String presentacion = descriptionPost.getText();
        String nameTit = nameTitular.getText();
        String descrip = description.getText();
        Post nuevo = new Post(person, title, descrip);
        mediator.makePost(nuevo);
    }

    public void volverHome(ActionEvent actionEvent) {
        mediator.returnHome();
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }
}
