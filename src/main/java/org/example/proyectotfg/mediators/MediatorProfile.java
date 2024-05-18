package org.example.proyectotfg.mediators;


import org.example.proyectotfg.entities.NormalUser;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.exceptions.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public interface MediatorProfile {

    void loginUser(String usuario, String pass, TypeUser typeUser);

    void userRegister();

    void recoverPassword();

    void makeRecordRegister(ProfessionalUser user);

    void makeRecordRegister(NormalUser user);



    void volverIncio();


}
