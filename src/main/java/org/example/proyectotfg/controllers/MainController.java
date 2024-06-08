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
import javafx.util.Duration;
import org.example.proyectotfg.DAO.SqliteConnector;
import org.example.proyectotfg.entities.*;
import org.example.proyectotfg.enumAndTypes.Notificators;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.exceptions.*;
//import org.example.proyectotfg.functions.FirebaseInitializer;
import org.example.proyectotfg.functions.FunctionsApp;
import org.example.proyectotfg.functions.SenderReaderMail;
import org.example.proyectotfg.mediators.*;

//import javax.mail.MessagingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


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
    public MainController(Stage mainStage) throws IOException {
        try {
            connect = new SqliteConnector();
            this.mainStage = mainStage;
            loadFirstView();
        } catch (SQLException | OperationsDBException e) {
            showError("Error", e.getMessage());
        }
    }
    /*   ================================================================================================
        ======================================vistas principales =====================================================*/

    public void loadFirstView() {
        try {
            loadView("/org/example/proyectotfg/entry-view.fxml");
            mainStage.setTitle("MeetPshyc!");
        } catch (ThereIsNoView e) {
            showError("Error", "Error al cargar la aplicación");
        }
    }

    /*   ================================================================================================
       ====================================CARGAR TODOS LOS SCENE ==============================*/
    private void loadInterfazInicial() {
        try {
            loadView("/org/example/proyectotfg/initial-interface-view.fxml");
            InitialInterfaceController initialInterfaceController = (InitialInterfaceController) actualController;
            initialInterfaceController.loadServices();
            initialInterfaceController.setTextWelcome(person.getNames());
            initialInterfaceController.setUser(person);
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }

    }

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

    @Override
    public void loadRecoverPassword() {
        try {
            mainStage.setTitle("Recupere su contraseña!!");
            loadView("/org/example/proyectotfg/recover-password-view.fxml");
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

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

    public void loadPostView() {
        try {
            loadView("/org/example/proyectotfg/post-view.fxml");
            mainStage.setTitle("¡Estos son los posts que has compartido!");
            ControllerPost controllerPost = (ControllerPost) actualController;
            controllerPost.setPerson(person);
            controllerPost.setPosts(SqliteConnector.serchPostByPerson(person));
            controllerPost.loadPosts();
        } catch (ThereIsNoView | IncorrectDataException | NullArgumentException | OperationsDBException e) {
            showError("Error", e.getMessage());
        }

    }

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

    private void enviarEmail(SenderReaderMail sender, String user, String mensaje) {
        try {
            sender.enviarMensajeHTML("meetpsychproject@gmail.com", user, "Verificación de Cuenta", mensaje, "meetpsychproject@gmail.com", passWordApp);
        } catch (Exception e) {
            showError("Error", "Error al enviar el mensaje: " + e.getMessage());
        }
    }

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

    @Override
    public void loginUser(String user, String pass, TypeUser typeuser) {
        try {
            Person person = connect.loginUser(user, pass, typeuser);
            if (person != null) {
                this.person = person;
                mainStage.setTitle("Bienvenidos ");
                loadInterfazInicial();
                actualController.setMediator(this);
            }
        } catch (InvalidKeySpecException | NonexistingUser | IncorrectLoginEception | DataAccessException |
                 OperationsDBException | NoSuchAlgorithmException e) {
            showError("Error", e.getMessage());
        }
    }

    /*   ================================================================================================
        ======================================Firs screen =====================================================*/
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

    @Override
    public void addToFavorites(ProfessionalUser professionalUser, Person person) throws OperationsDBException {
        boolean addto = connect.addProfesionalUserInFavorites(professionalUser, person);
        if (!addto) {
            //TODO: Que mostrar??
        }
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

    @Override
    public Parent loadProfessionalsInMediatorCalendar() {
        HBox contenedorHBox2 = new HBox(6);
        try {
            List<ProfessionalUser> professionalUsers = SqliteConnector.getProfesionales();
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

                //cambiar este callback para que el metodo permita acceder junto con la fecha, a la agenda del profesional
                controller.setCallback(() -> {
                    //como lo puedo pasar al callback
                    AppointmentManegemenController appointmentManegemenController = (AppointmentManegemenController) actualController;
                    appointmentManegemenController.setProfessionalUser(us);
                    LocalDate localDate = appointmentManegemenController.getDatePicker().getValue();
                    //llama al metodo que verifica las citas??
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

    @Override
    public Parent loadNotAvailableAppointmentsInCalendar(List<MedicalAppointment> medicalAppointments) {
        HBox contenedorHBox2 = new HBox(6);
        return contenedorHBox2;
    }

    @Override
    public Parent myNextAppoinments() {
        HBox contenedorHBox2 = new HBox(6);

        try {
            List<MedicalAppointment> medicalAppointments = connect.searchMyAppointments(person);

            for (MedicalAppointment medicalApp : medicalAppointments) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proyectotfg/fragment-appointment-hours-view.fxml"));
                Node fragment = fxmlLoader.load();
                ControllerFragmentApoinmentHours controller = fxmlLoader.getController();
                //cambiar este callback para que el metodo permita acceder junto con la fecha, a la agenda del profesional
                Calendar calendar = Calendar.getInstance();
                Date date = medicalApp.getVisitDate();
                calendar.setTime(date);
                int hours = calendar.get(Calendar.HOUR_OF_DAY); // Hora en formato 24 horas
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
                    controllerAppointment.setTextAppointment("Cita seleccionada: Psicologo \"" + medicalApp.getPsicologo().getNames() + "\" el dia: " + stringDate + " a las " + stringHours + ":" + stringMinutes);
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
                                System.out.println("Ejecutando hilo");

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

                           /* int segundosActualesPrevios = cancionActual.getSegundosActuales();
                            int segundosActuales = controladorVistaCancion.recogerValorProgreso();
                            if (segundosActualesPrevios != segundosActuales) {
                                player.stop();
                                player.setStartTime(Duration.seconds(segundosActuales));
                                player.play();
                            }
                            cancionActual.setSegundosActuales(segundosActuales + 1);

                            //RUN LATER!! para repintar la pantalla
                          *//*  Platform.runLater(() -> {
                                if (cancionActual != null) {
                                    controladorVistaCancion.establecerDuracion(cancionActual.getSegundosActuales(), cancionActual.getDuracion());
                                }


                            });*//*
                            if (cancionActual.getSegundosActuales() == cancionActual.getDuracion()) {
                                timerTask.cancel();
                                timer.cancel();
                            }*/
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

    @Override
    public void backFromNotifiersToHome() {
        fromFirstScreenToHome();
    }

    @Override
    public void deleteAppointment(MedicalAppointment medicalAppointment) {
        try {
            if (connect.deleteMedicalAppointments(medicalAppointment.getIdCita(), person.getIdPerson(), medicalAppointment.getVisitDate())) {
                editedAppointment = null;
            } else {
                throw new OperationsDBException("No se ha podido eliminar");
            }
        } catch (OperationsDBException e) {
            showError("Error", e.getMessage());
        }
        openAppointmentView();
    }

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

    //crear arraylist de citas disponibles
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
                        //todo: revisa
                    }
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return availableAppointments;
    }


    /*   ================================================================================================
      ====================================== Update Data =====================================================*/
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

    @Override
    public void updateAllDataPerson(ProfessionalUser user) {
        try {
            connect.updateProfesionalUser(user);
            showInfo("Actualización correcta", "Se ha actualizado correctamente el usuario");
            person = user;
            openLogin();
        } catch (OperationsDBException | SQLException e) {
            showError("Error en la operaciones", e.getMessage());
        }
    }

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


    @Override
    public void fromFirstScreenToHome() {
        try {
            loadView("/org/example/proyectotfg/initial-interface-view.fxml");
            mainStage.setTitle("MeetPsych!!");
            loadInterfazInicial();
            actualController.setMediator(this);
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

    @Override
    public void returnHome() {
        fromFirstScreenToHome();
    }

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

    @Override
    public void makePost(Post newPost) {
        try {
            connect.makeNewPost(newPost);
            loadInterfazInicial();
        } catch (OperationsDBException e) {
            throw new RuntimeException(e);
        }
    }

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

    private void loadPublicPostView() throws ThereIsNoView {
        mainStage.setTitle("¡Estamos construyendo nuevos espacios para ti!");
        loadView("/org/example/proyectotfg/post-generator-view.fxml");

        PostGeneratorController postGeneratorController = (PostGeneratorController) actualController;
        postGeneratorController.setPerson(person);
        postGeneratorController.setTitlePost(person.getNames());
    }

    /*   ================================================================================================
        ====================================== Log out =====================================================*/
    @Override
    public void logOut() {
        actualAppointment = null;
        person = null;
        volverIncio();
    }
    /*   ================================================================================================
        ======================================show errors =====================================================*/

    public static void showError(String titleWindow, String menssage) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        showMessage(titleWindow, menssage, alerta);
    }

    public static void showInfo(String titleWindow, String menssage) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        showMessage(titleWindow, menssage, alerta);
    }

    private static void showMessage(String titleWindow, String menssage, Alert alerta) {
        alerta.setHeaderText(titleWindow);
        alerta.setContentText(menssage);
        alerta.showAndWait();
    }


    @Override
    public void backToHome() {
        fromFirstScreenToHome();
    }


}
