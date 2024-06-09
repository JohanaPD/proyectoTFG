package org.example.proyectotfg.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.example.proyectotfg.exceptions.NotFoundImage;
import org.example.proyectotfg.exceptions.OperationsDBException;
import org.example.proyectotfg.exceptions.ThereIsNoView;
import org.example.proyectotfg.mediators.Callback;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class ControllerFragmentServicios {

    @FXML
    private ImageView imgService;

    @FXML
    private Text titleService;
    private Callback callback;

    List<String> serviciosArray = new ArrayList<>();

    /**
     * Sets the callback.
     *
     * @param callback the callback to set.
     */
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    /**
     * Gets the service image.
     *
     * @return the service image.
     */
    public ImageView getImgService() {
        return imgService;
    }

    /**
     * Sets the service image.
     *
     * @param imgService the service image to set.
     */
    public void setImgService(ImageView imgService) {
        this.imgService = imgService;
    }

    /**
     * Gets the service title.
     *
     * @return the service title.
     */
    public Text getTitleService() {
        return titleService;
    }

    /**
     * Sets the service title.
     *
     * @param titleService the service title to set.
     */
    public void setTitleService(Text titleService) {
        this.titleService = titleService;
    }

    /**
     * Sets the data for the service.
     *
     * @param title the title of the service.
     * @param imagePath the path to the image.
     * @throws NotFoundImage if the image cannot be found.
     */
    public void setData(String title, String imagePath) throws NotFoundImage {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        if (image != null) {
            imgService.setImage(image);
        } else {
            throw new NotFoundImage("No se pudo cargar la imagen. Ruta incorrecta o recurso no encontrado.");
        }
        titleService.setText(title);
    }

    /**
     * Loads the service.
     *
     * @param event the mouse event.
     * @throws ThereIsNoView if there is no view to load.
     */
    @FXML
    void cargarServicio(MouseEvent event) throws ThereIsNoView {
        serviciosArray.add("Medico");
        serviciosArray.add("Foros");
        serviciosArray.add("Publicar Post");
        serviciosArray.add("Ver Post");
        String titulo = titleService.getText().trim().toString();
    }

    /**
     * Opens the fragment in detail.
     *
     * @param mouseEvent the mouse event.
     */
    public void openFragmentInDetail(MouseEvent mouseEvent) {
        callback.doAction();
    }

}


