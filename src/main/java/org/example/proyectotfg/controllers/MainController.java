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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.example.proyectotfg.DAO.SqliteConnector;
import org.example.proyectotfg.MainApplication;
import org.example.proyectotfg.entities.NormalUser;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
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

public class MainController implements Mediator, MediatorAcceso, MediatorProfile, MediatorFirstScreen {
    //mongo, sql, sqlite
    private Stage mainStage;
    private Mediator mediatorAplicado;
    SqliteConnector connect;
    ViewController controllerActual;
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
            System.out.println("Desde el main  " + fxmlLoader.getLocation());
            Scene scene = new Scene(fxmlLoader.load());
            controllerActual = fxmlLoader.getController();
            controllerActual.setMainController(this);
            ViewController controller = fxmlLoader.getController();
            controller.setMediator(this);
            mainStage.setTitle("MeetPsych!");
            mainStage.setScene(scene);
            //controllerPrincipal.setControllers(fxmlLoader, controller);

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
            InterfazIncialController interfazIncialController = (InterfazIncialController) controllerActual;
            interfazIncialController.loadServices();
            interfazIncialController.setTextWelcome(person.getNames());
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }

    }

    private void loadView(String s) throws ThereIsNoView {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
            System.out.println("Desde el maincontroller " + loader.getLocation());
            Parent root = loader.load();
            controllerActual = loader.getController();
            controllerActual.setMediator(this);
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Esto imprimirá el error específico en la consola
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

    @Override
    public void makeRecordRegister(ProfessionalUser user) {
        String mensaje = FunctionsApp.devolverStringMail(user);
        SenderReaderMail sender = new SenderReaderMail();
        try {
            sender.enviarMensajeTexto("meetpsychproject@gmail.com", user.getNames(),
                    "Verificación de Cuenta", mensaje, user.getEmail(), passWordApp);
        } catch (Exception e) {
            showError("Error", "Error al enviar el mensaje: " + e.getMessage());
        }
        try {
            connect.registerProfessionalUser(user);
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
        try {
            sender.enviarMensajeTexto("meetpsychproject@gmail.com", user.getEmail(),
                    "Verificación de Cuenta", mensaje, "meetpsychproject@gmail.com", passWordApp);
        } catch (Exception e) {
            showError("Error", "Error al enviar el mensaje: " + e.getMessage());
        }
        try {
            connect.registerNormalUser(user);
            showInfo("Registro correcto", "Se ha registrado correctamente el usuario");
            openLogin();
        } catch (DuplicateKeyException | OperationsDBException e) {
            showError("Error", e.getMessage());
        }
        person = user;
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

    void updateLastActivity(Person person) {
        //este metodo debe llamar a la DAO y registrar en la tabla del usuario que se ha conectado, la ultima actividad

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
                controllerActual.setMediator(this);
            }
        } catch (InvalidKeySpecException | NonexistingUser | IncorrectLoginEception |
                 DataAccessException | OperationsDBException e) {
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
                //ojo con el mediator
                //controller.setMediador(this);
                controller.setData(String.valueOf(map.getKey()), String.valueOf(map.getValue()));
                contenedorHBox.getChildren().add(fragment);
            }
            // Añade el HBox al contenedor
            AnchorPane.setTopAnchor(contenedorHBox, 0.0);
            AnchorPane.setRightAnchor(contenedorHBox, 0.0);
            AnchorPane.setBottomAnchor(contenedorHBox, 0.0);
            AnchorPane.setLeftAnchor(contenedorHBox, 0.0);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return contenedorHBox;
    }

    @Override
    public Parent initializeProfessionals(List<ProfessionalUser> professionalUsers) throws NonexistingUser {
        HBox contenedorHBox2 = new HBox(6);
        if (professionalUsers != null) {
            contenedorHBox2.setAlignment(Pos.CENTER);
            contenedorHBox2.setMaxWidth(100);
            contenedorHBox2.setMaxHeight(130);
            try {
                for (ProfessionalUser us : professionalUsers) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proyectotfg/fragment-servicios.fxml"));
                    Node fragment = fxmlLoader.load();
                    ControllerFragmentServicios controller = fxmlLoader.getController();
                    controller.setData(String.valueOf(us.getNames()), String.valueOf(us.getSpeciality()));
                    contenedorHBox2.getChildren().add(fragment);
                }

                AnchorPane.setTopAnchor(contenedorHBox2, 0.0);
                AnchorPane.setRightAnchor(contenedorHBox2, 0.0);
                AnchorPane.setBottomAnchor(contenedorHBox2, 0.0);
                AnchorPane.setLeftAnchor(contenedorHBox2, 0.0);

            } catch (IOException ioe) {
                throw  new NonexistingUser("Lista vacía");
            }
        }

        return contenedorHBox2;
    }

    @Override
    public void openCalendarView() {
        try {
            mainStage.setTitle("Tu calendario de citas!!");
            loadView("/org/example/proyectotfg/appointmentNotifiers/jfx-calendar-view.fxml");
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

    @Override
    public void datosPerfilPsico(TextFlow descripcionCurriculum, Text nombreDoctor, ImageView imgmed, ProfessionalUser usuarioPerfilesCarga) {

    }

    @Override
    public void regresar() {
        try {
            loadView("");
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

    @Override
    public void abrirBusqueda(String busqueda) {
        try {
            loadView("");
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
    }

    @Override
    public void cargarVistaPSicologo() {
        try {
            loadView("");
        } catch (ThereIsNoView e) {
            showError("Error", e.getMessage());
        }
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

    private static void showMessage(String titleWindow, String menssage, Alert alerta) {
        alerta.setHeaderText(titleWindow);
        alerta.setContentText(menssage);
        alerta.showAndWait();
    }


}
