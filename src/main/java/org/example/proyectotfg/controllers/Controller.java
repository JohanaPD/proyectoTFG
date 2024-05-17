package org.example.proyectotfg.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.proyectotfg.MainApplication;
import org.example.proyectotfg.mediators.ViewController;

import javax.swing.text.View;
import java.io.IOException;
import java.util.*;

public class Controller {
    private List<Stage> cargaStage;
    private EntryController entryController;
    private LoginController loginContrller;
    private Map<Scene, ViewController> controllers= new HashMap<>();

    public Map<Scene, ViewController> getControllers() {
        return controllers;
    }

    public void setControllers() {
        try {
            List<String> views = new ArrayList<>();
            views.add("entradaView.fxml");
            views.add("registro-view.fxml");
            views.add("login-general.fxml");
            views.add("initial-interface.fxml");
            views.add("fragment-psicologos.fxml");
            views.add("fragment-servicios.fxml");

            for (String view : views) {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(view));
                Scene scene = fxmlLoader.load();
                ViewController viewController = fxmlLoader.getController();
                controllers.put(scene, viewController);
            }
        } catch (IOException E) {

        }
        }

    public void setControllers(FXMLLoader fxmlLoader, ViewController controller) {
        this.controllers = controllers;
    }

    public List<Stage> getCargaStage() {
        return cargaStage;
    }

    public void setCargaStage(List<Stage> cargaStage) {
        this.cargaStage = cargaStage;
    }


    //metodo que retorne scene del controller
    public Stage returnStage(ViewController controller) {
        Stage view= (Stage) controllers.get(controller);
        return view;
    }


    //metodo que devuelva el controller

    public ViewController returnStage(Stage stage) {
        ViewController controller= (ViewController) controllers.get(stage);
        return controller;
    }

}
