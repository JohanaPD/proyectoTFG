package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.ThereIsNoView;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorFirstScreen;
import org.example.proyectotfg.mediators.ViewController;

import javax.swing.text.View;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ControllerSearch implements ViewController {
    MediatorFirstScreen mediator;
    @FXML
    private Label detalleBusqueda;
    @FXML
    private VBox listaResultados;
    private Person person;

    public void initialize() {

    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public void setMediator(Mediator mediador) {
        mediator = (MediatorFirstScreen) mediador;
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }

    public void loadSearchs(List<ProfessionalUser> professionalUsers) throws ThereIsNoView {
        try {
            for (ProfessionalUser us : professionalUsers) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/proyectotfg/fragment-infoSearch_view.fxml"));
                Node fragment = fxmlLoader.load();
                FragmentInfoSerchController controller = fxmlLoader.getController();
                controller.setProfessionalUser(us);
                controller.setPerson(person);
                controller.setData(String.valueOf(us.getNames()), String.valueOf(us.getSpecialty()), "/org/example/proyectotfg/imgUsuario/doctor3.png", 5);
                fragment.resize(340, 180);
                listaResultados.getChildren().add(fragment);
            }

        } catch (IOException e) {
            throw new ThereIsNoView("Error a la hora de cargar el fragmento: " + e.getMessage());
        }
    }

    public void setStringSearch(String search) {
        detalleBusqueda.setText("Resultados de busqueda [" + search + "]");
    }

    public void volverIncio(ActionEvent actionEvent) {
        mediator.regresar();
    }
}

/*
*
*
* package com.ies.appmeetpshyc.meetphsyc;

import com.ies.appmeetpshyc.meetphsyc.Mediator.MediadorPrimerPantallaContrller;
import com.ies.appmeetpshyc.meetphsyc.Mediator.Mediator;
import com.ies.appmeetpshyc.meetphsyc.Mediator.ViewController;
import com.ies.appmeetpshyc.meetphsyc.excepciones.DataAccessException;
import com.ies.appmeetpshyc.meetphsyc.excepciones.NonexistingUser;
import com.ies.appmeetpshyc.meetphsyc.excepciones.ThereIsNoView;
import com.ies.appmeetpshyc.meetphsyc.model.UsuarioEspecifico;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerSearch implements Serializable, ViewController, Initializable {
    @FXML
    private VBox contenedorResultados;

    @FXML
    private Label detalleBusqueda;

    @FXML
    private VBox listaResultados;

    @FXML
    private VBox mainContainer;

    @FXML
    private ScrollPane resultSearch;
    MediadorPrimerPantallaContrller mediador;
    private String busqueda;

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    @Override
    public void setMediator(Mediator mediador) {
        this.mediador = (MediadorPrimerPantallaContrller) mediador;
    }


        public List<UsuarioEspecifico> cargarUsuarioProfesional(String titulo) throws NonexistingUser, DataAccessException {
        List<UsuarioEspecifico> psicologosEspecialistas = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite")) {
            String sql = "SELECT u.nombre, h.especialidad, h.presentacion, h.urlImagen FROM HISTORIALES h, USUARIOS u WHERE u.nombre LIKE ? OR h.especialidad LIKE ?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "%" + titulo + "%");
                statement.setString(2, "%" + titulo + "%");
                try (ResultSet resultSet = statement.executeQuery()) {
                    boolean found = false;
                    while (resultSet.next()) {
                        found = true;
                        UsuarioEspecifico nuevo = new UsuarioEspecifico();
                        nuevo.setFullName(resultSet.getString("nombre"));
                        nuevo.setFicheros(resultSet.getString("urlImagen"));
                        nuevo.setEspecialidad(resultSet.getString("especialidad"));
                        nuevo.setContenido(resultSet.getString("presentacion"));

                        if (psicologosEspecialistas != null && !psicologosEspecialistas.contains(nuevo.getFullName())) {
                            psicologosEspecialistas.add(nuevo);
                        }
                    }
                    if (!found) {
                        throw new NonexistingUser("No se encontraron usuarios con el criterio: " + titulo);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al acceder a la base de datos: " + e.getMessage());
        }
        return psicologosEspecialistas;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<UsuarioEspecifico> usuariosEspecificos = new ArrayList<>();
        try {
            usuariosEspecificos = cargarUsuarioPsicologo("Neuropsicología");
        } catch (NonexistingUser e) {
            System.out.println(e);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        VBox contenedorVBox = new VBox(4);
           try {
            for (UsuarioEspecifico us : usuariosEspecificos) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fragment-info-serch.fxml"));
                Node fragment = fxmlLoader.load();
                FragmentInfoSerchController controller = fxmlLoader.getController();
                controller.setData(String.valueOf(us.getFullName()), String.valueOf(us.getEspecialidad()), String.valueOf(us.getFicheros()), 5);
                contenedorVBox.getChildren().add(fragment);
            }
            resultSearch.setContent(contenedorVBox);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void realizarBusquedaAsincrona(String textoDeBusqueda) {
    //     new Thread(() -> {
            try {
                // Lógica de búsqueda
                List<UsuarioEspecifico> resultados = cargarUsuarioPsicologo(textoDeBusqueda);
                Platform.runLater(() -> {
                    listaResultados.getChildren().clear();
                    for (UsuarioEspecifico us : resultados) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fragment-info-serch.fxml"));
                            Node fragment = fxmlLoader.load();
                            FragmentInfoSerchController controller = fxmlLoader.getController();
                            controller.setData(String.valueOf(us.getFullName()),
                                    String.valueOf(us.getEspecialidad()),
                                    String.valueOf(us.getFicheros()),
                                    5);
                            listaResultados.getChildren().add(fragment);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    mostrarErrorEnUI(e);
                });
            } catch (NonexistingUser e) {
                throw new RuntimeException(e);
            }
     //   }).start();
    }

    private void mostrarErrorEnUI(Exception e) {
        e.printStackTrace();
    }


    public void volverIncio(ActionEvent actionEvent) throws ThereIsNoView {
        mediador.regresar();
    }
}

*/
