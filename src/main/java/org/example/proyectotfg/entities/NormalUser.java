package org.example.proyectotfg.entities;

import org.example.proyectotfg.exceptions.IncorrectDataException;
import org.example.proyectotfg.exceptions.NullArgumentException;
import org.example.proyectotfg.enumAndTypes.StatesUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.functions.VerificatorSetter;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;



public class NormalUser extends Person {
    private String nickname;
    private Deque<ProfessionalUser> serchs = new LinkedList<>();
    private List<ProfessionalUser> psychologists = new ArrayList<>();
    private boolean inTherapySession;

    /**
     * Constructs a new NormalUser object with the specified parameters.
     *
     * @param id          The ID of the normal user.
     * @param names             The names of the normal user.
     * @param lastNames         The last names of the normal user.
     * @param scrippPass        The script password of the normal user.
     * @param birrth            The birth date of the normal user.
     * @param registration      The registration date of the normal user.
     * @param email             The email of the normal user.
     * @param type              The type of user.
     * @param state             The state of the normal user.
     * @param direction         The direction of the normal user.
     * @param lastActivity      The last activity date of the normal user.
     * @param nickname          The nickname of the normal user.
     * @throws IncorrectDataException     If the provided data is incorrect.
     * @throws NoSuchAlgorithmException  If a required cryptographic algorithm is not available in the environment.
     * @throws InvalidKeySpecException    If the provided key specification is inappropriate for the cryptographic operation.
     * @throws NullArgumentException     If any argument is null.
     */
    public NormalUser(int id, String names, String lastNames, String scrippPass,
                      Date birrth, Date registration, String email, TypeUser type,
                      StatesUser state, Direction direction, Date lastActivity, String nickname) throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        super(id, names, lastNames, scrippPass, birrth, registration, email, type, state, direction, lastActivity);
        setNickname(nickname);
        setInTherapySession(false);
    }
    /**
     * Constructs a new NormalUser object with the specified parameters.
     *
     * @param names             The names of the normal user.
     * @param lastNames         The last names of the normal user.
     * @param passScript        The script password of the normal user.
     * @param birthday            The birth date of the normal user.
     * @param registrationDate      The registration date of the normal user.
     * @param email             The email of the normal user.
     * @param typeUser              The type of user.
     * @param direction         The direction of the normal user.
     * @throws IncorrectDataException     If the provided data is incorrect.
     * @throws NoSuchAlgorithmException  If a required cryptographic algorithm is not available in the environment.
     * @throws InvalidKeySpecException    If the provided key specification is inappropriate for the cryptographic operation.
     * @throws NullArgumentException     If any argument is null.
     */
    public NormalUser(String names, String lastNames, String passScript, Date birthday,
                      Date registrationDate, String email, TypeUser typeUser, Direction direction) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        super(names, lastNames, passScript, birthday, registrationDate, email, typeUser, direction);
        this.inTherapySession = false;
    }
    /**
     * Constructs a new NormalUser object with the specified parameters.
     * @param idPerson          The ID of the normal user.
     * @param names             The names of the normal user.
     * @param lastNames         The last names of the normal user.
     * @param passScript        The script password of the normal user.
     * @param birthday            The birth date of the normal user.
     * @param registrationDate      The registration date of the normal user.
     * @param email             The email of the normal user.
     * @param typeUser              The type of user.
     * @param state             The state of the normal user.
     * @param direction         The direction of the normal user.
     * @throws IncorrectDataException     If the provided data is incorrect.
     * @throws NoSuchAlgorithmException  If a required cryptographic algorithm is not available in the environment.
     * @throws InvalidKeySpecException    If the provided key specification is inappropriate for the cryptographic operation.
     * @throws NullArgumentException     If any argument is null.
     */
    public NormalUser(int idPerson, String names, String lastNames, String passScript, Date birthday,
                      Date registrationDate, String email, TypeUser typeUser, StatesUser state, Direction direction, Date lastActivityDate, String nickname, boolean inTherapySession) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        super(idPerson, names, lastNames, passScript, birthday, registrationDate, email, typeUser, state, direction, lastActivityDate);
        setNickname(nickname);
        setInTherapySession(inTherapySession);
    }

    /**
     * Constructs a new NormalUser object with the specified parameters.
     * @param idPerson          The ID of the normal user.
     * @param names             The names of the normal user.
     * @param lastNames         The last names of the normal user.
     * @param passScript        The script password of the normal user.
     * @param birthDate            The birth date of the normal user.
     * @param registrationDate      The registration date of the normal user.
     * @param email             The email of the normal user.
     * @param typeUser              The type of user.
     * @param state             The state of the normal user.
     * @param direction         The direction of the normal user.
     * @param lastActivityDate      The last activity date of the normal user.
     * @throws IncorrectDataException     If the provided data is incorrect.
     * @throws NoSuchAlgorithmException  If a required cryptographic algorithm is not available in the environment.
     * @throws InvalidKeySpecException    If the provided key specification is inappropriate for the cryptographic operation.
     * @throws NullArgumentException     If any argument is null.
     */
    public NormalUser(int idPerson, String names, String lastNames, String passScript, Date birthDate,
                      Date registrationDate, String email, TypeUser typeUser, StatesUser state,
                      Direction direction, Date lastActivityDate) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        super(idPerson, names, lastNames, passScript, birthDate, registrationDate, email, typeUser, state, direction, lastActivityDate);
    }

    /**
     * Constructs a new NormalUser object with the specified parameters.
     *
     * @param idPerson          The ID of the normal user.
     * @param names             The names of the normal user.
     * @param lastNames         The last names of the normal user.
     * @param mail              The email of the normal user.
     * @param nueva             The direction of the normal user.
     * @throws IncorrectDataException       If the provided data is incorrect.
     * @throws NullArgumentException        If any argument is null.
     */
    public NormalUser(int idPerson, String names, String lastNames, String mail, Direction nueva) throws IncorrectDataException, NullArgumentException {
        super(idPerson, names, lastNames, mail, nueva);
        setNickname(names+" "+lastNames);
    }

    /**
     * Constructs a new NormalUser object with the specified parameters.
     *
     * @param idPerson          The ID of the normal user.
     * @param names             The names of the normal user.
     * @param lastNames         The last names of the normal user.
     * @param mail              The email of the normal user.
     * @param nueva             The direction of the normal user.
     * @param typeUser          The type of user.
     * @throws IncorrectDataException       If the provided data is incorrect.
     * @throws NullArgumentException        If any argument is null.
     */
    public NormalUser(int idPerson, String names, String lastNames, String mail, Direction nueva, TypeUser typeUser) throws IncorrectDataException, NullArgumentException {
        super(idPerson, names, lastNames, mail, nueva, typeUser);
        setNickname(names+" "+lastNames);
    }

    /**
     * Constructs a new NormalUser object with the specified parameters.
     *
     * @param idP               The ID of the normal user.
     * @param names             The names of the normal user.
     * @param lastNames         The last names of the normal user.
     * @param pass1             The script password of the normal user.
     * @param mail              The email of the normal user.
     * @param nueva             The direction of the normal user.
     * @param type              The type of user.
     * @throws IncorrectDataException       If the provided data is incorrect.
     * @throws NullArgumentException        If any argument is null.
     * @throws NoSuchAlgorithmException    If a required cryptographic algorithm is not available in the environment.
     * @throws InvalidKeySpecException      If the provided key specification is inappropriate for the cryptographic operation.
     */
    public NormalUser(int idP, String names, String lastNames, String pass1, String mail, Direction nueva, TypeUser type) throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        super(idP, names, lastNames, pass1, mail, nueva, type);
        setNickname(names+" "+lastNames);
    }

    /**
     * Constructs a new NormalUser object with the specified parameters.
     *
     * @param nickname          The nickname of the normal user.
     * @param inTherapySession  The therapy session status of the normal user.
     * @throws IncorrectDataException       If the provided data is incorrect.
     * @throws NullArgumentException        If any argument is null.
     * @throws NoSuchAlgorithmException    If a required cryptographic algorithm is not available in the environment.
     * @throws InvalidKeySpecException      If the provided key specification is inappropriate for the cryptographic operation.
     */
    public NormalUser(String nickname, String inTherapySession) throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        super();
        setNickname(nickname);
        setInTherapySession(Boolean.parseBoolean(inTherapySession));
    }

    public NormalUser() throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        super();

    }

    /**
     * Retrieves the nickname of the normal user.
     *
     * @return The nickname of the normal user.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the nickname of the normal user.
     *
     * @param nickname The nickname to set for the normal user.
     * @throws NullArgumentException    If the provided nickname is null.
     */
    public void setNickname(String nickname) throws NullArgumentException {
        if (nickname != null) {
            if (VerificatorSetter.stringVerificator(nickname, 100)) {
                this.nickname = nickname;
            } else {
                throw new NullArgumentException("Has introducido uno o más argumentos nulo a la hora de crear el usuario");
            }
        } else {
            this.nickname = "null";
        }
    }

    /**
     * Checks if the normal user is in a therapy session.
     *
     * @return True if the normal user is in a therapy session, false otherwise.
     */
    public boolean isInTherapySession() {
        return inTherapySession;
    }

    /**
     * Sets whether the normal user is in a therapy session or not.
     *
     * @param inTherapySession The status to set for the therapy session of the normal user.
     */
    public void setInTherapySession(boolean inTherapySession) {
        this.inTherapySession = inTherapySession;
    }

    /**
     * Returns a string representation of the NormalUser object.
     *
     * @return A string representation of the NormalUser object.
     */
    @Override
    public String toString() {
        return "NormalUser{" + "nickname='" + nickname + '\'' + ", serchs=" + serchs + ", psychologists=" + psychologists + ", inTherapySession=" + inTherapySession + '}';
    }

}
