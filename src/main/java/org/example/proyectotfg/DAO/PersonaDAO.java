package org.example.proyectotfg.DAO;

import org.example.proyectotfg.entities.*;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.exceptions.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface PersonaDAO {

    void createTables() throws OperationsDBException;

    void registerProfessionalUser(ProfessionalUser professionalUser, boolean update) throws OperationsDBException, DuplicateKeyException;

    void registerNormalUser(NormalUser normalUser, boolean update) throws OperationsDBException, DuplicateKeyException;

    Direction registerDirection(Direction direction) throws OperationsDBException;

    Person loginUser(String usuario, String cripp_pass, TypeUser typeUser) throws IncorrectLoginEception, NonexistingUser, OperationsDBException, DataAccessException, NoSuchAlgorithmException, InvalidKeySpecException;

    int searchIdDirection(Direction direction) throws OperationsDBException;

    Person registerPerson(Person person) throws OperationsDBException, DuplicateKeyException;

    List<ProfessionalUser> searchProfessionalsUsers(String nameUser) throws NonexistingUser, DataAccessException, OperationsDBException;

    boolean addProfesionalUserInFavorites(ProfessionalUser professionalUser, Person person) throws OperationsDBException, NonexistingUser, DuplicateKeyException;


    List<MedicalAppointment> searchMedicalAppointments(int id, Date date) throws OperationsDBException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException;

    List<MedicalAppointment> searchMyAppointments(Person person) throws OperationsDBException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException;

    boolean insertMedicalAppointments(MedicalAppointment medicalAppointment) throws OperationsDBException;

    boolean updateMedicalAppointment(MedicalAppointment medicalAppointment, Date dateAppointment) throws OperationsDBException;
    String searchNameForPerson(int idPerson) throws OperationsDBException;
}
