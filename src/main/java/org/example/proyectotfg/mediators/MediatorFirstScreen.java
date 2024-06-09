package org.example.proyectotfg.mediators;

import javafx.scene.Parent;
import org.example.proyectotfg.entities.NormalUser;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NonexistingUser;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.exceptions.OperationsDBException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * MediatorFirstScreen interface for first screen mediator functionality.
 *
 * <p>Authors: [Author Name]</p>
 * <p>Version: [Version Number]</p>
 * <p>Since: [Date of Creation]</p>
 */
public interface MediatorFirstScreen {

    /**
     * Navigates from the first screen to the home interface.
     */
    void fromFirstScreenToHome();

    /**
     * Opens the search interface with the specified search query.
     *
     * @param busqueda The search query.
     */
    void openSearch(String busqueda);

    /**
     * Opens the professional user interface with the specified professional user and index.
     *
     * @param professionalUser The professional user.
     * @param index            The index.
     */
    void openProfessionalUser(ProfessionalUser professionalUser, int index);

    /**
     * Initializes the services interface with the provided services.
     *
     * @param services A HashMap containing services.
     * @return The initialized services interface.
     */
    Parent initializeServices(HashMap<String, String> services);

    /**
     * Initializes the professionals interface with the provided list of professional users.
     *
     * @param professionalUsers The list of professional users.
     * @return The initialized professionals interface.
     * @throws NonexistingUser if a user does not exist.
     */
    Parent initializeProfessionals(List<ProfessionalUser> professionalUsers) throws NonexistingUser;

    /**
     * Adds the specified professional user to favorites for the given person.
     *
     * @param professionalUser The professional user.
     * @param person           The person.
     * @throws OperationsDBException if a database operation fails.
     */
    void addToFavorites(ProfessionalUser professionalUser, Person person) throws OperationsDBException;

    /**
     * Opens the appointment view.
     */
    void openAppointmentView();

    /**
     * Updates the personal data for the given user.
     *
     * @param user The user whose data is to be updated.
     * @throws OperationsDBException if a database operation fails.
     */
    void updatePersonalData(Person user) throws OperationsDBException;

    /**
     * Updates the data for the specified professional user.
     *
     * @param user The professional user whose data is to be updated.
     */
    void updateDataPerson(ProfessionalUser user);

    /**
     * Updates the data for the specified normal user.
     *
     * @param user The normal user whose data is to be updated.
     * @throws SQLException if a SQL exception occurs.
     */
    void updateDataPerson(NormalUser user) throws SQLException;

    /**
     * Updates all data for the specified professional user.
     *
     * @param user The professional user whose data is to be updated.
     */
    void updateAllDataPerson(ProfessionalUser user);

    /**
     * Updates all data for the specified normal user.
     *
     * @param user The normal user whose data is to be updated.
     * @throws SQLException if a SQL exception occurs.
     */
    void updateAllDataPerson(NormalUser user) throws SQLException;

    /**
     * Loads searches with the provided list of professional users.
     *
     * @param professionalUsers The list of professional users.
     * @return The loaded searches.
     */
    Parent loadSearchs(List<ProfessionalUser> professionalUsers);

    /**
     * Logs out from the system.
     */
    void logOut();

    /**
     * Retrieves the list of professionals.
     *
     * @return The list of professionals.
     * @throws SQLException             if a SQL exception occurs.
     * @throws IncorrectDataException   if the data is incorrect.
     * @throws NoSuchAlgorithmException if the algorithm is not found.
     * @throws InvalidKeySpecException  if the key specification is invalid.
     * @throws NonexistingUser          if a user does not exist.
     * @throws NullArgumentException   if an argument is null.
     * @throws OperationsDBException    if a database operation fails.
     */
    List<ProfessionalUser> getProfessionals() throws SQLException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NonexistingUser, NullArgumentException, OperationsDBException;

    /**
     * Charges the professional user by the specified ID.
     *
     * @param id The ID of the professional user to charge.
     * @return The charged professional user.
     * @throws OperationsDBException if a database operation fails.
     */
    ProfessionalUser chargeProfessionalUserById(int id) throws OperationsDBException;
}

