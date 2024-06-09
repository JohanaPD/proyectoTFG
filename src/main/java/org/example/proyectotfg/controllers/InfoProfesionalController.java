package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorFirstScreen;
import org.example.proyectotfg.mediators.ViewController;

/**
 * Manages professional information as a ViewController.
 * <p>Authors: Johana Pardo, Daniel Ocaña</p>
 * <p>Version: Java 21</p>
 * <p>Since: 2024-06-08</p>
 */
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

    /**
     * Initializes the controller.
     */
    public void initialize() {
    }

    /**
     * Sets the mediator.
     *
     * @param mediador the mediator to set.
     */
    @Override
    public void setMediator(Mediator mediador) {
        this.mediator = (MediatorFirstScreen) mediador;
    }

    /**
     * Sets the elements for the professional user.
     *
     * @param professionalUser the professional user.
     * @param index the index.
     */
    public void setElementsPerson(ProfessionalUser professionalUser, int index) {
        String imagePath = String.format("/org/example/proyectotfg/imgUsuario/doctor%d.png", index);
        Image imageProfessional = new Image(getClass().getResourceAsStream(imagePath));
        image.setImage(imageProfessional);
        name.setText(professionalUser.getNames());
        specialty.setText(professionalUser.getSpecialty());
        description.setText(professionalUser.getDescription());
    }

    /**
     * Returns to the home view.
     *
     * @param actionEvent the action event.
     */
    public void volverHome(ActionEvent actionEvent) {
        mediator.fromFirstScreenToHome();
    }

}
