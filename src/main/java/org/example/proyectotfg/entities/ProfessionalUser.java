package org.example.proyectotfg.entities;


import org.example.proyectotfg.enumAndTypes.StatesUser;
import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.functions.VerificatorSetter;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a professional user who is a person with a specific specialty.
 */
public class ProfessionalUser extends Person {
    private String collegiate;
    private String specialty;
    private String description;
    private List<NormalUser> patients = new ArrayList<>();

    /**
     * Constructs a professional user with the specified details.
     *
     * @param idPerson           the ID of the professional user
     * @param names              the names of the professional user
     * @param lastNames          the last names of the professional user
     * @param passScript         the password script of the professional user
     * @param birthDate          the birth date of the professional user
     * @param registrationDate   the registration date of the professional user
     * @param email              the email address of the professional user
     * @param typeUser           the type of user (e.g., normal user or professional user)
     * @param stateUser          the state of the user
     * @param direction          the direction of the professional user
     * @param lastActivityDate   the date of the last activity of the professional user
     * @param collegiate         the collegiate number of the professional user
     * @param specialty          the specialty of the professional user
     * @param description        the description of the professional user
     * @throws IncorrectDataException if any of the data provided is incorrect
     * @throws NullArgumentException if any of the arguments is null
     * @throws NoSuchAlgorithmException if a required cryptographic algorithm is not available in the environment
     * @throws InvalidKeySpecException if the password script is invalid
     */
    public ProfessionalUser(int idPerson, String names, String lastNames, String passScript, Date birthDate, Date registrationDate, String email, TypeUser typeUser, StatesUser stateUser, Direction direction, Date lastActivityDate, String collegiate, String specialty, String description) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        super(idPerson, names, lastNames, passScript, birthDate, registrationDate, email, typeUser, stateUser, direction, lastActivityDate);
        setCollegiate(collegiate);
        setSpecialty(specialty);
        setDescription(description);
    }

    /**
     * Constructs a professional user with the specified details.
     *
     * @param names          the names of the professional user
     * @param lastNames      the last names of the professional user
     * @param passScript     the password script of the professional user
     * @param birthDate      the birth date of the professional user
     * @param registrationDate  the registration date of the professional user
     * @param email          the email address of the professional user
     * @param typeUser       the type of user (e.g., normal user or professional user)
     * @param direction      the direction of the professional user
     * @param collegiate     the collegiate number of the professional user
     * @param specialty      the specialty of the professional user
     * @param description    the description of the professional user
     * @throws IncorrectDataException if any of the data provided is incorrect
     * @throws NullArgumentException if any of the arguments is null
     * @throws NoSuchAlgorithmException if a required cryptographic algorithm is not available in the environment
     * @throws InvalidKeySpecException if the password script is invalid
     */
    public ProfessionalUser(String names, String lastNames, String passScript, Date birthDate, Date registrationDate, String email, TypeUser typeUser, Direction direction, String collegiate, String specialty, String description) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        super(names, lastNames, passScript, birthDate, registrationDate, email, typeUser, direction);
        setCollegiate(collegiate);
        setSpecialty(specialty);
        setDescription(description);
    }
    /**
     * Constructs a professional user with the specified details.
     *
     * @param collegiate     the collegiate number of the professional user
     * @param specialty      the specialty of the professional user
     * @param description    the description of the professional user
     * @throws IncorrectDataException if any of the data provided is incorrect
     * @throws NullArgumentException if any of the arguments is null
     * @throws NoSuchAlgorithmException if a required cryptographic algorithm is not available in the environment
     * @throws InvalidKeySpecException if the password script is invalid
     */
    public ProfessionalUser(String collegiate, String specialty, String description) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        super();
        setCollegiate(collegiate);
        setSpecialty(specialty);
        setDescription(description);
    }

    /**
     * Constructs a professional user with default values.
     *
     * @throws IncorrectDataException if any of the data provided is incorrect
     * @throws NoSuchAlgorithmException if a required cryptographic algorithm is not available in the environment
     * @throws InvalidKeySpecException if the password script is invalid
     * @throws NullArgumentException if any of the arguments is null
     */
    public ProfessionalUser() throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        super();
    }

    /**
     * Constructs a professional user with the specified details.
     *
     * @param idPerson the ID of the professional user
     * @param names the names of the professional user
     * @param lastNames the last names of the professional user
     * @param passScript the password script of the professional user
     * @param birthDate the birth date of the professional user
     * @param registrationDate the registration date of the professional user
     * @param email the email address of the professional user
     * @param typeUser the type of user (e.g., normal user or professional user)
     * @param stateUser the state of the user
     * @param direction the direction of the professional user
     * @param lastActivity the date of the last activity of the professional user
     * @throws IncorrectDataException if any of the data provided is incorrect
     * @throws NoSuchAlgorithmException if a required cryptographic algorithm is not available in the environment
     * @throws InvalidKeySpecException if the password script is invalid
     * @throws NullArgumentException if any of the arguments is null
     */
    public ProfessionalUser(int idPerson, String names, String lastNames, String passScript, Date birthDate, Date registrationDate, String email, TypeUser typeUser, StatesUser stateUser, Direction direction, Date lastActivity) throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        super(idPerson, names, lastNames, passScript, birthDate, registrationDate, email, typeUser, stateUser, direction, lastActivity);
    }

    /**
     * Constructs a professional user with the specified details.
     *
     * @param names the names of the professional user
     * @param lastNames the last names of the professional user
     * @param birthd the birth date of the professional user
     * @param mail the email address of the professional user
     * @param typeUs the type of user (e.g., normal user or professional user)
     * @param nueva the direction of the professional user
     * @param collegiate the collegiate number of the professional user
     * @param specialty the specialty of the professional user
     * @param description the description of the professional user
     * @throws IncorrectDataException if any of the data provided is incorrect
     * @throws NullArgumentException if any of the arguments is null
     */
    public ProfessionalUser(String names, String lastNames, Date birthd, String mail, TypeUser typeUs, Direction nueva, String collegiate, String specialty, String description) throws IncorrectDataException, NullArgumentException {
        super(names, lastNames, birthd, mail, typeUs, nueva);
        setCollegiate(collegiate);
        setSpecialty(specialty);
        setDescription(description);
    }

    /**
     * Constructs a professional user with the specified details.
     *
     * @param id the ID of the professional user
     * @param names the names of the professional user
     * @param lastNames the last names of the professional user
     * @param pass1 the password script of the professional user
     * @param mail the email address of the professional user
     * @param nueva the direction of the professional user
     * @param typeUser the type of user (e.g., normal user or professional user)
     * @param collegiate the collegiate number of the professional user
     * @param specialty the specialty of the professional user
     * @param description the description of the professional user
     * @throws IncorrectDataException if any of the data provided is incorrect
     * @throws NullArgumentException if any of the arguments is null
     * @throws NoSuchAlgorithmException if a required cryptographic algorithm is not available in the environment
     * @throws InvalidKeySpecException if the password script is invalid
     */
    public ProfessionalUser(int id, String names, String lastNames, String pass1, String mail, Direction nueva, TypeUser typeUser, String collegiate, String specialty, String description) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        super(id, names, lastNames, pass1, mail, typeUser, nueva);
        setCollegiate(collegiate);
        setSpecialty(specialty);
        setDescription(description);
    }


    /**
     * Constructs a professional user with the specified details.
     *
     * @param idPerson the ID of the professional user
     * @param names the names of the professional user
     * @param lastNames the last names of the professional user
     * @param mail the email address of the professional user
     * @param nueva the direction of the professional user
     * @param typeUser the type of user (e.g., normal user or professional user)
     * @param collegiate the collegiate number of the professional user
     * @param specialty the specialty of the professional user
     * @param description the description of the professional user
     * @throws IncorrectDataException if any of the data provided is incorrect
     * @throws NullArgumentException if any of the arguments is null
     */
    public ProfessionalUser(int idPerson, String names, String lastNames, String mail, Direction nueva, TypeUser typeUser, String collegiate, String specialty, String description) throws IncorrectDataException, NullArgumentException {
        super(idPerson, names, lastNames, mail, nueva, typeUser);
        setCollegiate(collegiate);
        setSpecialty(specialty);
        setDescription(description);
    }

    /**
     * Retrieves the collegiate number of the professional user.
     *
     * @return the collegiate number of the professional user
     */
    public String getCollegiate() {
        return collegiate;
    }

    /**
     * Sets the collegiate number of the professional user.
     *
     * @param collegiate the collegiate number to set
     * @throws NullArgumentException if the collegiate number is null
     * @throws IncorrectDataException if the collegiate number contains invalid characters
     */
    public void setCollegiate(String collegiate) throws NullArgumentException, IncorrectDataException {
        if (collegiate != null) {
            if (VerificatorSetter.stringVerificatorletterAndNumbers(collegiate, collegiate.length())) {
                this.collegiate = collegiate;
            } else {
                throw new IncorrectDataException("Verifica los datos introducidos, solo se aceptan letras");
            }
        } else {
            throw new NullArgumentException("Has pasado argumentos nulos a la hora de crear el psicólogo");
        }
    }

    /**
     * Retrieves the specialty of the professional user.
     *
     * @return the specialty of the professional user
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the specialty of the professional user.
     *
     * @param specialty the specialty to set
     * @throws NullArgumentException if the specialty is null
     * @throws IncorrectDataException if the specialty contains invalid characters
     */
    public void setSpecialty(String specialty) throws NullArgumentException, IncorrectDataException {
        if (specialty != null) {
            if (VerificatorSetter.stringVerificatorletterAndNumbers(specialty, specialty.length())) {
                this.specialty = specialty;
            } else {
                throw new IncorrectDataException("Verifica los datos introducidos, solo se aceptan letras");
            }
        } else {
            throw new NullArgumentException("Has pasado argumentos nulos a la hora de crear el psicólogo");
        }
    }


    /**
     * Retrieves the description of the professional user.
     *
     * @return the description of the professional user
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the professional user.
     *
     * @param description the description to set
     * @throws NullArgumentException if the description is null
     * @throws IncorrectDataException if the description contains invalid characters
     */
    public void setDescription(String description) throws NullArgumentException, IncorrectDataException {
        if (description != null) {
            if (VerificatorSetter.stringVerificatorletterAndNumbers(description, 7000)) {
                this.description = description;
            } else {
                throw new IncorrectDataException("Verifica los datos introducidos, solo se aceptan letras");
            }
        } else {
            throw new NullArgumentException("Has pasado argumentos nulos a la hora de crear el psicólogo");
        }
    }

    /**
     * Retrieves the list of patients associated with the professional user.
     *
     * @return the list of patients associated with the professional user
     */
    public List<NormalUser> getPatients() {
        return patients;
    }

    /**
     * Sets the list of patients associated with the professional user.
     *
     * @param patients the list of patients to set
     */
    public void setPatients(List<NormalUser> patients) {
        this.patients = patients;
    }

    /**
     * Returns a string representation of the professional user.
     *
     * @return a string representation of the professional user
     */
    @Override
    public String toString() {
        return "ProfessionalUser{" + "collegiate='" + collegiate + '\'' + ", speciality='" + specialty + '\'' + ", description='" + description + '\'' + ", patients=" + patients + '}';
    }

}



