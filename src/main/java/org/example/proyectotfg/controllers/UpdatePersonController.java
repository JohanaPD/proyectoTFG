package org.example.proyectotfg.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.proyectotfg.DAO.SqliteConnector;
import org.example.proyectotfg.entities.Direction;
import org.example.proyectotfg.entities.NormalUser;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.exceptions.OperationsDBException;
import org.example.proyectotfg.functions.VerificatorSetter;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorFirstScreen;
import org.example.proyectotfg.mediators.ViewController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class UpdatePersonController implements ViewController {
    @FXML
    private Label collegiateLabel;

    @FXML
    private TextField collegiateTextField;

    @FXML
    private ComboBox<TypeUser> comboTypeUser;

    @FXML
    private TextField confirmEmail;


    @FXML
    private Label descriptionLabel;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField email;

    @FXML
    private Label specialtyLabel;

    @FXML
    private TextField specialtyTextField;

    @FXML
    private TextField textLastNames;

    @FXML
    private TextField textStreet;

    @FXML
    private TextField textCity;

    @FXML
    private TextField PostalCode;

    @FXML
    private TextField textName;

    @FXML
    private PasswordField textPassword;

    @FXML
    private PasswordField textPassword2;

    private MediatorFirstScreen mediator;
    private Person person;

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = (MediatorFirstScreen) mediator;
    }


    public void initialize() {
        comboTypeUser.setItems(FXCollections.observableArrayList(TypeUser.values()));
        comboTypeUser.getSelectionModel().select(TypeUser.USUARIO_NORMAL);
        setConditionalVisibility(comboTypeUser.getValue());  // Set initial visibility based on default selection
        comboTypeUser.valueProperty().addListener((obs, oldVal, newVal) -> {
            setConditionalVisibility(newVal);
        });
    }

    private void setConditionalVisibility(TypeUser typeUser) {
        boolean visible = typeUser != TypeUser.USUARIO_NORMAL;
        collegiateTextField.setText("");
        collegiateLabel.setVisible(visible);
        collegiateTextField.setVisible(visible);
        specialtyTextField.setText("");
        specialtyLabel.setVisible(visible);
        specialtyTextField.setVisible(visible);
        descriptionTextArea.setText("");
        descriptionLabel.setVisible(visible);
        descriptionTextArea.setVisible(visible);
    }

    public void chargePerson(Person person) throws OperationsDBException {
        textName.setText(person.getNames());
        textLastNames.setText(person.getLastNames());
        email.setText(person.getEmail());
        confirmEmail.setText(person.getEmail());
        Direction dir = person.getDirection();
        textStreet.setText(dir.getStreet());
        textCity.setText(dir.getCity());
        PostalCode.setText(String.valueOf(dir.getPostalCode()));
        comboTypeUser.setValue(person.getTypeUser());
        if (!person.getTypeUser().equals(TypeUser.USUARIO_NORMAL)) {
            ProfessionalUser profesionalPerson = SqliteConnector.chargeProfesionalUserById(person.getIdPerson());
            specialtyTextField.setText(profesionalPerson.getSpecialty());
            collegiateTextField.setText(profesionalPerson.getCollegiate());
            descriptionTextArea.setText(profesionalPerson.getDescription());
        }
        this.person = person;
    }

    public void updateData(ActionEvent actionEvent) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException, OperationsDBException, SQLException {
        boolean correctDirection = true;
        String names = textName.getText();
        if (names.equalsIgnoreCase("")) {
            names = person.getNames();
        }
        String lastNames = textLastNames.getText();
        if (lastNames.equalsIgnoreCase("")) {
            lastNames = person.getLastNames();
        }
        String calle = textStreet.getText();
        if (calle.equalsIgnoreCase("")) {
            calle = person.getDirection().getStreet();
        }
        String city = textCity.getText();
        if (city.equalsIgnoreCase("")) {
            city = person.getDirection().getCity();
        }
        String postalCode = PostalCode.getText();
        if (city.equalsIgnoreCase("")) {
            postalCode = String.valueOf(person.getDirection().getPostalCode());
        }
        Direction newDirection = null;
        try {
            newDirection = new Direction(person.getDirection().getIdDireccion(), calle, city, Integer.parseInt(postalCode));
        } catch (NumberFormatException e) {
            correctDirection = false;
        }
        if (correctDirection) {
            String mail = email.getText();
            if (mail.equalsIgnoreCase("")) {
                mail = person.getEmail();
            }
            String confirMail = confirmEmail.getText();
            String pass1 = textPassword.getText().trim();
            String pass2 = textPassword2.getText().trim();

            String errores = verificatorData(names, lastNames, mail, confirMail, pass1, pass2, newDirection);
            if (!errores.isEmpty()) {
                ((MainController) mediator).showError("Errores en el registro", errores);
            }
        }
    }


    private String verificatorData(String names, String lastNames, String mail, String confirMail, String pass1, String pass2, Direction newDirection) throws OperationsDBException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException, SQLException {
        StringBuilder errors = new StringBuilder();
        if (!VerificatorSetter.stringVerificator(names, 100)) {
            errors.append("El nombre no puede contener números ni caracteres especiales.\n");
        }
        if (!VerificatorSetter.stringVerificator(lastNames, 100)) {
            errors.append("El apellido no puede contener números ni caracteres especiales.\n");
        }
        if (!mail.isEmpty() && !confirMail.isEmpty()) {
            if (VerificatorSetter.validarCorreoElectronico(mail) && VerificatorSetter.validarCorreoElectronico(confirMail)) {
                if (mail.equalsIgnoreCase(confirMail)) {
                    TypeUser tipeUs = comboTypeUser.getValue();
                    String tipeUser = tipeUs.toString();
                    TypeUser typeUser = TypeUser.valueOf(tipeUser);
                    if (!pass1.isEmpty() || !pass2.isEmpty()) {
                        if (!pass1.equals(pass2)) {
                            errors.append("Las contraseñas no coinciden.\n");
                        } else {
                            // Actualizar con contraseña///

                            if (typeUser == TypeUser.USUARIO_NORMAL) {
                                NormalUser newUser = new NormalUser(person.getIdPerson(), names, lastNames, pass1, mail, newDirection, TypeUser.USUARIO_NORMAL);
                                mediator.updateAllDataPerson(newUser);
                            } else {
                                // ProfessionalUser
                                checkProfessionalUser result = getCheckProfessionalUser();
                                if (!result.college.equalsIgnoreCase("") || !result.specialty.equalsIgnoreCase("") || !result.description.equalsIgnoreCase("")) {
                                    ProfessionalUser prof = new ProfessionalUser(person.getIdPerson(), names, lastNames, pass1, mail, newDirection, typeUser, result.college, result.specialty, result.description);
                                    mediator.updateAllDataPerson(prof);
                                } else {
                                    errors.append("Datos profesionales incorrectos.\n");
                                }
                            }
                        }
                    } else {
                        // Actualizar sin contraseña
                        if (tipeUser.equalsIgnoreCase(String.valueOf(TypeUser.USUARIO_NORMAL))) {
                            NormalUser newUser = new NormalUser(person.getIdPerson(), names, lastNames, mail, newDirection, typeUser);
                            mediator.updateDataPerson(newUser);
                        } else {
                            // ProfessionalUser
                            checkProfessionalUser result = getCheckProfessionalUser();

                            if (!result.college().equalsIgnoreCase("") || !result.specialty().equalsIgnoreCase("") || !result.description().equalsIgnoreCase("")) {
                                ProfessionalUser prof = new ProfessionalUser(person.getIdPerson(), names, lastNames, mail, newDirection, typeUser, result.college(), result.specialty(), result.description());
                                mediator.updateDataPerson(prof);
                            } else {
                                errors.append("Datos profesionales incorrectos.\n");
                            }
                        }
                    }
                } else {
                    errors.append("Los correos electrónicos no coinciden.\n");
                }
            } else {
                errors.append("No has introducido o no tiene el formato correcto el correo.\n");
            }
        }

        return errors.toString();
    }

    private checkProfessionalUser getCheckProfessionalUser() throws OperationsDBException {
        ProfessionalUser replace = SqliteConnector.chargeProfesionalUserById(person.getIdPerson());

        String college = collegiateTextField.getText();
        if (college.equalsIgnoreCase("")) {
            college = replace.getCollegiate();
        }
        String specialty = specialtyTextField.getText();
        if (specialty.equalsIgnoreCase("")) {
            specialty = replace.getCollegiate();
        }
        String description = descriptionTextArea.getText();
        if (description.equalsIgnoreCase("")) {
            description = replace.getCollegiate();
        }
        checkProfessionalUser result = new checkProfessionalUser(college, specialty, description);
        return result;
    }

    private record checkProfessionalUser(String college, String specialty, String description) {
    }


    public void returnHome(ActionEvent actionEvent) {
        mediator.regresar();
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }
}
