package org.example.proyectotfg.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.example.proyectotfg.exceptions.ThereIsNoView;
import org.example.proyectotfg.mediators.Callback;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorFirstScreen;
import org.example.proyectotfg.mediators.ViewController;

import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class ControllerFragmentServicios{

    @FXML
    private ImageView imgService;

    @FXML
    private Text titleService;
    private Callback callback;

    List<String> serviciosArray = new ArrayList<>();



    public void setCallback(Callback callback) {

        this.callback = (Callback) callback;

    }


    public ImageView getImgService() {
        return imgService;
    }

    public void setImgService(ImageView imgService) {
        this.imgService = imgService;
    }

    public Text getTitleService() {
        return titleService;
    }

    public void setTitleService(Text titleService) {
        this.titleService = titleService;
    }

    public void setData(String title, String imagePath) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        if (image != null) {
            imgService.setImage(image);
        } else {
            System.err.println("No se pudo cargar la imagen. Ruta incorrecta o recurso no encontrado.");
        }
        titleService.setText(title);

    }

    @FXML
    void cargarServicio(MouseEvent event) throws ThereIsNoView {
        serviciosArray.add("Medico");
        serviciosArray.add("Foros");
        serviciosArray.add("Comunidad");
        serviciosArray.add("Consultas");

        String titulo = titleService.getText().trim().toString();

        if (titulo != null) {
            if (!serviciosArray.contains(titulo)) {

            } else {

            }
        }
    }

    public void openFragmentInDetail(MouseEvent mouseEvent) {
        callback.doAction();
    }
}


