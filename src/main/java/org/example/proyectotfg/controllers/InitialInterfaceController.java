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

public class InitialInterfaceController implements ViewController, Initializable {
    @FXML
    private Text textSaludo;
    @FXML
    private ScrollPane contenedorListaPersonas;
    @FXML
    private AnchorPane contenedorListaServicios;
    @FXML
    private TextArea serchBuscar;

    private MediatorFirstScreen mediator;
    HashMap<String, String> servicios = new HashMap<>();
    List<ProfessionalUser> usuariosEspecificos = new ArrayList<>();
    private Person user;

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    @Override
    public void setMediator(Mediator mediador) {
        this.mediator = (MediatorFirstScreen) mediador;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servicios = getServicio();
        try {
            usuariosEspecificos = SqliteConnector.getProfesionales();
        } catch (SQLException | IncorrectDataException | NonexistingUser | OperationsDBException |
                 NoSuchAlgorithmException | InvalidKeySpecException | NullArgumentException e) {
            ((MainController) mediator).showError("Error", e.getMessage());
        }
    }

    public void setTextWelcome(String name) {
        textSaludo.setText("Hola, " + name);
    }

    public void loadServices() {
        Parent listaServicios = mediator.initializeServices(servicios);
        contenedorListaServicios.getChildren().add(listaServicios);
        try {
            Parent professionalUserBox = mediator.initializeProfessionals(usuariosEspecificos);
            contenedorListaPersonas.setContent(professionalUserBox);
        } catch (NonexistingUser e) {
            ((MainController) mediator).showError("Error", e.getMessage());
        }
    }

    public HashMap<String, String> getServicio() {
        HashMap<String, String> servicios = new HashMap<>();
        servicios.put("Foros", "/org/example/proyectotfg/img/factores.png");
        servicios.put("Publicar Post", "/org/example/proyectotfg/img/gestion.png");
        servicios.put("Ver Post", "/org/example/proyectotfg/img/consulta.png");
        servicios.put("Medico", "/org/example/proyectotfg/img/psicologo.png");
        return servicios;
    }

    public void openSearch(ActionEvent actionEvent) {
        String textoBusqueda = serchBuscar.getText();
        mediator.openSearch(textoBusqueda);
    }

    public void adminSetting(ActionEvent actionEvent) throws OperationsDBException {
        mediator.updatePersonalData(user);
    }

    public void openCalendar(ActionEvent actionEvent) {
        mediator.openCalendarView();
    }


}
