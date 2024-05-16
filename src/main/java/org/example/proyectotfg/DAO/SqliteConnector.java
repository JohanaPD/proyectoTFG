package org.example.proyectotfg.DAO;

import org.example.proyectotfg.entities.Direction;
import org.example.proyectotfg.entities.Person;
import org.example.proyectotfg.entities.ProfessionalUser;
import org.example.proyectotfg.entities.NormalUser;
import org.example.proyectotfg.enumAndTypes.StatesUser;
import org.example.proyectotfg.enumAndTypes.TypeUser;
import org.example.proyectotfg.exceptions.*;
import org.example.proyectotfg.functions.FunctionsApp;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteConnector implements AutoCloseable, PersonaDAO {
    static final String URL = "jdbc:sqlite:src/main/resources/sqliteBBDD/MeetPsych.db";

    static Connection connection;

    public SqliteConnector() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
        createTables();
        if (connection != null) {
            System.out.println("Se ha conectado a ella exitosamente.");
        }
    }

    @Override
    public void createTables() {
        String consultaDireccion = "CREATE TABLE IF NOT EXISTS  direction(" +
                "id_direction INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "street VARCHAR(150) NOT NULL," +
                "city VARCHAR(100) NOT NULL," +
                "postal_code INTEGER" +
                ");";
        String createPerson = "CREATE TABLE IF NOT EXISTS  person(" +
                "id_person INTEGER PRIMARY KEY  AUTOINCREMENT," +
                "user_names VARCHAR(100) NOT NULL," +
                "last_names VARCHAR(150) NOT NULL," +
                "pass_script TEXT NOT NULL," +
                "date_birth DATE NOT NULL," +
                "registration_date DATE NOT NULL," +
                "email VARCHAR(150) NOT NULL," +
                "type_user VARCHAR(50) NOT NULL," + //-- 0 para false, 1 para true
                "user_state VARCHAR(20) NOT NULL," +
                "last_activity DATE NOT NULL," +
                "id_direction INTEGER NOT NULL," +
                "foreign key (id_direction) references direction(id_direction)" +
                ");";
        String queryPsychologist = "CREATE TABLE IF NOT EXISTS  professional_user(" +
                "id_person INTEGER PRIMARY KEY," +
                "collegiate VARCHAR(200) UNIQUE NOT NULL," +
                "specialty VARCHAR(150) NOT NULL," +
                "description VARCHAR(10000) NOT NULL," +
                "FOREIGN KEY(id_person) REFERENCES person(id_person) );";


        String consultaUsuarioNormal = "CREATE TABLE IF NOT EXISTS  normal_user(" +
                "id_person INTEGER PRIMARY KEY," +
                "nickname VARCHAR(40) UNIQUE NOT NULL," +
                "in_therapy_session INTEGER NOT NULL," + //-- 0 para false, 1 para true
                "FOREIGN KEY(id_person) REFERENCES person(id_person) );";

        String consultaPost = "CREATE TABLE IF NOT EXISTS post(" +
                "id_post INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "title VARCHAR(250) NOT NULL," +
                "content TEXT NOT NULL," +
                "url_image VARCHAR(500) NOT NULL," +
                "date_post DATE NOT NULL, " +
                "id_person INTEGER NOT NULL," +
                "FOREIGN KEY(id_person) REFERENCES person(id_person) ON DELETE CASCADE);";

        String consultaHistorial = "CREATE TABLE IF NOT EXISTS history(" +
                "id_history INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "id_user INTEGER NOT NULL," +
                "FOREIGN KEY(id_user) REFERENCES person(id_user) ON DELETE CASCADE);";


        String consultaDiagnostico = "CREATE TABLE IF NOT EXISTS diagnose(" +
                "id_diagnose INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "id_title TEXT NOT NULL," +
                "id_content TEXT NOT NULL);";

        String consultaDiagnosticosHistorial = "CREATE TABLE IF NOT EXISTS diagnoses_history(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "id_diagnose INTEGER NOT NULL," +
                "id_history INTEGER NOT NULL," +
                "FOREIGN KEY(id_diagnose) REFERENCES diagnose(id_diagnose) ON DELETE CASCADE," +
                "FOREIGN KEY(id_history) REFERENCES history(id_history) ON DELETE CASCADE);";

        String consultaCitas = "CREATE TABLE IF NOT EXISTS medical_appointment (" +
                "id_appointment INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "id_professional TEXT NOT NULL," +
                "id_normal_user TEXT NOT NULL," +
                "visit_date TEXT NOT NULL," +
                "notification TEXT NOT NULL," +
                "FOREIGN KEY(id_normal_user) REFERENCES normal_user(id_person) ON DELETE CASCADE," +
                "FOREIGN KEY(id_professional) REFERENCES professional_user(id_person) ON DELETE CASCADE);";

        try (Statement stmt = connection.createStatement()) {
            // Ejecutar cada sentencia de creación
            stmt.executeUpdate(consultaDireccion);
            stmt.executeUpdate(createPerson);
            stmt.executeUpdate(queryPsychologist);
            stmt.executeUpdate(consultaUsuarioNormal);
            stmt.executeUpdate(consultaPost);
            stmt.executeUpdate(consultaHistorial);
            stmt.executeUpdate(consultaDiagnostico);
            stmt.executeUpdate(consultaDiagnosticosHistorial);
            stmt.executeUpdate(consultaCitas);
            System.out.println("Tabla creada correctamente");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /*@Override
    public void inscribeUser(Person Person) {

    }*/

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
        //Si no existe en la base de datos
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
    public Person loginUser(String email, String scripp_pass, TypeUser typeUser) throws IncorrectLoginEception, InvalidKeySpecException, NonexistingUser, OperationsDBException, DataAccessException {
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
                                //la contraseña que incluye el usuario
                                Date birrth = resultSet.getDate("date_birth");
                                Date registration = resultSet.getDate("registration_date");
//ok
                                TypeUser type = TypeUser.valueOf(resultSet.getString("type_user"));
                                StatesUser state = StatesUser.valueOf(resultSet.getString("user_state"));
                                Date last_activity = resultSet.getDate("last_activity");
                                idDirection = resultSet.getInt("id_direction");
                                Direction direction = chargeDirection(idDirection);
//ok
                                if (typeUser.equals(TypeUser.USUARIO_NORMAL)) {

                                    String nickname = chargeNickname(id);
                                    person = new NormalUser(id, nombres, apellidos, scripp_pass, birrth, registration, email, type, state, direction, last_activity, nickname);

                                } else {
                                    // Person nueva= new Person(id, nombres,apellidos, birrth,registration,type,direction);
                                    person = chargeProfesionalUser(id, nombres, apellidos, scripp_pass, birrth, registration, email, type, state, direction, last_activity);

                                }
                            } else {
                                throw new IncorrectLoginEception("La contraseña es incorrecta");
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

    private static ProfessionalUser chargeProfesionalUser(int id, String nombres, String apellidos, String scripp_pass,
                                                          Date birrth, Date registration, String email, TypeUser type, StatesUser state, Direction direction, Date last_activity) throws OperationsDBException {
        ProfessionalUser professionalUser = null;
        String consulta = "SELECT * FROM professional_user WHERE id_person=?;";

        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String collegiate = resultSet.getString("collegiate");
                String specialty = resultSet.getString("specialty");
                String description = resultSet.getString("description");
                professionalUser = new ProfessionalUser(id, nombres, apellidos, scripp_pass, birrth, registration,
                        email, type, state, direction, last_activity, collegiate, specialty, description);
            }

        } catch (SQLException | NullArgumentException | IncorrectDataException | NoSuchAlgorithmException |
                 InvalidKeySpecException e) {
            throw new OperationsDBException("Error al encontrar la dirección");
        }
        return professionalUser;
    }

    private static Direction chargeDirection(int idDirection) throws OperationsDBException {
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
        String consulta = "SELECT id_person FROM person WHERE (user_names=? and last_names=?) or email =? /*and date_birth=?*/;";

        try (PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setString(1, person.getNames());
            statement.setString(2, person.getLastNames());
            statement.setString(3, person.getEmail());
/*
            statement.setDate(3, new Date(person.getBirthDate().getTime()));
*/
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
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query2)) {
            while (resultSet.next()) {
                ProfessionalUser user = new ProfessionalUser();
                int id = resultSet.getInt("id_person");
                String nombres = resultSet.getString("user_names");
                String apellidos = resultSet.getString("last_names");
                //la contraseña que incluye el usuario
                Date birrth = resultSet.getDate("date_birth");
                Date registration = resultSet.getDate("registration_date");
                String email = resultSet.getString("email");
                String scripp_pass = resultSet.getString("pass_script");
                String typeString=resultSet.getString("type_user");
                TypeUser type = TypeUser.valueOf(typeString);
                String typeState=resultSet.getString("user_state");
                StatesUser state = StatesUser.valueOf(typeState);
                Date last_activity = resultSet.getDate("last_activity");
                int idDirection = resultSet.getInt("id_direction");
                Direction direction = chargeDirection(idDirection);
                Person enviar= new ProfessionalUser(id, nombres, apellidos,  scripp_pass, birrth, registration, email, type, state,direction, last_activity);
                user = searchProfesionalPersonForId((ProfessionalUser) enviar);
                usuarios.add(user);
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
            //Si no existe en la base de datos
            if (idBBDD == -1) {

                String textoConsulta = "insert into person(user_names,last_names,pass_script,date_birth,registration_date,email,type_user,user_state,last_activity,id_direction) values (?,?,?,?,?,?,?,?,?,?);";
                try (PreparedStatement preparedStatement = connection.prepareStatement(textoConsulta)) {
                    preparedStatement.setString(1, person.getNames());
                    preparedStatement.setString(2, person.getLastNames());
                    preparedStatement.setString(3, person.getPassScript());
                    preparedStatement.setDate(4, new Date(person.getBirthDate().getTime()));
                    preparedStatement.setDate(5, new Date(person.getRegistrationDate().getTime()));
                    preparedStatement.setString(6, person.getEmail());
                    preparedStatement.setString(7, String.valueOf(person.getTypeUser()));
                    preparedStatement.setString(8, String.valueOf(person.getState()));
                    preparedStatement.setDate(9, new Date(person.getLastActivityDate().getTime()));
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
    public void registerProfessionalUser(ProfessionalUser professionalUser) throws OperationsDBException, DuplicateKeyException {
        registerPerson(professionalUser);
        try {
            String textoConsulta = "insert into professional_user(id_person,collegiate,specialty,description) values (?,?,?,?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(textoConsulta)) {
                preparedStatement.setInt(1, professionalUser.getIdPerson());
                preparedStatement.setString(2, professionalUser.getCollegiate());
                preparedStatement.setString(3, professionalUser.getSpeciality());
                preparedStatement.setString(4, professionalUser.getDescription());
                preparedStatement.executeUpdate();

            }
        } catch (SQLException e) {
            throw new OperationsDBException("Se ha producido un error al guardar el usuario profesional");
        }
    }

    @Override
    public void registerNormalUser(NormalUser normalUser) throws OperationsDBException, DuplicateKeyException {
        normalUser = (NormalUser) registerPerson(normalUser);
        try {
            String textoConsulta = "insert into normal_user(id_person,nickname,in_therapy_session) values (?,?,?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(textoConsulta)) {
                preparedStatement.setInt(1, normalUser.getIdPerson());
                preparedStatement.setString(2, normalUser.getNames()/*getNickname()*/);
                preparedStatement.setInt(3, normalUser.isInTherapySession() ? 1 : 0);
                preparedStatement.executeUpdate();

            }
        } catch (SQLException e) {
            throw new OperationsDBException("Se ha producido un error al guardar el usuario normal");
        }
    }

    //métodos para cargar interfaz principal
    private List<ProfessionalUser> cargarHistorialesParaUsuarios() throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException, NullArgumentException, SQLException, OperationsDBException {
        List<ProfessionalUser> usuariosEs = new ArrayList<>();
        String query2 = "SELECT * FROM person";

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query2)) {
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
                person.setSpeciality(specialty);
                person.setDescription(description);
            }
        } catch (SQLException | NullArgumentException | IncorrectDataException e) {
            throw new OperationsDBException("Error al encontrar datos");
        }
        return person;
    }
    /*  @Override
    public NormalUser searchPatient(String nombre) {
        return null;
    }

    @Override
    public PsychologistUser searchPhsycologist(String nombre) {
        return null;
    }*/


    @Override
    public void close() throws Exception {
        connection.close();
    }
}
