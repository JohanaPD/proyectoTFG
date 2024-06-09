package org.example.proyectotfg.controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.proyectotfg.DAO.SqliteConnector;
import org.example.proyectotfg.entities.*;
import org.example.proyectotfg.enumAndTypes.Notificators;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.exceptions.*;
import org.example.proyectotfg.functions.FunctionsApp;
import org.example.proyectotfg.functions.SenderReaderMail;
import org.example.proyectotfg.mediators.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Main controller class implementing various mediator interfaces.
 *
 * <p>This class serves as the main controller for the application, implementing several mediator interfaces
 * to handle different aspects of the application's functionality.</p>
 *
 * <p>Authors: Johana Pardo, Daniel Ocaña</p>
 * <p>Version: Java 21</p>
 * <p>Since: 2024-06-08</p>
 */
public class MainController implements Mediator, MediatorAccess, MediatorProfile, MediatorFirstScreen, MediatorPost, MediatorConstruction, MediatorNotifiers {

    private Stage mainStage;
    SqliteConnector connect;
    ViewController actualController;
    Person person;
    String passWordApp = "j v g l r d n k f x kw m s b e";
    Timer timer;
    TimerTask timerTask;
    MedicalAppointment actualAppointment;
    MedicalAppointment editedAppointment;

    /**
     * Constructs a new MainController object.
     *
     * @param mainStage the main stage of the application
     * @throws IOException if an I/O error occurs while loading the first view
     */
    public MainController(Stage mainStage) throws IOException {
        try {
            connect = new SqliteConnector("jdbc:sqlite:src/main/resources/sqliteBBDD/MeetPsych.db");
            this.mainStage = mainStage;
            loadFirstView();
        } catch (SQLException | OperationsDBException e) {
            showError("Error", e.getMessage());
        }
    }
    /*   ================================================================================================
        ======================================vistas principales =====================================================*/

    /**
     * Loads the first view of the application.
     */
    public void loadFirstView() {
        try {
            loadView("/org/example/proyectotfg/entry-view.fxml");
            mainStage.setTitle("MeetPsych!");
        } catch (ThereIsNoView e) {
            showError("Error", "Error al cargar la aplicación");
        }
    }

    /*   ================================================================================================
       ====================================CARGAR TODOS LOS SCENE ==============================*/

    /**
     * Loads the initial interface view of the application.
     */
    private void loadInitialInterface() {
        try {
            loadView("/org/example/proyectotfg/initial-interface-view.fxml");
            InitialInterfaceController initialInterfaceController = (InitialInterfaceController) actualController;
            initialInterfaceController.getProfessionals();
            initialInterfaceController.loadServices();
            initialInterfaceController.setTextWelcome(person.getNames());
            initialInterfaceController.setUser(person);
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }

    }

    @Override
    public List<ProfessionalUser> getProfessionals() throws SQLException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NonexistingUser, NullArgumentException, OperationsDBException {
        List<ProfessionalUser>
                professionalUsers = connect.getProfessionals();

        return professionalUsers;
    }

