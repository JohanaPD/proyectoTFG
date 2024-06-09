package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.NotFoundImage;
import org.example.proyectotfg.exceptions.OperationsDBException;
import org.example.proyectotfg.mediators.Callback;


public class FragmentInfoSerchController {
    @FXML
    private Text especialidad;

    @FXML
    private ImageView idImagen;

    @FXML
    private Text nombreMedico;

    @FXML
    private Text valoraciones1;
    private Callback callback;

    private Person person;
    private ProfessionalUser professionalUser;

    /**
     * Sets the professional user.
     *
     * @param professionalUser the professional user to set.
     */
    public void setProfessionalUser(ProfessionalUser professionalUser) {
        this.professionalUser = professionalUser;
    }

    /**
     * Sets the Person object.
     *
     * @param person the person to set.
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Gets the specialty text.
     *
     * @return the specialty text.
     */
    public Text getEspecialidad() {
        return especialidad;
    }

    /**
     * Sets the specialty text.
     *
     * @param especialidad the specialty text to set.
     */
    public void setEspecialidad(Text especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * Gets the image view.
     *
     * @return the image view.
     */
    public ImageView getIdImagen() {
        return idImagen;
    }

    /**
     * Sets the image view.
     *
     * @param idImagen the image view to set.
     */
    public void setIdImagen(ImageView idImagen) {
        this.idImagen = idImagen;
    }

    /**
     * Gets the doctor's name text.
     *
     * @return the doctor's name text.
     */
    public Text getNombreMedico() {
        return nombreMedico;
    }

    /**
     * Sets the doctor's name text.
     *
     * @param nombreMedico the doctor's name text to set.
     */
    public void setNombreMedico(Text nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    /**
     * Gets the ratings text.
     *
     * @return the ratings text.
     */
    public Text getValoraciones() {
        return valoraciones1;
    }

    /**
     * Sets the ratings text.
     *
     * @param valoraciones the ratings text to set.
     */
    public void setValoraciones(Text valoraciones) {
        this.valoraciones1 = valoraciones;
    }

    /**
     * Sets the callback.
     *
     * @param callback the callback to set.
     */
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    /**
     * Sets the data for the professional user.
     *
     * @param nombreMed the doctor's name.
     * @param espec the specialty.
     * @param imagePath the path to the image.
     * @param val the rating value.
     * @throws NotFoundImage if the image cannot be found.
     */
    public void setData(String nombreMed, String espec, String imagePath, int val) throws NotFoundImage {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        if (image != null) {
            idImagen.setImage(image);
        } else {
            throw new NotFoundImage("No se pudo cargar la imagen. Ruta incorrecta o recurso no encontrado.");
        }
        nombreMedico.setText(nombreMed);
        especialidad.setText(espec);
        valoraciones1.setText(String.valueOf(val));
    }

    /**
     * Adds the professional user to favorites.
     *
     * @param event the action event.
     * @throws OperationsDBException if there is an error adding to favorites.
     */
    @FXML
    void addToFavorites(ActionEvent event) throws OperationsDBException {
        System.out.println("callback 'Añadir a Favoritos' pulsado");
        callback.doAction();
    }

}
