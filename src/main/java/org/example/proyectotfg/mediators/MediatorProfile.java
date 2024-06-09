package org.example.proyectotfg.mediators;


import org.example.proyectotfg.entities.NormalUser;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;

/**
 * MediatorProfile interface for profile mediator functionality.
 *
 * <p>Authors: [Author Name]</p>
 * <p>Version: [Version Number]</p>
 * <p>Since: [Date of Creation]</p>
 */
public interface MediatorProfile {

    /**
     * Logs in the user.
     *
     * @param user     The username.
     * @param pass     The password.
     * @param typeUser The type of user.
     */
    void loginUser(String user, String pass, TypeUser typeUser);

    /**
     * Registers a new user.
     */
    void userRegister();

    /**
     * Loads the password recovery process.
     */
    void loadRecoverPassword();

    /**
     * Recovers the password.
     *
     * @param mail The email address.
     * @param pass The new password.
     */
    public void recoverPassword(String mail, String pass);

    /**
     * Makes a record register for a professional user.
     *
     * @param user The professional user.
     */
    void makeRecordRegister(ProfessionalUser user);

    /**
     * Makes a record register for a normal user.
     *
     * @param user The normal user.
     */
    void makeRecordRegister(NormalUser user);

    /**
     * Returns to the home interface.
     */
    void volverIncio();
}

