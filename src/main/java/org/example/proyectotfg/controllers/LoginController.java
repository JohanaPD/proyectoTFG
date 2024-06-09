package org.example.proyectotfg.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.exceptions.ThereIsNoView;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorProfile;
import org.example.proyectotfg.mediators.ViewController;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoginController implements ViewController {

    private MediatorProfile mediator;
    @FXML
    TextField reciveUser;
    @FXML
    PasswordField recivePasswor;
    @FXML
    private ComboBox<String> tipoUsuario;

    /**
     * Initializes the controller.
     */
    public void initialize() {
        Stream<String> opcionesStream = Stream.of("PSICOLOGO", "COACH", "USUARIO_NORMAL", "ENTRENADOR_PERSONAL", "MINDFULNESS");
        ObservableList<String> opcionesList = opcionesStream.collect(Collectors.toCollection(FXCollections::observableArrayList));
        tipoUsuario.setItems(opcionesList);
        tipoUsuario.getSelectionModel().selectFirst();
    }

    /**
     * Handles the action when the hello button is clicked.
     *
     * @param event the action event.
     */
    @FXML
    void onHelloButtonClick(ActionEvent event) {
        String usuario = reciveUser.getText().toString();
        String pass = recivePasswor.getText().toString();
        String typeUser = tipoUsuario.getValue();
        TypeUser typeUserEnum = TypeUser.valueOf(typeUser);
        mediator.loginUser(usuario, pass, typeUserEnum);
    }

    /**
     * Handles the action to register a user.
     *
     * @param event the action event.
     * @throws IOException      if an I/O error occurs.
     * @throws ThereIsNoView   if there is no view.
     */
    @FXML
    void userRegister(ActionEvent event) throws IOException, ThereIsNoView {
        mediator.userRegister();
    }

    /**
     * Handles the action to recover password.
     *
     * @param event the action event.
     * @throws IOException      if an I/O error occurs.
     * @throws ThereIsNoView   if there is no view.
     */
    @FXML
    void recovLink(ActionEvent event) throws IOException, ThereIsNoView {
        mediator.loadRecoverPassword();
    }

    /**
     * Sets the mediator.
     *
     * @param mediador the mediator to set.
     */
    @Override
    public void setMediator(Mediator mediador) {
        this.mediator = (MediatorProfile) mediador;
    }
}
