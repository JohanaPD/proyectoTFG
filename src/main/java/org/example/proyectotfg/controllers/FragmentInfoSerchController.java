package org.example.proyectotfg.controllers;

import org.example.proyectotfg.mediators.Mediator;
import org.example.proyectotfg.mediators.ViewController;

public class FragmentInfoSerchController implements ViewController {
    @Override
    public void setMediator(Mediator mediador) {


    }

    @Override
    public MainController getMainController() {
        return null;
    }

    @Override
    public void setMainController(MainController mainController) {

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