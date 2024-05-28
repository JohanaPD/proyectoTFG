package org.example.proyectotfg.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.proyectotfg.entities.Direction;
import org.example.proyectotfg.entities.NormalUser;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.*;
import org.example.proyectotfg.functions.FunctionsApp;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.functions.VerificatorSetter;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorProfile;
import org.example.proyectotfg.mediators.ViewController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class RecordUserController implements ViewController {

    private MediatorProfile mediator;

    private MainController mainController;

    @FXML
    private TextField confirmarMail;

    @FXML
    private DatePicker dateNacimiento;

    @FXML
    private TextField email;

    @FXML
    private TextField textApellidos;

    @FXML
    private TextField textCalle;

    @FXML
    private TextField textCity;

    @FXML
    private TextField textCodPostal;

    @FXML
    private TextArea descripcionTextArea;

    @FXML
    private TextField colegiadoTextField;

    @FXML
    private TextField especialidadTextField;

    @FXML
    private TextField textNombre;

    @FXML
    private PasswordField textPassword;

    @FXML
    private PasswordField textPassword2;

    @FXML
    private ComboBox<TypeUser> comboTypeUser;
    @FXML
    private Label colegiadoLabel;

    @FXML
    private Label especialidadLabel;

    @FXML
    private Label descripcionLabel;


    public void initialize() {
        comboTypeUser.setItems(FXCollections.observableArrayList(TypeUser.values()));
        comboTypeUser.getSelectionModel().select(TypeUser.USUARIO_NORMAL);
        setConditionalVisibility(comboTypeUser.getValue());
        comboTypeUser.valueProperty().addListener((obs, oldVal, newVal) -> {
            setConditionalVisibility(newVal);
        });
    }

    private void setConditionalVisibility(TypeUser typeUser) {
        boolean visible = typeUser != TypeUser.USUARIO_NORMAL;
        colegiadoTextField.setText("");
        colegiadoLabel.setVisible(visible);
        colegiadoTextField.setVisible(visible);
        especialidadTextField.setText("");
        especialidadLabel.setVisible(visible);
        especialidadTextField.setVisible(visible);
        descripcionTextArea.setText("");
        descripcionLabel.setVisible(visible);
        descripcionTextArea.setVisible(visible);
    }

    @FXML
    public void makeRegister(ActionEvent event) {
        try {
            boolean correctDirection = true;
            String names = textNombre.getText();
            String lastNames = textApellidos.getText();
            String calle = textCalle.getText();
            String city = textCity.getText();
            String codPostal = textCodPostal.getText();
            Direction nueva = null;
            try {
                nueva = new Direction(calle, city, Integer.parseInt(codPostal));
            } catch (NumberFormatException e) {
                correctDirection = false;
            }
            if (correctDirection) {
                String mail = email.getText();
                String confirMail = confirmarMail.getText();
                String pass1 = textPassword.getText().trim();
                String pass2 = textPassword2.getText().trim();
                LocalDate birthDate = dateNacimiento.getValue();
                Date birthd = null;
                if (birthDate != null) {
                    ZoneId zoneId = ZoneId.systemDefault();
                    birthd = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    Date registrationDate = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
                    String errores = verificatorData(names, lastNames, mail, confirMail, registrationDate, birthd, pass1, pass2, nueva);
                    if (!errores.isEmpty()) {
                        ((MainController) mediator).showError("Errores en el registro", errores);
                    }
                } else {
                    ((MainController) mediator).showError("Error", "No has definido una fecha en el calendario");
                }
            } else {
                ((MainController) mediator).showError("Error", "El código postal solo puede contener números");
            }
        } catch (IncorrectDataException | NoSuchAlgorithmException | InvalidKeySpecException | NullArgumentException |
                 IncompleteDataInRecord | IOException | DataAccessException | OperationsDBException | ThereIsNoView e) {
            ((MainController) mediator).showError("Error", e.getMessage());
        }
    }

    private String verificatorData(String names, String lastNames, String mail, String confirMail, Date registrationDate, Date birthd, String pass1, String pass2, Direction nueva) throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException, IncompleteDataInRecord, IOException, DataAccessException, OperationsDBException, ThereIsNoView {
        StringBuilder errores = new StringBuilder();
        if (names.isEmpty()) {
            errores.append("El nombre es requerido.\n");
        } else if (!VerificatorSetter.stringVerificator(names, 100)) {
            errores.append("El nombre no puede contener números ni caracteres especiales");
        }
        if (lastNames.isEmpty()) {
            errores.append("El apellido es requerido.\n");
        } else if (!VerificatorSetter.stringVerificator(lastNames, 100)) {
            errores.append("El apellido no puede contener números ni caracteres especiales");
        }
        if (VerificatorSetter.validarCorreoElectronico(mail) && VerificatorSetter.validarCorreoElectronico(confirMail)) {
            if (mail.equalsIgnoreCase(confirMail)) {
                int calculatorAge = FunctionsApp.calculateAge(registrationDate, birthd);
                if (calculatorAge >= 18 && calculatorAge <= 100) {
                    if (pass1.isEmpty() || pass2.isEmpty()) {
                        errores.append("Ambas contraseñas son requeridas.\n");
                    } else if (!pass1.equals(pass2)) {
                        errores.append("Las contraseñas no coinciden.\n");
                    } else {
                        TypeUser tipeUs = comboTypeUser.getValue();
                        String tipeUser = tipeUs.toString();
                        if (!tipeUser.equalsIgnoreCase(String.valueOf(TypeUser.USUARIO_NORMAL))) {
                            String college = colegiadoTextField.getText();
                            String especialidad = especialidadTextField.getText();
                            String descripcion = descripcionTextArea.getText();
                            if (VerificatorSetter.stringVerificatorletterAndNumbers(especialidad, 150)) {
                                if (VerificatorSetter.stringVerificatorletterAndNumbers(descripcion, 7000)) {
                                    ProfessionalUser nuevo = new ProfessionalUser(names, lastNames, pass1, birthd, registrationDate, mail, tipeUs, nueva, college, especialidad, descripcion);
                                    mediator.makeRecordRegister(nuevo);
                                } else {
                                    ((MainController) mediator).showError("Error", "Revise la descripción, puede contener carácteres no adecuados o ser superior a 2000 caracteres");
                                }
                            }
                        } else {
                            NormalUser nuevouser = new NormalUser(names, lastNames, pass1, birthd, registrationDate, mail, tipeUs, nueva);
                            System.out.println(nuevouser.toString());
                            mediator.makeRecordRegister(nuevouser);
                        }
                    }

                } else {
                    ((MainController) mediator).showError("Error", "Para inscribirte necesitas tener 18 años");
                }
            } else {
                ((MainController) mediator).showError("Error", "Los correos electrónicos no coinciden.\n");
            }
        } else {
            ((MainController) mediator).showError("Error", "No has introducido o no tiene el formato correcto el correo");
        }
        return errores.toString();
    }


    @FXML
    void volverHome(ActionEvent event) throws ThereIsNoView {
        mediator.volverIncio();
    }

    @Override
    public void setMediator(Mediator mediador) {
        this.mediator = (MediatorProfile) mediador;
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
