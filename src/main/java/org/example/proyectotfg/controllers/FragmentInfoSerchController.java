package org.example.proyectotfg.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
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
    private Text valoraciones;

    private MediatorSearch mediatorSearch;
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
        return valoraciones;
    }

    public void setValoraciones(Text valoraciones) {
        this.valoraciones = valoraciones;
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
        valoraciones.setText(String.valueOf(10));

    }




}

/*
*  @FXML
    private Text especialidad;

    @FXML
    private ImageView idImagen;

    @FXML
    private Text nombreMedico;

    @FXML
    private Text valoraciones;
    MediadorPrimerPantallaContrller mediador;
    @Override
    public void setMediator(Mediator mediador) {
        this.mediador = (MediadorPrimerPantallaContrller) mediador;
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
        return valoraciones;
    }

    public void setValoraciones(Text valoraciones) {
        this.valoraciones = valoraciones;
    }

    public MediadorPrimerPantallaContrller getMediador() {
        return mediador;
    }

    public void setMediador(MediadorPrimerPantallaContrller mediador) {
        this.mediador = mediador;
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
        valoraciones.setText(String.valueOf(10));

    }

*
* */