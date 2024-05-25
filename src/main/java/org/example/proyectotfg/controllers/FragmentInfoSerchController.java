package org.example.proyectotfg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.OperationsDBException;
import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.MediatorSearch;
import org.example.proyectotfg.mediators.ViewController;




public class FragmentInfoSerchController implements ViewController {
    @FXML
    private Text especialidad;

    @FXML
    private ImageView idImagen;

    @FXML
    private Text nombreMedico;

    @FXML
    private Text valoraciones1;

    private MediatorSearch mediatorSearch;
    private Person person;
    private ProfessionalUser professionalUser;

    public void setProfessionalUser(ProfessionalUser professionalUser) {
        this.professionalUser = professionalUser;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public void setMediator(Mediator mediador) {
        this.mediatorSearch = (MediatorSearch) mediador;
    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

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

    public MediatorSearch getMediador() {
        return mediatorSearch;
    }


    public void setData(String nombreMed, String espec, String imagePath, int val) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        if (image != null) {
            idImagen.setImage(image);
        } else {
            System.err.println("No se pudo cargar la imagen. Ruta incorrecta o recurso no encontrado.");
        }
        nombreMedico.setText(nombreMed);
        especialidad.setText(espec);
        valoraciones1.setText(String.valueOf(10));

    }

    @FXML
    void addToFavorites(ActionEvent event) throws OperationsDBException {
           mediatorSearch.addToFavorites(professionalUser, person);
    }


}
