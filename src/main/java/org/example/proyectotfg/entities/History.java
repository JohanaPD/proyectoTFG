package org.example.proyectotfg.entities;

import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.functions.VerificatorSetter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a medical history associated with a patient, including user information, psychologists involved,
 * and diagnoses.
 */
public class History {
    private int idHistory;
    private NormalUser user;
    private List<ProfessionalUser> psychologists = new ArrayList<>();
    private List<String> diagnoses = new ArrayList<>();

    /**
     * Constructs a new History object with the specified ID, user, psychologists, and diagnoses.
     *
     * @param idHistory     The history ID.
     * @param user          The user associated with the history.
     * @param psychologists The list of psychologists involved.
     * @param diagnoses     The list of diagnoses.
     * @throws IncorrectDataException If data is incorrect.
     * @throws NullArgumentException If any argument is null.
     */
    public History(int idHistory, NormalUser user, List<ProfessionalUser> psychologists, List<String> diagnoses) throws IncorrectDataException, NullArgumentException {
        setIdHistory(idHistory);
        setUser(user);
    }


    /**
     * Sets the history ID.
     *
     * @param idHistory The history ID.
     * @throws IncorrectDataException If the ID is incorrect.
     */
    public void setIdHistory(int idHistory) throws IncorrectDataException {
        if (VerificatorSetter.numberVerificator(idHistory, 1000000000) && idHistory >= 0) {
            this.idHistory = idHistory;
        } else {
            throw new IncorrectDataException("El id del historial tiene que ser un número");
        }
    }

    /**
     * Retrieves the user associated with the history.
     *
     * @return The user associated with the history.
     */
    public NormalUser getUser() {
        return user;
    }

    /**
     * Sets the user associated with the history.
     *
     * @param user The user associated with the history.
     * @throws NullArgumentException If the user is null.
     */
    public void setUser(NormalUser user) throws NullArgumentException {
        if (user != null) {
            this.user = user;
        } else {
            throw new NullArgumentException("Has pasado un usuario nulo a la hora de crear el historial");
        }
    }

    /**
     * Returns a string representation of the History object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "Historial{" + "id_historial=" + idHistory + ", paciente=" + user + '}';
    }
}

