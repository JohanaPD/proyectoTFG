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

public abstract class Person {

    private int idPerson;
    private String names;
    private String lastNames;
    private String passScript;
    private Date birthDate;
    private Date registrationDate;
    private String email;
    //  private int age;
    private TypeUser typeUser;
    private StatesUser state;
    private TreeMap<Date, Post> postTreeMap = new TreeMap<>();
    private Date lastActivityDate;
    private Direction direction;

    public Person() throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        this("SINDATOS", "SINDATOS", "SINDATOS", new Date(), new Date(), "SINDATOS@SINDATOS.COM", TypeUser.USUARIO_NORMAL, new Direction("SINDATOS", "SINDATOS", 00001));
        setState(StatesUser.NOT_VERIFIED);
        //setLastActivityDate(new Date());
    }

    //Constructor para el registro
    public Person(String names, String lastNames, String passScript, Date birthDate, Date registrationDate, String email, TypeUser typeUser, Direction direction) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
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

    //entra aquí
    public Person(int idPerson, String names, String lastNames, String passScript, Date birthDate, Date registrationDate, String email, TypeUser typeUser, StatesUser state, Direction direction, Date lastActivityDate) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
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

    public Person(String names, String lastNames, Date birthd, String mail, TypeUser tipeUs, Direction nueva) throws IncorrectDataException, NullArgumentException {
        setNames(names);
        setLastNames(lastNames);
        setBirthDate(birthDate);
        setEmail(email);
        setTypeUser(typeUser);
        setDirection(direction);
    }

    public Person(String names, String lastNames, String mail, TypeUser type, Direction nueva) throws NullArgumentException, IncorrectDataException {
        setNames(names);
        setLastNames(lastNames);
        setEmail(mail);
        setTypeUser(type);
        setDirection(nueva);
    }

    public Person(String names, String lastNames, String passScript, String mail, TypeUser type, Direction nueva) throws NullArgumentException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException {
        setNames(names);
        setLastNames(lastNames);
        setPassScript(passScript);
        setEmail(mail);
        setTypeUser(type);
        setDirection(nueva);
    }

    public Person(int id, String names, String lastNames, String passScript, String mail, TypeUser type, Direction nueva) throws NullArgumentException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException {
        setIdPerson(id);
        setNames(names);
        setLastNames(lastNames);
        setPassScript(passScript);
        setEmail(mail);
        setTypeUser(type);
        setDirection(nueva);
    }

    public Person(int idPerson, String names, String lastNames, String mail, Direction direction) throws IncorrectDataException, NullArgumentException {
        setIdPerson(idPerson);
        setNames(names);
        setLastNames(lastNames);
        setEmail(mail);
        setDirection(direction);
    }

    public Person(int idPerson, String names, String lastNames, String mail, Direction direction, TypeUser typeUser) throws IncorrectDataException, NullArgumentException {
        setIdPerson(idPerson);
        setNames(names);
        setLastNames(lastNames);
        setEmail(mail);
        setDirection(direction);
        setTypeUser(typeUser);
    }

    public Person(int idPerson, String names, String lastNames, String pass, String mail, Direction direction) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        setIdPerson(idPerson);
        setNames(names);
        setLastNames(lastNames);
        setPassScript(pass);
        setEmail(mail);
        setDirection(direction);
    }

    public Person(int idPerson, String names, String lastNames, String pass, String mail, Direction direction, TypeUser type) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        setIdPerson(idPerson);
        setNames(names);
        setLastNames(lastNames);
        setPassScript(pass);
        setEmail(mail);
        setDirection(direction);
        setTypeUser(type);
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) throws IncorrectDataException {
        if (VerificatorSetter.numberVerificator(idPerson, 1000000000) && idPerson >= 0) {
            this.idPerson = idPerson;
        } else {
            throw new IncorrectDataException("Verifica el id de persona, solo se aceptan números");
        }
    }


    public String getNames() {
        return names;
    }


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

    public String getLastNames() {
        return lastNames;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) throws NullArgumentException, IncorrectDataException {
        if (direction != null) {
            this.direction = direction;
        } else {
            throw new NullArgumentException("Has introducido datos nulos a la hora de crear la persona");
        }
    }


    public TreeMap<Date, Post> getPostTreeMap() {
        return postTreeMap;
    }


    public void setPostTreeMap(TreeMap<Date, Post> postTreeMap) {
        this.postTreeMap = postTreeMap;
    }

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

    public String getEmail() {
        return email;
    }

    public Direction getDireccion() {
        return direction;
    }


    public void setDireccion(Direction direction) throws NullArgumentException {
        if (direction != null) {
            this.direction = direction;
        } else {
            throw new NullArgumentException("Has introducido datos nulos a la hora de crear la persona");
        }
    }


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


    public Date getBirthDate() {
        return birthDate;
    }


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


  /* public int getAge() {
       return age;
   }


   public void setAge(int age) throws NullArgumentException, IncorrectDataException {
       if (VerificatorSetter.esMayorDeEdad(age)) {
           this.age = age;
       } else {
           throw new IncorrectDataException("La edad tiene que ser mayor de 18 años");
       }
   }*/


    public Date getRegistrationDate() {
        return registrationDate;
    }


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


    public TypeUser getTypeUser() {
        return typeUser;
    }


    public void setTypeUser(TypeUser typeUser) throws NullArgumentException {
        if (typeUser != null) {
            this.typeUser = typeUser;
        } else {
            throw new NullArgumentException("Has introducido datos nulos a la hora de crear la persona");
        }


    }


    public String getPassScript() {
        return passScript;
    }


    public void setPassScript(String passScript) throws NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (passScript != null) {
            this.passScript = FunctionsApp.generateStrongPasswordHash(passScript);
        } else {
            throw new NullArgumentException("Has introducido datos nulos a la hora de crear la persona");
        }
    }


    public Date getLastActivityDate() {
        return lastActivityDate;
    }


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


    public StatesUser getState() {
        return state;
    }

    public void setState(StatesUser state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Person{" + "idPerson=" + idPerson + ", names='" + names + '\'' + ", lastNames='" + lastNames + '\'' + ", direction=" + direction + ", email='" + email + '\'' + ", birthday=" + birthDate + ", registrationDate=" + registrationDate + ", passScript='" + passScript + '\'' + ", typeUser=" + typeUser + ", stateUser=" + state + ", postTreeMap=" + postTreeMap + ", lastActivityDate=" + lastActivityDate + '}';
    }
}

