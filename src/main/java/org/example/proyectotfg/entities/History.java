package org.example.proyectotfg.entities;

import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.functions.VerificatorSetter;

import java.util.ArrayList;
import java.util.List;

public class History {
    private int idHistory;
    private NormalUser user;
    private List<ProfessionalUser> psychologists = new ArrayList<>();
    private List<String> diagnoses = new ArrayList<>();

    public History(int idHistory, NormalUser user, List<ProfessionalUser> psychologists, List<String> diagnoses) throws IncorrectDataException, NullArgumentException {
        setIdHistory(idHistory);
        setUser(user);
    }


    public void setIdHistory(int idHistory) throws IncorrectDataException {
        if (VerificatorSetter.numberVerificator(idHistory, 1000000000) && idHistory >= 0) {
            this.idHistory = idHistory;
        } else {
            throw new IncorrectDataException("El id del historial tiene que ser un número");
        }
    }

    public NormalUser getUser() {
        return user;
    }

    public void setUser(NormalUser user) throws NullArgumentException {
        if (user != null) {
            this.user = user;
        } else {
            throw new NullArgumentException("Has pasado un usuario nulo a la hora de crear el historial");
        }
    }
    @Override
    public String toString() {
        return "Historial{" + "id_historial=" + idHistory + ", paciente=" + user + '}';
    }
}
