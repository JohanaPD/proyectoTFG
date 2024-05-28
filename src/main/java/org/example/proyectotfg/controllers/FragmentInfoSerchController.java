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

    public void setProfessionalUser(ProfessionalUser professionalUser) {
        this.professionalUser = professionalUser;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Text getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Text especialidad) {
        this.especialidad = especialidad;
    }

    public ImageView getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(ImageView idImagen) {
        this.idImagen = idImagen;
    }

    public Text getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(Text nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public Text getValoraciones() {
        return valoraciones1;
    }

    public void setValoraciones(Text valoraciones) {
        this.valoraciones1 = valoraciones;
    }

    public void setCallback(Callback callback) {

        this.callback = (Callback) callback;

    }

    public void setData(String nombreMed, String espec, String imagePath, int val) throws NotFoundImage {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        if (image != null) {
            idImagen.setImage(image);
        } else {
            throw new NotFoundImage("No se pudo cargar la imagen. Ruta incorrecta o recurso no encontrado.");
        }
        nombreMedico.setText(nombreMed);
        especialidad.setText(espec);
        valoraciones1.setText(String.valueOf(10));
    }

    @FXML
    void addToFavorites(ActionEvent event) throws OperationsDBException {
        System.out.println("callback 'Añadir a Favoritos' pulsado");
        callback.doAction();
    }
}