    /**
     * Loads a view with the specified FXML file path.
     *
     * @param s the file path of the FXML file to load.
     * @throws ThereIsNoView if the view does not exist or cannot be loaded.
     */
    private void loadView(String s) throws ThereIsNoView {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
            Parent root = loader.load();
            actualController = loader.getController();
            actualController.setMediator(this);
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ThereIsNoView("La vista no existe o no se pudo cargar: " + e.getMessage());
        }
    }

    /**
     * Opens the login view.
     */
    @Override
    public void openLogin() {
        try {
            mainStage.setTitle("Bienvenidos!!");
            loadView("/org/example/proyectotfg/general-login-view.fxml");
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

    /*=======================================================================================================
     * ==========================================HACER REGISTRO =============================================*/

    /**
     * Opens the user registration view.
     */
    @Override
    public void userRegister() {
        try {
            mainStage.setTitle("Registrate en solo un minuto!!");
            loadView("/org/example/proyectotfg/registro-view.fxml");
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }
    /*=======================================================================================================
     * ==========================================Recuperar Contraseña =============================================*/

    /**
     * Loads the password recovery view.
     */
    @Override
    public void loadRecoverPassword() {
        try {
            mainStage.setTitle("Recupere su contraseña!!");
            loadView("/org/example/proyectotfg/recover-password-view.fxml");
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Recovers the password for a user with the given email address.
     *
     * @param mail the email address of the user.
     * @param pass the new password for the user.
     */

    @Override
    public void recoverPassword(String mail, String pass) {
        try {
            Person person = connect.chargePersonWithNewPassword(mail, pass);
            String messageHTML = FunctionsApp.returnPasswordRecoverString(person);
            SenderReaderMail sender = new SenderReaderMail();
            if (person.getTypeUser() == TypeUser.USUARIO_NORMAL) {
                updateAllDataPerson((NormalUser) person);
            } else {
                updateAllDataPerson((ProfessionalUser) person);
            }
            enviarEmail(sender, person.getEmail(), messageHTML);
        } catch (InvalidKeySpecException | NonexistingUser | IncorrectLoginEception | DataAccessException |
                 OperationsDBException | SQLException e) {
            showError("Error", e.getMessage());
        }

    }

    /**
     * Loads the view for displaying posts shared by the user.
     */
    public void loadPostView() {
        try {
            loadView("/org/example/proyectotfg/post-view.fxml");
            mainStage.setTitle("¡Estos son los posts que has compartido!");
            ControllerPost controllerPost = (ControllerPost) actualController;
            controllerPost.setPerson(person);
            controllerPost.setPosts(connect.serchPostByPerson(person));
            controllerPost.loadPosts();
        } catch (ThereIsNoView | IncorrectDataException | NullArgumentException | OperationsDBException e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Performs the registration process for a professional user and sends a confirmation email.
     *
     * @param user The ProfessionalUser object containing the registration details.
     */
    @Override
    public void makeRecordRegister(ProfessionalUser user) {
        String mensaje = FunctionsApp.devolverStringMail(user);
        SenderReaderMail sender = new SenderReaderMail();
        enviarEmail(sender, user.getEmail(), mensaje);
        try {
            connect.registerProfessionalUser(user, false);
            showInfo("Registro correcto", "Se ha registrado correctamente el usuario");
            openLogin();
        } catch (DuplicateKeyException | OperationsDBException e) {
            showError("Error", e.getMessage());
        }
        person = user;
    }

    /**
     * Performs the registration process for a normal user and sends a confirmation email.
     *
     * @param user The NormalUser object containing the registration details.
     */
    @Override
    public void makeRecordRegister(NormalUser user) {
        String mensaje = FunctionsApp.devolverStringMail(user);
        SenderReaderMail sender = new SenderReaderMail();
        enviarEmail(sender, user.getEmail(), mensaje);
        try {
            connect.registerNormalUser(user, false);
            showInfo("Registro correcto", "Se ha registrado correctamente el usuario");
            openLogin();
        } catch (DuplicateKeyException | OperationsDBException e) {
            showError("Error", e.getMessage());
        }
        person = user;
    }

    /**
     * Sends an email to the specified user with the provided message.
     *
     * @param sender  The SenderReaderMail object used to send the email.
     * @param user    The email address of the recipient.
     * @param mensaje The message content to be sent.
     */
    private void enviarEmail(SenderReaderMail sender, String user, String mensaje) {
        try {
            sender.enviarMensajeHTML("meetpsychproject@gmail.com", user, "Verificación de Cuenta", mensaje, "meetpsychproject@gmail.com", passWordApp);
        } catch (Exception e) {
            showError("Error", "Error al enviar el mensaje: " + e.getMessage());
        }
    }

    /**
     * Returns to the initial login screen.
     */
    @Override
    public void volverIncio() {
        try {
            mainStage.setTitle("Te esperamos pronto!!");
            loadView("/org/example/proyectotfg/general-login-view.fxml");
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

   /*   ================================================================================================
        ======================================vistas principales =====================================================*/

    /**
     * Logs in the user with the provided credentials.
     *
     * @param user     the username or email of the user.
     * @param pass     the password of the user.
     * @param typeuser the type of user logging in (e.g., NormalUser or ProfessionalUser).
     */
    @Override
    public void loginUser(String user, String pass, TypeUser typeuser) {
        try {
            Person person = connect.loginUser(user, pass, typeuser);
            if (person != null) {
                this.person = person;
                mainStage.setTitle("Bienvenidos ");
                loadInitialInterface();
                actualController.setMediator(this);
            }
        } catch (InvalidKeySpecException | NonexistingUser | IncorrectLoginEception | DataAccessException |
                 OperationsDBException | NoSuchAlgorithmException e) {
            showError("Error", e.getMessage());
        }
    }

    /*   ================================================================================================
        ======================================Firs screen =====================================================*/

    /**
     * Initializes the services provided by the application.
     *
     * @param services a HashMap containing the services to be initialized, where the key is the service name and the value is the service description.
     * @return the Parent node containing the initialized services.
     */
    @Override
    public Parent initializeServices(HashMap<String, String> services) {
        HBox contenedorHBox = new HBox(4);
        contenedorHBox.setAlignment(Pos.CENTER);
        contenedorHBox.setMaxWidth(60);
        contenedorHBox.setMaxHeight(80);
        try {
            for (Map.Entry<String, String> map : services.entrySet()) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proyectotfg/fragment-services-view.fxml"));
                Parent fragment = fxmlLoader.load();
                ControllerFragmentServicios controller = fxmlLoader.getController();
                controller.setData(String.valueOf(map.getKey()), String.valueOf(map.getValue()));
                controller.setCallback(new Callback() {
                    @Override
                    public void doAction() {
                        try {
                            switch (map.getKey()) {
                                case "Ver Post":
                                    loadPostView();
                                    break;
                                case "Publicar Post":
                                    loadPublicPostView();
                                    break;
                                default:
                                    loadView("/org/example/proyectotfg/in-construction-view.fxml");
                                    mainStage.setTitle("¡Estamos construyendo nuevos espacios para ti!");
                            }
                        } catch (ThereIsNoView e) {
                            showError("Error", e.getMessage());
                        }
                    }
                });
                contenedorHBox.getChildren().add(fragment);
            }
            AnchorPane.setTopAnchor(contenedorHBox, 0.0);
            AnchorPane.setRightAnchor(contenedorHBox, 0.0);
            AnchorPane.setBottomAnchor(contenedorHBox, 0.0);
            AnchorPane.setLeftAnchor(contenedorHBox, 0.0);
        } catch (IOException | NotFoundImage ie) {
            showError("Error", ie.getMessage());
        }
        return contenedorHBox;
    }

    /*   ================================================================================================
          ======================================Search View=====================================================*/

    /**
     * Loads the search results of professional users.
     *
     * @param professionalUsers a List containing the professional users to be displayed in the search results.
     * @return the Parent node containing the loaded search results.
     */

    @Override
    public Parent loadSearchs(List<ProfessionalUser> professionalUsers) {
        StackPane stackPane = new StackPane();
        stackPane.getChildren().clear();
        VBox contenedorHBox = new VBox(4);
        contenedorHBox.setAlignment(Pos.CENTER);
        contenedorHBox.setMaxWidth(60);
        contenedorHBox.setMaxHeight(70);
        try {
            for (ProfessionalUser us : professionalUsers) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proyectotfg/fragment-info-search_view.fxml"));
                Parent fragment = fxmlLoader.load();
                FragmentInfoSerchController controller = fxmlLoader.getController();
                controller.setProfessionalUser(us);
                controller.setPerson(person);
                controller.setData(String.valueOf(us.getNames()), String.valueOf(us.getSpecialty()), "/org/example/proyectotfg/imgUsuario/doctor3.png", 5);
                fragment.resize(340, 180);
                controller.setCallback(() -> {
                    try {
                        addToFavorites(us, person);
                    } catch (OperationsDBException e) {
                        showError("Error", "Error a la hora de añadirlo a favoritos");
                    }
                });
                contenedorHBox.getChildren().add(fragment);
            }
            stackPane.getChildren().add(contenedorHBox);
            contenedorHBox.toFront();
        } catch (IOException | NotFoundImage e) {
            showError("Error", "Error a la hora de cargar el fragmento: " + e.getMessage());
        }
        return contenedorHBox;
    }

    /**
     * Adds a professional user to the favorites list of a person.
     *
     * @param professionalUser the ProfessionalUser to be added to the favorites list.
     * @param person           the Person who wants to add the professional user to their favorites list.
     * @throws OperationsDBException if there is an error while adding the professional user to the favorites list.
     */
    @Override
    public void addToFavorites(ProfessionalUser professionalUser, Person person) throws OperationsDBException {
        boolean addto = connect.addProfesionalUserInFavorites(professionalUser, person);
    }

    @Override
    public Parent initializeProfessionals(List<ProfessionalUser> professionalUsers) throws NonexistingUser {
        HBox contenedorHBox2 = new HBox(6);
        if (professionalUsers != null) {
            contenedorHBox2.setAlignment(Pos.CENTER);
            contenedorHBox2.setMaxWidth(50);
            contenedorHBox2.setMaxHeight(80);
            try {
                int imageIndex = 1;
                int totalImages = 6;
                for (ProfessionalUser us : professionalUsers) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proyectotfg/fragment-services-view.fxml"));
                    Node fragment = fxmlLoader.load();
                    ControllerFragmentServicios controller = fxmlLoader.getController();
                    String imagePath = String.format("/org/example/proyectotfg/imgUsuario/doctor%d.png", imageIndex);
                    controller.setData(String.valueOf(us.getNames()), String.valueOf(imagePath));
                    int finalImageIndex = imageIndex;
                    controller.setCallback(() -> MainController.this.openProfessionalUser(us, finalImageIndex));
                    imageIndex = (imageIndex % totalImages) + 1;
                    contenedorHBox2.getChildren().add(fragment);
                }
                AnchorPane.setTopAnchor(contenedorHBox2, 0.0);
                AnchorPane.setRightAnchor(contenedorHBox2, 0.0);
                AnchorPane.setBottomAnchor(contenedorHBox2, 0.0);
                AnchorPane.setLeftAnchor(contenedorHBox2, 0.0);
                return contenedorHBox2;
            } catch (IOException | NotFoundImage ioe) {
                showError("Error ", ioe.getMessage());
            }
        }
        return contenedorHBox2;
    }


    /*   ================================================================================================
       ======================================Appointment manager=====================================================*/

    /**
     * Opens the appointment management view.
     *
     * <p>This method sets the title of the main stage to "Gestor de citas" and loads the appointment management view.
     * It also initializes the AppointmentManegemenController, sets the person attribute, sets the title post, and loads the professionals and the person's appointments.</p>
     *
     * @throws ThereIsNoView if there is an error loading the appointment management view.
     */
    @Override
    public void openAppointmentView() {
        try {
            mainStage.setTitle("Gestor de citas");
            loadView("/org/example/proyectotfg/appointment-manegement-view.fxml");
            AppointmentManegemenController appointmentManegemenController = (AppointmentManegemenController) actualController;
            appointmentManegemenController.setPerson(person);
            appointmentManegemenController.setTitlePost("Hola, " + person.getNames());
            appointmentManegemenController.loadProfessionals();
            appointmentManegemenController.loadMyAppointments();

        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Loads professionals in the mediator calendar.
     *
     * <p>This method loads a list of professional users into a horizontal box, each represented by a fragment-services-view.
     * It sets the data for each professional user including their names and image path. It also sets a callback function to handle user interactions.
     * When a professional user is selected, it retrieves the selected date from the appointment management controller and searches for appointments for that professional on that date.</p>
     *
     * @return The parent node containing the loaded professionals in the mediator calendar.
     */
    @Override
    public Parent loadProfessionalsInMediatorCalendar() {
        HBox contenedorHBox2 = new HBox(6);
        try {
            List<ProfessionalUser> professionalUsers = connect.getProfessionals();
            contenedorHBox2.setAlignment(Pos.CENTER);
            contenedorHBox2.setMaxWidth(100);
            contenedorHBox2.setMaxHeight(130);
            int imageIndex = 1;
            int totalImages = 6;
            for (ProfessionalUser us : professionalUsers) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proyectotfg/fragment-services-view.fxml"));
                Node fragment = fxmlLoader.load();
                ControllerFragmentServicios controller = fxmlLoader.getController();
                String imagePath = String.format("/org/example/proyectotfg/imgUsuario/doctor%d.png", imageIndex);
                controller.setData(String.valueOf(us.getNames()), String.valueOf(imagePath));

                controller.setCallback(() -> {
                    AppointmentManegemenController appointmentManegemenController = (AppointmentManegemenController) actualController;
                    appointmentManegemenController.setProfessionalUser(us);
                    LocalDate localDate = appointmentManegemenController.getDatePicker().getValue();
                    if (localDate == null) {
                        showError("Error", "Tienes que seleccionar una" + " fecha antes de continuar);");
                    } else {
                        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        searchAppointments(us.getIdPerson(), date, false);
                    }
                });
                imageIndex = (imageIndex % totalImages) + 1;
                contenedorHBox2.getChildren().add(fragment);
            }
            AnchorPane.setTopAnchor(contenedorHBox2, 0.0);
            AnchorPane.setRightAnchor(contenedorHBox2, 0.0);
            AnchorPane.setBottomAnchor(contenedorHBox2, 0.0);
            AnchorPane.setLeftAnchor(contenedorHBox2, 0.0);
        } catch (IOException | NotFoundImage | SQLException | IncorrectDataException | NoSuchAlgorithmException |
                 InvalidKeySpecException | NullArgumentException | OperationsDBException | NonexistingUser ioe) {
            showError("Error ", ioe.getMessage());
        }
        return contenedorHBox2;

    }

    /**
     * Loads available appointments in the calendar.
     *
     * <p>This method loads a list of available medical appointments into a horizontal box, each represented by a fragment-appointment-hours-view.
     * It sets the hour and minute texts for each appointment based on the provided date. It also sets a callback function to handle user interactions.
     * When an appointment is selected, it either sets the appointment date and confirmation text in the appointment management controller or edits the existing appointment if updateAppointment is true.</p>
     *
     * @param medicalAppointments The list of available medical appointments to be loaded.
     * @param updateAppointment   A boolean value indicating whether to update the appointment.
     * @return The parent node containing the loaded available appointments in the calendar.
     */
    @Override
    public Parent loadAvailableAppointmentsInCalendar(List<Date> medicalAppointments, boolean updateAppointment) {
        HBox contenedorHBox2 = new HBox(6);
        try {
            for (Date date : medicalAppointments) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proyectotfg/fragment-appointment-hours-view.fxml"));
                Node fragment = fxmlLoader.load();
                ControllerFragmentApoinmentHours controller = fxmlLoader.getController();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hours = calendar.get(Calendar.HOUR_OF_DAY); // Hora en formato 24 horas
                int minutes = calendar.get(Calendar.MINUTE);

                String stringHours = String.valueOf(hours).length() == 1 ? hours + "0" : String.valueOf(hours);
                String stringMinutes = String.valueOf(minutes).length() == 1 ? minutes + "0" : String.valueOf(minutes);

                controller.setHourText(stringHours);
                controller.setMinuteText(stringMinutes);
                controller.setCallback(() -> {
                    AppointmentManegemenController controllerAppointment = (AppointmentManegemenController) actualController;
                    if (!updateAppointment) {
                        controllerAppointment.setAppointmentDate(date);
                        controllerAppointment.setTextConfirm("Hora de cita seleccionada: " + stringHours + ":" + stringMinutes);

                    } else {
                        MedicalAppointment oldAppointment = controllerAppointment.getActualMediacalAppointment();
                        editAppointment(oldAppointment, date);
                    }
                });

                contenedorHBox2.getChildren().add(fragment);
            }
        } catch (IOException e) {
            showError("Error ", e.getMessage());
        }
        return contenedorHBox2;
    }

    /**
     * Loads the user's next appointments.
     *
     * <p>This method loads the user's next medical appointments into a horizontal box, each represented by a fragment-appointment-hours-view.
     * It sets the date, hour, and minute texts for each appointment based on the appointment's visit date.
     * It also sets a callback function to handle user interactions.
     * When an appointment is selected, it sets the appointment date, appointment text, actual appointment, and edited appointment in the appointment management controller.
     * It also starts a timer to monitor any changes to the appointment and updates the UI accordingly.</p>
     *
     * @return The parent node containing the loaded next appointments.
     */
    @Override
    public Parent myNextAppoinments() {
        HBox contenedorHBox2 = new HBox(6);

        try {
            List<MedicalAppointment> medicalAppointments = connect.searchMyAppointments(person);

            for (MedicalAppointment medicalApp : medicalAppointments) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proyectotfg/fragment-appointment-hours-view.fxml"));
                Node fragment = fxmlLoader.load();
                ControllerFragmentApoinmentHours controller = fxmlLoader.getController();
                Calendar calendar = Calendar.getInstance();
                Date date = medicalApp.getVisitDate();
                calendar.setTime(date);
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String stringDate = simpleDateFormat.format(calendar.getTime());
                controller.setDataAppointment(stringDate);
                String stringHours = String.valueOf(hours).length() == 1 ? hours + "0" : String.valueOf(hours);
                String stringMinutes = String.valueOf(minutes).length() == 1 ? minutes + "0" : String.valueOf(minutes);
                controller.setHourText(stringHours);
                controller.setMinuteText(stringMinutes);
                controller.setCallback(() -> {
                    AppointmentManegemenController controllerAppointment = (AppointmentManegemenController) actualController;
                    controllerAppointment.setAppointmentDate(medicalApp.getVisitDate());
                    controllerAppointment.setTextAppointment("Psicologo \"" + medicalApp.getProfessionalUser().getNames() + medicalApp.getProfessionalUser().getLastNames() + "\" " + stringDate + " " + stringHours + ":" + stringMinutes);
                    actualAppointment = medicalApp;
                    editedAppointment = new MedicalAppointment(actualAppointment);
                    controllerAppointment.setActualMediacalAppointment(medicalApp);
                    if (timer != null) {
                        timer.cancel();
                        timerTask.cancel();
                    }
                    timer = new Timer();

                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                if (editedAppointment == null) {
                                    timerTask.cancel();
                                    timer.cancel();
                                    showInfo("Operación realizada", "Su cita se ha eliminado correctamente");
                                    openAppointmentView();
                                } else {
                                    if (actualAppointment.getVisitDate() != editedAppointment.getVisitDate()) {
                                        timerTask.cancel();
                                        timer.cancel();
                                        showInfo("Cita modificada", "Su cita ha sido modificada al dia " + editedAppointment.getVisitDate());

                                        openAppointmentView();

                                    }
                                }

                            });
                        }
                    };
                    timer.schedule(this.timerTask, 1000, 1000);
                });

                contenedorHBox2.getChildren().add(fragment);
            }
        } catch (IncorrectDataException | NoSuchAlgorithmException | InvalidKeySpecException | NullArgumentException |
                 OperationsDBException | IOException e) {
            throw new RuntimeException(e);
        }
        return contenedorHBox2;
    }

    /**
     * Navigates back from the notifier screen to the home screen.
     *
     * <p>This method cancels any running timer tasks and timers.
     * Then, it navigates back to the home screen using the {@code fromFirstScreenToHome()} method.</p>
     */
    @Override
    public void backFromNotifiersToHome() {
        if (timer != null) {
            timerTask.cancel();
            timer.cancel();
        }
        fromFirstScreenToHome();
    }

    /**
     * Deletes a medical appointment.
     *
     * @param medicalAppointment The medical appointment to be deleted.
     *
     *                           <p>This method attempts to delete the specified medical appointment from the database.
     *                           If the deletion is successful, the {@code editedAppointment} variable is set to {@code null}.
     *                           If the deletion fails, an {@code OperationsDBException} is thrown.</p>
     *
     *                           <p>After deletion, the method navigates back to the appointment view using the {@code openAppointmentView()} method.</p>
     */
    @Override
    public void deleteAppointment(MedicalAppointment medicalAppointment) {
        try {
            if (connect.deleteMedicalAppointments(medicalAppointment.getIdAppointment(), person.getIdPerson(), medicalAppointment.getVisitDate())) {
                editedAppointment = null;
            } else {
                throw new OperationsDBException("No se ha podido eliminar");
            }
        } catch (OperationsDBException e) {
            showError("Error", e.getMessage());
        }
        openAppointmentView();
    }

    /**
     * Adds a new medical appointment.
     *
     * @param professionalUser The professional user for the appointment.
     * @param date             The date of the appointment.
     *
     *                         <p>This method creates a new medical appointment with the specified professional user and date,
     *                         and attempts to insert it into the database. If the insertion is successful,
     *                         a success message is displayed, and the user is navigated back to the appointment view.
     *                         If the insertion fails, an error message is shown.</p>
     */
    @Override
    public void addAppointment(ProfessionalUser professionalUser, Date date) {
        try {
            MedicalAppointment medicalAppointment = new MedicalAppointment(professionalUser, (NormalUser) person, date, Notificators.CITACREADA);
            boolean insert = connect.insertMedicalAppointments(medicalAppointment);
            if (insert) {
                showInfo("Cita creada", "Su cita ha sido creada correctamente");
                openAppointmentView();
            }
        } catch (OperationsDBException e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Edits the details of a medical appointment.
     *
     * @param medicalAppointment The medical appointment to be edited.
     * @param dateNewAppointment The new date for the appointment.
     *
     *                           <p>This method updates the date of the specified medical appointment
     *                           to the new date provided. If the update is successful, the appointment's
     *                           details are updated locally. If the update fails, an error message is displayed.</p>
     */
    @Override
    public void editAppointment(MedicalAppointment medicalAppointment, Date dateNewAppointment) {
        try {
            boolean updateAppointment = connect.updateMedicalAppointment(medicalAppointment, dateNewAppointment);

            if (updateAppointment) {
                editedAppointment.setVisitDate(dateNewAppointment);
            } else {
                showError("Cita no actualizada", "No se ha podido actualizar correctamente la cita");
            }

        } catch (OperationsDBException e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Searches for available appointments for a given professional user and date.
     *
     * @param idPerson          The ID of the professional user.
     * @param date              The date for which appointments are to be searched.
     * @param updateAppointment A flag indicating whether the appointment needs to be updated.
     *
     *                          <p>This method searches for available appointments for the specified professional user
     *                          on the given date. If the updateAppointment flag is set to true, it indicates that the
     *                          appointment needs to be updated. The method loads the available appointments and updates
     *                          the appointment management view accordingly. If an error occurs during the operation,
     *                          an error message is displayed.</p>
     */
    @Override
    public void searchAppointments(int idPerson, Date date, boolean updateAppointment) {
        try {
            List<MedicalAppointment> notAvailableMedicalAppointments = connect.searchMedicalAppointments(idPerson, date);
            List<Date> availableMedicalAppointments = loadNextAvailableAppointments(notAvailableMedicalAppointments, date);
            AppointmentManegemenController appointmentManegemenController = (AppointmentManegemenController) actualController;
            appointmentManegemenController.loadAppointments(availableMedicalAppointments, updateAppointment);
        } catch (OperationsDBException e) {
            showError("Error", e.getMessage());
        } catch (IncorrectDataException | NoSuchAlgorithmException | InvalidKeySpecException |
                 NullArgumentException ex) {
            showError("Error", ex.getMessage());
        }
    }

    /**
     * Loads the next available appointments based on the given list of appointments and date.
     *
     * @param listAppointments The list of existing appointments.
     * @param date             The date for which appointments are to be loaded.
     * @return A list of next available appointments.
     *
     * <p>This method loads the next available appointments based on the provided list of
     * appointments and the specified date. It checks the existing appointments and returns
     * a list of available appointment times. If no appointments are available, it returns
     * an empty list. If an error occurs during the operation, it throws a runtime exception.</p>
     */
    private List<Date> loadNextAvailableAppointments(List<MedicalAppointment> listAppointments, Date date) {
        List<Date> availableAppointments = new ArrayList<>();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int day = calendar.get(Calendar.DAY_OF_MONTH); // Hora en formato 24 horas
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            final Date[] TIMES = FunctionsApp.fillArray(6, day, month, year);
            if (listAppointments.size() >= MedicalAppointment.MAX_APPOINTMENTS) {
                showInfo("Error ", "No quedan citas disponibles para la fecha elegida");
            } else if (listAppointments.isEmpty()) {
                for (int i = 0; i < TIMES.length; i++) {
                    availableAppointments.add(TIMES[i]);
                }
            } else if (listAppointments.size() > 0 && listAppointments.size() <= MedicalAppointment.MAX_APPOINTMENTS - 1) {
                for (int i = 0; i < listAppointments.size(); i++) {
                    if (listAppointments.get(i) != null) {
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        String hora = format.format(listAppointments.get(i).getVisitDate().getTime());
                        if (!hora.equals(TIMES)) {
                            availableAppointments.add(listAppointments.get(i).getVisitDate());
                        }
                    } else {
                        showError("Error", "Error con los datos recibidos ");
                    }
                }
            }
        } catch (ParseException e) {
            showError("Error", e.getMessage());
        }
        return availableAppointments;
    }


    /*   ================================================================================================
      ====================================== Update Data =====================================================*/

    @Override
    public ProfessionalUser chargeProfessionalUserById(int id) throws OperationsDBException {
        ProfessionalUser professionalUser = connect.chargeProfessionalUserById(id);
        return professionalUser;
    }

    /**
     * Updates the personal data of the user.
     *
     * @param user The user whose data needs to be updated.
     * @throws OperationsDBException If an error occurs during the database operation.
     *
     *                               <p>This method updates the personal data of the specified user. It loads the view
     *                               for updating user data and sets the user's information in the controller. If there
     *                               is an error loading the view, it displays an error message.</p>
     */
    @Override
    public void updatePersonalData(Person user) throws OperationsDBException {
        try {
            mainStage.setTitle("Modifica tus datos en solo un minuto!!");
            loadView("/org/example/proyectotfg/update-user-view.fxml");
            UpdatePersonController updatePerson = (UpdatePersonController) actualController;
            updatePerson.chargePerson(person);
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Updates the data of a normal user.
     *
     * @param user The normal user whose data needs to be updated.
     *
     *             <p>This method updates the data of the specified normal user in the database. It
     *             sets the user's information and navigates back to the home screen after successful
     *             update. If there is an error during the update operation, it displays an error message.</p>
     */
    @Override
    public void updateDataPerson(NormalUser user) {
        try {
            connect.updateNormalUserWP(user);
            showInfo("Actualización correcta", "Se ha actualizado correctamente el usuario");
            person = user;
            fromFirstScreenToHome();
        } catch (OperationsDBException | SQLException e) {
            showError("Error en la operaciones", e.getMessage());
        }
    }

    /**
     * Updates the data of a professional user.
     *
     * @param user The professional user whose data needs to be updated.
     *
     *             <p>This method updates the data of the specified professional user in the database. It
     *             sets the user's information and navigates back to the home screen after successful
     *             update. If there is an error during the update operation, it displays an error message.</p>
     */
    @Override
    public void updateDataPerson(ProfessionalUser user) {
        try {
            connect.updateProfesionalUserWP(user);
            showInfo("Actualización correcta", "Se ha actualizado correctamente el usuario");
            person = user;
            fromFirstScreenToHome();
        } catch (OperationsDBException | SQLException e) {
            showError("Error en la operaciones", e.getMessage());
        }
    }

    /**
     * Updates all data of a professional user.
     *
     * @param user The professional user whose data needs to be updated.
     *
     *             <p>This method updates all the data of the specified professional user in the database.
     *             It sets the user's information and navigates to the login screen after successful update.
     *             If there is an error during the update operation, it displays an error message.</p>
     */

    @Override
    public void updateAllDataPerson(ProfessionalUser user) {
        try {
            connect.updateProfesionalUser(user);
            showInfo("Actualización correcta", "Se ha actualizado correctamente el usuario");
            person = user;
            fromFirstScreenToHome();
        } catch (OperationsDBException | SQLException e) {
            showError("Error en la operaciones", e.getMessage());
        }
    }

    /**
     * Updates all data of a normal user.
     *
     * @param user The normal user whose data needs to be updated.
     * @throws SQLException If a database access error occurs.
     *
     *                      <p>This method updates all the data of the specified normal user in the database.
     *                      It sets the user's information and navigates to the home screen after successful update.
     *                      If there is an error during the update operation, it displays an error message.</p>
     */

    @Override
    public void updateAllDataPerson(NormalUser user) throws SQLException {
        try {
            connect.updateDataPerson(user);
            showInfo("Actualización correcta", "Se ha actualizado correctamente");
            person = user;
            fromFirstScreenToHome();
        } catch (OperationsDBException e) {
            showError("Error en la operaciones", e.getMessage());
        }
    }

    /**
     * Navigates from the first screen to the home screen.
     *
     * <p>This method loads the initial interface view and sets the title to "MeetPsych!!".
     * It then loads the initial interface, sets the mediator, and handles any errors that may occur during the process.</p>
     */

    @Override
    public void fromFirstScreenToHome() {
        try {
            loadView("/org/example/proyectotfg/initial-interface-view.fxml");
            mainStage.setTitle("MeetPsych!!");
            loadInitialInterface();
            actualController.setMediator(this);
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Returns to the home screen.
     *
     * <p>This method delegates the task to the {@code fromFirstScreenToHome()} method.</p>
     */

    @Override
    public void returnHome() {
        fromFirstScreenToHome();
    }

    /**
     * Opens the search view with the specified search query.
     *
     * <p>This method searches for professional users based on the provided query and loads the search view if results are found.</p>
     *
     * @param busqueda The search query.
     */

    @Override
    public void openSearch(String busqueda) {
        try {
            List<ProfessionalUser> professionalUsers = connect.searchProfessionalsUsers(busqueda);
            if (!professionalUsers.isEmpty()) {
                loadView("/org/example/proyectotfg/search-view.fxml");
                mainStage.setTitle("¡Encuentra a tu profesional favorito!");
                ControllerSearch controllerSearch = (ControllerSearch) actualController;
                controllerSearch.setPerson(person);
                controllerSearch.loadSearchs(professionalUsers);
            }
        } catch (ThereIsNoView | NonexistingUser | DataAccessException | OperationsDBException e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Opens the professional user information view.
     *
     * <p>This method loads the professional user information view with the details of the specified professional user.</p>
     *
     * @param professionalUser The professional user for whom the information is displayed.
     * @param index            The index of the professional user.
     */

    @Override
    public void openProfessionalUser(ProfessionalUser professionalUser, int index) {
        try {
            loadView("/org/example/proyectotfg/info-profesional-user-view.fxml");
            mainStage.setTitle("¡Cuenta con la ayuda más profesional!");
            InfoProfesionalController infoProfesionalController = (InfoProfesionalController) actualController;
            infoProfesionalController.setElementsPerson(professionalUser, index);
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

  /*   ================================================================================================
        ======================================Hacer Post =====================================================*/

    /**
     * Creates a new post.
     *
     * <p>This method creates a new post and saves it to the database.</p>
     *
     * @param newPost The new post to be created.
     */

    @Override
    public void makePost(Post newPost) {
        try {
            connect.makeNewPost(newPost);
            showInfo("Post publicado", "Post publicado correctamente");
            loadInitialInterface();
        } catch (OperationsDBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays a list of posts.
     *
     * <p>This method generates a graphical view to display a list of posts.</p>
     *
     * @param posts The list of posts to be displayed.
     * @return A graphical representation of the list of posts.
     */
    @Override
    public Parent viewPost(List<Post> posts) {
        Parent parent = new VBox();
        ((VBox) parent).setPrefWidth(335);
        ((VBox) parent).setStyle("-fx-padding: 5;");
        try {
            for (Post post : posts) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proyectotfg/fragment-post_view.fxml"));
                Node fragment = fxmlLoader.load();
                FragmentPostController controller = fxmlLoader.getController();
                controller.setData(String.valueOf(post.getTitle()), String.valueOf(post.getTitular().getNames()), String.valueOf(post.getContent()), "/org/example/proyectotfg/imgPost/meditacion.jpg");
                ((VBox) parent).getChildren().add(fragment);
            }
        } catch (IOException | NotFoundImage e) {
            showError("Error ", e.getMessage());
        }
        return parent;
    }

    /**
     * Loads the public post view.
     *
     * <p>This method loads the public post view, allowing users to create new posts.</p>
     *
     * @throws ThereIsNoView If the view cannot be loaded.
     */
    private void loadPublicPostView() throws ThereIsNoView {
        mainStage.setTitle("¡Estamos construyendo nuevos espacios para ti!");
        loadView("/org/example/proyectotfg/post-generator-view.fxml");

        PostGeneratorController postGeneratorController = (PostGeneratorController) actualController;
        postGeneratorController.setPerson(person);
        postGeneratorController.setTitlePost(person.getNames());
    }

    /*   ================================================================================================
        ====================================== Log out =====================================================*/

    /**
     * Logs out the current user.
     *
     * <p>This method clears the current appointment and person information and returns the user to the login screen.</p>
     */
    @Override
    public void logOut() {
        actualAppointment = null;
        person = null;
        volverIncio();
    }
    /*   ================================================================================================
        ======================================show errors =====================================================*/

    /**
     * Displays an error message dialog.
     *
     * @param titleWindow the title of the error window
     * @param message     the error message to display
     */

    public static void showError(String titleWindow, String message) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        showMessage(titleWindow, message, alerta);
    }

    /**
     * Displays an information message dialog.
     *
     * @param titleWindow the title of the information window
     * @param message     the information message to display
     */

    public static void showInfo(String titleWindow, String message) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        showMessage(titleWindow, message, alerta);
    }

    /**
     * Displays a message with a specified title and content in a given alert dialog.
     *
     * @param titleWindow the title of the alert window
     * @param message     the message content to display
     * @param alert       the Alert object used to display the message
     */

    private static void showMessage(String titleWindow, String message, Alert alert) {
        alert.setHeaderText(titleWindow);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Navigates back to the home screen.
     */

    @Override
    public void backToHome() {
        fromFirstScreenToHome();
    }


}
