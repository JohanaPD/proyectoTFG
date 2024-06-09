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
import java.util.*;

/**
 * SqliteConnector  class of the application.
 * Class responsible for managing the connection to the SQLite database
 * and performing CRUD operations (Create, Read, Update, Delete) on all tables.
 * <p>This class implements the DAO (Data Access Object) pattern to provide
 * * an abstract interface to the database. It handles establishing the connection,
 * * as well as executing various SQL operations such as insert, update, and delete
 * * for different tables.</p>
 *
 * @author [JOhana Pardo, Daniel Ocaña]
 * @version Java 21
 * @since 2024-06-08
 */

public class SqliteConnector implements AutoCloseable, PersonaDAO {

    public final String URL;
    private Connection connection;

    /**
     * Constructor for the SQLIteConnector class.
     *
     * <p>Initializes the connection to the SQLite database and creates the necessary tables.
     * If the connection is successful, a confirmation message is printed to the console.</p>
     *
     * @throws SQLException          if a database access error occurs or the URL is incorrect.
     * @throws OperationsDBException if an error occurs while performing database operations.
     */
    public SqliteConnector(String url) throws SQLException, OperationsDBException {
        URL = url;
        this.connection = DriverManager.getConnection(url);
        createTables();
        if (connection != null) {
            System.out.println("Se ha conectado a ella exitosamente.");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    /*   ================================================================================================
        ======================================Crear BBDD y tablas =====================================================*/

    /**
     * Creates the necessary tables in the SQLite database if they do not already exist.
     *
     * <p>This method executes a series of SQL statements to create the following tables:
     * <ul>
     * <li><strong>direction</strong>: Stores addresses.</li>
     * <li><strong>person</strong>: Stores personal user information.</li>
     * <li><strong>professional_user</strong>: Stores professional user information (psychologists).</li>
     * <li><strong>normal_user</strong>: Stores normal user information.</li>
     * <li><strong>post</strong>: Stores posts made by users.</li>
     * <li><strong>history</strong>: Stores user history records.</li>
     * <li><strong>diagnose</strong>: Stores diagnoses information.</li>
     * <li><strong>diagnoses_history</strong>: Stores the relationship between diagnoses and history records.</li>
     * <li><strong>medical_appointment</strong>: Stores medical appointment details.</li>
     * <li><strong>favorites_professionals</strong>: Stores the relationship between normal users and their favorite professionals.</li>
     * </ul>
     * </p>
     *
     * @throws OperationsDBException if an error occurs while creating the tables.
     */
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

    /**
     * Searches for professional users in the database whose names or specialties match the given search criteria.
     *
     * <p>This method executes a SQL query to find professional users whose names or specialties contain the specified keyword.
     * The search is case-insensitive and uses a wildcard match. If no users are found, a {@link NonexistingUser} exception is thrown.</p>
     *
     * @param nameUser the search keyword to match against professional user names and specialties.
     * @return a list of {@link ProfessionalUser} objects that match the search criteria.
     * @throws NonexistingUser       if no professional users are found matching the search criteria.
     * @throws DataAccessException   if there is an error accessing the database.
     * @throws OperationsDBException if there is an error processing the user data.
     */
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

    /**
     * Searches for the ID of a given direction in the database.
     *
     * <p>This method executes a SQL query to find the ID of a direction that matches the provided street and city.
     * If a matching direction is found, the ID is returned. If no matching direction is found, -1 is returned.</p>
     *
     * @param direction the {@link Direction} object containing the street and city to search for.
     * @return the ID of the matching direction, or -1 if no matching direction is found.
     * @throws OperationsDBException if there is an error accessing the database or performing the query.
     */
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

    /**
     * Registers a new direction in the database if it does not already exist.
     *
     * <p>This method first checks if the direction already exists in the database by calling
     * {@link #searchIdDirection(Direction)}. If the direction exists, it sets the ID of the
     * provided {@link Direction} object. If the direction does not exist, it inserts the
     * direction into the database and retrieves the generated ID.</p>
     *
     * @param direction the {@link Direction} object to be registered.
     * @return the updated {@link Direction} object with its ID set.
     * @throws OperationsDBException if there is an error accessing the database or performing the query,
     *                               or if there is an error setting the ID of the direction.
     */
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

    /**
     * Logs in a user by validating their email and password, and retrieves their information from the database.
     *
     * <p>This method connects to the database, validates the provided email and password, and retrieves the user's
     * information if the login credentials are correct. It distinguishes between normal users and professional users
     * based on the {@link TypeUser} parameter.</p>
     *
     * @param email       the email address of the user attempting to log in.
     * @param scripp_pass the password of the user attempting to log in.
     * @param typeUser    the type of user attempting to log in (normal or professional).
     * @return the {@link Person} object representing the logged-in user, or null if the login fails.
     * @throws IncorrectLoginEception   if the provided password is incorrect.
     * @throws InvalidKeySpecException  if there is an error with the key specification during password validation.
     * @throws NonexistingUser          if the user does not exist or the user type is incorrect.
     * @throws OperationsDBException    if there is an error performing database operations.
     * @throws DataAccessException      if there is an error accessing the database.
     * @throws NoSuchAlgorithmException if there is an error with the algorithm used for password validation.
     */

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

    /**
     * Retrieves a user's information from the database and updates their password.
     *
     * <p>This method connects to the database, retrieves the user's information based on their email,
     * and returns a {@link Person} object with the updated password. It distinguishes between normal
     * users and professional users based on the user type.</p>
     *
     * @param email       the email address of the user.
     * @param newPassword the new password to set for the user.
     * @return the updated {@link Person} object with the new password set.
     * @throws IncorrectLoginEception  if the login credentials are incorrect.
     * @throws InvalidKeySpecException if there is an error with the key specification during password validation.
     * @throws NonexistingUser         if the user does not exist or the user type is incorrect.
     * @throws OperationsDBException   if there is an error performing database operations.
     * @throws DataAccessException     if there is an error accessing the database.
     */
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

    /**
     * Retrieves the nickname of a normal user based on their ID.
     *
     * <p>This method executes a SQL query to find the nickname of a normal user associated with
     * the provided user ID. If a matching nickname is found, it is returned. If no matching
     * nickname is found, an empty string is returned.</p>
     *
     * @param id the ID of the user.
     * @return the nickname of the user, or an empty string if no nickname is found.
     * @throws OperationsDBException if there is an error accessing the database or performing the query.
     */
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

    /**
     * Constructs a ProfessionalUser object using the provided information retrieved from the database.
     *
     * <p>This method executes a SQL query to retrieve additional information specific to a professional user
     * based on their ID. It constructs a {@link ProfessionalUser} object using the retrieved information and
     * returns it.</p>
     *
     * @param id            the ID of the professional user.
     * @param nombres       the names of the professional user.
     * @param apellidos     the last names of the professional user.
     * @param scripp_pass   the hashed password of the professional user.
     * @param birrth        the date of birth of the professional user.
     * @param registration  the registration date of the professional user.
     * @param email         the email address of the professional user.
     * @param type          the type of user (professional or normal).
     * @param state         the state of the professional user.
     * @param direction     the {@link Direction} object representing the address of the professional user.
     * @param last_activity the last activity date of the professional user.
     * @return a {@link ProfessionalUser} object constructed using the retrieved information.
     * @throws OperationsDBException if there is an error accessing the database or performing the query.
     */
    private ProfessionalUser chargeProfesionalUser(int id, String nombres, String apellidos, String scripp_pass, Date birrth, Date registration, String email, TypeUser type, StatesUser state, Direction direction, Date last_activity) throws OperationsDBException {
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

    /**
     * Constructs a ProfessionalUser object using the information retrieved from the database based on the user ID.
     *
     * <p>This method executes a SQL query to retrieve additional information specific to a professional user
     * based on their ID. It constructs a {@link ProfessionalUser} object using the retrieved information and
     * returns it.</p>
     *
     * @param id the ID of the professional user.
     * @return a {@link ProfessionalUser} object constructed using the retrieved information.
     * @throws OperationsDBException if there is an error accessing the database or performing the query.
     */
    public ProfessionalUser chargeProfessionalUserById(int id) throws OperationsDBException {
        ProfessionalUser professionalUser = null;
        String consultaPerson = "SELECT * FROM person WHERE id_person=?";
        String consulta = "SELECT * FROM professional_user WHERE id_person=?;";
        try (PreparedStatement statementPerson = connection.prepareStatement(consultaPerson);
             PreparedStatement statement = connection.prepareStatement(consulta)) {
            statementPerson.setInt(1, id);
            try (ResultSet resultSet = statementPerson.executeQuery()) {
                if (resultSet.next()) {
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
                    professionalUser = new ProfessionalUser(id, nombres, apellidos, scripp_pass, birrth, registration, email, type, state, direction, last_activity);
                }
            }
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String collegiate = resultSet.getString("collegiate");
                    String specialty = resultSet.getString("specialty");
                    String description = resultSet.getString("description");
                    professionalUser.setCollegiate(collegiate);
                    professionalUser.setSpecialty(specialty);
                    professionalUser.setDescription(description);
                } else {
                    return null;
                }

            }
        } catch (SQLException | NullArgumentException | IncorrectDataException | NoSuchAlgorithmException |
                 InvalidKeySpecException e) {
            throw new OperationsDBException("Error al encontrar la dirección");
        }
        return professionalUser;
    }

    /**
     * Constructs a NormalUser object using the information retrieved from the database based on the user ID.
     *
     * <p>This method executes a SQL query to retrieve additional information specific to a normal user
     * based on their ID. It constructs a {@link NormalUser} object using the retrieved information and
     * returns it.</p>
     *
     * @param id the ID of the normal user.
     * @return a {@link NormalUser} object constructed using the retrieved information.
     * @throws OperationsDBException if there is an error accessing the database or performing the query.
     */
    public NormalUser chargeNormalUserById(int id) throws OperationsDBException {
        NormalUser normalUser = null;
        String consultaPerson = "SELECT * FROM person WHERE id_person=?";
        String consulta = "SELECT * FROM normal_user WHERE id_person=?;";

        try (PreparedStatement statementPerson = connection.prepareStatement(consultaPerson);
             PreparedStatement statement = connection.prepareStatement(consulta)) {
            statementPerson.setInt(1, id);
            try (ResultSet resultSet = statementPerson.executeQuery()) {
                if (resultSet.next()) {
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
                    normalUser = new NormalUser(id, nombres, apellidos, scripp_pass, birrth, registration, email, type, state, direction, last_activity);
                }
            }
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nickname = resultSet.getString("nickname");
                    String inTherapySession = resultSet.getString("in_therapy_session");
                    normalUser.setNickname(nickname);
                    normalUser.setInTherapySession(Boolean.parseBoolean(inTherapySession));
                } else {
                    return null;
                }
            }
        } catch (SQLException | NullArgumentException | IncorrectDataException | NoSuchAlgorithmException |
                 InvalidKeySpecException e) {
            throw new OperationsDBException("Error al encontrar la dirección");
        }
        return normalUser;
    }

    /**
     * Constructs a Direction object using the information retrieved from the database based on the direction ID.
     *
     * <p>This method executes a SQL query to retrieve information specific to a direction based on its ID.
     * It constructs a {@link Direction} object using the retrieved information and returns it.</p>
     *
     * @param idDirection the ID of the direction.
     * @return a {@link Direction} object constructed using the retrieved information.
     * @throws OperationsDBException if there is an error accessing the database or performing the query.
     */
    public Direction chargeDirection(int idDirection) throws OperationsDBException {
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

    /**
     * Searches for the ID of a person in the database based on their names and last names or email.
     *
     * <p>This method executes a SQL query to search for the ID of a person in the database based on
     * their names and last names or email. It returns the ID if the person is found, or -1 otherwise.</p>
     *
     * @param person the Person object containing the names, last names, and email to search for.
     * @return the ID of the person if found, or -1 if not found.
     * @throws OperationsDBException if there is an error accessing the database or performing the query.
     */
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

    /**
     * Retrieves a list of ProfessionalUser objects from the database.
     *
     * <p>This method executes a SQL query to retrieve information about professional users from the database.
     * It constructs {@link ProfessionalUser} objects using the retrieved information and adds them to a list,
     * which is then returned.</p>
     *
     * @return a list of {@link ProfessionalUser} objects retrieved from the database.
     * @throws SQLException             if a database access error occurs.
     * @throws IncorrectDataException   if there is incorrect data retrieved from the database.
     * @throws NoSuchAlgorithmException if the hashing algorithm used for password validation is not available.
     * @throws InvalidKeySpecException  if the password specification is invalid.
     * @throws NullArgumentException    if a null argument is passed to a method that does not accept it.
     * @throws OperationsDBException    if there is an error accessing the database or performing the query.
     * @throws NonexistingUser          if the list is empty because there are no users in the database.
     */
    public List<ProfessionalUser> getProfessionals() throws SQLException, IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException, OperationsDBException, NonexistingUser {
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

    /**
     * Registers a new person in the database.
     *
     * <p>This method registers a new person in the database. It first registers the person's direction
     * if it does not exist in the database. Then, it checks if the person already exists based on their
     * names, last names, and email. If the person does not exist, it inserts the person's information
     * into the database. If the person already exists, a {@link DuplicateKeyException} is thrown.</p>
     *
     * @param person the Person object to register.
     * @return the registered Person object with its assigned ID.
     * @throws OperationsDBException if there is an error accessing the database or performing the query.
     * @throws DuplicateKeyException if the person already exists in the database.
     */
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

    /**
     * Registers a new professional user in the database.
     *
     * <p>This method registers a new professional user in the database. If the 'update' parameter is
     * set to false, it first registers the professional user as a regular person using the
     * {@link #registerPerson(Person)} method. Then, it inserts the professional user's information into
     * the 'professional_user' table. If the 'update' parameter is set to true, it only inserts the
     * professional user's information into the 'professional_user' table.</p>
     *
     * @param professionalUser the ProfessionalUser object to register.
     * @param update           a boolean value indicating whether to update an existing professional user or not.
     * @throws OperationsDBException if there is an error accessing the database or performing the query.
     * @throws DuplicateKeyException if there is a duplicate key violation in the database.
     */
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

    /**
     * Registers a new normal user in the database.
     *
     * <p>This method registers a new normal user in the database. If the 'update' parameter is
     * set to false, it first registers the normal user as a regular person using the
     * {@link #registerPerson(Person)} method. Then, it inserts the normal user's information into
     * the 'normal_user' table. If the 'update' parameter is set to true, it only inserts the
     * normal user's information into the 'normal_user' table.</p>
     *
     * @param normalUser the NormalUser object to register.
     * @param update     a boolean value indicating whether to update an existing normal user or not.
     * @throws OperationsDBException if there is an error accessing the database or performing the query.
     * @throws DuplicateKeyException if there is a duplicate key violation in the database.
     */
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

    /**
     * Loads professional users with their history for all users from the database.
     *
     * <p>This method loads professional users along with their history for all users from the database.
     * It retrieves information from the 'person' table and uses the {@link #chargeDirection(int)} method
     * to fetch the direction associated with each user. It then searches for each user's professional
     * information and adds them to the list. Finally, it returns the list of professional users.</p>
     *
     * @return a list of ProfessionalUser objects with their history for all users.
     * @throws IncorrectDataException   if there is incorrect data retrieved from the database.
     * @throws NoSuchAlgorithmException if there is an error with the algorithm used.
     * @throws InvalidKeySpecException  if there is an error with the key specifications.
     * @throws NullArgumentException    if a null argument is passed where it is not allowed.
     * @throws SQLException             if there is an error accessing the database or performing the query.
     * @throws OperationsDBException    if there is an error performing operations on the database.
     */

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

    /**
     * Searches for professional information for a specific user ID.
     *
     * <p>This method searches for professional information for a specific user ID in the 'professional_user'
     * table of the database. It retrieves the collegiate, specialty, and description fields for the
     * user with the given ID and sets them in the provided ProfessionalUser object. If no matching record
     * is found, the provided ProfessionalUser object remains unchanged.</p>
     *
     * @param person the ProfessionalUser object to search professional information for.
     * @return the ProfessionalUser object with updated professional information if found, otherwise the same object.
     * @throws IncorrectDataException   if there is incorrect data retrieved from the database.
     * @throws NoSuchAlgorithmException if there is an error with the algorithm used.
     * @throws InvalidKeySpecException  if there is an error with the key specifications.
     * @throws NullArgumentException    if a null argument is passed where it is not allowed.
     * @throws OperationsDBException    if there is an error accessing the database or performing the query.
     */
    private ProfessionalUser searchProfesionalPersonForId(ProfessionalUser person) throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException, OperationsDBException {
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

    /**
     * Searches for a normal user by their ID.
     *
     * <p>This method searches for a normal user in the 'normal_user' table of the database
     * using the provided person ID. It retrieves the nickname and therapy session status for
     * the user with the given ID and creates a new NormalUser object with this information.</p>
     *
     * @param person the ID of the normal user to search for.
     * @return a NormalUser object with the information retrieved from the database.
     * @throws IncorrectDataException   if there is incorrect data retrieved from the database.
     * @throws NoSuchAlgorithmException if there is an error with the algorithm used.
     * @throws InvalidKeySpecException  if there is an error with the key specifications.
     * @throws NullArgumentException    if a null argument is passed where it is not allowed.
     * @throws OperationsDBException    if there is an error accessing the database or performing the query.
     */
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

    /**
     * Updates the information of a professional user in the database.
     *
     * <p>This method updates the information of a professional user in the database, including their personal details,
     * address, and professional details. It first updates the 'person' table with the user's names, last names, email,
     * address, and type. Then, it updates the 'direction' table with the user's street, city, and postal code.
     * Finally, it updates the 'professional_user' table with the user's collegiate, specialty, and description.</p>
     *
     * <p>If the user does not exist in the database, it attempts to register them as a new professional user.
     * If the registration fails due to a duplicate key, it handles the exception silently.</p>
     *
     * @param nuevo the ProfessionalUser object containing the updated information.
     * @throws OperationsDBException if there is an error updating the user information in the database.
     * @throws SQLException          if a database access error occurs.
     */
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
            if (isUpdated == 0 || chargeProfessionalUserById(nuevo.getIdPerson()) == null) {
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

    /**
     * Updates the information of a professional user in the database.
     *
     * <p>This method updates the information of a professional user in the database, including their personal details,
     * address, and professional details. It first updates the 'person' table with the user's names, last names,
     * email, address, and user type. Then, it updates the 'direction' table with the street, city, and postal code
     * of the user. Finally, it updates the 'professional_user' table with the collegiate, specialty, and description
     * of the user.</p>
     *
     * <p>If the user does not exist in the database, it attempts to register them as a new professional user.
     * If the registration fails due to a duplicate key, it handles the exception silently.</p>
     *
     * @param user the ProfessionalUser object containing the updated information.
     * @throws OperationsDBException if there is an error updating the user information in the database.
     * @throws SQLException          if there is an error accessing the database.
     */

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
            updateProfessionalUserStmt.setInt(4, user.getIdPerson());

            updatePersonStmt.executeUpdate();
            updateDireccionStmt.executeUpdate();
            int isUpdate = updateProfessionalUserStmt.executeUpdate();
            if (isUpdate == 0 || chargeProfessionalUserById(user.getIdPerson()) == null) {
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

    /**
     * Updates the information of a normal user in the database.
     *
     * <p>This method updates the information of a normal user in the database, including their personal details,
     * address, and specific details of a normal user. It first updates the 'person' table with the user's names,
     * last names, email, address, and user type. Then, it updates the 'direction' table with the street, city,
     * and postal code of the user. If the user is a normal user, it updates the 'normal_user' table with the user's
     * nickname and therapy status. If the user does not exist in the database, it attempts to register them as a new
     * normal user. If the registration fails due to a duplicate key, it handles the exception silently.</p>
     *
     * @param user the NormalUser object containing the updated information.
     * @throws OperationsDBException if there is an error updating the user information in the database.
     * @throws SQLException          if there is an error accessing the database.
     */
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
        if (user.getTypeUser() == TypeUser.USUARIO_NORMAL && chargeProfessionalUserById(user.getIdPerson()) != null) {
            deleteProfessionalUser(user.getIdPerson());
        }
    }

    /**
     * Updates the information of a normal user in the database.
     *
     * <p>This method updates the information of a normal user in the database, including their personal details,
     * email, and address. It first updates the 'person' table with the user's names, last names,
     * email, and address. Then, it updates the 'direction' table with the street, city, and postal code
     * of the user. Finally, it updates the 'normal_user' table with the nickname and therapy session status
     * of the user.</p>
     *
     * <p>If the user does not exist in the database, it attempts to register them as a new normal user.
     * If the registration fails due to a duplicate key, it handles the exception silently.</p>
     *
     * @param nuevo the NormalUser object containing the updated information.
     * @throws OperationsDBException if there is an error updating the user information in the database.
     * @throws SQLException          if there is an error accessing the database.
     */

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
        if (nuevo.getTypeUser() == TypeUser.USUARIO_NORMAL && chargeProfessionalUserById(nuevo.getIdPerson()) != null) {
            deleteProfessionalUser(nuevo.getIdPerson());
        }
    }

    /**
     * Deletes a professional user from the database.
     *
     * <p>This method deletes a professional user from the database based on their ID. It removes the corresponding
     * entry from the 'professional_user' table.</p>
     *
     * @param idPerson the ID of the professional user to be deleted.
     * @throws OperationsDBException if there is an error performing the database operation.
     */
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

    /**
     * Deletes a normal user from the database.
     *
     * <p>This method deletes a normal user from the database based on their ID. It removes the corresponding
     * entry from the 'normal_user' table.</p>
     *
     * @param idPerson the ID of the normal user to be deleted.
     * @throws OperationsDBException if there is an error performing the database operation.
     */
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

    /**
     * Makes a new post in the database.
     *
     * <p>This method creates a new post in the database. If a post with the same title already exists, it does not create
     * a duplicate post. It inserts the post title, content, URL of the image, date of the post, and the ID of the person
     * who created the post into the 'post' table.</p>
     *
     * @param nuevo the post object containing the information of the new post to be created.
     * @throws OperationsDBException if there is an error performing the database operation.
     */
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

    /**
     * Searches for a post by its title in the database.
     *
     * <p>This method checks if a post with the specified title already exists in the database. It performs a search
     * in the 'post' table using a LIKE query on the title column.</p>
     *
     * @param titulo the title of the post to search for.
     * @return true if a post with the specified title exists in the database, false otherwise.
     * @throws OperationsDBException if there is an error performing the database operation.
     */
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

    /**
     * Searches for posts created by a specific person in the database.
     *
     * <p>This method retrieves all posts created by the specified person from the database. It performs a search
     * in the 'post' table using the person's ID.</p>
     *
     * @param person the Person object representing the creator of the posts to search for.
     * @return a list of Post objects created by the specified person.
     * @throws IncorrectDataException if there is incorrect data during the operation.
     * @throws NullArgumentException  if a null argument is provided.
     * @throws OperationsDBException  if there is an error performing the database operation.
     */
    public List<Post> serchPostByPerson(Person person) throws IncorrectDataException, NullArgumentException, OperationsDBException {
        List<Post> listaPost = new ArrayList<>();
        String consulta = "SELECT * FROM post WHERE id_person = ?";
        try (PreparedStatement preparetStmt = connection.prepareStatement(consulta)) {
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

    /**
     * Adds a professional user to the favorites list of a normal user in the database.
     *
     * <p>This method adds a professional user to the favorites list of a normal user in the database.
     * It inserts a new record into the 'favorites_professionals' table with the IDs of the normal user and
     * the professional user.</p>
     *
     * @param professionalUser the ProfessionalUser object to add to the favorites list.
     * @param person           the Person object representing the normal user whose favorites list will be updated.
     * @return true if the professional user was successfully added to the favorites list, false otherwise.
     * @throws OperationsDBException if there is an error performing the database operation.
     */
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

    /**
     * Searches for medical appointments for a given professional and date in the database.
     *
     * <p>This method retrieves medical appointments from the database for a specific professional and date.
     * It executes a SQL query with the professional ID and visit date as parameters.</p>
     *
     * @param id   the ID of the professional for whom medical appointments will be searched.
     * @param date the date for which medical appointments will be searched.
     * @return a list of MedicalAppointment objects representing the found appointments.
     * @throws OperationsDBException    if there is an error performing the database operation.
     * @throws IncorrectDataException   if incorrect data is encountered during processing.
     * @throws NoSuchAlgorithmException if a required cryptographic algorithm is not available.
     * @throws InvalidKeySpecException  if an invalid key specification is encountered.
     * @throws NullArgumentException    if a required argument is null.
     */
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
                    ProfessionalUser profesionalUser = chargeProfessionalUserById(id_medical);
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

    /**
     * Searches for medical appointments associated with a specific user in the database.
     *
     * <p>This method retrieves medical appointments from the database associated with a particular user.
     * It executes a SQL query with the user's ID as a parameter.</p>
     *
     * @param person the Person object representing the user for whom medical appointments will be searched.
     * @return a list of MedicalAppointment objects representing the found appointments.
     * @throws OperationsDBException    if there is an error performing the database operation.
     * @throws IncorrectDataException   if incorrect data is encountered during processing.
     * @throws NoSuchAlgorithmException if a required cryptographic algorithm is not available.
     * @throws InvalidKeySpecException  if an invalid key specification is encountered.
     * @throws NullArgumentException    if a required argument is null.
     */
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
                    ProfessionalUser profesionalUser = chargeProfessionalUserById(id_medical);
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

    /**
     * Inserts a new medical appointment into the database.
     *
     * <p>This method inserts a new medical appointment into the database. It executes an SQL INSERT query
     * with the details of the medical appointment, including the professional, user, visit date, and notification type.</p>
     *
     * @param medicalAppointment the MedicalAppointment object representing the appointment to be inserted.
     * @return true if the appointment is successfully inserted, false otherwise.
     * @throws OperationsDBException if there is an error performing the database operation.
     */
    @Override
    public boolean insertMedicalAppointments(MedicalAppointment medicalAppointment) throws OperationsDBException {
        boolean existe = false;

        String consulta = "INSERT INTO medical_appointment(id_professional, id_normal_user, visit_date, notification) VALUES(?,?,?,?)  ";

        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparetStmt = connection.prepareStatement(consulta)) {
            preparetStmt.setInt(1, medicalAppointment.getProfessionalUser().getIdPerson());
            preparetStmt.setInt(2, medicalAppointment.getUser().getIdPerson());
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

    /**
     * Checks if there is a medical appointment for a professional on a specific date.
     *
     * <p>This method checks if there is a medical appointment scheduled for the specified professional
     * on the given date. It executes an SQL SELECT query to search for the appointment.</p>
     *
     * @param idProfesional the ID of the professional.
     * @param date          the date of the appointment.
     * @return true if there is an appointment for the professional on the specified date, false otherwise.
     * @throws OperationsDBException if there is an error performing the database operation.
     */
    public boolean thereIsAQuote(int idProfesional, Date date) throws OperationsDBException {
        boolean exist = false;
        String consulta = "SELECT * FROM medical_appointment WHERE id_professional = ? AND visit_date = ?";

        try (PreparedStatement preparetStmt = connection.prepareStatement(consulta)) {
            preparetStmt.setInt(1, idProfesional);

            preparetStmt.setObject(2, new Date(date.getTime()));

            try (ResultSet resultSet = preparetStmt.executeQuery()) {
                if (resultSet.next()) {
                    exist = true;
                }
            }
        } catch (SQLException e) {
            throw new OperationsDBException(e.getMessage());
        }
        return exist;
    }

    /**
     * Updates the date of a medical appointment.
     *
     * <p>This method updates the date of a medical appointment in the database. It takes the new appointment date
     * and the ID of the appointment to be updated, then executes an SQL UPDATE query to update the appointment.</p>
     *
     * @param medicalAppointment the medical appointment object containing the ID of the appointment to be updated.
     * @param dateAppointment    the new date for the appointment.
     * @return true if the appointment is successfully updated, false otherwise.
     * @throws OperationsDBException if there is an error performing the database operation.
     */
    @Override
    public boolean updateMedicalAppointment(MedicalAppointment medicalAppointment, Date dateAppointment) throws OperationsDBException {
        boolean updated = false;
        TimeZone cestTimeZone = TimeZone.getTimeZone("CEST");
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateAppointment);
        cal.setTimeZone(cestTimeZone);
        Date cestDate = cal.getTime();
        String consulta = "UPDATE medical_appointment SET visit_date = ?, notification=?  WHERE id_appointment = ?";

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparetStmt = connection.prepareStatement(consulta)) {
            preparetStmt.setObject(1, new Date(cestDate.getTime()));
            preparetStmt.setString(2, String.valueOf(Notificators.CITAEDITADA));
            preparetStmt.setInt(3, medicalAppointment.getIdAppointment());
            int affectedRows = preparetStmt.executeUpdate();
            System.out.println("ID Cita: " + medicalAppointment.getIdAppointment());
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

    /**
     * Searches for the full name of a person by their ID in the database.
     *
     * <p>This method searches for the full name of a person in the database based on their ID. It executes an SQL SELECT
     * query to retrieve the user names and last names of the person with the specified ID.</p>
     *
     * @param idPerson the ID of the person.
     * @return the full name of the person.
     * @throws OperationsDBException if there is an error performing the database operation.
     */
    @Override
    public String searchNameForPerson(int idPerson) throws OperationsDBException {
        String completeName = "";
        String consulta = "SELECT user_names , last_names FROM person WHERE id_person = ?";
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparetStmt = connection.prepareStatement(consulta)) {
            preparetStmt.setInt(1, idPerson);
            try (ResultSet resultSet = preparetStmt.executeQuery()) {
                while (resultSet.next()) {
                    completeName = resultSet.getString("user_names");
                    completeName += resultSet.getString("last_names");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return completeName;
    }

    /**
     * Deletes a medical appointment from the database.
     *
     * <p>This method deletes a medical appointment from the database based on the appointment ID, user ID, and appointment date.
     * It executes an SQL DELETE query to remove the appointment with the specified details.</p>
     *
     * @param id_appointment the ID of the medical appointment to delete.
     * @param id_normal_user the ID of the user associated with the appointment.
     * @param date           the date of the medical appointment.
     * @return true if the appointment was successfully deleted, false otherwise.
     * @throws OperationsDBException if there is an error performing the database operation.
     */
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

    /**
     * Closes the database connection.
     *
     * <p>This method closes the connection to the database. It should be called when the database operations are complete
     * to release any resources associated with the connection.</p>
     *
     * @throws Exception if there is an error closing the database connection.
     */

    @Override
    public void close() throws Exception {
        connection.close();
    }
}



