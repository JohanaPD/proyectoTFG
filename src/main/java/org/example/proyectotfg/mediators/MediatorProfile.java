package org.example.proyectotfg.mediators;


import org.example.proyectotfg.entities.NormalUser;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;

public interface MediatorProfile {

    void loginUser(String usuario, String pass, TypeUser typeUser);

    void userRegister();

    void loadRecoverPassword();

    public void recoverPassword(String mail, String pass);

    void makeRecordRegister(ProfessionalUser user);

    void makeRecordRegister(NormalUser user);


    void volverIncio();


}
