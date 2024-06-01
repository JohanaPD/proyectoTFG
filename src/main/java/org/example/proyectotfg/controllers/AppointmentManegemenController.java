package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.example.proyectotfg.DAO.SqliteConnector;
import org.example.proyectotfg.entities.MedicalAppointment;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.*;
import org.example.proyectotfg.functions.FunctionsApp;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorNotifiers;
import org.example.proyectotfg.mediators.ViewController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class AppointmentManegemenController implements ViewController {

    @FXML
    private Label username;

    @FXML
    private AnchorPane appointmentsList;

    @FXML
    private AnchorPane availableAppointmentsList;

    @FXML
    private DatePicker datePicker;

    @FXML
    private AnchorPane myAppointments;

    @FXML
    private ScrollPane professionalsList;

    private MediatorNotifiers mediatorNotifiers;
    private Person person;
    java.util.Date[] times;

    {
        try {
            times = FunctionsApp.fillArray(6);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
      //  this.datePicker = datePicker;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setTitlePost(String names) {
        username.setText(names);
    }

    public void loadProfessionals() {
        Parent professionalUserBox = mediatorNotifiers.loadProfessionalsInMediatorCalendar();
        professionalsList.setContent(professionalUserBox);
    }

    public void loadAvailableAppointments(List<MedicalAppointment> medicalAppointments) {
        Parent availableAppointments=mediatorNotifiers.loadAvailableAppointmentsInCalendar(medicalAppointments);
        availableAppointmentsList.getChildren().add(availableAppointments);
    }
   /* private HBox loadProfessionalsInMediatorCalendar() {
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
                    LocalDate localDate = datePicker.getValue();
                    if (localDate == null) {
                        ((MainController) mediatorNotifiers).showError("Error", "Tienes que seleccionar una" +
                                " fecha antes de continuar);");
                    } else {
                        Date date = Da.valueOf(localDate);
                        mediatorNotifiers.searchAppointments(us.getIdPerson(), date);
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
            ((MainController) mediatorNotifiers).showError("Error ", ioe.getMessage());
        }
        return contenedorHBox2;
    }*/
     void chargeInInterfazCites(List<MedicalAppointment> list){
        if(list.size()>=6){
            ((MainController) mediatorNotifiers).showInfo("Error ", "No quedan citas disponibles para la fecha elegida");
        } else if (list.isEmpty()) {
            //todo: pinta todos los dias
        } else if (list.size()>0 && list.size()<=5) {
            for (int i = 0; i < list.size() ; i++) {
                if(list.get(i)!=null){
                SimpleDateFormat format= new SimpleDateFormat("HH:mm:ss");
                String hora= format.format(list.get(i).getVisitDate().getTime());
               if( !hora.equals(times)){
                   //todo: pinta las horas disponibles
               }
                }else{
                    //todo: revisa
                }
            }




        }
     };

    @Override
    public void setMediator(Mediator mediador) {
        this.mediatorNotifiers = (MediatorNotifiers) mediador;
    }

    @FXML
    void deleteAppoinment(ActionEvent event) {
    }

    @FXML
    void editAppoinment(ActionEvent event) {

    }

    @FXML
    void goToHome(ActionEvent event) {
        mediatorNotifiers.backFromNotifiersToHome();
    }


}


