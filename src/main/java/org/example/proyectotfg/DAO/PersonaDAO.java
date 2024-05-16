package org.example.proyectotfg.DAO;

import org.example.proyectotfg.entities.Direction;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.NormalUser;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.exceptions.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface PersonaDAO {
//void crear bbdd si no existe

    void createTables();
    void buscarPorNombreoEspecialidad(String tabla, String busqueda);
    NormalUser buscarPaciente(String nombre)throws OperationsDBException;
    ProfessionalUser buscarPsicologo(String nombre)throws OperationsDBException;
    void registerProfessionalUser(ProfessionalUser professionalUser)throws OperationsDBException, DuplicateKeyException;
    void registerNormalUser(NormalUser normalUser)throws OperationsDBException,DuplicateKeyException;
    Direction registerDirection(Direction direction)throws OperationsDBException;
    Person loginUser(String usuario, String cripp_pass, TypeUser typeUser) throws IncorrectLoginEception,NonexistingUser, OperationsDBException,DataAccessException, NoSuchAlgorithmException, InvalidKeySpecException;
    public int searchIdDirection(Direction direction) throws OperationsDBException;
    public Person registerPerson(Person person)throws OperationsDBException, DuplicateKeyException;


}
