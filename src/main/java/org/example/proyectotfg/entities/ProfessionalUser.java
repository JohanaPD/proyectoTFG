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
    private String speciality;
    private String description;
    private List<NormalUser> patients = new ArrayList<>();



    public ProfessionalUser(int idPerson, String names, String lastNames, String passScript, Date birthDate, Date registrationDate, String email, TypeUser typeUser, StatesUser stateUser,Direction direction, Date lastActivityDate, String collegiate, String speciality, String description) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        super(idPerson,names,lastNames,passScript,birthDate,registrationDate,email,typeUser,stateUser,direction,lastActivityDate);
        setCollegiate(collegiate);
        setSpeciality(speciality);
        setDescription(description);
    }
    public ProfessionalUser(String names, String lastNames, String passScript, Date birthDate, Date registrationDate, String email, TypeUser typeUser, Direction direction, String collegiate, String speciality, String description) throws IncorrectDataException, NullArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        super(names, lastNames, passScript, birthDate, registrationDate, email, typeUser,  direction);
        setCollegiate(collegiate);
        setSpeciality(speciality);
        setDescription(description);
    }

    public ProfessionalUser() throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        super();

    }

    public ProfessionalUser(int idPerson, String names, String lastNames, String passScript, Date birthDate, Date registrationDate, String email, TypeUser typeUser, StatesUser stateUser,Direction direction , Date lastActivity) throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        super(idPerson, names, lastNames, passScript, birthDate, registrationDate, email, typeUser, stateUser, direction, lastActivity);
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


    public String getSpeciality() {
        return speciality;
    }


    public void setSpeciality(String speciality) throws NullArgumentException, IncorrectDataException {
        if (speciality != null) {
            if (VerificatorSetter.stringVerificator(speciality, speciality.length())) {
                this.speciality = speciality;
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
                ", speciality='" + speciality + '\'' +
                ", description='" + description + '\'' +
                ", patients=" + patients +
                '}';
    }
}



