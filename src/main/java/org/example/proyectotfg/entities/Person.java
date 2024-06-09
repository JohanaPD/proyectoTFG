package org.example.proyectotfg.entities;

import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.functions.FunctionsApp;
import org.example.proyectotfg.enumAndTypes.StatesUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.functions.VerificatorSetter;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.TreeMap;
/**
 * The {@code Person} class represents a generic person in the system.
 * It serves as a base class for different types of users.
 */
public abstract class Person {

    private int idPerson;
    private String names;
    private String lastNames;
    private String passScript;
    private Date birthDate;
    private Date registrationDate;
    private String email;
    private TypeUser typeUser;
    private StatesUser state;
    private TreeMap<Date, Post> postTreeMap = new TreeMap<>();
    private Date lastActivityDate;
    private Direction direction;
    /**
     * Constructs a person with default data.
     *
     * @throws IncorrectDataException if the default data is incorrect
     * @throws NullArgumentException if any of the arguments are null
     * @throws NoSuchAlgorithmException if the specified algorithm for password encryption is not found
     * @throws InvalidKeySpecException if the provided password key specification is invalid
     */
    public Person() throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        this("SINDATOS", "SINDATOS", "SINDATOS", new Date(), new Date(), "SINDATOS@SINDATOS.COM", TypeUser.USUARIO_NORMAL, new Direction("SINDATOS", "SINDATOS", 00001));
        setState(StatesUser.NOT_VERIFIED);
    }

    /**
     * Constructs a person with the provided registration data.
     *
     * @param names the names of the person
     * @param lastNames the last names of the person
     * @param passScript the password of the person
     * @param birthDate the birth date of the person
     * @param registrationDate the registration date of the person
     * @param email the email address of the person
     * @param typeUser the type of user
     * @param direction the direction of the person
     * @throws IncorrectDataException if any of the provided data is incorrect
     * @throws NullArgumentException if any of the arguments are null
     * @throws NoSuchAlgorithmException if the specified algorithm for password encryption is not found
     * @throws InvalidKeySpecException if the provided password key specification is invalid
     */
    public Person(String names, String lastNames, String passScript, Date birthDate,
                  Date registrationDate, String email, TypeUser typeUser, Direction direction) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        setNames(names);
        setLastNames(lastNames);
        setPassScript(passScript);
        setBirthDate(birthDate);
        setRegistrationDate(registrationDate);
        setEmail(email);
        setTypeUser(typeUser);
        setDirection(direction);
        setLastActivityDate(new Date());
        setState(StatesUser.NOT_VERIFIED);
    }
    /**
     * Constructs a person with the provided data.
     *
     * @param idPerson the ID of the person
     * @param names the names of the person
     * @param lastNames the last names of the person
     * @param passScript the password of the person
     * @param birthDate the birth date of the person
     * @param registrationDate the registration date of the person
     * @param email the email address of the person
     * @param typeUser the type of user
     * @param state the state of the person
     * @param direction the direction of the person
     * @param lastActivityDate the last activity date of the person
     * @throws IncorrectDataException if any of the provided data is incorrect
     * @throws NullArgumentException if any of the arguments are null
     * @throws NoSuchAlgorithmException if the specified algorithm for password encryption is not found
     * @throws InvalidKeySpecException if the provided password key specification is invalid
     */
    public Person(int idPerson, String names, String lastNames, String passScript, Date birthDate,
                  Date registrationDate, String email, TypeUser typeUser, StatesUser state,
                  Direction direction, Date lastActivityDate) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        setIdPerson(idPerson);
        setNames(names);
        setLastNames(lastNames);
        setPassScript(passScript);
        setBirthDate(birthDate);
        setRegistrationDate(registrationDate);
        setEmail(email);
        setTypeUser(typeUser);
        setDirection(direction);
        setState(state);
        setLastActivityDate(lastActivityDate);
        setState(StatesUser.NOT_VERIFIED);
    }

    /**
     * Constructs a person with the provided data.
     *
     * @param names the names of the person
     * @param lastNames the last names of the person
     * @param birthd the birth date of the person
     * @param mail the email address of the person
     * @param tipeUs the type of user
     * @param nueva the direction of the person
     * @throws IncorrectDataException if any of the provided data is incorrect
     * @throws NullArgumentException if any of the arguments are null
     */
    public Person(String names, String lastNames, Date birthd, String mail, TypeUser tipeUs,
                  Direction nueva) throws IncorrectDataException, NullArgumentException {
        setNames(names);
        setLastNames(lastNames);
        setBirthDate(birthDate);
        setEmail(email);
        setTypeUser(typeUser);
        setDirection(direction);
    }

    /**
     * Constructs a person with the provided data.
     *
     * @param names the names of the person
     * @param lastNames the last names of the person
     * @param mail the email address of the person
     * @param type the type of user
     * @param nueva the direction of the person
     * @throws NullArgumentException if any of the arguments are null
     * @throws IncorrectDataException if any of the provided data is incorrect
     */
    public Person(String names, String lastNames, String mail, TypeUser type, Direction nueva) throws NullArgumentException, IncorrectDataException {
        setNames(names);
        setLastNames(lastNames);
        setEmail(mail);
        setTypeUser(type);
        setDirection(nueva);
    }

    /**
     * Constructs a person with the provided data.
     *
     * @param names the names of the person
     * @param lastNames the last names of the person
     * @param passScript the password script of the person
     * @param mail the email address of the person
     * @param type the type of user
     * @param nueva the direction of the person
     * @throws NullArgumentException if any of the arguments are null
     * @throws IncorrectDataException if any of the provided data is incorrect
     * @throws NoSuchAlgorithmException if a required cryptographic algorithm is not available
     * @throws InvalidKeySpecException if the provided key specification is invalid
     */
    public Person(String names, String lastNames, String passScript, String mail, TypeUser type,
                  Direction nueva) throws NullArgumentException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException {
        setNames(names);
        setLastNames(lastNames);
        setPassScript(passScript);
        setEmail(mail);
        setTypeUser(type);
        setDirection(nueva);
    }

    /**
     * Constructs a person with the provided data.
     *
     * @param id the id of the person
     * @param names the names of the person
     * @param lastNames the last names of the person
     * @param passScript the password script of the person
     * @param mail the email address of the person
     * @param type the type of user
     * @param nueva the direction of the person
     * @throws NullArgumentException if any of the arguments are null
     * @throws IncorrectDataException if any of the provided data is incorrect
     * @throws NoSuchAlgorithmException if a required cryptographic algorithm is not available
     * @throws InvalidKeySpecException if the provided key specification is invalid
     */
    public Person(int id, String names, String lastNames, String passScript, String mail,
                  TypeUser type, Direction nueva) throws NullArgumentException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException {
        setIdPerson(id);
        setNames(names);
        setLastNames(lastNames);
        setPassScript(passScript);
        setEmail(mail);
        setTypeUser(type);
        setDirection(nueva);
    }

    /**
     * Constructs a person with the provided data.
     *
     * @param idPerson the id of the person
     * @param names the names of the person
     * @param lastNames the last names of the person
     * @param mail the email address of the person
     * @param direction the direction of the person
     * @throws IncorrectDataException if any of the provided data is incorrect
     * @throws NullArgumentException if any of the arguments are null
     */
    public Person(int idPerson, String names, String lastNames, String mail, Direction direction) throws IncorrectDataException, NullArgumentException {
        setIdPerson(idPerson);
        setNames(names);
        setLastNames(lastNames);
        setEmail(mail);
        setDirection(direction);
    }

    /**
     * Constructs a person with the provided data.
     *
     * @param idPerson the id of the person
     * @param names the names of the person
     * @param lastNames the last names of the person
     * @param mail the email address of the person
     * @param direction the direction of the person
     * @param typeUser the type of user
     * @throws IncorrectDataException if any of the provided data is incorrect
     * @throws NullArgumentException if any of the arguments are null
     */
    public Person(int idPerson, String names, String lastNames, String mail, Direction direction,
                  TypeUser typeUser) throws IncorrectDataException, NullArgumentException {
        setIdPerson(idPerson);
        setNames(names);
        setLastNames(lastNames);
        setEmail(mail);
        setDirection(direction);
        setTypeUser(typeUser);
    }

    /**
     * Constructs a person with the provided data.
     *
     * @param idPerson the id of the person
     * @param names the names of the person
     * @param lastNames the last names of the person
     * @param pass the password script of the person
     * @param mail the email address of the person
     * @param direction the direction of the person
     * @throws IncorrectDataException if any of the provided data is incorrect
     * @throws NullArgumentException if any of the arguments are null
     * @throws NoSuchAlgorithmException if a required cryptographic algorithm is not available
     * @throws InvalidKeySpecException if the provided key specification is invalid
     */
    public Person(int idPerson, String names, String lastNames, String pass, String mail,
                  Direction direction) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        setIdPerson(idPerson);
        setNames(names);
        setLastNames(lastNames);
        setPassScript(pass);
        setEmail(mail);
        setDirection(direction);
    }

    /**
     * Constructs a person with the provided data.
     *
     * @param idPerson the id of the person
     * @param names the names of the person
     * @param lastNames the last names of the person
     * @param pass the password script of the person
     * @param mail the email address of the person
     * @param direction the direction of the person
     * @param type the type of user
     * @throws IncorrectDataException if any of the provided data is incorrect
     * @throws NullArgumentException if any of the arguments are null
     * @throws NoSuchAlgorithmException if a required cryptographic algorithm is not available
     * @throws InvalidKeySpecException if the provided key specification is invalid
     */
    public Person(int idPerson, String names, String lastNames, String pass, String mail,
                  Direction direction, TypeUser type) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        setIdPerson(idPerson);
        setNames(names);
        setLastNames(lastNames);
        setPassScript(pass);
        setEmail(mail);
        setDirection(direction);
        setTypeUser(type);
    }

    /**
     * Returns the id of the person.
     *
     * @return the id of the person
     */
    public int getIdPerson() {
        return idPerson;
    }

    /**
     * Sets the id of the person.
     *
     * @param idPerson the id of the person
     * @throws IncorrectDataException if the provided id is incorrect
     */
    public void setIdPerson(int idPerson) throws IncorrectDataException {
        if (VerificatorSetter.numberVerificator(idPerson, 1000000000) && idPerson >= 0) {
            this.idPerson = idPerson;
        } else {
            throw new IncorrectDataException("Verifica el id de persona, solo se aceptan números");
        }
    }

    /**
     * Returns the names of the person.
     *
     * @return the names of the person
     */
    public String getNames() {
        return names;
    }

    /**
     * Sets the names of the person.
     *
     * @param names the names of the person
     * @throws IncorrectDataException if the provided names are incorrect
     * @throws NullArgumentException if the provided names are null
     */
    public void setNames(String names) throws IncorrectDataException, NullArgumentException {
        if (names != null) {
            if (VerificatorSetter.stringVerificator(names, names.length())) {
                this.names = names;
            } else {
                throw new IncorrectDataException("Verifica el nombre, solo se aceptan letras");
            }
        } else {
            throw new NullArgumentException("Has introducido datos nulos a la hora de crear la persona");
        }
    }

    /**
     * Returns the last names of the person.
     *
     * @return the last names of the person
     */
    public String getLastNames() {
        return lastNames;
    }

    /**
     * Returns the direction of the person.
     *
     * @return the direction of the person
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the person.
     *
     * @param direction the direction of the person
     * @throws NullArgumentException if the provided direction is null
     * @throws IncorrectDataException if the provided direction is incorrect
     */
    public void setDirection(Direction direction) throws NullArgumentException, IncorrectDataException {
        if (direction != null) {
            this.direction = direction;
        } else {
            throw new NullArgumentException("Has introducido datos nulos a la hora de crear la persona");
        }
    }

    /**
     * Returns the TreeMap of posts of the person.
     *
     * @return the TreeMap of posts of the person
     */
    public TreeMap<Date, Post> getPostTreeMap() {
        return postTreeMap;
    }

    /**
     * Sets the TreeMap of posts of the person.
     *
     * @param postTreeMap the TreeMap of posts of the person
     */
    public void setPostTreeMap(TreeMap<Date, Post> postTreeMap) {
        this.postTreeMap = postTreeMap;
    }

    /**
     * Sets the last names of the person.
     *
     * @param lastNames the last names of the person
     * @throws NullArgumentException if the provided last names are null
     */
    public void setLastNames(String lastNames) throws NullArgumentException {
        if (lastNames != null) {
            if (VerificatorSetter.stringVerificator(lastNames, lastNames.length())) {
                this.lastNames = lastNames;
            } else {
                throw new IllegalArgumentException("Verifica los datos introducidos, solo se aceptan letras");
            }
        } else {
            throw new NullArgumentException("Has introducido datos nulos a la hora de crear la persona");
        }
    }

    /**
     * Returns the email address of the person.
     *
     * @return the email address of the person
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the direction of the person.
     *
     * @return the direction of the person
     */
    public Direction getDireccion() {
        return direction;
    }

    /**
     * Sets the direction of the person.
     *
     * @param direction the direction of the person
     * @throws NullArgumentException if the provided direction is null
     */
    public void setDireccion(Direction direction) throws NullArgumentException {
        if (direction != null) {
            this.direction = direction;
        } else {
            throw new NullArgumentException("Has introducido datos nulos a la hora de crear la persona");
        }
    }

    /**
     * Sets the email address of the person.
     *
     * @param email the email address of the person
     * @throws NullArgumentException if the provided email is null
     * @throws IncorrectDataException if the provided email is incorrect
     */
    public void setEmail(String email) throws NullArgumentException, IncorrectDataException {
        if (email != null) {
            if (VerificatorSetter.validateEmailAddress(email)) {
                this.email = email;
            } else {
                throw new IncorrectDataException("El correo introducido es erróneo");
            }
        } else {
            throw new NullArgumentException("Has introducido datos nulos a la hora de crear la persona");
        }
    }

    /**
     * Returns the birth date of the person.
     *
     * @return the birth date of the person
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the birth date of the person.
     *
     * @param birthDate the birth date of the person
     * @throws NullArgumentException if the provided birth date is null
     * @throws IncorrectDataException if the provided birth date is incorrect
     */
    public void setBirthDate(Date birthDate) throws NullArgumentException, IncorrectDataException {
        if (birthDate != null) {
            if (VerificatorSetter.isValidBirthday(birthDate)) {
                this.birthDate = birthDate;
            } else {
                throw new IncorrectDataException("La fecha es incorrecta, revise los datos");
            }
        } else {
            throw new NullArgumentException("Has introducido datos nulos a la hora de crear la persona");
        }
    }

    /**
     * Returns the registration date of the person.
     *
     * @return the registration date of the person
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets the registration date of the person.
     *
     * @param registrationDate the registration date of the person
     * @throws NullArgumentException if the provided registration date is null
     * @throws IncorrectDataException if the provided registration date is incorrect
     */
    public void setRegistrationDate(Date registrationDate) throws NullArgumentException, IncorrectDataException {
        if (registrationDate != null) {
            if (VerificatorSetter.isValidBirthday(registrationDate)) {
                this.registrationDate = registrationDate;
            } else {
                throw new IncorrectDataException("La fecha es incorrecta, revise los datos");
            }
        } else {
            throw new NullArgumentException("Has introducido datos nulos a la hora de crear la persona");
        }
    }

    /**
     * Returns the type of user.
     *
     * @return the type of user
     */
    public TypeUser getTypeUser() {
        return typeUser;
    }

    /**
     * Sets the type of user.
     *
     * @param typeUser the type of user
     * @throws NullArgumentException if the provided type of user is null
     */
    public void setTypeUser(TypeUser typeUser) throws NullArgumentException {
        if (typeUser != null) {
            this.typeUser = typeUser;
        } else {
            throw new NullArgumentException("Has introducido datos nulos a la hora de crear la persona");
        }
    }

    /**
     * Returns the password script of the person.
     *
     * @return the password script of the person
     */
    public String getPassScript() {
        return passScript;
    }

    /**
     * Sets the password script of the person.
     *
     * @param passScript the password script of the person
     * @throws NullArgumentException if the provided password script is null
     * @throws NoSuchAlgorithmException if a security provider is not available
     * @throws InvalidKeySpecException if the provided key specification is invalid
     */
    public void setPassScript(String passScript) throws NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (passScript != null) {
            this.passScript = FunctionsApp.generateStrongPasswordHash(passScript);
        } else {
            throw new NullArgumentException("Has introducido datos nulos a la hora de crear la persona");
        }
    }

    /**
     * Returns the last activity date of the person.
     *
     * @return the last activity date of the person
     */
    public Date getLastActivityDate() {
        return lastActivityDate;
    }

    /**
     * Sets the last activity date of the person.
     *
     * @param lastActivity the last activity date of the person
     * @throws NullArgumentException if the provided last activity date is null
     */
    public void setLastActivityDate(Date lastActivity) throws NullArgumentException {
        if (lastActivity != null) {
            if (VerificatorSetter.isValidBirthday(lastActivity)) {
                this.lastActivityDate = lastActivity;
            } else {
                throw new IllegalArgumentException("Verifica los datos introducidos, la fecha no es correcta");
            }
        } else {
            throw new NullArgumentException("Has pasado argumentos nulos a la hora de crear el psicólogo");
        }
    }

    /**
     * Returns the state of the person.
     *
     * @return the state of the person
     */
    public StatesUser getState() {
        return state;
    }

    /**
     * Sets the state of the person.
     *
     * @param state the state of the person
     */
    public void setState(StatesUser state) {
        this.state = state;
    }

    /**
     * Returns a string representation of the person.
     *
     * @return a string representation of the person
     */
    @Override
    public String toString() {
        return "Person{" + "idPerson=" + idPerson + ", names='" + names + '\'' + ", lastNames='" + lastNames + '\'' + ", direction=" + direction + ", email='" + email + '\'' + ", birthday=" + birthDate + ", registrationDate=" + registrationDate + ", passScript='" + passScript + '\'' + ", typeUser=" + typeUser + ", stateUser=" + state + ", postTreeMap=" + postTreeMap + ", lastActivityDate=" + lastActivityDate + '}';
    }

}

