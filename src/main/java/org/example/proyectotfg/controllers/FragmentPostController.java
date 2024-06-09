package org.example.proyectotfg.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.example.proyectotfg.exceptions.NotFoundImage;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorPost;
import org.example.proyectotfg.mediators.ViewController;

public class FragmentPostController implements ViewController {
    @FXML
    private Text descripcion;

    @FXML
    private ImageView idImagen;

    @FXML
    private Text nombre;

    @FXML
    private Text titulo;

    private MediatorPost mediator;
    /**
     * Sets the mediator.
     *
     * @param mediador the mediator to set.
     */
    @Override
    public void setMediator(Mediator mediador) {
        this.mediator = (MediatorPost) mediador;
    }

    /**
     * Gets the mediator.
     *
     * @return the mediator.
     */
    public MediatorPost getMediador() {
        return (MediatorPost) mediator;
    }

    /**
     * Sets the data for the post.
     *
     * @param tit the title of the post.
     * @param name the name associated with the post.
     * @param cont the content of the post.
     * @param imagePath the path to the image.
     * @throws NotFoundImage if the image cannot be found.
     */
    public void setData(String tit, String name, String cont, String imagePath) throws NotFoundImage {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        if (image != null) {
            idImagen.setImage(image);
        } else {
            throw new NotFoundImage("No se pudo cargar la imagen. Ruta incorrecta o recurso no encontrado.");
        }
        nombre.setText(name);
        titulo.setText(tit);
        descripcion.setText(cont);
    }

}
