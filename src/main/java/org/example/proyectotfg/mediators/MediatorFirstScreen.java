package org.example.proyectotfg.mediators;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.NonexistingUser;

import java.util.HashMap;
import java.util.List;

public interface MediatorFirstScreen {


    void datosPerfilPsico(TextFlow descripcionCurriculum, Text nombreDoctor, ImageView imgmed, ProfessionalUser usuarioPerfilesCarga);

    void regresar();

    void openSearch(String busqueda);

    void cargarVistaPSicologo();

    Parent initializeServicios(HashMap<String, String> servicios);

    Parent initializeProfessionals(List<ProfessionalUser> professionalUsers) throws NonexistingUser;

    void openCalendarView();
}
