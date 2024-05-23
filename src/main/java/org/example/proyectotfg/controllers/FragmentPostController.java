package org.example.proyectotfg.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorPost;
import org.example.proyectotfg.mediators.MediatorSearch;
import org.example.proyectotfg.mediators.ViewController;

public class FragmentPostController implements ViewController {
    private MediatorPost mediator;
    @FXML
    private Text descripcion;

    @FXML
    private ImageView idImagen;

    @FXML
    private Text nombre;

    @FXML
    private Text titulo;


    @Override
    public void setMediator(Mediator mediador) {
            this.mediator=(MediatorPost)mediador;
    }

    public MediatorPost getMediador() {
        return (MediatorPost) mediator;
    }

    public void setData(String tit, String name, String cont, String imagePath) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        if (image != null) {
            idImagen.setImage(image);
        } else {
            System.err.println("No se pudo cargar la imagen. Ruta incorrecta o recurso no encontrado.");
        }
        nombre.setText(name);
        titulo.setText(tit);
        descripcion.setText(cont);
        
    }


    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

    }
}
