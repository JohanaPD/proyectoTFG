package org.example.proyectotfg.mediators;

import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.OperationsDBException;

public interface MediatorSearch {
    void callback();

    void addToFavorites(ProfessionalUser professionalUser, Person person) throws OperationsDBException;
}
