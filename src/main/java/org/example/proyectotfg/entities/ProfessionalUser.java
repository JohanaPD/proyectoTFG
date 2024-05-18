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


public class ProfessionalUser extends Person {
    private String collegiate;
    private String specialty;
    private String description;
    private List<NormalUser> patients = new ArrayList<>();



    public ProfessionalUser(int idPerson, String names, String lastNames, String passScript, Date birthDate, Date registrationDate, String email, TypeUser typeUser, StatesUser stateUser, Direction direction, Date lastActivityDate, String collegiate, String specialty, String description) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        super(idPerson,names,lastNames,passScript,birthDate,registrationDate,email,typeUser,stateUser,direction,lastActivityDate);
        setCollegiate(collegiate);
        setSpecialty(specialty);
        setDescription(description);
    }
    public ProfessionalUser(String names, String lastNames, String passScript, Date birthDate, Date registrationDate, String email, TypeUser typeUser, Direction direction, String collegiate, String specialty, String description) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        super(names, lastNames, passScript, birthDate, registrationDate, email, typeUser,  direction);
        setCollegiate(collegiate);
        setSpecialty(specialty);
        setDescription(description);
    }
    public ProfessionalUser(String collegiate, String specialty, String description) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        super();
        setCollegiate(collegiate);
        setSpecialty(specialty);
        setDescription(description);
    }

    public ProfessionalUser() throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        super();

    }

    public ProfessionalUser(int idPerson, String names, String lastNames, String passScript, Date birthDate, Date registrationDate, String email, TypeUser typeUser, StatesUser stateUser,Direction direction , Date lastActivity) throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        super(idPerson, names, lastNames, passScript, birthDate, registrationDate, email, typeUser, stateUser, direction, lastActivity);
    }

    public ProfessionalUser(String names, String lastNames, Date birthd, String mail, TypeUser tipeUs, Direction nueva, String college, String especialidad, String descripcion) throws IncorrectDataException, NullArgumentException {
   super(names,lastNames,birthd,mail,tipeUs,nueva);
        setCollegiate(collegiate);
        setSpecialty(specialty);
        setDescription(description);
    }


    public String getCollegiate() {
        return collegiate;
    }


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


    public String getSpecialty() {
        return specialty;
    }


    public void setSpecialty(String specialty) throws NullArgumentException, IncorrectDataException {
        if (specialty != null) {
            if (VerificatorSetter.stringVerificator(specialty, specialty.length())) {
                this.specialty = specialty;
            } else {
                throw new IncorrectDataException("Verifica los datos introducidos, solo se aceptan letras");
            }
        } else {
            throw new NullArgumentException("Has pasado argumentos nulos a la hora de crear el psicólogo");
        }
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) throws NullArgumentException, IncorrectDataException {
        if (description != null) {
            if (VerificatorSetter.stringVerificator(description, description.length()))
                this.description = description;
            else throw new IncorrectDataException("Verifica los datos introducidos, solo se aceptan letras");


        } else {
            throw new NullArgumentException("Has pasado argumentos nulos a la hora de crear el psicólogo");
        }
    }




    public List<NormalUser> getPatients() {
        return patients;
    }


    public void setPatients(List<NormalUser> patients) {
        this.patients = patients;
    }




    @Override
    public String toString() {
        return "ProfessionalUser{" +
                "collegiate='" + collegiate + '\'' +
                ", speciality='" + specialty + '\'' +
                ", description='" + description + '\'' +
                ", patients=" + patients +
                '}';
    }
}



