package org.example.proyectotfg.mediators;

import org.example.proyectotfg.entities.ProfessionalUser;

public interface MediatorSearch {
    void callback();

    void addToFavorites(ProfessionalUser professionalUser);
}
