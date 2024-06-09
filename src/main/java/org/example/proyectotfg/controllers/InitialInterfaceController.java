package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.example.proyectotfg.DAO.SqliteConnector;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.*;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorFirstScreen;
import org.example.proyectotfg.mediators.ViewController;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controls the initial interface and implements Initializable for JavaFX initialization.
 * <p>Authors: Johana Pardo, Daniel Ocaña</p>
 * <p>Version: Java 21</p>
 * <p>Since: 2024-06-08</p>
 */
public class InitialInterfaceController implements ViewController, Initializable {
    @FXML
    private Text textSaludo;
    @FXML
    private ScrollPane containerProfessionalsList;
    @FXML
    private AnchorPane containerServicesList;
    @FXML
    private TextArea serchBuscar;

    private MediatorFirstScreen mediator;
    HashMap<String, String> servicios = new HashMap<>();
    List<ProfessionalUser> usuariosEspecificos = new ArrayList<>();
    private Person user;

    /**
     * Gets the user.
     *
     * @return the user.
     */
    public Person getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user the user to set.
     */
    public void setUser(Person user) {
        this.user = user;
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
     * Initializes the controller.
     *
     * @param url the URL.
     * @param resourceBundle the resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servicios = getServicio();
    }

    /**
     * Retrieves the professionals.
     */
    public void getProfessionals() {
        try {
            usuariosEspecificos = mediator.getProfessionals();
        } catch (SQLException | IncorrectDataException | NonexistingUser | OperationsDBException |
                 NoSuchAlgorithmException | InvalidKeySpecException | NullArgumentException e) {
            ((MainController) mediator).showError("Error", e.getMessage());
        }
    }

    /**
     * Sets the welcome text.
     *
     * @param name the name to set.
     */
    public void setTextWelcome(String name) {
        textSaludo.setText("Hola, " + name);
    }

    /**
     * Loads the services.
     */
    public void loadServices() {
        Parent listaServicios = mediator.initializeServices(servicios);
        containerServicesList.getChildren().add(listaServicios);
        try {
            Parent professionalUserBox = mediator.initializeProfessionals(usuariosEspecificos);
            containerProfessionalsList.setContent(professionalUserBox);
        } catch (NonexistingUser e) {
            ((MainController) mediator).showError("Error", e.getMessage());
        }
    }

    /**
     * Retrieves the service data.
     *
     * @return the service data.
     */
    public HashMap<String, String> getServicio() {
        HashMap<String, String> servicios = new HashMap<>();
        servicios.put("Foros", "/org/example/proyectotfg/img/factores.png");
        servicios.put("Publicar Post", "/org/example/proyectotfg/img/gestion.png");
        servicios.put("Ver Post", "/org/example/proyectotfg/img/consulta.png");
        servicios.put("Medico", "/org/example/proyectotfg/img/psicologo.png");
        return servicios;
    }

    /**
     * Opens the search view.
     *
     * @param actionEvent the action event.
     */
    public void openSearch(ActionEvent actionEvent) {
        String textoBusqueda = serchBuscar.getText();
        mediator.openSearch(textoBusqueda);
    }

    /**
     * Handles the action to update personal data.
     *
     * @param actionEvent the action event.
     * @throws OperationsDBException if there is an error updating personal data.
     */
    public void adminSetting(ActionEvent actionEvent) throws OperationsDBException {
        mediator.updatePersonalData(user);
    }

    /**
     * Opens the appointment calendar view.
     *
     * @param actionEvent the action event.
     */
    public void openCalendar(ActionEvent actionEvent) {
        mediator.openAppointmentView();
    }

    /**
     * Logs out the user.
     *
     * @param actionEvent the action event.
     */
    public void logOut(ActionEvent actionEvent) {
        mediator.logOut();
    }

}
