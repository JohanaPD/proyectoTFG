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

    public NormalUser(int id, String nombres, String apellidos, String scrippPass, Date birrth, Date registration, String email, TypeUser type, StatesUser state, Direction direction, Date lastActivity, String nickname) throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        super(id, nombres, apellidos, scrippPass, birrth, registration, email, type, state, direction, lastActivity);
        setNickname(nickname);
        setInTherapySession(false);
    }

    public NormalUser(String names, String lastNames, String passScript, Date birthday, Date registrationDate, String email, TypeUser typeUser, Direction direction) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        super(names, lastNames, passScript, birthday, registrationDate, email, typeUser, direction);
        this.inTherapySession = false;
    }

    public NormalUser(int idPerson, String names, String lastNames, String passScript, Date birthday, Date registrationDate, String email, TypeUser typeUser, StatesUser state, Direction direction, Date lastActivityDate, String nickname, boolean inTherapySession) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        super(idPerson, names, lastNames, passScript, birthday, registrationDate, email, typeUser, state, direction, lastActivityDate);
        setNickname(nickname);
        setInTherapySession(inTherapySession);
    }

    //constructor for test
    public NormalUser(int idPerson, String names, String lastNames, String passScript, Date birthDate, Date registrationDate, String email, TypeUser typeUser, StatesUser state, Direction direction, Date lastActivityDate) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        super(idPerson, names, lastNames, passScript, birthDate, registrationDate, email, typeUser, state, direction, lastActivityDate);
    }

    public NormalUser() throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        super();
    }

    public NormalUser(String names, String lastNames, String mail, TypeUser type, Direction nueva) throws IncorrectDataException, NullArgumentException {
        super(names, lastNames, mail, type, nueva);
        setNickname(names+" "+lastNames);
    }

    public NormalUser(int idPerson, String names, String lastNames, String mail, Direction nueva) throws IncorrectDataException, NullArgumentException {
        super(idPerson, names, lastNames, mail, nueva);
        setNickname(names+" "+lastNames);
    }

    public NormalUser(int idPerson, String names, String lastNames, String mail, Direction nueva, TypeUser typeUser) throws IncorrectDataException, NullArgumentException {
        super(idPerson, names, lastNames, mail, nueva, typeUser);
        setNickname(names+" "+lastNames);

    }

    public NormalUser(int idP, String names, String lastNames, String pass1, String mail, Direction nueva, TypeUser type) throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        super(idP, names, lastNames, pass1, mail, nueva, type);
        setNickname(names+" "+lastNames);
    }

    public NormalUser(String nickname, String inTherapySession) throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        super();
        setNickname(nickname);
        setInTherapySession(Boolean.parseBoolean(inTherapySession));
    }

    public String getNickname() {
        return nickname;
    }

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


    public boolean isInTherapySession() {
        return inTherapySession;
    }

    public void setInTherapySession(boolean inTherapySession) {
        this.inTherapySession = inTherapySession;
    }


    @Override
    public String toString() {
        return "NormalUser{" + "nickname='" + nickname + '\'' + ", serchs=" + serchs + ", psychologists=" + psychologists + ", inTherapySession=" + inTherapySession + '}';
    }
}
