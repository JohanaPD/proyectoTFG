package org.example.proyectotfg.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.proyectotfg.DAO.SqliteConnector;
import org.example.proyectotfg.entities.Direction;
import org.example.proyectotfg.entities.NormalUser;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.exceptions.OperationsDBException;
import org.example.proyectotfg.functions.FunctionsApp;
import org.example.proyectotfg.functions.VerificatorSetter;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorFirstScreen;
import org.example.proyectotfg.mediators.MediatorProfile;
import org.example.proyectotfg.mediators.ViewController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class UpdatePersonController implements ViewController {
    @FXML
    private Label colegiadoLabel;

    @FXML
    private TextField colegiadoTextField;

    @FXML
    private ComboBox<TypeUser> comboTypeUser;

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
    private TextField textStreet;

    @FXML
    private TextField textCity;

    @FXML
    private TextField PostalCode;

    @FXML
    private TextField textNombre;

    @FXML
    private PasswordField textPassword;

    @FXML
    private PasswordField textPassword2;

    private MediatorFirstScreen mediator;
    private Person person;
    Connection conect;

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
        colegiadoTextField.setText("");
        colegiadoLabel.setVisible(visible);
        colegiadoTextField.setVisible(visible);
        especialidadTextField.setText("");
        especialidadLabel.setVisible(visible);
        especialidadTextField.setVisible(visible);
        descripcionTextArea.setText("");
        descripcionLabel.setVisible(visible);
        descripcionTextArea.setVisible(visible);
        /*dateNacimiento.setEditable(false);*/
    }

    public void chargePerson(Person person) throws OperationsDBException {
        textNombre.setText(person.getNames());
        textApellidos.setText(person.getLastNames());
        email.setText(person.getEmail());
        confirmarMail.setText(person.getEmail());
        Direction dir = person.getDirection();
        textStreet.setText(dir.getStreet());
        textCity.setText(dir.getCity());
        PostalCode.setText(String.valueOf(dir.getPostalCode()));
        if (!person.getTypeUser().equals(TypeUser.USUARIO_NORMAL)) {
            ProfessionalUser profesionalPerson = SqliteConnector.chargeProfesionalUserById(person.getIdPerson());
            especialidadTextField.setText(profesionalPerson.getSpecialty());
            colegiadoLabel.setText(profesionalPerson.getCollegiate());
            descripcionTextArea.setText(profesionalPerson.getDescription());
        }
        this.person = person;
    }

    public void updateData(ActionEvent actionEvent) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException, OperationsDBException, SQLException {
        boolean correctDirection = true;
        String names = textNombre.getText();
        if (names.equalsIgnoreCase("")) {
            names = person.getNames();
        }
        String lastNames = textApellidos.getText();
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
        String codPostal = PostalCode.getText();
        if (city.equalsIgnoreCase("")) {
            codPostal = String.valueOf(person.getDirection().getPostalCode());
        }
        Direction nueva = null;
        try {
            nueva = new Direction(calle, city, Integer.parseInt(codPostal));
        } catch (NumberFormatException e) {
            correctDirection = false;
        }
        if (correctDirection) {
            String mail = email.getText();
            if (mail.equalsIgnoreCase("")) {
                mail = person.getEmail();
            }
            String confirMail = confirmarMail.getText();
            String pass1 = textPassword.getText().trim();
            String pass2 = textPassword2.getText().trim();

            String errores = verificatorData(names, lastNames, mail, confirMail, pass1, pass2, nueva);
            if (!errores.isEmpty()) {
                ((MainController) mediator).showError("Errores en el registro", errores);
            }
        }
    }


    private String verificatorData(String names, String lastNames, String mail, String confirMail, String pass1, String pass2, Direction nueva) throws OperationsDBException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException, SQLException {
        StringBuilder errores = new StringBuilder();
        if (!VerificatorSetter.stringVerificator(names, 100)) {
            errores.append("El nombre no puede contener números ni caracteres especiales.\n");
        }
        if (!VerificatorSetter.stringVerificator(lastNames, 100)) {
            errores.append("El apellido no puede contener números ni caracteres especiales.\n");
        }
        if (!mail.isEmpty() && !confirMail.isEmpty()) {
            if (VerificatorSetter.validarCorreoElectronico(mail) && VerificatorSetter.validarCorreoElectronico(confirMail)) {
                if (mail.equalsIgnoreCase(confirMail)) {
                    TypeUser tipeUs = comboTypeUser.getValue();
                    String tipeUser = tipeUs.toString();

                    if (!pass1.isEmpty() || !pass2.isEmpty()) {
                        if (!pass1.equals(pass2)) {
                            errores.append("Las contraseñas no coinciden.\n");
                        } else {
                            // Actualizar con contraseña
                            if (tipeUser.equalsIgnoreCase(String.valueOf(TypeUser.USUARIO_NORMAL))) {
                                NormalUser nuevoUser = new NormalUser(names, lastNames, pass1, mail, nueva);
                                mediator.updateAllDataPerson(nuevoUser);
                            } else {
                                // ProfessionalUser
                                ProfessionalUser replace=SqliteConnector.chargeProfesionalUserById(person.getIdPerson());

                                String college = colegiadoTextField.getText();
                                if (college.equalsIgnoreCase("")) {
                                    college= replace.getCollegiate();
                                }
                                String especialidad = especialidadTextField.getText();
                                if (especialidad.equalsIgnoreCase("")) {
                                    especialidad= replace.getCollegiate();
                                }
                                String descripcion = descripcionTextArea.getText();
                                if (descripcion.equalsIgnoreCase("")) {
                                    descripcion= replace.getCollegiate();
                                }
                                if (!college.equalsIgnoreCase("") || !especialidad.equalsIgnoreCase("") || !descripcion.equalsIgnoreCase("")) {
                                    ProfessionalUser prof = new ProfessionalUser(names, lastNames, pass1, mail, nueva, college, especialidad, descripcion);
                                    mediator.updateAllDataPerson(prof);
                                } else {
                                    errores.append("Datos profesionales incorrectos.\n");
                                }
                            }
                        }
                    } else {
                        // Actualizar sin contraseña
                        if (tipeUser.equalsIgnoreCase(String.valueOf(TypeUser.USUARIO_NORMAL))) {
                            NormalUser nuevoUser = new NormalUser(person.getIdPerson(), names, lastNames, mail, nueva);
                            mediator.updateDataPerson(nuevoUser);
                        } else {
                            // ProfessionalUser
                            ProfessionalUser replace=SqliteConnector.chargeProfesionalUserById(person.getIdPerson());

                            String college = colegiadoTextField.getText();
                            if (college.equalsIgnoreCase("")) {
                                college= replace.getCollegiate();
                            }
                            String especialidad = especialidadTextField.getText();
                            if (especialidad.equalsIgnoreCase("")) {
                                especialidad= replace.getCollegiate();
                            }
                            String descripcion = descripcionTextArea.getText();
                            if (descripcion.equalsIgnoreCase("")) {
                                descripcion= replace.getCollegiate();
                            }

                            if (!college.equalsIgnoreCase("") || !especialidad.equalsIgnoreCase("") || !descripcion.equalsIgnoreCase("")) {
                                ProfessionalUser prof = new ProfessionalUser(replace.getIdPerson(), names, lastNames, mail, nueva, college, especialidad, descripcion);
                                mediator.updateDataPerson(prof);
                            } else {
                                errores.append("Datos profesionales incorrectos.\n");
                            }
                        }
                    }
                } else {
                    errores.append("Los correos electrónicos no coinciden.\n");
                }
            } else {
                errores.append("No has introducido o no tiene el formato correcto el correo.\n");
            }
        }

        return errores.toString();
    }


  /*  public void updateData(ActionEvent actionEvent) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException, OperationsDBException {
        boolean correctDirection = true;
        String names = textNombre.getText();
        if (names.equalsIgnoreCase("")) {
            names = person.getNames();
        }
        String lastNames = textApellidos.getText();
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
        String codPostal = PostalCode.getText();
        if (city.equalsIgnoreCase("")) {
            codPostal = String.valueOf(person.getDirection().getPostalCode());
        }
        Direction nueva = null;
        try {
            nueva = new Direction(calle, city, Integer.parseInt(codPostal));
        } catch (NumberFormatException e) {
            correctDirection = false;
        }
        if (correctDirection) {
            String mail = email.getText();
            if (mail.equalsIgnoreCase("")) {
                mail = person.getLastNames();
            }
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
                String errores = verificatorData(names, lastNames, mail, confirMail, birthd, pass1, pass2, nueva);
                if (!errores.isEmpty()) {
                    ((MainController) mediator).showError("Errores en el registro", errores);
                } else {
                    ((MainController) mediator).showError("Error", "No has definido una fecha en el calendario");
                }

            }

        }
    }

    private String verificatorData(String names, String lastNames, String mail, String confirMail, Date birthd, String pass1, String pass2, Direction nueva) throws OperationsDBException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        StringBuilder errores = new StringBuilder();
        System.out.println(errores);
        if (!names.isEmpty()) {
            if (!VerificatorSetter.stringVerificator(names, 100)) {
                errores.append("El nombre no puede contener números ni caracteres especiales");
            }
            errores.append("El nombre es requerido.\n");
        }
        if (!lastNames.isEmpty()) {
            if (!VerificatorSetter.stringVerificator(lastNames, 100)) {
                errores.append("El apellido no puede contener números ni caracteres especiales");
            }
            errores.append("El apellido es requerido.\n");
        }
        if (!mail.isEmpty() && !confirMail.isEmpty()) {
            if (VerificatorSetter.validarCorreoElectronico(mail) && VerificatorSetter.validarCorreoElectronico(confirMail)) {
                if (mail.equalsIgnoreCase(confirMail)) {
                    if (birthd != null) {
                        int calculatorAge = FunctionsApp.calculateAge(person.getRegistrationDate(), birthd);
                        if (calculatorAge >= 18 && calculatorAge <= 100) {
                            if (!pass1.isEmpty() || !pass2.isEmpty()) {
                                if (!pass1.equals(pass2)) {
                                    errores.append("Las contraseñas no coinciden.\n");
                                } else {
                                    TypeUser tipeUs = comboTypeUser.getValue();
                                    String tipeUser = tipeUs.toString();
                                    System.out.println(tipeUser);
                                    if (!tipeUser.equalsIgnoreCase(String.valueOf(TypeUser.USUARIO_NORMAL))) {
                                        //aqui, llamar al metodo para hacer ver los datos del usuario especifico
                                        ProfessionalUser prof = SqliteConnector.chargeProfesionalUserById(person.getIdPerson());
                                        String college = colegiadoTextField.getText();
                                        if (!college.equalsIgnoreCase("")) {
                                            college = prof.getCollegiate();
                                        }
                                        String especialidad = especialidadTextField.getText();
                                        if (!especialidad.equalsIgnoreCase("")) {
                                            especialidad = prof.getSpecialty();
                                        }
                                        String descripcion = descripcionTextArea.getText();
                                        if (!descripcion.equalsIgnoreCase("")) {
                                            descripcion = prof.getDescription();
                                        }
                                        if (VerificatorSetter.stringVerificator(especialidad, 150)) {
                                            if (VerificatorSetter.stringVerificator(descripcion, 2000)) {
                                                ProfessionalUser nuevo = new ProfessionalUser(names, lastNames, pass1, birthd, person.getRegistrationDate(), mail, tipeUs, nueva, college, especialidad, descripcion);
                                                mediator.updateAllDataPerson(nuevo);
                                            } else {
                                                errores.append("Datos profesionales incorrectos .\n");
                                            }
                                        }
                                    } else {
                                        NormalUser nuevouser = new NormalUser(names, lastNames, pass1, birthd, person.getRegistrationDate(), mail, tipeUs, nueva);
                                        System.out.println(nuevouser.toString());
                                        mediator.updateAllDataPerson(nuevouser);
                                        if (!tipeUser.equalsIgnoreCase(String.valueOf(TypeUser.USUARIO_NORMAL))) {
                                            ProfessionalUser prof = SqliteConnector.chargeProfesionalUserById(person.getIdPerson());
                                            String college = colegiadoTextField.getText();
                                            if (!college.equalsIgnoreCase("")) {
                                                college = prof.getCollegiate();
                                            }
                                            String especialidad = especialidadTextField.getText();
                                            if (!especialidad.equalsIgnoreCase("")) {
                                                especialidad = prof.getSpecialty();
                                            }
                                            String descripcion = descripcionTextArea.getText();
                                            if (!descripcion.equalsIgnoreCase("")) {
                                                descripcion = prof.getDescription();
                                            }
                                            if (VerificatorSetter.stringVerificator(especialidad, 150)) {
                                                if (VerificatorSetter.stringVerificator(descripcion, 2000)) {
                                                    ProfessionalUser nuevo = new ProfessionalUser(names, lastNames, pass1, birthd, person.getRegistrationDate(), mail, tipeUs, nueva, college, especialidad, descripcion);
                                                    mediator.updateDataPerson(nuevo);
                                                } else {
                                                    //todo: revisar
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                TypeUser tipeUs = comboTypeUser.getValue();
                                String tipeUser = tipeUs.toString();
                                System.out.println(tipeUser);
                                if (!tipeUser.equalsIgnoreCase(String.valueOf(TypeUser.USUARIO_NORMAL))) {
                                    ProfessionalUser prof = SqliteConnector.chargeProfesionalUserById(person.getIdPerson());
                                    String college = colegiadoTextField.getText();
                                    if (!college.equalsIgnoreCase("")) {
                                        college = prof.getCollegiate();
                                    }
                                    String especialidad = especialidadTextField.getText();
                                    if (!especialidad.equalsIgnoreCase("")) {
                                        especialidad = prof.getSpecialty();
                                    }
                                    String descripcion = descripcionTextArea.getText();
                                    if (!descripcion.equalsIgnoreCase("")) {
                                        descripcion = prof.getDescription();
                                    }
                                    if (VerificatorSetter.stringVerificator(especialidad, 150)) {
                                        if (VerificatorSetter.stringVerificator(descripcion, 2000)) {
                                            ProfessionalUser nuevo = new ProfessionalUser(names, lastNames, birthd, mail, tipeUs, nueva, college, especialidad, descripcion);
                                            mediator.updateDataPerson(nuevo);
                                        } else {
                                            //todo: revisar
                                        }
                                    }
                                }
                            }
                                           } else {
                            ((MainController) mediator).showError("Error", "Para inscribirte necesitas tener 18 años");
                        }
                    } else {
                        String type = String.valueOf(person.getTypeUser());
                        NormalUser nuevouser = new NormalUser(names, lastNames, mail, type, nueva);
                        System.out.println(nuevouser.toString());
                        mediator.updateDataPerson(nuevouser);
                    }
                    ((MainController) mediator).showError("Error", "Los correos electrónicos no coinciden.\n");
                }
            } else {
                ((MainController) mediator).showError("Error", "No has introducido o no tiene el formato correcto el correo");
            }
        }


        return errores.toString();

    }*/


    public void volverHome(ActionEvent actionEvent) {
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
