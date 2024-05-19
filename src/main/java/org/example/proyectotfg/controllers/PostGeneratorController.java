package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.example.proyectotfg.mediators.Mediator;
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
    private TextFlow textDescripton;

    @FXML
    private TextField titlePost;
    @Override
    public void setMediator(Mediator mediador) {

    }
    @FXML
    void generarPost(ActionEvent event) {

    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }
}
