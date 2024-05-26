package org.example.proyectotfg.controllers;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.example.proyectotfg.DAO.SqliteConnector;
import org.example.proyectotfg.MainApplication;
import org.example.proyectotfg.entities.*;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.exceptions.*;
import org.example.proyectotfg.functions.FunctionsApp;
import org.example.proyectotfg.functions.SenderReaderMail;
import org.example.proyectotfg.mediators.*;

//import javax.mail.MessagingException;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController implements Mediator, MediatorAcceso, MediatorProfile, MediatorFirstScreen, MediatorPost, MediatorConstruction, MediatorNotifiers {
    //mongo, sql, sqlite
    private Stage mainStage;
    private Mediator mediatorAplicado;
    SqliteConnector connect;
    ViewController actualController;
    Person person;
    String passWordApp = "j v g l r d n k f x kw m s b e";

    public MainController(Stage mainStage) {
        try {
            connect = new SqliteConnector();
            this.mainStage = mainStage;
            setMediator(this);
            loadFirstView();
        } catch (SQLException e) {
            showError("Error", e.getMessage());
        }
    }

    public void setMediator(Mediator mediator) {
        this.mediatorAplicado = mediator;
    }

    /*   ================================================================================================
        ======================================vistas principales =====================================================*/

    public void loadFirstView() {
        try {
            // FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("entradaView.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("entradaView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            actualController = fxmlLoader.getController();
            actualController.setMainController(this);
            ViewController controller = fxmlLoader.getController();
            controller.setMediator(this);
            mainStage.setTitle("MeetPshyc!");
            mainStage.setScene(scene);
            mainStage.show();

        } catch (IOException e) {
            showError("Error", "Error al cargar la aplicación");
        }
    }

    /*   ================================================================================================
       ====================================CARGAR TODOS LOS SCENE ==============================*/
    private void loadInterfazInicial() {
        try {
            loadView("/org/example/proyectotfg/interfaz-inicial-view.fxml");
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
            //loadView("entradaView.fxml");
            loadView("/org/example/proyectotfg/login-general.fxml");
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

    @Override
    public void haciaAtras() {
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
    public void recoverPassword() {
        try {
            mainStage.setTitle("Recupere su contraseña!!");
            loadView("/org/example/proyectotfg/recover-password.fxml");
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

    public void loadPostView() {
        try {
            loadView("/org/example/proyectotfg/post-view.fxml");
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
            loadView("/org/example/proyectotfg/login-general.fxml");
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
                 OperationsDBException e) {
            showError("Error", e.getMessage());
        }
    }

    /*   ================================================================================================
        ======================================Firs screen =====================================================*/
    @Override
    public Parent initializeServicios(HashMap<String, String> servicios) {
        HBox contenedorHBox = new HBox(4);
        contenedorHBox.setAlignment(Pos.CENTER);
        contenedorHBox.setMaxWidth(80);
        contenedorHBox.setMaxHeight(90);
        try {
            for (Map.Entry<String, String> map : servicios.entrySet()) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proyectotfg/fragment-servicios.fxml"));
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
                                    loadView("/org/example/proyectotfg/enContruccion-view.fxml");
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
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return contenedorHBox;
    }

  /*   ================================================================================================
        ======================================Search View=====================================================*/


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
            contenedorHBox2.setMaxWidth(100);
            contenedorHBox2.setMaxHeight(130);
            try {
                int imageIndex = 1; // Inicializa el índice de la imagen
                int totalImages = 6; // Total de imágenes disponibles
                for (ProfessionalUser us : professionalUsers) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proyectotfg/fragment-servicios.fxml"));
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
            } catch (IOException ioe) {
                throw new RuntimeException("Error cargando el fragmento de servicio", ioe);
            }
        }

        // En caso de que professionalUsers sea null, devolver un contenedor vacío o manejar el caso adecuadamente
        return contenedorHBox2;
    }

    /*   ================================================================================================
       ======================================Appointment manager=====================================================*/
    @Override
    public void openCalendarView() {
        try {
            mainStage.setTitle("Te esperamos pronto!!");
            loadView("/org/example/proyectotfg/appointmentManegementView.fxml");
            AppointmentManegemenController appointmentManegemenController = (AppointmentManegemenController) actualController;
            appointmentManegemenController.setPerson(person);
            appointmentManegemenController.setTitlePost(person.getNames());
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

    @Override
    public void volver() {
        regresar();
    }

    @Override
    public void deleteAppointment(MedicalAppointment medicalAppointment) {

    }

    @Override
    public void addAppointment(MedicalAppointment medicalAppointment) {

    }

    @Override
    public void editAppointment(MedicalAppointment medicalAppointment) {

    }

    /*   ================================================================================================
      ====================================== Update Data =====================================================*/
    @Override
    public void updatePersonalData(Person user) throws OperationsDBException {
        try {
            mainStage.setTitle("Modifica tus datos en solo un minuto!!");
            loadView("/org/example/proyectotfg/update-user.fxml");
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
            regresar();
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
            regresar();
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
            regresar();
        } catch (OperationsDBException | SQLException e) {
            showError("Error en la operaciones", e.getMessage());
        }
    }

    @Override
    public void updateAllDataPerson(NormalUser user) throws SQLException {
        try {
            connect.updateDataPerson(user);
            showInfo("Actualización correcta", "Se ha actualizado correctamente el usuario");
            person = user;
            regresar();
        } catch (OperationsDBException e) {
            showError("Error en la operaciones", e.getMessage());
        }
    }

    @Override
    public void datosPerfilPsico(TextFlow descripcionCurriculum, Text nombreDoctor, ImageView imgmed, ProfessionalUser usuarioPerfilesCarga) {

    }

    @Override
    public void regresar() {
        try {
            loadView("/org/example/proyectotfg/interfaz-inicial-view.fxml");
            loadInterfazInicial();
            actualController.setMediator(this);
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

    @Override
    public void returnHome() {
        regresar();
    }

    @Override
    public void openSearch(String busqueda) {
        try {
            List<ProfessionalUser> professionalUsers = connect.searchProfessionalsUsers(busqueda);
            if (!professionalUsers.isEmpty()) {
                loadView("/org/example/proyectotfg/search-view.fxml");
                ControllerSearch controllerSearch = (ControllerSearch) actualController;
                controllerSearch.setPerson(person);
                controllerSearch.setStringSearch(busqueda);
                controllerSearch.loadSearchs(professionalUsers);
            }
        } catch (ThereIsNoView | NonexistingUser | DataAccessException | OperationsDBException e) {
            showError("Error", e.getMessage());
        }
    }

    @Override
    public void openProfessionalUser(ProfessionalUser professionalUser, int index) {
        try {
            loadView("/org/example/proyectotfg/view-info-profesionalUser.fxml");
            InfoProfesionalController infoProfesionalController = (InfoProfesionalController) actualController;
            infoProfesionalController.setElementsPerson(professionalUser, index);
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

  /*   ================================================================================================
        ======================================Hacer Post =====================================================*/

    @Override
    public void makePost(Post nuevo) {
        try {
            connect.makeNewPost(nuevo);
            loadInterfazInicial();
        } catch (OperationsDBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Parent viewPost(List<Post> posts) {
        Parent parent = new VBox();
        ((VBox) parent).setPrefWidth(360);
        ((VBox) parent).setStyle("-fx-padding: 5;");
        try {
            for (Post post : posts) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proyectotfg/fragment-post_view.fxml"));
                Node fragment = fxmlLoader.load();
                FragmentPostController controller = fxmlLoader.getController();
                controller.setData(String.valueOf(post.getTitle()), String.valueOf(post.getTitular().getNames()), String.valueOf(post.getContent()), "/org/example/proyectotfg/imgPost/meditacion.jpg");
                ((VBox) parent).getChildren().add(fragment);
            }
        } catch (IOException e) {
            throw new RuntimeException(new ThereIsNoView("Error a la hora de cargar el fragmento: " + e.getMessage()));
        }

        return parent;
    }

    private void loadPublicPostView() throws ThereIsNoView {
        loadView("/org/example/proyectotfg/post-generator-view.fxml");
        PostGeneratorController postGeneratorController = (PostGeneratorController) actualController;
        postGeneratorController.setPerson(person);
        postGeneratorController.setTitlePost(person.getNames());
    }


    /*   ================================================================================================
        ======================================show errors =====================================================*/

    public void showError(String titleWindow, String menssage) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        showMessage(titleWindow, menssage, alerta);
    }

    public void showInfo(String titleWindow, String menssage) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        showMessage(titleWindow, menssage, alerta);
    }

    private void showMessage(String titleWindow, String menssage, Alert alerta) {
        alerta.setHeaderText(titleWindow);
        alerta.setContentText(menssage);
        alerta.showAndWait();
    }


    @Override
    public void backToHome() {
        regresar();
    }


}
