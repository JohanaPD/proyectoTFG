package org.example.proyectotfg.DAO;

import org.example.proyectotfg.entities.*;
import org.example.proyectotfg.enumAndTypes.Notificators;
import org.example.proyectotfg.enumAndTypes.StatesUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.exceptions.*;
import org.example.proyectotfg.functions.FunctionsApp;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class SqliteConnector implements AutoCloseable, PersonaDAO {

    static final String URL = "jdbc:sqlite:src/main/resources/sqliteBBDD/MeetPsych.db";
    static Connection connection;

    public SqliteConnector() throws SQLException, OperationsDBException {
        this.connection = DriverManager.getConnection(URL);
        createTables();
        if (connection != null) {
            System.out.println("Se ha conectado a ella exitosamente.");
        }
    }
  /*   ================================================================================================
        ======================================Crear BBDD y tablas =====================================================*/

    @Override
    public void createTables() throws OperationsDBException {

        String consultaDireccion = "CREATE TABLE IF NOT EXISTS  direction(" + "id_direction INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + "street VARCHAR(150) NOT NULL," + "city VARCHAR(100) NOT NULL," + "postal_code INTEGER" + ");";

        String createPerson = "CREATE TABLE IF NOT EXISTS  person(" + "id_person INTEGER PRIMARY KEY  AUTOINCREMENT," + "user_names VARCHAR(100) NOT NULL," + "last_names VARCHAR(150) NOT NULL," + "pass_script TEXT NOT NULL," + "date_birth DATE NOT NULL," + "registration_date DATE NOT NULL," + "email VARCHAR(150) NOT NULL," + "type_user VARCHAR(50) NOT NULL," + //-- 0 para false, 1 para true
                "user_state VARCHAR(20) NOT NULL," + "last_activity DATE NOT NULL," + "id_direction INTEGER NOT NULL," + "foreign key (id_direction) references direction(id_direction)" + ");";

        String queryPsychologist = "CREATE TABLE IF NOT EXISTS  professional_user(" + "id_person INTEGER PRIMARY KEY," + "collegiate VARCHAR(200) UNIQUE NOT NULL," + "specialty VARCHAR(150) NOT NULL," + "description VARCHAR(10000) NOT NULL," + "FOREIGN KEY(id_person) REFERENCES person(id_person) );";

        String consultaUsuarioNormal = "CREATE TABLE IF NOT EXISTS  normal_user(" + "id_person INTEGER PRIMARY KEY," + "nickname VARCHAR(40) UNIQUE NOT NULL," + "in_therapy_session INTEGER NOT NULL," + //-- 0 para false, 1 para true
                "FOREIGN KEY(id_person) REFERENCES person(id_person) );";

        String consultaPost = "CREATE TABLE IF NOT EXISTS post(" + "id_post INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + "title VARCHAR(250) NOT NULL," + "content TEXT NOT NULL," + "url_image VARCHAR(500) NOT NULL," + "date_post DATE NOT NULL, " + "id_person INTEGER NOT NULL," + "FOREIGN KEY(id_person) REFERENCES person(id_person) ON DELETE CASCADE);";

        String consultaHistorial = "CREATE TABLE IF NOT EXISTS history(" + "id_history INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + "id_user INTEGER NOT NULL," + "FOREIGN KEY(id_user) REFERENCES person(id_user) ON DELETE CASCADE);";

        String consultaDiagnostico = "CREATE TABLE IF NOT EXISTS diagnose(" + "id_diagnose INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + "id_title TEXT NOT NULL," + "id_content TEXT NOT NULL);";

        String consultaDiagnosticosHistorial = "CREATE TABLE IF NOT EXISTS diagnoses_history(" + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + "id_diagnose INTEGER NOT NULL," + "id_history INTEGER NOT NULL," + "FOREIGN KEY(id_diagnose) REFERENCES diagnose(id_diagnose) ON DELETE CASCADE," + "FOREIGN KEY(id_history) REFERENCES history(id_history) ON DELETE CASCADE);";

        String consultaCitas = "CREATE TABLE IF NOT EXISTS medical_appointment (" + "id_appointment INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + "id_professional INTEGER NOT NULL," + "id_normal_user INTEGER NOT NULL," + "visit_date DATE NOT NULL," + "notification TEXT NOT NULL," + "FOREIGN KEY(id_normal_user) REFERENCES normal_user(id_person) ON DELETE CASCADE," + "FOREIGN KEY(id_professional) REFERENCES professional_user(id_person) ON DELETE CASCADE);";

        String favoritesProfesional = "CREATE TABLE IF NOT EXISTS favorites_professionals (" + "id_normal_user INTEGER  NOT NULL," + "id_profesional_user INTEGER," + "PRIMARY KEY(id_normal_user, id_profesional_user)," + "FOREIGN KEY(id_profesional_user) REFERENCES professional_user(id_person)," + "FOREIGN KEY(id_normal_user) REFERENCES normal_user(id_person) ON DELETE CASCADE);";

        try (Statement stmt = connection.createStatement()) {

            stmt.executeUpdate(consultaDireccion);
            stmt.executeUpdate(createPerson);
            stmt.executeUpdate(queryPsychologist);
            stmt.executeUpdate(consultaUsuarioNormal);
            stmt.executeUpdate(consultaPost);
            stmt.executeUpdate(consultaHistorial);
            stmt.executeUpdate(consultaDiagnostico);
            stmt.executeUpdate(consultaDiagnosticosHistorial);
            stmt.executeUpdate(consultaCitas);
            stmt.executeUpdate(favoritesProfesional);

        } catch (SQLException e) {
            throw new OperationsDBException("Error al generar la base de Datos");
        }
    }

    @Override
    public void buscarPorNombreoEspecialidad(String tabla, String busqueda) {

    }

    @Override
    public NormalUser buscarPaciente(String nombre) {
        return null;
    }

    @Override
    public ProfessionalUser buscarPsicologo(String nombre) {
        return null;
    }

    @Override
    public List<ProfessionalUser> searchProfessionalsUsers(String nameUser) throws NonexistingUser, DataAccessException, OperationsDBException {

        List<ProfessionalUser> professionalUsers = new ArrayList<>();

        String sql = "SELECT u.user_names, p.specialty, p.description FROM professional_user p, person u WHERE (u.user_names LIKE ? OR p.specialty LIKE ?) and p.id_person=u.id_person;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + nameUser + "%");
            statement.setString(2, "%" + nameUser + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                boolean found = false;
                while (resultSet.next()) {
                    found = true;
                    ProfessionalUser nuevo = new ProfessionalUser();
                    nuevo.setNames(resultSet.getString("user_names"));
                    nuevo.setSpecialty(resultSet.getString("specialty"));
                    nuevo.setDescription(resultSet.getString("description"));
                    professionalUsers.add(nuevo);
                }
                if (!found) {
                    throw new NonexistingUser("No se encontraron usuarios con el criterio: " + nameUser);
                }
            } catch (IncorrectDataException | NoSuchAlgorithmException | InvalidKeySpecException |
                     NullArgumentException e) {
                throw new OperationsDBException("Error al obtener los datos del usuario");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al acceder a la base de datos: " + e.getMessage());
        }
        return professionalUsers;
    }

    @Override
    public int searchIdDirection(Direction direction) throws OperationsDBException {

        String consulta = "SELECT id_direction FROM direction WHERE street=? and city=?;";

        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setString(1, direction.getStreet());
            statement.setString(2, direction.getCity());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id_direction");
                return id;
            }
            return -1;
        } catch (SQLException e) {
            throw new OperationsDBException("Error al encontrar la dirección");
        }
    }

    @Override
    public Direction registerDirection(Direction direction) throws OperationsDBException {

        int idBBDD = searchIdDirection(direction);

        if (idBBDD == -1) {
            try {
                String textoConsulta = "insert into direction(street,city,postal_code) values (?,?,?);";
                try (PreparedStatement preparedStatement = connection.prepareStatement(textoConsulta)) {

                    preparedStatement.setString(1, direction.getStreet());
                    preparedStatement.setString(2, direction.getCity());
                    preparedStatement.setInt(3, direction.getPostalCode());
                    preparedStatement.executeUpdate();
                    ResultSet resultSet = preparedStatement.getGeneratedKeys();
                    if (resultSet.next()) {
                        direction.setIdDireccion(resultSet.getInt(1));
                    }

                } catch (IncorrectDataException e) {
                    throw new OperationsDBException("El identificador supera el máximo permitido");
                }
            } catch (SQLException e) {
                throw new OperationsDBException("No se ha podido introducir correctamente la persona en la base de datos");
            }
        } else {
            try {
                direction.setIdDireccion(idBBDD);
            } catch (IncorrectDataException e) {
                throw new OperationsDBException("Error al establecer el id de la dirección");
            }
        }
        return direction;
    }

    @Override
    public Person loginUser(String email, String scripp_pass, TypeUser typeUser) throws IncorrectLoginEception, InvalidKeySpecException, NonexistingUser, OperationsDBException, DataAccessException, NoSuchAlgorithmException {
        Person person = null;
        boolean conectado = false;
        if (email != null && scripp_pass != null) {
            try (Connection connection = DriverManager.getConnection(URL)) {
                int idDirection = 0;
                String sql = "SELECT * FROM PERSON WHERE email = ? and type_user=? ";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, email);
                    statement.setString(2, String.valueOf(typeUser));
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            String hashCode = resultSet.getString("pass_script").trim();
                            boolean scriptPass = FunctionsApp.validatePassword(scripp_pass.trim(), hashCode);
                            if (scriptPass) {
                                conectado = true;
                                int id = resultSet.getInt("id_person");
                                String nombres = resultSet.getString("user_names");
                                String apellidos = resultSet.getString("last_names");
                                Date birrth = resultSet.getDate("date_birth");
                                Date registration = resultSet.getDate("registration_date");
                                TypeUser type = TypeUser.valueOf(resultSet.getString("type_user"));
                                StatesUser state = StatesUser.valueOf(resultSet.getString("user_state"));
                                Date last_activity = resultSet.getDate("last_activity");
                                idDirection = resultSet.getInt("id_direction");
                                Direction direction = chargeDirection(idDirection);
                                if (typeUser.equals(TypeUser.USUARIO_NORMAL)) {
                                    String nickname = chargeNickname(id);
                                    person = new NormalUser(id, nombres, apellidos, scripp_pass, birrth, registration, email, type, state, direction, last_activity, nickname);
                                } else {
                                    person = chargeProfesionalUser(id, nombres, apellidos, scripp_pass, birrth, registration, email, type, state, direction, last_activity);
                                }
                            } else {
                                throw new IncorrectLoginEception("La contraseña es incorrecta");
                            }
                        } else {
                            throw new NonexistingUser("No existe el usuario o no es su tipo correcto");
                        }
                    } catch (SQLException | OperationsDBException | IncorrectDataException | NullArgumentException e) {
                        throw new OperationsDBException("Error al realizar las operaciones" + e.getMessage());
                    } catch (NoSuchAlgorithmException ex) {
                        throw new NoSuchAlgorithmException("Error en la " + ex.getMessage());
                    }
                }
            } catch (SQLException e) {
                throw new DataAccessException("Conexión no establecida");
            }
        }
        return person;
    }

    public Person chargePersonWithNewPassword(String email, String newPassword) throws IncorrectLoginEception, InvalidKeySpecException, NonexistingUser, OperationsDBException, DataAccessException {
        Person person = null;
        if (email != null) {
            try (Connection connection = DriverManager.getConnection(URL)) {
                int idDirection = 0;
                String sql = "SELECT * FROM PERSON WHERE email = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, email);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            int id = resultSet.getInt("id_person");
                            String nombres = resultSet.getString("user_names");
                            String apellidos = resultSet.getString("last_names");
                            Date birrth = resultSet.getDate("date_birth");
                            Date registration = resultSet.getDate("registration_date");//ok
                            TypeUser type = TypeUser.valueOf(resultSet.getString("type_user"));
                            StatesUser state = StatesUser.valueOf(resultSet.getString("user_state"));
                            Date last_activity = resultSet.getDate("last_activity");
                            idDirection = resultSet.getInt("id_direction");
                            Direction direction = chargeDirection(idDirection);//ok
                            if (type.equals(TypeUser.USUARIO_NORMAL)) {
                                String nickname = chargeNickname(id);
                                person = new NormalUser(id, nombres, apellidos, newPassword, birrth, registration, email, type, state, direction, last_activity, nickname);
                            } else {
                                person = chargeProfesionalUser(id, nombres, apellidos, newPassword, birrth, registration, email, type, state, direction, last_activity);
                            }
                        } else {
                            throw new NonexistingUser("No existe el usuario o no es el tipo correcto");
                        }
                    } catch (SQLException | OperationsDBException | IncorrectDataException | NullArgumentException e) {
                        throw new OperationsDBException("Error al realizar las operaciones" + e.getMessage());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                System.out.println("Ups!!! la conexión está fallando!!");
                throw new DataAccessException("Conexión no establecida");
            }
        }
        return person;
    }

    private String chargeNickname(int id) throws OperationsDBException {
        String consulta = "SELECT nickname FROM normal_user WHERE id_person=?;";
        String nickname = "";
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                nickname = resultSet.getString("nickname");
            }
        } catch (SQLException e) {
            throw new OperationsDBException("Error al encontrar la dirección");
        }
        return nickname;
    }

    private static ProfessionalUser chargeProfesionalUser(int id, String nombres, String apellidos, String scripp_pass, Date birrth, Date registration, String email, TypeUser type, StatesUser state, Direction direction, Date last_activity) throws OperationsDBException {
        ProfessionalUser professionalUser = null;
        String consulta = "SELECT * FROM professional_user WHERE id_person=?;";

        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String collegiate = resultSet.getString("collegiate");
                String specialty = resultSet.getString("specialty");
                String description = resultSet.getString("description");
                professionalUser = new ProfessionalUser(id, nombres, apellidos, scripp_pass, birrth, registration, email, type, state, direction, last_activity, collegiate, specialty, description);
            }
        } catch (SQLException | NullArgumentException | IncorrectDataException | NoSuchAlgorithmException |
                 InvalidKeySpecException e) {
            throw new OperationsDBException("Error al encontrar la dirección");
        }
        return professionalUser;
    }

    public static ProfessionalUser chargeProfesionalUserById(int id) throws OperationsDBException {
        ProfessionalUser professionalUser = null;
        String consulta = "SELECT * FROM professional_user WHERE id_person=?;";
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String collegiate = resultSet.getString("collegiate");
                String specialty = resultSet.getString("specialty");
                String description = resultSet.getString("description");
                professionalUser = new ProfessionalUser(collegiate, specialty, description);
            }
        } catch (SQLException | NullArgumentException | IncorrectDataException | NoSuchAlgorithmException |
                 InvalidKeySpecException e) {
            throw new OperationsDBException("Error al encontrar la dirección");
        }
        return professionalUser;
    }

    public static NormalUser chargeNormalUserById(int id) throws OperationsDBException {
        NormalUser normalUser = null;
        String consulta = "SELECT * FROM normal_user WHERE id_person=?;";

        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nickname = resultSet.getString("nickname");
                String inTherapySession = resultSet.getString("in_therapy_session");
                normalUser = new NormalUser(nickname, inTherapySession);
            }
        } catch (SQLException | NullArgumentException | IncorrectDataException | NoSuchAlgorithmException |
                 InvalidKeySpecException e) {
            throw new OperationsDBException("Error al encontrar la dirección");
        }
        return normalUser;
    }

    public static Direction chargeDirection(int idDirection) throws OperationsDBException {
        Direction direction = null;
        String consulta = "SELECT * FROM direction WHERE id_direction=?;";
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setInt(1, idDirection);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String street = resultSet.getString("street");
                String city = resultSet.getString("city");
                int postalCode = resultSet.getInt("postal_code");
                direction = new Direction(idDirection, street, city, postalCode);
            }
        } catch (SQLException | NullArgumentException | IncorrectDataException e) {
            throw new OperationsDBException("Error al encontrar la dirección");
        }
        return direction;
    }

    public int searchIdPerson(Person person) throws OperationsDBException {
        String consulta = "SELECT id_person FROM person WHERE (user_names=? and last_names=?) or email =? ;";
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setString(1, person.getNames());
            statement.setString(2, person.getLastNames());
            statement.setString(3, person.getEmail());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id_person");
                return id;
            }
            return -1;
        } catch (SQLException e) {
            throw new OperationsDBException("Error al encontrar la dirección");
        }
    }

    public static List<ProfessionalUser> getProfesionales() throws SQLException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException, OperationsDBException, NonexistingUser {
        List<ProfessionalUser> usuarios = new ArrayList<>();
        String query2 = "SELECT * FROM person WHERE id_person between 0 and 6";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query2)) {
            while (resultSet.next()) {
                ProfessionalUser user = new ProfessionalUser();
                int id = resultSet.getInt("id_person");
                String nombres = resultSet.getString("user_names");
                String apellidos = resultSet.getString("last_names");
                Date birrth = resultSet.getDate("date_birth");
                Date registration = resultSet.getDate("registration_date");
                String email = resultSet.getString("email");
                String scripp_pass = resultSet.getString("pass_script");
                String typeString = resultSet.getString("type_user");
                TypeUser type = TypeUser.valueOf(typeString);
                String typeState = resultSet.getString("user_state");
                StatesUser state = StatesUser.valueOf(typeState);
                Date last_activity = resultSet.getDate("last_activity");
                int idDirection = resultSet.getInt("id_direction");
                Direction direction = chargeDirection(idDirection);
                Person enviar = new ProfessionalUser(id, nombres, apellidos, scripp_pass, birrth, registration, email, type, state, direction, last_activity);
                user = searchProfesionalPersonForId((ProfessionalUser) enviar);
                if (user.getTypeUser() != TypeUser.USUARIO_NORMAL) {
                    usuarios.add(user);
                }
            }
        } catch (SQLException e) {
            throw new NonexistingUser("Lista vacía, no existen usuarios");
        }
        return usuarios;
    }

    @Override
    public Person registerPerson(Person person) throws OperationsDBException, DuplicateKeyException {
        Direction direction = registerDirection(person.getDirection());
        try {
            int idBBDD = searchIdPerson(person);
            if (idBBDD == -1) {
                String textoConsulta = "insert into person(user_names,last_names,pass_script," +
                        "date_birth,registration_date,email,type_user,user_state,last_activity,id_direction) values (?,?,?,?,?,?,?,?,?,?);";
                try (PreparedStatement preparedStatement = connection.prepareStatement(textoConsulta)) {
                    preparedStatement.setString(1, person.getNames());
                    preparedStatement.setString(2, person.getLastNames());
                    preparedStatement.setString(3, person.getPassScript());
                    preparedStatement.setObject(4, new Date(person.getBirthDate().getTime()));
                    preparedStatement.setObject(5, new Date(person.getRegistrationDate().getTime()));
                    preparedStatement.setString(6, person.getEmail());
                    preparedStatement.setString(7, String.valueOf(person.getTypeUser()));
                    preparedStatement.setString(8, String.valueOf(person.getState()));
                    preparedStatement.setObject(9, new Date(person.getLastActivityDate().getTime()));
                    preparedStatement.setInt(10, person.getDireccion().getIdDireccion());
                    preparedStatement.executeUpdate();
                    ResultSet resultSet = preparedStatement.getGeneratedKeys();
                    if (resultSet.next()) {
                        person.setIdPerson(resultSet.getInt(1));
                    }
                } catch (IncorrectDataException e) {
                    throw new OperationsDBException("El identificador supera el máximo permitido");
                }
            } else {
                throw new DuplicateKeyException("La cuenta ya existe");
            }
        } catch (SQLException e) {
            throw new OperationsDBException("No se ha podido introducir correctamente la persona en la base de datos");
        }
        return person;
    }

    @Override
    public void registerProfessionalUser(ProfessionalUser professionalUser, boolean update) throws OperationsDBException, DuplicateKeyException {
        if (!update) {
            registerPerson(professionalUser);
        }
        try {
            String textoConsulta = "insert into professional_user(id_person,collegiate,specialty,description) values (?,?,?,?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(textoConsulta)) {
                preparedStatement.setInt(1, professionalUser.getIdPerson());
                preparedStatement.setString(2, professionalUser.getCollegiate());
                preparedStatement.setString(3, professionalUser.getSpecialty());
                preparedStatement.setString(4, professionalUser.getDescription());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new OperationsDBException("Se ha producido un error al guardar el usuario profesional,\ncomprueba que el colegiado no esté repetido y si continua el problema consulta con el soporte");
        }
    }

    @Override
    public void registerNormalUser(NormalUser normalUser, boolean update) throws OperationsDBException, DuplicateKeyException {
        if (!update) {
            normalUser = (NormalUser) registerPerson(normalUser);
        }
        try {
            String textoConsulta = "insert into normal_user(id_person,nickname,in_therapy_session) values (?,?,?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(textoConsulta)) {
                preparedStatement.setInt(1, normalUser.getIdPerson());
                preparedStatement.setString(2, normalUser.getNames() + " " + normalUser.getLastNames()/*getNickname()*/);
                preparedStatement.setInt(3, normalUser.isInTherapySession() ? 1 : 0);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new OperationsDBException("Se ha producido un error al guardar el usuario normal");
        }
    }

    private List<ProfessionalUser> cargarHistorialesParaUsuarios() throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException, SQLException, OperationsDBException {
        List<ProfessionalUser> usuariosEs = new ArrayList<>();
        String query2 = "SELECT * FROM person";
        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query2)) {
            while (resultSet.next()) {
                ProfessionalUser person = null;
                int id = resultSet.getInt("id_person");
                String user_names = resultSet.getString("user_names");
                String last_names = resultSet.getString("last_names");
                String pass_script = resultSet.getString("pass_script");
                Date date_birth = resultSet.getDate("date_birth");
                Date registration_date = resultSet.getDate("registration_date");
                String email = resultSet.getString("email");
                String type_user = resultSet.getString("type_user");
                String user_state = resultSet.getString("user_state");
                Date last_activity = resultSet.getDate("last_activity");
                int id_direction = resultSet.getInt("id_direction");
                Direction direction = chargeDirection(id_direction);
                person = searchProfesionalPersonForId(person);
                usuariosEs.add(person);
            }
        }
        return usuariosEs;
    }

    private static ProfessionalUser searchProfesionalPersonForId(ProfessionalUser person) throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException, OperationsDBException {
        String consulta = "SELECT * FROM professional_user WHERE id_person=?;";
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setInt(1, person.getIdPerson());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String collegiate = resultSet.getString("collegiate");
                String specialty = resultSet.getString("specialty");
                String description = resultSet.getString("description");
                person.setCollegiate(collegiate);
                person.setSpecialty(specialty);
                person.setDescription(description);
            }
        } catch (SQLException | NullArgumentException | IncorrectDataException e) {
            throw new OperationsDBException("Error al encontrar datos");
        }
        return person;
    }

    public NormalUser searchNormalUserById(int person) throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException, OperationsDBException {
        String consulta = "SELECT * FROM normal_user WHERE id_person=?;";
        NormalUser newNormalUser = new NormalUser();
        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setInt(1, person);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id_person = person;
                String nickname = resultSet.getString("nickname");
                int in_terapy = resultSet.getInt("in_therapy_session");
                newNormalUser.setIdPerson(id_person);
                newNormalUser.setNickname(nickname);
                if (in_terapy == 0) {
                    newNormalUser.setInTherapySession(false);
                } else {
                    newNormalUser.setInTherapySession(true);
                }
            }
        } catch (SQLException | NullArgumentException | IncorrectDataException e) {
            throw new OperationsDBException("Error al encontrar datos");
        }
        return newNormalUser;
    }

    public void updateProfesionalUserWP(ProfessionalUser nuevo) throws OperationsDBException, SQLException {
        String updatePersonSQL = "UPDATE person SET user_names = ?, last_names = ?, email = ?, id_direction = ?, type_user=? WHERE id_person = ?";
        String updateProfessionalUserSQL = "UPDATE professional_user SET collegiate = ?, specialty = ?, description = ? WHERE id_person = ?";
        String updateDireccion = "UPDATE direction SET street= ? , city= ?, postal_code= ? WHERE id_direction= ?";
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement updatePersonStmt = connection.prepareStatement(updatePersonSQL); PreparedStatement updateDirectionStmt = connection.prepareStatement(updateDireccion); PreparedStatement updateProfessionalUserStmt = connection.prepareStatement(updateProfessionalUserSQL)) {
            connection.setAutoCommit(false);
            updatePersonStmt.setString(1, nuevo.getNames());
            updatePersonStmt.setString(2, nuevo.getLastNames());
            updatePersonStmt.setString(3, nuevo.getEmail());
            updatePersonStmt.setInt(4, nuevo.getDireccion().getIdDireccion());
            updatePersonStmt.setString(5, String.valueOf(nuevo.getTypeUser()));
            updatePersonStmt.setInt(6, nuevo.getIdPerson());

            updateDirectionStmt.setString(1, nuevo.getDirection().getStreet());
            updateDirectionStmt.setString(2, nuevo.getDirection().getCity());
            updateDirectionStmt.setString(3, String.valueOf(nuevo.getDirection().getPostalCode()));
            updateDirectionStmt.setInt(4, nuevo.getDireccion().getIdDireccion());

            updateProfessionalUserStmt.setString(1, nuevo.getCollegiate());
            updateProfessionalUserStmt.setString(2, nuevo.getSpecialty());
            updateProfessionalUserStmt.setString(3, nuevo.getDescription());
            updateProfessionalUserStmt.setInt(4, nuevo.getIdPerson());

            updatePersonStmt.executeUpdate();
            updateDirectionStmt.executeUpdate();
            int isUpdated = updateProfessionalUserStmt.executeUpdate();
            if (isUpdated == 0 && chargeProfesionalUserById(nuevo.getIdPerson()) == null) {
                try {
                    connection.commit();
                    connection.close();
                    registerProfessionalUser(nuevo, true);
                } catch (DuplicateKeyException e) {
                }
            } else {
                connection.commit();
            }
        } catch (SQLException e) {
            connection.rollback();
            throw new OperationsDBException("Error al actualizar los datos del usuario profesional: " + e.getMessage());
        }
        if (chargeNormalUserById(nuevo.getIdPerson()) != null) {
            deleteNormalUser(nuevo.getIdPerson());
        }
    }

    public void updateProfesionalUser(ProfessionalUser user) throws OperationsDBException, SQLException {
        String updatePersonSQL = "UPDATE person SET user_names = ?, last_names = ?, pass_script = ? ,email = ?, id_direction = ?, type_user=? WHERE id_person = ?";
        String updateDireccion = "UPDATE direction SET street= ? , city= ?, postal_code= ? WHERE id_direction= ?";
        String updateProfessionalUser = "UPDATE professional_user SET collegiate=?, specialty=?, description = ? WHERE id_person = ?";
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement updatePersonStmt = connection.prepareStatement(updatePersonSQL); PreparedStatement updateDireccionStmt = connection.prepareStatement(updateDireccion); PreparedStatement updateProfessionalUserStmt = connection.prepareStatement(updateProfessionalUser)) {
            connection.setAutoCommit(false);
            updatePersonStmt.setString(1, user.getNames());
            updatePersonStmt.setString(2, user.getLastNames());
            updatePersonStmt.setString(3, user.getPassScript());
            updatePersonStmt.setString(4, user.getEmail());
            updatePersonStmt.setInt(5, user.getDireccion().getIdDireccion());
            updatePersonStmt.setString(6, String.valueOf(user.getTypeUser()));
            updatePersonStmt.setInt(7, user.getIdPerson());

            updateDireccionStmt.setString(1, user.getDirection().getStreet());
            updateDireccionStmt.setString(2, user.getDirection().getCity());
            updateDireccionStmt.setString(3, String.valueOf(user.getDirection().getPostalCode()));
            updateDireccionStmt.setInt(4, user.getDireccion().getIdDireccion());

            updateProfessionalUserStmt.setString(1, user.getCollegiate());
            updateProfessionalUserStmt.setString(2, user.getSpecialty());
            updateProfessionalUserStmt.setString(3, user.getDescription());

            updatePersonStmt.executeUpdate();
            updateDireccionStmt.executeUpdate();
            int isUpdate = updateProfessionalUserStmt.executeUpdate();
            if (isUpdate == 0 && chargeProfesionalUserById(user.getIdPerson()) == null) {
                try {
                    connection.commit();
                    connection.close();
                    registerProfessionalUser(user, true);
                } catch (DuplicateKeyException e) {
                }
            } else {
                connection.commit();
            }
        } catch (SQLException e) {
            connection.rollback();
        }
        if (chargeNormalUserById(user.getIdPerson()) != null) {
            deleteNormalUser(user.getIdPerson());
        }
    }

    public void updateDataPerson(NormalUser user) throws OperationsDBException, SQLException {
        String updatePersonSQL = "UPDATE person SET user_names = ?, last_names = ?, pass_script = ? ,email = ?, id_direction = ?, type_user=? WHERE id_person = ?";
        String updateDireccion = "UPDATE direction SET street= ? , city= ?, postal_code= ? WHERE id_direction= ?";
        String updateNormalUser = "UPDATE normal_user SET nickname=?, in_therapy_session=? where id_person = ?";
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement updatePersonStmt = connection.prepareStatement(updatePersonSQL); PreparedStatement updateDireccionStmt = connection.prepareStatement(updateDireccion); PreparedStatement updateNormalUserStmt = connection.prepareStatement(updateNormalUser)) {
            connection.setAutoCommit(false);
            updatePersonStmt.setString(1, user.getNames());
            updatePersonStmt.setString(2, user.getLastNames());
            updatePersonStmt.setString(3, user.getPassScript());
            updatePersonStmt.setString(4, user.getEmail());
            updatePersonStmt.setInt(5, user.getDirection().getIdDireccion());
            updatePersonStmt.setString(6, String.valueOf(user.getTypeUser()));
            updatePersonStmt.setInt(7, user.getIdPerson());

            updateDireccionStmt.setString(1, user.getDirection().getStreet());
            updateDireccionStmt.setString(2, user.getDirection().getCity());
            updateDireccionStmt.setString(3, String.valueOf(user.getDirection().getPostalCode()));
            updateDireccionStmt.setInt(4, user.getDireccion().getIdDireccion());
            if (chargeNormalUserById(user.getIdPerson()) != null) {
                updateNormalUserStmt.setString(1, user.getNames() + " " + user.getLastNames());
                updateNormalUserStmt.setBoolean(2, user.isInTherapySession());
                updateNormalUserStmt.setInt(3, user.getIdPerson());
                updateNormalUserStmt.executeUpdate();
                connection.commit();
            } else {
                connection.commit();
                connection.close();
                registerNormalUser(user, true);
            }
            updatePersonStmt.executeUpdate();
            updateDireccionStmt.executeUpdate();
            connection.commit();
        } catch (SQLException | DuplicateKeyException e) {
            connection.rollback();
            throw new OperationsDBException("La cuenta ya existe en la base de datos");
        }
        if (user.getTypeUser() == TypeUser.USUARIO_NORMAL && chargeProfesionalUserById(user.getIdPerson()) != null) {
            deleteProfessionalUser(user.getIdPerson());
        }
    }

    public void updateNormalUserWP(NormalUser nuevo) throws OperationsDBException, SQLException {
        String updatePersonSQL = "UPDATE person SET user_names = ?, last_names = ?, email = ?, id_direction = ?, type_user=? WHERE id_person = ?";
        String updateDirection = "UPDATE direction SET street= ? , city= ?, postal_code= ? WHERE id_direction= ?";
        String updateNormalUser = "UPDATE normal_user SET nickname=?, in_therapy_session=? where id_person = ?";
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement updatePersonStmt = connection.prepareStatement(updatePersonSQL); PreparedStatement updateDirectionStmt = connection.prepareStatement(updateDirection); PreparedStatement updateNormalUserStmt = connection.prepareStatement(updateNormalUser)) {
            connection.setAutoCommit(false);
            updatePersonStmt.setString(1, nuevo.getNames());
            updatePersonStmt.setString(2, nuevo.getLastNames());
            updatePersonStmt.setString(3, nuevo.getEmail());
            updatePersonStmt.setInt(4, nuevo.getDireccion().getIdDireccion());
            updatePersonStmt.setString(5, String.valueOf(nuevo.getTypeUser()));
            updatePersonStmt.setInt(6, nuevo.getIdPerson());

            updateDirectionStmt.setString(1, nuevo.getDirection().getStreet());
            updateDirectionStmt.setString(2, nuevo.getDirection().getCity());
            updateDirectionStmt.setString(3, String.valueOf(nuevo.getDirection().getPostalCode()));
            updateDirectionStmt.setInt(4, nuevo.getDireccion().getIdDireccion());

            updatePersonStmt.executeUpdate();
            updateDirectionStmt.executeUpdate();
            if (chargeNormalUserById(nuevo.getIdPerson()) != null) {
                updateNormalUserStmt.setString(1, nuevo.getNames() + " " + nuevo.getLastNames());
                updateNormalUserStmt.setBoolean(2, nuevo.isInTherapySession());
                updateNormalUserStmt.setInt(3, nuevo.getIdPerson());
                updateNormalUserStmt.executeUpdate();
                connection.commit();
            } else {
                connection.commit();
                connection.close();
                registerNormalUser(nuevo, true);
            }
        } catch (SQLException | DuplicateKeyException e) {
            connection.rollback();
            throw new OperationsDBException("La cuenta ya existe");
        }
        if (nuevo.getTypeUser() == TypeUser.USUARIO_NORMAL && chargeProfesionalUserById(nuevo.getIdPerson()) != null) {
            deleteProfessionalUser(nuevo.getIdPerson());
        }
    }

    private void deleteProfessionalUser(int idPerson) throws OperationsDBException {
        String deleteProfessionalUser = "DELETE FROM professional_user WHERE id_person = ?";
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement deleteProfessionalUserStatement = connection.prepareStatement(deleteProfessionalUser)) {
            connection.setAutoCommit(false);
            deleteProfessionalUserStatement.setInt(1, idPerson);
            deleteProfessionalUserStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new OperationsDBException("Error al realizar las operaciones: " + e.getMessage());
        }
    }

    private void deleteNormalUser(int idPerson) throws OperationsDBException {
        String deleteNormalUser = "DELETE FROM normal_user WHERE id_person = ?";
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement deleteProfessionalUserStatement = connection.prepareStatement(deleteNormalUser)) {
            connection.setAutoCommit(false);
            deleteProfessionalUserStatement.setInt(1, idPerson);
            deleteProfessionalUserStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new OperationsDBException("Error al realizar las operaciones: " + e.getMessage());
        }
    }

    public void makeNewPost(Post nuevo) throws OperationsDBException {
        if (!serchPostByname(nuevo.getTitle())) {
            String insert = "INSERT INTO post (title, content, url_image ,date_post, id_person) VALUES (?, ?, 'src/main/resources/org/example/proyectotfg/imgPost/meditacion.jpg', date('now'), ?)";
            try (PreparedStatement statement = connection.prepareStatement(insert)) {
                statement.setString(1, nuevo.getTitle());
                statement.setString(2, nuevo.getContent());
                statement.setInt(3, nuevo.getTitular().getIdPerson());
                statement.executeUpdate();
                System.out.println("Datos insertados correctamente.");
            } catch (SQLException e) {
                throw new OperationsDBException("Error al realizar las operaciones: " + e.getMessage());
            }
        }
    }

    public boolean serchPostByname(String titulo) throws OperationsDBException {
        boolean existe = false;
        String consulta = "SELECT * FROM post WHERE title LIKE ?";
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparetStmt = connection.prepareStatement(consulta)) {
            preparetStmt.setString(1, titulo);
            try (ResultSet resultSet = preparetStmt.executeQuery()) {
                while (resultSet.next()) {
                    existe = true;
                    break;
                }
            }
        } catch (SQLException e) {
            existe = false;
            throw new OperationsDBException("Error" + e.getMessage());
        }
        return existe;
    }

    public static List<Post> serchPostByPerson(Person person) throws IncorrectDataException, NullArgumentException, OperationsDBException {
        List<Post> listaPost = new ArrayList<>();
        String consulta = "SELECT * FROM post WHERE id_person = ?";
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparetStmt = connection.prepareStatement(consulta)) {
            preparetStmt.setInt(1, person.getIdPerson());
            try (ResultSet resultSet = preparetStmt.executeQuery()) {
                while (resultSet.next()) {
                    int idPost = resultSet.getInt("id_post");
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    String url_image = resultSet.getString("url_image");
                    Post nuevo = new Post(idPost, person, title, content, url_image);
                    listaPost.add(nuevo);
                }
            }
        } catch (SQLException e) {
            throw new OperationsDBException("Error al realizar las operaciones: " + e.getMessage());
        }
        return listaPost;
    }

    @Override
    public boolean addProfesionalUserInFavorites(ProfessionalUser professionalUser, Person person) throws OperationsDBException {
        String consulta = "INSERT INTO favorites_professionals (id_normal_user , id_profesional_user) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparetStmt = connection.prepareStatement(consulta)) {
            preparetStmt.setInt(1, person.getIdPerson());
            preparetStmt.setInt(1, professionalUser.getIdPerson());
            int affectedRows = preparetStmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new OperationsDBException("Error al realizar las operaciones: " + e.getMessage());
        }
    }

    @Override
    public List<MedicalAppointment> searchMedicalAppointments(int id, Date date) throws OperationsDBException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        List<MedicalAppointment> listOfDates = new ArrayList<>();
        String consulta = "SELECT * FROM medical_appointment WHERE id_professional = ?  and  visit_date=? ";

        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparetStmt = connection.prepareStatement(consulta)) {
            preparetStmt.setInt(1, id);
            preparetStmt.setObject(2, new java.sql.Date(date.getTime()));
            try (ResultSet resultSet = preparetStmt.executeQuery()) {
                while (resultSet.next()) {
                    int id_appointment = resultSet.getInt("id_appointment");
                    int id_medical = resultSet.getInt("id_professional");
                    int id_user = resultSet.getInt("id_normal_user");
                    Date visit = resultSet.getDate("visit_date");
                    String notification = resultSet.getString("notification");
                    Notificators notificators = Notificators.valueOf(notification);
                    ProfessionalUser profesionalUser = chargeProfesionalUserById(id_medical);
                    NormalUser normalUser = searchNormalUserById(id_user);
                    MedicalAppointment medicalAppointment = new MedicalAppointment(
                            id_appointment, profesionalUser, normalUser, visit);

                    listOfDates.add(medicalAppointment);
                }
            }
        } catch (SQLException e) {
            throw new OperationsDBException("Error al realizar las operaciones: " + e.getMessage());
        }

        return listOfDates;
    }

    @Override
    public List<MedicalAppointment> searchMyAppointments(Person person) throws OperationsDBException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException {
        List<MedicalAppointment> listOfDates = new ArrayList<>();
        String consulta = "SELECT * FROM medical_appointment WHERE id_normal_user = ? ";

        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparetStmt = connection.prepareStatement(consulta)) {
            preparetStmt.setInt(1, person.getIdPerson());

            try (ResultSet resultSet = preparetStmt.executeQuery()) {
                while (resultSet.next()) {
                    int id_appointment = resultSet.getInt("id_appointment");
                    int id_medical = resultSet.getInt("id_professional");
                    int id_user = resultSet.getInt("id_normal_user");
                    Date visit = resultSet.getDate("visit_date");
                   /* String notification = resultSet.getString("notification");
                    Notificators notificators = Notificators.valueOf(notification);*/
                    ProfessionalUser profesionalUser = chargeProfesionalUserById(id_medical);
                    NormalUser normalUser = searchNormalUserById(id_user);
                    MedicalAppointment medicalAppointment = new MedicalAppointment(
                            id_appointment, profesionalUser, normalUser, visit);

                    listOfDates.add(medicalAppointment);
                }
            }
        } catch (SQLException e) {
            throw new OperationsDBException("Error al realizar las operaciones: " + e.getMessage());
        }

        return listOfDates;
    }

    @Override
    public boolean insertMedicalAppointments(MedicalAppointment medicalAppointment) throws OperationsDBException {
        boolean existe = false;

        String consulta = "INSERT INTO medical_appointment(id_professional, id_normal_user, visit_date, notification) VALUES(?,?,?,?)  ";

        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparetStmt = connection.prepareStatement(consulta)) {
            preparetStmt.setInt(1, medicalAppointment.getPsicologo().getIdPerson());
            preparetStmt.setInt(2, medicalAppointment.getUsuario().getIdPerson());
            preparetStmt.setObject(3, new Date(medicalAppointment.getVisitDate().getTime()));
            preparetStmt.setString(4, String.valueOf(medicalAppointment.getNotificator()));
            int affectedRows = preparetStmt.executeUpdate();
            if (affectedRows > 0) {
                existe = true;
            }
        } catch (SQLException e) {
            throw new OperationsDBException("Error a la hora de crear una cita");
        }
        return existe;
    }

    public static boolean thereIsAQuote(int idProfesional, Date date) throws OperationsDBException {
        boolean exist = false;
        String consulta = "SELECT * FROM medical_appointment WHERE id_professional = ? AND visit_date = ?";

        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparetStmt = connection.prepareStatement(consulta)) {
            preparetStmt.setInt(1, idProfesional);

            preparetStmt.setObject(2, new Date(date.getTime()));

            int affectedRows = preparetStmt.executeUpdate();
            if (affectedRows > 0) {
                exist = true;
            }
        } catch (SQLException e) {
            throw new OperationsDBException(e.getMessage());
        }
        return exist;
    }

    @Override
    public boolean updateMedicalAppointment(MedicalAppointment medicalAppointment, Date dateAppointment) throws OperationsDBException {
        boolean updated = false;
/*
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
*/
        TimeZone cestTimeZone = TimeZone.getTimeZone("CEST");
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateAppointment);
        cal.setTimeZone(cestTimeZone);
        Date cestDate = cal.getTime();
        String consulta = "UPDATE medical_appointment SET visit_date = ?  WHERE id_appointment = ?";

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparetStmt = connection.prepareStatement(consulta)) {
            preparetStmt.setObject(1, new Date(cestDate.getTime()));
            preparetStmt.setInt(2, medicalAppointment.getIdCita());
            int affectedRows = preparetStmt.executeUpdate();
            System.out.println("ID Cita: " + medicalAppointment.getIdCita());
            System.out.println("Fecha: " + cestDate);
            System.out.println("Conexión a la base de datos: " + connection.getMetaData().getURL());
            System.out.println("Consulta ejecutada: " + preparetStmt.toString());
            if (affectedRows > 0) {
                updated = true;
            }
        } catch (SQLException e) {
            throw new OperationsDBException(e.getMessage());
        }
        return updated;
    }

    @Override
    public String searchNameForPerson(int idPerson) throws OperationsDBException {
        String completeName="";
        String consulta = "SELECT user_names , last_names FROM person WHERE id_person = ?";
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparetStmt = connection.prepareStatement(consulta)) {
            preparetStmt.setInt(1, idPerson);
           try(ResultSet resultSet = preparetStmt.executeQuery()){
            while (resultSet.next()) {
                completeName = resultSet.getString("user_names");
                completeName+=resultSet.getString("last_names");
            }
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return completeName;
    }

    public boolean deleteMedicalAppointments(int id_appointment, int id_normal_user, Date date) throws OperationsDBException {
        boolean delete = false;
        String consulta = "DELETE FROM medical_appointment WHERE id_appointment = ? AND id_normal_user = ? and visit_date = ? ";

        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparetStmt = connection.prepareStatement(consulta)) {
            preparetStmt.setInt(1, id_appointment);
            preparetStmt.setInt(2, id_normal_user);
            preparetStmt.setObject(3, date);

            int affectedRows = preparetStmt.executeUpdate();
            if (affectedRows > 0) {
                delete = true;
            }
        } catch (SQLException e) {
            throw new OperationsDBException(e.getMessage());
        }
        return delete;
    }



    @Override
    public void close() throws Exception {
        connection.close();
    }
}



