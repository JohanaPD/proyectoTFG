package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.proyectotfg.DAO.SqliteConnector;
import org.example.proyectotfg.entities.Direction;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.exceptions.OperationsDBException;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorProfile;
import org.example.proyectotfg.mediators.ViewController;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdatePersonController implements ViewController, Initializable {
    @FXML
    private Label colegiadoLabel;

    @FXML
    private TextField colegiadoTextField;

    @FXML
    private ComboBox<?> comboTypeUser;

    @FXML
    private TextField confirmarMail;

    @FXML
    private DatePicker dateNacimiento;

    @FXML
    private Label descripcionLabel;

    @FXML
    private TextArea descripcionTextArea;

    @FXML
    private TextField email;

    @FXML
    private Label especialidadLabel;

    @FXML
    private TextField especialidadTextField;

    @FXML
    private TextField textApellidos;

    @FXML
    private TextField textCalle;

    @FXML
    private TextField textCity;

    @FXML
    private TextField textCodPostal;

    @FXML
    private TextField textNombre;

    @FXML
    private PasswordField textPassword;

    @FXML
    private PasswordField textPassword2;
    private MediatorProfile mediator;
    Person person;

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = (MediatorProfile)mediator;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

   public void chargePerson() throws OperationsDBException {
        textNombre.setText(person.getNames());
        textApellidos.setText(person.getLastNames());
        email.setText(person.getEmail());
        confirmarMail.setText(person.getEmail());
        Direction dir= person.getDirection();
        textCalle.setText(dir.getStreet());
        textCity.setText(dir.getCity());
        textCodPostal.setText(String.valueOf(dir.getPostalCode()));
        if(!person.getTypeUser().equals(TypeUser.USUARIO_NORMAL)){
            ProfessionalUser profesionalPerson=SqliteConnector.chargeProfesionalUserById(person.getIdPerson());
            especialidadTextField.setText(profesionalPerson.getSpecialty());
            colegiadoLabel.setText(profesionalPerson.getCollegiate());
            descripcionTextArea.setText(profesionalPerson.getDescription());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            chargePerson();
        } catch (OperationsDBException e) {
            //todo: hacer exception
        }
    }



    public void updateData(ActionEvent actionEvent) {
    }

    public void volverHome(ActionEvent actionEvent) {

    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }
}
