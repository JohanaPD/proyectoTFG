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
import org.example.proyectotfg.exceptions.DataAccessException;
import org.example.proyectotfg.exceptions.NonexistingUser;
import org.example.proyectotfg.exceptions.OperationsDBException;
import org.example.proyectotfg.exceptions.ThereIsNoView;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorProfile;
import org.example.proyectotfg.mediators.ViewController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
    @FXML
    private Hyperlink recoverLink;

    public void initialize() {
        // Creamos un Stream de elementos para el ComboBox
        Stream<String> opcionesStream = Stream.of("PSICOLOGO", "COACH", "USUARIO_NORMAL", "ENTRENADOR_PERSONAL", "MINDFULNESS");
        // Convertimos el Stream a ObservableList directamente
        ObservableList<String> opcionesList = opcionesStream.collect(Collectors.toCollection(FXCollections::observableArrayList));
        // Asignamos las opciones al ComboBox
        tipoUsuario.setItems(opcionesList);
        // Si quieres seleccionar un ítem por defecto (opcional)
        tipoUsuario.getSelectionModel().selectFirst();
    }

    @FXML
    void onHelloButtonClick(ActionEvent event) {
        String usuario = reciveUser.getText().toString();
        String pass = recivePasswor.getText().toString();
        String typeUser = tipoUsuario.getValue();
        TypeUser typeUserEnum = TypeUser.valueOf(typeUser);
        mediator.loginUser(usuario, pass, typeUserEnum);
    }

    @FXML
    void userRegister(ActionEvent event) throws IOException, ThereIsNoView {
        mediator.userRegister();
    }
    @FXML
    void recovLink(ActionEvent event) throws IOException, ThereIsNoView {
        mediator.recoverPassword();
    }
    @Override
    public void setMediator(Mediator mediador) {
        this.mediator = (MediatorProfile) mediador;
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {
    }
}
