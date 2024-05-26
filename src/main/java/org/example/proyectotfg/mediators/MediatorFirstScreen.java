package org.example.proyectotfg.mediators;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.example.proyectotfg.entities.NormalUser;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.NonexistingUser;
import org.example.proyectotfg.exceptions.OperationsDBException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface MediatorFirstScreen {


    void datosPerfilPsico(TextFlow descripcionCurriculum, Text nombreDoctor, ImageView imgmed, ProfessionalUser usuarioPerfilesCarga);

    void regresar();

    void openSearch(String busqueda);

    void openProfessionalUser(ProfessionalUser professionalUser, int index);

    Parent initializeServicios(HashMap<String, String> servicios);

    Parent initializeProfessionals(List<ProfessionalUser> professionalUsers) throws NonexistingUser;

    void addToFavorites(ProfessionalUser professionalUser, Person person) throws OperationsDBException;

    void openCalendarView();

    void updatePersonalData(Person user) throws OperationsDBException;

    void updateDataPerson(ProfessionalUser user);

    void updateDataPerson(NormalUser user) throws SQLException;

    void updateAllDataPerson(ProfessionalUser nuevo);

    void updateAllDataPerson(NormalUser nuevo) throws SQLException;
}
