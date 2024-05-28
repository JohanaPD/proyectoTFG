package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorFirstScreen;
import org.example.proyectotfg.mediators.MediatorProfile;
import org.example.proyectotfg.mediators.ViewController;

public class InfoProfesionalController implements ViewController {
    @FXML
    private ImageView image;
    @FXML
    private Text name;
    @FXML
    private Text specialty;
    @FXML
    private Text description;
    private MediatorFirstScreen mediator;

    public void initialize() {
    }

    @Override
    public void setMediator(Mediator mediador) {
        this.mediator = (MediatorFirstScreen) mediador;
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }

    public void setElementsPerson(ProfessionalUser professionalUser, int index) {
        String imagePath = String.format("/org/example/proyectotfg/imgUsuario/doctor%d.png", index);
        Image imageProfessional = new Image(getClass().getResourceAsStream(imagePath));
        image.setImage(imageProfessional);
        name.setText(professionalUser.getNames());
        specialty.setText(professionalUser.getSpecialty());
        description.setText(professionalUser.getDescription());
    }

    public void volverHome(ActionEvent actionEvent) {
        mediator.regresar();
    }
}
