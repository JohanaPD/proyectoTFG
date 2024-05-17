package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.example.proyectotfg.DAO.SqliteConnector;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NonexistingUser;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.exceptions.OperationsDBException;
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
    private HBox banerPerfil;
    @FXML
    private Text textSaludo;

    @FXML
    private Button buttonBusqueda;

    @FXML
    private ScrollPane contenedorListaPersonas;

    @FXML
    private AnchorPane contenedorListaServicios;

    @FXML
    private TextArea serchBuscar;

    MediatorFirstScreen mediator;
    HashMap<String, String> servicios = new HashMap<>();
    List<ProfessionalUser> usuariosEspecificos = new ArrayList<>();
    @Override
    public void setMediator(Mediator mediador) {
        this.mediator=(MediatorFirstScreen) mediador;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servicios = getServicio();
        try {
            usuariosEspecificos= SqliteConnector.getProfesionales();
        } catch (SQLException|IncorrectDataException| NonexistingUser|OperationsDBException|
                NoSuchAlgorithmException | InvalidKeySpecException | NullArgumentException e) {
          //todo: meter cambios
        }

    }
    public void setTextWelcome(String name) {
        textSaludo.setText("Hola, " + name);
    }

    public void loadServices() {
        Parent listaServicios= mediator.initializeServicios(servicios);
        contenedorListaServicios.getChildren().add(listaServicios);
        try {
            Node professionalUserBox = mediator.initializeProfessionals(usuariosEspecificos);
            contenedorListaPersonas.setContent(professionalUserBox);
        } catch (NonexistingUser e) {

            //todo:  Manejar la excepción adecuadamente
        }
    }


    public HashMap<String, String> getServicio() {
        HashMap<String, String> servicios = new HashMap<>();
        servicios.put("Medico", "/org/example/proyectotfg/img/psicologo.png");
        servicios.put("Foros", "/org/example/proyectotfg/img/factores.png");
        servicios.put("Comunidad", "/org/example/proyectotfg/img/gestion.png");
        servicios.put("Consultas", "/org/example/proyectotfg/img/consulta.png");

        return servicios;
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }

    public void openSearch(ActionEvent actionEvent) {
        String textoBusqueda = serchBuscar.getText();
        mediator.openSearch(textoBusqueda);
    }
    public void VolverAtras(ActionEvent actionEvent) {
    }

    public void openCalendar(ActionEvent actionEvent) {
    mediator.openCalendarView();
    }

}
